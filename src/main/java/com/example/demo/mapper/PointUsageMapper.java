package com.example.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.dto.PointUsageDTO;
import com.example.demo.model.entity.PointUsage;

@Component
public class PointUsageMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	// Entity 轉 DTO
	public PointUsageDTO toDto(PointUsage pointUsage) {
		return modelMapper.map(pointUsage, PointUsageDTO.class);
	}
	
	// DTO 轉 Entity
	public PointUsage toEntity(PointUsageDTO pointUsageDTO) {
		return modelMapper.map(pointUsageDTO, PointUsage.class);
	}
}
