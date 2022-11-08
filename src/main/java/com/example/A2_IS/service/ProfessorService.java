package com.example.A2_IS.service;

import com.example.A2_IS.models.Professor;
import com.example.A2_IS.repository.ProfessorRepository;
import com.example.A2_IS.repository.RelationRepository;
import com.example.A2_IS.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProfessorService {

    @Autowired
    ProfessorRepository professorRepository;
    @Autowired
    RelationRepository relationRepository;
    @Autowired
    StudentRepository studentRepository;

    public Flux<Professor> getAllProfessors() {
        return professorRepository.findAll();
    }

    public Flux<Professor> getAllProfessorsWithStudents() {
        return professorRepository.findAll().flatMap(this::loadRelations);
    }

    public Mono<Professor> saveProfessor(Professor professor) {
       return professorRepository.save(professor);
    }

    public void deleteProfessor(Integer id) {
        professorRepository.deleteById(id).switchIfEmpty(Mono.error(new Exception("Professor not found"))).subscribe();
    }

    public Mono<Professor> loadRelations(final Professor professor) {
        var relations = relationRepository.findByProfessorId(professor.getId());

        var students = relations.flatMap(relation ->
                studentRepository.findById(relation.getStudentId())
        );

        // Load the students
        Mono<Professor> mono = Mono.just(professor)
                .zipWith(students.collectList())
                .map(result -> {
                    result.getT1().setStudents(result.getT2());
                    return result.getT1();
                });

        return mono;
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
