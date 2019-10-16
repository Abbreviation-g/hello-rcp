package com.my.hello.editor.editpart;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;

import com.my.hello.editor.editpolicy.AppEditDeletePolicy;
import com.my.hello.editor.editpolicy.AppEditLayoutPolicy;
import com.my.hello.editor.editpolicy.AppRenamePolicy;
import com.my.hello.editor.figure.ServiceFigure;
import com.my.hello.editor.model.INode;
import com.my.hello.editor.model.IService;
import com.my.hello.editor.model.impl.Node;
import com.my.hello.editor.model.impl.Service;

public class ServicePart extends AppAbstractEditpart {

	@Override
	protected IFigure createFigure() {
		return new ServiceFigure();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new AppEditLayoutPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new AppEditDeletePolicy());
		installEditPolicy(EditPolicy.NODE_ROLE, new AppRenamePolicy());
	}

	protected void refreshVisuals() {
		ServiceFigure figure = (ServiceFigure) getFigure();
		Service model = (Service) getModel();
		figure.setName(model.getName());
		figure.setEtage(model.getEtage());
		figure.setLayout(model.getLayout());
	}
	public List<INode> getModelChildren(){
		return ((IService)getModel()).getChildren();
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(Node.PROPERTY_LAYOUT)) {
			refreshVisuals();
		} else if(evt.getPropertyName().equals(Node.PROPERTY_ADD)){
			refreshChildren();
		} else if(evt.getPropertyName().equals(Node.PROPERTY_DLETE)){
			refreshChildren();
		} else if(evt.getPropertyName().equals(Node.PROPERTY_RENAME)){
			refreshVisuals();
		} else if(evt.getPropertyName().equals(Service.PROPERTY_COLOR)) {
			refreshVisuals();
		}
	}
}
