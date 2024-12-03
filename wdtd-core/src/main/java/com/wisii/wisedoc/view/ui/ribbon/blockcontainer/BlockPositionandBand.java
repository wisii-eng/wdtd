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

package com.wisii.wisedoc.view.ui.ribbon.blockcontainer;

import java.awt.CardLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.SpinnerFixedLengthModel;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.BlockContainer;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;
import com.wisii.wisedoc.view.ui.svg.transcoded.format_justify_left;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 高级块容器位置和大小面板
 * 
 * @author 闫舒寰
 * @version 1.0 2009/01/16
 */
public class BlockPositionandBand implements WiseBand
{

	private CardLayout cardlayout;

	private JPanel cardpanel;

	public JRibbonBand getBand()
	{
		// 高级块容器位置和大小面板
		JRibbonBand blockContainerPositionBand = new JRibbonBand(
				RibbonUIText.BLOCK_CONTAINER_POSITION_BAND,
				new format_justify_left(), null);

		JPanel abspanel = new JPanel(new GridLayout(2, 1));
		JPanel relpanel = new JPanel(new GridLayout(2, 1));
		FixedLength minlen = new FixedLength(-9000d, "pt");
		FixedLength maxlen = new FixedLength(9000d, "pt");
		FixedLength defcmlen = new FixedLength(0d, "cm");
		FixedLengthSpinner topValue = new FixedLengthSpinner(
				new SpinnerFixedLengthModel(defcmlen, minlen, maxlen, -1));
		RibbonUIManager.getInstance().bind(BlockContainer.TOP_POSITION_ACTION,
				topValue);

		FixedLengthSpinner leftValue = new FixedLengthSpinner(
				new SpinnerFixedLengthModel(defcmlen, minlen, maxlen, -1));
		RibbonUIManager.getInstance().bind(BlockContainer.LEFT_POSITION_ACTION,
				leftValue);
		FixedLength defptlen = new FixedLength(0d, "pt");
		FixedLengthSpinner startValue = new FixedLengthSpinner(
				new SpinnerFixedLengthModel(defptlen, minlen, maxlen, -1));
		RibbonUIManager.getInstance().bind(
				BlockContainer.START_POSITION_ACTION, startValue);

		FixedLengthSpinner endValue = new FixedLengthSpinner(
				new SpinnerFixedLengthModel(defptlen, minlen, maxlen, -1));
		RibbonUIManager.getInstance().bind(BlockContainer.END_POSITION_ACTION,
				endValue);
		JRibbonComponent topValueWrapper = new JRibbonComponent(MediaResource
				.getResizableIcon("00867.ico"),
				RibbonUIText.BLOCK_CONTAINER_TOP_LABEL, topValue);

		JRibbonComponent leftValueWrapper = new JRibbonComponent(MediaResource
				.getResizableIcon("00867left.ico"),
				RibbonUIText.BLOCK_CONTAINER_LEFT_LABEL, leftValue);

		JRibbonComponent startValueWrapper = new JRibbonComponent(MediaResource
				.getResizableIcon("00867left.ico"), RibbonUIText.STARTINDENT,
				startValue);
		JRibbonComponent endValueWrapper = new JRibbonComponent(MediaResource
				.getResizableIcon("00867right.ico"), RibbonUIText.ENDINDENT,
				endValue);

		JPanel one = new JPanel();
		JPanel two = new JPanel();
		JPanel three = new JPanel();
		JPanel four = new JPanel();

		one.add(topValueWrapper);

		two.add(leftValueWrapper);
		three.add(startValueWrapper);
		four.add(endValueWrapper);

		abspanel.add(one);
		abspanel.add(two);
		
		
		relpanel.add(three);
		relpanel.add(four);

		cardlayout = new CardLayout();
		cardpanel = new JPanel(cardlayout);
		cardpanel.add(abspanel, "" + Constants.EN_ABSOLUTE);
		cardpanel.add(relpanel, "" + Constants.EN_RELATIVE);

		JRibbonComponent mainWrapper = new JRibbonComponent(cardpanel);
		blockContainerPositionBand.addRibbonComponent(mainWrapper);
		return blockContainerPositionBand;
	}

	public void typeChanged(EnumProperty edittype)
	{
		if (edittype != null)
		{
			String name = "" + edittype.getEnum();
			cardlayout.show(cardpanel, name);
		}
	}
}
