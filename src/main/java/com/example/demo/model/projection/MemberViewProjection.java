package com.example.demo.model.projection;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.demo.model.entity.Gender;
import com.example.demo.model.entity.Level;

public interface MemberViewProjection {
    String getMemberId();
    String getLastName();
    String getFirstName();
    Gender getGender();
    String getPhoneNumber();
    Level getLevel();
    String getEmail();
    String getRegion();
    LocalDate getBirthDate();
    Boolean getActive();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
    Integer getRemainPoint();
}