package com.example.demo.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="point_logs")
public class PointLog {
	
	@Id
	@Column(name = "log_id", unique = true, nullable = false)
	private String logId;
	
	@ManyToOne
	@JoinColumn(name = "admin_id", nullable = false)
	private Admin admin;
	
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;
	
	@ManyToOne
	@JoinColumn(name = "type_id", nullable = false)
	private PointType pointType;
	
	@Column(name = "points", nullable = false)
	private Integer points;
	
	@CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
	
	@Column(name = "expired_at")
	private LocalDateTime expiredAt;
	
	@Column(name = "note")
	private String note;
	
	@OneToOne(mappedBy = "pointLog")
	private PointCollection pointCollection;
	
	@OneToMany(mappedBy = "pointLog")
	private List<PointUsage> pointUsages = new ArrayList<>();
	
	@PrePersist
	public void prePersist() {
		if (pointType != null && pointType.getCategory() == Category.ADD) { // 只有新增點數 add 類型才有過期日 expiredAt
	        LocalDate expiredDate = createdAt.toLocalDate().plusYears(1);
	        this.expiredAt = expiredDate.atTime(23, 59, 59); // 只取建立時間的「日期」+1年生成過期日，並確保時間都設定到過期日的23:59:59
	    } else {
	        this.expiredAt = null; // 消耗點數 consume 不會有過期日 expiredAt
	    }
	}
	
}
