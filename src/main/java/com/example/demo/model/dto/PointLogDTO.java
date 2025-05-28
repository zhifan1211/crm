package com.example.demo.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointLogDTO {
	private String logId;
    private String memberId;
    private String adminId;
    private String typeId;
    private Integer points;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private String orderId;
    private String note; 
}
