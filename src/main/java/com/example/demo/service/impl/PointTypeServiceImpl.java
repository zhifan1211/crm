package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.PointTypeAlreadyExistException;
import com.example.demo.exception.PointTypeNotFoundException;
import com.example.demo.mapper.PointTypeMapper;
import com.example.demo.model.dto.PointTypeDTO;
import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.PointType;
import com.example.demo.repository.PointTypeRepository;
import com.example.demo.service.IdGeneratorService;
import com.example.demo.service.PointTypeService;

@Service
public class PointTypeServiceImpl implements PointTypeService {
	
	@Autowired
	private PointTypeRepository pointTypeRepository;
	
	@Autowired
	private PointTypeMapper pointTypeMapper;
	
	@Autowired
	private IdGeneratorService idGeneratorService;
	
	// 查詢所有點數類型
	@Override
	public List<PointTypeDTO> getAllTypes() {
		return pointTypeRepository.findAll()
								  .stream()
								  .map(pointTypeMapper::toDto)
								  .toList();
	}
	
	// 查詢單筆點數類型
	@Override
	public PointTypeDTO getType(String typeId) {
		PointType pointType = pointTypeRepository.findById(typeId).orElseThrow(() -> new PointTypeNotFoundException("查無點數類型：typeId" + typeId));
		PointTypeDTO dto = pointTypeMapper.toDto(pointType);
		return dto;
	}
	
	// 新增單筆點數類型
	@Override
	public void addType(PointTypeDTO pointTypeDTO) {
		// 自動產生 id
		String newTypeId = idGeneratorService.generateId("TP");
		// 判斷 id 是否存在
		Optional<PointType> optType = pointTypeRepository.findById(pointTypeDTO.getTypeId());
		if(optType.isPresent()) { // 如果 id 已存在
			throw new PointTypeAlreadyExistException("新增失敗：點數ID:" + pointTypeDTO.getTypeId() + "已存在");
		}
		// 進入新增程序
		// DTO 轉 Entity
		pointTypeDTO.setTypeId(newTypeId); // 把生成的 ID 放進 DTO
		PointType pointType = pointTypeMapper.toEntity(pointTypeDTO);
		// 將 Entity pointType 存入
		pointTypeRepository.saveAndFlush(pointType);
	}
	
	@Override
	public void addType(String typeId, String name, Category category, Integer defaultValue, String description,
						Boolean active) {
		PointTypeDTO pointTypeDTO = new PointTypeDTO(typeId, name, category, defaultValue, description, active);
		addType(pointTypeDTO);
	}

	// 更新單筆點數類型
	@Override
	public void updateType(String typeId, PointTypeDTO pointTypeDTO) {
		// 判斷 id 是否存在
		Optional<PointType> optType = pointTypeRepository.findById(typeId); // 用 id 找
		if(optType.isEmpty()) { // 如果 id 不存在
			throw new PointTypeNotFoundException("修改失敗：點數類型名稱:" + pointTypeDTO.getName() + "不存在"); //顯示結果以名稱（通常 id 看不懂）
		}
		pointTypeDTO.setTypeId(typeId);
		PointType pointType = pointTypeMapper.toEntity(pointTypeDTO);
		pointTypeRepository.saveAndFlush(pointType); // 更新並立即強制寫入
	}
	
	@Override
	public void updateType(String typeId, String name, Category category, Integer defaultValue, String description,
						   Boolean active) {
		PointTypeDTO pointTypeDTO = new PointTypeDTO(typeId, name, category, defaultValue, description, active);
		updateType(typeId, pointTypeDTO);
	}

	// 刪除單筆點數類型
	@Override
	public void deleteType(String typeId) {
		// 判斷 id 是否存在
		Optional<PointType> optType = pointTypeRepository.findById(typeId); // 用 id 找
		if(optType.isEmpty()) { // 如果 id 不存在
			throw new PointTypeNotFoundException("刪除失敗：點數類型ID:" + typeId + "不存在");
		}
		pointTypeRepository.deleteById(typeId);
	}
}
