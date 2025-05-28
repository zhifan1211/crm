package com.example.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.dto.MemberDTO;
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
}
