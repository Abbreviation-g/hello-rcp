package com.my.hello.editor.command;

import org.eclipse.gef.requests.CreationFactory;

import com.my.hello.editor.editpart.EmployeePart;
import com.my.hello.editor.editpart.ServicePart;
import com.my.hello.editor.model.impl.Employee;
import com.my.hello.editor.model.impl.Service;

public class EmployeeMoveFactory implements CreationFactory {

	private EmployeePart selectedEmployeeEditPart;

	public EmployeeMoveFactory(EmployeePart selectedEmployeeEditPart) {
		this.selectedEmployeeEditPart = selectedEmployeeEditPart;
	}

	@Override
	public Object getNewObject() {
		Employee employee = (Employee) selectedEmployeeEditPart.getModel();
		ServicePart servicePart = (ServicePart) selectedEmployeeEditPart.getParent();
		Service service = (Service) servicePart.getModel();
		service.removeChild(employee);
		return employee;
	}

	@Override
	public Object getObjectType() {
		return selectedEmployeeEditPart.getModel().getClass();
	}

}
