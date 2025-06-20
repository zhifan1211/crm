package com.example.demo.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.DashboardSummaryDTO;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.AdminDashboardService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8002",}, allowCredentials = "true")
public class AdminDashboardRestController {
	
	
	@Autowired
	private AdminDashboardService adminDashboardService;
	
    // 回傳所有儀表板數據
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<DashboardSummaryDTO>> getDashboardSummary(
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        DashboardSummaryDTO dto = adminDashboardService.getDashboardSummary(start, end);
        return ResponseEntity.ok(ApiResponse.success("查詢成功", dto));
    }
}
