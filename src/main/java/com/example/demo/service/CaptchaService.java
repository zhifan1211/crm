package com.example.demo.service;

import java.io.IOException;

import jakarta.servlet.http.HttpSession;

public interface CaptchaService {
	byte[] generateCaptcha(HttpSession session) throws IOException;
	boolean validateCaptcha(String inputCaptcha, HttpSession session);
}
