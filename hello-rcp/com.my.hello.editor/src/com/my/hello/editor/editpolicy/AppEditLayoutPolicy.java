package com.my.hello.editor.editpolicy;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import com.my.hello.editor.command.AbstractLayoutCommand;
import com.my.hello.editor.command.EmployeeChangeLayoutCommand;
import com.my.hello.editor.command.EmployeeCreateCommand;
import com.my.hello.editor.command.ServiceChangeLayoutCommand;
import com.my.hello.editor.command.ServiceCreateCommand;
import com.my.hello.editor.editpart.EmployeePart;
import com.my.hello.editor.editpart.EnterprisePart;
import com.my.hello.editor.editpart.ServicePart;
import com.my.hello.editor.figure.EmployeeFigure;
import com.my.hello.editor.figure.ServiceFigure;

public class AppEditLayoutPolicy extends XYLayoutEditPolicy {

	@Override
	protected Command createChangeConstraintCommand(EditPart child, Object constraint) {
		AbstractLayoutCommand command = null;
		if (child instanceof EmployeePart) {
			command = new EmployeeChangeLayoutCommand();
		} else if (child instanceof ServicePart) {
			command = new ServiceChangeLayoutCommand();
		}
		command.setModel(child.getModel());
		command.setConstraint((Rectangle) constraint);
		return command;
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		if (request.getType() != REQ_CREATE) {
			return null;
		}
		if (getHost() instanceof EnterprisePart) {
			ServiceCreateCommand command = new ServiceCreateCommand();
			command.setEnterprise(getHost().getModel());
			command.setService(request.getNewObject());
			Rectangle constraint = (Rectangle) getConstraintFor(request);
			constraint.x = constraint.x < 0 ? 0 : constraint.x;
			constraint.y = constraint.y < 0 ? 0 : constraint.y;
			constraint.width = constraint.width <= 0 ? ServiceFigure.SERVICE_FIGURE_DEFAULT_WIDTH : constraint.width;
			constraint.height = constraint.height <= 0 ? ServiceFigure.SERVICE_FIGURE_DEFAULT_HEIGHT
					: constraint.height;
			command.setLayout(constraint);
			return command;
		} else if (getHost() instanceof ServicePart) {
			EmployeeCreateCommand command = new EmployeeCreateCommand();
			command.setService(getHost().getModel());
			command.setEmployee(request.getNewObject());
			Rectangle constraint = (Rectangle) getConstraintFor(request);
			constraint.x = constraint.x < 0 ? 0 : constraint.x;
			constraint.y = constraint.y < 0 ? 0 : constraint.y;
			constraint.width = constraint.width <= 0 ? EmployeeFigure.EMPLOYEE_FIGURE_DEFAULT_WIDTH : constraint.width;
			constraint.height = constraint.height <= 0 ? EmployeeFigure.EMPLOYEE_FIGURE_DEFAULT_HEIGHT
					: constraint.height;
			command.setLayout(constraint);

			return command;
		}
		return null;
	}

}
