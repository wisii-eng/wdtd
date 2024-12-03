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

package com.wisii.wisedoc.view.ui.ribbon;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JSeparator;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.JCommandToggleButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.common.icon.EmptyResizableIcon;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.ribbon.JRibbon;
import org.jvnet.flamingo.ribbon.RibbonContextualTaskGroup;
import com.wisii.security.Function;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Defalult;
import com.wisii.wisedoc.view.ui.ribbon.barcode.BarcodeStyleTask;
import com.wisii.wisedoc.view.ui.ribbon.barcode.BarcodeTextTask;
import com.wisii.wisedoc.view.ui.ribbon.blockcontainer.BlockContainerTask;
import com.wisii.wisedoc.view.ui.ribbon.chart.ChartBasicTask;
import com.wisii.wisedoc.view.ui.ribbon.chart.ChartTypeTask;
import com.wisii.wisedoc.view.ui.ribbon.chart.ChartXYAxisTask;
import com.wisii.wisedoc.view.ui.ribbon.checkbox.CheckBoxTask;
import com.wisii.wisedoc.view.ui.ribbon.dateformat.DateFormatTask;
import com.wisii.wisedoc.view.ui.ribbon.edit.EditTask;
import com.wisii.wisedoc.view.ui.ribbon.encrypttext.EncryptTextTask;
import com.wisii.wisedoc.view.ui.ribbon.externalgraphic.ExternalGraphicTask;
import com.wisii.wisedoc.view.ui.ribbon.graph.GraphStyleTask;
import com.wisii.wisedoc.view.ui.ribbon.numberformat.NumberFormatTask;
import com.wisii.wisedoc.view.ui.ribbon.regions.RegionAfterTask;
import com.wisii.wisedoc.view.ui.ribbon.regions.RegionBeforeTask;
import com.wisii.wisedoc.view.ui.ribbon.regions.RegionEndTask;
import com.wisii.wisedoc.view.ui.ribbon.regions.RegionStartTask;
import com.wisii.wisedoc.view.ui.ribbon.table.TableOperationTask;
import com.wisii.wisedoc.view.ui.ribbon.table.TablePropertyTask;
import com.wisii.wisedoc.view.ui.ribbon.wordarttext.WordArtTextTask;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 用于初始化和获得Ribbon面板
 * 
 * @author 闫舒寰
 * @version 1.0 2008/10/24
 */
public class RibbonPanel
{
	// ribbon 控制栏自身
	private static JRibbon ribbon;

	private static RibbonContextualTaskGroup barcodeTaskGroup;

	// 图片设置面板
	private static RibbonContextualTaskGroup imageTaskGroup;

	private static RibbonContextualTaskGroup tableTaskGroup;

	private static RibbonContextualTaskGroup blockContainer;

	private static RibbonContextualTaskGroup dateFormat;

	private static RibbonContextualTaskGroup numberFormat;
	
	private static RibbonContextualTaskGroup checkbox;
	
	private static RibbonContextualTaskGroup regionBefore;

	private static RibbonContextualTaskGroup regionAfter;

	private static RibbonContextualTaskGroup regionStart;

	private static RibbonContextualTaskGroup regionEnd;

	private static RibbonContextualTaskGroup svgGraph;
	private static RibbonContextualTaskGroup wordarttext;
	
	private static RibbonContextualTaskGroup encrypttext;


	/* 【添加：START】 by 李晓光 2009-5-18 */
	private static RibbonContextualTaskGroup chartGroup = null;
	/* 【添加：END】 by 李晓光 2009-5-18 */
	/* 【添加：START】 by zhangqiang 2009-07-09 */
	// 编辑设置面板
	private static RibbonContextualTaskGroup editsetGroup = null;
	/* 【添加：END】 by zhangqiang 2009-07-09 */
	private static boolean isEditEnable = false;

