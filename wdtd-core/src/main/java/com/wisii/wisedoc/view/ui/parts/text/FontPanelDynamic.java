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
package com.wisii.wisedoc.view.ui.parts.text;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.render.awt.viewer.WisedocConfigurePreviewPanel;
import com.wisii.wisedoc.swing.ui.JComboBoxTest;
import com.wisii.wisedoc.swing.ui.LayerComboBox;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.jxcombobox.IncompatibleLookAndFeelException;
import com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo.ColorComboBox;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Font;
import com.wisii.wisedoc.view.ui.model.FontPropertyModel;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 字体设置对话框面板
 * @author 闫舒寰
 * @version 1.0 2009/01/16
 */
@SuppressWarnings("serial")
public class FontPanelDynamic extends JPanel {

	/**
	 * Create the panel
	 */
	public FontPanelDynamic() {
		super();

		JPanel panel;
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new FontSetPanel(), BorderLayout.CENTER);
		/* 【添加：START】by 李晓光 2009-1-6 */
		final Border empty = BorderFactory.createEmptyBorder(0, 8, 0, 8);
		/* 【添加：END】by 李晓光 2009-1-6 */
		final JPanel panelAll = new JPanel();
		final Border border = BorderFactory.createCompoundBorder(empty, BorderFactory.createTitledBorder(UiText.FONT_COLOR_BORDER_LABEL));
		panelAll.setBorder(border);
		panelAll.setLayout(new BorderLayout());
		panelAll.add(new FontColorPanel(), BorderLayout.CENTER);
		
		//文字效果面板
		JPanel panel_1;
		panel_1 = new JPanel();
		/* 【添加：START】by 李晓光 2009-1-6 */
		final Border border_1 = BorderFactory.createCompoundBorder(empty, BorderFactory.createTitledBorder(UiText.FONT_EFFECT_BORDER_LABEL));
		/* 【添加：END】by 李晓光 2009-1-6 */
		/*panel_1.setBorder(new TitledBorder("文字效果："));*/
		panel_1.setBorder(border_1);
		panel_1.setLayout(new BorderLayout());
		panel_1.add(new FontEffect(), BorderLayout.CENTER);

		//预览面板
		JPanel panel_2;
		panel_2 = new JPanel();
		/* 【添加：START】by 李晓光 2009-1-6 */
		final Border border_2 =  BorderFactory.createCompoundBorder(empty, BorderFactory.createTitledBorder(UiText.PREVIEW_BORDER_LABEL));
		/* 【添加：END】by 李晓光 2009-1-6 */
		panel_2.setBorder(border_2);
		panel_2.setLayout(new BorderLayout());
		
		final WisedocConfigurePreviewPanel previewPanel = WisedocConfigurePreviewPanel.getInstance();
		Map<Integer, Object> map = RibbonUIModel.getCurrentPropertiesByType().get(ActionType.INLINE);
		FontPropertyModel.addPropertyChangeListener(previewPanel);
		map = filterMap(map);
		previewPanel.initValues(map, ActionType.INLINE, Boolean.FALSE);
		panel_2.add(previewPanel, BorderLayout.CENTER);
		
