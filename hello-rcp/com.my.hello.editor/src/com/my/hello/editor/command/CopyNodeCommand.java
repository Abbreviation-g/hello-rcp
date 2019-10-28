package com.my.hello.editor.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.Clipboard;

import com.my.hello.editor.model.impl.Employee;
import com.my.hello.editor.model.impl.Node;
import com.my.hello.editor.model.impl.Service;

public class CopyNodeCommand extends Command {
	private List<Node> list = new ArrayList<Node>();

	public boolean addElement(Node node) {
		if (!list.contains(node)) {
			return list.add(node);
		}
		return false;
	}
	
	@Override
	public boolean canExecute() {
		if(list == null || list.isEmpty()) {
			return false;
		}
		Iterator<Node> iterator = list.iterator();
		while (iterator.hasNext()) {
			if(!isCopyableNode(iterator.next())) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void execute() {
		Clipboard.getDefault().setContents(list);
	}
	
	@Override
	public boolean canUndo() {
		return false;
	}
	
	public boolean isCopyableNode(Node node) {
		if(node instanceof Service || node instanceof Employee) {
			return true;
		}
		return false;
	}
}
