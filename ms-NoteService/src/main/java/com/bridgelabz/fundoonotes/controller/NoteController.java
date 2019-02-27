package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.service.NoteService;

@Controller
@RequestMapping("/note")
public class NoteController {


	@Autowired
	private NoteService noteService;

	@PostMapping(value = "/createnote")
	public ResponseEntity<String> createNote( @RequestHeader("token") String token,@RequestBody Note note, HttpServletRequest request) {

		Note newNote=noteService.createNote(token, note, request);
		if (newNote!=null) {
			return new ResponseEntity<String>("Note Succesfully Created",HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Denied In Creation of Note",HttpStatus.BAD_REQUEST);
		}
	}
	@PostMapping(value = "/updatenote")
	public ResponseEntity<String> updateNote(@RequestParam("id") int id, @RequestHeader("token") String token,@RequestBody Note note, HttpServletRequest request)
	{
		Note updateNote=noteService.updateNote(id,token,note, request);
		if (updateNote!=null) {
			return new ResponseEntity<String>("Note Succesfully updated",HttpStatus.OK);
		} else
			return new ResponseEntity<String>("Note not Found by given  Id",HttpStatus.BAD_REQUEST);
	}


	@DeleteMapping(value="/deletenote")
	public ResponseEntity<String> deleteNote(@RequestParam("id") int id ,@RequestHeader("token") String token,HttpServletRequest request)
	{
		boolean deleteNote=noteService.deleteNote(id,token,request);
		if (deleteNote!=false) {
			return new ResponseEntity<String>("Note Succesfully deleted",HttpStatus.FOUND);
		} else {
			return  new ResponseEntity<String>("Note not Found by given  Id",HttpStatus.NOT_FOUND);
		}        
	}

	@GetMapping(value = "/retrievenote")
	public ResponseEntity<?> retrieveNote( @RequestHeader("token") String token,HttpServletRequest request) {
		List<Note> listOfNote = noteService.retrieveNote(token,request);
//		System.out.println("we are in");
		if (!listOfNote.isEmpty()) {
//			System.out.println("note present");
			return new ResponseEntity<List<Note>>(listOfNote, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/createlabel")
	public ResponseEntity<?> createLabel( @RequestHeader("token") String token, @RequestBody Label label, HttpServletRequest request) {
		Label newlabel=noteService.createLabel(token,label, request);
		if (newlabel!=null) {
			return new ResponseEntity<String>("Label Succesfully Created",HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Dinied In Creation of Label",HttpStatus.NOT_FOUND);
		}}

	@DeleteMapping(value="/deletelabel")
	public ResponseEntity<String> deleteLabel(@RequestParam("id") int id , @RequestHeader("token") String token,HttpServletRequest request)
	{
		try {
			if (noteService.deleteLabel(id,token,request)!=false)
				return new ResponseEntity<String>("Label Succesfully deleted",HttpStatus.FOUND);
			else
				return  new ResponseEntity<String>("Label not Found by given  Id",HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			e.printStackTrace();

		}
		return new ResponseEntity<String>("Pls provide details correctly",HttpStatus.CONFLICT);
	}	
	@PostMapping(value = "/updatelabel")
	public ResponseEntity<String> updateLabel(@RequestParam("id") int id, @RequestHeader("token") String token,@RequestBody Label label, HttpServletRequest request)
	{

		if (noteService.updateLabel(id,token,label, request)!=null) {
			return new ResponseEntity<String>("Note Succesfully updated",HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Note not Found by given  Id",HttpStatus.NOT_FOUND);
		} }

	@GetMapping(value = "/retrievelabel")
	public ResponseEntity<?> retrieveLabel(@RequestHeader("token") String token, HttpServletRequest request) {
		List<Label> listOfLabel = noteService.retrieveLabel(token, request);
		if (!listOfLabel.isEmpty()) {
			return new ResponseEntity<List<Label>>(listOfLabel, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<String>("ID incorrect. Please enter valid ID present in database",
					HttpStatus.NOT_FOUND);
		}
	}


	@PutMapping(value = "/mapnotelabel")
	public ResponseEntity<?> mapNoteLabel(@RequestParam("noteId") int noteId ,@RequestParam("labelId") int labelId ,@RequestHeader("token") String token, HttpServletRequest request) {

		if (noteService.mapNoteLabel(token,noteId,labelId, request)!=false) {
			return new ResponseEntity<String>("Mapped Successfully", HttpStatus.FOUND);
		}else {

			return new ResponseEntity<String>("Dinied In Mapping ",HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping(value="/deletenotelabel")
	public ResponseEntity<?> deleteNoteLabel(@RequestHeader("token") String token,@RequestParam("noteId") int noteId,@RequestParam("labelId") int labelId ,HttpServletRequest request){

		if(noteService.removeNoteLabel(token, noteId, labelId, request))	{
			return new ResponseEntity<String>("NoteLabel Deleted Successfully", HttpStatus.FOUND);

		}else
		{
			return new ResponseEntity<String>("Dinied In Deleting NoteLabel",HttpStatus.NOT_FOUND);
		}

	}
	
}





