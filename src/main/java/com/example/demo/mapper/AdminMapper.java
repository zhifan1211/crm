package com.example.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.dto.AdminCreateDTO;
import com.example.demo.model.dto.AdminDTO;
import com.example.demo.model.entity.Admin;

@Component
public class AdminMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	// Entity 轉 DTO
	public AdminDTO toDto(Admin admin) {
		return modelMapper.map(admin, AdminDTO.class);
	}
	
	// DTO 轉 Entity
	public Admin toEntity(AdminDTO adminDTO) {
		return modelMapper.map(adminDTO, Admin.class);
	}
	
	public Admin toEntity(AdminCreateDTO adminCreateDTO) {
	    return modelMapper.map(adminCreateDTO, Admin.class);
	}
}
