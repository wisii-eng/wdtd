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
/* $Id: ImageProxyPanel.java,v 1.10 2007/10/25 02:57:57 lzy Exp $ */

package com.wisii.wisedoc.render.awt.viewer;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;

import javax.swing.JPanel;

import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.area.PageViewport;
import com.wisii.wisedoc.area.inline.QianZhangArea;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.QianZhang;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.ZiMoban;
import com.wisii.wisedoc.exception.FOVException;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.render.java2d.Java2DRenderer;
import com.wisii.wisedoc.util.SystemUtil;
import com.wisii.wisedoc.view.AbstractEditComponent;
import com.wisii.wisedoc.view.DocumentEditor;
import com.wisii.wisedoc.view.LocationConvert;
import com.wisii.wisedoc.view.WisedocCaret;
import com.wisii.wisedoc.view.WisedocHighLighter;
import com.wisii.wisedoc.view.LocationConvert.PositionImpOP;

/**
 * Panel used to display a single page of a document. This is basically a
 * lazy-load display panel which gets the size of the image for layout purposes
 * but doesn't get the actual image data until needed. The image data is then
 * accessed via a soft reference, so it will be garbage collected when moving
 * through large documents.
 */
@SuppressWarnings("serial")
public class PageViewportPanel extends JPanel implements PropertyChangeListener {

	/** The reference to the BufferedImage storing the page data */
	private Reference<BufferedImage> imageRef;

	/** The maximum and preferred size of the panel */
	private Dimension size;

	/** The renderer. Shared with PreviewPanel and PreviewDialog. */
	private  Java2DRenderer renderer;

	/** The page to be rendered. */
	private int page;

	private int x;

	private int y;

	private int width;
	private int height;

	/* 【添加：START】by 李晓光 2008-09-09 */
	private boolean editable = Boolean.FALSE;
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(final boolean editable) {
		this.editable = editable;
	}
	private PageViewport pageViewport = null;
	private final LocationConvert convert = LocationConvert.getInstance();/*new LocationConvert();*/
	/* 操作系统用DPI */
	public final static int SYSTEM_DPI = Toolkit.getDefaultToolkit()
			.getScreenResolution();
	/* 代码系统使用的DPI 【72.0F】 */
	public final static int DEFAULT_DPI = (int) SystemUtil.DEFAULT_TARGET_RESOLUTION;

	/* 定义光标样式 */
	private WisedocCaret caret = null;//DEFAULT_CARET;
	/* 高亮显示处理 */
	private WisedocHighLighter highLighter = null;
	public void setCaret(final WisedocCaret caret){
		if(!isNull(this.caret)){
			this.caret.unload();
		}
		this.caret = caret;
	}
	public WisedocCaret getCaret(){
		return this.caret;
	}
	public void setHighLighter(final WisedocHighLighter highLighter){
		this.highLighter = highLighter;
	}
	public WisedocHighLighter getHighLighter(){
		return this.highLighter;
	}
	public LocationConvert getConvert() {
		if(isNull(pageViewport)) {
			pageViewport = getPagevViewport(page);
		}
		convert.setViewport(pageViewport);
		return convert;
	}
	public void loadImage(){
		try {
			final BufferedImage  image = renderer.getPageImage(page);
			imageRef = new SoftReference<BufferedImage>(image);
		} catch (final FOVException e) {
			e.printStackTrace();
		}
	}
	public boolean isLoad(){
		return (imageRef != null);
	}
	/* 【添加：END】by 李晓光 2008-09-09 */

	/**
	 * Panel constructor. Doesn't allocate anything until needed.
	 * 
	 * @param renderer
	 *            the AWTRenderer instance to use for painting
	 * @param page
	 *            initial page number to show
	 * @throws FOVException
	 */
	public PageViewportPanel(final Java2DRenderer renderer, final int page) {
		this.renderer = renderer;
		this.page = page;

		// add by xh,布局设成空
		this.setLayout(null);
		// add end

		// Allows single panel to appear behind page display.
		// Important for textured L&Fs.
		setOpaque(false);

		/* 【添加：START】by 李晓光 2008-09-09 */
		/*convert.setViewport(getPagevViewport(page));*/
		/* 【添加：END】by 李晓光 2008-09-09 */
	}

