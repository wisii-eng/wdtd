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
 * @QianZhangSelectHandler.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.mousehandler;


import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import com.wisii.wisedoc.document.QianZhang;
import com.wisii.wisedoc.view.cursor.CursorManager;
import com.wisii.wisedoc.view.cursor.CursorManager.CursorType;
import com.wisii.wisedoc.view.select.SelectionModel;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2011-11-15
 */
public class QianZhangSelectHandler extends SelectHandler
{

	private QianZhang qianzhang;

	@Override
	public void mouseMoved(MouseEvent e)
	{
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if (e.isConsumed())
			return;
		processSelection(e);
		e.consume();
	}

	/** 该方法主要用于处理表中单元格、行的选择处理 */
	private void processSelection(MouseEvent e)
	{
		int count = e.getClickCount();
		if (count != 1)
			return;
		SelectionModel model = getSelectionModel();
		model.clearSelection();
		System.out.println("qianzhang:"+qianzhang);
		model.addObjectSelection(qianzhang);
	}

	public boolean isInControl(MouseEvent e)
	{
		int modefies=e.getModifiers();
		//只有同时按住CTRL和ALT键时才容许选中章
		if((modefies&ActionEvent.CTRL_MASK)==0||(modefies&ActionEvent.ALT_MASK)==0)
		{
			return false;
		} 
		qianzhang = _editor.getQianZhang(e.getPoint());
		boolean isin = qianzhang != null;
		if(isin){
			_editor.setCursor(CursorManager.getSytemCursor(CursorType.HAND_CURSOR));
		}
		return isin;

	}

}
