package com.example.A2_IS.repository;

import com.example.A2_IS.models.Student;
import io.r2dbc.spi.Parameter;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


public interface StudentRepository extends ReactiveCrudRepository<Student, Integer> {
}
