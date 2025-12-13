package ru.hogwarts.school.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.services.FacultyServices;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class TestMockMvcFaculty {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyServices facultyServices;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetFacultyById() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("test");
        faculty.setColor("1");
        when(facultyServices.findFaculty(1L)).thenReturn(faculty);

        mockMvc.perform(get("/faculty/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.color").value("1"));

        verify(facultyServices).findFaculty(1L);
    }

    @Test
    void testGetFacultyById_NotFound() throws Exception {
        when(facultyServices.findFaculty(999L)).thenReturn(null);

        mockMvc.perform(get("/faculty/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateFaculty() throws Exception {
        Faculty input = new Faculty(); //тут еще был нул id
        input.setName("test2");
        input.setColor("2");

        Faculty created = new Faculty();
        created.setName("test2");
        created.setColor("2");
        when(facultyServices.createFaculty(any(Faculty.class))).thenReturn(created);

        mockMvc.perform(post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.name").value("test2"));

        verify(facultyServices).createFaculty(any(Faculty.class));
    }

    @Test
    void testEditFaculty() throws Exception {

        Faculty input = new Faculty();
        input.setName("test3");
        input.setColor("3");

        Faculty updated = new Faculty();
        updated.setName("test3");
        updated.setColor("3.1");
        when(facultyServices.editFaculty(any(Faculty.class))).thenReturn(updated);

        mockMvc.perform(put("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value("3.1"));
    }

    @Test
    void testEditFaculty_BadRequest() throws Exception {
        Faculty input = new Faculty();
        input.setName("test4");
        input.setColor("4");
        when(facultyServices.editFaculty(any(Faculty.class))).thenReturn(null);

        mockMvc.perform(put("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteFaculty() throws Exception {
        mockMvc.perform(delete("/faculty/{id}", 5L))
                .andExpect(status().isOk());

        verify(facultyServices).deleteFaculty(5L);
    }

    @Test
    void testFindByColor() throws Exception {
        Faculty f1 = new Faculty();
        f1.setName("test1");
        f1.setColor("1");
        when(facultyServices.findFacultyByColor("1")).thenReturn(List.of(f1));

        mockMvc.perform(get("/faculty/color/{color}","1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("test1"));

        verify(facultyServices).findFacultyByColor("1");
    }

       @Test
    void testGetFacultyStudents() throws Exception {
        Student s1 = new Student();
        s1.setName("Popa");
        s1.setAge(24);
        Student s2 = new Student();
        s2.setName("Pyxa");
        s2.setAge(80);
        when(facultyServices.getFacultyStudents(1L)).thenReturn(List.of(s1, s2));

        mockMvc.perform(get("/faculty/{id}/student", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Popa"))
                .andExpect(jsonPath("$[1].name").value("Pyxa"));

        verify(facultyServices).getFacultyStudents(1L);
    }

    @Test
    void testGetFacultyStudents_NotFound() throws Exception {
        when(facultyServices.getFacultyStudents(999L)).thenReturn(null);

        mockMvc.perform(get("/faculty/{id}/students", 999L))
                .andExpect(status().isNotFound());
    }
}

