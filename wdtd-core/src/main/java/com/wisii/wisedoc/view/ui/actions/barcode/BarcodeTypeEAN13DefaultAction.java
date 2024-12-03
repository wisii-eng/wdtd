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
 * 创建ean13barcode
 * @author 闫舒寰
 * @version 1.0 2009/02/02
 */
public class BarcodeTypeEAN13DefaultAction extends BarcodeDefaultAction {

	double BARCODE_DEFAULT_QUIET_VERTICAL = 0.5;
	double BARCODE_DEFAULT_TEXT_BLOCK = 3.048;
	double BARCODE_DEFAULT_TEXT_CHAR_SPACE = 0;

	@Override
	protected void initValue() {
		//EAN13码内容的长度必须为11或者12位
		BARCODE_CONTENT = "012345678901";
		BARCODE_DEFAULT_HEIGHT = 23.1;
		BARCODE_DEFAULT_MODULE = 0.33;
		BARCODE_DEFAULT_QUIET_HORIZONTAL = 0.5;
		BARCODE_DEFAULT_FONT_FAMILY = "Arial";
		BARCODE_DEFAULT_FONT_HEIGHT = 10;
		BARCODE_DEFAULT_ORIENTATION = 0;
	}

	@Override
	public void doAction(ActionEvent e) {
		//barcode类型
		barcodeProperties.put(Constants.PR_BARCODE_TYPE, new EnumProperty(Constants.EN_EAN13, ""));	
		
		//需要自动生成code-type
		
//		barcodeProperties.put(Constants.pr_barcode_, value)
		
		//barcode的quiet-vertical默认值
		barcodeProperties.put(Constants.PR_BARCODE_QUIET_VERTICAL, new FixedLength(BARCODE_DEFAULT_QUIET_VERTICAL, "mm"));
		
		//barcode的text-block默认值
		barcodeProperties.put(Constants.PR_BARCODE_TEXT_BLOCK, new FixedLength(BARCODE_DEFAULT_TEXT_BLOCK, "mm"));
		
		//barcode的text-char-space默认值
		barcodeProperties.put(Constants.PR_BARCODE_TEXT_CHAR_SPACE, new FixedLength(BARCODE_DEFAULT_TEXT_CHAR_SPACE, "mm"));
		
		new InsertDefaultBarcode().doAction(e);
	}
}
