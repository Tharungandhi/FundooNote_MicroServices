package com.bridgelabz.fundoonotes.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
			Note existingNote=optional.get();
			if(existingNote.getUserId()==userId) {
				noteDetailsRepository.delete(existingNote);
		return existingNote;
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
		
    	    public Label updateLabel(int id,String token, Label label, HttpServletRequest request) {
    	    	int userid = generateToken.verifyToken(token);
    			Optional<Label> optional = labelDetailsRepository.findById(id);
    			if(optional.isPresent())
    			{
    				Label updatedLabel=optional.get();
    				if(updatedLabel.getUserId()==userid) {
    					updatedLabel.setLabelName(label.getLabelName());
                       return updatedLabel;
    				}
    			}
    	    	return null;
    	    	
    	    }

	public List<Label> retrieveLabel(String token, HttpServletRequest request){
		 int userId = generateToken.verifyToken(token);
	        List<Label> labels = labelDetailsRepository.findAllByUserId(userId);
	        if (!labels.isEmpty()) {
	            return labels;
	        }
			return null;}
	
	
	
	public boolean mapNoteLabel(String token, int noteId, int labelId, HttpServletRequest request) {
		 int userId = generateToken.verifyToken(token);
	        Optional<Label> optional=labelDetailsRepository.findById(labelId);
	        Optional<Note> optional1=noteDetailsRepository.findById(noteId);
	        if (optional.isPresent() && optional1.isPresent())
	        {
	            Note note = optional1.get();
	            Label label = optional.get();
	            if(label.getUserId()==userId && note.getUserId()==userId)
	            {
	            List<Label> labels = note.getLabels();
	            labels.add(label);
	            if (!labels.isEmpty())
	            {
	            note.setLabels(labels);
	            noteDetailsRepository.save(note);
	            }
	                return true;
	        }}
			return false;
	}
	
	@Override
    public boolean removeNoteLabel(String token, int noteId, int labelId, HttpServletRequest request) {
        int userId = generateToken.verifyToken(token);
        Optional<Label> optionallabel=labelDetailsRepository.findById(labelId);
        Optional<Note> optionalnote=noteDetailsRepository.findById(noteId);
        if (optionallabel.isPresent() && optionalnote.isPresent()) {   
            Note note = optionalnote.get();
            List<Label> labels = note.getLabels();
            if (!labels.isEmpty()) {
                labels = labels.stream().filter(label -> label.getId() != labelId).collect(Collectors.toList());
                note.setLabels(labels);
                noteDetailsRepository.delete(note);
           
                return true;
            }
        }
        return false;
    }
	
	
}
	



