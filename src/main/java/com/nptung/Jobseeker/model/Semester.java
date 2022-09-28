package com.nptung.Jobseeker.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "Semester")
public class Semester implements Serializable {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private String id; 
	
	@Column(name = "SemesterName")
	private String name;

	@Column(name = "StartDate")
	private Date startDate;
	
	@Column(name = "EndDate")
	private Date endDate;
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY,
			cascade = CascadeType.MERGE)
	@JoinTable(name="CompanyInSemester",
			joinColumns=@JoinColumn(name="SemesterID"),
			inverseJoinColumns=@JoinColumn(name="CompanyID"))
	private List<Company> companies;	

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
}
