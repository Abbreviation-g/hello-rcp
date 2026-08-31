package com.my.hello.editor.filetree.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.my.hello.editor.filetree.ui.EditorInput;
import com.my.hello.editor.filetree.ui.FileTreeEditorPart;

public class OpenEditorHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			workbenchPage.openEditor(new EditorInput("File Tree"), FileTreeEditorPart.ID);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return null;
	}

}
