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

import java.awt.GraphicsEnvironment;

import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;

/**
 * UI中所有文字常量在这里声明，然后在各个UI中引用
 * 
 * 注释规则是第一行写解释，然后隔一行写列表中具体的信息
 * 
 * 常量命名的规则是 类别_ 加上 具体名称_ 加上 LABEL或者LIST或者BUTTON
 * 
 * @author 闫舒寰
 * @since 2008/09/27
 */
public interface UiText
{

	/**********
	 * 公用标签××××××××××××××开始××××××××××××××××××/ /** 公用标签里面包含：
	 * 
	 * 单位标签： 单位 单位列表： 磅、厘米、像素、英尺、em 数值标签： 数值： 设定值标签：设定值：
	 */

	/**
	 * 单位标签：
	 * 
	 * 单位
	 */
	String COMMON_MEASUREMENT_LABEL = Messages.getString("wsd.view.gui.text.0"); //$NON-NLS-1$

	/**
	 * <p>
	 * 单位列表：
	 * </p>
	 * 
	 * 单位列表： 磅、厘米、像素、英寸、em
	 */
	String[] COMMON_MEASUREMENT_LIST =
	{ Messages.getString("wsd.view.gui.text.1"), //$NON-NLS-1$
			Messages.getString("wsd.view.gui.text.2"), //$NON-NLS-1$
			Messages.getString("wsd.view.gui.text.3"), //$NON-NLS-1$
			Messages.getString("wsd.view.gui.text.4"), //$NON-NLS-1$
			Messages.getString("wsd.view.gui.text.5") }; //$NON-NLS-1$

	/**
	 * 数值标签：
	 * 
	 * 数值
	 */
	String COMMON_VALUE_LABEL = Messages.getString("wsd.view.gui.text.6"); //$NON-NLS-1$

	/**
	 * 设定值标签
	 * 
	 * 设定值：
	 */
	String COMMON_SET_VALUE_LABEL = Messages.getString("wsd.view.gui.text.7"); //$NON-NLS-1$

	/**
	 * <p>
	 * 文字高亮层数：
	 * </p>
	 * <p>
	 * 1层", "2层", "3层", "4层", "5层", "6层
	 * </p>
	 */
	String[] COMMON_COLOR_LAYER =
	{
			Messages.getString("wsd.view.gui.text.8"), Messages.getString("wsd.view.gui.text.9"), Messages.getString("wsd.view.gui.text.10"), Messages.getString("wsd.view.gui.text.11"), Messages.getString("wsd.view.gui.text.12"), Messages.getString("wsd.view.gui.text.13") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$

	/**
	 * 宽度
	 */
	String COMMON_WIDTH = Messages.getString("wsd.view.gui.text.14"); //$NON-NLS-1$

	/**
	 * 样式
	 */
	String COMMON_STYLE = Messages.getString("wsd.view.gui.text.15"); //$NON-NLS-1$

	/**
	 * 颜色
	 */
	String COMMON_COLOR = Messages.getString("wsd.view.gui.text.16"); //$NON-NLS-1$

	/**
	 * 边框设置对话框
	 */
	String BORDER_DIALOG = Messages.getString("wsd.view.gui.text.17"); //$NON-NLS-1$

	/**
	 * 边框
	 */
	String BORDER = Messages.getString("wsd.view.gui.text.18"); //$NON-NLS-1$

	/**
	 * 同步调整四边框设置
	 */
	String BORDER_SYNCHRONOUS = Messages.getString("wsd.view.gui.text.19"); //$NON-NLS-1$

	/**
	 * 分别调整四边框设置
	 */
	String BORDER_ASYNCHRONOUS = Messages.getString("wsd.view.gui.text.20"); //$NON-NLS-1$

	/**
	 * 底纹
	 */
	String SHADING = Messages.getString("wsd.view.gui.text.21"); //$NON-NLS-1$

	/**
	 * <p>
	 * 预览边框标签
	 * </p>
	 * <p>
	 * 预览
	 * </p>
	 */
	String PREVIEW_BORDER_LABEL = Messages.getString("wsd.view.gui.text.22"); //$NON-NLS-1$

	/**
	 * 对话框 确定按钮文字 确定
	 */
	String DIALOG_OK = MessageResource
			.getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.OK); //$NON-NLS-1$

	/**
	 * 对话框 取消按钮文字 取消
	 */
	String DIALOG_CANCEL = MessageResource
			.getMessage(MessageConstants.DIALOG_COMMON
					+ MessageConstants.CANCEL); //$NON-NLS-1$

	/**
	 * 对话框 帮助按钮文字 帮助
	 */
	String DIALOG_HELP = Messages.getString("wsd.view.gui.text.25"); //$NON-NLS-1$

	/**
	 * 对话框 恢复默认值按钮文字 恢复默认值
	 */
	String DIALOG_DEFAULT = Messages.getString("wsd.view.gui.text.26"); //$NON-NLS-1$

	/**
	 * 段落属性详细设置预览文字
	 */
	String PARAGRAPH_PREVIEW_TEXT = Messages.getString("wsd.view.gui.text.27"); //$NON-NLS-1$

	/**********
	 * 公用标签××××××××××××××结束××××××××××××××××××/
	 * 
	 * 
	 ****************/

	/**
	 * 字体设置对话框标签 文字属性设置
	 */
	String FONT_DIALOG_TITLE = Messages.getString("wsd.view.gui.text.28"); //$NON-NLS-1$

	/**
	 * 字体设置对话框内部字体标签 字体设置
	 */
	String FONT_DIALOG_TAB_TITLE_FONT = Messages
			.getString("wsd.view.gui.text.29"); //$NON-NLS-1$

	/**
	 * 字体设置对话框内部位置标签 位置设置
	 */
	String FONT_DIALOG_TAB_TITLE_POSITION = Messages
			.getString("wsd.view.gui.text.30"); //$NON-NLS-1$

	/**
	 * <p>
	 * 文字颜色边框标签
	 * </p>
	 * <p>
	 * 文字颜色
	 * </p>
	 */
	String FONT_COLOR_BORDER_LABEL = Messages.getString("wsd.view.gui.text.31"); //$NON-NLS-1$

	/**
	 * <p>
	 * 文字效果边框标签
	 * </p>
	 * <p>
	 * 文字效果
	 * </p>
	 */
	String FONT_EFFECT_BORDER_LABEL = Messages
			.getString("wsd.view.gui.text.32"); //$NON-NLS-1$

	/**
	 * 字体设置标签
	 * 
	 * 字体设置
	 */
	String FONT_SET_LABEL = Messages.getString("wsd.view.gui.text.33"); //$NON-NLS-1$

	/**
	 * 字体标签
	 * 
	 * 字体：
	 */
	String FONT_FAMILY_NAMES_LABEL = Messages.getString("wsd.view.gui.text.34"); //$NON-NLS-1$

	/**
	 * 字体样式列表
	 * 
	 * Agency FB， 宋体、黑体等字体样式
	 */
	String[] FONT_FAMILY_NAMES_LIST = GraphicsEnvironment
			.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

	/**
	 * 字型标签
	 * 
	 * 字型：
	 */
	String FONT_STYLE_LABEL = Messages.getString("wsd.view.gui.text.35"); //$NON-NLS-1$

