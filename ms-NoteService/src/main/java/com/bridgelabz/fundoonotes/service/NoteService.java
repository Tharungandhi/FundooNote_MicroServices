package com.bridgelabz.fundoonotes.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Note;

@Service
public interface NoteService {

	Note createNote(String token,Note user, HttpServletRequest request);
	
	Note updateNote(int id,String token,Note note,HttpServletRequest request);
	
	boolean deleteNote(int id,String token,HttpServletRequest request);
	
	List<Note> retrieveNote(String token,HttpServletRequest request);
	
	
	
	Label createLabel(String token, Label label, HttpServletRequest request);
	
	Label updateLabel(int id,String token, Label label, HttpServletRequest request);
	
	boolean  deleteLabel(int id,String token, HttpServletRequest request);
	
	List<Label> retrieveLabel(String token, HttpServletRequest request);
	
	
	
	boolean mapNoteLabel(String token, int noteId, int labelId, HttpServletRequest request);
   
	boolean removeNoteLabel(String token, int noteId, int labelId, HttpServletRequest request);
	}


