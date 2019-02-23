package com.bridgelabz.fundoonotes.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.Note;

@Repository
public interface NoteDetailsRepository extends JpaRepository<Note, Integer> {

	List<Note> findAllByUserId(int userId);
	
	Optional<Note> findByuserIdAndId(int userId,int noteId);


	
}
