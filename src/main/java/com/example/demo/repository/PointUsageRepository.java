package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.PointCollection;
import com.example.demo.model.entity.PointUsage;

@Repository
public interface PointUsageRepository extends JpaRepository<PointUsage, String> {

	@Query("SELECT c FROM PointCollection c WHERE c.pointLog.member.memberId = :memberId AND c.remainPoint > 0 ORDER BY c.pointLog.createdAt ASC")
	List<PointCollection> findValidCollectionsByMemberId(@Param("memberId") String memberId);
}
