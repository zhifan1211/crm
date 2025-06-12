package com.example.demo.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointLogHistoryDTO {
	private String logId;
	private String typeName;
	private String category; // ADD or CONSUME
    private Integer originalPoints;
    private Integer remainPoints; // 只在 ADD 顯示
	private LocalDateTime createdAt;
	private LocalDateTime expiredAt;   // 只在 ADD 顯示
}
