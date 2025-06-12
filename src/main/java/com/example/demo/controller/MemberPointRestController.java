package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.model.dto.MemberCert;
import com.example.demo.model.dto.PointLogHistoryDTO;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.MemberService;
import com.example.demo.service.PointLogService;

@RestController
@RequestMapping(value = "/member/point")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8002"}, allowCredentials = "true")
public class MemberPointRestController {

	@Autowired
	public MemberService memberService;
	
	@Autowired
	public PointLogService pointLogService;
	
	 // 查詢自己的點數紀錄
    @GetMapping
    public ResponseEntity<ApiResponse<List<PointLogHistoryDTO>>> getLogsByCert(@SessionAttribute("memberCert") MemberCert cert
    	) {
    	    List<PointLogHistoryDTO> logs = pointLogService.getLogsByMemberIdToMember(cert.getMemberId());
    	    return ResponseEntity.ok(ApiResponse.success("查詢成功", logs));
    }
}
