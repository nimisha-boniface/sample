package com.avin.sampleproject.test;

import static org.junit.Assert.assertNotNull;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class SWTWizardTest {

	private static SWTBot swtBot = null;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		swtBot = new SWTBot();
	}

	@Test
	public void test() {
	   SWTBotMenu newButton = swtBot.menu("Sample Menu").contextMenu("Sample Command").click();
	   assertNotNull(newButton);
	   SWTBotText nameLabel = swtBot.textWithLabel("NAME : ");
	   assertNotNull(nameLabel);
	   nameLabel.setText("Nimisha");
	   SWTBotText placeLabel = swtBot.textWithLabel("PLACE : ");
	   assertNotNull(placeLabel);
	   placeLabel.setText("Bangalore");
	   swtBot.button("Next >").click();
	   swtBot.sleep(3000);
	   swtBot.button("Finish").click();
	}

}
