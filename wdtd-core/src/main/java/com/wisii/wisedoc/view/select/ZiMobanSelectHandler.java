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
 * @ZiMobanSelectHandler.java 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.select;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;
import java.awt.event.MouseEvent;
import com.wisii.wisedoc.document.ZiMoban;
import com.wisii.wisedoc.view.mousehandler.SelectHandler;

/**
 * 类功能描述： 子模板选择类，进去子哦办区域之后 子模板中的内容不能进行编辑，
 * 选择时要将子模板中的所有内容选中 
 * 作者：zhangqiang
 * 创建日期：2010-10-25
 */
public class ZiMobanSelectHandler extends SelectHandler
{
	private ZiMoban zimoban;

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
		model.addObjectSelection(zimoban);
	}

	public boolean isInControl(MouseEvent e)
	{
		zimoban = _editor.getZiMoban(e.getPoint());
		return zimoban != null;

	}
}
