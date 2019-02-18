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
	
	Note deleteNote(int id,String token,HttpServletRequest request);
	
	List<Note> retrieveNote(String token,HttpServletRequest request);
	
	
	
	Label createLabel(String token, Label label, HttpServletRequest request);
	
	Label  deleteLabel(int id,String token, HttpServletRequest request);
}