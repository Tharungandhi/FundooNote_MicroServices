package com.bridgelabz.fundoonotes.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	
	
	
	boolean mapNoteLabel( int noteId, Label label, HttpServletRequest request);
   
	boolean removeNoteLabel( int noteId, int labelId, HttpServletRequest request);
	
	boolean createCollaborator(String token, int noteId, int userId);
	
	boolean removeCollaborator(int userId, int noteId);
	 
	 boolean addNoteImage(MultipartFile file, int noteId) throws IOException;
	 
	 boolean deleteFile(int imagesId);
	}


