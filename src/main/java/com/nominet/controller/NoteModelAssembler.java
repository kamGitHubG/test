package com.nominet.controller;

/**
 * Helper Method to encapsulate HATEOAS links within the response object.
 */

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.nominet.dto.NoteDTO;

@Component
public class NoteModelAssembler
		implements
			RepresentationModelAssembler<NoteDTO, EntityModel<NoteDTO>> {

	@Override
	public EntityModel<NoteDTO> toModel(NoteDTO noteDTO) {
		return EntityModel.of(noteDTO,
				WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder.methodOn(NoteController.class)
								.getNote(noteDTO.getId()))
						.withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
						.methodOn(NoteController.class).getNotes())
						.withRel("notes"));
	}
}
