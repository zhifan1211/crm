package com.example.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.dto.PointTypeDTO;
import com.example.demo.model.entity.PointType;

@Component
public class PointTypeMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	// Entity 轉 DTO
	public PointTypeDTO toDto(PointType pointType) {
		return modelMapper.map(pointType, PointTypeDTO.class);
	}
	
	// DTO 轉 Entity
	public PointType toEntity(PointTypeDTO pointTypeDTO) {
		return modelMapper.map(pointTypeDTO, PointType.class);
	}
}
