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
 * @AbstractEditComponent.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.TransferHandler;
import javax.swing.tree.TreeNode;

import org.xml.sax.SAXException;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.apps.FOUserAgent;
import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.area.AreaTreeHandler;
import com.wisii.wisedoc.area.PageViewport;
import com.wisii.wisedoc.area.RegionReference;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.DocumentPositionUtil;
import com.wisii.wisedoc.document.DocumentUtil;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.QianZhang;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.document.ZiMoban;
import com.wisii.wisedoc.document.attribute.Region;
import com.wisii.wisedoc.document.listener.DocumentCaretEvent;
import com.wisii.wisedoc.document.listener.DocumentCaretListener;
import com.wisii.wisedoc.document.listener.DocumentChange;
import com.wisii.wisedoc.document.listener.DocumentChangeEvent;
import com.wisii.wisedoc.document.listener.DocumentListener;
import com.wisii.wisedoc.exception.FOVException;
import com.wisii.wisedoc.io.wsd.ElementsWriter;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.manager.DocDropTargetHander;
import com.wisii.wisedoc.manager.DocInputHander;
import com.wisii.wisedoc.manager.WiseDocThreadService;
import com.wisii.wisedoc.render.awt.AWTRenderer;
import com.wisii.wisedoc.render.awt.viewer.PageViewportPanel;
import com.wisii.wisedoc.swing.DocumentDragGestureRecignizer;
import com.wisii.wisedoc.swing.basic.WSDData;
import com.wisii.wisedoc.swing.basic.WsdTransferable;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.view.cursor.CursorManager;
import com.wisii.wisedoc.view.mousehandler.DefaultMouseHandler;
import com.wisii.wisedoc.view.mousehandler.MouseHandler;
import com.wisii.wisedoc.view.select.DocumentPositionRange;
import com.wisii.wisedoc.view.select.ElementSelectionEvent;
import com.wisii.wisedoc.view.select.ElementSelectionListener;
import com.wisii.wisedoc.view.select.SelectionModel;

/**
 * 类功能描述：可编辑的、所有可编辑控件的父类。
 * 
 * 作者：李晓光 创建日期：2009-2-18
 */
