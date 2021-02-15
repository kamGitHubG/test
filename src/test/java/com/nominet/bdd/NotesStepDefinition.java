package com.nominet.bdd;

/**
 * Class containing step definitions for cucumber feature file.
 */
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.nominet.dto.NoteDTO;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class NotesStepDefinition extends AbstractSpringTest {

	private static final String BASE_URL = "http://localhost:8082/notes";

	ResponseEntity<NoteDTO> responseEntity;
	Long deletedNoteId = null;

	@When("^a user posts a note$")
	public void testCreateNote() {
		responseEntity = null;
		createNote();
		assertThat(responseEntity).isNotNull();
	}

	private void createNote() {
		NoteDTO noteDTO = new NoteDTO();
		noteDTO.setText("My First Note");
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<NoteDTO> request = new HttpEntity<>(noteDTO, headers);

		responseEntity = null;
		responseEntity = restTemplate.postForEntity(
				BASE_URL, request, NoteDTO.class);
	}

	@Then("^the user receives status code of (\\d+)$")
	public void verifyHttpStatusCode(int expectedHttpStatusCode) {
		assertEquals(responseEntity.getStatusCodeValue(),
				expectedHttpStatusCode);

	}

	@And("the note is successfully saved")
	public void verifyCreatedNote() {
		Long noteId = responseEntity.getBody().getId();
		ResponseEntity<NoteDTO> getNoteResponse = restTemplate
				.getForEntity(BASE_URL + "/" + String.valueOf(noteId),
						NoteDTO.class);

		assertThat(getNoteResponse).isNotNull();
		assertEquals(HttpStatus.OK, getNoteResponse.getStatusCode());

		NoteDTO retrievedNoteDTO = getNoteResponse.getBody();
		assertThat(retrievedNoteDTO).isNotNull();
		assertThat(retrievedNoteDTO.getId()).isEqualTo(noteId);
		assertEquals("My First Note", retrievedNoteDTO.getText());
	}

	@When("^a user attempts to retrieve a note by providing id$")
	public void testGetNoteById() {
		responseEntity = null;
		createNote();
		Long noteId = responseEntity.getBody().getId();
		responseEntity = restTemplate
				.getForEntity(BASE_URL + "/" + noteId, NoteDTO.class);

		assertThat(responseEntity).isNotNull();

	}

	@And("^the note is successfully retrieved$")
	public void verifyRetrievedNote() {
		NoteDTO retrievedNoteDTO = responseEntity.getBody();
		assertThat(retrievedNoteDTO).isNotNull();
		assertThat(retrievedNoteDTO.getId()).isGreaterThan(0l);
		assertEquals("My First Note", retrievedNoteDTO.getText());
	}

	@And("^then updates the note text with (.+)$")
	public void testUpdateNote(String newText) {
		NoteDTO retrievedNoteDTO = responseEntity.getBody();
		retrievedNoteDTO.setText(newText);
		Long noteId = retrievedNoteDTO.getId();
		HttpEntity<NoteDTO> noteEntity = new HttpEntity(retrievedNoteDTO);
		responseEntity = null;
		responseEntity = restTemplate.exchange(
				BASE_URL + "/" + noteId, HttpMethod.PUT, noteEntity,
				NoteDTO.class);
		assertThat(responseEntity).isNotNull();
	}

	@And("^the note is successfully updated$")
	public void verifyNoteUpdated() {
		Long noteId = responseEntity.getBody().getId();
		ResponseEntity<NoteDTO> getNoteResponse = restTemplate
				.getForEntity(BASE_URL + "/" + noteId, NoteDTO.class);

		assertThat(getNoteResponse).isNotNull();
		assertEquals(HttpStatus.OK, getNoteResponse.getStatusCode());

		NoteDTO retrievedNoteDTO = getNoteResponse.getBody();
		assertThat(retrievedNoteDTO).isNotNull();
		assertThat(retrievedNoteDTO.getId()).isEqualTo(noteId);
		assertEquals("My Note Text-Modified", retrievedNoteDTO.getText());
	}


	@When("a user attempts to delete a note by providing id$")
	public void testDeleteNote() {
		createNote();
		deletedNoteId = responseEntity.getBody().getId();
		HttpEntity<NoteDTO> noteEntity = new HttpEntity(new NoteDTO());
		responseEntity = null;
		responseEntity = restTemplate.exchange(BASE_URL + "/" + deletedNoteId,
				HttpMethod.DELETE,
				noteEntity, NoteDTO.class);
		assertThat(responseEntity).isNotNull();

	}

	@And("^the note is successfully deleted$")
	public void verifyNoteDeleted() {
		ResponseEntity<NoteDTO> retrievedNote2Response = restTemplate
				.getForEntity(BASE_URL + "/" + deletedNoteId, NoteDTO.class);
		assertEquals(HttpStatus.NOT_FOUND,
				retrievedNote2Response.getStatusCode());
	}

}
