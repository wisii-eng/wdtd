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

import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.FixedLengthSpinner;
import com.wisii.wisedoc.swing.ui.PercentLengthSpinner;
import com.wisii.wisedoc.swing.ui.SpinnerFixedLengthModel;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.TableCell;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.TableRow;
import com.wisii.wisedoc.view.ui.svg.transcoded.format_justify_left;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 单元格大小面板
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/09
 */
public class TableCellSizeBand
{

	public JRibbonBand getBand()
	{

		JRibbonBand sizeband = new JRibbonBand(RibbonUIText.TABLE_CELL_SIZE,
				new format_justify_left(), null);

		JPanel mainpanel = new JPanel(new GridLayout(2, 1));

		FixedLengthSpinner tableHeightValue = new FixedLengthSpinner(
				new SpinnerFixedLengthModel(null, InitialManager.MINLEN, null,
						-1));

		RibbonUIManager.getInstance().bind(TableRow.HEIGHT_ACTION,
				tableHeightValue);

		PercentLengthSpinner tableWidthValue = new PercentLengthSpinner();

		RibbonUIManager.getInstance().bind(TableCell.WIDTH_ACTION,
				tableWidthValue);

		JRibbonComponent heightValueWrapper = new JRibbonComponent(
				MediaResource.getResizableIcon("00541.ico"),
				RibbonUIText.TABLE_CELL_HEIGHT_LABEL, tableHeightValue);

		JRibbonComponent widthValueWrapper = new JRibbonComponent(MediaResource
				.getResizableIcon("00542.ico"),
				RibbonUIText.TABLE_CELL_WIDTH_LABEL, tableWidthValue);

		JPanel one = new JPanel();
		JPanel two = new JPanel();

		one.add(widthValueWrapper);

		two.add(heightValueWrapper);

		mainpanel.add(one);
		mainpanel.add(two);

		JRibbonComponent mainWrapper = new JRibbonComponent(mainpanel);
		sizeband.addRibbonComponent(mainWrapper);
		return sizeband;
	}
}
