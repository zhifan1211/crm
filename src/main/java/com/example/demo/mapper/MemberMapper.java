package com.example.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.dto.MemberDTO;
import com.example.demo.model.dto.MemberEditDTO;
import com.example.demo.model.dto.MemberInfoDTO;
import com.example.demo.model.dto.MemberViewDTO;
import com.example.demo.model.entity.Member;

@Component
public class MemberMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	// Entity 轉 DTO
	public MemberDTO toDto(Member member) {
		return modelMapper.map(member, MemberDTO.class);
	}
	
	// DTO 轉 Entity
	public Member toEntity(MemberDTO memberDTO) {
		return modelMapper.map(memberDTO, Member.class);
	}
	
	// Entity 轉 ViewDTO（管理後台用）
	public MemberViewDTO toViewDto(Member member) {
	    return modelMapper.map(member, MemberViewDTO.class);
	}
	
	// Entity 轉 DTO
	public MemberEditDTO toEditDto(Member member) {
		return modelMapper.map(member, MemberEditDTO.class);
	}
	
	// DTO 轉 Entity
	public Member toEntity(MemberEditDTO memberEditDTO) {
		return modelMapper.map(memberEditDTO, Member.class);
	}
	
	// Entity 轉 DTO
	public MemberInfoDTO toInfoDto(Member member) {
		return modelMapper.map(member, MemberInfoDTO.class);
	}
}
