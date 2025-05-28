package com.example.demo.model.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="point_types")
public class PointType {
	
	@Id
	@Column(name = "type_id", unique = true, nullable = false)
	private String typeId;
	
	@Column(name = "name",unique = true, nullable = false)
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "category", nullable = false)
	private Category category;
	
	@Column(name = "default_value", nullable = false)
	private Integer defaultValue;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "active", nullable = false)
	private Boolean active = true;
	
	@OneToMany(mappedBy = "pointType")
	private List<PointLog> pointLogs = new ArrayList<>();
}
