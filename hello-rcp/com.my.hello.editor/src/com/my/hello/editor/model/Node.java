package com.my.hello.editor.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;

public class Node {
	private PropertyChangeSupport listeners;
	public static final String PROPERTY_LAYOUT = "NodeLayout";
	public static final String PROPERTY_DLETE = "NodeDelete";

	private String name;
	private Rectangle layout;
	private List<Node> children;
	private Node parent;

	public Node() {
		this.name = "Unknown";
		this.layout = new Rectangle(10, 10, 100, 100);
		this.children = new ArrayList<Node>();
		this.parent = null;

		this.listeners = new PropertyChangeSupport(this);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setLayout(Rectangle newLayout) {
		Rectangle oldLayout = this.layout;
		this.layout = newLayout;
		getListeners().firePropertyChange(PROPERTY_LAYOUT, oldLayout, newLayout);
	}

	public Rectangle getLayout() {
		return this.layout;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node getParent() {
		return parent;
	}

	public boolean addChild(Node child) {
		child.setParent(this);
		boolean result = this.children.add(child);
		getListeners().firePropertyChange(PROPERTY_DLETE, null, child);
		return result;
	}

	public boolean removeChild(Node child) {
		child.setParent(null);
		boolean result = this.children.remove(child);
		getListeners().firePropertyChange(PROPERTY_DLETE, child, null);
		return result;
	}

	public List<Node> getChildren() {
		return children;
	}

	public Node[] getChildrenArray() {
		return this.children.toArray(new Node[this.children.size()]);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}

	public PropertyChangeSupport getListeners() {
		return listeners;
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}
}
