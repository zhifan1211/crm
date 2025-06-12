package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Member;
import com.example.demo.model.projection.MemberViewProjection;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> { // Room: entity, String: @Id 的型別
	// 用電話查詢會員
	Optional<Member> findByPhoneNumber(String phoneNumber);
	
	// 給管理者查詢 MemberOverViewDTO的資訊，查詢所有會員資訊和剩餘點數
	@Query(value = """
		    SELECT 
		        m.member_id AS memberId,
		        m.last_name AS lastName,
		        m.first_name AS firstName,
		        m.gender AS gender,
		        m.phone_number AS phoneNumber,
		        m.level AS level,
		        m.email AS email,
		        m.region AS region,
		        m.birth_date AS birthDate,
		        m.active AS active,
		        m.created_at AS createdAt,
		        m.updated_at AS updatedAt,
		        COALESCE(SUM(
		            CASE 
		                WHEN pc.remain_point > 0 AND pl.expired_at > NOW()
		                THEN pc.remain_point
		                ELSE 0
		            END
		        ), 0) AS remainPoint
		    FROM members m
		    LEFT JOIN point_logs pl ON pl.member_id = m.member_id
		    LEFT JOIN point_collections pc ON pc.log_id = pl.log_id
		    GROUP BY 
		        m.member_id, m.last_name, m.first_name, m.gender, m.phone_number,
		        m.level, m.email, m.region, m.birth_date, m.active, m.created_at, m.updated_at
		""", nativeQuery = true)
	List<MemberViewProjection> findAllMemberOverview();
}