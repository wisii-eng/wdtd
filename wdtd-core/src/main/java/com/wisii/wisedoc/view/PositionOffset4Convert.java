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
 * @PositionConvert.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.MessageFormat;
import java.util.List;

import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.area.Block;
import com.wisii.wisedoc.area.LineArea;
import com.wisii.wisedoc.area.inline.SpaceArea;
import com.wisii.wisedoc.area.inline.TextArea;
import com.wisii.wisedoc.area.inline.Viewport;
import com.wisii.wisedoc.area.inline.WordArea;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.FOText;
import com.wisii.wisedoc.fonts.Font;
import com.wisii.wisedoc.util.CharUtilities;

/**
 * 类功能描述：
 * ●用于计算指定点位置的element，在当前block中的偏移量，
 * ●根据偏移量获得area在显示界面中的位置。
 * ●Point → Offset 和  Offset → Point
 * 
 * 1、这个偏移量的计算是相对当前Block的。
 * 2、每个字符偏移量为1。
 * 3、如果是图片、绑定节点，偏移量为1【FOText的isWhole】。
 * 4、被嵌套的Block中元素不累加到当前【父对象】Block中的偏移量计算上来。
 * 5、偏移量的起始索引是0。
 * 6、如果是图片、绑定节点，偏移量为elementCount = 1
 * 
 * 作者：李晓光 
 * 创建日期：2010-1-22
 */
final class PositionOffset4Convert {

	/**
	 * 根据偏移量信息，在area所对应的source中，查找偏移量对应的cell-element。
	 * @param area						指定area。
	 * @param offset					指定偏移量信息。
	 * @return	{@link CellElement}		返回找到的cell-element，如：fo-text、page-number、PageNumberCitation等。
	 */
	public final static CellElement getElementWithArea(final Area area, /*final*/ int offset) {
		/*long start = System.nanoTime();*/
		if (!(area instanceof TextArea) || offset < 0) {
			return area.getSource();
		}
		final CellElement block = (CellElement) LocationConvert.searchCellElement(area.getSource(), com.wisii.wisedoc.document.Block.class);
		offset = repairOffset(block, offset);
		final CellElement text = ((com.wisii.wisedoc.document.Block) block).getFOTextWithOffset(offset);
		
		/*long end = System.nanoTime();*/
		
		/*System.out.println("time length = " + (TimeUnit.NANOSECONDS.toMillis((end - start))));*/
		return text;
	}
	
	/**
	 * 获得指定Text-Area中，指定偏移量位置的现实区域。
	 * 坐标系：相对最近的Reference-Area
	 * 如：text-area：[ABC]，offset:1
	 * 则：返回B字符在界面中显示的位置。
	 * 
	 * @param area					指定Area【Inline-Area：如：text-area】
	 * @param offset				指定字符偏移位置[0,N]。
	 * @return {@link Rectangle2D} 	返回指定Area，指定偏移位置的字符占据的矩形区域
	 */
	public final static Rectangle2D getRectWithOffset(final Area area, int offset) {
		Rectangle2D r = null;
		if (!(area instanceof TextArea)) {
			return r;
		}
		
		TextArea text = (TextArea)area;
		
		return getRectFromTextArea(text, offset);
	}
	
	/**
	 * 计算当前的Area【TextArea、Viewport】包含指定点的字符，相对于当前Block中的偏移量。
	 * 如：Block[ABC]->TextArea[ABC],其坐标落在字母B上。则偏移量为1，如果坐标靠近B的前
	 * 沿，则isStart = True，否则为False。
	 * ※Area必须是包含指定坐标点的Area。
	 * @param area					指定Inline的Area
	 * @param p						指定坐标点。
	 * @return	{@link OffsetBean}	封装所有的计算信息，并返回。
	 */
	public final static OffsetBean calcOffsetOfBlock(final Area area, final Point2D p){
		OffsetBean bean = new OffsetBean();
		if (!(area instanceof TextArea) && !(area instanceof Viewport)) {
			return bean;
		}
		bean = calcOffsetOfInlineArea(area, p);		
		if (bean.offset == -1) {
			return bean;
		}
		
		int offset = 0;
		if(area instanceof TextArea){
			offset = ((TextArea)area).getStartIndex();
		}else if(area instanceof Viewport){
			offset = ((Viewport)area).getStartIndex();
		}
		
		offset += bean.getOffset();
		bean.setOffset(offset);
		
		/*System.out.println("bean = " + bean);*/
		
		return bean;
	}
	
