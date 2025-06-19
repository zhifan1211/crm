package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.model.entity.PointLog;
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
        message.setSubject("Otter Point - 驗證信");
        message.setText("尊敬的會員您好，您的驗證碼為 " + code.getCode() + " ，請於 10 分鐘內完成驗證。");

        mailSender.send(message);
        System.out.println("寄出成功給：" + to);
	}
	
	public void sendConsumeNoticeEmail(String to, PointLog log) {
	    if (to == null || to.isBlank()) {
	        System.out.println("未設定 email，不發送通知。");
	        return;
	    }
	    try {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom(from);
	        message.setTo(to);
	        message.setSubject("Otter Point - 通知信");
	        message.setText("尊敬的會員您好，您已成功消耗 " + log.getPoints() + " 點，詳情請登入 Otter Point 查看");

	        mailSender.send(message);
	        System.out.println("寄出成功給：" + to);
	    } catch (Exception e) {
	        System.err.println("寄送消耗通知信失敗：" + e.getMessage());
	        // 可選：寫入 log 或後續補救措施
	    }
	}

}
