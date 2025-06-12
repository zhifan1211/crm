package com.example.demo.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.demo.model.entity.Gender;
import com.example.demo.model.entity.Level;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
	private String memberId;
	private String lastName;
	private String firstName;
	private Gender gender;
    private String phoneNumber;
    private Level level;
    private String email;
    private String region;
    private LocalDate birthDate;
    private Boolean confirmEmail;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
