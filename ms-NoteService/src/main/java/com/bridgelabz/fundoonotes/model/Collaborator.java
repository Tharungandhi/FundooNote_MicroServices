package com.bridgelabz.fundoonotes.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="Collaborator")
public class Collaborator {

	@Id
	@GeneratedValue
	@Column(name="collaboratorId")
	private int collaboratorId;
	
	@Column(name="noteId")
	private int noteId;
	
	@Column(name="userId")
	private int userId;
	
	@Column(name="updatedTime")
	@UpdateTimestamp
	private Timestamp updatedTime;

	public int getCollaboratorId() {
		return collaboratorId;
	}

	public void setCollaboratorId(int collaboratorId) {
		this.collaboratorId = collaboratorId;
	}

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Timestamp getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}

	
	
}
