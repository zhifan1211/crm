package com.example.demo.model.dto;

import java.time.LocalDate;

import com.example.demo.model.entity.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberRegisterDTO {
	private String lastName;
	private String firstName;
	private String phoneNumber;
	private LocalDate birthDate;
	private Gender gender;
}
