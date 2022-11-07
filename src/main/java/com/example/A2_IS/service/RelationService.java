package com.example.A2_IS.service;

import com.example.A2_IS.models.Relation;
import com.example.A2_IS.repository.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RelationService {

    @Autowired
    RelationRepository relationRepository;

    public Flux<Relation> getAllRelation(){return relationRepository.findAll();}

    public Flux<Relation> getAllRelationForProfessor(int professorId){return relationRepository.findByProfessorId(professorId);}

    public Mono<Relation> createRelation(Relation relation){return relationRepository.save(relation);}
}
