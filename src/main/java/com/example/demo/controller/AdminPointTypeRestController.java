package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.PointTypeException;
import com.example.demo.model.dto.PointTypeDTO;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.PointTypeService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping(value = {"/admin/point-types"})
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8002"}, allowCredentials = "true")
public class AdminPointTypeRestController {
	
	@Autowired
	public PointTypeService pointTypeService;
	
	// 取得所有點數類型列表
	@GetMapping
	public ResponseEntity<ApiResponse<List<PointTypeDTO>>> findAllTypes(
	    @RequestParam(required = false) Boolean active,
	    @RequestParam(required = false) String category) {

	    List<PointTypeDTO> dtos = pointTypeService.getFilteredTypes(active, category);
	    return ResponseEntity.ok(ApiResponse.success("查詢成功", dtos));
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
		PointTypeDTO savedDTO = pointTypeService.addType(pointTypeDTO);
		return ResponseEntity.ok(ApiResponse.success("點數類型新增成功", savedDTO));
	}
	
	// 修改點數類型
	@PutMapping("/{typeId}")
	public ResponseEntity<ApiResponse<PointTypeDTO>> updateType(@PathVariable String typeId, @Valid @RequestBody PointTypeDTO pointTypeDTO, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			throw new PointTypeException("修改失敗:" + bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		PointTypeDTO updatedDTO = pointTypeService.updateType(typeId, pointTypeDTO);
		return ResponseEntity.ok(ApiResponse.success("點數類型修改成功", updatedDTO));
	}
	
	@ExceptionHandler(PointTypeException.class)
	public ResponseEntity<ApiResponse<Void>> handlePointTypeException(PointTypeException e){
	    return ResponseEntity.badRequest().body(ApiResponse.error(400, e.getMessage()));
	}

}