package com.my.hello.editor.listener;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.swt.dnd.DropTargetEvent;

import com.my.hello.editor.command.EmployeeMoveFactory;
import com.my.hello.editor.command.NodeCreationFactory;

public class MyTemplateTransferDropListener extends TemplateTransferDropTargetListener {

	public MyTemplateTransferDropListener(EditPartViewer viewer) {
		super(viewer);
	}

	@Override
	protected CreationFactory getFactory(Object template) {
		if(template instanceof NodeCreationFactory) {
			return (CreationFactory) template;
		} else if(template instanceof EmployeeMoveFactory){
			return (CreationFactory) template;
		}
		return null;
	}
	
	@Override
	public void dropAccept(DropTargetEvent event) {
		super.dropAccept(event);
	}
}
