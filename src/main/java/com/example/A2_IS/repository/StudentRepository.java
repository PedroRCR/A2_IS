package com.example.A2_IS.repository;

import com.example.A2_IS.models.Student;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;


public interface StudentRepository extends ReactiveCrudRepository<Student, Integer> {
    Flux<Student> findAllByOrderByCreditsAsc();
    Flux<Student> findByCreditsGreaterThanEqual(int credits);
}
