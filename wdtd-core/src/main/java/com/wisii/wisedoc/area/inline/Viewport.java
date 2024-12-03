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
/* $Id: Viewport.java,v 1.1 2007/04/12 06:41:18 cvsuser Exp $ */

package com.wisii.wisedoc.area.inline;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.svg.Canvas;

/**
 * Inline viewport area. This is an inline-level viewport area for inline
 * container, external graphic and instream foreign object. This viewport holds
 * the area and positions it.
 */
public class Viewport extends InlineArea
{
	// contents could be container, foreign object or image
	private Area content;
	// clipping for the viewport
	private boolean clip = false;
	// position of the child area relative to this area
	private Rectangle2D contentPosition;
	//【添加：START】 by 李晓光 2010-1-22	
	private int startIndex = 0;
	private int elementCount = 0;
    public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public void setElementCount(int elementCount){
		this.elementCount = elementCount;
	}
	public int getElementCount() {
		return this.elementCount;
	}
	//【添加：END】 by 李晓光 2010-1-22
	
	/* 【添加：START】 by 李晓光 2008-10-27 */
	private Rectangle2D viewport = null;
	private CellElement source = null;

	public CellElement getSource()
	{
		return source;
	}

	public void setSource(CellElement source)
	{
		this.source = source;
		if(source instanceof Canvas)
			setAreaKind(AreaKind.SVG_FIGURE);
	}

	public Rectangle2D getViewport()
	{
		if (viewport != null)
		{
			return new Rectangle2D.Double(viewport.getX(), viewport.getY(),
					viewport.getWidth(), viewport.getHeight());
		} else
		{
			return viewport;
		}
	}

	public void setViewport(Rectangle2D viewport)
	{
		this.viewport = viewport;
	}

	/* 【添加：END】 by 李晓光 2008-10-27 */
	/**
	 * Create a new viewport area with the content area.
	 * 
	 * @param child
	 *            the child content area of this viewport
	 */
	public Viewport(Area child)
	{
		/* content = child; */
		setContent(child);
		/* 【添加：START】 by 李晓光 2009年3月4日14:11:53 */
		setAreaKind(AreaKind.INLINE_VIEWPORT);
		/* 【添加：END】 by 李晓光 2009年3月4日14:11:53 */
	}

	/**
	 * Set the clip of this viewport.
	 * 
	 * @param c
	 *            true if this viewport should clip
	 */
	public void setClip(boolean c)
	{
		clip = c;
	}

	/**
	 * Get the clip of this viewport.
	 * 
	 * @return true if this viewport should clip
	 */
	public boolean getClip()
	{
		return clip;
	}

	/**
	 * Set the position and size of the content of this viewport.
	 * 
	 * @param cp
	 *            the position and size to place the content
	 */
	public void setContentPosition(Rectangle2D cp)
	{
		contentPosition = cp;
	}

	/**
	 * Get the position and size of the content of this viewport.
	 * 
	 * @return the position and size to place the content
	 */
	public Rectangle2D getContentPosition()
	{
		return contentPosition;
	}

	/**
	 * Sets the content area.
	 * 
	 * @param content
	 *            the content area
	 */
	public void setContent(Area content)
	{
		this.content = content;
		/* 【添加：START】by 李晓光 2008-10-27 */
		content.setParentArea(this);
		/* 【添加：END】by 李晓光 2008-10-27 */
	}

	/* 【添加：START】by 李晓光 2008-10-27 */
	public List getChildAreas()
	{
		List list = new ArrayList();
		list.add(content);
		return list;
	}
	/**
	 * 判断当前Area的是否有SVG绘制图形创建的。
	 * @return	{@link Boolean}	是由SVG绘制：true，否则：false
	 */
	public boolean isFigure(){
		return (source instanceof Canvas);
	}
	/* 【添加：END】by 李晓光 2008-10-27 */
	/**
	 * Get the content area for this viewport.
	 * 
	 * @return the content area
	 */
	public Area getContent()
	{
		return content;
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{
		out.writeBoolean(contentPosition != null);
		if (contentPosition != null)
		{
			out.writeFloat((float) contentPosition.getX());
			out.writeFloat((float) contentPosition.getY());
			out.writeFloat((float) contentPosition.getWidth());
			out.writeFloat((float) contentPosition.getHeight());
		}
		out.writeBoolean(clip);
		out.writeObject(props);
		out.writeObject(content);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException
	{
		if (in.readBoolean())
		{
			contentPosition = new Rectangle2D.Float(in.readFloat(), in
					.readFloat(), in.readFloat(), in.readFloat());
		}
		clip = in.readBoolean();
		props = (HashMap) in.readObject();
		content = (Area) in.readObject();
	}

}
