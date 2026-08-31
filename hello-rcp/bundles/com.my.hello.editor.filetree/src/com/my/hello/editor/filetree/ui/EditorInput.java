package com.my.hello.editor.filetree.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class EditorInput implements IEditorInput {

	private String name;

	public EditorInput(String name) {
		this.name = name;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}

	@Override
	public boolean exists() {
		return (this.name != null);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EditorInput)) {
			return false;
		}
		return ((EditorInput) obj).getName().equals(this.getName());
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return ImageDescriptor.getMissingImageDescriptor();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return this.name;
	}

}
