package com.example.A2_IS.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Relation {
    @Id
    private Integer id;
    @Column("student_id")
    private Integer studentId;
    @Column("professor_id")
    private Integer professorId;
}
