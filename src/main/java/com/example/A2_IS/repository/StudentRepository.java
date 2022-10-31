package com.example.A2_IS.repository;

import com.example.A2_IS.models.Student;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface StudentRepository extends ReactiveCrudRepository<Student, Integer> {
}
