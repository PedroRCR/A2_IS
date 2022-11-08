package com.example.A2_IS.repository;

import com.example.A2_IS.models.Relation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface RelationRepository extends ReactiveCrudRepository<Relation, Integer> {
    Flux<Relation> findByProfessorId(Integer professorId);
    Flux<Relation> findByStudentId(Integer studentId);
}
