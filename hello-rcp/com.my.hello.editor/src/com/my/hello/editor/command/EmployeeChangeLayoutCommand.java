package com.my.hello.editor.command;

import org.eclipse.draw2d.geometry.Rectangle;

import com.my.hello.editor.model.Employee;

public class EmployeeChangeLayoutCommand extends AbstractLayoutCommand {
	private Employee model;
	private Rectangle layout;
	private Rectangle oldLayout;
	@Override
	public void execute() {
		model.setLayout(layout);
	}

	@Override
	public void setConstraint(Rectangle rectangle) {
		this.layout = rectangle;
	}

	@Override
	public void setModel(Object model) {
		this.model = (Employee) model;
		this.oldLayout = this.model.getLayout();
	}

	@Override
	public void undo() {
		this.model.setLayout(this.oldLayout);
	}
}
