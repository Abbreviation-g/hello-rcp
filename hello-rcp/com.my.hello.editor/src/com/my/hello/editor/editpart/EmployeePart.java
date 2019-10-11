package com.my.hello.editor.editpart;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;

import com.my.hello.editor.editpolicy.AppEditDeletePolicy;
import com.my.hello.editor.figure.EmployeeFigure;
import com.my.hello.editor.model.Employee;
import com.my.hello.editor.model.Node;

public class EmployeePart extends AppAbstractEditpart {

	@Override
	protected IFigure createFigure() {
		return new EmployeeFigure();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new AppEditDeletePolicy());
	}

	protected void refreshVisuals() {
		EmployeeFigure figure = (EmployeeFigure) getFigure();
		Employee model = (Employee) getModel();
		figure.setName(model.getName());
		figure.setFirstName(model.getPrenom());
		figure.setLayout(model.getLayout());
	}
	
	public List<Node> getModelChildren(){
		return new ArrayList<Node>();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(Node.PROPERTY_LAYOUT)) {
			refreshVisuals();
		} else if(evt.getPropertyName().equals(Employee.PROPERTY_FIRSTNAME)) {
			refreshVisuals();
		}
	}
}
