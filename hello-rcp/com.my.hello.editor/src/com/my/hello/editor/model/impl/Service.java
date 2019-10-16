package com.my.hello.editor.model.impl;

import java.util.Random;

import org.eclipse.swt.graphics.Color;

import com.my.hello.editor.model.IService;

public class Service extends Node implements IService {
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
	
	@Override
	public int getEtage() {
		return etage;
	}

	@Override
	public void setEtage(int etage) {
		this.etage = etage;
	} 
	
	@Override
	public Color getColor() {
		return color;
	}
	
	@Override
	public void setColor(Color newColor) {
		Color oldColor = this.color;
		this.color = newColor;
		firePropertyChange(PROPERTY_COLOR, oldColor, newColor);
	}

	@Override
	public String toString() {
		return "Service [etage=" + etage + ", color=" + color + ", getName()=" + getName() + ", getLayout()="
				+ getLayout() +  ", getChildren()=" + getChildren() + "]";
	}
}
