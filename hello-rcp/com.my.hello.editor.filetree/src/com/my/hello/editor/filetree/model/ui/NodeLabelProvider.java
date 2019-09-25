package com.my.hello.editor.filetree.model.ui;

import org.eclipse.jface.viewers.LabelProvider;

import com.my.hello.editor.filetree.model.INode;

public class NodeLabelProvider extends LabelProvider {
	@Override
	public String getText(Object element) {
		if (element instanceof INode) {
			INode entry = (INode) element;
			return entry.getFile().getName();
		}
		return null;
	}
}
