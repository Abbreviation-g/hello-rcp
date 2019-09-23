package com.my.hello.editor.editpart;

import java.beans.PropertyChangeListener;

import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.my.hello.editor.model.Node;

public abstract class AppAbstractEditpart extends AbstractGraphicalEditPart implements PropertyChangeListener {
	
	public void activate() {
		super.activate();
		((Node)getModel()).addPropertyChangeListener(this);
	}
	
	@Override
	public void deactivate() {
		((Node)getModel()).removePropertyChangeListener(this);
		super.deactivate();
	}
}
