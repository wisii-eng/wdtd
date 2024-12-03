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
package com.wisii.wisedoc.view.ui.ribbon.graph;

import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.text.JTextComponent;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandButtonStrip;
import org.jvnet.flamingo.common.JCommandToggleButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.ribbon.JFlowRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.JComboBoxTest;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.jxcombobox.IncompatibleLookAndFeelException;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.SvgGraphic;
import com.wisii.wisedoc.view.ui.ribbon.common.RibbonColorList;
import com.wisii.wisedoc.view.ui.svg.transcoded.preferences_desktop_font;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 图形内文字属性设置
 * @author 闫舒寰
 * @version 1.0 2009/02/27
 */
public class GraphTextStyleBand {
	
	public JFlowRibbonBand getBand() {
		
		//条形码字体条
		JFlowRibbonBand band = new JFlowRibbonBand(RibbonUIText.BARCODE_FONT_BAND, new preferences_desktop_font());
		
		//条形码字体设置，目前是普通的JComboBox，以后还是需要改成可以预览的
		//第一版条形码字体下拉菜单
		WiseCombobox fontFamily = new WiseCombobox(new DefaultComboBoxModel(backSort(UiText.FONT_FAMILY_NAMES_LIST)));
		fontFamily.setPreferredSize(new Dimension(100,22));
		fontFamily.setEditable(true);
		
		//为条形码字体设置添加可索引菜单
		JTextComponent editor = (JTextComponent) fontFamily.getEditor().getEditorComponent();
		editor.setDocument(new JComboBoxTest(fontFamily));
		RibbonUIManager.getInstance().bind(SvgGraphic.FONT_FAMILY_ACTION, fontFamily);
		//以Ribbon组件对象包装当前组件，然后以流式排版排列
		JRibbonComponent fontFamilyWrapper = new JRibbonComponent(fontFamily);
		band.addFlowComponent(fontFamilyWrapper);
		
		//条形码内容字号设置
		WiseCombobox fontSize = new WiseCombobox(new DefaultComboBoxModel(UiText.FONT_SIZE_LIST));
		RibbonUIManager.getInstance().bind(SvgGraphic.FONT_SIZE_ACTION, fontSize);
		fontSize.setPreferredSize(new Dimension(70,22));
		fontSize.setEditable(true);
		//以Ribbon组件对象包装当前组件，然后以流式排版排列
		JRibbonComponent fontSizeWrapper = new JRibbonComponent(fontSize);
		band.addFlowComponent(fontSizeWrapper);
		
		//清除条形码样式
		JCommandButtonStrip cleanStrip = new JCommandButtonStrip();		
		JCommandButton cleanStyle = new JCommandButton("", MediaResource.getResizableIcon("00047.ico"));
		cleanStyle.setActionRichTooltip(new RichTooltip("清除条形码样式", "还原条形码样式到默认值"));
		//RibbonUIManager.getInstance().bind(Barcode.CLEAN_STYLE_ACTION, cleanStyle);
		cleanStrip.add(cleanStyle);
		band.addFlowComponent(cleanStrip);
		
		//添加四种样式、两种字符位置和文字效果
		JCommandButtonStrip styleStrip = new JCommandButtonStrip();
		
		//粗体按钮
		JCommandToggleButton styleBoldButton = new JCommandToggleButton("",	MediaResource.getResizableIcon("00113.ico"));
		styleBoldButton.setActionRichTooltip(new RichTooltip("粗体", "设置条形码下文字为粗体"));
		RibbonUIManager.getInstance().bind(SvgGraphic.FONT_WEIGHT_ACTION, styleBoldButton);			
		styleStrip.add(styleBoldButton);
		
		//斜体按钮	
		JCommandToggleButton styleItalicButton = new JCommandToggleButton("", MediaResource.getResizableIcon("00114.ico"));
		styleItalicButton.setActionRichTooltip(new RichTooltip("斜体", "设置条形码下文字为斜体"));
		RibbonUIManager.getInstance().bind(SvgGraphic.FONT_ITALIC_ACTION, styleItalicButton);
		styleStrip.add(styleItalicButton);	
		
		//下划线按钮
		JCommandToggleButton styleUnderlineButton = new JCommandToggleButton("", MediaResource.getResizableIcon("03962.ico"));
		RibbonUIManager.getInstance().bind(SvgGraphic.FONT_UNDERLINE_ACTION, styleUnderlineButton);
		styleUnderlineButton.setActionRichTooltip(new RichTooltip(RibbonUIText.FONT_UNDER_LINE_TITLE, RibbonUIText.FONT_UNDER_LINE_DESCRIPTION));
		styleStrip.add(styleUnderlineButton);

		//删除线按钮
		JCommandToggleButton styleStrikeThroughButton = new JCommandToggleButton(
				"", MediaResource.getResizableIcon("00290.ico"));
		RibbonUIManager.getInstance().bind(SvgGraphic.FONT_LINE_THROUGH_ACTION, styleStrikeThroughButton);
		styleStrikeThroughButton.setActionRichTooltip(new RichTooltip(RibbonUIText.FONT_STRIKE_THROUGH_TITLE, RibbonUIText.FONT_STRIKE_THROUGH_DESCRIPTION));
		styleStrip.add(styleStrikeThroughButton);
		
		band.addFlowComponent(styleStrip);
		
		//文字颜色菜单组
		JCommandButtonStrip fontColorStrip = new JCommandButtonStrip();
//		fontColorStrip.setElementState(ElementState.COMPACT);
		//文字颜色
		JCommandButton fontColor = new JCommandButton("", MediaResource.getResizableIcon("02611.ico"));
		fontColor.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		fontColor.setPopupCallback(new PopupPanelCallback(){
			public JPopupPanel getPopupPanel(JCommandButton arg0) {
				return new FontColor();
			}
		});
		fontColorStrip.add(fontColor);	
		
		band.addFlowComponent(fontColorStrip);
		
		//条形码字符集
		/*JComboBox barcodeSubset = new JComboBox(new DefaultComboBoxModel(RibbonUIText.BARCODE_SUBSET));
		RibbonUIManager.getInstance().bind(Barcode.SUBSET_ACTION, barcodeSubset);
		barcodeSubset.setEditable(false);
		//以Ribbon组件对象包装当前组件，然后以流式排版排列
		JRibbonComponent barcodeSubsetWrapper = new JRibbonComponent(barcodeSubset);
		barcodeFontBand.addFlowComponent(barcodeSubsetWrapper);*/
		
		//目前(2009/02/03)条形码文字和条形码属于一体，没有层的问题
		/*WiseCombobox fontColorLayer = new WiseCombobox(new DefaultComboBoxModel(UiText.COMMON_COLOR_LAYER));
		RibbonUIManager.getInstance().bind(Barcode.FONT_COLOR_LAYER_ACTION, fontColorLayer);
		bottomPanel.add(fontColorLayer);*/

		return band;
	}
		
	private class FontColor extends JCommandPopupMenu {
		
		public FontColor() {
			try {
				RibbonColorList colorBox = new RibbonColorList();
				RibbonUIManager.getInstance().bind(SvgGraphic.FONT_COLOR_ACTION, colorBox);
				this.add(colorBox.getPopupComponent());
				this.add(colorBox.getCustomColorButton());
			} catch (IncompatibleLookAndFeelException e1) {
				e1.printStackTrace();
			}		
		}		
	}
	
	/*
	 * 逆序显示字体，暂时为了方便选择中文字体
	 */
	private String[] backSort(String[] a){
		
		String[] b = new String[a.length];
		
		for (int i = a.length - 1, j = 0; i >= 0 & j < a.length; i--, j++) {
			b[j] = a[i];
		}
		return b;
	}

}
