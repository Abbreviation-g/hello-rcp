package com.my.hello.editor.editpart;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.my.hello.editor.model.Employee;
import com.my.hello.editor.model.Enterprise;
import com.my.hello.editor.model.Service;

public class AppEditpartFactory implements EditPartFactory {

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		AbstractGraphicalEditPart editPart = null;
		if(model instanceof Enterprise) {
			editPart = new EnterprisePart();
		} else if(model instanceof Service) {
			editPart = new ServicePart();
		} else if(model instanceof Employee){
			editPart = new EmployeePart();
		}
		
		editPart.setModel(model);
		return editPart;
	}

}
