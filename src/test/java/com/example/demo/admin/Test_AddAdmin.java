package com.example.demo.admin;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.entity.Admin;
import com.example.demo.model.entity.Unit;
import com.example.demo.repository.AdminRepository;
import com.example.demo.util.Hash;

@SpringBootTest
public class Test_AddAdmin {
	
	@Autowired
	private AdminRepository adminRepository;
	
	String plainPassword = "1234";
    String salt = Hash.getSalt();
    String passwordHash = Hash.getHash(plainPassword, salt);
	
	@Test
	public void addAdmin() {
		Admin admin = new Admin();
		admin.setAdminId("AD002");
		admin.setUsername("operation001");
		admin.setAdminName("Mandy");
		admin.setPasswordHash(passwordHash);
		admin.setSalt(salt);
		admin.setUnit(Unit.OPS);
		admin.setActive(true);		
		
		adminRepository.save(admin); //存入資料庫
		
		System.out.println("管理員建立成功！");
	}
}
