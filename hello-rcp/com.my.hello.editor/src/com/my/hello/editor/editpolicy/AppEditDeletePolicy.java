package com.my.hello.editor.editpolicy;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import com.my.hello.editor.command.DeleteCommand;
import com.my.hello.editor.editpart.AppAbstractEditpart;

public class AppEditDeletePolicy extends ComponentEditPolicy {
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		DeleteCommand deleteCommand = new DeleteCommand();
		deleteCommand.setModel(((AppAbstractEditpart) getHost()).getModel());

		return deleteCommand;
	}
}
