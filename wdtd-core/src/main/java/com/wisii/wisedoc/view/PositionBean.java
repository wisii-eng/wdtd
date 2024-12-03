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
 * @PositionBean.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.Shape;
import java.text.MessageFormat;

/**
 * 类功能描述：用于描述由位置信息解析而来的矩形区域，及该区域是属于的页索引
 * 
 * 作者：李晓光 创建日期：2008-10-20
 */
public class PositionBean {
	public static enum FigureStyle {
		/** 普通类型 */
		NORMAL,
		/** 绘制Viewport的可拖动点 */
		DRAG_POINT,
		/** 绘制Viewport的边框 */
		BORDER,
	}

	private Integer pageIndex = -1;
	private Shape viewport = null;
	private FigureStyle figureStyle = FigureStyle.NORMAL;
	private String pattern = "Page Index:{0}-Viewport:{1}-Figure Style{2}";

	public PositionBean() {
		this(-1, null);
	}

	public PositionBean(int index, Shape viewport) {
		setPageIndex(index);
		setViewport(viewport);
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Shape getViewport() {
		return viewport;
	}

	public void setViewport(Shape viewport) {
		this.viewport = viewport;
	}

	public FigureStyle getFigureStyle() {
		return figureStyle;
	}

	public void setFigureStyle(FigureStyle figureStyle) {
		this.figureStyle = figureStyle;
	}

	public String toString() {
		String temp = "";
		if (!isNull(viewport))
			temp = viewport.toString();
		return MessageFormat.format(pattern, pageIndex, temp, figureStyle);
	}
}
