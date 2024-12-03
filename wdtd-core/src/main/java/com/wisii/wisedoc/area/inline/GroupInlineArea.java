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
 * @GroupInlineArea.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.area.inline;

import java.awt.Color;
import java.awt.Paint;
import java.awt.geom.Point2D;

/**
 * 类功能描述：根据捕获的鼠标事件，判读处理进行SVG图形选择。
 * 
 * 作者：李晓光 创建日期：2009-3-11
 */
@SuppressWarnings("serial")
public class GroupInlineArea extends InlineArea {
	public static enum InlineType{
		//这个用于备用。
		None,
		//直接Inline在首位添加 "[,]"
		Normal,
		//在Inline的"]"上绘制"+"
		Bind,
		//设置组的标示"┛,┗"
		Group;
	}
	private boolean start = Boolean.FALSE;
	private Paint paint = Color.RED;
	private Point2D point = null;
	private InlineType type = InlineType.Group;
	public GroupInlineArea(final boolean isStart){
		setStart(isStart);
	}
	public Point2D getPoint() {
		return point;
	}
	public void setPoint(final Point2D point) {
		this.point = point;
	}
	public boolean isStart() {
		return start;
	}

	public void setStart(final boolean start) {
		this.start = start;
	}
	public Paint getPaint() {
		/*return paint;*/
		return getP();
	}
	private Paint getP(){
		Paint p = Color.RED;
		switch (type) {
		case Normal:
			p = new Color(255, 102, 51);//Color.BLUE;
			break;
		case Bind:
			/*p = new Color(0, 102, 102);*/
			break;
		case Group:
//			p = new Color(0, 153, 153);//new Color(255, 0, 255);//new Color(0, 102, 102);//new Color(153, 0, 153);
			p = Color.RED;
			 break;
		default:
			break;
		}
		return p;
	}
	public void setPaint(final Paint paint) {
		this.paint = paint;
	}
	public InlineType getType() {
		return type;
	}
	public void setType(InlineType type) {
		this.type = type;
	}
}
