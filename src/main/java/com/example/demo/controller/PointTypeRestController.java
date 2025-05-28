package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.PointTypeException;
import com.example.demo.model.dto.PointTypeDTO;
import com.example.demo.repository.MemberRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.PointTypeService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping(value = {"/admin/point_type"})
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8002"}, allowCredentials = "true")
public class PointTypeRestController {

    private final MemberRepository memberRepository;
	
	@Autowired
	PointTypeService pointTypeService;

    PointTypeRestController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
	
	// 取得所有點數類型列表
	@GetMapping
	public ResponseEntity<ApiResponse<List<PointTypeDTO>>> findAllTypes(){
		List<PointTypeDTO> pointTypeDTOs = pointTypeService.getAllTypes();
		String message = pointTypeDTOs.isEmpty() ? "查無資料" : "查詢成功";
		return ResponseEntity.ok(ApiResponse.success(message, pointTypeDTOs));
	} 
	
	// 取得單筆點數類型
	@GetMapping("/{typeId}")
	public ResponseEntity<ApiResponse<PointTypeDTO>> getType(@PathVariable String typeId){
		PointTypeDTO pointTypeDTO = pointTypeService.getType(typeId);
		return ResponseEntity.ok(ApiResponse.success("點數類型查詢單筆成功", pointTypeDTO));
	}
	
	// 新增點數類型
	@PostMapping
	public ResponseEntity<ApiResponse<PointTypeDTO>> addType(@Valid @RequestBody PointTypeDTO pointTypeDTO, BindingResult bindingResult){
		if(bindingResult.hasErrors()) {
			throw new PointTypeException("新增失敗:" + bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		pointTypeService.addType(pointTypeDTO);
		return ResponseEntity.ok(ApiResponse.success("點數類型新增成功", pointTypeDTO));
	}
	
	// 修改點數類型
	@PutMapping("/{typeId}")
	public ResponseEntity<ApiResponse<PointTypeDTO>> updateType(@PathVariable String typeId, @Valid @RequestBody PointTypeDTO pointTypeDTO, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			throw new PointTypeException("修改失敗:" + bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		pointTypeService.updateType(typeId, pointTypeDTO);
		return ResponseEntity.ok(ApiResponse.success("點數類型修改成功", pointTypeDTO));
	}
	
	// 刪除點數類型
	@DeleteMapping("/{typeId}")
	public ResponseEntity<ApiResponse<String>> deleteType(@PathVariable String typeId){
		pointTypeService.deleteType(typeId);
		return ResponseEntity.ok(ApiResponse.success("點數類型刪除成功", typeId));
	}
	
	// 錯誤處理
	@ExceptionHandler({PointTypeException.class})
	public ResponseEntity<ApiResponse<Void>> handlePointTypeException(PointTypeException e){
		return ResponseEntity.ok(ApiResponse.error(500, e.getMessage()));
	}
}
