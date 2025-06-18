package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.dto.ItemDTO;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.FileService;
import com.example.demo.service.ItemService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(value = {"/admin/item-list"})
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8002"}, allowCredentials = "true")
public class AdminItemRestController {
	
	@Autowired
	public ItemService itemService;
	
	@Autowired
	private FileService fileService;
	
	// 取得所有商品列表
	@GetMapping
    public ResponseEntity<ApiResponse<List<ItemDTO>>> getAllItems(@RequestParam(required = false) Boolean active) {
        List<ItemDTO> dtos = (active == null)
                ? itemService.getAllItems()
                : itemService.getItemsByActive(active);
        return ResponseEntity.ok(ApiResponse.success("查詢成功", dtos));
    }
	
	// 取得單筆商品
	@GetMapping("/{itemId}")
	public ResponseEntity<ApiResponse<ItemDTO>> getItem(@PathVariable String itemId){
		ItemDTO itemDTO = itemService.getItemById(itemId);
		return ResponseEntity.ok(ApiResponse.success("查詢成功", itemDTO));
	}
	
	// 新增單筆商品
	@PostMapping
	public ResponseEntity<ApiResponse<ItemDTO>> addItem(@Valid @RequestBody ItemDTO itemDTO, BindingResult bindingResult) {
	    if (bindingResult.hasErrors()) {
	        throw new RuntimeException("新增失敗: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
	    }
	    ItemDTO savedDTO = itemService.addItem(itemDTO);
	    return ResponseEntity.ok(ApiResponse.success("商品新增成功", savedDTO));
	}
	
	// 修改單筆商品
	@PutMapping("/{itemId}")
	public ResponseEntity<ApiResponse<ItemDTO>> updateItem(@PathVariable String itemId, @Valid @RequestBody ItemDTO itemDTO,
	        BindingResult bindingResult) {
	    if (bindingResult.hasErrors()) {
	        throw new RuntimeException("修改失敗: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
	    }
	    ItemDTO updatedDTO = itemService.updateItem(itemId, itemDTO);
	    return ResponseEntity.ok(ApiResponse.success("商品修改成功", updatedDTO));
	}
	
	// 刪除單筆商品
	@DeleteMapping("/{itemId}")
	public ResponseEntity<ApiResponse<Void>> deleteItem(@PathVariable String itemId) {
	    itemService.deleteItem(itemId);
	    return ResponseEntity.ok(ApiResponse.success("商品刪除成功", null));
	}
	
	// 上傳圖片
	@PostMapping("/upload-image")
	public ResponseEntity<ApiResponse<String>> uploadImage(@RequestParam("file") MultipartFile file) {
	    try {
	        String imageUrl = fileService.saveImage(file);
	        return ResponseEntity.ok(ApiResponse.success("上傳成功", imageUrl));
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body(ApiResponse.error(500, "圖片上傳失敗: " + e.getMessage()));
	    }
	}

}
