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
/* $Id: InlineLayoutManager.java,v 1.1 2007/04/12 06:41:19 cvsuser Exp $ */

package com.wisii.wisedoc.layoutmanager.inline;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.area.inline.InlineArea;
import com.wisii.wisedoc.area.inline.InlineBlockParent;
import com.wisii.wisedoc.area.inline.InlineParent;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.InlineLevel;
import com.wisii.wisedoc.document.Title;
import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground;
import com.wisii.wisedoc.document.attribute.CommonMarginInline;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.fonts.Font;
import com.wisii.wisedoc.layoutmanager.BlockKnuthSequence;
import com.wisii.wisedoc.layoutmanager.BlockLevelLayoutManager;
import com.wisii.wisedoc.layoutmanager.BreakElement;
import com.wisii.wisedoc.layoutmanager.KnuthBox;
import com.wisii.wisedoc.layoutmanager.KnuthSequence;
import com.wisii.wisedoc.layoutmanager.LayoutContext;
import com.wisii.wisedoc.layoutmanager.LayoutManager;
import com.wisii.wisedoc.layoutmanager.NonLeafPosition;
import com.wisii.wisedoc.layoutmanager.Position;
import com.wisii.wisedoc.layoutmanager.PositionIterator;
import com.wisii.wisedoc.layoutmanager.SpaceSpecifier;
import com.wisii.wisedoc.layoutmanager.TraitSetter;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.traits.MinOptMax;
import com.wisii.wisedoc.traits.SpaceVal;
/**
 * LayoutManager for objects which stack children in the inline direction,
 * such as Inline or Line
 */
public class InlineLayoutManager extends InlineStackingLayoutManager {
    private final InlineLevel fobj;

    private CommonMarginInline inlineProps = null;
    private CommonBorderPaddingBackground borderProps = null;

    private boolean areaCreated = false;
    private LayoutManager lastChildLM = null; // Set when return last breakposs;

    private Position auxiliaryPosition;

    private Font font;

    /** The alignment adjust property */
    protected Length alignmentAdjust;
    /** The alignment baseline property */
    protected int alignmentBaseline = EN_BASELINE;
    /** The baseline shift property */
    protected Length baselineShift;
    /** The dominant baseline property */
    protected int dominantBaseline;
    /** The line height property */
    protected SpaceProperty lineHeight;

    private AlignmentContext alignmentContext = null;

    /**
     * Create an inline layout manager.
     * This is used for fo's that create areas that
     * contain inline areas.
     *
     * @param node the formatting object that creates the area
     */
    // The node should be FObjMixed
    public InlineLayoutManager(final InlineLevel node) {
        super(node);
        fobj = node;
    }

    private Inline getInlineFO() {
        return (Inline)fobj;
    }

    /** @see LayoutManager#initialize */
    @Override
	public void initialize() {
    	super.initialize();
        int padding = 0;
        /* 【替换】 by 李晓光 2008-09-02 */
        font = fobj.getCommonFont().getFontState(fontInfo, this);
        /*font = fobj.getCommonFont().getFontState(fobj.getFOEventHandler().getFontInfo(), this);*/
        lineHeight = fobj.getLineHeight();
        borderProps = fobj.getCommonBorderPaddingBackground();
        inlineProps = fobj.getCommonMarginInline();

        if (fobj instanceof Inline) {
            alignmentAdjust = ((Inline)fobj).getAlignmentAdjust();
            alignmentBaseline = ((Inline)fobj).getAlignmentBaseline();
            baselineShift = ((Inline)fobj).getBaselineShift();
            dominantBaseline = ((Inline)fobj).getDominantBaseline();
        } 
        /*else if (fobj instanceof Leader) {
            alignmentAdjust = ((Leader)fobj).getAlignmentAdjust();
            alignmentBaseline = ((Leader)fobj).getAlignmentBaseline();
            baselineShift = ((Leader)fobj).getBaselineShift();
            dominantBaseline = ((Leader)fobj).getDominantBaseline();
        }*/
        if (borderProps != null) {
            padding = borderProps.getPadding(CommonBorderPaddingBackground.BEFORE, false, this);
            padding += borderProps.getBorderWidth(CommonBorderPaddingBackground.BEFORE,
                                                 false);
            padding += borderProps.getPadding(CommonBorderPaddingBackground.AFTER, false, this);
            padding += borderProps.getBorderWidth(CommonBorderPaddingBackground.AFTER, false);
        }
        extraBPD = new MinOptMax(padding);

    }

