package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIConstants;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.User;
import com.qa.gorest.utils.ExcelUtil;
import com.qa.gorest.utils.StringUtils;

public class CreateUserTest extends BaseTest {
	/*			Test Case
	 * --> Create the user
	 * --> URL = https://gorest.co.in/public/v2/users
	 * --> Method = Post
	 * --> Authorization = Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e
	 * --> Body =
	 * 			name = "Sabawoon"
	 * 			email = Generate any random email. you can use the getRandomEmailId() method from "com.qa.gorest.utils" package, and StringUtils class
	 * 			gender = "male"
	 * 			status = "active
	 * --> Status code = 201
	 * --> Verify if the user has been added or not.
	 * 
	 * To create the user we will have to create a POJO class for it. Under "com.qa.gorest.pojo" package, "User" class.
	 * Create the Object of the User class to supply the body to POST call requestBody
	 */
	
//	RestClient restClient; We don't need the object of the RestClient class as a global variable anymore because we have
// already inherited the BaseTest class, and we have the object of the RestClient declared there. 
	
	@BeforeMethod
	public void getUserSetup() {
		restClient = new RestClient(prop, baseURI);
	}
	
	@DataProvider
	public Object[][] getUserTestData() {
		return new Object [][] {
			{"Sabawoon", "male", "active"}, // we don't need to use the email, it will pick it up from getRandomEmailId() method given below.
			{"Zala", "female", "inactive"},
			{"Abaseen", "male", "active"}
		};
	}
	
	@DataProvider
	public Object[][] getUserTestSheetData() {
			return ExcelUtil.getTestData(APIConstants.GOREST_USER_SHEETNAME);
	};
	
	@Test(dataProvider= "getUserTestSheetData")
	public void createUserTest(String name, String gender, String status) {
		User user = new User(name, StringUtils.getRandomEmailId(), gender, status); // import it from "com.qa.gorest.pojo"
//		restClient = new RestClient(prop, baseURI); this piece of code also needs to be deleted/commented, because the Object of RestClient is taken cared in BaseTest, and we have inherited BaseTest class.
		Integer userId = restClient.post(GOREST_ENDPOINT, "json", user, true, true)
							.then().log().all()
								.assertThat().statusCode(APIHttpStatus.CREATED_201.getCode())
									.extract().path("id");
		System.out.println("User ID: " + userId);
		
		//2. Get call: Verifying the user creation
		restClient = new RestClient(prop, baseURI); 
		restClient.get(GOREST_ENDPOINT + "/" + userId, true, true)
					.then().log().all()
						.assertThat().statusCode(APIHttpStatus.OK_200.getCode())
							.and()
								.assertThat().body("id", equalTo(userId));
		
		System.out.println("End test"); // this was added to practice git
	}
	
	
	/*		Note
	 * If you run this whole class, it will fail in Get Call. Because the Authorization header in Get call will
	 * be repeated for 2nd time, that is Y it will give you 400 bad request error. You can cross check within Postman by
	 *  adding the Authorization header two times, and you will get 400 bad request error.
	 * 
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
