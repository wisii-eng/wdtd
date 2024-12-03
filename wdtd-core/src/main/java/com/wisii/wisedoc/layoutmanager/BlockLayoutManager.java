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
/* $Id: BlockLayoutManager.java,v 1.1 2007/04/12 06:41:19 cvsuser Exp $ */

package com.wisii.wisedoc.layoutmanager;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.area.Block;
import com.wisii.wisedoc.area.LineArea;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.StaticContent;
import com.wisii.wisedoc.document.TableFooter;
import com.wisii.wisedoc.document.TableHeader;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.fonts.Font;
import com.wisii.wisedoc.layoutmanager.inline.InlineLayoutManager;
import com.wisii.wisedoc.layoutmanager.inline.InlineLevelLayoutManager;
import com.wisii.wisedoc.layoutmanager.inline.LineLayoutManager;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.traits.MinOptMax;
import com.wisii.wisedoc.traits.SpaceVal;
import com.wisii.wisedoc.view.LocationConvert;

/**
 * LayoutManager for a block FO.
 */
public class BlockLayoutManager extends BlockStackingLayoutManager implements
		ConditionalElementListener
{

	private Block curBlockArea;

	/** Iterator over the child layout managers. */
	protected ListIterator proxyLMiter;

	private int lead = 12000;
	private Length lineHeight;
	private int follow = 2000;
	private int middleShift = 0;

	private boolean discardBorderBefore;
	private boolean discardBorderAfter;
	private boolean discardPaddingBefore;
	private boolean discardPaddingAfter;
	private MinOptMax effSpaceBefore;
	private MinOptMax effSpaceAfter;

	/** The list of child BreakPoss instances. */
	protected List childBreaks = new java.util.ArrayList();
	// add by zq 表示该block是否已改变的变量
	private boolean isblockchanged = true;
//	是否已经做过需要重排的判断
	private boolean isdisided;
//	上一次排版得到的可断行对象
	private ListIterator<LinkedList> elementlist;

	// zq added end

    //【添加：START】 by 李晓光 2010-1-20
	private transient int currentOffset = 0;
	public void addOffset(int offset){
//		if(offset < 0)
		this.currentOffset += offset;
	}
	public int getCurrentOffset(){
		return this.currentOffset;
	}
	public void setCurrentOffset(int offset){
		this.currentOffset = offset;
	}
	public void resetCurrentOffset(){
		this.currentOffset = 0;
	}
    //【添加：END】 by 李晓光 2010-1-20
	/**
	 * Creates a new BlockLayoutManager.
	 * 
	 * @param inBlock
	 *            the block FO object to create the layout manager for.
	 */
	public BlockLayoutManager(com.wisii.wisedoc.document.Block inBlock)
	{
		super(inBlock);
		proxyLMiter = new ProxyLMiter();
		isdisided = false;
		// add by zq
		isblockchanged = isBlockChanged();
		if (!isblockchanged)
		{
			initElementlist();
		}
	}

	private void initElementlist()
	{
		elementlist = ((com.wisii.wisedoc.document.Block) fobj).getLinkedLists(
				this).listIterator();
	}

	/*
	 * add by zq 判断当前block是否在本次文档操作中发生了改变。如果发生了改变才重拍，否则，不重排
	 */
	private boolean isBlockChanged()
	{
		if (fobj instanceof com.wisii.wisedoc.document.Block)
		{
			/*Document doc = SystemManager.getCurruentDocument();*/
			Document doc = LocationConvert.getCurrentDocument(fobj);
			if (doc != null)
			{
				return doc.isCellChanged((CellElement) fobj);
			}
			// Cell doc =
		}
		return true;
	}

	public void initialize()
	{
		//【添加：START】 by 李晓光 2010-1-20
		/*if(!isStaticContentParent()){
			resetCurrentOffset();
		}*/
		//【添加：END】 by 李晓光 2010-1-20
		super.initialize();
		
		/* 【替换】 by 李晓光 2008-09-02 */
		Font fs = getBlockFO().getCommonFont().getFontState(fontInfo, this);
		/* getBlockFO().getFOEventHandler().getFontInfo() */

		lead = fs.getAscender();
		follow = -fs.getDescender();
		middleShift = -fs.getXHeight() / 2;
		lineHeight = getBlockFO().getLineHeight().getOptimum(this).getLength();
		cbpbackground = getBlockFO().getCommonBorderPaddingBackground();
		cmblock = getBlockFO().getCommonMarginBlock();
		startIndent = cmblock.getStartIndent()
				.getValue(this);
		endIndent = cmblock.getEndIndent()
				.getValue(this);
		foSpaceBefore = new SpaceVal(
				cmblock.getSpaceBefore(), this)
				.getSpace();
		foSpaceAfter = new SpaceVal(
				cmblock.getSpaceAfter(), this)
				.getSpace();
		bpUnit = 0; // non-standard extension
		if (bpUnit == 0)
		{
			// use optimum space values
			adjustedSpaceBefore = cmblock.getSpaceBefore()
					.getSpace().getOptimum(this).getLength().getValue(this);
			adjustedSpaceAfter = cmblock.getSpaceAfter()
					.getSpace().getOptimum(this).getLength().getValue(this);
		} else
		{
			// use minimum space values
			adjustedSpaceBefore = cmblock.getSpaceBefore()
					.getSpace().getMinimum(this).getLength().getValue(this);
			adjustedSpaceAfter = cmblock.getSpaceAfter()
					.getSpace().getMinimum(this).getLength().getValue(this);
		}
		/* 【添加：START】by 李晓光 2008-12-12 清空FO中包含的Block Area */
		boolean flag = isStaticContentParent(); 
		if(!flag)
			getBlockFO().reset();
		getBlockFO().setStaticParent(flag);
		/* 【添加：END】by 李晓光 2008-12-12  */
	}
	@SuppressWarnings("unchecked")
	private boolean isStaticContentParent(){
		Element content = (Element)LocationConvert.searchCellElement(getBlockFO(), StaticContent.class, TableHeader.class, TableFooter.class);
		
		return (content != null);
	}
	/** @see com.wisii.wisedoc.layoutmanager.BlockStackingLayoutManager */
	public LinkedList getNextKnuthElements(LayoutContext context, int alignment)
	{
		resetSpaces();
		return getnextKnuthElements(context, alignment);
	}

	private void resetSpaces()
	{
		this.discardBorderBefore = false;
		this.discardBorderAfter = false;
		this.discardPaddingBefore = false;
		this.discardPaddingAfter = false;
		this.effSpaceBefore = null;
		this.effSpaceAfter = null;
	}

	/**
	 * Proxy iterator for Block LM. This iterator creates and holds the complete
	 * list of child LMs. It uses fobjIter as its base iterator. Block LM's
	 * createNextChildLMs uses this iterator as its base iterator.
	 */
	protected class ProxyLMiter extends LMiter
	{

		/*
		 * Constructs a proxy iterator for Block LM.
		 */
		public ProxyLMiter()
		{
			super(BlockLayoutManager.this);
			listLMs = new java.util.ArrayList(10);
		}

		/**
		 * @return true if there are more child lms
		 */
		public boolean hasNext()
		{
			return (curPos < listLMs.size()) ? true
					: createNextChildLMs(curPos);
		}

		/**
		 * @return true if new child lms were added
		 */
		protected boolean createNextChildLMs(int pos)
		{
			List newLMs = createChildLMs(pos + 1 - listLMs.size());
			if (newLMs != null)
			{
				listLMs.addAll(newLMs);
			}
			return pos < listLMs.size();
		}
	}

	/**
	 * @see com.wisii.wisedoc.layoutmanager.LayoutManager#createNextChildLMs
	 */
	public boolean createNextChildLMs(int pos)
	{

		while (proxyLMiter.hasNext())
		{
			LayoutManager lm = (LayoutManager) proxyLMiter.next();
			if (lm instanceof InlineLevelLayoutManager)
			{
				LineLayoutManager lineLM = createLineManager(lm);
				addChildLM(lineLM);
			} else
			{
				addChildLM(lm);
			}
			if (pos < childLMs.size())
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Create a new LineLM, and collect all consecutive inline generating LMs as
	 * its child LMs.
	 * 
	 * @param firstlm
	 *            First LM in new LineLM
	 * @return the newly created LineLM
	 */
	private LineLayoutManager createLineManager(LayoutManager firstlm)
	{
		LineLayoutManager llm;
		llm = new LineLayoutManager(getBlockFO(), lineHeight, lead, follow);
		List inlines = new java.util.ArrayList();
		inlines.add(firstlm);
		while (proxyLMiter.hasNext())
		{
			LayoutManager lm = (LayoutManager) proxyLMiter.next();
			if (lm instanceof InlineLevelLayoutManager)
			{
				inlines.add(lm);
			} else
			{
				proxyLMiter.previous();
				break;
			}
		}
		llm.addChildLMs(inlines);
		return llm;
	}

	/**
	 * @see com.wisii.wisedoc.layoutmanager.BlockLevelLayoutManager#mustKeepTogether()
	 */
	public boolean mustKeepTogether()
	{
		// TODO Keeps will have to be more sophisticated sooner or later
		// TODO This is a quick fix for the fact that the parent is not always a
		// BlockLevelLM;
		// eventually mustKeepTogether() must be moved up to the LM interface
		return (!getBlockFO().getKeepTogether().getWithinPage().isAuto()
				|| !getBlockFO().getKeepTogether().getWithinColumn().isAuto()
				|| (getParent() instanceof BlockLevelLayoutManager && ((BlockLevelLayoutManager) getParent())
						.mustKeepTogether()) || (getParent() instanceof InlineLayoutManager && ((InlineLayoutManager) getParent())
				.mustKeepTogether()));
	}

	/**
	 * @see com.wisii.wisedoc.layoutmanager.BlockLevelLayoutManager#mustKeepWithPrevious()
	 */
	public boolean mustKeepWithPrevious()
	{
		return !getBlockFO().getKeepWithPrevious().getWithinPage().isAuto()
				|| !getBlockFO().getKeepWithPrevious().getWithinColumn()
						.isAuto();
	}

	/**
	 * @see com.wisii.wisedoc.layoutmanager.BlockLevelLayoutManager#mustKeepWithNext()
	 */
	public boolean mustKeepWithNext()
	{
		return !getBlockFO().getKeepWithNext().getWithinPage().isAuto()
				|| !getBlockFO().getKeepWithNext().getWithinColumn().isAuto();
	}

	/**
	 * @see com.wisii.wisedoc.layoutmanager.LayoutManager#addAreas(com.wisii.wisedoc.layoutmanager.PositionIterator,
	 *      com.wisii.wisedoc.layoutmanager.LayoutContext)
	 */
	public void addAreas(PositionIterator parentIter,
			LayoutContext layoutContext)
	{	
		getParentArea(null);
		//【添加：START】 by 李晓光 2010-1-26
		if(getBlockFO().isStaticParent()){
			List<Block> blocks = getBlockFO().getAllBlocks();
			if(blocks.size() > 1){
				Block block = blocks.get(blocks.size() - 2);
				setCurrentOffset(block.getStartIndex() + block.getElementCount());
			}
		}
		curBlockArea.setStartIndex(getCurrentOffset());
		//【添加：END】 by 李晓光 2010-1-26
		// if this will create the first block area in a page
		// and display-align is after or center, add space before
		if (layoutContext.getSpaceBefore() > 0)
		{
			addBlockSpacing(0.0, new MinOptMax(layoutContext.getSpaceBefore()));
		}

		LayoutManager childLM = null;
		LayoutManager lastLM = null;
		LayoutContext lc = new LayoutContext(0);
		lc.setSpaceAdjust(layoutContext.getSpaceAdjust());
		// set space after in the LayoutContext for children
		if (layoutContext.getSpaceAfter() > 0)
		{
			lc.setSpaceAfter(layoutContext.getSpaceAfter());
		}
		PositionIterator childPosIter;

		// "unwrap" the NonLeafPositions stored in parentIter
		// and put them in a new list;
		LinkedList positionList = new LinkedList();
		Position pos;
		boolean bSpaceBefore = false;
		boolean bSpaceAfter = false;
		Position firstPos = null;
		Position lastPos = null;
		while (parentIter.hasNext())
		{
			pos = (Position) parentIter.next();
			// log.trace("pos = " + pos.getClass().getName() + "; " + pos);
			if (pos.getIndex() >= 0)
			{
				if (firstPos == null)
				{
					firstPos = pos;
				}
				lastPos = pos;
			}
			Position innerPosition = pos;
			if (pos instanceof NonLeafPosition)
			{
				// Not all elements are wrapped
				innerPosition = ((NonLeafPosition) pos).getPosition();
			}
			if (innerPosition == null)
			{
				// pos was created by this BlockLM and was inside an element
				// representing space before or after
				// this means the space was not discarded
				if (positionList.size() == 0)
				{
					// pos was in the element representing space-before
					bSpaceBefore = true;
					// log.trace(" space before");
				} else
				{
					// pos was in the element representing space-after
					bSpaceAfter = true;
					// log.trace(" space-after");
				}
			} else if (innerPosition.getLM() == this
					&& !(innerPosition instanceof MappingPosition))
			{
				// pos was created by this BlockLM and was inside a penalty
				// allowing or forbidding a page break
				// nothing to do
				// log.trace(" penalty");
			} else
			{
				// innerPosition was created by another LM
				positionList.add(innerPosition);
				lastLM = innerPosition.getLM();
				// log.trace(" " + innerPosition.getClass().getName());
			}
		}

		getPSLM().addIDToPage(getBlockFO().getId());
		if (markers != null)
		{
			getCurrentPV().addMarkers(markers, true, isFirst(firstPos),
					isLast(lastPos));
		}

		if (bpUnit == 0)
		{
			// the Positions in positionList were inside the elements
			// created by the LineLM
			childPosIter = new StackingIter(positionList.listIterator());
		} else
		{
			// the Positions in positionList were inside the elements
			// created by the BlockLM in the createUnitElements() method
			// if (((Position) positionList.getLast()) instanceof
			// LeafPosition) {
			// // the last item inside positionList is a LeafPosition
			// // (a LineBreakPosition, more precisely); this means that
			// // the whole paragraph is on the same page
			// childPosIter = new KnuthPossPosIter(storedList, 0,
			// storedList.size());
			// } else {
			// // the last item inside positionList is a Position;
			// // this means that the paragraph has been split
			// // between consecutive pages
			LinkedList splitList = new LinkedList();
			int splitLength = 0;
			int iFirst = ((MappingPosition) positionList.getFirst())
					.getFirstIndex();
			int iLast = ((MappingPosition) positionList.getLast())
					.getLastIndex();
			// copy from storedList to splitList all the elements from
			// iFirst to iLast
			ListIterator storedListIterator = storedList.listIterator(iFirst);
			while (storedListIterator.nextIndex() <= iLast)
			{
				KnuthElement element = (KnuthElement) storedListIterator.next();
				// some elements in storedList (i.e. penalty items) were created
				// by this BlockLM, and must be ignored
				if (element.getLayoutManager() != this)
				{
					splitList.add(element);
					splitLength += element.getW();
					lastLM = element.getLayoutManager();
				}
			}
			// log.debug("Adding areas from " + iFirst + " to " + iLast);
			// log.debug("splitLength= " + splitLength
			// + " (" + neededUnits(splitLength) + " units') "
			// + (neededUnits(splitLength) * bpUnit - splitLength)
			// + " spacing");
			// add space before and / or after the paragraph
			// to reach a multiple of bpUnit
			if (bSpaceBefore && bSpaceAfter)
			{
				foSpaceBefore = new SpaceVal(cmblock.getSpaceBefore(), this).getSpace();
				foSpaceAfter = new SpaceVal(
						cmblock.getSpaceAfter(), this)
						.getSpace();
				adjustedSpaceBefore = (neededUnits(splitLength
						+ foSpaceBefore.min + foSpaceAfter.min)
						* bpUnit - splitLength) / 2;
				adjustedSpaceAfter = neededUnits(splitLength
						+ foSpaceBefore.min + foSpaceAfter.min)
						* bpUnit - splitLength - adjustedSpaceBefore;
			} else if (bSpaceBefore)
			{
				adjustedSpaceBefore = neededUnits(splitLength
						+ foSpaceBefore.min)
						* bpUnit - splitLength;
			} else
			{
				adjustedSpaceAfter = neededUnits(splitLength + foSpaceAfter.min)
						* bpUnit - splitLength;
			}
			// log.debug("spazio prima = " + adjustedSpaceBefore
			// + " spazio dopo = " + adjustedSpaceAfter + " totale = " +
			// (adjustedSpaceBefore + adjustedSpaceAfter + splitLength));
			childPosIter = new KnuthPossPosIter(splitList, 0, splitList.size());
			// }
		}

		while ((childLM = childPosIter.getNextChildLM()) != null)
		{
			// set last area flag
			lc.setFlags(LayoutContext.LAST_AREA,
					(layoutContext.isLastArea() && childLM == lastLM));
			lc.setStackLimit(layoutContext.getStackLimit());
			// Add the line areas to Area
			childLM.addAreas(childPosIter, lc);
		}

		if (markers != null)
		{
			getCurrentPV().addMarkers(markers, false, isFirst(firstPos),
					isLast(lastPos));
		}

		TraitSetter.addSpaceBeforeAfter(curBlockArea, layoutContext
				.getSpaceAdjust(), effSpaceBefore, effSpaceAfter);
		
		//【添加：START】 by 李晓光 2010-1-26
		curBlockArea.setElementCount(getCurrentOffset() - curBlockArea.getStartIndex());
		
		//【添加：END】 by 李晓光 2010-1-26
		
		flush();

		curBlockArea = null;
		resetSpaces();

		// Notify end of block layout manager to the PSLM
		getPSLM().notifyEndOfLayout(getBlockFO().getId());
	}

	/**
	 * Return an Area which can contain the passed childArea. The childArea may
	 * not yet have any content, but it has essential traits set. In general, if
	 * the LayoutManager already has an Area it simply returns it. Otherwise, it
	 * makes a new Area of the appropriate class. It gets a parent area for its
	 * area by calling its parent LM. Finally, based on the dimensions of the
	 * parent area, it initializes its own area. This includes setting the
	 * content IPD and the maximum BPD.
	 * 
	 * @param childArea
	 *            area to get the parent area for
	 * @return the parent area
	 */
	public Area getParentArea(Area childArea)
	{
		if (curBlockArea == null)
		{
			curBlockArea = new Block();

			curBlockArea.setIPD(super.getContentAreaIPD());

			TraitSetter.addBreaks(curBlockArea, getBlockFO().getBreakBefore(),
					getBlockFO().getBreakAfter());

			// Must get dimensions from parent area
			// Don't optimize this line away. It can have ugly side-effects.
			/* Area parentArea = */parentLM.getParentArea(curBlockArea);

			// set traits
			TraitSetter.setProducerID(curBlockArea, getBlockFO().getId());
			TraitSetter.addBorders(curBlockArea, cbpbackground, discardBorderBefore,
					discardBorderAfter, false, false, this);
			TraitSetter.addPadding(curBlockArea,cbpbackground, discardPaddingBefore,
					discardPaddingAfter, false, false, this);
			TraitSetter.addMargins(curBlockArea, cbpbackground, startIndent,
					endIndent, this);
			/* 【添加：START】 by 李晓光 2008-10-31 */
			curBlockArea.setSource(getBlockFO());
			/*getBlockFO().setArea(curBlockArea);*/
			getBlockFO().addArea(curBlockArea);
			/* 【添加：START】 by 李晓光 2008-10-31 */
			setCurrentArea(curBlockArea); // ??? for generic operations
		}
		return curBlockArea;
	}

	/**
	 * @see com.wisii.wisedoc.layoutmanager.LayoutManager#addChildArea(Area)
	 */
	public void addChildArea(Area childArea)
	{
		if (curBlockArea != null)
		{
			if (childArea instanceof LineArea)
			{
				curBlockArea.addLineArea((LineArea) childArea);
			} else
			{
				curBlockArea.addBlock((Block) childArea);
			}
		}
		/* 【添加：START】 by 李晓光 2008-10-6 */
		childArea.setParentArea(curBlockArea);
		/* 【添加：END】 by 李晓光 2008-10-6 */
	}

	/**
	 * Force current area to be added to parent area.
	 * 
	 * @see com.wisii.wisedoc.layoutmanager.BlockStackingLayoutManager#flush()
	 */
	protected void flush()
	{
		if (curBlockArea != null)
		{
			TraitSetter.addBackground(curBlockArea, cbpbackground, this);
			super.flush();
		}
	}

	/**
	 * @see com.wisii.wisedoc.layoutmanager.LayoutManager#resetPosition(com.wisii.wisedoc.layoutmanager.Position)
	 */
	public void resetPosition(Position resetPos)
	{
		if (resetPos == null)
		{
			reset(null);
			childBreaks.clear();
		} else
		{
			// reset(resetPos);
			LayoutManager lm = resetPos.getLM();
		}
	}

	/**
	 * convenience method that returns the Block node
	 * 
	 * @return the block node
	 */
	protected com.wisii.wisedoc.document.Block getBlockFO()
	{
		return (com.wisii.wisedoc.document.Block) fobj;
	}

	// --------- Property Resolution related functions --------- //

	/**
	 * Returns the IPD of the content area
	 * 
	 * @return the IPD of the content area
	 */
	public int getContentAreaIPD()
	{
		if (curBlockArea != null)
		{
			return curBlockArea.getIPD();
		}
		return super.getContentAreaIPD();
	}

	/**
	 * Returns the BPD of the content area
	 * 
	 * @return the BPD of the content area
	 */
	public int getContentAreaBPD()
	{
		if (curBlockArea != null)
		{
			return curBlockArea.getBPD();
		}
		return -1;
	}

	/**
	 * @see com.wisii.wisedoc.layoutmanager.LayoutManager#getGeneratesBlockArea
	 */
	public boolean getGeneratesBlockArea()
	{
		return true;
	}

	/** @see com.wisii.wisedoc.layoutmanager.ConditionalElementListener */
	public void notifySpace(RelSide side, MinOptMax effectiveLength)
	{
		if (RelSide.BEFORE == side)
		{
			LogUtil.debug(this + ": Space " + side + ", " + this.effSpaceBefore
					+ "-> " + effectiveLength);
			this.effSpaceBefore = effectiveLength;
		} else
		{
			LogUtil.debug(this + ": Space " + side + ", " + this.effSpaceAfter
					+ "-> " + effectiveLength);
			this.effSpaceAfter = effectiveLength;
		}
	}

	/** @see com.wisii.wisedoc.layoutmanager.ConditionalElementListener */
	public void notifyBorder(RelSide side, MinOptMax effectiveLength)
	{
		if (effectiveLength == null)
		{
			if (RelSide.BEFORE == side)
			{
				this.discardBorderBefore = true;
			} else
			{
				this.discardBorderAfter = true;
			}
		}
		LogUtil.debug(this + ": Border " + side + " -> " + effectiveLength);
	}

	/** @see com.wisii.wisedoc.layoutmanager.ConditionalElementListener */
	public void notifyPadding(RelSide side, MinOptMax effectiveLength)
	{
		if (effectiveLength == null)
		{
			if (RelSide.BEFORE == side)
			{
				this.discardPaddingBefore = true;
			} else
			{
				this.discardPaddingAfter = true;
			}
		}
		LogUtil.debug(this + ": Padding " + side + " -> " + effectiveLength);
	}

	/*
	 * add by zq 根据block有没有在本次操作中被改变而采取不同的操作
	 * 
	 * @param context
	 * 
	 * @param alignment
	 * 
	 * @return
	 */
	private LinkedList getnextKnuthElements(LayoutContext context, int alignment)
	{
		com.wisii.wisedoc.document.Block block = (com.wisii.wisedoc.document.Block) fobj;
		// log.debug("BLM.getNextKnuthElements> keep-together = "
		// + layoutProps.keepTogether.getType());
		// log.debug(" keep-with-previous = " +
		// layoutProps.keepWithPrevious.getType());
		// log.debug(" keep-with-next = " +
		// layoutProps.keepWithNext.getType());
		BlockLevelLayoutManager curLM; // currently active LM
		BlockLevelLayoutManager prevLM = null; // previously active LM

		referenceIPD = context.getRefIPD();

		updateContentAreaIPDwithOverconstrainedAdjust();

		LinkedList returnedList = null;
		LinkedList contentList = new LinkedList();
		LinkedList returnList = new LinkedList();
		if((!isblockchanged && context.equals(block.getLayoutcontext()))
				|| isdisided)
		{
			isdisided = true;
			while (elementlist.hasNext())
			{
				return elementlist.next();
			}
		} else
		{
			block.setLayoutManager(this);
			block.setLayoutcontext(context);
			if (!breakBeforeServed)
			{
				try
				{
					if (addKnuthElementsForBreakBefore(returnList, context))
					{
						block.addLinkedList(returnList);
						return returnList;
					}
				} finally
				{
					breakBeforeServed = true;
				}
			}

			if (!firstVisibleMarkServed)
			{
				addKnuthElementsForSpaceBefore(returnList, alignment);
			}

			addKnuthElementsForBorderPaddingBefore(returnList,
					!firstVisibleMarkServed);
			firstVisibleMarkServed = true;

			// Spaces, border and padding to be repeated at each break
			addPendingMarks(context);
			while ((curLM = (BlockLevelLayoutManager) getChildLM()) != null)
			{
				// block.addLayoutManager(curLM);
				LayoutContext childLC = new LayoutContext(0);
				childLC.copyPendingMarksFrom(context);
				if (curLM instanceof LineLayoutManager)
				{
					// curLM is a LineLayoutManager
					// set stackLimit for lines (stack limit is now
					// i-p-direction,
					// not b-p-direction!)
					childLC.setStackLimit(new MinOptMax(getContentAreaIPD()));
					childLC.setRefIPD(getContentAreaIPD());
				} else
				{
					// curLM is a ?
					// childLC.setStackLimit(MinOptMax.subtract(context
					// .getStackLimit(), stackSize));
					childLC.setStackLimit(context.getStackLimit());
					childLC.setRefIPD(referenceIPD);
				}

				// get elements from curLM
				returnedList = curLM.getNextKnuthElements(childLC, alignment);
				if (contentList.size() == 0
						&& childLC.isKeepWithPreviousPending())
				{
					context.setFlags(LayoutContext.KEEP_WITH_PREVIOUS_PENDING);
					childLC.setFlags(LayoutContext.KEEP_WITH_PREVIOUS_PENDING,
							false);
				}
				if (returnedList != null
						&& returnedList.size() == 1
						&& ((ListElement) returnedList.getFirst())
								.isForcedBreak())
				{
					// a descendant of this block has break-before
					/*
					 * if (returnList.size() == 0) { // the first child (or its
					 * first child ...) has // break-before; // all this block,
					 * including space before, will be put in // the //
					 * following page bSpaceBeforeServed = false; }
					 */
					contentList.addAll(returnedList);

					/*
					 * extension: conversione di tutta la sequenza fin'ora
					 * ottenuta
					 */
					if (bpUnit > 0)
					{
						storedList = contentList;
						contentList = createUnitElements(contentList);
					}
					/* end of extension */

					// "wrap" the Position inside each element
					// moving the elements from contentList to returnList
					returnedList = new LinkedList();
					wrapPositionElements(contentList, returnList);
					block.addLinkedList(returnList);
					return returnList;
				} else
				{
					if (prevLM != null)
					{
						// there is a block handled by prevLM
						// before the one handled by curLM
						if (mustKeepTogether()
								|| context.isKeepWithNextPending()
								|| childLC.isKeepWithPreviousPending())
						{
							// Clear keep pending flag
							context
									.setFlags(
											LayoutContext.KEEP_WITH_NEXT_PENDING,
											false);
							// add an infinite penalty to forbid a break between
							// blocks
							contentList.add(new BreakElement(
									new Position(this), KnuthElement.INFINITE,
									context));
						} else if (!((ListElement) contentList.getLast())
								.isGlue())
						{
							// add a null penalty to allow a break between
							// blocks
							contentList.add(new BreakElement(
									new Position(this), 0, context));
						} else
						{
							// the last element in contentList is a glue;
							// it is a feasible breakpoint, there is no need to
							// add
							// a penalty
							LogUtil
									.warn("glue-type break possibility not handled properly, yet");
							// TODO Does this happen? If yes, need to deal with
							// border and padding
							// at the break possibility
						}
					}
					if (returnedList == null || returnedList.size() == 0)
					{
						// Avoid NoSuchElementException below (happens with
						// empty
						// blocks)
						continue;
					}
					contentList.addAll(returnedList);
					if (((ListElement) returnedList.getLast()).isForcedBreak())
					{
						// a descendant of this block has break-after

						/*
						 * extension: conversione di tutta la sequenza fin'ora
						 * ottenuta
						 */
						if (bpUnit > 0)
						{
							storedList = contentList;
							contentList = createUnitElements(contentList);
						}
						/* end of extension */

						returnedList = new LinkedList();
						wrapPositionElements(contentList, returnList);
						block.addLinkedList(returnList);
						return returnList;
					}
				}
				// propagate and clear
				context.setFlags(LayoutContext.KEEP_WITH_NEXT_PENDING, childLC
						.isKeepWithNextPending());
				childLC.setFlags(LayoutContext.KEEP_WITH_NEXT_PENDING, false);
				childLC.setFlags(LayoutContext.KEEP_WITH_PREVIOUS_PENDING,
						false);
				prevLM = curLM;
			}
		}
		if (isdisided)
		{
			contentList = block.getContentlist();
		} else
		{
			block.setContentlist(contentList);
		}
		/* Extension: conversione di tutta la sequenza fin'ora ottenuta */
		if (bpUnit > 0)
		{
			storedList = contentList;
			contentList = createUnitElements(contentList);
		}
		/* end of extension */

		returnedList = new LinkedList();
		if (contentList.size() > 0)
		{
			wrapPositionElements(contentList, returnList);
		} else
		{
			// Empty fo:block, zero-length box makes sure the IDs are
			// registered.
			returnList
					.add(new KnuthBox(0, notifyPos(new Position(this)), true));
		}

		addKnuthElementsForBorderPaddingAfter(returnList, true);
		addKnuthElementsForSpaceAfter(returnList, alignment);
		addKnuthElementsForBreakAfter(returnList, context);

		if (mustKeepWithNext())
		{
			context.setFlags(LayoutContext.KEEP_WITH_NEXT_PENDING);
		}
		if (mustKeepWithPrevious())
		{
			context.setFlags(LayoutContext.KEEP_WITH_PREVIOUS_PENDING);
		}

		setFinished(true);

		return returnList;

	}
}
