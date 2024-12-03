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

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.AbstractAction;
import javax.swing.JComponent;

import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.ClipboardSupport;
import com.wisii.wisedoc.view.action.BaseAction;
import com.wisii.wisedoc.view.ui.manager.AbstractUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionID;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Defalult;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Font;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.svg.transcoded.edit_clear;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 
 * Ribbon界面的剪贴板面板
 * 
 * @author	闫舒寰
 * @version 1.0 2008/11/13
 */
public class ClipBand
{

	public JRibbonBand getBand()
	{

		// 图标是用Flamigo SVG transcoder转化成Java类，然后直接new 就可以了，这个应该是最小的时候的图标
		final JRibbonBand clipboardBand = new JRibbonBand(
				RibbonUIText.CLIP_BAND, MediaResource
						.getResizableIcon("09379.ico"));

		// 粘帖按钮
		final JCommandButton pasteButton = new JCommandButton(
				RibbonUIText.PASTE_BUTTON, MediaResource
						.getResizableIcon("Paste.gif")/*MediaResource.getResizableIcon("Paste.gif")*/);
		// 复制按钮的弹出式下拉菜单
		pasteButton.setPopupCallback(new PopupPanelCallback()
		{
			public JPopupPanel getPopupPanel(final JCommandButton arg0)
			{
				return new PasteList();
			}
		});
		// 粘帖按钮的动作
		RibbonUIManager.getInstance().bind(Defalult.PASTE_ACTION, pasteButton);

		// 设置按钮的种类和显示状态
		pasteButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
		pasteButton.setDisplayState(CommandButtonDisplayState.BIG);

		// 鼠标浮动显示解释说明
		final RichTooltip pbTooltip = new RichTooltip();
		pbTooltip.setTitle(RibbonUIText.PASTE_BUTTON_TITLE);
		pbTooltip.addDescriptionSection(RibbonUIText.PASTE_BUTTON_DESCRIPTION);
		pasteButton.setActionRichTooltip(pbTooltip);

		// 这个是在地方紧张的时候，显示的优先级
		clipboardBand.addCommandButton(pasteButton, RibbonElementPriority.TOP);

		// cut按钮
		final JCommandButton cutButton = new JCommandButton(
				RibbonUIText.CUT_BUTTON, MediaResource
						.getResizableIcon("Cut.gif"));
		RibbonUIManager.getInstance().bind(ActionFactory.Defalult.CUT_ACTION,
				cutButton);
		// 设置按钮的种类和显示状态
		cutButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
		cutButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		// 鼠标浮动显示解释说明
		final RichTooltip cutTooltip = new RichTooltip();
		cutTooltip.setTitle(RibbonUIText.CUT_BUTTON_TITLE);
		cutTooltip.addDescriptionSection(RibbonUIText.CUT_BUTTON_DESCRIPTION);
		cutButton.setActionRichTooltip(cutTooltip);

		clipboardBand.addCommandButton(cutButton, RibbonElementPriority.MEDIUM);
		// cut按钮结束，它的优先级为低

		// copy按钮
		final JCommandButton copyButton = new JCommandButton(
				RibbonUIText.COPY_BUTTON, MediaResource
						.getResizableIcon("Copy.gif"));
		RibbonUIManager.getInstance().bind(ActionFactory.Defalult.COPY_ACTION,
				copyButton);

		// 设置按钮的种类和显示状态
		copyButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		copyButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
		// 鼠标浮动显示解释说明
		final RichTooltip copyTooltip = new RichTooltip();
		copyTooltip.setTitle(RibbonUIText.COPY_BUTTON_TITLE);
		copyTooltip.addDescriptionSection(RibbonUIText.COPY_BUTTON_DESCRIPTION);
		copyTooltip.setFooterImage(MediaResource.getImage("Help.gif"));
		copyTooltip.addFooterSection(RibbonUIText.COPY_BUTTON_FOOTER);
		copyButton.setActionRichTooltip(copyTooltip);
		clipboardBand
				.addCommandButton(copyButton, RibbonElementPriority.MEDIUM);
		// copy按钮结束，它的优先级为低

		final JCommandButton cleanButton = new JCommandButton(
				RibbonUIText.CLEAN_BUTTON, new edit_clear());
		cleanButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
		cleanButton.setDisplayState(CommandButtonDisplayState.MEDIUM);
		// 鼠标浮动显示解释说明
		cleanButton.setActionRichTooltip(new RichTooltip(
				RibbonUIText.CLEAN_BUTTON_TITLE,
				RibbonUIText.CLEAN_BUTTON_DESCRIPTION));
		cleanButton.addActionListener(new Clean());
		clipboardBand.addCommandButton(cleanButton,
				RibbonElementPriority.MEDIUM);

		return clipboardBand;
	}

	// 弹出式下拉菜单项目
	private class PasteList extends JCommandPopupMenu
	{

