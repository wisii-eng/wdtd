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
 * @abstractElement.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.tree.TreeNode;

import com.wisii.wisedoc.apps.FOUserAgent;
import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.document.attribute.AttributeConstants;
import com.wisii.wisedoc.document.attribute.AttributeContainer;
import com.wisii.wisedoc.document.attribute.AttributeManager;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.attribute.ParagraphStyles;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.document.attribute.WiseDocColor;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.document.datatype.LengthBase;
import com.wisii.wisedoc.document.datatype.PercentBase;
import com.wisii.wisedoc.view.LocationConvert;

/**
 * 类功能描述：Element类顶层抽象实现 实现一些公用的方法
 * 
 * 作者：zhangqiang 创建日期：2008-8-14
 */
public abstract class DefaultElement implements CellElement
{
	protected final static Attributes NULLATTRIBUTES = new Attributes(
			new HashMap<Integer, Object>());
	// 元素的父元素，可以为空，为空表示无父节点
	protected Element _parent;

	// 子元素，为空表示没子元素。
	protected List<CellElement> _children;

	// 对象属性
	protected Attributes _attributes = NULLATTRIBUTES;
	
	// 是否容许有子节点
	protected boolean _allowsChildren = true;
	// 章节的主流的最后一个元素的id，用来生成总页码
	public final static String IDLAST = "last";

	public DefaultElement()
	{
		if (_allowsChildren)
		{
			_children = new ArrayList<CellElement>();
		}
	}

