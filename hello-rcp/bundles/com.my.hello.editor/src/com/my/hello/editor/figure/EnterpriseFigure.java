package com.my.hello.editor.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;

public class EnterpriseFigure extends Figure {
	private Label nameLabel = new Label();
	private Label addressLabel = new Label();
	private Label capitalLabel = new Label();
	
	private XYLayout layout;
	
	public EnterpriseFigure() {
		layout = new XYLayout();
		setLayoutManager(layout);
		
		nameLabel.setForegroundColor(ColorConstants.blue);
		add(nameLabel);
		setConstraint(nameLabel, new Rectangle(5, 5, -1, -1));
		
		addressLabel.setForegroundColor(ColorConstants.lightBlue);
		add(addressLabel);
		setConstraint(addressLabel, new Rectangle(5, 17, -1, -1));
		
		capitalLabel.setForegroundColor(ColorConstants.lightBlue);
		add(capitalLabel);
		setConstraint(capitalLabel, new Rectangle(5, 30, -1, -1));
		
		setForegroundColor(ColorConstants.black);
		setBorder(new LineBorder( 5));
	}
	
	public void setLayout(Rectangle rectangle) {
		setBounds(rectangle);
	}
	
	public void setName(String name) {
		nameLabel.setText(name);
	}
	
	public void setAddress(String address) {
		addressLabel.setText(address);
	}
	
	public void setCapital(int capital) {
		capitalLabel.setText("Capital: "+capital);
	}
}
