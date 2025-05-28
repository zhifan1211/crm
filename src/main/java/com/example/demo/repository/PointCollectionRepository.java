package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.entity.PointCollection;

public interface PointCollectionRepository extends JpaRepository<PointCollection, String> {

}
