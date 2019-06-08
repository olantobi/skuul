package com.liferon.skuul.controller;

import com.liferon.skuul.model.Student;
import com.liferon.skuul.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @project web-metering
 * @created by tobi on 2019-06-04
 */
@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;

    @PostMapping
    public ResponseEntity<?> registerStudent(@RequestBody Student student) {
        Student savedStudent = studentRepository.save(student);

        return ResponseEntity.ok(savedStudent);
    }

    @GetMapping
    public ResponseEntity<?> getAllStudent() {
        Iterable<Student> retrievedStudents = studentRepository.findAll();

        if (!retrievedStudents.iterator().hasNext()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(retrievedStudents);
        }

        return ResponseEntity.ok(retrievedStudents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable("id") String studentId) {
        Optional<Student> retrievedStudent = studentRepository.findById(studentId);

        if (!retrievedStudent.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(retrievedStudent.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable("id") String studentId, Student student) {
        Optional<Student> retrievedStudent = studentRepository.findById(studentId);

        if (!retrievedStudent.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        student.setId(studentId);
        Student updatedStudent = studentRepository.save(student);

        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") String studentId) {
        Optional<Student> retrievedStudent = studentRepository.findById(studentId);

        if (!retrievedStudent.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        studentRepository.deleteById(studentId);

        return ResponseEntity.ok().build();
    }

}
