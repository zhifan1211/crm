package com.example.demo.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item {
	
	@Id
	@Column(name = "item_id", nullable = false)
	private String itemId;
	
	@Column(name = "item_name")
	private String itemName;
	
	@Column(name = "image_url")
	private String imageURL;
	
	@Column(name = "points")
	private Integer points;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "active")
	private Boolean active = true;
}
