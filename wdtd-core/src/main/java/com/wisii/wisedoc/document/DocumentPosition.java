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
 * @DocumentPosition.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.wisii.wisedoc.document.attribute.WiseDocColor;

/**
 * 类功能描述：文档定位用接口，本系统 采用位模型树中的叶子节点来定位文档， 由于模型中的
 * 任何一个节点都有访问其父节点的接口，因此获得叶子节点，则等于知道这个分支位置。 作者：zhangqiang 创建日期：2008-4-14
 */
public final class DocumentPosition implements Comparable<DocumentPosition>
{
	// 以叶子节点标识的文档位置
	private CellElement _leaf;

	// 文档位置是在开始（左边）还是结束（右边）
	private boolean _isstart;
	// 是否在该对象的正上方
	private boolean _ison;
	private int offset = -1;
	public static int NOTONEDOCUMENT = -100;
	private Map<Integer, Object> inlineAtts;
	private Map<Integer, Object> blockAtts;

	/**
	 * 
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public DocumentPosition(final CellElement leaf)
	{
		this(leaf, false);
	}

	/**
	 * 
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public DocumentPosition(final CellElement leaf, final boolean isstart)
	{
		setLeaf(leaf);
		_isstart = isstart;
		initAttributes();
	}

	public DocumentPosition(final CellElement leaf, final boolean isstart,
			final int offset)
	{
		this(leaf, isstart, false, offset);
	}

	/**
	 * 
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public DocumentPosition(final CellElement leaf, final boolean isstart,
			final boolean ison)
	{
		setLeaf(leaf);
		_isstart = isstart;
		_ison = ison;
		initAttributes();
	}

	public DocumentPosition(final CellElement leaf, final boolean isstart,
			final boolean ison, final int offset)
	{
		this._leaf = leaf;
		_isstart = isstart;
		_ison = ison;
		this.offset = offset;
		initAttributes();
	}

	// add by 李晓光 2009-2-23
	private int pageIndex = -1;

	public DocumentPosition(final CellElement leaf, final boolean isstart,
			final boolean ison, final int offset, final int pageIndex)
	{
		this._leaf = leaf;
		_isstart = isstart;
		_ison = ison;
		this.offset = offset;
		this.pageIndex = pageIndex;
		initAttributes();
	}

	public void setPageIndex(final int pageIndex)
	{
		this.pageIndex = pageIndex;
	}

	public int getPageIndex()
	{
		return this.pageIndex;
	}

	/*
	 * 
	 * 初始化光标属性
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * 
	 * @return {返回参数名} {返回参数说明}
	 * 
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	private void initAttributes()
	{
		initInlineAttbutes();
		initBlockAttbutes();
	}

	/*
	 * 初始化inline属性
	 */
	private void initInlineAttbutes()
	{
		if (_leaf instanceof Inline)
		{
			initInlineAttbutes(_leaf.getAttributes().getAttributes());
		}
	}

	/*
	 * 初始化当前光标的段落属性
	 */
	private void initBlockAttbutes()
	{
		CellElement block = _leaf;
		while (block != null && !(block instanceof Block)
				&& !(block instanceof Flow))
		{
			block = (CellElement) block.getParent();
		}
		if (block != null && block instanceof Block)
		{
			blockAtts = block.getAttributes().getAttributes();
			// blockAtts.remove(Constants.PR_BLOCK_LEVEL);
			blockAtts.remove(Constants.PR_ID);
			// 清除掉条件和组属性
			blockAtts.remove(Constants.PR_CONDTION);
			blockAtts.remove(Constants.PR_GROUP);
			blockAtts.remove(Constants.PR_DYNAMIC_STYLE);
			// 处理光标在上一段落最后一个字符按回车新起一个段落时，
			// 输入的文本应该和回车前光标所在段落最后一个文字的字符属性一致的情况
			if (_leaf == block)
			{
				initInlineAttbutes(((Block) block).getInlineatt());
			}
		}

	}

