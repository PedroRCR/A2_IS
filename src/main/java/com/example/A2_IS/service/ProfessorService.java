package com.example.A2_IS.service;

import com.example.A2_IS.controllers.ProfessorController;
import com.example.A2_IS.models.Professor;
import com.example.A2_IS.models.Student;
import com.example.A2_IS.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProfessorService {

    @Autowired
    ProfessorRepository professorRepository;

    public Flux<Professor> getAllProfessors() {
        return professorRepository.findAll();
    }

    public Mono<Professor> saveProfessor(Professor professor) {
       return professorRepository.save(professor);
    }

    public void deleteProfessor(Integer id) {
        professorRepository.deleteById(id).switchIfEmpty(Mono.error(new Exception("Professor not found"))).subscribe();
    }

    public void updateProfessor(Professor professor) {
        professorRepository
                .findById(professor.getId())
                .map(p -> {
                    p = professor;
                    return p;
                })
                .flatMap(professorRepository::save)
                .subscribe();
    }
}
