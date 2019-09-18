package com.my.hello.editor.command;

import org.eclipse.draw2d.geometry.Rectangle;

import com.my.hello.editor.model.Service;

public class ServiceChangeLayoutCommand extends AbstractLayoutCommand {

	private Service model;
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
		this.model = (Service) model;
		this.oldLayout = this.model.getLayout();
	}

	@Override
	public void undo() {
		this.model.setLayout(this.oldLayout);
	}
	@Override
	public void redo() {
		super.redo();
	}
}
