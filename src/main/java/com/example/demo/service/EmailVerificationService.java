package com.example.demo.service;

import com.example.demo.model.dto.ResetCodeDTO;

public interface EmailVerificationService {
	public void generateAndSendCode(String email);
	public boolean verifyCode(String email, String inputCode);
	public void sendResetPasswordCodeByPhone(String phoneNumber);
	public String verifyCodeAndIssueToken(ResetCodeDTO dto);
}
