package com.avin.sampleproject1.handlers;

import org.eclipse.jface.wizard.Wizard;

public class WizardObject extends Wizard {

	private FirstPage wizardpage1;

	@Override
	public boolean performFinish() {
		return true;
	}

	@Override
	public boolean canFinish() {
		return true;
	}

	@Override
	public void addPages() {
		wizardpage1 = new FirstPage("FirstPage");
		addPage(wizardpage1);
	}


}
