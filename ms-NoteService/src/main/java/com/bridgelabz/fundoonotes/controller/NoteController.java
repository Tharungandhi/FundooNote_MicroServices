package com.bridgelabz.fundoonotes.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.service.NoteService;

@Controller
@RequestMapping("/note")
public class NoteController {


	@Autowired
	private NoteService noteService;

	@PostMapping(value = "/createnote")
	public ResponseEntity<?> createNote( @RequestHeader("token") String token,@RequestBody Note note, HttpServletRequest request) {

		Note newNote=noteService.createNote(token, note, request);
		if (newNote!=null) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Denied In Creation of Note",HttpStatus.BAD_REQUEST);
		}
	}
	@PutMapping(value = "/updatenote/{id:.+}")
	public ResponseEntity<?> updateNote(@PathVariable("id") int id, @RequestHeader("token") String token,@RequestBody Note note, HttpServletRequest request)
	{
		Note updateNote=noteService.updateNote(id,token,note, request);
		if (updateNote!=null) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else
			return new ResponseEntity<String>("Note not Found by given  Id",HttpStatus.BAD_REQUEST);
	}


	@DeleteMapping(value="/deletenote/{id:.+}")
	public ResponseEntity<?> deleteNote(@PathVariable("id") int id ,@RequestHeader("token") String token,HttpServletRequest request)
	{
		boolean deleteNote=noteService.deleteNote(id,token,request);
		if (deleteNote!=false) {
			return new ResponseEntity<Void>(HttpStatus.OK);
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
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}}

	@DeleteMapping(value="/deletelabel/{id:.+}")
	public ResponseEntity<?> deleteLabel(@PathVariable("id") int id , @RequestHeader("token") String token,HttpServletRequest request)
	{
		
			if (noteService.deleteLabel(id,token,request)!=false)
				return new ResponseEntity<Void>(HttpStatus.OK);
			else
				return  new ResponseEntity<Void>(HttpStatus.NOT_FOUND);

		}
	
	@PutMapping(value = "/updatelabel/{id:.+}")
	public ResponseEntity<?> updateLabel(@PathVariable("id") int id, @RequestHeader("token") String token,@RequestBody Label label, HttpServletRequest request)
	{

		if (noteService.updateLabel(id,token,label, request)!=null) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Note not Found by given  Id",HttpStatus.NOT_FOUND);
		} }

	@GetMapping(value = "/retrievelabel")
	public ResponseEntity<?> retrieveLabel(@RequestHeader("token") String token, HttpServletRequest request) {
		List<Label> listOfLabel = noteService.retrieveLabel(token, request);
		if (!listOfLabel.isEmpty()) {
			return new ResponseEntity<List<Label>>(listOfLabel, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("ID incorrect. Please enter valid ID present in database",
					HttpStatus.NOT_FOUND);
		}
	}


	@PutMapping(value = "/mapnotelabel/{noteId:.+}")
	public ResponseEntity<?> mapNoteLabel(@PathVariable("noteId") int noteId ,@RequestBody Label label , HttpServletRequest request) {

		if (noteService.mapNoteLabel(noteId,label, request)!=false) {
			return new ResponseEntity<Void>( HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("Dinied In Mapping ",HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping(value="/deletenotelabel")
	public ResponseEntity<?> deleteNoteLabel(@RequestParam("noteId") int noteId,@RequestParam("labelId") int labelId ,HttpServletRequest request){

		if(noteService.removeNoteLabel( noteId, labelId, request))	{
			return new ResponseEntity<Void>( HttpStatus.FOUND);

		}else
		{
			return new ResponseEntity<String>("Dinied In Deleting NoteLabel",HttpStatus.NOT_FOUND);
		}

	}
	
	
	@PostMapping(value = "createcollaborator/{id}/{userId}")
	public ResponseEntity<?> createCollaborator(@RequestHeader("token") String token, @PathVariable("noteId") int id,
			@PathVariable("userId") int userId,HttpServletRequest request) {
		if (noteService.createCollaborator(token, id, userId))
			return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<String>("There was a issue raised cannot create a collaborator", HttpStatus.CONFLICT);
	}
	
	@DeleteMapping("removecollaborator/{userId}/{noteId}")
    public ResponseEntity<?> removeCollaborator(@PathVariable("userId") int userId,
    		@PathVariable("noteId") int noteId) {
        if(noteService.removeCollaborator(userId,noteId))
			return new ResponseEntity<Void>(HttpStatus.OK);
        return new ResponseEntity<String>("Couldnot delete the image", HttpStatus.CONFLICT);
}
	
	@PostMapping("/uploadimage/{token:.+}")
	public ResponseEntity<?> uploadFile(@PathVariable ("token")String token,@RequestParam ("file")
	MultipartFile  imageUpload ) throws IOException {	      
		if( noteService.uploadImage(token,imageUpload)!=null)
			return new ResponseEntity<String>("Successfully uploaded", HttpStatus.OK);

		else
			return new ResponseEntity<String>("Something went wrong",HttpStatus.NOT_FOUND);

}
	
	@GetMapping("uploadimage")
    public ResponseEntity<?> downloadFile(@RequestHeader("token") String token) {
        Note note = noteService.getImage(token);
        if(note!=null)
			return new ResponseEntity<Note>(note,HttpStatus.OK);
        return new ResponseEntity<String>("Couldnot download the image", HttpStatus.CONFLICT);
}
	
	@DeleteMapping("uploadimage")
    public ResponseEntity<?> deleteFile(@RequestHeader("token") String token) {
		Note user = noteService.deleteImage(token);
        if(user!=null)
			return new ResponseEntity<Void>(HttpStatus.OK);
        return new ResponseEntity<String>("Couldnot delete the image", HttpStatus.CONFLICT);
}
}





