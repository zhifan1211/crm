package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.PointUsageDTO;
import com.example.demo.model.entity.PointLog;

public interface PointUsageService {
	List<PointUsageDTO> getAllUsages(); // 查詢所有點數追蹤
	public PointUsageDTO getUsage(String usageId); // 查詢單筆點數追蹤
	public void addUsage(PointLog pointLog); // 新增單筆點數追蹤
}
