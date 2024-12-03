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

package com.wisii.wisedoc.view.ui.ribbon.table;

import java.awt.GridLayout;

import javax.swing.JPanel;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.SpinnerFixedLengthModel;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Table;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;
import com.wisii.wisedoc.view.ui.svg.transcoded.format_justify_left;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

public class TableIndentBand implements WiseBand
{

	public JRibbonBand getBand()
	{
		// 高级块容器位置和大小面板
		JRibbonBand blockContainerPositionBand = new JRibbonBand(
				RibbonUIText.TABLE_INDENT, new format_justify_left(), null);

		JPanel mainpanel = new JPanel(new GridLayout(2, 1));
		FixedLength minlen = new FixedLength(-9000d,"pt");
		FixedLength maxlen = new FixedLength(9000d,"pt");
		FixedLength deflen = new FixedLength(0d,"pt");
		FixedLengthSpinner startValue = new FixedLengthSpinner(new SpinnerFixedLengthModel(deflen,minlen,maxlen,-1));
		RibbonUIManager.getInstance().bind(Table.START_POSITION_ACTION,
				startValue);

		FixedLengthSpinner endValue = new FixedLengthSpinner(new SpinnerFixedLengthModel(deflen,minlen,maxlen,-1));
		RibbonUIManager.getInstance().bind(Table.END_POSITION_ACTION, endValue);

		JRibbonComponent startValueWrapper = new JRibbonComponent(MediaResource
				.getResizableIcon("00867left.ico"), RibbonUIText.STARTINDENT,
				startValue);
		JRibbonComponent endValueWrapper = new JRibbonComponent(MediaResource
				.getResizableIcon("00867right.ico"), RibbonUIText.ENDINDENT,
				endValue);

		JPanel three = new JPanel();
		JPanel four = new JPanel();

		three.add(startValueWrapper);
		four.add(endValueWrapper);

		mainpanel.add(three);
		mainpanel.add(four);
		JRibbonComponent mainWrapper = new JRibbonComponent(mainpanel);
		blockContainerPositionBand.addRibbonComponent(mainWrapper);
		return blockContainerPositionBand;
	}
}
