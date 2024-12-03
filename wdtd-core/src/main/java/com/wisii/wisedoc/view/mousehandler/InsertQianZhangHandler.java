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
 * @InsertQianZhangHandler.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.mousehandler;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.QianZhangInline;
import com.wisii.wisedoc.swing.SwingUtil;
import com.wisii.wisedoc.view.cursor.CursorManager;
import com.wisii.wisedoc.view.cursor.CursorManager.CursorType;
/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2011-11-14
 */
public class InsertQianZhangHandler extends AbstractMouseHandler
{
	private Point currentpoint = null;
	private Image qianzhangimage;
	private static float lineheight = 1.0f;
	private Point paintpoint;
	int imagewidth;
	int imageheight;

	public void paint(Graphics g)
	{
		if (paintpoint != null && qianzhangimage != null)
		{
             System.out.println(paintpoint);
			Graphics2D g2d = (Graphics2D) g;
			double scale = _editor.getScaleFactor();
			int width = (int) (imagewidth * scale);
			int height = (int) (imageheight * scale);
			int x = paintpoint.x;
			int y = paintpoint.y;
			g2d.drawImage(qianzhangimage, x, y, width, height, null);
			g2d.setStroke(SwingUtil.getStrokefromLineType(SwingUtil.DASGEDLINE,
					lineheight));
			g2d.drawRect(x, y, width, height);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.view.mousehandler.MouseHandler#isInControl(java.awt
	 * .event.MouseEvent)
	 */
	@Override
	public boolean isInControl(MouseEvent event)
	{
		String path = QianZhangInsertManager.getPATH();
		if (path != null)
		{
			if (qianzhangimage == null)
			{
				_editor.setCursor(CursorManager
						.getSytemCursor(CursorType.CROSSHAIR_CURSOR));
				try
				{
					qianzhangimage = ImageIO.read(new File("qianzhangs"
							+ File.separator + path));
					final int dpi = Toolkit.getDefaultToolkit()
							.getScreenResolution(); // dpi
					float scale = dpi / 72f;
					imagewidth = Math.round(qianzhangimage.getWidth(null)
							* scale);
					imageheight = Math.round(qianzhangimage.getHeight(null)
							* scale);
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			return true;
		} else
		{
			qianzhangimage = null;
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.view.mousehandler.AbstractMouseHandler#mouseMoved(java
	 * .awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e)
	{
		super.mouseMoved(e);
		updataView(e.getPoint(), e.getComponent());
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	private void updataView(Point newpoint, Component source)
	{
		if (!newpoint.equals(currentpoint))
		{

			currentpoint = newpoint;
			DocumentPosition pos = _editor.pointtodocpos(new Point(newpoint.x
					- imagewidth / 2, newpoint.y - imageheight / 2));
			if (pos == null)
			{
				return;
			}
			Point newpospoint = _editor.docpostopoint(pos);
			if (newpospoint == null)
			{
				return;
			}
			newpospoint.setLocation(newpospoint.x / 1000+1,
					newpospoint.y / 1000+5);
			Point newpaintpoint = SwingUtilities.convertPoint(_editor.getPageOf(pos.getPageIndex()),
					newpospoint, _editor);
			if (newpaintpoint.equals(paintpoint))
			{
				return;
			}
			Rectangle bound = new Rectangle(newpaintpoint.x - 2,
					newpaintpoint.y - 2, imagewidth + 4, imageheight + 4);
			if (paintpoint != null)
			{
				Rectangle oldbound = new Rectangle(paintpoint.x - 2,
						paintpoint.y - 2, imagewidth + 4, imageheight + 4);
				bound = bound.union(oldbound);
			}
			paintpoint = newpaintpoint;
			double scale = _editor.getScaleFactor();
			bound.setBounds((int) (bound.x * scale), (int) (bound.y * scale),
					(int) (bound.width * scale), (int) (bound.height * scale));
			_editor.repaint(bound);
		}
	}

	public void mousePressed(MouseEvent event)
	{
		if (SwingUtilities.isRightMouseButton(event))
		{
			clear();
			QianZhangInsertManager.clearPath();
		}
	}

	public void mouseReleased(MouseEvent event)
	{
		Point pageendpoint = event.getPoint();
		if (SwingUtilities.isLeftMouseButton(event))
		{
			pageendpoint.setLocation(pageendpoint.x - imagewidth / 2,
					pageendpoint.y - imageheight / 2);
			DocumentPosition pos = _editor.pointtodocpos(pageendpoint);
			if (pos != null)
			{
				CellElement element = new QianZhangInline(
						QianZhangInsertManager.getPATH());
				insertCell(element, pos);
			}
		}
		updataView(event.getPoint(), event.getComponent());
		QianZhangInsertManager.clearPath();
		clear();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	private void clear()
	{
		qianzhangimage = null;
		currentpoint = null;
		imagewidth = 0;
		imageheight = 0;
		paintpoint = null;
	}

	private void insertCell(CellElement element, DocumentPosition pos)
	{

		List<CellElement> elements = new ArrayList<CellElement>();
		elements.add(element);
		SystemManager.getCurruentDocument().insertElements(elements, pos);
	}
}
