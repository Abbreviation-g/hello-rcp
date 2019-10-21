package com.my.hello.editor.ui;

import java.io.InputStream;
import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.parts.ScrollableThumbnail;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.my.hello.editor.Activator;
import com.my.hello.editor.action.RenameAction;
import com.my.hello.editor.command.NodeCreationFactory;
import com.my.hello.editor.editpart.AppEditpartFactory;
import com.my.hello.editor.editpart.tree.AppTreeEditpartFactory;
import com.my.hello.editor.listener.MyTemplateTransferDropListener;
import com.my.hello.editor.model.IEnterprise;
import com.my.hello.editor.model.file.DocumentToEnterprise;
import com.my.hello.editor.model.file.EnterpriseToDocument;
import com.my.hello.editor.model.impl.Employee;
import com.my.hello.editor.model.impl.Enterprise;
import com.my.hello.editor.model.impl.Service;

public class MyGraphicalEditor extends GraphicalEditorWithPalette {

	public static final String ID = "com.my.hello.editor.ui.mygraphicaleditor";

	private IEnterprise model;
	private KeyHandler keyHandler;

	private boolean dirty = false;

	public MyGraphicalEditor() {
		setEditDomain(new DefaultEditDomain(this));
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);

		setPartName(getEditorInput().getName());
		setTitleToolTip(getEditorInput().getToolTipText());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void createActions() {
		super.createActions();
		ActionRegistry registry = getActionRegistry();
		IAction action = new RenameAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setEditPartFactory(new AppEditpartFactory());

		ScalableRootEditPart rootEditPart = new ScalableRootEditPart();
		viewer.setRootEditPart(rootEditPart);

		ZoomManager manager = rootEditPart.getZoomManager();
		getActionRegistry().registerAction(new ZoomInAction(manager));
		getActionRegistry().registerAction(new ZoomOutAction(manager));

		double[] zoomLevels = new double[] { 0.25, 0.5, 0.75, 1.0, 1.5, 2.0, 2.5, 3.0, 4.0, 5.0, 10.0, 20.0 };
		manager.setZoomLevels(zoomLevels);

		manager.setZoomLevelContributions(
				Arrays.asList(ZoomManager.FIT_ALL, ZoomManager.FIT_HEIGHT, ZoomManager.FIT_WIDTH));

		this.keyHandler = new KeyHandler();
		keyHandler.put(KeyStroke.getPressed(SWT.ZoomChanged, 0),
				getActionRegistry().getAction(GEFActionConstants.ZOOM_IN));
		keyHandler.put(KeyStroke.getPressed(SWT.ZoomChanged, 0),
				getActionRegistry().getAction(GEFActionConstants.ZOOM_OUT));

		viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.CTRL), MouseWheelZoomHandler.SINGLETON);
		viewer.setKeyHandler(keyHandler);

		ContextMenuProvider menuProvider = new AppContextMenuProvider(viewer, getActionRegistry());
		viewer.setContextMenu(menuProvider);
	}

	@Override
	protected void initializeGraphicalViewer() {
		GraphicalViewer viewer = getGraphicalViewer();
		IEditorInput editorInput = getEditorInput();
		if (editorInput instanceof IFileEditorInput) {
			IFileEditorInput fileEditorInput = (IFileEditorInput) editorInput;
			try {
				InputStream fileInputStream = fileEditorInput.getFile().getContents();
				DocumentToEnterprise documentToEnterprise = new DocumentToEnterprise(fileInputStream);
				this.model = documentToEnterprise.toEnterprise();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		} else {
			this.model = Enterprise.createEnterprise();
		}
		this.model.addPropertyChangeListener((event) -> {
			if (event.getNewValue() != null) {
				if (!event.getNewValue().equals(event.getOldValue())) {
					dirty = true;
					firePropertyChange(IEditorPart.PROP_DIRTY);
				}
			} else if (event.getOldValue() != null) {
				if (!event.getOldValue().equals(event.getNewValue())) {
					dirty = true;
					firePropertyChange(IEditorPart.PROP_DIRTY);
				}
			}
		});
		viewer.setContents(model);
		// 12章，拖放
		viewer.addDropTargetListener(new MyTemplateTransferDropListener(viewer));
	}
	
	@Override
	protected void initializePaletteViewer() {
		super.initializePaletteViewer();
		// 12章，拖放
		getPaletteViewer().addDragSourceListener(new TemplateTransferDragSourceListener(getPaletteViewer()));
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		IEditorInput editorInput = getEditorInput();
		if (editorInput instanceof IFileEditorInput) {
			IFileEditorInput fileEditorInput = (IFileEditorInput) editorInput;
			IFile file = fileEditorInput.getFile();
			EnterpriseToDocument enterpriseToDocument = new EnterpriseToDocument(model);
			InputStream inputStream = enterpriseToDocument.toInputStream();
			try {
				file.setContents(inputStream, true, true, monitor);
				this.dirty = false;
				firePropertyChange(PROP_DIRTY);
			} catch (CoreException e) {
				ErrorDialog.openError(getSite().getShell(), "Error", "", e.getStatus());
			}
		}
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class type) {
		if (type == ZoomManager.class) {
			return ((ScalableRootEditPart) getGraphicalViewer().getRootEditPart()).getZoomManager();
		} else if (type == IContentOutlinePage.class) {
			return new OutlinePage();
		}
		return super.getAdapter(type);
	}

	protected class OutlinePage extends ContentOutlinePage {
		private ScrollableThumbnail thumbnail;
		private DisposeListener disposeListener;

		private SashForm sash;

		public OutlinePage() {
			super(new TreeViewer());
		}

		@Override
		public void createControl(Composite parent) {
			sash = new SashForm(parent, SWT.VERTICAL);
			getViewer().createControl(sash);
			getViewer().setEditDomain(getEditDomain());
			getViewer().setEditPartFactory(new AppTreeEditpartFactory());
			getViewer().setContents(model);
			getSelectionSynchronizer().addViewer(getViewer());

			// 增加缩略图
			Canvas canvas = new Canvas(sash, SWT.BORDER);
			LightweightSystem lws = new LightweightSystem(canvas);
			thumbnail = new ScrollableThumbnail(
					(Viewport) ((ScalableRootEditPart) getGraphicalViewer().getRootEditPart()).getFigure());
			thumbnail.setSource(((ScalableRootEditPart) getGraphicalViewer().getRootEditPart())
					.getLayer(LayerConstants.PRINTABLE_LAYERS));
			lws.setContents(thumbnail);
			disposeListener = (event) -> {
				if (thumbnail != null) {
					thumbnail.deactivate();
					thumbnail = null;
				}
			};
			getGraphicalViewer().getControl().addDisposeListener(disposeListener);
		}

		@Override
		public void init(IPageSite pageSite) {
			super.init(pageSite);
			IActionBars bars = getSite().getActionBars();
			bars.setGlobalActionHandler(ActionFactory.UNDO.getId(),
					getActionRegistry().getAction(ActionFactory.UNDO.getId()));
			bars.setGlobalActionHandler(ActionFactory.REDO.getId(),
					getActionRegistry().getAction(ActionFactory.REDO.getId()));
			bars.setGlobalActionHandler(ActionFactory.DELETE.getId(),
					getActionRegistry().getAction(ActionFactory.DELETE.getId()));
			bars.updateActionBars();
			getViewer().setKeyHandler(keyHandler);

			ContextMenuProvider menuProvider = new AppContextMenuProvider(getViewer(), getActionRegistry());
			getViewer().setContextMenu(menuProvider);
		}

		@Override
		public Control getControl() {
			return this.sash;
		}

		@Override
		public void dispose() {
			getSelectionSynchronizer().removeViewer(getViewer());
			if (getGraphicalViewer().getControl() != null && !getGraphicalViewer().getControl().isDisposed()) {
				getGraphicalViewer().getControl().removeDisposeListener(disposeListener);
			}
			super.dispose();
		}
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		PaletteRoot paletteRoot = new PaletteRoot();
		PaletteGroup manipGroup = new PaletteGroup("编辑对象工具");
		paletteRoot.add(manipGroup);

		SelectionToolEntry selectionToolEntry = new SelectionToolEntry();
		manipGroup.add(selectionToolEntry);// 单选
		manipGroup.add(new MarqueeToolEntry());// 多选
		paletteRoot.setDefaultEntry(selectionToolEntry);

		paletteRoot.add(new PaletteSeparator());

		// 添加新的图片元素
		PaletteGroup insertGroup = new PaletteGroup("创建元素工具");
		ImageDescriptor serviceSmall = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
				"icons/service_small.png");
		ImageDescriptor serviceLarge = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
				"icons/service_large.png");
		CreationToolEntry serviceCreationToolEntry = new CombinedTemplateCreationEntry("Service", "创建一个Service",
				new NodeCreationFactory(Service.class), serviceSmall, serviceLarge);
		insertGroup.add(serviceCreationToolEntry);

		ImageDescriptor employeeSmall = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
				"icons/employee_small.png");
		ImageDescriptor employeeLarge = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
				"icons/employee_large.png");
		CreationToolEntry employeeCreationToolEntry = new CombinedTemplateCreationEntry("Employee", "创建一个Employee",
				new NodeCreationFactory(Employee.class), employeeSmall, employeeLarge);
		insertGroup.add(employeeCreationToolEntry);

		paletteRoot.add(insertGroup);

		return paletteRoot;
	}
}
