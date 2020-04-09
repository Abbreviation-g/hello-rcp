package com.my.hello.editor.model.impl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.ui.views.properties.IPropertySource;

import com.my.hello.editor.model.INode;

public class Node implements INode{
	private PropertyChangeSupport listeners;
	public static final String PROPERTY_LAYOUT = "NodeLayout";
	public static final String PROPERTY_DLETE = "NodeDelete";
	public static final String PROPERTY_ADD = "NodeAdd";
	public static final String PROPERTY_RENAME = "NodeRename";

	private String name;
	private Rectangle layout;
	private List<INode> children;
	private INode parent;

	public Node() {
		this.name = "Unknown";
		this.layout = null;
		this.children = new ArrayList<INode>();
		this.parent = null;

		this.listeners = new PropertyChangeSupport(this);
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChange(PROPERTY_RENAME, oldName, newName);
	}

	public String getName() {
		return this.name;
	}

	public void setLayout(Rectangle newLayout) {
		Rectangle oldLayout = this.layout;
		this.layout = newLayout;
		firePropertyChange(PROPERTY_LAYOUT, oldLayout, newLayout);
	}

	public Rectangle getLayout() {
		return this.layout;
	}

	public void setParent(INode parent) {
		this.parent = parent;
	}

	public INode getParent() {
		return parent;
	}

	public boolean addChild(INode child) {
		child.setParent(this);
		boolean result = this.children.add(child);
		firePropertyChange(PROPERTY_ADD, null, child);
		return result;
	}

	public boolean removeChild(INode child) {
		child.setParent(null);
		boolean result = this.children.remove(child);
		firePropertyChange(PROPERTY_DLETE, child, null);
		return result;
	}

	public List<INode> getChildren() {
		return children;
	}

	public INode[] getChildrenArray() {
		return this.children.toArray(new Node[this.children.size()]);
	}

	public boolean contains(INode child) {
		return this.children.contains(child);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}

	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		if (oldValue == null || newValue == null || !oldValue.equals(newValue)) {
            this.listeners.firePropertyChange(propertyName,oldValue,newValue);
        }
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
