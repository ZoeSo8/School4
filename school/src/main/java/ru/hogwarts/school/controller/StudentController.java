package ru.hogwarts.school.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.services.AvatarServices;
import ru.hogwarts.school.services.StudentServices;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentServices.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/age/{age}")
    public ResponseEntity<Collection<Student>> findStudentAge(@PathVariable int age) {
        if (age > 0) {
            return ResponseEntity.ok(studentServices.findStudentAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/age-range")
    public Collection<Student> getStudentsByAgeRange(
            @RequestParam("min") int minAge,
            @RequestParam("max") int maxAge) {

        if (minAge < 0 || maxAge < 0) {
            throw new IllegalArgumentException("Возраст не может быть отрицательным");
        }

        if (minAge > maxAge) {
            throw new IllegalArgumentException("Минимальный возраст не может быть больше максимального");
        }

        return studentServices.getStudentsByAgeRange(minAge, maxAge);
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<Faculty> getStudentFaculty(@PathVariable Long id) {
        Faculty faculty = studentServices.getStudentFaculty(id);
        if (faculty != null) {
            return ResponseEntity.ok(faculty);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/count")
    public Long getStudentCount(){
        return studentServices.getStudentCount();
    }

    @GetMapping("/average-age")
    public Double getStudentAvgAge(){
        return studentServices.getStudentAvgAge();
    }

    @GetMapping("/last-five")
    public List<Student> getLasFiveStudents(){
        return studentServices.getLastFiveStudents();
    }
}
