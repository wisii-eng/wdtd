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
/**
 * @BarcodeTypeEAN128DefaultAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.barcode;

import java.awt.event.ActionEvent;

import com.wisii.wisedoc.document.BarCodeText;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-7-22
 */
public class BarcodeTypeEAN128DefaultAction extends BarcodeDefaultAction
{	
	
	double BARCODE_DEFAULT_QUIET_VERTICAL = 0.5;
	double BARCODE_DEFAULT_TEXT_BLOCK = 3.048;
	double BARCODE_DEFAULT_TEXT_CHAR_SPACE = 0;

	@Override
	protected void initValue() {
		BARCODE_DEFAULT_HEIGHT = 14;
		BARCODE_DEFAULT_MODULE = 0.216;
		BARCODE_DEFAULT_QUIET_HORIZONTAL = 0.5;
		BARCODE_DEFAULT_FONT_FAMILY = "Arial";
		BARCODE_DEFAULT_FONT_HEIGHT = 10;
		BARCODE_DEFAULT_ORIENTATION = 0;
	}

	@Override
	public void doAction(ActionEvent e) {
		//barcode类型
		barcodeProperties.put(Constants.PR_BARCODE_TYPE, new EnumProperty(Constants.EN_EAN128, ""));		
		
		/**************《Barcode_128模板代码接口》中的特定参数*********/
		//barcode的subset默认值
		barcodeProperties.put(Constants.PR_BARCODE_SUBSET, new EnumProperty(Constants.EN_A, ""));
		
		//barcode的makeUCC默认值
		barcodeProperties.put(Constants.PR_BARCODE_MAKEUCC, new EnumProperty(Constants.EN_FALSE, ""));
		
		//barcode的quiet-vertical默认值
		barcodeProperties.put(Constants.PR_BARCODE_QUIET_VERTICAL, new FixedLength(BARCODE_DEFAULT_QUIET_VERTICAL, "mm"));
		
		//barcode的text-block默认值
		barcodeProperties.put(Constants.PR_BARCODE_TEXT_BLOCK, new FixedLength(BARCODE_DEFAULT_TEXT_BLOCK, "mm",3));
		
		//barcode的text-char-space默认值
		barcodeProperties.put(Constants.PR_BARCODE_TEXT_CHAR_SPACE, new FixedLength(BARCODE_DEFAULT_TEXT_CHAR_SPACE, "mm"));
		barcodeProperties.put(Constants.PR_BARCODE_CONTENT, new BarCodeText("12345678912"));
		
		new InsertDefaultBarcode().doAction(e);
	}
}
