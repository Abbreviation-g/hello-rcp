package com.my.hello.editor.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.my.hello.editor.Activator;
import com.my.hello.editor.model.impl.Node;
import com.my.hello.editor.ui.RenameWizard;

public class RenameAction extends SelectionAction {

	public RenameAction(IWorkbenchPart part) {
		super(part);
		setLazyEnablementCalculation(false);
	}

	@Override
	protected void init() {
		setText("Rename...");
		setToolTipText("Rename");
		setId(ActionFactory.RENAME.getId());
		ImageDescriptor icon = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/rename.png");
		if(icon != null)
			setImageDescriptor(icon);
		setEnabled(false);
	}
	
	@Override
	protected boolean calculateEnabled() {
		Command command = createRenameCommand("");
		if(command == null)
			return false;
		return true;
	}

	public Command createRenameCommand(String name) {
		Request renameRequest = new Request("rename");
		Map<String,String> requestData = new HashMap<>();
		requestData.put("newName", name);
		renameRequest.setExtendedData(requestData);
		
		@SuppressWarnings("unchecked")
		List<Object> objects = getSelectedObjects();
		if(objects.isEmpty()) {
			return null;
		}
		if(objects.get(0) instanceof EditPart) {
			EditPart editPart = (EditPart) objects.get(0);
			Command command = editPart.getCommand(renameRequest);
			return command;
		} 
		return null;
	}
	
	@Override
	public void run() {
		Node node = getSelectedNode();
		RenameWizard wizard = new RenameWizard(node.getName());
		WizardDialog dialog = new WizardDialog(getWorkbenchPart().getSite().getShell(), wizard);
		dialog.create();
		dialog.getShell().setSize(400,320);
		dialog.setTitle("Rename Wizard");
		dialog.setMessage("Rename");
		if(dialog.open() == WizardDialog.OK) {
			String newName = wizard.getRenameValue();
			execute(createRenameCommand(newName));
		}
	}
	
	private Node getSelectedNode() {
		@SuppressWarnings("unchecked")
		List<Object> objects = getSelectedObjects();
		if(objects.isEmpty())
			return null;
		if(!(objects.get(0) instanceof EditPart)) {
			return null;
		}
		EditPart editPart = (EditPart) objects.get(0);
		return (Node) editPart.getModel();
	}
	
}