	public void mouseClicked(final MouseEvent e) {
		Point2D p = e.getPoint();
		p = getDPIPoint(p);
	}

	/* 【添加：START】by 李晓光 2008-09-09 */
	
	public PageViewport getPagevViewport(final int page) {
		try {
			return renderer.getPageViewport(page);
		} catch (final FOVException e) {
			LogUtil.debugException(e.getMessage(), e);
			return null;
		}
	}
	public Point2D getDPIPoint(final Point2D p) {
		if (p == null) {
			return p;
		}
		
		double left = (p.getX() - x) * Constants.PRECISION;
		double top = (p.getY() - y) * Constants.PRECISION;

		left /= getPreviewScaleX();
		top /= getPreviewScaleY();

		p.setLocation(left, top);

		return p;
	}

	public double getPreviewScaleX() {
		return renderer.getPreviewScaleX();
	}

	public double getPreviewScaleY() {
		return renderer.getPreviewScaleY();
	}

	/* 【添加：END】by 李晓光 2008-09-09 */
	/**
	 * @return the size of the page plus the border.
	 */
	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	/**
	 * @return the size of the page plus the border.
	 */
	@Override
	public Dimension getPreferredSize() {
		if (size == null) {
			try {
				final Insets insets = getInsets();
				size = renderer.getPageImageSize(page);
				size = new Dimension(size.width + insets.left + insets.right,
						size.height + insets.top + insets.bottom);
			} catch (final FOVException fovEx) {
				// Arbitary size. Doesn't really matter what's returned here.
				return new Dimension(10, 10);
			}
		}
		return size;
	}

