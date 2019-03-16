package com.bridgelabz.fundoonotes.model;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "UserDetails")
@Getter
@Setter
@Accessors(chain = true)
public class UserDetails implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "emailId", unique = true)
	private String emailId;

	@Column(name = "password", length = 60)
	private String password;

	@Column(name = "mobileNumber")
	private long mobileNumber;

	@Column(name = "activation_status")
	private boolean activationStatus;
	
	@Lob
	private byte[] image;

	public boolean isActivationStatus() {
		return activationStatus;
	}
	
	
	public byte[] getImage() {
		return image;
	}

	public UserDetails setImage(byte[] image) {
		this.image = image;
		return this;
	}

	public UserDetails setActivationStatus(boolean activationStatus) {
		this.activationStatus = activationStatus;
		return this;
	}

	public int getId() {
		return id;
	}

	public UserDetails setId(int id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public UserDetails setName(String name) {
		this.name = name;
		return this;
	}

	public String getEmailId() {
		return emailId;
	}

	public UserDetails setEmailId(String emailId) {
		this.emailId = emailId;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public UserDetails setPassword(String password) {
		this.password = password;
		return this;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public UserDetails setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
		return this;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", emailId=" + emailId + ", password=" + password
				+ ", mobileNumber=" + mobileNumber + ",note=";
	}
}
