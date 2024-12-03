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

package com.wisii.wisedoc.view.ui.ribbon.insertband;

import static com.wisii.wisedoc.view.ui.text.RibbonUIText.INSERT_CHART_BUTTON;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.INSERT_CHART_BUTTON_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.INSERT_CHART_BUTTON_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.INSERT_GRAPH_BUTTOKN;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.INSERT_GRAPH_BUTTOKN_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.INSERT_GRAPH_BUTTOKN_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.INSERT_GRAPH_BUTTON_LIST;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.INSERT_GRAPH_CANVAS_BUTTON;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.INSERT_GRAPH_CANVAS_BUTTON_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.INSERT_GRAPH_CANVAS_BUTTON_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.INSERT_GRAPH_GROUP_BUTTON;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.INSERT_SVG_TEXT;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.INSERT_SVG_TEXT_TITLE;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandButtonPanel;
import org.jvnet.flamingo.common.JCommandToggleButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Barcode;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Defalult;
import com.wisii.wisedoc.view.ui.svg.NumberedResizableIcon;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 
 * 插入图片面板
 * 
 * @author 闫舒寰
 * @version 1.0 2008/11/13
 */

public class PictureBand
{

	public JRibbonBand getBand()
	{

		// 图标是用Flamigo SVG transcoder转化成Java类，然后直接new 就可以了，这个应该是最小的时候的图标
		final JRibbonBand pictureBand = new JRibbonBand(
				RibbonUIText.PICTURE_BAND, MediaResource
						.getResizableIcon("Image.gif"), null);

		/*********** 添加图片 开始 *******************/
		// 图片按钮
		final JCommandButton insertPictureButton = new JCommandButton(
				RibbonUIText.PICTURE_INSERT_BUTTON, MediaResource
						.getResizableIcon("00931.ico"));
		// mainButton.setMnemonic('P');
		// 主按钮的动作
		RibbonUIManager.getInstance().bind(Defalult.INSERT_IMAGE_ACTION,
				insertPictureButton);
		// 下拉菜单的按钮
		// insertPictureButton.addPopupMenuListener(new
		// SamplePopupActionListener());
		// 添加可下拉按钮
		insertPictureButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		// 鼠标浮动显示解释说明
		insertPictureButton.setActionRichTooltip(new RichTooltip(
				RibbonUIText.PICTURE_INSERT_BUTTON_TITLE,
				RibbonUIText.PICTURE_INSERT_BUTTON_DESCRIPTION));
		// 这个是在地方紧张的时候，显示的优先级
		pictureBand.addCommandButton(insertPictureButton,
				RibbonElementPriority.TOP);
		/*********** 添加图片 结束 *******************/

		/*********** 添加条码 开始 *******************/
		// 添加一维条形码按钮
		final JCommandButton insertBarcodeButton = new JCommandButton(
				RibbonUIText.BARCODE_INSERT_BUTTON, MediaResource
						.getResizableIcon("06366.ico"));
		// 主按钮的动作
		insertBarcodeButton.setPopupCallback(new BarcodePopup());
		// 添加可下拉按钮
		insertBarcodeButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		// 鼠标浮动显示解释说明
		insertBarcodeButton.setActionRichTooltip(new RichTooltip(
				RibbonUIText.BARCODE_INSERT_BUTTON_TITLE,
				RibbonUIText.BARCODE_INSERT_BUTTON_DESCRIPTION));
		// 这个是在地方紧张的时候，显示的优先级
		pictureBand.addCommandButton(insertBarcodeButton,
				RibbonElementPriority.TOP);

		// 添加二维条形码按钮
		final JCommandButton insert2DBarcodeButton = new JCommandButton(
				RibbonUIText.BARCODE_2D_INSERT_BUTTON, MediaResource
						.getResizableIcon("06366.ico"));
		// 主按钮的动作
		insert2DBarcodeButton.setPopupCallback(new Barcode2DPopup());
		// 添加可下拉按钮
		insert2DBarcodeButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		// 鼠标浮动显示解释说明
		insert2DBarcodeButton.setActionRichTooltip(new RichTooltip(
				RibbonUIText.BARCODE_2D_INSERT_BUTTON_TITLE,
				RibbonUIText.BARCODE_2D_INSERT_BUTTON_DESCRIPTION));
		// 这个是在地方紧张的时候，显示的优先级
		pictureBand.addCommandButton(insert2DBarcodeButton,
				RibbonElementPriority.TOP);

		/*********** 添加条码 结束 *******************/

		/*********** 添加画布 开始 *******************/
		// 添加画布按钮
		final JCommandButton insertGraphCanvas = new JCommandButton(
				INSERT_GRAPH_CANVAS_BUTTON, MediaResource
						.getResizableIcon("07726.ico"));
		RibbonUIManager.getInstance().bind(Defalult.INSERT_CANVAS_ACTION,
				insertGraphCanvas);
		// 主按钮的动作
		// insertGraphCanvas.setPopupCallback(new GraphPopup());
		// 添加可下拉按钮
		insertGraphCanvas
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		// 鼠标浮动显示解释说明
		insertGraphCanvas.setActionRichTooltip(new RichTooltip(
				INSERT_GRAPH_CANVAS_BUTTON_TITLE,
				INSERT_GRAPH_CANVAS_BUTTON_DESCRIPTION));
		// 这个是在地方紧张的时候，显示的优先级
		pictureBand.addCommandButton(insertGraphCanvas,
				RibbonElementPriority.TOP);
		/*********** 添加图形 结束 *******************/

		/*********** 添加图形 开始 *******************/
		// 添加图形按钮
		final JCommandButton insertGraphButton = new JCommandButton(
				INSERT_GRAPH_BUTTOKN, MediaResource
						.getResizableIcon("00689.ico"));
		// RibbonUIManager.getInstance().bind(Defalult.INSERT_BARCODE_ACTION,
		// insertGraphButton);
		// 主按钮的动作
		insertGraphButton.setPopupCallback(new GraphPopup());
		// 添加可下拉按钮
		insertGraphButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		// 鼠标浮动显示解释说明
		insertGraphButton.setActionRichTooltip(new RichTooltip(
				INSERT_GRAPH_BUTTOKN_TITLE, INSERT_GRAPH_BUTTOKN_DESCRIPTION));
		// 这个是在地方紧张的时候，显示的优先级
		pictureBand.addCommandButton(insertGraphButton,
				RibbonElementPriority.TOP);
		/*********** 添加图形 结束 *******************/
		/*********** 添加图形 开始 *******************/
		// 添加图形按钮
		final JCommandButton insertSVGTextButton = new JCommandButton(
				INSERT_SVG_TEXT, MediaResource.getResizableIcon("00689.ico"));
		 RibbonUIManager.getInstance().bind(Defalult.INSERT_WORDARTTEXT_ACTION,
				 insertSVGTextButton);
		 insertSVGTextButton
			.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		// 鼠标浮动显示解释说明
		insertSVGTextButton.setActionRichTooltip(new RichTooltip(
				INSERT_SVG_TEXT_TITLE, INSERT_SVG_TEXT_TITLE));
		// 这个是在地方紧张的时候，显示的优先级
		pictureBand.addCommandButton(insertSVGTextButton,
				RibbonElementPriority.TOP);
		/*********** 添加图形 结束 *******************/

		// 添加统计图开始
		final JCommandButton insertchart = new JCommandButton(
				INSERT_CHART_BUTTON, MediaResource
						.getResizableIcon("chart.gif"));
		RibbonUIManager.getInstance().bind(Defalult.INSERT_CHART_ACTION,
				insertchart);
		// 主按钮的动作
		// insertGraphCanvas.setPopupCallback(new GraphPopup());
		// 添加可下拉按钮
		insertchart
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		// 鼠标浮动显示解释说明
		insertchart.setActionRichTooltip(new RichTooltip(
				INSERT_CHART_BUTTON_TITLE, INSERT_CHART_BUTTON_DESCRIPTION));
		// 这个是在地方紧张的时候，显示的优先级
		pictureBand.addCommandButton(insertchart, RibbonElementPriority.TOP);
		/*********** 添加图形 结束 *******************/

		return pictureBand;
	}

