package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.model.dto.PointLogDTO;
import com.example.demo.model.dto.PointLogHistoryDTO;
import com.example.demo.model.dto.PointLogViewDTO;
import com.example.demo.model.entity.Admin;
import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Member;
import com.example.demo.model.entity.PointLog;
import com.example.demo.model.entity.PointType;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.PointLogRepository;
import com.example.demo.repository.PointTypeRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.IdGeneratorService;
import com.example.demo.service.PointCollectionService;
import com.example.demo.service.PointLogService;
import com.example.demo.service.PointUsageService;

@Service
public class PointLogServiceImpl implements PointLogService{
	
	@Autowired
	private PointLogRepository pointLogRepository;
	
	@Autowired
	private IdGeneratorService idGeneratorService;
	
	@Autowired
	private PointCollectionService pointCollectionService;
	
	@Autowired
	private PointUsageService pointUsageService;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private PointTypeRepository pointTypeRepository;
	
	// 新增單筆點數紀錄
	@Override
	@Transactional
	public void addLog(PointLogDTO dto) throws RuntimeException {
	    // 1. 產生 ID 並檢查是否存在
	    String newLogId = idGeneratorService.generateId("LG");
	    if(pointLogRepository.findById(newLogId).isPresent()) throw new RuntimeException("ID已存在");

	    // 2. 轉成 Entity 並補上 admin、member、pointType（這三個要從 DB 抓）
	    PointLog log = new PointLog();
	    
	    Member member = memberRepository.findById(dto.getMemberId())
		        .orElseThrow(() -> new RuntimeException("找不到會員"));
		    log.setMember(member);

	    Admin admin = adminRepository.findById(dto.getAdminId())
	        .orElseThrow(() -> new RuntimeException("找不到管理員"));
	    log.setAdmin(admin);

	    PointType pointType = pointTypeRepository.findById(dto.getTypeId())
	        .orElseThrow(() -> new RuntimeException("找不到點數類型"));
	    log.setPointType(pointType);
	    
	    if (dto.getPoints().intValue() <= 0) {
	    	throw new RuntimeException("點數需為正整數");
	    }
	    
	    log.setLogId(newLogId);
	    log.setNote(dto.getNote());
	    log.setPoints(dto.getPoints());
	    log.setCreatedAt(LocalDateTime.now());
	    if (pointType.getCategory() == Category.ADD) {
	        log.setExpiredAt(log.getCreatedAt().plusYears(1));
	    }
	    log.setExpiredAt(log.getCreatedAt().plusYears(1)); // 只有 ADD 會用到

	    // 3. 存進資料庫
	    pointLogRepository.save(log);

	    // 4. 根據類別進行派發或消耗
	    switch (pointType.getCategory()) {
	        case ADD -> pointCollectionService.addCollection(log); // 建立一筆 PointCollection
	        case CONSUME -> pointUsageService.addUsage(log); // 根據 FIFO 扣點，建立多筆 PointUsage
	        default -> throw new IllegalArgumentException("未支援的類別");
	    }
	}
	
	// 管理者查詢單一會員的點數紀錄
	@Override
	public List<PointLogViewDTO> getLogsViewByMemberIdToAdmin(String memberId) {
	    List<PointLog> logs = pointLogRepository.findByMember_MemberIdOrderByCreatedAtDesc(memberId);

	    return logs.stream().map(log -> {
	        PointLogViewDTO view = new PointLogViewDTO();
	        view.setMemberId(log.getMember().getMemberId());
	        view.setMemberName(log.getMember().getLastName() + log.getMember().getFirstName());
	        view.setLogId(log.getLogId());
	        view.setTypeName(log.getPointType().getName());
	        view.setCategory(log.getPointType().getCategory().name());
	        view.setOriginalPoints(log.getPoints());
	        view.setNote(log.getNote());
	        view.setAdminName(log.getAdmin().getAdminName());
	        view.setCreatedAt(log.getCreatedAt());
	        view.setUnit(log.getAdmin().getUnit().name());
	    try {
	        if (log.getPointType().getCategory() == Category.ADD) {
	            view.setRemainPoints(log.getPointCollection() != null ? log.getPointCollection().getRemainPoint() : 0);
	            view.setExpiredAt(log.getExpiredAt());
	            view.setConsumeFromLogIds(null);
	        } else if (log.getPointType().getCategory() == Category.CONSUME) {
	            List<String> fromIds = log.getPointUsages().stream()
	                    .map(u -> u.getPointCollection().getPointLog().getLogId())
	                    .collect(Collectors.toList());
	            view.setConsumeFromLogIds(fromIds);
	            view.setRemainPoints(null);
	            view.setExpiredAt(null);
	        }
	    } catch (Exception e) {
	    	System.err.println(e);
		}

	        return view;
	    }).collect(Collectors.toList());
	}
	
