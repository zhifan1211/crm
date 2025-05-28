package com.example.demo.model.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="point_collections")
public class PointCollection {
	
	@Id
	@Column(name = "collection_id", unique = true, nullable = false)
	private String collectionId;
	
	@OneToOne
	@JoinColumn(name = "log_id")
	private PointLog pointLog;
	
	@Column(name = "remain_point", nullable = false)
	private Integer remainPoint;
	
	@OneToMany(mappedBy = "pointRemain")
	private List<PointUsage> pointUsages = new ArrayList<>();
	
}
