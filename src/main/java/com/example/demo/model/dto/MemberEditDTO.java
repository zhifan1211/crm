package com.example.demo.model.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberEditDTO {
	private String memberId;
	private String lastName;
	private String firstName;
	private String gender;
	private String phoneNumber;
	private String level;
	private String email;
	private String region;
	private LocalDate birthDate;
	private Boolean confirmEmail;
}
