package com.my.hello.editor.filetree.model.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.my.hello.editor.filetree.model.INode;

public class NodeLabelProvider extends LabelProvider {
	private ImageDescriptor foldImageDescriptor;
	private ImageDescriptor classDescriptor;

	{
		foldImageDescriptor = PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER);
		classDescriptor = PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof INode) {
			INode entry = (INode) element;
			if (entry.getFile() == null) {
				return null;
			}
			return entry.getFile().getName();
		}
		return null;
	}

	@Override
	public Image getImage(Object element) {
		if (!(element instanceof INode)) {
			return null;
		}
		INode node = (INode) element;
		if (node.getFile().isDirectory()) {
			return foldImageDescriptor.createImage();
		} else {
			return classDescriptor.createImage();
		}
	}
}
