package com.example.demo.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name="admins")
public class Admin {
	
	@Id
	@Column(name = "admin_id", unique = true, nullable = false)
	private String adminId;
	
	@Column(name = "username", unique = true, nullable = false)
	private String username;
	
	@Column(name = "admin_name", nullable = false)
	private String adminName;
	
	@Column(name = "password_hash", nullable = false)
	private String passwordHash;
	
	@Column(name = "salt", nullable = false)
	private String salt;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "unit", nullable = false)
	private Unit unit;
	
	@Column(name = "active", nullable = false)
	private Boolean active = true;
	
	@CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
	
	@OneToMany(mappedBy = "admin")
	private List<PointLog> pointLogs = new ArrayList<>();
}
