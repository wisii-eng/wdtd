/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wisii.wisedoc.view.ui.actions.barcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.BarCodeInline;
import com.wisii.wisedoc.document.BarCodeText;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.WiseDocColor;
import com.wisii.wisedoc.view.action.InsertObjectAction;
import com.wisii.wisedoc.view.ui.actions.Actions;

/**
 * 用于创建各种类型的条形码的父类
 * @author 闫舒寰
 * @version 1.0 2009/02/02
 */
public abstract class BarcodeDefaultAction extends Actions {
	
	Map<Integer, Object> barcodeProperties = new HashMap<Integer, Object>();
	//条形码的初始值
	String BARCODE_CONTENT = "123456789";
	double BARCODE_DEFAULT_HEIGHT;
	double BARCODE_DEFAULT_MODULE;
	double BARCODE_DEFAULT_QUIET_HORIZONTAL;
	String BARCODE_DEFAULT_FONT_FAMILY;
	double BARCODE_DEFAULT_FONT_HEIGHT;
	int BARCODE_DEFAULT_ORIENTATION;
	Object BARCODE_DEFAULT_PRINT_TEXT = new EnumProperty(Constants.EN_TRUE, "");

	public BarcodeDefaultAction() {
		initValue();
		/**************《Barcode_128模板代码接口》中的一般参数*********/
		//barcode的value目前设置成了123456789，以后会按照需要更改的；见《Barcode_128模板代码接口》4.1.1
		barcodeProperties.put(Constants.PR_BARCODE_CONTENT, new BarCodeText(BARCODE_CONTENT));
		
		//barcode的默认值
		barcodeProperties.put(Constants.PR_BARCODE_HEIGHT, new FixedLength(BARCODE_DEFAULT_HEIGHT, "mm"));
		
		//barcode的module默认值
		barcodeProperties.put(Constants.PR_BARCODE_MODULE, new FixedLength(BARCODE_DEFAULT_MODULE, "mm",3));
		
		//barcode的quiet-horizontal默认值
		barcodeProperties.put(Constants.PR_BARCODE_QUIET_HORIZONTAL, new FixedLength(BARCODE_DEFAULT_QUIET_HORIZONTAL, "mm",3));
		
		//value的默认值见《Barcode_128模板代码接口》4.1.5
		
		//barcode的print-text默认值
		barcodeProperties.put(Constants.PR_BARCODE_PRINT_TEXT, BARCODE_DEFAULT_PRINT_TEXT);
		
		//barcode的font-family默认值
		barcodeProperties.put(Constants.PR_BARCODE_FONT_FAMILY, BARCODE_DEFAULT_FONT_FAMILY);
		
		//barcode的font-height默认值
		barcodeProperties.put(Constants.PR_BARCODE_FONT_HEIGHT, new FixedLength(BARCODE_DEFAULT_FONT_HEIGHT, "pt"));
		
		
		//barcode的orientation默认值
		barcodeProperties.put(Constants.PR_BARCODE_ORIENTATION, BARCODE_DEFAULT_ORIENTATION);
	}
	
	/**
	 * 子类要给出具体的值
	 */
	abstract protected void initValue();
	
	
	protected class InsertDefaultBarcode extends InsertObjectAction {
		@Override
		protected List<CellElement> creatCells() {
			final BarCodeInline barcode = new BarCodeInline(barcodeProperties);
			final List<CellElement> inserts = new ArrayList<CellElement>();
			inserts.add(barcode);
			return inserts;
		}
	}
}
