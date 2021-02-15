package com.nominet.service;

/**
 * Interface for service methods for performing business logic on note entity.
 */

import java.util.List;

import com.nominet.dto.NoteDTO;
import com.nominet.exception.NominetRuntimeException;
import com.nominet.model.Note;

public interface INotesService {

	public Note findById(Long noteId) throws NominetRuntimeException;
	public List<Note> findAll();
	public Note saveNote(NoteDTO noteDTO);
	public Note updateNote(Long noteId, NoteDTO noteDTO);
	public void deleteNote(Long noteId);

}
