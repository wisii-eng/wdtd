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
 * @Block.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.wisii.wisedoc.document.attribute.CommonAccessibility;
import com.wisii.wisedoc.document.attribute.CommonAural;
import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground;
import com.wisii.wisedoc.document.attribute.CommonFont;
import com.wisii.wisedoc.document.attribute.CommonHyphenation;
import com.wisii.wisedoc.document.attribute.CommonMarginBlock;
import com.wisii.wisedoc.document.attribute.CommonRelativePosition;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.KeepProperty;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.document.datatype.Numeric;
import com.wisii.wisedoc.layoutmanager.LayoutContext;
import com.wisii.wisedoc.layoutmanager.LayoutManager;
import com.wisii.wisedoc.layoutmanager.ListElement;

/**
 * 类功能描述：段落对象
 * 
 * 作者：zhangqiang 创建日期：2008-4-14
 */
public class Block extends DefaultElement implements Groupable
{

	// 上一次排版得到的可断行点的结果
	private final List<LinkedList> linkedlists = new ArrayList<LinkedList>();

	// 最后一次排版的排版环境
	private LayoutContext layoutcontext;

	private LinkedList contentlist;

	// 上次排版的layout
	private LayoutManager oldlayoutmanager;

	/* 【添加：START】 by 李晓光 2008-11-25 */
	/* 判断当前的Block是否是StaticContent的子对象。 */
	private boolean staticParent = Boolean.FALSE;
	
//	边框，背景，padding等属性
	private CommonBorderPaddingBackground cbpbackground;
	private CommonFont  commonfont;
	private CommonHyphenation commonhyphenation;
	private CommonMarginBlock commonmarginblock;
	//add by zhangqiang用来记录空段落的文字属性，
	//在段尾按回车新建一个段落时，将会
	//用该属性记录回车前段尾文字的属性
	private Map<Integer,Object> inlineatt;
	
	
	public boolean isStaticParent() {
		return staticParent;
	}

	public void setStaticParent(final boolean staticParent) {
		this.staticParent = staticParent;
	}

	private List<com.wisii.wisedoc.area.Block> blocks = new ArrayList<com.wisii.wisedoc.area.Block>();

	public void reset()
	{
		blocks.clear();
	}

	/* 【添加：END】 by 李晓光 2008-11-25 */
	public Block()
	{
		super();
		init();
	}

	public Block(final Map<Integer, Object> attributes)
	{
		super(attributes);
		init();
	}

	private void init()
	{
		initFOProperty();
	}
	@Override
	public void initFOProperty()
	{
		// TODO Auto-generated method stub
		super.initFOProperty();
		if(cbpbackground == null)
		{
			cbpbackground = new CommonBorderPaddingBackground(this);
		}
		else
		{
			cbpbackground.init(this);
		}
		if(commonfont == null)
		{
			commonfont = new CommonFont(this);
		}
		else
		{
			commonfont.init(this);
		}
		if(commonhyphenation == null)
		{
			commonhyphenation = new CommonHyphenation(this);
		}
		else
		{
			commonhyphenation.init(this);
		}
		if(commonmarginblock == null)
		{
			commonmarginblock = new CommonMarginBlock(this);
		}
		else
		{
			commonmarginblock.init(this);
		}
	}

	/**
	 * 
	 * {方法的功能/动作描述}
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public CommonMarginBlock getCommonMarginBlock()
	{
		return commonmarginblock;
	}

	/**
	 * @return the Common Border, Padding, and Background Properties.
	 */
	public CommonBorderPaddingBackground getCommonBorderPaddingBackground()
	{
		return cbpbackground;
	}

	/**
	 * @return the Common Hyphenation Properties.
	 */
	public CommonHyphenation getCommonHyphenation()
	{
		return commonhyphenation;
	}

	/**
	 * @return the Common Font Properties.
	 */
	public CommonFont getCommonFont()
	{
		return commonfont;
	}

	/**
	 * 
	 * 获得后分页属性
	 * 
	 * @param
	 * 
	 * @return
	 * @exception
	 */
	public int getBreakAfter()
	{
		return ((EnumProperty) getAttribute(Constants.PR_BREAK_AFTER))
				.getEnum();
	}

