package com.nominet.bdd;

/**
 * Top level class for performing integrations and functional tests.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.nominet.NominetApplication;

import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = NominetApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class AbstractSpringTest {
	@Autowired
	public TestRestTemplate restTemplate;

}
