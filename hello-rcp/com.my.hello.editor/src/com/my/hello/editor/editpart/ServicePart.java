package com.my.hello.editor.editpart;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;

import com.my.hello.editor.editpolicy.AppEditLayoutPolicy;
import com.my.hello.editor.figure.ServiceFigure;
import com.my.hello.editor.model.Node;
import com.my.hello.editor.model.Service;

public class ServicePart extends AppAbstractEditpart {

	@Override
	protected IFigure createFigure() {
		return new ServiceFigure();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new AppEditLayoutPolicy());
	}

	protected void refreshVisuals() {
		ServiceFigure figure = (ServiceFigure) getFigure();
		Service model = (Service) getModel();
		figure.setName(model.getName());
		figure.setEtage(model.getEtage());
		figure.setLayout(model.getLayout());
	}
	public List<Node> getModelChildren(){
		return ((Service)getModel()).getChildren();
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(Node.PROPERTY_LAYOUT)) {
			refreshVisuals();
		}
	}
}
