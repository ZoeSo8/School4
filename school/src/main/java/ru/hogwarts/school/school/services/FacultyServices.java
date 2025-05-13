package ru.hogwarts.school.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.school.model.Faculty;
import ru.hogwarts.school.school.repository.FacultyRepository;


import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FacultyServices {
    private final FacultyRepository facultyRepository;

    public FacultyServices(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {

        return facultyRepository.findById(id).get();
    }

    public Collection<Faculty> findFacultyColor(String color) {
       return facultyRepository.findAll().stream()
               .filter(faculty -> Objects.equals(faculty.getColor(),color))
               .collect(Collectors.toList());
            }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

}
