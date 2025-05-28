package com.example.demo.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.service.MemberService;

@SpringBootTest
public class MemberJPAReadTest {
	
	@Autowired
	private MemberService memberService;
	
	@Test
	public void testMemberAdd() {
		System.out.println(memberService.getMemberByPhoneNumber("+886 916280406"));
	}
}
