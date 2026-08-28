package com.my.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.equo.chromium.swt.Browser;

public class CefBrowserViewPart extends ViewPart {
	private Browser browser;
	@Override
	public void createPartControl(Composite parent) {
		this.browser = new Browser(parent, SWT.NONE);
		browser.setLayoutData(new GridData(GridData.FILL_BOTH));
		browser.setUrl("https://www.baidu.com");
	}

	@Override
	public void setFocus() {
		this.browser.setFocus();
	}
}
