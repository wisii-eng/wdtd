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
 * @MarginBean.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.render.awt.viewer;

/**
 * 类功能描述：用于保存Margin部分数据【所有的Space + Padding】
 * 
 * 作者：李晓光 创建日期：2008-11-13
 */
public class MarginBean {
	public MarginBean() {
	}

	public MarginBean(final double top, final double bottom, final double left, final double right) {
		setMarginTop(top);
		setMarginBottom(bottom);
		setMarginLeft(left);
		setMarginRight(right);
	}

	public double getMarginLeft() {
		return marginLeft;
	}

	public void setMarginLeft(final double marginLeft) {
		this.marginLeft = marginLeft;
	}

	public double getMarginRight() {
		return marginRight;
	}

	public void setMarginRight(final double marginRight) {
		this.marginRight = marginRight;
	}

	public double getMarginTop() {
		return marginTop;
	}

	public void setMarginTop(final double marginTop) {
		this.marginTop = marginTop;
	}

	public double getMarginBottom() {
		return marginBottom;
	}

	public void setMarginBottom(final double marginBottom) {
		this.marginBottom = marginBottom;
	}

	/**
	 * 相加两个Margion
	 * 
	 * @param bean
	 *            指定要加的Margion
	 * @return {@link MarginBean} 返回相加后的Margin。
	 */
	public MarginBean add(final MarginBean bean) {
		final MarginBean result = new MarginBean();
		result.marginLeft = this.marginLeft + bean.marginLeft;
		result.marginTop = this.marginTop + bean.marginTop;
		result.marginRight = this.marginRight + bean.marginRight;
		result.marginBottom = this.marginBottom + bean.marginBottom;
		return result;
	}
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("[");
		sb.append("MarginLeft=");
		sb.append(marginLeft);
		sb.append(",MarginRight=");
		sb.append(marginRight);
		sb.append(",MarginTop=");
		sb.append(marginTop);
		sb.append(",MarginBottomt=");
		sb.append(marginBottom);
		sb.append("]");
		
		return sb.toString();
	}
	private double marginLeft = 0;
	private double marginRight = 0;
	private double marginTop = 0;
	private double marginBottom = 0;
}
