package com.example.demo.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.exception.CertException;
import com.example.demo.service.AdminCertService;

@SpringBootTest
public class AdminCertServiceTest {

	@Autowired
	private AdminCertService adminCertService;
	
	@Test
	public void testAdminAdd() {
		try {
			System.out.println(adminCertService.getAdminCert("market001", "1234"));
		} catch (CertException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
