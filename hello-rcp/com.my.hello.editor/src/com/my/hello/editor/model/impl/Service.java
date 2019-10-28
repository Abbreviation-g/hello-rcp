package com.my.hello.editor.model.impl;

import java.util.Iterator;
import java.util.Random;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

import com.my.hello.editor.model.INode;
import com.my.hello.editor.model.IService;

public class Service extends Node implements IService, Cloneable {
	public static final String PROPERTY_COLOR = "ServiceColor";
	public static final String PROPERTY_FLOOR = "ServiceFloor";

	// 层级
	private int etage;

	private Color color;

	private Color createRandomColor() {
		Random random = new Random();
		return new Color(null, random.nextInt(128) + 128, random.nextInt(128) + 128, random.nextInt(128) + 128);
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
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		super.firePropertyChange(propertyName, oldValue, newValue);
		if (getParent() != null)
			getParent().firePropertyChange(propertyName, oldValue, newValue);
	}

	@Override
	public String toString() {
		return "Service [etage=" + etage + ", color=" + color + ", getName()=" + getName() + ", getLayout()="
				+ getLayout() + ", getChildren()=" + getChildren() + "]";
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Service service = (Service) super.clone();
		service.setColor(new Color(null, this.color.getRed(), color.getGreen(), color.getBlue()));
		service.setParent(this.getParent());
		service.setLayout(new Rectangle(getLayout().x + 10, getLayout().y + 10, getLayout().width, getLayout().height));
		Iterator<INode> iterator = this.getChildren().iterator();
		while (iterator.hasNext()) {
			INode iNode = (INode) iterator.next();
			if(iNode instanceof Employee) {
				service.addChild((INode) ((Employee) iNode).clone());
			}
		}
		return service;
	}
}
