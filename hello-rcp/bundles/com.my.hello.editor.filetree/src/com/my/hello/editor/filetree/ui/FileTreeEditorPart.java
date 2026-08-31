package com.my.hello.editor.filetree.ui;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.my.hello.editor.filetree.console.ConsoleHandler;
import com.my.hello.editor.filetree.model.INode;
import com.my.hello.editor.filetree.model.Node;
import com.my.hello.editor.filetree.model.ui.NodeContentProvider;
import com.my.hello.editor.filetree.model.ui.NodeLabelProvider;

public class FileTreeEditorPart extends EditorPart {
	public static final String ID = "com.my.hello.editor.filetree.editor";
	private FileTreeContentOutlinePage contentOutlinePage;
	private TreeViewer viewer;
	private Node root = new Node();
	private ConsoleHandler consoleHandler;

	@Override
	public void doSave(IProgressMonitor monitor) {

	}

	@Override
	public void doSaveAs() {

	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
		getContentOutlinePage();
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	public ConsoleHandler getConsoleHandler() {
		if (this.consoleHandler == null) {
			consoleHandler = new ConsoleHandler("FileTreeEditorPart");
		}
		return consoleHandler;
	}

	private FileTreeContentOutlinePage getContentOutlinePage() {
		if (contentOutlinePage == null) {
			this.contentOutlinePage = new FileTreeContentOutlinePage();
		}
		return contentOutlinePage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if (IContentOutlinePage.class.isAssignableFrom(adapter)) {
			return (T) getContentOutlinePage();
		}
		return super.getAdapter(adapter);
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		Text text = new Text(parent, SWT.BORDER | SWT.SINGLE);
		text.setToolTipText("输入路径，扫描文件夹");
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		Button button = new Button(parent, SWT.PUSH);
		button.setText("扫描");
		GridData buttonGridData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		buttonGridData.widthHint = 100;
		button.setLayoutData(buttonGridData);

		this.viewer = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		Tree tree = viewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 5));

		Button removeButton = new Button(parent, SWT.PUSH);
		removeButton.setText("Remove");
		removeButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		removeButton.addSelectionListener(SelectionListener.widgetSelectedAdapter((e) -> {
			IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
			INode node = (INode) selection.getFirstElement();
			if (node == null)
				return;

			INode parentNode = node.getParent();
			if (parentNode == null) {
				return;
			}
			parentNode.remove(node);

			refreshNode(parentNode);
			getConsoleHandler().error("remove a node: " + node.getFile().getName());
		}));
		Button addButton = new Button(parent, SWT.PUSH);
		addButton.setText("Add");
		addButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		addButton.addSelectionListener(SelectionListener.widgetSelectedAdapter((e) -> {
			IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
			INode parentNode = (INode) selection.getFirstElement();
			if (parentNode == null) {
				return;
			}
			Node child = new Node();
			child.setFile(new File(parentNode.getFile(), "Child"));
			child.setParent(parentNode);

			parentNode.addChild(child);
			refreshNode(parentNode);
			getConsoleHandler().info("add a node: " + child.getFile().getName());
		}));

		button.addSelectionListener(SelectionListener.widgetSelectedAdapter((e) -> {
			INode list = Node.scanFolder(text.getText());
			if (list == null)
				return;

			root.init();
			root.addChild(list);

			refreshNode(null);
			createContextMenu();
			getConsoleHandler().info("show all: " + list.getFile().getName());
		}));

		viewer.setContentProvider(new NodeContentProvider());
		viewer.setLabelProvider(new NodeLabelProvider());
		viewer.setInput(root);

		getSite().setSelectionProvider(viewer);
		getSite().getSelectionProvider().addSelectionChangedListener(getContentOutlinePage());
		createContextMenu();
	}

	private void refreshNode(INode refreshedNode) {
		if (refreshedNode == null) {
			viewer.refresh();
			if (contentOutlinePage.getTreeViewer() != null) {
				contentOutlinePage.getTreeViewer().refresh();
			}
		} else {
			viewer.refresh(refreshedNode);
			if (contentOutlinePage.getTreeViewer() != null) {
				contentOutlinePage.getTreeViewer().refresh(refreshedNode);
			}
		}
	}

	private class TreeViewerListener implements ITreeViewerListener {
		private TreeViewer targetViewer;
		private TreeViewerListener(TreeViewer targetViewer) {
			this.targetViewer = targetViewer;
		}
		@Override
		public void treeCollapsed(TreeExpansionEvent event) {
			if(targetViewer != null) {
				targetViewer.collapseToLevel(event.getElement(), 1);
			}
		}

		@Override
		public void treeExpanded(TreeExpansionEvent event) {
			if(targetViewer != null) {
				targetViewer.expandToLevel(event.getElement(), 1);
			}
		}
	}

	private void createContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		fillContextMenu(menuMgr);

		Menu menu = new Menu(viewer.getControl());
		MenuItem item = new MenuItem(menu, SWT.PUSH);
		item.setText("Test");

		viewer.getControl().setMenu(menuMgr.getMenu());
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void fillContextMenu(IMenuManager menuMgr) {
		menuMgr.add(new Separator("addition"));
		menuMgr.add(new Separator("edit"));
//	      menuMgr.add(removeContributionItem);
		menuMgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		menuMgr.add(new Separator("other"));
	}

	@Override
	public void setFocus() {
		this.viewer.getTree().setFocus();
	}

	@Override
	public void dispose() {
		super.dispose();
		getSite().setSelectionProvider(null);
		getConsoleHandler().dispose();
	}

	private class FileTreeContentOutlinePage extends ContentOutlinePage {

		@Override
		public void createControl(Composite parent) {
			super.createControl(parent);

			TreeViewer contentOutlineViewer = getTreeViewer();
			contentOutlineViewer.setContentProvider(FileTreeEditorPart.this.viewer.getContentProvider());
			contentOutlineViewer.setLabelProvider(FileTreeEditorPart.this.viewer.getLabelProvider());
			contentOutlineViewer.setInput(root);
			getSite().setSelectionProvider(contentOutlineViewer);
			
			contentOutlineViewer.addTreeListener(new TreeViewerListener(viewer));
			viewer.addTreeListener(new TreeViewerListener(contentOutlinePage.getTreeViewer()));
		}

		@Override
		protected TreeViewer getTreeViewer() {
			return super.getTreeViewer();
		}

		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			if (getTreeViewer() == null) {
				return;
			}
			if (!(event.getStructuredSelection().getFirstElement() instanceof Node)) {
				return;
			}
			Viewer editorViewer = FileTreeEditorPart.this.viewer;
			ISelectionProvider selectionProvicer = event.getSelectionProvider();
			if (selectionProvicer.equals(editorViewer)) {
				if (!getTreeViewer().getSelection().equals(event.getSelection())) {
					getTreeViewer().setSelection(event.getSelection());
				}
			} else if (selectionProvicer.equals(getTreeViewer())) {
				if (!editorViewer.getSelection().equals(event.getSelection())) {
					editorViewer.setSelection(event.getSelection());
				}
			}
		}
	}

}
