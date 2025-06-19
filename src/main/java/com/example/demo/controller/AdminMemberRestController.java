package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.exception.MemberException;
import com.example.demo.model.dto.AdminCert;
import com.example.demo.model.dto.MemberViewDTO;
import com.example.demo.model.dto.PointLogDTO;
import com.example.demo.model.dto.PointLogViewDTO;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.MemberService;
import com.example.demo.service.PointLogService;

// 給管理員看的會員資訊
@RestController
@RequestMapping(value = {"/admin/member"})
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8002"}, allowCredentials = "true")
public class AdminMemberRestController {
	
	@Autowired
	public MemberService memberService;
	
	@Autowired
	public PointLogService pointLogService;
	
	// 取得所有會員資訊列表
	@GetMapping
	public ResponseEntity<ApiResponse<List<MemberViewDTO>>> findAllMembers(@SessionAttribute("adminCert") AdminCert cert){
		List<MemberViewDTO> memberViewDTOs = memberService.getAllMembers();
		String message = memberViewDTOs.isEmpty() ? "查無資料" : "查詢成功";
		return ResponseEntity.ok(ApiResponse.success(message, memberViewDTOs));
	}
	
	// 取得單筆會員資訊列表
	@GetMapping("/{memberId}")
	public ResponseEntity<ApiResponse<MemberViewDTO>> getMember(@PathVariable String memberId){
		MemberViewDTO memberViewDTO = memberService.getMemberViewById(memberId);
		return ResponseEntity.ok(ApiResponse.success("會員資訊查詢成功", memberViewDTO));
	}
	
    // 新增單筆會員點數紀錄（派發或消耗）
    @PostMapping("/{memberId}/point")
    public ResponseEntity<ApiResponse<Void>> addPointLog(@SessionAttribute("adminCert") AdminCert cert, @PathVariable String memberId, @RequestBody PointLogDTO pointLogDTO) {

        // 補上 adminId 與 memberId
    	pointLogDTO.setAdminId(cert.getAdminId());
    	pointLogDTO.setMemberId(memberId);
        try {
        	pointLogService.addLog(pointLogDTO); // 交由 Service 處理新增
        } catch (RuntimeException e) {
        	 return ResponseEntity
        	            .badRequest()
        	            .body(ApiResponse.error(104,e.getMessage()));
        }
        return ResponseEntity.ok(ApiResponse.success("點數交易執行成功", null));
    }

    // 查詢單筆會員的點數紀錄
    @GetMapping("/{memberId}/point")
    public ResponseEntity<ApiResponse<List<PointLogViewDTO>>> getLogsViewByMemberId(
    	    @PathVariable String memberId
    	) {
    	    List<PointLogViewDTO> logs = pointLogService.getLogsViewByMemberIdToAdmin(memberId);
    	    return ResponseEntity.ok(ApiResponse.success("會員點數紀錄查詢成功", logs));
    }
	
    // 修改單筆會員的啟用狀態
    @PatchMapping("/{memberId}/toggle-active")
    public ResponseEntity<ApiResponse<Void>> toggleActiveStatus(@PathVariable String memberId) {
        memberService.toggleActive(memberId);
        return ResponseEntity.ok(ApiResponse.success("會員啟用狀態已切換", null));
    }
    
	// 錯誤處理
	@ExceptionHandler({MemberException.class})
	public ResponseEntity<ApiResponse<Void>> handleMemberException(MemberException e){
		return ResponseEntity.ok(ApiResponse.error(500, e.getMessage()));
	}
}