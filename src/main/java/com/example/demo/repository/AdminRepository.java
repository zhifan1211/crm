package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
	Optional<Admin> findByUsername(String username);
}
