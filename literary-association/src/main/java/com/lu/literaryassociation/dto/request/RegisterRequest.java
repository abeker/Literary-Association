package com.lu.literaryassociation.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String confirmPass;
	private String city;
	private String country;
	private Boolean betaReader;
	
	
	
	
	public RegisterRequest() {
		super();
	}
	public RegisterRequest(String username, String firstName, String lastName, String email, String password,
			String confirmPass, String city, String country, Boolean betaReader) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.confirmPass = confirmPass;
		this.city = city;
		this.country = country;
		this.betaReader = betaReader;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPass() {
		return confirmPass;
	}
	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Boolean getBetaReader() {
		return betaReader;
	}
	public void setBetaReader(Boolean betaReader) {
		this.betaReader = betaReader;
	}
	
	
}