	private class GraphPopup implements PopupPanelCallback
	{

		@Override
		public JPopupPanel getPopupPanel(final JCommandButton commandButton)
		{
			return new JCommandPopupMenu(new GraphType(), 6, 6);
		}

	}
	private class GraphType extends JCommandButtonPanel
	{

		public GraphType()
		{
			super(12);

			this.addButtonGroup(INSERT_GRAPH_GROUP_BUTTON);

			final JCommandToggleButton line = new JCommandToggleButton(
					INSERT_GRAPH_BUTTON_LIST[0], MediaResource
							.getResizableIcon("01042.ico"));
			final JCommandToggleButton rectangel = new JCommandToggleButton(
					INSERT_GRAPH_BUTTON_LIST[1], MediaResource
							.getResizableIcon("01111.ico"));
			final JCommandToggleButton circle = new JCommandToggleButton(
					INSERT_GRAPH_BUTTON_LIST[2], MediaResource
							.getResizableIcon("01119.ico"));
			RibbonUIManager.getInstance().bind(Defalult.INSERT_LINE_ACTION,
					line);
			RibbonUIManager.getInstance().bind(
					Defalult.INSERT_RECTANGLE_ACTION, rectangel);
			RibbonUIManager.getInstance().bind(Defalult.INSERT_ELLIPSE_ACTION,
					circle);

			this.addButtonToLastGroup(line);
			this.addButtonToLastGroup(rectangel);
			this.addButtonToLastGroup(circle);

			this.setSingleSelectionMode(true);
		}
	}

