package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.exception.CertException;
import com.example.demo.model.dto.AdminCert;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.AdminCertService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
@CrossOrigin(origins = {"http://localhost:5173, http://localhost:8002"}, allowCredentials = "true")
public class AdminLoginRestController {
	
	@Autowired
	private AdminCertService adminCertService;
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<Void>> login(@RequestParam String username, @RequestParam String password, HttpSession session){
		try {
			AdminCert adminCert = adminCertService.getAdminCert(username, password);
			session.setAttribute("adminCert", adminCert);
			return ResponseEntity.ok(ApiResponse.success("登入成功", null));
		} catch (CertException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(401, "登入失敗:" + e.getMessage()));
		}
	}
	
	@GetMapping("/logout")
	public ResponseEntity<ApiResponse<Void>> logout(HttpSession session){
		if(session.getAttribute("adminCert") == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(401, "登出失敗: 尚未登入"));
		}
		session.invalidate();
		return ResponseEntity.ok(ApiResponse.success("登出成功", null));
	}
	
	@GetMapping("/check-login")
	public ResponseEntity<ApiResponse<Boolean>> checkLogin(HttpSession session){
		boolean loggedIn = session.getAttribute("adminCert") != null;
		return ResponseEntity.ok(ApiResponse.success("檢查登入", loggedIn));
	}
}
