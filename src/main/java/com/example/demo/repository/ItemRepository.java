package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
	
    // 查所有啟用中或未啟用的
    List<Item> findByActive(boolean active);
}
