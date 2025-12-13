package ru.hogwarts.school.MockMvc;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.services.StudentServices;

import java.util.Arrays;
import java.util.Collection;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class TestMockMvcStudent {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentServices studentServices;


    @Test
    void testGetStudentInfo_found() throws Exception {
        Student student = new Student();
        student.setName("One");
        student.setId(1L);
        student.setAge(10);
        Mockito.when(studentServices.findStudent(1L)).thenReturn(student);

        mockMvc.perform(get("/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("One"))
                .andExpect(jsonPath("$.age").value(10));
    }

    @Test
    void testGetStudentInfo_notFound() throws Exception {
        Mockito.when(studentServices.findStudent(1L)).thenReturn(null);

        mockMvc.perform(get("/student/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    void testCreateStudent() throws Exception {

        Student student = new Student();
        student.setName("Two");

        student.setAge(20);
        Student saved = new Student();
        saved.setName("Two");
        saved.setId(1L);
        saved.setAge(20);

        Mockito.when(studentServices.createStudent(student)).thenReturn(saved);

        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }


    @Test
    void testEditStudent_ok() throws Exception {

        Student student = new Student();
        student.setName("Three");
        student.setId(1L);
        student.setAge(30);

        Mockito.when(studentServices.editStudent(student)).thenReturn(student);

        mockMvc.perform(put("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Three"));
    }

    @Test
    void testEditStudent_badRequest() throws Exception {

        Student student = new Student();
        student.setName("Three");
        student.setId(1L);
        student.setAge(20);

        Mockito.when(studentServices.editStudent(student)).thenReturn(null);

        mockMvc.perform(put("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void testDeleteStudent() throws Exception {
        mockMvc.perform(delete("/student/5"))
                .andExpect(status().isOk());

        Mockito.verify(studentServices).deleteStudent(5L);
    }


    @Test
    void testFindStudentAge_positive() throws Exception {
        Student student = new Student();
        student.setName("Fore");
        student.setId(1L);
        student.setAge(40);
        Collection<Student> students = Arrays.asList(student
        );

        Mockito.when(studentServices.findStudentAge(40)).thenReturn(students);

        mockMvc.perform(get("/student/age/40"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Fore"));
    }

    @Test
    void testFindStudentAge_zero() throws Exception {
        mockMvc.perform(get("/student/age/0"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }


    @Test
    void testGetStudentsByAgeRange_ok() throws Exception {
        Student student = new Student();
        student.setName("Five");
        student.setId(1L);
        student.setAge(50);
        Student student2 = new Student();
        student2.setName("Six");
        student2.setId(2L);
        student2.setAge(60);
        Collection<Student> students = Arrays.asList(
               student,student2
        );

        Mockito.when(studentServices.getStudentsByAgeRange(50, 60)).thenReturn(students);

        mockMvc.perform(get("/student/age-range?min=50&max=60"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].name").value("Six"));
    }

    @Test
    void testGetStudentsByAgeRange_negativeAge() throws Exception {
        mockMvc.perform(get("/student/age-range?min=-1&max=10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetStudentsByAgeRange_minGreaterThanMax() throws Exception {
        mockMvc.perform(get("/student/age-range?min=30&max=20"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetStudentFaculty_ok() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Engineering");
        faculty.setColor("Blue");

        Mockito.when(studentServices.getStudentFaculty(1L)).thenReturn(faculty);

        mockMvc.perform(get("/student/1/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Engineering"));
    }

    @Test
    void testGetStudentFaculty_notFound() throws Exception {
        Mockito.when(studentServices.getStudentFaculty(1L)).thenReturn(null);

        mockMvc.perform(get("/student/1/faculty"))
                .andExpect(status().isNotFound());
    }
}

