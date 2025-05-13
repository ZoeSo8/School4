package ru.hogwarts.school.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.school.model.Student;


import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class StudentServices {
    private HashMap<Long, Student> studentRoster = new HashMap<>();
    private long lastId = 0;

    public Student createStudent(Student student) {
        student.setId(++lastId);
        studentRoster.put(lastId, student);
        return student;
    }

    public Student findStudent(long id) {
        return studentRoster.get(id);
    }

    public Collection<Student> findStudentAge(int age) {
        return studentRoster.values().stream()
                .filter(student -> Objects.equals(student.getAge(),age))
                .collect(Collectors.toList());
    }


    public Student editStudent(Student student) {
        if (studentRoster.containsKey(student.getId())) {
            studentRoster.put(student.getId(), student);
            return student;
        }
        return null;
    }

    public Student deleteStudent(long id) {
        return studentRoster.remove(id);
    }
}
