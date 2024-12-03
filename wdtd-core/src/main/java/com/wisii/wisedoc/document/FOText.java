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
 * @FOText.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.swing.tree.TreeNode;

import com.wisii.wisedoc.apps.FOUserAgent;
import com.wisii.wisedoc.area.inline.TextArea;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.CommonFont;
import com.wisii.wisedoc.document.attribute.CommonHyphenation;
import com.wisii.wisedoc.document.attribute.CommonTextDecoration;
import com.wisii.wisedoc.document.attribute.Property;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-8-28
 */
public class FOText implements CellElement
{
	/* 【添加：START】 by 李晓光 2008-09-23*/
	private List<TextArea> areas = new ArrayList<TextArea>();
	/* 表示当前FOText是否被看做整体 */
	private boolean whole = Boolean.FALSE;
	private boolean bind = Boolean.FALSE;
	private int spaceNumber = -1;
	public boolean isSpace(){
		return (spaceNumber != -1);
	}
	public int getSpaceNumber() {
		return spaceNumber;
	}
	public void setSpaceNumber(int spaceNumber) {
		this.spaceNumber = spaceNumber;
	}
	public boolean isBind() {
		return bind;
	}
	public void setBind(boolean bind) {
		this.bind = bind;
	}
	private CommonFont  commonfont;
	private CommonHyphenation commonhyphenation;
	private CommonTextDecoration commontextdecoration;
	@Override
	public void initFOProperty()
	{
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
		if(commontextdecoration == null)
		{
			commontextdecoration = new CommonTextDecoration(this);
		}
		else
		{
			commontextdecoration.init(this);
		}
	}
	public boolean isWhole() {
		return whole;
	}

	public void setWhole(final boolean whole) {
		this.whole = whole;
	}

	public TextArea getArea() {
		if(areas == null || areas.isEmpty()) {
			return null;
		}
		return areas.get(0);
	}
	public TextArea getArea(final boolean isStart){
		if(areas == null || areas.isEmpty()) {
			return null;
		}
		if(isStart) {
			return areas.get(0);
		}
		return areas.get(areas.size() - 1);
	}
	
	/***
	 * 如果一个FO-Text是一个绑定的，这就要求把他们看作一个整体，如果在这个整体
	 * 放在一个单元格内，且一行放不下，则需要换行，这样原来的光标位置计算是有问
	 * 题的。
	 * @param area
	 */
	public void addArea(final TextArea area){
		areas.add(area);
	}
	public List<TextArea> getAreas(){
		return areas;
	}
	/* 删除 by 李晓光 2009-12-3 */
	//由于目前的位置信息采用的是offset【相对当前Block的】的方式，该设置就没有意义了。
	//该设置当初是为了处理合并前的element 和 Area的对应关系。
	/*public void setArea(final TextArea area) {
		reset();
		areas.add(area);
	}*/
	/*public void setAreas(final List<TextArea> areas){
		if(areas == null) {
			this.areas.clear();
		} else {
			this.areas = areas;
		}
	}*/
	public void reset() { 
		areas.clear();
	}
	/* 【添加：END】 by 李晓光 2008-09-23*/
	public char[] ca;

	CellElement _parent;

	/**
	 * The starting valid index of the ca array to be processed.
	 * 
	 * This value is originally equal to 0, but becomes incremented during
	 * leading whitespace removal by the flow.Block class, via the
	 * TextCharIterator.remove() method below.
	 */
	public int startIndex = 0;

	/**
	 * The ending valid index of the ca array to be processed.
	 * 
	 * This value is originally equal to ca.length, but becomes decremented
	 * during between-word whitespace removal by the flow.Block class, via the
	 * TextCharIterator.remove() method below.
	 */
	public int endIndex = 0;
	
	/**
	 * Keeps track of the last FOText object created within the current block.
	 * This is used to create pointers between such objects. TODO: As soon as
	 * the control hierarchy is straightened out, this static variable needs to
	 * become an instance variable in some parent object, probably the
	 * page-sequence.
	 */
	private static FOText lastFOTextProcessed = null;

	/**
	 * Points to the previous FOText object created within the current block. If
	 * this is "null", this is the first such object.
	 */
	private FOText prevFOTextThisBlock = null;

	/**
	 * Points to the next FOText object created within the current block. If
	 * this is "null", this is the last such object.
	 */
	private FOText nextFOTextThisBlock = null;

	/**
	 * Points to the ancestor Block object. This is used to keep track of which
	 * FOText nodes are descendants of the same block.
	 */
	private Block ancestorBlock = null;

	private static final int IS_WORD_CHAR_FALSE = 0;

