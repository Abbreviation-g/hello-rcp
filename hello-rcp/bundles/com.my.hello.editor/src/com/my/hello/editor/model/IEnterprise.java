package com.my.hello.editor.model;

public interface IEnterprise extends INode {
	String ELEMENT_NAME = "enterprise";
	String ADDRESS_ATTRIBUTE = "address";
	String CAPITAL_ATTRIBUTE = "capital";
	
	public String getAddress();

	public void setAddress(String address);

	public int getCapital();

	public void setCapital(int capital);

}
