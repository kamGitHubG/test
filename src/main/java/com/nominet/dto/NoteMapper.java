package com.nominet.dto;

/**
 * Helper method to convert Entity to DTO object and vice versa.
 */
import com.nominet.model.Note;

public class NoteMapper {

	private NoteMapper() {
		super();
	}

	public static NoteDTO toDTO(Note note) {
		return NoteDTO.builder().id(note.getId())
				.text(note.getText()).build();
	}

	public static Note toModel(NoteDTO noteDTO) {
		return Note.builder().id(noteDTO.getId()).text(noteDTO.getText())
				.build();
	}

}
