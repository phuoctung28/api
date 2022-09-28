package com.nptung.Jobseeker.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.bytebuddy.utility.nullability.MaybeNull;

@Entity
@Table(name = "Job")
public class Job implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "JobTitle")
	private String jobTitle;
	
	@Column(name = "Benefit")
	private String jobSummary;
	
	@Column(name = "DatePublished")
	private Date datePublished;
	
	@Column(name = "Salary")
	private String salary;
	
	@Column(name = "Responsibilities")
	private String responsibility;
	
	@Column(name = "WorkingLocation")
	private String workLocation;
	
	@Column(name = "Qualifications")
	private String qualifications;
	
//	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="JobCategoryID")
	private JobCategory jobCategory;
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY,
			cascade = {CascadeType.DETACH,CascadeType.MERGE
			,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(name="Application",
				joinColumns=@JoinColumn(name="JobID"),
				inverseJoinColumns=@JoinColumn(name="ApplicantID"))
	private List<Applicant> applicants;
	
	@ManyToOne
	@JoinColumn(name="CompanyID")
	private Company company;
	
	@ManyToOne
	@JoinColumn(name="MajorID")
	private Major major;
	
	
	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Applicant> getApplicants() {
		return applicants;
	}

	public void setApplicants(List<Applicant> applicants) {
		this.applicants = applicants;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getJobSummary() {
		return jobSummary;
	}

	public void setJobSummary(String jobSummary) {
		this.jobSummary = jobSummary;
	}

	public Date getDatePublished() {
		return datePublished;
	}

	public void setDatePublished(Date datePublished) {
		this.datePublished = datePublished;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}

	public String getWorkLocation() {
		return workLocation;
	}

	public void setWorkLocation(String workLocation) {
		this.workLocation = workLocation;
	}

	public String getQualifications() {
		return qualifications;
	}

	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}

	public JobCategory getJobCategory() {
		return jobCategory;
	}

	public void setJobCategory(JobCategory jobCategory) {
		this.jobCategory = jobCategory;
	}
	
	
	
}
