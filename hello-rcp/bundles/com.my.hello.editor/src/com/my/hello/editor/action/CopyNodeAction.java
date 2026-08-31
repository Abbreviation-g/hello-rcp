package com.my.hello.editor.action;

import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import com.my.hello.editor.command.CopyNodeCommand;
import com.my.hello.editor.model.impl.Node;

/**
 * 13 章，剪切和粘贴
 * 
 * @author guo
 *
 */
public class CopyNodeAction extends SelectionAction {

	public CopyNodeAction(IWorkbenchPart part) {
		super(part);
		setLazyEnablementCalculation(true);
	}

	@Override
	protected void init() {
		super.init();
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setText("Copy");
		setId(ActionFactory.COPY.getId());

		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setHoverImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_BACK));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));

		setEnabled(false);
	}

	private Command createCopyCommand(List<Object> selectedObjects) {
		if (selectedObjects == null || selectedObjects.isEmpty())
			return null;

		CopyNodeCommand command = new CopyNodeCommand();
		Iterator<Object> iterator = selectedObjects.iterator();
		while (iterator.hasNext()) {
			Object selectedObject = iterator.next();
			if(!(selectedObject instanceof EditPart)) {
				continue;
			}
			EditPart editPart = (EditPart) selectedObject;
			Node node = (Node) editPart.getModel();
			if (!command.isCopyableNode(node)) {
				return null;
			}
			command.addElement(node);
		}
		return command;
	}

	@Override
	protected boolean calculateEnabled() {
		@SuppressWarnings("unchecked")
		Command command = createCopyCommand(getSelectedObjects());
		if (command == null)
			return false;
		return command.canExecute();
	}

	@Override
	public void run() {
		@SuppressWarnings("unchecked")
		Command command = createCopyCommand(getSelectedObjects());
		if (command == null || !command.canExecute()) {
			return;
		}
		command.execute();
	}

}
