package com.example.demo.member;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.entity.Gender;
import com.example.demo.model.entity.Level;
import com.example.demo.service.MemberService;

@SpringBootTest
public class MemberJPAAddTest {
	
	@Autowired
	private MemberService memberService;
	
	@Test
	public void testMemberAdd() {
		memberService.addMember("MB001", "鍾", "智凡", Gender.male, "+886 916280406", "1234", Level.formal, "zhifan1211@gmail.com", "臺灣", LocalDate.parse("1997-12-11"), true, null, null);
		System.out.println("會員建立成功！");
	}
}
