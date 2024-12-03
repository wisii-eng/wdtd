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
import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 设置条形码样式为默认样式动作
 * @author 闫舒寰
 * @version 1.0 2009/02/03
 */
public class BarcodeCleanStyleAction extends Actions{
	
	/**
	 * TODO
	 * 目前明显没有做完
	 */

	private Map<Integer, Object> barcodeDefProperties = new HashMap<Integer, Object>();
	private String BARCODE_DEFAULT_FONT_FAMILY;
	private double BARCODE_DEFAULT_FONT_HEIGHT;
	
	protected void initValue() {
		BARCODE_DEFAULT_FONT_FAMILY = "Arial";
		BARCODE_DEFAULT_FONT_HEIGHT = 10;
		
		//barcode的font-family默认值
		barcodeDefProperties.put(Constants.PR_BARCODE_FONT_FAMILY, BARCODE_DEFAULT_FONT_FAMILY);
		
		
		//barcode的font-height默认值
		barcodeDefProperties.put(Constants.PR_BARCODE_FONT_HEIGHT, new FixedLength(BARCODE_DEFAULT_FONT_HEIGHT, "pt"));
		
	}
	
	@Override
	public void doAction(ActionEvent e) {
		initValue();
		setFOProperties(barcodeDefProperties);
	}
	
	@Override
	protected void uiStateChange(UIStateChangeEvent evt) {
		super.uiStateChange(evt);
	}
	
	@Override
	protected void setDifferentPropertyState(UIStateChangeEvent evt) {
		super.setDifferentPropertyState(evt);
	}
	
	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		super.setDefaultState(null);
	}
	
	@Override
	public boolean isAvailable() {
		return super.isAvailable();
	}
}
