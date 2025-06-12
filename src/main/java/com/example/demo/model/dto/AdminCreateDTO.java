package com.example.demo.model.dto;

import com.example.demo.model.entity.Unit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminCreateDTO {
    private String username;
    private String adminName;
    private Unit unit;
    private Boolean active = true;
}
