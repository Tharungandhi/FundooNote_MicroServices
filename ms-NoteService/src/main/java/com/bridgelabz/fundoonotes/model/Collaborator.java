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
@Table(name = "Collaborator", uniqueConstraints = @UniqueConstraint(columnNames = { "userId", "id" }))
public class Collaborator {

	
	    @Id
	    @GeneratedValue
	    @Column (name = "colaborateId")
	    private int colaborateId;

	    @Column(name = "userId")
	    private int userId;

	    @Column(name = "id")
	    private int id;

	    @Column(name = "updatedTime")
	    @UpdateTimestamp
	    private Timestamp updatedTime;

	    
	    
		public int getColaborateId() {
			return colaborateId;
		}

		public Collaborator setColaborateId(int colaborateId) {
			this.colaborateId = colaborateId;
			return this;
		}

		public int getUserId() {
			return userId;
		}

		public Collaborator setUserId(int userId) {
			this.userId = userId;
			return this;
		}

		public int getId() {
			return id;
		}

		public Collaborator setId(int id) {
			this.id = id;
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
