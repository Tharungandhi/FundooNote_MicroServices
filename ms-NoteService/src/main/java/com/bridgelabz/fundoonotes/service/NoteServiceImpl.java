package com.bridgelabz.fundoonotes.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dao.CollaboratorRepository;
import com.bridgelabz.fundoonotes.dao.LabelDetailsRepository;
import com.bridgelabz.fundoonotes.dao.NoteDetailsRepository;
import com.bridgelabz.fundoonotes.model.Collaborator;
import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.util.EmailUtil;
import com.bridgelabz.fundoonotes.util.GenerateTokenImpl;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	GenerateTokenImpl generateToken;

	@Autowired
	NoteDetailsRepository noteDetailsRepository;

	@Autowired
	LabelDetailsRepository labelDetailsRepository;
	
	@Autowired
	CollaboratorRepository collaboratorRepository;
	
	@Autowired
	EmailUtil emailUtil;

	public Note createNote(String token, Note note, HttpServletRequest request) {
		int id = generateToken.verifyToken(token);
		note.setUserId(id);
		return noteDetailsRepository.save(note);

	}

	public Note updateNote(int id, String token, Note note, HttpServletRequest request) {
		int userId = generateToken.verifyToken(token);
		Optional<Note> optional = noteDetailsRepository.findByuserIdAndId(userId, id);
		return optional
				.map(existingNote -> noteDetailsRepository
						.save(existingNote.setTitle(note.getTitle()).setDiscription(note.getDiscription())
								.setArchive(note.isArchive()).setInTrash(note.isInTrash()).setColor(note.getColor()).setReminder(note.getReminder()).setPinned(note.isPinned())))
				.orElseGet(() -> null);
	}

	public boolean deleteNote(int id, String token, HttpServletRequest request) {
		int userId = generateToken.verifyToken(token);
		Optional<Note> optional = noteDetailsRepository.findByuserIdAndId(userId, id);
		return optional.map(note -> {
			noteDetailsRepository.delete(note);
			return true;
		}).orElseGet(() -> false);

	}

	public List<Note> retrieveNote(String token, HttpServletRequest request) {
		int userId = generateToken.verifyToken(token);
		List<Note> notes=new ArrayList<>();
		List<Collaborator> collaborators=collaboratorRepository.findAllByUserId(userId);
		for(Collaborator collaborator:collaborators)
		{
			notes.add(noteDetailsRepository.findById(collaborator.getNoteId()).get());
		}
		List<Note> newNotes = noteDetailsRepository.findAllByUserId(userId);
		notes.addAll(newNotes);
		return notes;
	}

	public Label createLabel(String token, Label label, HttpServletRequest request) {
		int userId = generateToken.verifyToken(token);
		label.setUserId(userId);
		return labelDetailsRepository.save(label);
	}

	public boolean deleteLabel(int id, String token, HttpServletRequest request) {
		int userId = generateToken.verifyToken(token);
		Optional<Label> optional = labelDetailsRepository.findByuserIdAndId(userId, id);
		return optional.map(label -> {
			labelDetailsRepository.delete(label);
			return true;
		}).orElseGet(() -> false);

	}

	public Label updateLabel(int id, String token, Label label, HttpServletRequest request) {
		int userId = generateToken.verifyToken(token);
		Optional<Label> optional = labelDetailsRepository.findByuserIdAndId(userId, id);
		return optional
				.map(existingLabel -> labelDetailsRepository.save(existingLabel.setLabelName(label.getLabelName())))
				.orElseGet(() -> null);
	}

	public List<Label> retrieveLabel(String token, HttpServletRequest request) {
		int userId = generateToken.verifyToken(token);
		List<Label> labels = labelDetailsRepository.findAllByUserId(userId);
		if (!labels.isEmpty()) {
			return labels;
		}
		return null;
	}

	public boolean mapNoteLabel(int noteId, Label existingLabel, HttpServletRequest request) {
		Optional<Label> optional1 = labelDetailsRepository.findById(existingLabel.getId());
		Optional<Note> optional2 = noteDetailsRepository.findById(noteId);
		if (optional1.isPresent() && optional1.isPresent()) {
			Note note = optional2.get();
			Label label = optional1.get();
			if (label != null && note != null) {
				List<Label> labels = note.getLabels();
				labels.add(label);
				if (!labels.isEmpty()) {
					note.setLabels(labels);
					noteDetailsRepository.save(note);
					return true;
				}
			}
		}
		return false;
	}

	public boolean removeNoteLabel( int noteId, int labelId, HttpServletRequest request) {
		Optional<Label> optionallabel = labelDetailsRepository.findById(labelId);
		Optional<Note> optionalnote = noteDetailsRepository.findById(noteId);
		if (optionallabel.isPresent() && optionalnote.isPresent()) {
			Note note = optionalnote.get();
			List<Label> labels = note.getLabels();
			if (!labels.isEmpty()) {
				labels = labels.stream().filter(label -> label.getId() != labelId).collect(Collectors.toList());
				note.setLabels(labels);
				noteDetailsRepository.save(note);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean createCollaborator(String token, int noteId, int userId) {
		Collaborator collaborator = new Collaborator();
		collaborator = collaboratorRepository.save(collaborator.setNoteId(noteId).setUserId(userId));
		if (collaborator != null) {
			emailUtil.sendEmail("","Note has been added", "");
			return true;
	}
		return false;
		}

	@Override
	public boolean removeCollaborator(int userId, int noteId) {
		Collaborator collaborator = collaboratorRepository.findByNoteIdAndUserId(noteId, userId).get();
		if (collaborator != null) {
			collaboratorRepository.delete(collaborator);
			return true;
		}
		return false;
}

}
