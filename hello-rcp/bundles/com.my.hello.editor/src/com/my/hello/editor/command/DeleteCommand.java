package com.my.hello.editor.command;

import org.eclipse.gef.commands.Command;

import com.my.hello.editor.model.INode;
import com.my.hello.editor.model.impl.Node;

public class DeleteCommand extends Command {
	private INode parent;
	private INode deletedNode;

	public void setModel(Object model) {
		this.deletedNode = (Node) model;
		this.parent = this.deletedNode.getParent();
	}

	@Override
	public void execute() {
		this.parent.removeChild(deletedNode);
	}

	@Override
	public void undo() {
		this.parent.addChild(deletedNode);
	}
}
