package ru.hogwarts.school.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.school.model.Faculty;
import ru.hogwarts.school.school.services.FacultyServices;
import org.springframework.http.HttpStatusCode;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("faculty")

public class FacultyController {
    private final FacultyServices facultyServices;

    public FacultyController(FacultyServices facultyServices) {
        this.facultyServices = facultyServices;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id) {
        Faculty faculty = facultyServices.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyServices.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyServices.editFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}")
    public void deleteFaculty(@PathVariable Long id) {
        facultyServices.deleteFaculty(id);
    }

    @GetMapping("{color}")
    public ResponseEntity<Collection<Faculty>> getFacultyInfoColor(@RequestParam (required = false) String color) {
           if (color != null&&!color.isBlank()) {
            return ResponseEntity.ok(facultyServices.findFacultyColor(color));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

}
