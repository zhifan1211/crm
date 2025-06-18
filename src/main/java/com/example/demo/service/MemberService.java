package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.ChangePasswordDTO;
import com.example.demo.model.dto.MemberDTO;
import com.example.demo.model.dto.MemberEditDTO;
import com.example.demo.model.dto.MemberRegisterDTO;
import com.example.demo.model.dto.MemberViewDTO;
import com.example.demo.model.entity.Member;

public interface MemberService {
	List<MemberViewDTO> getAllMembers();
	public MemberViewDTO getMemberViewById(String memberId);
	public MemberDTO getMemberByPhoneNumber(String phoneNumber);
	public void addMember(MemberRegisterDTO memberRegisterDTO); // 註冊會員
	public MemberEditDTO updateMemberByMember(String memberId, MemberEditDTO memberEditDTO); // 會員更新個人資訊
	public MemberEditDTO getMemberById(String memberId);
	public void setEmailConfirmed(String memberId);
	public void changePassword(String memberId, ChangePasswordDTO dto);
    public void changePasswordByPhone(String phoneNumber, String newPassword);
	public void upgradeMemberLevelIfQualified(Member member) ;
	public boolean isFormalQualified(Member member);
	public boolean isNotBlank(String s);
	public void toggleActive(String memberId);
}
