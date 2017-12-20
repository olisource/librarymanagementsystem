package com.librarymanagementsystem.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class ConfigParameter {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Long id;
	
	@Column(name="name", nullable=false, unique=true)
    private String name;

	private String category;
	
    private String datatype;
	
    private String value;
	
    private String level;
	
    private String branch;

	public ConfigParameter(Long id) {
		super();
		this.id = id;
	}
	
	public ConfigParameter(String name, String category, String datatype, String value, String level, String branch) {
		super();
		this.name = name;
		this.category = category;
		this.datatype = datatype;
		this.value = value;
		this.level = level;
		this.branch = branch;
	}

	public ConfigParameter() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}	
}