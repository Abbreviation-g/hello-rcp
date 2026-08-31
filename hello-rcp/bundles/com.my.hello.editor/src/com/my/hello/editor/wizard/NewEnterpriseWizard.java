package com.my.hello.editor.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.ide.DialogUtil;
import org.eclipse.ui.internal.wizards.newresource.ResourceMessages;
import org.eclipse.ui.wizards.newresource.BasicNewFileResourceWizard;

import com.my.hello.editor.model.file.EnterpriseToDocument;
import com.my.hello.editor.model.impl.Enterprise;

@SuppressWarnings("restriction")
public class NewEnterpriseWizard extends BasicNewFileResourceWizard {

	private WizardNewFileCreationPage mainPage;

	@Override
	public void addPages() {
		mainPage = new WizardNewFileCreationPage("newFilePage1", getSelection());//$NON-NLS-1$
		mainPage.setTitle("New Enterprise");
		mainPage.setDescription("Create new enterprise. ");
		mainPage.setFileExtension("enterprise");
		addPage(mainPage);
	}

	@Override
	public boolean performFinish() {
		IFile file = mainPage.createNewFile();
		if (file == null) {
			return false;
		}

		Enterprise enterprise = new Enterprise();
		String enterpriseName = mainPage.getFileName();
		enterpriseName = enterpriseName.substring(0, enterpriseName.lastIndexOf('.'));
		enterprise.setName(enterpriseName);
		EnterpriseToDocument enterpriseToDocument = new EnterpriseToDocument(enterprise);
		try {
			file.setContents(enterpriseToDocument.toInputStream(), true, true, new NullProgressMonitor());
		} catch (CoreException e1) {
			e1.printStackTrace();
		}

		selectAndReveal(file);

		// Open editor on new file.
		IWorkbenchWindow dw = getWorkbench().getActiveWorkbenchWindow();
		try {
			if (dw != null) {
				IWorkbenchPage page = dw.getActivePage();
				if (page != null) {
					IDE.openEditor(page, file, true);
				}
			}
		} catch (PartInitException e) {
			DialogUtil.openError(dw.getShell(), ResourceMessages.FileResource_errorMessage, e.getMessage(), e);
		}

		return true;
	}
}
