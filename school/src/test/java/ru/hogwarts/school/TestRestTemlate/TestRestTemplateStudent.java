package ru.hogwarts.school.TestRestTemlate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;



import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class TestRestTemplateStudent {

    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FacultyRepository facultyRepository;

    private String url(String endpoint) {
        return "http://localhost:" + port + "/student" + endpoint;
    }

    @BeforeEach
    void  setUp(){
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    @Test
    void testGetStudentInfo() {
        Student student = new Student();
        student.setAge(10);
        student.setName("One");

        Student created = restTemplate.postForEntity(url(""), student, Student.class).getBody();

        ResponseEntity<Student> response =
                restTemplate.getForEntity(url("/" + created.getId()), Student.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("One", response.getBody().getName());
    }

    @Test
    void testCreateStudent() {
        Student student = new Student();
        student.setAge(20);
        student.setName("Two");


        ResponseEntity<Student> response =
                restTemplate.postForEntity(url(""), student, Student.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getId());
        assertEquals("Two", response.getBody().getName());
    }

    @Test
    void testEditStudent() {
        Student student = new Student();
        student.setAge(30);
        student.setName("Three");
        Student created = restTemplate.postForEntity(url(""), student, Student.class).getBody();

        created.setName("Three Updated");

        ResponseEntity<Student> response =
                restTemplate.exchange(url(""),
                        HttpMethod.PUT,
                        new HttpEntity<>(created),
                        Student.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Three Updated", response.getBody().getName());
    }

    @Test
    void testDeleteStudent() {
        Student student = new Student();
        student.setAge(40);
        student.setName("Four");
        Student created = restTemplate.postForEntity(url(""), student, Student.class).getBody();

        ResponseEntity<Void> response =
                restTemplate.exchange(url("/" + created.getId()),
                        HttpMethod.DELETE,
                        HttpEntity.EMPTY,
                        Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ResponseEntity<Student> getResponse =
                restTemplate.getForEntity(url("/" + created.getId()), Student.class);

        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }


    @Test
    void testFindStudentAge() {
        Student student = new Student();
        student.setAge(50);
        student.setName("Five");

        restTemplate.postForEntity(url(""), student, Student.class);

        ResponseEntity<Student[]> response =
                restTemplate.getForEntity(url("/age/50"), Student[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length >= 1);
    }


    @Test
    void testGetStudentsByAgeRange() {
        Student student6 = new Student();
        student6.setAge(60);
        student6.setName("Six");
        Student student7 = new Student();
        student7.setAge(70);
        student7.setName("Seven");

        restTemplate.postForEntity(url(""), student6,Student.class);

        restTemplate.postForEntity(url(""), student7, Student.class);

        ResponseEntity<Student[]> response =
                restTemplate.getForEntity(url("/age-range?min=60&max=79"), Student[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length >= 2);
    }

    @Test
    void testGetStudentsByAgeRange_invalid() {
        ResponseEntity<String> response =
                restTemplate.getForEntity(url("/age-range?min=-1&max=20"), String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetStudentFaculty() {

        Faculty faculty = new Faculty();
        faculty.setColor("blue");
        faculty.setName("IT");


        ResponseEntity<Faculty> createdFaculty =
                restTemplate.postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);


        Student student = new Student();
        student.setAge(80);
        student.setName("Eight");
        student.setFaculty(createdFaculty.getBody());

        Student createdStudent =
                restTemplate.postForEntity(url(""), student, Student.class).getBody();

        ResponseEntity<Faculty> response =
                restTemplate.getForEntity(url("/" + createdStudent.getId() + "/faculty"), Faculty.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("IT", response.getBody().getName());

    }
}


