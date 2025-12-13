package ru.hogwarts.school.repository;

import org.hibernate.annotations.Collate;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty,Long> {

Faculty findByNameIgnoreCase (String name);
Collection <Faculty> findByColorIgnoreCase (String color);

}
