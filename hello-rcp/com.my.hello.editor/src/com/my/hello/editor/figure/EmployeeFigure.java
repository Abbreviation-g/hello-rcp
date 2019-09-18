package com.my.hello.editor.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Rectangle;

public class EmployeeFigure extends Figure {
	private Label nameLabel = new Label();
	private Label firstNameLabel = new Label();

	public EmployeeFigure() {
		ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);
		firstNameLabel.setForegroundColor(ColorConstants.black);
		add(firstNameLabel, ToolbarLayout.ALIGN_CENTER);
		nameLabel.setForegroundColor(ColorConstants.darkGray);
		add(nameLabel, ToolbarLayout.ALIGN_CENTER);
		setForegroundColor(ColorConstants.darkGray);
		setBackgroundColor(ColorConstants.lightGray);
		setBorder(new LineBorder(1));
		setOpaque(true);
	}

	public void setName(String text) {
		nameLabel.setText(text);
	}

	public void setFirstName(String text) {
		firstNameLabel.setText(text);
	}
	
	public void setLayout(Rectangle rectangle) {
		getParent().setConstraint(this, rectangle);
	}
}
