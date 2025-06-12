package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.PointLog;

@Repository
public interface PointLogRepository extends JpaRepository<PointLog, String> {
	
	// 根據 memberId，查出該會員的所有 PointLog 紀錄，並依據 createdAt 欄位做「由新到舊」的排序
	@Query("""
		    SELECT pl FROM PointLog pl
		    LEFT JOIN FETCH pl.pointUsages pu
		    LEFT JOIN FETCH pu.pointCollection pc
		    LEFT JOIN FETCH pc.pointLog pl2
		    WHERE pl.member.memberId = :memberId
		    ORDER BY pl.createdAt Desc
		""")
	List<PointLog> findByMember_MemberIdOrderByCreatedAtDesc(String memberId);
	
	// 找全部而且「由新到舊」排序
	@Query("""
		    SELECT pl FROM PointLog pl
		    LEFT JOIN FETCH pl.pointUsages pu
		    LEFT JOIN FETCH pu.pointCollection pc
		    LEFT JOIN FETCH pc.pointLog pl2
		    LEFT JOIN FETCH pl.member m
		    LEFT JOIN FETCH pl.admin a
		    LEFT JOIN FETCH pl.pointType pt
		    ORDER BY pl.createdAt DESC
		""")
	List<PointLog> findAllByOrderByCreatedAtDesc();
}
