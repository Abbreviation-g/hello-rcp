package com.my.hello.editor.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class MyEditorInput implements IEditorInput {

	private String name;

	public MyEditorInput(String name) {
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
		if (!(obj instanceof MyEditorInput)) {
			return false;
		}
		return ((MyEditorInput) obj).getName().equals(this.getName());
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
