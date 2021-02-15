package com.nominet.ft;

/**
 * Functional test cases.
 */
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.nominet.bdd.AbstractSpringTest;
import com.nominet.dto.NoteDTO;

public class NotesFunctionalTest extends AbstractSpringTest {
	@Autowired
	private TestRestTemplate restTemplate;

	private final String BASE_URI = "http://localhost";
	private final String CONTEXT_PATH = "/notes";

	private String contextURI;
	private HttpHeaders headers;

	@BeforeEach
	public void init() {
		contextURI = BASE_URI + ":" + 8082 + CONTEXT_PATH;
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		NoteDTO noteDTO = new NoteDTO();
		noteDTO.setText("My First Note");
		HttpEntity<NoteDTO> request = new HttpEntity<>(noteDTO, headers);

		restTemplate.postForEntity(contextURI, request, NoteDTO.class);
	}

	@Test
	public void testCreateNote() {
		NoteDTO noteDTO = new NoteDTO();
		noteDTO.setText("My Second Note");
		HttpEntity<NoteDTO> request = new HttpEntity<>(noteDTO, headers);

		ResponseEntity<NoteDTO> savedNoteResponse = restTemplate
				.postForEntity(contextURI, request, NoteDTO.class);

		assertThat(savedNoteResponse).isNotNull();
		assertEquals(HttpStatus.CREATED, savedNoteResponse.getStatusCode());

		NoteDTO savedNoteDTO = savedNoteResponse.getBody();
		assertThat(savedNoteDTO).isNotNull();
		assertThat(savedNoteDTO.getId()).isGreaterThan(1l);
		assertEquals("My Second Note", savedNoteDTO.getText());
	}

	@Test
	public void testGetNoteById() {
		ResponseEntity<NoteDTO> getNoteResponse = restTemplate
				.getForEntity(contextURI + "/1", NoteDTO.class);

		assertThat(getNoteResponse).isNotNull();
		assertEquals(HttpStatus.OK, getNoteResponse.getStatusCode());

		NoteDTO retrievedNoteDTO = getNoteResponse.getBody();
		assertThat(retrievedNoteDTO).isNotNull();
		assertThat(retrievedNoteDTO.getId()).isEqualTo(1l);
		assertEquals("My First Note", retrievedNoteDTO.getText());
	}

	@Test
	public void testGetAllNotes() {
		ResponseEntity<Object> notesResponse = restTemplate
				.getForEntity(contextURI, Object.class);
		assertThat(notesResponse).isNotNull();
		assertEquals(HttpStatus.OK, notesResponse.getStatusCode());
	}

	@Test
	public void testUpdateNote()
	{
		ResponseEntity<NoteDTO> getNoteResponse = restTemplate
				.getForEntity(contextURI + "/1", NoteDTO.class);
		NoteDTO retrievedNoteDTO = getNoteResponse.getBody();
		retrievedNoteDTO.setText("My First Note - Modified");
		HttpEntity<NoteDTO> noteEntity = new HttpEntity(retrievedNoteDTO);

		ResponseEntity<NoteDTO> updatedNoteResponse = restTemplate.exchange(
				contextURI + "/1", HttpMethod.PUT, noteEntity,
				NoteDTO.class);

		assertThat(updatedNoteResponse).isNotNull();
		assertEquals(HttpStatus.OK, updatedNoteResponse.getStatusCode());
		
		ResponseEntity<NoteDTO> retrievedNote2Response = restTemplate
				.getForEntity(contextURI + "/1", NoteDTO.class);
		NoteDTO retrievedNoteDTO2 = retrievedNote2Response.getBody();
		assertEquals("My First Note - Modified", retrievedNoteDTO2.getText());

	}
	
	@Test
	public void testDeleteNote() {
		NoteDTO noteDTO = new NoteDTO();
		noteDTO.setText("My Second Note");
		HttpEntity<NoteDTO> request = new HttpEntity<>(noteDTO, headers);

		ResponseEntity<NoteDTO> savedNoteResponse = restTemplate
				.postForEntity(contextURI, request, NoteDTO.class);

		restTemplate.delete(contextURI + "/1");

		ResponseEntity<NoteDTO> retrievedNote2Response = restTemplate
				.getForEntity(contextURI + "/1", NoteDTO.class);
		assertEquals(HttpStatus.NOT_FOUND,
				retrievedNote2Response.getStatusCode());
	}

}