		public PasteList()
		{
			final JCommandMenuButton copyAll = new JCommandMenuButton(
					RibbonUIText.PASTE_BUTTON, MediaResource
							.getResizableIcon("00223.ico"));
			// 鼠标浮动显示解释说明
			final RichTooltip copyAllTooltip = new RichTooltip();
			copyAllTooltip.setTitle(RibbonUIText.PASTE_BUTTON_TITLE);
			copyAllTooltip
					.addDescriptionSection(RibbonUIText.PASTE_BUTTON_DESCRIPTION);
			copyAll.setActionRichTooltip(copyAllTooltip);
			RibbonUIManager.getInstance().bind(
					ActionFactory.Defalult.PASTE_ACTION, copyAll);
			this.addMenuButton(copyAll);
			
			final JCommandMenuButton copyWithoutBindnode = new JCommandMenuButton(
					RibbonUIText.PASTE_WITHOUT_BINDNODE_BUTTON, MediaResource
							.getResizableIcon("00223.ico"));
			// 鼠标浮动显示解释说明
			final RichTooltip copyWithoutBindnodeTooltip = new RichTooltip();
			copyWithoutBindnodeTooltip.setTitle(RibbonUIText.PASTE_WITHOUT_BINDNODE_BUTTON_TITLE);
			copyWithoutBindnodeTooltip
					.addDescriptionSection(RibbonUIText.PASTE_WITHOUT_BINDNODE_BUTTON_DESCRIPTION);
			copyWithoutBindnode.setActionRichTooltip(copyWithoutBindnodeTooltip);
			RibbonUIManager.getInstance().bind(
					ActionFactory.Defalult.PASTE_WITHOUTBINDNODE_ACTION, copyWithoutBindnode);
			this.addMenuButton(copyWithoutBindnode);
			
			final JCommandMenuButton copyText = new JCommandMenuButton(
					RibbonUIText.PASTE_TEXT_BUTTON, MediaResource
							.getResizableIcon("00219.ico"));
			RibbonUIManager.getInstance().bind(
					ActionFactory.Defalult.PASTE_TEXT_ACTION, copyText);
			// 鼠标浮动显示解释说明
			final RichTooltip copyTextTooltip = new RichTooltip();
			copyTextTooltip.setTitle(RibbonUIText.PASTE_TEXT_BUTTON_TITLE);
			copyTextTooltip
					.addDescriptionSection(RibbonUIText.PASTE_TEXT_BUTTON_DESCRIPTION);
			copyText.setActionRichTooltip(copyTextTooltip);
			this.addMenuButton(copyText);
		}
	}

	private class Clean extends AbstractAction
	{
		@Override
		public void actionPerformed(final ActionEvent e)
		{
			List coms = RibbonUIManager.getUIComponents().get(
					Defalult.PASTE_ACTION).get(ActionCommandType.RIBBON_ACTION)
					.iterator().next();
			if (coms != null)
			{
				for (Object com : coms)
				{
					if (com instanceof JComponent)
					{
						((JComponent) com).setEnabled(false);
					}
				}
			}
			ClipboardSupport.writeToClipboard(null);
			System.gc();
		}
	}

