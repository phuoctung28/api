package com.nptung.Jobseeker.controller;

import java.util.List;

import com.nptung.Jobseeker.repository.CompanyRepository;
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

import com.nptung.Jobseeker.model.Company;

@CrossOrigin
@RestController
@RequestMapping("/api/company")
public class CompanyController {
	@Autowired 
	private CompanyRepository companyRepository;
	
	
	@GetMapping("/")
	public ResponseEntity<List<Company>> getAllCompanies(){
		List<Company> result = companyRepository.findAll();
		return ResponseEntity.ok()
				.header("X-Total-Count", String.valueOf(result.size()))
				.body(result);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Company> getCompanyById(@PathVariable(value = "id") int companyId) throws Exception{
		Company Company = companyRepository.findById(companyId)
		          .orElseThrow(() -> new Exception("Company not found for this id :: " + companyId));
		        return ResponseEntity.ok().body(Company);
	}
	
	@GetMapping("/industry/{industryId}")
	public ResponseEntity<List<Company>> getCompanyByIndustry(@PathVariable(value = "industryId") int industryId) throws Exception{
		List<Company> result = (List<Company>) companyRepository.findByIndustryId(industryId);
		return ResponseEntity.ok()
				.header("X-Total-Count", String.valueOf(result.size()))
				.body(result);
	}
	
	@PostMapping(value = "/add")
	public ResponseEntity<Company> addCompany(@RequestBody Company company){
//		Company CompanyCheck = companyRepository.getOne(company.getId());
		companyRepository.save(company);
		return ResponseEntity.ok().body(company);
	}
	
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Company> updateCompany(@PathVariable(value = "id") int id, @RequestBody Company company){
		Company result = companyRepository.findById(id).get();
		if(result == null) {
			return ResponseEntity.notFound().build();
		}
		result = company;
		companyRepository.save(result);
		return ResponseEntity.ok().body(result);
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Company> deleteCompany(@PathVariable(value = "id") int id){
		Company result = companyRepository.findById(id).get();
		if(result == null) {
			return ResponseEntity.notFound().build();
		}
		companyRepository.delete(result);
		
		return ResponseEntity.ok().body(result);
	}
}
