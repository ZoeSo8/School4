package ru.hogwarts.school.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student,Long> {
Student findByAgeBetween(int minAge, int maxAge);
Student findByFacultyIgnoreCase(String name);
}
