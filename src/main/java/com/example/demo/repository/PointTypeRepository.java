package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.PointType;
import com.example.demo.model.entity.Category;

@Repository
public interface PointTypeRepository extends JpaRepository<PointType, String> {

    // 查所有啟用中或未啟用的
    List<PointType> findByActive(boolean active);

    // 查啟用中 + 指定類別（ADD 或 CONSUME）
    List<PointType> findByActiveAndCategory(boolean active, Category category);
}