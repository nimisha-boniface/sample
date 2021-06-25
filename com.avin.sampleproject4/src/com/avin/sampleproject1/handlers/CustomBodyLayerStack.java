package com.avin.sampleproject1.handlers;

import java.io.Serializable;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;
import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.data.IRowIdAccessor;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.extension.glazedlists.GlazedListsEventLayer;
import org.eclipse.nebula.widgets.nattable.freeze.CompositeFreezeLayer;
import org.eclipse.nebula.widgets.nattable.freeze.FreezeLayer;
import org.eclipse.nebula.widgets.nattable.hideshow.ColumnHideShowLayer;
import org.eclipse.nebula.widgets.nattable.layer.AbstractLayerTransform;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.reorder.ColumnReorderLayer;
import org.eclipse.nebula.widgets.nattable.reorder.RowReorderLayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.preserve.PreserveSelectionModel;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.TransformedList;

public class CustomBodyLayerStack<T> extends AbstractLayerTransform {
	/**
	 * Event List.
	 */
	private EventList<String> eventList;
	/**
	 * Sorted List.
	 */
	private final SortedList<String> sortedList;
	/**
	 * Filtered List.
	 */
	private final FilterList<String> filterList;
	/**
	 * Body Data Provider.
	 */
	private final ListDataProvider<String> bodyDataProvider;
	/**
	 * Body Data Layer.
	 */
	private final DataLayer bodyDataLayer;
	/**
	 * Selection Layer.
	 */
	private final SelectionLayer selectionLayer;
	/**
	 * Viewport Layer.
	 */
	private final ViewportLayer viewportLayer;
	/**
	 * Freeze Layer.
	 */
	private final FreezeLayer freezeLayer;
	/**
	 * Composite Freeze Layer.
	 */
	private final CompositeFreezeLayer compositeFreezeLayer;
	/**
	 * Row Reorder Layer.
	 */
	private RowReorderLayer rowReorderLayer;

	/**
	 * Create body layer stack for Nattable.
	 * 
	 * @param values
	 *            List of container String
	 * @param columnPropertyAccessor
	 *            Property Accessor
	 */
	public CustomBodyLayerStack(List<String> values, IColumnPropertyAccessor<String> columnPropertyAccessor) {
		this.eventList = GlazedLists.eventList(values);
		EventList<String> rowStringsGlazedList = GlazedLists.threadSafeList(this.eventList);

		this.sortedList = new SortedList<String>(rowStringsGlazedList, null);
		this.filterList = new FilterList<String>(getSortedList());
		this.bodyDataProvider = new ListDataProvider<String>(getFilterList(), columnPropertyAccessor);
		this.bodyDataLayer = new DataLayer(getBodyDataProvider());

		GlazedListsEventLayer<String> glazedListsEventLayer = new GlazedListsEventLayer<String>(getBodyDataLayer(),
				getFilterList());
		ColumnReorderLayer columnReorderLayer = new ColumnReorderLayer(glazedListsEventLayer);
		ColumnHideShowLayer columnHideShowLayer = new ColumnHideShowLayer(columnReorderLayer);
		this.rowReorderLayer = new RowReorderLayer(columnHideShowLayer);
		this.selectionLayer = new SelectionLayer(getRowReorderLayer());
		PreserveSelectionModel<String> rowSelectionModel = new PreserveSelectionModel<String>(getSelectionLayer(),
				getBodyDataProvider(), new IRowIdAccessor<String>() {

					@Override
					public Serializable getRowId(String rowString) {
						return rowString.hashCode();
					}

				});
		getSelectionLayer().setSelectionModel(rowSelectionModel);
		this.viewportLayer = new ViewportLayer(getSelectionLayer());
		this.freezeLayer = new FreezeLayer(getSelectionLayer());
		this.compositeFreezeLayer = new CompositeFreezeLayer(getFreezeLayer(), getViewportLayer(), getSelectionLayer());
		setUnderlyingLayer(getCompositeFreezeLayer());
	}

	/**
	 * Get freeze layer.
	 * 
	 * @return Freeze Layer.
	 */
	public FreezeLayer getFreezeLayer() {
		return this.freezeLayer;
	}

	/**
	 * Get composite freeze layer.
	 * 
	 * @return Composite Freeze Layer
	 */
	public CompositeFreezeLayer getCompositeFreezeLayer() {
		return this.compositeFreezeLayer;
	}

	/**
	 * Get viewport layer.
	 * 
	 * @return Viewport Layer
	 */
	public ViewportLayer getViewportLayer() {
		return this.viewportLayer;
	}

	/**
	 * Get selection layer.
	 * 
	 * @return Selection Layer
	 */
	public SelectionLayer getSelectionLayer() {
		return this.selectionLayer;
	}

	/**
	 * Get event list.
	 * 
	 * @return Event List
	 */
	public EventList<String> getEventList() {
		return this.eventList;
	}

	/**
	 * Get sorted list.
	 * 
	 * @return Sorted List
	 */
	public SortedList<String> getSortedList() {
		return this.sortedList;
	}

	/**
	 * Get filtered list.
	 * 
	 * @return Filtered List
	 */
	public FilterList<String> getFilterList() {
		return this.filterList;
	}

	/**
	 * Get row reorder layer.
	 * 
	 * @return RowReorder Layer
	 */
	public RowReorderLayer getRowReorderLayer() {
		return rowReorderLayer;
	}

	/**
	 * Get body data provider.
	 * 
	 * @return BodyData Provider
	 */
	public ListDataProvider<String> getBodyDataProvider() {
		return this.bodyDataProvider;
	}

	/**
	 * Get body data layer.
	 * 
	 * @return BodyData Layer
	 */
	public DataLayer getBodyDataLayer() {
		return this.bodyDataLayer;
	}
}
