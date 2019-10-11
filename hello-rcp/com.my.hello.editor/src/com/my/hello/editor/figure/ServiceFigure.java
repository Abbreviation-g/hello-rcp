package com.my.hello.editor.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;

import com.my.hello.editor.model.Service;

public class ServiceFigure extends Figure {
	private Label nameLabel = new Label();
	private Label etageLabel = new Label();
	
	public ServiceFigure(){
		XYLayout layout = new XYLayout();
		setLayoutManager(layout);
		nameLabel.setForegroundColor(ColorConstants.darkGray);
		add(nameLabel, ToolbarLayout.ALIGN_CENTER);
		setConstraint(nameLabel, new Rectangle(5, 17, -1, -1));
		etageLabel.setForegroundColor(ColorConstants.black);
		add(etageLabel, ToolbarLayout.ALIGN_CENTER);
		setConstraint(etageLabel, new Rectangle(5, 5, -1, -1));
		
		//设置随机颜色
//		Random random = new Random();
//		setForegroundColor(new Color(null, random.nextInt(128), random.nextInt(128), random.nextInt(128)));
//		setBackgroundColor(new Color(null, random.nextInt(128)+128, random.nextInt(128)+128, random.nextInt(128)+128));
		
		Service service = new Service();
		setForegroundColor(ColorConstants.black);
		setBackgroundColor(service.getColor());
		
		setBorder(new LineBorder(1));
		setOpaque(true);//不透明
	}
	public void setName(String text) {
		nameLabel.setText(text);
	}
	public void setEtage(int etage){
		etageLabel.setText("Etage: "+etage);
	}
	public void setLayout(Rectangle rectangle) {
		getParent().setConstraint(this, rectangle);
	}
}
