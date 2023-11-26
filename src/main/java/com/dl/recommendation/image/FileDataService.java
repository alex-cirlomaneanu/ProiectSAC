package com.dl.recommendation.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileDataService {
    private final static String FILE_PATH = "/home/alex/Desktop/frontend/auto-trade/public/images/users/";
    private final static String USER = "user";

    private String getTypeImage(String contentType) {
        if (contentType.endsWith(".jpg")){
            return ".jpg";
        } else if (contentType.endsWith(".jpeg")) {
            return ".jpeg";
        } else if (contentType.endsWith(".png")) {
            return ".png";
        }
        return "";
    }

    private FileData addFileData(MultipartFile file, String userId, String title, int index) {
        String filePath;
        String path = FILE_PATH + userId + "/" + title;
        File folder = new File(path);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        String newName = "img" + index + getTypeImage(file.getOriginalFilename());
        filePath = path + "/" + newName;
        return FileData.builder()
                .name(newName)
                .type(file.getContentType())
                .filePath(filePath)
                .build();
    }

    public Map<String, String> uploadImage(MultipartFile[] files, String userId, String title) throws IOException {
        StringBuilder images = new StringBuilder("[ ");
        Map<String, String> map = new HashMap<>();
        int index = 1;
        for (MultipartFile file : files) {
            FileData fileData = addFileData(file, userId, title, index++);
            images.append(fileData.getName()).append(", ");
            file.transferTo(new File(fileData.getFilePath()));
        }
        images = new StringBuilder(images.substring(0, images.length() - 2));
        images.append(" ]");

        map.put("images", images.toString());
        map.put("path", FILE_PATH + userId + "/" + title + "/");

        return map;
    }


    public String uploadSellerImage(MultipartFile file, String userID) throws IOException {
        String filePath = FILE_PATH + userID;
        File folder = new File(filePath);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        filePath +=  "/" + USER;
        folder = new File(filePath);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        filePath +=  "/" + file.getOriginalFilename();
        file.transferTo(new File(filePath));

        return filePath;
    }


//
//    public byte[] downloadImage(String fileName) throws IOException {
//        Optional<FileData> fileData = repository.findByName(fileName);
//        if (fileData.isEmpty())
//            return null;
//        String filePath = fileData.get().getFilePath();
//        return Files.readAllBytes(new File(filePath).toPath());
//    }

//
//    public List<String> getAllImages() {
//        return repository.findAllByName();
//    }
}
