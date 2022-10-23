package com.example.A2_IS.repository;

import com.example.A2_IS.models.Professor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProfessorRepository extends ReactiveCrudRepository<Professor, Integer> {
}
