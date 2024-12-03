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
 * @BaseAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.InlineContent;
import com.wisii.wisedoc.document.Text;
import com.wisii.wisedoc.view.AbstractEditComponent;
import com.wisii.wisedoc.view.select.DocumentPositionRange;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.update.RibbonUpdateManager;

/**
 * 类功能描述：
 * 
 * 作者：李晓光 创建日期：2007-6-8
 */
public abstract class BaseAction extends AbstractAction
{

	/**
	 * 接受XML文件中的Param信息
	 */
	private String param = "";

	/**
	 * 
	 * 用默认的字符串和默认的图标定义一个Action对象。
	 */
	public BaseAction()
	{
		super();
	}

	/**
	 * 
	 * 用指定描述字符串和默认图标定义一个 Action 对象。
	 * 
	 * @param name
	 *            指定Action的名字。
	 */
	public BaseAction(String name)
	{
		super(name);
	}

	/**
	 * 
	 * 用指定描述字符串指定图标定义一个 Action 对象。
	 * 
	 * @param name
	 *            指定Action的名字。
	 * @param icon
	 *            指定Action的图标。
	 */
	public BaseAction(String name, Icon icon)
	{
		super(name, icon);
	}

	/*
	 * 
	 * 处理事件，然后更新事件类状态
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @exception
	 */
	public final void actionPerformed(ActionEvent e)
	{
		// 先校验值是否合法。
		if (!checkSetting(e))
		{
			return;
		}
		doAction(e);
		resetActionState();
		AbstractEditComponent editpanel = getEditorPanel();
		if (editpanel != null)
		{
			editpanel.requestFocus();
		}
	};

	/**
	 * 
	 * 用来校验设置的值是否合法，需要校验操作是否合法时需要覆写该方法。
	 * 
	 * @param
	 * @return boolean:合法则返回true，否则返回false
	 * @exception
	 */
	protected boolean checkSetting(ActionEvent e)
	{
		return true;
	}

	public abstract void doAction(ActionEvent e);

	/**
	 * 
	 * 重新设置时间的可用性
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	protected void resetActionState()
	{
		RibbonUIManager.updateUIAvailable();
	}

	/**
	 * 
	 * 获得当前布局
	 * 
	 * @return Layout 返回当前布局
	 */
	public Document getCurrentDocument()
	{
		AbstractEditComponent editor = getEditorPanel();
		if (editor != null)
		{
			return editor.getDocument();
		}
		return null;
	}

	/*
	 * 
	 * 获得通过流式方式选中的当前的选中范围对象 这种主要是在处理插入对象时， 如果有多个区域处于选择状态 则只替换最后操作的选中区域
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @exception
	 */
	protected DocumentPositionRange getSelect()
	{
		return getEditorPanel().getSelectionModel().getSelectionCell();
	}

	/*
	 * 
	 * 获得通过流式方式选中的对象
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @exception
	 */
	protected List<DocumentPositionRange> getSelects()
	{
		return getEditorPanel().getSelectionModel().getSelectionCells();
	}

	/*
	 * 
	 * 获得通过对象方式选中的对象
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @exception
	 */
	protected List<CellElement> getObjectSelects()
	{
		return getEditorPanel().getSelectionModel().getObjectSelection();
	}

	/*
	 * 
	 * 获得所有的选中对象，包括通过流式方式选中的对象和通过对象方式选中的对象
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @exception
	 */
	protected List<CellElement> getAllSelectObjects()
	{
		return getEditorPanel().getSelectionModel().getSelectedObject();
	}

	protected DocumentPosition getCaretPosition()
	{
		return getEditorPanel().getCaretPosition();
	}

	public AbstractEditComponent getEditorPanel()
	{
		return RibbonUpdateManager.Instance.getCurrentEditPanel();
	}

	/**
	 * 
	 * 判断当前按钮、或菜单不是可用的
	 * 
	 * @return boolean 如果是可用的则返回True，否则返回False
	 */
	public boolean isAvailable()
	{
		Document doc = getCurrentDocument();
		return doc != null;
	}

	/**
	 * 
	 * 是否有选择
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	protected boolean isSelected()
	{
		List<DocumentPositionRange> ranges = getSelects();
		if (ranges != null && !ranges.isEmpty())
		{
			return true;
		}
		List<CellElement> elements = getObjectSelects();
		if (elements != null && !elements.isEmpty())
		{
			return true;
		}
		return false;
	}

	/**
	 * @返回 param变量的值
	 */
	public String getParam()
	{
		return param;
	}

	/**
	 * @param param
	 *            设置param成员变量的值 值约束说明
	 */
	public void setParam(String param)
	{

		this.param = param;
	}

	protected boolean isBindingInline(CellElement cellment)
	{
		if (cellment instanceof Inline)
		{
			Inline inlne = (Inline) cellment;
			InlineContent textin = inlne.getContent();
			if (textin != null)
			{
				if (textin instanceof Text)
				{
					Text textinLine = (Text) textin;
					return textinLine.isBindContent();
				}
			}
		}
		return false;
	}
}
