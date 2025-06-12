package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.ResetToken;

@Repository
public interface ResetTokenRepository extends JpaRepository<ResetToken, String>{

	// 用 hash 查找 token（安全查法）
    Optional<ResetToken> findByTokenHash(String tokenHash);
}
