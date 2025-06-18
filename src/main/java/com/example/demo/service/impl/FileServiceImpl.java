package com.example.demo.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.FileService;

@Service
public class FileServiceImpl implements FileService{
	
	private static final String UPLOAD_DIR = "uploads/images/";
	
	@Override
    public String saveImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IOException("未選擇圖片");
        }

        // 自動建立目錄
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 產生唯一檔名（加時間戳，防止重複）
        String originalFilename = file.getOriginalFilename();
        String fileName = System.currentTimeMillis() + "_" + (originalFilename != null ? originalFilename.replaceAll("\\s+", "_") : "image.jpg");

        // 儲存到 uploads/images/ 目錄下
        Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIR + fileName), StandardCopyOption.REPLACE_EXISTING);

        // 回傳給前端用的網址（對應 application.properties static-locations 設定）
        return "/images/" + fileName;
    }
}
