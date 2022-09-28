package com.nptung.Jobseeker.controller;

import java.util.ArrayList;
import java.util.List;

import com.nptung.Jobseeker.repository.ApplicantRepository;
import com.nptung.Jobseeker.repository.ApplicationRepository;
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

import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;
import com.google.firebase.auth.UserRecord;
import com.nptung.Jobseeker.model.Applicant;
import com.nptung.Jobseeker.model.Application;
import com.nptung.Jobseeker.model.Job;

@CrossOrigin
@RestController
@RequestMapping("/api/application")
public class ApplicationController {
	
	@Autowired
	private ApplicationRepository applicationRepository;
	@Autowired
	private ApplicantRepository applicantRepository;
	
	@GetMapping("/")
	public ResponseEntity<List<Application>> getAllApplications() throws FirebaseAuthException{
		List<Application> result = (List<Application>) applicationRepository.findAll();
		return ResponseEntity.ok()
				.header("X-Total-Count", String.valueOf(result.size()))
				.body(result);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Application> getApplicationById(@PathVariable(value = "id") int ApplicationId) throws Exception{
		Application Application = applicationRepository.findById(ApplicationId)
		          .orElseThrow(() -> new Exception("Application not found for this id :: " + ApplicationId));
		        return ResponseEntity.ok().body(Application);
	}
	
	@GetMapping("/job/{jobId}")
	public ResponseEntity<List<Applicant>> getApplicantByJobId(@PathVariable(value = "jobId") int jobId) throws Exception{
		List<Application> listApplication = applicationRepository.findByJobId(jobId);
		List<Applicant> result = new ArrayList<Applicant>();;
		for (Application application : listApplication) {
			if (application.getJob().getId() == jobId)
				result.add(application.getApplicant());
		}
		return ResponseEntity.ok()
				.header("X-Total-Count", String.valueOf(result.size()))
				.body(result);
	}
	
//	@GetMapping("/applicant/{applicantId}")
//	public ResponseEntity<List<Job>> getJobByApplicantId(@PathVariable(value = "applicantId") int applicantId) throws Exception{
//		List<Application> listApplication = applicationRepository.findByApplicantId(applicantId);
//		List<Job> result = new ArrayList<Job>();
//		for (Application application : listApplication) {
//			if (application.getApplicant().getId() == applicantId)
//				result.add(application.getJob());
//		}
//		return ResponseEntity.ok()
//				.header("X-Total-Count", String.valueOf(result.size()))
//				.body(result);
//	}
	
	@GetMapping("/applicant/{email}")
	public ResponseEntity<List<Application>> getJobByApplicantEmail(@PathVariable(value = "email") String email) throws Exception{
		List<Application> listApplication = applicationRepository.findByApplicantEmail(email);
		return ResponseEntity.ok()
				.header("X-Total-Count", String.valueOf(listApplication.size()))
				.body(listApplication);
	}
	
	@PostMapping(value = "/add/{applicantId}")
	public ResponseEntity<Application> addApplication(@PathVariable(value = "applicantId") int applicantId, @RequestBody Job job){
		Applicant applicant = applicantRepository.findById(applicantId).get();
		List<Application> listApplication = applicationRepository.findByApplicantId(applicantId);
		Application result;
//		for (Application application : listApplication) {
//			if(application.getJob().getId() == job.getId()) {
//				return ResponseEntity.status(409).body(null);
//			}
//		}
		result = new Application((int) (applicationRepository.count() + 1), 0, job, applicant);
		applicationRepository.save(result);
		return ResponseEntity.ok().body(result);
	}
	
	@PutMapping(value = "/status/{id}")
	public ResponseEntity<Application> updateApplicationStatus(@PathVariable(value = "id") int id, @RequestBody int status){
		Application result = applicationRepository.findById(id).get();
		if(result == null) {
			return ResponseEntity.notFound().build();
		}
		result.setStatus(status);
		applicationRepository.save(result);
		return ResponseEntity.ok().body(result);
	}
	
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Application> updateApplication(@PathVariable(value = "id") int id, @RequestBody Application Application){
		Application result = applicationRepository.findById(id).get();
		if(result == null) {
			return ResponseEntity.notFound().build();
		}
		result = Application;
		applicationRepository.save(result);
		return ResponseEntity.ok().body(result);
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Application> deleteApplication(@PathVariable(value = "id") int id){
		Application result = applicationRepository.findById(id).get();
		if(result == null) {
			return ResponseEntity.notFound().build();
		}
		applicationRepository.delete(result);
		return ResponseEntity.ok().body(result);
	}
	
}
