package ru.hogwarts.school.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.school.model.Faculty;


import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FacultyServices {
    private HashMap<Long, Faculty> facultyRoster = new HashMap<>();
    private long lastId = 0;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++lastId);
        facultyRoster.put(lastId, faculty);
        return faculty;
    }

    public Faculty findFaculty(long id) {
        return facultyRoster.get(id);
    }

    public Collection<Faculty> findFacultyColor(String color) {
       return facultyRoster.values().stream()
               .filter(faculty -> Objects.equals(faculty.getColor(),color))
               .collect(Collectors.toList());
            }

    public Faculty editFaculty(Faculty faculty) {
        if (facultyRoster.containsKey(faculty.getId())) {
            facultyRoster.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public Faculty deleteFaculty(long id) {
        return facultyRoster.remove(id);
    }

}
