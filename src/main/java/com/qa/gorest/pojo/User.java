package com.qa.gorest.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	
	@JsonProperty("id") // This variable comes from response, as you know that the body from request side doesn't have any id
	private Integer id; // And the server literally creates the id. since we want to have Request and Response POJOs together that is Y we created the variable for id as well
	
	@JsonProperty ("name")
	private String name;
	
	@JsonProperty ("email")
	private String email;
	
	@JsonProperty ("gender")
	private String gender;
	
	@JsonProperty ("status")
	private String status;


	public User(String name, String email, String gender, String status) {
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.status = status;
	}

}
