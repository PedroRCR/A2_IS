package com.example.A2_IS.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    private Integer id;
    private String name;
    private LocalDate dob;
    private Integer credits;
    private Float avarage;
    @Transient
    private List<Professor> professors;

    //    Completed	credits	(between	0	and	180).
}
