package com.my.hello.editor.command;

import org.eclipse.gef.requests.CreationFactory;

import com.my.hello.editor.model.Employee;
import com.my.hello.editor.model.Service;

public class NodeCreationFactory implements CreationFactory{

	private Class<?> template;
	public NodeCreationFactory(Class<?> template) {
		this.template = template;
	}
	
	@Override
	public Object getNewObject() {
		if(template == null)
			return null;
		if(Service.class.isAssignableFrom(template)) {
			Service service = new Service();
			service.setEtage(42);
			service.setName("客房");
			return service;
		} else if(Employee.class.isAssignableFrom(template)) {
			Employee employee = new Employee();
			employee.setPrenom("员工");
			employee.setName("员工甲");
			return employee;
		}
		return null;
	}

	@Override
	public Object getObjectType() {
		return this.template;
	}

}