	private void initInlineAttbutes(Map<Integer, Object> inlineAtts)
	{
		if (inlineAtts != null && !inlineAtts.isEmpty())
		{
			inlineAtts.remove(Constants.PR_ENDOFALL);
			inlineAtts.remove(Constants.PR_ID);
			// 清除掉条件和组属性动态样式
			inlineAtts.remove(Constants.PR_CONDTION);
			inlineAtts.remove(Constants.PR_GROUP);
			inlineAtts.remove(Constants.PR_DYNAMIC_STYLE);
			inlineAtts.remove(Constants.PR_TRANSFORM_TABLE);
			inlineAtts.remove(Constants.PR_GRAPHIC_LAYER);
			for (int i = Constants.PR_EDITTYPE; i < Constants.PR_GROUP_NONE_SELECT_VALUE; i++)
			{
				inlineAtts.remove(i);
			}
			this.inlineAtts = inlineAtts;
		}
	}

	public Block getBlock()
	{
		Map<Integer, Object> map = getBlockAttriute();
		if (_leaf instanceof TableCell || _leaf instanceof BlockContainer
				|| _leaf instanceof Flow)
		{
			if (map == null)
			{
				map = new HashMap<Integer, Object>();
			}
			map.put(Constants.PR_DISPLAY_ALIGN, _leaf
					.getAttribute(Constants.PR_DISPLAY_ALIGN));
		}
		if (_leaf instanceof Block)
		{
			Map<Integer, Object> inlineatt = ((Block) _leaf).getInlineatt();
			if (inlineatt != null)
			{
				inlineatt.putAll(map);
				map = inlineatt;
			}
		}
		final Block block = new Block(map);
		return block;
	}

	/**
	 * 
	 * 获得位置所在InLine对象
	 * 
	 * @param 无
	 * @return 位置所在Inline
	 * @exception
	 */
	public CellElement getLeafElement()
	{
		return _leaf;
	}

	/**
	 * 
	 * 文档位置是在_offset标识的开始（左边）还是结束（右边）
	 * 
	 * @param 无
	 * @return true:文档位置为offset所标识的左位置，false为右位置
	 * @exception
	 */
	public boolean isStartPos()
	{
		return _isstart;
	}

	/*
	 * 
	 */
	public boolean isOn()
	{
		return _ison;
	}

	public int getOffset()
	{
		return this.offset;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (obj == null || !(obj instanceof DocumentPosition))
		{
			return false;
		}
		final DocumentPosition pos = (DocumentPosition) obj;
		return _leaf.equals(pos._leaf) && _isstart == pos._isstart
				&& (offset == pos.offset) && (_ison == pos._ison)
				//【添加：START】 by 李晓光 2009-12-28
				//不同的页采用了相同的static-content时，由于以上项目都是相等的，这样就不能触发光标改变事件，使得光标不能被及时更新。
				&& pageIndex == pos.pageIndex;
				//【添加：END】 by 李晓光 2009-12-28
	}

	/**
 * 
 */
	public int compareTo(final DocumentPosition pos)
	{
		if (pos == null)
		{
			return 1;
		} else
		{
			// 如果相等，则返回相等
			if (this.equals(pos))
			{
				return 0;
			}
			final Element leaf = pos.getLeafElement();
			Element parent = leaf;
			Element parentthis = _leaf;
			if (leaf.equals(_leaf))
			{
				if (!this._isstart)
				{
					return 1;
				} else
				{
					return -1;
				}
			}
			b: while (parentthis != null && !(parentthis instanceof Document))
			{
				parent = leaf;
				while (parent != null && !(parent instanceof Document))
				{
					if (parent.equals(parentthis))
					{
						break b;
					}
					parent = parent.getParent();
				}
				parentthis = parentthis.getParent();
			}
			// 如果有共同的父对象，即使在同一文档中的位置
			if (parent != null && parent.equals(parentthis))
			{
				final int offsetthis = parentthis.getoffsetofPos(this);
				final int offset = parentthis.getoffsetofPos(pos);
				if (offsetthis > offset)
				{
					return 1;
				} else
				{
					return -1;
				}
			}
			// 如果不在同一文档内，则返回特殊标识
			return NOTONEDOCUMENT;
		}

	}

	/**
	 * @param _leaf
	 *            设置_leaf成员变量的值 值约束说明
	 */
	void setLeaf(final CellElement _leaf)
	{
		this._leaf = _leaf;
		if (_leaf instanceof Inline)
		{
			final Block block = ((DefaultElement) _leaf).getCurrentBlock();
			if (block == null)
			{
				return;
			}
			offset = block.calcOffsetInBlock(_leaf);
		}
	}

