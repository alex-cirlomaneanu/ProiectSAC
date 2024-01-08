import asyncio
import csv
from pyppeteer import launch
import pandas as pd
import uuid

parent_selector="div"
article_selector = "article"
section_selector = ".ev7e6t817"
div_selector = ".ev7e6t812"
h_selector = ".er34gjf0"
a_selector = "a"

img_a_selector = ".e1j52kpv0"
img_a_div_selector = ".ooa-17rb9mp"
img_a_div_div_selector = ".ooa-7wsc2v"
img_a_div_div_div_selector = ".ooa-1h7b6p7"
img_a_div_div_div_div_selector = ".e1j52kpv4"
img_a_div_div_div_div_div_selector = ".e1j52kpv9"
img_a_div_div_div_div_div_selector_1 = ".e1j52kpv9"

price_h_div_div_selector = ".ev7e6t813"
price_h_div_selector = ".ermhljg4"
price_h_selector = "h3"
price_selector = ""


cols_tranlations = {
    'Km' : 'km',
    'Marca' : 'brand',
    'Model' : 'model',
    'Anul fabricației' : 'year',
    'Combustibil' : 'fuel_type',
    'Putere' : 'power',
    'Capacitate cilindrica' : 'cylinder_capacity',
    'Transmisie' : 'transmission',
    'Norma de poluare' : 'pollution_norm',
    'Cutie de viteze' : 'gearbox',
    'Tip Caroserie' : 'body_type',
    'Numar de portiere' : 'number_of_doors',
    'Culoare' : 'color',
    'Numar locuri' : 'number_of_seats',
    'Optiuni culoare' : 'color_option',
    'Are VIN (Serie sasiu)' : 'vin',
    'Tara de origine' : 'country_of_origin',
    'Primul proprietar (de nou)' : 'owned',
    'Stare' : 'vehicle_condition',
    'Emisii CO2' : 'co2_emission',
    'img_src': 'path_images',
    'price': 'price',
    'ad_id' : 'ad_id'
}

async def scrape_ad_page(ad_url):
    # print(ad_url)

    browser = await launch()
    page = await browser.newPage()

    # Navigate to the ad page
    await page.goto(ad_url, {'timeout': 60000})

    # Extract information from the ad page
    ad_info = await extract_ad_info(page)

    # Close the browser
    await browser.close()

    return ad_info

async def extract_ad_info(page):
    parent_div_selector = ".e1g4m1q20"
    details_selector = '.e1iqsx46'

    image_div_selector = ".e1ejyjdh0"
    
    await page.waitForSelector(parent_div_selector)

    details_div = await page.querySelector(details_selector)
    
    details = await details_div.querySelectorAll('div')

    details_dict = {}

    for detail in details:
        paragraphs = await detail.querySelectorAll('p')

        if paragraphs is None or len(paragraphs) == 0:
            continue

        paragraph = paragraphs[0]

        if paragraph is not None:
            detail_name = await page.evaluate('(el) => el.textContent', paragraph)
            # print(detail_name)

            if len(paragraphs) > 1:
                detail = await page.evaluate('(el) => el.textContent', paragraphs[1])
                details_dict[detail_name] = detail
            else:
                a = await detail.querySelector('a')
                detail = await page.evaluate('(el) => el.textContent', a)
                details_dict[detail_name] = detail
    
    return details_dict

async def get_crt_image(section):
    img_a_div_div_div_div_div = await section.querySelector(".e1j52kpv9")
    if img_a_div_div_div_div_div is not None:
        img_a_div_div_div_div = await img_a_div_div_div_div_div.querySelector(img_a_div_div_div_div_selector)
        # # print(img_a_div_div_div_div)
        
        img_a_div_div_div = await img_a_div_div_div_div.querySelector(img_a_div_div_div_selector)

        # # print(img_a_div_div_div)

        img_a_div_div = await img_a_div_div_div.querySelector(img_a_div_div_selector)
        # # print(img_a_div_div)

        img_a_div = await img_a_div_div.querySelector(img_a_div_selector)
        # # print(img_a_div)

        img_a = await img_a_div.querySelector("a")
        # # print(img_a)

        
        img = await img_a.querySelector("img")

        return img
        # # print(img)
    else:
        print('NOT SLIDESHOW')
        img_a = await section.querySelector(".e1j52kpv10")
        img = await img_a.querySelector("img")
        return img

