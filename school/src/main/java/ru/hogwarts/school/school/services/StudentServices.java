package ru.hogwarts.school.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.school.model.Student;
import ru.hogwarts.school.school.repository.StudentRepository;


import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class StudentServices {
  private final StudentRepository studentRepository;

    public StudentServices(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).get();
    }

    public Collection<Student> findStudentAge(int age) {
        return studentRepository.findAll().stream()
                .filter(student -> Objects.equals(student.getAge(),age))
                .collect(Collectors.toList());
    }


    public Student editStudent(Student student) {
       return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }
}
