package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.PhoneNumberDTO;
import com.example.demo.model.dto.ResetCodeDTO;
import com.example.demo.model.dto.ResetPasswordDTO;
import com.example.demo.model.entity.ResetToken;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.EmailVerificationService;
import com.example.demo.service.MemberService;
import com.example.demo.service.ResetTokenService;

@RestController
@RequestMapping(value = {"/member/reset-password"})
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8002"}, allowCredentials = "true")
public class MemberResetPasswordRestController {
	
	@Autowired
	private EmailVerificationService emailVerificationService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ResetTokenService resetTokenService;
	
	// 寄驗證信
	@PostMapping("/send-email")
    public ResponseEntity<ApiResponse<Void>> sendEmail(@RequestBody PhoneNumberDTO phoneNumberDTO) {
        emailVerificationService.sendResetPasswordCodeByPhone(phoneNumberDTO.getPhoneNumber());
        return ResponseEntity.ok(ApiResponse.success("驗證信已寄出，請檢查您的信箱", null));
    }
	
	// 驗證碼驗證成功後 → 回傳一次性 Token
	@PostMapping("/check-email")
	public ResponseEntity<ApiResponse<String>> verifyEmailCode(@RequestBody ResetCodeDTO resetCodeDTO) {
	    String token = emailVerificationService.verifyCodeAndIssueToken(resetCodeDTO);
	    if (token == null) {
	        return ResponseEntity.badRequest().body(ApiResponse.error(400, "驗證失敗：驗證碼錯誤或已過期"));
	    }
	    return ResponseEntity.ok(ApiResponse.success("Email 驗證成功", token));
	}

	// 驗證通過後，送出新的密碼來重設密碼
	@PutMapping
	public ResponseEntity<ApiResponse<Void>> resetPassword(@RequestBody ResetPasswordDTO dto) {
	    // 驗證 token
	    ResetToken token = resetTokenService.verifyToken(dto.getToken());

	    // 取出 phoneNumber（注意：這裡 token 需要有 phoneNumber 欄位）
	    String phoneNumber = token.getPhoneNumber(); 

	    // 執行密碼重設（直接使用你想保留的方法）
	    memberService.changePasswordByPhone(phoneNumber, dto.getNewPassword());

	    // 標記 token 已使用
	    resetTokenService.markTokenAsUsed(dto.getToken());

	    return ResponseEntity.ok(ApiResponse.success("新密碼修改完成", null));
	}
	
}
