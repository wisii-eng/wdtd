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
 * @SelectWordAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.select;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.InlineContent;
import com.wisii.wisedoc.document.Text;
import com.wisii.wisedoc.view.AbstractEditComponent;

/**
 * 类功能描述：词选中处理器类，默认的词选中处理类，目前只处理了西文单词的选中
 *
 * 作者：zhangqiang
 * 创建日期：2008-9-23
 */
public class SelectWordAction extends AbstractAction {

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent action) {
		Object source = action.getSource();
		if(source != null&&source instanceof AbstractEditComponent)
		{
			AbstractEditComponent edit = (AbstractEditComponent) source;
			DocumentPosition position = edit.getCaretPosition();
			if(position != null)
			{
				CellElement element = position.getLeafElement();
				if(element instanceof Inline)
				{
					Inline inline = (Inline) element;
					InlineContent content = inline.getContent();
//					如果绑定的是动态数据节点，则双击选中整个动态数据节点
					if(!(content instanceof Text&&!content.isBindContent()))
					{
						SelectionModel selmodel = edit.getSelectionModel();						
						DocumentPosition startpos = new DocumentPosition(inline, true);
						DocumentPosition endpos = new DocumentPosition(inline, false);
						DocumentPositionRange range =   DocumentPositionRange.creatSelectCell(startpos,endpos);
						selmodel.setSelection(range);
					}
				}
			}
		}

	}

}
