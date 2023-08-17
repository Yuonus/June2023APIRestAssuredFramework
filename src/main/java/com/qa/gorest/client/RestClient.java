package com.qa.gorest.client;

import static io.restassured.RestAssured.given;

import java.util.Map;
import java.util.Properties;

import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.frameworkexception.APIFrameworkException;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {
	
//	private static final String BASE_URI = "https://gorest.co.in";
//	private static final String BEARER_TOKEN = "201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e";
	
	private static RequestSpecBuilder specBuilder;
	

	private Properties prop;
	private String baseURI;
	
	private boolean isAuthorizationHeaderAdded = false;
	
	public RestClient (Properties prop, String baseURI) {
		specBuilder = new RequestSpecBuilder();
		this.prop = prop;
		this.baseURI = baseURI;
	}
	
	
	public void addAuthorizationHeader() {
		if(!isAuthorizationHeaderAdded) {
			specBuilder.addHeader("Authorization", "Bearer " + prop.getProperty("tokenId"));
			isAuthorizationHeaderAdded = true;
		}
	}
	
	private void setRequestContentType(String contentType) { // json, JSON, Json
		switch (contentType.toLowerCase()) {
		case "json":
			specBuilder.setContentType(ContentType.JSON);
			break;
		case "xml":
			specBuilder.setContentType(ContentType.XML);
			break;
		case "text":
			specBuilder.setContentType(ContentType.TEXT);
			break;
		case "multipart":
			specBuilder.setContentType(ContentType.MULTIPART);
			break;
		default:
			System.out.println("Please pass the right content Type ...");
			throw new APIFrameworkException("Invalid Content Type");
		}
	}
	
	
	// Let's assume this method for a basic GET Call. (for RequestSpecification)
	private RequestSpecification createRequestSpec(boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		if(includeAuth) {
			addAuthorizationHeader();
		}
		return specBuilder.build(); 
	}
	
	/* (for RequestSpecification)
	 * What if you have a GET call, where you will have to pass an extra header, like: 
	 * some other headers. So, we can add as many header as we want with the help of 
	 * Hash map. we will over load the method.
	 * what if somebody is supplying a blank hash map, So, for that we can write one check with
	 * the help of if condition. 
	 * if(headersMap !=null) --> if the header is not equal to null, then i will have to add a header
	 */
	private RequestSpecification createRequestSpec(Map<String, String> headersMap, boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		if(includeAuth) {
			addAuthorizationHeader();
		}
		if(headersMap !=null) {
			specBuilder.addHeaders(headersMap);
		}
		return specBuilder.build(); 
	}
	
	/*	(for RequestSpecification)
	 * Pretend there is one more GET call where you will have feed/supply the query parameters.
	 * 	the above two methods will not work. We will overload the method one more time to handle the
	 * 	query parameters, and we will add one extra parameter to the method signature. we will add this
	 * parameter using the hashmap, because we can have as many query parameters as we want.
	 */
	private RequestSpecification createRequestSpec(Map<String, String> headersMap, Map<String, Object> queryParams, boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		if(includeAuth) {
			addAuthorizationHeader();
		}
		if(headersMap !=null) { 
			specBuilder.addHeaders(headersMap);
		}
		if(queryParams!=null) {
			specBuilder.addQueryParams(queryParams);
		}
		return specBuilder.build(); 
	}
	
	/* Let's pretend this is a POST call. (for RequestSpecification)
	 * What if I want to add some content-Type? like Content-Type = application/json
	 * Content-Type = application/xml, html or any kind of application/pdf
	 * we will overload the method and remove the query params from the parameters because with the 
	 * POST call we don't need any query params. with POST call we have to supply the body and the body
	 * needs to be supplied in the form of POJO.
	 */
	private RequestSpecification createRequestSpec(Object requestBody, String contentType, boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		if(includeAuth) {
			addAuthorizationHeader();
		}
		setRequestContentType(contentType);
		if(requestBody!=null) {
			specBuilder.setBody(requestBody);
		}
		return specBuilder.build(); 
	}
	
	/* What if you are passing some extra headers to the POST call? (for RequestSpecification)
	 * 
	 */
	private RequestSpecification createRequestSpec(Object requestBody, String contentType, Map<String, String> headersMap, boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		if(includeAuth) {
			addAuthorizationHeader();
		}
		setRequestContentType(contentType);
		if(headersMap!=null) {
			specBuilder.addHeaders(headersMap);
		}
		if(requestBody!=null) {
			specBuilder.setBody(requestBody);
		}
		return specBuilder.build(); 
	}
	
	
	// Time to create HTTP Methods Utils --> GET call
	/*If you don't want to generate logs for some use cases/test cases, how will you handle that in your code?
	 * we can add one extra boolean parameter to get().
	 */
	public Response get(String serviceUrl, boolean includeAuth, boolean log) {
		
		if(log) { // if log is true
			return RestAssured.given(createRequestSpec(includeAuth)).log().all().when().get(serviceUrl); 
		}
		return RestAssured.given(createRequestSpec(includeAuth)).log().all().when().get(serviceUrl); // if log is false
	}
	
	//What if someone is passing a header along with a GET call?
	public Response get(String serviceUrl, Map<String, String> headersMap,boolean includeAuth, boolean log) {
		if(log) { // if log is true
			return RestAssured.given(createRequestSpec(headersMap, includeAuth)).log().all()
					.when().get(serviceUrl); 
		}
		return RestAssured.given(createRequestSpec(headersMap, includeAuth)).log().all()
				.when().get(serviceUrl); // if log is false
	}
	
	// Supplying query parameters with GET call
	public Response get(String serviceUrl, Map<String, Object> queryParams,  Map<String, String> headersMap, boolean includeAuth, boolean log) {
		
		if(log) {
			return RestAssured.given(createRequestSpec(headersMap, queryParams, includeAuth)).log().all()
			.when()
				.get(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(headersMap, queryParams, includeAuth)).when().get(serviceUrl);
	}
	
	/*			POST CALL
	 * For POST call the most important thing is "body". Simple Post call without any extra header
	 */
	public Response post(String serviceUrl, String contentType, Object requestBody, boolean includeAuth,
			 boolean log) {
		if(log) {
		return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).log().all()
			.when()
				.post(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth))
				.when()
					.post(serviceUrl);
	}
	
	/*			POST CALL
	 * For POST call the most important thing is "body"
	 * What if you want to add headers and some extra headers
	 */
	public Response post(String serviceUrl, String contentType, Object requestBody, 
			Map<String, String> headersMap, boolean includeAuth, boolean log) {
		if(log) {
		return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).log().all()
			.when()
				.post(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
				.when()
					.post(serviceUrl);
	}
	

	/*			PUT CALL
	 * For PUT call the most important thing is "body". Simple PUT call without any extra header
	 */
	public Response put(String serviceUrl, String contentType, Object requestBody,boolean includeAuth, 
			 boolean log) {
		if(log) {
		return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).log().all()
			.when()
				.put(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth))
				.when()
					.put(serviceUrl);
	}
	
	/*			PUT CALL
	 * For PUT call the most important thing is "body"
	 * What if you want to add headers and some extra headers
	 */
	public Response put(String serviceUrl, String contentType, Object requestBody, 
			Map<String, String> headersMap, boolean includeAuth, boolean log) {
		if(log) {
		return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).log().all()
			.when()
				.put(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
				.when()
					.put(serviceUrl);
	}
	
	/*			PATCH CALL
	 * For PATCH call the most important thing is "body". Simple PATCH call without any extra header
	 */
	public Response patch(String serviceUrl, String contentType, Object requestBody, boolean includeAuth,
			 boolean log) {
		if(log) {
		return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).log().all()
			.when()
				.patch(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth))
				.when()
					.patch(serviceUrl);
	}
	
	/*			PATCH CALL
	 * For PUT call the most important thing is "body"
	 * What if you want to add headers and some extra headers
	 */
	public Response patch(String serviceUrl, String contentType, Object requestBody, 
			Map<String, String> headersMap,boolean includeAuth, boolean log) {
		if(log) {
		return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).log().all()
			.when()
				.patch(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
				.when()
					.patch(serviceUrl);
	}
	
	/*		DELETE Call
	 * with Delete call we don't add any header & body. the authentication has already been added 
	 * in RequestSpec
	 */
	public Response delete(String serviceUrl, boolean includeAuth, boolean log) {
		if(log) {
			return RestAssured.given(createRequestSpec(includeAuth)).log().all()
				.when()
					.delete(serviceUrl);
			}
			return RestAssured.given(createRequestSpec(includeAuth))
					.when()
						.delete(serviceUrl);
			
			/*NOTE: to see all these created methods/function or blue print of the RestClient class
			 * -> press "CTRL+O" from your keyboard 
			 *   --> sf = static final variables
			 *   --> s = static
			 *   --> s{...} = static block
			 *   --> red methods = private methods
			 */
	}
	
	public String getAccessToken(String serviceURL, String grantType, String clientId, String clientSecret) {
		RestAssured.baseURI = "https://test.api.amadeus.com";
		
		//1. POST -- Get the access Token
		String accessToken = given().log().all()	
			.contentType(ContentType.URLENC)
			.formParam("grant_type", grantType)
			.formParam("client_id", clientId)
			.formParam("client_secret", clientSecret)
		.when()
		.post(serviceURL)
		.then().log().all()
			.assertThat()
				.statusCode(APIHttpStatus.OK_200.getCode())
					.extract()
						.path("access_token");
		System.out.println("Access Token -> " + accessToken);
		return accessToken;
	}

}