	public RibbonPanel(List<Function> funclist)
	{
		setRibbon(new JRibbon());

		RibbonPanelManager.Instance.initialTask(funclist);

		// 根据用户所选择的对象出现的菜单
		tableTaskGroup = new RibbonContextualTaskGroup(
				RibbonUIText.TABLE_TASK_GROUP, Color.green,
				new TableOperationTask().getTask(), new TablePropertyTask()
						.getTask());

		barcodeTaskGroup = new RibbonContextualTaskGroup(
				RibbonUIText.BARCODE_TASK_GROUP, Color.red,
				new BarcodeStyleTask().getTask(), new BarcodeTextTask()
						.getTask());

		imageTaskGroup = new RibbonContextualTaskGroup(
				RibbonUIText.PICTURE_TASK_TITLE, Color.red,
				new ExternalGraphicTask().getTask());

		blockContainer = new RibbonContextualTaskGroup(
				RibbonUIText.BLOCK_CONTAINER_TASK_GROUP, Color.blue,
				new BlockContainerTask().getTask());

		regionBefore = new RibbonContextualTaskGroup(UiText.PAGE_HEADER_TITLE,
				Color.cyan, new RegionBeforeTask().getTask());
		regionAfter = new RibbonContextualTaskGroup(UiText.PAGE_FOOTER_TITLE,
				Color.cyan, new RegionAfterTask().getTask());
		regionStart = new RibbonContextualTaskGroup(UiText.PAGE_START_TITLE,
				Color.cyan, new RegionStartTask().getTask());
		regionEnd = new RibbonContextualTaskGroup(UiText.PAGE_END_TITLE,
				Color.cyan, new RegionEndTask().getTask());

		svgGraph = new RibbonContextualTaskGroup(RibbonUIText.SVG_GRAPH_TASK,
				Color.yellow, new GraphStyleTask().getTask()
		/* new GraphSizeTask().getTask() */);// 目前暂不支持图形，svg绘制方面有问题
		wordarttext = new RibbonContextualTaskGroup(RibbonUIText.RIBBON_WORDARTTEXT,
				Color.GRAY, new WordArtTextTask().getTask());
		
		encrypttext = new RibbonContextualTaskGroup("加密文本",
				Color.GRAY, new EncryptTextTask().getTask());

		/* 【添加：START】 by 李晓光 2009-5-18 */
		chartGroup = new RibbonContextualTaskGroup(RibbonUIText.Chart_TASK,
				Color.RED, new ChartBasicTask().getTask(),
				new ChartXYAxisTask().getTask(), new ChartTypeTask().getTask());

		/* 【添加：END】 by 李晓光 2009-5-18 */
		/* 【添加：START】 by zhangqiang 2009-07-09 */
		editsetGroup = new RibbonContextualTaskGroup(RibbonUIText.Edit_TASK,
				Color.green, new EditTask().getTask());
		/* 【添加：END】 by zhangqiang 2009-07-09 */

		RibbonPanel.getRibbon().addContextualTaskGroup(tableTaskGroup);
		RibbonPanel.getRibbon().addContextualTaskGroup(barcodeTaskGroup);
		RibbonPanel.getRibbon().addContextualTaskGroup(imageTaskGroup);
		RibbonPanel.getRibbon().addContextualTaskGroup(blockContainer);
		RibbonPanel.getRibbon().addContextualTaskGroup(regionBefore);
		RibbonPanel.getRibbon().addContextualTaskGroup(regionAfter);
		RibbonPanel.getRibbon().addContextualTaskGroup(regionStart);
		RibbonPanel.getRibbon().addContextualTaskGroup(regionEnd);

		RibbonPanel.getRibbon().addContextualTaskGroup(svgGraph);
		RibbonPanel.getRibbon().addContextualTaskGroup(wordarttext);
		RibbonPanel.getRibbon().addContextualTaskGroup(encrypttext);
		/* 【添加：START】 by 李晓光 2009-5-18 */
		RibbonPanel.getRibbon().addContextualTaskGroup(chartGroup);
		/* 【添加：END】 by 李晓光 2009-5-18 */
		/* 【添加：START】 by zhangqiang 2009-07-09 */
		RibbonPanel.getRibbon().addContextualTaskGroup(editsetGroup);
		/* 【添加：END】 by zhangqiang 2009-07-09 */
		dateFormat = new RibbonContextualTaskGroup(
				RibbonUIText.DATE_FORMAT_TASK, Color.yellow,
				new DateFormatTask().getTask());

		numberFormat = new RibbonContextualTaskGroup(
				RibbonUIText.NUMBER_FORMAT_TASK, Color.yellow,
				new NumberFormatTask().getTask());
		checkbox = new RibbonContextualTaskGroup(
				RibbonUIText.CHECKBOX_TASKGROUP, Color.yellow,
				new CheckBoxTask().getTask());
		RibbonPanel.getRibbon().addContextualTaskGroup(dateFormat);
		RibbonPanel.getRibbon().addContextualTaskGroup(numberFormat);
		RibbonPanel.getRibbon().addContextualTaskGroup(checkbox);

		// 初始化Ribbon那个圆形按钮旁边的小按钮
		initialTaskbarButtons(funclist);
		if (funclist != null && !funclist.isEmpty())
		{
			for (Function fun : funclist)
			{
				if (fun.getId().equals("lojnb"))
				{
					isEditEnable = true;
				}
			}
		}

		// ShortCutKeyBind.initShortCutKey();

		// ram = new ApplicationMenu().getApplicationMenu();
		//
		// RibbonPanel.getRibbon().setApplicationMenu(ram);
	}

