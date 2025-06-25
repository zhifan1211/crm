package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.AlreadyExistException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.AdminMapper;
import com.example.demo.model.dto.AdminCreateDTO;
import com.example.demo.model.dto.AdminDTO;
import com.example.demo.model.dto.ChangePasswordDTO;
import com.example.demo.model.entity.Admin;
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
		Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new NotFoundException("ADMIN_NOT_FOUND","查無此管理員"));
		AdminDTO dto  = adminMapper.toDto(admin);
		return dto;
	}
	
	// 新增管理者
	@Override
	public void addAdmin(AdminCreateDTO adminCreateDTO) {
	    String newAdminId = idGeneratorService.generateId("AD");

	    if (adminRepository.findByUsername(adminCreateDTO.getUsername()).isPresent()) {
	        throw new AlreadyExistException("ADMIN_ALREADY_EXIST","管理員已存在");
	    }

	    String plainPassword = "otterpoint";
	    String salt = Hash.getSalt();
	    String passwordHash = Hash.getHash(plainPassword, salt);

	    Admin admin = adminMapper.toEntity(adminCreateDTO);
	    admin.setAdminId(newAdminId); // 設定 ID
	    admin.setSalt(salt);
	    admin.setPasswordHash(passwordHash);

	    adminRepository.saveAndFlush(admin);
	}

	
	// 更新管理員
	@Override
	public void updateAdmin(AdminDTO adminDTO) {
	    Admin admin = adminRepository.findById(adminDTO.getAdminId())
	        .orElseThrow(() -> new NotFoundException("ADMIN_NOT_FOUND","查無此管理員"));

	    // 更新可編輯欄位
	    admin.setUsername(adminDTO.getUsername());
	    admin.setAdminName(adminDTO.getAdminName());
	    admin.setUnit(adminDTO.getUnit());
	    admin.setActive(adminDTO.getActive());

	    // 不改密碼與鹽
	    adminRepository.save(admin);
	}

	
	// 取得所有管理員列表
	@Override
	public List<AdminDTO> getAllAdmins() {
	    List<Admin> admins = adminRepository.findAll(); // 或可加排序如 findAll(Sort.by("createdAt").descending())
	    return admins.stream()
	                 .map(adminMapper::toDto)
	                 .collect(Collectors.toList());
	}
	
	// 管理者修改密碼
	@Override
	public void changePassword(String adminId, ChangePasswordDTO dto) {
	    Admin admin = adminRepository.findById(adminId)
	        .orElseThrow(() -> new NotFoundException("ADMIN_NOT_FOUND","查無此管理員"));

	    // 檢查舊密碼是否正確
	    String hashOld = Hash.getHash(dto.getOldPassword(), admin.getSalt());
	    if (!hashOld.equals(admin.getPasswordHash())) {
	        throw new RuntimeException("舊密碼錯誤，無法修改");
	    }

	    // 產生新鹽與新密碼
	    String newSalt = Hash.getSalt();
	    String newHash = Hash.getHash(dto.getNewPassword(), newSalt);

	    // 更新
	    admin.setSalt(newSalt);
	    admin.setPasswordHash(newHash);
	    adminRepository.save(admin);
	}

	
}
