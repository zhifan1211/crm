package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.model.dto.AdminCert;
import com.example.demo.model.dto.AdminCreateDTO;
import com.example.demo.model.dto.AdminDTO;
import com.example.demo.model.dto.ChangePasswordDTO;
import com.example.demo.model.entity.Unit;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.AdminService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8002"}, allowCredentials = "true")
public class AdminRestController {
	
	@Autowired
	private AdminService adminService;
	
	// 取得自己管理者資訊
	@GetMapping("/me")
	public ResponseEntity<ApiResponse<AdminDTO>> getCurrentAdmin(@SessionAttribute("adminCert") AdminCert cert) {
	    AdminDTO admin = adminService.getAdminById(cert.getAdminId());
	    return ResponseEntity.ok(ApiResponse.success("查詢成功", admin));
	}
	
	// 取得管理者們的資訊
    @GetMapping("/manage-admins")
	public ResponseEntity<ApiResponse<List<AdminDTO>>> findAllAdmins(@SessionAttribute("adminCert") AdminCert cert){
		List<AdminDTO> adminDTOs = adminService.getAllAdmins();
		String message = adminDTOs.isEmpty() ? "查無資料" : "查詢成功";
		return ResponseEntity.ok(ApiResponse.success(message, adminDTOs));
	}
    
    // 新增單筆管理者
    @PostMapping("/manage-admins")
    public ResponseEntity<ApiResponse<Void>> createAdmin(@RequestBody @Valid AdminCreateDTO adminCreateDTO) {
        adminService.addAdmin(adminCreateDTO);
        return ResponseEntity.ok(ApiResponse.success("新增成功", null));
    }
    
    // 修改單筆管理者
    @PutMapping("/manage-admins/{adminId}")
    public ResponseEntity<ApiResponse<Void>> updateAdmin(@PathVariable String adminId,@RequestBody @Valid AdminDTO adminDTO) {
        adminDTO.setAdminId(adminId); // 確保 DTO 中也有 adminId
        adminService.updateAdmin(adminDTO);
        return ResponseEntity.ok(ApiResponse.success("修改成功", null));
    }
    
    // 取得部門
    @GetMapping("/manage-admins/units")
    public ResponseEntity<ApiResponse<List<String>>> getAllUnits() {
        List<String> units = Arrays.stream(Unit.values())
                                   .map(Enum::name)
                                   .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("查詢成功", units));
    }
    
    // 修改密碼
    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@SessionAttribute("adminCert") AdminCert cert, @RequestBody ChangePasswordDTO dto) {
        adminService.changePassword(cert.getAdminId(), dto);
        return ResponseEntity.ok(ApiResponse.success("密碼修改成功", null));
    }
	
}
