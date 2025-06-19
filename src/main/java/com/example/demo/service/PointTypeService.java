package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.PointTypeDTO;

public interface PointTypeService {
    List<PointTypeDTO> getFilteredTypes(Boolean active, String category); // 查全部或過濾後結果
    List<PointTypeDTO> getAllTypes(); // 查全部
    PointTypeDTO getType(String typeId); // 查單筆
    PointTypeDTO addType(PointTypeDTO pointTypeDTO); // 新增
    PointTypeDTO updateType(String typeId, PointTypeDTO pointTypeDTO); // 修改
}