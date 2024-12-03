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
 * @DocumentDragGestureRecignizer.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing;

import java.awt.dnd.DragSource;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import sun.awt.dnd.SunDragSourceContextPeer;

import com.wisii.wisedoc.document.AbstractGraphics;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableFObj;
import com.wisii.wisedoc.document.svg.AbstractSVG;
import com.wisii.wisedoc.view.AbstractEditComponent;
import com.wisii.wisedoc.view.mousehandler.InsertManager;
import com.wisii.wisedoc.view.mousehandler.MouseHandler;
import com.wisii.wisedoc.view.mousehandler.SizeHandler;
import com.wisii.wisedoc.view.select.DocumentPositionRange;
import com.wisii.wisedoc.view.select.SVGSelectMouseHandler;
import com.wisii.wisedoc.view.select.SelectionModel;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-10-27
 */
public class DocumentDragGestureRecignizer implements MouseListener,
		MouseMotionListener
{

	private MouseEvent dndArmedEvent = null;
	private AbstractEditComponent _editor;//修改by 李晓光   2009-2-18

	public DocumentDragGestureRecignizer(AbstractEditComponent editor)
	{
		_editor = editor;
	}

	private static int getMotionThreshold()
	{
		return DragSource.getDragThreshold();
	}

	protected int mapDragOperationFromModifiers(MouseEvent e)
	{
		int mods = e.getModifiersEx();

		if ((mods & InputEvent.BUTTON1_DOWN_MASK) != InputEvent.BUTTON1_DOWN_MASK)
		{
			return TransferHandler.NONE;
		}

		JComponent c = getComponent(e);
		TransferHandler th = c.getTransferHandler();
		return SunDragSourceContextPeer.convertModifiersToDropAction(mods, th
				.getSourceActions(c));
	}

	public void mouseClicked(MouseEvent e)
	{
	}

	public void mousePressed(MouseEvent e)
	{
		dndArmedEvent = null;
		if (isDragPossible(e)
				&& mapDragOperationFromModifiers(e) != TransferHandler.NONE)
		{
			dndArmedEvent = e;
//			SelectionModel select = _editor.getSelectionModel();
//			List<DocumentPositionRange> ranges = select.getSelectionCells();
//			if(ranges!=null&&!ranges.isEmpty())
//			{
//				e.consume();
//			}
		}
	}

	public void mouseReleased(MouseEvent e)
	{
		dndArmedEvent = null;
	}

	public void mouseEntered(MouseEvent e)
	{
		// dndArmedEvent = null;
	}

	public void mouseExited(MouseEvent e)
	{
		// if (dndArmedEvent != null && mapDragOperationFromModifiers(e) ==
		// TransferHandler.NONE) {
		// dndArmedEvent = null;
		// }
	}

	public void mouseDragged(MouseEvent e)
	{
		if (dndArmedEvent != null)
		{
			e.consume();

			int action = mapDragOperationFromModifiers(e);

			if (action == TransferHandler.NONE)
			{
				return;
			}

			int dx = Math.abs(e.getX() - dndArmedEvent.getX());
			int dy = Math.abs(e.getY() - dndArmedEvent.getY());
			if ((dx > getMotionThreshold()) || (dy > getMotionThreshold()))
			{
				// start transfer... shouldn't be a click at this point
				JComponent c = getComponent(e);
				TransferHandler th = c.getTransferHandler();
				th.exportAsDrag(c, dndArmedEvent, action);
				dndArmedEvent = null;
			}
		}
	}

	public void mouseMoved(MouseEvent e)
	{
	}

	/**
	 * Determines if the following are true:
	 * <ul>
	 * <li>the press event is located over a selection
	 * <li>the dragEnabled property is true
	 * <li>A TranferHandler is installed
	 * </ul>
	 * <p>
	 * This is implemented to check for a TransferHandler. Subclasses should
	 * perform the remaining conditions.
	 */
	protected boolean isDragPossible(MouseEvent e)
	{
		if (_editor.isDragEnabled() && _editor.isEditable())
		{
			// 如果用户选择了插入对象，则此时拖拽功能不可用
			if (InsertManager.getType() != InsertManager.NOTYPE)
			{
				return false;
			}
			MouseHandler currenthander = _editor.getMouseHandler()
					.getCurrentHandler();
			if ((currenthander instanceof SizeHandler)
					|| ((currenthander instanceof SVGSelectMouseHandler) && ((SVGSelectMouseHandler) currenthander)
							.canResize()))
			{
				return false;
			}
			DocumentPosition pos = _editor.pointtodocpos(e.getPoint());
			if (pos == null)
			{
				return false;
			}
			SelectionModel select = _editor.getSelectionModel();
			List<DocumentPositionRange> ranges = select.getSelectionCells();
			List<CellElement> objects = select.getObjectSelection();
			if ((ranges == null || ranges.isEmpty())
					&& (objects == null || objects.isEmpty()))
			{
				return false;
			}
			if (objects != null && !objects.isEmpty())
			{
				int size = objects.size();
				CellElement poselement = pos.getLeafElement();
				boolean ison = false;
				for (int i = 0; i < size; i++)
				{
					CellElement element = objects.get(i);
					// 如果选择的对象当中有表格子对象（如表格行，单元格等），则不可拖拽
					if (element instanceof TableFObj&&!(element instanceof Table))
					{
						return false;
					}
					if (element instanceof AbstractSVG)
					{
						element = (CellElement) element.getParent();
					}
					if (element instanceof AbstractGraphics)
					{
						element = (CellElement) element.getParent();
					}
					Element posparent = poselement;
					while (posparent != null && !(posparent instanceof Flow))
					{
						if (posparent == element)
						{
							ison = true;
						}
						posparent = posparent.getParent();
					}

				}
				if(ison)
				{
					return true;
				}
			}

			if (ranges != null && !ranges.isEmpty())
			{
				int size = ranges.size();
				for (int i = 0; i < size; i++)
				{
					DocumentPositionRange range = ranges.get(i);
					if (range.isPositionin(pos))
					{
						return true;
					}

				}
			}
		}
		return false;

	}

	protected JComponent getComponent(MouseEvent e)
	{
		return _editor;
	}

}
