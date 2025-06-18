package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
	private String itemId;
	private String itemName;
	private String imageURL;
	private Integer points;
	private String description;
	private Boolean active;
}
