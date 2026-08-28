package com.my.cef;

import org.eclipse.swt.SWT;
import com.equo.chromium.swt.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
 
public class CEFBrowserTest {
	public static void main(String[] args) {
//		System.setProperty("chromium.path", "C:\\1\\eclipse-rcp-2026-06-R\\workspace-hello-rcp\\.metadata\\.plugins\\org.eclipse.pde.core\\.bundle_pool\\plugins\\com.equo.chromium.cef.win32.win32.x86_64_150.0.0");
		
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(1, false));
		
		Browser browser = new Browser(shell, SWT.NONE);
		browser.setLayoutData(new GridData(GridData.FILL_BOTH));
		browser.setUrl("https://www.baidu.com");
		
		shell.open();
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
