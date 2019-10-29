package com.my.hello.editor.model.impl;

import org.eclipse.draw2d.geometry.Rectangle;

import com.my.hello.editor.model.IEmployee;

public class Employee extends Node implements IEmployee, Cloneable {

	public static final String PROPERTY_FIRSTNAME = "EmployeePrenom";

	// first name
	private String prenom;

	@Override
	public void setPrenom(String prenom) {
		String oldValue = this.prenom;
		String newValue = prenom;
		this.prenom = newValue;
		firePropertyChange(PROPERTY_FIRSTNAME, oldValue, newValue);
	}

	@Override
	public String getPrenom() {
		return this.prenom;
	}

	@Override
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		super.firePropertyChange(propertyName, oldValue, newValue);
		if (getParent() != null)
			getParent().firePropertyChange(propertyName, oldValue, newValue);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Employee employee = new Employee();
		employee.setName(this.getName());
		employee.setParent(this.getParent());
		employee.setPrenom(this.prenom);
		employee.setLayout(
				new Rectangle(getLayout().x + 10, getLayout().y + 10, getLayout().width, getLayout().height));

		return employee;
	}
}
