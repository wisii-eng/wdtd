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

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jvnet.flamingo.common.CommandToggleButtonGroup;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandButtonStrip;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.JCommandToggleButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.icon.EmptyResizableIcon;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.jxcombobox.IncompatibleLookAndFeelException;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Paragraph;
import com.wisii.wisedoc.view.ui.parts.dialogs.ParagraphAllPropertiesDialog;
import com.wisii.wisedoc.view.ui.ribbon.BorderAndBackGroundList;
import com.wisii.wisedoc.view.ui.ribbon.common.RibbonColorList;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 段落的Ribbon面板
 * 
 * @author 闫舒寰
 * @version 1.0 2008/11/25
 */
public class ParagraphBand
{

	public JRibbonBand getBand()
	{

		// 图标是用Flamigo SVG transcoder转化成Java类，然后直接new 就可以了，这个应该是最小的时候的图标
		JRibbonBand paragraphBand = new JRibbonBand(RibbonUIText.PARAGRAPH_BAND, MediaResource.getResizableIcon("09379.ico"),
				new ExpandActionListener());

		JPanel beforePanel = new JPanel(new GridLayout(2, 1));
		JPanel topPanel = new JPanel(new FlowLayout());
		JPanel bottomPanel = new JPanel(new FlowLayout());

		beforePanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		beforePanel.add(topPanel);
		beforePanel.add(bottomPanel);

		// 左侧上板内容开始
		paragraphBand.startGroup();

		// 添加后四种对齐方式
		JCommandButtonStrip alignStrip = new JCommandButtonStrip();

		CommandToggleButtonGroup alignGroup = new CommandToggleButtonGroup();

		JCommandToggleButton alignLeftButton = new JCommandToggleButton("",
				MediaResource.getResizableIcon("AlignLeft.gif"));
		alignLeftButton.setActionRichTooltip(new RichTooltip(RibbonUIText.PARAGRAPH_ALIGN_LEFT_TITLE, RibbonUIText.PARAGRAPH_ALIGN_LEFT_DESCRIPTION));
		RibbonUIManager.getInstance().bind(Paragraph.TEXT_ALIGN_START_ACTION, alignLeftButton);
		alignGroup.add(alignLeftButton);
		alignStrip.add(alignLeftButton);

		JCommandToggleButton alignCenterButton = new JCommandToggleButton("",
				MediaResource.getResizableIcon("AlignCenter.gif"));
		alignCenterButton.setActionRichTooltip(new RichTooltip(RibbonUIText.PARAGRAPH_ALIGN_CENTER_TITLE, RibbonUIText.PARAGRAPH_ALIGN_CENTER_DESCRIPTION));
		RibbonUIManager.getInstance().bind(Paragraph.TEXT_ALIGN_CENTER_ACTION,
				alignCenterButton);
		alignGroup.add(alignCenterButton);
		alignStrip.add(alignCenterButton);

		JCommandToggleButton alignRightButton = new JCommandToggleButton("",
				MediaResource.getResizableIcon("AlignRight.gif"));
		alignRightButton.setActionRichTooltip(new RichTooltip(RibbonUIText.PARAGRAPH_ALIGN_RIGHT_TITLE, RibbonUIText.PARAGRAPH_ALIGN_RIGHT_DESCRIPTION));
		RibbonUIManager.getInstance().bind(Paragraph.TEXT_ALIGN_END_ACTION,
				alignRightButton);
		alignGroup.add(alignRightButton);
		alignStrip.add(alignRightButton);

		JCommandToggleButton alignJustifyButton = new JCommandToggleButton("",
				MediaResource.getResizableIcon("02792.ico"));
		alignJustifyButton.setActionRichTooltip(new RichTooltip(RibbonUIText.PARAGRAPH_ALIGN_JUSTIFY_TITLE, RibbonUIText.PARAGRAPH_ALIGN_JUSTIFY_DESCRIPTION));
		RibbonUIManager.getInstance().bind(Paragraph.TEXT_ALIGN_JUSTIFY_ACTION,
				alignJustifyButton);
		alignGroup.add(alignJustifyButton);
		alignStrip.add(alignJustifyButton);

		topPanel.add(alignStrip);

		// 行间距
		// 1、设置一个按钮组，这个按钮组可以使按钮紧挨到一起
		JCommandButtonStrip lineSpaceStrip = new JCommandButtonStrip();

		// 3、开始创建真正的按钮并陪图片
		JCommandButton lineSpace = new JCommandButton("", MediaResource
				.getResizableIcon("02710.ico"));
		// 4、为按钮添加悬浮的解释
		lineSpace.setActionRichTooltip(new RichTooltip(RibbonUIText.PARAGRAPH_LINE_SPACE_TITLE, RibbonUIText.PARAGRAPH_LINE_SPACE_DESCRIPTION));

		// 5、设置按钮下拉菜单的弹出方式
		lineSpace
				.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		// 6、为弹出式下拉菜单添加监听器
		lineSpace.setPopupCallback(new PopupPanelCallback()
		{
			@Override
			public JPopupPanel getPopupPanel(JCommandButton commandButton)
			{
				return new LineSpace();
			}
		});
		// 7、添加按钮到Strip上
		lineSpaceStrip.add(lineSpace);
		// 8、添加strip到其他容器中，例如JPanel或者JRibbonBand
		topPanel.add(lineSpaceStrip);

		// 背景和边框设置
		JCommandButtonStrip backgroundStrip = new JCommandButtonStrip();
		JCommandButton backgroundColor = new JCommandButton("", MediaResource
				.getResizableIcon("00417.ico"));
		backgroundColor.setPopupRichTooltip(new RichTooltip(RibbonUIText.PARAGRAPH_BACKGROUND_COLOR_TITLE, RibbonUIText.PARAGRAPH_BACKGROUND_COLOR_DESCRIPTION));
		backgroundColor
				.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		backgroundColor.setPopupCallback(new PopupPanelCallback()
		{
			public JPopupPanel getPopupPanel(JCommandButton arg0)
			{
				return new BackGroundColor();
			}
		});

		backgroundStrip.add(backgroundColor);
		//段落背景颜色层
		WiseCombobox backgroundColorLayer = new WiseCombobox(
				new DefaultComboBoxModel(UiText.COMMON_COLOR_LAYER));
		RibbonUIManager.getInstance().bind(
				Paragraph.BACKGROUND_COLOR_LAYER_ACTION, backgroundColorLayer);
		//以Ribbon组件对象包装当前组件，然后以流式排版排列
		/*JRibbonComponent backgroundStripWrapper = new JRibbonComponent(backgroundStrip);
		paragraphBand.addRibbonComponent(backgroundStripWrapper);
		JRibbonComponent backgroundColorLayerWrapper = new JRibbonComponent(backgroundColorLayer);
		paragraphBand.addRibbonComponent(backgroundColorLayerWrapper);*/

		JCommandButtonStrip borderStrip = new JCommandButtonStrip();
		JCommandButton border = new JCommandButton("", MediaResource
				.getResizableIcon("Border.gif"));
		border.setPopupRichTooltip(new RichTooltip(RibbonUIText.PARAGRAPH_BORDER_TITLE, RibbonUIText.PARAGRAPH_BORDER_DESCRIPTION));
		border.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		border.setPopupCallback(new PopupPanelCallback()
		{
			public JPopupPanel getPopupPanel(JCommandButton arg0)
			{
				return new BorderAndBackGroundList(ActionType.BLOCK);
			}
		});
		borderStrip.add(border);
		/*JRibbonComponent borderStripWrapper = new JRibbonComponent(borderStrip);
		paragraphBand.addRibbonComponent(borderStripWrapper);*/
		bottomPanel.add(backgroundStrip);
		bottomPanel.add(backgroundColorLayer);
		bottomPanel.add(borderStrip);
		
//		JComboBox index = new JComboBox(new DefaultComboBoxModel(new String[] {"1","2","3","4","5"}));
//		bottomPanel.add(index);

		// 左侧下面板结束
		
		// 放置主面板到fontband上
//		 paragraphBand.addPanel(beforePanel);
		JRibbonComponent beforePanelWrapper = new JRibbonComponent(beforePanel);
		paragraphBand.addRibbonComponent(beforePanelWrapper);

		paragraphBand.startGroup(RibbonUIText.PARAGRAPH_INDENT);
		
		// 右侧面板开始，缩进面板
		IndentPanel indents = new IndentPanel();
		JRibbonComponent indentsWrapper = new JRibbonComponent(indents);
		paragraphBand.addRibbonComponent(indentsWrapper);
		
		RibbonUIManager.getInstance().bind(Paragraph.START_INDENT,
				indents.getStartIndentValue());
		RibbonUIManager.getInstance().bind(Paragraph.END_INDENT,
				indents.getEndIndentValue());


		return paragraphBand;
	}

