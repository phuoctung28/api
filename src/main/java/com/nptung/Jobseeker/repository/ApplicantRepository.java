package com.nptung.Jobseeker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nptung.Jobseeker.model.Applicant;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Integer> {

	Applicant findByEmail(String email);
		
	List<Applicant> findBySemesterId(String id);
}
