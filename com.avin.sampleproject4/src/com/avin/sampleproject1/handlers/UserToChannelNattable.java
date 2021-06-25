/***************************************************************************
 * File Name        : ECUMappingNatTable.java
 * Revision History : 
 * Revision       Date          Author      Description
 ***************************************************************************
 * 1.0.0          30-SEP-20    Venkatesh veera     Initial version
 ***************************************************************************
 * Copyright (c) 2020 AVIN Systems Private Limited.
 ***************************************************************************/

package com.avin.sampleproject1.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.ConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.DefaultNatTableStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.edit.editor.IComboBoxDataProvider;
import org.eclipse.nebula.widgets.nattable.extension.glazedlists.GlazedListsSortModel;
import org.eclipse.nebula.widgets.nattable.extension.glazedlists.filterrow.ComboBoxFilterRowHeaderComposite;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultColumnHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultCornerDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultRowHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.CornerLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.RowHeaderLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.config.DefaultRowStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.config.DefaultColumnHeaderStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.sort.SortHeaderLayer;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.style.Style;
import org.eclipse.nebula.widgets.nattable.tooltip.NatTableContentTooltip;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

/**
 * NatTable Class.
 * 
 * @author Nimisha
 *
 */
public class UserToChannelNattable {
	/**
	 * selected module.
	 */
	private CustomBodyLayerStack<String> bodyLayerStack;
	private ComboBoxFilterRowHeaderComposite<String> filterRowHeaderLayer;
	public CustomEditorConfiguration editorInstance;
	private NatTable nattable;
	private ConfigRegistry configRegistry;

	public UserToChannelNattable() {

	}

	/**
	 * Method for creating nattable.
	 */
	public NatTable createNattable(Composite parent) {
		 String[] properties = {"one","two"};
//		ArrayList<EcucContainerValue> channelList = UiUtil.getInstance().getChannelList();
//		Map<String, String> propertyLabelMap = getInitListPropertyToLabelMap(channelList);
		Map<String, String> propertyLabelMap = new HashMap<>();
		propertyLabelMap.put("one", "one");
		propertyLabelMap.put("two", "two");
		List<String > listOfUsers = new ArrayList<>();
		listOfUsers.add("ramen");
		listOfUsers.add("nimi");
		listOfUsers.add("jean");
		listOfUsers.add("boney");
		nattable = constructNattable("User To channel Mapping", parent, properties, propertyLabelMap, listOfUsers);
		return nattable;
	}

	/**
	 * Creates NatTable in the right form .
	 * 
	 * @param tableName
	 * @param composite
	 * @param propertyNamesString
	 * @param listOfUsers
	 * @param properties
	 * @param propertyLabelMap
	 * @return NatTable
	 */
	@SuppressWarnings("unchecked")
	private NatTable constructNattable(String tableName, Composite composite, String[] propertyNames,
			Map<String, String> propertyToLabelMap, List<String> listOfUsers) {
		Composite nattableComposite = new Composite(composite, SWT.BORDER);
		nattableComposite.setLayout(new GridLayout());
		nattableComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		 configRegistry = new ConfigRegistry();

		@SuppressWarnings("rawtypes")
		IColumnPropertyAccessor columnPropertyAccessor = new ColumnPropAccessor(propertyNames);
		EventList<String> eventList = GlazedLists.eventList(listOfUsers);
		bodyLayerStack = new CustomBodyLayerStack<String>(eventList, columnPropertyAccessor);

		
		// build the column header layer stack
		IDataProvider columnHeaderDataProvider = new DefaultColumnHeaderDataProvider(propertyNames, propertyToLabelMap);
		DataLayer columnHeaderDataLayer = new DataLayer(columnHeaderDataProvider);
		ColumnHeaderLayer columnHeaderLayer = new ColumnHeaderLayer(columnHeaderDataLayer,
				bodyLayerStack.getViewportLayer(), bodyLayerStack.getSelectionLayer());

		GlazedListsSortModel<String> sortModel = new GlazedListsSortModel<String>(bodyLayerStack.getSortedList(),
				columnPropertyAccessor, configRegistry, columnHeaderDataLayer);
		final SortHeaderLayer<String> sortHeaderLayer = new SortHeaderLayer<String>(columnHeaderLayer, sortModel);

		filterRowHeaderLayer = new ComboBoxFilterRowHeaderComposite<String>(bodyLayerStack.getFilterList(),
				bodyLayerStack.getBodyDataLayer(), bodyLayerStack.getSortedList(), columnPropertyAccessor,
				sortHeaderLayer, columnHeaderDataProvider, configRegistry, false);
		final IComboBoxDataProvider comboBoxDataProvider = filterRowHeaderLayer.getComboBoxDataProvider();
		CustomComboBoxFilterRowConfiguration comboBoxFltrCfg = new CustomComboBoxFilterRowConfiguration(
				comboBoxDataProvider);

		filterRowHeaderLayer.addConfiguration(comboBoxFltrCfg);

		// build the row header layer stack
		IDataProvider rowHeaderDataProvider = new DefaultRowHeaderDataProvider(bodyLayerStack.getBodyDataProvider());
		DataLayer rowHeaderDataLayer = new DataLayer(rowHeaderDataProvider, 40, 20);
		ILayer rowHeaderLayer = new RowHeaderLayer(rowHeaderDataLayer, bodyLayerStack.getViewportLayer(),
				bodyLayerStack.getSelectionLayer());
		// build the corner layer stack
		IDataProvider cornerDataProvider = new DefaultCornerDataProvider(columnHeaderDataProvider,
				rowHeaderDataProvider);

		DataLayer cornerDataLayer = new DataLayer(cornerDataProvider);
		ILayer cornerLayer = new CornerLayer(cornerDataLayer, rowHeaderLayer, filterRowHeaderLayer);

		// create the grid layer composed with the prior created layer stacks
		GridLayer gridLayer = new GridLayer(bodyLayerStack, filterRowHeaderLayer, rowHeaderLayer, cornerLayer);
		
	    DefaultRowStyleConfiguration rowStyleConfiguration = new DefaultRowStyleConfiguration();
	    rowStyleConfiguration.oddRowBgColor = GUIHelper.COLOR_WHITE;
	    rowStyleConfiguration.evenRowBgColor = GUIHelper.COLOR_WHITE;
	    
	    DefaultColumnHeaderStyleConfiguration headerStyle = setHeaderStyle();
		NatTable nattable = new NatTable(nattableComposite, gridLayer, false);
		nattable.setConfigRegistry(configRegistry);
		nattable.addConfiguration(rowStyleConfiguration);
		nattable.addConfiguration(headerStyle);
		nattable.addConfiguration(new DefaultNatTableStyleConfiguration());

	    setTooltip(propertyToLabelMap, nattable);
		nattable.setConfigRegistry(configRegistry);
		editorInstance = new CustomEditorConfiguration(propertyNames, bodyLayerStack);
		nattable.addConfiguration(editorInstance);
		nattable.configure();
		GridDataFactory.fillDefaults().grab(true, true).applyTo(nattable);
		return nattable;
	}