	// 管理者查詢所有會員的點數紀錄
	public List<PointLogViewDTO> getLogByAllMemberToAdmin(){
		List<PointLog> logs = pointLogRepository.findAllByOrderByCreatedAtDesc();
	    return logs.stream().map(log -> {
	        PointLogViewDTO view = new PointLogViewDTO();
	        view.setMemberId(log.getMember().getMemberId());
	        view.setMemberName(log.getMember().getLastName() + log.getMember().getFirstName());
	        view.setLogId(log.getLogId());
	        view.setTypeName(log.getPointType().getName());
	        view.setCategory(log.getPointType().getCategory().name());
	        view.setOriginalPoints(log.getPoints());
	        view.setNote(log.getNote());
	        view.setAdminName(log.getAdmin().getAdminName());
	        view.setCreatedAt(log.getCreatedAt());
	        view.setUnit(log.getAdmin().getUnit().name());
	    try {
	        if (log.getPointType().getCategory() == Category.ADD) {
	            view.setRemainPoints(log.getPointCollection() != null ? log.getPointCollection().getRemainPoint() : 0);
	            view.setExpiredAt(log.getExpiredAt());
	            view.setConsumeFromLogIds(null);
	        } else if (log.getPointType().getCategory() == Category.CONSUME) {
	            List<String> fromIds = log.getPointUsages().stream()
	                    .map(u -> u.getPointCollection().getPointLog().getLogId())
	                    .collect(Collectors.toList());
	            view.setConsumeFromLogIds(fromIds);
	            view.setRemainPoints(null);
	            view.setExpiredAt(null);
	        }
	    } catch (Exception e) {
	    	System.err.println(e);
		}

	        return view;
	    }).collect(Collectors.toList());
	}
	
	// 會員查詢自己的點數紀錄
	@Override
	public List<PointLogHistoryDTO> getLogsByMemberIdToMember(String memberId) {
	    List<PointLog> logs = pointLogRepository.findByMember_MemberIdOrderByCreatedAtDesc(memberId);

	    return logs.stream().map(log -> {
	        PointLogHistoryDTO history = new PointLogHistoryDTO();
	        history.setLogId(log.getLogId());
	        history.setTypeName(log.getPointType().getName());
	        history.setCategory(log.getPointType().getCategory().name());
	        history.setOriginalPoints(log.getPoints());
	        history.setCreatedAt(log.getCreatedAt());
	        history.setUnit(log.getAdmin().getUnit().name());
	    try {
	        if (log.getPointType().getCategory() == Category.ADD) {
	        	history.setRemainPoints(log.getPointCollection() != null ? log.getPointCollection().getRemainPoint() : 0);
	        	history.setExpiredAt(log.getExpiredAt());
	        } else if (log.getPointType().getCategory() == Category.CONSUME) {
	            history.setRemainPoints(null);
	            history.setExpiredAt(null);
	        }
	    } catch (Exception e) {
	    	System.err.println(e);
		}

	        return history;
	    }).collect(Collectors.toList());
	}
	
	// 捕捉錯誤
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
	    e.printStackTrace(); // 可以留下 log
	    return ResponseEntity.ok(ApiResponse.error(500, "系統錯誤：" + e.getMessage()));
	}
}