	public DefaultElement(final Map<Integer,Object> attributes)
	{
		if (_allowsChildren)
		{
			_children = new ArrayList<CellElement>();
		}
		if (attributes != null)
		{
			setAttributes(attributes, true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.Element#add(com.wisii.wisedoc.document.Element
	 * )
	 */
	public void add(final CellElement newChild)
	{
		if (newChild != null && iscanadd(newChild))
		{
			_children.add(newChild);
			newChild.setParent(this);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#getAttribute(int)
	 * 规则：自身——>段落样式——>父对象——>默认
	 */
	public Object getAttribute(final int key)
	{
		final Attributes attributes = getAttributes();
		Object value = null;
		if (attributes != null)
		{
			value = attributes.getAttribute(key);
			/* 如果元素引用了段落样式集，从段落样式集合中获得属性 */
			if (value == null)
			{// 【添加】 by 李晓光 2009-3-27
				final ParagraphStyles styles = (ParagraphStyles) attributes
						.getAttribute(Constants.PR_BLOCK_STYLE);
				if (styles != null)
				{
					value = styles.getAttribute(key);
				}
			}
		}
		// 如果用户没有设置该属性值，则根据属性是否继承去继承值或取默认值
		if (value == null)
		{
			// 如果是可继承的，则取父对象的相应属性
			if (iscanInherit(key))
			{
				final Element parent = getParent();
				if (parent != null)
				{
					value = parent.getAttribute(key);
				}
			}
			if (value == null)
			{
				value = InitialManager.getInitialValue(key, this);
			}
		}
		// 如果是百分比长度，则需要把当前元素传进去，以计算大小
		if ((value instanceof PercentLength) && (key != Constants.PR_FONT_SIZE))
		{
			final PercentLength oldlength = (PercentLength) value;
			final PercentBase oldbase = oldlength.getBaseLength();
			if (oldbase instanceof LengthBase)
			{
				value = new PercentLength(oldlength.value(), new LengthBase(
						this, ((LengthBase) oldbase).getBaseType()));
			}
		}
		// 如果是范围长度
		else if ((value instanceof LengthRangeProperty)
				&& (key != Constants.PR_FONT_SIZE)&&(key != Constants.PR_LINE_HEIGHT))
		{
			final LengthRangeProperty lrp = (LengthRangeProperty) value;
			LengthProperty newminp = null;
			LengthProperty newmaxp = null;
			LengthProperty newoptp = null;
			boolean ischanged = false;
			newminp = lrp.getMinimum(null);
			if (newminp instanceof PercentLength)
			{
				final PercentLength oldmin = (PercentLength) newminp;
				final PercentBase oldbase = oldmin.getBaseLength();
				if (oldbase instanceof LengthBase)
				{
					newminp = new PercentLength(oldmin.value(), new LengthBase(
							this, ((LengthBase) oldbase).getBaseType()));
					ischanged = true;
				}
			}
			newmaxp = lrp.getMaximum(null);
			if (newmaxp instanceof PercentLength)
			{
				final PercentLength oldmax = (PercentLength) newmaxp;
				final PercentBase oldbase = oldmax.getBaseLength();
				if (oldbase instanceof LengthBase)
				{
					newmaxp = new PercentLength(oldmax.value(), new LengthBase(
							this, ((LengthBase) oldbase).getBaseType()));
					ischanged = true;
				}
			}
			newoptp = lrp.getOptimum(null);
			if (newoptp instanceof PercentLength)
			{
				final PercentLength oldmax = (PercentLength) newoptp;
				final PercentBase oldbase = oldmax.getBaseLength();
				if (oldbase instanceof LengthBase)
				{
					newoptp = new PercentLength(oldmax.value(), new LengthBase(
							this, ((LengthBase) oldbase).getBaseType()));
					ischanged = true;
				}
			}
			if (ischanged)
			{
				if (value instanceof SpaceProperty)
				{
					final SpaceProperty sp = (SpaceProperty) value;
					value = new SpaceProperty(newminp, newoptp, newmaxp, sp
							.getPrecedence(), sp.getConditionality());
				} else
				{
					value = new LengthRangeProperty(newminp, newoptp, newmaxp);
				}
			}
		}
		return value;
	}

	/**
	 * 
	 * 判断属性是否可继承
	 * 
	 * @param key
	 *            ：属性键值
	 * @return 可继承则返回“true”，否则返回“false”
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private boolean iscanInherit(final int key)
	{
		return AttributeConstants.PROPERTYINHERIT[key];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#getAttributes()
	 */
	public Attributes getAttributes()
	{
		return _attributes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#getChildren()
	 */
	public Iterator<CellElement> getChildren()
	{
		return _children.iterator();
	}

	public List<CellElement> getChildren(final int childIndex)
	{
		List<CellElement> list = new ArrayList<CellElement>();
		int size = _children.size();
		if (childIndex >= 0 && childIndex < size)
		{
			for (int i = childIndex; i < size; i++)
			{
				list.add(_children.get(i));
			}
		}
		return list;
	}

	/* 【添加：START】by 李晓光 2008-10-28 */
	public List<CellElement> getAllChildren()
	{
		return new ArrayList<CellElement>(_children);
	}

	/* 【添加：END】by 李晓光 2008-10-28 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.CellElement#getChildren(int, int)
	 */
	public List<CellElement> getChildren(final int childIndex, final int endindex)
	{
		final List<CellElement> list = new ArrayList<CellElement>();
		final int size = _children.size();
		if (childIndex >= 0 && childIndex <= endindex && endindex >= 0
				&& endindex <= size)
		{
			list.addAll(_children.subList(childIndex, endindex));
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.Element#insert(com.wisii.wisedoc.document.
	 * Element, int)
	 */
	public void insert(final CellElement newChild, final int childIndex)
	{
		if (newChild != null && iscaninsert(newChild, childIndex))
		{
			_children.add(childIndex, newChild);
			newChild.setParent(this);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#insert(java.util.List, int)
	 */
	public void insert(final List<CellElement> children, final int childIndex)
	{
		if (children != null && !children.isEmpty()
				&& iscaninsert(children, childIndex))
		{
			final List<CellElement> nchildren = new ArrayList<CellElement>();
			final int childrensize = children.size();
			for (int i = 0; i < childrensize; i++)
			{
				final CellElement child = children.get(i);
				// 去除点其中的空元素
				if (child != null)
				{
					child.setParent(this);
					nchildren.add(child);
				}
			}
			if (!nchildren.isEmpty())
			{
				_children.addAll(childIndex, nchildren);
			}
		}

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#remove(int)
	 */
	public void remove(final int childIndex)
	{
		final CellElement child = getChildAt(childIndex);
		remove(child);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.Element#remove(com.wisii.wisedoc.document.
	 * Element)
	 */
	public void remove(final CellElement child)
	{
		if (child != null && _children != null && _children.contains(child)
				&& iscanRemove(child))
		{
			_children.remove(child);
			if (this.equals(child.getParent()))
			{
				child.setParent(null);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.Element#removeChildren(com.wisii.wisedoc.document
	 * .Element)
	 */
	public void removeChildren(final List<CellElement> children)
	{
		if (children != null && !children.isEmpty() && iscanRemove(children))
		{
			_children.removeAll(children);
			for (int i = 0; i < children.size(); i++)
			{
				final CellElement child = children.get(i);
				if (child != null && this.equals(child.getParent()))
				{
					child.setParent(null);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#removeAllChildren()
	 */
	public void removeAllChildren()
	{
		if (iscanremoveall())
		{
			for (int i = 0; i < _children.size(); i++)
			{
				final CellElement child = _children.get(i);
				if (child != null)
				{
					child.setParent(null);
				}
			}
			_children.clear();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#removeAttributes(int)
	 */
	public void removeAttributes(final int key)
	{
		final Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(key, null);
		setAttributes(map, false);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#setAttribute(int,
	 * java.lang.Object)
	 */
	public void setAttribute(final int key, final Object value)
	{
		if (iscanSetAttribute(key, value))
		{
			final Map<Integer, Object> map = new HashMap<Integer, Object>();
			map.put(key, value);
			setAttributes(map, false);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#setAttributes(java.util.Map)
	 */
	public void setAttributes(final Map<Integer, Object> atts, final boolean isreplace)
	{
		if (atts != null)
		{
			final Map<Integer, Object> oldattribute = _attributes.getAttributes();
			Map<Integer, Object> map = null;
			if (isreplace)
			{
				map = new HashMap<Integer, Object>(atts);
			} else
			{
				map = oldattribute;
			}
			final Iterator<Integer> it = atts.keySet().iterator();
			while (it.hasNext())
			{
				final int i = it.next();
				Object newvalue = atts.get(i);
				// 去除掉里面为空的属性
				if (newvalue == null)
				{
					map.remove(i);
				} else if (newvalue == Constants.NULLOBJECT)
				{
					if (isreplace)
					{
						map.remove(i);
					}
				} else
				{
					// 如果设置的是字号属性
					if (i == Constants.PR_FONT_SIZE
							&& !atts.containsKey(Constants.PR_BASELINE_SHIFT))
					{

						final LengthProperty oldsize = (LengthProperty) getAttribute(Constants.PR_FONT_SIZE);
						if (oldsize instanceof PercentLength)
						{
							final PercentLength oldpl = (PercentLength) oldsize;
							if (newvalue instanceof FixedLength)
							{
								final LengthBase lenbase = new LengthBase(
										(Length) newvalue, LengthBase.FONTSIZE);
								newvalue = new PercentLength(oldpl.value(),
										lenbase);
							}
						}
					}
					// 如果是颜色属性值，则需要做特殊处理
					else if (newvalue instanceof Color)
					{
						if (newvalue instanceof WiseDocColor)
						{

							final WiseDocColor color = (WiseDocColor) newvalue;
							// 如果只需要设置颜色，则保留原属性的层
							if (color.isSetColor())
							{
								final Color oldcolor = (Color) getAttribute(i);
								if (oldcolor != null)
								{
									int layer = 0;
									if (oldcolor instanceof WiseDocColor)
									{
										layer = ((WiseDocColor) oldcolor)
												.getLayer();
									}
									newvalue = new WiseDocColor(color, layer);
								}
							}
							// 如果是设置层，则保留原属性的颜色
							else if (color.isSetLayer())
							{
								final Color oldcolor = (Color) getAttribute(i);
								if (oldcolor != null)
								{
									newvalue = new WiseDocColor(oldcolor,
											(color).getLayer());
								} else
								{
									if (i == Constants.PR_BACKGROUND_COLOR)
									{

										continue;
									}
								}

							} else
							{
								// nothing
							}
						} else
						{
							newvalue = new WiseDocColor((Color) newvalue, 0);
						}

					}
					map.put(i, newvalue);
				}
			}
			final AttributeContainer container = AttributeManager
					.getAttributeContainer(this);
			if (container.isAttributesSupport(map))
			{
				_attributes = container.getAttribute(map);
			}
		} else
		{
			_attributes = NULLATTRIBUTES;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.Element#setParent(com.wisii.wisedoc.document
	 * .Element)
	 */
	public void setParent(final Element newParent)
	{
		_parent = newParent;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#children()
	 */
	public Enumeration<CellElement> children()
	{
		final Vector<CellElement> children = new Vector<CellElement>();
		children.addAll(_children);
		return children.elements();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getAllowsChildren()
	 */
	public boolean getAllowsChildren()
	{
		return _allowsChildren;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getChildAt(int)
	 */
	public CellElement getChildAt(final int childIndex)
	{
		if (getAllowsChildren() && childIndex < getChildCount())
		{
			return _children.get(childIndex);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getChildCount()
	 */
	public int getChildCount()
	{
		if (_children != null)
		{
			return _children.size();
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getIndex(javax.swing.tree.TreeNode)
	 */
	public int getIndex(final TreeNode node)
	{
		// TODO Auto-generated method stub
		return _children.indexOf(node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getParent()
	 */
	public Element getParent()
	{
		return _parent;
	}

	@Override
	public Element clone()
	{
		DefaultElement element = null;
		try
		{
			element = (DefaultElement) super.clone();
			if (element != null && _children != null)
			{
				element._children = new ArrayList<CellElement>();
				final int size = _children.size();
				for (int i = 0; i < size; i++)
				{
					final CellElement child = _children.get(i);
					final CellElement nchild = (CellElement) child.clone();
					element.add(nchild);
				}
			}
			return element;
		} catch (final CloneNotSupportedException e)
		{
			e.printStackTrace();
			return element;

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#isLeaf()
	 */
	public boolean isLeaf()
	{
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.Element#getoffsetofPos(com.wisii.wisedoc.document
	 * .DocumentPosition)
	 */
	public int getoffsetofPos(final DocumentPosition pos)
	{
		int offset = -1;
		final CellElement element = pos.getLeafElement();
		Element parent = element.getParent();
		Element oldparent = element;
		while (parent != null && !(parent instanceof Document))
		{
			if (parent == this)
			{
				offset = parent.getIndex(oldparent);
				break;
			}
			oldparent = parent;
			parent = parent.getParent();
		}
		return offset;
	}

	/**
	 * 
	 * 判断是否可以添加子对象 如果对子对象的类型等有要求，需要在子类中进行覆写
	 * 
	 * @param newChild
	 *            ：要添加的子对象
	 * @return 是否可添加该子对象的布尔值
	 * @exception
	 */
	public boolean iscanadd(final CellElement newChild)
	{
		return newChild != null;
	}

	/**
	 * 
	 * 判断是否可以在指定位置添加子对象 对插入对象有特殊要求，可覆写该方法
	 * 
	 * @param newChild
	 *            ：要插入的子对象 childIndex：要插入的位置
	 * @return 是否可在指定位置添加子对象的布尔值
	 * @exception
	 */
	public boolean iscaninsert(final CellElement newChild, final int childIndex)
	{
		return newChild != null
				&& (childIndex >= 0 && childIndex <= getChildCount());
	}

	/**
	 * 
	 * 判断是否可以在指定位置添加子对象 对插入对象有特殊要求，可覆写该方法
	 * 
	 * @param newChild
	 *            ：要插入的子对象 childIndex：要插入的位置
	 * @return 是否可在指定位置添加子对象的布尔值
	 * @exception
	 */
	public boolean iscaninsert(final List<CellElement> children, final int childIndex)
	{
		return children != null
				&& (childIndex >= 0 && childIndex <= getChildCount());
	}

	/**
	 * 
	 * 是否可删除某个子对象
	 * 
	 * 
	 * @param child
	 *            ：要删除的子对象
	 * @return 是否可删除该子对象的布尔值
	 * @exception
	 */
	public boolean iscanRemove(final CellElement child)
	{
		return child != null;
	}

	/**
	 * 
	 * 是否可删除指定的子对象集合
	 * 
	 * 
	 * @param children
	 *            ：要删除的子对象集合
	 * @return true
	 * @exception
	 */
	public boolean iscanRemove(final List<CellElement> children)
	{
		return children != null;
	}

	/**
	 * 
	 * 是否可设置指定指定关键字的属性值
	 * 
	 * @param
	 * @return true为可设置，false为不可设置
	 * @exception
	 */
	public boolean iscanSetAttribute(final int key, final Object value)
	{
		// 新属性值和原属性值相等，则不需要设置，即返回空
		if ((value == null && _attributes.getAttribute(key) == null)
				|| (value != null && value
						.equals(_attributes.getAttribute(key))))
		{
			return false;
		}
		return true;
	}

	/**
	 * 
	 * 是否可删除所有的子对象
	 * 
	 * @param
	 * @return true表示可删除所有的子对象，false表示不能删除
	 * @exception
	 */
	public boolean iscanremoveall()
	{
		return true;
	}

	/** @see com.wisii.fov.fo.FONode#getChildNodes() */
	public ListIterator getChildNodes()
	{
		if (_children != null) {
			return _children.listIterator();
		}
		return null;
	}

	/** @return the extension attachments of this FObj. */
	public List getExtensionAttachments()
	{
		return Collections.EMPTY_LIST;
	}

	/**
	 * Check if this formatting object generates reference areas.
	 * 
	 * @return true if generates reference areas
	 * @todo see if needed
	 */
	public boolean generatesReferenceAreas()
	{
		return false;
	}

	public int getNameId()
	{
		return Constants.FO_UNKNOWN_NODE;
	}

	public FOUserAgent getUserAgent()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getId()
	{
		String id = (String) getAttribute(Constants.PR_ID);

		final Element parent = getParent();
		if (parent != null
				&& (parent instanceof Flow && !(parent instanceof StaticContent)))
		{
			final int index = parent.getIndex(this);
			if (index == (parent.getChildCount() - 1))
			{
				id = IDLAST+Integer.toHexString(parent.hashCode());
			}
		}
		return id;
	}

	/* 【添加：START】by 李晓光 2008-10-13 */
	private final short zero = 1;

	public CellElement getFirstChild()
	{
		return searchElement(_children.get(zero), true);
	}

	public CellElement getLastChild()
	{
		return searchElement(_children.get(getChildCount() - 1), true);
	}

	private CellElement searchElement(CellElement element, final boolean isStart)
	{
		if (element == null || element.getChildCount() == 0) {
			return element;
		}
		if ((element.getClass() == TextInline.class)) {
			return element;
		}
		if (element.getClass() == Inline.class) {
			return element;
		}

		final List<CellElement> elements = ((DefaultElement) element)._children;
		if (isStart) {
			element = elements.get(zero);// 【目前0位置是回车符】
		} else {
			element = elements.get(getChildCount() - 1);
		}

		if (element.getChildCount() > 0) {
			element = searchElement(element, isStart);
		}

		return element;
	}

	/**
	 * 获得指定元素所在的Block对象，如果不属于Block返回Null.
	 * 
	 * @param ele
	 *            指定元素
	 * @return {@link Block} 返回包含指定元素的Block，如果没有返回Null。
	 */
	public static Block getCurrentBlock(final CellElement ele)
	{
		final Block block = (Block) LocationConvert.searchCellElement(ele,
				Block.class);
		return block;
	}

	/**
	 * 获得当前元素所在的Block对象，如果不属于Block返回Null.
	 * 
	 * @return {@link Block} 返回包含当前元素的Block，如果没有返回Null。
	 */
	public Block getCurrentBlock()
	{
		return getCurrentBlock(this);
	}
	@Override
	public Area getArea()
	{
		return null;
	}
	/* 【添加：END】 by 李晓光 2008-10-13 */
	@Override
	public void initFOProperty()
	{
		
	}
}
