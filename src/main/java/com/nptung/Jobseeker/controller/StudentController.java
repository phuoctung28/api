package com.nptung.Jobseeker.controller;

import java.util.List;
import java.util.Optional;

import com.nptung.Jobseeker.model.Student;
import com.nptung.Jobseeker.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/student")
public class StudentController{

	@Autowired
	private StudentRepository studentRepository;
	
	@GetMapping("/")
	public ResponseEntity<List<Student>> getAllstudents(){
		List<Student> result = (List<Student>) studentRepository.findAll();
		return ResponseEntity.ok()
				.header("X-Total-Count", String.valueOf(result.size()))
				.body(result);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Student> getstudentById(@PathVariable(value = "id") String studentId) throws Exception{
		Student student = studentRepository.findById(studentId)
		          .orElseThrow(() -> new Exception("student not found for this id :: " + studentId));
		        return ResponseEntity.ok().body(student);
	}
	
	@PostMapping(value = "/add")
	public ResponseEntity<Student> addstudent(@RequestBody Student student){
//		Optional<Student> studentCheck = studentRepository.findById(student.getId());
//		if(studentCheck == null) {
			studentRepository.save(student);
//		} else {
//			return ResponseEntity.status(409).body(null);
//		}
		return ResponseEntity.ok().body(student);
	}
	
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Student> updatestudent(@PathVariable(value = "id") String id, @RequestBody Student student){
		Student result = studentRepository.findById(id).get();
		if(result == null) {
			return ResponseEntity.notFound().build();
		}
		result = student;
		studentRepository.save(result);
		return ResponseEntity.ok().body(result);
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Student> deletestudent(@PathVariable(value = "id") String id){
		Student result = studentRepository.findById(id).get();
		if(result == null) {
			return ResponseEntity.notFound().build();
		}
		studentRepository.delete(result);
		return ResponseEntity.ok().body(result);
	}
}
