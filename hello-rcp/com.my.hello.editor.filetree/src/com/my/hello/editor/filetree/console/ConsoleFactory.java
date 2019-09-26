package com.my.hello.editor.filetree.console;

import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleFactory;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;

public class ConsoleFactory implements IConsoleFactory {
	private static MessageConsole console = null;
	private static final String DEFAULT_CONSOLE_NAME = "Console Info";
	@Override
	public void openConsole() {
		showConsole(DEFAULT_CONSOLE_NAME);
	}

	public static void showConsole(String name) {
			IConsoleManager manager = ConsolePlugin.getDefault().getConsoleManager();
			console = new MessageConsole(name, PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_DEF_VIEW));
			IConsole[] existing = manager.getConsoles();
			boolean exists = false;
			for (int i = 0; i < existing.length; i++) {
				if (console == existing[i])
					exists = true;
			}
			if (!exists) {
				manager.addConsoles(new IConsole[] { console });
			}
			manager.showConsoleView(console);
	}

	public static void closeConsole() {
		IConsoleManager manager = ConsolePlugin.getDefault().getConsoleManager();
		if (console != null) {
			manager.removeConsoles(new IConsole[] { console });
		}
	}

	public static MessageConsole getConsole() {
		return console;
	}
}
