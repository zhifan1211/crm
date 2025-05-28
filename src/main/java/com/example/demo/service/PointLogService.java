package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.model.dto.PointLogDTO;
import com.example.demo.model.entity.Admin;
import com.example.demo.model.entity.Member;
import com.example.demo.model.entity.PointType;

public interface PointLogService {
	List<PointLogDTO> getAllLogs(); // 查詢所有點數紀錄
	public PointLogDTO getLog(String logId); // 查詢單筆點數紀錄
	public void addLog(PointLogDTO pointLogDTO); // 新增單筆點數紀錄
	public void addLog(String logId, Admin admin, Member member, PointType pointType, Integer points,
					   LocalDateTime createdAt, LocalDateTime expiredAt, String orderId, String note);
}