	/**
	 * 根据指定的位置信息，获得位置信息表示的Inline-Area【text-area\Viewport】
	 * @param pos				指定位置信息。
	 * @return	{@link Area}	返回获得的Area，如果没有匹配的Area，则返回Null。
	 */
	@SuppressWarnings("unchecked")
	public final static Area getInlineArea(final DocumentPosition pos){
		final CellElement ele = pos.getLeafElement();
		final com.wisii.wisedoc.document.Block block = (com.wisii.wisedoc.document.Block) LocationConvert.searchCellElement(ele, com.wisii.wisedoc.document.Block.class);
		if (isNull(block) || block.getChildCount() == 0) {
			return null;
		}
		final List<Block> blocks = block.getAllBlocks();
		if(blocks.isEmpty())
			return null;
		/*int page = pos.getPageIndex();
		/*if(page == -1 || page >= blocks.size())
			return null;*/
		Block b = null;//blocks.get(page);
		for (Block temp : blocks) {
			if(temp.getStartIndex() + temp.getElementCount() > pos.getOffset()){
				b = temp;
				break;
			}
		}
		
		Bean<Area> bean = Bean.getBean();
		bean.offset = pos.getOffset();
		bean.begin = pos.isStartPos();
		
		return getAreaBeanWithOffset(b, bean);
	}
	//-----------------------------------计算：从Offset to Point-----------------------------------------------
	/**
	 * 修正偏移量：
	 * 1.可以共同应用的cell-element中的偏移量是累加的，这样就避免了。同一页中region-before和region-after共用static-content的情况。
	 * 2.但这要求在根据offset查找block中对应的cell-element时，需要重新修正下offset。
	 * 如：block-element 创建两个block-area[0, 20]和[20, 20]
	 * 要修正第二个block的起始索引是：零。
	 * @param blockElment					指定要修正的cell-element。
	 * @param offset						指定偏移量。
	 * @return	{@link Integer}				返回修正后的偏移量。
	 */
	private final static int repairOffset(CellElement blockElment, int offset){
		if(!(blockElment instanceof com.wisii.wisedoc.document.Block))
			return offset;
		com.wisii.wisedoc.document.Block block = (com.wisii.wisedoc.document.Block)blockElment;
		
		if(!block.isStaticParent())
			return offset;
		List<Block> blocks = block.getAllBlocks();
		Block temp = null;
		for (Block b : blocks) {
			if(b.getStartIndex() + b.getElementCount() > offset){
				temp = b;
				break;
			}
		}
		if(temp != null){
			offset -= temp.getStartIndex();
		}
		return offset;
	}
	
	/**
	 *获得指定Text-Area中，指定偏移量位置的现实区域。
	 * 坐标系：相对最近的Reference-Area
	 * 如：text-area：[ABC]，offset:1
	 * 则：返回B字符在界面中显示的位置。
	 * 
	 * @param area					指定Area【Inline-Area：如：text-area】
	 * @param offset				指定字符偏移位置[0,N]。
	 * @return {@link Rectangle2D} 	返回指定Area，指定偏移位置的字符占据的矩形区域
	 */
	private final static Rectangle2D getRectFromTextArea(TextArea text, int offset) {
		Rectangle2D r = null;
		final Rectangle2D rect = text.getViewport();
		if (isNull(rect)) {
			return r;
		}
		
		if(offset >= text.getElementCount()){
			r = new Rectangle2D.Double(rect.getX() + rect.getWidth(), rect.getY(), rect.getWidth(), rect.getHeight());
			
			return r;
		}else if(offset < 0){
			r = (Rectangle2D)rect.clone();
			return r; 
		}
		
		final Font font = text.getFontFromArea();
		final List<Area> areas = text.getChildAreas();

		double width = 0;
		for (final Area a : areas) {
			if (a instanceof WordArea) {
				final WordArea word = (WordArea) a;
				final String s = word.getWord();
				final int[] letterAdjust = word.getLetterAdjustArray();
				final int[] wordWidth = getGlyphOffsets(s, font, text,
						letterAdjust);
				if (offset >= wordWidth.length) {
					width += getWidht(wordWidth, wordWidth.length);
				} else {
					width += getWidht(wordWidth, offset);
					r = new Rectangle2D.Double();
					r.setRect(rect.getX() + width, rect.getY(),
							wordWidth[offset], rect.getHeight());
				}
				offset = offset - wordWidth.length;
			} else if (a instanceof SpaceArea) {
				final SpaceArea space = (SpaceArea) a;
				final String s = space.getSpace();
				final char sp = s.charAt(0);
				int tws = (space.isAdjustable() ? text.getTextWordSpaceAdjust()
						+ 2 * text.getTextLetterSpaceAdjust() : 0);
				tws += font.getCharWidth(sp);
				if (offset > 0) {
					width += tws;
				} else {
					r = new Rectangle2D.Double();
					r.setRect(rect.getX() + width, rect.getY(), tws, rect
							.getHeight());
				}
				offset -= s.length();
			}
			if (offset < 0) {
				break;
			}
		}
		return r;
	}
	/**
	 * 获得指定数组中，前length位的数值总和。
	 * 如：int[0, 1, 2, 3] length = 3 则：int[0] + int[1] + int[2] = 3;
	 * @param wordWidth			指定数值集合。
	 * @param length			指定要累加的位数。
	 * @return	{@link Double}	返回累加的和。
	 */
	private final static double getWidht(final int[] wordWidth, final int length) {
		double width = 0;
		int index = 1;
		for (final int i : wordWidth) {
			if (index > length) {
				break;
			}
			index++;
			width += i;
		}
		return width;
	}
	/**
	 * 从指定的Block-Area中计算获得指定偏移量的位置的Text-Area。
	 * @param area				指定Block-Area。
	 * @param bean				指定偏移量信息。
	 * @return	{@link Area}	返回获得的Area。
	 */
	private static Area getAreaBeanWithOffset(final Area area, final Bean<Area> bean){
		if(area == null)
			return area;
		
		final List<Area> areas = area.getChildAreas();
		LineArea line = null;
		for (Area a : areas) {
			if(!(a instanceof LineArea))
				continue;
			LineArea temp = (LineArea)a;
			if(temp.getStartIndex() + temp.getElementCount() > bean.offset){					
				line = temp;
				break;
			}
		}
		if(line == null)
			return area;
		
		//判断offset所指的位置，是否包含在指定的line-area中。
		Area temp = getAreaWithOffsetFromLine(line, bean);

		if(temp != null)
			return temp;
		
		return getAreaWithOffset(line, bean);
	}
	