	private static final int IS_WORD_CHAR_TRUE = 1;

	private static final int IS_WORD_CHAR_MAYBE = 2;

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public FOText(final CellElement parent, final char[] ca)
	{
		_parent = parent;
		endIndex = ca.length;
		this.ca = ca;
		createBlockPointers(parent);
		textTransform();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#getAttribute(int)
	 */
	public Object getAttribute(final int key)
	{
		return _parent.getAttribute(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#getAttributes()
	 */
	public Attributes getAttributes()
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#getNameId()
	 */
	public int getNameId()
	{
		return Constants.FO_UNKNOWN_NODE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#getUserAgent()
	 */
	public FOUserAgent getUserAgent()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#getoffsetofPos(com.wisii.wisedoc.document.DocumentPosition)
	 */
	public int getoffsetofPos(final DocumentPosition pos)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#insert(java.util.List, int)
	 */
	public void insert(final List<CellElement> children, final int childIndex)
	{
		return;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#removeAttributes(int)
	 */
	public void removeAttributes(final int key)
	{
		return;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#removeChildren(java.util.List)
	 */
	public void removeChildren(final List<CellElement> children)
	{
		return;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#setAttribute(int,
	 *      java.lang.Object)
	 */
	public void setAttribute(final int key, final Object value)
	{
		return;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#setAttributes(java.util.Map,
	 *      boolean)
	 */
	public void setAttributes(final Map<Integer, Object> atts, final boolean isreplace)
	{
		return;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#children()
	 */
	public Enumeration children()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getAllowsChildren()
	 */
	public boolean getAllowsChildren()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getChildAt(int)
	 */
	public CellElement getChildAt(final int childIndex)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getChildCount()
	 */
	public int getChildCount()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getIndex(javax.swing.tree.TreeNode)
	 */
	public int getIndex(final TreeNode node)
	{
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getParent()
	 */
	public CellElement getParent()
	{
		return _parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#isLeaf()
	 */
	public boolean isLeaf()
	{
		return true;
	}

	@Override
	public Element clone()
	{
		Element fotext = null;
		try
		{
			fotext = (Element) super.clone();
		} catch (final CloneNotSupportedException e)
		{
			LogUtil.debugException("克隆FOText出错", e);
		}
		
		return fotext;
	}

	/**
	 * Check if this text node will create an area. This means either there is
	 * non-whitespace or it is preserved whitespace. Maybe this just needs to
	 * check length > 0, since char iterators handle whitespace.
	 * 
	 * @return true if this will create an area in the output
	 */
	public boolean willCreateArea()
	{
		final int whiteSpaceCollapse = (Integer) getAttribute(Constants.PR_WHITE_SPACE_COLLAPSE);
		if (whiteSpaceCollapse == Constants.EN_FALSE
				&& endIndex - startIndex > 0)
		{
			return true;
		}
		for (int i = startIndex; i < endIndex; i++)
		{
			final char ch = ca[i];
			if (!((ch == ' ') || (ch == '\n') || (ch == '\r') || (ch == '\t')))
			{ // whitespace
				return true;
			}
		}
		return false;
	}

	/**
	 * @return a new TextCharIterator
	 */
	public CharIterator charIterator()
	{
		return new TextCharIterator();
	}

	/**
	 * This method is run as part of the ancestor Block's flushText(), to create
	 * xref pointers to the previous FOText objects within the same Block
	 */
	protected void createBlockPointers(Element ancestorBlock)
	{
		while (ancestorBlock != null && !(ancestorBlock instanceof Block)
				&& !(ancestorBlock instanceof Document))
		{
			ancestorBlock = ancestorBlock.getParent();
		}
		if (ancestorBlock instanceof Block)
		{
			this.ancestorBlock = (Block) ancestorBlock;
			// if the last FOText is a sibling, point to it, and have it point
			// here
			if (lastFOTextProcessed != null)
			{
				if (lastFOTextProcessed.ancestorBlock == this.ancestorBlock)
				{
					prevFOTextThisBlock = lastFOTextProcessed;
					prevFOTextThisBlock.nextFOTextThisBlock = this;
				} else
				{
					prevFOTextThisBlock = null;
				}
			}
			// save the current node in static field so the next guy knows where
			// to look
			lastFOTextProcessed = this;
			return;
		}
	}

	/**
	 * This method is run as part of the Constructor, to handle the
	 * text-transform property.
	 */
	private void textTransform()
	{
		final int textTransform = (Integer) getAttribute(Constants.PR_TEXT_TRANSFORM);
		if (textTransform == Constants.EN_NONE)
		{
			return;
		}
		for (int i = 0; i < endIndex; i++)
		{
			ca[i] = charTransform(i, textTransform);
		}
	}

	/**
	 * Transforms one character in ca[] using the text-transform property.
	 * 
	 * @param i
	 *            the index into ca[]
	 * @return char with transformed value
	 */
	private char charTransform(final int i, final int textTransform)
	{
		switch (textTransform)
		{
		/* put NONE first, as this is probably the common case */
		case Constants.EN_NONE:
			return ca[i];
		case Constants.EN_UPPERCASE:
			return Character.toUpperCase(ca[i]);
		case Constants.EN_LOWERCASE:
			return Character.toLowerCase(ca[i]);
		case Constants.EN_CAPITALIZE:
			if (isStartOfWord(i))
			{
				/*
				 * Use toTitleCase here. Apparently, some languages use a
				 * different character to represent a letter when using initial
				 * caps than when all of the letters in the word are
				 * capitalized. We will try to let Java handle this.
				 */
				return Character.toTitleCase(ca[i]);
			} else
			{
				return ca[i];
			}
		default:
			LogUtil.warn("Invalid text-tranform value: " + textTransform);
			return ca[i];
		}
	}

	/**
	 * Determines whether a particular location in an FOText object's text is
	 * the start of a new "word". The use of "word" here is specifically for the
	 * text-transform property, but may be useful for other things as well, such
	 * as word-spacing. The definition of "word" is somewhat ambiguous and
	 * appears to be definable by the user agent.
	 * 
	 * @param i
	 *            index into ca[]
	 * 
	 * @return True if the character at this location is the start of a new
	 *         word.
	 */
	private boolean isStartOfWord(final int i)
	{
		final char prevChar = getRelativeCharInBlock(i, -1);
		/*
		 * All we are really concerned about here is of what type prevChar is.
		 * If inputChar is not part of a word, then the Java conversions will
		 * (we hope) simply return inputChar.
		 */
		switch (isWordChar(prevChar))
		{
		case IS_WORD_CHAR_TRUE:
			return false;
		case IS_WORD_CHAR_FALSE:
			return true;
			/*
			 * "MAYBE" implies that additional context is needed. An example is
			 * a single-quote, either straight or closing, which might be
			 * interpreted as a possessive or a contraction, or might be a
			 * closing quote.
			 */
		case IS_WORD_CHAR_MAYBE:
			final char prevPrevChar = getRelativeCharInBlock(i, -2);
			switch (isWordChar(prevPrevChar))
			{
			case IS_WORD_CHAR_TRUE:
				return false;
			case IS_WORD_CHAR_FALSE:
				return true;
			case IS_WORD_CHAR_MAYBE:
				return true;
			default:
				return false;
			}
		default:
			return false;
		}
	}

	/**
	 * Finds a character within the current Block that is relative in location
	 * to a character in the current FOText. Treats all FOText objects within a
	 * block as one unit, allowing text in adjoining FOText objects to be
	 * returned if the parameters are outside of the current object.
	 * 
	 * @param i
	 *            index into ca[]
	 * @param offset
	 *            signed integer with relative position within the block of the
	 *            character to return. To return the character immediately
	 *            preceding i, pass -1. To return the character immediately
	 *            after i, pass 1.
	 * @return the character in the offset position within the block; \u0000 if
	 *         the offset points to an area outside of the block.
	 */
	private char getRelativeCharInBlock(final int i, final int offset)
	{
		// The easy case is where the desired character is in the same FOText
		if (((i + offset) >= 0) && ((i + offset) <= this.endIndex))
		{
			return ca[i + offset];
		}
		// For now, we can't look at following FOText nodes
		if (offset > 0)
		{
			return '\u0000';
		}
		// Remaining case has the text in some previous FOText node
		boolean foundChar = false;
		char charToReturn = '\u0000';
		FOText nodeToTest = this;
		int remainingOffset = offset + i;
		while (!foundChar)
		{
			if (nodeToTest.prevFOTextThisBlock == null)
			{
				foundChar = true;
				break;
			}
			nodeToTest = nodeToTest.prevFOTextThisBlock;
			if ((nodeToTest.endIndex + remainingOffset) >= 0)
			{
				charToReturn = nodeToTest.ca[nodeToTest.endIndex
						+ remainingOffset];
				foundChar = true;
			} else
			{
				remainingOffset = remainingOffset + nodeToTest.endIndex;
			}
		}
		return charToReturn;
	}

	/**
	 * Determines whether the input char should be considered part of a "word".
	 * This is used primarily to determine whether the character immediately
	 * following starts a new word, but may have other uses. We have not found a
	 * definition of "word" in the standard (1.0), so the logic used here is
	 * based on the programmer's best guess.
	 * 
	 * @param inputChar
	 *            the character to be tested.
	 * @return int IS_WORD_CHAR_TRUE, IS_WORD_CHAR_FALSE, or IS_WORD_CHAR_MAYBE,
	 *         depending on whether the character should be considered part of a
	 *         word or not.
	 */
	public static int isWordChar(final char inputChar)
	{
		switch (Character.getType(inputChar))
		{
		case Character.COMBINING_SPACING_MARK:
			return IS_WORD_CHAR_TRUE;
		case Character.CONNECTOR_PUNCTUATION:
			return IS_WORD_CHAR_TRUE;
		case Character.CONTROL:
			return IS_WORD_CHAR_FALSE;
		case Character.CURRENCY_SYMBOL:
			return IS_WORD_CHAR_TRUE;
		case Character.DASH_PUNCTUATION:
			if (inputChar == '-')
			{
				return IS_WORD_CHAR_TRUE; // hyphen
			}
			return IS_WORD_CHAR_FALSE;
		case Character.DECIMAL_DIGIT_NUMBER:
			return IS_WORD_CHAR_TRUE;
		case Character.ENCLOSING_MARK:
			return IS_WORD_CHAR_FALSE;
		case Character.END_PUNCTUATION:
			if (inputChar == '\u2019')
			{
				return IS_WORD_CHAR_MAYBE; // apostrophe, right single quote
			}
			return IS_WORD_CHAR_FALSE;
		case Character.FORMAT:
			return IS_WORD_CHAR_FALSE;
		case Character.LETTER_NUMBER:
			return IS_WORD_CHAR_TRUE;
		case Character.LINE_SEPARATOR:
			return IS_WORD_CHAR_FALSE;
		case Character.LOWERCASE_LETTER:
			return IS_WORD_CHAR_TRUE;
		case Character.MATH_SYMBOL:
			return IS_WORD_CHAR_FALSE;
		case Character.MODIFIER_LETTER:
			return IS_WORD_CHAR_TRUE;
		case Character.MODIFIER_SYMBOL:
			return IS_WORD_CHAR_TRUE;
		case Character.NON_SPACING_MARK:
			return IS_WORD_CHAR_TRUE;
		case Character.OTHER_LETTER:
			return IS_WORD_CHAR_TRUE;
		case Character.OTHER_NUMBER:
			return IS_WORD_CHAR_TRUE;
		case Character.OTHER_PUNCTUATION:
			if (inputChar == '\'')
			{
				return IS_WORD_CHAR_MAYBE; // ASCII apostrophe
			}
			return IS_WORD_CHAR_FALSE;
		case Character.OTHER_SYMBOL:
			return IS_WORD_CHAR_TRUE;
		case Character.PARAGRAPH_SEPARATOR:
			return IS_WORD_CHAR_FALSE;
		case Character.PRIVATE_USE:
			return IS_WORD_CHAR_FALSE;
		case Character.SPACE_SEPARATOR:
			return IS_WORD_CHAR_FALSE;
		case Character.START_PUNCTUATION:
			return IS_WORD_CHAR_FALSE;
		case Character.SURROGATE:
			return IS_WORD_CHAR_FALSE;
		case Character.TITLECASE_LETTER:
			return IS_WORD_CHAR_TRUE;
		case Character.UNASSIGNED:
			return IS_WORD_CHAR_FALSE;
		case Character.UPPERCASE_LETTER:
			return IS_WORD_CHAR_TRUE;
		default:
			return IS_WORD_CHAR_FALSE;
		}
	}

	/**
	 * @return The previous FOText node in this Block; null, if this is the
	 *         first FOText in this Block.
	 */
	public FOText getPrevFOTextThisBlock()
	{
		return prevFOTextThisBlock;
	}

	/**
	 * @return The next FOText node in this Block; null if this is the last
	 *         FOText in this Block; null if subsequent FOText nodes have not
	 *         yet been processed.
	 */
	public FOText getNextFOTextThisBlock()
	{
		return nextFOTextThisBlock;
	}

	/**
	 * @return The nearest ancestor block object which contains this FOText.
	 */
	public Block getAncestorBlock()
	{
		return ancestorBlock;
	}

	/**
	 * @return the Common Font Properties.
	 */
	public CommonFont getCommonFont()
	{
		return commonfont;
	}

	/**
	 * @return the Common Hyphenation Properties.
	 */
	public CommonHyphenation getCommonHyphenation()
	{		
		return commonhyphenation;
	}

	/**
	 * @return the "color" property.
	 */
	public Color getColor()
	{
		return (Color) getAttribute(Constants.PR_COLOR);
	}

	/**
	 * @return the "letter-spacing" property.
	 */
	public Property getLetterSpacing()
	{
		return (Property) getAttribute(Constants.PR_LETTER_SPACING);
	}

	/**
	 * @return the "line-height" property.
	 */
	public SpaceProperty getLineHeight()
	{
		return (SpaceProperty) getAttribute(Constants.PR_LINE_HEIGHT);
	}

	/**
	 * @return the "white-space-treatment" property
	 */
	public int getWhitespaceTreatment()
	{
		return (Integer) getAttribute(Constants.PR_WHITE_SPACE_TREATMENT);
	}

	/**
	 * @return the "word-spacing" property.
	 */
	public Property getWordSpacing()
	{
		return (Property) getAttribute(Constants.PR_WORD_SPACING);
	}

	/**
	 * @return the "wrap-option" property.
	 */
	public int getWrapOption()
	{
		return (Integer) getAttribute(Constants.PR_WRAP_OPTION);
	}

	/** @return the "text-decoration" property. */
	public CommonTextDecoration getTextDecoration()
	{
		return new CommonTextDecoration(this);
	}

	/** @return the baseline-shift property */
	public Length getBaseLineShift()
	{
		return (Length) getAttribute(Constants.PR_BASELINE_SHIFT);
	}

	/** @see java.lang.Object#toString() */
	@Override
	public String toString()
	{
		final StringBuffer sb = new StringBuffer(super.toString());
		sb.append(" (").append(ca).append(")");
		return sb.toString();
	}

	/** @see com.wisii.fov.fo.FONode#getLocalName() */
	public String getLocalName()
	{
		return null;
	}

	/** @see com.wisii.fov.fo.FONode#getNormalNamespacePrefix() */
	public String getNormalNamespacePrefix()
	{
		return null;
	}

	private class TextCharIterator extends CharIterator
	{
		private int curIndex = 0;

		/*
		 * Current space removal process: just increment the startIndex to
		 * "remove" leading spaces from ca, until an unremoved character is
		 * found. Then perform arraycopy's to remove extra spaces between words.
		 * nextCharCalled is used to determine if an unremoved character has
		 * already been found--if its value > 2 than it means that has occurred
		 * (it is reset to zero each time we remove a space via incrementing the
		 * startIndex.)
		 */
		private int nextCharCalled = 0;

		@Override
		public boolean hasNext()
		{
			if (curIndex == 0)
			{
				// log.debug("->" + new String(ca) + "<-");
			}
			return (curIndex < endIndex);
		}

		@Override
		public char nextChar()
		{
			if (curIndex < endIndex)
			{
				nextCharCalled++;
				// Just a char class? Don't actually care about the value!
				return ca[curIndex++];
			} else
			{
				throw new NoSuchElementException();
			}
		}

		@Override
		public void remove()
		{
			if (curIndex < endIndex && nextCharCalled < 2)
			{
				startIndex++;
				nextCharCalled = 0;
				// log.debug("removeA: " + new String(ca, startIndex, endIndex -
				// startIndex));
			} else if (curIndex < endIndex)
			{
				// copy from curIndex to end to curIndex-1
				System.arraycopy(ca, curIndex, ca, curIndex - 1, endIndex
						- curIndex);
				endIndex--;
				curIndex--;
				// log.debug("removeB: " + new String(ca, startIndex, endIndex -
				// startIndex));
			} else if (curIndex == endIndex)
			{
				// log.debug("removeC: " + new String(ca, startIndex, endIndex -
				// startIndex));
				endIndex--;
				curIndex--;
			}
		}

		@Override
		public void replaceChar(final char c)
		{
			if (curIndex > 0 && curIndex <= endIndex)
			{
				ca[curIndex - 1] = c;
			}
		}

	}

	public void add(final CellElement newChild)
	{
	}

	public Iterator<CellElement> getChildren()
	{
		return null;
	}

	public List<CellElement> getChildren(final int childIndex)
	{
		return null;
	}

	public List<CellElement> getChildren(final int childIndex, final int endindex)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void insert(final CellElement newChild, final int childIndex)
	{

	}

	public void remove(final int childIndex)
	{
	}

	public void remove(final CellElement child)
	{

	}

	public void removeAllChildren()
	{

	}

	public void setParent(final Element newParent)
	{
		_parent = (CellElement) newParent;
	}

	public ListIterator getChildNodes()
	{
		return null;
	}

	public String getId()
	{
		return (String) getAttribute(Constants.PR_ID);
	}
	@Override
	public List<CellElement> getAllChildren()
	{
		return Collections.EMPTY_LIST;
	}
}