	/**
	 * 字型样式列表
	 * 
	 * 正常、粗体、斜体、粗斜体
	 */
	String[] FONT_STYLE_LIST =
	{
			Messages.getString("wsd.view.gui.text.36"), Messages.getString("wsd.view.gui.text.37"), Messages.getString("wsd.view.gui.text.38"), Messages.getString("wsd.view.gui.text.39") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

	/**
	 * 字号标签
	 * 
	 * 字号：
	 */
	String FONT_SIZE_LABEL = Messages.getString("wsd.view.gui.text.40"); //$NON-NLS-1$

	/**
	 * 字号列表
	 * 
	 * 大特号、特号、初号、一号、二号等字号列表
	 */
	String[] FONT_SIZE_LIST =
	{
			Messages.getString("wsd.view.gui.text.41"), Messages.getString("wsd.view.gui.text.42"), Messages.getString("wsd.view.gui.text.43"), Messages.getString("wsd.view.gui.text.44"), Messages.getString("wsd.view.gui.text.45"), Messages.getString("wsd.view.gui.text.46"), Messages.getString("wsd.view.gui.text.47"), Messages.getString("wsd.view.gui.text.48"), Messages.getString("wsd.view.gui.text.49"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$
			Messages.getString("wsd.view.gui.text.50"), Messages.getString("wsd.view.gui.text.51"), Messages.getString("wsd.view.gui.text.52"), Messages.getString("wsd.view.gui.text.53"), Messages.getString("wsd.view.gui.text.54"), Messages.getString("wsd.view.gui.text.55"), Messages.getString("wsd.view.gui.text.56"), Messages.getString("wsd.view.gui.text.57"), Messages.getString("wsd.view.gui.text.58"), Messages.getString("wsd.view.gui.text.59"), Messages.getString("wsd.view.gui.text.60"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$
			Messages.getString("wsd.view.gui.text.61"), Messages.getString("wsd.view.gui.text.62"), Messages.getString("wsd.view.gui.text.63"), Messages.getString("wsd.view.gui.text.64"), Messages.getString("wsd.view.gui.text.65"), Messages.getString("wsd.view.gui.text.66"), Messages.getString("wsd.view.gui.text.67"), Messages.getString("wsd.view.gui.text.68"), Messages.getString("wsd.view.gui.text.69"), Messages.getString("wsd.view.gui.text.70"), Messages.getString("wsd.view.gui.text.71"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$
			Messages.getString("wsd.view.gui.text.72"), Messages.getString("wsd.view.gui.text.73"), Messages.getString("wsd.view.gui.text.74"), Messages.getString("wsd.view.gui.text.75"), Messages.getString("wsd.view.gui.text.76"), Messages.getString("wsd.view.gui.text.77"), Messages.getString("wsd.view.gui.text.78") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$

	/**
	 * 颜色标签
	 * 
	 * 文字颜色
	 */
	String FONT_COLOR_LABEL = Messages.getString("wsd.view.gui.text.79"); //$NON-NLS-1$

	String FONT_COLOR_LAYER_LABEL = Messages.getString("wsd.view.gui.text.80"); //$NON-NLS-1$

	/**
	 * 颜色设置下拉菜单中的按钮
	 * 
	 * 更多颜色...
	 */
	// String FONT_COLOR_BUTTON =
	// MessageResource.getMessage(MessageConstants.TEXT_FONT_EDITER
	// ,MessageConstants.FONT_COLOR);
	String FONT_COLOR_BUTTON = Messages.getString("wsd.view.gui.text.81"); //$NON-NLS-1$

	String AUTO_COLOR_BUTTON = Messages.getString("wsd.view.gui.text.82"); //$NON-NLS-1$

	String NULL_COLOR_BUTTON = Messages.getString("wsd.view.gui.text.83"); //$NON-NLS-1$

	/********** 字体设置滚动菜单********结束 ****************/

	/********** 字型设置滚动菜单********开始 ****************/
	/**
	 * 字型设置标签
	 * 
	 * 字型设置
	 */
	String FONT_STYLE_SET_LABEL = Messages.getString("wsd.view.gui.text.84"); //$NON-NLS-1$

	/**
	 * 下划线标签
	 * 
	 * 下划线
	 */
	String FONT_UNDERLINE = Messages.getString("wsd.view.gui.text.85"); //$NON-NLS-1$

	/**
	 * 删除线标签
	 * 
	 * 删除线
	 */
	String FONT_LINETHROUGH = Messages.getString("wsd.view.gui.text.86"); //$NON-NLS-1$

	/**
	 * 上划线标签
	 * 
	 * 上划线
	 */
	String FONT_OVERLINE = Messages.getString("wsd.view.gui.text.87"); //$NON-NLS-1$

	/********** 字体设置滚动菜单********结束 ****************/

	/********** 文字背景设置滚动菜单********开始 ****************/
	/**
	 * 文字背景设置标签
	 * 
	 * 文字背景设置
	 */
	String FONT_BACKGROUND_SET_LABEL = Messages
			.getString("wsd.view.gui.text.88"); //$NON-NLS-1$

	/**
	 * 文字高亮显示标签
	 * 
	 * 文字高亮显示：
	 */
	String FONT_HEIGHTLIGHT_LABEL = Messages.getString("wsd.view.gui.text.89"); //$NON-NLS-1$

	/**
	 * 文字高亮显示按钮
	 * 
	 * 文字高亮显示菜单
	 */
	String FONT_HEIGHTLIGHT_BUTTON = Messages.getString("wsd.view.gui.text.90"); //$NON-NLS-1$

	/**
	 * 文字底纹设置标签
	 * 
	 * 文字底纹设置：
	 */
	String FONT_SHADING_LABEL = Messages.getString("wsd.view.gui.text.91"); //$NON-NLS-1$

	/**
	 * 文字底纹设置按钮
	 * 
	 * 文字底纹菜单
	 */
	String FONT_SHADING_BUTTON = Messages.getString("wsd.view.gui.text.92"); //$NON-NLS-1$

	/********** 文字背景设置滚动菜单********结束 ****************/

	/********** 文字位置设置滚动菜单********开始 ****************/
	/**
	 * 文字位置设置标签
	 * 
	 * 文字位置设置
	 */
	String FONT_POSITION_SET_LABEL = Messages.getString("wsd.view.gui.text.93"); //$NON-NLS-1$

	/**
	 * 字符位置标签
	 * 
	 * 字符位置：
	 */
	String FONT_POSITION_LABEL = Messages.getString("wsd.view.gui.text.94"); //$NON-NLS-1$

	/**
	 * 字符位置下拉菜单中的文字
	 * 
	 * 正常、上标、下标、自定义
	 */
	String[] FONT_POSITION_LIST =
	{
			Messages.getString("wsd.view.gui.text.95"), Messages.getString("wsd.view.gui.text.96"), Messages.getString("wsd.view.gui.text.97"), Messages.getString("wsd.view.gui.text.98") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

	/**
	 * 字间距标签
	 * 
	 * 字符间距
	 */
	String FONT_SPACE_LABEL = Messages.getString("wsd.view.gui.text.99"); //$NON-NLS-1$

	/**
	 * 字间距下拉菜单
	 * 
	 * 正常、自定义
	 */
	String[] FONT_SPACE_LIST =
	{
			Messages.getString("wsd.view.gui.text.100"), Messages.getString("wsd.view.gui.text.101") }; //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * 字符缩放标签
	 * 
	 * 字符缩放
	 */
	String FONT_STRETCH_LABEL = Messages.getString("wsd.view.gui.text.102"); //$NON-NLS-1$

	/**
	 * 字符缩放下拉菜单
	 * 
	 * 标准、加宽、紧缩
	 */
	String[] FONT_STRETCH_LIST =
	{
			Messages.getString("wsd.view.gui.text.103"), Messages.getString("wsd.view.gui.text.104"), Messages.getString("wsd.view.gui.text.105") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	/**
	 * 词间距标签
	 * 
	 * 词间距：
	 */
	String FONT_WORD_SPACE_LABEL = Messages.getString("wsd.view.gui.text.106"); //$NON-NLS-1$

	/**
	 * 词间距下拉菜单:
	 * 
	 * <P>
	 * 正常、自定义
	 * </p>
	 */
	String[] FONT_WORD_SPACE_LIST =
	{
			Messages.getString("wsd.view.gui.text.107"), Messages.getString("wsd.view.gui.text.108") }; //$NON-NLS-1$ //$NON-NLS-2$

	/********** 文字位置设置滚动菜单********结束 ****************/

	/********** 文字边框设置滚动菜单********开始 ****************/
	/**
	 * 文字边框设置标签
	 * 
	 * 文字边框设置
	 */
	String FONT_BORDER_SET_LABEL = Messages.getString("wsd.view.gui.text.109"); //$NON-NLS-1$

	/********** 文字边框设置滚动菜单********结束 ****************/
	/********** 文字链接设置滚动菜单********开始 ****************/
	/**
	 * 文字链接设置标签
	 * 
	 * 文字链接
	 */
	String FONT_LINK_SET_LABEL = Messages.getString("wsd.view.gui.text.110"); //$NON-NLS-1$

	/********** 文字边框设置滚动菜单********结束 ****************/
	/********** 特殊文字设置滚动菜单********开始 ****************/
	/**
	 * 特殊文字设置标签
	 * 
	 * 特殊文字
	 */
	String FONT_SPECIAL_SET_LABEL = Messages.getString("wsd.view.gui.text.111"); //$NON-NLS-1$

	/********** 特殊文字设置滚动菜单********结束 ****************/

	/**
	 * 所有文本属性设置按钮
	 * 
	 * 全部文本属性
	 */
	String FONT_ALL_PROPERTY_BUTTON = Messages
			.getString("wsd.view.gui.text.112"); //$NON-NLS-1$

	/********** 段落对齐方式属性设置菜单********开始 ****************/
	/**
	 * 段落对齐方式属性设置标签
	 * 
	 * 段落对齐方式
	 */
	String PARAGRAPH_DISPLAY_ALIGN_SET_LABEL = Messages
			.getString("wsd.view.gui.text.113"); //$NON-NLS-1$

	/**
	 * 段落内文本对齐方式标签
	 * 
	 * 段落内文本对齐方式：
	 */
	String PARAGRAPH_TEXT_ALIGN_LABEL = Messages
			.getString("wsd.view.gui.text.114"); //$NON-NLS-1$

	/**
	 * 段落内文本对齐方式列表
	 * 
	 * <p>
	 * 自动对齐、左对齐、右对齐、居中、匀满
	 * </p>
	 */
	// 最后一个分散对齐改成了匀满
	String[] PARAGRAPH_TEXT_ALIGN_LIST =
	{
			Messages.getString("wsd.view.gui.text.115"), Messages.getString("wsd.view.gui.text.116"), Messages.getString("wsd.view.gui.text.117"), Messages.getString("wsd.view.gui.text.118"), Messages.getString("wsd.view.gui.text.119") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

	/**
	 * 尾行标签
	 * 
	 * 末行对齐方式：
	 */
	// 尾行改成末行对齐方式：
	String PARAGRAPH_TEXT_ALIGN_LAST_LABEL = Messages
			.getString("wsd.view.gui.text.120"); //$NON-NLS-1$

	/**
	 * 段落间对齐方式标签
	 * 
	 * 段落间对齐方式：
	 */
	String PARAGRAPH_DISPLAY_ALIGN_LABEL = Messages
			.getString("wsd.view.gui.text.121"); //$NON-NLS-1$

	/**
	 * 段落间对齐方式列表
	 * 
	 * 自动设置、和上边对齐、居中、和下边对齐
	 */
	String[] PARAGRAPH_DISPLAY_ALIGN_LIST =
	{
			Messages.getString("wsd.view.gui.text.122"), Messages.getString("wsd.view.gui.text.123"), Messages.getString("wsd.view.gui.text.124"), Messages.getString("wsd.view.gui.text.125") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

	/**
	 * 文字基线对齐方式标签
	 * 
	 * 文字基线对齐方式：
	 */
	String TEXT_BASELINE_DISPLAY_ALIGN_LABEL = Messages
			.getString("wsd.view.gui.text.126"); //$NON-NLS-1$

	/**
	 * 文字基线对齐方式列表
	 * 
	 * alphic基线对齐、中线对齐、上边对齐
	 */
	String[] TEXT_BASELINE_DISPLAY_ALIGN_LIST =
	{
			Messages.getString("wsd.view.gui.text.127"), Messages.getString("wsd.view.gui.text.128"), Messages.getString("wsd.view.gui.text.129") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	/********** 段落对齐方式属性设置菜单********结束 ****************/

	/********** 段落缩进属性设置菜单********开始 ****************/
	/**
	 * 段落缩进属性设置标签
	 * 
	 * 缩进设置
	 */
	String PARAGRAPH_INDENT_SET_LABEL = Messages
			.getString("wsd.view.gui.text.130"); //$NON-NLS-1$

	/**
	 * 右缩进标签
	 * 
	 * 右缩进：
	 */
	String PARAGRAPH_START_INDENT_LABEL = Messages
			.getString("wsd.view.gui.text.131"); //$NON-NLS-1$

	/**
	 * 左缩进标签
	 * 
	 * 左缩进
	 */
	String PARAGRAPH_END_INDENT_LABEL = Messages
			.getString("wsd.view.gui.text.132"); //$NON-NLS-1$

	/**
	 * 特殊缩进标签
	 * 
	 * 特殊缩进：
	 */
	String PARAGRAPH_SPECIAL_INDENT_LABEL = Messages
			.getString("wsd.view.gui.text.133"); //$NON-NLS-1$

	/**
	 * 特殊缩进列表
	 * 
	 * （无），段首缩进，首行缩进
	 */
	// 段首缩进改成首行缩进
	String[] PARAGRAPH_SPECIAL_INDENT_LIST =
	{
			Messages.getString("wsd.view.gui.text.134"), Messages.getString("wsd.view.gui.text.135"), Messages.getString("wsd.view.gui.text.136") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	/**
	 * 首行文本缩进值标签
	 * 
	 * 首行文本缩进值
	 */
	String PARAGRAPH_FIRST_LINE_INDENT_LABEL = Messages
			.getString("wsd.view.gui.text.137"); //$NON-NLS-1$

	/**
	 * 段落缩进值标签
	 * 
	 * 段落缩进值
	 */
	String PARAGRAPH_INDENT_VALUE_LABEL = Messages
			.getString("wsd.view.gui.text.138"); //$NON-NLS-1$

	/********** 段落缩进属性设置菜单********结束 ****************/

	/********** 段落行间距和段间距属性设置菜单********开始 ****************/

	/**
	 * <p>
	 * 段落属性设置对话框标签
	 * </p>
	 * 
	 * <p>
	 * 段落属性设置
	 * </p>
	 */
	String PARAGRAPH_DIALOG_TITLE = Messages.getString("wsd.view.gui.text.139"); //$NON-NLS-1$

	/**
	 * <p>
	 * 段落属性设置对话框位置设置标签
	 * </p>
	 * 
	 * <p>
	 * 位置设置
	 * </p>
	 */
	String PARAGRAPH_DIALOG_POSITION_TAB = Messages
			.getString("wsd.view.gui.text.140"); //$NON-NLS-1$

	/**
	 * <p>
	 * 段落属性设置对话框换行和分页设置标签
	 * </p>
	 * 
	 * <p>
	 * 换行和分页
	 * </p>
	 */
	String PARAGRAPH_DIALOG_KEEP_BREAK_TAB = Messages
			.getString("wsd.view.gui.text.141"); //$NON-NLS-1$

	String SPAN_ALL = Messages.getString("spanall");

	/**
	 * <p>
	 * 段落属性设置对话框换行和分页设置标签
	 * </p>
	 * 
	 * <p>
	 * 换行和分页
	 * </p>
	 */
	String PARAGRAPH_DIALOG_SPY_SYM_TAB = Messages
			.getString("wsd.view.gui.text.142"); //$NON-NLS-1$

	/**
	 * 段落行间距和段间距属性设置标签
	 * 
	 * 行间距和段间距
	 */
	String PARAGRAPH_SPACE_SET_LABEL = Messages
			.getString("wsd.view.gui.text.143"); //$NON-NLS-1$

	/**
	 * 行距设定标签
	 * 
	 * 行距设定
	 */
	String PARAGRAPH_INLINE_SPACE_SET_LABEL = Messages
			.getString("wsd.view.gui.text.144"); //$NON-NLS-1$

	/**
	 * 行间空标签
	 * 
	 * 行间空：
	 */
	String PARAGRAPH_INLINE_SPACE_LABEL = Messages
			.getString("wsd.view.gui.text.145"); //$NON-NLS-1$

	/**
	 * 行间空设置值
	 */
	String PARAGRAPH_INLINE_SPACE_VALUE = Messages
			.getString("wsd.view.gui.text.146"); //$NON-NLS-1$

	/**
	 * <p>
	 * 行间空列表
	 * </p>
	 * 
	 * 单倍行距、1.5倍行距、2倍行距、最小值、固定值、多倍行距
	 */
	String[] PARAGRAPH_INLINE_SPACE_LIST =
	{
			Messages.getString("wsd.view.gui.text.147"), Messages.getString("wsd.view.gui.text.148"), Messages.getString("wsd.view.gui.text.149"), Messages.getString("wsd.view.gui.text.150"), Messages.getString("wsd.view.gui.text.151"), Messages.getString("wsd.view.gui.text.152") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$

	/**
	 * <p>
	 * 行高设置值标签
	 * </p>
	 * 
	 * 行高设置值：
	 */
	String PARAGRAPH_LINE_HEIGHT_LABEL = Messages
			.getString("wsd.view.gui.text.153"); //$NON-NLS-1$

	/**
	 * <p>
	 * 行高策略设置标签
	 * </p>
	 * 
	 * 行高策略设置
	 */
	String PARAGRAPH_LINE_STACKING_STRATEGY_LABEL = Messages
			.getString("wsd.view.gui.text.154"); //$NON-NLS-1$

	/**
	 * <p>
	 * 行高策略设置列表
	 * </p>
	 * 
	 * "max-height", "line-height", "font-height"
	 */
	String[] PARAGRAPH_LINE_STACKING_STRATEGY_LIST =
	{
			Messages.getString("wsd.view.gui.text.155"), Messages.getString("wsd.view.gui.text.156"), Messages.getString("wsd.view.gui.text.157") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	/**
	 * 段间距标签
	 * 
	 * 段间距设置
	 */
	String PARAGRAPH_BLOCK_SPACE_SET_LABEL = Messages
			.getString("wsd.view.gui.text.158"); //$NON-NLS-1$

	/**
	 * 行高行间距设置
	 */
	String PARAGRAPH_HEIGHT_SPACE_SET = Messages
			.getString("wsd.view.gui.text.159"); //$NON-NLS-1$

	/**
	 * 该段位于页首时保留
	 */
	String PARAGRAPH_SPACE_BEFORE_KEEP_IN_FIRST_PAGE = Messages
			.getString("wsd.view.gui.text.160"); //$NON-NLS-1$

	/**
	 * 段前距标签
	 * 
	 * 段前距
	 */
	String PARAGRAPH_BLOCK_SPACE_BEFORE_LABEL = Messages
			.getString("wsd.view.gui.text.161"); //$NON-NLS-1$

	/**
	 * 段后距标签
	 * 
	 * 段后距
	 */
	String PARAGRAPH_BLOCK_SPACE_AFTER_LABEL = Messages
			.getString("wsd.view.gui.text.162"); //$NON-NLS-1$

	/**
	 * 该段位于尾页时保留
	 */
	String PARAGRAPH_SPACE_AFTER_KEEP_IN_LAST_PAGE = Messages
			.getString("wsd.view.gui.text.163"); //$NON-NLS-1$

	/**
	 * 段间距详细设置菜单按钮
	 * 
	 * 段间距详细设置菜单
	 */
	String PARAGRAPH_BLOCK_DETIAL_SET_BUTTON = Messages
			.getString("wsd.view.gui.text.164"); //$NON-NLS-1$

	/********** 段落行间距和段间距属性设置菜单********结束 ****************/

	/********** 段落背景属性设置菜单********开始 ****************/
	/**
	 * 段落背景属性设置标签
	 * 
	 * 背景设置
	 */
	String PARAGRAPH_BACKGROUND_SET_LABEL = Messages
			.getString("wsd.view.gui.text.165"); //$NON-NLS-1$

	/**
	 * 段落背景设置菜单按钮
	 * 
	 * 段落背景设置菜单
	 */
	String PARAGRAPH_BACKGROUND_BUTTON = Messages
			.getString("wsd.view.gui.text.166"); //$NON-NLS-1$

	/********** 段落背景属性设置菜单********结束 ****************/

	/********** 段落边框属性设置菜单********开始 ****************/
	/**
	 * 段落边框属性设置标签
	 * 
	 * 段落边框设置
	 */
	String PARAGRAPH_BORDER_SET_LABEL = Messages
			.getString("wsd.view.gui.text.167"); //$NON-NLS-1$

	/**
	 * 设置边框按钮
	 * 
	 * 设置边框
	 */
	String PARAGRAPH_BORDER_SET_BUTTON = Messages
			.getString("wsd.view.gui.text.168"); //$NON-NLS-1$

	/**
	 * 边框宽度标签
	 * 
	 * 边框宽度
	 */
	String PARAGRAPH_BORDER_WIDTH_LABEL = Messages
			.getString("wsd.view.gui.text.169"); //$NON-NLS-1$

	/**
	 * 边框颜色标签
	 * 
	 * 边框颜色
	 */
	String PARAGRAPH_BORDER_COLOR_LABEL = Messages
			.getString("wsd.view.gui.text.170"); //$NON-NLS-1$

	/**
	 * 边框颜色设置按钮
	 * 
	 * 边框颜色
	 */
	String PARAGRAPH_BORDER_COLOR_BUTTON = Messages
			.getString("wsd.view.gui.text.171"); //$NON-NLS-1$

	/**
	 * 边框样式标签
	 * 
	 * 边框样式
	 */
	String PARAGRAPH_BORDER_STYLE_LABEL = Messages
			.getString("wsd.view.gui.text.172"); //$NON-NLS-1$

	/**
	 * <p>
	 * 边框样式下拉菜单
	 * </p>
	 * 
	 * none, hidden, dotted, dashed, solid, double, groove, ridge, inset, outset
	 * (详见7.8.20）
	 */
	String[] PARAGRAPH_BORDER_STYLE_LIST =
	{
			Messages.getString("wsd.view.gui.text.173"), Messages.getString("wsd.view.gui.text.174"), Messages.getString("wsd.view.gui.text.175"), Messages.getString("wsd.view.gui.text.176"), Messages.getString("wsd.view.gui.text.177"), Messages.getString("wsd.view.gui.text.178"), Messages.getString("wsd.view.gui.text.179"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
			Messages.getString("wsd.view.gui.text.180"), Messages.getString("wsd.view.gui.text.181") }; //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * 边框详细设置标签
	 * 
	 * 详细设定
	 */
	String PARAGRAPH_BORDER_DETAIL_SET_LABEL = Messages
			.getString("wsd.view.gui.text.182"); //$NON-NLS-1$

	/**
	 * 边框详细设定按钮
	 * 
	 * 详细设定按钮
	 */
	String PARAGRAPH_BORDER_DETAIL_SET_BUTTON = Messages
			.getString("wsd.view.gui.text.183"); //$NON-NLS-1$

	/********** 段落边框属性设置菜单********结束 ****************/

	/********** 段落换行和分页属性设置菜单********开始 ****************/
	/**
	 * 段落换行和分页属性标签
	 * 
	 * 换行和分页
	 */
	String PARAGRAPH_PAGINATION_SET_LABEL = Messages
			.getString("wsd.view.gui.text.184"); //$NON-NLS-1$

	/**
	 * 孤行控制按钮
	 * 
	 * 孤行控制
	 */
	String PARAGRAPH_ORPHAN_CONTROL_BOTTON = Messages
			.getString("wsd.view.gui.text.185"); //$NON-NLS-1$

	/**
	 * 与下段同页按钮
	 * 
	 * 与下段同页
	 */
	String PARAGRAPH_KEEP_WITH_NEXT_PARA_BUTTON = Messages
			.getString("wsd.view.gui.text.186"); //$NON-NLS-1$

	/**
	 * 段中不分页按钮
	 * 
	 * 段中不分页
	 */
	String PARAGRAPH_KEEP_IN_ONE_PAGE_BUTTON = Messages
			.getString("wsd.view.gui.text.187"); //$NON-NLS-1$

	/**
	 * 段前分页按钮
	 * 
	 * 段前分页
	 */
	String PARAGRAPH_BREAK_PAGE_BEFORE_BUTTON = Messages
			.getString("wsd.view.gui.text.188"); //$NON-NLS-1$

	/**
	 * 与前段同页
	 */
	String PARAGRAPH_KEEP_WITH_PREVIOUS_PAGE = Messages
			.getString("wsd.view.gui.text.189"); //$NON-NLS-1$

	/**
	 * 与后段同栏
	 */
	String PARAGRAPH_KEEP_WITH_NEXT_COLUMN = Messages
			.getString("wsd.view.gui.text.190"); //$NON-NLS-1$

	/**
	 * 本段不跨栏
	 */
	String PARAGRAPH_PAGE_NO_BREAK_COLUMN = Messages
			.getString("wsd.view.gui.text.191"); //$NON-NLS-1$

	/**
	 * 本段不跨页
	 */
	String PARAGRAPH_PAGE_NO_BREAK_PAGE = Messages
			.getString("wsd.view.gui.text.192"); //$NON-NLS-1$

	/**
	 * 与前段同栏
	 */
	String PARAGRAPH_KEEP_PREVIOUS_COLUMN = Messages
			.getString("wsd.view.gui.text.193"); //$NON-NLS-1$

	/**
	 * 换页
	 */
	String PARAGRAPH_PAGE_NEXT_PAGE_LABEL = Messages
			.getString("wsd.view.gui.text.194"); //$NON-NLS-1$

	/**
	 * 跨页跨栏（流式分布，不同于跨所有栏属性）
	 */
	String PARAGRAPH_PAGE_AND_COLUMN_LABEL = Messages
			.getString("pageandcolumn");

	/**
	 * 段后分页：
	 */
	String PARAGRAPH_BREAK_AFTER_PAGE_LABEL = Messages
			.getString("wsd.view.gui.text.195"); //$NON-NLS-1$

	// 以下是paragraphKeepBreakPanel中的文字的引用
	/**
	 * 不换页
	 */
	String PARAGRAPH_NO_PAGE_BREAK = Messages
			.getString("wsd.view.gui.text.196"); //$NON-NLS-1$

	/**
	 * 下一页
	 */
	String PARAGRAPH_NEXT_PAGE_BREAK = Messages
			.getString("wsd.view.gui.text.197"); //$NON-NLS-1$

	/**
	 * 下一奇数页
	 */
	String PARAGRAPH_NEXT_ODD_PAGE = Messages
			.getString("wsd.view.gui.text.198"); //$NON-NLS-1$

	/**
	 * 下一偶数数页
	 */
	String PARAGRAPH_NEXT_EVEN_PAGE = Messages
			.getString("wsd.view.gui.text.199"); //$NON-NLS-1$

	/**
	 * 下一栏
	 */
	String PARAGRAPH_NEXT_COLUMN = Messages.getString("wsd.view.gui.text.200"); //$NON-NLS-1$

	/**
	 * 自动
	 */
	String PARAGRAPH_AUTO = Messages.getString("wsd.view.gui.text.201"); //$NON-NLS-1$

	/**
	 * 总是
	 */
	String PARAGRAPH_ALWAYS = Messages.getString("wsd.view.gui.text.202"); //$NON-NLS-1$

	/**
	 * column
	 */
	String PARAGRAPH_COLUMN = Messages.getString("wsd.view.gui.text.203"); //$NON-NLS-1$

	/**
	 * page
	 */
	String PARAGRAPH_PAGE = Messages.getString("wsd.view.gui.text.204"); //$NON-NLS-1$

	/**
	 * 段尾最少行数
	 */
	String PARAGRAPH_LAST_LINE_MIN_NUMBER = Messages
			.getString("wsd.view.gui.text.205"); //$NON-NLS-1$

	/**
	 * 段尾最少行数
	 */
	String PARAGRAPH_FIRST_LINE_MIN_NUMBER = Messages
			.getString("wsd.view.gui.text.206"); //$NON-NLS-1$

	/**
	 * 孤行寡行
	 */
	String PARAGRAPH_WIDOWS_ORPHAN = Messages
			.getString("wsd.view.gui.text.207"); //$NON-NLS-1$

	/********** 段落换行和分页属性设置菜单********结束 ****************/

	/********** 特殊段落格式属性设置菜单********开始 ****************/
	/**
	 * 特殊段落格式属性标签
	 * 
	 * 特殊段落格式
	 */
	String PARAGRAPH_SPECIAL_SET_LABEL = Messages
			.getString("wsd.view.gui.text.208"); //$NON-NLS-1$

	/**
	 * 首字下沉标签
	 * 
	 * 首字下沉
	 */
	String PARAGRAPH_PREFIX_SINK_LABEL = Messages
			.getString("wsd.view.gui.text.209"); //$NON-NLS-1$

	/**
	 * 字首下沉按钮
	 * 
	 * 显示字首下沉
	 */
	String PARAGRAPH_PREFIX_SINK_BUTTON = Messages
			.getString("wsd.view.gui.text.210"); //$NON-NLS-1$

	/**
	 * 中文段落版式标签
	 * 
	 * 中文段落版式
	 */
	String PARAGRAPH_CHINA_STYLE_LABEL = Messages
			.getString("wsd.view.gui.text.211"); //$NON-NLS-1$

	/**
	 * 禁首禁尾按钮
	 * 
	 * 禁首禁尾
	 */
	String PARAGRAPH_CHINA_FORBIDDEN_LABEL = Messages
			.getString("wsd.view.gui.text.212"); //$NON-NLS-1$

	/**
	 * 允许标点溢出边界按钮
	 * 
	 * 允许标点溢出边界
	 */
	String PARAGRAPH_CHINA_PUNCTUATION_OUT_BUTTON = Messages
			.getString("wsd.view.gui.text.213"); //$NON-NLS-1$

	/**
	 * 字符间距标签
	 * 
	 * 字符间距
	 */
	String PARAGRAPH_CHAR_SPACE_LABEL = Messages
			.getString("wsd.view.gui.text.214"); //$NON-NLS-1$

	/**
	 * 自动调整中文与西文的间距按钮
	 * 
	 * 自动调整中文与西文的间距
	 */
	String PARAGRAPH_AUTO_ADJUST_CN_EN_BUTTON = Messages
			.getString("wsd.view.gui.text.215"); //$NON-NLS-1$

	/**
	 * 自动调整中文与数字的间距按钮
	 * 
	 * 自动调整中文与数字的间距
	 */
	String PARAGRAPH_AUTO_ADJUST_CN_NUM_BUTTON = Messages
			.getString("wsd.view.gui.text.216"); //$NON-NLS-1$

	// 以下为特殊符号设置面板
	/**
	 * 全部保留
	 */
	String PARAGRAPH_SPYSYM_ALL = Messages.getString("wsd.view.gui.text.217"); //$NON-NLS-1$

	/**
	 * 只保留一个
	 */
	String PARAGRAPH_SPYSYM_ONE = Messages.getString("wsd.view.gui.text.218"); //$NON-NLS-1$

	/**
	 * 忽略
	 */
	String PARAGRAPH_SPYSSYM_IGNORE = Messages
			.getString("wsd.view.gui.text.219"); //$NON-NLS-1$

	/**
	 * 保留
	 */
	String PARAGRAPH_SPYSSYM_PRESERVE = Messages
			.getString("wsd.view.gui.text.220"); //$NON-NLS-1$

	/**
	 * 换行符之前忽略
	 */
	String PARAGRAPH_SPYSSYM_IGNORE_BEFORE = Messages
			.getString("wsd.view.gui.text.221"); //$NON-NLS-1$

	/**
	 * 换行符之后忽略
	 */
	String PARAGRAPH_SPYSSYM_IGNORE_AFTER = Messages
			.getString("wsd.view.gui.text.222"); //$NON-NLS-1$

	/**
	 * 换行符前后忽略
	 */
	String PARAGRAPH_SPYSSYM_IGNORE_BOTH = Messages
			.getString("wsd.view.gui.text.223"); //$NON-NLS-1$

	/**
	 * 作为空格处理
	 */
	String PARAGRAPH_SPYSSYM_AS_SPACE = Messages
			.getString("wsd.view.gui.text.224"); //$NON-NLS-1$

	/**
	 * 连续空格处理
	 */
	String PARAGRAPH_SPYSYM_CONTINUAL_SPACE = Messages
			.getString("wsd.view.gui.text.225"); //$NON-NLS-1$

	/**
	 * 空格的处理
	 */
	String PARAGRAPH_SPYSYM_DEAL_SPACE_LABEL = Messages
			.getString("wsd.view.gui.text.226"); //$NON-NLS-1$

	/**
	 * 换行符的处理
	 */
	String PARAGRAPH_SPYSYM_DEAL_ENTER_LABEL = Messages
			.getString("wsd.view.gui.text.227"); //$NON-NLS-1$

	/**
	 * 特殊符号的处理
	 */
	String PARAGRAPH_SPYSYM_SPECIAL_LABEL = Messages
			.getString("wsd.view.gui.text.228"); //$NON-NLS-1$

	/********** 特殊段落格式属性设置菜单********结束 ****************/

	/**
	 * 所有段落属性菜单按钮
	 * 
	 * 所有段落属性菜单
	 */
	String PARAGRAPH_ALL_PROPERTY_BUTTON = Messages
			.getString("wsd.view.gui.text.229"); //$NON-NLS-1$

	/**
	 * 丢弃
	 */
	String PARAGRAPH_DISCARD = Messages.getString("wsd.view.gui.text.230"); //$NON-NLS-1$

	/**
	 * 保留
	 */
	String PARAGRAPH_RETAIN = Messages.getString("wsd.view.gui.text.231"); //$NON-NLS-1$

	/********** 页面设置属性菜单********结束 ****************/
	/**
	 * 页布局属性
	 */
	String PAGE_PROPERTY_TITLE = Messages.getString("wsd.view.gui.text.232"); //$NON-NLS-1$

	/**
	 * 页布局面板标签
	 */
	String PAGE_LAYOUT_TITLE = Messages.getString("wsd.view.gui.text.233"); //$NON-NLS-1$

	/**
	 * 页布局名字
	 */
	String PAGE_SIMPLE_MASTER_NAME = Messages
			.getString("wsd.view.gui.text.234"); //$NON-NLS-1$

	/**
	 * 版心区域
	 */
	String PAGE_BODY_TITLE = Messages.getString("wsd.view.gui.text.235"); //$NON-NLS-1$

	/**
	 * 页眉设置
	 */
	String PAGE_HEADER_TITLE = Messages.getString("wsd.view.gui.text.236"); //$NON-NLS-1$

	/**
	 * 页脚设置
	 */
	String PAGE_FOOTER_TITLE = Messages.getString("wsd.view.gui.text.237"); //$NON-NLS-1$

	/**
	 * 左区域设置
	 */
	String PAGE_START_TITLE = Messages.getString("wsd.view.gui.text.238"); //$NON-NLS-1$

	/**
	 * 右区域设置
	 */
	String PAGE_END_TITLE = Messages.getString("wsd.view.gui.text.239"); //$NON-NLS-1$

	// 以下是SimplePageLayout类下四个区域的内容
	String ADD_REGION_BEFORE = Messages.getString("wsd.view.gui.text.240"); //$NON-NLS-1$

	String ADD_REGION_AFTER = Messages.getString("wsd.view.gui.text.241"); //$NON-NLS-1$

	String ADD_REGION_START = Messages.getString("wsd.view.gui.text.242"); //$NON-NLS-1$

	String ADD_REGION_END = Messages.getString("wsd.view.gui.text.243"); //$NON-NLS-1$

	String APPLY_FLOW = Messages.getString("wsd.view.gui.text.244"); //$NON-NLS-1$

	String APPLY_CURRENT_LAYOUT = Messages.getString("wsd.view.gui.text.245"); //$NON-NLS-1$

	/**
	 * 纸张方向标签
	 * 
	 * 纸张方向
	 */
	String PAGE_ORIENTATION_LABEL = Messages.getString("wsd.view.gui.text.246"); //$NON-NLS-1$

	/**
	 * 纵向按钮
	 * 
	 * 纵向
	 */
	String PAGE_PROTRAIT_BUTTON = Messages.getString("wsd.view.gui.text.247"); //$NON-NLS-1$

	/**
	 * 横向按钮
	 * 
	 * 横向
	 */
	String PAGE_LANDSCAPE_BUTTON = Messages.getString("wsd.view.gui.text.248"); //$NON-NLS-1$

	/**
	 * 文字方向标签
	 * 
	 * 文字方向
	 */
	String PAGE_TEXT_DIRECTION_LABEL = Messages
			.getString("wsd.view.gui.text.249"); //$NON-NLS-1$

	/**
	 * 文字方向列表
	 * 
	 * "lr-tb", "rl-tb", "tb-rl", "bt-lr", "bt-rl", "lr-bt", "rl-bt"
	 */
	String[] REGION_TEXT_DIRECTION_LIST =
	{ Messages.getString("wsd.view.gui.text.250"),
			Messages.getString("wsd.view.gui.text.251"),
			Messages.getString("wsd.view.gui.text.252"),
			Messages.getString("extendpage") };

	String[] PAGE_TEXT_DIRECTION_LIST =
	{ Messages.getString("wsd.view.gui.text.250"),
			Messages.getString("wsd.view.gui.text.251"),
			Messages.getString("wsd.view.gui.text.252") };

	/**
	 * 纸张大小标签
	 * 
	 * 纸张大小
	 */
	String PAGE_PAPER_FORMAT_LABEL = Messages
			.getString("wsd.view.gui.text.257"); //$NON-NLS-1$
	String PAGE_MEDIAUSE = Messages
	.getString("wsd.view.gui.text.mediause"); //$NON-NLS-1$
	String PAGE_MEDIAUSE_HELP = Messages
	.getString("wsd.view.gui.text.mediause.help");
	/**
	 * <p>
	 * 纸张大小列表
	 * </p>
	 * 
	 * "Letter", "Tabloid", "Legal", "A3", "A4", "A5", "B4", "B5", "16开", "32开",
	 * "大32开"
	 */
	String[] PAGE_PAPER_FORMAT_LIST =
	{ Messages.getString("wsd.view.gui.text.258"),
			Messages.getString("wsd.view.gui.text.259"),
			Messages.getString("wsd.view.gui.text.260"),
			Messages.getString("wsd.view.gui.text.261"),
			Messages.getString("wsd.view.gui.text.262"),
			Messages.getString("wsd.view.gui.text.263"),
			Messages.getString("wsd.view.gui.text.264"),
			Messages.getString("wsd.view.gui.text.265"),
			Messages.getString("wsd.view.gui.text.266"),
			Messages.getString("wsd.view.gui.text.267"),
			Messages.getString("wsd.view.gui.text.268"),
			Messages.getString("wsd.view.gui.text.269") };

	/**
	 * 页面宽度标签
	 * 
	 * 宽度
	 */
	String PAGE_WIDTH_LABEL = Messages.getString("wsd.view.gui.text.270"); //$NON-NLS-1$

	/**
	 * 页面高度标签
	 * 
	 * 高度：
	 */
	String PAGE_HEIGHT_LABEL = Messages.getString("wsd.view.gui.text.271"); //$NON-NLS-1$

	/**
	 * 页边距标签
	 * 
	 * 页边距
	 */
	String PAGE_MARGINS_LABEL = Messages.getString("wsd.view.gui.text.272"); //$NON-NLS-1$

	/**
	 * 版心边距
	 */
	String PAGE_BODY_MARGINS_LABEL = Messages
			.getString("wsd.view.gui.text.273"); //$NON-NLS-1$

	/**
	 * 上边距标签
	 * 
	 * 上：
	 */
	String PAGE_MARGIN_TOP_LABEL = Messages.getString("wsd.view.gui.text.274"); //$NON-NLS-1$

	/**
	 * 下边距标签
	 * 
	 * 下：
	 */
	String PAGE_MARGIN_BOTTOM_LABEL = Messages
			.getString("wsd.view.gui.text.275"); //$NON-NLS-1$

	/**
	 * 左边距标签
	 * 
	 * 左：
	 */
	String PAGE_MARGIN_LEFT_LABEL = Messages.getString("wsd.view.gui.text.276"); //$NON-NLS-1$

	/**
	 * 右边距标签
	 * 
	 * 右：
	 */
	String PAGE_MARGIN_RIGHT_LABEL = Messages
			.getString("wsd.view.gui.text.277"); //$NON-NLS-1$

	/**
	 * 装订线标签
	 * 
	 * 装订线：
	 */
	// String
	// 中文数字格式
	String[] CHINESE_NUMBER_SET =
	{
			Messages.getString("wsd.view.gui.text.279"), Messages.getString("wsd.view.gui.text.280"), Messages.getString("chinesejine"),
			Messages.getString("wsd.view.gui.text.279_1"), Messages.getString("wsd.view.gui.text.280_1"), Messages.getString("chinesejine_1") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	/**
	 * 版心方向：
	 */
	String PAGE_BODY_ORIENTATION_LABEL = Messages
			.getString("wsd.view.gui.text.281"); //$NON-NLS-1$

	/**
	 * <p>
	 * 版心方向列表
	 * </p>
	 * <p>
	 * "0", "90", "180", "270", "-90", "-180", "-270"
	 * </p>
	 */
	String[] PAGE_BODY_ORIENTATION_LIST =
	{ Messages.getString("wsd.view.gui.text.282"),
			Messages.getString("wsd.view.gui.text.283"),
			Messages.getString("wsd.view.gui.text.284"),
			Messages.getString("wsd.view.gui.text.285"),
			Messages.getString("wsd.view.gui.text.286"),
			Messages.getString("wsd.view.gui.text.287"),
			Messages.getString("wsd.view.gui.text.288") };

	/**
	 * 版心文字方向：
	 */
	String PAGE_BODY_WRITING_MODE = Messages.getString("wsd.view.gui.text.289"); //$NON-NLS-1$

	/**
	 * 分栏数
	 */
	String PAGE_COLUMN_COUNT_LABEL = Messages
			.getString("wsd.view.gui.text.290"); //$NON-NLS-1$

	/**
	 * 栏间距
	 */
	String PAGE_COLUMN_GAP_LABEL = Messages.getString("wsd.view.gui.text.291"); //$NON-NLS-1$

	/**
	 * 版心内容对齐方式
	 */
	String PAGE_BODY_DISPLAY_ALIGN_LABEL = Messages
			.getString("wsd.view.gui.text.292"); //$NON-NLS-1$

	/**
	 * "顶端对齐", "上下居中", "底端对齐"
	 */
	String[] PAGE_BODY_DISPLAY_ALIGN_LIST =
	{
			Messages.getString("wsd.view.gui.text.293"), Messages.getString("wsd.view.gui.text.294"), Messages.getString("wsd.view.gui.text.295") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	/**
	 * 版心内容溢出方式
	 */
	String PAGE_BODY_CONTENT_OVERFLOW_LABEL = Messages
			.getString("wsd.view.gui.text.296"); //$NON-NLS-1$

	/**
	 * 溢出方式列表 "auto", "visible", "hidden", "scroll"
	 */
	String[] PAGE_BODY_CONTENT_OVERFLOW_LIST =
	{ Messages.getString("wsd.view.gui.text.298"),
			Messages.getString("wsd.view.gui.text.299") };

	/**
	 * 版心背景设置
	 */
	String PAGE_BODY_BACKGROUND_LABEL = Messages
			.getString("wsd.view.gui.text.301"); //$NON-NLS-1$

	/********** 页面设置属性菜单********结束 ****************/

	String SET_SVG_STYLE = Messages.getString("wsd.view.gui.text.302"); //$NON-NLS-1$

	String SVG_BORDER_AND_COLOR = Messages.getString("wsd.view.gui.text.303"); //$NON-NLS-1$

	String SVG_POSITION = Messages.getString("wsd.view.gui.text.304"); //$NON-NLS-1$

	/************ 背景面板属性菜单 ************** 开始 ********************/
	/**
	 * 主要是BackGroundPanel类内的文字
	 */
	String COLOR_LAYER = Messages.getString("wsd.view.gui.text.305"); //$NON-NLS-1$

	String REPEAT_SET = Messages.getString("wsd.view.gui.text.306"); //$NON-NLS-1$

	String[] REPEAT_SET_LIST =
	{
			Messages.getString("wsd.view.gui.text.307"), Messages.getString("wsd.view.gui.text.308"), Messages.getString("wsd.view.gui.text.309"), Messages.getString("wsd.view.gui.text.310") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

	String HORIZONTAL_POSITION = Messages.getString("wsd.view.gui.text.311"); //$NON-NLS-1$

	String[] HORIZONTAL_POSITION_LIST =
	{
			Messages.getString("wsd.view.gui.text.312"), Messages.getString("wsd.view.gui.text.313"), Messages.getString("wsd.view.gui.text.314"), Messages.getString("wsd.view.gui.text.315") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

	String VERTICAL_SET = Messages.getString("wsd.view.gui.text.316"); //$NON-NLS-1$

	String[] VERTICAL_SET_LIST =
	{
			Messages.getString("wsd.view.gui.text.317"), Messages.getString("wsd.view.gui.text.318"), Messages.getString("wsd.view.gui.text.319"), Messages.getString("wsd.view.gui.text.320") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

	String STATIC_PATH = Messages.getString("wsd.view.gui.text.321"); //$NON-NLS-1$

	String REMOVE_BACKGROUNDIMAGE = Messages
			.getString("wsd.view.gui.text.removebgimage"); //$NON-NLS-1$

	String FILL_COLOR = Messages.getString("wsd.view.gui.text.322"); //$NON-NLS-1$

	String FILL_COLOR_ERROR = Messages.getString("wsd.view.gui.text.323"); //$NON-NLS-1$

	String FILL_PICTURE = Messages.getString("wsd.view.gui.text.324"); //$NON-NLS-1$

	String DYNAMIC_PATH_BUTTON = ""; //$NON-NLS-1$

	String DYNAMIC_PATH_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.text.326"); //$NON-NLS-1$

	String DYNAMIC_PATH_BUTTON_DESCRIPTION = Messages
			.getString("wsd.view.gui.text.327"); //$NON-NLS-1$

	/************ 背景面板属性菜单 ************** 结束 ********************/

	/************ 表格属性设置菜单 ************** 开始 ********************/
	String TABLE_PROPERTY_TAB = Messages.getString("wsd.view.gui.text.328"); //$NON-NLS-1$

	String TABLE_ALIGN = Messages.getString("wsd.view.gui.text.329"); //$NON-NLS-1$

	String[] TABLE_ALIGN_LIST =
	{
			Messages.getString("wsd.view.gui.text.330"), Messages.getString("wsd.view.gui.text.331"), Messages.getString("wsd.view.gui.text.332"), Messages.getString("wsd.view.gui.text.333"), Messages.getString("wsd.view.gui.text.334"), Messages.getString("wsd.view.gui.text.335") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$

	String TABLE_WIDTH_LABEL = Messages.getString("wsd.view.gui.text.336"); //$NON-NLS-1$

	String TABLE_AUTO_WIDTH = Messages.getString("wsd.view.gui.text.337"); //$NON-NLS-1$

	// 单元格属性面板

	String TABLE_CELL_PROPERTY = Messages.getString("wsd.view.gui.text.338"); //$NON-NLS-1$

	String TABLE_CELL_HEIGHT = Messages.getString("wsd.view.gui.text.339"); //$NON-NLS-1$

	String TABLE_CELL_WIDTH = Messages.getString("wsd.view.gui.text.340"); //$NON-NLS-1$

	String TABLE_CELL_AUTO_SIZE = Messages.getString("wsd.view.gui.text.341"); //$NON-NLS-1$

	String[] TABLE_CELL_AUTO_SIZE_LIST =
	{
			Messages.getString("wsd.view.gui.text.342"), Messages.getString("wsd.view.gui.text.343") }; //$NON-NLS-1$ //$NON-NLS-2$

	String TABLE_CELL_CONTENT_ALIGN = Messages
			.getString("wsd.view.gui.text.344"); //$NON-NLS-1$

	String[] TABLE_CELL_CONTENT_ALIGN_LIST =
	{
			Messages.getString("wsd.view.gui.text.345"), Messages.getString("wsd.view.gui.text.346"), Messages.getString("wsd.view.gui.text.347") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	String TABLE_CELL_CONTENT_INDENT_TITLE = Messages
			.getString("wsd.view.gui.text.348"); //$NON-NLS-1$

	String TABLE_CELL_START_INDENT = Messages
			.getString("wsd.view.gui.text.349"); //$NON-NLS-1$

	String TABLE_CELL_END_INDENT = Messages.getString("wsd.view.gui.text.350"); //$NON-NLS-1$

	String TABLE_CELL_BEFORE_INDENT = Messages
			.getString("wsd.view.gui.text.351"); //$NON-NLS-1$

	String TABLE_CELL_AFTER_INDENT = Messages
			.getString("wsd.view.gui.text.352"); //$NON-NLS-1$

	/************ 表格属性菜单 ************** 结束 ********************/

	/************ 块容器属性菜单 ************** 开始 ********************/
	// BlockContainerPropertyPanel类
	String BLOCK_CONTAINER_PROPERTY = Messages
			.getString("wsd.view.gui.text.353"); //$NON-NLS-1$

	String BLOCK_CONTAINER_TOP = Messages.getString("wsd.view.gui.text.354"); //$NON-NLS-1$

	String BLOCK_CONTAINER_LEFT = Messages.getString("wsd.view.gui.text.355"); //$NON-NLS-1$

	String BLOCK_CONTAINER_HEIGHT = Messages.getString("wsd.view.gui.text.356"); //$NON-NLS-1$

	String BLOCK_CONTAINER_WIDTH = Messages.getString("wsd.view.gui.text.357"); //$NON-NLS-1$

	String BLOCK_CONTAINER_CONTENT_ALIGN = Messages
			.getString("wsd.view.gui.text.358"); //$NON-NLS-1$

	String[] BLOCK_CONTAINER_CONTENT_ALIGN_LIST =
	{
			Messages.getString("wsd.view.gui.text.359"), Messages.getString("wsd.view.gui.text.360"), Messages.getString("wsd.view.gui.text.361") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	String BLOCK_CONTAINER_OVER_FLOW = Messages
			.getString("wsd.view.gui.text.362"); //$NON-NLS-1$

	String[] BLOCK_CONTAINER_OVER_FLOW_LIST =
	{
			Messages.getString("wsd.view.gui.text.363"), Messages.getString("wsd.view.gui.text.364") }; //$NON-NLS-1$ //$NON-NLS-2$

	String BLOCK_CONTAINER_AUTO_RESIZE = Messages
			.getString("wsd.view.gui.text.365"); //$NON-NLS-1$

	/************ 块容器属性菜单 ************** 结束 ********************/

	/************ 版心边距面板菜单 ************** 开始 ********************/
	// MarginPanel
	String MARGIN_TOP = Messages.getString("wsd.view.gui.ribbon.margin.0"); //$NON-NLS-1$

	String MARGIN_BOTTOM = Messages.getString("wsd.view.gui.ribbon.margin.1"); //$NON-NLS-1$

	String MARGIN_LEFT = Messages.getString("wsd.view.gui.ribbon.margin.2"); //$NON-NLS-1$

	String MARGIN_RIGHT = Messages.getString("wsd.view.gui.ribbon.margin.3"); //$NON-NLS-1$

	/************ 版心边距面板菜单 ************** 结束 ********************/

	/************ 目录菜单 ************** 开始 ********************/
	// IndexBand
	/**
	 * 目录级别
	 */
	String[] INDEX_LEVEL =
	{
			Messages.getString("UiText.0"), Messages.getString("UiText.1"), Messages.getString("UiText.2"), Messages.getString("UiText.3"), Messages.getString("UiText.4"), Messages.getString("UiText.5"), Messages.getString("UiText.6"), }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$

	String[] INDEX_STYLE =
	{
			Messages.getString("UiText.7"), Messages.getString("UiText.8"), Messages.getString("UiText.9") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

	// IndexDialog
	String INDEX_SET_TITLE = Messages.getString("UiText.12"); //$NON-NLS-1$

	// IndexListPanel
	String INDEX_LIST_TITLE = Messages.getString("UiText.13"); //$NON-NLS-1$

	String INDEX_LEADER_PATTERN_LABEL = Messages.getString("UiText.16"); //$NON-NLS-1$

	String INDEX_SHOW_LEVEL_LABEL = Messages.getString("UiText.17"); //$NON-NLS-1$

	String INDEX_STYLE_BORDER_TITLE = Messages.getString("UiText.18"); //$NON-NLS-1$

	String[] INDEX_LEADER_PATTERN_POSITION =
	{
			Messages.getString("UiText.19"), Messages.getString("UiText.20"), Messages.getString("UiText.21") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	// IndexStylePropertyPanel
	String INDEX_FONT_PROPERTY = Messages.getString("UiText.22"); //$NON-NLS-1$

	String INDEX_PARAGRAPH_PROPERTY = Messages.getString("UiText.23"); //$NON-NLS-1$

	String INDEX_PROPERTY_DESCRIPTION = Messages.getString("UiText.24"); //$NON-NLS-1$

	// ChartAxisLabelBand
	String AXIS_LABEL_BAND_TITLE = Messages
			.getString("wisedoc.chart.axis.label"); //$NON-NLS-1$

	String AXIS_LABEL_BAND_VALUE_LABEL = Messages
			.getString("wisedoc.chart.axis.label.value"); //$NON-NLS-1$

	String AXIS_LABEL_BAND_SERIES_LABEL = Messages
			.getString("wisedoc.chart.axis.label.series"); //$NON-NLS-1$

	// ChartBasicTask
	String BASIC_TASK_LABEL = Messages.getString("wisedoc.chart.basic.task"); //$NON-NLS-1$

	// ChartBorderAndBackgroundBand
	String BORDER_AND_BACK_BAND_TITLE = Messages
			.getString("wisedoc.chart.border.title"); //$NON-NLS-1$

	String BORDER_AND_BACK_BAND_LAYER = Messages
			.getString("wisedoc.chart.border.layer"); //$NON-NLS-1$

	String BORDER_AND_BACK_BAND_APAPH = Messages
			.getString("wisedoc.chart.background.apaph"); //$NON-NLS-1$

	String BORDER_AND_BACK_BAND_FORE_APAPH = Messages
			.getString("wisedoc.chart.foreground.apaph"); //$NON-NLS-1$

	String BORDER_AND_BACK_BAND_BORDER = Messages
			.getString("wisedoc.chart.border"); //$NON-NLS-1$

	// ChartBoundBand
	String BOUND_BAND_TITLE = Messages.getString("wisedoc.chart.bound.title"); //$NON-NLS-1$

	String BOUND_BAND_HEIGHT = Messages.getString("wisedoc.chart.bound.height"); //$NON-NLS-1$

	String BOUND_BAND_WIGHT = Messages.getString("wisedoc.chart.bound.wight"); //$NON-NLS-1$

	// ChartIndicatorLabelBand
	String INDICATOR_LABEL_BAND_TITLE = Messages
			.getString("wisedoc.chart.indicator.title"); //$NON-NLS-1$

	String INDICATOR_LABEL_BAND_LOCATION = Messages
			.getString("wisedoc.chart.indicator.location"); //$NON-NLS-1$

	// ChartMarginBand
	String MARGIN_BAND_TITLE = Messages.getString("wisedoc.chart.margin.title"); //$NON-NLS-1$

	// ChartSeriesStyleBand

	String SERIES_STYLE_BAND_DEGREE = Messages
			.getString("wisedoc.chart.series.style.degree"); //$NON-NLS-1$

	// ChartShowOrHiddenBand
	String SHOW_OR_HIDDEN_BAND_TITLE = Messages
			.getString("wisedoc.chart.show.hidden.title"); //$NON-NLS-1$

	String SHOW_OR_HIDDEN_BAND_BASELINE = Messages
			.getString("wisedoc.chart.show.hidden.baseline"); //$NON-NLS-1$

	String SHOW_OR_HIDDEN_BAND_3D = Messages
			.getString("wisedoc.chart.show.hidden.3d"); //$NON-NLS-1$

	String SHOW_OR_HIDDEN_BAND_VALUE = Messages
			.getString("wisedoc.chart.show.hidden.value"); //$NON-NLS-1$

	String SHOW_OR_HIDDEN_BAND_AXIS_Y = Messages
			.getString("wisedoc.chart.show.hidden.axis.y"); //$NON-NLS-1$

	String SHOW_OR_HIDDEN_BAND_AXIS_X = Messages
			.getString("wisedoc.chart.show.hidden.axis.x"); //$NON-NLS-1$

	String SHOW_OR_HIDDEN_BAND_AXIS_DEPTH = Messages
			.getString("wisedoc.chart.show.hidden.depth"); //$NON-NLS-1$

	String SHOW_OR_HIDDEN_BAND_AXIS_ZERO = Messages
			.getString("wisedoc.chart.show.hidden.zero"); //$NON-NLS-1$

	String SHOW_OR_HIDDEN_BAND_AXIS_NULL = Messages
			.getString("wisedoc.chart.show.hidden.null"); //$NON-NLS-1$

	// ChartTitleBand
	String TITLE_BAND_TITLE = Messages.getString("wisedoc.chart.title"); //$NON-NLS-1$

	String TITLE_BAND_BAR = Messages.getString("wisedoc.chart.type.bar"); //$NON-NLS-1$

	String TITLE_BAND_PIE = Messages.getString("wisedoc.chart.type.pie"); //$NON-NLS-1$

	String TITLE_BAND_LINE = Messages.getString("wisedoc.chart.type.line");// "折线图" //$NON-NLS-1$

	String CHART_TYPE = Messages.getString("chart.type");

	String CHART_TITLE = Messages.getString("chart.title");

	// ;

	String TITLE_BAND_STACKBAR = Messages
			.getString("wisedoc.chart.type.stackbar");// "股价图"; //$NON-NLS-1$

	String TITLE_BAND_AREA = Messages.getString("wisedoc.chart.type.area");// "面积图" //$NON-NLS-1$

	// ;

	// ChartTypeTask
	String TYPE_TASK_TITLE = Messages.getString("wisedoc.chart.type.title"); //$NON-NLS-1$

	// ChartXYAxisTask
	String XY_AXIS_TASK_TITLE = Messages.getString("wisedoc.chart.axis.title"); //$NON-NLS-1$

	// ChartYAxisgrAduateBand
	String YAXIS_ADUATE_BAND_TITLE = Messages
			.getString("wisedoc.chart.axisy.title"); //$NON-NLS-1$

	String YAXIS_ADUATE_BAND_MIN = Messages
			.getString("wisedoc.chart.aduate.min"); //$NON-NLS-1$

	String YAXIS_ADUATE_BAND_MAX = Messages
			.getString("wisedoc.chart.aduate.max"); //$NON-NLS-1$

	String YAXIS_ADUATE_BAND_STEP = Messages
			.getString("wisedoc.chart.aduate.step"); //$NON-NLS-1$

	String YAXIS_ADUATE_BAND_RANGE = Messages
			.getString("wisedoc.chart.aduate.range"); //$NON-NLS-1$

	String YAXIS_ADUATE_BAND_OFFSET = Messages
			.getString("wisedoc.chart.aduate.offset"); //$NON-NLS-1$

	String YAXIS_ADUATE_BAND_ORI = Messages
			.getString("wisedoc.chart.aduate.ori"); //$NON-NLS-1$

	String YAXIS_ADUATE_AUTO = Messages.getString("wisedoc.chart.aduate.auto");

	// PieChartBasicBand
	String PIE_BASIC_BAND_TITLE = Messages.getString("wisedoc.chart.pie.title"); //$NON-NLS-1$

	String PIE_BASIC_TASK_TITLE = Messages
			.getString("wisedoc.chart.pie.task.title");

	String PIE_BASIC_BAND_ORI = Messages.getString("wisedoc.chart.pie.ori"); //$NON-NLS-1$

	String PIE_BASIC_BAND_SHOW_LABEL = Messages
			.getString("wisedoc.chart.pie.show.label"); //$NON-NLS-1$

	String PIE_BASIC_BAND_SHOW_PER = Messages
			.getString("wisedoc.chart.pie.show.per"); //$NON-NLS-1$

	String PIE_BASIC_BAND_SHOW_FACT = Messages
			.getString("wisedoc.chart.pie.show.fact"); //$NON-NLS-1$

	String PIE_BASIC_BAND_SHOW_START_DEGREE = Messages
			.getString("wisedoc.chart.pie.start.degree"); //$NON-NLS-1$

	// SeriesAndValueCountBand
	String COUNT_BAND_TITLE = Messages
			.getString("wisedoc.chart.series.value.style.title"); //$NON-NLS-1$

	String COUNT_BAND_VALUE = Messages.getString("wisedoc.chart.value.count"); //$NON-NLS-1$

	String COUNT_BAND_SERIES = Messages.getString("wisedoc.chart.series.count"); //$NON-NLS-1$

	// SeriesStypeDialog
	String SERIES_STYLE_DIALOG_TITLE = Messages
			.getString("wsd.series.style.dialog.title"); //$NON-NLS-1$

	String SERIES_STYLE_DIALOG_VALUE_COLOR = Messages
			.getString("wsd.series.style.dialog.value.color"); //$NON-NLS-1$

	String SERIES_STYLE_DIALOG_SERIES_COLOR = Messages
			.getString("wsd.series.style.dialog.series.color"); //$NON-NLS-1$

	String ADD = Messages.getString("UiText.78"); //$NON-NLS-1$

	String DELETE = Messages.getString("UiText.79"); //$NON-NLS-1$

	String SET_GROUP_CONDITION = Messages.getString("UiText.80"); //$NON-NLS-1$

	String CURRENT_GROUP_NODE = Messages.getString("UiText.81"); //$NON-NLS-1$

	String MAX_REPEAT_TIMES = Messages.getString("UiText.group.maxnumber");

	String EDIT = Messages.getString("UiText.82"); //$NON-NLS-1$

	String SORT = Messages.getString("UiText.83"); //$NON-NLS-1$

	String CONDITION = Messages.getString("UiText.84"); //$NON-NLS-1$

	/************ 目录菜单 ************** 结束 ********************/
	/*********************** 插入特殊字符相关Message开始 ***********/
	String SPECIALCHARACTER_TITLE = Messages
			.getString("wsd.view.gui.ribbon.insert.chars.title");

	String SPECIALCHARACTER_BAND_TITLE = Messages
			.getString("wsd.view.gui.ribbon.insert.chars.band.title");

	String SPECIALCHARACTER_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.insert.chars.button.title");

	String CHECKBOX_BUTTON_TITLE = Messages
			.getString("wsd.view.gui.ribbon.insert.checkbox.button.title");

	/*********************** 插入特殊字符相关Message结束 ***********/
	String DATA_SOURCE_NAME = Messages.getString("datasourcename");

	String OLD_DATA = Messages.getString("olddata");

	String VIEW_AS = Messages.getString("viewas");

	String SELECT_DATASOURCE_INNER = Messages
			.getString("select.datasource.inner");

	String SPECIFIED_STRING = Messages.getString("specifiedstring");

	String NULL_DATA = Messages.getString("nulldata");

	String VIEW_AS_OLD_DATA = Messages.getString("viewasolddata");

	String ADD_ROW = Messages.getString("addrow");

	String DELETE_ROW = Messages.getString("deleterow");

	String OK = Messages.getString("ok");

	String TESTCONNECTION = Messages.getString("testconnection");

	String CANCLE = Messages.getString("cancle");

	String RESET = Messages.getString("reset");

	String IF_MODIFIED_ROOTELEMENT = Messages
			.getString("ifmodifiedrootelement");

	String LOST_TREE_DATA = Messages.getString("losttreedata");

	String LOST_NOT_TREE_DATA = Messages.getString("lostnottreedata");

	String SET_DATA = Messages.getString("setdata");

	String SORT_ORDER = Messages.getString("sortorder");

	String HORIZONTAL_SPLICING = Messages.getString("horizontalsplicing");

	String EQUAL_HORIZONTAL_SPLICING = Messages
			.getString("equalhorizontalsplicing");

	String VERTICAL_MOSAIC = Messages.getString("verticalmosaic");

	String SET_DATASOURCE_PATH = Messages.getString("setdatasourcepath");

	String KEY_COLUMN_NUMBER = Messages.getString("keycolumnnumber");

	String ROOTELEMENT_NAME = Messages.getString("rootelementname");

	String DATA_TYPE = Messages.getString("datatype");

	String ADD_COLUMN = Messages.getString("addcolumn");

	String DELETE_COLUMN = Messages.getString("deletecolumn");

	String CHECK_DATASOURCE = Messages.getString("checkdatasource");

	String ADD_DATA = Messages.getString("adddata");

	String LOAD_DATA = Messages.getString("loaddata");

	String NULL_DATA_TREAT = Messages.getString("nulldatatreat");

	String HIDDEN_ALL = Messages.getString("overflowhidden");

	String HIDDEN_OVERFLOW = Messages.getString("overflowvisible");

	String ONELINE_MAX_NUMBER = Messages.getString("onelinemaxnumber");

	String OVERFLOW_CONDITION = Messages.getString("overflowcondition");

	String OVERFLOW_TREAT = Messages.getString("overflowtreat");

	String MAX_LINES = Messages.getString("maxlines");

	String LEAD_CHARACTER = Messages.getString("leadcharacter");

	String FIRST_AND_LAST_CHARACTER = Messages
			.getString("firstandlastcharacter");

	String REAR_CHARACTER = Messages.getString("rearcharacter");

	String CONTENT_TREAT = Messages.getString("contenttreat");

	String XIAOYUDENGYU = Messages.getString("xiaoyu");

	String DAYU = Messages.getString("buxiaoyu");

	String CONTENT_VIEW_CONDITION = Messages.getString("contentviewcondition");

	String CONTENT_NULL_VIEW_AS = Messages.getString("contentnullviewas");

	String CONTENT_ADD = Messages.getString("contentadd");

	String FIRST_DIFFENT = Messages.getString("firstdiffent");

	String LAST_DIFFENT = Messages.getString("lastdiffent");

	String ODD_EVEN_DIFFENT = Messages.getString("oddevendiffent");

	String BLANK_NOBLANK_DIFFENT = Messages.getString("blanknoblankdiffent");

	String CURRENT_PAGE_SEQUENCE_PSM = Messages
			.getString("currentpagesequencepsm");

	String FIRST_SPM = Messages.getString("firstspm");

	String LAST_SPM = Messages.getString("lastspm");

	String REST_SPM = Messages.getString("restspm");

	String ODD_SPM = Messages.getString("oddspm");

	String EVEN_SPM = Messages.getString("evenspm");

	String ONE_SPM = Messages.getString("onespm");

	String OTHER_SPM = Messages.getString("otherspm");

	String BLANK_SPM = Messages.getString("blankspm");

	String SET_SIMPLE_PAGE_SEQUENCE_PSM = Messages
			.getString("setsimplepagesequencepsm");

	String REGION_BEFORE_APPEAR_FIRST = Messages
			.getString("wsd.view.gui.ribbon.427");

	String REGION_AFTER_APPEAR_FIRST = Messages
			.getString("wsd.view.gui.ribbon.440");

	String SET_REGION_BEFORE_CONTENT = Messages
			.getString("setregionbeforecontent");

	String SET_REGION_AFTER_CONTENT = Messages
			.getString("setregionaftercontent");

	String SET_REGION_START_CONTENT = Messages
			.getString("setregionstartcontent");

	String SET_REGION_END_CONTENT = Messages.getString("setregionendcontent");

	String REMOVE_REGION_BEFORE_CONTENT = Messages
			.getString("removeregionbeforecontent");

	String REMOVE_REGION_AFTER_CONTENT = Messages
			.getString("removeregionaftercontent");

	String REMOVE_REGION_START_CONTENT = Messages
			.getString("removeregionstartcontent");

	String REMOVE_REGION_END_CONTENT = Messages
			.getString("removeregionendcontent");

	String REGION_BEFORE_HEIGHT = Messages.getString("regionbeforeheight");

	String REGION_AFTER_HEIGHT = Messages.getString("regionafterheight");

	String REGION_START_WIDTH = Messages.getString("regionstartwidth");

	String REGION_END_WIDTH = Messages.getString("regionendwidth");

	String ORIENTATION_LABEL = Messages.getString("orientation");

	String TEXT_DIRECTION = Messages.getString("textdirection");

	String DISPLAY_ALIGN = Messages.getString("dispalyalign");

	String CONTENT_OVERFLOW = Messages.getString("contentoverflow");

	String BACKGROUND = Messages.getString("background");

	String CREATE_DATA_SOURCE = Messages.getString("select.datasource.inner");

	String REFERENCE_ORIENTATION = Messages.getString("referenceorientation");

	// String OK = Messages.getString("ok");
	// String OK = Messages.getString("ok");
	String SQL_ADDSUB = Messages.getString("sql.addsub");

	String SQL_ADDNEXT = Messages.getString("sql.addnext");

	String SQL_DEL = Messages.getString("sql.del");

	String SQL_IMPORT = Messages.getString("sql.import");

	String SQL_TITLE = Messages.getString("sql.title");

	String SQL_CONNECTION_SET = Messages.getString("sql.connection.set");

	String SQL_CONNECTION_BORDERTITLE = Messages
			.getString("sql.connection.bordertitle");

	String SQL_CONNECTION_ERROR = Messages.getString("sql.connection.error");

	String FIRST_USE = Messages.getString("firstuse");

	String LAST_USE = Messages.getString("lastuse");

	String ODD_USE = Messages.getString("odduse");

	String EVEN_USE = Messages.getString("evenuse");

	String BLANK_USE = Messages.getString("blankuse");

	String REST_USE = Messages.getString("restuse");

	String ONE_USE = Messages.getString("oneuse");

	String MORE_USE = Messages.getString("moreuse");

	String POSITION_USE = Messages.getString("positionuse");

	String CONDITION_USE = Messages.getString("conditionuse");

	String USESMPANDPROPERTY = Messages.getString("usesmpandproperty");

	String SETPROPERTY = Messages.getString("setproperty");

	String FIRST_USE_SPM = Messages.getString("firstusespm");

	String LAST_USE_SPM = Messages.getString("lastusespm");

	String ODD_USE_SPM = Messages.getString("oddusespm");

	String EVEN_USE_SPM = Messages.getString("evenusespm");

	String BLANK_USE_SPM = Messages.getString("blankusespm");

	String REST_USE_SPM = Messages.getString("restusespm");

	String ONE_USE_SPM = Messages.getString("oneusespm");

	String MORE_USE_SPM = Messages.getString("moreusespm");
	
	String THIS_USE_SPM = Messages.getString("thisusespm");

	String CONDITION_USE_SPM = Messages.getString("conditionusespm");

	String POSITION_USE_SPM = Messages.getString("positionusespm");

	String USENUMBER_SPM = Messages.getString("usenumberspm");

	String BUXIANZAHIYESHU = Messages.getString("buxianzhiyeshu");

	String ZHIDINGYESHU = Messages.getString("zhidingyeshu");
}
