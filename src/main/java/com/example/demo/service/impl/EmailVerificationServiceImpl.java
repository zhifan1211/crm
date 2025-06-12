package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.MemberNotFoundException;
import com.example.demo.model.dto.MemberDTO;
import com.example.demo.model.dto.ResetCodeDTO;
import com.example.demo.model.entity.EmailVerification;
import com.example.demo.model.entity.Member;
import com.example.demo.repository.EmailVerificationRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.EmailVerificationService;
import com.example.demo.service.IdGeneratorService;
import com.example.demo.service.MemberService;
import com.example.demo.service.ResetTokenService;
import com.example.demo.util.VerificationCode;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {
	
	@Autowired
	private EmailVerificationRepository emailVerificationRepository;
	
	@Autowired
	private EmailServiceImpl emailService;
	
	@Autowired
	private IdGeneratorService idGeneratorService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ResetTokenService resetTokenService;
	
	// 產生 code 並寄驗證信
	public void generateAndSendCode(String email) {
		VerificationCode code = new VerificationCode();

        EmailVerification record = new EmailVerification();

        String newVerificationId = idGeneratorService.generateId("EV");
        record.setVerificationId(newVerificationId);
        record.setEmail(email);
        record.setCode(code.getCode());
        record.setExpiredAt(code.getExpireAt());
        record.setUsed(false);
        record.setCreatedAt(LocalDateTime.now());

        emailVerificationRepository.save(record);
        emailService.sendVerificationCodeEmail(email, code);
	}
	
	// 確認驗證碼
	public boolean verifyCode(String email, String inputCode) {
	    Optional<EmailVerification> recordOpt =
	        emailVerificationRepository.findByEmailAndCodeAndUsedFalse(email, inputCode);

	    if (recordOpt.isEmpty()) return false;

	    EmailVerification record = recordOpt.get();
	    if (record.getExpiredAt().isBefore(LocalDateTime.now())) return false;

	    // 驗證成功
	    record.setUsed(true);
	    emailVerificationRepository.save(record);
	    return true;
	}
	
	// 用電話查這個信箱的會員是誰，再寄驗證信
	public void sendResetPasswordCodeByPhone(String phoneNumber) {
		MemberDTO memberDTO = memberService.getMemberByPhoneNumber(phoneNumber);
		String email = memberDTO.getEmail();
	    if (email == null || email.isBlank()) {
	        throw new RuntimeException("該會員尚未設定 email，無法重設密碼");
	    }

	    // 產生驗證碼
	    VerificationCode code = new VerificationCode();
	    EmailVerification record = new EmailVerification();
	    String newVerificationId = idGeneratorService.generateId("EV");
	    record.setVerificationId(newVerificationId);
	    record.setEmail(email);
	    record.setCode(code.getCode());
	    record.setExpiredAt(code.getExpireAt());
	    record.setUsed(false);
	    record.setCreatedAt(LocalDateTime.now());

	    emailVerificationRepository.save(record);
	    emailService.sendVerificationCodeEmail(email, code);
	}
	
	public String verifyCodeAndIssueToken(ResetCodeDTO dto) {
	    MemberDTO member = memberService.getMemberByPhoneNumber(dto.getPhoneNumber());
	    String email = member.getEmail();
	    if (email == null || email.isBlank()) {
	        return null;
	    }

	    boolean valid = this.verifyCode(email, dto.getCode());
	    if (!valid) return null;

	    return resetTokenService.createToken(dto.getPhoneNumber());
	}

	
}
