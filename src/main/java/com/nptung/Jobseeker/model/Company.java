package com.nptung.Jobseeker.model;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Company")
public class Company implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "Description")
	private String description;

	@OneToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="IndustryID")
	private Industry industry ;

	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY,
	cascade = {CascadeType.DETACH,CascadeType.MERGE
	,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(name="CompanyInSemester",
		joinColumns=@JoinColumn(name="CompanyID"),
		inverseJoinColumns=@JoinColumn(name="SemesterID"))
	private List<Semester> semesters;
	
	
	public List<Semester> getSemesters() {
		return semesters;
	}

	public void setSemesters(List<Semester> semesters) {
		this.semesters = semesters;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Industry getIndustry() {
		return industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}
	
	
}
