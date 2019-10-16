package com.my.hello.editor.model.impl;

import com.my.hello.editor.model.IEmployee;

public class Employee extends Node implements IEmployee {
	public static final String PROPERTY_FIRSTNAME = "EmployeePrenom";
	
	// first name
	private String prenom;

	@Override
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Override
	public String getPrenom() {
		return this.prenom;
	}

}
