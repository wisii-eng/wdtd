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
 * @SelectBlockAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.select;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.view.AbstractEditComponent;

/**
 * 类功能描述：段落的选中处理类
 * 
 * 作者：zhangqiang 创建日期：2008-9-23
 */
public class SelectBlockAction extends AbstractAction
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		if (source != null && source instanceof AbstractEditComponent)
		{
			AbstractEditComponent docpanel = (AbstractEditComponent) source;
			DocumentPosition pos = docpanel.getCaretPosition();
			if (pos != null)
			{
				Element element = pos.getLeafElement();
				if (element instanceof Inline)
				{
					while (element != null && !(element instanceof Block))
					{
						element = element.getParent();
					}
					if (element != null && element instanceof Block)
					{
						CellElement first = (CellElement) element.getChildAt(0);
						CellElement last = (CellElement) element
								.getChildAt(element.getChildCount() - 1);
						if (first!= null&&first instanceof Inline && last instanceof Inline)
						{
							SelectionModel selmodel = docpanel
									.getSelectionModel();
							/* 【修改】by 李晓光2008-12-10  为Position添加偏移量信息 */
							DocumentPosition startpos = new DocumentPosition(first, true, 1);
							startpos.setPageIndex(pos.getPageIndex());
							DocumentPosition endpos = new DocumentPosition(last, false, (element.getChildCount()));
							endpos.setPageIndex(pos.getPageIndex());
							DocumentPositionRange range = DocumentPositionRange
									.creatSelectCell(startpos, endpos);
							selmodel.setSelection(range);
						}

					}
				}
			}
		}
	}

}