	/**
	 * Sets the number of the page to be displayed and refreshes the display.
	 * 
	 * @param pg
	 *            the page number
	 */
	public void setPage(final int pg, Java2DRenderer render)
	{
		// if (page != pg || this.renderer != render)
		// {

		page = pg;
		this.renderer = render;
		pageViewport = null;
		imageRef = null;
		/* 【添加：START】by 李晓光 2008-09-12 */
		/* convert.setViewport(getPagevViewport(pg)); */
		/* 【添加：START】by 李晓光 2008-09-12 */
		repaint();
		// }
	}
	public void loadPage(){
		imageRef = null;
		pageViewport = null;
		repaint();
	}
	/**
	 * Gets the image data and paints it on screen. Will make calls to
	 * getPageImage as required.
	 * 
	 * @param graphics
	 * @see javax.swing.JComponent#paintComponent(Graphics)
	 * @see com.wisii.wisedoc.render.java2d.Java2DRenderer#getPageImage(int)
	 */
	@Override
	public synchronized void paintComponent(final Graphics graphics) {
		try {
			if (isOpaque()) { // paint background
				graphics.setColor(getBackground());
				graphics.fillRect(0, 0, getWidth(), getHeight());
			}
			
			super.paintComponent(graphics);

			BufferedImage image = null;
			if (imageRef == null || imageRef.get() == null) {
				image = renderer.getPageImage(page);
				imageRef = new SoftReference<BufferedImage>(image);
			} else {
				image = imageRef.get();
			}

			x = (getWidth() - image.getWidth()) / 2;
			y = (getHeight() - image.getHeight()) / 2;
			width = image.getWidth();
			height = image.getHeight();
			final Insets space = getInsets();
			x = (x < 0)? space.left : x;
			y = (y < 0)? space.top : y;
			/* 【添加：START】by 李晓光 2008-09-26 从Java2DRenderer中移过来的 */
			graphics.setColor(Color.white);
			graphics.fillRect(x, y, width, height);
			drawPageBorder(graphics);
			//【修改：START】 by 李晓光 2010-1-6
			//提前绘制region-side区域标示，避免遮挡排版内容。
			drawRegionSide(graphics);
			//【修改：END】 by 李晓光 2010-1-6			
			/* 【添加：END】by 李晓光 2008-09-26 */

			graphics.drawImage(image, x, y, image.getWidth(), image.getHeight(), null);
			/* 【添加：START】by 李晓光 2008-10-22 */
			if(isEditable() && !isNull(highLighter)){
				highLighter.paintHighLighter(graphics,this);
			}
			/*if(isEditable() && !isNull(caret))
				caret.paintCaret(graphics);*/
			/*drawPageCorner(graphics);*/
			/* 【添加：END】by 李晓光 2008-10-22 */
		} catch (final FOVException e) {
			LogUtil.errorException(e.getMessage(), e);
		}
	}
	/* 【添加：START】by 李晓光 2008-10-22 */
	/** 绘制页边框 */
	private void drawPageBorder(final Graphics g){
		final Graphics2D g2d = (Graphics2D)g.create();
		g2d.setPaint(Color.BLACK);
		g2d.draw3DRect(x, y, width, height, true);
		g2d.dispose();
	}
	/* 绘制页的Body区域的标识符，四个拐角。 */
	private void drawPageCorner(final Graphics g){
		final Graphics2D g2d = (Graphics2D)g.create();
		g2d.setPaint(Color.LIGHT_GRAY);
		g2d.draw(createPageCornoer());
		g2d.dispose();
	}
	private void drawRegionSide(Graphics g){
		final Graphics2D g2d = (Graphics2D)g.create();
		BasicStroke s = new BasicStroke(1.0F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f, new float[] { 1, 3, 5 }, 0);
		g2d.setStroke(s);
		g2d.setPaint(Color.LIGHT_GRAY);
		g2d.draw(createRegionSide());
		g2d.dispose();
	}
	/* 计算Body区域标识 */
	private Shape createPageCornoer(){
		final GeneralPath path = new GeneralPath();
		final MarginBean body = getBodyMargin();
		final MarginBean page = getPageMargin();
		final MarginBean bean = body.add(page);
		/*final Rectangle2D before =  getBeforeMargin(),
		start = getStartMargin(),
		end = getEndMargin(),
		after = getAfterMargin();*/
		final double w = width - bean.getMarginLeft() - bean.getMarginRight() /*- start.getWidth() - end.getWidth()*/;
		final double h = height - bean.getMarginTop() - bean.getMarginBottom() /*- before.getHeight() - after.getHeight()*/;
		final double length = 20;
		
		final double
		minX = bean.getMarginLeft() + getOffsetX(),
		minY = bean.getMarginTop() + getOffsetY(),
		maxX = minX + w + 1,
		maxY = minY + h;
		//左上
		path.moveTo(minX, minY - length);
		path.lineTo(minX, minY);
		path.lineTo(minX - length, minY);
		//右上
		path.moveTo(maxX, minY - length);
		path.lineTo(maxX, minY);
		path.lineTo(maxX + length, minY);
		//右下
		path.moveTo(maxX, maxY + length);
		path.lineTo(maxX, maxY);
		path.lineTo(maxX + length, maxY);
		//左下
		path.moveTo(minX - length, maxY);
		path.lineTo(minX, maxY);
		path.lineTo(minX, maxY + length);
		return path;
	}
	private Shape createRegionSide(){
		final GeneralPath path = new GeneralPath();
		
		final Rectangle2D before =  getBeforeMargin(),
		start = getStartMargin(),
		end = getEndMargin(),
		after = getAfterMargin();
		AffineTransform at = AffineTransform.getTranslateInstance(getOffsetX(), getOffsetY());
		// 页眉
		before.setRect(before.getX(), before.getY(), before.getWidth() - 1, before.getHeight() - 1);
		path.append(before, Boolean.FALSE);
		
		//页脚
		after.setRect(after.getX(), after.getY(), after.getWidth() - 1, after.getHeight() - 1);
		path.append(after, Boolean.FALSE);
		
		//左区域
		start.setRect(start.getX(), start.getY(), start.getWidth() - 1, start.getHeight() - 1);
		path.append(start, Boolean.FALSE);
		
		//右区域
		end.setRect(end.getX(), end.getY(), end.getWidth() - 1, end.getHeight() - 1);
		path.append(end, Boolean.FALSE);
		
		path.transform(at);
		
		return path;
	}
	/* 【添加：END】by 李晓光 2008-10-22 */
	public Reference<BufferedImage> getImageRef() {
		return imageRef;
	}

