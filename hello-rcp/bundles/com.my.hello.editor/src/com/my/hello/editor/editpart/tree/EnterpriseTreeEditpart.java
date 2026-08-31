package com.my.hello.editor.editpart.tree;

import java.beans.PropertyChangeEvent;
import java.util.List;

import com.my.hello.editor.model.IEnterprise;
import com.my.hello.editor.model.INode;
import com.my.hello.editor.model.impl.Enterprise;
import com.my.hello.editor.model.impl.Node;

public class EnterpriseTreeEditpart extends AppAbstractTreeEditpart {
	@Override
	protected List<INode> getModelChildren() {
		return ((IEnterprise) getModel()).getChildren();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
		case Node.PROPERTY_ADD:
		case Node.PROPERTY_DLETE:
			refreshChildren();
			break;
		case Node.PROPERTY_RENAME:
		case Enterprise.PROPERTY_CAPITAL:
			refreshVisuals();
			break;
		default:
			break;
		}
	}
}
