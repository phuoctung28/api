package com.nptung.Jobseeker.controller;

import java.util.List;

import com.nptung.Jobseeker.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
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

import com.nptung.Jobseeker.model.Job;

@CrossOrigin
@RestController
@RequestMapping("/api/job")
public class JobController {

	@Autowired 
	private JobRepository jobRepository;
	
	@GetMapping("/")
	public ResponseEntity<List<Job>> getAllJobs(){
		List<Job> result = (List<Job>) jobRepository.findAll();
		return ResponseEntity.ok()
				.header("X-Total-Count", String.valueOf(result.size()))
				.body(result);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Job> getJobById(@PathVariable(value = "id") int jobId) throws Exception{
		Job job = jobRepository.findById(jobId)
		          .orElseThrow(() -> new Exception("Job not found for this id :: " + jobId));
		        return ResponseEntity.ok().body(job);
	}
	
	@GetMapping("/search/{search}")
	public ResponseEntity<List<Job>> findByTitle(@PathVariable(value = "search") String search) throws Exception{
		List<Job> result = (List<Job>) jobRepository.findByJobTitleContains(search);
		return ResponseEntity.ok()
				.header("X-Total-Count", String.valueOf(result.size()))
				.body(result);
	}
	
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<Job>> findByCategory(@PathVariable(value = "categoryId") int id) throws Exception{
		List<Job> result = (List<Job>) jobRepository.findByJobCategoryId(id);
		return ResponseEntity.ok()
				.header("X-Total-Count", String.valueOf(result.size()))
				.body(result);
	}
	
	@GetMapping("/company/{companyId}")
	public ResponseEntity<List<Job>> findByCompany(@PathVariable(value = "companyId") int id) throws Exception{
		List<Job> result = (List<Job>) jobRepository.findByCompanyId(id);
		return ResponseEntity.ok()
				.header("X-Total-Count", String.valueOf(result.size()))
				.body(result);
	}
	
	@PostMapping(value = "/add")
	public ResponseEntity<Job> addJob(@RequestBody Job job){
//		Job jobCheck = jobRepository.getOne(job.getId());
//		if(jobCheck == null) {
			jobRepository.save(job);
//		} else {
//			return ResponseEntity.status(409).body(null);
//		}
		return ResponseEntity.ok().body(job);
	}
	
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Job> updateJob(@PathVariable(value = "id") int id, @RequestBody Job job){
		Job result = jobRepository.findById(id).get();
		if(result == null) {
			return ResponseEntity.notFound().build();
		}
		result = job;
		jobRepository.save(result);
		return ResponseEntity.ok().body(result);
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Job> deleteJob(@PathVariable(value = "id") int id){
		Job result = jobRepository.findById(id).get();
		if(result == null) {
			return ResponseEntity.notFound().build();
		}
		jobRepository.delete(result);
		
		return ResponseEntity.ok().body(result);
	}
	
}