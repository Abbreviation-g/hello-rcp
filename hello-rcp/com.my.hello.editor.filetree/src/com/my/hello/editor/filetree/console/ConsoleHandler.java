package com.my.hello.editor.filetree.console;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.MessageConsoleStream;

public class ConsoleHandler {
	private MessageConsoleStream consoleStream ;
	public ConsoleHandler(String name) {
		ConsoleFactory.showConsole(name);
		this.consoleStream = ConsoleFactory.getConsole().newMessageStream();
	}
	
	public void info(final String _message) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				consoleStream.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + "(INFO)" + " " + _message);
			}
		});
	}

	public void error(final String _message) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				consoleStream.setColor(new Color(null, 255, 0, 0));
				consoleStream.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + "(ERROR)" + " " + _message);
			}
		});
	}
	
	public void dispose() {
		ConsoleFactory.closeConsole();
	}
}
