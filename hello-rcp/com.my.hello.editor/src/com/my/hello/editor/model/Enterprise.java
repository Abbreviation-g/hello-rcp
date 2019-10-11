package com.my.hello.editor.model;

public class Enterprise extends Node {
	public static final String PROPERTY_CAPITAL = "EnterpriseCapital";
	
	private String address;
	private int capital;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCapital() {
		return capital;
	}

	public void setCapital(int capital) {
		this.capital = capital;
	}

}
