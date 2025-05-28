package com.example.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.dto.PointCollectionDTO;
import com.example.demo.model.entity.PointCollection;

@Component
public class PointCollectionMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	// Entity 轉 DTO
	public PointCollectionDTO toDto(PointCollection pointCollection) {
		return modelMapper.map(pointCollection, PointCollectionDTO.class);
	}
	
	// DTO 轉 Entity
	public PointCollection toEntity(PointCollectionDTO pointCollectionDTO) {
		return modelMapper.map(pointCollectionDTO, PointCollection.class);
	}
}
