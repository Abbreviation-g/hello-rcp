package com.my.hello.editor.command;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import com.my.hello.editor.model.Enterprise;
import com.my.hello.editor.model.Service;

public class ServiceCreateCommand extends Command {
	private Enterprise enterprise;
	private Service service;

	public ServiceCreateCommand() {
	}

	public void setService(Object service) {
		if (service instanceof Service)
			this.service = (Service) service;
	}

	public void setEnterprise(Object enterprise) {
		if (enterprise instanceof Enterprise)
			this.enterprise = (Enterprise) enterprise;
	}
	
	public void setLayout(Rectangle rectangle){
		if (service ==null) {
			return;
		}
		service.setLayout(rectangle);
	}
	
	@Override
	public boolean canExecute() {
		if(service == null || enterprise == null)
			return false;
		return true;
	}
	
	@Override
	public void execute() {
		enterprise.addChild(service);
	}
	
	@Override
	public boolean canUndo() {
		if(!canExecute()) {
			return false;
		}
		return enterprise.getChildren().contains(service);
	}
	@Override
	public void undo() {
		enterprise.removeChild(service);
	}
}
