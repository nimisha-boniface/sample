/*********************************************************************
 * File Name        : CompositeComboBoxFilterRowConfiguration.java
 * Revision History : 
 * Revision    Date         Author         Description
 *********************************************************************
 * 1.0.0       02-Oct-18    Nimisha        Initial version
 *********************************************************************
 * Copyright (c) 2018 AVIN Systems Private Limited.
*********************************************************************/
package com.avin.sampleproject1.handlers;
import org.eclipse.nebula.widgets.nattable.edit.editor.IComboBoxDataProvider;
import org.eclipse.nebula.widgets.nattable.filterrow.combobox.ComboBoxFilterIconPainter;
import org.eclipse.nebula.widgets.nattable.filterrow.combobox.ComboBoxFilterRowConfiguration;
import org.eclipse.nebula.widgets.nattable.filterrow.combobox.FilterRowComboBoxCellEditor;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
/**
 * @author nimisha_boniface
 *
 */
public class CustomComboBoxFilterRowConfiguration  extends ComboBoxFilterRowConfiguration{
	
	public CustomComboBoxFilterRowConfiguration(IComboBoxDataProvider comboBoxDataProvider) {
      this.cellEditor = new FilterRowComboBoxCellEditor(comboBoxDataProvider, 5);
      this.filterIconPainter =
          new ComboBoxFilterIconPainter(comboBoxDataProvider, GUIHelper.getImage("filter"), null);

	}	
}