async def scrape_car_ads():

    cars_details = []

    browser = await launch()

    page = await browser.newPage()

    for i in range(1, 201):
        if i == 1:
            await page.goto('https://www.autovit.ro/autoturisme', {'timeout': 60000})
        else:
            await page.goto('https://www.autovit.ro/autoturisme?page='+str(i), {'timeout': 60000})
        print("//////////////////////// I = ", i, " //////////////////////////////////")

        parent_div_selector = '.er8sc6m11'

        # Wait for the parent div to be present
        await page.waitForSelector(parent_div_selector)

        if page is not None:
            print('div found')

            ads = await page.querySelectorAll(f'{parent_div_selector} > div')
            print(len(ads))
            
            j = 0
            for ad in ads:
                print('\n')
                class_attribute = await page.evaluate('(el) => el.className', ad)
                
                print('ad POSSIBLE')
                if len(class_attribute) == 0:
                    print('ad found')
                    article = await ad.querySelector(article_selector)

                    if article is not None:
                        print('article found')
                        section = await article.querySelector(section_selector)
                        
                        div = await section.querySelector(div_selector)
                        
                        h = await div.querySelector(h_selector)

                        a = await h.querySelector(a_selector)
                    
                        url = await page.evaluate('(el) => el.href', a)
                        print(url)
                        
                        # img_a_div_div_div_div_div = await section.querySelector(".e1j52kpv9")

                        img = await get_crt_image(section=section)

                        if img is not None:
                            print('IMAGE DIV FOUND')
                            
                            image_src = await page.evaluate('(el) => el.src', img)

                            # print(image_src)

                            price_h_div_div = await section.querySelector(price_h_div_div_selector)
                            price_h_div= await price_h_div_div.querySelector(price_h_div_selector)
                            price_h = await price_h_div.querySelector(price_h_selector)

                            price = await page.evaluate('(el) => el.textContent', price_h)
                            # print(price)

                            j += 1
                            print(f'Get crt_car details -> {j}')

                            try:
                                details_dict = await scrape_ad_page(url)
                                print(f'Finished getting crt_car details-> {j}\n')

                                details_dict['price'] = price
                                details_dict['img_src'] = image_src
                                details_dict['ad_id'] = str(uuid.uuid4())

                                crt_car_details = []
                                for key  in cols_tranlations:
                                    if key in details_dict:
                                        crt_car_details.append(details_dict[key])
                                    else:
                                        crt_car_details.append(None)

                                cars_details.append(crt_car_details)
                            finally:
                                continue
        print ("FINISHED SHOWING THE CURRENT PAGE'S CARS!!!!!!!!!!!!!")

        
        if i == 1:
            print(f"i = {i} - save current ads to csv")
            col_names = [cols_tranlations[key] for key in cols_tranlations]
            df = pd.DataFrame(columns=col_names, data=cars_details)
            csv_file_path = 'output.csv'

            df.to_csv(csv_file_path, index=False)
            cars_details = []
        else:
            if i % 2 == 0:
                print(f"i = {i} - save current ads to csv")
                col_names = [cols_tranlations[key] for key in cols_tranlations]
                df = pd.DataFrame(columns=col_names, data=cars_details)
                csv_file_path = 'output.csv'

                df.to_csv(csv_file_path, mode='a', header=False, index=False)
                cars_details = []


    await browser.close()
    return cars_details

async def save_to_csv(data, filename='detailed_car_ads.csv'):
    return
    # Assuming your data is a list of dictionaries
    keys = data[0].keys()

    with open(filename, 'w', newline='', encoding='utf-8') as csv_file:
        writer = csv.DictWriter(csv_file, fieldnames=keys)
        writer.writeheader()
        writer.writerows(data)

if __name__ == '__main__':
    loop = asyncio.get_event_loop()
    detailed_car_ads_data = loop.run_until_complete(scrape_car_ads())
    loop.run_until_complete(save_to_csv(detailed_car_ads_data))


    # image_div = await page.querySelector(image_div_selector)
    # image = await image_div.querySelector('img')
    # image_src = await page.evaluate('(el) => el.src', image)
    # print(image_src)
    # await page.waitForSelector('.e1ejyjdh0')
    # image = await page.querySelector('img')
    # first_image_src = await page.evaluate('(el) => el.src', image)
    # print(first_image_src)

    # await page.waitForSelector('.e1ejyjdh18')
    # slider_init_id = await page.querySelector('.slick-initialized')
    
    # slider_list_id = await slider_init_id.querySelector('.slick-list')
    
    # slider_track_id = await slider_list_id.querySelector('.slick-track')
    
    # slider_crt_id = await slider_track_id.querySelector('.slick-active')

    # print(await page.evaluate("(el) => el.className", slider_crt_id))
    # print(slider_crt_id)

    # slider_crt_id_div = await slider_crt_id.querySelector('div:not([class])')
    # print(slider_crt_id_div)
    
    # image_div_div = await slider_crt_id_div.querySelector('.e1ejyjdh16')
    # print(image_div_div)   

    # await page.waitForSelector('.e1ejyjdh18')

    # image_sources = await page.evaluate('''() => {
    #     const images = document.querySelectorAll('img');
    #     return Array.from(images).map(img => img.src);
    # }''', '.e1ejyjdh18')

    # for source in image_sources:
    #     print(source)

    # ad_info = []
    # print()
    # return ad_info