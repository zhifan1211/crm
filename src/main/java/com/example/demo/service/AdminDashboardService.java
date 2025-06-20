package com.example.demo.service;

import java.time.LocalDateTime;

import com.example.demo.model.dto.DashboardSummaryDTO;

public interface AdminDashboardService {
    public DashboardSummaryDTO getDashboardSummary(LocalDateTime start, LocalDateTime end);
}
