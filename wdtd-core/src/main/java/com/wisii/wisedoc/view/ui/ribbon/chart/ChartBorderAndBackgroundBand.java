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
 * @ChartBorderAndBackgroundBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.ribbon.chart;

import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerNumberModel;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.swing.ui.jxcombobox.IncompatibleLookAndFeelException;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.WisedocChart;
import com.wisii.wisedoc.view.ui.ribbon.BackGroundImageList;
import com.wisii.wisedoc.view.ui.ribbon.BorderAndBackGroundList;
import com.wisii.wisedoc.view.ui.ribbon.common.RibbonColorList;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 类功能描述：用于设置统计图背景【背景图、背景色、透明度、层】、边框。
 * 
 * 作者：李晓光 创建日期：2009-5-20
 */
public class ChartBorderAndBackgroundBand {
	private final static String BAND_TITLE = UiText.BORDER_AND_BACK_BAND_TITLE;//MessageResource.getMessage("wisedoc.chart.border.title");
	private final static String LAYER = UiText.BORDER_AND_BACK_BAND_LAYER;//MessageResource.getMessage("wisedoc.chart.border.layer");
	private final static String BACK_APAPH = UiText.BORDER_AND_BACK_BAND_APAPH;//MessageResource.getMessage("wisedoc.chart.background.apaph");
	private final static String FORE_APAPH = UiText.BORDER_AND_BACK_BAND_FORE_APAPH;//MessageResource.getMessage("wisedoc.chart.foreground.apaph");
	private final static String BORDER = UiText.BORDER_AND_BACK_BAND_BORDER;//MessageResource.getMessage("wisedoc.chart.border");
	public JRibbonBand getBand() {

		// 表格样式设置
		final JRibbonBand band = new JRibbonBand(BAND_TITLE, MediaResource.getResizableIcon("09379.ico"));

		
		//层设置
		final WiseCombobox layervalue = new WiseCombobox(new DefaultComboBoxModel(UiText.COMMON_COLOR_LAYER));
		layervalue.setPreferredSize(new Dimension(70,20));
		final JRibbonComponent layer = new JRibbonComponent(MediaResource.getResizableIcon("06184.ico"), LAYER, layervalue);
		
		//背景透明度设置
		final WiseSpinner alaphvalue = new WiseSpinner();
		alaphvalue.setPreferredSize(new Dimension(70, 20));
		final SpinnerNumberModel bottomModel = new SpinnerNumberModel(1.0, 0.0, 1.0, 0.1);
		alaphvalue.setModel(bottomModel);
		final JRibbonComponent apaph = new JRibbonComponent(MediaResource.getResizableIcon("05874.ico"),
				BACK_APAPH, alaphvalue);
		
		//前景透明度设置
		final WiseSpinner alaphvalueF = new WiseSpinner();
		alaphvalueF.setPreferredSize(new Dimension(70, 20));
		final SpinnerNumberModel bottomModelF = new SpinnerNumberModel(1.0, 0.0, 1.0, 0.1);
		alaphvalueF.setModel(bottomModelF);
		final JRibbonComponent apaphF = new JRibbonComponent(MediaResource.getResizableIcon("05874.ico"),
				FORE_APAPH, alaphvalueF);
		
		
		RibbonUIManager.getInstance().bind(WisedocChart.LAYER_ACTION, layervalue);
		RibbonUIManager.getInstance().bind(WisedocChart.BACK_ALPHA_ACTION, alaphvalue);
		RibbonUIManager.getInstance().bind(WisedocChart.FRONT_ALPHA_ACTION, alaphvalueF);

		band.addRibbonComponent(layer);
		band.addRibbonComponent(apaph);
		band.addRibbonComponent(apaphF);
		
		
		//边框、背景、背景图按钮
		band.startGroup();
		final JCommandButton border = new JCommandButton(BORDER, MediaResource.getResizableIcon("03466.ico"));
		/*border.setPopupRichTooltip(new RichTooltip("边框和底纹", "设置图边框、底纹"));*/
		border.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		border.setPopupCallback(new PopupPanelCallback() {
			public JPopupPanel getPopupPanel(final JCommandButton arg0) {
				return new BorderAndBackGroundList(ActionType.Chart);
			}
		});
		band.addCommandButton(border, RibbonElementPriority.MEDIUM);

		final JCommandButton backgroundColor = new JCommandButton(
				RibbonUIText.TABLE_BACKGROUND_COLOR_BUTTON, MediaResource
						.getResizableIcon("00417.ico"));
		backgroundColor.setPopupRichTooltip(new RichTooltip(
				RibbonUIText.TABLE_BACKGROUND_COLOR_BUTTON_TITLE,
				RibbonUIText.TABLE_BACKGROUND_COLOR_BUTTON_DESCRIPTION));
		backgroundColor
				.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		backgroundColor.setPopupCallback(new PopupPanelCallback() {
			public JPopupPanel getPopupPanel(final JCommandButton arg0) {
				return new BackGroundColor();
			}
		});
		band.addCommandButton(backgroundColor,
				RibbonElementPriority.MEDIUM);

		final JCommandButton backgroundimage = new JCommandButton(
				RibbonUIText.TABLE_PICTURE_BUTTON, MediaResource
						.getResizableIcon("00931.ico"));
		backgroundimage.setPopupRichTooltip(new RichTooltip(
				RibbonUIText.TABLE_PICTURE_BUTTON_TITLE,
				RibbonUIText.TABLE_PICTURE_BUTTON_DESCRIPTION));
		backgroundimage
				.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		backgroundimage.setPopupCallback(new PopupPanelCallback() {
			public JPopupPanel getPopupPanel(final JCommandButton arg0) {
				return new BackGroundImageList(ActionType.Chart);
			}
		});
		band.addCommandButton(backgroundimage, RibbonElementPriority.MEDIUM);
		
		
		
		return band;
	}

	// 背景颜色下拉菜单
	private class BackGroundColor extends JCommandPopupMenu {

		public BackGroundColor() {
			try {
				final RibbonColorList colorBox = new RibbonColorList();
				RibbonUIManager.getInstance().bind(WisedocChart.BACKGROUND_COLOR_ACTION, colorBox);
				this.add(colorBox.getNullColorButton());
				this.add(colorBox.getPopupComponent());
				this.add(colorBox.getCustomColorButton());
			} catch (final IncompatibleLookAndFeelException e) {
				e.printStackTrace();
			}
		}
	}
}
