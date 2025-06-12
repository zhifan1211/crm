package com.example.demo.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.model.entity.Admin;
import com.example.demo.model.entity.Member;
import com.example.demo.model.entity.PointCollection;
import com.example.demo.model.entity.PointLog;
import com.example.demo.model.entity.PointType;
import com.example.demo.model.entity.PointUsage;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.PointCollectionRepository;
import com.example.demo.repository.PointLogRepository;
import com.example.demo.repository.PointTypeRepository;
import com.example.demo.repository.PointUsageRepository;
import com.example.demo.service.IdGeneratorService;

@Component
public class PointExpiryScheduler {

    @Autowired
    private PointCollectionRepository pointCollectionRepository;

    @Autowired
    private PointLogRepository pointLogRepository;

    @Autowired
    private PointUsageRepository pointUsageRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PointTypeRepository pointTypeRepository;
    
    @Autowired
    private IdGeneratorService idGeneratorService;

    // 每天凌晨 0 點執行
    @Scheduled(cron = "0 0 0 * * ?")
    public void clearExpiredPoints() {
        List<PointCollection> expiredCollections = pointCollectionRepository.findExpiredCollections(LocalDateTime.now());
        
        for (PointCollection collection : expiredCollections) {
            if (collection.getRemainPoint() <= 0) continue;

            PointLog originalLog = collection.getPointLog();
            Member member = originalLog.getMember();
            Admin systemAdmin = adminRepository.findById("admin001").orElseThrow();
            PointType expireType = pointTypeRepository.findById("TP00001")
                    .orElseThrow(() -> new RuntimeException("未找到過期清除用的 PointType (TP00001)"));

            // 建立新的 PointLog（視為自動扣點）
            PointLog expireLog = new PointLog();
            String newLogId = idGeneratorService.generateId("LG");
            expireLog.setLogId(newLogId);
            expireLog.setAdmin(systemAdmin);
            expireLog.setMember(member);
            expireLog.setPointType(expireType);
            expireLog.setPoints(collection.getRemainPoint());
            expireLog.setNote("點數過期自動清除");
            pointLogRepository.save(expireLog);

            // 建立新的 PointUsage
            PointUsage usage = new PointUsage();
            String newUsageId = idGeneratorService.generateId("UG");
            usage.setUsageId(newUsageId);
            usage.setPointLog(expireLog);
            usage.setPointCollection(collection);
            usage.setConsumePoint(collection.getRemainPoint());
            pointUsageRepository.save(usage);

            // 將原本 PointCollection 的剩餘點數歸零
            collection.setRemainPoint(0);
            pointCollectionRepository.save(collection);
        }
    }
}
