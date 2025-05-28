package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.PointLogAlreadyExistException;
import com.example.demo.exception.PointLogNotFoundException;
import com.example.demo.mapper.PointLogMapper;
import com.example.demo.model.dto.PointLogDTO;
import com.example.demo.model.entity.Admin;
import com.example.demo.model.entity.Member;
import com.example.demo.model.entity.PointLog;
import com.example.demo.model.entity.PointType;
import com.example.demo.repository.PointLogRepository;
import com.example.demo.service.IdGeneratorService;
import com.example.demo.service.PointCollectionService;
import com.example.demo.service.PointLogService;
import com.example.demo.service.PointUsageService;

@Service
public class PointLogServiceImpl implements PointLogService{
	
	@Autowired
	private PointLogRepository pointLogRepository;
	
	@Autowired
	private PointLogMapper pointLogMapper;
	
	@Autowired
	private IdGeneratorService idGeneratorService;
	
	@Autowired
	private PointCollectionService pointCollectionService;
	
	@Autowired
	private PointUsageService pointUsageService;
	
	// 查詢所有點數紀錄
	@Override
	public List<PointLogDTO> getAllLogs() {
		return pointLogRepository.findAll()
								 .stream()
								 .map(pointLogMapper::toDto)
								 .toList();
	}
	
	// 查詢單筆點數紀錄
	@Override
	public PointLogDTO getLog(String logId) {
		PointLog pointLog = pointLogRepository.findById(logId).orElseThrow(() -> new PointLogNotFoundException("查無點數紀錄：logId" + logId));
		return pointLogMapper.toDto(pointLog);
	}
	
	// 新增單筆點數紀錄
	@Override
	public void addLog(PointLogDTO pointLogDTO) {
		// 自動產生 id
		String newLogId = idGeneratorService.generateId("LG");
		// 判斷 id 是否存在
		Optional<PointLog> optType = pointLogRepository.findById(pointLogDTO.getLogId());
		if(optType.isPresent()) { // 如果名稱已存在
			throw new PointLogAlreadyExistException("新增失敗：點數紀錄" + pointLogDTO.getLogId() + "已存在");
		}
		// 進入新增程序
		// DTO 轉 Entity
		pointLogDTO.setLogId(newLogId); // 把生成的 ID 放進 DTO
		PointLog pointLog = pointLogMapper.toEntity(pointLogDTO);
		// 將 Entity pointLog 存入
		pointLogRepository.save(pointLog);
		// 根據 pointType 的 Category 做處理
		switch (pointLog.getPointType().getCategory()) {
			case add -> pointCollectionService.addCollection(pointLog);
			case consume -> pointUsageService.addUsage(pointLog);
			default -> throw new IllegalArgumentException("未支援的點數類別");
		}
	}
	
	@Override
	public void addLog(String logId, Admin admin, Member member, PointType pointType, Integer points,
					   LocalDateTime createdAt, LocalDateTime expiredAt, String orderId, String note) {
		PointLogDTO pointLogDTO = new PointLogDTO(logId, admin.getAdminId(), member.getMemberId(), pointType.getTypeId(), points, createdAt, expiredAt, orderId, note);
		addLog(pointLogDTO);
	}
	
}
