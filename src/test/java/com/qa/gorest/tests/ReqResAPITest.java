package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;

public class ReqResAPITest extends BaseTest{
	/*		Reqres Test
	 * URL = https://reqres.in/api/users/2
	 * Service URL = /api/users/2
	 * method: Get
	 * status code: 200
	 */
	
	@BeforeMethod
	public void getUserSetup() {
		restClient = new RestClient(prop, baseURI);
	}
	
	@Test
	public void getReResTest() {
		restClient.get(REQRES_ENDPOINT + "/2", false, true)
					.then().log().all() // We didn't add the then() at RestClient, because the assertions are the responsibility of the testNG, not the utility class.
						.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
	}

}
