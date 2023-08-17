package com.qa.gorest.tests;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.utils.JsonPathValidator;

import io.restassured.response.Response;

public class CircuitTest extends BaseTest{
	/*		Circuit Test
	 * URL = http://ergast.com/api/f1/2017/circuits.json
	 * Service URL = /api/f1/2017/circuits.json
	 * method: Get
	 * status code: 200
	 * 						NOTE NOTE NOTE NOTE
	 * We cannot run this test directly from here. Because this test doesn't know how we are supplying the parameters to it  
	 * So, we will have to run the test from testng_regression.xml
	 */
	
	@BeforeMethod
	public void getUserSetup() {
		restClient = new RestClient(prop, baseURI);
	}
	
	
	@Test
	public void getCircuitTest( ) {
		Response circuitResponse = restClient.get(CIRCUIT_ENDPOINT + "/2017/circuits.json", false, true);
		int statusCode = circuitResponse.statusCode();
		Assert.assertEquals(statusCode, APIHttpStatus.OK_200.getCode());
		
		JsonPathValidator js = new JsonPathValidator();
		List<String> countryList = js.readList(circuitResponse, "$.MRData.CircuitTable.Circuits[?(@.circuitId == 'shanghai')].Location.country");
		System.out.println(countryList);
		Assert.assertTrue(countryList.contains("China"));
		
		
//					.then().log().all() // We didn't add the then() at RestClient, because the assertions are the responsibility of the testNG, not the utility class.
//						.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
		
		//Using JsonPathValidator is optional in the framework, you can also do your assertions through the chaining method.
	}
}
