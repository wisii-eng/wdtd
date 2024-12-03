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
package com.wisii.wisedoc.render;

// Java
import java.awt.Color;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.awt.image.FilteredImageSource;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.avalon.framework.configuration.Configurable;
import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.w3c.dom.Document;

import com.wisii.wisedoc.apps.FOUserAgent;
import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.area.BeforeFloat;
import com.wisii.wisedoc.area.Block;
import com.wisii.wisedoc.area.BlockViewport;
import com.wisii.wisedoc.area.BodyRegion;
import com.wisii.wisedoc.area.CTM;
import com.wisii.wisedoc.area.Footnote;
import com.wisii.wisedoc.area.LineArea;
import com.wisii.wisedoc.area.MainReference;
import com.wisii.wisedoc.area.NormalFlow;
import com.wisii.wisedoc.area.OffDocumentItem;
import com.wisii.wisedoc.area.Page;
import com.wisii.wisedoc.area.PageViewport;
import com.wisii.wisedoc.area.RegionReference;
import com.wisii.wisedoc.area.RegionViewport;
import com.wisii.wisedoc.area.Span;
import com.wisii.wisedoc.area.Trait;
import com.wisii.wisedoc.area.inline.Character;
import com.wisii.wisedoc.area.inline.Container;
import com.wisii.wisedoc.area.inline.ForeignObject;
import com.wisii.wisedoc.area.inline.GroupInlineArea;
import com.wisii.wisedoc.area.inline.Image;
import com.wisii.wisedoc.area.inline.InlineArea;
import com.wisii.wisedoc.area.inline.InlineBlockParent;
import com.wisii.wisedoc.area.inline.InlineParent;
import com.wisii.wisedoc.area.inline.Leader;
import com.wisii.wisedoc.area.inline.Space;
import com.wisii.wisedoc.area.inline.SpaceArea;
import com.wisii.wisedoc.area.inline.TextArea;
import com.wisii.wisedoc.area.inline.Viewport;
import com.wisii.wisedoc.area.inline.WordArea;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.WiseDocColor;
import com.wisii.wisedoc.exception.FOVException;
import com.wisii.wisedoc.fonts.FontInfo;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.render.java2d.FovImageFilter;

/**
 * Abstract base class for all renderers. The Abstract renderer does all the
 * top level processing of the area tree and adds some abstract methods to
 * handle viewports. This keeps track of the current block and inline position.
 */
public abstract class AbstractRenderer implements Renderer, Configurable, Constants
{
    /** user agent    */
    protected FOUserAgent userAgent = new FOUserAgent();

    /** block progression position     */
    protected int currentBPPosition = 0;
    /** inline progression position     */
    protected int currentIPPosition = 0;
    /** the block progression position of the containing block used for absolutely positioned blocks    */
    protected int containingBPPosition = 0;
    /** the inline progression position of the containing block used for absolutely positioned blocks     */
    protected int containingIPPosition = 0;

    protected int parentBlockLength = 0; // TextArea所在的Block的显示区域的宽度
    // add by huangzl.
//    protected float parentBlockRecX = 0.0f; // TextArea的上层节点（Block）的显示区域的左上角的X坐标
//    protected float parentBlockRecY = 0.0f; // TextArea的上层节点（Block）的显示区域的左上角的Y坐标
    protected float parentIndent = 0.0f; // TextArea的缩进值
//    protected boolean isTextAlign = false; // true:缩进类型是text-align
    // add end.
    protected boolean _multiTextFlag;

    /** the currently active PageViewport */
    protected PageViewport currentPageViewport;

    private Set warnedXMLHandlers;

    //addby 许浩 全局变量，存放当前绘制的ViewPort的显示区域
    protected static Rectangle2D _currentViewportArea;
//    addby zq打印机名称
    protected String printerName;
//    打印信息
    protected List table;

    /** @see org.apache.avalon.framework.configuration.Configurable#configure(Configuration)     */
    public void configure(Configuration conf) throws ConfigurationException
    {
    }

    /** @see com.wisii.wisedoc.render.Renderer#setupFontInfo(FontInfo)     */
    public abstract void setupFontInfo(FontInfo fontInfo);

    /**  @see com.wisii.wisedoc.render.Renderer#setUserAgent(FOUserAgent)     */
    public void setUserAgent(FOUserAgent agent)
    {
        userAgent = agent;
    }

