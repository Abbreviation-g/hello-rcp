package com.my.hello.editor.command;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.Clipboard;

import com.my.hello.editor.model.impl.Employee;
import com.my.hello.editor.model.impl.Node;
import com.my.hello.editor.model.impl.Service;

/**
 * 13. 剪切和粘贴
 * 
 * @author guo
 *
 */
public class PasteNodeCommand extends Command {
	private Map<Node, Node> list = new HashMap<Node, Node>();

	@Override
	public boolean canExecute() {
		Object copiedObject = Clipboard.getDefault().getContents();
		if (!(copiedObject instanceof List)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		List<Node> copiedList = (List<Node>) copiedObject;
		Iterator<Node> iterator = copiedList.iterator();
		while (iterator.hasNext()) {
			Node node = iterator.next();
			if (isPastableNode(node)) {
				list.put(node, null);
			}
		}
		return !list.isEmpty();
	}

	@Override
	public void execute() {
		if (!canExecute())
			return;
		Iterator<Node> iterator = list.keySet().iterator();
		while (iterator.hasNext()) {
			Node node = (Node) iterator.next();
			try {
				if (node instanceof Service) {
					Service service = (Service) node;
					Service clone = (Service) service.clone();
					list.put(service, clone);
				} else if (node instanceof Employee) {
					Employee employee = (Employee) node;
					Employee clone = (Employee) employee.clone();
					list.put(employee, clone);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		redo();
	}

	@Override
	public void redo() {
		Iterator<Node> iterator = list.values().iterator();
		while (iterator.hasNext()) {
			Node node = (Node) iterator.next();
			if (isPastableNode(node)) {
				node.getParent().addChild(node);
			}
		}
	}

	@Override
	public boolean canUndo() {
		return !list.isEmpty();
	}

	@Override
	public void undo() {
		list.values().forEach((node) -> {
			if (isPastableNode(node)) {
				node.getParent().removeChild(node);
			}
		});
	}

	private boolean isPastableNode(Node node) {
		if (node instanceof Service || node instanceof Employee) {
			return true;
		}
		return false;
	}
}