	public int getOffsetX() {
		if(x <= 0) {
			initComponentLocation(this, page);
		}
		return x;
	}

	public int getOffsetY() {
		if(y <= 0) {
			initComponentLocation(this, page);
		}
		return y;
	}
	public int getOffsetPanel(){
		if(y <= 0) {
			initComponentLocation(this, page);
		}
		final Point p = getLocation();
		return (y + p.y);
	}
	public int getImageWidth() {
		if(width <= 0) {
			initComponentLocation(this, page);
		}
		return width;
	}

	public int getImageHeight() {
		if(height <= 0) {
			initComponentLocation(this, page);
		}
		return height;
	}
	private Dimension initComponentLocation(final PageViewportPanel comp, final int page){
		final PageViewport view = comp.getPagevViewport(page);
		if(view == null) {
			return getPreferredSize();
		}
		Rectangle2D rect = view.getViewport();
		final double factor = Constants.PRECISION;
		rect = LocationConvert.getScaleRectangle((Rectangle2D)rect.clone(), comp.getPreviewScaleX()/factor, comp.getPreviewScaleY()/factor);
		final int w = (int) (rect.getWidth());
		final int h = (int) (rect.getHeight());
		final int x0 = (comp.getWidth() - w) / 2;
		final int y0 = (comp.getHeight() - h) / 2;
		x = x0;
		y = y0;
		width = w;
		height = h;
		return new Dimension(w, h);
	}
	public MarginBean getPageMargin(){
		return getScalMarginBean(getConvert().getPageMargin());
	}
	public MarginBean getBodyMargin(){
		return getScalMarginBean(getConvert().getRegionBodyMargin());
	}
	private MarginBean getScalMarginBean(final MarginBean bean){
		final double x = bean.getMarginLeft(),
		y = bean.getMarginTop(), w = bean.getMarginRight(), h = bean.getMarginBottom();
		Rectangle2D rect = new Rectangle2D.Double(x, y, w, h);
		rect = LocationConvert.getScaleRectangle(rect, getPreviewScaleX()/Constants.PRECISION, getPreviewScaleY()/Constants.PRECISION);
		bean.setMarginLeft(rect.getX());
		bean.setMarginTop(rect.getY());
		bean.setMarginRight(rect.getWidth());
		bean.setMarginBottom(rect.getHeight());
		return bean;
	}
	public Rectangle2D getBeforeMargin(){
		Rectangle2D rect = getConvert().getRegionBeforeMargin();
		if(isNull(rect)) {
			return new Rectangle();
		}
		rect = (Rectangle2D)rect.clone();
		rect = LocationConvert.getScaleRectangle(rect, getPreviewScaleX()/Constants.PRECISION, getPreviewScaleY()/Constants.PRECISION);
		return rect;
	}
	public Rectangle2D getAfterMargin(){
		Rectangle2D rect = getConvert().getRegionAfterMargin();
		if(isNull(rect)) {
			return new Rectangle();
		}
		rect = (Rectangle2D)rect.clone();
		rect = LocationConvert.getScaleRectangle(rect, getPreviewScaleX()/Constants.PRECISION, getPreviewScaleY()/Constants.PRECISION);
		return rect;
	}
	public Rectangle2D getStartMargin(){
		Rectangle2D rect = getConvert().getRegionStartMargin();
		if(isNull(rect)) {
			return new Rectangle();
		}
		rect = (Rectangle2D)rect.clone();
		rect = LocationConvert.getScaleRectangle(rect, getPreviewScaleX()/Constants.PRECISION, getPreviewScaleY()/Constants.PRECISION);
		return rect;
	}
	public Rectangle2D getEndMargin(){
		Rectangle2D rect = getConvert().getRegionEndMargin();
		if(isNull(rect)) {
			return new Rectangle();
		}
		rect = (Rectangle2D)rect.clone();
		rect = LocationConvert.getScaleRectangle(rect, getPreviewScaleX()/Constants.PRECISION, getPreviewScaleY()/Constants.PRECISION);
		return rect;
	}

