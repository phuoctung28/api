package com.nptung.Jobseeker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nptung.Jobseeker.model.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer>{

	List<Application> findByJobId(int jobId);
	
	List<Application> findByApplicantId(int applicantId);

	List<Application> findByApplicantEmail(String email);
	

}
