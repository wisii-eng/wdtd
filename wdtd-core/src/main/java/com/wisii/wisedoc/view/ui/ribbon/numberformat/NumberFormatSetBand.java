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
package com.wisii.wisedoc.view.ui.ribbon.numberformat;

import static com.wisii.wisedoc.view.ui.text.RibbonUIText.NUMBER_FORMAT_CH_TITLE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.NUMBER_FORMAT_DECIMAL;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.NUMBER_FORMAT_BAIFENSHU_LABEL;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.NUMBER_FORMAT_THOUSAND_LABEL;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.NUMBER_FORMAT_TITLE;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.swing.ui.WiseSpinner;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.NumberText;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 格式化数字设置面板
 * @author 张强
 * @version 1.0 2009/03/23
 */
public class NumberFormatSetBand implements WiseBand
{

	public JRibbonBand getBand()
	{

		return new NFSetBand();
	}

	public static class NFSetBand extends JRibbonBand
	{
		private CardLayout cardlayout;
		private JPanel cardpanel;
		private JCheckBox ischesecheckbox;

		public NFSetBand()
		{
			// paste.
			super(NUMBER_FORMAT_TITLE, MediaResource.getResizableIcon("09379.ico"));
			JPanel formatPanel = new JPanel(new GridLayout(2, 1, 0, 0));
			ischesecheckbox = new JCheckBox(NUMBER_FORMAT_CH_TITLE);
			FlowLayout flowlayout = new FlowLayout();
			flowlayout.setAlignment(FlowLayout.LEFT);
			JPanel numberset = new JPanel(flowlayout);
			JLabel labelcount = new JLabel(NUMBER_FORMAT_DECIMAL);
			WiseSpinner textcount = new WiseSpinner(new SpinnerNumberModel(2,0,100,1));
			JLabel labelthousands = new JLabel(NUMBER_FORMAT_THOUSAND_LABEL);
			JCheckBox textthousands = new JCheckBox();
			textthousands.setSelected(true);
			JLabel labeldots = new JLabel(NUMBER_FORMAT_BAIFENSHU_LABEL);
			JCheckBox textdots = new JCheckBox();
			textdots.setSelected(false);
			Dimension dimension = new Dimension(80, 22);
			textcount.setPreferredSize(dimension);
			textthousands.setPreferredSize(dimension);
			textdots.setPreferredSize(dimension);
			numberset.add(labelcount);
			numberset.add(textcount);
			numberset.add(labelthousands);
			numberset.add(textthousands);
			numberset.add(labeldots);
			numberset.add(textdots);
			RibbonUIManager.getInstance().bind(NumberText.THOUSAND_SET_ACTION,
					textcount);
			RibbonUIManager.getInstance().bind(
					NumberText.THOUSAND_SEPARATOR_SET_ACTION, textthousands);
			RibbonUIManager.getInstance().bind(
					NumberText.BAIFENBI_SET_ACTION, textdots);
			JPanel chinesenumberset = new JPanel(flowlayout);
			final WiseCombobox chinesestyle = new WiseCombobox(new DefaultComboBoxModel(
					UiText.CHINESE_NUMBER_SET));
			RibbonUIManager.getInstance().bind(
					NumberText.CHINESE_NUMBER_SET_ACTION, chinesestyle);
			RibbonUIManager.getInstance().bind(
					NumberText.SET_ISCHINESE_ACTION, ischesecheckbox);
			chinesestyle.setLocation(2, 2);
			chinesenumberset.add(chinesestyle);
			cardlayout = new CardLayout();
			cardpanel = new JPanel(cardlayout);
			cardpanel.add(chinesenumberset, "chinesnumberset");
			cardpanel.add(numberset, "numberset");
			cardlayout.show(cardpanel, "numberset");
			formatPanel.add(ischesecheckbox);
			formatPanel.add(cardpanel);
//			addPanel(formatPanel);
			//以Ribbon组件对象包装当前组件，然后以流式排版排列
			JRibbonComponent formatPanelWrapper = new JRibbonComponent(formatPanel);
			addRibbonComponent(formatPanelWrapper);
			ischesecheckbox.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					boolean iscn = ischesecheckbox.isSelected();
					changeCardPanel(iscn);
				}

			});
		}

		public void changeCardPanel(boolean ischinese)
		{
			if (ischinese)
			{
//				cardlayout.show(cardpanel, "chinesnumberset");
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						cardlayout.show(cardpanel, "chinesnumberset");
					}
				});
			} else
			{
//				cardlayout.show(cardpanel, "numberset");
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						cardlayout.show(cardpanel, "numberset");
					}
				});
			}
			if (ischesecheckbox.isSelected() != ischinese)
			{
				ischesecheckbox.setSelected(ischinese);
			}
		}
	}

}
