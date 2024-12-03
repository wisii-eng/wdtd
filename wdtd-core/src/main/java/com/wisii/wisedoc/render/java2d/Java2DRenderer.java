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
package com.wisii.wisedoc.render.java2d;

// Java
import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.FilteredImageSource;
import java.awt.image.PixelInterleavedSampleModel;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import javax.swing.tree.TreeNode;
import org.w3c.dom.Document;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.apps.FOUserAgent;
import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.area.BlockViewport;
import com.wisii.wisedoc.area.CTM;
import com.wisii.wisedoc.area.LineArea;
import com.wisii.wisedoc.area.PageViewport;
import com.wisii.wisedoc.area.Trait;
import com.wisii.wisedoc.area.inline.BarCodeArea;
import com.wisii.wisedoc.area.inline.ChartArea;
import com.wisii.wisedoc.area.inline.GroupInlineArea;
import com.wisii.wisedoc.area.inline.Image;
import com.wisii.wisedoc.area.inline.InlineArea;
import com.wisii.wisedoc.area.inline.Leader;
import com.wisii.wisedoc.area.inline.QianZhangArea;
import com.wisii.wisedoc.area.inline.SpaceArea;
import com.wisii.wisedoc.area.inline.TextArea;
import com.wisii.wisedoc.area.inline.WordArea;
import com.wisii.wisedoc.area.inline.GroupInlineArea.InlineType;
import com.wisii.wisedoc.configure.ConfigureUtil;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.CheckBoxInline;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.FOText;
import com.wisii.wisedoc.document.Group;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.exception.FOVException;
import com.wisii.wisedoc.fonts.Font;
import com.wisii.wisedoc.fonts.FontInfo;
import com.wisii.wisedoc.fonts.Typeface;
import com.wisii.wisedoc.image.FovImage;
import com.wisii.wisedoc.image.ImageFactory;
import com.wisii.wisedoc.image.XMLImage;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.render.AbstractPathOrientedRenderer;
import com.wisii.wisedoc.render.Graphics2DAdapter;
import com.wisii.wisedoc.render.RendererContext;
import com.wisii.wisedoc.util.CharUtilities;
import com.wisii.wisedoc.util.SystemUtil;
import com.wisii.wisedoc.view.LocationConvert;

/**
 * The <code>Java2DRenderer</code> class provides the abstract technical
 * foundation for all rendering with the Java2D API. Renderers like
 * <code>AWTRenderer</code> subclass it and provide the concrete output paths.
 * <p>
 * A lot of the logic is performed by <code>AbstractRenderer</code>. The
 * class-variables <code>currentIPPosition</code> and
 * <code>currentBPPosition</code> hold the position of the currently rendered
 * area.
 * <p>
 * <code>Java2DGraphicsState state</code> holds the <code>Graphics2D</code>,
 * which is used along the whole rendering. <code>state</code> also acts as a
 * stack (<code>state.push()</code> and <code>state.pop()</code>).
 * <p>
 * The rendering process is basically always the same:
 * <p>
 * <code>void renderXXXXX(Area area) {
 *    //calculate the currentPosition
 *    state.updateFont(name, size, null);
 *    state.updateColor(ct, false, null);
 *    state.getGraph.draw(new Shape(args));
 * }</code>
 * 
 */
