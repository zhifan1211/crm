package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.PointCollection;

@Repository
public interface PointCollectionRepository extends JpaRepository<PointCollection, String> {
	
	// 取出所有還有剩餘點數的點數池，並依照建立時間由舊到新排序
	@Query(value = "SELECT collection_id, remain_point, point_log_id FROM point_collections WHERE remain_point > 0 ORDER BY created_at", nativeQuery = true)
	List<PointCollection> findByRemainPointGreaterThan();
}
