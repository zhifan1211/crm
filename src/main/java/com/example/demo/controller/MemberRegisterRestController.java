package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.MemberRegisterDTO;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.MemberService;

@RestController
@RequestMapping("/member/register")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8002"}, allowCredentials = "true")
public class MemberRegisterRestController {
	
	@Autowired
    private MemberService memberService;
	
    // 會員註冊
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerMember(@RequestBody MemberRegisterDTO registerDTO) {
        try {
            memberService.addMember(registerDTO);
            return ResponseEntity.ok(ApiResponse.success("註冊成功", null));
        } catch (Exception e) {
        	return ResponseEntity.badRequest().body(ApiResponse.error(400, "電話已被註冊"));
        }
    }
}
