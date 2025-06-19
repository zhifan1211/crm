package com.example.demo.service;

import com.example.demo.model.entity.PointLog;
import com.example.demo.util.VerificationCode;

public interface EmailService {
	public void sendVerificationCodeEmail(String to, VerificationCode code);
	public void sendConsumeNoticeEmail(String to, PointLog log);
}
