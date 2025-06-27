package com.example.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.CertException;
import com.example.demo.model.dto.AdminCert;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.AdminCertService;
import com.example.demo.service.CaptchaService;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8002"}, allowCredentials = "true")
public class AdminLoginRestController {
    
    @Autowired
    private AdminCertService adminCertService;
    
    @Autowired
    private CaptchaService captchaService;
    
    // 登入
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(
            @RequestParam String username, 
            @RequestParam String password, 
            @RequestParam String captcha,
            HttpSession session) throws CertException {
        // 先比對驗證碼
        boolean captchaValid = captchaService.validateCaptcha(captcha, session);
        if (!captchaValid) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(400, "驗證碼錯誤"));
        }

        // 再檢查帳密
        AdminCert adminCert = adminCertService.getAdminCert(username, password);
        session.setAttribute("adminCert", adminCert);

        return ResponseEntity.ok(ApiResponse.success("登入成功", null));
    }
    
    // 登出
    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpSession session) {
        if(session.getAttribute("adminCert") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(ApiResponse.error(401, "登出失敗: 尚未登入"));
        }
        session.removeAttribute("adminCert");
        return ResponseEntity.ok(ApiResponse.success("登出成功", null));
    }
    
    // 確認是否登入，取得 Cert
    @GetMapping("/check-login")
    public ResponseEntity<ApiResponse<Boolean>> checkLogin(HttpSession session) {
        boolean loggedIn = session.getAttribute("adminCert") != null;
        System.out.println("2=> " + session.getAttribute("adminCert"));
        System.out.println("CHECK sessionId=" + session.getId());
        return ResponseEntity.ok(ApiResponse.success("檢查登入", loggedIn));
    }
    
    // 圖形驗證碼
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response, HttpSession session) throws IOException {
        response.setContentType("image/jpeg");
        byte[] imageBytes = captchaService.generateCaptcha(session);
        ServletOutputStream out = response.getOutputStream();
        out.write(imageBytes);
        out.flush();
        out.close();
    }
    
    @ExceptionHandler(CertException.class)
    public ResponseEntity<ApiResponse<Void>> handleCertException(CertException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(ApiResponse.error(401, "登入失敗: " + e.getMessage()));
    }
}
