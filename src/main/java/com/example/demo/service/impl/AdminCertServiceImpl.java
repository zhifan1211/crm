package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.AdminNotFoundException;
import com.example.demo.exception.PasswordInvalidException;
import com.example.demo.model.dto.AdminCert;
import com.example.demo.model.entity.Admin;
import com.example.demo.repository.AdminRepository;
import com.example.demo.service.AdminCertService;
import com.example.demo.util.Hash;

@Service
public class AdminCertServiceImpl implements AdminCertService {
	
	@Autowired
	private AdminRepository adminRepository;

	@Override
	public AdminCert getAdminCert(String username, String password) throws AdminNotFoundException, PasswordInvalidException {
		// 1. 是否有此管理員
		Admin admin = adminRepository.findByUsername(username).orElseThrow(() -> new AdminNotFoundException("查無此管理員"));
		// 2. 密碼 hash 比對
		String passwordHash = Hash.getHash(password, admin.getSalt());
		if(!passwordHash.equals(admin.getPasswordHash())) {
			throw new PasswordInvalidException("密碼錯誤");
		}
		// 3. 簽發憑證
		AdminCert adminCert = new AdminCert(admin.getAdminId(), admin.getUsername(), admin.getUnit());
		return adminCert;
	}
	
	
}