    public FOUserAgent getUserAgent()
    {
        return userAgent;
    }


    /** @see com.wisii.wisedoc.render.Renderer#startRenderer(OutputStream) */
    public void startRenderer(OutputStream outputStream) throws IOException
    {}

    /** @see com.wisii.wisedoc.render.Renderer#stopRenderer() */
    public void stopRenderer() throws IOException
    {}

    /**
     * Check if this renderer supports out of order rendering. If this renderer
     * supports out of order rendering then it means that the pages that are
     * not ready will be prepared and a future page will be rendered.
     * @return   True if the renderer supports out of order rendering
     * @see      com.wisii.wisedoc.render.Renderer
     */
    public boolean supportsOutOfOrder()
    {
        return false;
    }

    /** @see     com.wisii.wisedoc.render.Renderer#processOffDocumentItem(OffDocumentItem)     */
    public void processOffDocumentItem(OffDocumentItem oDI)
    {}

    /** @see com.wisii.wisedoc.render.Renderer#getGraphics2DAdapter() */
    public Graphics2DAdapter getGraphics2DAdapter()
    {
        return null;
    }

    /** @see com.wisii.wisedoc.render.Renderer#getImageAdapter() */
    public ImageAdapter getImageAdapter()
    {
        return null;
    }

    /** @return the current PageViewport or null, if none is active */
    protected PageViewport getCurrentPageViewport()
    {
        return this.currentPageViewport;
    }

    /**
     * Prepare a page for rendering. This is called if the renderer supports out of order rendering. The renderer should
     * prepare the page so that a page further on in the set of pages can be rendered. The body of the page should not
     * be rendered. The page will be rendered at a later time by the call to render page.
     * @see com.wisii.wisedoc.render.Renderer#preparePage(PageViewport)
     */
    public void preparePage(PageViewport page)
    {}

    /**
     * Utility method to convert a page sequence title to a string. Some
     * renderers may only be able to use a string title. A title is a sequence
     * of inline areas that this method attempts to convert to an equivalent string.
     * @param title  The Title to convert
     * @return       An expanded string representing the title
     */
    protected String convertTitleToString(LineArea title)
    {
        List children = title.getInlineAreas();
        String str = convertToString(children);
        return str.trim();
    }

