package com.bridgelabz.fundoonotes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoonotes.model.Images;

public interface ImageRepository extends JpaRepository<Images, Integer> {

}
