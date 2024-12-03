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
 * @CopyTextAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Group;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.Text;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.swing.ClipboardSupport;
import com.wisii.wisedoc.view.select.DocumentPositionRange;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：只粘贴文本,不粘贴其中的样式
 * 
 * 作者：zhangqiang 创建日期：2008-10-17
 */
public class PasteTextAction extends BaseAction
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.view.action.BaseAction#doAction(java.awt.event.ActionEvent
	 * )
	 */
	public void doAction(ActionEvent e)
	{
		final Document doc = getCurrentDocument();
		final DocumentPositionRange range = getSelect();
		final DocumentPosition pos = getCaretPosition();
		String text = ClipboardSupport.readTextFromClipboard();
		if (text != null && !text.isEmpty())
		{
			if (text.contains("\n"))
			{
				if (range != null)
				{
					DocumentPosition startpos = range.getStartPosition();
					CellElement startleaf = startpos.getLeafElement();
					// 如果当前选中范围的起始点在一个Inline重复组中，则不能粘贴文本
					if (startleaf instanceof Inline
							&& startleaf.getParent() instanceof Group)
					{
						SystemManager.getMainframe().setStatus(
								RibbonUIText.FRAME_STATUS_UNABLE_INPUT);
						return;
					}
					DocumentPosition endpos = range.getEndPosition();
					CellElement endleaf = endpos.getLeafElement();
					// 如果当前选中范围的结束点在一个Inline重复组中，则不能粘贴文本
					if (endleaf instanceof Inline
							&& endleaf.getParent() instanceof Group)
					{
						SystemManager.getMainframe().setStatus(
								RibbonUIText.FRAME_STATUS_UNABLE_INPUT);
						return;
					}
				} else if (pos != null)
				{
					CellElement leafelement = pos.getLeafElement();
					// 如果当前光标所表示的元素在一个Group类，则表示时一个Inline
					// Group，此时不容许输入回车
					if (leafelement instanceof Inline
							&& leafelement.getParent() instanceof Group)
					{
						SystemManager.getMainframe().setStatus(
								RibbonUIText.FRAME_STATUS_UNABLE_INPUT);
						return;
					}
				}

			}
			if (range != null)
			{
				final List<CellElement> elemt = new ArrayList<CellElement>();
				final int size = text.length();
				Attributes att = null;
				final CellElement element = range.getStartPosition()
						.getLeafElement();
				if (element instanceof Inline)
					att = element.getAttributes();
				for (int i = 0; i < size; i++)
				{
					elemt.add(new TextInline(new Text(text.charAt(i)), att
							.getAttributes()));
				}
				doc.replaceElements(range, elemt);
				getEditorPanel().getSelectionModel().clearSelection();
			} else if (pos != null)
			{

				final CellElement element = pos.getLeafElement();
				Attributes att = null;
				if (element instanceof Inline)
					att = element.getAttributes();
				Map<Integer, Object> attmap = null;
				if (att != null)
					attmap = att.getAttributes();
				doc.insertString(text, pos, attmap);
				getEditorPanel().getSelectionModel().clearSelection();
			}
		}
	}

	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		String text = ClipboardSupport.readTextFromClipboard();
		return text != null && !text.isEmpty();
	}
}
