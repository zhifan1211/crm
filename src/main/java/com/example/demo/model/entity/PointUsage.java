package com.example.demo.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "point_usages")
public class PointUsage {
	
	@Id
	@Column(name = "usage_id", unique = true, nullable = false)
	private String usageId;
	
	@ManyToOne
	@JoinColumn(name = "log_id")
	private PointLog pointLog;
	
	@ManyToOne
	@JoinColumn(name = "collection_id")
	private PointCollection pointCollection;
	
	@Column(name = "consume_point")
	private Integer consumePoint;
	
	
}
