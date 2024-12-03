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
 * @AbstractMouseHandler.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.mousehandler;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import com.wisii.wisedoc.view.AbstractEditComponent;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-10-30
 */
public abstract class AbstractMouseHandler implements MouseHandler
{
	// 文档编辑器
	protected AbstractEditComponent _editor;// 修改 by 李晓光 2009-2-18

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.view.select.SelectHandler#install(com.wisii.wisedoc
	 * .view.AbstractEditComponent)
	 */
	public void install(AbstractEditComponent c)
	{
		_editor = c;

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
		_editor = null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.view.mousehandler.MouseHandler#init()
	 */
	public void init()
	{
		// TODO Auto-generated method stub

	}

	public void mouseClicked(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void mouseDragged(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}
	public void mouseMoved(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}
	public void paint(Graphics g)
	{
		
	}
	/*
	 * 
	 * 根据传入的坐标点返回包含这些点的最先矩形区域
	 *
	 * @param      
	 * @return      
	 * @exception   
	 */
	protected Rectangle findBound(Point... points)
	{
		Rectangle rect = null;
		if (points != null && points.length > 1)
		{
			int size = points.length;
			int minx = points[0].x;
			int miny = points[0].y;
			int maxx = minx;
			int maxy = miny;
			for (int i = 1; i < size; i++)
			{
				if (points[i].x < minx)
				{
					minx = points[i].x;
				}
				if (points[i].y < miny)
				{
					miny = points[i].y;
				}
				if (points[i].x > maxx)
				{
					maxx = points[i].x;
				}
				if (points[i].y > maxy)
				{
					maxy = points[i].y;
				}
			}
			rect = new Rectangle(minx, miny, maxx - minx, maxy - miny);
		}
		return rect;
	}
	public MouseHandler getCurrentHandler()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
