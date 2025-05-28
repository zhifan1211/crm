package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointUsageDTO {
	private String usageId;
	private String logId;
	private String collectionId;
	private Integer consumePoint;
}