		final GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 380, GroupLayout.DEFAULT_SIZE)//Short.MAX_VALUE
				.addComponent(panelAll, GroupLayout.DEFAULT_SIZE, 380, GroupLayout.DEFAULT_SIZE)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 380, GroupLayout.DEFAULT_SIZE)//458->380
				.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 380, GroupLayout.DEFAULT_SIZE)//458->380
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)//78->60
					/*.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)*/
					.addComponent(panelAll, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					/*.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)*/
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)//117->55
					/*.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)*/
					.addComponent(panel_2)//, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE
					.addContainerGap(GroupLayout.DEFAULT_SIZE, 5))//Short.MAX_VALUE->5
		);
		setLayout(groupLayout);
		//
	}
	
	private Map<Integer, Object> filterMap(final Map<Integer, Object> map){
		
		if (map == null) {
			return new HashMap<Integer, Object>();
		}
		
		final Integer[] keys = {Constants.PR_FONT_FAMILY, Constants.PR_FONT_SIZE, Constants.PR_FONT_STYLE, Constants.PR_FONT_WEIGHT, Constants.PR_TEXT_DECORATION, Constants.PR_COLOR};
		/*Set<Integer> keys = map.keySet();*/
		final Map<Integer, Object> result = new HashMap<Integer, Object>();
		for (final Integer key : keys) {
			result.put(key, map.get(key));
		}
		return result;
	}
	
	//颜色属性设置面板
	private class FontColorPanel extends JPanel{
		
		JComboBox color_comboBox;
		
		FontColorPanel(){
			super();
			
			setPreferredSize(new Dimension(1, 30));
			
			//颜色标签
			JLabel font_color;
			font_color = new JLabel();
			font_color.setText(UiText.FONT_COLOR_LABEL);
			
			//颜色设置
			try {
				color_comboBox = new ColorComboBox();
			} catch (final IncompatibleLookAndFeelException e) {
				e.printStackTrace();
			}
			
			//颜色层标签
			final JLabel labLayer = new JLabel(UiText.FONT_COLOR_LAYER_LABEL);
			//颜色层设置
			final WiseCombobox comLayer = new LayerComboBox();
			
			//颜色监听器
			RibbonUIManager.getInstance().bind(Font.COLOR_ACTION, ActionCommandType.DYNAMIC_ACTION, color_comboBox, comLayer);
			
			final GroupLayout groupLayout = new GroupLayout(this);
			groupLayout.setHorizontalGroup(
					groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(font_color)
							.addGap(5, 5, 5)
							.addComponent(color_comboBox, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
							.addGap(5, 10, 10)
							.addComponent(labLayer)
							.addGap(5, 5, 5)
							.addComponent(comLayer, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
			
			groupLayout.setVerticalGroup(
					groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1, 1, 1)
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
								.addComponent(font_color, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)//26
								.addComponent(color_comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(labLayer)
								.addComponent(comLayer))
							.addContainerGap(GroupLayout.DEFAULT_SIZE, 5))
				);
			setLayout(groupLayout);
		}
	}
	
	//文字 字体、字号和字型面板
	private class FontSetPanel extends JPanel{
				
		//字体样式
		//字形样式
		String fontStyle[] = UiText.FONT_STYLE_LIST;
		
		//字号列表
		String[] fontSize = UiText.FONT_SIZE_LIST;

		private JComboBox font_style_comboBox;
		private JComboBox font_size_comboBox;
		private WiseCombobox font_family_comboBox;
		
		/*ColorComboBox color_comboBox;*/
		
		//测试用，文字样式预览
		Color fontColor;

		/**
		 * Create the panel
		 */
		public FontSetPanel() {
			super();
			
			//该Panel用GroupLayout布局，在后面给出
			
			//字体标签
			JLabel font_family;
			font_family = new JLabel();
			font_family.setText(UiText.FONT_FAMILY_NAMES_LABEL);
			//字体设置
			font_family_comboBox = new WiseCombobox(new DefaultComboBoxModel(UiText.FONT_FAMILY_NAMES_LIST));
			font_family_comboBox.setEditable(true);
			//为字体设置添加可索引菜单
			final JTextComponent editor = (JTextComponent) font_family_comboBox.getEditor().getEditorComponent();
			editor.setDocument(new JComboBoxTest(font_family_comboBox));
			
			//字号标签
			JLabel font_size;
			font_size = new JLabel();
			font_size.setText(UiText.FONT_SIZE_LABEL);
			//字号设置
			font_size_comboBox = new WiseCombobox();
			font_size_comboBox.setModel(new DefaultComboBoxModel(fontSize));
			font_size_comboBox.setEditable(true);

			//字形标签
			JLabel font_style;
			font_style = new JLabel();
			font_style.setText(UiText.FONT_STYLE_LABEL);
			//字形设置
			font_style_comboBox = new WiseCombobox();
			font_style_comboBox.setModel(new DefaultComboBoxModel(fontStyle));
//			font_style_comboBox.setEditable(true);
			
			//颜色标签
			/*JLabel font_color;
			font_color = new JLabel();
			font_color.setText(UiText.FONT_COLOR_LABEL);*/
			
			//颜色设置
			/*try {
				color_comboBox = new ColorComboBox();
			} catch (IncompatibleLookAndFeelException e) {
				e.printStackTrace();
			}*/
					
			//字号监听器
			RibbonUIManager.getInstance().bind(Font.SIZE_ACTION, ActionCommandType.DYNAMIC_ACTION, font_size_comboBox);
			//字体监听器
			RibbonUIManager.getInstance().bind(Font.FAMILY_ACTION, ActionCommandType.DYNAMIC_ACTION, font_family_comboBox);
			//字形监听器
			RibbonUIManager.getInstance().bind(Font.STYLE_ACTION, ActionCommandType.DYNAMIC_ACTION, font_style_comboBox);
			//颜色监听器
			/*RibbonUIManager.getInstance().bind(Font.COLOR_ACTION, ActionCommandType.DYNAMIC_ACTION, color_comboBox);*/
			
			//第四版可配合对话框使用的面板
			final GroupLayout groupLayout = new GroupLayout(this);
			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(1, 1, 1)
								.addComponent(font_family, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
							.addComponent(font_family_comboBox, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE))//【修改】 by 李晓光 2009-1-6 【90->210】
						.addGap(5, 5, 20)//20->5
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(font_size)
							.addComponent(font_size_comboBox, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))//【修改】 by 李晓光 2009-1-6 【80->75】
						.addGap(5, 5, 20)//20->5
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(font_style)
							.addComponent(font_style_comboBox, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))//【修改】 by 李晓光 2009-1-6 【62->75】
						.addGap(5, 5, 20)//20->5
						/*.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(color_comboBox, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
							.addComponent(font_color))*/
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(font_family, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
							.addComponent(font_size, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
							.addComponent(font_style, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
							/*.addComponent(font_color)*/)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(font_family_comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)//19
								.addComponent(font_size_comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(font_style_comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							/*.addComponent(color_comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)*/)//31
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			setLayout(groupLayout);
		}
	}
	
	//文字效果面板
	private class FontEffect extends JPanel{
		
		/**
		 * Create the panel
		 */
		public FontEffect() {
			super();
			
			//下划线
			JCheckBox underline_checkBox;
			//创建下划线checkbox并且添加动作
			underline_checkBox = new JCheckBox();
			underline_checkBox.setText(UiText.FONT_UNDERLINE);

			//删除线
			JCheckBox lineThroughCheckBox;
			lineThroughCheckBox = new JCheckBox();
			lineThroughCheckBox.setText(UiText.FONT_LINETHROUGH);

			//上划线
			JCheckBox checkBox_2;
			checkBox_2 = new JCheckBox();
			checkBox_2.setText(UiText.FONT_OVERLINE);
			
			//以这三个为一组添加到一个监听器中，以方便互相设置
			RibbonUIManager.getInstance().bind(Font.UNDERLINE_ACTION, ActionCommandType.DYNAMIC_ACTION, underline_checkBox);
			RibbonUIManager.getInstance().bind(Font.OVERLINE_ACTION, ActionCommandType.DYNAMIC_ACTION, checkBox_2);
			RibbonUIManager.getInstance().bind(Font.LINE_THROUGH_ACTION, ActionCommandType.DYNAMIC_ACTION, lineThroughCheckBox);
			
			
			final GroupLayout groupLayout = new GroupLayout(this);
			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(1, 1, 1)
						.addComponent(underline_checkBox)
						.addGap(5, 5, 5)
						.addComponent(checkBox_2)
						.addGap(14, 14, 14)
						.addComponent(lineThroughCheckBox)
						.addContainerGap(10, Short.MAX_VALUE))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(1, 1, 1)
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(underline_checkBox)
							.addComponent(checkBox_2)
							.addComponent(lineThroughCheckBox))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			setLayout(groupLayout);
			//
		}
	}
}

