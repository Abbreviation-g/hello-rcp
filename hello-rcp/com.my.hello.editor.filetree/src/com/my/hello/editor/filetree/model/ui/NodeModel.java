package com.my.hello.editor.filetree.model.ui;

import java.io.File;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.my.hello.editor.filetree.model.INode;

public class NodeModel  implements INode, IPropertySource{
	private INode node;
	public NodeModel(INode node) {
		this.node = node;
	}
	
	@Override
	public void setFile(File file) {
		this.node.setFile(file);
	}

	@Override
	public INode getParent() {
		return this.node.getParent();
	}

	@Override
	public void setParent(INode parent) {
		this.node.setParent(parent);
	}

	@Override
	public List<INode> getChildren() {
		return this.node.getChildren();
	}

	@Override
	public File getFile() {
		return this.node.getFile();
	}

	@Override
	public boolean addChild(INode child) {
		return this.node.addChild(child);
	}

	@Override
	public boolean remove(INode child) {
		return this.node.remove(child);
	}

	@Override
	public Object getEditableValue() {
		return this;
	}

	private static final String FILE_NAME_ID="FILE_NAME_ID"; 
	private static final String FILE_PATH_ID="FILE_PATH_ID";
	private static TextPropertyDescriptor fileNamePropertyDescriptor = new TextPropertyDescriptor(FILE_NAME_ID, "File Name");
	private static TextPropertyDescriptor filePathPropertyDescriptor = new TextPropertyDescriptor(FILE_PATH_ID, "File Path");
	private static IPropertyDescriptor[] descriptors = {fileNamePropertyDescriptor, filePathPropertyDescriptor};
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return descriptors;
	}

	@Override
	public Object getPropertyValue(Object id) {
		if(id.equals(FILE_NAME_ID)) {
			return this.getFile().getName();
		} else if(id.equals(FILE_PATH_ID)){
			return this.getFile().getPath();
		}
		return null;
	}

	@Override
	public boolean isPropertySet(Object id) {
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
		
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		
	}

}
