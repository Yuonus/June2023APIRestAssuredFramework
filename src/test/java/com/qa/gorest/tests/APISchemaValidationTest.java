package com.qa.gorest.tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.User;
import com.qa.gorest.utils.StringUtils;

public class APISchemaValidationTest extends BaseTest{
	
	@BeforeMethod
	public void getUserSetup() {
		restClient = new RestClient(prop, baseURI);
	}
	
	// FOR SCHEMA VALIDATION
	@Test()
	public void createUserAPISchemaTest() {
		User user = new User("Abaseen", StringUtils.getRandomEmailId(), "male", "active"); 
		restClient.post(GOREST_ENDPOINT, "json", user, true, true)
							.then().log().all()
								.assertThat().statusCode(APIHttpStatus.CREATED_201.getCode())
									.and()
										.body(matchesJsonSchemaInClasspath("createuserschema.json"));		
	}

}
