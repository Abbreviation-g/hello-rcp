package com.my.hello.editor.model;

import java.util.Random;

import org.eclipse.swt.graphics.Color;

public class Service extends Node {
	public static final String PROPERTY_COLOR = "ServiceColor";
	public static final String PROPERTY_FLOOR = "ServiceFloor";
	
	// 层级
	private int etage;

	private Color color;
	
	private Color createRandomColor() {
		Random random = new Random();
		return new Color(null, random.nextInt(128)+128, random.nextInt(128)+128, random.nextInt(128)+128);
	}
	
	public Service() {
		this.color = createRandomColor();
	}
	
	public int getEtage() {
		return etage;
	}

	public void setEtage(int etage) {
		this.etage = etage;
	} 
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color newColor) {
		Color oldColor = this.color;
		this.color = newColor;
		getListeners().firePropertyChange(PROPERTY_COLOR, oldColor, newColor);
	}
}