    private String convertToString(List children)
    {
        StringBuffer sb = new StringBuffer();
        for(int count = 0; count < children.size(); count++)
        {
            InlineArea inline = (InlineArea)children.get(count);
            //if (inline instanceof Character)
            //    sb.append(((Character) inline).getChar());
            /*else*/if(inline instanceof TextArea)
            {
                sb.append(((TextArea)inline).getText());
            }
            else if(inline instanceof InlineParent)
            {
                sb.append(convertToString(((InlineParent)inline).getChildAreas()));
            }
            else
            {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    /** @see com.wisii.wisedoc.render.Renderer#startPageSequence(LineArea) */
    public void startPageSequence(LineArea seqTitle)
    {
        //do nothing
    }

    // normally this would be overriden to create a page in the output
    /** @see com.wisii.wisedoc.render.Renderer#renderPage(PageViewport) */
    public void renderPage(PageViewport page) throws IOException, FOVException
    {
        this.currentPageViewport = page;
        try
        {
            Page p = page.getPage();
            renderPageAreas(p);
        }
        finally
        {
            this.currentPageViewport = null;
        }
    }

    /**
     * Renders page areas.
     * @param page  The page whos page areas are to be rendered
     */
    protected void renderPageAreas(Page page)
    {
        /* Spec does not appear to specify whether fo:region-body should appear above or below side regions in cases of
         * overlap.  FOV decision is to have fo:region-body on top, hence it is rendered last here. */
        //增加给_currentViewportArea赋值的语句
        RegionViewport viewport;
        viewport = page.getRegionViewport(FO_REGION_BEFORE);
        if(viewport != null)
        {
            _currentViewportArea = viewport.getViewport();
        }
        renderRegionViewport(viewport);

        viewport = page.getRegionViewport(FO_REGION_START);
        if(viewport != null)
        {
            _currentViewportArea = viewport.getViewport();
        }
        renderRegionViewport(viewport);

        viewport = page.getRegionViewport(FO_REGION_END);
        if(viewport != null)
        {
            _currentViewportArea = viewport.getViewport();
        }
        renderRegionViewport(viewport);

        viewport = page.getRegionViewport(FO_REGION_AFTER);
        if(viewport != null)
        {
            _currentViewportArea = viewport.getViewport();
        }
        renderRegionViewport(viewport);

        viewport = page.getRegionViewport(FO_REGION_BODY);
        if(viewport != null)
        {
            _currentViewportArea = viewport.getViewport();
        }
        renderRegionViewport(viewport);
    }

    /**
     * Renders a region viewport. <p>
     * The region may clip the area and it establishes a position from where the region is placed.</p>
     * @param port  The region viewport to be rendered
     */
    protected void renderRegionViewport(RegionViewport port)
    {
        if(port != null)
        {
            Rectangle2D view = port.getViewport();
            // The CTM will transform coordinates relative to
            // this region-reference area into page coords, so
            // set origin for the region to 0,0.
            currentBPPosition = 0;
            currentIPPosition = 0;

            RegionReference regionReference = port.getRegionReference();
            handleRegionTraits(port);

            //  shouldn't the viewport have the CTM
            startVParea(regionReference.getCTM(), port.isClip() ? view : null, port);
            // do after starting viewport area
            if(regionReference.getRegionClass() == FO_REGION_BODY)
            {
                renderBodyRegion((BodyRegion)regionReference);
            }
            else
            {
                renderRegion(regionReference);
            }
            endVParea();
        }
    }

    /**
     * Establishes a new viewport area.
     * @param ctm the coordinate transformation matrix to use
     * @param clippingRect the clipping rectangle if the viewport should be clipping, null if no clipping is performed.
     * @param area TODO
     */
    protected abstract void startVParea(CTM ctm, Rectangle2D clippingRect, Area area);

    /** Signals exit from a viewport area. Subclasses can restore transformation matrices valid before the viewport area was started.     */
    protected abstract void endVParea();

    /**
     * Handle the traits for a region. This is used to draw the traits for the given page region.
     * (See Sect. 6.4.1.2 of XSL-FO spec.)
     * @param rv the RegionViewport whose region is to be drawn
     */
    protected void handleRegionTraits(RegionViewport rv)
    {
        // draw border and background
    }

    /**
     * Renders a region reference area.
     * @param region  The region reference area
     */
    protected void renderRegion(RegionReference region)
    {
        List blocks = region.getBlocks();
        renderBlocks(null, blocks);
    }

    /**
     * Renders a body region area.
     * @param region  The body region
     */
    protected void renderBodyRegion(BodyRegion region)
    {
        BeforeFloat bf = region.getBeforeFloat();
        if(bf != null)
        {
            renderBeforeFloat(bf);
        }
        MainReference mr = region.getMainReference();
        if(mr != null)
        {
            renderMainReference(mr);
        }
        Footnote foot = region.getFootnote();
        if(foot != null)
        {
            renderFootnote(foot);
        }
    }

    /**
     * Renders a before float area.
     * @param bf  The before float area
     */
    protected void renderBeforeFloat(BeforeFloat bf)
    {
        List blocks = bf.getChildAreas();
        if(blocks != null)
        {
            renderBlocks(null, blocks);
            Block sep = bf.getSeparator();
            if(sep != null)
            {
                renderBlock(sep);
            }
        }
    }

    /**
     * Renders a footnote
     * @param footnote  The footnote
     */
    protected void renderFootnote(Footnote footnote)
    {
        currentBPPosition += footnote.getTop();
        List blocks = footnote.getChildAreas();
        if(blocks != null)
        {
            Block sep = footnote.getSeparator();
            if(sep != null)
            {
                renderBlock(sep);
            }
            renderBlocks(null, blocks);
        }
    }

    /**
     * Renders the main reference area.
     * <p>
     * The main reference area contains a list of spans that are stacked on the page.
     * The spans contain a list of normal flow reference areas that are positioned into columns.
     * </p>
     * @param mr  The main reference area
     */
    protected void renderMainReference(MainReference mr)
    {
        int saveIPPos = currentIPPosition;

        Span span = null;
        List spans = mr.getSpans();
        int saveBPPos = currentBPPosition;
        int saveSpanBPPos = saveBPPos;
        for(int count = 0; count < spans.size(); count++)
        {
            span = (Span)spans.get(count);
            for(int c = 0; c < span.getColumnCount(); c++)
            {
                NormalFlow flow = (NormalFlow)span.getNormalFlow(c);
                /* 【添加：START】by 李晓光 2009-1-15 */
                /*Rectangle2D view = new Rectangle2D.Double(flow.getXOffset(), flow.getYOffset(), flow.getIPD(), flow.getBPD());
                flow.setViewport(view);*/
                /* 【添加：END】by 李晓光 2009-1-15 */
                if(flow != null)
                {
                    currentBPPosition = saveSpanBPPos;
                    renderFlow(flow);
                    currentIPPosition += flow.getIPD();
                    currentIPPosition += mr.getColumnGap();
                }
            }
            currentIPPosition = saveIPPos;
            currentBPPosition = saveSpanBPPos + span.getHeight();
            saveSpanBPPos = currentBPPosition;
        }
        currentBPPosition = saveBPPos;
    }

    /**
     * Renders a flow reference area.
     * @param flow  The flow reference area
     */
    protected void renderFlow(NormalFlow flow)
    {
        // the normal flow reference area contains stacked blocks
        List blocks = flow.getChildAreas();
        if(blocks != null)
        {
            renderBlocks(null, blocks);
        }
    }

    /**
     * Handle block traits. This method is called when the correct ip and bp posiiton is set. This should be overridden
     * to draw border and background traits for the block area.
     * @param block the block area
     */
    protected void handleBlockTraits(Block block)
    {
        // draw border and background
    }

    /**
     * Renders a block viewport.
     * @param bv        The block viewport
     * @param children  The children to render within the block viewport
     */
    protected void renderBlockViewport(BlockViewport bv, List children)
    {
        // clip and position viewport if necessary
        if(bv.getPositioning() == Block.ABSOLUTE)
        {
            // save positions
            int saveIP = currentIPPosition;
            int saveBP = currentBPPosition;

            Rectangle2D clippingRect = null;
            if(bv.getClip())
            {
                clippingRect = new Rectangle(saveIP, saveBP, bv.getIPD(), bv.getIPD());
            }

            CTM ctm = bv.getCTM();
            currentIPPosition = 0;
            currentBPPosition = 0;

            startVParea(ctm, clippingRect, bv);
            handleBlockTraits(bv);
            renderBlocks(bv, children);
            endVParea();

            // clip if necessary
            currentIPPosition = saveIP;
            currentBPPosition = saveBP;
        }
        else
        {
            // save position and offset
            int saveIP = currentIPPosition;
            int saveBP = currentBPPosition;

            handleBlockTraits(bv);
            renderBlocks(bv, children);

            currentIPPosition = saveIP;
            currentBPPosition = saveBP + bv.getAllocBPD();
        }
    }

    /**
     * Renders a list of block areas.
     * @param parent  the parent block if the parent is a block, otherwise a null value.
     * @param blocks  The block areas
     */
    protected void renderBlocks(Block parent, List blocks)
	{
		int saveIP = currentIPPosition;
		int saveBP = currentBPPosition;

		// Calculate the position of the content rectangle.
//		boolean inviewpo
		if (parent != null
				&& !Boolean.TRUE
						.equals(parent.getTrait(Trait.IS_VIEWPORT_AREA)))
		{
			currentBPPosition += parent.getBorderAndPaddingWidthBefore();
			/* This is unnecessary now as we're going to use the *-indent traits
			             currentIPPosition += parent.getBorderAndPaddingWidthStart();
			             Integer spaceStart = (Integer) parent.getTrait(Trait.SPACE_START);
			             if (spaceStart != null) {
			    currentIPPosition += spaceStart.intValue();
			             }*/
		}

		// the position of the containing block is used for absolutely
		// positioned areas
		int contBP = currentBPPosition;
		int contIP = currentIPPosition;
		containingBPPosition = currentBPPosition;
		containingIPPosition = currentIPPosition;

		// add by xuhao
		if (parent != null)
		{
			parentBlockLength = parent.getIPD(); // mod by
													// huangzl.只获取内容的宽度,不需要borders
													// 和
													// padding的宽度，否则控件可能覆盖Block的边框。
			// del.因为parentBlockLength的值为Block内容的宽度，即getIPD()，而不是getAllocIPD，所以不用考虑start-indent的情况
			// parentIndent = parent.getStartIndent();//add by
			// huangzl.处理Block的属性：start-indent
		}
		// add end

		for (int count = 0; count < blocks.size(); count++)
		{
			Object obj = blocks.get(count);
			if (obj instanceof Block)
			{
				currentIPPosition = contIP;
				containingBPPosition = contBP;
				containingIPPosition = contIP;
				renderBlock((Block) obj);
				containingBPPosition = contBP;
				containingIPPosition = contIP;
			} else
			{
				// a line area is rendered from the top left position
				// of the line, each inline object is offset from there
				LineArea line = (LineArea) obj;
				currentIPPosition = contIP + parent.getStartIndent()
						+ line.getStartIndent();
				// mod.因为parentBlockLength的值为Block内容的宽度，即getIPD()，而不是getAllocIPD，所以不用考虑start-indent的情况
				// float startIndent = line.getStartIndent();
				// if(startIndent > 0.0)
				// {
				// isTextAlign = true;
				// parentIndent = startIndent; // add by huangzl.处理属性：text-align
				// }
				parentIndent = line.getStartIndent();
				/* 【添加：START】 by 李晓光	2008-10-07  */
				line.setOffsetLeft(currentIPPosition);
				/* 【添加：END】 by 李晓光	2008-10-07  */
				// mod end.
				renderLineArea(line);
				// InlineArea child = (InlineArea) line.getInlineAreas().get(0);
				currentBPPosition += line.getAllocBPD();
			}
			currentIPPosition = saveIP;
		}
	}

    /**
     * Renders a block area.
     * @param block  The block area
     */
    protected void renderBlock(Block block)
    {
        List children = block.getChildAreas();
        if(block instanceof BlockViewport)
        {
            if(children != null)
            {
                renderBlockViewport((BlockViewport)block, children);
            }
            else
            {
                handleBlockTraits(block);
                // simply move position
                currentBPPosition += block.getAllocBPD();
            }
        }
        else
        {
        	
            // save position and offset
            int saveIP = currentIPPosition;
            int saveBP = currentBPPosition;

            if(block.getPositioning() == Block.ABSOLUTE)
            {
                currentIPPosition += block.getXOffset();
                currentBPPosition += block.getYOffset();
                currentBPPosition += block.getSpaceBefore();
                handleBlockTraits(block);

                if(children != null)
                {
                    renderBlocks(block, children);
                }

                // absolute blocks do not effect the layout
                currentBPPosition = saveBP;
            }
            else
            {
                // relative blocks are offset
                currentIPPosition += block.getXOffset();
                currentBPPosition += block.getYOffset();
                currentBPPosition += block.getSpaceBefore();

                handleBlockTraits(block);

                if(children != null)
                {
                    renderBlocks(block, children);
                }

                // stacked and relative blocks effect stacking
                currentIPPosition = saveIP;
                currentBPPosition = saveBP + block.getAllocBPD();
            }
        }
    }

    /**
     * Renders a line area. <p>
     * A line area may have grouped styling for its children such as underline, background.</p>
     * @param line  The line area
     */
    protected void renderLineArea(LineArea line)
    {
        List children = line.getInlineAreas();
        int saveBP = currentBPPosition;
        line.setOffsetTop(saveBP);
        
        
        currentBPPosition += line.getSpaceBefore();       
        //add by xuhao
        int textCount = getInlineCount(children);
        if(textCount > 1)
        {
            _multiTextFlag = true;
        }
        else
        {
            _multiTextFlag = false;
        }
        //add end
        int size = children.size();
        if(size>0){
        for(int count = 0; count < size; count++)
        {
            InlineArea inline = (InlineArea)children.get(count);
            renderInlineArea(inline);
        }
        //add by zhangqiang 显示换行符号
        }
        else
        {
        	drawLineBreak(line);
        }
        currentBPPosition = saveBP;
    }

    /**
	 * add by zhangqiang
	 * 显示换行符号
	 *
	 * @param     
	 * @return      
	 * @exception   
	 */
	
	protected void drawLineBreak(Area line)
	{
	}

	private int getInlineCount(List children)
    {
        int count = 0;
        for(int i = 0; i < children.size(); i++)
        {
            Object obj = children.get(i);
            if(obj instanceof InlineParent)
            {
                count++;
            }
        }
        return count;
    }

    /**
     * Render the given InlineArea.
     * @param inlineArea inline area text to render
     */
    protected void renderInlineArea(InlineArea inlineArea)
    {
        if(inlineArea instanceof TextArea)
        {
            renderText((TextArea)inlineArea);
        }
        //else if (inlineArea instanceof Character)
        //renderCharacter((Character) inlineArea);
        else if(inlineArea instanceof WordArea)
        {
            renderWord((WordArea)inlineArea);
        }
        else if(inlineArea instanceof SpaceArea)
        {
            renderSpace((SpaceArea)inlineArea);
        }
        else if(inlineArea instanceof InlineParent)
        {
            renderInlineParent((InlineParent)inlineArea);
        }
        else if(inlineArea instanceof InlineBlockParent)
        {
            renderInlineBlockParent((InlineBlockParent)inlineArea);
        }
        else if(inlineArea instanceof Space)
        {
            renderInlineSpace((Space)inlineArea);
        }
        else if(inlineArea instanceof Viewport)
        {
            renderViewport((Viewport)inlineArea);
        }
        else if(inlineArea instanceof Leader)
        {
            renderLeader((Leader)inlineArea);
        }else if(inlineArea instanceof GroupInlineArea){
        	renderGroupInlineArea((GroupInlineArea)inlineArea);//添加： by 李晓光  2009-3-11
        }
    }

    /**
     * Render the given Character.
     * @param ch the character to render
     * @deprecated Only TextArea should be used. This method will be removed eventually.
     */
    protected void renderCharacter(Character ch)
    {
        currentIPPosition += ch.getAllocIPD();
    }

    /**
     * Common method to render the background and borders for any inline area.
     * The all borders and padding are drawn outside the specified area.
     * @param area the inline area for which the background, border and padding is to be rendered
     */
    protected abstract void renderInlineAreaBackAndBorders(InlineArea area);

    /**
     * Render the given Space.
     * @param space the space to render
     */
    protected void renderInlineSpace(Space space)
    {
        space.setBPD(0);
        renderInlineAreaBackAndBorders(space);
        // an inline space moves the inline progression position for the current block by the width or height of the
        // space it may also have styling (only on this object) that needs handling
        currentIPPosition += space.getAllocIPD();
    }

    /**
     * Render the given Leader.
     * @param area the leader to render
     */
    protected void renderLeader(Leader area)
    {
        currentIPPosition += area.getAllocIPD();
    }

    /**
     * Render the given TextArea.
     * @param text the text to render
     */
    protected void renderText(TextArea text)
    {
        int saveIP = currentIPPosition;
        int saveBP = currentBPPosition;
        Iterator iter = text.getChildAreas().iterator();
        while(iter.hasNext())
        {
            renderInlineArea((InlineArea)iter.next());
        }
        currentIPPosition = saveIP + text.getAllocIPD();
    }

    /**
     * Render the given WordArea.
     * @param word the word to render
     */
    protected void renderWord(WordArea word)
    {
        currentIPPosition += word.getAllocIPD();
    }

    /**
     * Render the given SpaceArea.
     * @param space the space to render
     */
    protected void renderSpace(SpaceArea space)
    {
        currentIPPosition += space.getAllocIPD();
    }

    /**
     * Render the given InlineParent.
     * @param ip the inline parent to render
     */
    protected void renderInlineParent(InlineParent ip)
    {
        renderInlineAreaBackAndBorders(ip);
        int saveIP = currentIPPosition;
        int saveBP = currentBPPosition;
        currentIPPosition += ip.getBorderAndPaddingWidthStart();
        currentBPPosition += ip.getOffset();
        
        Iterator iter = ip.getChildAreas().iterator();
        while(iter.hasNext())
        {
            InlineArea area = (InlineArea)iter.next();

            // add by xuhao
            if(area instanceof TextArea && ((TextArea)area).getEditMode() != 0)
            {
                // mod by huangzl.保存上层节点的相关属性
                area.setParentArea(ip); // 从InlineParent得到边框的宽度
                if(_multiTextFlag)
                {
                    ((TextArea)area).setMultiInLine(true);
                }
                else
                {
                    ((TextArea)area).setMaxShowWidth(parentBlockLength / 1000);
//                    ((TextArea)area).setParentBlockRecX(parentBlockRecX);
//                    ((TextArea)area).setParentBlockRecY(parentBlockRecY);
                    ((TextArea)area).setIndent(parentIndent / 1000);
//                    ((TextArea)area).setTextAlign(isTextAlign);// del.因为parentBlockLength的值为Block内容的宽度，即getIPD()，而不是getAllocIPD，所以不用考虑start-indent的情况
                }
                // mod end.
            }
            // add end

            renderInlineArea(area);
        }
        currentIPPosition = saveIP + ip.getAllocIPD();
        currentBPPosition = saveBP;
    }

    /**
     * Render the given InlineBlockParent.
     * @param ibp the inline block parent to render
     */
    protected void renderInlineBlockParent(InlineBlockParent ibp)
    {
        renderInlineAreaBackAndBorders(ibp);
        currentIPPosition += ibp.getBorderAndPaddingWidthStart();
        // For inline content the BP position is updated by the enclosing line area
        int saveBP = currentBPPosition;
        currentBPPosition += ibp.getOffset();
        renderBlock(ibp.getChildArea());
        currentBPPosition = saveBP;
    }

    /**
     * Render the given Viewport.
     * @param viewport the viewport to render
     */
    protected void renderViewport(Viewport viewport)
    {
        Area content = viewport.getContent();
        int saveBP = currentBPPosition;
        currentBPPosition += viewport.getOffset();
        Rectangle2D contpos = viewport.getContentPosition();
        
        /* 【添加：START】 by 李晓光 2008-10-27 */
        Rectangle2D view = new Rectangle2D.Double(currentIPPosition, currentBPPosition, viewport.getAllocIPD(), viewport.getAllocBPD());
        viewport.setViewport(view);
        /* 【添加：END】 by 李晓光 2008-10-27 */
        /*renderGroupInlineArea(new GroupInlineArea(true));*/
        if(content instanceof Image)
        {
            renderImage((Image)content, contpos);
        }
        else if(content instanceof Container)
        {
            renderContainer((Container)content);
        }
        else if(content instanceof ForeignObject)
        {
            renderForeignObject((ForeignObject)content, contpos);
        }
        currentIPPosition += viewport.getAllocIPD();
        currentBPPosition = saveBP;
        /*renderGroupInlineArea(new GroupInlineArea(false));*/
    }
    /* 【添加：START】 by 李晓光 2009-3-11 */
    protected void renderGroupInlineArea(GroupInlineArea area){}
    
    /* 【添加：END】 by 李晓光 2009-3-11 */
    /**
     * Renders an image area.
     * @param image  The image
     * @param pos    The target position of the image
     * (todo) Make renderImage() protected
     */
    public void renderImage(Image image, Rectangle2D pos)
    {
        // Default: do nothing.
        // Some renderers (ex. Text) don't support images.
    }

    /**
     * Tells the renderer to render an inline container.
     * @param cont  The inline container area
     */
    protected void renderContainer(Container cont)
    {
        int saveIP = currentIPPosition;
        int saveBP = currentBPPosition;

        List blocks = cont.getBlocks();
        renderBlocks(null, blocks);
        currentIPPosition = saveIP;
        currentBPPosition = saveBP;
    }

    /**
     * Renders a foreign object area.
     * @param fo   The foreign object area
     * @param pos  The target position of the foreign object
     * (todo) Make renderForeignObject() protected
     */
    public void renderForeignObject(ForeignObject fo, Rectangle2D pos)
    {
        // Default: do nothing.
        // Some renderers (ex. Text) don't support foreign objects.
    }

    /**
     * Returns the configuration subtree for a specific renderer.
     * @param cfg the renderer configuration
     * @param namespace the namespace (i.e. the XMLHandler) for which the configuration should be returned
     * @return the requested configuration subtree, null if there's no configuration
     */
    public static Configuration getHandlerConfig(Configuration cfg, String namespace)
    {
        if(cfg == null || namespace == null)
        {
            return null;
        }

        Configuration handlerConfig = null;

        Configuration[] children = cfg.getChildren("xml-handler");
        for(int i = 0; i < children.length; ++i)
        {
            try
            {
                if(children[i].getAttribute("namespace").equals(namespace))
                {
                    handlerConfig = children[i];
                    break;
                }
            }
            catch(ConfigurationException e)
            {
                // silently pass over configurations without namespace
            }
        }
        if(LogUtil.canDebug())
        {
            LogUtil.debug((handlerConfig == null ? "No" : "") + "XML handler configuration found for namespace " + namespace);
        }
        return handlerConfig;
    }

    /**
     * Render the xml document with the given xml namespace.
     * The Render Context is by the handle to render into the current rendering target.
     * @param ctx rendering context
     * @param doc DOM Document containing the source document
     * @param namespace Namespace URI of the document
     */
    public void renderXML(RendererContext ctx, Document doc, String namespace)
    {
//chg by huangzl.
//        XMLHandler handler = userAgent.getXMLHandlerRegistry().getXMLHandler(this, namespace);
        XMLHandler handler = new com.wisii.wisedoc.render.java2d.Java2DSVGHandler();
        if(handler != null)
        {
            try
            {
                //Optional XML handler configuration
//del by huagnzl.因为getUserRendererConfig()方法返回的总是null
//                Configuration cfg = userAgent.getUserRendererConfig(getMimeType());
//                if (cfg != null)
//                {
//                    cfg = getHandlerConfig(cfg, namespace);
//                    if (cfg != null)
//                    {
//                        ctx.setProperty(RendererContextConstants.HANDLER_CONFIGURATION, cfg);
//                    }
//                }
                handler.handleXML(ctx, doc, namespace);
            }
            catch(Throwable t)
            {
                // could not handle document
                LogUtil.errorException("Some XML content will be ignored. " + "Could not render XML", t);
            }
        }
        else
        {
            if(warnedXMLHandlers == null)
            {
                warnedXMLHandlers = new java.util.HashSet();
            }
            if(!warnedXMLHandlers.contains(namespace))
            {
                // no handler found for document
                warnedXMLHandlers.add(namespace);
                LogUtil.warn("Some XML content will be ignored. " + "No handler defined for XML: " + namespace);
            }
        }
    }

    /**
     * Get the MIME type of the renderer.
     * @return   The MIME type of the renderer
     */
    public String getMimeType()
    {
        return null;
    }

    /*默认实现 Render接口*/
    public void setupPrinterInfo(ArrayList PrinterList, String PrinterName)
    {
        this.printerName = PrinterName;
        table = PrinterList;
    }


    /**add by lzy 过滤图片
     * imageSrc 需要过滤的image
     * */
    public java.awt.Image FilterImage(java.awt.Image imageSrc, int aphla)
    {
        java.awt.Image Filteredimage = null;

        Color BACKCOLOR = new Color(255, 255, 255, 0); //背景色
        Color FLGCOLOR = new Color(0, 0, 255, 255); //前景色
        int FlgAphla = aphla; //转换后前景色的透明度。
        int BACKAPLHA = 0; //转换后背景色的透明度。

        FovImageFilter d = new FovImageFilter(BACKCOLOR, BACKCOLOR, FlgAphla); //过滤器

        Filteredimage = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(
            imageSrc.getSource(), d));

        //等待加栽图片完成
        MediaTracker mt = null;

       
//            mt = new MediaTracker(Start.appl);
        
       

        mt.addImage(Filteredimage, 1);
        try
        {
            mt.waitForID(1);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return Filteredimage;
    }
    /* 【添加：START】by 李晓光2009-1-20 */
	public static boolean isAvailabilityLayer(Color color, Set<Integer> layers){
		if(!(color instanceof WiseDocColor))
			return Boolean.TRUE;
		if(layers == null || layers.size() == 0 )
			return Boolean.FALSE;
		WiseDocColor c = (WiseDocColor)color;
		
		return layers.contains(c.getLayer());
	}
	public static boolean isAvailabilityLayer(Area image, Set<Integer> layers){
		if(layers == null || layers.size() == 0 )
			return Boolean.FALSE;
		Integer layer = (Integer)image.getTrait(Trait.IMAGE_LAYER);
		if(layer == null)
			layer = 0;
		return layers.contains(layer);
	}
	public static boolean isAvailabilityLayer(int layer, Set<Integer> layers){
		if(layers == null || layers.size() == 0 )
			return Boolean.FALSE;
		
		return layers.contains(layer);
	}
	/* 【添加：END】by 李晓光2009-1-20 */

}
