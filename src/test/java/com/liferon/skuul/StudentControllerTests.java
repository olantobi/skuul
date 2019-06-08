package com.liferon.skuul;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferon.skuul.model.Student;
import com.liferon.skuul.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @project web-metering
 * @created by tobi on 2019-06-05
 */
//@RunWith(SpringRunner.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTests {

    @MockBean
    private StudentRepository studentRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @BeforeAll
//    public void setUp() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }

    @Test
    public void createStudent_should_return_created_student() throws Exception {
        Student student = new Student();
        student.setId("STD000023");
        student.setName("Femi Olanrewaju");
        student.setGender(Student.Gender.Male);
        student.setGrade(5);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/api/student")
                .content(mapper.writeValueAsString(student))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.gender").value(student.getGender().toString()))
                .andExpect(jsonPath("$.grade").value(student.getGrade()));
    }

    @Test
    public void updateStudent_should_return_updated_student() throws Exception {
        Student student = new Student();
        student.setId("STD000023");
        student.setName("Femi Olanrewaju");
        student.setGender(Student.Gender.Male);
        student.setGrade(7);


        when(studentRepository.findById("STD000023")).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(put("/api/student/{id}", "STD000023")
                .content(mapper.writeValueAsString(student))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.gender").value(student.getGender().toString()))
                .andExpect(jsonPath("$.grade").value(student.getGrade()));
    }

    @Test
    public void updateStudent_with_non_existent_id_should_return_404() throws Exception {
        Student student = new Student();
        student.setId("STD000023");
        student.setName("Femi Olanrewaju");
        student.setGender(Student.Gender.Male);
        student.setGrade(7);


        when(studentRepository.findById("STD000023")).thenReturn(Optional.of(student));

        mockMvc.perform(put("/api/student/{id}", "STD000025")
                .content(mapper.writeValueAsString(student))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllStudent_should_return_all_created_students() throws Exception {
        Student femi = new Student();
        femi.setId("STD000023");
        femi.setName("Femi Olanrewaju");
        femi.setGender(Student.Gender.Male);
        femi.setGrade(5);

        Student ayo = new Student();
        ayo.setId("STD00024");
        ayo.setName("Ayomide Olanrewaju");
        ayo.setGender(Student.Gender.Female);
        ayo.setGrade(7);

        List<Student> studentList = new ArrayList<Student>();
        studentList.add(femi);
        studentList.add(ayo);

        when(studentRepository.findAll()).thenReturn(studentList);

        mockMvc.perform(get("/api/student")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(studentList.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(studentList.get(0).getName()))
                .andExpect(jsonPath("$[0].gender").value(studentList.get(0).getGender().toString()))
                .andExpect(jsonPath("$[0].grade").value(studentList.get(0).getGrade()));
    }

    @Test
    public void getAllStudent_when_empty_should_return_no_content() throws Exception {

        List<Student> studentList = new ArrayList<Student>();

        when(studentRepository.findAll()).thenReturn(studentList);

        mockMvc.perform(get("/api/student")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.*", hasSize(0)));
    }

    @Test
    public void getStudent_should_return_student() throws Exception {
        Student student = new Student();
        student.setId("STD000023");
        student.setName("Femi Olanrewaju");
        student.setGender(Student.Gender.Male);
        student.setGrade(5);

        when(studentRepository.findById("STD000023")).thenReturn(Optional.of(student));

        mockMvc.perform(get("/api/student/{id}", "STD000023")
                .content(mapper.writeValueAsString(student))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.gender").value(student.getGender().toString()))
                .andExpect(jsonPath("$.grade").value(student.getGrade()));
    }

    @Test
    public void getStudent_with_non_existent_id_should_return_404() throws Exception {
        Student student = new Student();
        student.setId("STD000023");
        student.setName("Femi Olanrewaju");
        student.setGender(Student.Gender.Male);
        student.setGrade(7);


        when(studentRepository.findById("STD000023")).thenReturn(Optional.of(student));

        mockMvc.perform(get("/api/student/{id}", "STD000025")
                .content(mapper.writeValueAsString(student))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteStudent_should_return_Ok() throws Exception {
        Student student = new Student();
        student.setId("STD000023");
        student.setName("Femi Olanrewaju");
        student.setGender(Student.Gender.Male);
        student.setGrade(5);

        when(studentRepository.findById("STD000023")).thenReturn(Optional.of(student));

        mockMvc.perform(delete("/api/student/{id}", "STD000023")
                .content(mapper.writeValueAsString(student))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteStudent_with_non_existent_id_should_return_404() throws Exception {
        Student student = new Student();
        student.setId("STD000023");
        student.setName("Femi Olanrewaju");
        student.setGender(Student.Gender.Male);
        student.setGrade(7);


        when(studentRepository.findById("STD000023")).thenReturn(Optional.of(student));

        mockMvc.perform(delete("/api/student/{id}", "STD000025")
                .content(mapper.writeValueAsString(student))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