	/**
	 * 
	 * 获得前分页属性
	 * 
	 * @param
	 * 
	 * @return
	 * @exception
	 */
	public int getBreakBefore()
	{
		return ((EnumProperty) getAttribute(Constants.PR_BREAK_BEFORE))
				.getEnum();
	}

	/**
	 * 
	 * {方法的功能/动作描述}
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public Numeric getHyphenationLadderCount()
	{
		return (Numeric) getAttribute(Constants.PR_HYPHENATION_LADDER_COUNT);
	}

	/** @return the "keep-with-next" property. */
	public KeepProperty getKeepWithNext()
	{
		return (KeepProperty) getAttribute(Constants.PR_KEEP_WITH_NEXT);
	}

	/** @return the "keep-with-previous" property. */
	public KeepProperty getKeepWithPrevious()
	{
		return (KeepProperty) getAttribute(Constants.PR_KEEP_WITH_PREVIOUS);
	}

	/** @return the "keep-together" property. */
	public KeepProperty getKeepTogether()
	{
		return (KeepProperty) getAttribute(Constants.PR_KEEP_TOGETHER);
	}

	/**
	 * 
	 * 返回孤行控制属性
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public int getOrphans()
	{
		return (Integer) getAttribute(Constants.PR_ORPHANS);
	}

	/**
	 * 
	 * 返回寡行控制属性
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public int getWidows()
	{
		return (Integer) getAttribute(Constants.PR_WIDOWS);
	}

	/**
	 * 
	 * {方法的功能/动作描述}
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public int getLineStackingStrategy()
	{
		return (Integer) getAttribute(Constants.PR_LINE_STACKING_STRATEGY);
	}

	/**
	 * 
	 * 返回颜色属性值
	 * 
	 * @param
	 * 
	 * @return
	 * @exception
	 */
	public Color getColor()
	{
		return (Color) getAttribute(Constants.PR_COLOR);
	}

	/**
	 * 
	 * 返回行高属性
	 * 
	 * @param
	 * 
	 * @return
	 * @exception
	 */
	public SpaceProperty getLineHeight()
	{
		return (SpaceProperty) getAttribute(Constants.PR_LINE_HEIGHT);
	}

	/**
	 * 
	 * 返回跨栏值
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public int getSpan()
	{
		return (Integer) getAttribute(Constants.PR_SPAN);
	}

	/**
	 * 
	 * 返回文本对齐属性
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public int getTextAlign()
	{

		return ((EnumProperty) getAttribute(Constants.PR_TEXT_ALIGN)).getEnum();
	}

	/**
	 * 
	 * 返回末行文本对齐属性
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public int getTextAlignLast()
	{
		return ((EnumProperty) getAttribute(Constants.PR_TEXT_ALIGN_LAST))
				.getEnum();
	}

	/**
	 * 
	 * 返回文本缩进属性
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public Length getTextIndent()
	{
		final Length hanging = (Length) getAttribute(Constants.PR_HANGING_INDENT);
		final Length textindent = (Length) getAttribute(Constants.PR_TEXT_INDENT);
		if (hanging != null)
		{
			if (textindent != null)
			{
				final FixedLength linshi = new FixedLength(textindent.getValue()
						- hanging.getValue());
				return linshi;
			} else
			{
				final FixedLength linshi = new FixedLength(-hanging.getValue());
				return linshi;
			}
		}
		return textindent;
	}

	/**
	 * 
	 * 返回文本缩进属性
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public Length getStartIndent()
	{
		final Length hanging = (Length) getAttribute(Constants.PR_HANGING_INDENT);
		final Length startindent = (Length) getAttribute(Constants.PR_START_INDENT);
		if (hanging != null)
		{
			if (startindent != null)
			{
				final FixedLength linshi = new FixedLength(startindent.getValue()
						+ hanging.getValue());
				return linshi;
			} else
			{
				final FixedLength linshi = new FixedLength(hanging.getValue());
				return linshi;
			}
		}
		return startindent;
	}

	/**
	 * 
	 * 返回文本末行缩进属性
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public Length getLastLineEndIndent()
	{
		return (Length) getAttribute(Constants.PR_LAST_LINE_END_INDENT);
	}

	/**
	 * 
	 * 
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public int getWrapOption()
	{
		return (Integer) getAttribute(Constants.PR_WRAP_OPTION);
	}

	/**
	 * 
	 * 返回换行符处理属性
	 * 
	 * @param
	 * 
	 * @return
	 * @exception
	 */
	public int getLinefeedTreatment()
	{
		return (Integer) getAttribute(Constants.PR_LINEFEED_TREATMENT);
	}