    /** @see InlineStackingLayoutManager#getExtraIPD(boolean, boolean) */
    @Override
	protected MinOptMax getExtraIPD(final boolean isNotFirst, final boolean isNotLast) {
        int borderAndPadding = 0;
        if (borderProps != null) {
            borderAndPadding
                = borderProps.getPadding(CommonBorderPaddingBackground.START, isNotFirst, this);
            borderAndPadding
                += borderProps.getBorderWidth(CommonBorderPaddingBackground.START, isNotFirst);
            borderAndPadding
                += borderProps.getPadding(CommonBorderPaddingBackground.END, isNotLast, this);
            borderAndPadding
                += borderProps.getBorderWidth(CommonBorderPaddingBackground.END, isNotLast);
        }
        return new MinOptMax(borderAndPadding);
    }


    /** @see InlineStackingLayoutManager#hasLeadingFence(boolean) */
    @Override
	protected boolean hasLeadingFence(final boolean isNotFirst) {
        return borderProps != null
            && (borderProps.getPadding(CommonBorderPaddingBackground.START, isNotFirst, this) > 0
                || borderProps.getBorderWidth(CommonBorderPaddingBackground.START, isNotFirst) > 0
               );
    }

    /** @see InlineStackingLayoutManager#hasTrailingFence(boolean) */
    @Override
	protected boolean hasTrailingFence(final boolean isNotLast) {
        return borderProps != null
            && (borderProps.getPadding(CommonBorderPaddingBackground.END, isNotLast, this) > 0
                || borderProps.getBorderWidth(CommonBorderPaddingBackground.END, isNotLast) > 0
               );
    }

    /** @see InlineStackingLayoutManager#getSpaceStart */
    @Override
	protected SpaceProperty getSpaceStart() {
        return inlineProps != null ? inlineProps.getSpaceStart() : null;
    }
    /** @see InlineStackingLayoutManager#getSpaceEnd */
    @Override
	protected SpaceProperty getSpaceEnd() {
        return inlineProps != null ? inlineProps.getSpaceEnd() : null;
    }

    /** @see com.wisii.wisedoc.layoutmanager.inline.InlineLayoutManager#createArea(boolean) */
    protected InlineArea createArea(final boolean hasInlineParent) {
        InlineArea area;
        if (hasInlineParent) {
            area = new InlineParent();
            area.setOffset(0);
        } else {
            area = new InlineBlockParent();
        }
        if (fobj instanceof Inline) {
            TraitSetter.setProducerID(area, getInlineFO().getId());
        }
        /* add by 李晓光 2009-5-8 */
        area.setSource(fobj);
        /* add by 李晓光 2009-5-8 */
        return area;
    }

    /**
     * @see com.wisii.wisedoc.layoutmanager.inline.InlineStackingLayoutManager#setTraits(boolean, boolean)
     */
    @Override
	protected void setTraits(final boolean isNotFirst, final boolean isNotLast) {
        if (borderProps != null) {
            // Add border and padding to current area and set flags (FIRST, LAST ...)
            TraitSetter.setBorderPaddingTraits(getCurrentArea(),
                                               borderProps, isNotFirst, isNotLast, this);
            TraitSetter.addBackground(getCurrentArea(), borderProps, this);
        }
    }

    /**
     * @return true if this element must be kept together
     */
    // TODO Use the keep-together property on Inline as well
    public boolean mustKeepTogether() {
        return mustKeepTogether(this.getParent());
    }

    private boolean mustKeepTogether(final LayoutManager lm) {
        if (lm instanceof BlockLevelLayoutManager) {
            return ((BlockLevelLayoutManager) lm).mustKeepTogether();
        } else if (lm instanceof InlineLayoutManager) {
            return ((InlineLayoutManager) lm).mustKeepTogether();
        } else {
            return mustKeepTogether(lm.getParent());
        }
    }

