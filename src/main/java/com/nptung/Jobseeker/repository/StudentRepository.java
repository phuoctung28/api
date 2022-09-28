package com.nptung.Jobseeker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nptung.Jobseeker.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String>{

}