	// 一维条形码下拉菜单
	private class BarcodePopup implements PopupPanelCallback
	{

		@Override
		public JPopupPanel getPopupPanel(final JCommandButton commandButton)
		{
			return new JCommandPopupMenu(new BarcodeType(), 5, 5);
		}
	}

	private class BarcodeType extends JCommandButtonPanel
	{

		public BarcodeType()
		{
			super(12);

			this.addButtonGroup(RibbonUIText.BARCODE_TYPE_LABEL);

			final String[] codeType = RibbonUIText.BARCODE_TYPES;
			for (int i = 0; i < codeType.length; i++)
			{
				final JCommandToggleButton additionalButton = new JCommandToggleButton(
						codeType[i], new NumberedResizableIcon(i, 50, 50));

				this.addButtonToLastGroup(additionalButton);
			}

			// 第一组第一个
			RibbonUIManager.getInstance().bind(Barcode.TYPE_39_DEFAULT_ACTION,
					this.getGroupButtons(0).get(0));
			RibbonUIManager.getInstance().bind(Barcode.TYPE_128_DEFAULT_ACTION,
					this.getGroupButtons(0).get(1));
			RibbonUIManager.getInstance().bind(
					Barcode.TYPE_EAN128_DEFAULT_ACTION,
					this.getGroupButtons(0).get(2));
			RibbonUIManager.getInstance().bind(Barcode.TYPE_25_DEFAULT_ACTION,
					this.getGroupButtons(0).get(3));
			RibbonUIManager.getInstance().bind(
					Barcode.TYPE_UPCE_DEFAULT_ACTION,
					this.getGroupButtons(0).get(7));
			RibbonUIManager.getInstance().bind(
					Barcode.TYPE_EAN8_DEFAULT_ACTION,
					this.getGroupButtons(0).get(5));
			RibbonUIManager.getInstance().bind(
					Barcode.TYPE_EAN13_DEFAULT_ACTION,
					this.getGroupButtons(0).get(4));
			RibbonUIManager.getInstance().bind(
					Barcode.TYPE_UPCA_DEFAULT_ACTION,
					this.getGroupButtons(0).get(6));

			this.setSingleSelectionMode(true);
		}
	}

	// 二维条形码下拉菜单
	private class Barcode2DPopup implements PopupPanelCallback
	{

		@Override
		public JPopupPanel getPopupPanel(final JCommandButton commandButton)
		{
			return new JCommandPopupMenu(new Barcode2DType(), 5, 5);
		}
	}

	private class Barcode2DType extends JCommandButtonPanel
	{

		public Barcode2DType()
		{
			super(12);

			this.addButtonGroup(RibbonUIText.BARCODE_TYPE_LABEL);

			final String[] codeType = RibbonUIText.BARCODE_2D_TYPES;
			for (int i = 0; i < codeType.length; i++)
			{
				final JCommandToggleButton additionalButton = new JCommandToggleButton(
						codeType[i], new NumberedResizableIcon(i, 50, 50));

				this.addButtonToLastGroup(additionalButton);
			}

			// 第一组第一个
			RibbonUIManager.getInstance().bind(
					Barcode.TYPE_PDF417_DEFAULT_ACTION,
					this.getGroupButtons(0).get(0));

			this.setSingleSelectionMode(true);
		}
	}

}