	/**
	 * 获得line-area中指定偏移量offset，对应的inline-area。
	 * 这里处理的内容：
	 * 1.如果offset包含在line-area中。
	 * 2.offset位置恰属于忽略的部分。如：空格。
	 * 3.该情况主要是行中首尾忽略部分的定位处理。
	 * @param line				指定line-area。
	 * @param bean				指定包含了偏移量信息的封装对象。
	 * @return	{@link Area}	返回获得inline-area：text-area。如果offset对应的是非忽略部分，返回null。
	 */
	private final static Area getAreaWithOffsetFromLine(final LineArea line, final Bean<Area> bean){
		if(line.getStartIndex() > bean.offset){
			return getInlineAreaOf(line, Boolean.TRUE);
		}else if(/*line.getStartIndex() + line.getElementCount() - 1*/ line.getEndIndex() <= bean.offset){
			return getInlineAreaOf(line, Boolean.FALSE);
		}
		return null;
	}
	
	/**
	 * 获得指定line-area中的第一个、最后一个text-area\viewport。
	 * 1.是获得第一个还是最后一个area，由isBegin决定。true：第一个、否则：最后一个。
	 * 2.
	 * @param line						指定line-area。
	 * @param isBegin					指定是获得第一个\最后一个
	 * @return	{@link Area}			返回找打的inline-area[text-area\viewport]
	 */
	@SuppressWarnings("unchecked")
	final static Area getInlineAreaOf(final Area line, final boolean isBegin){
		if(line == null)
			return null;
		if(line instanceof TextArea){
			return line;
		}else if(line instanceof Viewport){
			return line;
		}
		
		List<Area> areas = line.getChildAreas();		
		if(areas == null || areas.isEmpty())
			return null;
		
		if(isBegin){
			for (Area a : areas) {
				Area temp = getInlineAreaOf(a, isBegin);
				if(temp != null){
					return temp;
				}	
			}
		}else{
			for (int i = areas.size() - 1; i >= 0; i--) {
				Area temp = getInlineAreaOf(areas.get(i), isBegin);
				if(temp != null){
					return temp;
				}	
			}
		}
		return null;
	}
	/**
	 * 根据制定的offset，获得指定的line-area中对应位置的的area
	 * @param line				指定line-area
	 * @param bean				指定封装了offset信息的bean。
	 * @return	{@link Area}	返回找到的area，如果没有找到则返回null。
	 */
	private final static Area getAreaWithOffset(Area line, final Bean<Area> bean){
		if(line == null)
			return line;
		if(line instanceof TextArea){
			TextArea text = (TextArea)line;
			if(text.getStartIndex() + text.getElementCount() > bean.offset){
				if(!text.isWhole()){
					bean.area = text;
				}else {
					CellElement fo = text.getSource();
					if(!(fo instanceof FOText)){
						bean.area = fo.getArea();
					}else{
						FOText t = (FOText)fo;
						List<TextArea> areas = t.getAreas();
						if(bean.begin){
							bean.area = areas.get(0);
						}else{
							bean.area = areas.get(areas.size() - 1);
						}
					}
				}
				bean.found = Boolean.TRUE;
			}
			return text;
		}else if(line instanceof Viewport){
			Viewport view = (Viewport)line;
			if(view.getStartIndex() + view.getElementCount() > bean.offset){
				bean.area = view;
				bean.found = Boolean.TRUE;
				
			}
			return view;
		}
		final List<Area> areas = line.getChildAreas();
		if(areas == null || areas.isEmpty())
			return null;
		for (Area a : areas) {
			getAreaWithOffset(a, bean);
			if(bean.found){
				return bean.area;
			}
		}
		return null;
	}
	//-----------------------------------计算：从Offset to Point-----------------------------------------------
	//-----------------------------------计算：从Point to Offset-----------------------------------------------
	/**
	 * 获得指定Area包含指定点的字符偏移量，及点在字符Rectangle的位置， 如果在Rectangle的前部分返回True，否则返回False。
	 * 
	 * @param area					指定Arrea
	 * @param p						指定点
	 * @return {@link OffsetBean} 	返回包含了偏移量、及在Rectangle的位置信息。
	 */
	private final static OffsetBean calcOffsetOfInlineArea(final Area area, final Point2D p) {
		OffsetBean bean = null;

		if (area instanceof Viewport) {
			return calcOffsetOfViewport(area, p);
		}
		if (!(area instanceof TextArea)) {
			return bean;
		}

		final TextArea text = (TextArea) area;

		final Rectangle2D rect = text.getViewport();
		if (!rect.contains(p)) {
			bean = calcNearOffsetForLetter(text, p);
		}
		if (bean != null) {
			return bean;
		}

		p.setLocation(p.getX() - rect.getX(), p.getY());// 更新坐标系
		if (text.isWhole()) {
			return calceOffsetForBindArea(text, p);
		}

		final Font font = text.getFontFromArea();
		final List<Area> areas = text.getChildAreas();
		int index = 0;
		int offset = -1;
		for (final Area a : areas) {
			if (a instanceof WordArea) {
				final WordArea word = (WordArea) a;
				final String s = word.getWord();
				final int[] letterAdjust = word.getLetterAdjustArray();
				final int[] wordWidth = getGlyphOffsets(s, font, text, letterAdjust);
				bean = calcOffsetOfWord(text, p, wordWidth);
				offset = bean.getOffset();
				if (offset == -1) {
					index += s.length();
				} else {
					index += offset;
				}
			} else if (a instanceof SpaceArea) {
				final SpaceArea space = (SpaceArea) a;
				final String s = space.getSpace();
				final char sp = s.charAt(0);
				int tws = (space.isAdjustable() ? text.getTextWordSpaceAdjust()
						+ 2 * text.getTextLetterSpaceAdjust() : 0);
				tws += font.getCharWidth(sp);
				if (0 <= p.getX() && p.getX() <= tws) {
					if (bean == null) {
						bean = new OffsetBean();
					}
					bean.setStart((p.getX() <= tws / 2));
					offset = index;
				}
				p.setLocation(p.getX() - tws, p.getY());
				index++;
			}
			if (offset != -1) {
				//? -1这里的计算是按字符长度进行的，而我们需要的是偏移量 ，索引是从0开始的，故需要-1.
				bean.setOffset(index - 1);
				return bean;
			}
		}
		return bean;
	}
	
