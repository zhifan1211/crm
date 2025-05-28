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
import com.example.demo.service.PointLogService;

@Service
public class PointLogServiceImpl implements PointLogService{
	
	@Autowired
	private PointLogRepository pointLogRepository;
	
	@Autowired
	private PointLogMapper pointLogMapper;

	@Override
	public List<PointLogDTO> getAllLogs() {
		return pointLogRepository.findAll()
								 .stream()
								 .map(pointLogMapper::toDto)
								 .toList();
	}

	@Override
	public PointLogDTO getLog(String logId) {
		PointLog pointLog = pointLogRepository.findById(logId).orElseThrow(() -> new PointLogNotFoundException("查無點數紀錄：logId" + logId));
		return pointLogMapper.toDto(pointLog);
	}

	@Override
	public void addLog(PointLogDTO pointLogDTO) {
		// 判斷 id 是否存在
		Optional<PointLog> optType = pointLogRepository.findById(pointLogDTO.getLogId());
		if(optType.isPresent()) { // 如果名稱已存在
			throw new PointLogAlreadyExistException("新增失敗：點數紀錄" + pointLogDTO.getLogId() + "已存在");
		}
		// 進入新增程序
		// DTO 轉 Entity
		PointLog pointLog = pointLogMapper.toEntity(pointLogDTO);
		// 將 Entity pointLog 存入
		pointLogRepository.save(pointLog);
		pointLogRepository.flush();
	}

	@Override
	public void addLog(String logId, Admin admin, Member member, PointType pointType, Integer points,
					   LocalDateTime createdAt, LocalDateTime expiredAt, String orderId, String note) {
		PointLogDTO pointLogDTO = new PointLogDTO(logId, admin.getAdminId(), member.getMemberId(), pointType.getTypeId(), points, createdAt, expiredAt, orderId, note);
		addLog(pointLogDTO);
	}
	
}