	/**
	 * Method for setting header style.
	 */
	private DefaultColumnHeaderStyleConfiguration setHeaderStyle() {
		DefaultColumnHeaderStyleConfiguration headerStyle = new DefaultColumnHeaderStyleConfiguration() {
	    	 @Override
			public void configureRegistry(IConfigRegistry configRegistry) {
				super.configureRegistry(configRegistry);
				Style cellStyle = new Style();
				cellStyle.setAttributeValue(CellStyleAttributes.FONT,
						GUIHelper.getFont(new FontData[] { new FontData("Calibri", 10, SWT.BOLD) }));
				cellStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, this.bgColor);
				cellStyle.setAttributeValue(CellStyleAttributes.FOREGROUND_COLOR, this.fgColor);
				cellStyle.setAttributeValue(CellStyleAttributes.GRADIENT_BACKGROUND_COLOR, this.gradientBgColor);
				cellStyle.setAttributeValue(CellStyleAttributes.GRADIENT_FOREGROUND_COLOR, this.gradientFgColor);
				cellStyle.setAttributeValue(CellStyleAttributes.HORIZONTAL_ALIGNMENT, this.hAlign);
				cellStyle.setAttributeValue(CellStyleAttributes.VERTICAL_ALIGNMENT, this.vAlign);
				cellStyle.setAttributeValue(CellStyleAttributes.BORDER_STYLE, this.borderStyle);
				configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.NORMAL,
						GridRegion.COLUMN_HEADER);
			}
		};
		return headerStyle;
	}
	/**
	 * Method for setting tooltip.
	 */
	private void setTooltip(Map<String, String> propertyToLabelMap, NatTable nattable) {
		new NatTableContentTooltip(nattable) {
			@Override
			protected String getText(Event event) {
				String val = "";
				int rowIndex = natTable.getRowIndexByPosition(this.natTable.getRowPositionByY(event.y));
				int columnIndex = natTable.getColumnIndexByPosition(natTable.getColumnPositionByX(event.x));
				if(rowIndex != -1){
				if (bodyLayerStack.getBodyDataLayer().getDataValue(columnIndex, rowIndex) != null) {
					String tooltip = bodyLayerStack.getBodyDataLayer().getDataValue(columnIndex, rowIndex).toString();
					if (tooltip != "") {
						val = tooltip;
					} else {
						val = super.getText(event);
					}
				} else {
					val = super.getText(event);
				}
			}
				return val;
			}
		};
	}



	/**
	 * Method for getting body layer stack.
	 */
	public CustomBodyLayerStack<String> getBodyLayerStack() {
		return bodyLayerStack;
	}

	/**
	 * Get combo box filter row header composite.
	 * 
	 * @return the filterRowHeaderLayer
	 */
	public ComboBoxFilterRowHeaderComposite<String> getFilterRowHeaderLayer() {
		return filterRowHeaderLayer;
	}

	/**
	 * Method for getting nattable.
	 */
	public NatTable getNattable() {
		return nattable;
	}
	
	public ConfigRegistry getConfigRegistry() {
		return configRegistry;
	}




}
