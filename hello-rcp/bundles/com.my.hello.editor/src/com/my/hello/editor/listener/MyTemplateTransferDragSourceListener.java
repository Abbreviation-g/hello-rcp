package com.my.hello.editor.listener;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.swt.dnd.DragSourceEvent;

import com.my.hello.editor.command.EmployeeMoveFactory;
import com.my.hello.editor.editpart.EmployeePart;

public class MyTemplateTransferDragSourceListener extends TemplateTransferDragSourceListener {

	public MyTemplateTransferDragSourceListener(EditPartViewer viewer) {
		super(viewer);
	}

	@Override
	protected Object getTemplate() {
		@SuppressWarnings("rawtypes")
		List selection = getViewer().getSelectedEditParts();
		if (selection.size() == 1) {
			EditPart editpart = (EditPart) getViewer().getSelectedEditParts().get(0);
			if(editpart instanceof EmployeePart) {
				return new EmployeeMoveFactory((EmployeePart) editpart);
			}
		}
		return null;
	}

	@Override
	public void dragStart(DragSourceEvent event) {
		super.dragStart(event);
	}
}