	private final static OffsetBean calceOffsetForBindArea(final TextArea area, final Point2D p) {
		OffsetBean bean = null;
		if (!area.isWhole()) {
			return bean;
		}
		bean = new OffsetBean();
		final Rectangle2D r = area.getViewport();
		bean.setOffset(0);
		final CellElement element = area.getSource();
		if (element instanceof FOText) {
			final FOText text = (FOText) element;
			final List<TextArea> areas = text.getAreas();
			if (areas.size() > 1) {
				bean.setStart(area == text.getArea());

				return bean;
			}
		}
		bean.setStart((p.getX() <= r.getWidth() / 2));
		return bean;
	}
	
	/** 处理点处在Text Area矩形区域的最前、最后时，即光标应该定位行首、行尾。 */
	private final static OffsetBean calcNearOffsetForLetter(final TextArea text, final Point2D p) {
		OffsetBean bean = new OffsetBean();
		final Rectangle2D rect = text.getViewport();
		final int outer = rect.outcode(p);
		if ((outer & Rectangle2D.OUT_LEFT) != 0) {
			bean.setOffset(text.getStartIndex());
			bean.setStart(Boolean.TRUE);
		} else if ((outer & Rectangle2D.OUT_RIGHT) != 0) {
			bean.setOffset(text.getElementCount() - 1);
			bean.setStart(Boolean.FALSE);
		} else {
			bean = null;
		}
		return bean;
	}
	/** 计算包含指定的Area是Viewport时，偏移量的处理。 */
	private final static OffsetBean calcOffsetOfViewport(final Area area, final Point2D p) {
		final OffsetBean bean = new OffsetBean();
		if (!(area instanceof Viewport)) {
			return bean;
		}
		final Rectangle2D rect = area.getViewport();
		p.setLocation(p.getX() - rect.getX(), p.getY());
		if (0 <= p.getX() && p.getX() <= rect.getWidth()) {
			bean.setStart((p.getX() <= rect.getWidth() / 2));
		}
		bean.setOffset(0);
		return bean;
	}

