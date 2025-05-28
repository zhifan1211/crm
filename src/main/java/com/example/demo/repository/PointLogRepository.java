package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.PointLog;

@Repository
public interface PointLogRepository extends JpaRepository<PointLog, String> {

}
