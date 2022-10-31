package com.example.A2_IS.controllers;

import com.example.A2_IS.models.Student;
import com.example.A2_IS.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@RestController
@RequestMapping("/a2/student")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping
    public Flux<Student> getAllStudents(){return studentService.getStudents();}

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveStudent(@RequestBody Student student){studentService.saveStudent(student);}

    @DeleteMapping("delete/{id}")
    public void deleteStudent(@PathVariable Integer id){studentService.deleteStudent(id);}

    @PutMapping
    public void updateStudent(@RequestBody Student student){studentService.updateStudent(student);}

    @GetMapping("/sortedStudentsByCredits")
    public Flux<Student> getStudentsByGraduationNotes(){
        return studentService.getStudents()
                .sort(Comparator.comparing(
                                Student::getCredits
                        )
                );
    }

    @GetMapping("/sortedStudentsByAge")
    public Flux<Student> getStudentsByAge(){
        return studentService.getStudents()
                .sort(Comparator.comparing(
                                Student::getDob
                        )
                );
    }
}
