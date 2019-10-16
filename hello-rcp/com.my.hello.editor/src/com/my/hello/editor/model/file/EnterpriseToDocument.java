package com.my.hello.editor.model.file;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

import com.my.hello.editor.model.IEmployee;
import com.my.hello.editor.model.IEnterprise;
import com.my.hello.editor.model.INode;
import com.my.hello.editor.model.IService;
import com.my.hello.editor.model.impl.Enterprise;

public class EnterpriseToDocument {

	private IEnterprise enterprise;

	public EnterpriseToDocument(IEnterprise enterprise) {
		this.enterprise = enterprise;
	}

	public Document toDocument() {
		DocumentFactory documentFactory = DocumentFactory.getInstance();
		Document document = documentFactory.createDocument("UTF-8");
		Element rootElement = createNodeElement(documentFactory, enterprise);
		document.setRootElement(rootElement);
		return document;
	}

	public String toXmlString() {
		try {
			OutputFormat format = new OutputFormat("    ", true, "UTF-8");
			format.setNewLineAfterDeclaration(false);
			Document document = toDocument();
			StringWriter out = new StringWriter();
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			writer.flush();

			return out.toString();
		} catch (IOException e) {
			throw new RuntimeException("IOException while generating textual " + "representation: " + e.getMessage());
		}
	}

	public InputStream toInputStream() {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(toXmlString().getBytes());
		return inputStream;
	}

	private static Element createNodeElement(DocumentFactory documentFactory, INode node) {
		if (node instanceof IEnterprise) {
			IEnterprise enterprise = (IEnterprise) node;
			Element rootElement = documentFactory.createElement(IEnterprise.ELEMENT_NAME);
			rootElement.addAttribute(INode.NAME_ATTRIBUTE, enterprise.getName());
			rootElement.addAttribute(IEnterprise.ADDRESS_ATTRIBUTE, enterprise.getAddress());
			rootElement.addAttribute(IEnterprise.CAPITAL_ATTRIBUTE, Integer.toString(enterprise.getCapital()));

			List<INode> services = enterprise.getChildren();
			if (services == null || services.isEmpty())
				return rootElement;
			for (INode serviceNode : services) {
				Element serviceElement = createNodeElement(documentFactory, serviceNode);
				rootElement.add(serviceElement);
			}
			return rootElement;
		} else if (node instanceof IService) {
			IService service = (IService) node;
			Element serviceElement = documentFactory.createElement(IService.ELEMENT_NAME);
			serviceElement.addAttribute(INode.NAME_ATTRIBUTE, service.getName());
			serviceElement.add(createLayoutElement(documentFactory, INode.LAYOUT_ELEMENT, service.getLayout()));
			serviceElement.addAttribute(IService.ETAGE_ATTRIBUTE, Integer.toString(service.getEtage()));
			serviceElement.add(createColorElement(documentFactory, IService.COLOR_ELEMENT, service.getColor()));

			List<INode> employees = service.getChildren();
			if (employees == null || employees.isEmpty())
				return serviceElement;
			for (INode employeeNode : employees) {
				Element employeeElement = createNodeElement(documentFactory, employeeNode);
				serviceElement.add(employeeElement);
			}
			return serviceElement;
		} else if (node instanceof IEmployee) {
			IEmployee employee = (IEmployee) node;
			Element employeeElement = documentFactory.createElement(IEmployee.ELEMENT_NAME);
			employeeElement.addAttribute(INode.NAME_ATTRIBUTE, employee.getName());
			employeeElement.add(createLayoutElement(documentFactory, INode.LAYOUT_ELEMENT, employee.getLayout()));
			employeeElement.addAttribute(IEmployee.PRENOM_ATTRIBUTE, employee.getPrenom());
			return employeeElement;
		}
		return null;
	}

	private static Element createLayoutElement(DocumentFactory documentFactory, String elementName, Rectangle layout) {
		Element layoutElement = documentFactory.createElement(elementName);
		layoutElement.addAttribute("x", Integer.toString(layout.x));
		layoutElement.addAttribute("y", Integer.toString(layout.y));
		layoutElement.addAttribute("height", Integer.toString(layout.height));
		layoutElement.addAttribute("width", Integer.toString(layout.width));
		return layoutElement;
	}

	private static Element createColorElement(DocumentFactory documentFactory, String elementName, Color color) {
		Element colorElement = documentFactory.createElement(elementName);
		colorElement.addAttribute("red", Integer.toString(color.getRed()));
		colorElement.addAttribute("green", Integer.toString(color.getGreen()));
		colorElement.addAttribute("blue", Integer.toString(color.getBlue()));
		colorElement.addAttribute("alpha", Integer.toString(color.getAlpha()));
		return colorElement;
	}

	public static void main(String[] args) {
		Enterprise enterprise = new Enterprise();
		EnterpriseToDocument enterpriseToDocument = new EnterpriseToDocument(enterprise);
		
		System.out.println(enterpriseToDocument.toXmlString());
	}
}
