package com.my.hello.editor.command;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import com.my.hello.editor.model.Employee;
import com.my.hello.editor.model.Service;

public class EmployeeCreateCommand extends Command {
	private Service service;
	private Employee employee;
	public EmployeeCreateCommand() {
	}
	
	public void setService(Object service) {
		if(service instanceof Service)
		this.service = (Service) service;
	}
	
	public void setEmployee(Object employee) {
		if(employee instanceof Employee)
			this.employee = (Employee) employee;
	}
	
	public void setLayout(Rectangle rectangle){
		if(employee == null)
			return;
		employee.setLayout(rectangle);
	}
	
	@Override
	public boolean canExecute() {
		if(service == null || employee == null)
			return false;
		return true;
	}
	
	@Override
	public void execute() {
		service.addChild(employee);
	}
	
	@Override
	public boolean canUndo() {
		if(!canExecute())
			return false;
		return service.contains(employee);
	}
	
	@Override
	public void undo() {
		service.removeChild(employee);
	}
}
