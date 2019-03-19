package com.bridgelabz.fundoonotes.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="Collaborator",uniqueConstraints = @UniqueConstraint(columnNames = { "userId", "noteId" }))
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

	public Collaborator setNoteId(int noteId) {
		this.noteId = noteId;
		return this;
	}

	public int getUserId() {
		return userId;
	}

	public Collaborator setUserId(int userId) {
		this.userId = userId;
		return this;
	}

	public Timestamp getUpdatedTime() {
		return updatedTime;
	}

	public Collaborator setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
		return this;
	}

	
	
}