	/**
	 * 
	 * 返回空格处理属性
	 * 
	 * @param
	 * 
	 * @return
	 * @exception
	 */
	public int getWhitespaceTreatment()
	{
		return (Integer) getAttribute(Constants.PR_WHITE_SPACE_TREATMENT);
	}

	/**
	 * 
	 * 返回空格处理属性
	 * 
	 * @param
	 * 
	 * @return
	 * @exception
	 */
	public int getWhitespaceCollapse()
	{
		return (Integer) getAttribute(Constants.PR_WHITE_SPACE_COLLAPSE);
	}

	/**
	 * 
	 * 
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public CommonAccessibility getCommonAccessibility()
	{
		return new CommonAccessibility(this);
	}

	/**
	 * @return Returns the commonAural.
	 */
	public CommonAural getCommonAural()
	{
		return new CommonAural(this);
	}

	/**
	 * @return Returns the commonRelativePosition.
	 */
	public CommonRelativePosition getCommonRelativePosition()
	{
		return new CommonRelativePosition(this);
	}

	/**
	 * @return Returns the hyphenationKeep.
	 */
	public int getHyphenationKeep()
	{
		return (Integer) getAttribute(Constants.PR_HYPHENATION_KEEP);
	}

	/**
	 * @return Returns the intrusionDisplace.
	 */
	public int getIntrusionDisplace()
	{
		return (Integer) getAttribute(Constants.PR_INTRUSION_DISPLACE);
	}

	/**
	 * @return Returns the lineHeightShiftAdjustment.
	 */
	public int getLineHeightShiftAdjustment()
	{
		return (Integer) getAttribute(Constants.PR_LINE_HEIGHT_SHIFT_ADJUSTMENT);
	}

	/** @see com.wisii.fov.fo.FONode#charIterator() */
	public CharIterator charIterator()
	{
		return NullCharIterator.getInstance();
	}

	@Override
	public int getNameId()
	{
		return Constants.FO_BLOCK;
	}

	/** @see com.wisii.fov.fo.FONode#getChildNodes() */
	@Override
	public ListIterator getChildNodes()
	{
		List<CellElement> list;
		if (_children != null && !_children.isEmpty())
		{
			/* list = _children; */
			list = createTextlist();
		}
		// 如果block为空，则显示一换行符
		else
		{
			final TextInline inline = new TextInline(new Text('\n'), getInlineatt());
			inline.setParent(this);
			list = new ArrayList<CellElement>();
			list.add(inline);

		}
		return list.listIterator();
	}

	/*
	 * 排版是合并相同属性的文本元素
	 */
	private List<CellElement> createTextlist()
	{
		final int size = _children.size();
		final List<CellElement> list = new ArrayList<CellElement>();
		StringBuilder text = new StringBuilder();
		TextInline oldtext = null;
		for (int i = 0; i < size; i++)
		{
			final CellElement element = _children.get(i);
			if (!(element instanceof TextInline)
					|| ((element instanceof TextInline) && ((TextInline) element)
							.getContent().isBindContent()))
			{
				if (text.length() != 0)
				{
					final Inline inline = new Inline(oldtext.getAttributes().getAttributes());
					inline.add(new FOText(inline, text.toString().toCharArray()));
					list.add(inline);
					inline.setParent(this);
					text = new StringBuilder();
				}
				list.add(element);
				oldtext = null;
			} else
			{
				final TextInline textinline = (TextInline) element;
				if (oldtext == null
						|| ((oldtext != null && oldtext.getAttributes().equals(
								element.getAttributes()))&&issametype(oldtext,textinline)))
				{
//					text = text + textinline.getText();
					text.append(textinline.getText());
				} else
				{
					if (text.length() != 0)
					{
						String intext = text.toString();
//						如果有多个连续空格合并属性。则多个空格合并成一个空格
						int spaceNumber = -1;
						if(getWhitespaceCollapse()==Constants.EN_TRUE&&"".equals(intext.trim()))
						{
							spaceNumber = intext.length();
							intext = " ";
						}
						final Inline inline = new Inline(oldtext.getAttributes().getAttributes());
						final FOText t = new FOText(inline, intext.toCharArray());
						if(spaceNumber != -1){
							t.setSpaceNumber(spaceNumber);
						}
						inline.add(t);
						list.add(inline);
						text = new StringBuilder(textinline.getText());
						inline.setParent(this);
					}
				}
				oldtext = textinline;
			}
		}
		if (text.length() != 0)
		{
			final Inline inline = new Inline(oldtext.getAttributes().getAttributes());
			inline.add(new FOText(inline, text.toString().toCharArray()));
			list.add(inline);
			inline.setParent(this);
		}
		return list;
	}

