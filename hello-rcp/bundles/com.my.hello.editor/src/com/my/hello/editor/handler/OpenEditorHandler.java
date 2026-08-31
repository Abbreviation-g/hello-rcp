package com.my.hello.editor.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.my.hello.editor.ui.MyEditorInput;
import com.my.hello.editor.ui.MyGraphicalEditor;

public class OpenEditorHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorInput input = new MyEditorInput("name");
		try {
			workbenchPage.openEditor(input, MyGraphicalEditor.ID);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return null;
	}

}
