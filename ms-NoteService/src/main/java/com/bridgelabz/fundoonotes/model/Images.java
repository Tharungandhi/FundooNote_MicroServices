package com.bridgelabz.fundoonotes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
@Entity
@Table(name="image")
public class Images {

	@Id
	@GeneratedValue
	@Column(name = "imagesId")
	private int imagesId;

	@Column(name = "id")
	private int id;

	@Lob
	@Column(name = "images")
    private byte[] images;

	public int getImagesId() {
		return imagesId;
	}

	public Images setImagesId(int imagesId) {
		this.imagesId = imagesId;
		return this;
	}

	

	public int getId() {
		return id;
	}

	public Images setId(int id) {
		this.id = id;
		return this;
	}

	public byte[] getImages() {
		return images;
	}

	public Images setImages(byte[] images) {
		this.images = images;
		return this;
	}
	
	
}
