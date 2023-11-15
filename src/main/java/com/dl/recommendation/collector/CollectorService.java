package com.dl.recommendation.collector;

import com.microsoft.playwright.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollectorService {
    private static final String URL = "https://www.autovit.ro/autoturisme/";
    private static final String PATH_FILE = "src/main/python/resources/brand-models.txt";

    private static int NUMBER = 0;

    public Map<String, ArrayList<String>> readDataFromFile() {
        Map<String, ArrayList<String>> dataMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String brand = parts[0];
                    String[] models = parts[1].substring(1, parts[1].length() - 1) .split(",");
                    dataMap.put(brand, new ArrayList<>(List.of(models)));
                }
            }
        } catch (IOException e) {
            return null;
        }

        return dataMap;
    }

    private static void extractDataFromDiv(Page page, Map<String, String> dataMap) {
        var priceElement = page.querySelector("#siteWrap > main > div.flex-container-main > div.flex-container-main__right > div.offer-content__aside > div.offer-summary > div.price-wrapper > div > span.offer-price__number");
        if (priceElement != null) {
            dataMap.put("price", priceElement.innerText());
        }

        ElementHandle divElement = page.querySelector("div[id=\"parameters\"]");
        if (divElement == null) {
            return;
        }
        List<ElementHandle> list1Elements = divElement.querySelectorAll("ul:nth-child(1) li");
        List<ElementHandle> list2Elements = divElement.querySelectorAll("ul:nth-child(2) li");

        list1Elements.addAll(list2Elements);
        for (ElementHandle list1Item : list1Elements) {
            var l1 = list1Item.querySelector(".offer-params__label");
            var l2 = list1Item.querySelector(".offer-params__value");

            if (l1 == null || l2 == null) {
                continue;
            }
            dataMap.put(l1.innerText(), l2.innerText());
        }

        String filePath = "vehicles/" + NUMBER + "/data.txt";
        try {
            File file = new File(filePath);
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (var entry : dataMap.entrySet()) {
                writer.write(entry.getKey() + ": " +  entry.getValue());
                writer.newLine();
            }
            System.out.println("List saved to file successfully.");
        } catch (IOException e) {
            System.err.println("Error saving list to file: " + e.getMessage());
        }
    }

    private static void addVehicle(Page page) throws IOException, InterruptedException {
        File folder = new File("vehicles/" + NUMBER);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        Map<String, String> dataMap = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            String link = page.waitForSelector("img[class='bigImage landscape']").getAttribute("src");
            if (link == null) {
                continue;
            }
            dataMap.put("img" + i, link);

            Thread.sleep(2000);

            var next =  page.waitForSelector("//*[@id=\"offer-photos\"]/div[2]/button[3]/span");

            if (next != null) {
                next.click();
            }
        }
        extractDataFromDiv(page, dataMap);
        NUMBER++;
    }

    private void collectCars(Map<String, ArrayList<String>> data) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            for (var entry : data.entrySet()) {
                String brand = entry.getKey();
                ArrayList<String> models = entry.getValue();
                String url = URL + brand + "/";
                for (String model : models) {
                    int j = 11;
                    int ok = 1;
                    for (int i = 1; i < j; i++) {
                        page.navigate(url + model);
                        Thread.sleep(500);

                        var popUP = page.querySelector("button[id='onetrust-accept-btn-handler']");
                        if (popUP != null) {
                            popUP.click();
                        }

                        if (ok == 1) {
                            var num = page.querySelector("//*[@id=\"__next\"]/div/div/div/div[2]/main/div[2]/div/div[3]/div[1]/div[1]/p/b");
                            if (num == null) {
                                break;
                            }
                            String nr = num.innerText();
                            int number;
                            try {
                                number = Integer.parseInt(nr);
                            } catch (Exception e) {
                                number = 3;
                            }

                            ok = 0;
                            j = Math.min(number, 7);

                            if (number == 0) {
                                break;
                            }
                        }

                        String path = "(//article)[" + i + "]";
                        var elem = page.querySelector(path);
                        if (elem == null) {
                            break;
                        } else {
                            elem.click();
                        }
                        Thread.sleep(4000);
                        addVehicle(page);
                    }
                }
            }
            browser.close();
        } catch (IOException | InterruptedException e) {
            log.error("Error ", e);
        }
    }

    public ResponseEntity<?> collect() {
        Map<String, ArrayList<String>> data = readDataFromFile();
        if (data == null) {
            return ResponseEntity.ok("data == null");
        }

        collectCars(data);
        return ResponseEntity.ok("Finish");
    }

}
