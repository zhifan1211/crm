package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.model.dto.ChangePasswordDTO;
import com.example.demo.model.dto.EmailCodeDTO;
import com.example.demo.model.dto.EmailDTO;
import com.example.demo.model.dto.MemberCert;
import com.example.demo.model.dto.MemberEditDTO;
import com.example.demo.model.dto.MemberInfoDTO;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.EmailVerificationService;
import com.example.demo.service.MemberService;

@RestController
@RequestMapping(value = {"/member"})
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8002"}, allowCredentials = "true")
public class MemberRestController {
		
	@Autowired
	public MemberService memberService;
	
	@Autowired
	public EmailVerificationService emailVerificationService;
	
	// 取得自己的資料（大部分用）
	@GetMapping("/info")
	public ResponseEntity<ApiResponse<MemberInfoDTO>> getMemberInfo(@SessionAttribute("memberCert") MemberCert cert){
		MemberInfoDTO dto = memberService.getMemberInfo(cert.getMemberId());
		return ResponseEntity.ok(ApiResponse.success("查詢成功", dto));
	}
	
	// 取得自己的資料（編輯用）
	@GetMapping("/me")
	public ResponseEntity<ApiResponse<MemberEditDTO>> getMember(@SessionAttribute("memberCert") MemberCert cert){
		MemberEditDTO dto = memberService.getMemberById(cert.getMemberId());
		return ResponseEntity.ok(ApiResponse.success("查詢成功", dto));
	}
	
	// 編輯自己的資料
	@PutMapping("/edit")
	public ResponseEntity<ApiResponse<MemberEditDTO>> updateMember(@SessionAttribute("memberCert") MemberCert cert, @RequestBody MemberEditDTO memberEditDTO){
		MemberEditDTO dto = memberService.updateMemberByMember(cert.getMemberId(), memberEditDTO);
		return ResponseEntity.ok(ApiResponse.success("更新成功", dto));
	}
	
	// 寄驗證信
	@PostMapping("/edit/send-email")
	public ResponseEntity<ApiResponse<Void>> sendEmail(@SessionAttribute("memberCert") MemberCert cert, @RequestBody EmailDTO emailDTO){
	    emailVerificationService.generateAndSendCode(emailDTO.getEmail());
		return ResponseEntity.ok(ApiResponse.success("驗證信已寄出", null ));
	}
	
	// 確認郵件驗證碼
	@PostMapping("/edit/check-email")
	public ResponseEntity<ApiResponse<Void>> verifyEmailCode(@SessionAttribute("memberCert") MemberCert cert, @RequestBody EmailCodeDTO dto) {
	    boolean success = emailVerificationService.verifyCode(dto.getEmail(), dto.getCode());
	    if (!success) {
	        return ResponseEntity.badRequest().body(ApiResponse.error(400, "驗證失敗：驗證碼錯誤或已過期"));
	    }
	    // 設定會員 emailConfirm 為 true
	    memberService.setEmailConfirmed(cert.getMemberId());
	    return ResponseEntity.ok(ApiResponse.success("Email 驗證成功", null));
	}
	
	// 修改密碼
	@PutMapping("/edit/change-password")
	public ResponseEntity<ApiResponse<Void>> changePassword(@SessionAttribute("memberCert") MemberCert cert, @RequestBody ChangePasswordDTO changePasswordDTO){
		memberService.changePassword(cert.getMemberId(), changePasswordDTO);
		return ResponseEntity.ok(ApiResponse.success("新密碼修改完成", null));
	}
}
