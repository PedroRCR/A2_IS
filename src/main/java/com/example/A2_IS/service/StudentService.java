package com.example.A2_IS.service;

import com.example.A2_IS.models.Professor;
import com.example.A2_IS.models.Student;
import com.example.A2_IS.repository.ProfessorRepository;
import com.example.A2_IS.repository.RelationRepository;
import com.example.A2_IS.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

@Service
public class StudentService {

    @Autowired
    ProfessorRepository professorRepository;
    @Autowired
    RelationRepository relationRepository;
    @Autowired
    StudentRepository studentRepository;

    public Mono<Student> getStudentById(int id){
        return studentRepository.findById(id);
    }

    public Flux<Student> getStudents(){
        return studentRepository.findAll();
    }

    public Flux<Student> getAllStudentsWithProfessors() {
        return studentRepository.findAll().flatMap(this::loadRelations);
    }

    public Mono<Student> saveStudent(Student student){return studentRepository.save(student);}

    public void deleteStudent(Integer id) {
        studentRepository.deleteById(id).switchIfEmpty(Mono.error(new Exception("Student not found"))).subscribe();
    }


    public Mono<Student> loadRelations(final Student student) {
        var relations = relationRepository.findByStudentId(student.getId());

        var professors = relations.flatMap(relation ->
                professorRepository.findById(relation.getProfessorId())
        );

        // Load the students
        Mono<Student> mono = Mono.just(student)
                .zipWith(professors.collectList())
                .map(result -> {
                    result.getT1().setProfessors(result.getT2());
                    return result.getT1();
                });

        return mono;
    }

    public void updateStudent(Student student) {
        studentRepository
                .findById(student.getId())
                .map(s -> {
                    s = student;
                    return s;
                })
                .flatMap(studentRepository::save)
                .subscribe();
    }
}

