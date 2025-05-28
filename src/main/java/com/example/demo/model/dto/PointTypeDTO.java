package com.example.demo.model.dto;

import com.example.demo.model.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointTypeDTO {
	private String typeId;
	private String name;
	private Category category;
	private Integer defaultValue;
	private String description;
	private Boolean active;
}
