package ru.hogwarts.school.TestRestTemlate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestsRestTemplateFaculty {

    @LocalServerPort
    private int port;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void  setUp(){
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }



    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    public void testGetFaculty() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty", String.class))
                .isNotNull();
    }

    @Test
    public void testCreateFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("test");
        faculty.setColor("1");
        ResponseEntity<Faculty> response = restTemplate.postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("test");
    }

    @Test
    void testGetFacultyById() {
        Faculty faculty = new Faculty();
        faculty.setColor("2");
        faculty.setName("test2");

        Faculty saved = facultyRepository.save(faculty);
        ResponseEntity<Faculty> response =
                restTemplate.getForEntity("http://localhost:" + port + "/faculty" + "/" + saved.getId(), Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("test2");
    }

    @Test
    void testGetFacultyById_NotFound() {
        ResponseEntity<Faculty> response =
                restTemplate.getForEntity("http://localhost:" + port + "/faculty" + "/9999", Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testEditFaculty() {
        Faculty faculty = new Faculty();
        faculty.setColor("3");
        faculty.setName("test3");

        Faculty saved = facultyRepository.save(faculty);
        saved.setColor("3.1");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Faculty> request = new HttpEntity<>(saved, headers);

        ResponseEntity<Faculty> response = restTemplate.exchange(
                "http://localhost:" + port + "/faculty", HttpMethod.PUT, request, Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getColor()).isEqualTo("3.1");
    }

    @Test
    void testDeleteFaculty() {
        Faculty faculty = new Faculty();
        faculty.setColor("4");
        faculty.setName("test4");

        Faculty saved = facultyRepository.save(faculty);
        restTemplate.delete("http://localhost:" + port + "/faculty" + "/" + saved.getId());

        assertThat(facultyRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    void testFindByColor() {
        Faculty faculty1 = new Faculty();
        faculty1.setColor("5");
        faculty1.setName("test5");

        Faculty saved1 = facultyRepository.save(faculty1);

        Faculty faculty2 = new Faculty();
        faculty2.setColor("6");
        faculty2.setName("test6");

        Faculty saved2 = facultyRepository.save(faculty2);

        ResponseEntity<Faculty[]> response =
                restTemplate.getForEntity("http://localhost:" + port +"/faculty/color/5", Faculty[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody()[0].getName()).isEqualTo("test5");
    }

    @Test
    void testGetFacultyStudents() {
        Faculty faculty = new Faculty();
        faculty.setColor("7");
        faculty.setName("test7");

        Faculty facultySaved = facultyRepository.save(faculty);

        Student student1 = new Student();
        student1.setName("Tyty");
        student1.setAge(16);
        student1.setFaculty(faculty);
        Student saved1 = studentRepository.save(student1);

        Student student2 = new Student();
        student2.setName("Popo");
        student2.setAge(17);
        student2.setFaculty(faculty);
        Student saved2 = studentRepository.save(student2);

        ResponseEntity<Student[]> response =
                restTemplate.getForEntity("http://localhost:" + port + "/faculty" + "/" + faculty.getId() + "/student", Student[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
    }


}
