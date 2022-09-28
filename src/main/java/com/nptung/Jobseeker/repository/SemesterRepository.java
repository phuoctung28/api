package com.nptung.Jobseeker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nptung.Jobseeker.model.Semester;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, String>{

}
