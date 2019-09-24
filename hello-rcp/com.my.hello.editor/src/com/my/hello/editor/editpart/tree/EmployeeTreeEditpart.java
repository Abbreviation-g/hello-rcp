package com.my.hello.editor.editpart.tree;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.gef.EditPolicy;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.my.hello.editor.editpolicy.AppEditDeletePolicy;
import com.my.hello.editor.model.Employee;
import com.my.hello.editor.model.Node;

public class EmployeeTreeEditpart extends AppAbstractTreeEditpart {
	@Override
	protected List<Node> getModelChildren() {
		return ((Employee)getModel()).getChildren();
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new AppEditDeletePolicy());
	}
	
	@Override
	protected void refreshVisuals() {
		Employee employee = (Employee) getModel();
		setWidgetText(employee.getName()+" "+ employee.getPrenom());
		setWidgetImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_DEF_VIEW));
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
