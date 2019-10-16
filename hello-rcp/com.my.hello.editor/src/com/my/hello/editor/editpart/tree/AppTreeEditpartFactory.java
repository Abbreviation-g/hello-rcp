package com.my.hello.editor.editpart.tree;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.my.hello.editor.model.impl.Employee;
import com.my.hello.editor.model.impl.Enterprise;
import com.my.hello.editor.model.impl.Service;

public class AppTreeEditpartFactory implements EditPartFactory {

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		EditPart part = null;
		if(model instanceof Enterprise) {
			part = new EnterpriseTreeEditpart();
		} else if(model instanceof Service) {
			part = new ServiceTreeEditpart();
		} else if(model instanceof Employee) {
			part = new EmployeeTreeEditpart();
		}
		if(part != null)
			part.setModel(model);
		return part;
	}

}
