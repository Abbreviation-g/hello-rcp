package com.my.hello.editor.editpolicy;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import com.my.hello.editor.command.AbstractLayoutCommand;
import com.my.hello.editor.command.EmployeeChangeLayoutCommand;
import com.my.hello.editor.command.ServiceChangeLayoutCommand;
import com.my.hello.editor.editpart.EmployeePart;
import com.my.hello.editor.editpart.ServicePart;

public class AppEditLayoutPolicy extends XYLayoutEditPolicy {

	@Override
	protected Command createChangeConstraintCommand(EditPart child, Object constraint) {
		AbstractLayoutCommand command = null;
		if(child instanceof EmployeePart) {
			command = new EmployeeChangeLayoutCommand();
		} else if(child instanceof ServicePart) {
			command = new ServiceChangeLayoutCommand();
		}
		command.setModel(child.getModel());
		command.setConstraint((Rectangle) constraint);
		return command;
	}
	
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		return null;
	}

}
