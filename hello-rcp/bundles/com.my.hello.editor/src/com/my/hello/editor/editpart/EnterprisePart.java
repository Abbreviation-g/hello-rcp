package com.my.hello.editor.editpart;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;

import com.my.hello.editor.editpolicy.AppEditLayoutPolicy;
import com.my.hello.editor.figure.EnterpriseFigure;
import com.my.hello.editor.model.IEnterprise;
import com.my.hello.editor.model.INode;
import com.my.hello.editor.model.impl.Enterprise;
import com.my.hello.editor.model.impl.Node;

public class EnterprisePart extends AppAbstractEditpart {

	@Override
	protected IFigure createFigure() {
		IFigure figure = new EnterpriseFigure();
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new AppEditLayoutPolicy());
	}

	protected void refreshVisuals() {
		EnterpriseFigure figure = (EnterpriseFigure) getFigure();
		Enterprise model = (Enterprise) getModel();

		figure.setName(model.getName());
		figure.setAddress(model.getAddress());
		figure.setCapital(model.getCapital());
	}

	public List<INode> getModelChildren() {
		return ((IEnterprise) getModel()).getChildren();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(Node.PROPERTY_LAYOUT)) {
			refreshVisuals();
		} else if (evt.getPropertyName().equals(Node.PROPERTY_DLETE)) {
			refreshChildren();
		} else if (evt.getPropertyName().equals(Node.PROPERTY_ADD)) {
			refreshChildren();
		} else if(evt.getPropertyName().equals(Node.PROPERTY_RENAME)) {
			refreshVisuals();
		} else if(evt.getPropertyName().equals(Enterprise.PROPERTY_CAPITAL)) {
			refreshVisuals();
		}
	}
}