	private boolean issametype(final TextInline oldtext, final TextInline textinline)
	{
		final String oldtexts = oldtext.getText();
		final String thistext = textinline.getText();
		char oldc = oldtexts.charAt(0);
		char thisc = thistext.charAt(0);
		if (oldc == ' ')
		{
			if (thisc == ' ')
			{
				return true;
			} else
			{
				return false;
			}
		} else
		{
			if (thisc == ' ')
			{
				return false;
			} else
			{
				boolean oldisletter = (oldc >= 0x4E00) && (oldc <= 0x9FBF);
				boolean thisisletter = (thisc >= 0x4E00) && (thisc <= 0x9FBF);
				return oldisletter == thisisletter;
			}
		}
	}

	/**
	 * @return the context
	 */
	public void setLayoutcontext(final LayoutContext layoutcontext)
	{
		this.layoutcontext = layoutcontext.clone();
		linkedlists.clear();
		contentlist = null;
	}

	public void addLinkedList(final LinkedList list)
	{
		if (list != null)
		{
			linkedlists.add(new LinkedList(list));
		}
	}

	public void setLayoutManager(final LayoutManager lm)
	{
		oldlayoutmanager = lm;
	}

	/**
	 * @return the context
	 */
	public LayoutContext getLayoutcontext()
	{
		return layoutcontext;
	}

	public List<LinkedList> getLinkedLists(final LayoutManager pl)
	{
		initParentLayout(pl);
		return linkedlists;
	}

	private void initParentLayout(final LayoutManager pl)
	{
		final List childrenlist = oldlayoutmanager.getChildLMs();
		if (childrenlist != null && !childrenlist.isEmpty())
		{
			final int size = childrenlist.size();
			for (int i = 0; i < size; i++)
			{
				final LayoutManager layout = (LayoutManager) childrenlist.get(i);
				layout.setParent(pl);

			}
			pl.addChildLMs(childrenlist);
		}
		oldlayoutmanager = pl;
	}

	/**
	 * @return the contentlist
	 */
	public LinkedList getContentlist()
	{

		return clonelist(contentlist);
	}

	/**
	 * @param contentlist
	 *            the contentlist to set
	 */
	public void setContentlist(final LinkedList list)
	{

		this.contentlist = clonelist(list);
	}

	private LinkedList clonelist(final LinkedList list)
	{
		if (list != null)
		{
			final LinkedList newlist = new LinkedList();
			final int size = list.size();
			for (int i = 0; i < size; i++)
			{
				final ListElement element = (ListElement) list.get(i);
				if (element != null)
				{
					newlist.add(element.clone());
				}
			}
			return newlist;
		} else
		{
			return null;
		}
	}

	/* 【添加：START】 by 李晓光 2008-11-25 */
	@Override
	public com.wisii.wisedoc.area.Block getArea()
	{
		if (blocks == null || blocks.size() == 0) {
			return null;
		}
		return blocks.get(0);
	}

	public void addArea(final com.wisii.wisedoc.area.Block area)
	{
		blocks.add(area);
	}

	public List<com.wisii.wisedoc.area.Block> getAllBlocks()
	{
		if (blocks == null) {
			blocks = new ArrayList<com.wisii.wisedoc.area.Block>();
		}
		return blocks;
	}

	/**
	 * 根据偏移量查找偏移量位置处的FOText、PageNumber、PageNumberCitation
	 * 
	 * @param offset
	 *            指定偏移量
	 * @return 返回偏移量位置出的CellElement。
	 */
	public CellElement getFOTextWithOffset(final int offset)
	{
		final FOText text = null;
		if (offset == -1) {
			return text;
		}
		return searchFOText(this, offset - 0);
	}

