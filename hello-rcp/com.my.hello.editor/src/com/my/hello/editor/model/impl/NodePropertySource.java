package com.my.hello.editor.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.views.properties.ColorPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.my.hello.editor.model.IEmployee;
import com.my.hello.editor.model.IService;

public class NodePropertySource implements IPropertySource {
	private Node node;
	
	public NodePropertySource(Node node) {
		this.node = node;
	}
	@Override
	public Object getEditableValue() {
		return null;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> propertyDescriptors = new ArrayList<IPropertyDescriptor>();
		if(node instanceof Employee) {
			propertyDescriptors.add(new PropertyDescriptor(Node.PROPERTY_RENAME, "Name"));
		} else {
			propertyDescriptors.add(new TextPropertyDescriptor(Node.PROPERTY_RENAME, "Name"));
		}
		
		if(node instanceof Service) {
			propertyDescriptors.add(new ColorPropertyDescriptor(Service.PROPERTY_COLOR, "Color"));
			propertyDescriptors.add(new TextPropertyDescriptor(Service.PROPERTY_FLOOR, "Etage"));
		} else if(node instanceof Enterprise){
			propertyDescriptors.add(new TextPropertyDescriptor(Enterprise.PROPERTY_CAPITAL, "Capital"));
		} else if(node instanceof Employee) {
			propertyDescriptors.add(new PropertyDescriptor(Employee.PROPERTY_FIRSTNAME, "Prenom"));
		}
		return propertyDescriptors.toArray(new IPropertyDescriptor[propertyDescriptors.size()]);
	}

	@Override
	public Object getPropertyValue(Object id) {
		if(id.equals(Node.PROPERTY_RENAME) ) {
			return node.getName();
		} else if(id.equals(Service.PROPERTY_COLOR)) {
			return ((IService)node).getColor().getRGB();
		} else if(id.equals(Service.PROPERTY_FLOOR)) {
			return Integer.toString(((IService)node).getEtage());
		} else if(id.equals(Enterprise.PROPERTY_CAPITAL)) {
			return Integer.toString(((Enterprise)node).getCapital());
		} else if(id.equals(Employee.PROPERTY_FIRSTNAME)) {
			return ((IEmployee)node).getPrenom();
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
		if(id.equals(Node.PROPERTY_RENAME)) {
			node.setName((String) value);
		} else if(id.equals(Service.PROPERTY_COLOR)) {
			Color newColor = new Color(null, (RGB) value);
			((IService)node).setColor(newColor);
		} else if(id.equals(Service.PROPERTY_FLOOR)) {
			try {
				Integer floor = Integer.parseInt((String) value);
				((IService)node).setEtage(floor);
			} catch (Exception e) {
			}
		} else if(id.equals(Enterprise.PROPERTY_CAPITAL)) {
			try {
				Integer capital = Integer.parseInt((String) value);
				((Enterprise)node).setCapital(capital);
			} catch (Exception e) {
			}
		}
	}

}
