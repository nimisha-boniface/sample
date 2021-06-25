package com.avin.sampleproject1.handlers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.coordinate.PositionCoordinate;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.TextCellEditor;
import org.eclipse.nebula.widgets.nattable.layer.ILayerListener;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.layer.event.ILayerEvent;
import org.eclipse.nebula.widgets.nattable.selection.event.CellSelectionEvent;
import org.eclipse.nebula.widgets.nattable.selection.event.RowSelectionEvent;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.widget.EditModeEnum;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class FirstPage extends org.eclipse.jface.wizard.WizardPage {

	public HashMap<String, String> map = new HashMap<String, String>();
	private Label labelName;
	private Label labelPlace;
	private Text textName;
	private Text textPlace;
	private ControlDecoration errorDec_Textname;
	private ControlDecoration errorDec_Placename;
	private boolean isNameValid = false;
	private boolean isPalaceValid = false;
	private UserToChannelNattable nattableInstance;

	protected FirstPage(String pageName) {
		super(pageName);
		setTitle("First Page");
		setDescription("This is the first page");
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		composite.setLayout(gridLayout);

		 nattableInstance = new UserToChannelNattable();
		nattableInstance.createNattable(composite);
		natTableListener();
		setControl(composite);
	}
	
	private void natTableListener() {
		nattableInstance.getNattable().addLayerListener(new ILayerListener() {
			@Override
			public void handleLayerEvent(ILayerEvent event) {
				if (event instanceof CellSelectionEvent
						&& ((CellSelectionEvent) event).getColumnPosition() > -1) {
					if (nattableInstance.getBodyLayerStack().getSelectionLayer()
							.getFullySelectedRowPositions().length == 0) {
						Collection<ILayerCell> selectedCells = nattableInstance.getBodyLayerStack().getSelectionLayer()
								.getSelectedCells();
						for (Iterator<ILayerCell> iterator = selectedCells.iterator(); iterator.hasNext();) {
							ILayerCell ilayerCelll = (ILayerCell) iterator.next();
							int colPos = ilayerCelll.getColumnPosition();
							int rowPos = ilayerCelll.getRowPosition();
							String rowName = nattableInstance.getBodyLayerStack().getFilterList()
								.get(rowPos);
							if(rowName.equals("nimi")){
								
							}
							
						}
//						PositionCoordinate[] posCordinate = nat 
					}
				} 
			}
		});
	}
	
	


}
