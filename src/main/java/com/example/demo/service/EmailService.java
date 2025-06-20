package com.example.demo.service;

import java.time.LocalDate;

import com.example.demo.model.entity.PointLog;
import com.example.demo.util.VerificationCode;

public interface EmailService {
	void sendVerificationCodeEmail(String to, VerificationCode code);
	void sendConsumeNoticeEmail(String to, PointLog log);
	void sendExpiryReminderEmail(String to, LocalDate expiryDate);
}
