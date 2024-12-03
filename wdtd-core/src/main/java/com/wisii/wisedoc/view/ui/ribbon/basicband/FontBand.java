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
package com.wisii.wisedoc.view.ui.ribbon.basicband;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.text.JTextComponent;

import org.jvnet.flamingo.common.CommandToggleButtonGroup;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandButtonStrip;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.JCommandToggleButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.ribbon.JFlowRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.JComboBoxTest;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.jxcombobox.IncompatibleLookAndFeelException;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Font;
import com.wisii.wisedoc.view.ui.parts.dialogs.TextAllPropertiesDialog;
import com.wisii.wisedoc.view.ui.ribbon.BorderAndBackGroundList;
import com.wisii.wisedoc.view.ui.ribbon.common.RibbonColorList;
import com.wisii.wisedoc.view.ui.ribbon.updateUI.FontColorUIUpdate;
import com.wisii.wisedoc.view.ui.svg.transcoded.preferences_desktop_font;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 字体设置Ribbon面板
 * 
 * @author 闫舒寰
 * @version 1.6 2009/02/10
 */
public class FontBand {
	
	
	public JFlowRibbonBand getBand() {
		//字体条
		JFlowRibbonBand fontBand = new JFlowRibbonBand(RibbonUIText.FONT_BAND,
				new preferences_desktop_font(), new FontProperties());
		
		//字体设置，目前是普通的JComboBox，以后还是需要改成可以预览的
		//第一版字体下拉菜单
		WiseCombobox fontFamily = new WiseCombobox(new DefaultComboBoxModel(UiText.FONT_FAMILY_NAMES_LIST));
		fontFamily.setPreferredSize(new Dimension(100,22));
		fontFamily.setEditable(true);
		//为字体设置添加可索引菜单
		JTextComponent editor = (JTextComponent) fontFamily.getEditor().getEditorComponent();
		editor.setDocument(new JComboBoxTest(fontFamily));
		RibbonUIManager.getInstance().bind(Font.FAMILY_ACTION, fontFamily);
		//以Ribbon组件对象包装当前组件，然后以流式排版排列
		JRibbonComponent fontFamilyWrapper = new JRibbonComponent(fontFamily);
		fontBand.addFlowComponent(fontFamilyWrapper);
		
		//字号设置
		WiseCombobox fontSize = new WiseCombobox(new DefaultComboBoxModel(UiText.FONT_SIZE_LIST));
		RibbonUIManager.getInstance().bind(Font.SIZE_ACTION, fontSize);
		fontSize.setActionCommand("use set");
		fontSize.setPreferredSize(new Dimension(70,22));
		fontSize.setEditable(true);
		//以Ribbon组件对象包装当前组件，然后以流式排版排列
		JRibbonComponent fontSizeWrapper = new JRibbonComponent(fontSize);
		fontBand.addFlowComponent(fontSizeWrapper);

		//增加字号
		JCommandButtonStrip fontSizeStrip = new JCommandButtonStrip();
		
		JCommandButton bigger = new JCommandButton("", MediaResource.getResizableIcon("00062.ico"));
		bigger.setActionRichTooltip(new RichTooltip(RibbonUIText.FONT_BIGGER_TITLE, RibbonUIText.FONT_BIGGER_DESCRIPTION));
		JCommandButton smaller = new JCommandButton("", MediaResource.getResizableIcon("00063.ico"));
		smaller.setActionRichTooltip(new RichTooltip(RibbonUIText.FONT_SMALLER_TITLE, RibbonUIText.FONT_SMALLER_DESCRIPTION));
		
		fontSizeStrip.add(bigger);
		fontSizeStrip.add(smaller);
		
		//以Ribbon组件对象包装当前组件，然后以流式排版排列
//		fontBand.addFlowComponent(fontSizeStrip);
		
		//清除格式
		JCommandButtonStrip cleanStrip = new JCommandButtonStrip();
		JCommandButton cleanStyle = new JCommandButton("", MediaResource.getResizableIcon("00368.ico"));
		cleanStyle.setActionRichTooltip(new RichTooltip(RibbonUIText.FONT_CLEAN_TITLE, RibbonUIText.FONT_CLEAN_DESCRIPTION));
		RibbonUIManager.getInstance().bind(Font.CLEAN_ACTION, cleanStyle);
		cleanStrip.add(cleanStyle);
		fontBand.addFlowComponent(cleanStrip);
		
		//文字边框
		JCommandButtonStrip fontBorderStrip = new JCommandButtonStrip();
		JCommandButton border = new JCommandButton("", MediaResource.getResizableIcon("Border.gif"));
		border.setActionRichTooltip(new RichTooltip(RibbonUIText.FONT_BORDER_TITLE, RibbonUIText.FONT_BORDER_DESCRIPTION));
		border.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		border.setPopupCallback(new PopupPanelCallback(){
			public JPopupPanel getPopupPanel(JCommandButton arg0) {
				return new BorderAndBackGroundList(ActionType.INLINE);
			}
		});
		fontBorderStrip.add(border);
		fontBand.addFlowComponent(fontBorderStrip);
		
		//字体样式下面板内容开始
		//添加四种样式、两种字符位置和文字效果
		JCommandButtonStrip styleStrip = new JCommandButtonStrip();
		
		//粗体按钮
		JCommandToggleButton styleBoldButton = new JCommandToggleButton("",	MediaResource.getResizableIcon("00113.ico"));
		RibbonUIManager.getInstance().bind(Font.BOLD_ACTION, styleBoldButton);
		styleBoldButton.setActionRichTooltip(new RichTooltip(RibbonUIText.FONT_BOLD_TITLE, RibbonUIText.FONT_BOLD_DESCRIPTION));
		styleStrip.add(styleBoldButton);
		
		//斜体按钮	
		JCommandToggleButton styleItalicButton = new JCommandToggleButton("", MediaResource.getResizableIcon("00114.ico"));
		RibbonUIManager.getInstance().bind(Font.ITALIC_ACTION, styleItalicButton);
		styleItalicButton.setActionRichTooltip(new RichTooltip(RibbonUIText.FONT_ITALIC_TITLE, RibbonUIText.FONT_ITALIC_DESCRIPTION));
		styleStrip.add(styleItalicButton);

		//下划线按钮
		JCommandToggleButton styleUnderlineButton = new JCommandToggleButton("", MediaResource.getResizableIcon("03962.ico"));
		RibbonUIManager.getInstance().bind(Font.UNDERLINE_ACTION, styleUnderlineButton);
		styleUnderlineButton.setActionRichTooltip(new RichTooltip(RibbonUIText.FONT_UNDER_LINE_TITLE, RibbonUIText.FONT_UNDER_LINE_DESCRIPTION));
		styleStrip.add(styleUnderlineButton);

		//删除线按钮
		JCommandToggleButton styleStrikeThroughButton = new JCommandToggleButton(
				"", MediaResource.getResizableIcon("00290.ico"));
		RibbonUIManager.getInstance().bind(Font.LINE_THROUGH_ACTION, styleStrikeThroughButton);
		styleStrikeThroughButton.setActionRichTooltip(new RichTooltip(RibbonUIText.FONT_STRIKE_THROUGH_TITLE, RibbonUIText.FONT_STRIKE_THROUGH_DESCRIPTION));
		styleStrip.add(styleStrikeThroughButton);
		
		//大小写转换
		/*JCommandButton caseConversion = new JCommandButton("", MediaResource.getResizableIcon("00309.ico"));
		caseConversion.setToolTipText("大小写转换");
		caseConversion.setComponentPopupMenu(null);
		styleStrip.add(caseConversion);*/
		
//		fontBand.addFlowComponent(styleStrip);
		
		//存放上下标的容器
		JCommandButtonStrip subSuperStrip = new JCommandButtonStrip();
		CommandToggleButtonGroup subSuper = new CommandToggleButtonGroup();
		//上标
		JCommandToggleButton superscript = new JCommandToggleButton("", MediaResource.getResizableIcon("00057.ico"));
		superscript.setActionRichTooltip(new RichTooltip(RibbonUIText.FONT_SUPER_SCRIPT_TITLE, RibbonUIText.FONT_SUPER_SCRIPT_DESCRIPTON));
		RibbonUIManager.getInstance().bind(Font.SUPER_ACTION, superscript);
		subSuper.add(superscript);
		subSuperStrip.add(superscript);
		
		//下标
		JCommandToggleButton subscript = new JCommandToggleButton("", MediaResource.getResizableIcon("00058.ico"));
		subscript.setActionRichTooltip(new RichTooltip(RibbonUIText.FONT_SUB_SCRIPT_TITLE, RibbonUIText.FONT_SUB_SCRIPT_DESCRIPTION));
		RibbonUIManager.getInstance().bind(Font.SUB_ACTION, subscript);
		subSuper.add(subscript);
		subSuperStrip.add(subscript);
		
		fontBand.addFlowComponent(subSuperStrip);
		//样式在上下标后面
		fontBand.addFlowComponent(styleStrip);
		
		//高亮
		JCommandButtonStrip fontHeighLighterStrip = new JCommandButtonStrip();
		
		//高亮菜单
		/**第三个版本 已经解决了选项菜单不消失的问题了**/
		JCommandButton heighLighter = new JCommandButton("", MediaResource.getResizableIcon("00340.ico"));
		FontColorUIUpdate.HeighLighter.setButton(heighLighter);
		heighLighter.setActionRichTooltip(new RichTooltip(RibbonUIText.FONT_HEIGH_LIGHTER_TITLE, RibbonUIText.FONT_HEIGH_LIGHTER_DESCRIPTION));
		
		heighLighter.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		heighLighter.setPopupCallback(new PopupPanelCallback(){
			public JPopupPanel getPopupPanel(JCommandButton arg0) {
				return new HeightLighter();
			}
		});
		//文字高亮层
		WiseCombobox heighLighterLayer = new WiseCombobox(new DefaultComboBoxModel(UiText.COMMON_COLOR_LAYER));
		RibbonUIManager.getInstance().bind(Font.BACKGROUND_COLOR_LAYER_ACTION, heighLighterLayer);
		fontHeighLighterStrip.add(heighLighter);		
		//以Ribbon组件对象包装当前组件，然后以流式排版排列
		fontBand.addFlowComponent(fontHeighLighterStrip);
		JRibbonComponent heighLighterLayerWrapper = new JRibbonComponent(heighLighterLayer);
		fontBand.addFlowComponent(heighLighterLayerWrapper);
		
		//文字颜色菜单组
		JCommandButtonStrip fontColorStrip = new JCommandButtonStrip();
		
		//文字颜色
		JCommandButton fontColor = new JCommandButton("", MediaResource.getResizableIcon("02611.ico"));
		FontColorUIUpdate.FontColor.setButton(fontColor);
		fontColor.addActionListener(new FontColorButtonAction());
		fontColor.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
		fontColor.setPopupCallback(new PopupPanelCallback(){
			public JPopupPanel getPopupPanel(JCommandButton arg0) {
				return new FontColor();
			}
		});
		
		fontColorStrip.add(fontColor);		
		
		//文字颜色层
		WiseCombobox fontColorLayer = new WiseCombobox(new DefaultComboBoxModel(UiText.COMMON_COLOR_LAYER));
		RibbonUIManager.getInstance().bind(Font.COLOR_LAYER_ACTION, fontColorLayer);
		//以Ribbon组件对象包装当前组件，然后以流式排版排列
		fontBand.addFlowComponent(fontColorStrip);
		JRibbonComponent fontColorLayerWrapper = new JRibbonComponent(fontColorLayer);
		fontBand.addFlowComponent(fontColorLayerWrapper);
		
		return fontBand;
	}
		
