package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.PointCollectionNotFoundException;
import com.example.demo.mapper.PointCollectionMapper;
import com.example.demo.model.dto.PointCollectionDTO;
import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.PointCollection;
import com.example.demo.model.entity.PointLog;
import com.example.demo.repository.PointCollectionRepository;
import com.example.demo.service.IdGeneratorService;
import com.example.demo.service.PointCollectionService;

@Service
public class PointCollectionServiceImpl implements PointCollectionService{
	
	@Autowired
	private PointCollectionRepository pointCollectionRepository;
	
	@Autowired
	private PointCollectionMapper pointCollectionMapper;
	
	@Autowired
	private IdGeneratorService idGeneratorService;
	
	// 查詢所有點數池
	@Override
	public List<PointCollectionDTO> getAllCollections() {
		return pointCollectionRepository.findAll()
										.stream()
										.map(pointCollectionMapper::toDto)
										.toList();
	}
	
	// 查詢單筆點數池
	@Override
	public PointCollectionDTO getCollection(String collectionId) {
		PointCollection pointCollection = pointCollectionRepository.findById(collectionId).orElseThrow(() -> new PointCollectionNotFoundException("查無點數池:collectionId:" + collectionId));
		PointCollectionDTO dto = pointCollectionMapper.toDto(pointCollection);
		return dto;
	}
	
	// 新增單筆點數池(僅對點數增加有效)
	@Override
	public void addCollection(PointLog pointLog) {
		if(pointLog.getPointType().getCategory() != Category.ADD) {
			 throw new IllegalArgumentException("新增失敗: 點數類型非「派發」類型");
		}
		// 自動產生 id
		String newCollectionId = idGeneratorService.generateId("CL");
		// 建立 collection 的 entity
		PointCollection collection = new PointCollection();
	    collection.setCollectionId(newCollectionId); // 自動產生 id
	    collection.setPointLog(pointLog); // 綁定來源 log
	    collection.setRemainPoint(pointLog.getPoints()); // 初始剩餘點 = 原始點數
	    
	    pointCollectionRepository.save(collection);

	}
	
	// 查詢單一會員的有效點數（總合）
	@Override
	public int getMemberRemainingPoint(String memberId) {
		List<PointCollection> pointCollections = pointCollectionRepository.findValidCollectionsByMemberId(memberId);
		return pointCollections.stream()
                			   .mapToInt(PointCollection::getRemainPoint)
                			   .sum();
	}
}
