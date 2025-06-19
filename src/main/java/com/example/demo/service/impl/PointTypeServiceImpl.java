package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // 統一查詢：依條件過濾（Controller 只呼叫這支）
    @Override
    public List<PointTypeDTO> getFilteredTypes(Boolean active, String category) {
        if (active != null && category != null) {
            return findByActiveAndCategory(active, category);
        } else if (active != null) {
            return findByActive(active);
        } else {
            return getAllTypes();
        }
    }

    // 查全部
    @Override
    public List<PointTypeDTO> getAllTypes() {
        return pointTypeRepository.findAll()
                                  .stream()
                                  .map(pointTypeMapper::toDto)
                                  .toList();
    }

    // 查單筆
    @Override
    public PointTypeDTO getType(String typeId) {
        PointType pointType = pointTypeRepository.findById(typeId)
                .orElseThrow(() -> new PointTypeNotFoundException("查無點數類型：typeId " + typeId));
        return pointTypeMapper.toDto(pointType);
    }

    // 新增
    @Override
    public PointTypeDTO addType(PointTypeDTO pointTypeDTO) {
        pointTypeDTO.setTypeId(idGeneratorService.generateId("TP"));
        PointType entity = pointTypeMapper.toEntity(pointTypeDTO);
        PointType saved = pointTypeRepository.saveAndFlush(entity);
        return pointTypeMapper.toDto(saved);
    }

    // 修改
    @Override
    public PointTypeDTO updateType(String typeId, PointTypeDTO pointTypeDTO) {
        if (!pointTypeRepository.existsById(typeId)) {
            throw new PointTypeNotFoundException("修改失敗：點數類型名稱 " + pointTypeDTO.getName() + " 不存在");
        }
        pointTypeDTO.setTypeId(typeId);
        PointType entity = pointTypeMapper.toEntity(pointTypeDTO);
        PointType saved = pointTypeRepository.saveAndFlush(entity);
        return pointTypeMapper.toDto(saved);
    }

    // 私有方法：給 getFilteredTypes 用
    private List<PointTypeDTO> findByActiveAndCategory(boolean active, String category) {
        Category catEnum = Category.valueOf(category.toUpperCase());
        return pointTypeRepository.findByActiveAndCategory(active, catEnum)
                                  .stream()
                                  .map(pointTypeMapper::toDto)
                                  .toList();
    }

    private List<PointTypeDTO> findByActive(boolean active) {
        return pointTypeRepository.findByActive(active)
                                  .stream()
                                  .map(pointTypeMapper::toDto)
                                  .toList();
    }
}