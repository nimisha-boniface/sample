package com.avin.sampleproject1.handlers;

import java.util.ArrayList;

import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.CheckBoxCellEditor;
import org.eclipse.nebula.widgets.nattable.edit.editor.ComboBoxCellEditor;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.painter.cell.CheckBoxPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.ComboBoxPainter;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;

public class CustomEditorConfiguration extends AbstractRegistryConfiguration {
	CustomBodyLayerStack<String> bodyLayerStack;
	private String[] propertyNames;

	public CustomEditorConfiguration(String[] propertyNames, CustomBodyLayerStack<String> bodyLayerStack) {
		this.propertyNames = propertyNames;
		this.bodyLayerStack = bodyLayerStack;
	}

	@Override
	public void configureRegistry(IConfigRegistry configRegistry) {
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITABLE_RULE, IEditableRule.ALWAYS_EDITABLE);
		for (String property : propertyNames) {
			if (property.equalsIgnoreCase("One")) {
				configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITABLE_RULE,
						IEditableRule.NEVER_EDITABLE, DisplayMode.NORMAL, property);
			} else {
				configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITABLE_RULE,
						IEditableRule.ALWAYS_EDITABLE, DisplayMode.NORMAL, property);
			}
		}
		settingConfigLabels(configRegistry);
	}

	private void settingConfigLabels(IConfigRegistry configRegistry) {
		(bodyLayerStack.getBodyDataLayer())
				.setConfigLabelAccumulator((LabelStack configLabels, int columnPosition, int rowPosition) ->{
					
					configLabels.addLabel(propertyNames[columnPosition]);
					
//					String val = (String) bodyLayerStack.getBodyDataLayer().getDataValue(columnPosition, rowPosition);
					String rowname = bodyLayerStack.getFilterList()
							.get(rowPosition);

					String columnName = propertyNames[columnPosition];
					if(columnName.equals("one")){
						bodyLayerStack.getBodyDataLayer().setDataValue(columnPosition, rowPosition,rowname);
					}else{
						configRegistry.unregisterConfigAttribute(CellConfigAttributes.CELL_PAINTER,
							     DisplayMode.NORMAL, propertyNames[columnPosition]);
						 if(rowname.equals("ramen")){
							configRegistry.unregisterConfigAttribute(CellConfigAttributes.CELL_PAINTER,
							DisplayMode.NORMAL, propertyNames[columnPosition]);
//							bodyLayerStack.getBodyDataLayer().setDataValue(columnPosition, rowPosition, "combobox"+columnPosition+rowPosition);
							ArrayList<String> comboItems = new ArrayList<>();
						    comboItems.add("A");
						    comboItems.add("B");
						    ComboBoxCellEditor comboBoxCellEditor = new ComboBoxCellEditor(comboItems, -1);
						    comboBoxCellEditor.setFreeEdit(false);
						    configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, comboBoxCellEditor,
						        DisplayMode.EDIT, propertyNames[columnPosition]);
						   
						    configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, new ComboBoxPainter(),
						        DisplayMode.NORMAL, propertyNames[columnPosition]);

						}else {
							configRegistry.unregisterConfigAttribute(CellConfigAttributes.CELL_PAINTER,
								     DisplayMode.NORMAL, propertyNames[columnPosition]);
							
						}
						
					}
					
				});
	}
	
	
}
