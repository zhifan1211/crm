package com.example.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.dto.PointLogDTO;
import com.example.demo.model.entity.PointLog;

@Component
public class PointLogMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	// Entity 轉 DTO
	public PointLogDTO toDto(PointLog pointLog) {
		return modelMapper.map(pointLog, PointLogDTO.class);
	}
	
	// DTO 轉 Entity
	public PointLog toEntity(PointLogDTO pointLogDTO) {
		return modelMapper.map(pointLogDTO, PointLog.class);
	}
}
