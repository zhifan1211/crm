package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.exception.CertException;
import com.example.demo.model.dto.MemberCert;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.MemberCertService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8002"}, allowCredentials = "true")
public class MemberLoginRestController {

	@Autowired
	private MemberCertService memberCertService;

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<Void>> login(@RequestParam String phoneNumber, @RequestParam String password, HttpSession session)
		throws CertException{
		MemberCert memberCert = memberCertService.getMemberCert(phoneNumber, password);
		session.setAttribute("memberCert", memberCert);
		return ResponseEntity.ok(ApiResponse.success("登入成功", null));
	}

	@GetMapping("/logout")
	public ResponseEntity<ApiResponse<Void>> logout(HttpSession session){
		if(session.getAttribute("memberCert") == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(401, "登出失敗: 尚未登入"));
		}
		session.invalidate();
		return ResponseEntity.ok(ApiResponse.success("登出成功", null));
	}

	@GetMapping("/check-login")
	public ResponseEntity<ApiResponse<Boolean>> checkLogin(HttpSession session){
		boolean loggedIn = session.getAttribute("memberCert") != null;
		return ResponseEntity.ok(ApiResponse.success("檢查登入", loggedIn));
	}

	@ExceptionHandler(CertException.class)
	public ResponseEntity<ApiResponse<Void>> handleCertException(CertException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
		                     .body(ApiResponse.error(401, "登入失敗: " + e.getMessage()));
	}
}
