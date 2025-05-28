package com.example.demo.model.dto;

import java.time.LocalDateTime;

import com.example.demo.model.entity.Unit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {
	private String adminId;
	private String username;
	private String adminName;
	private Unit unit;
	private Boolean active;
	private LocalDateTime createdAt;
}
