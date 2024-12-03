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
 * @DefaultSelectHandler.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.mousehandler;

import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import com.wisii.wisedoc.view.AbstractEditComponent;
import com.wisii.wisedoc.view.cursor.CursorManager;
import com.wisii.wisedoc.view.cursor.CursorManager.CursorType;
import com.wisii.wisedoc.view.menu.PopupMenuHandler;
import com.wisii.wisedoc.view.select.ContainerSelectMouseHandler;
import com.wisii.wisedoc.view.select.SVGSelectMouseHandler;
import com.wisii.wisedoc.view.select.TableSelectMouseHandler;
import com.wisii.wisedoc.view.select.ZiMobanSelectHandler;

/**
 * 类功能描述：根据当前文档的操作状态，负责鼠标事件的分发
 * 
 * 
 */
public class DefaultMouseHandler extends AbstractMouseHandler implements
		FocusListener
{

	MouseHandler _currenthandler;
	//	
	List<MouseHandler> _handlers;
	SizeHandler sizehandler;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.view.select.SelectHandler#install(com.wisii.wisedoc
	 * .view.AbstractEditComponent)
	 */
	public void install(AbstractEditComponent c)
	{
		super.install(c);
		c.addMouseListener(this);
		c.addMouseMotionListener(this);
		c.addFocusListener(this);
		installAllHanlers(c);

	}

	private void installAllHanlers(AbstractEditComponent c)
	{
		_currenthandler = null;
		if (_handlers == null)
		{
			_handlers = new ArrayList<MouseHandler>();
			MouseHandler inserthanler = new InsertObjectHandler();
			_handlers.add(inserthanler);
			MouseHandler qianzhanghanler = new InsertQianZhangHandler();
			_handlers.add(qianzhanghanler);
			MouseHandler qianzhangselecthanler = new QianZhangSelectHandler();
			_handlers.add(qianzhangselecthanler);
			MouseHandler zimobanhanler = new ZiMobanSelectHandler();
			_handlers.add(zimobanhanler);
			sizehandler = new SizeHandler();
			_handlers.add(sizehandler);
			SVGSelectMouseHandler svgSelect = new SVGSelectMouseHandler();
			_handlers.add(svgSelect);
			TableSelectMouseHandler tableSelectHandler = new TableSelectMouseHandler(
					c);
			_handlers.add(tableSelectHandler);
			
			ContainerSelectMouseHandler containerSelect = new ContainerSelectMouseHandler();
			_handlers.add(containerSelect);
			MouseHandler selecthandler = new SelectHandler();
			_handlers.add(selecthandler);

		}
		// else
		// {
		int size = _handlers.size();
		for (int i = 0; i < size; i++)
		{
			_handlers.get(i).install(c);
		}
		// }

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.view.select.SelectHandler#deinstall(com.wisii.wisedoc
	 * .view.AbstractEditComponent)
	 */
	public void deinstall(AbstractEditComponent c)
	{
		super.deinstall(c);
		c.removeMouseListener(this);
		c.removeMouseMotionListener(this);
		c.removeFocusListener(this);
		deinstallAllHanlers(c);

	}

	private void deinstallAllHanlers(AbstractEditComponent c)
	{
		int size = _handlers.size();
		for (int i = 0; i < size; i++)
		{
			_handlers.get(i).deinstall(c);
		}
		_handlers = null;
		_currenthandler = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e)
	{
		_currenthandler.mouseClicked(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e)
	{
		resetHandler(e);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e)
	{
		// _currenthandler = null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e)
	{
		_currenthandler.mousePressed(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e)
	{
		if (SwingUtilities.isRightMouseButton(e))
		{
			JPopupMenu popupmenu = PopupMenuHandler.getCurrentPopupMenu();
			if (popupmenu != null)
			{
				MouseEvent event = SwingUtilities.convertMouseEvent(e
						.getComponent(), e, _editor);
				popupmenu.show(_editor, event.getX(), event.getY());
			}
			InsertManager.clearType();
			return;
		}
		_currenthandler.mouseReleased(e);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent
	 * )
	 */
	public void mouseDragged(MouseEvent e)
	{
		_currenthandler.mouseDragged(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent e)
	{
		resetHandler(e);
	}

	private void resetHandler(MouseEvent e)
	{
		int size = _handlers.size();
		for (int i = 0; i < size; i++)
		{
			MouseHandler handler = _handlers.get(i);
			if (handler.isInControl(e))
			{
				if (handler != _currenthandler)
				{
					_currenthandler = handler;
					_currenthandler.init();
				}
				if (_currenthandler.getClass() != TableSelectMouseHandler.class)
					_editor.setCursor(CursorManager
							.getSytemCursor(CursorType.TEXT_CURSOR));
				break;
			}
		}
		_currenthandler.mouseMoved(e);

	}

	public void paint(Graphics g)
	{
		sizehandler.paintResizePort(g);
		if (_currenthandler != null)
		{
			_currenthandler.paint(g);
		}
	}

	public void focusGained(FocusEvent e)
	{

	}

	public void focusLost(FocusEvent e)
	{
//		InsertManager.clearType();
	}

	public boolean isInControl(MouseEvent event)
	{
		return _currenthandler == sizehandler;
	}
	public MouseHandler getCurrentHandler()
	{
		// TODO Auto-generated method stub
		return _currenthandler;
	}
}