	private CellElement searchFOText(CellElement element, int offset)
	{
		if (element == null) {
			return null;
		}

		if (element instanceof FOText) {
			return element;
		}

		List<CellElement> elements = null;
		if (element instanceof Block) {
			elements = DocumentUtil.getElements(element, Inline.class);
		} else {
			elements = element.getChildren(0);
		}
		if(offset>elements.size()-1)
		{
			offset = elements.size()-1;
		}
		element = elements.get(offset);
		if ((element instanceof PageNumber)
				|| (element instanceof PageNumberCitation)) {
			return element;
		} else if (element instanceof Inline) {
			offset = 0;
		}
		return searchFOText(element, offset);
	}

	/***
	 * 计算指定FOText或Inline级别的FO元素在包含该元素的Block中的偏移量。
	 * 
	 * @param inline
	 *            指定要查找的元素。
	 * @return int 返回指定元素在当前Block中的偏移量。
	 */
	public int calcOffsetInBlock(final CellElement inline)
	{
		final OffsetBean bean = calcOffsetForBlockImp(this, inline);
		if (!bean.found) {
			return -1;
		}
		return bean.offset - 1;
	}

	private OffsetBean calcOffsetForBlockImp(final CellElement block,
			final CellElement inline)
	{
		final OffsetBean bean = new OffsetBean();
		final List<CellElement> list = block.getChildren(0);
		for (final CellElement ele : list)
		{
			if (ele == inline)
			{
				bean.found = Boolean.TRUE;
				bean.offset++;
				return bean;
			}
			if ((ele instanceof AbstractGraphics) || (ele instanceof FOText))
			{
				bean.offset++;
				return bean;
			} else if ((ele instanceof PageNumber)
					|| ele instanceof PageNumberCitation)
			{
				bean.offset++;
				continue;
			}

			final OffsetBean b = calcOffsetForBlockImp(ele, inline);
			bean.offset += b.offset;
			bean.found = b.found;
			if (b.found) {
				break;
			}
		}
		return bean;
	}

	private boolean isNeedIncrement(final CellElement ele)
	{
		return (ele instanceof AbstractGraphics) || (ele instanceof FOText)
				|| (ele instanceof PageNumber)
				|| (ele instanceof PageNumberCitation);
	}

	public int calcTotalOffsetForBlock()
	{
		return getChildCount();
	}

	private class OffsetBean
	{

		private int offset = 0;

		private boolean found = Boolean.FALSE;
	}

	/* 【添加：END】 by 李晓光 2008-11-25 */
	public boolean isGroupAble()
	{
		return true;
	}

	public boolean ischange()
	{
		if (oldlayoutmanager == null)
		{
			return true;
		}
		if (getAttributes().getAttribute(Constants.PR_SPACE_BEFORE) != null
				|| getAttributes().getAttribute(Constants.PR_SPACE_AFTER) != null)
		{
              return true;
		}
		if (getBreakBefore() != Constants.EN_AUTO
				|| getBreakAfter() != Constants.EN_AUTO)
		{
			return true;
		}
		if (iskeepSet(getKeepTogether()) || iskeepSet(getKeepWithNext())
				|| iskeepSet(getKeepWithPrevious()))
		{
			return true;
		}
		return false;
	}

	private boolean iskeepSet(final KeepProperty keep)
	{
		if (keep != null)
		{
			if ((keep.getWithinColumn() != null && keep.getWithinColumn()
					.getEnum() != Constants.EN_AUTO)
					|| (keep.getWithinLine() != null && keep.getWithinLine()
							.getEnum() != Constants.EN_AUTO)
					|| (keep.getWithinPage() != null && keep.getWithinPage()
							.getEnum() != Constants.EN_AUTO))
			{
				return true;
			}
		}
		return false;
	}
	@Override
	public String getId()
	{
		String id = super.getId();
		if(id==null)
		{
		   id = Integer.toHexString(this.hashCode());
		}
		return id;
	}

	/**
	 * @返回  inlineatt变量的值
	 */
	public final Map<Integer, Object> getInlineatt()
	{
		if(inlineatt==null)
		{
			return inlineatt;
		}
		return new HashMap<Integer, Object>(inlineatt);
	}

	/**
	 * @param inlineatt 设置inlineatt成员变量的值
	
	 * 值约束说明
	
	 */
	public final void setInlineatt(Map<Integer, Object> inlineatt)
	{
		this.inlineatt = inlineatt;
	}
	
}
