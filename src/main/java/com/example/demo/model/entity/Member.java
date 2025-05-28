package com.example.demo.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Entity //實體類與資料表對應(會自動建立資料表)
@Table(name="members") // 資料庫名稱
public class Member {
	
	@Id
	@Column(name = "member_id", nullable = false) // 資料表中預設的欄位名稱
	private String memberId;
	
	@Column(name = "last_name", nullable = false, length = 100)
	private String lastName;
	
	@Column(name = "first_name", nullable = false, length = 100)
	private String firstName;
	
	@Enumerated(EnumType.STRING) // 將 gender 儲存為字串 "male" / "female" 
	@Column(name = "gender", nullable = false)
	private Gender gender;
	
	@Column(name = "phone_number", unique = true, nullable = false, length = 20)
	private String phoneNumber;
	
	@Column(name = "password_hash", nullable = false)
	private String passwordHash;
	
	@Column(name = "salt", nullable = false)
	private String salt;
	
	@Enumerated(EnumType.STRING) // 將 level 儲存為字串 "passer" / "formal" 
	@Column(name = "level", nullable = false)
	private Level level = Level.passer;
	
	@Column(name = "email", length = 255)
	private String email;
	
	@Column(name = "region", length = 255)
	private String region;
	
    @Column(name = "birth_date")
	private LocalDate birthDate; // LocalDate 只儲存日月年，沒有時間，適合存生日用
    
    @Column(name = "confirm_email", nullable = false)
	private Boolean confirmEmail = false;
    
    @CreationTimestamp // 自動記錄建立時間
    @Column(name = "created_at", updatable = false)  // 不可更新
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "member")
    private List<PointLog> pointLogs = new ArrayList<>();
}
