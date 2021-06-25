package com.avin.sampleproject1.handlers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.nebula.widgets.nattable.data.ReflectiveColumnPropertyAccessor;


public class ColumnPropAccessor extends ReflectiveColumnPropertyAccessor<String> {

	private List<String> propertyNames;

	private Map<String, String> valueMap = new HashMap<String, String>();

	public ColumnPropAccessor(String[] properties) {
		this.propertyNames = Arrays.asList(properties);
	}

	@Override
	public int getColumnIndex(String propertyName) {
		return propertyNames.indexOf(propertyName);
	}

	@Override
	public String getColumnProperty(int columnIndex) {
		return propertyNames.get(columnIndex);
	}

	public void setPropertyNames(List<String> propertyNames) {
		this.propertyNames = propertyNames;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return propertyNames.size();
	}

	@Override
	public String getDataValue(String rowString, int columnIndex) {
		String ret = null;
		if (rowString instanceof String && columnIndex == 0) {
			ret = rowString;
		} else {
			ret = valueMap.get(rowString + columnIndex);
		}
		return ret;
	}

	@Override
	public void setDataValue(String rowObj, int columnIndex, Object newValue) {
		if(newValue == null){
			valueMap.put(rowObj+columnIndex, "");
		}else{
			valueMap.put(rowObj+columnIndex, (String) newValue);
		}
	}

}