    /** @see com.wisii.fov.layoutmgr.LayoutManager */
    @Override
	public LinkedList getNextKnuthElements(final LayoutContext context, final int alignment) {
        LayoutManager curLM;

        // the list returned by child LM
        LinkedList returnedList;

        // the list which will be returned to the parent LM
        final LinkedList returnList = new LinkedList();
        KnuthSequence lastSequence = null;

        SpaceSpecifier leadingSpace = context.getLeadingSpace();

        if (fobj instanceof Title) {
            alignmentContext = new AlignmentContext(font,
                                    lineHeight.getOptimum(this).getLength().getValue(this),
                                    context.getWritingMode());

        } else {
            alignmentContext = new AlignmentContext(font
                                    , lineHeight.getOptimum(this).getLength().getValue(this)
                                    , alignmentAdjust
                                    , alignmentBaseline
                                    , baselineShift
                                    , dominantBaseline
                                    , context.getAlignmentContext());
        }

        childLC = new LayoutContext(context);
        childLC.setAlignmentContext(alignmentContext);

        if (context.startsNewArea()) {
            // First call to this LM in new parent "area", but this may
            // not be the first area created by this inline
            if (getSpaceStart() != null) {
                context.getLeadingSpace().addSpace(new SpaceVal(getSpaceStart(), this));
            }

            // Check for "fence"
            if (hasLeadingFence(!context.isFirstArea())) {
                // Reset leading space sequence for child areas
                leadingSpace = new SpaceSpecifier(false);
            }
            // Reset state variables
            clearPrevIPD(); // Clear stored prev content dimensions
        }

        final StringBuffer trace = new StringBuffer("InlineLM:");

        // We'll add the border to the first inline sequence created.
        // This flag makes sure we do it only once.
        boolean borderAdded = false;

        if (borderProps != null) {
            childLC.setLineStartBorderAndPaddingWidth(context.getLineStartBorderAndPaddingWidth()
                + borderProps.getPaddingStart(true, this)
                + borderProps.getBorderStartWidth(true)
             );
            childLC.setLineEndBorderAndPaddingWidth(context.getLineEndBorderAndPaddingWidth()
                + borderProps.getPaddingEnd(true, this)
                + borderProps.getBorderEndWidth(true)
             );
        }

        while ((curLM = getChildLM()) != null) {
            if (!(curLM instanceof InlineLevelLayoutManager)) {
                // A block LM
                // Leave room for start/end border and padding
                if (borderProps != null) {
                    childLC.setRefIPD(childLC.getRefIPD()
                            - borderProps.getPaddingStart(lastChildLM != null, this)
                            - borderProps.getBorderStartWidth(lastChildLM != null)
                            - borderProps.getPaddingEnd(hasNextChildLM(), this)
                            - borderProps.getBorderEndWidth(hasNextChildLM()));
                }
            }
            // get KnuthElements from curLM
            returnedList = curLM.getNextKnuthElements(childLC, alignment);
            if (returnList.size() == 0 && childLC.isKeepWithPreviousPending()) {
                childLC.setFlags(LayoutContext.KEEP_WITH_PREVIOUS_PENDING, false);
            }
            if (returnedList == null) {
                // curLM returned null because it finished;
                // just iterate once more to see if there is another child
                continue;
            }
            if (returnedList.size() == 0) {
                continue;
            }
            if (curLM instanceof InlineLevelLayoutManager) {
                context.setFlags(LayoutContext.KEEP_WITH_NEXT_PENDING, false);
                // "wrap" the Position stored in each element of returnedList
                final ListIterator seqIter = returnedList.listIterator();
                while (seqIter.hasNext()) {
                    final KnuthSequence sequence = (KnuthSequence) seqIter.next();
                    sequence.wrapPositions(this);
                }
                if (lastSequence != null && lastSequence.appendSequenceOrClose
                        ((KnuthSequence) returnedList.get(0))) {
                    returnedList.remove(0);
                }
                // add border and padding to the first complete sequence of this LM
                if (!borderAdded && returnedList.size() != 0) {
                    addKnuthElementsForBorderPaddingStart((KnuthSequence) returnedList.get(0));
                    borderAdded = true;
                }
                returnList.addAll(returnedList);
            } else { // A block LM
                final BlockKnuthSequence sequence = new BlockKnuthSequence(returnedList);
                sequence.wrapPositions(this);
                boolean appended = false;
                if (lastSequence != null) {
                    if (lastSequence.canAppendSequence(sequence)) {
                        final BreakElement bk = new BreakElement(new Position(this), 0, context);
                        final boolean keepTogether = (mustKeepTogether()
                                                || context.isKeepWithNextPending()
                                                || childLC.isKeepWithPreviousPending());
                        appended = lastSequence.appendSequenceOrClose(sequence, keepTogether, bk);
                    } else {
                        lastSequence.endSequence();
                    }
                }
                if (!appended) {
                    // add border and padding to the first complete sequence of this LM
                    if (!borderAdded) {
                        addKnuthElementsForBorderPaddingStart(sequence);
                        borderAdded = true;
                    }
                    returnList.add(sequence);
                }
                // propagate and clear
                context.setFlags(LayoutContext.KEEP_WITH_NEXT_PENDING,
                                 childLC.isKeepWithNextPending());
                childLC.setFlags(LayoutContext.KEEP_WITH_NEXT_PENDING, false);
                childLC.setFlags(LayoutContext.KEEP_WITH_PREVIOUS_PENDING, false);
            }
            lastSequence = (KnuthSequence) returnList.getLast();
            lastChildLM = curLM;
        }

        if (lastSequence != null) {
            addKnuthElementsForBorderPaddingEnd(lastSequence);
        }

        setFinished(true);
        LogUtil.debug(trace);
        return returnList.size() == 0 ? null : returnList;
    }

