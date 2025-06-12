package com.example.demo.model.entity;

import java.time.LocalDateTime;

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
@Table(name = "email_verification")
public class EmailVerification {
	 @Id
	 @Column(name = "verification_id", unique = true, nullable = false)
	 private String verificationId;

	 @Column(name = "email")
	 private String email;
	 
	 @Column(name = "code")
	 private String code;

	 @Column(name = "used")
	 private boolean used = false;
	 
	 @Column(name = "expired_at")
	 private LocalDateTime expiredAt;

	 @Column(name = "created_at")
	 private LocalDateTime createdAt = LocalDateTime.now();
}
