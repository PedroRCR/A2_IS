package com.example.A2_IS.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Professor {
    @Id
    private Integer id;
    private String name;
}
