package com.bridgelabz.fundoonotes.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dao.LabelDetailsRepository;
import com.bridgelabz.fundoonotes.dao.NoteDetailsRepository;
import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.util.GenerateTokenImpl;

@Service
public class NoteServiceImpl implements NoteService {

	
	@Autowired
	GenerateTokenImpl generateToken;
	
	@Autowired
	NoteDetailsRepository noteDetailsRepository;
	
	@Autowired
	LabelDetailsRepository labelDetailsRepository;
	
	public Note createNote(String token,Note note, HttpServletRequest request) {
		int id = generateToken.verifyToken(token);
		note.setUserId(id);
		return  noteDetailsRepository.save(note);	
		
	}

	
	public Note updateNote(int id, String token, Note note, HttpServletRequest request) {
		int userid = generateToken.verifyToken(token);
		Optional<Note> optional = noteDetailsRepository.findById(id);
		if(optional.isPresent())
		{
			Note updatedNote=optional.get();
			if(updatedNote.getUserId()==userid) {
			updatedNote.setTitle(note.getTitle());
			updatedNote.setDiscription(note.getDiscription());
			noteDetailsRepository.save(updatedNote);	
			return updatedNote;
		}}
		return null;
	}
	
	public Note deleteNote(int id,String token,HttpServletRequest request) {
		int userId = generateToken.verifyToken(token);
		Optional<Note> optional = noteDetailsRepository.findById(id);
		if(optional.isPresent()) {
			Note updatedNote=optional.get();
			if(updatedNote.getUserId()==userId) {
				noteDetailsRepository.delete(updatedNote);
		return updatedNote;
	}}
		return null;}
	
    	    public List<Note> retrieveNote(String token, HttpServletRequest request) {
    	        int userId = generateToken.verifyToken(token);
    	        List<Note> notes = noteDetailsRepository.findAllByUserId(userId);
    	        if (!notes.isEmpty()) {
    	            return notes;
    	        }
    	        return null;
    	    }
	
	
    	    
    	    public Label createLabel(String token, Label label, HttpServletRequest request) {
    			  int userId = generateToken.verifyToken(token);
    			  label.setUserId(userId);
    			  return labelDetailsRepository.save(label);
    		}
    	    
    	    public  Label  deleteLabel(int id,String token, HttpServletRequest request) {
    	    	 int userId = generateToken.verifyToken(token);
    	    	 Optional<Label> optional = labelDetailsRepository.findById(id);
    	 		if(optional.isPresent()) {
    	 			Label deletedLabel=optional.get();
    	 			if(deletedLabel.getUserId()==userId) {
    	 				labelDetailsRepository.delete(deletedLabel);
    	 				return deletedLabel;
    	 			}	}	
    	    	return null;
    	    	
    	    }
		

	
	
}


