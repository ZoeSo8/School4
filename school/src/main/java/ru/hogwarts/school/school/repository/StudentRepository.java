package ru.hogwarts.school.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.school.model.Student;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
