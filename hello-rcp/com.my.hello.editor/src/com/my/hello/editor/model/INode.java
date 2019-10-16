package com.my.hello.editor.model;

import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.geometry.Rectangle;

public interface INode extends IAdaptable {
	String NAME_ATTRIBUTE = "name";
	String LAYOUT_ELEMENT = "layout";

	public void setName(String newName);

	public String getName();

	public void setLayout(Rectangle newLayout);

	public Rectangle getLayout();

	public void setParent(INode parent);

	public INode getParent();

	public boolean addChild(INode child);

	public boolean removeChild(INode child);

	public List<INode> getChildren();

	public INode[] getChildrenArray();

	public boolean contains(INode child);

	public void addPropertyChangeListener(PropertyChangeListener listener);

	public void removePropertyChangeListener(PropertyChangeListener listener);

	public void firePropertyChange(String propertyName, Object oldValue, Object newValue);
}
