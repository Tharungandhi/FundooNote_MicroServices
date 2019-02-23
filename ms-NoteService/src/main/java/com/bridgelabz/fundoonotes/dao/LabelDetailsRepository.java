package com.bridgelabz.fundoonotes.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.Label;

@Repository
public interface LabelDetailsRepository extends JpaRepository<Label, Integer> {

	List<Label> findAllByUserId(int userId);

	Optional<Label> findByuserIdAndId(int userId,int noteId);


	
}
