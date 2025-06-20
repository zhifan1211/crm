package com.example.demo.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.model.entity.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.EmailService;
import com.example.demo.service.PointCollectionService;
import com.example.demo.util.ReminderDateRange;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class PointExpiryReminderScheduler {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PointCollectionService pointCollectionService; // 提供 getMemberNearestExpiryDate
    @Autowired
    private EmailService emailService; // 寄送信件

    @Scheduled(cron = "0 0 12 5,20 * ?") // 每月5號、20號中午12:00執行
    public void sendExpiryReminder() {
        LocalDate today = LocalDate.now();
        LocalDate[] range = ReminderDateRange.getSearchRange(today);
        if (range == null) return; // 只在5號或20號執行

        LocalDate start = range[0];
        LocalDate end = range[1];

        List<Member> members = memberRepository.findAll();

        for (Member member : members) {
            String expiryDateStr = pointCollectionService.getMemberNearestExpiryDate(member.getMemberId());
            if (expiryDateStr == null) continue;

            LocalDate expiryDate = LocalDate.parse(expiryDateStr, DateTimeFormatter.ISO_LOCAL_DATE);

            if ((expiryDate.isEqual(start) || expiryDate.isAfter(start)) &&
                (expiryDate.isEqual(end)   || expiryDate.isBefore(end))) {

                String email = member.getEmail();
                if (email != null && !email.isBlank()) {
                    emailService.sendExpiryReminderEmail(email, expiryDate);
                }
            }
        }
    }
}
