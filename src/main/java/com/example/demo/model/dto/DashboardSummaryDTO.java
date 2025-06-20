package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardSummaryDTO {
	private Integer memberCount;
	private Integer formalMemberCount;
    private Integer addedPoints;
    private Integer consumedPoints;      // 排除過期
    private Integer unredeemedPoints;    // 剩餘未核銷
    private Integer expiredPoints;       // 只算過期
}	
