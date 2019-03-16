package com.bridgelabz.fundoonotes.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "Note")

public class Note implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "title")
	private String title;

	@Column(name = "discription")
	private String discription;

	@Column(name = "isPinned")
	private boolean isPinned;

	@Column(name = "inTrash")
	private boolean inTrash;

	@Column(name = "updateTime")
	@UpdateTimestamp
	private Timestamp updateTime;

	@Column(name = "userId")
	private int userId;
	
	@Column(name ="colour")
	private String colour;

	@ManyToMany(fetch = FetchType.EAGER, targetEntity = Label.class, cascade = {CascadeType.ALL })
	@JoinTable(name = "Note_Label", joinColumns = { @JoinColumn(name = "noteId") }, inverseJoinColumns = {
			@JoinColumn(name = "labelId") })
	private List<Label> labels;
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "createdTime")
	@UpdateTimestamp
	private Timestamp createdTime;

	@Column(name = "isArchive")
	private boolean isArchive;

	

	public int getId() {
		return id;
	}

	public Note setId(int id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Note setTitle(String title) {
		this.title = title;
		return this;
		
	}

	public String getDiscription() {
		return discription;
	}

	public Note setDiscription(String discription) {
		this.discription = discription;
		return this;
	}

	public boolean isPinned() {
		return isPinned;
	}

	public Note setPinned(boolean isPinned) {
		this.isPinned = isPinned;
		return this;
	}

	public boolean isInTrash() {
		return inTrash;
	}

	public Note setInTrash(boolean inTrash) {
		this.inTrash = inTrash;
		return this;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public boolean isArchive() {
		return isArchive;
	}

	public Note setArchive(boolean isArchive) {
		this.isArchive = isArchive;
		return this;
	}

	public List<Label> getLabels() {
		return labels;
	}

	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}

	public String getColour() {
		return colour;
	}

	public Note setColour(String colour) {
		this.colour = colour;
		return this;
	}

}
