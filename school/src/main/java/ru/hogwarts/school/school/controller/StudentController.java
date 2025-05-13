package ru.hogwarts.school.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.school.model.Student;
import ru.hogwarts.school.school.services.StudentServices;


import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("student")

public class StudentController {
    private final StudentServices studentServices;

    public StudentController(StudentServices studentServices) {
        this.studentServices = studentServices;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentServices.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentServices.createStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentServices.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public Student deleteBook(@PathVariable Long id) {
        return studentServices.deleteStudent(id);
    }

    @GetMapping("{age}")
    public ResponseEntity <Collection<Student>> findStudentAge(@RequestParam(required = false) int age) {
        if (age> 0) {
            return ResponseEntity.ok(studentServices.findStudentAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
}
