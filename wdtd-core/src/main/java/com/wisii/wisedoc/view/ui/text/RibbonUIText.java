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

package com.wisii.wisedoc.view.ui.text;

/**
 * Ribbon UI上所有文字常量
 * 
 * @author 闫舒寰
 * @version 2009/01/13
 */
public interface RibbonUIText
{

	/*************** Ribbon公共文字**********开始 ******************/

	/**
	 * 边框下拉菜单中的文字
	 */
	String[] BORDERS =
	{
			Messages.getString("wsd.view.gui.ribbon.0"), Messages.getString("wsd.view.gui.ribbon.1"), Messages.getString("wsd.view.gui.ribbon.2"), Messages.getString("wsd.view.gui.ribbon.3"), Messages.getString("wsd.view.gui.ribbon.4"), Messages.getString("wsd.view.gui.ribbon.5"), }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$

	/**
	 * 边框和底纹
	 */
	String BORDER_SHADING = Messages.getString("wsd.view.gui.ribbon.6"); //$NON-NLS-1$

	/*************** Ribbon公共文字**********结束 ******************/

	/********** Ribbon上边动作**************开始 *****************/
	/**
	 * 保存按钮
	 */
	String SAVE_BUTTON_TITLE = Messages.getString("wsd.view.gui.ribbon.7"); //$NON-NLS-1$

	String SAVE_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.8"); //$NON-NLS-1$

	/**
	 * 撤销按钮
	 */
	String UNDO_BUTTON_TITLE = Messages.getString("wsd.view.gui.ribbon.9"); //$NON-NLS-1$

	String UNDO_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.10"); //$NON-NLS-1$

	/**
	 * 重做按钮
	 */
	String REDO_BUTTON_TITLE = Messages.getString("wsd.view.gui.ribbon.11"); //$NON-NLS-1$

	String REDO_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.12"); //$NON-NLS-1$

	/**
	 * 最小化Ribbon按钮
	 */
	String MIN_BUTTON_TITLE = Messages.getString("wsd.view.gui.ribbon.13"); //$NON-NLS-1$

	String MIN_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.14"); //$NON-NLS-1$

	/********** Ribbon上边动作**************结束 *****************/

	/********** 基本属性**************开始 ******************/
	/**
	 * 基本属性标签
	 */
	String BASIC_PROPERTY = Messages.getString("wsd.view.gui.ribbon.15"); //$NON-NLS-1$

	/********** 剪贴板**************开始 ******************/
	/**
	 * 剪贴板
	 */
	String CLIP_BAND = Messages.getString("wsd.view.gui.ribbon.16"); //$NON-NLS-1$

	/**
	 * 剪切按钮
	 */
	String CUT_BUTTON = Messages.getString("wsd.view.gui.ribbon.20"); //$NON-NLS-1$

	String CUT_BUTTON_TITLE = Messages.getString("wsd.view.gui.ribbon.21"); //$NON-NLS-1$

	String CUT_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.22"); //$NON-NLS-1$

	/**
	 * 拷贝按钮
	 */
	String COPY_BUTTON = Messages.getString("wsd.view.gui.ribbon.23"); //$NON-NLS-1$

	String COPY_BUTTON_TITLE = Messages.getString("wsd.view.gui.ribbon.24"); //$NON-NLS-1$

	String COPY_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.25"); //$NON-NLS-1$

	String COPY_BUTTON_FOOTER = Messages.getString("wsd.view.gui.ribbon.26"); //$NON-NLS-1$\
	/** 粘帖按钮系列文本 **/
	/**
	 * 粘帖按钮文本
	 */
	String PASTE_BUTTON = Messages.getString("wsd.view.gui.ribbon.17"); //$NON-NLS-1$
	/**
	 * 粘帖解释文本
	 */

	String PASTE_BUTTON_TITLE = Messages.getString("wsd.view.gui.ribbon.18"); //$NON-NLS-1$

	String PASTE_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.19"); //$NON-NLS-1$
	/**
	 * 粘帖按钮文本
	 */
	String PASTE_WITHOUT_BINDNODE_BUTTON = Messages.getString("wsd.view.gui.ribbon.27"); //$NON-NLS-1$

	/**
	 * 粘帖解释文本
	 */

	String PASTE_WITHOUT_BINDNODE_BUTTON_TITLE = Messages.getString("wsd.view.gui.ribbon.28"); //$NON-NLS-1$

	String PASTE_WITHOUT_BINDNODE_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.29"); //$NON-NLS-1$

	String PASTE_TEXT_BUTTON = Messages.getString("wsd.view.gui.ribbon.30"); //$NON-NLS-1$

	String PASTE_TEXT_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.31"); //$NON-NLS-1$

	String PASTE_TEXT_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.32"); //$NON-NLS-1$

	/**
	 * 格式刷按钮
	 */
	String FORMAT_BUTTON = Messages.getString("wsd.view.gui.ribbon.33"); //$NON-NLS-1$

	String FORMAT_BUTTON_TITLE = Messages.getString("wsd.view.gui.ribbon.34"); //$NON-NLS-1$

	String FORMAT_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.35"); //$NON-NLS-1$

	String CLEAN_BUTTON = Messages.getString("wsd.view.gui.ribbon.clean");

	String CLEAN_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.clean.title");

	String CLEAN_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.clean.description");

	/********** 剪贴板**************结束 ******************/

	/********** 字体板**************开始 ******************/
	/**
	 * 字体面板
	 */
	String FONT_BAND = Messages.getString("wsd.view.gui.ribbon.36"); //$NON-NLS-1$

	/**
	 * 字号变大解释
	 */
	String FONT_BIGGER_TITLE = Messages.getString("wsd.view.gui.ribbon.37"); //$NON-NLS-1$

	String FONT_BIGGER_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.38"); //$NON-NLS-1$

	/**
	 * 字号变小解释
	 */
	String FONT_SMALLER_TITLE = Messages.getString("wsd.view.gui.ribbon.39"); //$NON-NLS-1$

	String FONT_SMALLER_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.40"); //$NON-NLS-1$

	/**
	 * 清楚样式解释
	 */
	String FONT_CLEAN_TITLE = Messages.getString("wsd.view.gui.ribbon.41"); //$NON-NLS-1$

	String FONT_CLEAN_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.42"); //$NON-NLS-1$

	/**
	 * 文字边框解释
	 */
	String FONT_BORDER_TITLE = Messages.getString("wsd.view.gui.ribbon.43"); //$NON-NLS-1$

	String FONT_BORDER_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.44"); //$NON-NLS-1$

	/**
	 * 粗体按钮解释
	 */
	String FONT_BOLD_TITLE = Messages.getString("wsd.view.gui.ribbon.45"); //$NON-NLS-1$

	String FONT_BOLD_DESCRIPTION = Messages.getString("wsd.view.gui.ribbon.46"); //$NON-NLS-1$

	/**
	 * 斜体按钮解释
	 */
	String FONT_ITALIC_TITLE = Messages.getString("wsd.view.gui.ribbon.47"); //$NON-NLS-1$

	String FONT_ITALIC_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.48"); //$NON-NLS-1$

	/**
	 * 下划线按钮解释
	 */
	String FONT_UNDER_LINE_TITLE = Messages.getString("wsd.view.gui.ribbon.49"); //$NON-NLS-1$

	String FONT_UNDER_LINE_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.50"); //$NON-NLS-1$

	/**
	 * 删除线按钮解释
	 */
	String FONT_STRIKE_THROUGH_TITLE = Messages
			.getString("wsd.view.gui.ribbon.51"); //$NON-NLS-1$

	String FONT_STRIKE_THROUGH_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.52"); //$NON-NLS-1$

	/**
	 * 上标按钮解释
	 */
	String FONT_SUPER_SCRIPT_TITLE = Messages
			.getString("wsd.view.gui.ribbon.53"); //$NON-NLS-1$

	String FONT_SUPER_SCRIPT_DESCRIPTON = Messages
			.getString("wsd.view.gui.ribbon.54"); //$NON-NLS-1$

	/**
	 * 下标按钮解释
	 */
	String FONT_SUB_SCRIPT_TITLE = Messages.getString("wsd.view.gui.ribbon.55"); //$NON-NLS-1$

	String FONT_SUB_SCRIPT_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.56"); //$NON-NLS-1$

	/**
	 * 高亮菜单按钮解释
	 */
	String FONT_HEIGH_LIGHTER_TITLE = Messages
			.getString("wsd.view.gui.ribbon.57"); //$NON-NLS-1$

	String FONT_HEIGH_LIGHTER_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.58"); //$NON-NLS-1$

	/********** 字体板**************结束 ******************/

	/********** 段落板**************开始 ******************/
	/**
	 * 段落面板
	 */
	String PARAGRAPH_BAND = Messages.getString("wsd.view.gui.ribbon.59"); //$NON-NLS-1$

	// 缩进
	String PARAGRAPH_INDENT = Messages.getString("wsd.view.gui.ribbon.app.0"); //$NON-NLS-1$

	String PARAGRAPH_INDENT_START = Messages
			.getString("wsd.view.gui.ribbon.app.1"); //$NON-NLS-1$

	String PARAGRAPH_INDENT_END = Messages
			.getString("wsd.view.gui.ribbon.app.2"); //$NON-NLS-1$

	/**
	 * 段落左对齐按钮解释
	 */
	String PARAGRAPH_ALIGN_LEFT_TITLE = Messages
			.getString("wsd.view.gui.ribbon.60"); //$NON-NLS-1$

	String PARAGRAPH_ALIGN_LEFT_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.61"); //$NON-NLS-1$

	/**
	 * 段落居中按钮解释
	 */
	String PARAGRAPH_ALIGN_CENTER_TITLE = Messages
			.getString("wsd.view.gui.ribbon.62"); //$NON-NLS-1$

	String PARAGRAPH_ALIGN_CENTER_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.63"); //$NON-NLS-1$

	/**
	 * 段落右对齐按钮解释
	 */
	String PARAGRAPH_ALIGN_RIGHT_TITLE = Messages
			.getString("wsd.view.gui.ribbon.64"); //$NON-NLS-1$

	String PARAGRAPH_ALIGN_RIGHT_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.65"); //$NON-NLS-1$

	/**
	 * 段落分散对齐按钮解释
	 */
	String PARAGRAPH_ALIGN_JUSTIFY_TITLE = Messages
			.getString("wsd.view.gui.ribbon.66"); //$NON-NLS-1$

	String PARAGRAPH_ALIGN_JUSTIFY_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.67"); //$NON-NLS-1$

	/**
	 * 行间距按钮解释
	 */
	String PARAGRAPH_LINE_SPACE_TITLE = Messages
			.getString("wsd.view.gui.ribbon.68"); //$NON-NLS-1$

	String PARAGRAPH_LINE_SPACE_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.69"); //$NON-NLS-1$

