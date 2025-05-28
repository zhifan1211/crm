package com.example.demo.service;

import java.time.LocalDateTime;

import com.example.demo.model.dto.AdminDTO;
import com.example.demo.model.entity.Unit;

public interface AdminService {
	public AdminDTO getAdminById(String adminId);
	public AdminDTO getAdminByUsername(String username);
	public void addAdmin(AdminDTO adminDTO, String plainPassword);
	public void addAdmin(String adminId, String username, String name, String password, Unit unit, Boolean active, LocalDateTime createdAt);
	public void updateAdmin(String adminId, AdminDTO adminDTO);
	public void updateAdmin(String adminId, String username, String name, String password, Unit unit, Boolean active, LocalDateTime createdAt);
	public void deleteAdmin(String adminId);
}
