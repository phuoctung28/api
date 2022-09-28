package com.nptung.Jobseeker.controller;

import java.util.*;

import com.nptung.Jobseeker.repository.ApplicantRepository;
import com.nptung.Jobseeker.repository.StudentRepository;
import org.springframework.beans.BeanUtils;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.nptung.Jobseeker.model.Applicant;
import com.nptung.Jobseeker.model.Job;
import com.nptung.Jobseeker.model.Semester;
import com.nptung.Jobseeker.model.Student;

@CrossOrigin
@RestController
@RequestMapping("/api/applicant")
public class ApplicantController {
	
	@Autowired
	private ApplicantRepository applicantRepository;
	@Autowired
	private StudentRepository studentRepository;
	
	@GetMapping("/")
	public ResponseEntity<List<Applicant>> getAllApplicants(){
		List<Applicant> result = (List<Applicant>) applicantRepository.findAll();
//		for (int i = 0; i < result.size(); i ++) {
//			CreateRequest request = new CreateRequest()
//				    .setEmail(result.get(i).getEmail())
//				    .setEmailVerified(false)
//				    .setPassword("secretPassword")
//				    .setPhoneNumber("+84" + result.get(i).getPhoneNumber())
//				    .setDisplayName(result.get(i).getStudent().getName())
//				    .setPhotoUrl("http://www.example.com/12345678/photo.png")
//				    .setDisabled(false);
//
//				try {
//					UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
//					System.out.println(userRecord.getEmail());
//				} catch (FirebaseAuthException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//		}
		return ResponseEntity.ok()
				.header("X-Total-Count", String.valueOf(result.size()))
				.body(result);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Applicant> getApplicantById(@PathVariable(value = "id") int applicantId) throws Exception{
		Applicant applicant = applicantRepository.findById(applicantId)
		          .orElseThrow(() -> new Exception("Applicant not found for this id :: " + applicantId));
		        return ResponseEntity.ok().body(applicant);
	}
	
//	@GetMapping("/google/")
//	public ResponseEntity<List<Applicant>> getAllApplicantsByGoogle() throws Exception{
//		List<Applicant> list = (List<Applicant>) applicantRepository.findAll();
////		List<Applicant> result = null;
//		boolean check = false;
//		Applicant applicant = null;
////		ExportedUserRecord u = null;
//		ListUsersPage page = FirebaseAuth.getInstance().listUsers(null);
//		for (ExportedUserRecord user : page.iterateAll()) {
//			for (int j = 0; j < list.size(); j++) {
//				  if (!list.get(j).getEmail()
//						  .equalsIgnoreCase(user.getEmail()) && !user.getEmail().equalsIgnoreCase("admin@fpt.admin.vn")) {
//					  check = true;
//					  Student student = new Student((
//							  user.getEmail().split("@")[0]).substring(user.getEmail().split("@")[0].length() - 8),
//							  user.getDisplayName(), 
//							  "", 
//							  "", 
//							  list.get(0).getStudent().getSemester(), 
//							  list.get(0).getStudent().getMajor(), 
//							  0);
//					  applicant = new Applicant(
//							  list.size(), 
////							  user.getPhoneNumber().replace("+84", "0"), 
//							  "012345678",
//							  "", 
//							  user.getEmail(), 
//							  0, 
//							  student,
//							  list.get(0).getJobs(),
//							  student.getSemester(),
//							  null);
//				  };
//			  }
//		}
//		
//		if (check) {
//			  applicantRepository.save(applicant);
//		}
//		list = (List<Applicant>) applicantRepository.findAll();
//		return ResponseEntity.ok()
//				.header("X-Total-Count", String.valueOf(list.size()))
//				.body(list);
//	}
	
//	@GetMapping("/google/")
//	public ResponseEntity<List<Applicant>> getApplicantsByGoogleId(@RequestBody List<String> listEmail) throws Exception{
//		List<Applicant> list = (List<Applicant>) applicantRepository.findAll();
//		List<Applicant> result = null;
//		for (int i = 0; i < listEmail.size(); i++) {
//		  for (int j = 0; j < list.size(); j++) {
//			  if (list.get(j).getEmail()
//					  .equalsIgnoreCase(listEmail.get(i))) {
//				  result.add(list.get(i));
//			  };
//		  }
//		}
//		return ResponseEntity.ok()
//				.header("X-Total-Count", String.valueOf(result.size()))
//				.body(result);
//	}
	
	@GetMapping("/google/{email}")
	public ResponseEntity<Applicant> getApplicantByGoogleEmail(@PathVariable(value = "email") String email) throws Exception{
		UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
		Applicant applicant = applicantRepository.findByEmail(userRecord.getEmail());   
		
		return ResponseEntity.ok().body(applicant);
	}
	
	@PostMapping("/google/{email}")
	public ResponseEntity<Applicant> createApplicantByGoogleEmail(@PathVariable(value = "email") String email) throws Exception{
		UserRecord user = FirebaseAuth.getInstance().getUserByEmail(email);
		Applicant a = applicantRepository.findByEmail(email);
		
		if (a == null) {
			Applicant temp = applicantRepository.findByEmail("dangnvhse161026@fpt.edu.vn");
			
			Student student = new Student((
					  email.split("@")[0]).substring(email.split("@")[0].length() - 8),
					  user.getDisplayName(), 
					  "", 
					  "", 
					  temp.getStudent().getSemester(), 
					  temp.getStudent().getMajor(), 
					  0);
			
			List<Job> lj = Collections.<Job>emptyList();
			BeanUtils.copyProperties(temp.getJobs(), lj);
			  a = new Applicant(
//					  list.size(), 
//					  user.getPhoneNumber().replace("+84", "0"), 
					  "012345678",
					  "", 
					  email, 
					  0, 
					  student,
					  lj,
					  student.getSemester(),
					  null);
			  studentRepository.save(student);
			applicantRepository.save(a);
		}
		
		return ResponseEntity.ok().body(a);
	}
	
	@PostMapping(value = "/add")
	public ResponseEntity<Applicant> addApplicant(@RequestBody Applicant applicant){
//		Applicant applicantCheck = applicantRepository.getOne(applicant.getId());
//		if(applicantCheck == null) {
			applicantRepository.save(applicant);
//		} else {
//			return ResponseEntity.status(409).body(null);
//		}
		return ResponseEntity.ok().body(applicant);
	}
	
	@PutMapping(value = "/cv/{email}")
	public ResponseEntity<Applicant> updateApplicantCV(@PathVariable(value = "email") String email, @RequestBody String src) throws FirebaseAuthException{
		UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
		Applicant result = applicantRepository.findByEmail(userRecord.getEmail());
		if(result == null) {
			return ResponseEntity.notFound().build();
		}
		result.setCvFile(src);
		applicantRepository.save(result);
		return ResponseEntity.ok().body(result);
	}
	
	@PutMapping(value = "/semester/{email}")
	public ResponseEntity<Applicant> updateApplicantSemsester(@PathVariable(value = "email") String email, @RequestBody Semester semester) throws FirebaseAuthException{
		
		Applicant result = applicantRepository.findByEmail(email);
		if(result == null) {
			return ResponseEntity.notFound().build();
		}
		result.setSemester(semester);
		applicantRepository.save(result);
		return ResponseEntity.ok().body(result);
	}
	
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Applicant> updateApplicant(@PathVariable(value = "id") int id, @RequestBody Applicant applicant){
		Applicant result = applicantRepository.findById(id).get();
		if(result == null) {
			return ResponseEntity.notFound().build();
		}
		result = applicant;
		applicantRepository.save(result);
		return ResponseEntity.ok().body(result);
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Applicant> deleteApplicant(@PathVariable(value = "id") int id){
		Applicant result = applicantRepository.findById(id).get();
		if(result == null) {
			return ResponseEntity.notFound().build();
		}
		applicantRepository.delete(result);
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping(value = "/semester/{semesterId}")
	public ResponseEntity<List<Applicant>> findBySemester(@PathVariable(value  = "semesterId") String id){
		List<Applicant> result = applicantRepository.findBySemesterId(id);
		return ResponseEntity.ok()
				.header("X-Total-Count", String.valueOf(result.size()))
				.body(result);
	}
	
	
}
