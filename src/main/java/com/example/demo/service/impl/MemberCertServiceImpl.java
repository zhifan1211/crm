package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.MemberNotFoundException;
import com.example.demo.exception.PasswordInvalidException;
import com.example.demo.model.dto.MemberCert;
import com.example.demo.model.entity.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MemberCertService;
import com.example.demo.util.Hash;

@Service
public class MemberCertServiceImpl implements MemberCertService{
	
	@Autowired
	public MemberRepository memberRepository;

	@Override
	public MemberCert getMemberCert(String phoneNumber, String password) throws MemberNotFoundException, PasswordInvalidException {
		// 1. 是否有此管理員
		Member member = memberRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new MemberNotFoundException("查無此會員"));
		// 2. 是否啟用中
	    if (!member.getActive()) {
	        throw new PasswordInvalidException("此帳號已停用，無法登入");
	    }
		// 3. 密碼 hash 比對
		String passwordHash = Hash.getHash(password, member.getSalt());
		if(!passwordHash.equals(member.getPasswordHash())) {
			throw new PasswordInvalidException("密碼錯誤");
		}
		// 4. 簽發憑證
		MemberCert memberCert = new MemberCert(member.getMemberId());
		return memberCert;
	}
	
	
}
