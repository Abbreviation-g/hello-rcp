package com.my.hello.editor.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.ui.views.properties.IPropertySource;

public class Node implements IAdaptable{
	private PropertyChangeSupport listeners;
	public static final String PROPERTY_LAYOUT = "NodeLayout";
	public static final String PROPERTY_DLETE = "NodeDelete";
	public static final String PROPERTY_ADD = "NodeAdd";
	public static final String PROPERTY_RENAME = "NodeRename";

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

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		getListeners().firePropertyChange(PROPERTY_RENAME, oldName, newName);
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
		getListeners().firePropertyChange(PROPERTY_ADD, null, child);
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

	private IPropertySource propertySource = null;
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if(IPropertySource.class.isAssignableFrom(adapter)) {
			if(propertySource == null) {
				propertySource = new NodePropertySource(this);
			}
			return (T) propertySource;
		}
		
		return null;
	}
}