	/**
	 * <p>
	 * 行距列表：
	 * </p>
	 * <p>
	 * 1倍行距、1.15倍行距、1.5倍行距、2倍行距、2.5倍行距、3倍行距
	 * </p>
	 */
	String[] PARAGRAPH_LINE_SPACE_LIST =
	{
			Messages.getString("wsd.view.gui.ribbon.70"), Messages.getString("wsd.view.gui.ribbon.71"), Messages.getString("wsd.view.gui.ribbon.72"), Messages.getString("wsd.view.gui.ribbon.73"), Messages.getString("wsd.view.gui.ribbon.74"), Messages.getString("wsd.view.gui.ribbon.75") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$

	/**
	 * 行距选项按钮
	 */
	String PARAGRAPH_LINE_SPACE_MENUE = Messages
			.getString("wsd.view.gui.ribbon.76"); //$NON-NLS-1$

	/**
	 * 段落背景颜色按钮解释
	 */
	String PARAGRAPH_BACKGROUND_COLOR_TITLE = Messages
			.getString("wsd.view.gui.ribbon.77"); //$NON-NLS-1$

	String PARAGRAPH_BACKGROUND_COLOR_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.78"); //$NON-NLS-1$

	/**
	 * 段落边框和底纹按钮解释
	 */
	String PARAGRAPH_BORDER_TITLE = Messages
			.getString("wsd.view.gui.ribbon.79"); //$NON-NLS-1$

	String PARAGRAPH_BORDER_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.80"); //$NON-NLS-1$

	/********** 段落板**************结束 ******************/

	/********** 查找板**************开始 ******************/
	/**
	 * 查找板
	 */
	String LAYER_BAND = Messages.getString("wsd.view.gui.ribbon.81"); //$NON-NLS-1$

	/**
	 * 查找按钮
	 */
	String DRAW_SETTING_BAND = Messages.getString("wsd.view.gui.ribbon.82"); //$NON-NLS-1$

	/**
	 * 显示框线
	 */
	String DRAW_SETTING_NULLBORDER_BUTTON = Messages.getString("wsd.view.gui.ribbon.83"); //$NON-NLS-1$
	/**
	 * 显示框线
	 */
	String DRAW_SETTING_NULLBORDER_TITLE = Messages.getString("wsd.view.gui.ribbon.nullborder.title");
	/**
	 * 显示框线
	 */
	String DRAW_SETTING_NULLBORDER_DESCRIPTION = Messages.getString("wsd.view.gui.ribbon.nullborder.description");
	
	/**
	 * 显示空行标记
	 */
	String DRAW_SETTING_LINEBREAK_BUTTON = Messages.getString("wsd.view.gui.ribbon.linebreak.button"); //$NON-NLS-1$
	/**
	 * 显示空行标记
	 */
	String DRAW_SETTING_LINEBREAK_TITLE = Messages.getString("wsd.view.gui.ribbon.linebreak.title");
	/**
	 * 显示空行标记
	 */
	String DRAW_SETTING_LINEBREAK_DESCRIPTION = Messages.getString("wsd.view.gui.ribbon.linebreak.description");

	/********** 查找板**************结束 ******************/

	/********** 基本属性**************结束 *****************/

	/********** 插入面板**************开始 ******************/
	/**
	 * 插入task
	 */
	String INSERT_TASK = Messages.getString("wsd.view.gui.ribbon.84"); //$NON-NLS-1$

	/********** 章节板**************开始 ******************/
	/**
	 * 章节面板标签
	 */
	String INSERT_PAGE_SEQUENCE_BAND = Messages
			.getString("wsd.view.gui.ribbon.85"); //$NON-NLS-1$

	/**
	 * 插入章节按钮
	 */
	String INSERT_PAGE_SEQUENCE_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.86"); //$NON-NLS-1$

	String INSERT_PAGE_SEQUENCE_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.87"); //$NON-NLS-1$

	String INSERT_PAGE_SEQUENCE_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.88"); //$NON-NLS-1$

	/**
	 * 插入子模板按钮
	 */
	String INSERT_ZIMOBAN_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.insertzimoban.button"); //$NON-NLS-1$

	String IINSERT_ZIMOBAN_TITLE = Messages
			.getString("wsd.view.gui.ribbon.insertzimoban.title"); //$NON-NLS-1$

	String IINSERT_ZIMOBAN_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.insertzimoban.description"); //$NON-NLS-1$

	/********** 章节板**************结束 ******************/

	/********** 表格板**************开始 ******************/
	/**
	 * 表格面板
	 */
	String TABLE_BAND = Messages.getString("wsd.view.gui.ribbon.89"); //$NON-NLS-1$

	/**
	 * 表格按钮
	 */
	String TABLE_BUTTON = Messages.getString("wsd.view.gui.ribbon.90"); //$NON-NLS-1$

	String TABLE_BUTTON_TITLE = Messages.getString("wsd.view.gui.ribbon.91"); //$NON-NLS-1$

	String TABLE_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.92"); //$NON-NLS-1$

	/**
	 * 插入表格按钮
	 */
	String INSERT_TABLE_BUTTON = Messages.getString("wsd.view.gui.ribbon.93"); //$NON-NLS-1$

	/********** 表格板**************结束 ******************/

	/********** 图片板**************开始 ******************/
	/**
	 * 图片板
	 */
	String PICTURE_BAND = Messages.getString("wsd.view.gui.ribbon.94"); //$NON-NLS-1$

	/**
	 * 插入图片按钮
	 */
	String PICTURE_INSERT_BUTTON = Messages.getString("wsd.view.gui.ribbon.95"); //$NON-NLS-1$

	String PICTURE_INSERT_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.96"); //$NON-NLS-1$

	String PICTURE_INSERT_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.97"); //$NON-NLS-1$

	/**
	 * 插入条形码按钮
	 */
	String BARCODE_INSERT_BUTTON = Messages.getString("wsd.view.gui.ribbon.98"); //$NON-NLS-1$

	String BARCODE_INSERT_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.99"); //$NON-NLS-1$

	String BARCODE_INSERT_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.100"); //$NON-NLS-1$

	/**
	 * 插入二维条形码按钮
	 */
	String BARCODE_2D_INSERT_BUTTON = Messages.getString("RibbonUIText.28"); //$NON-NLS-1$

	String BARCODE_2D_INSERT_BUTTON_TITLE = Messages
			.getString("RibbonUIText.29"); //$NON-NLS-1$

	String BARCODE_2D_INSERT_BUTTON_DESCRIPTION = Messages
			.getString("RibbonUIText.30"); //$NON-NLS-1$

	/**
	 * 插入条形码下拉菜单
	 */
	String BARCODE_TYPE_LABEL = Messages.getString("wsd.view.gui.ribbon.101"); //$NON-NLS-1$

	String[] BARCODE_TYPES =
	{
			Messages.getString("wsd.view.gui.ribbon.102"), Messages.getString("wsd.view.gui.ribbon.103"), Messages.getString("wsd.view.gui.ribbon.104"), Messages.getString("wsd.view.gui.ribbon.105"), Messages.getString("wsd.view.gui.ribbon.106"), Messages.getString("wsd.view.gui.ribbon.107"), Messages.getString("wsd.view.gui.ribbon.108"), Messages.getString("wsd.view.gui.ribbon.109") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$

	String[] BARCODE_2D_TYPES =
	{ Messages.getString("RibbonUIText.31") }; //$NON-NLS-1$

	// 画布
	String INSERT_GRAPH_CANVAS_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.app.3"); //$NON-NLS-1$

	String INSERT_GRAPH_CANVAS_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.app.4"); //$NON-NLS-1$

	String INSERT_GRAPH_CANVAS_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.app.5"); //$NON-NLS-1$

	// 图形
	String INSERT_GRAPH_BUTTOKN = Messages
			.getString("wsd.view.gui.ribbon.app.6"); //$NON-NLS-1$

	String INSERT_SVG_TEXT = Messages.getString("wsd.view.gui.ribbon.app.text");

	String INSERT_GRAPH_BUTTOKN_TITLE = Messages
			.getString("wsd.view.gui.ribbon.app.7"); //$NON-NLS-1$

	String INSERT_SVG_TEXT_TITLE = Messages
			.getString("wsd.view.gui.ribbon.app.inserttext");

	String INSERT_GRAPH_BUTTOKN_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.app.8"); //$NON-NLS-1$

	String INSERT_GRAPH_GROUP_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.app.9"); //$NON-NLS-1$

	// 统计图
	String INSERT_CHART_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.app.chart"); //$NON-NLS-1$

	String INSERT_CHART_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.app.chart.title"); //$NON-NLS-1$

	String INSERT_CHART_BUTTON_DESCRIPTION = Messages.getString(Messages
			.getString("RibbonUIText.32")); //$NON-NLS-1$

	String[] INSERT_GRAPH_BUTTON_LIST =
	{
			Messages.getString("wsd.view.gui.ribbon.app.10"), Messages.getString("wsd.view.gui.ribbon.app.11"), Messages.getString("wsd.view.gui.ribbon.app.12") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	/********** 图片板**************结束 ******************/

	/********** 高级块容器板**************开始 ******************/
	/**
	 * 高级块容器板
	 */
	String BLOCK_CONTAINER_BAND = Messages.getString("wsd.view.gui.ribbon.110"); //$NON-NLS-1$

	/**
	 * 绝对位置容器按钮
	 */
	String BLOCK_CONTAINER_ABS_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.111"); //$NON-NLS-1$

	String BLOCK_CONTAINER_ABS_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.112"); //$NON-NLS-1$

	String BLOCK_CONTAINER_ABS_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.113"); //$NON-NLS-1$

	/**
	 * 相对位置容器按钮
	 */
	String BLOCK_CONTAINER_REL_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.114"); //$NON-NLS-1$

	String BLOCK_CONTAINER_REL_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.115"); //$NON-NLS-1$

	String BLOCK_CONTAINER_REL_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.116"); //$NON-NLS-1$

	/********** 高级块容器板**************结束 ******************/

	/********** 页码板**************开始 ******************/
	/**
	 * 插入页码板
	 */
	String PAGE_NUMBER_INSERT_BAND = Messages
			.getString("wsd.view.gui.ribbon.117"); //$NON-NLS-1$

	/**
	 * 插入当前页码按钮
	 */
	String PAGE_NUMBER_CURRNET_INSERT_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.118"); //$NON-NLS-1$

	String PAGE_NUMBER_CURRNET_INSERT_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.119"); //$NON-NLS-1$

	String PAGE_NUMBER_CURRNET_INSERT_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.120"); //$NON-NLS-1$

	/**
	 * 插入当前章节页码按钮
	 */
	String PAGE_NUMBER_CHAPTER_INSERT_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.121"); //$NON-NLS-1$

	String PAGE_NUMBER_CHAPTER_INSERT_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.122"); //$NON-NLS-1$

	String PAGE_NUMBER_CHAPTER_INSERT_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.123"); //$NON-NLS-1$

	/**
	 * 插入文档总页码按钮
	 */
	String PAGE_NUMBER_TOTAL_INSERT_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.124"); //$NON-NLS-1$

	String PAGE_NUMBER_TOTAL_INSERT_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.125"); //$NON-NLS-1$

	String PAGE_NUMBER_TOTAL_INSERT_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.126"); //$NON-NLS-1$

	/********** 页码板**************结束 ******************/

	/********** 格式化数字板**************开始 ******************/
	/**
	 * 格式化数字板
	 */
	String NUMBER_FORMAT_BAND = Messages.getString("wsd.view.gui.ribbon.127"); //$NON-NLS-1$

	/**
	 * 日期和时间按钮
	 */
	String DATE_TIME_BUTTON = Messages.getString("wsd.view.gui.ribbon.128"); //$NON-NLS-1$

	String DATE_TIME_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.129"); //$NON-NLS-1$

	String DATE_TIME_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.130"); //$NON-NLS-1$

	/**
	 * 格式化数字按钮
	 */
	String NUMBER_FORMAT_BUTTON = Messages.getString("wsd.view.gui.ribbon.131"); //$NON-NLS-1$

	String NUMBER_FORMAT_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.132"); //$NON-NLS-1$

	String NUMBER_FORMAT_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.133"); //$NON-NLS-1$

	/********** 格式化数字板**************结束 ******************/
	/**序号版*/
	String POSITIONINLINE_BUTTON = Messages.getString("wsd.view.gui.ribbon.positioninline");

	String POSITIONINLINE_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.positioninline.title"); 

	String POSITIONINLINE_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.positioninline.description");
	String POSITIONINLINE_BUTTON_1 = Messages
	.getString("wsd.view.gui.ribbon.positioninline.1");
	String POSITIONINLINE_BUTTON_a = Messages
	.getString("wsd.view.gui.ribbon.positioninline.a");
	String POSITIONINLINE_BUTTON_A = Messages
	.getString("wsd.view.gui.ribbon.positioninline.A");
	String POSITIONINLINE_BUTTON_i = Messages
	.getString("wsd.view.gui.ribbon.positioninline.i");
	String POSITIONINLINE_BUTTON_I = Messages
	.getString("wsd.view.gui.ribbon.positioninline.I");
	/********** 序号版**************结束 ******************/

	/********** 插入面板**************结束 *****************/

	/********** 页布局面板**************开始 ******************/
	/**
	 * 页布局面板
	 */
	String PAGE_TASK = Messages.getString("wsd.view.gui.ribbon.134"); //$NON-NLS-1$

	/********** 简单页布局板**************结束 ******************/
	/**
	 * 简单页面设置板
	 */
	String PAGE_BAND = Messages.getString("wsd.view.gui.ribbon.135"); //$NON-NLS-1$

	/**
	 * 创建单个页布局按钮
	 */
	String LAYOUT_CREATE_NORMAL_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.136"); //$NON-NLS-1$

	String LAYOUT_CREATE_NORMAL_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.137"); //$NON-NLS-1$

	String LAYOUT_CREATE_NORMAL_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.138"); //$NON-NLS-1$

	/**
	 * 编辑单个页布局按钮
	 */
	String LAYOUT_EDIT_NORMAL_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.139"); //$NON-NLS-1$

	String LAYOUT_EDIT_NORMAL_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.140"); //$NON-NLS-1$

	String LAYOUT_EDIT_NORMAL_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.141"); //$NON-NLS-1$

	/**
	 * 文字方向按钮
	 */
	String TEXT_ORIENTATION_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.142"); //$NON-NLS-1$

	String TEXT_ORIENTATION_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.143"); //$NON-NLS-1$

	String TEXT_ORIENTATION_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.144"); //$NON-NLS-1$

	/**
	 * 文字方向下拉菜单
	 */
	String PAGE_ORIENTATION_LRTB = Messages
			.getString("wsd.view.gui.ribbon.145"); //$NON-NLS-1$

	String PAGE_ORIENTATION_RLTB = Messages
			.getString("wsd.view.gui.ribbon.146"); //$NON-NLS-1$

	String PAGE_ORIENTATION_TBRL = Messages
			.getString("wsd.view.gui.ribbon.147"); //$NON-NLS-1$

	/**
	 * 纸张方向按钮
	 */
	String PAGE_ORIENTATION_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.148"); //$NON-NLS-1$

	String PAGE_ORIENTATION_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.149"); //$NON-NLS-1$

	String PAGE_ORIENTATION_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.150"); //$NON-NLS-1$

	/**
	 * 纸张方向下拉菜单
	 */
	String PAGE_ORIENTATION_VERTICAL = Messages
			.getString("wsd.view.gui.ribbon.151"); //$NON-NLS-1$

	String PAGE_ORIENTATION_HORIZONTAL = Messages
			.getString("wsd.view.gui.ribbon.152"); //$NON-NLS-1$

	/**
	 * 纸张大小按钮
	 */
	String PAGE_SIZE_BUTTON = Messages.getString("wsd.view.gui.ribbon.153"); //$NON-NLS-1$

	String PAGE_SIZE_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.154"); //$NON-NLS-1$

	String PAGE_SIZE_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.155"); //$NON-NLS-1$

	/**
	 * 纸张大小下拉菜单
	 */
	String PAGE_SIZE_DETAIL_MENU = Messages
			.getString("wsd.view.gui.ribbon.156"); //$NON-NLS-1$

	/********** 简单页布局板**************结束 ******************/

	/********** 版心页布局板**************开始 ******************/
	/**
	 * 版心页布局板
	 */
	String BODY_BAND = Messages.getString("wsd.view.gui.ribbon.157"); //$NON-NLS-1$

	String PAGE_BODY_BAND = Messages.getString("pageband");

	/**
	 * 版心边框标签
	 */
	String BODY_BORDER_LABEL = Messages.getString("wsd.view.gui.ribbon.158"); //$NON-NLS-1$

	/**
	 * 版心对齐按钮
	 */
	String BODY_DISPLAY_ALIGN_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.159"); //$NON-NLS-1$

	String BODY_DISPLAY_ALIGN_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.160"); //$NON-NLS-1$

	String BODY_DISPLAY_ALIGN_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.161"); //$NON-NLS-1$

	/**
	 * 版心对齐下拉菜单
	 */
	String BODY_DISPLAY_ALIGN_BEFORE = Messages
			.getString("wsd.view.gui.ribbon.162"); //$NON-NLS-1$

	String BODY_DISPLAY_ALIGN_CENTER = Messages
			.getString("wsd.view.gui.ribbon.163"); //$NON-NLS-1$

	String BODY_DISPLAY_ALIGN_AFTER = Messages
			.getString("wsd.view.gui.ribbon.164"); //$NON-NLS-1$

	/**
	 * 分栏按钮
	 */
	String PAGE_COLUMN_BUTTON = Messages.getString("wsd.view.gui.ribbon.165"); //$NON-NLS-1$

	String PAGE_COLUMN_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.166"); //$NON-NLS-1$

	String PAGE_COLUMN_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.167"); //$NON-NLS-1$

	/**
	 * 分栏个数下拉菜单
	 */
	String PAGE_COLUMN_ONE = Messages.getString("wsd.view.gui.ribbon.168"); //$NON-NLS-1$

	String PAGE_COLUMN_TWO = Messages.getString("wsd.view.gui.ribbon.169"); //$NON-NLS-1$

	String PAGE_COLUMN_THREE = Messages.getString("wsd.view.gui.ribbon.170"); //$NON-NLS-1$

	/********** 版心页布局板**************结束 ******************/

	/********** 页布局序列板**************开始 ******************/
	/**
	 * 页布局序列板
	 */
	String REGION_BAND = Messages.getString("wsd.view.gui.ribbon.171"); //$NON-NLS-1$

	/**
	 * 创建页布局序列按钮
	 */
	String PAGE_SEQUENCE_MASTER_ADD_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.172"); //$NON-NLS-1$

	String PAGE_SEQUENCE_MASTER_ADD_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.173"); //$NON-NLS-1$

	String PAGE_SEQUENCE_MASTER_ADD_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.174"); //$NON-NLS-1$

	/**
	 * 编辑页布局序列按钮
	 */
	String PAGE_SEQUENCE_MASTER_EDIT_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.175"); //$NON-NLS-1$

	String PAGE_SEQUENCE_MASTER_EDIT_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.176"); //$NON-NLS-1$

	String PAGE_SEQUENCE_MASTER_EDIT_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.177"); //$NON-NLS-1$

	/********** 页布局序列板**************结束 ******************/

	/********** 版心背景板**************开始 ******************/
	/**
	 * 版心背景板
	 */
	String BODY_BACKGROUND_BAND = Messages.getString("wsd.view.gui.ribbon.178"); //$NON-NLS-1$

	/**
	 * 背景图按钮
	 */
	String BODY_BACKGROUND_PICTURE_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.179"); //$NON-NLS-1$

	String BODY_BACKGROUND_PICTURE_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.180"); //$NON-NLS-1$

	String BODY_BACKGROUND_PICTURE_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.181"); //$NON-NLS-1$

	/**
	 * 背景颜色按钮
	 */
	String BODY_BACKGROUND_COLOR_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.182"); //$NON-NLS-1$

	String BODY_BACKGROUND_COLOR_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.183"); //$NON-NLS-1$

	String BODY_BACKGROUND_COLOR_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.184"); //$NON-NLS-1$

	/**
	 * 背景重复按钮
	 */
	String BODY_BACKGROUND_PIC_REPEAT_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.185"); //$NON-NLS-1$

	String BODY_BACKGROUND_PIC_REPEAT_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.186"); //$NON-NLS-1$

	String BODY_BACKGROUND_PIC_REPEAT_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.187"); //$NON-NLS-1$

	/**
	 * 背景重复按钮下拉菜单
	 */
	String[] BACKGROUND_PIC_REPEAT_MENU =
	{
			Messages.getString("wsd.view.gui.ribbon.188"), Messages.getString("wsd.view.gui.ribbon.189"), Messages.getString("wsd.view.gui.ribbon.190"), Messages.getString("wsd.view.gui.ribbon.191") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

	/**
	 * 水平方向对齐方式按钮
	 */
	String BODY_BACKGROUND_HORIZONTAL_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.192"); //$NON-NLS-1$

	String BODY_BACKGROUND_HORIZONTAL_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.193"); //$NON-NLS-1$

	String BODY_BACKGROUND_HORIZONTAL_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.194"); //$NON-NLS-1$

	/**
	 * 水平方向对齐方式按钮下拉菜单
	 */
	String[] BACKGROUND_HORIZONTAL_MENU =
	{
			Messages.getString("wsd.view.gui.ribbon.195"), Messages.getString("wsd.view.gui.ribbon.196"), Messages.getString("wsd.view.gui.ribbon.197") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	/**
	 * 垂直方向对齐方式按钮
	 */
	String BODY_BACKGROUND_VERTICAL_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.198"); //$NON-NLS-1$

	String BODY_BACKGROUND_VERTICAL_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.199"); //$NON-NLS-1$

	String BODY_BACKGROUND_VERTICAL_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.200"); //$NON-NLS-1$

	/**
	 * 垂直方向对齐方式下拉菜单
	 */
	String[] BACKGROUND_VERTICAL_MENU =
	{
			Messages.getString("wsd.view.gui.ribbon.201"), Messages.getString("wsd.view.gui.ribbon.202"), Messages.getString("wsd.view.gui.ribbon.203") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	/********** 版心背景板**************结束 ******************/

	/********** 表格操作task**************开始 ******************/
	/**
	 * 表格task group
	 */
	String TABLE_TASK_GROUP = Messages.getString("wsd.view.gui.ribbon.204"); //$NON-NLS-1$

	/**
	 * 表格操作task
	 */
	String TABLE_OPERATION_TASK = Messages.getString("wsd.view.gui.ribbon.205"); //$NON-NLS-1$

	/********** 行和列板**************开始 ******************/
	/**
	 * 行和列板
	 */
	String TABLE_ROW_COLUMN_BAND = Messages
			.getString("wsd.view.gui.ribbon.206"); //$NON-NLS-1$

	/**
	 * 删除表格按钮
	 */
	String TABLE_DELETE_BUTTON = Messages.getString("wsd.view.gui.ribbon.207"); //$NON-NLS-1$

	String TABLE_DELETE_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.208"); //$NON-NLS-1$

	String TABLE_DELETE_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.209"); //$NON-NLS-1$

	/**
	 * 删除表格按钮下拉菜单
	 */
	String[] TABLE_DELETE_MENU =
	{
			Messages.getString("wsd.view.gui.ribbon.210"), Messages.getString("wsd.view.gui.ribbon.211"), Messages.getString("wsd.view.gui.ribbon.212"), Messages.getString("wsd.view.gui.ribbon.213"), Messages.getString("wsd.view.gui.ribbon.214"), Messages.getString("wsd.view.gui.ribbon.215") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$

	/**
	 * 表格上方插入行按钮
	 */
	String TABLE_INSERT_UP_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.216"); //$NON-NLS-1$

	String TABLE_INSERT_UP_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.217"); //$NON-NLS-1$

	String TABLE_INSERT_UP_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.218"); //$NON-NLS-1$

	/**
	 * 下方插入行按钮
	 */
	String TABLE_INSERT_BOTTOM_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.219"); //$NON-NLS-1$

	String TABLE_INSERT_BOTTOM_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.220"); //$NON-NLS-1$

	String TABLE_INSERT_BOTTOM_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.221"); //$NON-NLS-1$

	/**
	 * 表格左侧插入按钮
	 */
	String TABLE_INSERT_LEFT_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.222"); //$NON-NLS-1$

	String TABLE_INSERT_LEFT_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.223"); //$NON-NLS-1$

	String TABLE_INSERT_LEFT_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.224"); //$NON-NLS-1$

	/**
	 * 表格右侧插入按钮
	 */
	String TABLE_INSERT_RIGHT_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.225"); //$NON-NLS-1$

	String TABLE_INSERT_RIGHT_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.226"); //$NON-NLS-1$

	String TABLE_INSERT_RIGHT_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.227"); //$NON-NLS-1$

	/**
	 * 添加表头按钮
	 */
	String TABLE_INSERT_HEADER_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.228"); //$NON-NLS-1$

	String TABLE_INSERT_HEADER_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.229"); //$NON-NLS-1$

	String TABLE_INSERT_HEADER_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.230"); //$NON-NLS-1$

	/**
	 * 添加表尾按钮
	 */
	String TABLE_INSERT_FOOTER_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.231"); //$NON-NLS-1$

	String TABLE_INSERT_FOOTER_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.232"); //$NON-NLS-1$

	String TABLE_INSERT_FOOTER_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.233"); //$NON-NLS-1$

	/********** 行和列板**************结束 ******************/

	/********** 合并拆分板**************开始 ******************/
	/**
	 * 合并拆分板
	 */
	String TABLE_COMBINE_BREAKDOWN_BAND = Messages
			.getString("wsd.view.gui.ribbon.234"); //$NON-NLS-1$

	/**
	 * 合并单元格按钮
	 */
	String TABLE_MERGE_CELL_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.235"); //$NON-NLS-1$

	String TABLE_MERGE_CELL_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.236"); //$NON-NLS-1$

	String TABLE_MERGE_CELL_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.237"); //$NON-NLS-1$

	/**
	 * 拆分单元格按钮
	 */
	String TABLE_CELL_SPLIT_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.238"); //$NON-NLS-1$

	String TABLE_CELL_SPLIT_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.239"); //$NON-NLS-1$

	String TABLE_CELL_SPLIT_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.240"); //$NON-NLS-1$

	/**
	 * 拆分表格按钮
	 */
	String TABLE_SPLIT_BUTTON = Messages.getString("wsd.view.gui.ribbon.241"); //$NON-NLS-1$

	String TABLE_SPLIT_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.242"); //$NON-NLS-1$

	String TABLE_SPLIT_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.243"); //$NON-NLS-1$

	/********** 合并拆分板**************结束 ******************/

	/********** 表格操作task**************结束 *****************/

	/********** 表格属性task**************开始 *****************/
	/**
	 * 表格属性task
	 */
	String TABLE_PROPERTY_TASK = Messages.getString("wsd.view.gui.ribbon.244"); //$NON-NLS-1$

	/********** 表格样式板**************开始 ******************/
	/**
	 * 表格样式
	 */
	String TABLE_STYLE_BAND = Messages.getString("wsd.view.gui.ribbon.245"); //$NON-NLS-1$

	/**
	 * 表格边框按钮
	 */
	String TABLE_BORDER_BUTTON = Messages.getString("wsd.view.gui.ribbon.246"); //$NON-NLS-1$

	String TABLE_BORDER_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.247"); //$NON-NLS-1$

	String TABLE_BORDER_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.248"); //$NON-NLS-1$

	/**
	 * 表格背景色按钮
	 */
	String TABLE_BACKGROUND_COLOR_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.249"); //$NON-NLS-1$

	String TABLE_BACKGROUND_COLOR_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.250"); //$NON-NLS-1$

	String TABLE_BACKGROUND_COLOR_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.251"); //$NON-NLS-1$

	/**
	 * 背景图按钮
	 */
	String TABLE_PICTURE_BUTTON = Messages.getString("wsd.view.gui.ribbon.252"); //$NON-NLS-1$

	String TABLE_PICTURE_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.253"); //$NON-NLS-1$

	String TABLE_PICTURE_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.254"); //$NON-NLS-1$

	/********** 表格样式板**************结束 ******************/

	/********** 单元格属性板**************开始 ******************/

	String TABLE_CELL_SIZE = Messages.getString("tablecellsize");

	/**
	 * 单元格属性板
	 */
	String TABLE_CELL_PROPERTY_BAND = Messages
			.getString("wsd.view.gui.ribbon.255"); //$NON-NLS-1$

	/**
	 * 表格高度标签
	 */
	String TABLE_CELL_HEIGHT_LABEL = Messages
			.getString("wsd.view.gui.ribbon.256"); //$NON-NLS-1$

	/**
	 * 表格宽度标签
	 */
	String TABLE_CELL_WIDTH_LABEL = Messages
			.getString("wsd.view.gui.ribbon.257"); //$NON-NLS-1$

	String TABLE_CELL_PADDING = Messages.getString("wsd.view.gui.ribbon.258"); //$NON-NLS-1$

	String LEFT_PADDING = Messages.getString("wsd.view.gui.ribbon.259"); //$NON-NLS-1$

	String RIGHT_PADDING = Messages.getString("wsd.view.gui.ribbon.260"); //$NON-NLS-1$

	String TOP_PADDING = Messages.getString("wsd.view.gui.ribbon.261"); //$NON-NLS-1$

	String BOTTOM_PADDING = Messages.getString("wsd.view.gui.ribbon.262"); //$NON-NLS-1$

	/**
	 * 自动调整按钮
	 */
	String CELL_AUTO_SIZE_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.263"); //$NON-NLS-1$

	String CELL_AUTO_SIZE_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.264"); //$NON-NLS-1$

	String CELL_AUTO_SIZE_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.265"); //$NON-NLS-1$

	/**
	 * 自动调整下拉菜单
	 */
	String[] CELL_AUTO_SIZE_MENU =
	{
			Messages.getString("wsd.view.gui.ribbon.266"), Messages.getString("wsd.view.gui.ribbon.267") }; //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * 单元格边框按钮
	 */
	String CELL_BORDER_BUTTON = Messages.getString("wsd.view.gui.ribbon.268"); //$NON-NLS-1$

	String CELL_BORDER_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.269"); //$NON-NLS-1$

	String CELL_BORDER_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.270"); //$NON-NLS-1$

	/**
	 * 单元格背景色按钮
	 */
	String CELL_BACKGROUND_COLOR_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.271"); //$NON-NLS-1$

	String CELL_BACKGROUND_COLOR_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.272"); //$NON-NLS-1$

	String CELL_BACKGROUND_COLOR_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.273"); //$NON-NLS-1$

	/**
	 * 背景图按钮
	 */
	String CELL_BACKGROUND_PICTURE_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.274"); //$NON-NLS-1$

	String CELL_BACKGROUND_PICTURE_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.275"); //$NON-NLS-1$

	String CELL_BACKGROUND_PICTURE_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.276"); //$NON-NLS-1$

	/**
	 * 单元格内容对齐按钮
	 */
	String CELL_DISPLAY_ALIGN_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.277"); //$NON-NLS-1$

	String CELL_DISPLAY_ALIGN_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.278"); //$NON-NLS-1$

	String CELL_DISPLAY_ALIGN_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.279"); //$NON-NLS-1$

	/**
	 * 单元格对齐方式下拉菜单
	 */
	String[] CELL_DISPLAY_ALIGN_MENU =
	{
			Messages.getString("wsd.view.gui.ribbon.280"), Messages.getString("wsd.view.gui.ribbon.281"), Messages.getString("wsd.view.gui.ribbon.282"), }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	/********** 单元格属性板**************结束 ******************/

	/********** 表格属性task**************结束 *****************/

	/********** 条形码 task group**************开始 *****************/
	/**
	 * 条形码task group
	 */
	String BARCODE_TASK_GROUP = Messages.getString("wsd.view.gui.ribbon.283"); //$NON-NLS-1$

	/********** 条形码样式 task**************开始 ****************/
	/**
	 * 条形码样式
	 */
	String BARCODE_TASK = Messages.getString("wsd.view.gui.ribbon.284"); //$NON-NLS-1$

	/********** 条形码编码设置板**********开始 ***************/
	/**
	 * 条形码设置band
	 */
	String BARCODE_CODING_BAND = Messages.getString("wsd.view.gui.ribbon.285"); //$NON-NLS-1$

	/**
	 * 是否添加校验码
	 */
	String BARCODE_CHECK_SUM = Messages.getString("wsd.view.gui.ribbon.286"); //$NON-NLS-1$

	/**
	 * 是否按UCC/EAN编码
	 */
	String BARCODE_UCC_EAN = Messages.getString("wsd.view.gui.ribbon.287"); //$NON-NLS-1$

	/********** 条形码编码设置板**********结束 ***************/

	/********** 条形码内容板**********开始 ***************/
	/**
	 * 条形码内容板
	 */
	String BARCODE_CONTENT_BAND = Messages.getString("wsd.view.gui.ribbon.288"); //$NON-NLS-1$

	/**
	 * 条形码节点按钮
	 */
	String BARCODE_NODE_BUTTON = Messages.getString("wsd.view.gui.ribbon.289"); //$NON-NLS-1$

	String BARCODE_NODE_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.290"); //$NON-NLS-1$

	String BARCODE_NODE_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.291"); //$NON-NLS-1$

	/**
	 * 条形码内容标签
	 */
	String BARCODE_CONTENT_LABEL = Messages
			.getString("wsd.view.gui.ribbon.292"); //$NON-NLS-1$

	/**
	 * 条形码临时内容
	 */
	String BARCODE_CONTENT_FIELD = Messages
			.getString("wsd.view.gui.ribbon.293"); //$NON-NLS-1$

	/********** 条形码内容板**********结束 ***************/

	/********** 条形码类型板**********开始 ***************/
	/**
	 * 条形码类型band
	 */
	String BARCODE_TYPE_BAND = Messages.getString("wsd.view.gui.ribbon.294"); //$NON-NLS-1$

	/**
	 * 条形码整体高度标签
	 */
	String BARCODE_HEIGHT_LABEL = Messages.getString("wsd.view.gui.ribbon.295"); //$NON-NLS-1$

	String BARCODE_HEIGHT_LABEL_TITLE = Messages
			.getString("wsd.view.gui.ribbon.296"); //$NON-NLS-1$

	String BARCODE_HEIGHT_LABEL_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.297"); //$NON-NLS-1$

	/**
	 * 条形码基本单元宽度
	 */
	String BARCODE_WIDTH_LABEL = Messages.getString("wsd.view.gui.ribbon.298"); //$NON-NLS-1$

	String BARCODE_WIDTH_LABEL_TITLE = Messages
			.getString("wsd.view.gui.ribbon.299"); //$NON-NLS-1$

	String BARCODE_WIDTH_LABEL_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.300"); //$NON-NLS-1$

	/********** 条形码类型板**********结束 ***************/

	/********** 条形码位置板**********结束 ***************/
	/**
	 * 条形码位置设置
	 */
	String BARCODE_POSITION_BAND = Messages
			.getString("wsd.view.gui.ribbon.301"); //$NON-NLS-1$

	String BARCODE_POSITION_GROUP = Messages
			.getString("wsd.view.gui.ribbon.302"); //$NON-NLS-1$

	/**
	 * 距左右距离标签
	 */
	String BARCODE_START_LABEL = Messages.getString("wsd.view.gui.ribbon.303"); //$NON-NLS-1$

	String BARCODE_START_LABEL_TITLE = Messages
			.getString("wsd.view.gui.ribbon.304"); //$NON-NLS-1$

	String BARCODE_START_LABEL_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.305"); //$NON-NLS-1$

	/**
	 * 距上下距离标签
	 */
	String BARCODE_TOP_LABEL = Messages.getString("wsd.view.gui.ribbon.306"); //$NON-NLS-1$

	String BARCODE_TOP_LABEL_TITLE = Messages
			.getString("wsd.view.gui.ribbon.307"); //$NON-NLS-1$

	String BARCODE_TOP_LABEL_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.308"); //$NON-NLS-1$

	/********** 条形码位置板**********结束 ***************/

	/********** 条形码方向板**********开始 ***************/
	/**
	 * 条形码方向板
	 */
	String BARCODE_DIRECTION_BAND = Messages
			.getString("wsd.view.gui.ribbon.309"); //$NON-NLS-1$

	/**
	 * 条形码方向标签
	 */
	String BARCODE_DIRECTION_LABEL = Messages
			.getString("wsd.view.gui.ribbon.310"); //$NON-NLS-1$

	/********** 条形码方向板**********结束 ***************/

	/********** 条形码样式 task**************结束 ****************/

	/********** 条形码内容样式 task**************开始 ****************/
	/**
	 * 条形码内容task
	 */
	String BARCODE_TEXT_TASK = Messages.getString("wsd.view.gui.ribbon.311"); //$NON-NLS-1$

	/********** 条形码字体样式板**********开始 ***************/
	/**
	 * 条形码字体样式band
	 */
	String BARCODE_FONT_BAND = Messages.getString("wsd.view.gui.ribbon.312"); //$NON-NLS-1$

	/**
	 * 清除条形码样式按钮
	 */
	String BARCODE_CLEAN_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.313"); //$NON-NLS-1$

	String BARCODE_CLEAN_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.314"); //$NON-NLS-1$

	String BARCODE_FONT_FAMILY = Messages.getString("wsd.view.gui.ribbon.315"); //$NON-NLS-1$

	String BARCODE_FONT_SIZE = Messages.getString("wsd.view.gui.ribbon.316"); //$NON-NLS-1$

	String BARCODE_FONT_STYLE = Messages.getString("wsd.view.gui.ribbon.317"); //$NON-NLS-1$

	String BARCODE_FONT_COLOR = Messages.getString("wsd.view.gui.ribbon.318"); //$NON-NLS-1$

	String BARCODE_SUBSET_LABEL = Messages.getString("wsd.view.gui.ribbon.319"); //$NON-NLS-1$

	/**
	 * 条形码字符集
	 */
	String[] BARCODE_SUBSET =
	{
			Messages.getString("wsd.view.gui.ribbon.320"), Messages.getString("wsd.view.gui.ribbon.321"), Messages.getString("wsd.view.gui.ribbon.322") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	/********** 条形码字体样式板**********结束 ***************/

	/********** 条形码显示内容板**********开始 ***************/

	String BARCODE_CONTENT_POSITION_TITLE = Messages
			.getString("wsd.view.gui.ribbon.323"); //$NON-NLS-1$

	/**
	 * 条形码显示内容板
	 */
	String BARCODE_CONTENT_DISPLAY_BAND = Messages
			.getString("wsd.view.gui.ribbon.324"); //$NON-NLS-1$

	/**
	 * 条形码内容显示field
	 */
	String BARCODE_CONTENT_DISPLAY_FIELD = Messages
			.getString("wsd.view.gui.ribbon.325"); //$NON-NLS-1$

	/**
	 * 是否打印显示内容
	 */
	String BARCODE_CONTENT_PRINT = Messages
			.getString("wsd.view.gui.ribbon.326"); //$NON-NLS-1$

	/********** 条形码显示内容板**********结束 ***************/

	/********** 条形码文字位置板**********开始 ***************/
	/**
	 * 条形码文字位置板
	 */
	String BARCODE_TEXT_POSITION_BAND = Messages
			.getString("wsd.view.gui.ribbon.327"); //$NON-NLS-1$

	/**
	 * 条码内部距离标签
	 */
	String BARCODE_INNER_SPACE_LABEL = Messages
			.getString("wsd.view.gui.ribbon.328"); //$NON-NLS-1$

	/**
	 * 文字和条码间距
	 */
	String BARCODE_TEXT_SPACE_LABEL = Messages
			.getString("wsd.view.gui.ribbon.329"); //$NON-NLS-1$

	/********** 条形码文字位置板**********结束 ***************/

	/********** 条形码宽窄比板**********开始 ***************/
	/**
	 * 条形码宽窄比
	 */
	String BARCODE_WIDE_NARROW_BAND = Messages
			.getString("wsd.view.gui.ribbon.330"); //$NON-NLS-1$

	String BARCODE_COLOR_LAYER = Messages.getString("wsd.view.gui.ribbon.331"); //$NON-NLS-1$

	/**
	 * 宽窄比标签
	 */
	String BARCODE_WIDE_NARROW_LABEL = Messages
			.getString("wsd.view.gui.ribbon.332"); //$NON-NLS-1$

	/********** 条形码宽窄比板**********结束 ***************/

	/********** 条形码内容样式 task**************结束 ****************/

	/********** 条形码 task group**************结束 *****************/

	/********** 高级块容器 task group**************开始 *****************/
	/**
	 * 高级块容器task group
	 */
	String BLOCK_CONTAINER_TASK_GROUP = Messages
			.getString("wsd.view.gui.ribbon.333"); //$NON-NLS-1$

	/**
	 * 高级块容器task
	 */
	String BLOCK_CONTAINER_TASK = Messages.getString("wsd.view.gui.ribbon.334"); //$NON-NLS-1$

	/********** 块容器位置和大小面板**********开始 ***************/
	/**
	 * 块容器位置和大小
	 */
	String BLOCK_CONTAINER_POSITION_BAND = Messages
			.getString("wsd.view.gui.ribbon.335"); //$NON-NLS-1$

	String TABLE_INDENT = Messages.getString("tableindent");

	/**
	 * 上边距标签
	 */
	String BLOCK_CONTAINER_TOP_LABEL = Messages
			.getString("wsd.view.gui.ribbon.336"); //$NON-NLS-1$

	/**
	 * 左边距标签
	 */
	String BLOCK_CONTAINER_LEFT_LABEL = Messages
			.getString("wsd.view.gui.ribbon.337"); //$NON-NLS-1$

	/**
	 * 块高标签
	 */
	String BLOCK_CONTAINER_HEIGHT_LABEL = Messages
			.getString("wsd.view.gui.ribbon.338"); //$NON-NLS-1$

	/**
	 * 块宽标签
	 */
	String BLOCK_CONTAINER_WIDTH_LABEL = Messages
			.getString("wsd.view.gui.ribbon.339"); //$NON-NLS-1$

	/********** 块容器位置和大小面板**********结束 ***************/

	/********** 块容器边框和背景面板**********开始 ***************/
	/**
	 * 块容器边框和背景面板
	 */
	String BLOCK_CONTAINER_BORDER_BACKGROUND_BAND = Messages
			.getString("wsd.view.gui.ribbon.340"); //$NON-NLS-1$

	/**
	 * 边框按钮
	 */
	String BLOCK_CONTAINER_BORDER_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.341"); //$NON-NLS-1$

	String BLOCK_CONTAINER_BORDER_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.342"); //$NON-NLS-1$

	String BLOCK_CONTAINER_BORDER_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.343"); //$NON-NLS-1$

	/**
	 * 背景颜色按钮
	 */
	String BLOCK_CONTAINER_BACKGROUND_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.344"); //$NON-NLS-1$

	String BLOCK_CONTAINER_BACKGROUND_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.345"); //$NON-NLS-1$

	String BLOCK_CONTAINER_BACKGROUND_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.346"); //$NON-NLS-1$

	/**
	 * 背景图按钮
	 */
	String BLOCK_CONTAINER_PICTURE_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.347"); //$NON-NLS-1$

	String BLOCK_CONTAINER_PICTURE_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.348"); //$NON-NLS-1$

	String BLOCK_CONTAINER_PICTURE_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.349"); //$NON-NLS-1$

	/********** 块容器边框和背景面板**********结束 ***************/

	/********** 块容器对齐方式面板**********开始 ***************/
	/**
	 * 块容器对齐方式面板
	 */
	String BLOCK_CONTAINER_DISPLAY_ALIGN_BAND = Messages
			.getString("wsd.view.gui.ribbon.350"); //$NON-NLS-1$

	/**
	 * 块容器内文字对齐方式按钮
	 */
	String BLOCK_CONTAINER_DISPLAY_ALIGN_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.351"); //$NON-NLS-1$

	String BLOCK_CONTAINER_DISPLAY_ALIGN_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.352"); //$NON-NLS-1$

	String BLOCK_CONTAINER_DISPLAY_ALIGN_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.353"); //$NON-NLS-1$

	/**
	 * 块容器内文字对齐方式下拉菜单
	 */
	String[] BLOCK_CONTAINER_DISPLAY_MENU =
	{
			Messages.getString("wsd.view.gui.ribbon.354"), Messages.getString("wsd.view.gui.ribbon.355"), Messages.getString("wsd.view.gui.ribbon.356"), }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	/********** 块容器对齐方式面板**********结束 ***************/

	/********** 块容器溢出处理面板**********开始 ***************/
	/**
	 * 溢出处理面板
	 */
	String BLOCK_CONTAINER_OVERFLOW_BAND = Messages
			.getString("wsd.view.gui.ribbon.357"); //$NON-NLS-1$

	/**
	 * 可视按钮
	 */
	String BLOCK_CONTAINER_OVERFLOW_VISIBLE_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.358"); //$NON-NLS-1$

	String BLOCK_CONTAINER_OVERFLOW_VISIBLE_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.359"); //$NON-NLS-1$

	String BLOCK_CONTAINER_OVERFLOW_VISIBLE_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.360"); //$NON-NLS-1$

	/**
	 * 隐藏按钮
	 */
	String BLOCK_CONTAINER_OVERFLOW_HIDDEN_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.361"); //$NON-NLS-1$

	String BLOCK_CONTAINER_OVERFLOW_HIDDEN_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.362"); //$NON-NLS-1$

	String BLOCK_CONTAINER_OVERFLOW_HIDDEN_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.363"); //$NON-NLS-1$

	/**
	 * 自动调整大小按钮
	 */
	String BLOCK_CONTAINER_AUTO_SIZE_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.364"); //$NON-NLS-1$

	String BLOCK_CONTAINER_OVERFLOW_ALLHIDDEN_BUTTON = Messages
			.getString("allhidden");

	String BLOCK_CONTAINER_CONTENT_SPECIAL_TREATMENT = Messages
			.getString("blockcontainercontentspecialtreatment");

	String CLEAR_BLOCK_CONTAINER_CONTENT_SPECIAL_TREATMENT = Messages
			.getString("clearblockcontainercontentspecialtreatment");

	String BLOCK_CONTAINER_AUTO_SIZE_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.365"); //$NON-NLS-1$

	String BLOCK_CONTAINER_OVERFLOW_ALLHIDDEN_BUTTON_DESCRIPTION = Messages
			.getString("allhiddendescription");
	String BLOCK_CONTAINER_AUTOFONTSIZE_BUTTON = Messages
			.getString("block.container.autofontsize.button");
	String BLOCK_CONTAINER_AUTOFONTSIZE_TITLE = Messages
			.getString("block.container.autofontsize.title");
	String BLOCK_CONTAINER_AUTOFONTSIZE_DESCRIPT = Messages
			.getString("block.container.autofontsize.descript");
	String HAVE_POSITION = Messages.getString("havaposition");

	String NO_POSITION = Messages.getString("noposition");

	String CONTENTHIDDEN_IF_OBJECTHIDDEN = Messages
			.getString("contenthiddenifobjecthidden");

	String BLOCK_CONTAINER_AUTO_SIZE_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.366"); //$NON-NLS-1$

	/********** 块容器溢出处理面板**********结束 ***************/

	/********** 高级块容器 task group**************结束 *****************/

	/********** 右键菜单**************开始 ****************/

	String EDITMODE = Messages.getString("wsd.view.gui.ribbon.367"); //$NON-NLS-1$

	/********** 右键菜单**************结束 ****************/

	/********** 组属性**************开始 ****************/

	String GROUP = Messages.getString("wsd.view.gui.ribbon.368"); //$NON-NLS-1$

	String SET_GROUP = Messages.getString("wsd.view.gui.ribbon.369"); //$NON-NLS-1$

	String REMOVE_GROUP = Messages.getString("wsd.view.gui.ribbon.370"); //$NON-NLS-1$

	String CURRENT_INLINE = Messages.getString("wsd.view.gui.ribbon.371"); //$NON-NLS-1$

	String CURRENT_BLOCK = Messages.getString("wsd.view.gui.ribbon.372"); //$NON-NLS-1$

	String CURRENT_OBJECT = Messages.getString("wsd.view.gui.ribbon.373"); //$NON-NLS-1$

	String CURRENT_PAGE_SEQUENCE = Messages
			.getString("wsd.view.gui.ribbon.374"); //$NON-NLS-1$

	String CURRENT_WISEDOCUMENT = Messages.getString("wsd.view.gui.ribbon.375"); //$NON-NLS-1$

	String CURRENT_GROUP = Messages.getString("wsd.view.gui.ribbon.376"); //$NON-NLS-1$

	String DYNAMIC_STYLE = Messages.getString("wsd.view.gui.ribbon.377"); //$NON-NLS-1$

	String SET_DYNAMIC_STYLE = Messages.getString("wsd.view.gui.ribbon.378"); //$NON-NLS-1$

	String REMOVE_DYNAMIC_STYLE = Messages.getString("wsd.view.gui.ribbon.379"); //$NON-NLS-1$

	String REMOVE_BAND = Messages.getString("wsd.view.gui.ribbon.app.13"); //$NON-NLS-1$

	// GroupBand
	String GROUP_BAND = Messages.getString("RibbonUIText.33"); //$NON-NLS-1$

	String GROUP_SET_BUTTON = Messages.getString("wsd.view.gui.ribbon.app.15"); //$NON-NLS-1$

	String GROUP_CANCEL_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.app.16"); //$NON-NLS-1$

	/********** 组属性**************结束 ****************/

	/********** 章节属性**************开始 ****************/
	String PAGE_SEQUENCE_TASK = Messages.getString("wsd.view.gui.ribbon.380"); //$NON-NLS-1$

	String PAGE_SEQUENCE_BAND = Messages.getString("wsd.view.gui.ribbon.381"); //$NON-NLS-1$

	String PAGE_SEQUENCE_BAND_ONE = Messages
			.getString("wsd.view.gui.ribbon.382"); //$NON-NLS-1$

	String PAGE_SEQUENCE_BAND_AUTO = Messages
			.getString("wsd.view.gui.ribbon.383"); //$NON-NLS-1$

	String PAGE_SEQUENCE_BAND_ODD = Messages
			.getString("wsd.view.gui.ribbon.384"); //$NON-NLS-1$

	String PAGE_SEQUENCE_BAND_EVEN = Messages
			.getString("wsd.view.gui.ribbon.385"); //$NON-NLS-1$

	String PAGE_SEQUENCE_BAND_NUMBER = Messages
			.getString("wsd.view.gui.ribbon.386"); //$NON-NLS-1$

	String PAGE_SEQUENCE_BAND_ALL_ODD = Messages
			.getString("wsd.view.gui.ribbon.387"); //$NON-NLS-1$

	String PAGE_SEQUENCE_BAND_ALL_EVEN = Messages
			.getString("wsd.view.gui.ribbon.388"); //$NON-NLS-1$

	String PAGE_SEQUENCE_BAND_END_ODD = Messages
			.getString("wsd.view.gui.ribbon.389"); //$NON-NLS-1$

	String PAGE_SEQUENCE_BAND_END_EVEN = Messages
			.getString("wsd.view.gui.ribbon.390"); //$NON-NLS-1$

	String PAGE_SEQUENCE_BAND_NO_LIMIT = Messages
			.getString("wsd.view.gui.ribbon.391"); //$NON-NLS-1$

	String PAGE_SEQUENCE_BAND_ALB = Messages
			.getString("wsd.view.gui.ribbon.392"); //$NON-NLS-1$

	String PAGE_SEQUENCE_BAND_ROMAN = Messages
			.getString("wsd.view.gui.ribbon.393"); //$NON-NLS-1$

	String PAGE_SEQUENCE_BAND_START_PAGE_NUMBER = Messages
			.getString("wsd.view.gui.ribbon.394"); //$NON-NLS-1$

	String PAGE_SEQUENCE_BAND_PAGE_LIMIT = Messages
			.getString("wsd.view.gui.ribbon.395"); //$NON-NLS-1$

	String PAGE_SEQUENCE_BAND_PAGE_TYPE = Messages
			.getString("wsd.view.gui.ribbon.396"); //$NON-NLS-1$

	String PAGE_SEQUENCE_GROUP = Messages.getString("wsd.view.gui.ribbon.397"); //$NON-NLS-1$

	String PAGE_SEQUENCE_GROUP_SET = Messages
			.getString("wsd.view.gui.ribbon.398"); //$NON-NLS-1$

	String PAGE_SEQUENCE_GROUP_CLEAR = Messages
			.getString("wsd.view.gui.ribbon.399"); //$NON-NLS-1$

	String DELETE_PAGE_SEQUENCE_BAND = Messages
	.getString("wsd.view.gui.ribbon.pagesequence.delete.band"); 
	String DELETE_PAGE_SEQUENCE_BUTTON = Messages
	.getString("wsd.view.gui.ribbon.pagesequence.delete.button"); 
	/********** 章节属性**************结束 ****************/

	/********** 图片工具属性 *********** 开始 **************/
	// ExternalGraphicTask系列属性
	String PICTURE_TASK_TITLE = Messages.getString("wsd.view.gui.ribbon.400"); //$NON-NLS-1$

	String PICTURE_TASK = Messages.getString("wsd.view.gui.ribbon.401"); //$NON-NLS-1$

	String PICTURE_SET = Messages.getString("wsd.view.gui.ribbon.402"); //$NON-NLS-1$

	String PICTURE_SOURCE_TYPE_TITLE = Messages
			.getString("wsd.view.gui.ribbon.403"); //$NON-NLS-1$

	String[] PICTURE_SOURCE_TYPE_LIST =
	{
			Messages.getString("wsd.view.gui.ribbon.404"), Messages.getString("wsd.view.gui.ribbon.405") }; //$NON-NLS-1$ //$NON-NLS-2$

	String PICTURE_LAYER = Messages.getString("wsd.view.gui.ribbon.406"); //$NON-NLS-1$

	String PICTURE_TRANSPARENCY = Messages.getString("wsd.view.gui.ribbon.407"); //$NON-NLS-1$

	String PICTURE_SIZE_BAND_TITLE = Messages
			.getString("wsd.view.gui.ribbon.408"); //$NON-NLS-1$

	String PICTURE_SIZE_TYPE = Messages.getString("wsd.view.gui.ribbon.409"); //$NON-NLS-1$

	String[] PICTURE_SIZE_TYPE_LIST =
	{
			Messages.getString("wsd.view.gui.ribbon.410"), Messages.getString("wsd.view.gui.ribbon.411") }; //$NON-NLS-1$ //$NON-NLS-2$

	String PICTURE_FIX_ASPECT_RATIO = Messages
			.getString("wsd.view.gui.ribbon.412"); //$NON-NLS-1$

	String PICTURE_SIZE_HEIGHT = Messages.getString("wsd.view.gui.ribbon.413"); //$NON-NLS-1$

	String PICTURE_SIZE_WIDTH = Messages.getString("wsd.view.gui.ribbon.414"); //$NON-NLS-1$

	String PICTURE_BORDER_CONTENT_BAND_TITLE = Messages
			.getString("wsd.view.gui.ribbon.415"); //$NON-NLS-1$

	String PICTURE_CHANGE_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.416"); //$NON-NLS-1$

	String PICTURE_CHANGE_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.417"); //$NON-NLS-1$

	String PICTURE_CHANGE_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.418"); //$NON-NLS-1$

	String PICTURE_BORDER_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.419"); //$NON-NLS-1$

	String PICTURE_BORDER_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.420"); //$NON-NLS-1$

	String PICTURE_BORDER_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.421"); //$NON-NLS-1$

	String PICTURE_RESET_BUTTON = Messages.getString("wsd.view.gui.ribbon.422"); //$NON-NLS-1$

	/********** 图片工具属性 *********** 结束 **************/

	/********** 页眉属性 *********** 开始 **************/
	// RegionBeforeTask系列属性
	String REGION_BEFORE_TASK = Messages.getString("wsd.view.gui.ribbon.423"); //$NON-NLS-1$

	String REGION_BEFORE_BAND = Messages.getString("wsd.view.gui.ribbon.424"); //$NON-NLS-1$

	String REGION_BEFORE_HEIGHT_TITLE = Messages
			.getString("wsd.view.gui.ribbon.425"); //$NON-NLS-1$

	String REGION_BEFORE_HEIGHT_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.426"); //$NON-NLS-1$

	String REGION_BEFORE_PRECEDENCE = Messages
			.getString("wsd.view.gui.ribbon.427"); //$NON-NLS-1$

	String REGION_BEFORE_PROPERTY_BAND = Messages
			.getString("wsd.view.gui.ribbon.428"); //$NON-NLS-1$

	String REGION_BEFORE_WRITING_MODE_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.429"); //$NON-NLS-1$

	String REGION_BEFORE_REFERENCE_ORIENTATION_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.430"); //$NON-NLS-1$

	String REGION_BEFORE_REFERENCE_ORIENTATION_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.431"); //$NON-NLS-1$

	String REGION_BEFORE_REFERENCE_ORIENTATION_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.432"); //$NON-NLS-1$

	String REGION_BEFORE_OVER_FLOW_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.433"); //$NON-NLS-1$

	String REGION_BEFORE_OVER_FLOW_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.434"); //$NON-NLS-1$

	String REGION_BEFORE_OVER_FLOW_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.435"); //$NON-NLS-1$

	/********** 页眉属性 *********** 结束 **************/

	/********** 页脚属性 *********** 开始 **************/
	// RegionAfterTask系列属性
	String REGION_AFTER_TASK = Messages.getString("wsd.view.gui.ribbon.436"); //$NON-NLS-1$

	String REGION_AFTER_BAND = Messages.getString("wsd.view.gui.ribbon.437"); //$NON-NLS-1$

	String REGION_AFTER_HEIGHT_TITLE = Messages
			.getString("wsd.view.gui.ribbon.438"); //$NON-NLS-1$

	String REGION_AFTER_HEIGHT_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.439"); //$NON-NLS-1$

	String REGION_AFTER_PRECEDENCE = Messages
			.getString("wsd.view.gui.ribbon.440"); //$NON-NLS-1$

	String REGION_AFTER_PROPERTY_BAND = Messages
			.getString("wsd.view.gui.ribbon.441"); //$NON-NLS-1$

	String REGION_AFTER_WRITING_MODE_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.442"); //$NON-NLS-1$

	String REGION_AFTER_DISPLAY_ALIGN_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.443"); //$NON-NLS-1$

	String REGION_AFTER_REFERENCE_ORIENTATION_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.444"); //$NON-NLS-1$

	String REGION_AFTER_REFERENCE_ORIENTATION_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.445"); //$NON-NLS-1$

	String REGION_AFTER_REFERENCE_ORIENTATION_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.446"); //$NON-NLS-1$

	String REGION_AFTER_OVER_FLOW_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.447"); //$NON-NLS-1$

	String REGION_AFTER_OVER_FLOW_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.448"); //$NON-NLS-1$

	String REGION_AFTER_OVER_FLOW_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.449"); //$NON-NLS-1$

	/********** 页脚属性 *********** 结束 **************/

	/********** 左区域属性 *********** 开始 **************/
	// RegionAfterTask系列属性
	String REGION_START_TASK = Messages.getString("wsd.view.gui.ribbon.450"); //$NON-NLS-1$

	String REGION_START_BAND = Messages.getString("wsd.view.gui.ribbon.451"); //$NON-NLS-1$

	String REGION_START_HEIGHT_TITLE = Messages
			.getString("wsd.view.gui.ribbon.452"); //$NON-NLS-1$

	String REGION_START_PROPERTY_BAND = Messages
			.getString("wsd.view.gui.ribbon.454"); //$NON-NLS-1$

	String REGION_START_WRITING_MODE_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.455"); //$NON-NLS-1$

	String REGION_START_REFERENCE_ORIENTATION_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.456"); //$NON-NLS-1$

	String REGION_START_REFERENCE_ORIENTATION_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.457"); //$NON-NLS-1$

	String REGION_START_REFERENCE_ORIENTATION_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.458"); //$NON-NLS-1$

	String REGION_START_DISPLAY_ALIGN_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.459"); //$NON-NLS-1$

	String REGION_START_OVER_FLOW_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.460"); //$NON-NLS-1$

	String REGION_START_OVER_FLOW_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.461"); //$NON-NLS-1$

	String REGION_START_OVER_FLOW_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.462"); //$NON-NLS-1$

	/********** 左区域属性 *********** 结束 **************/

	/********** 右区域属性 *********** 开始 **************/
	// RegionAfterTask系列属性
	String REGION_END_TASK = Messages.getString("wsd.view.gui.ribbon.463"); //$NON-NLS-1$

	String REGION_END_BAND = Messages.getString("wsd.view.gui.ribbon.464"); //$NON-NLS-1$

	String REGION_END_HEIGHT_TITLE = Messages
			.getString("wsd.view.gui.ribbon.465"); //$NON-NLS-1$

	String REGION_END_HEIGHT_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.466"); //$NON-NLS-1$

	String REGION_END_PROPERTY_BAND = Messages
			.getString("wsd.view.gui.ribbon.467"); //$NON-NLS-1$

	String REGION_END_WRITING_MODE_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.468"); //$NON-NLS-1$

	String REGION_END_REFERENCE_ORIENTATION_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.469"); //$NON-NLS-1$

	String REGION_END_REFERENCE_ORIENTATION_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.470"); //$NON-NLS-1$

	String REGION_END_REFERENCE_ORIENTATION_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.471"); //$NON-NLS-1$

	String REGION_END_DISPLAY_ALIGN_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.472"); //$NON-NLS-1$

	String REGION_END_OVER_FLOW_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.473"); //$NON-NLS-1$

	String REGION_END_OVER_FLOW_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.474"); //$NON-NLS-1$

	String REGION_END_OVER_FLOW_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.475"); //$NON-NLS-1$

	/********** 右区域属性 *********** 结束 **************/

	/********** SVG图形属性 *********** 开始 **************/
	// GraphStyleTask系列文字
	String SVG_GRAPH_TASK = Messages.getString("wsd.view.gui.ribbon.476"); //$NON-NLS-1$

	String SVG_GRAPH_STYLE = Messages.getString("wsd.view.gui.ribbon.477"); //$NON-NLS-1$

	String SVG_GRAPH_LINE_STYLE_BAND = Messages
			.getString("wsd.view.gui.ribbon.478"); //$NON-NLS-1$

	String SVG_GRAPH_FILL_BAND = Messages.getString("svg.graph.fill.band");

	String SVG_GRAPH_LINE_STYLE = Messages.getString("svg.grapg.line.style");

	String SVG_GRAPH_LINE_WIDTH_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.479"); //$NON-NLS-1$

	String SVG_GRAPH_LINE_WIDTH_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.480"); //$NON-NLS-1$

	String SVG_GRAPH_LINE_WIDTH_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.481"); //$NON-NLS-1$

	String[] SVG_GRAPH_LINE_STYLE_LIST =
	{
			Messages.getString("wsd.view.gui.ribbon.482"), Messages.getString("wsd.view.gui.ribbon.483"), Messages.getString("wsd.view.gui.ribbon.484"), Messages.getString("wsd.view.gui.ribbon.485") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

	String SVG_GRAPH_LINE_STYLE_TITLE = Messages
			.getString("wsd.view.gui.ribbon.486"); //$NON-NLS-1$

	String SVG_GRAPH_LINE_STYLE_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.487"); //$NON-NLS-1$

	String SVG_GRAPH_LINE_COLOR_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.488"); //$NON-NLS-1$

	String SVG_GRAPH_LINE_COLOR_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.489"); //$NON-NLS-1$

	String SVG_GRAPH_LINE_COLOR_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.490"); //$NON-NLS-1$

	String SVG_GRAPH_SET_BAND = Messages.getString("wsd.view.gui.ribbon.491"); //$NON-NLS-1$

	String SVG_GRAPH_ROTATION = Messages.getString("wsd.view.gui.ribbon.492"); //$NON-NLS-1$

	String SVG_GRAPH_LAYER = Messages.getString("wsd.view.gui.ribbon.493"); //$NON-NLS-1$

	String SVG_GRAPH_TRANSPARENCY = Messages
			.getString("wsd.view.gui.ribbon.494"); //$NON-NLS-1$

	String SVG_GRAPH_ARROW_STYLE_BAND = Messages
			.getString("wsd.view.gui.ribbon.495"); //$NON-NLS-1$

	String[] SVG_GRAPH_ARROW_STYLE_LIST =
	{
			Messages.getString("wsd.view.gui.ribbon.496"), Messages.getString("wsd.view.gui.ribbon.497"), Messages.getString("wsd.view.gui.ribbon.498"), Messages.getString("wsd.view.gui.ribbon.499"), Messages.getString("wsd.view.gui.ribbon.500"), Messages.getString("wsd.view.gui.ribbon.501"), Messages.getString("wsd.view.gui.ribbon.502") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$

	String SVG_GRAPH_ARROW_START_STYLE_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.503"); //$NON-NLS-1$

	String SVG_GRAPH_ARROW_START_STYLE_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.504"); //$NON-NLS-1$

	String SVG_GRAPH_ARROW_START_STYLE_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.505"); //$NON-NLS-1$

	String SVG_GRAPH_ARROW_END_STYLE_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.506"); //$NON-NLS-1$

	String SVG_GRAPH_ARROW_END_STYLE_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.507"); //$NON-NLS-1$

	String SVG_GRAPH_ARROW_END_STYLE_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.508"); //$NON-NLS-1$

	String SVG_GRAPH_STYLE_BAND_TITLE = Messages
			.getString("wsd.view.gui.ribbon.509"); //$NON-NLS-1$

	String SVG_GRAPH_TOOL_BAND_TITLE = Messages
			.getString("svg.graph.tool.band.title");

	String SVG_GRAPH_DISPLAY_ALIGN_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.510"); //$NON-NLS-1$

	String SVG_GRAPH_DISPLAY_ALIGN_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.511"); //$NON-NLS-1$

	String SVG_GRAPH_DISPLAY_ALIGN_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.512"); //$NON-NLS-1$

	String[] SVG_GRAPH_DISPLAY_ALIGN_LIST =
	{
			CELL_DISPLAY_ALIGN_MENU[0],
			CELL_DISPLAY_ALIGN_MENU[1],
			CELL_DISPLAY_ALIGN_MENU[2],
			Messages.getString("wsd.view.gui.ribbon.513"), Messages.getString("wsd.view.gui.ribbon.514"), Messages.getString("wsd.view.gui.ribbon.515") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	String SVG_GRAPH_EQUAL_SIZE_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.516"); //$NON-NLS-1$

	String SVG_GRAPH_EQUAL_SIZE_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.517"); //$NON-NLS-1$

	String SVG_GRAPH_EQUAL_SIZE_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.518"); //$NON-NLS-1$

	String[] SVG_GRAPH_EQUAL_LIST =
	{
			Messages.getString("wsd.view.gui.ribbon.519"), Messages.getString("wsd.view.gui.ribbon.520") }; //$NON-NLS-1$ //$NON-NLS-2$

	String[] SVG_GRAPH_ARROWHEAD_STYLE_LIST =
	{
			Messages.getString("wsd.view.gui.ribbon.521"), Messages.getString("wsd.view.gui.ribbon.522") }; //$NON-NLS-1$ //$NON-NLS-2$

	String[] SVG_GRAPH_ARROWHEAD_POSITION_LIST =
	{
			Messages.getString("wsd.view.gui.ribbon.523"), Messages.getString("wsd.view.gui.ribbon.524"), Messages.getString("wsd.view.gui.ribbon.525") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	String SVG_GRAPH_LINE_JOIN_STROKE_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.526"); //$NON-NLS-1$

	String SVG_GRAPH_LINE_JOIN_STROKE_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.527"); //$NON-NLS-1$

	String SVG_GRAPH_LINE_JOIN_STROKE_BUTTON_DESCRPITION = Messages
			.getString("wsd.view.gui.ribbon.528"); //$NON-NLS-1$

	String[] SVG_GRAPH_LINE_JOIN_STROKE_LIST =
	{
			Messages.getString("wsd.view.gui.ribbon.529"), Messages.getString("wsd.view.gui.ribbon.530"), Messages.getString("wsd.view.gui.ribbon.531") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	String SVG_GRAPH_BACKGROUND_COLOR_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.532"); //$NON-NLS-1$

	String SVG_GRAPH_BACKGROUND_COLOR_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.533"); //$NON-NLS-1$

	String SVG_GRAPH_BACKGROUND_COLOR_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.534"); //$NON-NLS-1$

	// GraphRectangleStyle.java
	String SVG_GRAPH_RECTANGLE_STYLE_BAND = Messages
			.getString("wsd.view.gui.ribbon.535"); //$NON-NLS-1$

	String SVG_GRAPH_RECTANGLE_STYLE_GROUP = Messages
			.getString("wsd.view.gui.ribbon.536"); //$NON-NLS-1$

	String SVG_GRAPH_RECTANGLE_STYLE_RX_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.537"); //$NON-NLS-1$

	String SVG_GRAPH_RECTANGLE_STYLE_RX_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.538"); //$NON-NLS-1$

	String SVG_GRAPH_RECTANGLE_STYLE_RX_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.539"); //$NON-NLS-1$

	String SVG_GRAPH_RECTANGLE_STYLE_RY_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.540"); //$NON-NLS-1$

	String SVG_GRAPH_RECTANGLE_STYLE_RY_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.541"); //$NON-NLS-1$

	String SVG_GRAPH_RECTANGLE_STYLE_RY_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.542"); //$NON-NLS-1$

	// GraphTextBand
	String SVG_GRAPH_TEXT_BAND_TITLE = Messages
			.getString("wsd.view.gui.ribbon.543"); //$NON-NLS-1$

	String SVG_GRAPH_TEXT_LABEL = Messages.getString("wsd.view.gui.ribbon.544"); //$NON-NLS-1$

	String SVG_GRAPH_TEXT_INPUT_FIELD = Messages
			.getString("wsd.view.gui.ribbon.545"); //$NON-NLS-1$

	String SVG_GRAPH_TEXT_NODE_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.546"); //$NON-NLS-1$

	String SVG_GRAPH_TEXT_NODE_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.547"); //$NON-NLS-1$

	String SVG_GRAPH_TEXT_NODE_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.548"); //$NON-NLS-1$

	// CanvasSizeBand
	String SVG_GRAPH_CANVAS_BAND_TITLE = Messages
			.getString("wsd.view.gui.ribbon.549"); //$NON-NLS-1$

	String SVG_GRAPH_CANVAS_BAND_GROUP = Messages
			.getString("wsd.view.gui.ribbon.550"); //$NON-NLS-1$

	String SVG_GRAPH_CANVAS_HEIGHT_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.551"); //$NON-NLS-1$

	String SVG_GRAPH_CANVAS_HEIGHT_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.552"); //$NON-NLS-1$

	String SVG_GRAPH_CANVAS_HEIGHT_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.553"); //$NON-NLS-1$

	String SVG_GRAPH_CANVAS_WIDTH_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.554"); //$NON-NLS-1$

	String SVG_GRAPH_CANVAS_WIDTH_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.555"); //$NON-NLS-1$

	String SVG_GRAPH_CANVAS_WIDTH_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.556"); //$NON-NLS-1$

	// GraphSizeBand

	String SVG_GRAPH_SIZE_BAND_TITLE = Messages
			.getString("wsd.view.gui.ribbon.557"); //$NON-NLS-1$

	String SVG_GRAPH_POSITION_GROUP = Messages
			.getString("wsd.view.gui.ribbon.558"); //$NON-NLS-1$

	String SVG_GRAPH_START_POINT_X_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.559"); //$NON-NLS-1$

	String SVG_GRAPH_START_POINT_X_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.560"); //$NON-NLS-1$

	String SVG_GRAPH_START_POINT_X_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.561"); //$NON-NLS-1$

	String SVG_GRAPH_START_POINT_Y_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.562"); //$NON-NLS-1$

	String SVG_GRAPH_START_POINT_Y_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.563"); //$NON-NLS-1$

	String SVG_GRAPH_START_POINT_Y_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.564"); //$NON-NLS-1$

	String SVG_GRAPH_SIZE_GROUP = Messages.getString("wsd.view.gui.ribbon.565"); //$NON-NLS-1$

	String SVG_GRAPH_HEIGHT_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.566"); //$NON-NLS-1$

	String SVG_GRAPH_HEIGHT_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.567"); //$NON-NLS-1$

	String SVG_GRAPH_HEIGHT_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.568"); //$NON-NLS-1$

	String SVG_GRAPH_WIDTH_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.569"); //$NON-NLS-1$

	String SVG_GRAPH_WIDTH_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.570"); //$NON-NLS-1$

	String SVG_GRAPH_WIDTH_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.571"); //$NON-NLS-1$

	/********** SVG图形属性 *********** 结束 **************/

	/********** 格式化日期属性 *********** 开始 **************/

	String DATE_FORMAT_TASK = Messages.getString("wsd.view.gui.ribbon.572"); //$NON-NLS-1$

	String DATE_FORMAT_TASK_TITLE = Messages
			.getString("wsd.view.gui.ribbon.573"); //$NON-NLS-1$

	// DateTimeTypeSetBand
	String DATE_TYPE_BAND = Messages.getString("wsd.view.gui.ribbon.574"); //$NON-NLS-1$

	String DATE_TYPE_ONLY_DATE_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.575"); //$NON-NLS-1$

	String DATE_TYPE_ONLY_DATE_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.576"); //$NON-NLS-1$

	String DATE_TYPE_ONLY_DATE_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.577"); //$NON-NLS-1$

	String DATE_TYPE_ONLY_TIME_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.578"); //$NON-NLS-1$

	String DATE_TYPE_ONLY_TIME_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.579"); //$NON-NLS-1$

	String DATE_TYPE_ONLY_TIME_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.580"); //$NON-NLS-1$

	String DATE_TIME_TYPE_BUTTON = Messages
			.getString("wsd.view.gui.ribbon.581"); //$NON-NLS-1$

	String DATE_TIME_TYPE_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.582"); //$NON-NLS-1$

	String DATE_TIME_TYPE_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.583"); //$NON-NLS-1$

	// InputSetBand

	String DATE_TIME_INPUT_BAND = Messages.getString("wsd.view.gui.ribbon.584"); //$NON-NLS-1$

	// DateTimeFormatSetPanel
	String[] DATE_TIME_ITEMS =
	{
			Messages.getString("RibbonUI.datetime.item.list.none"), //$NON-NLS-1$
			Messages.getString("wsd.view.gui.ribbon.585"), Messages.getString("wsd.view.gui.ribbon.586"), Messages.getString("wsd.view.gui.ribbon.587"), Messages.getString("wsd.view.gui.ribbon.588"), Messages.getString("wsd.view.gui.ribbon.589"), Messages.getString("wsd.view.gui.ribbon.590"), Messages.getString("wsd.view.gui.ribbon.591"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
			Messages.getString("wsd.view.gui.ribbon.592"), Messages.getString("wsd.view.gui.ribbon.593") }; //$NON-NLS-1$ //$NON-NLS-2$

	String[] DATE_TIME_VALUES =
	{
			Messages.getString("RibbonUI.datetime.item.list.none"), //$NON-NLS-1$
			Messages.getString("wsd.view.gui.ribbon.594"), Messages.getString("wsd.view.gui.ribbon.595"), Messages.getString("wsd.view.gui.ribbon.596"), Messages.getString("wsd.view.gui.ribbon.597"), Messages.getString("wsd.view.gui.ribbon.598"), Messages.getString("wsd.view.gui.ribbon.599"), Messages.getString("wsd.view.gui.ribbon.600"), Messages.getString("wsd.view.gui.ribbon.601"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
			Messages.getString("wsd.view.gui.ribbon.602") }; //$NON-NLS-1$

	String[] DATE_TIME_ITEMS_LIST =
	{
			Messages.getString("RibbonUI.datetime.item.list.none"), //$NON-NLS-1$
			Messages.getString("wsd.view.gui.ribbon.603"), Messages.getString("wsd.view.gui.ribbon.604"), Messages.getString("wsd.view.gui.ribbon.605"), Messages.getString("wsd.view.gui.ribbon.606"), Messages.getString("wsd.view.gui.ribbon.607"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
			Messages.getString("wsd.view.gui.ribbon.608"), //$NON-NLS-1$
			Messages.getString(Messages.getString("RibbonUIText.34")), //$NON-NLS-1$
			Messages.getString("wsd.view.gui.ribbon.610"), Messages.getString("wsd.view.gui.ribbon.611"), Messages.getString("wsd.view.gui.ribbon.612"), Messages.getString("wsd.view.gui.ribbon.613"), Messages.getString("wsd.view.gui.ribbon.614"), Messages.getString("wsd.view.gui.ribbon.615"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
			Messages.getString("wsd.view.gui.ribbon.616"), Messages.getString("wsd.view.gui.ribbon.617"), Messages.getString("wsd.view.gui.ribbon.618"), Messages.getString("wsd.view.gui.ribbon.619"), Messages.getString("wsd.view.gui.ribbon.620") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

	String[] DATE_TIME_VALUE_LIST =
	{
			Messages.getString("RibbonUI.datetime.item.list.none"), //$NON-NLS-1$
			Messages.getString("wsd.view.gui.ribbon.621"), Messages.getString("wsd.view.gui.ribbon.622"), Messages.getString("wsd.view.gui.ribbon.623"), Messages.getString("wsd.view.gui.ribbon.624"), Messages.getString("wsd.view.gui.ribbon.625"), Messages.getString("wsd.view.gui.ribbon.626"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
			Messages.getString(Messages.getString("RibbonUIText.35")), //$NON-NLS-1$
			Messages.getString("wsd.view.gui.ribbon.628"), Messages.getString("wsd.view.gui.ribbon.629"), Messages.getString("wsd.view.gui.ribbon.630"), Messages.getString("wsd.view.gui.ribbon.631"), Messages.getString("wsd.view.gui.ribbon.632"), Messages.getString("wsd.view.gui.ribbon.633"), Messages.getString("wsd.view.gui.ribbon.634"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
			Messages.getString("wsd.view.gui.ribbon.635"), Messages.getString("wsd.view.gui.ribbon.636"), Messages.getString("wsd.view.gui.ribbon.637"), Messages.getString("wsd.view.gui.ribbon.638") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

	String[] DATE_TIME_INPUT_ITEM_DEMO =
	{
			Messages.getString("RibbonUI.datetime.item.list.none"), //$NON-NLS-1$
			Messages.getString("wsd.view.gui.ribbon.639"), Messages.getString("wsd.view.gui.ribbon.640"), Messages.getString("wsd.view.gui.ribbon.641"), Messages.getString("wsd.view.gui.ribbon.642"), Messages.getString("wsd.view.gui.ribbon.643"), Messages.getString("wsd.view.gui.ribbon.644"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
			Messages.getString("wsd.view.gui.ribbon.645"), Messages.getString("wsd.view.gui.ribbon.646"), Messages.getString("wsd.view.gui.ribbon.647"), Messages.getString("wsd.view.gui.ribbon.648"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			Messages.getString("wsd.view.gui.ribbon.649"), Messages.getString("wsd.view.gui.ribbon.650") }; //$NON-NLS-1$ //$NON-NLS-2$

	String[] DATE_TIME_IPNUT_VALUE_DEMO =
	{
			Messages.getString("RibbonUI.datetime.item.list.none"), //$NON-NLS-1$
			Messages.getString("wsd.view.gui.ribbon.651"), Messages.getString("wsd.view.gui.ribbon.652"), Messages.getString("wsd.view.gui.ribbon.653"), Messages.getString("wsd.view.gui.ribbon.654"), Messages.getString("wsd.view.gui.ribbon.655"), Messages.getString("wsd.view.gui.ribbon.656"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
			Messages.getString("wsd.view.gui.ribbon.657"), Messages.getString("wsd.view.gui.ribbon.658"), Messages.getString("wsd.view.gui.ribbon.659"), Messages.getString("wsd.view.gui.ribbon.660"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			Messages.getString("wsd.view.gui.ribbon.661"), Messages.getString("wsd.view.gui.ribbon.662") }; //$NON-NLS-1$ //$NON-NLS-2$

	String DATE_TIME_DATE_TITLE = Messages.getString("wsd.view.gui.ribbon.663"); //$NON-NLS-1$

	String DATE_TIME_SPERATE_TITLE = Messages
			.getString("wsd.view.gui.ribbon.664"); //$NON-NLS-1$

	String DATE_TIME_TIME_TITLE = Messages.getString("wsd.view.gui.ribbon.665"); //$NON-NLS-1$

	// OutputSetBand
	String DATE_TIME_OUTPUT_BAND = Messages
			.getString("wsd.view.gui.ribbon.666"); //$NON-NLS-1$

	/********** 格式化日期属性 *********** 结束 **************/

	/********** 格式化数字属性 *********** 开始 **************/

	String NUMBER_FORMAT_TASK = Messages.getString("wsd.view.gui.ribbon.667"); //$NON-NLS-1$

	String NUMBER_FORMAT_TASK_TITLE = Messages
			.getString("wsd.view.gui.ribbon.668"); //$NON-NLS-1$

	// NumberFormatSetBand
	String NUMBER_FORMAT_TITLE = Messages.getString("wsd.view.gui.ribbon.669"); //$NON-NLS-1$

	String NUMBER_FORMAT_CH_TITLE = Messages
			.getString("wsd.view.gui.ribbon.670"); //$NON-NLS-1$

	String NUMBER_FORMAT_DECIMAL = Messages
			.getString("wsd.view.gui.ribbon.671"); //$NON-NLS-1$

	String NUMBER_FORMAT_THOUSAND_LABEL = Messages
			.getString("wsd.view.gui.ribbon.672"); //$NON-NLS-1$

	String NUMBER_FORMAT_BAIFENSHU_LABEL = Messages
			.getString("wsd.view.gui.ribbon.673"); //$NON-NLS-1$

	/********** 格式化数字属性 *********** 结束 **************/

	/********** 选择框属性 *********** 开始 **************/
	String CHECKBOX_TASKGROUP = Messages
			.getString("wsd.view.gui.ribbon.checkbox.taskgroup");

	String CHECKBOX_TASK = Messages
			.getString("wsd.view.gui.ribbon.checkbox.task");

	String CHECKBOX_STYLE_BAND = Messages
			.getString("wsd.view.gui.ribbon.checkbox.style.band");

	String CHECKBOX_BUTTONGROUP = Messages
			.getString("wsd.view.gui.ribbon.checkbox.buttongroup");

	String CHECKBOX_BUTTONGROUP_CREATE = Messages
			.getString("wsd.view.gui.ribbon.checkbox.buttongroup.create");

	String CHECKBOX_BUTTONGROUP_CREATE_DIALOG = Messages
			.getString("wsd.view.gui.ribbon.checkbox.buttongroup.create.dialog");

	String CHECKBOX_BUTTONGROUP_SET = Messages
			.getString("wsd.view.gui.ribbon.checkbox.buttongroup.set");

	String CHECHBOX_SELECTVALUE = Messages
			.getString("wsd.view.gui.ribbon.edit.checkbox.selectvalue");

	String CHECHBOX_UNSELECTVALUE = Messages
			.getString("wsd.view.gui.ribbon.edit.checkbox.unselectvalue");

	String CHECHBOX_SELECTED = Messages
			.getString("wsd.view.gui.ribbon.edit.checkbox.isselected");

	String CHECHBOX_BOXSTYLE = Messages
			.getString("wsd.view.gui.ribbon.edit.checkbox.boxstyle");

	String CHECHBOX_BOXSTYLE_CIRCLE = Messages
			.getString("wsd.view.gui.ribbon.edit.checkbox.boxstyle.circle");

	String CHECHBOX_BOXSTYLE_SQUARE = Messages
			.getString("wsd.view.gui.ribbon.edit.checkbox.boxstyle.square");

	String CHECHBOX_TICKMARK = Messages
			.getString("wsd.view.gui.ribbon.edit.checkbox.tickmark");

	String CHECHBOX_TICKMARK_TICK = Messages
			.getString("wsd.view.gui.ribbon.edit.checkbox.tickmark.tick");

	String CHECHBOX_TICKMARK_DOT = Messages
			.getString("wsd.view.gui.ribbon.edit.checkbox.tickmark.dot");

	String CHECHBOX_EDIT_BAND = Messages
			.getString("wsd.view.gui.ribbon.checkbox.edit.band");

	String CHECHBOX_EDIT_ISEDIT = Messages
			.getString("wsd.view.gui.ribbon.checkbox.edit.isedit");

	/********** 选择框属性 *********** 结束 **************/

	/********** 文档属性 *********** 开始 **************/
	// WiseDocTask
	String WISE_DOC_TASK = Messages.getString("wsd.view.gui.ribbon.674"); //$NON-NLS-1$

	// WiseDocBand
	String WISE_DOC_BAND = Messages.getString("wsd.view.gui.ribbon.675"); //$NON-NLS-1$

	String WISE_DOC_NUMBER_SPACE = Messages
			.getString("wsd.view.gui.ribbon.676"); //$NON-NLS-1$

	String WISE_DOC_SPACE_TREAT = Messages.getString("wsd.view.gui.ribbon.677"); //$NON-NLS-1$

	String WISE_DOC_LINE_FEED = Messages.getString("wsd.view.gui.ribbon.678"); //$NON-NLS-1$

	String WISE_DOC_ALL = Messages.getString("wsd.view.gui.ribbon.679"); //$NON-NLS-1$

	String WISE_DOC_ONE = Messages.getString("wsd.view.gui.ribbon.680"); //$NON-NLS-1$

	String WISE_DOC_IGNORE = Messages.getString("wsd.view.gui.ribbon.681"); //$NON-NLS-1$

	String WISE_DOC_PRESERVE = Messages.getString("wsd.view.gui.ribbon.682"); //$NON-NLS-1$

	String WISE_DOC_IGNORE_B = Messages.getString("wsd.view.gui.ribbon.683"); //$NON-NLS-1$

	String WISE_DOC_IGNORE_A = Messages.getString("wsd.view.gui.ribbon.684"); //$NON-NLS-1$

	String WISE_DOC_IGNORE_R = Messages.getString("wsd.view.gui.ribbon.685"); //$NON-NLS-1$

	String WISE_DOC_SPACE = Messages.getString("wsd.view.gui.ribbon.686"); //$NON-NLS-1$

	// EditBand
	String WISE_DOC_EDIT_BAND = Messages.getString("wsd.view.gui.ribbon.687"); //$NON-NLS-1$

	String WISE_DOC_CAN_EDIT = Messages.getString("wsd.view.gui.ribbon.689"); //$NON-NLS-1$

	String WISE_DOC_CANNOT_EDIT = Messages.getString("wsd.view.gui.ribbon.690"); //$NON-NLS-1$

	String WISE_DOC_IS_STANDARD = Messages.getString("RibbonUIText.36"); //$NON-NLS-1$

	String WISE_DOC_STANDARD = Messages.getString("RibbonUIText.37"); //$NON-NLS-1$

	String WISE_DOC_IF_STANDARD = Messages.getString("RibbonUIText.39"); //$NON-NLS-1$

	/********** 文档属性 *********** 结束 **************/

	/********** 背景图属性 *********** 开始 **************/
	// BackGroundImageList
	String BACKGROUND_CHOOSE_PICTURE = Messages
			.getString("wsd.view.gui.ribbon.692"); //$NON-NLS-1$

	String BACKGROUND_CLEAR_PICTURE = Messages
			.getString("wsd.view.gui.ribbon.693"); //$NON-NLS-1$

	String BACKGROUND_AND_BORDER = Messages
			.getString("wsd.view.gui.ribbon.694"); //$NON-NLS-1$

	/********** 背景图属性 *********** 结束 **************/

	/********** 显示条件属性 *********** 开始 **************/

	// ConditionTask
	String CONDITION_TASK = Messages.getString("wsd.view.gui.ribbon.695"); //$NON-NLS-1$

	// VisibleConditionBand
	String CONDITION_BAND = Messages.getString("wsd.view.gui.ribbon.696"); //$NON-NLS-1$

	String CONDITION_INLINE = Messages.getString("wsd.view.gui.ribbon.697"); //$NON-NLS-1$

	String CONDITION_BLOCK = Messages.getString("wsd.view.gui.ribbon.698"); //$NON-NLS-1$

	String CONDITION_OBJECT = Messages.getString("wsd.view.gui.ribbon.699"); //$NON-NLS-1$

	String CONDITION_PAGE_SEQUENCE = Messages
			.getString("wsd.view.gui.ribbon.700"); //$NON-NLS-1$

	// RemoveConditionBand
	String CONDITION_REMOVE_BAND = Messages
			.getString("wsd.view.gui.ribbon.701"); //$NON-NLS-1$

	String CONDITION_REMOVE_INLINE = Messages
			.getString("wsd.view.gui.ribbon.702"); //$NON-NLS-1$

	String CONDITION_REMOVE_BLOCK = Messages
			.getString("wsd.view.gui.ribbon.703"); //$NON-NLS-1$

	String CONDITION_REMOVE_OBJECT = Messages
			.getString("wsd.view.gui.ribbon.704"); //$NON-NLS-1$

	String CONDITION_REMOVE_PAGE_SEQUENCE = Messages
			.getString("wsd.view.gui.ribbon.705"); //$NON-NLS-1$

	/********** 显示条件属性 *********** 结束 **************/

	/********** 菜单 *********** 开始 **************/
	// ApplicationMenu
	String APP_NEW = Messages.getString("wsd.view.gui.ribbon.app.17"); //$NON-NLS-1$

	String APP_OPEN = Messages.getString("wsd.view.gui.ribbon.app.18"); //$NON-NLS-1$

	String APP_OPEN_GROUP = Messages.getString("wsd.view.gui.ribbon.app.19"); //$NON-NLS-1$

	String APP_MODEL_MANEGER = Messages.getString("RibbonUIText.19"); //$NON-NLS-1$

	String APP_MODEL_RECENT = Messages.getString("RibbonUIText.20"); //$NON-NLS-1$

	String APP_MODEL = Messages.getString("RibbonUIText.21"); //$NON-NLS-1$

	String APP_MODEL_OPEN = Messages.getString("RibbonUIText.22"); //$NON-NLS-1$

	String APP_MODEL_EDIT = Messages.getString("RibbonUIText.23"); //$NON-NLS-1$

	String APP_SAVE = Messages.getString("wsd.view.gui.ribbon.app.20"); //$NON-NLS-1$

	String APP_SAVE_GROUP = Messages.getString("wsd.view.gui.ribbon.app.21"); //$NON-NLS-1$

	String APP_SAVE_AS = Messages.getString("wsd.view.gui.ribbon.app.22"); //$NON-NLS-1$

	String APP_SAVE_AS_WSD = Messages.getString("wsd.view.gui.ribbon.app.23"); //$NON-NLS-1$

	String APP_SAVE_AS_WSDT = Messages.getString("RibbonUIText.26"); //$NON-NLS-1$

	String APP_SAVE_AS_WSDX = Messages.getString("saveaswsdx"); //$NON-NLS-1$

	String APP_SAVE_AS_WSDM = Messages.getString("saveaswsdm");

	String APP_SAVE_AS_WSDX_DESCRIPTION = Messages
			.getString("saveaswsdxdescription"); //$NON-NLS-1$

	String APP_SAVE_AS_WSDM_DESCRIPTION = Messages
			.getString("saveaswsdmdescription");

	String APP_SAVE_AS_WSD_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.app.24"); //$NON-NLS-1$

	String APP_SAVE_AS_WSDT_DESCRIPTION = Messages.getString("RibbonUIText.27"); //$NON-NLS-1$

	String APP_SAVE_AS_OTHER_FORMAT = Messages
			.getString("wsd.view.gui.ribbon.app.save.as.0"); //$NON-NLS-1$

	String APP_SAVE_AS_OTHER_FORMAT_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.app.save.as.1"); //$NON-NLS-1$

	String APP_SAVE_AS_GROUP = Messages
			.getString("wsd.view.gui.ribbon.app.save.as.2"); //$NON-NLS-1$

	String APP_EXPORT = Messages.getString("wsd.view.gui.ribbon.app.27"); //$NON-NLS-1$

	String APP_EXPORT_WHOLE_DOC = Messages.getString("RibbonUIText.44"); //$NON-NLS-1$

	String APP_EXPORT_WHOLE_STANDARD_DOC = Messages
			.getString("RibbonUIText.45"); //$NON-NLS-1$

	String APP_EXPORT_WHOLE_DOC_DESCRIPTION = Messages
			.getString("RibbonUIText.40"); //$NON-NLS-1$

	String APP_EXPORT_WHOLE_DOC_STANDARD_DESCRIPTION = Messages
			.getString("RibbonUIText.48"); //$NON-NLS-1$

	String APP_EXPORT_PAGE_SEQUENCE = Messages.getString("RibbonUIText.46"); //$NON-NLS-1$

	String APP_EXPORT_STANDARD_PAGE_SEQUENCE = Messages
			.getString("RibbonUIText.49"); //$NON-NLS-1$

	String APP_EXPORT_PAGE_SEQUENCE_DESCRIPTION = Messages
			.getString("RibbonUIText.42"); //$NON-NLS-1$

	String APP_EXPORT_PAGE_SEQUENCE_STANDARD_DESCRIPTION = Messages
			.getString("RibbonUIText.50"); //$NON-NLS-1$
	String APP_EXPORT_HTML = Messages
	.getString("RibbonUIText.export.html"); //$NON-NLS-1$

String APP_EXPORT_HTML_DESCRIPTION = Messages
	.getString("ribbonuitext.export.html.description"); //$NON-NLS-1$
String APP_EXPORT_XPATH = Messages
.getString("RibbonUIText.export.xpath"); //$NON-NLS-1$
String APP_EXPORT_XPATH_NULL = Messages
.getString("RibbonUIText.export.xpath.null"); //$NON-NLS-1$

String APP_EXPORT_XPATH_DESCRIPTION = Messages
.getString("ribbonuitext.export.xpath.description");

	String APP_EXPORT_GROUP = Messages.getString("wsd.view.gui.ribbon.app.32"); //$NON-NLS-1$

	String APP_PREVIEW = Messages.getString("wsd.view.gui.ribbon.app.33"); //$NON-NLS-1$
	String APP_HTML_PREVIEW = Messages.getString("wsd.view.gui.ribbon.app.html.pre"); //$NON-NLS-1$
	String APP_PRINT = Messages.getString("wsd.view.gui.ribbon.app.34"); //$NON-NLS-1$

	String APP_PRINT_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.app.35"); //$NON-NLS-1$

	String APP_PRINT_QUICK = Messages.getString("wsd.view.gui.ribbon.app.36"); //$NON-NLS-1$

	String APP_PRINT_QUICK_DESCRIPTION = Messages
			.getString("wsd.view.gui.ribbon.app.37"); //$NON-NLS-1$

	String APP_PRINT_GROUP = Messages.getString("wsd.view.gui.ribbon.app.38"); //$NON-NLS-1$

	String APP_PAGE_SET = Messages.getString("wsd.view.gui.ribbon.app.39"); //$NON-NLS-1$

	String APP_PAGE_SET_GROUP = Messages
			.getString("wsd.view.gui.ribbon.app.40"); //$NON-NLS-1$

	String APP_WISE_DOC_OPTION = Messages
			.getString("wsd.view.gui.ribbon.app.41"); //$NON-NLS-1$

	String APP_EXIT = Messages.getString("wsd.view.gui.ribbon.app.42"); //$NON-NLS-1$

	String Chart_TASK = Messages.getString("wsd.view.gui.ribbon.charttask");

	// 编辑设置相关文本开始
	String Edit_TASK = Messages.getString("wsd.view.gui.ribbon.editTask");

	String EDIT_COMMON = Messages.getString("wsd.view.gui.ribbon.edit.common");

	String EDIT_COMPONENT = Messages
			.getString("wsd.view.gui.ribbon.edit.component");

	String EDIT_TYPE = Messages.getString("wsd.view.gui.ribbon.edit.type");

	String EDIT_AUTHORITY = Messages
			.getString("wsd.view.gui.ribbon.edit.authority");

	String EDIT_ISREROAD = Messages
			.getString("wsd.view.gui.ribbon.edit.isreroad");

	String EDIT_TYPE_UNEDITABLE = Messages
			.getString("wsd.view.gui.ribbon.edit.type.uneditable");

	String EDIT_TYPE_INPUT = Messages
			.getString("wsd.view.gui.ribbon.edit.type.input");

	String EDIT_TYPE_SELECT = Messages
			.getString("wsd.view.gui.ribbon.edit.type.select");

	String EDIT_TYPE_DATE = Messages
			.getString("wsd.view.gui.ribbon.edit.type.date");

	String EDIT_TYPE_RADIO = Messages
			.getString("wsd.view.gui.ribbon.edit.type.radio");

	String INPUT_TYPE = Messages
			.getString("input.type");

	String INPUT_TYPE_PASSWORD = Messages
			.getString("input.type.password");

	String INPUT_TYPE_TEXT = Messages
			.getString("input.type.text");

	String INPUT_MULTILINE = Messages
			.getString("input.multiline");

	String INPUT_WARP = Messages
			.getString("input.warp");

	String INPUT_FILTER = Messages
			.getString("input.filter");

	String INPUT_FILTERMSG = Messages
			.getString("input.filtermsg");
	String INPUT_DEFALUTVALUE = Messages
			.getString("input.defalutvalue");
	String INPUT_DATASOURCE = Messages
			.getString("input.datasource");
	String DATE_TYPE = Messages.getString("wsd.view.gui.ribbon.edit.date.type");

	String DATE_TYPE_C = Messages
			.getString("wsd.view.gui.ribbon.edit.date.type.c");

	String DATE_TYPE_T = Messages
			.getString("wsd.view.gui.ribbon.edit.date.type.t");

	String SELECT_TYPE = Messages
			.getString("select.type");

	String SELECT_TYPE_LIST = Messages
			.getString("select.type.list");

	String SELECT_TYPE_COMBOBOX = Messages
			.getString("select.type.combobox");

	String SELECT_LINES = Messages
			.getString("select.lines");

	String SELECT_ISEDIT = Messages
			.getString("select.isedit");

	String SELECT_SHOWLIST = Messages
			.getString("select.showlist");

	String SELECT_OTHER = Messages
			.getString("select.other");

	String SELECT_DATASOURCE = Messages
			.getString("select.datasource");

	String SELECT_NAME = Messages
			.getString("select.name");

	String SELECT_NEXT = Messages
			.getString("select.next");

	String SELECT_NEXT_SET = Messages
			.getString("select.next.set");

	String SELECT_NEXT_REMOVE = Messages
			.getString("select.next.remove");

	String DATASOURCE_TYPE = Messages
			.getString("datasource.type");

	String DATASOURCE_SCHEMA = Messages
			.getString("datasource.schema");

	String DATASOURCE_INNER = Messages
			.getString("datasource.inner");

	String DATASOURCE_INCLUDE = Messages
			.getString("datasource.include");

	String DATASOURCE_OUTFUNCTION = Messages
			.getString("datasource.outfunction");
	String DATASOURCE_SWINGDS = Messages
			.getString("datasource.swingds");
	String DATASOURCE_SWINGDS_CALLBACK_CHECK = Messages
			.getString("datasource.swingds.callback.check");
	String DATASOURCE_SWINGDS_CALLBACK_LABEL = Messages
			.getString("datasource.swingds.callback.label");
	String DATASOURCE_SWINGDS_TITLE = Messages
			.getString("datasource.swingds.title");
	String DATASOURCE_SWINGDS_NAME_LABEL = Messages
			.getString("datasource.swingds.name.label");
	String DATASOURCE_SWINGDS_TABLE = Messages
			.getString("datasource.swingds.table");
	String DATASOURCE_SWINGDS_TREE = Messages
			.getString("datasource.swingds.tree");
	String SELECT_SORT = Messages
			.getString("select.sort");

	String SELECT_SORT_P = Messages
			.getString("select.sort.p");

	String SELECT_SORT_N = Messages
			.getString("select.sort.n");

	String SELECT_SORT_C = Messages
			.getString("select.sort.c");

	String SELECT_ISSEARCHABLE = Messages
			.getString("select.issearchable");

	String EDIT_DATASOURCE_TITLE = Messages
			.getString("datasource.title");

	String EDIT_DATASOURCE_FILESOURCE_FILE = Messages
			.getString("datasource.filesource.file");

	String EDIT_DATASOURCE_FILESOURCE_FILE_STRUCTURE = Messages
			.getString("datasource.filesource.file.structure");

	String EDIT_DATASOURCE_STRUCTURE = Messages
			.getString("datasource.structure");

	String EDIT_DATASOURCE_STRUCTURE_TREE = Messages
			.getString("datasource.structure.tree");

	String EDIT_DATASOURCE_STRUCTURE_TABLE = Messages
			.getString("datasource.structure.table");

	String EDIT_DATASOURCE_STRUCTURE_TABLE1 = Messages
			.getString("datasource.structure.table1");

	String EDIT_DATASOURCE_STRUCTURE_TABLE2 = Messages
			.getString("datasource.structure.table2");

	String EDIT_DATASOURCE_FILESOURCE_FILE_STRUCTURE_TABLE1 = Messages
			.getString("datasource.filesource.structure.table1");

	String EDIT_DATASOURCE_FILESOURCE_FILE_STRUCTURE_TABLE2 = Messages
			.getString("datasource.filesource.structure.table2");

	String EDIT_DATASOURCE_FILESOURCE_FILE_ROOT = Messages
			.getString("datasource.filesource.root");

	String EDIT_DATASOURCE_FILESOURCE_FILE_COLUMN = Messages
			.getString("datasource.filesource.column");

	String EDIT_DATASOURCE_FILESOURCE_ADD = Messages
			.getString("datasource.filesource.add");

	String EDIT_DATASOURCE_FILESOURCE_DEl = Messages
			.getString("datasource.filesource.del");

	String EDIT_DATASOURCE_FILESOURCE_FILE_COLUMN_NAME = Messages
			.getString("datasource.filesource.column.name");

	String EDIT_DATASOURCE_FILESOURCE_FILE_COLUMN_ISKEY = Messages
			.getString("datasource.filesource.column.iskey");

	String EDIT_DATASOURCE_MULTISOURCE_BOND = Messages
			.getString("datasource.multisource.bond");

	String EDIT_DATASOURCE_MULTISOURCE_BOND_SQ = Messages
			.getString("datasource.multisource.bond.sq");

	String EDIT_DATASOURCE_MULTISOURCE_BOND_EQ = Messages
			.getString("datasource.multisource.bond.eq");

	String EDIT_DATASOURCE_MULTISOURCE_BOND_VERT = Messages
			.getString("datasource.multisource.bond.sq.vert");

	String EDIT_DATASOURCE_OUTINTERFACE = Messages
			.getString("datasource.outinterface");

	String EDIT_DATASOURCE_OUTINTERFACE_CLASSNAME = Messages
			.getString("datasource.outinterface.classname");
	String EDIT_DATASOURCE_OUTINTERFACE_COLUMNS = Messages
			.getString("datasource.outinterface.columns");
	String EDIT_DATASOURCE_OUTINTERFACE_COLUMNCOUNT = Messages
			.getString("datasource.outinterface.columncount");

	String EDIT_SELECTINFO_COLUMN_INDEX = Messages
			.getString("selectinfo.column.index");

	String EDIT_SELECTINFO_COLUMN_ISVIEWABLE = Messages
			.getString("selectinfo.column.isviewable");

	String EDIT_SELECTINFO_COLUMN_SORTORDER = Messages
			.getString("selectinfo.column.sortorder");

	String EDIT_SELECTINFO_COLUMN_SORTORDER_NONE = Messages
			.getString("selectinfo.column.sortorder.none");

	String EDIT_SELECTINFO_COLUMN_SEARCHORDER = Messages
			.getString("selectinfo.column.searchorder");

	String EDIT_SELECTINFO_COLUMN_OPTIONPATH = Messages
			.getString("selectinfo.column.optionpath");

	String EDIT_SELECTINFO_COLUMN_VIEWNAME = Messages
			.getString("selectinfo.column.viewname");

	String EDIT_SELECTINFO_DIALOG_TITLE = Messages
			.getString("selectinfo.dialog.title");

	String EDIT_SELECTINFO_COLUMN_COLUMNTYPE = Messages
			.getString("selectinfo.column.columntype");

	String EDIT_SELECT_NEXT_NAME = Messages
			.getString("select.next.name");

	String EDIT_SELECT_NEXT_COLUMNTHIS = Messages
			.getString("select.next.columnthis");

	String EDIT_SELECT_NEXT_COLUMNNEXT = Messages
			.getString("select.next.columnnext");

	String EDIT_WIDTH = Messages.getString("RibbonUIText.77"); //$NON-NLS-1$

	String EDIT_HEIGHT = Messages.getString("RibbonUIText.78"); //$NON-NLS-1$

	String EDIT_INFORMATION = Messages.getString("RibbonUIText.79"); //$NON-NLS-1$

	String EDIT_UISTYLE = Messages.getString("RibbonUIText.80"); //$NON-NLS-1$

	String EDIT_UIATTRIBUTE = Messages.getString("RibbonUIText.81"); //$NON-NLS-1$

	String VALIDATION = Messages
			.getString("edit.validation");

	String VALIDATION_VALIDATE = Messages
			.getString("edit.validation.validate");

	String VALIDATION_TYPE_SCHEMA = Messages
			.getString("validation.type.schema");

	String VALIDATION_TYPE_ISBLANKORNULL = Messages
			.getString("validation.type.isblankornull");

	String VALIDATION_TYPE_ISBYTE = Messages
			.getString("validation.type.isbyte");

	String VALIDATION_TYPE_ISDOUBLE = Messages
			.getString("validation.type.isdouble");

	String VALIDATION_TYPE_ISFLOAT = Messages
			.getString("validation.type.isfloat");

	String VALIDATION_TYPE_ISINT = Messages
			.getString("validation.type.isint");

	String VALIDATION_TYPE_ISLONG = Messages
			.getString("validation.type.islong");

	String VALIDATION_TYPE_ISSHORT = Messages
			.getString("validation.type.isshort");

	String VALIDATION_TYPE_MATCHREGEXP = Messages
			.getString("validation.type.matchregexp");

	String VALIDATION_TYPE_MAXLENGTH = Messages
			.getString("validation.type.maxlength");

	String VALIDATION_TYPE_MINLENGTH = Messages
			.getString("validation.type.minlength");

	String VALIDATION_TYPE_ISINRANGE = Messages
			.getString("validation.type.isinrange");

	String VALIDATION_TYPE_MAXVALUE = Messages
			.getString("validation.type.maxvalue");

	String VALIDATION_TYPE_MINVALUE = Messages
			.getString("validation.type.minvalue");

	String VALIDATION_TYPE_ISINRANGEOFDATE = Messages
			.getString("validation.type.isinrangeofdate");

	String VALIDATION_MSG = Messages
			.getString("validation.msg");

	String VALIDATION_PARM = Messages
			.getString("validation.parm");

	String VALIDATION_PARMSET = Messages
			.getString("validation.parmset");

	String VALIDATION_PARM_ADD = Messages
			.getString("validation.parm.add");

	String VALIDATION_PARM_DEL = Messages
			.getString("validation.parm.del");

	String VALIDATION_ONBLUR = Messages
			.getString("validation.onblur");

	String VALIDATION_ONEDIT = Messages
			.getString("validation.onedit");

	String VALIDATION_ONRESULT = Messages
			.getString("validation.onresult");

	String EDIT_SET = Messages.getString("edit.set");

	String EDIT_REMOVE = Messages.getString("edit.remove");

	String EDIT_CONNWITH_TITLE = Messages.getString("connwith.title");

	String EDIT_CONNWITH_PARM = Messages.getString("connwith.parm");

	String EDIT_CONNWITH_FORMUL = Messages.getString("connwith.formul");

	String EDIT_CONNWITH_PARM_NAME = Messages
			.getString("connwith.parm.name");

	String EDIT_CONNWITH_PARM_TYPE = Messages
			.getString("connwith.parm.type");

	String EDIT_CONNWITH_PARM_DATATYPE = Messages
			.getString("edit.connwith.parm.datatype");

	String EDIT_CONNWITH_PARM_VALUE = Messages
			.getString("connwith.parm.value");

	String EDIT_CONNWITH_PARM_TYPE_UI = Messages
			.getString("connwith.parm.type.ui");

	String EDIT_CONNWITH_PARM_TYPE_CONSTANCE = Messages
			.getString("connwith.parm.type.constance");

	String EDIT_CONNWITH_PARM_TYPE_XPATH = Messages
			.getString("connwith.parm.type.xpath");

	String EDIT_CONNWITH_PARM_DATATYPE_STRING = Messages
			.getString("connwith.parm.datatype.string");

	String EDIT_CONNWITH_PARM_DATATYPE_NUMBER = Messages
			.getString("connwith.parm.datatype.number");

	String EDIT_CONNWITH_FORMUL_EXPRESSION = Messages
			.getString("connwith.formul.expression");

	String EDIT_CONNWITH_FORMUL_NODE = Messages
			.getString("connwith.formul.node");

	String EDIT_FULL = "full"; //$NON-NLS-1$

	String EDIT_COMPACT = "compact"; //$NON-NLS-1$

	String EDIT_MINIMAL = "minimal"; //$NON-NLS-1$

	String DATA_TRAET = Messages.getString("datatreat");

	String DATA_TRANSFORM = Messages.getString("datatransform");

	String DATA_TRANSFORM_TABLE = Messages.getString("datatransformtable");

	String XPATH_POSITION = Messages.getString("xpathposition");

	String REMOVE_DATA_TRANSFORM_TABLE = Messages
			.getString("removedatatransformtable");

	String REMOVE_XPATH_POSITION = Messages.getString("removexpathposition");

	String NODE_XPATH = Messages.getString("nodexpath");

	// 编辑设置相关文本结束
	String SET_SIMPLE_DATA_TRANSFORM_TABLE = Messages
			.getString("setsimpledatatransformtable");

	String REMOVE_SIMPLE_DATA_TRANSFORM_TABLE = Messages
			.getString("removesimpledatatransformtable");

	String BUTTONS = Messages.getString("buttons");

	String ABOUT = Messages.getString("about");

	String ABOUT_FIRST = Messages.getString("aboutfirst");

	String ABOUT_LAST = Messages.getString("aboutlast");

	String SET_BUTTONS = Messages.getString("setbuttons");

	String REMOVE_BUTTONS = Messages.getString("removebuttons");

	// 生成注册信息
	String GENERATE_REGISTRATION_INFORMATION = Messages
			.getString("generatedregistrationinformation");

	// 导入注册文件
	String LOAD_REGISTRATION_DOCUMENT = Messages
			.getString("loadregistrationdocument");

	String CURRENT_GROUP_UI = Messages.getString("currentgroupui");

	/********** 菜单 *********** 结束 **************/
	// 艺术字相关属性设置文本开始
	String RIBBON_WORDARTTEXT = Messages
			.getString("wordarttext");

	String RIBBON_WORDARTTEXT_TASK = Messages
			.getString("wordarttext.task");

	String RIBBON_WORDARTTEXT_SIZE = Messages
			.getString("wordarttext.size");

	String RIBBON_WORDARTTEXT_SIZE_WIDTH = Messages
			.getString("wordarttext.size.width");

	String RIBBON_WORDARTTEXT_SIZE_HEIGHT = Messages
			.getString("wordarttext.size.height");

	String RIBBON_WORDARTTEXT_TEXT = Messages
			.getString("wordarttext.text");

	String RIBBON_WORDARTTEXT_TEXT_CONTENT = Messages
			.getString("wordarttext.text.content");

	String RIBBON_WORDARTTEXT_TEXT_LETTERSPACE = Messages
			.getString("wordarttext.text.letterspace");

	String RIBBON_WORDARTTEXT_TEXT_ALIGNMENT = Messages
			.getString("wordarttext.text.alignment");

	String RIBBON_WORDARTTEXT_TEXT_ALIGNMENT_LEFT = Messages
			.getString("wordarttext.text.alignment.left");

	String RIBBON_WORDARTTEXT_TEXT_ALIGNMENT_CENTER = Messages
			.getString("wordarttext.text.alignment.center");

	String RIBBON_WORDARTTEXT_TEXT_ALIGNMENT_RIGHT = Messages
			.getString("wordarttext.text.alignment.right");

	String RIBBON_WORDARTTEXT_STYLE = Messages
			.getString("wordarttext.style");

	String RIBBON_WORDARTTEXT_STYLE_PATH = Messages
			.getString("wordarttext.path");

	String RIBBON_WORDARTTEXT_STYLE_ROTATION = Messages
			.getString("wordarttext.rotation");

	String RIBBON_WORDARTTEXT_STYLE_START = Messages
			.getString("wordarttext.start");

	String RIBBON_WORDARTTEXT_STYLE_PATHVISABLE = Messages
			.getString("wordarttext.pathvisable");

	String SET = Messages.getString("set");

	String CLEAR = Messages.getString("clear");

	String AUTO = Messages.getString("auto");

	String STARTINDENT = Messages.getString("startindent");

	String ENDINDENT = Messages.getString("endindent");

	// 艺术字相关属性设置文本结束
	// 主面板状态栏相关message开始
	String FRAME_STATUS_CURRENTNODE = Messages
			.getString("frame.status.currentnode");

	String FRAME_STATUS_CURRENTIMAGE = Messages
			.getString("frame.status.currentimage");

	String FRAME_STATUS_SELECT_TABLECELL = Messages
			.getString("frame.status.select.tablecell");

	String FRAME_STATUS_SELECT_TABLEROW = Messages
			.getString("frame.status.select.tablerow");

	String FRAME_STATUS_SELECT_TABLE = Messages
			.getString("frame.status.select.table");
	String FRAME_STATUS_SELECT_ZIMOBAN = Messages
	.getString("frame.status.select.zimoban");

	String FRAME_STATUS_UNABLE_INPUT = Messages
			.getString("frame.status.unable.input");

	String FRAME_STATUS_UNABLE_PASTE = Messages
			.getString("frame.status.unable.paste");

	String FRAME_STATUS_UNABLE_DROP = Messages
			.getString("frame.status.unable.drop");

	String SIMPLE_ABOUT = Messages.getString("simpleabout");

	String PADDING = Messages.getString("padding");

	String PADDING_TOP = Messages.getString("paddingtop");

	String PADDING_BOTTOM = Messages.getString("paddingbottom");

	String PADDING_LEFT = Messages.getString("paddingleft");

	String PADDING_RIGHT = Messages.getString("paddingrigth");

	// 主面板状态栏相关message结束
	String FONTFAMILYUNABLE = Messages.getString("font.family.unable");

	String SIZE = Messages.getString("size");

	String UTIL_ADD = Messages.getString("util.add");

	String UTIL_DEL = Messages.getString("util.del");

	String DESIGNER_BUTTON = Messages.getString("designerbutton");

	String DESIGNER_DISCRIBLE = Messages.getString("designerdiscrible");

	String DATASTRUCT_NEWSTRUCT_DEAL_INFO = Messages
			.getString("datastruct.newstruct.deal.info");

	String DATASTRUCT_NEWSTRUCT_DEAL_METHOD_TITLE = Messages
			.getString("datastruct.newstruct.deal.method.title");

	String DATASTRUCT_NEWSTRUCT_DEAL_BINDNODE = Messages
			.getString("datastruct.newstruct.deal.bindnode");

	String DATASTRUCT_NEWSTRUCT_DEAL_GROUP_CONDITION = Messages
			.getString("datastruct.newstruct.deal.group.condition");

	String DATASTRUCT_NEWSTRUCT_DEAL_GROUP_SORT = Messages
			.getString("datastruct.newstruct.deal.group.sort");

	String DATASTRUCT_NEWSTRUCT_DEAL_CONDITION = Messages
			.getString("datastruct.newstruct.deal.condition");

	String DATASTRUCT_NEWSTRUCT_DEAL_DYNAMIC_CONDITION = Messages
			.getString("datastruct.newstruct.deal.dynamic.condition");

	String DATASTRUCT_NEWSTRUCT_DEAL_DYNAMIC = Messages
			.getString("datastruct.newstruct.deal.dynamic");

	String CONTENT = Messages.getString("region.content");

	String HEIGHT_AND_PRECEDENCE = Messages
			.getString("region.heightandprecedence");

	String WIDTH = Messages.getString("region.width");

	String WIDTH_COLON = Messages.getString("region.widthcolon");

	String HEIGHT = Messages.getString("region.height");

	String HEIGHT_COLON = Messages.getString("region.heightcolon");

	String DELETE_REGION_CONTENT = Messages.getString("region.deletecontent");

	String WRITEMODE_REFERENCEORIENTATION_ALIGN_OVERFLOW_BACKGROUND = Messages
			.getString("region.writemode.reference_orientation.align.overflow.background");

	String DB_TYPE = Messages.getString("dbtype");

	String CHECK_DB_TYPE = Messages.getString("checkdatype");
	
	String QIANZHANG_TASK = Messages.getString("qianzhang.task");
	String QIANZHANG_INSERT_BAND = Messages.getString("qianzhang.insert.band");
	String QIANZHANG_INSERT_BUTTON = Messages.getString("qianzhang.insert.button");
	String QIANZHANG_INSERT_BUTTON_TOOLTIP = Messages.getString("qianzhang.insert.button.tooltip");
	String QIANZHANG_CHANGE_BUTTON = Messages.getString("qianzhang.change.button");
	String QIANZHANG_CHANGE_BUTTON_TOOLTIP = Messages.getString("qianzhang.change.button.tooltip");
	
	String FORMULA_EDIT = Messages.getString("formula.edit");
}
