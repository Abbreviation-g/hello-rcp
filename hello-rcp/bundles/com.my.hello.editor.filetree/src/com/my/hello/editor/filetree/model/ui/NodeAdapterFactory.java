package com.my.hello.editor.filetree.model.ui;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.views.properties.IPropertySource;

import com.my.hello.editor.filetree.model.INode;

public class NodeAdapterFactory implements IAdapterFactory {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if((IPropertySource.class.isAssignableFrom(adapterType) ) && (adaptableObject instanceof INode)) {
			return (T) new NodeModel((INode)adaptableObject);
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class[] {IPropertySource.class};
	}

}