	private class ExpandActionListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e)
		{
			new ParagraphAllPropertiesDialog(ActionType.BLOCK);
		}
	}

	// 背景颜色下拉菜单
	private class BackGroundColor extends JCommandPopupMenu
	{
		public BackGroundColor()
		{
			try
			{
				RibbonColorList colorBox = new RibbonColorList();
				// colorBox.addActionListener(com.wisii.wisedoc.view.ui.manager.
				// ActionFactory.getAction(Paragraph.BACKGROUND_COLOR_ACTION));
				RibbonUIManager.getInstance().bind(
						Paragraph.BACKGROUND_COLOR_ACTION, colorBox);
				this.add(colorBox.getNullColorButton());
				this.add(colorBox.getPopupComponent());
				this.add(colorBox.getCustomColorButton());
			} catch (IncompatibleLookAndFeelException e)
			{
				e.printStackTrace();
			}

		}
	}

	// 弹出式下拉菜单项目
	public static class LineSpace extends JCommandPopupMenu
	{

		public LineSpace()
		{

			JCommandMenuButton t1 = new JCommandMenuButton(RibbonUIText.PARAGRAPH_LINE_SPACE_LIST[0],
					new EmptyResizableIcon(16));
			JCommandMenuButton t1_15 = new JCommandMenuButton(RibbonUIText.PARAGRAPH_LINE_SPACE_LIST[1],
					new EmptyResizableIcon(16));
			JCommandMenuButton t1_5 = new JCommandMenuButton(RibbonUIText.PARAGRAPH_LINE_SPACE_LIST[2],
					new EmptyResizableIcon(16));
			JCommandMenuButton t2 = new JCommandMenuButton(RibbonUIText.PARAGRAPH_LINE_SPACE_LIST[3],
					new EmptyResizableIcon(16));
			JCommandMenuButton t2_5 = new JCommandMenuButton(RibbonUIText.PARAGRAPH_LINE_SPACE_LIST[4],
					new EmptyResizableIcon(16));
			JCommandMenuButton t3 = new JCommandMenuButton(RibbonUIText.PARAGRAPH_LINE_SPACE_LIST[5],
					new EmptyResizableIcon(16));
            RibbonUIManager.getInstance().bind(Paragraph.LINEHEIGHT_100_ACTION, t1);
            RibbonUIManager.getInstance().bind(Paragraph.LINEHEIGHT_115_ACTION, t1_15);
            RibbonUIManager.getInstance().bind(Paragraph.LINEHEIGHT_150_ACTION, t1_5);
            RibbonUIManager.getInstance().bind(Paragraph.LINEHEIGHT_200_ACTION, t2);
            RibbonUIManager.getInstance().bind(Paragraph.LINEHEIGHT_250_ACTION, t2_5);
            RibbonUIManager.getInstance().bind(Paragraph.LINEHEIGHT_300_ACTION, t3);
			this.addMenuButton(t1);
			this.addMenuButton(t1_15);
			this.addMenuButton(t1_5);
			this.addMenuButton(t2);
			this.addMenuButton(t2_5);
			this.addMenuButton(t3);

			this.addMenuSeparator();

			JCommandMenuButton line = new JCommandMenuButton(RibbonUIText.PARAGRAPH_LINE_SPACE_MENUE,
					new EmptyResizableIcon(16));
			this.addMenuButton(line);

			line.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					//该index是用来标记应该切换到的详细菜单中的面板
					int INDEX = 0;// 原来的该选项卡位于第三项，现已调整到第一项
					new ParagraphAllPropertiesDialog(ActionType.BLOCK, INDEX);
				}
			});

			/*
			 * this.addMenuSeparator(); this.addMenuButton(new
			 * JCommandMenuButton("增加段前距", new EmptyResizableIcon(16)));
			 * this.addMenuButton(new JCommandMenuButton("增加段后距", new
			 * EmptyResizableIcon(16)));
			 */
		}
	}

	public static class ParagraphPage extends JCommandPopupMenu
	{

		public ParagraphPage()
		{

			JCommandMenuButton breakBefore = new JCommandMenuButton("段前分页",
					new EmptyResizableIcon(16));
			JCommandMenuButton breakAfter = new JCommandMenuButton("断后分页",
					new EmptyResizableIcon(16));
			JCommandMenuButton keepTogether = new JCommandMenuButton("当前段不分页",
					new EmptyResizableIcon(16));
			JCommandMenuButton keepWithNext = new JCommandMenuButton("和下段同页",
					new EmptyResizableIcon(16));
			JCommandMenuButton keepWithPrevious = new JCommandMenuButton(
					"和上段同页", new EmptyResizableIcon(16));

			this.addMenuButton(breakBefore);
			this.addMenuButton(breakAfter);
			this.addMenuButton(keepTogether);
			this.addMenuButton(keepWithNext);
			this.addMenuButton(keepWithPrevious);

			RibbonUIManager.getInstance().bind(Paragraph.BREAK_BEFORE_ACTION,
					breakBefore);
			RibbonUIManager.getInstance().bind(Paragraph.BREAK_AFTER_ACTION,
					breakAfter);
			RibbonUIManager.getInstance().bind(Paragraph.KEEP_TOGETHER_ACTION,
					keepTogether);
			RibbonUIManager.getInstance().bind(Paragraph.KEEP_WITH_NEXT_ACTION,
					keepWithNext);
			RibbonUIManager.getInstance().bind(
					Paragraph.KEEP_WITH_PREVIOUS_ACTION, keepWithPrevious);
		}
	}

}
