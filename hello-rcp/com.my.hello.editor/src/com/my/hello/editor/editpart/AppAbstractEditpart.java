package com.my.hello.editor.editpart;

import java.beans.PropertyChangeListener;

import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.my.hello.editor.model.Node;

public abstract class AppAbstractEditpart extends AbstractGraphicalEditPart implements PropertyChangeListener {
	
	public void activate() {
		super.activate();
		((Node)getModel()).addPropertyChangeListener(this);
	}
	
	@Override
	public void deactivate() {
		((Node)getModel()).removePropertyChangeListener(this);
		super.deactivate();
	}
	
	/**
	 * 对 GraphicalEditPart 双击事实上生成了一个 REQ_OPEN 类型的 Request。这个 请求没有转化为 EditPolicy，而是被 EditPart 中的 performRequest()方法处理。 
	 */
	@Override
	public void performRequest(Request req) {
		if(req.getType().equals(RequestConstants.REQ_OPEN)) {
			try {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				page.showView(IPageLayout.ID_PROP_SHEET);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
