package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.AdminCreateDTO;
import com.example.demo.model.dto.AdminDTO;
import com.example.demo.model.dto.ChangePasswordDTO;

public interface AdminService {
	AdminDTO getAdminById(String adminId);
	void addAdmin(AdminCreateDTO adminCreateDTO);
	void updateAdmin(AdminDTO adminDTO);
	List<AdminDTO> getAllAdmins();
	void changePassword(String adminId, ChangePasswordDTO dto);
}