@SuppressWarnings( { "serial", "unchecked" })
public abstract class AbstractEditComponent extends JComponent implements
		DocumentEditor, Scrollable {
	public AbstractEditComponent() {
		this(null);
	}

	public AbstractEditComponent(final Document document) {
		super();
		setDoubleBuffered(Boolean.TRUE);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));//new GridLayout(0, 1)
		try {
			handler = new AreaTreeHandler(userAgent, FORMAT, null);
		} catch (final Exception e) {
			LogUtil.errorException(e.getMessage(), e);
		}
		renderer = (AWTRenderer) userAgent.getRendererOverride();
		initialize();
		setDocument(document);
	}

	/**
	 * 如果当前控件是可编辑的，用于初始化必要的监听。
	 */
	protected void initialize() {
		initComponet();
		initActions();
	}
	/*
	 *为当前控件添加必要的事件处理。 
	 */
	private void initActions(){
		addFocusListener(new FocusListener(){
			@Override
			public void focusGained(final FocusEvent e) {
				if(!isNull(caret)) {
					caret.restartCaret();
				}
			}

			@Override
			public void focusLost(final FocusEvent e) {
				if(!isNull(caret)) {
					caret.stopCaret();
				}
			}
		});
	}
	/**
	 * 如设置了当前控件是不可编辑的，注销掉没有用的监听。
	 */
	protected void unInitialize() {	}

	/**
	 * 获得当前Render的所有页
	 * 
	 * @return {@link PageViewport} 返回当前控件包含的所有页
	 */
	public List<PageViewport> getPageViewports() {
		return renderer.getPageViewports();
	}
	/**
	 * 获得指定页的PageViewport
	 * @param page	指定页号[0, N]
	 * @return	{@link PageViewport}	返回获得的PageViewport对象，如果没有则返回Null。
	 */
	public PageViewport getPageViewport(final int page){
		try {
			return renderer.getPageViewport(page);
		} catch (final FOVException e) {
			LogUtil.debugException(e.getMessage(), e);
			return null;
		}
	}
	/**
	 * 获得包含指定坐标点所在页【PageViewportPanel】，所对应的PageViewport
	 * @param p	指定坐标点【坐标系参照：当前对象】
	 * @return	返回指定的PageViewport
	 */
	public PageViewport getPageViewport(Point p){
		Component comp = getComponentAt(p);
		if(!(comp instanceof PageViewportPanel))
			return null;
		PageViewportPanel panel = (PageViewportPanel)comp;
		return panel.getPageViewport();
	}
	/**
	 * 抽象方法，用于重新排版。
	 */
	public void startPageSequence() {
		if (caret != null) {
			caret.setPosition(null);
		}
		if (curthread != null && curthread.isAlive()) {
			curthread.stop();
		}
		curthread = new LayoutTask();
		// 延迟一段时间再进行排版
		curthread.start();
	}

	/**
	 * 重新排版指定的章节【PageSequence】。
	 * 
	 * @param seqs
	 *            指定需要重新排版的章节【PageSequence】
	 */
	public void reloatPageSequence(final PageSequence... seqs) {

	}

	/**
	 * 获得当前需要重排的PageSequence对象集。
	 * 
	 * @return {@link Collection} 返回需要重排的PageSequence集合
	 */
	public Collection<PageSequence> getPageSequences() {
		/* if(isNull(_changeelements) || _changeelements.isEmpty()) */
		return getAllPageSequences();
		/*
		 * else return DocumentUtil.getPageSequences(_changeelements);
		 */
	}

	private class LayoutTask extends Thread {
		@Override
		public void run() {
			try {
				sleep(20);
			} catch (final InterruptedException e1) {
			}

			synchronized (AbstractEditComponent.this) {
				if (isNull(handler)) {
					LogUtil.error("没有初始化Handler");
					return;
				}
				document.setChangeCells(changeelements);
				reset();
				final Collection<PageSequence> seqs = getPageSequences();
				if (seqs == null || seqs.isEmpty()) {
					return;
				}

				for (final PageSequence sequence : seqs) {
					sequence.reSet();
					/* clearRenderer(); */
					handler.startPageSequence(sequence);
					handler.endPageSequence(sequence);
					//【删除：START】 by 李晓光  2010-1-13
					//目前没有使用，还浪费内存。
					/*renderer.getAll().put(sequence, getPageViewports());*/
					//【删除：END】 by 李晓光  2010-1-13
				}
				changeelements.clear();
			}
			try {
				handler.endDocument();

			} catch (final SAXException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * 获得Document下所有的PageSequence对象
	 * 
	 * @return {@link List} 返回Document下的所有PageSequence对象。
	 */
	public List<PageSequence> getAllPageSequences() {
		final List<PageSequence> sequences = new ArrayList<PageSequence>();
		final Document document = getDocument();
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

	/**
	 * 重置当前控件的状态，清空AreaTreeHandler、AWTRenderer中的垃圾对象。
	 */
	public void reset() {
		final AreaTreeHandler handler = getTreeHandler();
		if (!isNull(handler)) {
			handler.reset();
		}
		//add by zq 增加了重排时停掉光标的功能
		if (!isNull(caret)) {
			caret.stop();
		}
		//zq add end
		clearRenderer();
		clearDocument();
	}

	private void clearRenderer() {
		final AWTRenderer renderer = getCurrentRenderer();
		if (!isNull(renderer)) {
			renderer.clearViewportList();
		}
	}

	private void clearDocument() {
		if (!isNull(document)) {
			((WiseDocDocument) document).reset();
		}
	}

	private void initComponet() {
		enableInputMethods(true);
		setCursor(CursorManager
				.getSytemCursor(CursorManager.CursorType.TEXT_CURSOR));
		final DocumentDragGestureRecignizer drag = new DocumentDragGestureRecignizer(
				this);
		addMouseListener(drag);
		addMouseMotionListener(drag);
		setSelecctionModel(new SelectionModel());
		setMouseHandler(new DefaultMouseHandler());
		
		setDragEnable(true);
		setTransferHandler(new TransferHandler() {
			@Override
			protected Transferable createTransferable(final JComponent c) {
				final AbstractEditComponent edit = (AbstractEditComponent) c;
				final SelectionModel select = edit.getSelectionModel();
				final List<CellElement> elements = select.getSelectedObject();
				final Document doc = edit.getDocument();
				if (doc != null && elements != null && !elements.isEmpty())
				{

					final String plaintext = new ElementsWriter()
							.write(elements);
					String text = DocumentUtil.getText(elements);
					return new WsdTransferable(new WSDData(text, plaintext));
				}
				return null;
			}

			@Override
			public int getSourceActions(final JComponent c) {
				return TransferHandler.COPY_OR_MOVE;
			}

			@Override
			public boolean importData(final JComponent comp,
					final Transferable t) {
				return true;
			}

		});

		// 添加拖拽监听器
		droptargethander = new DocDropTargetHander(this);
		new DropTarget(this, droptargethander);
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(final ComponentEvent e) {
				if (!isNull(caretPosition)) {
					caret.setPosition(caretPosition);
				}

				if (!isNull(selectmodel)) {
					highLighter.setSelections(selectmodel.getObjectSelection(),
							selectmodel.getSelectionCells());
				}
			}
		});

		// 添加输入事件监听器
		addKeyListener(new DocInputHander(this));
		initKey();
	}

	private void initKey() {
		KeyManager.initKey(this);
	}

	/**
	 * 获得当前的Renderer对象
	 * 
	 * @return {@link AWTRenderer} 返回当前的Renderer对象。
	 */
	public AWTRenderer getCurrentRenderer() {
		return renderer;
	}

	/**
	 * 获得指定的页控件
	 * 
	 * @param page
	 *            指定页序号，规则【0-N】
	 * @return {@link PageViewportPanel} 返回指定页序的控件。
	 */
	public PageViewportPanel getPageOf(final int page) {
		if (page == -1) {
			return null;
		}
		final int count = getGridPanel().getComponentCount();
		if (page >= count) {
			return null;
		}
		final Component comp = getGridPanel().getComponent(page);
		if (comp instanceof PageViewportPanel) {
			return (PageViewportPanel) comp;
		}

		return null;
	}

	public Rectangle getViewportBound() {
		final JComponent comp = getGridPanel();
		return comp.getVisibleRect();
	}

	// ------------------------------------------------------------
	/** 将来要干掉，这些是为了适应DocumentPanel中的一些方法。 */
	public PageViewportPanel findPageViewportPanel(final Point p) {
		final Component comp = getGridPanel().getComponentAt(p);

		if (comp instanceof PageViewportPanel) {
			return (PageViewportPanel) comp;
		}
		return null;
	}

	/**
	 * 获得当前显示的页。 规则： 1、通常把光标所在的页，看作当前页。 2、如果没有光标，则第一页是当前页。
	 * 
	 * @return {@link PageViewportPanel} 返回当前页控件。
	 */
	public PageViewportPanel getCurrentPagePanel() {
		if (isNull(currentPagePanel)) {
			setCurrentPagePanel(getPageOf(0));
		}
		return currentPagePanel;
		/* return getPagePanel(caretPosition); */
	}

	/* 根据DocumentPosition计算当前页。【现在不采用该算法。】 */
	private PageViewportPanel getPagePanel(final DocumentPosition pos) {
		if (isNull(pos)) {
			return getPageOf(0);
		}
		final PageViewport view = LocationConvert.getViewportWithPosition(pos);
		int index = 0;
		if (!isNull(view)) {
			index = view.getPageIndex();
		}
		index = (index < 0) ? 0 : index;
		return getPageOf(index);
	}

	/**
	 * 设置当前页
	 * 
	 * @param currentPage
	 *            指定当前页
	 * @exception IllegalArgumentException
	 *                如果指定的页为Null或不属于当前控件，抛出该异常。
	 */
	public void setCurrentPagePanel(final PageViewportPanel currentPage) {
		final int index = getComponentZOrder(currentPage);
		if (index == -1) {
			throw new IllegalArgumentException(
					"the component is null or doesn't belong to the container");
		}
		if (isNull(currentPage) || this.currentPagePanel == currentPage) {
			return;
		}
		fireRulerUpdate(this.currentPagePanel, currentPage);
		this.currentPagePanel = currentPage;
	}

	/**
	 * 获得当前页的，LocationConvert对象。
	 * 
	 * @return {@link LocationConvert} 返回当前的Convert对象。
	 */
	public LocationConvert getCurrentConvert(final Point p) {
		final PageViewportPanel page = findPageViewportPanel(p);
		if (isNull(page)) {
			return null;
		}
		return page.getConvert();
	}

	/** 获得页的父层容器 */
	public JComponent getGridPanel() {
		return this;
	}

	public double getPreviewScaleX() {
		if (isNull(renderer)) {
			throw new NullPointerException("您还没有初始化Render");
		}
		return renderer.getPreviewScaleX();
	}

	public double getPreviewScaleY() {
		if (isNull(renderer)) {
			throw new NullPointerException("您还没有初始化Render");
		}
		return renderer.getPreviewScaleY();
	}

	// ------------------------------------------------------------
	/** 将来要干掉，这些是为了适应DocumentPanel中的一些方法。 */
	public boolean isEditable() {
		return editable;
	}

	public void setEditable(final boolean editable) {
		final boolean old = this.editable;
		this.editable = editable;
		if (this.editable) {
			initialize();
		} else {
			unInitialize();
		}
		firePropertyChange(EDITBALE, old, this.editable);
	}

	public boolean isDragEnabled() {
		return dragEnabled;
	}

	public void setDragEnable(final boolean dragEnabled) {
		this.dragEnabled = dragEnabled;
	}

	public WisedocHighLighter getHighLighter() {
		if (isNull(highLighter)) {
			return DEFAULT_HIGH_LIGHTER;
		}
		return highLighter;
	}

	public void setHighLighter(final WisedocHighLighter highLighter) {
		if (!isNull(this.highLighter)) {
			this.highLighter.destroy();
		}

		this.highLighter = highLighter;
	}

	public WisedocCaret getCaret() {
		if (isNull(caret)) {
			setCaret(DEFAULT_CARET);
		}
		return caret;
	}

	public void setCaret(final WisedocCaret caret) {
		if (!isNull(this.caret)) {
			this.caret.unload();
		}
		this.caret = caret;
		if (!isNull(this.caret)) {
			this.caret.load();
		}
	}

	/**
	 * 获得鼠标所在位置的FO-Region
	 * 
	 * @return {@link Region} 返回鼠标位置处的Region。
	 */
	public Region findRegion() {
		if (isNull(caretPosition)) {
			return null;
		}
		//添加：by 李晓光  2009-12-23
		//为了处理空位置时，获取对应的Area
		Area area = null;//LocationConvert.getAreaWithPosition(caretPosition);
		if(LocationConvert.isBlankPosition(caretPosition))
			area= caretPosition.getLeafElement().getArea();
		else{
			area = LocationConvert.getAreaWithPosition(caretPosition);
		}
		final RegionReference ref = (RegionReference) LocationConvert.searchArea(area, RegionReference.class);
		if (isNull(ref)) {
			return null;
		}
		return ref.getRegion();
	}

	/* ----------------DocumentEditor接口实现：开始--------------------- */
	/**
	 * @see {@link DocumentEditor#getAreaofPoint(Point point)}
	 */
	@Override
	public Area getAreaofPoint(final Point point) {
		if (isNull(point)) {
			return null;
		}
		final PageViewportPanel panel = findPageViewportPanel(point);
		if (isNull(panel)) {
			return null;
		}
		final Point p = SwingUtilities.convertPoint(getGridPanel(), point,
				panel);
		final Point2D p2d = panel.getDPIPoint(p);
		final Area area = panel.getConvert().getViewportArea(p2d);
		return area;
	}

	/**
	 * @see {@link DocumentEditor#setMouseHandler(MouseHandler mousehandler)}
	 */
	@Override
	public void setMouseHandler(final MouseHandler mousehandler) {
		if (mousehandler != null) {
			if (mouseHandler != null) {
				mouseHandler.deinstall(this);
			}
			mouseHandler = mousehandler;
			mouseHandler.install(this);
		}
	}

	/**
	 * @see {@link DocumentEditor#getMouseHandler()}
	 */
	@Override
	public MouseHandler getMouseHandler() {
		return mouseHandler;
	}

	/**
	 * @see {@link DocumentEditor#getDocument()}
	 */
	@Override
	public void setDocument(final Document document) {
		// 页眉页脚部分需要针对当前document来更改，这样document的hashcode还是相等的，这时对应的面板不更新，这会有问题，所以暂时注掉了这个IF
		// if (this.document != document) {
		SystemManager.setCurruentDocument(document);
		if (this.document != null) {
			this.document.removeDocumentListener(docListener);
		}
		this.document = document;
		if (this.document != null) {
			this.document.addDocumentListener(docListener);
			this.startPageSequence();
		}
		destroy();
		// }
	}

	/**
	 * 释放当前对象持有的资源
	 */
	public void destroy() {
		synchronized (this) {
			caretPosition = null;
			if (selectmodel != null) {
				selectmodel.clearSelection();
			}
			if (caret != null) {
				caret.destroy();
			}
			if (highLighter != null) {
				highLighter.destroy();
			}
		}
	}

	public Document getDocument() {
		return this.document;
	}

	/**
	 * @see {@link DocumentEditor#getCaretPosition()}
	 */
	@Override
	public DocumentPosition getCaretPosition() {
		return caretPosition;
	}

	/**
	 * @see {@link DocumentEditor#setCaretPosition(DocumentPosition caretPosition)}
	 */
	@Override
	public void setCaretPosition(final DocumentPosition caretPosition) {
		if (!isEditable()) {
			return;
		}
		if (this.caretPosition != null) {
			if (!this.caretPosition.equals(caretPosition)) {
				final DocumentPosition old = this.caretPosition;
				this.caretPosition = caretPosition;
				fireDocumentCaretUpdate(old, caretPosition);
				if (!WiseDocThreadService.Instance.isDoingLayout()) {
					caret.setPosition(this.caretPosition);
				} else {
					caret.setPosition(null);
				}
				this.requestFocus();
			}
		} else if (caretPosition != null) {
			final DocumentPosition old = this.caretPosition;
			this.caretPosition = caretPosition;
			fireDocumentCaretUpdate(old, caretPosition);
			if (!WiseDocThreadService.Instance.isDoingLayout()) {
				caret.setPosition(this.caretPosition);
			} else {
				caret.setPosition(null);
			}
			this.requestFocus();
		}
		/* 用于更新视窗以保证光标可见 */
		caret.updateViewShape();
	}

	@Override
	public void setSelecctionModel(final SelectionModel selmodel) {
		if (selectmodel != null) {
			selectmodel.removeElementSelectionListener(selectListener);
		}
		if (selmodel != null) {
			selmodel.addElementSelectionListener(selectListener);
		}
		selectmodel = selmodel;
	}

	@Override
	public SelectionModel getSelectionModel() {
		if (isNull(selectmodel)) {
			selectmodel = SELECTIONMODEL;
			selectmodel.addElementSelectionListener(selectListener);
		}

		return selectmodel;
	}

	/**
	 * @see {@link DocumentEditor#toScreen(Rectangle2D rect, Area area)}
	 */
	@Override
	public Rectangle toScreen(Rectangle2D rect, final Area area) {
		if (isNull(rect) || isNull(area)) {
			return null;
		}
		final PageViewport page = LocationConvert.getViewportWithArea(area);
		if (isNull(page)) {
			return null;
		}
		final PageViewportPanel panel = getPageOf(page.getPageIndex());
		if (isNull(panel)) {
			return null;
		}

		rect = (Rectangle2D) rect.clone();
		rect = LocationConvert
				.getScaleRectangle(rect, getPreviewScaleX()
						/ Constants.PRECISION, getPreviewScaleY()
						/ Constants.PRECISION);
		double x = rect.getX(), y = rect.getY();
		final double w = rect.getWidth(), h = rect.getHeight();

		final double offset[] = LocationConvert.getOffsetForToScreen(area);

		x += offset[0] / Constants.PRECISION * getPreviewScaleX();
		x += panel.getOffsetX();
		y += offset[1] / Constants.PRECISION * getPreviewScaleY();
		y += panel.getOffsetY();

		Point2D p = new Point((int) x, (int) y);
		p = SwingUtilities.convertPoint(panel, (Point) p, this);
		x = p.getX();
		y = p.getY();

		return new Rectangle((int) x, (int) y, (int) w, (int) h);
	}

	/**
	 * @see {@link DocumentEditor#fromScreen(Rectangle2D source)}
	 */
	@Override
	public Rectangle2D fromScreen(Rectangle2D source) {
		if (isNull(source)) {
			return source;
		}
		Point2D p = new Point((int) source.getX(), (int) source.getY());
		final PageViewportPanel panel = findPageViewportPanel((Point) p);
		p = SwingUtilities.convertPoint(getGridPanel(), (Point) p, panel);
		source = new Rectangle2D.Double(p.getX(), p.getY(), source.getWidth(),
				source.getHeight());
		source = LocationConvert.getScaleRectangle(source,
				1 / getPreviewScaleX(), 1 / getPreviewScaleY());
		p = panel.getDPIPoint(p);

		final Area area = panel.getConvert().getCurrentArea(p);
		final double[] offset = LocationConvert.getOffsetOfBlocks(area);

		double x = source.getX(), y = source.getY();
		final double w = source.getWidth(), h = source.getHeight();

		x = x - offset[0] / Constants.PRECISION;
		x -= panel.getOffsetX() / getPreviewScaleX();
		y = y - offset[1] / Constants.PRECISION;
		y -= panel.getOffsetY() / getPreviewScaleY();

		source = WisedocUtil.convertMPT2Length(x, y, w, h);
		return source;
	}

	/**
	 * @see {@link DocumentEditor#pointtodocpos(Point point)}
	 */
	@Override
	public DocumentPosition pointtodocpos(Point point) {
		final PageViewportPanel panel = findPageViewportPanel(point);
		if (isNull(panel)) {
			return null;
		}
		if (currentPagePanel != panel) {
			setCurrentPagePanel(panel);
		}

		point = (Point) point.clone();
		point = SwingUtilities.convertPoint(getGridPanel(), point, panel);

		return panel.pointtodocpos(point);
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
    	final PageViewportPanel panel = findPageViewportPanel(point);
		if (isNull(panel)) {
			return null;
		}
		point = SwingUtilities.convertPoint(getGridPanel(), point, panel);
		return panel.getZiMoban(point);
    }
    public QianZhang getQianZhang(Point point)
    {
     	final PageViewportPanel panel = findPageViewportPanel(point);
		if (isNull(panel)) {
			return null;
		}
		point = SwingUtilities.convertPoint(getGridPanel(), point, panel);
		return panel.getQianZhang(point);
    }
	/**
	 * @see {@link DocumentEditor#docpostopoint(DocumentPosition pos)}
	 */
	@Override
	public Point docpostopoint(final DocumentPosition pos) {
		
		Shape bean = caret.getShapeWithPosition(pos);
		if(bean!=null)
		{
			Rectangle bound=bean.getBounds();
			return new Point(bound.x,bound.y);
		}
		return null;
	}

	/**
	 * @see {@link DocumentEditor#getPageViewportPanel(DocumentPosition pos)}
	 */
	public PageViewportPanel getPageViewportPanel(final DocumentPosition pos) {
		final PageViewportPanel panel = null;
		if (isNull(pos)) {
			return panel;
		}
		final PageViewport viewport = LocationConvert
				.getViewportWithPosition(pos);
		if (isNull(viewport)) {
			return panel;
		}

		return getPageOf(viewport.getPageIndex());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.view.DocumentEditor#addDocumentCaretListener(com.wisii
	 *      .wisedoc.document.listener.DocumentCaretListener)
	 */
	@Override
	public void addDocumentCaretListener(final DocumentCaretListener listener) {
		listenerList.add(DocumentCaretListener.class, listener);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.view.DocumentEditor#removeDocumentCaretListener(com
	 *      .wisii.wisedoc.document.listener.DocumentCaretListener)
	 */
	@Override
	public void removeDocumentCaretListener(final DocumentCaretListener listener) {
		listenerList.remove(DocumentCaretListener.class, listener);
	}

	/**
	 * 触发数据结构改变事件
	 */
	protected void fireDocumentCaretUpdate(final DocumentPosition oldpos,
			final DocumentPosition newpos) {
		// Guaranteed to return a non-null array
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == DocumentCaretListener.class) {
				((DocumentCaretListener) listeners[i + 1])
						.documentCaretUpdate(new DocumentCaretEvent(this, oldpos, newpos));
			}
		}
	}

	/**
	 * 通知绘制标尺
	 * 
	 * @param oldValue
	 * @param newValue
	 */
	protected void fireRulerUpdate(final PageViewportPanel oldValue,
			final PageViewportPanel newValue) {
		firePropertyChange(RULER, oldValue, newValue);
	}

	/* ----------------DocumentEditor接口实现：结束--------------------- */

	/* -------------Scrollable接口实现：开始-------------------- */
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return this.getPreferredSize();
	}

	@Override
	public int getScrollableBlockIncrement(final Rectangle visibleRect,
			final int orientation, final int direction) {
		switch (orientation) {
		case SwingConstants.VERTICAL:
			return visibleRect.height;
		case SwingConstants.HORIZONTAL:
			return visibleRect.width;
		default:
			throw new IllegalArgumentException("Invalid orientation: "
					+ orientation);
		}
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		if (getParent() instanceof JViewport) {
			return (((JViewport) getParent()).getHeight() > getPreferredSize().height);
		}
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		if (getParent() instanceof JViewport) {
			return (((JViewport) getParent()).getWidth() > getPreferredSize().width);
		}
		return false;
	}

	@Override
	public int getScrollableUnitIncrement(final Rectangle visibleRect,
			final int orientation, final int direction) {
		switch (orientation) {
		case SwingConstants.VERTICAL:
			return visibleRect.height;
		case SwingConstants.HORIZONTAL:
			return visibleRect.width;
		default:
			throw new IllegalArgumentException("Invalid orientation: "
					+ orientation);
		}
	}

	public AreaTreeHandler getTreeHandler() {
		return handler;
	}
	/**
	 * 计算当前控件的子控件【页】显示的范围。
	 * @param painter	指定当前主控件。即包含所有页的控件。
	 * @return	{@link Collection}	返回页序集。
	 */
	public static final Collection<Integer> calcAllPageNumbersToShown(final JComponent painter) {
		Collection<Integer> set = null;
		if (isNull(painter)) {
			return Collections.EMPTY_SET;
		}
		set = new HashSet<Integer>();
		final Rectangle r = painter.getVisibleRect();
		JComponent comp = null;
		for (int i = 0, count = painter.getComponentCount(); i < count; i++) {
			comp = (JComponent) painter.getComponent(i);
			if (!(comp instanceof PageViewportPanel)) {
				continue;
			}
			if (r.intersects(comp.getBounds())) {
				set.add(i);
			}
		}
		return set;
	}
	@Override
	public void paint(final Graphics g) {
		super.paint(g);
		if(!isEditable()) {
			return;
		}
		final MouseHandler mouseHandler = getMouseHandler();
		if(!isNull(mouseHandler)) {
			mouseHandler.paint(g);
			if(droptargethander != null)
			{
				droptargethander.paint(g);
			}
		}
		final WisedocCaret caret = getCaret();
		if(!isNull(caret)) {
			caret.paintCaret(g);
		}
	}
	/* -------------Scrollable接口实现：结束-------------------- */
	/** 当前出于活动状态的PageViewportPanel */
	private PageViewportPanel currentPagePanel = null;
	/** 定义用于排版的线程 */
	protected Thread curthread = null;
	/** 封装发生属性变化的元素 */
	protected List<Element> changeelements = Collections.synchronizedList(new ArrayList<Element>());
	/** 标识当前控件是否支持拖拽 */
	private boolean dragEnabled = Boolean.TRUE;
	/** 标识当前控件是否为可变接的 */
	private boolean editable = Boolean.TRUE;
	/** The AWT renderer - often shared with PreviewDialog */
	protected AWTRenderer renderer;
	/** 定义AreaTreeHandler对象 */
	protected AreaTreeHandler handler = null;
	/** 定义文档对象 */
	protected Document document = null;
	/** 定义选择模型对象 */
	protected SelectionModel selectmodel = null;
	/** 定义FOUserAgent */
	protected FOUserAgent userAgent = DocumentUtil.getUserAgent();
	/** 表示是否可编辑字段 */
	public static final String EDITBALE = "EDITBALE";
	/** 表示更新标尺字段 */
	public final static String RULER = "RULER";
	/** 定义光标样式 */
	private final WisedocCaret DEFAULT_CARET = new WisedocCaret(this);
	/** 定义缺省光标样式 */
	protected WisedocCaret caret = DEFAULT_CARET;
	/** 定义高亮显示处理 */
	private final WisedocHighLighter DEFAULT_HIGH_LIGHTER = new WisedocHighLighter(
			this);
	/** 定义缺省高亮显示处理 */
	protected WisedocHighLighter highLighter = DEFAULT_HIGH_LIGHTER;
	/** 定义数据样式 */
	private final static String FORMAT = "application/X-fov-awt-preview";
	/** 记录当前光标的位置 */
	protected DocumentPosition caretPosition = null;
	/** 用于刷新页的线程 */
	protected SwingWorker curswingworker = null;
	/** 选择处理算法 */
	protected MouseHandler mouseHandler = null;
	protected DocDropTargetHander droptargethander;
	/** 文档变化监听器 */
	private final DocumentListener docListener = new DocumentListenerImp();
	/** 选择监听器 */
	private final ElementSelectionListener selectListener = new ElementSelectionListenerImp();

	protected class ElementSelectionListenerImp implements
			ElementSelectionListener {
		@Override
		public void valueChanged(final ElementSelectionEvent e) {
			if (isNull(selectmodel) || isNull(highLighter)) {
				return;
			}
			final List<DocumentPositionRange> list = selectmodel.getSelectionCells();
			final List<CellElement> cells = selectmodel.getObjectSelection();
			highLighter.setSelections(cells, list);
		}
	}

	private class DocumentListenerImp implements DocumentListener {
		@Override
		public void changedUpdate(final DocumentChangeEvent e) {
			final List<DocumentChange> changes = e.getChanges().getChanges();
			final DocumentPosition npos = DocumentPositionUtil
					.getPositionofChanges(changes);
			if (npos != null) {
				setCaretPosition(npos);
			}
			final List<Element> newchangees = DocumentUtil
					.getChangeElements(changes);
			if (newchangees != null && !newchangees.isEmpty()) {
				final int size = newchangees.size();
				T1: for (int i = 0; i < size; i++) {
					Element changeelement = newchangees.get(i);
					while (changeelement != null
							&& !(changeelement instanceof Document)) {
						if (changeelements.contains(changeelement)) {
							continue T1;
						}
						changeelement = changeelement.getParent();
					}
					changeelements.add(newchangees.get(i));
				}
			}
			AbstractEditComponent.this.startPageSequence();
		}
	}

	/** 选择数据容器 */
	private final SelectionModel SELECTIONMODEL = new SelectionModel() {
		@Override
		protected void notifyTableCellChanged() {
			if (objSelection == null) {
				return;
			}

			final CellElement[] elemetns = objSelection.toArray(
					new CellElement[] {}).clone();
			final boolean[] b = new boolean[elemetns.length];
			Arrays.fill(b, Boolean.FALSE);
			final ElementSelectionEvent event = new ElementSelectionEvent(this,
					elemetns, b);

			fireValueChanged(event);
		}
	};
	public double getScaleFactor()
	{
		return renderer.getScaleFactor();
	}
}
