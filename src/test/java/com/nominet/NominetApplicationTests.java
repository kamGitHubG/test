package com.nominet;

/**
 * Test class for testing context loading.
 */

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.nominet.bdd.AbstractSpringTest;
import com.nominet.controller.NoteController;

class NominetApplicationTests extends AbstractSpringTest {
	@Autowired
	private NoteController noteController;

	@Test
	void contextLoads() {
		assertThat(noteController).isNotNull();
	}

}
