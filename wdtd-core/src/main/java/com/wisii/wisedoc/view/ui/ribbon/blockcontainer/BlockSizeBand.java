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

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.SpinnerFixedLengthModel;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.BlockContainer;
import com.wisii.wisedoc.view.ui.svg.transcoded.format_justify_left;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 块容器大小面板
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/09
 */
public class BlockSizeBand
{

	public JRibbonBand getBand()
	{

		JRibbonBand paragraphBand = new JRibbonBand(RibbonUIText.SIZE,
				new format_justify_left(), null);

		JPanel sizepanel = new JPanel(new GridLayout(2, 1));

		JPanel one = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel two = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel widthlabel = new JLabel(RibbonUIText.BLOCK_CONTAINER_WIDTH_LABEL);
		JLabel heightlabel = new JLabel(
				RibbonUIText.BLOCK_CONTAINER_HEIGHT_LABEL);

		FixedLengthSpinner heightValue = new FixedLengthSpinner(
				new SpinnerFixedLengthModel(null, InitialManager.MINLEN, null,
						-1));
		RibbonUIManager.getInstance().bind(BlockContainer.BPD_ACTION,
				heightValue);

		FixedLengthSpinner widthValue = new FixedLengthSpinner(
				new SpinnerFixedLengthModel(null, InitialManager.MINLEN, null,
						-1));
		RibbonUIManager.getInstance().bind(BlockContainer.IPD_ACTION,
				widthValue);

		// paragraphBand.startGroup(RibbonUIText.BLOCK_CONTAINER_HEIGHT_LABEL);
		// JRibbonComponent heightValueWrapper = new JRibbonComponent(
		// MediaResource.getResizableIcon("00541.ico"),
		// RibbonUIText.BLOCK_CONTAINER_HEIGHT_LABEL, heightValue);

		// JRibbonComponent widthValueWrapper = new
		// JRibbonComponent(MediaResource
		// .getResizableIcon("00542.ico"),
		// RibbonUIText.BLOCK_CONTAINER_WIDTH_LABEL, widthValue);

		JRadioButton autowidth = new JRadioButton(RibbonUIText.AUTO);
		JRadioButton autoheight = new JRadioButton(RibbonUIText.AUTO);

		RibbonUIManager.getInstance().bind(BlockContainer.AUTO_IPD_ACTION,
				autowidth);
		RibbonUIManager.getInstance().bind(BlockContainer.AUTO_BPD_ACTION,
				autoheight);
		// JRibbonComponent width = new JRibbonComponent(autowidth);
		// JRibbonComponent height = new JRibbonComponent(autoheight);
		one.add(widthlabel);
		one.add(autowidth);
		one.add(widthValue);
		two.add(heightlabel);
		two.add(autoheight);
		two.add(heightValue);
		sizepanel.add(one);
		sizepanel.add(two);
		JRibbonComponent maincom = new JRibbonComponent(sizepanel);
		paragraphBand.addRibbonComponent(maincom);
		// paragraphBand.addRibbonComponent(width);
		// paragraphBand.addRibbonComponent(height);
		// paragraphBand.addRibbonComponent(widthValueWrapper);
		// paragraphBand.addRibbonComponent(heightValueWrapper);

		return paragraphBand;
	}
}
