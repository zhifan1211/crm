package com.example.demo.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "id_sequence")
public class IdSequence {

    @Id
    @Column(name = "prefix", nullable = false)
    private String prefix; // 例如 member, admin 等等

    @Column(name = "current_value", nullable = false)
    private Integer currentValue;
}
