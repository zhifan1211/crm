package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> { // Room: entity, String: @Id 的型別
	Optional<Member> findByPhoneNumber(String phonenumber);
}
