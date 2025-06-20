package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.ChangePasswordDTO;
import com.example.demo.model.dto.MemberDTO;
import com.example.demo.model.dto.MemberEditDTO;
import com.example.demo.model.dto.MemberInfoDTO;
import com.example.demo.model.dto.MemberRegisterDTO;
import com.example.demo.model.dto.MemberViewDTO;
import com.example.demo.model.entity.Member;

public interface MemberService {
	List<MemberViewDTO> getAllMembers();
	MemberViewDTO getMemberViewById(String memberId);
	MemberDTO getMemberByPhoneNumber(String phoneNumber);
	void addMember(MemberRegisterDTO memberRegisterDTO); // 註冊會員
	MemberEditDTO updateMemberByMember(String memberId, MemberEditDTO memberEditDTO); // 會員更新個人資訊
	MemberEditDTO getMemberById(String memberId);
	void setEmailConfirmed(String memberId);
	void changePassword(String memberId, ChangePasswordDTO dto);
    void changePasswordByPhone(String phoneNumber, String newPassword);
	void upgradeMemberLevelIfQualified(Member member) ;
	boolean isFormalQualified(Member member);
	boolean isNotBlank(String s);
	void toggleActive(String memberId);
	MemberInfoDTO getMemberInfo(String memberId);
}