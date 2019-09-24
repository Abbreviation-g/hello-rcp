package com.my.hello.editor.editpart.tree;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.gef.EditPolicy;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.my.hello.editor.editpolicy.AppEditDeletePolicy;
import com.my.hello.editor.model.Node;
import com.my.hello.editor.model.Service;

public class ServiceTreeEditpart extends AppAbstractTreeEditpart {
	@Override
	protected List<Node> getModelChildren() {
		return ((Service)getModel()).getChildren();
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new AppEditDeletePolicy());
	}
	
	public void refreshVisuals() {
		Service service = (Service) getModel();
		setWidgetText(service.getName());
		setWidgetImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT));
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
		case Node.PROPERTY_ADD:
		case Node.PROPERTY_DLETE:
			refreshChildren();
			break;
		default:
			break;
		}
	}
}