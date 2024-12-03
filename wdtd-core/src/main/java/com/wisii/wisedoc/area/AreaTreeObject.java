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
package com.wisii.wisedoc.area;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import com.wisii.wisedoc.util.QName;

/** Abstract base class for all area tree objects. */
public abstract class AreaTreeObject
{
	
	/* 【添加：START】 by 李晓光 2008-11-13 */
	public static enum AreaKind {
		/* 普通的Area */
		NORMAL,
		/* 由FOText创建的Area【TextArea】 */ 
		TEXT,
		/* 由普通的Block创建来的Area */
		BLOCK,
		/* 单元格 */
		CELL,
		/* 表 */
		TABLE,
		/* Viewport 相对Reference Area的Viewport，Page->PageViewport*/
		VIEWPORT,
		/* Referenc Area */
		REFERENCE,
		/* Container */
		CONTAINER,
		/* 用来方式SVG图形的Container */
		SVG_CONTAINER,
		/* 用来方式目录的Container */
		TABLE_CONTENT,
		/* 合并单元格时，或用于处理背景色、边框、背景图的Block */
		BACKGROUND,
		/* 表示FO：Viewport */
		INLINE_VIEWPORT,
		/* 表示SVG图形 [线、矩形、圆、椭圆、画布]*/
		SVG_FIGURE,
		/* 临时的Block,为了需要主动添加进去的，但不属于其子对象 */
		TEMP,
		/* 表示无效的类型，主要用于判断类型用。 */
		NONE
	}
	/* 定义Area的类型 */
	private AreaKind areaKind = AreaKind.NORMAL;

	public AreaKind getAreaKind() {
		return areaKind;
	}

	public void setAreaKind(AreaKind blockKind) {
		this.areaKind = blockKind;
	}
	public boolean isEquateViewport(){
		return isViewport() || isTableCell() || isNormalBlock() || isTable();
	}
	public boolean isViewport(){
		return (areaKind == AreaKind.VIEWPORT) || isContainer();
	}
	public boolean isReference(){
		return (areaKind == AreaKind.REFERENCE) || isBackground();
	}
	public boolean isBackground(){
		return (areaKind == AreaKind.BACKGROUND);
	}
	public boolean isTableCell() {
		return (areaKind == AreaKind.CELL);
	}

	public boolean isTable() {
		return (areaKind == AreaKind.TABLE);
	}

	public boolean isContainer() {
		return (areaKind == AreaKind.CONTAINER) || isSVGContainer() || isTableContent();
	}
	public boolean isSVGContainer(){
		return (areaKind == AreaKind.SVG_CONTAINER);
	}
	public boolean isTableContent(){
		return (areaKind == AreaKind.TABLE_CONTENT);
	}
	public boolean isNormalBlock(){
		return (this instanceof Block) && isNormal() && !(isNull(getViewport()));
	}
	public boolean isInlineViewport(){
		return (areaKind == AreaKind.INLINE_VIEWPORT);
	}
	public boolean isNormal() {
		return (areaKind == AreaKind.NORMAL);
	}
	/* 【添加：END】 by 李晓光 2008-11-13 */
	
	/** Foreign attributes */
	protected Map foreignAttributes = null;

	/**
	 * Sets a foreign attribute.
	 * @param name the qualified name of the attribute
	 * @param value the attribute value
	 */
	public void setForeignAttribute(QName name, String value)
	{
		if (this.foreignAttributes == null) this.foreignAttributes = new java.util.HashMap();
		this.foreignAttributes.put(name, value);
	}

	/**
	 * Set foreign attributes from a Map.
	 * @param atts a Map with attributes (keys: QName, values: String)
	 */
	public void setForeignAttributes(Map atts)
	{
		if (atts.size() == 0) return;
		Iterator iter = atts.keySet().iterator();
		while (iter.hasNext())
		{
			QName qName = (QName)iter.next();
			String value = (String)atts.get(qName);
			//The casting is only to ensure type safety (too bad we can't use generics, yet)
			setForeignAttribute(qName, value);
		}
	}

	/**
	 * Returns the value of a foreign attribute on the area.
	 * @param name the qualified name of the attribute
	 * @return the attribute value or null if it isn't set
	 */
	public String getForeignAttributeValue(QName name)
	{
		if (this.foreignAttributes != null)
			return (String)this.foreignAttributes.get(name);
		else
			return null;
	}

	/** @return the foreign attributes associated with this area */
	public Map getForeignAttributes()
	{
		if (this.foreignAttributes != null)
			return Collections.unmodifiableMap(this.foreignAttributes);
		else
			return Collections.EMPTY_MAP;
	}
	/* 【添加：START】 by 李晓光 2008-11-13 */
	public Rectangle2D getViewport()
	{
		return null;
	}
	/* 【添加：END】 by 李晓光 2008-11-13 */
}
