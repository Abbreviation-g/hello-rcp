package com.my.hello.editor.filetree.ui;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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
	private Node root;
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
		if(this.consoleHandler == null) {
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
				viewer.setInput(null);
				return;
			}
			parentNode.remove(node);

			viewer.refresh(parentNode);
			getConsoleHandler().error("remove a node: "+ node.getFile().getName());
		}));
		Button addButton = new Button(parent, SWT.PUSH);
		addButton.setText("Add");
		addButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		addButton.addSelectionListener(SelectionListener.widgetSelectedAdapter((e) -> {
			IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
			INode parentNode = (INode) selection.getFirstElement();
			if (parentNode == null) {
				INode child = new Node();
				child.setFile(new File("Child"));

				root = new Node();
				root.addChild(child);

				viewer.setInput(root);
				
				getConsoleHandler().info("remove a node: "+ child.getFile().getName());
				return;
			}
			INode child = new Node();
			child.setFile(new File(parentNode.getFile(), "Child"));
			child.setParent(parentNode);

			parentNode.addChild(child);
			viewer.refresh(parentNode);
		}));

		button.addSelectionListener(SelectionListener.widgetSelectedAdapter((e) -> {
			INode list = Node.scanFolder(text.getText());
			if (list == null)
				return;

			root = new Node();
			root.addChild(list);

			viewer.setInput(root);
			getContentOutlinePage().setInput(root);
			createContextMenu();
			getConsoleHandler().info("show all: "+ list.getFile().getName());
		}));
		
		viewer.setContentProvider(new NodeContentProvider());
		viewer.setLabelProvider(new NodeLabelProvider());
		
		getSite().setSelectionProvider(viewer);
		getSite().getSelectionProvider().addSelectionChangedListener(getContentOutlinePage());
		createContextMenu();
	}

	private void createContextMenu() {
	      MenuManager menuMgr = new MenuManager("#PopupMenu");
	      menuMgr.setRemoveAllWhenShown(true);
//	      menuMgr.addMenuListener(new IMenuListener() {
//	         public void menuAboutToShow(IMenuManager m) {
//	            FileTreeEditorPart.this.fillContextMenu(m);
//	         }
//	      });
	      fillContextMenu(menuMgr);
	      Menu menu = new Menu(viewer.getControl());
	      MenuItem item = new MenuItem(menu, SWT.PUSH);
	      item.setText("Test");
	      
	      viewer.getControl().setMenu(menu);
	      getSite().registerContextMenu(menuMgr, viewer);
	   }

	   private void fillContextMenu(IMenuManager menuMgr) {
	      menuMgr.add(new Separator("edit"));
//	      menuMgr.add(removeContributionItem);
	      menuMgr.add(new Separator(
	            IWorkbenchActionConstants.MB_ADDITIONS));
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
		private TreeViewer contentOutlineViewer;

		@Override
		public void createControl(Composite parent) {
			super.createControl(parent);

			this.contentOutlineViewer = getTreeViewer();
			contentOutlineViewer.setContentProvider(FileTreeEditorPart.this.viewer.getContentProvider());
			contentOutlineViewer.setLabelProvider(FileTreeEditorPart.this.viewer.getLabelProvider());
			getSite().setSelectionProvider(contentOutlineViewer);
		}

		private void setInput(Object input) {
			contentOutlineViewer.setInput(input);
		}

		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			if (!(event.getStructuredSelection().getFirstElement() instanceof Node)) {
				return;
			}
			Viewer editorViewer = FileTreeEditorPart.this.viewer;
			ISelectionProvider selectionProvicer = event.getSelectionProvider();
			if (selectionProvicer.equals(editorViewer)) {
				if (!contentOutlineViewer.getSelection().equals(event.getSelection())) {
					contentOutlineViewer.setSelection(event.getSelection());
				}
			} else if (selectionProvicer.equals(contentOutlineViewer)) {
				if(!editorViewer.getSelection().equals(event.getSelection())) {
					editorViewer.setSelection(event.getSelection());
				}
			}
		}
	}

}
