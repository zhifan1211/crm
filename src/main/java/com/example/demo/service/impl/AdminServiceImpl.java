package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.AdminAlreadyExistException;
import com.example.demo.exception.AdminNotFoundException;
import com.example.demo.mapper.AdminMapper;
import com.example.demo.model.dto.AdminDTO;
import com.example.demo.model.entity.Admin;
import com.example.demo.model.entity.Unit;
import com.example.demo.repository.AdminRepository;
import com.example.demo.service.AdminService;
import com.example.demo.service.IdGeneratorService;
import com.example.demo.util.Hash;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private AdminMapper adminMapper;
	
	@Autowired
	private IdGeneratorService idGeneratorService;
	
	// 用 id 得到管理者
	@Override
	public AdminDTO getAdminById(String adminId) {
		Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new AdminNotFoundException("ID:"+ adminId +"找不到管理員"));
		AdminDTO dto  = adminMapper.toDto(admin);
		return dto;
	}
	
	// 用 帳號 得到管理者
	@Override
	public AdminDTO getAdminByUsername(String username) {
		Admin admin = adminRepository.findByUsername(username).orElseThrow(() -> new AdminNotFoundException("帳號:"+ username +"找不到管理員"));;
		AdminDTO dto = adminMapper.toDto(admin);
		return dto;
	}
	
	// 新增管理者
	@Override
	public void addAdmin(AdminDTO adminDTO, String plainPassword) {
		// 自動產生 id
		String newAdminId = idGeneratorService.generateId("AD");
		// 判斷 id 是否存在 (保險起見)
		Optional<Admin> optAdmin = adminRepository.findById(adminDTO.getAdminId());
		if(optAdmin.isPresent()) {
			throw new AdminAlreadyExistException("新增失敗，管理員ID:" + adminDTO.getAdminId() + "已存在");
		}
		// 加鹽與密碼加密
		String salt = Hash.getSalt();
		String passwordHash = Hash.getHash(plainPassword, salt);
		// 進入新增程序
		// DTO 轉 Entity
		adminDTO.setAdminId(newAdminId); // 把生成的 ID 放進 DTO
		Admin admin = adminMapper.toEntity(adminDTO);
		// 存入 Entity
		admin.setSalt(salt);
		admin.setPasswordHash(passwordHash);
		
		adminRepository.saveAndFlush(admin);
	}
	
	@Override
	public void addAdmin(String adminId, String username, String name, String password, Unit unit, Boolean active,
						 LocalDateTime createdAt) {
		AdminDTO dto = new AdminDTO(adminId, username, name, unit, active, createdAt);
		addAdmin(dto, password);
	}
	
	// 更新管理員
	@Override
	public void updateAdmin(String adminId, AdminDTO adminDTO) {
		// 判斷 id 是否存在
		Optional<Admin> optAdmin = adminRepository.findById(adminId);
		if(optAdmin.isEmpty()) {
			throw new AdminNotFoundException("修改失敗：管理員ID:" + adminId + "不存在");
		}
		adminDTO.setAdminId(adminId);
		Admin admin = adminMapper.toEntity(adminDTO);
		adminRepository.saveAndFlush(admin);		
	}
	
	@Override
	public void updateAdmin(String adminId, String username, String name, String password, Unit unit, Boolean active, LocalDateTime createdAt) {
		AdminDTO adminDTO = new AdminDTO(adminId, username, name, unit, active, createdAt);
		updateAdmin(adminId, adminDTO);
	}
	
	// 刪除管理員
	@Override
	public void deleteAdmin(String adminId) {
		// 判斷 id 是否存在
		Optional<Admin> optAdmin = adminRepository.findById(adminId);
		if(optAdmin.isEmpty()) {
			throw new AdminNotFoundException("刪除失敗：管理員ID:" + adminId + "不存在");
		}
		adminRepository.deleteById(adminId);
	}
	
}
