package com.my.hello.editor.ui;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.actions.ActionFactory;

public class AppContextMenuProvider extends ContextMenuProvider {
	private ActionRegistry actionRegistry;

	public AppContextMenuProvider(EditPartViewer viewer, ActionRegistry registry) {
		super(viewer);
		setActionRegistry(registry);
	}

	public void setActionRegistry(ActionRegistry actionRegistry) {
		this.actionRegistry = actionRegistry;
	}

	public ActionRegistry getActionRegistry() {
		return actionRegistry;
	}

	@Override
	public void buildContextMenu(IMenuManager menu) {
		GEFActionConstants.addStandardActionGroups(menu);
		IAction deleteAction = getActionRegistry().getAction(ActionFactory.DELETE.getId());
		IAction undoAction = getActionRegistry().getAction(ActionFactory.UNDO.getId());
		IAction redoAction = getActionRegistry().getAction(ActionFactory.REDO.getId());
		IAction renameAction = getActionRegistry().getAction(ActionFactory.RENAME.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT, deleteAction);
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT, undoAction);
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT, redoAction);
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT, renameAction);
	}

}