	/**
	 * @see {@link DocumentEditor}
	 */
	public DocumentPosition pointtodocpos(Point point) {
		DocumentPosition position = null;
		point = (Point) getDPIPoint(point);// 2008-09-23
		/*CellElement inline = convert.getInline(point);*/
		final PositionImpOP imp = getConvert().getInline(point);
		CellElement inline = imp.getElement();
		if (isNull(inline)) {
			return position;
		}
		//如果当前找到的元素在子模板上，则光标选中为该子模板，从而放置光标定位到子模板内部
		Element parent = inline.getParent();
		//如果空行是在空单元格，由于是空单元格时，会添加一个
		if(parent instanceof TableCell&&inline instanceof Block)
		{
			if(parent.getChildCount()==0)
			{
				inline = (CellElement) parent;
			}
		}
		while(parent!=null&&!(parent instanceof PageSequence))
		{
			if(parent instanceof ZiMoban)
			{
				inline = (CellElement) parent;
				break;
			}
			parent = parent.getParent();
		}
		position = new DocumentPosition(inline, imp.isStart(), imp.isOnTop(), imp.getOffset(), imp.getPageIndex());
		/*imp = CaretLocationConvert.getPosition(position, PositionType.PAGE_DOWN);		
		position = new DocumentPosition(imp.getElement(), imp.isStart(), imp.isOnTop(), imp.getOffset(), imp.getPageIndex());*/
		/*position = CaretLocationConvert.getPosition(position, PositionType.PAGE_DOWN);*/
		return position;
	}
	/**
	 * 
	 * 获得当前点所在的子模板对象
	 * 不在子模板上则返回null
	 *
	 * @param     
	 * @return     
	 * @exception  
	 */
	public ZiMoban getZiMoban(Point point)
	{
		LocationConvert convert = getConvert();
		point = (Point)getDPIPoint(point);
		Area currentarea = convert.getCurrentArea(point);
		if(currentarea==null)
		{
			return null;
		}
		CellElement source = currentarea.getSource();
		if(source==null)
		{
			Area parea = currentarea.getParentArea();
			while(parea!=null&&source==null)
			{
				source = parea.getSource();
				parea = parea.getParentArea();
			}
		}
		if(source==null)
		{
			return null;
		}
		while(source!=null&&!(source instanceof PageSequence))
		{
			if(source instanceof ZiMoban)
			{
				return (ZiMoban) source;
			}
			source=(CellElement) source.getParent();
		}
		return null;
	}
	public QianZhang getQianZhang(Point point)
	{
		point = (Point)getDPIPoint(point);
		LocationConvert convert = getConvert();
		QianZhangArea area=convert.getCurrentQianZhangArea(point);
		if(area!=null)
		{
			return area.getQianZhang();
		}
		return null;
	}
	/* 【添加：END】by 李晓光 2008-09-22 */
	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		final String name = evt.getPropertyName();
		if(AbstractEditComponent.EDITBALE.equals(name)){
			final Boolean flag = (Boolean)evt.getNewValue();
			if(isEditable() != flag) {
				setEditable(flag);
			}
			
		}
	}
	/**
	 * @返回  pageViewport变量的值
	 */
	public final PageViewport getPageViewport()
	{
		return pageViewport;
	}
	/**
	 * @返回  page变量的值
	 */
	public final int getPage()
	{
		return page;
	}
	
}
