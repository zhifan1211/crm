package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.PointCollection;

@Repository
public interface PointCollectionRepository extends JpaRepository<PointCollection, String> {
	
	// 根據會員計算剩餘點數總和
	@Query(value = """
		    SELECT pc.* 
		    FROM point_collections pc
		    JOIN point_logs pl ON pc.log_id = pl.log_id
		    WHERE pl.member_id = :memberId
		      AND pc.remain_point > 0
		""", nativeQuery = true)
	List<PointCollection> findValidCollectionsByMemberId(@Param("memberId") String memberId);
	
	
	// 抓取剩餘值大於0，且已至到期日的紀錄
	@Query("SELECT c FROM PointCollection c " +
		       "WHERE c.remainPoint > 0 AND c.pointLog.expiredAt IS NOT NULL AND c.pointLog.expiredAt < :now")
	List<PointCollection> findExpiredCollections(@Param("now") LocalDateTime now);
	
	// 剩餘未核銷點數（從 PointCollection 表直接 sum）
	@Query("""
	    SELECT COALESCE(SUM(pc.remainPoint), 0)
	    FROM PointCollection pc
	    WHERE (:start IS NULL OR pc.pointLog.createdAt >= :start)
	      AND (:end IS NULL OR pc.pointLog.createdAt <= :end)
	""")
	Integer sumUnredeemedPoints(
	    @org.springframework.lang.Nullable java.time.LocalDateTime start,
	    @org.springframework.lang.Nullable java.time.LocalDateTime end
	);
	
}