	/** 计算指定Word Area包含字符的放缩后占据的宽度。 */
	private final static int[] getGlyphOffsets(final String s, final Font font, final TextArea text,
			final int[] letterAdjust) {
		final int textLen = s.length();
		final int[] offsets = new int[textLen];
		for (int i = 0; i < textLen; i++) {
			final char c = s.charAt(i);
			final char mapped = font.mapChar(c);
			int wordSpace;

			if (CharUtilities.isAdjustableSpace(mapped)) {
				wordSpace = text.getTextWordSpaceAdjust();
			} else {
				wordSpace = 0;
			}
			final int cw = font.getWidth(mapped);
			final int ladj = (letterAdjust != null && i < textLen - 1 ? letterAdjust[i + 1]
					: 0);
			final int tls = (i < textLen - 1 ? text.getTextLetterSpaceAdjust() : 0);
			offsets[i] = cw + ladj + tls + wordSpace;
		}
		return offsets;
	}
	/** 计算获得包含指定点的字符偏移量信息【偏移量，点在字符的位置】 */
	private final static OffsetBean calcOffsetOfWord(final TextArea area, final Point2D p,
			final int[] wordWidth) {
		Rectangle2D rect = area.getViewport();
		rect = (Rectangle2D) rect.clone();
		final OffsetBean bean = new OffsetBean();

		int index = 0;
		for (final int width : wordWidth) {
			index++;
			if (0 <= p.getX() && p.getX() <= width) {
				bean.setOffset(index);
				bean.setStart(p.getX() <= width / 2);
				return bean;
			}
			p.setLocation(p.getX() - width, p.getY());
		}
		return bean;
	}
	//-----------------------------------计算：从Point to Offset-----------------------------------------------
	/**
	 * 用于包装数据单元，主要用在遍历Area-Tree时使用，
	 * 返回Area的同时，明确是否找打对象。方便退出遍历【迭代】
	 */
	final static class Bean<T extends Area>{
		private T area = null;
		private boolean found = Boolean.FALSE;
		//标示Position是标示start还是end。
		private boolean begin = Boolean.FALSE;
		private int offset = -1;
		T getValue(){
			return this.area;
		}
		boolean isFound(){
			return this.found;
		}
		int getOffset(){
			return this.offset;
		}
		boolean isBegin(){
			return begin;
		}
		@Override
		public String toString() {
			StringBuilder s = new StringBuilder();
			s.append("offset = " + offset);
			s.append(",found = " + found);
			s.append(",area = " + area);
			s.append(",begin = " + begin);
			return s.toString();
		}
		final static <T extends Area> Bean<T> getBean(){
			return new Bean<T>();
		}
	}
	/** 数据结构单元，用于计算光标位置，主要包含位置在当前段落中的偏移量，是在开始、结束位置信息  */
	final static class OffsetBean {
		public int getOffset() {
			return offset;
		}

		public void setOffset(final int offset) {
			this.offset = offset;
		}

		public boolean isStart() {
			return start;
		}

		public void setStart(final boolean start) {
			this.start = start;
		}

		@Override
		public String toString() {
			return MessageFormat.format(pattern, getOffset(), isStart());
		}

		private final static String pattern = "[Offset:{0}]:[Is Start:{1}]";
		private int offset = -1;
		private boolean start = Boolean.FALSE;
	}
}
