package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.PointType;
import com.example.demo.model.entity.Category;

@Repository
public interface PointTypeRepository extends JpaRepository<PointType, String> {

    // 以 name 準確比對搜尋指定的 PointType
    @Query(value = "select type_id, name, category, default_value, description, active from point_types where name = :name", nativeQuery = true)
    Optional<PointType> findByName(String name);

    // 以 name 模糊比對搜尋相符的 PointType
    @Query(value = "select type_id, name, category, default_value, description, active from point_types where name like CONCAT('%', :name, '%')", nativeQuery = true)
    List<PointType> findByNameLike(@Param("name") String name);

    // 查所有啟用中或未啟用的
    List<PointType> findByActive(boolean active);

    // 查啟用中 + 指定類別（ADD 或 CONSUME）
    List<PointType> findByActiveAndCategory(boolean active, Category category);
}
