package com.amigoscode.tutorial.student;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return this.studentService.getStudents();
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student newStudent) {
        this.studentService.addNewStudent(newStudent);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        this.studentService.deleteStudent(studentId);
    }

    // @Transactional
    // public void updateStudent(@RequestBody Student student){
    // this.studentService.updateStudent(student);
    // }

    @PutMapping(path = "{studentId}")
    public void updateStudentById(@PathVariable("studentId") Long studentId, @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        this.studentService.updateStudent(studentId, name, email);
    }
}
