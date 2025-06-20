package com.example.demo.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.DashboardSummaryDTO;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.PointCollectionRepository;
import com.example.demo.repository.PointLogRepository;
import com.example.demo.service.AdminDashboardService;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private PointLogRepository pointLogRepository;
	
	@Autowired
	private PointCollectionRepository pointCollectionRepository;
	
	@Override
    public DashboardSummaryDTO getDashboardSummary(LocalDateTime start, LocalDateTime end) {
        DashboardSummaryDTO dto = new DashboardSummaryDTO();
        // 1. 會員總數
        dto.setMemberCount(memberRepository.countByCreatedAtBetween(start, end));
        // 2. 正式會員總數
        dto.setFormalMemberCount(memberRepository.countFormalMembersByCreatedAtBetween(start, end));
		// 3. 派發總點數
		dto.setAddedPoints(pointLogRepository.sumAddedPoints(start, end));
		// 4. 會員消耗總點數（排除TP00001）
		dto.setConsumedPoints(pointLogRepository.sumConsumedPointsExcludeExpire(start, end));
		// 5. 剩餘未核銷點數
		dto.setUnredeemedPoints(pointCollectionRepository.sumUnredeemedPoints(start, end));
		// 6. 過期點數
		dto.setExpiredPoints(pointLogRepository.sumExpiredPoints(start, end));
		return dto;
    }
}
