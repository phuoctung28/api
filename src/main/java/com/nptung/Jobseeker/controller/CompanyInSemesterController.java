package com.nptung.Jobseeker.controller;

import java.util.List;

import com.nptung.Jobseeker.repository.CompanyInSemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuthException;
import com.nptung.Jobseeker.model.CompanyInSemester;

@CrossOrigin
@RestController
@RequestMapping("/api/cis")
public class CompanyInSemesterController {

	@Autowired
	private CompanyInSemesterRepository companyInSemesterRepository;
	
	@GetMapping("/")
	public ResponseEntity<List<CompanyInSemester>> getAllCompanyInSemesters() throws FirebaseAuthException{
		List<CompanyInSemester> result = (List<CompanyInSemester>) companyInSemesterRepository.findAll();
		return ResponseEntity.ok()
				.header("X-Total-Count", String.valueOf(result.size()))
				.body(result);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CompanyInSemester> getCompanyInSemesterById(@PathVariable(value = "id") int cisId) throws Exception{
		CompanyInSemester cis = companyInSemesterRepository.findById(cisId)
		          .orElseThrow(() -> new Exception("Company in semester not found for this id :: " + cisId));
		        return ResponseEntity.ok().body(cis);
	}
	
	@PostMapping(value = "/add")
	public ResponseEntity<CompanyInSemester> addCIS(@RequestBody CompanyInSemester cis){
		companyInSemesterRepository.save(cis);
		return ResponseEntity.ok().body(cis);
	}
	
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<CompanyInSemester> updateCIS(@PathVariable(value = "id") int id, @RequestBody CompanyInSemester cis){
		CompanyInSemester result = companyInSemesterRepository.findById(id).get();
		if(result == null) {
			return ResponseEntity.notFound().build();
		}
		result = cis;
		companyInSemesterRepository.save(result);
		return ResponseEntity.ok().body(result);
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<CompanyInSemester> deleteCIS(@PathVariable(value = "id") int id){
	    CompanyInSemester result = companyInSemesterRepository.findById(id).get();
		if(result == null) {
			return ResponseEntity.notFound().build();
		}
		companyInSemesterRepository.delete(result);
		return ResponseEntity.ok().body(result);
	}
}
