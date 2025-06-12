package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.service.EmailService;
import com.example.demo.util.VerificationCode;

@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
	public JavaMailSender mailSender;
		
	private String from = "zhifan1211@gmail.com";
	
	public void sendVerificationCodeEmail(String to, VerificationCode code) {
		SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("OTTER HOTEL - 正式會員驗證信");
        message.setText("尊敬的會員您好，您的驗證碼為 " + code.getCode() + " ，請於 10 分鐘內完成驗證。");

        mailSender.send(message);
        System.out.println("寄出成功給：" + to);
	}

}
