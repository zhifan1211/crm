package com.example.demo.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.MemberAlreadyExistException;
import com.example.demo.exception.MemberNotFoundException;
import com.example.demo.mapper.MemberMapper;
import com.example.demo.model.dto.MemberDTO;
import com.example.demo.model.entity.Gender;
import com.example.demo.model.entity.Level;
import com.example.demo.model.entity.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.IdGeneratorService;
import com.example.demo.service.MemberService;
import com.example.demo.util.Hash;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private IdGeneratorService idGeneratorService;
	
	// 用 id 查詢得到指定會員
	@Override
	public MemberDTO getMemberById(String memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException("ID:"+ memberId +"找不到會員"));
		MemberDTO dto = memberMapper.toDto(member);
		return dto;
	}
	
	// 用 電話 查詢指定會員
	@Override
	public MemberDTO getMemberByPhoneNumber(String phoneNumber) {
		Member member = memberRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new MemberNotFoundException("電話:"+ phoneNumber +"找不到會員"));
		MemberDTO dto = memberMapper.toDto(member);
		return dto;
	}

	// 新增單筆會員
	@Override
	public void addMember(MemberDTO memberDTO, String plainPassword) {
		// 自動產生會員 id
		String newMemberId = idGeneratorService.generateId("MB");
		// 判斷 id 是否存在(保險起見)
		Optional<Member> optMember = memberRepository.findById(memberDTO.getMemberId());
		if(optMember.isPresent()) {
			throw new MemberAlreadyExistException("新增失敗，會員ID:" + memberDTO.getMemberId() + "已存在");
		}
		// 加鹽與密碼加密
		String salt = Hash.getSalt();
		String passwordHash = Hash.getHash(plainPassword, salt);
		// 進入新增程序
		// DTO 轉 Entity
		memberDTO.setMemberId(newMemberId); // 把生成的 ID 放進 DTO
		Member member = memberMapper.toEntity(memberDTO);
		// 存入 Entity
		member.setSalt(salt);
	    member.setPasswordHash(passwordHash);

	    memberRepository.saveAndFlush(member);
	}
	
	@Override
	public void addMember(String memberId, String lastName, String firstName, Gender gender, String phoneNumber,
						  String password, Level level, String email, String region, LocalDate birthDate,
						  Boolean confirmEmail, LocalDateTime createdAt, LocalDateTime updatedAt) {
		MemberDTO dto = new MemberDTO(memberId, lastName, firstName, gender, phoneNumber, level, email, region, birthDate, confirmEmail, createdAt, updatedAt);
	    addMember(dto, password);
	}
	
	// 更新會員
	@Override
	public void updateMember(String memberId, MemberDTO memberDTO) {
		// 判斷 id 是否存在
		Optional<Member> optMember = memberRepository.findById(memberId);
		if(optMember.isEmpty()) {
			throw new MemberNotFoundException("修改失敗：會員ID:" + memberId + "不存在");
		}
		memberDTO.setMemberId(memberId);
		Member member = memberMapper.toEntity(memberDTO);
		memberRepository.saveAndFlush(member);
	}

	@Override
	public void updateMember(String memberId, String lastName, String firstName, Gender gender, String phoneNumber, String password, Level level,
				 			 String email, String region, LocalDate birthDate, Boolean confirmEmail, LocalDateTime createdAt, LocalDateTime updatedAt) {
		MemberDTO memberDTO = new MemberDTO(memberId, lastName, firstName, gender, phoneNumber, level, email, region, birthDate, confirmEmail, createdAt, updatedAt);
		updateMember(memberId, memberDTO);
	}

	// 刪除會員
	@Override
	public void deleteMember(String memberId) {
		// 判斷 id 是否存在
		Optional<Member> optMember = memberRepository.findById(memberId);
		if(optMember.isEmpty()) {
			throw new MemberNotFoundException("刪除失敗：會員ID:" + memberId + "不存在");
		}
		memberRepository.deleteById(memberId);
	}
	
}
