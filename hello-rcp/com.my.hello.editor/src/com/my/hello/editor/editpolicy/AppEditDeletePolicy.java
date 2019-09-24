package com.my.hello.editor.editpolicy;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractEditPart;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import com.my.hello.editor.command.DeleteCommand;

public class AppEditDeletePolicy extends ComponentEditPolicy {
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		DeleteCommand deleteCommand = new DeleteCommand();
		deleteCommand.setModel(((AbstractEditPart) getHost()).getModel());

		return deleteCommand;
	}
}
