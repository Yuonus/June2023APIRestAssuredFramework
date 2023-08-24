package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;

import io.qameta.allure.Description;

public class GetUserTest extends BaseTest{
	/*		Verify if the RestClient utility is working or not?
	 * --> Get all the users
	 * --> URL = https://gorest.co.in/public/v2/users
	 * --> Method = Get
	 * --> Authorization = Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e
	 * --> Status code = 200
	 * 
	 * To access the methods of RestClient, we will create its object. (only the public methods 
	 * will be accessed, the private methods are already used by public methods inside the RestClient
	 * class. We wouldn't be able to directly access them here.)
	 * 
	 * Note: to disable a test / to not run a test, use -> @Test(enabled = false). to enable back -> @Test(enabled = true)
	 * 		 To prioritize a test, use -> @Test(@priority = give a number order)
	 */
	@BeforeMethod
	public void getUserSetup() {
		restClient = new RestClient(prop, baseURI);
	}
	

	@Test(priority = 3)
	public void getAllUsersTest() {
		restClient.get(GOREST_ENDPOINT, true, true)
					.then().log().all() // We didn't add the then() at RestClient, because the assertions are the responsibility of the testNG, not the utility class.
						.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
		// If u want to assert the body, you can use JsonPath
	}
	
	// How to fetch a specific user. We will do it hard coded for now. after the service URl add the specific user ID
	@Test(enabled = true, priority = 2)
	public void getUserTest() {
		restClient.get(GOREST_ENDPOINT + "/4654063", true, true)
					.then().log().all()
						.assertThat().statusCode(APIHttpStatus.OK_200.getCode()); 
	}
	/*	
	 * --> Get the user where name = Sabawoon & status = active
	 * --> URL = https://gorest.co.in/public/v2/users/
	 * --> Method = Get
	 * --> Authorization = Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e
	 * --> Status code = 200
	 * Since Header is not wanted then we can put the header value as "null"
	 */
	@Test(priority = 1)
	public void getUserWithQueryParamsTest() {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("name", "sabawoon");
		queryParams.put("status", "active");
		restClient.get(GOREST_ENDPOINT, queryParams, null,true, true)
						.then().log().all()
							.assertThat().statusCode(APIHttpStatus.OK_200.getCode());	
	}
	
	/*		Note
	 * If you run this whole class, it will fail in 2nd and 3rd Tests. Because the Authorization header in 2nd test will
	 * will be repeated/printed two times, and in the 3rd test it will be repeated 3 times, that is Y it will give you 400 bad 
	 * request error. You can cross check within Postman by adding the Authorization header two times, and you will get
	 * 400 bad request error.
	 * Reason there is a bug in RestClient code that has to be fixed.
	 * 		Resolution/Solution
	 * 				1st APPROACH
	 * To resolve this issue, go back to RestClient class, comment the static block and add a public constructor of the RestClient with
	 * initializing the specBuilder inside it. In case if this approach is not working, use the 2nd approach
	 * 
	 * 				2nd APPROACH
	 * To resolve this issue, we can remove the object of the RestClient (declared in class level) and create separate
	 * RestClient Objects for each Method individually.
	 * And then, go back to RestClient class, comment the static block and add a public constructor of the RestClient with
	 * initializing the specBuilder inside it.
	 */
	  
	  


}
