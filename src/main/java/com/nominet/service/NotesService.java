package com.nominet.service;

/**
 * Implementation of the service interface to perform business logic on Notes.
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nominet.dto.NoteDTO;
import com.nominet.dto.NoteMapper;
import com.nominet.exception.ErrorCode;
import com.nominet.exception.NominetRuntimeException;
import com.nominet.model.Note;
import com.nominet.model.NoteRepository;

@Service
public class NotesService implements INotesService {

	@Autowired
	private NoteRepository noteRepository;

	@Override
	public Note findById(Long noteId) throws NominetRuntimeException {
		return noteRepository.findById(noteId)
				.orElseThrow(() -> new NominetRuntimeException(
						ErrorCode.NOT_FOUND,
						String.format("No note found with id {%d}", noteId)));
	}

	@Override
	public List<Note> findAll() {
		return noteRepository.findAll();
	}

	@Override
	public Note saveNote(NoteDTO noteDTO)
	{
		Note note = NoteMapper.toModel(noteDTO);
		note = noteRepository.save(note);
		return note;
	}

	@Override
	public Note updateNote(Long noteId, NoteDTO noteDTO) {
		Note note = this.findById(noteId);
		note.setText(noteDTO.getText());
		note = noteRepository.save(note);
		return note;
	}

	@Override
	public void deleteNote(Long noteId) {
		Note note = this.findById(noteId);
		noteRepository.delete(note);
	}


}
