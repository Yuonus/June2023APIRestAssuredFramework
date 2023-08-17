package com.qa.gorest.utils;

import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.qa.gorest.frameworkexception.APIFrameworkException;

public class JsonPathValidator {
	
	private String getJsonResponseAsString(Response response) {
		return response.getBody().asString();
	}
	
	public <T> T read(Response response, String jsonPath) {
		String jsonResponse = getJsonResponseAsString(response);
		try {
		return JsonPath.read(jsonResponse, jsonPath);
		}
		catch(PathNotFoundException e) {
			e.printStackTrace();
			throw new APIFrameworkException(jsonPath + " is not found ..."); 
		}
	}

	// This method will return a List, and this list might have any type of data, So, again we will store it in <T> generic
	// could have list integers, strings, doubles, floats ...etc.
	public <T> List<T> readList(Response response, String jsonPath) {
		String jsonResponse = getJsonResponseAsString(response);
		try {
		return JsonPath.read(jsonResponse, jsonPath);
		}
		catch(PathNotFoundException e) {
			e.printStackTrace();
			throw new APIFrameworkException(jsonPath + " is not found ..."); 
		}
	}
	
//	If we have to validate more than one attributes, we will use list of maps
	public <T> List<Map<String, T>> readListOfMaps(Response response, String jsonPath) {
		String jsonResponse = getJsonResponseAsString(response);
		try {
		return JsonPath.read(jsonResponse, jsonPath);
		}
		catch(PathNotFoundException e) {
			e.printStackTrace();
			throw new APIFrameworkException(jsonPath + " is not found ..."); 
		}
	}
	
	
}