	// 调试用
	private class Debug extends AbstractAction
	{
		@Override
		public void actionPerformed(final ActionEvent e)
		{
			final Map<Enum<? extends ActionID>, BaseAction> temp = ActionFactory
					.getActiveActions();
			// Enum<? extends ActionID>[] a = temp.keySet().toArray(new Enum<?
			// extends ActionID>[temp.size()]);
			final Set<Enum<? extends ActionID>> keys = temp.keySet();
			/*for (int i = 0; i < a.length; i++) {
				System.out.println(a[i] +  ": " + temp.get(a[i]));
			}*/

			/*if (temp.containsKey(Font.SIZE_ACTION)) {
				System.out.println(RibbonUIModel.RIBBON_MODEL.currentProperties.get(Constants.PR_FONT_SIZE));
			}*/

			System.out.println("active actions: " + keys.size());

			/*Object o = temp.get(Font.COLOR_LAYER_ACTION);
			
			System.out.println(o);*/

			// Actions action =(Actions)
			// ActionFactory.getActiveActions().get(Page.COLUMN_COUNT_ACTION);

			// final List<Object> list = RibbonUIModel.getElementList();

			final Map<ActionType, Map<Integer, Object>> currentProperties = RibbonUIModel
					.getCurrentPropertiesByType();
			for (final Entry<ActionType, Map<Integer, Object>> entry1 : currentProperties
					.entrySet())
			{
				final ActionType actionType = entry1.getKey();
				final Map<Integer, Object> readProperties = entry1.getValue();
				System.out.println(actionType + ": " + readProperties);
			}

			System.out.println();

			System.out.println(RibbonUIModel.getReadCompletePropertiesByType()
					.get(ActionType.PAGE_SEQUENCE));
			System.out.println(RibbonUIModel.getCurrentPropertiesByType().get(
					ActionType.LAYOUT));

			System.out.println("============================================");

			System.out.println("actions: "
					+ ActionFactory.getActionMap().size());

			System.out.println("active actions: "
					+ ActionFactory.getActiveActions().size());

			System.out.println("components: "
					+ AbstractUIManager.getUIComponents().size());

			final Set<Enum<? extends ActionID>> aid = ActionFactory
					.getActiveActions().keySet();

			final Set<Enum<? extends ActionID>> acd = AbstractUIManager
					.getUIComponents().keySet();

			for (final Enum<? extends ActionID> enu : aid)
			{
				if (!acd.contains(enu))
				{
					System.err.println(enu);
				}
			}

			System.out
					.println(ActionFactory.getActiveActions().get(
							Font.BOLD_ACTION)
							+ " : "
							+ AbstractUIManager.getUIComponents().get(
									Font.BOLD_ACTION));

			// 测试动态属性
			// Map<Integer, Object> proMap = new HashMap<Integer, Object>();
			// proMap.putAll(currentProperties.get(Font.getType()));
			//
			// proMap.put(Constants.PR_TOP, new FixedLength(300));
			// proMap.put(Constants.PR_DISPLAY_ALIGN, new
			// EnumProperty(Constants.EN_CENTER, ""));
			// proMap.put(Constants.PR_BARCODE_ADDCHECKSUM, true);
			// proMap.put(Constants.PR_FONT_FAMILY, "哈哈");
			// proMap.put(Constants.PR_TEXT_DECORATION, 3);
			// proMap.put(Constants.PR_FONT_SIZE, new FixedLength(20.0, "pt"));
			// proMap.put(Constants.PR_COLOR, new WiseDocColor(Color.red));

			// BarcodePropertyPanel pp = new BarcodePropertyPanel();
			// BlockContainerPropertyPanel pp = new
			// BlockContainerPropertyPanel();
			// FontPropertyPanel fpp = new FontPropertyPanel();
			// PicturePropertyPanel pp = new PicturePropertyPanel();
			// TableCellPropertyPanel pp = new TableCellPropertyPanel();
			// TablePropertyPanel pp = new TablePropertyPanel();
			// TableRowPropertyPanel pp = new TableRowPropertyPanel();
			// ParagraphStylesPanel pp = new ParagraphStylesPanel();
			// ParagraphPanel pp = new ParagraphPanel();
			//
			// pp.install(proMap);
			// fpp.install(proMap, PropertyPanelType.NO_BORDER_AND_BACKGROUND);

			// JPanel jp = new JPanel(new BorderLayout());
			// jp.add(fpp, BorderLayout.WEST);
			// jp.add(pp, BorderLayout.EAST);

			// new OpenDialogTest(pp, fpp);
			// 动态属性测试完毕

			// new OpenDialogTest(new ParagraphStylesPanel());

			// new CustomLayoutDialog();

			/*Map<Enum<? extends ActionID>, Map<ActionCommandType, Set<List<Object>>>> uiCom = RibbonUIManager.getUIComponents();
			
			Set<Enum<? extends ActionID>> key = uiCom.keySet();
			int count = 0;
			for (Enum<? extends ActionID> eKey : key) {
			//				System.out.println("key: " + eKey + " c size: " + uiCom.get(eKey).values().size());
				Collection<Set<List<Object>>> c = uiCom.get(eKey).values();
				for (Set<List<Object>> set : c) {
					if (set.size() > 1) {
						System.err.print(set.size());
						for (List<Object> list2 : set) {
							System.out.println(" list " + list2.size());
						}
					}
				}
				++count;
			}
			System.err.println(count);*/

			// System.out.println(((Actions)ActionFactory.getActiveActions().get(Font.SIZE_ACTION)));

			// SimplePageMaster o =
			// (SimplePageMaster)RibbonUIModel.getCurrentPropertiesByType().get(Page.getType()).get(Constants.PR_SIMPLE_PAGE_MASTER);

			// System.out.println(o.getCommonMarginBlock().marginTop);

			// RepaintManager.setCurrentManager(new TracingRepaintManager());

			// RepaintManager.currentManager(RibbonPanel.getRibbon());

			// System.out.println(RibbonUIModel.getCurrentPropertiesByType().get(Page.getType()).get(Constants.PR_SIMPLE_PAGE_MASTER));
			// System.out.println(RibbonUIModel.getCurrentPropertiesByType().get(ActionType.NUMBERTEXT).get(Constants.PR_NUMBERTEXT_TYPE));
			/*for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				if (object instanceof BarCode) {
					BarCode value = (BarCode) object;
					
				}
			}*/

			// System.out.println(RibbonUIManager.getUIComponents().get(Page.COLUMN_COUNT_ACTION).size());

		}
	}

}
