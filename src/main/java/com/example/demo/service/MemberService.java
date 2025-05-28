package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.demo.model.dto.MemberDTO;
import com.example.demo.model.entity.Gender;
import com.example.demo.model.entity.Level;

public interface MemberService {
	public MemberDTO getMemberById(String memberId);
	public MemberDTO getMemberByPhoneNumber(String phoneNumber);
	public void addMember(MemberDTO memberDTO, String plainPassword);
	public void addMember(String memberId, String lastName, String firstName, Gender gender, String phoneNumber, String password, Level level,
						  String email, String region, LocalDate birthDate, Boolean confirmEmail, LocalDateTime createdAt, LocalDateTime updatedAt);
	public void updateMember(String memberId, MemberDTO memberDTO);
	public void updateMember(String memberId, String lastName, String firstName, Gender gender, String phoneNumber, String password, Level level,
			  				 String email, String region, LocalDate birthDate, Boolean confirmEmail, LocalDateTime createdAt, LocalDateTime updatedAt);
	public void deleteMember(String memberId);
}
