package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.PointTypeDTO;
import com.example.demo.model.entity.Category;

public interface PointTypeService {
	List<PointTypeDTO> getAllTypes(); // 查詢所有點數類型
	public PointTypeDTO getType(String typeId); // 查詢單筆點數類型
	public void addType(PointTypeDTO pointTypeDTO); // 新增單筆點數類型
	public void addType(String typeId, String name, Category category, Integer defaultValue, String description, Boolean active); // 測試用-新增單筆點數類型
	public void updateType(String typeId, PointTypeDTO pointTypeDTO); // 修改單筆點數類型
	public void updateType(String typeId, String name, Category category, Integer defaultValue, String description, Boolean active); // 測試用-修改單筆點數類型
	public void deleteType(String typeId); // 刪除單筆點數類型
}
