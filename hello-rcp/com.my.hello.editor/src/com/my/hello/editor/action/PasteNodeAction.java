package com.my.hello.editor.action;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import com.my.hello.editor.command.PasteNodeCommand;

public class PasteNodeAction extends SelectionAction {

	public PasteNodeAction(IWorkbenchPart part) {
		super(part);
		setLazyEnablementCalculation(true);
	}
	
	@Override
	protected void init() {
		super.init();
		setEnabled(false);
		setText("Paste");
		setId(ActionFactory.PASTE.getId());

		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setHoverImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
	}
	
	private Command createPasteCommand() {
		return new PasteNodeCommand();
	}
	
	@Override
	protected boolean calculateEnabled() {
		Command command = createPasteCommand();
		return command!= null && command.canExecute();
	}
	@Override
	public void run() {
		Command command = createPasteCommand();
		if(command != null && command.canExecute()) {
			execute(command);
		}
		
	}

}
