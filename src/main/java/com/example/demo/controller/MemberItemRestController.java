package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.ItemDTO;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.ItemService;

@RestController
@RequestMapping(value = {"/member/item-list"})
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8002"}, allowCredentials = "true")
public class MemberItemRestController {
	
	@Autowired
	private ItemService itemService;
	
	// 取得所有商品列表
	@GetMapping
    public ResponseEntity<ApiResponse<List<ItemDTO>>> getAllItems(@RequestParam(required = false) Boolean active) {
        List<ItemDTO> dtos = itemService.getItemsByActive(active);
        return ResponseEntity.ok(ApiResponse.success("查詢成功", dtos));
    }
	
}
