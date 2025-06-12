package com.example.demo.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointLogViewDTO {
	private String memberId;
	private String memberName;
	private String logId;
    private String typeName;
    private String category; // ADD or CONSUME
    private Integer originalPoints;
    private Integer remainPoints; // 只在 ADD 顯示
    private List<String> consumeFromLogIds; // 只在 CONSUME 顯示
    private String note;
    private String adminName;
    private String unit;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt; // 只在 ADD 顯示
}
