package com.example.A2_IS.controllers;

import com.example.A2_IS.models.Professor;
import com.example.A2_IS.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/a2/professor")
@RequiredArgsConstructor
@Slf4j
public class ProfessorController {

    @Autowired
    ProfessorService professorService;

    @GetMapping
    public Flux<Professor> getAllProfessors(){return professorService.getAllProfessors();}

    @GetMapping("getAllProfessorsWithStudents")
    public Flux<Professor> getAllProfessorsWithStudents(){return professorService.getAllProfessorsWithStudents();}

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Professor> saveProfessor(@RequestBody Professor professor){return professorService.saveProfessor(professor);}

    @DeleteMapping("delete/{id}")
    public void deleteStudent(@PathVariable Integer id){professorService.deleteProfessor(id);}

    @PutMapping
    public void updateStudent(@RequestBody Professor professor){professorService.updateProfessor(professor);}
}
