package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.PointCollectionDTO;
import com.example.demo.model.entity.PointLog;

public interface PointCollectionService {
	List<PointCollectionDTO> getAllCollections(); // 查詢所有點數池
	public PointCollectionDTO getCollection(String collectionId); // 查詢單筆點數池
	public void addCollection(PointLog pointLog); // 新增單筆點數池
}
