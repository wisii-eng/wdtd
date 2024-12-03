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
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 定义供人识别字符中各字符之间的间距大小的动作
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/02
 */
public class BarcodeTextCharSpaceAction extends Actions {

	@Override
	public void doAction(ActionEvent e) {
		if (e.getSource() instanceof FixedLengthSpinner) {
			FixedLengthSpinner value = (FixedLengthSpinner) e.getSource();
			setFOProperty(Constants.PR_BARCODE_TEXT_CHAR_SPACE, value
					.getValue());
		}
	}

	@Override
	protected void uiStateChange(UIStateChangeEvent evt) {
		super.uiStateChange(evt);
		if (hasPropertyKey(Constants.PR_BARCODE_TEXT_CHAR_SPACE)) {
			if (uiComponent instanceof FixedLengthSpinner) {
				FixedLengthSpinner ui = (FixedLengthSpinner) uiComponent;
				FixedLength value = null;
				Object oldvalue = evt.getNewValue();
				if (oldvalue instanceof FixedLength) {
					value = (FixedLength) oldvalue;
				}
				ui.initValue(value);
			}
		}
	}

	@Override
	protected void setDifferentPropertyState(UIStateChangeEvent evt) {
		super.setDifferentPropertyState(evt);
		if (uiComponent instanceof FixedLengthSpinner) {
			FixedLengthSpinner ui = (FixedLengthSpinner) uiComponent;
			ui.initValue(null);
		}
	}

	@Override
	public void setDefaultState(ActionCommandType actionCommand) {
		super.setDefaultState(null);
		if (uiComponent instanceof FixedLengthSpinner) {
			FixedLengthSpinner ui = (FixedLengthSpinner) uiComponent;
			ui.setValue(new FixedLength(0));
		}
	}

	@Override
	public boolean isAvailable() {
		return super.isAvailable();
	}

}
