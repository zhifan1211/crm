package com.example.demo.service;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.util.VerificationCode;

import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

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