public abstract class Java2DRenderer extends AbstractPathOrientedRenderer
		implements Printable {
	/** The scale factor for the image size, values: ]0 ; 1] */
	protected double scaleFactor = 1;

	/** The page width in pixels */
	protected int pageWidth = 0;

	/** The page height in pixels */
	protected int pageHeight = 0;

	/** List of Viewports */
	protected List<PageViewport> pageViewportList = Collections
			.synchronizedList(new ArrayList<PageViewport>());// new
																// java.util.ArrayList<PageViewport>();
	/* 用于缓存所有的页 */
	protected static Map<PageSequence, List<PageViewport>> pageViewports = Collections
			.synchronizedMap(new LinkedHashMap<PageSequence, List<PageViewport>>());

	/** The 0-based current page number */
	private int currentPageNumber = 0;

	/** The 0-based total number of rendered pages */
	private int numberOfPages;

	/** true if antialiasing is set */
	protected boolean antialiasing = true;

	/** true if qualityRendering is set */
	protected boolean qualityRendering = true;

	/** The current state, holds a Graphics2D and its context */
	protected Java2DGraphicsState state;

	private final Stack stateStack = new Stack();

	/** true if the renderer has finished rendering all the pages */
	private boolean renderingDone;

	private GeneralPath currentPath = null;
	// private static AffineTransform _saveAT = null; // add by huangzl.保存呈现状态

	/** 页面纵向的偏移量。正数时，向上偏移；负数时，向下偏移 */
	public float excursionX = 0.0f;
	/** 页面横向的偏移量。正数时，向左偏移；负数时，向右偏移 */
	public float excursionY = 0.0f;
	/** 页面横向的缩放比例 */
	public float scaleX = 1.0f;
	/** 页面纵向的的缩放比例 */
	public float scaleY = 1.0f;
	/** 页高是否随比例变化 */
	public boolean isSelectedHeightCheckBox = false;
	/** 页高增加的绝对高度 */
	public float heightAddABS;

	/* 【添加：START】 by 李晓光 2008-09-19 */
	// 页面横向的放缩比例
	private double previewScaleX = 1.0F;
	// 页面纵向的放缩比例
	private double previewScaleY = 1.0F;

	/* 【添加：END】 by 李晓光 2008-09-19 */

	/** Default constructor */
	public Java2DRenderer() {
	}

	/** @see com.wisii.wisedoc.render.Renderer#setUserAgent(com.wisii.wisedoc.apps.FOUserAgent) */
	@Override
	public void setUserAgent(final FOUserAgent foUserAgent) {
		super.setUserAgent(foUserAgent);
		userAgent.setRendererOverride(this); // for document regeneration
	}

	/** @return the FOUserAgent */
	@Override
	public FOUserAgent getUserAgent() {
		return userAgent;
	}

	/** @see com.wisii.wisedoc.render.Renderer#setupFontInfo(com.wisii.wisedoc.fonts.FontInfo) */
	@Override
	public void setupFontInfo(final FontInfo inFontInfo) {
		// Don't call super.setupFontInfo() here! Java2D needs a special font
		// setup
		// create a temp Image to test font metrics on
		fontInfo = inFontInfo;
		final BufferedImage fontImage = new BufferedImage(100, 100,
				BufferedImage.TYPE_INT_RGB);
		final Graphics2D g = fontImage.createGraphics();
		// The next line is important to get accurate font metrics!
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
				RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		FontSetup.setup(fontInfo, g);
	}

	/** @see com.wisii.wisedoc.render.Renderer#getGraphics2DAdapter() */
	@Override
	public Graphics2DAdapter getGraphics2DAdapter() {
		return new Java2DGraphics2DAdapter(state);
	}

	/**
	 * Sets the new scale factor.
	 * 
	 * @param newScaleFactor
	 *            ]0 ; 1]
	 */
	public void setScaleFactor(final double newScaleFactor) {
		scaleFactor = newScaleFactor;
	}

	/** @return the scale factor */
	public double getScaleFactor() {
		return scaleFactor;
	}

	/** @see com.wisii.wisedoc.render.Renderer#startRenderer(java.io.OutputStream) */
	@Override
	public void startRenderer(final OutputStream out) throws IOException {
		// do nothing by default
	}

	/** @see com.wisii.wisedoc.render.Renderer#stopRenderer() */
	@Override
	public void stopRenderer() throws IOException {
		LogUtil.debug("Java2DRenderer stopped");
		renderingDone = true;
		processGroup();
		// TODO set all vars to null for gc
		if (numberOfPages == 0) {
			new FOVException("无可用页");
		}
	}

	/** @return true if the renderer is not currently processing */
	public boolean isRenderingDone() {
		return this.renderingDone;
	}

	public void setRenderingDone(final boolean b) {
		renderingDone = b;
	}

	/** @return The 0-based current page number */
	public int getCurrentPageNumber() {
		return currentPageNumber;
	}

	/**
	 * @param c
	 *            the 0-based current page number
	 */
	public void setCurrentPageNumber(final int c) {
		this.currentPageNumber = c;
	}

	/** @return The 0-based total number of rendered pages */
	public int getNumberOfPages() {
		return numberOfPages;
		/* return getPageViewportCount(); */
	}

	/** Clears the ViewportList. Used if the document is reloaded. */
	public void clearViewportList() {
		numberOfPages = 0;
		pageViewportList.clear();
		// setCurrentPageNumber(0);//del by huangzl.因为文档reload时，当前显示的页面可能不是第一页。
	}

	public Map<PageSequence, List<PageViewport>> getAll() {
		return pageViewports;
	}

	/**
	 * @author 李晓光 2009-4-20
	 * @return
	 */
	private int getPageViewportCount() {
		int count = 0;
		final Collection<List<PageViewport>> pages = pageViewports.values();
		for (final List<PageViewport> list : pages) {
			count += list.size();
		}
		return count;
	}

	/**
	 * @author 李晓光 2009-4-20
	 * @param index
	 * @return
	 */
	private PageViewport getPageViewportOf(final int index) {
		if (index < 0) {
			return null;
		}
		int count = index;
		final Collection<List<PageViewport>> pages = pageViewports.values();
		for (final List<PageViewport> list : pages) {
			if (list.size() > count) {
				return list.get(count);
			} else {
				count -= list.size();
			}
		}

		return null;
	}

	/**
	 * This method override only stores the PageViewport in a List. No actual
	 * rendering is performed here. A renderer override renderPage() to get the
	 * freshly produced PageViewport, and rendere them on the fly (producing the
	 * desired BufferedImages by calling getPageImage(), which lazily starts the
	 * rendering process).
	 * 
	 * @param pageViewport
	 *            the <code>PageViewport</code> object supplied by the Area Tree
	 * @throws IOException
	 *             In case of an I/O error
	 * @see com.wisii.wisedoc.render.Renderer
	 */
	@Override
	public void renderPage(final PageViewport pageViewport) throws IOException {
		// TODO clone?
		pageViewportList.add((PageViewport) pageViewport.clone());
		// currentPageNumber++;//del by huangzl.因为在render过程中，当前显示的页面是不变的。
		numberOfPages++;
	}

	/**
	 * Generates a desired page from the renderer's page viewport list.
	 * 
	 * @param pageViewport
	 *            the PageViewport to be rendered
	 * @return the <code>java.awt.image.BufferedImage</code> corresponding to
	 *         the page or null if the page doesn't exist.
	 */
	public BufferedImage getPageImage(final PageViewport pageViewport) {
		this.currentPageViewport = pageViewport;
		try {
			final Rectangle2D bounds = pageViewport.getViewArea();
			pageWidth = (int) Math.round(bounds.getWidth() / 1000f);
			pageHeight = (int) Math.round(bounds.getHeight() / 1000f);

			// log.info("Rendering Page " + pageViewport.getPageNumberString() +
			// " (pageWidth "
			// + pageWidth + ", pageHeight " + pageHeight + ")");

			LogUtil.info("Rendering Page " + pageViewport.getPageNumberString()
					+ " (pageWidth " + (int) (pageWidth * 25.4 / 72) + "mm"
					+ ", pageHeight " + (int) (pageHeight * 25.4 / 72) + "mm"
					+ ")");

			// mod by huangzl.因为客户端不再使用FOUserAgent
			// double scaleX = scaleFactor * (25.4 /
			// SystemUtil.DEFAULT_TARGET_RESOLUTION)
			// / (SystemUtil.DEFAULT_SOURCE_RESOLUTION /
			// SystemUtil.DEFAULT_TARGET_RESOLUTION);
			// double scaleY = scaleFactor * (25.4 /
			// SystemUtil.DEFAULT_TARGET_RESOLUTION)
			// / (SystemUtil.DEFAULT_SOURCE_RESOLUTION /
			// SystemUtil.DEFAULT_TARGET_RESOLUTION);

			// 获取屏幕的DPI显示
			final int dpi = Toolkit.getDefaultToolkit().getScreenResolution(); // dpi
			userAgent.setTargetResolution(dpi);

			final double scaleX = scaleFactor
					* (25.4 / SystemUtil.DEFAULT_TARGET_RESOLUTION)
					/ (userAgent.getTargetPixelUnitToMillimeter());
			final double scaleY = scaleFactor
					* (25.4 / SystemUtil.DEFAULT_TARGET_RESOLUTION)
					/ (userAgent.getTargetPixelUnitToMillimeter());

			/* 【添加：START】 by 李晓光 2008-09-19 */
			// 更新放缩系数
			setPreviewScaleX(scaleX);
			setPreviewScaleY(scaleY);
			/* 【添加：END】 by 李晓光 2008-09-19 */

			// double scaleX = scaleFactor
			// * (25.4 / FOUserAgent.DEFAULT_TARGET_RESOLUTION)
			// / userAgent.getTargetPixelUnitToMillimeter();
			// double scaleY = scaleFactor
			// * (25.4 / FOUserAgent.DEFAULT_TARGET_RESOLUTION)
			// / userAgent.getTargetPixelUnitToMillimeter();
			final int bitmapWidth = (int) ((pageWidth * scaleX) + 0.5);
			final int bitmapHeight = (int) ((pageHeight * scaleY) + 0.5);

			final BufferedImage currentPageImage = new BufferedImage(
					bitmapWidth, bitmapHeight, BufferedImage.TYPE_INT_ARGB);
			// FIXME TYPE_BYTE_BINARY ?

			final Graphics2D graphics = currentPageImage.createGraphics();
			graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
					RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			if (antialiasing) {
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			}
			if (qualityRendering) {
				graphics.setRenderingHint(RenderingHints.KEY_RENDERING,
						RenderingHints.VALUE_RENDER_QUALITY);
			}

			// transform page based on scale factor supplied
			final AffineTransform at = graphics.getTransform();
			at.scale(scaleX, scaleY);
			graphics.setTransform(at);

			// draw page frame
			/* 【删除：START】by 李晓光 2008-09-26 移到AwtRenderer中处理 */
			/*
			 * graphics.setColor(Color.white); graphics.fillRect(0, 0,
			 * pageWidth, pageHeight);
			 */
			/* 【删除：END】by 李晓光 2008-09-26 */

			// graphics.setColor(Color.black);
			// graphics.drawRect(-1, -1, pageWidth + 2, pageHeight + 2);
			// graphics.drawLine(pageWidth + 2, 0, pageWidth + 2, pageHeight +
			// 2);
			// graphics.drawLine(pageWidth + 3, 1, pageWidth + 3, pageHeight +
			// 3);
			// graphics.drawLine(0, pageHeight + 2, pageWidth + 2, pageHeight +
			// 2);
			// graphics.drawLine(1, pageHeight + 3, pageWidth + 3, pageHeight +
			// 3);
			/* 【添加】 by 李晓光 2008-09-26 测试用 */
			/*
			 * graphics.setColor(new Color(0, 0, 128, 50)); graphics.fillRect(0,
			 * 0, 100, 200);
			 */
			state = new Java2DGraphicsState(graphics, this.fontInfo, at);
			try {
				// reset the current Positions
				currentBPPosition = 0;
				currentIPPosition = 0;
				renderPageAreas(pageViewport.getPage());
			} finally {
				state = null;
			}
			return currentPageImage;
		} finally {
			this.currentPageViewport = null;
		}
	}

	private void drawTest(final Graphics2D graphics, final Shape shape[],
			final BufferedImage Image, final double sX, final double sY) {
		// 测试用户 在1/4高度处

		final double w = shape[1].getBounds2D().getWidth() * sX
				* Math.cos(3.14 / 6);
		final double h = shape[1].getBounds2D().getWidth() * sY
				* Math.sin(3.14 / 6);
		final int height = Image.getHeight();
		final int width = Image.getWidth();

		graphics.rotate(330 * 3.14 / 180, (width - w) / 2 / sX,
				(height / 2 + h) / 2 / sY);
		graphics.setPaint(new Color(255, 0, 0));

		graphics.translate((width - w) / 2 / sX, (height / 2 + h) / 2 / sY);

		for (final Shape element : shape) {
			graphics.draw(element);
		}
	}

	private Shape[] getTextGlyphVector() {
		byte b[];

		// if(SystemUtil.RELEASE_USER) //正式用户使用
		// {
		// return null;
		// }
		b = new byte[12];
		b[0] = -26;
		b[1] = -75;
		b[2] = -117;
		b[3] = -24;
		b[4] = -81;
		b[5] = -107;
		b[6] = -28;
		b[7] = -67;
		b[8] = -65;
		b[9] = -25;
		b[10] = -108;
		b[11] = -88;

		try {
			final String str = new String(b, "UTF-8");
			final java.awt.Font font = new java.awt.Font("宋体",
					java.awt.Font.BOLD, 30);
			final AffineTransform a = new AffineTransform();
			final FontRenderContext context = new FontRenderContext(a, true,
					true);
			final GlyphVector gpv = font.createGlyphVector(context, str);
			final Shape shape[] = new Shape[2];
			shape[0] = gpv.getOutline();
			shape[1] = gpv.getVisualBounds();

			return shape;

		} catch (final UnsupportedEncodingException ex) {
			return null;
		}
	}

	/**
	 * Returns a page viewport.
	 * 
	 * @param pageNum
	 *            the page number
	 * @return the requested PageViewport instance
	 * @exception FOVException
	 *                If the page is out of range.
	 */
	public PageViewport getPageViewport(final int pageNum) throws FOVException {
		if (pageNum < 0 || pageNum >= pageViewportList.size()) {
			throw new FOVException("页码越界");
		}
		return pageViewportList.get(pageNum);
		/*
		 * if (pageNum < 0 || pageNum >= getNumberOfPages()) { throw new
		 * FOVException("页码越界"); } return getPageViewportOf(pageNum);
		 */
	}

	/**
	 * Generates a desired page from the renderer's page viewport list.
	 * 
	 * @param pageNum
	 *            the 0-based page number to generate
	 * @return the <code>java.awt.image.BufferedImage</code> corresponding to
	 *         the page or null if the page doesn't exist.
	 * @throws FOVException
	 *             If there's a problem preparing the page image
	 */
	public BufferedImage getPageImage(final int pageNum) throws FOVException {
		// 处理Group对象，为Group添加界面标识
//		processGroup();
		return getPageImage(getPageViewport(pageNum));
	}

	private void processGroup() {
		final WiseDocDocument doc = (WiseDocDocument) SystemManager
				.getCurruentDocument();
		final List<PageSequence> sequences = getPageSequences(doc);
		for (final PageSequence seq : sequences) {
			addMarker(seq);
		}
	}

	private void addMarker(final CellElement ele) {
		if (ele == null) {
			return;
		}
		if (ele instanceof Group) {
			final Group group = (Group) ele;
			addMarkerForGroup(group, true);
			addMarkerForGroup(group, false);
		} else if (ele instanceof FOText && ((FOText) ele).isWhole())
		{
			//add by zq 由于checkbox只有一个字符，因此不需要加整体标识
			if (!(ele.getParent() instanceof CheckBoxInline))
			{
				addMarkerForWhole(ele, Boolean.TRUE);
				addMarkerForWhole(ele, Boolean.FALSE);
			}
			if (((FOText) ele).isBind())
			{
				addMarkerForBind(ele, Boolean.FALSE);
			}
		}
		final List<CellElement> cells = ele.getChildren(0);
		if (cells == null || cells.isEmpty()) {
			return;
		}
		for (final CellElement cell : cells) {
			addMarker(cell);
		}
	}
	private void addMarkerForWhole(final CellElement element, final boolean isFirst){
		if(!(element instanceof FOText))
			return;
		FOText text  = (FOText)element;
		if(!text.isWhole())
			return;
		addInlineMarker(element, InlineType.Normal, isFirst);
	}
	private void addMarkerForBind(final CellElement element, final boolean isFirst){
		if(!(element instanceof FOText))
			return;
		FOText text  = (FOText)element;
		if(!text.isBind())
			return;
		addInlineMarker(element, InlineType.Bind, isFirst);
	}
	private void addMarkerForGroup(final Group group, final boolean isFirst) {
		final CellElement cell = getCellElement(group, isFirst);
		/*
		 * List<CellElement> cells = group.getAllChildren(); if(isFirst){ cell =
		 * cells.get(0); }else cell = cells.get(cells.size() - 1);
		 */

		if (cell instanceof Block) {
			addBlockMarker(cell, isFirst);
		} else if (cell instanceof Table) {
			addTableMarker(cell, isFirst);
		} else if (cell instanceof BlockContainer) {

		} else {
			addInlineMarker(cell, InlineType.Group, isFirst);
		}

	}

	private CellElement getCellElement(final CellElement group,
			final boolean isFirst) {
		if (group == null) {
			return group;
		}
		if (!(group instanceof Group)) {
			return group;
		}
		final List<CellElement> cells = group.getChildren(0);
		CellElement cell = null;
		if (isFirst) {
			cell = cells.get(0);
		} else {
			cell = cells.get(cells.size() - 1);
		}
		return getCellElement(cell, isFirst);
	}

	private void addTableMarker(final CellElement table, final boolean isFirst) {
		final TableCell cell = getFirstOrLastCell(table, isFirst);
		final List<com.wisii.wisedoc.area.Block> blocks = cell.getBlocks();
		Area area = null;
		if (isFirst) {
			area = blocks.get(0);
		} else {
			area = blocks.get(blocks.size() - 1);
		}
		final LineArea line = (LineArea) LocationConvert
				.getFirstOrLastLineArea(area, isFirst);
		if (line == null) {
			return;
		}
		if (isFirst) {
			line.insertChildArea(new GroupInlineArea(isFirst), 0);
		} else {
			final GroupInlineArea inline = new GroupInlineArea(isFirst);
			line.addChildArea(inline);
		}
	}

	private TableCell getFirstOrLastCell(CellElement cell, final boolean isFirst) {
		if (cell instanceof TableCell) {
			return (TableCell) cell;
		}
		final List<CellElement> cells = cell.getChildren(0);
		if (cells == null || cells.isEmpty()) {
			return null;
		}
		if (isFirst) {
			cell = cells.get(0);
		} else {
			cell = cells.get(cells.size() - 1);
		}
		return getFirstOrLastCell(cell, isFirst);
	}

	private void addBlockMarker(final CellElement block, final boolean isFirst) {
		final com.wisii.wisedoc.area.Block b = (com.wisii.wisedoc.area.Block) block
				.getArea();
		final LineArea line = (LineArea) LocationConvert
				.getFirstOrLastLineArea(b, isFirst);
		if (line == null) {
			return;
		}
		if (isFirst) {
			line.insertChildArea(new GroupInlineArea(isFirst), 0);
		} else {
			line.addChildArea(new GroupInlineArea(isFirst));
		}
	}

	private void addInlineMarker(CellElement inline, GroupInlineArea.InlineType type, final boolean isFirst) {
		final List<CellElement> cells = inline.getChildren(0);
		if(!(inline instanceof FOText))
			inline = cells.get(0);
		
		Area a = null;// ((FOText)inline).getArea(isFirst);
		if (inline instanceof FOText) {
			a = ((FOText) inline).getArea(isFirst);
		} else {
			a = inline.getArea();
		}
		if(a == null)
			return;
		a = a.getParentArea();
		final Area area = a.getParentArea();
		if (!(area instanceof LineArea)) {
			return;
		}
		final LineArea line = (LineArea) area;
		int index = line.getChildAreas().indexOf(a);
		if (!isFirst) {
			index++;
		}
		/*
		 * InlineParent parent = new InlineParent(); parent.addChildArea(new
		 * GroupInlineArea(isFirst));
		 */
		GroupInlineArea ar = new GroupInlineArea(isFirst);
		ar.setType(type);
		line.insertChildArea(ar, index);
	}

	private List<PageSequence> getPageSequences(final WiseDocDocument doc) {
		final List<PageSequence> sequences = new ArrayList<PageSequence>();
		final com.wisii.wisedoc.document.Document document = doc;
		if (isNull(document)) {
			return sequences;
		}
		final int count = document.getChildCount();
		TreeNode node = null;
		for (int i = 0; i < count; i++) {
			node = document.getChildAt(i);
			if (!(node instanceof PageSequence)) {
				continue;
			}
			sequences.add((PageSequence) node);
		}
		return sequences;
	}

	/** Saves the graphics state of the rendering engine. */
	@Override
	protected void saveGraphicsState() {
		// push (and save) the current graphics state
		stateStack.push(state);
		state = new Java2DGraphicsState(state);
	}

	/** Restores the last graphics state of the rendering engine. */
	@Override
	protected void restoreGraphicsState() {
		state.dispose();
		state = (Java2DGraphicsState) stateStack.pop();
	}

	/**
	 * @see com.wisii.wisedoc.render.AbstractRenderer#startVParea(CTM,
	 *      Rectangle2D, Area)
	 */
	@Override
	protected void startVParea(final CTM ctm, final Rectangle2D clippingRect,
			Area area)
	{
		saveGraphicsState();
		CTM nctm = ctm;
		if (clippingRect != null)
		{
			clipRect((float) clippingRect.getX() / 1000f, (float) clippingRect
					.getY() / 1000f, (float) clippingRect.getWidth() / 1000f,
					(float) clippingRect.getHeight() / 1000f);
			// zq添加的溢出时缩放显示功能的实现性代码
			// if (area instanceof BlockViewport)
			// {
			// double height = clippingRect.getHeight();
			// int bpd = area.getAllocBPD();
			// List<Area> children = area.getChildAreas();
			// if (children != null && !children.isEmpty())
			// {
			// int chbpd = 0;
			// for (Area child : children)
			// {
			// chbpd = chbpd + child.getAllocBPD();
			// }
			// double scale = height / chbpd;
			// if (scale < 1)
			// {
			// nctm = ctm.scale(1.0d, scale);
			// }
			// // state.getGraph().scale(1.0d, height / chbpd);
			// }

			// }
			// zq add end
		}
		// Set the given CTM in the graphics state
		// state.setTransform(new AffineTransform(CTMHelper.toPDFArray(ctm)));
		state.transform(new AffineTransform(toPDFArray(nctm)));
	}

	/**
	 * <p>
	 * Creates an array of six doubles from the source CTM.
	 * </p>
	 * <p>
	 * For example:
	 * 
	 * <pre>
	 * com.wisii.wisedoc.area.CTM inCTM = new com.wisii.wisedoc.area.CTM(1.0, 0.0,
	 * 		0.0, 1.0, 1000.0, 1000.0);
	 * double matrix[] = com.wisii.wisedoc.render.pdf.CTMHelper.toPDFArray(ctm);
	 * </pre>
	 * 
	 * will return a new array where matrix[0] == 1.0, matrix[1] == 0.0,
	 * matrix[2] == 0.0, matrix[3] == 1.0, matrix[4] == 1.0 and matrix[5] ==
	 * 1.0.
	 * 
	 * @param sourceMatrix
	 *            - The matrix to convert.
	 * @return an array of doubles containing the converted matrix.
	 */
	// （等同于CTMHelper中的toPDFArray ,为的是把pdfRender 独立出来）
	public static double[] toPDFArray(final CTM sourceMatrix) {
		if (null == sourceMatrix) {
			throw new NullPointerException("sourceMatrix must not be null");
		}

		final double[] matrix = sourceMatrix.toArray();

		return new double[] { matrix[0], matrix[1], matrix[2], matrix[3],
				matrix[4] / 1000.0, matrix[5] / 1000.0 };
	}

	/** @see com.wisii.wisedoc.render.AbstractRenderer#endVParea() */
	@Override
	protected void endVParea() {
		restoreGraphicsState();
	}

	/** @see com.wisii.wisedoc.render.AbstractPathOrientedRenderer#breakOutOfStateStack() */
	@Override
	protected List breakOutOfStateStack() {
		LogUtil.debug("Block.FIXED --> break out");
		List breakOutList;
		breakOutList = new java.util.ArrayList();
		while (!stateStack.isEmpty()) {
			breakOutList.add(0, state);
			// We only pop, we don't dispose, because we can use the instances
			// again later
			state = (Java2DGraphicsState) stateStack.pop();
		}
		return breakOutList;
	}

	/** @see com.wisii.wisedoc.render.AbstractPathOrientedRenderer#restoreStateStackAfterBreakOut(java.util.List) */
	@Override
	protected void restoreStateStackAfterBreakOut(final List breakOutList) {
		LogUtil.debug("Block.FIXED --> restoring context after break-out");

		final Iterator i = breakOutList.iterator();
		while (i.hasNext()) {
			final Java2DGraphicsState s = (Java2DGraphicsState) i.next();
			stateStack.push(state);
			state = s;
		}
	}

	/**
	 * @see com.wisii.wisedoc.render.AbstractPathOrientedRenderer#updateColor(com.wisii.wisedoc.datatypes.ColorType,
	 *      boolean)
	 */
	@Override
	protected void updateColor(final Color col, final boolean fill) {
		state.updateColor(col);
	}

	/** @see com.wisii.wisedoc.render.AbstractPathOrientedRenderer#clip() */
	@Override
	protected void clip() {
		if (currentPath == null) {
			throw new IllegalStateException("当前路径不对!");
		}
		state.updateClip(currentPath);
		currentPath = null;
	}

	/** @see com.wisii.wisedoc.render.AbstractPathOrientedRenderer#closePath() */
	@Override
	protected void closePath() {
		currentPath.closePath();
	}

	/**
	 * @see com.wisii.wisedoc.render.AbstractPathOrientedRenderer#lineTo(float,
	 *      float)
	 */
	@Override
	protected void lineTo(final float x, final float y) {
		if (currentPath == null) {
			currentPath = new GeneralPath();
		}
		currentPath.lineTo(x, y);
	}

	/**
	 * @see com.wisii.wisedoc.render.AbstractPathOrientedRenderer#moveTo(float,
	 *      float)
	 */
	@Override
	protected void moveTo(final float x, final float y) {
		if (currentPath == null) {
			currentPath = new GeneralPath();
		}
		currentPath.moveTo(x, y);
	}

	/**
	 * @see com.wisii.wisedoc.render.AbstractPathOrientedRenderer#clipRect(float,
	 *      float, float, float)
	 */
	@Override
	protected void clipRect(final float x, final float y, final float width,
			final float height) {
		state.updateClip(new Rectangle2D.Float(x, y, width, height));
	}

	/**
	 * @see com.wisii.wisedoc.render.AbstractPathOrientedRenderer#fillRect(float,
	 *      float, float, float)
	 */
	@Override
	protected void fillRect(final float x, final float y, final float width,
			final float height) {
		if (isAvailabilityLayer(state.getColor(), userAgent.getLayers())) {
			state.getGraph().fill(new Rectangle2D.Float(x, y, width, height));
		}

		// Graphics2D g2d = (Graphics2D)state.getGraph();
		// Composite alp = g2d.getComposite();
		// AlphaComposite comp =
		// AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5F);
		// /*Color c = new Color(143, 136, 136, 200);
		// g2d.setColor(c);*/
		// g2d.setComposite(comp);
		// g2d.fill(new Rectangle2D.Float(x, y, width, height));
		// /*g2d.fill(new Rectangle2D.Float(x + 10, y + 10, width - 30, height -
		// 30));*/
		// g2d.setComposite(alp);
	}

	/**
	 * @see com.wisii.wisedoc.render.AbstractPathOrientedRenderer#drawBorderLine(float,
	 *      float, float, float, boolean, boolean,
	 *      int,com.wisii.wisedoc.datatypes.ColorType)
	 */
	@Override
	protected void drawBorderLine(final float x1, final float y1,
			final float x2, final float y2, final boolean horz,
			final boolean startOrBefore, final int style, final Color col) {
		final Graphics2D g2d = state.getGraph();
		drawBorderLine(new Rectangle2D.Float(x1, y1, x2 - x1, y2 - y1), horz,
				startOrBefore, style, col, g2d, this.userAgent);
	}

	private void drawBorderLine(final Rectangle2D.Float lineRect,
			final boolean horz, final boolean startOrBefore, final int style,
			final Color col, final Graphics2D g2d, final FOUserAgent userAgent) {
		/* 【添加：START】 by 李晓光 2009-1-19 */
		if (style!=Constants.EN_NOBORDER&&!isAvailabilityLayer(col, userAgent.getLayers())) {
			return;
		}
		/* 【添加：END】 by 李晓光 2009-1-19 */
		drawBorderLine(lineRect, horz, startOrBefore, style, col, g2d);
	}

	/**
	 * Draw a border segment of an XSL-FO style border.
	 * 
	 * @param lineRect
	 *            the line defined by its bounding rectangle
	 * @param horz
	 *            true for horizontal border segments, false for vertical border
	 *            segments
	 * @param startOrBefore
	 *            true for border segments on the start or before edge, false
	 *            for end or after.
	 * @param style
	 *            the border style (one of Constants.EN_DASHED etc.)
	 * @param col
	 *            the color for the border segment
	 * @param g2d
	 *            the Graphics2D instance to paint to
	 */
	public static void drawBorderLine(final Rectangle2D.Float lineRect,
			final boolean horz, final boolean startOrBefore, final int style,
			Color col, final Graphics2D g2d) {

		// add by zq : reset the Clip,
		// or not the line to be drawed will be missed
		final Shape oldclip = g2d.getClip();
		g2d.setClip(lineRect);
		// add end
		final float x1 = lineRect.x;
		final float y1 = lineRect.y;
		final float x2 = x1 + lineRect.width;
		final float y2 = y1 + lineRect.height;
		final float w = lineRect.width;
		final float h = lineRect.height;
		if ((w < 0) || (h < 0)) {
			LogUtil.error("Negative extent received. Border won't be painted.");
			return;
		}
		switch (style) {
		//边框为空时，显示单元格，BlockContainer的格线的功能
		case Constants.EN_NOBORDER:
			if(!ConfigureUtil.isDrawnullborder())
			{
				return;
			}
			g2d.setClip(new Rectangle2D.Float(x1-2,y1-2,w+5,h+5));
			g2d.setColor(new Color(0,125,255,200));
			if (horz) {
				float unit = Math.abs(2 * h);
				int rep = (int) (w / unit);
				if (rep % 2 == 0) {
					rep++;
				}
				unit = w / rep;
				final float ym = y1 + (h / 2);
				final BasicStroke s = new BasicStroke(0.5f, BasicStroke.CAP_BUTT,
						BasicStroke.JOIN_MITER, 10.0f, new float[] { 2f,1f }, 0);
				g2d.setStroke(s);
				g2d.draw(new Line2D.Float(x1, ym, x2, ym));
			} else {
				float unit = Math.abs(2 * w);
				int rep = (int) (h / unit);
				if (rep % 2 == 0) {
					rep++;
				}
				unit = h / rep;
				final float xm = x1 + (w / 2);
				final BasicStroke s = new BasicStroke(0.5f, BasicStroke.CAP_BUTT,
						BasicStroke.JOIN_MITER, 10.0f, new float[] { 2f,1f}, 0);
				g2d.setStroke(s);
				g2d.draw(new Line2D.Float(xm, y1, xm, y2));
			}
			break;
		case Constants.EN_DASHED:
			g2d.setColor(col);
			if (horz) {
				float unit = Math.abs(2 * h);
				int rep = (int) (w / unit);
				if (rep % 2 == 0) {
					rep++;
				}
				unit = w / rep;
				final float ym = y1 + (h / 2);
				final BasicStroke s = new BasicStroke(h, BasicStroke.CAP_BUTT,
						BasicStroke.JOIN_MITER, 10.0f, new float[] { unit }, 0);
				g2d.setStroke(s);
				g2d.draw(new Line2D.Float(x1, ym, x2, ym));
			} else {
				float unit = Math.abs(2 * w);
				int rep = (int) (h / unit);
				if (rep % 2 == 0) {
					rep++;
				}
				unit = h / rep;
				final float xm = x1 + (w / 2);
				final BasicStroke s = new BasicStroke(w, BasicStroke.CAP_BUTT,
						BasicStroke.JOIN_MITER, 10.0f, new float[] { unit }, 0);
				g2d.setStroke(s);
				g2d.draw(new Line2D.Float(xm, y1, xm, y2));
			}
			break;
		case Constants.EN_DOTTED:
			g2d.setColor(col);
			if (horz) {
				float unit = Math.abs(2 * h);
				int rep = (int) (w / unit);
				if (rep % 2 == 0) {
					rep++;
				}
				unit = w / rep;
				final float ym = y1 + (h / 2);
				final BasicStroke s = new BasicStroke(h, BasicStroke.CAP_ROUND,
						BasicStroke.JOIN_MITER, 10.0f, new float[] { 0, unit },
						0);
				g2d.setStroke(s);
				g2d.draw(new Line2D.Float(x1, ym, x2, ym));
			} else {
				float unit = Math.abs(2 * w);
				int rep = (int) (h / unit);
				if (rep % 2 == 0) {
					rep++;
				}
				unit = h / rep;
				final float xm = x1 + (w / 2);
				final BasicStroke s = new BasicStroke(w, BasicStroke.CAP_ROUND,
						BasicStroke.JOIN_MITER, 10.0f, new float[] { 0, unit },
						0);
				g2d.setStroke(s);
				g2d.draw(new Line2D.Float(xm, y1, xm, y2));
			}
			break;
		case Constants.EN_DOUBLE:
			g2d.setColor(col);
			if (horz) {
				final float h3 = h / 3;
				final float ym1 = y1 + (h3 / 2);
				final float ym2 = ym1 + h3 + h3;
				final BasicStroke s = new BasicStroke(h3);
				g2d.setStroke(s);
				g2d.draw(new Line2D.Float(x1, ym1, x2, ym1));
				g2d.draw(new Line2D.Float(x1, ym2, x2, ym2));
			} else {
				final float w3 = w / 3;
				final float xm1 = x1 + (w3 / 2);
				final float xm2 = xm1 + w3 + w3;
				final BasicStroke s = new BasicStroke(w3);
				g2d.setStroke(s);
				g2d.draw(new Line2D.Float(xm1, y1, xm1, y2));
				g2d.draw(new Line2D.Float(xm2, y1, xm2, y2));
			}
			break;
		case Constants.EN_GROOVE:
		case Constants.EN_RIDGE:
			float colFactor = (style == EN_GROOVE ? 0.4f : -0.4f);
			if (horz) {
				final Color uppercol = lightenColor(col, -colFactor);
				final Color lowercol = lightenColor(col, colFactor);
				final float h3 = h / 3;
				final float ym1 = y1 + (h3 / 2);
				g2d.setStroke(new BasicStroke(h3));
				g2d.setColor(uppercol);
				g2d.draw(new Line2D.Float(x1, ym1, x2, ym1));
				g2d.setColor(col);
				g2d.draw(new Line2D.Float(x1, ym1 + h3, x2, ym1 + h3));
				g2d.setColor(lowercol);
				g2d
						.draw(new Line2D.Float(x1, ym1 + h3 + h3, x2, ym1 + h3
								+ h3));
			} else {
				final Color leftcol = lightenColor(col, -colFactor);
				final Color rightcol = lightenColor(col, colFactor);
				final float w3 = w / 3;
				final float xm1 = x1 + (w3 / 2);
				g2d.setStroke(new BasicStroke(w3));
				g2d.setColor(leftcol);
				g2d.draw(new Line2D.Float(xm1, y1, xm1, y2));
				g2d.setColor(col);
				g2d.draw(new Line2D.Float(xm1 + w3, y1, xm1 + w3, y2));
				g2d.setColor(rightcol);
				g2d
						.draw(new Line2D.Float(xm1 + w3 + w3, y1,
								xm1 + w3 + w3, y2));
			}
			break;
		case Constants.EN_INSET:
		case Constants.EN_OUTSET:
			colFactor = (style == EN_OUTSET ? 0.4f : -0.4f);
			if (horz) {
				col = lightenColor(col, (startOrBefore ? 1 : -1) * colFactor);
				g2d.setStroke(new BasicStroke(h));
				final float ym1 = y1 + (h / 2);
				g2d.setColor(col);
				g2d.draw(new Line2D.Float(x1, ym1, x2, ym1));
			} else {
				col = lightenColor(col, (startOrBefore ? 1 : -1) * colFactor);
				final float xm1 = x1 + (w / 2);
				g2d.setStroke(new BasicStroke(w));
				g2d.setColor(col);
				g2d.draw(new Line2D.Float(xm1, y1, xm1, y2));
			}
			break;
		case Constants.EN_HIDDEN:
			break;
		default:
			g2d.setColor(col);
			if (horz) {
				final float ym = y1 + (h / 2);
				g2d.setStroke(new BasicStroke(h));
				g2d.draw(new Line2D.Float(x1, ym, x2, ym));
			} else {
				final float xm = x1 + (w / 2);
				g2d.setStroke(new BasicStroke(w));
				g2d.draw(new Line2D.Float(xm, y1, xm, y2));
			}
		}
		// add by zq : set back the Clip
		g2d.setClip(oldclip);
		// add end
	}

	/* 【添加：START】 by 李晓光 2009-3-11 */

	/*
	 * @see
	 * com.wisii.wisedoc.render.AbstractRenderer#renderGroupInlineArea(com.wisii
	 * .wisedoc.area.inline.GroupInlineArea)
	 */
	@Override
	protected void renderGroupInlineArea(final GroupInlineArea area) {
		state.updatePaint(area.getPaint());
		saveGraphicsState();
		final Graphics2D g2d = state.getGraph();
		float x = currentIPPosition / Constants.PRECISION;
		float y = currentBPPosition / Constants.PRECISION;
		GeneralPath path = new GeneralPath();
		if (area.getPoint() != null) {
			x = (float) area.getPoint().getX();
			y = (float) area.getPoint().getY();
		}
		InlineType type = area.getType();
		if (area.isStart()) {
			y -= 1;
			switch (type) {
			case Normal:
				path = createWholeMarker(area, g2d);
				break;
			case Group:
				path = createStartMarker(x, y);
				break;
			case Bind:				
			default:
				break;
			}
		} else {
			final LineArea line = (LineArea) area.getParentArea();
			final float size = line.getBPD() / Constants.PRECISION;
			y += size;
			switch (type) {
			case Normal:
				path = createWholeMarker(area, g2d);
				break;
			case Group:
				path = createEndMarker(x, y);
				break;
			case Bind:
				path = createBindMarker(area, g2d);				
				break;
			default:
				break;
			}
		}
		
		/*
		 * Stroke stroke = new BasicStroke(1.0F); g2d.setStroke(stroke);
		 * g2d.drawString("2", x, y);
		 */
		g2d.draw(path);
		restoreGraphicsState();
	}
	@SuppressWarnings("unchecked")
	private GeneralPath createWholeMarker(final GroupInlineArea area, Graphics2D g2d){
		final GeneralPath path = new GeneralPath();
		final LineArea line = (LineArea) area.getParentArea();
		List<Area> areas = line.getChildAreas();
		int index = areas.indexOf(area);
		if(area.isStart()){
			index++;
		}else{
			index--;
		}
		
		TextArea text = getTextArea(line, index, area.isStart());
		if(text == null)
			return path;
		
		float x = currentIPPosition + text.getBorderAndPaddingWidthStart();
		float y = currentBPPosition + text.getOffset();
		InlineArea inline = (InlineArea)text.getParentArea();
		y += inline.getOffset();
		float h = text.getBPD();
		x /= Constants.PRECISION;
		y /= Constants.PRECISION;
		h /= Constants.PRECISION;
		final Font font = getFontFromArea(text);
		state.updateFont(font.getFontName(), font.getFontSize());
		double offsetY = g2d.getFontMetrics().getLeading() * scaleX;
		y += offsetY / 2;
		h -= offsetY;
		double scale =scaleX * font.getFontSize() / 12000F;
		double w = 2.25 * scale;//3;
		if(area.isStart()){
			path.moveTo(w, 0);
			path.lineTo(0, 0);
			path.lineTo(0, h);
			path.lineTo(0 + w, h);
		}else{
			path.moveTo(-w, 0);
			path.lineTo(0, 0);
			path.lineTo(0, h);
			path.lineTo(- w, h);
		}
		
		AffineTransform at = AffineTransform.getTranslateInstance(x, y);
		
		path.transform(at);
		
		Color col = (Color) text.getTrait(Trait.COLOR);
		col = new Color(col.getRed(),col.getGreen(),col.getBlue(),col.getAlpha()/6);
		state.updatePaint(col);
		return path;
	}
	private TextArea getTextArea(Area area, int index, boolean isFirst){
		List<Area> areas = area.getChildAreas();
		if(areas == null || areas.isEmpty())
			return null;
		int length = isFirst ? areas.size() : 0;
		for (int i = index; isFirst ? i < length : i >= length; ) {
			area = areas.get(i);
			if(area instanceof GroupInlineArea){
				if(isFirst)
					i++;
				else
					i--;
				continue;
			}
			return (TextArea)area.getChildAreas().get(0);
		}
		return null;
	}
	private GeneralPath createBindMarker(final GroupInlineArea area, Graphics2D g2d){
		final GeneralPath path = new GeneralPath();
		
		final LineArea line = (LineArea) area.getParentArea();
		List<Area> areas = line.getChildAreas();
		int index = areas.indexOf(area);
		if(area.isStart()){
			index++;
		}else{
			index--;
		}
		
		TextArea text = getTextArea(line, index, area.isStart());
		if(text == null)
			return path;
		
		float x = currentIPPosition + text.getBorderAndPaddingWidthStart();
		float y = currentBPPosition + text.getOffset();
		InlineArea inline = (InlineArea)text.getParentArea();
		y += inline.getOffset();
		float h = text.getAllocBPD();
		x /= Constants.PRECISION;
		y /= Constants.PRECISION;
		h /= Constants.PRECISION;
		y += h;
		
		/*double move = 3;
		float length = 2.5F;*/
		
		/*path.moveTo(-length, 0);
		path.lineTo(length, 0);
		path.moveTo(0, -length);
		path.lineTo(0, length);*/
		
		/*AffineTransform at = AffineTransform.getTranslateInstance(x - move, y - move);
		path.transform(at);*/
		
		final Font font = getFontFromArea(text);
		state.updateFont(font.getFontName(), font.getFontSize());
		//不取Area的实际字体，而取默认字体，是为了处理有些字体显示不了+号的问题，如winding系列字体等
		java.awt.Font g2dfont = g2d.getFont();
		g2dfont = new java.awt.Font((String)InitialManager.getInitialValue(Constants.PR_FONT_FAMILY, null),g2dfont.getStyle(),g2dfont.getSize());
		g2d.setFont(g2dfont);
		int w = g2d.getFontMetrics().charWidth('+');
		int offsetY = g2d.getFont().getSize() / 4;
		
		Color col = (Color) text.getTrait(Trait.COLOR);
		col = new Color(col.getRed(),col.getGreen(),col.getBlue(),col.getAlpha()/6);
		state.updatePaint(col);
		g2d.drawString("+", x - w, y + offsetY);
		
		return path;
	}
	/* 创建表示组起始位置的标识符 */
	private GeneralPath createStartMarker(final double x, final double y) {
		final GeneralPath path = new GeneralPath();
		final int length = 10;
		path.moveTo(x, y + length);
		path.lineTo(x, y);
		path.lineTo(x + length, y);

		/*
		 * Ellipse2D ellipse = new Ellipse2D.Double(x - 8, y - 6, 16, 12);
		 * path.append(ellipse, false);
		 */
		return path;
	}

	/* 创建表示组结束位置的标识符 */
	private GeneralPath createEndMarker(final double x, final double y) {
		final GeneralPath path = new GeneralPath();
		final int length = 10;
		path.moveTo(x, y - length);
		path.lineTo(x, y);
		path.lineTo(x - length, y);
		/*
		 * Ellipse2D ellipse = new Ellipse2D.Double(x -8, y - 6, 16, 12);
		 * path.append(ellipse, false);
		 */
		return path;
	}

	/* 【添加：END】 by 李晓光 2009-3-11 */
	/** @see com.wisii.wisedoc.render.AbstractRenderer#renderText(TextArea) */
	@Override
	public void renderText(final TextArea text) {
		// if (text.getHideName() != null && !"".equals(text.getHideName()))
		// {
		// PreviewPanel.addAreaToHideList(text);
		// return;
		// }
		renderInlineAreaBackAndBorders(text);

		final int rx = currentIPPosition + text.getBorderAndPaddingWidthStart();
		final int bl = currentBPPosition + text.getOffset()
				+ text.getBaselineOffset();
		final int saveIP = currentIPPosition;
		final Font font = getFontFromArea(text);
		state.updateFont(font.getFontName(), font.getFontSize());
		saveGraphicsState();
		final AffineTransform at = new AffineTransform();
		at.translate(rx / 1000f, bl / 1000f);
		// _saveAT = state.getGraph().getTransform(); // add by huangzl.保存呈现状态
		state.transform(at);

		renderText(text, state.getGraph(), font, rx, bl, this.userAgent);
		restoreGraphicsState();

		currentIPPosition = saveIP + text.getAllocIPD();
		// super.renderText(text);

		// rendering text decorations
		final Typeface tf = (Typeface) fontInfo.getFonts().get(
				font.getFontName());
		final int fontsize = text.getTraitAsInteger(Trait.FONT_SIZE);
		renderTextDecoration(tf, fontsize, text, bl, rx);
	}

	/* (non-Javadoc)
    *绘制空行的换行符
	* @see com.wisii.wisedoc.render.AbstractRenderer#drawLineBreak()
	*/
	@Override
	protected void drawLineBreak(Area area)
	{
		if(!ConfigureUtil.isDrawlinebreak())
		{
			return;
		}
		saveGraphicsState();
		final Graphics2D g2d = state.getGraph();
		g2d.setColor(Color.gray);
		Rectangle2D bounds = area.getViewport();
		double[] offset = getOffsetForContainer(area);
		double x = (bounds.getX() + offset[0] + 2000) / 1000;
		double y = (bounds.getY() + offset[1] + 2000) / 1000;
		java.awt.geom.Path2D.Double jiantou = new Path2D.Double();
		jiantou.moveTo(x + 2, y + 2);
		jiantou.lineTo(x, y + 4);
		jiantou.lineTo(x + 2, y + 6);
		jiantou.lineTo(x + 2, y + 2);
		g2d.fill(jiantou);
		java.awt.geom.Path2D.Double line = new Path2D.Double();
		line.moveTo(x + 2, y + 4);
		line.lineTo(x + 3, y + 4);
		line.curveTo(x + 3, y + 4, x + 4, y + 4, x + 4, y + 3);
		line.lineTo(x + 4, y+1);
		g2d.draw(line);
		restoreGraphicsState();
	}

	public static double[] getOffsetForContainer(final Area area)
	{
		double offsetX = 0;
		double offsetY = 0;
		if (isNull(area))
		{
			return new double[]
			{ offsetX, offsetY };
		}

		if (area instanceof BlockViewport
				&& !Boolean.TRUE.equals(area.getTrait(Trait.IS_VIEWPORT_AREA)))
		{
			final int left = area.getBorderAndPaddingWidthStart();
			final int top = area.getBorderAndPaddingWidthBefore();
			offsetX += left;
			offsetY += top;
		}
		final double[] offset = getOffsetForContainer(area.getParentArea());
		offsetX += offset[0];
		offsetY += offset[1];

		return new double[]
		{ offsetX, offsetY };
}
	/**
	 * Renders a TextArea to a Graphics2D instance. Adjust the coordinate system
	 * so that the start of the baseline of the first character is at coordinate
	 * (0,0).
	 * 
	 * @param text
	 *            the TextArea
	 * @param g2d
	 *            the Graphics2D to render to
	 * @param font
	 *            the font to paint with
	 */
	public static void renderText(final TextArea text, final Graphics2D g2d,
			final Font font, final int rx, final int bl,
			final FOUserAgent userAgent) {
		final Color col = (Color) text.getTrait(Trait.COLOR);
		g2d.setColor(col);
		if (!isAvailabilityLayer(col, userAgent.getLayers())) {
			return;
		}

		float textCursor = 0;

		// addby 许浩 声明两个需要用到的变量，设置字体给当前绘制的TextArea
		double w = 0;
		double h = 0;
		text.setFont(g2d.getFont());
		// add end

		// add by huangzl.处理fo:block-container中，overflow="hidden"的情况。
		double clipMaxX = 1.0;
		double clipMaxY = 1.0;
		if (g2d.getClip() != null) {
			// Rectangle2D.Float shapeTemp = (Float)
			// g2d.getClip().getBounds2D();
			final Rectangle2D shapeTemp = g2d.getClip().getBounds2D();
			clipMaxX = shapeTemp.getX() + shapeTemp.getWidth();
			clipMaxY = shapeTemp.getY() + shapeTemp.getHeight();
		}
		// add end.

		final Iterator iter = text.getChildAreas().iterator();
		while (iter.hasNext()) {
			final InlineArea child = (InlineArea) iter.next();
			if (child instanceof WordArea) {
				final WordArea word = (WordArea) child;
				final String s = word.getWord();
				final int[] letterAdjust = word.getLetterAdjustArray();
				final GlyphVector gv = g2d.getFont().createGlyphVector(
						g2d.getFontRenderContext(), s);
				double additionalWidth = 0.0;
				if (letterAdjust == null
						&& text.getTextLetterSpaceAdjust() == 0
						&& text.getTextWordSpaceAdjust() == 0) {
					// nop
				} else {
					final int[] offsets = getGlyphOffsets(s, font, text,
							letterAdjust);
					float cursor = 0.0f;
					// -------------------by李晓光2009-1-20处理匀满------------
					/*
					 * double adjust = text.getAdjust(); int length =
					 * LocationConvert.calcOffsetForCurrentLine(text); adjust =
					 * (length adjust) / (length - 1);
					 */
					// -------------------by李晓光2009-1-20处理匀满------------
					for (int i = 0; i < offsets.length; i++) {
						final Point2D pt = gv.getGlyphPosition(i);
						pt.setLocation(cursor, pt.getY());
						gv.setGlyphPosition(i, pt);
						cursor += offsets[i] / 1000f;
					}
					additionalWidth = cursor - gv.getLogicalBounds().getWidth();
				}
				g2d.drawGlyphVector(gv, textCursor, 0);

				// addby 许浩 计算文本的宽度与高度
				w += gv.getLogicalBounds().getWidth();
				if (Math.abs(h) < Math.abs(gv.getLogicalBounds().getY())) {
					h = gv.getLogicalBounds().getY();
					// add end
				}

				textCursor += gv.getLogicalBounds().getWidth()
						+ additionalWidth;
			} else if (child instanceof SpaceArea) {
				final SpaceArea space = (SpaceArea) child;
				final String s = space.getSpace();
				final char sp = s.charAt(0);
				final int tws = (space.isAdjustable() ? text
						.getTextWordSpaceAdjust()
						+ 2 * text.getTextLetterSpaceAdjust() : 0);
				// addby 许浩 计算空格的宽度
				w += (font.getCharWidth(sp) + tws) / 1000f;
				// add end
				textCursor += (font.getCharWidth(sp) + tws) / 1000f;
			} else {
				throw new IllegalStateException("不支持子元素: " + child);
			}
		}

		// add by huangzl.处理fo:block-container中，overflow="hidden"的情况。
		if (clipMaxX < 0
				|| (clipMaxY < 0 && Math.abs(clipMaxY) > Math.abs(h) / 2)) {
			return;
		}
	}

	/**
	 * Renders a TextArea to a Graphics2D instance. Adjust the coordinate system
	 * so that the start of the baseline of the first character is at coordinate
	 * (0,0).
	 * 
	 * @param text
	 *            the TextArea
	 * @param g2d
	 *            the Graphics2D to render to
	 * @param font
	 *            the font to paint with
	 */
	public static void renderText(final TextArea text, final Graphics2D g2d,
			final Font font) {

		final Color col = (Color) text.getTrait(Trait.COLOR);
		g2d.setColor(col);

		float textCursor = 0;

		final Iterator iter = text.getChildAreas().iterator();
		while (iter.hasNext()) {
			final InlineArea child = (InlineArea) iter.next();
			if (child instanceof WordArea) {
				final WordArea word = (WordArea) child;
				final String s = word.getWord();
				final int[] letterAdjust = word.getLetterAdjustArray();
				final GlyphVector gv = g2d.getFont().createGlyphVector(
						g2d.getFontRenderContext(), s);
				double additionalWidth = 0.0;
				if (letterAdjust == null
						&& text.getTextLetterSpaceAdjust() == 0
						&& text.getTextWordSpaceAdjust() == 0) {
					// nop
				} else {
					final int[] offsets = getGlyphOffsets(s, font, text,
							letterAdjust);
					float cursor = 0.0f;
					for (int i = 0; i < offsets.length; i++) {
						final Point2D pt = gv.getGlyphPosition(i);
						pt.setLocation(cursor, pt.getY());
						gv.setGlyphPosition(i, pt);
						cursor += offsets[i] / 1000f;
					}
					additionalWidth = cursor - gv.getLogicalBounds().getWidth();
				}
				g2d.drawGlyphVector(gv, textCursor, 0);
				textCursor += gv.getLogicalBounds().getWidth()
						+ additionalWidth;
			} else if (child instanceof SpaceArea) {
				final SpaceArea space = (SpaceArea) child;
				final String s = space.getSpace();
				final char sp = s.charAt(0);
				final int tws = (space.isAdjustable() ? text
						.getTextWordSpaceAdjust()
						+ 2 * text.getTextLetterSpaceAdjust() : 0);

				textCursor += (font.getCharWidth(sp) + tws) / 1000f;
			} else {
				throw new IllegalStateException("Unsupported child element: "
						+ child);
			}
		}
	}

	private static int[] getGlyphOffsets(final String s, final Font font,
			final TextArea text, final int[] letterAdjust) {
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
			final int tls = (i < textLen - 1 ? text.getTextLetterSpaceAdjust()
					: 0);
			// add by 李晓光 2008-12-29 处理匀满
			/*
			 * int adjust = 0;//(i < textLen - 1 ? text.getTextWordSpaceAdjust()
			 * : 0);; if(text.getAlignment() == EN_JUSTIFY){ adjust =
			 * text.getTextWordSpaceAdjust(); }
			 */

			offsets[i] = cw + ladj + tls + wordSpace /* + adjust */;
		}
		return offsets;
	}

	/**
	 * Render leader area. This renders a leader area which is an area with a
	 * rule.
	 * 
	 * @param area
	 *            the leader area to render
	 */
	@Override
	public void renderLeader(final Leader area) {
		renderInlineAreaBackAndBorders(area);

		// TODO leader-length: 25%, 50%, 75%, 100% not working yet
		// TODO Colors do not work on Leaders yet

		final float startx = (currentIPPosition + area
				.getBorderAndPaddingWidthStart()) / 1000f;
		final float starty = ((currentBPPosition + area.getOffset()) / 1000f);
		final float endx = (currentIPPosition
				+ area.getBorderAndPaddingWidthStart() + area.getIPD()) / 1000f;

		final Color col = (Color) area.getTrait(Trait.COLOR);
		state.updateColor(col);

		final Line2D line = new Line2D.Float();
		line.setLine(startx, starty, endx, starty);
		final float ruleThickness = area.getRuleThickness() / 1000f;

		final int style = area.getRuleStyle();
		switch (style) {
		case EN_SOLID:
		case EN_DASHED:
		case EN_DOUBLE:
			drawBorderLine(startx, starty, endx, starty + ruleThickness, true,
					true, style, col);
			break;
		case EN_DOTTED:
			// TODO Dots should be shifted to the left by ruleThickness / 2
			state.updateStroke(ruleThickness, style);
			final float rt2 = ruleThickness / 2f;
			line.setLine(line.getX1(), line.getY1() + rt2, line.getX2(), line
					.getY2()
					+ rt2);
			state.getGraph().draw(line);
			break;
		case EN_GROOVE:
		case EN_RIDGE:
			final float half = area.getRuleThickness() / 2000f;
			state.updateColor(lightenColor(col, 0.6f));
			moveTo(startx, starty);
			lineTo(endx, starty);
			lineTo(endx, starty + 2 * half);
			lineTo(startx, starty + 2 * half);
			closePath();
			state.getGraph().fill(currentPath);
			currentPath = null;
			state.updateColor(col);
			if (style == EN_GROOVE) {
				moveTo(startx, starty);
				lineTo(endx, starty);
				lineTo(endx, starty + half);
				lineTo(startx + half, starty + half);
				lineTo(startx, starty + 2 * half);
			} else {
				moveTo(endx, starty);
				lineTo(endx, starty + 2 * half);
				lineTo(startx, starty + 2 * half);
				lineTo(startx, starty + half);
				lineTo(endx - half, starty + half);
			}
			closePath();
			state.getGraph().fill(currentPath);
			currentPath = null;

		case EN_NONE:
			// No rule is drawn
			break;
		default:
		} // end switch
		super.renderLeader(area);
	}

	/**
	 * add by lzy 过滤图片 imageSrc 需要过滤的image
	 * */
	@Override
	public java.awt.Image FilterImage(final java.awt.Image imageSrc,
			final int aphla) {
		java.awt.Image Filteredimage = null;

		final Color BACKCOLOR = new Color(255, 255, 255, 0); // 背景色
		final Color FLGCOLOR = new Color(0, 0, 255, 255); // 前景色
		final int FlgAphla = aphla; // 转换后前景色的透明度。
		final int BACKAPLHA = 0; // 转换后背景色的透明度。

		final FovImageFilter d = new FovImageFilter(BACKCOLOR, BACKCOLOR,
				FlgAphla);// 过滤器

		Filteredimage = Toolkit.getDefaultToolkit().createImage(
				new FilteredImageSource(imageSrc.getSource(), d));

		// 等待加栽图片完成
		MediaTracker mt = null;

		/*
		 * if (PreviewDialog.previewSelf != null) { mt = new
		 * MediaTracker(PreviewDialog.previewSelf); } else if
		 * (PrintDialog.printDialogself != null) { mt = new
		 * MediaTracker(PrintDialog.printDialogself); } else
		 */
		final Component comp = SystemManager.getMainframe();
		if (comp != null) {
			mt = new MediaTracker(comp);
		}

		mt.addImage(Filteredimage, 1);
		try {
			mt.waitForID(1);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return Filteredimage;
	}

	/**
	 * @see com.wisii.wisedoc.render.AbstractRenderer#renderImage(Image,
	 *      Rectangle2D)
	 */
	@Override
	public void renderImage(final Image image, final Rectangle2D pos)
	{
		// endTextObject();
		// add by zq 增加了barcode的显示
		// if(image instanceof BarCodeArea)
		// {
		// drawBarCode((BarCodeArea)image,pos);
		// }
		// else{
		java.awt.Image drawImage = null;
		final int aphla = image.getAphla();
		boolean isqianzhang = false;
		if (image instanceof BarCodeArea)
		{
			drawImage = ((BarCodeArea) image).getBarCode().getImage();
		} else if (image instanceof ChartArea)
		{
			drawImage = ((ChartArea) image).getChart().getImage();
		} else if (image instanceof QianZhangArea)
		{
			isqianzhang = true;
			drawImage = ((QianZhangArea) image).getQianZhang().getImage();
		} else
		// 原Fo规范
		{
			final String url = image.getURL();
			drawImage = drawImage(url, pos);
		}

		if (drawImage == null)
		{
			return;
		}

		final int x = currentIPPosition + (int) Math.round(pos.getX());
		final int y = currentBPPosition + (int) Math.round(pos.getY());

		// 如果需要过滤
		if (aphla > 0 && aphla < 255)
		{

			// 调用我们的图片绘制策略，过滤掉背景色/
			drawImage = FilterImage(drawImage, aphla);

			// state.getGraph().drawImage(filterimage, (int)(x / 1000f), (int)(y
			// / 1000f),
			// (int)(pos.getWidth() / 1000f), (int)(pos.getHeight() / 1000f),
			// null);
		}
		if (isqianzhang)
		{
			int width = drawImage.getWidth(null);
			int height = drawImage.getHeight(null);
			Rectangle2D bounds = new Rectangle2D.Double(x, y, width * 1000,
					height * 1000);
			saveGraphicsState();
			Rectangle2D clip = new Rectangle2D.Double(x / 1000, y / 1000,
					width, height);
			this.getCurrentPageViewport().addQianZhangArea((QianZhangArea)image);
			((QianZhangArea)image).setViewport(clip);
			state.getGraph().setClip(clip);
			drawImage(image, drawImage, bounds);
			restoreGraphicsState();
		} else
		{
			drawImage(image, drawImage, new Rectangle2D.Double(x, y, pos
					.getWidth(), pos.getHeight()));
		}
	}

	/**
	 * @see com.wisii.wisedoc.render.AbstractPathOrientedRenderer#drawImage(java.lang.String,
	 *      java.awt.geom.Rectangle2D, java.util.Map)
	 */
	@Override
	protected java.awt.Image drawImage(String url, final Rectangle2D pos,
			final Map foreignAttributes)
	{
		java.awt.Image awtImage = null;
		final int x = currentIPPosition + (int) Math.round(pos.getX());
		final int y = currentBPPosition + (int) Math.round(pos.getY());

		// System.out.println("1218url : " + url); //R1.bmp
		url = ImageFactory.getURL(url);
		// System.out.println("1220url: " + url);//R1.bmp

		final ImageFactory fact = userAgent.getImageFactory();
		final FovImage fovimage = fact.getImage(url, userAgent);
		// System.out.println("fovimage : " + fovimage);
		if (fovimage == null)
		{
			return null;
		}
		if (!fovimage.load(FovImage.DIMENSIONS))
		{
			return null;
		}

		final int w = fovimage.getWidth();
		final int h = fovimage.getHeight();
		final String mime = fovimage.getMimeType();
		if ("text/xml".equals(mime))
		{
			if (!fovimage.load(FovImage.ORIGINAL_DATA))
			{
				return null;
			}
			final Document doc = ((XMLImage) fovimage).getDocument();
			final String ns = ((XMLImage) fovimage).getNameSpace();
			renderDocument(doc, ns, pos, foreignAttributes);
		} else if ("image/svg+xml".equals(mime))
		{
			if (!fovimage.load(FovImage.ORIGINAL_DATA))
			{
				return null;
			}
			final Document doc = ((XMLImage) fovimage).getDocument();
			final String ns = ((XMLImage) fovimage).getNameSpace();
			renderDocument(doc, ns, pos, foreignAttributes);
		} else if ("image/eps".equals(mime))
		{
			LogUtil.warn("EPS images are not supported by this renderer");
		} else
		{
			if (!fovimage.load(FovImage.BITMAP))
			{
				LogUtil.warn("Loading of bitmap failed: " + url);
				return null;
			}

			final byte[] raw = fovimage.getBitmaps();

			// TODO Hardcoded color and sample models, FIX ME!
			final ColorModel cm = new ComponentColorModel(ColorSpace
					.getInstance(ColorSpace.CS_LINEAR_RGB), new int[]
			{ 8, 8, 8 }, false, false, ColorModel.OPAQUE, DataBuffer.TYPE_BYTE);
			final SampleModel sampleModel = new PixelInterleavedSampleModel(
					DataBuffer.TYPE_BYTE, w, h, 3, w * 3, new int[]
					{ 0, 1, 2 });
			final DataBuffer dbuf = new DataBufferByte(raw, w * h * 3);
			final WritableRaster raster = Raster.createWritableRaster(
					sampleModel, dbuf, null);

			awtImage = new BufferedImage(cm, raster, false, null);
		}
		return awtImage;
	}

	@Override
	protected void drawImage(final Area area, final java.awt.Image image,
			final Rectangle2D pos) {
		if (isAvailabilityLayer(area, userAgent.getLayers())) {
			state.getGraph().drawImage(image, (int) (pos.getX() / 1000f),
					(int) (pos.getY() / 1000f), (int) (pos.getWidth() / 1000f),
					(int) (pos.getHeight() / 1000f), null);
		}
	}

	/**
	 * @see com.wisii.wisedoc.render.PrintRenderer#createRendererContext(int,
	 *      int, int, int, java.util.Map)
	 */
	@Override
	protected RendererContext createRendererContext(final int x, final int y,
			final int width, final int height, final Map foreignAttributes) {
		final RendererContext context = super.createRendererContext(x, y,
				width, height, foreignAttributes);
		context.setProperty(Java2DRendererContextConstants.JAVA2D_STATE, state);
		return context;
	}

	// 为每一种打印方向设置相应的打印区域。
	public Paper setStatePaper(final double width, final double height,
			final Paper paper) {
		// if(width > height)
		{
			if (getOrientation() == 0) {
				paper.setImageableArea(0, 0, height / 1000d, width / 1000d);
				paper.setSize(height / 1000d, width / 1000d);
			} else if (getOrientation() == 1) {
				paper.setImageableArea(0, 0, width / 1000d, height / 1000d);
				paper.setSize(width / 1000d, height / 1000d);
			} else if (getOrientation() == 2) {
				paper.setImageableArea(0, 0, height / 1000d, width / 1000d);
				paper.setSize(height / 1000d, width / 1000d);
			}
		}
		// else
		{

		}

		return paper;
	}

	/** 设置打印纸的高度 */
	public double setPagerHeight(final double h) {
		double height = h;
		if (isSelectedHeightCheckBox) {
			height = (int) (height * scaleY);
		}

		height = (int) (height + heightAddABS * 1000);

		return height;
	}

	private int Orientation = 1;

	public void setOrientation(final int o) {
		Orientation = o;
	}

	public int getOrientation() {
		return Orientation;
	}

	/**
	 * @see java.awt.print.Printable#print(java.awt.Graphics,
	 *      java.awt.print.PageFormat, int)
	 */
	public int print(final Graphics g, final PageFormat pageFormat,
			final int pageIndex) throws PrinterException {

		if (pageIndex >= getNumberOfPages()) {
			return NO_SUCH_PAGE;
		}

		if (state != null) {
			throw new IllegalStateException("状态必须为空");
		}
		final Graphics2D graphics = (Graphics2D) g;

//		final BufferedImage currentPageImage = new BufferedImage(
//				(int) pageFormat.getWidth(), (int) pageFormat.getHeight(),
//				BufferedImage.TYPE_INT_ARGB);
		try {
			final PageViewport viewport = getPageViewport(pageIndex);
			final AffineTransform at = graphics.getTransform();
			at.scale(scaleX, scaleY);
			at.translate(excursionX, excursionY);

			state = new Java2DGraphicsState(graphics, this.fontInfo, at);

			// reset the current Positions
			currentBPPosition = 0;
			currentIPPosition = 0;

//			if (viewport.shap != null) {
//				// 测试用户
//				drawTest((Graphics2D) graphics.create(), viewport
//						.getTextGlyphVector(), currentPageImage, scaleX, scaleY);
//			} else {
//				// 客户端当前时间
//				final Calendar c = Calendar.getInstance();
//				final long currentDate = c.get(Calendar.YEAR) * 365
//						+ (c.get(Calendar.MONTH) + 1) * 31
//						+ c.get(Calendar.DAY_OF_MONTH); // 当前时间
//				final long allowDate = 2008 * 365 + 8 * 31 + 1; // 过期时间
//				final long packageDate = 2008 * 365 + 7 * 31 + 1; // 打包时间
//
//				// if(Math.abs(currentDate - viewport.data) > 3 || (packageDate
//				// > allowDate) || (currentDate > allowDate))
//				if ((Math.abs(currentDate - viewport.data) > 3)
//						|| (packageDate > allowDate)
//						|| (currentDate > allowDate)
//						|| (packageDate > currentDate)) { // 绘制
//					drawTest((Graphics2D) graphics.create(),
//							getTextGlyphVector(), currentPageImage, scaleX,
//							scaleY);
//				}
//			}

			renderPageAreas(viewport.getPage());

			return PAGE_EXISTS;
		} catch (final FOVException e) {
			LogUtil.errorException(e.getMessage(), e);
			return NO_SUCH_PAGE;
		} finally {
			state = null;
		}
	}

	/** @see com.wisii.wisedoc.render.AbstractPathOrientedRenderer#beginTextObject() */
	@Override
	protected void beginTextObject() {
		// not necessary in Java2D
	}

	/** @see com.wisii.wisedoc.render.AbstractPathOrientedRenderer#endTextObject() */
	@Override
	protected void endTextObject() {
		// not necessary in Java2D
	}

	// add by huangzl
	/**
	 * 设置是否显示锯齿效果.
	 * 
	 * @param antParamer
	 *            .true:锯齿效果;false:普通显示
	 */
	public void setAntialias(final boolean antParam) {
		antialiasing = antParam;
	}

	/***
	 * 设置打印偏移量和缩放比例
	 * 
	 * @param excursionX
	 *            页面纵向的偏移量。正数时，向上偏移；负数时，向下偏移
	 * @param excursionY
	 *            页面横向的偏移量。正数时，向左偏移；负数时，向右偏移
	 * @param scaleX
	 *            页面横向的缩放比例
	 * @param scaleY
	 *            页面纵向的的缩放比例
	 * */
	public void setPrintProperties(final float excursionX,
			final float excursionY, final float scaleX, final float scaleY) {
		// this.excursionX = (float)(excursionX * (25.4 /
		// FOUserAgent.DEFAULT_TARGET_RESOLUTION) /
		// userAgent.getTargetPixelUnitToMillimeter());
		// this.excursionY = (float)(excursionY * (25.4 /
		// FOUserAgent.DEFAULT_TARGET_RESOLUTION) /
		// userAgent.getTargetPixelUnitToMillimeter());
		// this.scaleX = (float)(scaleX / 100d * (25.4 /
		// FOUserAgent.DEFAULT_TARGET_RESOLUTION) /
		// userAgent.getTargetPixelUnitToMillimeter());
		// this.scaleY = (float)(scaleY / 100d * (25.4 /
		// FOUserAgent.DEFAULT_TARGET_RESOLUTION) /
		// userAgent.getTargetPixelUnitToMillimeter());

		this.excursionX = excursionX;
		this.excursionY = excursionY;
		this.scaleX = (float) (scaleX / 100d);
		this.scaleY = (float) (scaleY / 100d);

	}

	public void setPrintProperties(final float excursionX,
			final float excursionY, final float scaleX, final float scaleY,
			final boolean ch, final float h) {
		this.excursionX = excursionX;
		this.excursionY = excursionY;
		this.scaleX = (float) (scaleX / 100d);
		this.scaleY = (float) (scaleY / 100d);

		/** 页高是否随比例变化 */
		isSelectedHeightCheckBox = ch;
		/** 页高增加的绝对高度 */
		heightAddABS = h;
	}

	/**
	 * @return the dimensions of the specified page
	 * @param pageNum
	 *            the page number
	 * @exception FOVException
	 *                If the page is out of range or has not been rendered.
	 */
	public Dimension getPageImageSize(final int pageNum) throws FOVException {
		// 在子类中实现具体操作
		return null;
	}

	// add end
	/* 【添加：START】 by 李晓光 2008-09-19 */
	public double getPreviewScaleX() {
		return previewScaleX;
	}

	public void setPreviewScaleX(final double previewScaleX) {
		this.previewScaleX = previewScaleX;
	}

	public double getPreviewScaleY() {
		return previewScaleY;
	}

	public void setPreviewScaleY(final double previewScaleY) {
		this.previewScaleY = previewScaleY;
	}

	/**
	 * 
	 * 获得指定页在也序列中的位置索引
	 * 
	 * @param viewport
	 *            指定页
	 * @return int 返回也位置索引
	 */
	/*
	 * public int indexOfPage(PageViewport viewport) { return
	 * pageViewportList.indexOf(viewport); }
	 */
	public List<PageViewport> getPageViewports() {
		return pageViewportList;
		/*
		 * List<PageViewport> list = new
		 * ArrayList<PageViewport>(pageViewportList); clearViewportList();
		 * return list;
		 */
	}
}
