package com.my.hello.editor.model.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

import com.my.hello.editor.model.IEmployee;
import com.my.hello.editor.model.IEnterprise;
import com.my.hello.editor.model.INode;
import com.my.hello.editor.model.IService;
import com.my.hello.editor.model.impl.Employee;
import com.my.hello.editor.model.impl.Enterprise;
import com.my.hello.editor.model.impl.Service;

public class DocumentToEnterprise {
	private InputStream inputStream;

	public DocumentToEnterprise(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public IEnterprise toEnterprise() {
		IEnterprise enterprise = parseEnterprise(inputStream);
		return enterprise;
	}

	private static IEnterprise parseEnterprise(InputStream inputStream) {
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(inputStream);
			Element rootElement = document.getRootElement();

			if (!IEnterprise.ELEMENT_NAME.equalsIgnoreCase(rootElement.getName())) {
				return null;
			}
			IEnterprise enterprise = parseEnterprise(rootElement);
			return enterprise;
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static IEnterprise parseEnterprise(Element rootElement) {
		Enterprise enterprise = new Enterprise();
		enterprise.setName(rootElement.attribute(INode.NAME_ATTRIBUTE).getStringValue());
		enterprise.setAddress(rootElement.attributeValue(IEnterprise.ADDRESS_ATTRIBUTE));
		enterprise.setCapital(Integer.parseInt(rootElement.attribute(IEnterprise.CAPITAL_ATTRIBUTE).getStringValue()));

		List<Element> serviceElements = rootElement.elements(IService.ELEMENT_NAME);
		if (serviceElements == null || serviceElements.isEmpty()) {
			return enterprise;
		}
		for (Element serviceElement : serviceElements) {
			enterprise.addChild(parseService(enterprise, serviceElement));
		}
		return enterprise;
	}

	private static IService parseService(IEnterprise enterprise, Element serviceElement) {
		Service service = new Service();
		service.setName(serviceElement.attributeValue(INode.NAME_ATTRIBUTE));
		service.setParent(enterprise);
		service.setEtage(Integer.valueOf(serviceElement.attributeValue(IService.ETAGE_ATTRIBUTE)));
		service.setColor(parseColor(serviceElement.element(IService.COLOR_ELEMENT)));
		service.setLayout(parseLayout(serviceElement.element(INode.LAYOUT_ELEMENT)));

		List<Element> employeeElements = serviceElement.elements(IEmployee.ELEMENT_NAME);
		if (employeeElements == null || employeeElements.isEmpty()) {
			return service;
		}
		for (Element employeeElement : employeeElements) {
			service.addChild(parseEmployee(service, employeeElement));
		}
		return service;
	}

	private static IEmployee parseEmployee(IService service, Element employeeElement) {
		IEmployee employee = new Employee();
		employee.setName(employeeElement.attributeValue(INode.NAME_ATTRIBUTE));
		employee.setParent(service);
		employee.setLayout(parseLayout(employeeElement.element(INode.LAYOUT_ELEMENT)));
		employee.setPrenom(employeeElement.attributeValue(IEmployee.PRENOM_ATTRIBUTE));
		return employee;
	}

	private static Color parseColor(Element colorElement) {
		int red = Integer.parseInt(colorElement.attributeValue("red"));
		int green = Integer.parseInt(colorElement.attributeValue("green"));
		int blue = Integer.parseInt(colorElement.attributeValue("blue"));
		int alpha = Integer.parseInt(colorElement.attributeValue("alpha"));

		Color color = new Color(null, red, green, blue, alpha);
		return color;
	}

	private static Rectangle parseLayout(Element layoutElement) {
		int x = Integer.parseInt(layoutElement.attributeValue("x"));
		int y = Integer.parseInt(layoutElement.attributeValue("y"));
		int height = Integer.parseInt(layoutElement.attributeValue("height"));
		int width = Integer.parseInt(layoutElement.attributeValue("width"));

		return new Rectangle(x, y, width, height);	
	}

	public static void main(String[] args) throws FileNotFoundException {
		final File xmlFile = new File("C:\\Users\\guo\\Desktop\\temp\\dom4j-xml\\enterprise.xml");
		InputStream inputStream = new FileInputStream(xmlFile);

		IEnterprise enterprise = new DocumentToEnterprise(inputStream).toEnterprise();
		System.out.println(enterprise);
	}
}
