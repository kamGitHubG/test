package com.nominet.controller;

/**
 * Controller class exposing various API endpoints.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nominet.dto.NoteDTO;
import com.nominet.dto.NoteMapper;
import com.nominet.model.Note;
import com.nominet.service.INotesService;

@RestController
public class NoteController {
	@Autowired
	private INotesService notesService;

	@Autowired
	private NoteModelAssembler noteModelAssembler;

	@GetMapping("/")
	@ResponseStatus(HttpStatus.OK)
	public CollectionModel<EntityModel<NoteDTO>> getHome()
	{
		return CollectionModel.of(new ArrayList<EntityModel<NoteDTO>>(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
						.methodOn(NoteController.class).getNotes())
						.withRel("notes"));
	}
	
	@GetMapping("/notes/{noteId}")
	@ResponseStatus(HttpStatus.OK)
	public EntityModel<NoteDTO> getNote(@PathVariable Long noteId) {
		Note note = notesService.findById(noteId);
		return noteModelAssembler.toModel(NoteMapper.toDTO(note));
	}

	@GetMapping("/notes")
	@ResponseStatus(HttpStatus.OK)
	public CollectionModel<EntityModel<NoteDTO>> getNotes() {
		List<EntityModel<NoteDTO>> noteEntityModelList = notesService
				.findAll().stream().map(NoteMapper::toDTO)
				.map(noteModelAssembler::toModel).collect(Collectors.toList());
				
		return CollectionModel.of(noteEntityModelList,
				WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder
								.methodOn(NoteController.class).getNotes())
						.withSelfRel());
	}

	@PostMapping("/notes")
	@ResponseStatus(HttpStatus.CREATED)
	public EntityModel<NoteDTO> saveNote(@RequestBody NoteDTO noteDTO) {
		Note note = notesService.saveNote(noteDTO);
		return noteModelAssembler.toModel(NoteMapper.toDTO(note));
	}

	@PutMapping("/notes/{noteId}")
	@ResponseStatus(HttpStatus.OK)
	public EntityModel<NoteDTO> updateNote(@PathVariable Long noteId,
			@RequestBody NoteDTO noteDTO) {
		Note note = notesService.updateNote(noteId, noteDTO);
		return noteModelAssembler.toModel(NoteMapper.toDTO(note));
	}

	@DeleteMapping("/notes/{noteId}")
	@ResponseStatus(HttpStatus.OK)
	public CollectionModel<EntityModel<NoteDTO>> deleteNote(
			@PathVariable Long noteId) {
		notesService.deleteNote(noteId);
		return CollectionModel.of(new ArrayList<EntityModel<NoteDTO>>(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
						.methodOn(NoteController.class).getNotes())
				.withRel("notes"));
	}

}