	//全部字体属性设置菜单，就是在最下面那个箭头弹出的菜单
	private class FontProperties implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new TextAllPropertiesDialog(ActionType.INLINE);
		}
	}	
	
	/**
	 * 字体颜色下拉面板
	 * @author 闫舒寰
	 *
	 */
	private class FontColor extends JCommandPopupMenu {
		
		public FontColor() {
			try {
				RibbonColorList colorBox = new RibbonColorList();
				RibbonUIManager.getInstance().bind(Font.COLOR_ACTION, colorBox);
				this.add(colorBox.getAutoColorButton());
				this.add(colorBox.getPopupComponent());
				this.add(colorBox.getCustomColorButton());	
				
			} catch (IncompatibleLookAndFeelException e1) {
				e1.printStackTrace();
			}		
		}		
	}
	
	/**
	 * 当用户单击颜色按钮的时候通过这个action来设置颜色
	 * @author 闫舒寰
	 *
	 */
	private class FontColorButtonAction extends Actions {

		@Override
		public void doAction(ActionEvent e) {
			/*System.out.println("font color: " + FontColorUIUpdate.FontColor.getClass());
			System.out.println("font color: " + FontColorUIUpdate.FontColor.getColor());
			System.out.println("heighLighter color: " + FontColorUIUpdate.HeighLighter.getClass());
			System.out.println("heighLighter color: " + FontColorUIUpdate.HeighLighter.getColor());*/
			
			setFOProperty(Constants.PR_COLOR, FontColorUIUpdate.FontColor.getColor());
		}
	}

	/**
	 * 高亮显示文字
	 * @author 闫舒寰
	 *
	 */
	private class HeightLighter extends JCommandPopupMenu {
		
		public HeightLighter() {
		
			try {
				RibbonColorList colorBox = new RibbonColorList();
				colorBox.addActionListener(com.wisii.wisedoc.view.ui.manager.ActionFactory.getAction(Font.HIGH_LIGHTING_ACTION));
				this.add(colorBox.getNullColorButton());
				this.add(colorBox.getPopupComponent());
				this.add(colorBox.getCustomColorButton());
			} catch (IncompatibleLookAndFeelException e) {
				e.printStackTrace();
			}
		}
	}
	
	//暂时不能用这个，这样会有显示方面的文字
	private class BoldStyle extends JCommandPopupMenu {
		
		public BoldStyle() {		
			JCommandMenuButton en_200 = new JCommandMenuButton("200粗", MediaResource.getResizableIcon("00146.ico"));
			JCommandMenuButton en_400 = new JCommandMenuButton("400粗", MediaResource.getResizableIcon("00146.ico"));
			JCommandMenuButton en_600 = new JCommandMenuButton("600粗", MediaResource.getResizableIcon("00146.ico"));
			JCommandMenuButton en_800 = new JCommandMenuButton("800粗", MediaResource.getResizableIcon("00146.ico"));
			this.addMenuButton(en_200);
			this.addMenuButton(en_400);
			this.addMenuButton(en_600);
			this.addMenuButton(en_800);		
		}
	}
}
