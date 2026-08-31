package com.my.hello.editor.model;

public interface IEmployee extends INode{
	String ELEMENT_NAME = "employee";
	String PRENOM_ATTRIBUTE = "prenom";

	void setPrenom(String prenom);

	String getPrenom();

}