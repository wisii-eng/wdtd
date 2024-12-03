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

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.TableCell;
import com.wisii.wisedoc.view.ui.svg.transcoded.format_justify_left;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 单元格内容padding设置
 * 
 * @author 钟亚军
 * @version 1.0 2009/03/20
 */
public class TableCellPaddingBand
{

	public JRibbonBand getBand()
	{
		JRibbonBand paddingBand = new JRibbonBand(
				RibbonUIText.TABLE_CELL_PADDING, new format_justify_left(),
				null);

		JPanel mainpanel = new JPanel(new GridLayout(2, 2));

		FixedLengthSpinner topValue = new FixedLengthSpinner();

		RibbonUIManager.getInstance().bind(TableCell.TOP_PADDING_ACTION,
				topValue);

		FixedLengthSpinner bottomValue = new FixedLengthSpinner();

		RibbonUIManager.getInstance().bind(TableCell.BOTTOM_PADDING_ACTION,
				bottomValue);

		FixedLengthSpinner leftValue = new FixedLengthSpinner();

		RibbonUIManager.getInstance().bind(TableCell.LEFT_PADDING_ACTION,
				leftValue);

		FixedLengthSpinner rightValue = new FixedLengthSpinner();
		RibbonUIManager.getInstance().bind(TableCell.RIGHT_PADDING_ACTION,
				rightValue);

		JRibbonComponent topValueWrapper = new JRibbonComponent(MediaResource
				.getResizableIcon("00867.ico"), RibbonUIText.PADDING_TOP,
				topValue);

		JRibbonComponent bottomValueWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("00867bottom.ico"),
				RibbonUIText.PADDING_BOTTOM, bottomValue);

		JRibbonComponent leftValueWrapper = new JRibbonComponent(MediaResource
				.getResizableIcon("00867left.ico"), RibbonUIText.PADDING_LEFT,
				leftValue);
		JRibbonComponent rightValueWrapper = new JRibbonComponent(MediaResource
				.getResizableIcon("00867right.ico"),
				RibbonUIText.PADDING_RIGHT, rightValue);

		JPanel one = new JPanel();
		JPanel two = new JPanel();
		JPanel three = new JPanel();
		JPanel four = new JPanel();

		one.add(topValueWrapper);

		two.add(bottomValueWrapper);
		three.add(leftValueWrapper);
		four.add(rightValueWrapper);

		mainpanel.add(one);
		mainpanel.add(three);
		mainpanel.add(two);
		mainpanel.add(four);

		JRibbonComponent mainWrapper = new JRibbonComponent(mainpanel);
		paddingBand.addRibbonComponent(mainWrapper);
		return paddingBand;
	}
}
