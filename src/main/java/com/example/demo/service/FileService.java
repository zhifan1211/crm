package com.example.demo.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	String saveImage(MultipartFile file) throws IOException;
}
