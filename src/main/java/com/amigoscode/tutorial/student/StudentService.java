package com.amigoscode.tutorial.student;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

	private final StudentRepository studentRepository;

	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public List<Student> getStudents() {
		return studentRepository.findAll();
	}

	public void addNewStudent(Student student) {
		Optional<Student> studentOptional = this.studentRepository.findStudentByEmail(student.getEmail());
		if (studentOptional.isPresent()) {
			throw new IllegalStateException("email taken");
		}
		this.studentRepository.save(student);
	}

	public void deleteStudent(Long studentId) {
		if (this.studentRepository.findById(studentId).isPresent()) {
			this.studentRepository.deleteById(studentId);
		} else {
			throw new IllegalStateException("no student exists for id: " + studentId);
		}
	}

	@Transactional
	public void updateStudent(Long studentId, String name, String email) {
		Student studentToUpdate = this.studentRepository.findById(studentId)
				.orElseThrow(() -> new IllegalStateException("No student found with ID: " + studentId));
		if (name != null && !name.isEmpty() && !studentToUpdate.getName().equals(name)) {
			System.out.print("changing name to: " + name);
			studentToUpdate.setName(name);
		}

		if (email != null && !email.isEmpty() && !studentToUpdate.getEmail().equals(email)) {
			if (this.studentRepository.findStudentByEmail(email).isPresent()) {
				throw new IllegalStateException("user with provided email already exists");
			}
			studentToUpdate.setEmail(email);
		}
	}
}