	/*
	 * public void update(){ ram. }
	 */

	/**
	 * 初始化Ribbon那个圆形按钮旁边的小按钮
	 */
	private void initialTaskbarButtons(List<Function> funclist)
	{
		// taskbar components tab旁边添加的按钮
		// 保存按钮
		final JCommandButton saveButton = new JCommandButton("", MediaResource
				.getResizableIcon("Save.gif"));
		saveButton.setActionRichTooltip(new RichTooltip(
				RibbonUIText.SAVE_BUTTON_TITLE,
				RibbonUIText.SAVE_BUTTON_DESCRIPTION));
		saveButton.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(Defalult.SAVE_FILE_ACTION,
				saveButton);
		ribbon.addTaskbarComponent(saveButton);

		// 撤销按钮
		final JCommandButton undoButton = new JCommandButton("", MediaResource
				.getResizableIcon("Undo.gif"));
		undoButton.setActionRichTooltip(new RichTooltip(
				RibbonUIText.UNDO_BUTTON_TITLE,
				RibbonUIText.UNDO_BUTTON_DESCRIPTION));
		undoButton
				.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
		RibbonUIManager.getInstance().bind(Defalult.UNDO_ACTION, undoButton);
		undoButton.setPopupCallback(new PopupPanelCallback()
		{

			public JPopupPanel getPopupPanel(final JCommandButton arg0)
			{
				return new undoMenu();
			}
		});
		ribbon.addTaskbarComponent(undoButton);

		// 重做按钮
		final JCommandButton redoButton = new JCommandButton("", MediaResource
				.getResizableIcon("Redo.gif"));
		redoButton.setActionRichTooltip(new RichTooltip(
				RibbonUIText.REDO_BUTTON_TITLE,
				RibbonUIText.REDO_BUTTON_DESCRIPTION));
		RibbonUIManager.getInstance().bind(Defalult.REDO_ACTION, redoButton);
		ribbon.addTaskbarComponent(redoButton);

		ribbon.addTaskbarComponent(new JSeparator(JSeparator.VERTICAL));

		// 最小化按钮
		final JCommandToggleButton minButton = new JCommandToggleButton("",
				MediaResource.getResizableIcon("03274.ico"));
		minButton.setActionRichTooltip(new RichTooltip(
				RibbonUIText.MIN_BUTTON_TITLE,
				RibbonUIText.MIN_BUTTON_DESCRIPTION));
		minButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(final ActionEvent e)
			{
				// ribbon.setMinimized(!ribbon.isMinimized());
				ribbon.setMinimized(minButton.getActionModel().isSelected());
			}

		});
		ribbon.addTaskbarComponent(minButton);
	}

	/**
	 * 用于显示撤销的下拉菜单，暂时没有完成
	 */
	public static class undoMenu extends JCommandPopupMenu
	{

		public undoMenu()
		{
			this.addMenuButton(new JCommandMenuButton("undo1",
					new EmptyResizableIcon(16)));
			this.addMenuButton(new JCommandMenuButton("undo2",
					new EmptyResizableIcon(16)));
			this.addMenuButton(new JCommandMenuButton("undo3",
					new EmptyResizableIcon(16)));
			this.addMenuSeparator();
			this.addMenuButton(new JCommandMenuButton("undo4",
					new EmptyResizableIcon(16)));
			this.addMenuButton(new JCommandMenuButton("undo5",
					new EmptyResizableIcon(16)));
		}
	}

	public static JRibbon getRibbon()
	{
		return ribbon;
	}

	private void setRibbon(final JRibbon ribbon)
	{
		RibbonPanel.ribbon = ribbon;
	}

	public static RibbonContextualTaskGroup getBarcodeTaskGroup()
	{
		return barcodeTaskGroup;
	}

	public static RibbonContextualTaskGroup getImageTaskGroup()
	{
		return imageTaskGroup;
	}

	public static RibbonContextualTaskGroup getTableTaskGroup()
	{
		return tableTaskGroup;
	}

	public static RibbonContextualTaskGroup getBlockContainer()
	{
		return blockContainer;
	}

	/* 【添加：START】 by 李晓光 2009-5-18 */
	public static RibbonContextualTaskGroup getChartGroup()
	{
		return chartGroup;
	}

	/* 【添加：END】 by 李晓光 2009-5-18 */
	/* 【添加：START】 by zhangqiang 2009-07-09 */
	public static RibbonContextualTaskGroup getEditGroup()
	{
		return editsetGroup;
	}

	/* 【添加：END】 by zhangqiang 2009-07-09 */
	public static RibbonContextualTaskGroup getDateFormat()
	{
		return dateFormat;
	}

	public static RibbonContextualTaskGroup getNumberFormat()
	{
		return numberFormat;
	}
	public static RibbonContextualTaskGroup getCheckBox()
	{
		return checkbox;
	}
	public static RibbonContextualTaskGroup getRegionBefore()
	{
		return regionBefore;
	}

	public static void setRegionBefore(
			final RibbonContextualTaskGroup regionBefore)
	{
		RibbonPanel.regionBefore = regionBefore;
	}

	public static RibbonContextualTaskGroup getRegionAfter()
	{
		return regionAfter;
	}

	public static void setRegionAfter(
			final RibbonContextualTaskGroup regionAfter)
	{
		RibbonPanel.regionAfter = regionAfter;
	}

	public static RibbonContextualTaskGroup getRegionStart()
	{
		return regionStart;
	}

	public static void setRegionStart(
			final RibbonContextualTaskGroup regionStart)
	{
		RibbonPanel.regionStart = regionStart;
	}

	public static RibbonContextualTaskGroup getRegionEnd()
	{
		return regionEnd;
	}

	public static void setRegionEnd(final RibbonContextualTaskGroup regionEnd)
	{
		RibbonPanel.regionEnd = regionEnd;
	}

	public static RibbonContextualTaskGroup getSvgGraph()
	{
		return svgGraph;
	}
	public static RibbonContextualTaskGroup getWordArtText()
	{
		return wordarttext;
	}
	
	public static RibbonContextualTaskGroup getEncryptText()
	{
		return encrypttext;
	}
	public static void setSvgGraph(final RibbonContextualTaskGroup svgGraph)
	{
		RibbonPanel.svgGraph = svgGraph;
	}

	public static boolean isEditenable()
	{
		return isEditEnable;
	}
}
