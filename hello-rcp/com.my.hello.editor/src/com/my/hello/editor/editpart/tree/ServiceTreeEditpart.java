package com.my.hello.editor.editpart.tree;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.gef.EditPolicy;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.my.hello.editor.editpolicy.AppEditDeletePolicy;
import com.my.hello.editor.editpolicy.AppRenamePolicy;
import com.my.hello.editor.model.INode;
import com.my.hello.editor.model.IService;
import com.my.hello.editor.model.impl.Node;
import com.my.hello.editor.model.impl.Service;

public class ServiceTreeEditpart extends AppAbstractTreeEditpart {
	@Override
	protected List<INode> getModelChildren() {
		return ((IService)getModel()).getChildren();
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new AppEditDeletePolicy());
		installEditPolicy(EditPolicy.NODE_ROLE, new AppRenamePolicy());
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
		case Node.PROPERTY_RENAME:
		case Service.PROPERTY_COLOR:
			refreshVisuals();
			break;
		default:
			break;
		}
	}
}
