package com.my.hello.editor.filetree.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public  class Node implements INode{
	private INode parent;
	private File file;
	private List<INode> children = new ArrayList<INode>();

	public void setFile(File file) {
		this.file = file;
	}

	public INode getParent() {
		return parent;
	}

	public void setParent(INode parent) {
		this.parent = parent;
	}

	public List<INode> getChildren() {
		return children;
	}

	public File getFile() {
		return file;
	}

	public boolean addChild(INode child) {
		return children.add(child);
	}

	public boolean remove(INode child) {
		return children.remove(child);
	}

	@Override
	public String toString() {
		return file.getName();
	}
	
	public static INode scanFolder(String folderPath) {
		File folder = new File(folderPath);
		if (!folder.exists()) {
			return null;
		}
		Node node = new Node();
		node.setFile(folder);
		if (folder.isDirectory()) {
			for (File file : folder.listFiles()) {
				INode child = scanFolder(file.getAbsolutePath());
				child.setParent(node);
				node.addChild(child);
			}
		}

		return node;
	}
	
	public static void main(String[] args) {
		System.out.println(Object.class.isAssignableFrom(Node.class));
	}
}

