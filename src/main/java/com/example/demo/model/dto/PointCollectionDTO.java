package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointCollectionDTO {
	private String collectionId;
	private String logId;
	private Integer remainPoint;
}
