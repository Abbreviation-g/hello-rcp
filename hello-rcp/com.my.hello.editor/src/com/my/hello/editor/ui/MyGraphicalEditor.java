package com.my.hello.editor.ui;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.swt.widgets.Composite;

import com.my.hello.editor.editpart.AppEditpartFactory;
import com.my.hello.editor.model.Employee;
import com.my.hello.editor.model.Enterprise;
import com.my.hello.editor.model.Service;

public class MyGraphicalEditor extends GraphicalEditor {

	public static final String ID = "com.my.hello.editor.ui.mygraphicaleditor";

	public MyGraphicalEditor() {
		setEditDomain(new DefaultEditDomain(this));
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);

		setPartName(getEditorInput().getName());
		setTitleToolTip(getEditorInput().getToolTipText());
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setEditPartFactory(new AppEditpartFactory());
	}

	@Override
	protected void initializeGraphicalViewer() {
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setContents(createEnterprise());
	}

	public Enterprise createEnterprise() {
		Enterprise enterprise = new Enterprise();
		enterprise.setName("同福客栈");
		enterprise.setAddress("西绒线胡同七号");
		enterprise.setCapital(8000000);
		Service service_QianTang = new Service();
		service_QianTang.setName("前堂");
		service_QianTang.setEtage(2);
		service_QianTang.setLayout(new Rectangle(30, 50, 250, 150));
		Employee empolyee_1 = new Employee();
		empolyee_1.setName("掌柜");
		empolyee_1.setPrenom("佟");
		empolyee_1.setLayout(new Rectangle(25, 40, 60, 40));
		service_QianTang.addChild(empolyee_1);
		Employee empolyee_2 = new Employee();
		empolyee_2.setName("展堂");
		empolyee_2.setPrenom("白");

		empolyee_2.setLayout(new Rectangle(100, 60, 60, 40));
		service_QianTang.addChild(empolyee_2);
		Employee empolyee_3 = new Employee();
		empolyee_3.setName("秀才");
		empolyee_3.setPrenom("吕");
		empolyee_3.setLayout(new Rectangle(180, 90, 60, 40));
		service_QianTang.addChild(empolyee_3);
		enterprise.addChild(service_QianTang);
		Service service_HouChu = new Service();
		service_HouChu.setName("后厨");
		service_HouChu.setEtage(1);
		service_HouChu.setLayout(new Rectangle(220, 230, 250, 150));
		Employee employee_4 = new Employee();
		employee_4.setName("大嘴");
		employee_4.setPrenom("李");
		employee_4.setLayout(new Rectangle(40, 70, 60, 40));
		service_HouChu.addChild(employee_4);
		Employee employee_5 = new Employee();
		employee_5.setName("芙蓉");
		employee_5.setPrenom("郭");
		employee_5.setLayout(new Rectangle(170, 100, 60, 40));
		service_HouChu.addChild(employee_5);
		enterprise.addChild(service_HouChu);

		return enterprise;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {

	}

}
