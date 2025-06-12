package com.example.demo.model.dto;

import com.example.demo.model.entity.Unit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class AdminCert {
	private String adminId;
	private String username;
	private Unit unit;
}
