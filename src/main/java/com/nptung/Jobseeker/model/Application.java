
package com.nptung.Jobseeker.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Application")
public class Application implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int status;
	
	@ManyToOne
	@JoinColumn(name = "JobID")
	private Job job;
	
	@ManyToOne
	@JoinColumn(name = "ApplicantID")
	private Applicant applicant;
	
	public Application() {
	}
	
	public Application(int id, int status, Job job, Applicant applicant) {
		super();
		this.id = id;
		this.status = status;
		this.job = job;
		this.applicant = applicant;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Applicant getApplicant() {
		return applicant;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}
	
}
