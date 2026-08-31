package com.my.hello.editor.model.impl;

import org.eclipse.draw2d.geometry.Rectangle;

import com.my.hello.editor.model.IEnterprise;

public class Enterprise extends Node implements IEnterprise{

	public static final String PROPERTY_CAPITAL = "EnterpriseCapital";
	
	private String address;
	private int capital;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCapital() {
		return capital;
	}

	public void setCapital(int newCapital) {
		int oldCapital = this.capital;
		this.capital = newCapital;
		firePropertyChange(PROPERTY_CAPITAL, oldCapital, newCapital);
	}

	@Override
	public String toString() {
		return "Enterprise [address=" + address + ", capital=" + capital + ", getName()=" + getName()
				+ ", getChildren()=" + getChildren() + "]";
	}

	public static Enterprise createEnterprise() {
		Enterprise enterprise = new Enterprise();
		enterprise.setName("**饭店");
		enterprise.setAddress("**街道七号");
		enterprise.setCapital(8000000);
		
		Service service_QianTang = new Service();
		service_QianTang.setName("大堂");
		service_QianTang.setEtage(2);
		service_QianTang.setLayout(new Rectangle(30, 50, 250, 150));
		Employee empolyee_1 = new Employee();
		empolyee_1.setName("李某a");
		empolyee_1.setPrenom("李");
		empolyee_1.setLayout(new Rectangle(25, 40, 60, 40));
		service_QianTang.addChild(empolyee_1);
		Employee empolyee_2 = new Employee();
		empolyee_2.setName("白某b");
		empolyee_2.setPrenom("白");
		empolyee_2.setLayout(new Rectangle(100, 60, 60, 40));
		service_QianTang.addChild(empolyee_2);
		Employee empolyee_3 = new Employee();
		empolyee_3.setName("吕某c");
		empolyee_3.setPrenom("吕");
		empolyee_3.setLayout(new Rectangle(180, 90, 60, 40));
		service_QianTang.addChild(empolyee_3);
		enterprise.addChild(service_QianTang);
		
		Service service_HouChu = new Service();
		service_HouChu.setName("后厨");
		service_HouChu.setEtage(1);
		service_HouChu.setLayout(new Rectangle(220, 230, 250, 150));
		Employee employee_4 = new Employee();
		employee_4.setName("楚某d");
		employee_4.setPrenom("楚");
		employee_4.setLayout(new Rectangle(40, 70, 60, 40));
		service_HouChu.addChild(employee_4);
		Employee employee_5 = new Employee();
		employee_5.setName("付某e");
		employee_5.setPrenom("付");
		employee_5.setLayout(new Rectangle(170, 100, 60, 40));
		service_HouChu.addChild(employee_5);
		enterprise.addChild(service_HouChu);

		return enterprise;
	}
}