    /**
     * Generate and add areas to parent area.
     * Set size of each area. This should only create and return one
     * inline area for any inline parent area.
     *
     * @param parentIter Iterator over Position information returned
     * by this LayoutManager.
     * @param context layout context.
     */
    @Override
	public void addAreas(final PositionIterator parentIter,
                         final LayoutContext context) {

        Position lastPos = null;

        addId();

        setChildContext(new LayoutContext(context)); // Store current value

        // If this LM has fence, make a new leading space specifier.
        if (hasLeadingFence(areaCreated)) {
            getContext().setLeadingSpace(new SpaceSpecifier(false));
            getContext().setFlags(LayoutContext.RESOLVE_LEADING_SPACE, true);
        } else {
            getContext().setFlags(LayoutContext.RESOLVE_LEADING_SPACE, false);
        }

        if (getSpaceStart() != null) {
            context.getLeadingSpace().addSpace(new SpaceVal(getSpaceStart(), this));
        }

        // "Unwrap" the NonLeafPositions stored in parentIter and put
        // them in a new list.  Set lastLM to be the LayoutManager
        // which created the last Position: if the LAST_AREA flag is
        // set in the layout context, it must be also set in the
        // layout context given to lastLM, but must be cleared in the
        // layout context given to the other LMs.
        final LinkedList positionList = new LinkedList();
        NonLeafPosition pos = null;
        LayoutManager lastLM = null; // last child LM in this iterator
        while (parentIter.hasNext()) {
            pos = (NonLeafPosition) parentIter.next();
            if (pos != null && pos.getPosition() != null) {
                positionList.add(pos.getPosition());
                lastLM = pos.getPosition().getLM();
                lastPos = pos;
            }
        }
        /*if (pos != null) {
            lastLM = pos.getPosition().getLM();
        }*/

        final InlineArea parent = createArea(lastLM == null
                                        || lastLM instanceof InlineLevelLayoutManager);
        parent.setBPD(alignmentContext.getHeight());
        if (parent instanceof InlineParent) {
            parent.setOffset(alignmentContext.getOffset());
        } else if (parent instanceof InlineBlockParent) {
            // All inline elements are positioned by the renderers relative to
            // the before edge of their content rectangle
            if (borderProps != null) {
                parent.setOffset(borderProps.getPaddingBefore(false, this)
                                + borderProps.getBorderBeforeWidth(false));
            }
        }
        setCurrentArea(parent);

        final StackingIter childPosIter
            = new StackingIter(positionList.listIterator());

        LayoutManager prevLM = null;
        LayoutManager childLM;
        while ((childLM = childPosIter.getNextChildLM()) != null) {
            getContext().setFlags(LayoutContext.LAST_AREA,
                                  context.isLastArea() && childLM == lastLM);
            childLM.addAreas(childPosIter, getContext());
            getContext().setLeadingSpace(getContext().getTrailingSpace());
            getContext().setFlags(LayoutContext.RESOLVE_LEADING_SPACE, true);
            prevLM = childLM;
        }

        /* If this LM has a trailing fence, resolve trailing space
         * specs from descendants.  Otherwise, propagate any trailing
         * space specs to the parent LM via the layout context.  If
         * the last child LM called returns LAST_AREA in the layout
         * context and it is the last child LM for this LM, then this
         * must be the last area for the current LM too.
         */
        final boolean isLast = (getContext().isLastArea() && prevLM == lastChildLM);
        if (hasTrailingFence(isLast)) {
            addSpace(getCurrentArea(),
                     getContext().getTrailingSpace().resolve(false),
                     getContext().getSpaceAdjust());
            context.setTrailingSpace(new SpaceSpecifier(false));
        } else {
            // Propagate trailing space-spec sequence to parent LM in context.
            context.setTrailingSpace(getContext().getTrailingSpace());
        }
        // Add own trailing space to parent context (or set on area?)
        if (context.getTrailingSpace() != null  && getSpaceEnd() != null) {
            context.getTrailingSpace().addSpace(new SpaceVal(getSpaceEnd(), this));
        }

        // Not sure if lastPos can legally be null or if that masks a different problem.
        // But it seems to fix bug 38053.
        setTraits(areaCreated, lastPos == null || !isLast(lastPos));
        parentLM.addChildArea(getCurrentArea());

        context.setFlags(LayoutContext.LAST_AREA, isLast);
        areaCreated = true;
    }

