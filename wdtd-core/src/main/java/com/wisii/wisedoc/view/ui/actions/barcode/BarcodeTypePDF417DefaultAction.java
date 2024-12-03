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

import java.awt.event.ActionEvent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;

/**
 * 插入二维条码的动作
 * @author 闫舒寰
 * @version 1.0 2009/04/17
 */
public class BarcodeTypePDF417DefaultAction extends BarcodeDefaultAction {

	final double BARCODE_DEFAULT_WIDE_TO_NARROW = 3.0;
	final double BARCODE_DEFAULT_QUIET_VERTICAL = 0.5;
	final double BARCODE_DEFAULT_TEXT_BLOCK = 3.048;
	final double BARCODE_DEFAULT_TEXT_CHAR_SPACE = 0;
	final String BARCODE_DEFAULT_STRING = "";
	
	//二维码所特有的
	int BARCODE_DEFAULT_EC_LEVEL = 0;
	int BARCODE_DEFAULT_COLUMNS = 0;
	int BARCODE_DEFAULT_MIN_COLUMNS = 1;
	int BARCODE_DEFAULT_MAX_COLUMNS = 30;
	int BARCODE_DEFAULT_MIN_ROWS = 3;
	int BARCODE_DEFAULT_MAX_ROWS = 90;
	
	@Override
	protected void initValue() {
		BARCODE_CONTENT = "";
		BARCODE_DEFAULT_HEIGHT = 0.99;
		BARCODE_DEFAULT_MODULE = 0.33;
		BARCODE_DEFAULT_QUIET_HORIZONTAL = 0.5;
		BARCODE_DEFAULT_FONT_FAMILY = "Arial";
		BARCODE_DEFAULT_FONT_HEIGHT = 10;
		BARCODE_DEFAULT_ORIENTATION = 0;
		BARCODE_DEFAULT_PRINT_TEXT = new EnumProperty(Constants.EN_FALSE, "");
	}

	@Override
	public void doAction(final ActionEvent e) {
		//barcode类型
		barcodeProperties.put(Constants.PR_BARCODE_TYPE, new EnumProperty(Constants.EN_PDF417, ""));
		//barcode的供人识别的字符
		barcodeProperties.put(Constants.PR_BARCODE_STRING, BARCODE_DEFAULT_STRING);
		
		/**************《Barcode_128模板代码接口》中的特定参数*********/
		//barcode的wide-to-narrow默认值
		barcodeProperties.put(Constants.PR_BARCODE_WIDE_TO_NARROW, BARCODE_DEFAULT_WIDE_TO_NARROW);
		
		//barcode的quiet-vertical默认值
		barcodeProperties.put(Constants.PR_BARCODE_QUIET_VERTICAL, new FixedLength(BARCODE_DEFAULT_QUIET_VERTICAL, "mm",3));
		
		//barcode的text-block默认值
		barcodeProperties.put(Constants.PR_BARCODE_TEXT_BLOCK, new FixedLength(BARCODE_DEFAULT_TEXT_BLOCK, "mm",3));
		
		//barcode的text-char-space默认值
		barcodeProperties.put(Constants.PR_BARCODE_TEXT_CHAR_SPACE, new FixedLength(BARCODE_DEFAULT_TEXT_CHAR_SPACE, "mm",3));
		
		//下面是二维条码所特有的
		barcodeProperties.put(Constants.PR_BARCODE_EC_LEVEL, BARCODE_DEFAULT_EC_LEVEL);
		barcodeProperties.put(Constants.PR_BARCODE_COLUMNS, BARCODE_DEFAULT_COLUMNS);
		barcodeProperties.put(Constants.PR_BARCODE_MIN_COLUMNS, BARCODE_DEFAULT_MIN_COLUMNS);
		barcodeProperties.put(Constants.PR_BARCODE_MAX_COLUMNS, BARCODE_DEFAULT_MAX_COLUMNS);
		barcodeProperties.put(Constants.PR_BARCODE_MIN_ROWS, BARCODE_DEFAULT_MIN_ROWS);
		barcodeProperties.put(Constants.PR_BARCODE_MAX_ROWS, BARCODE_DEFAULT_MAX_ROWS);
		
		new InsertDefaultBarcode().doAction(e);
	}
}
