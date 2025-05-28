package com.example.demo.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.entity.Gender;
import com.example.demo.model.entity.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.util.Hash;

@SpringBootTest
public class Test_AddMember {
	
	@Autowired
	private MemberRepository memberRepository;
	
	String plainPassword = "1234";
    String salt = Hash.getSalt();
    String passwordHash = Hash.getHash(plainPassword, salt);
	
	@Test
	public void addMember() {
		Member member = new Member();
		member.setMemberId("MB001");
		member.setLastName("鍾");
		member.setFirstName("智凡");
		member.setGender(Gender.male);
		member.setPhoneNumber("+886 916280406");
		member.setPasswordHash(passwordHash);
		member.setSalt(salt);	
		
		memberRepository.save(member); //存入資料庫
		
		System.out.println("會員建立成功！");
	}
}
