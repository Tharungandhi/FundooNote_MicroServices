package com.bridgelabz.fundoonotes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Label")
public class Label {

	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="labelName")
	private String labelName;
	
	@Column(name="userId")
	private int userId;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabelName() {
		return labelName;
	}

	public Label setLabelName(String labelName) {
		this.labelName = labelName;
		return this;
		
	}

	public int getUserId() {
		return userId;
	}

	public Label setUserId(int userId) {
		this.userId = userId;
		return this;
	}
}	
