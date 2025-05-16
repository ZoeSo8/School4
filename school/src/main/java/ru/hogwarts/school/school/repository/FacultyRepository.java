package ru.hogwarts.school.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.school.model.Faculty;
import ru.hogwarts.school.school.model.Student;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty,Long> {
    Faculty findByColorOrName(String color, String name);
    Collection<Student> findStudentByFacultyIgnoreCase (String name, String color);
}
