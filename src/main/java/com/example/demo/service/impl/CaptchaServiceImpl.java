package com.example.demo.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.service.CaptchaService;
import com.google.code.kaptcha.Producer;

import jakarta.servlet.http.HttpSession;

@Service
public class CaptchaServiceImpl implements CaptchaService {
	
	@Autowired
    private Producer captchaProducer;
	
    /**
     * 產生驗證碼圖片並回傳 byte[]
     * 同時將驗證碼文字存到 session
     */
	@Override
    public byte[] generateCaptcha(HttpSession session) throws IOException {
        // 1. 產生隨機驗證碼文字
        String capText = captchaProducer.createText();

        // 2. 存到 session 供後續比對
        session.setAttribute("captcha", capText);

        // 3. 產生圖像
        BufferedImage image = captchaProducer.createImage(capText);

        // 4. 轉換成 byte[] 方便回傳給前端
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        return baos.toByteArray();
    }

    /**
     * 驗證使用者輸入的驗證碼
     */
	@Override
    public boolean validateCaptcha(String inputCaptcha, HttpSession session) {
        String sessionCaptcha = (String) session.getAttribute("captcha");
        return sessionCaptcha != null && sessionCaptcha.equalsIgnoreCase(inputCaptcha);
    }
}
