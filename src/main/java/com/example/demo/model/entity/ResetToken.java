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
@Table(name = "reset_tokens")
public class ResetToken {
	
	@Id
	@Column(name = "token_id")
	private String tokenId;
	
	@Column(name = "token_hash")
	private String tokenHash;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@Column(name = "expired_at")
	private LocalDateTime expiredAt;
	
	@Column(name = "used")
	private boolean used = false;
	

}
