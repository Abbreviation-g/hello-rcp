package com.my.hello.editor.model;

import org.eclipse.swt.graphics.Color;

public interface IService extends INode {
	String ELEMENT_NAME = "service";
	String ETAGE_ATTRIBUTE = "etage";
	String COLOR_ELEMENT = "color";

	int getEtage();

	void setEtage(int etage);

	Color getColor();

	void setColor(Color newColor);

}