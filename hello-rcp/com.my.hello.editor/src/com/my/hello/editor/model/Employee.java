package com.my.hello.editor.model;

public class Employee extends Node {
	public static final String PROPERTY_FIRSTNAME = "EmployeePrenom";
	
	// first name
	private String prenom;

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getPrenom() {
		return this.prenom;
	}

}
