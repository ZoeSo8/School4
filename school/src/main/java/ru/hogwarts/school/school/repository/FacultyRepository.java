package ru.hogwarts.school.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.school.model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty,Long> {
}
