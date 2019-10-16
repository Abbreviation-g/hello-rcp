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
	
	@Override
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		super.firePropertyChange(propertyName, oldValue, newValue);
		if(getParent() != null)
			getParent().firePropertyChange(propertyName, oldValue, newValue);
	}

}
