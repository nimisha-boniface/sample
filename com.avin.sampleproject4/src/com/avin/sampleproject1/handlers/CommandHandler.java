package com.avin.sampleproject1.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class CommandHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		WizardObject wizardObject = new WizardObject();
		Shell shell = Display.getCurrent().getActiveShell();
		
		WizardDialog wizardDialog = new WizardDialog(shell, wizardObject);
		wizardDialog.open();
		return null;
	}
}
