package com.nominet.bdd;
/**
 * Hook class to integrate cucumber test frame work with spring boot application.
 */

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources")
public class RunCucumberTest {

}