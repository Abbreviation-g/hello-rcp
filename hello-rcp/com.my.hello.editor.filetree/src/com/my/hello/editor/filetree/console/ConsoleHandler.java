package com.my.hello.editor.filetree.console;

import java.util.Date;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.MessageConsoleStream;

public class ConsoleHandler {
	private MessageConsoleStream consoleStream ;
	private MessageConsoleStream errorConsoleStream;
	public ConsoleHandler(String name) {
		ConsoleFactory.showConsole(name);
		this.consoleStream = ConsoleFactory.getConsole().newMessageStream();
		this.errorConsoleStream = ConsoleFactory.getConsole().newMessageStream();
		errorConsoleStream.setColor(new Color(null, 255, 0, 0));
	}
	
	public void info(final String _message) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				consoleStream.println(String.format("%tT", new Date()) + "(INFO)" + " " + _message);
			}
		});
	}

	public void error(final String _message) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				errorConsoleStream.println(String.format("%tT", new Date()) + "(ERROR)" + " " + _message);
			}
		});
	}
	
	public void dispose() {
		ConsoleFactory.closeConsole();
	}
}
