package com.my.hello.editor.editpolicy;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import com.my.hello.editor.command.DeleteCommand;
import com.my.hello.editor.editpart.AppAbstractEditpart;

public class AppEditDeletePolicy extends ComponentEditPolicy{
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		CompoundCommand compoundCommand = new CompoundCommand();
		@SuppressWarnings("unchecked")
		List<EditPart> deletedEditParts = deleteRequest.getEditParts();
		for (EditPart editPart : deletedEditParts) {
			if(editPart instanceof AppAbstractEditpart) {
				DeleteCommand deleteCommand = new DeleteCommand();
				deleteCommand.setModel(((AppAbstractEditpart)editPart).getModel());
				compoundCommand.add(deleteCommand);
			}
		}
		return compoundCommand;
	}
}