	/**
	 * @param _isstart
	 *            设置_isstart成员变量的值 值约束说明
	 */
	void setIsstart(final boolean isstart)
	{
		this._isstart = isstart;
	}

	/*
	 * 
	 * 获得当前光标的inline属性
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * 
	 * @return {返回参数名} {返回参数说明}
	 * 
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public Map<Integer, Object> getInlineAttriute()
	{
		if (inlineAtts == null)
		{
			return null;
		}
		return new HashMap<Integer, Object>(inlineAtts);
	}

	/*
	 * 
	 * 获得当前光标的inline属性
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * 
	 * @return {返回参数名} {返回参数说明}
	 * 
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public Map<Integer, Object> getBlockAttriute()
	{
		if (blockAtts == null)
		{
			return null;
		}
		return new HashMap<Integer, Object>(blockAtts);
	}

	/**
	 * 
	 * 设置当前光标的inline属性
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public void setInlineAttributes(final Map<Integer, Object> newatt,
			final boolean isreplace)
	{
		inlineAtts = setAttributes(newatt, inlineAtts, isreplace);
	}

	/**
	 * 
	 * 设置当前光标的段落属性
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public void setBlockAttributes(final Map<Integer, Object> newatt,
			final boolean isreplace)
	{
		blockAtts = setAttributes(newatt, blockAtts, isreplace);
	}

	private Map<Integer, Object> setAttributes(
			final Map<Integer, Object> newatt,
			final Map<Integer, Object> oldatt, final boolean isreplace)
	{
		if (newatt != null)
		{
			Map<Integer, Object> map = null;
			if (isreplace)
			{
				map = newatt;
			} else
			{
				if (oldatt != null)
				{
					map = oldatt;
				} else
				{
					map = new HashMap<Integer, Object>();
				}
			}
			final Iterator<Integer> it = newatt.keySet().iterator();
			while (it.hasNext())
			{
				final int i = it.next();
				Object newvalue = newatt.get(i);
				if (newvalue == null
						|| i == Constants.PR_ENDOFALL
						|| i == Constants.PR_ID
						|| i == Constants.PR_CONDTION
						|| i == Constants.PR_GROUP
						|| i == Constants.PR_DYNAMIC_STYLE
						|| i == Constants.PR_TRANSFORM_TABLE
						|| i == Constants.PR_ID
						|| i == Constants.PR_GRAPHIC_LAYER
						|| (i >= Constants.PR_EDITTYPE && i <= Constants.PR_GROUP_NONE_SELECT_VALUE))
				{
					map.remove(i);
				} else
				{
					if (!isreplace)
					{
						// 如果是颜色属性值，则需要做特殊处理
						if (newvalue instanceof Color)
						{
							if (newvalue instanceof WiseDocColor)
							{
								final WiseDocColor color = (WiseDocColor) newvalue;
								// 如果只需要设置颜色，则保留原属性的层
								if (color.isSetColor())
								{
									final Color oldcolor = (Color) oldatt
											.get(i);
									if (oldcolor != null)
									{
										int layer = 0;
										if (oldcolor instanceof WiseDocColor)
										{
											layer = ((WiseDocColor) oldcolor)
													.getLayer();
										}
										newvalue = new WiseDocColor(color,
												layer);
									}
								}
								// 如果是设置层，则保留原属性的颜色
								else if (color.isSetLayer())
								{
									final Color oldcolor = (Color) oldatt
											.get(i);
									if (oldcolor != null)
									{
										newvalue = new WiseDocColor(oldcolor,
												(color).getLayer());
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
			}
			if (map.isEmpty())
			{
				map = null;
			}
			return map;
		} else
		{
			return null;
		}

	}

	@Override
	public String toString()
	{
		final StringBuilder s = new StringBuilder("[leaf = ");
		s.append(_leaf);
		s.append(", On = ");
		s.append(_ison);
		s.append(", start = ");
		s.append(_isstart);
		s.append(", offset = ");
		s.append(offset);
		s.append("]");
		return s.toString();
	}
}
