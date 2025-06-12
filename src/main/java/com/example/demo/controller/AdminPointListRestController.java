package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.PointLogViewDTO;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.PointLogService;

@RestController
@RequestMapping(value = {"/admin/point-list"})
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8002"}, allowCredentials = "true")
public class AdminPointListRestController {
	
	@Autowired
	private PointLogService pointLogService;
	
	// 取得所有點數列表
	@GetMapping
    public ResponseEntity<ApiResponse<List<PointLogViewDTO>>> getLogsByAllMember() {
    	    List<PointLogViewDTO> logs = pointLogService.getLogByAllMemberToAdmin();
    	    return ResponseEntity.ok(ApiResponse.success("查詢成功", logs));
    }
	
}
