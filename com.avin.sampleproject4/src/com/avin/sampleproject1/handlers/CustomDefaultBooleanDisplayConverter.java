/****************************************************************************
 * File Name        : CustomDefaultBooleanDisplayConverter.java
 * Revision History : 
 * Revision    Date         Author        Description
 ****************************************************************************
 * 1.0.0       08-Aug-17    Gaurav        Initial version
 * 1.1.0       31-May-19    Kusuma        CR# 5762  Tri state 
 *                                        feature for boolean parameters.
 ****************************************************************************
 * Copyright (c) 2017 AVIN Systems Private Limited.
****************************************************************************/

package com.avin.sampleproject1.handlers;

import org.eclipse.nebula.widgets.nattable.data.convert.DisplayConverter;

/**
 * Display Converter class for Boolean type value in NatTable.
 * 
 * @author gaurav_tripathi
 *
 */
public class CustomDefaultBooleanDisplayConverter extends DisplayConverter {

  @Override
  public Object canonicalToDisplayValue(Object displayValue) {
    if (displayValue == null || displayValue.equals("")) {
      return null;
    } else if (String.valueOf(displayValue.toString()).equals("1")) {
      return true;
    } else if (String.valueOf(displayValue.toString()).equals("0")) {
      return false;
    } else {
      return Boolean.valueOf(displayValue.toString());
    }
  }

  @Override
  public Object displayToCanonicalValue(Object canonicalValue) {
    if (canonicalValue == null) {
      return null;
    } else {
      return canonicalValue.toString();
    }
  }

}