    /** @see LayoutManager#addChildArea(Area) */
    @Override
	public void addChildArea(final Area childArea) {
        final Area parent = getCurrentArea();
        if (getContext().resolveLeadingSpace()) {
            addSpace(parent,
                    getContext().getLeadingSpace().resolve(false),
                    getContext().getSpaceAdjust());
        }
        parent.addChildArea(childArea);
        /*【添加：START】by 李晓光 2008-09-25*/
        /*if(childArea instanceof InlineArea){
        	((InlineArea)childArea).setParentArea(parent);
        }*/
        childArea.setParentArea(parent);
        /*【添加：END】by 李晓光 2008-09-25*/
    }

    /** @see LayoutManager#getChangedKnuthElements(List, int) */
    @Override
	public LinkedList getChangedKnuthElements(final List oldList, final int alignment) {
        final LinkedList returnedList = new LinkedList();
        addKnuthElementsForBorderPaddingStart(returnedList);
        returnedList.addAll(super.getChangedKnuthElements(oldList, alignment));
        addKnuthElementsForBorderPaddingEnd(returnedList);
        return returnedList;
    }

    /**
     * Creates Knuth elements for start border padding and adds them to the return list.
     * @param returnList return list to add the additional elements to
     */
    protected void addKnuthElementsForBorderPaddingStart(final List returnList) {
        //Border and Padding (start)
        if (borderProps != null) {
            final int ipStart = borderProps.getBorderStartWidth(false)
                         + borderProps.getPaddingStart(false, this);
            if (ipStart > 0) {
                returnList.add(0,new KnuthBox(ipStart, getAuxiliaryPosition(), true));
            }
        }
    }

    /**
     * Creates Knuth elements for end border padding and adds them to the return list.
     * @param returnList return list to add the additional elements to
     */
    protected void addKnuthElementsForBorderPaddingEnd(final List returnList) {
        //Border and Padding (after)
        if (borderProps != null) {
            final int ipEnd = borderProps.getBorderEndWidth(false)
                        + borderProps.getPaddingEnd(false, this);
            if (ipEnd > 0) {
                returnList.add(new KnuthBox(ipEnd, getAuxiliaryPosition(), true));
            }
        }
    }

    /** @return a cached auxiliary Position instance used for things like spaces. */
    protected Position getAuxiliaryPosition() {
        //if (this.auxiliaryPosition == null) {
            //this.auxiliaryPosition = new NonLeafPosition(this, new LeafPosition(this, -1));
            this.auxiliaryPosition = new NonLeafPosition(this, null);
        //}
        return this.auxiliaryPosition;
    }

    /** @see com.wisii.wisedoc.layoutmanager.inline.LeafNodeLayoutManager#addId() */
    @Override
	protected void addId() {
        if (fobj instanceof Inline) {
            getPSLM().addIDToPage(getInlineFO().getId());
        }
    }

}

