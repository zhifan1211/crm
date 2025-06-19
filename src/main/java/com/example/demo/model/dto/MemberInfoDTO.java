package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoDTO {
    private String memberId;
    private String lastName;
    private String firstName;
    private String gender;
    private Integer totalPoints;       // 剩餘有效點數
    private String nearestExpiryDate;  // 最近即將過期日 yyyy-MM-dd
    private String level;
}
