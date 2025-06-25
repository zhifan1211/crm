package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.PointUsageMapper;
import com.example.demo.model.dto.PointUsageDTO;
import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.PointCollection;
import com.example.demo.model.entity.PointLog;
import com.example.demo.model.entity.PointUsage;
import com.example.demo.repository.PointCollectionRepository;
import com.example.demo.repository.PointUsageRepository;
import com.example.demo.service.IdGeneratorService;
import com.example.demo.service.PointUsageService;

@Service
public class PointUsageServiceImpl implements PointUsageService {
	
	@Autowired
	private PointUsageRepository pointUsageRepository;
	
	@Autowired
	private PointUsageMapper pointUsageMapper;
	
	@Autowired
	private PointCollectionRepository pointCollectionRepository;
	
	@Autowired
	private IdGeneratorService idGeneratorService;

	// 查詢所有點數追蹤
	@Override
	public List<PointUsageDTO> getAllUsages() {
        return pointUsageRepository.findAll()
                				   .stream()
                				   .map(pointUsageMapper::toDto)
                				   .toList();
	}
	
	// 查詢單筆點數追蹤
	@Override
	public PointUsageDTO getUsage(String usageId) {
        PointUsage usage = pointUsageRepository.findById(usageId).orElseThrow(() -> new NotFoundException("USAGE_NOT_FOUND","查無點數消耗追蹤"));
        PointUsageDTO dto = pointUsageMapper.toDto(usage);
        return dto;
	}
	
	// 新增單筆點數追蹤(僅限 consume 類型)
	@Override
	@Transactional
	public void addUsage(PointLog pointLog) {
	    if (pointLog.getPointType().getCategory() != Category.CONSUME) {
	        throw new IllegalArgumentException("新增失敗: 點數類型非「消耗」類型");
	    }
	    int toConsume = pointLog.getPoints(); // 本次要扣的總點數
	    // 建立一個 List, 取出所有還有剩餘點數的點數池, 並依照建立時間由舊到新排序
	    List<PointCollection> collections = pointCollectionRepository.findValidCollectionsByMemberId(pointLog.getMember().getMemberId());
	    // 建立一個 List, 來收集本次扣點要儲存的 usage 紀錄
	    List<PointUsage> usagesToSave = new ArrayList<>();
	    // 開始從最舊、有剩餘點數的點數池依序扣除
	    for (PointCollection pointCollection : collections) {
	        if (toConsume == 0) break; // 已經扣完，跳出

	        int available = pointCollection.getRemainPoint();				// 該池剩餘點數
	        int consumeFromThis = Math.min(toConsume, available);			// 本次要從這筆扣多少（要扣的點數, 剩餘的點數）
	        pointCollection.setRemainPoint(available - consumeFromThis);	// 更新該池剩餘值

	        // 建立一筆 usage（紀錄這筆扣點的來源池與數量）
	        PointUsage usage = new PointUsage();
			String newUsageId = idGeneratorService.generateId("UG");
	        usage.setUsageId(newUsageId);									// 自動產生 ID
	        usage.setPointLog(pointLog);									// 指向這次的消費 log
	        usage.setPointCollection(pointCollection);						// 被扣除的池子
	        usage.setConsumePoint(consumeFromThis);							// 實際扣除的數量

	        usagesToSave.add(usage);										// 放入暫存 list
	        toConsume -= consumeFromThis;									// 更新還剩多少要扣
	    }
	    
	    // 扣不夠的情況下, 全部回滾, 不進行資料庫寫入
	    if (toConsume > 0) {
	        throw new IllegalStateException("交易失敗: 剩餘點數不足扣除");
	    }
	    
	    // 一切正常, 則批次寫入 usage 與更新後的 collections
	    pointUsageRepository.saveAll(usagesToSave);
	    pointCollectionRepository.saveAll(collections);
	}
	
	
}
