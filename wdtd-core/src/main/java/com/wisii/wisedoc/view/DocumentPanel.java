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
 * @DocumentPanel.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputMethodEvent;
import java.awt.geom.Rectangle2D;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.border.EmptyBorder;
import org.xml.sax.SAXException;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.area.PageViewport;
import com.wisii.wisedoc.document.AbstractGraphics;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.DocumentPositionUtil;
import com.wisii.wisedoc.document.DocumentUtil;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.Text;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.listener.DocumentChange;
import com.wisii.wisedoc.document.listener.DocumentChangeEvent;
import com.wisii.wisedoc.document.listener.DocumentListener;
import com.wisii.wisedoc.exception.FOVException;
import com.wisii.wisedoc.io.wsd.ElementsWriter;
import com.wisii.wisedoc.layoutmanager.PageSequenceLayoutManager;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.manager.DocDropTargetHander;
import com.wisii.wisedoc.manager.DocInputHander;
import com.wisii.wisedoc.manager.DocTemplet;
import com.wisii.wisedoc.manager.WiseDocThreadService;
import com.wisii.wisedoc.render.awt.viewer.PageViewportPanel;
import com.wisii.wisedoc.swing.DocumentDragGestureRecignizer;
import com.wisii.wisedoc.swing.basic.WSDData;
import com.wisii.wisedoc.swing.basic.WsdTransferable;
import com.wisii.wisedoc.util.SystemUtil;
import com.wisii.wisedoc.view.cursor.CursorManager;
import com.wisii.wisedoc.view.mousehandler.DefaultMouseHandler;
import com.wisii.wisedoc.view.select.DocumentPositionRange;
import com.wisii.wisedoc.view.select.SelectionModel;

/**
 * 类功能描述：文档显示类
 * 
 * 作者：zhangqiang 创建日期：2008-4-16
 */
@SuppressWarnings("serial")
public class DocumentPanel extends AbstractEditComponent implements/* , MouseListener, MouseMotionListener */ DocumentEditor,
		/* ElementSelectionListener, */DocumentListener
{
	/** Constant for setting single page display. */
	public static final int SINGLE = 1;

	/** Constant for setting continuous page display. */
	public static final int CONTINUOUS = 2;

	/** Constant for displaying even/odd pages side by side in continuous form. */
	public static final int CONT_FACING = 3;

	/** The number of pixels left empty at the top bottom and sides of the page. */
	public static final int BORDER_SPACING = 10;
	/** The number of pixels left empty at the sides of the page. */
	private static final int BORDER_SPACING_FOR_WIDTH = 20;
	/** The number of the page which is currently selected */
	private int currentPage = 0;
	/** The index of the first page displayed on screen. */
	private int firstPage = 0;
	/** The number of pages concurrently displayed on screen. */
	private int pageRange = 1;
	/** The display mode. One of SINGLE, CONTINUOUS or CONT_FACING. */
	private int displayMode = CONTINUOUS;// SINGLE;
	/** The component(s) that hold the rendered page(s) */
	/* protected PageViewportPanel[] pagePanels = null; */
	protected List<PageViewportPanel> pagePanels = Collections
			.synchronizedList(new ArrayList<PageViewportPanel>());
	/**
	 * Panel showing the page panels in a grid. Usually the dimensions of the
	 * grid are 1x1, nx1 or nx2.
	 */

	private String committext = "";

	public DocumentPanel()
	{
		this(null);
	}

	public DocumentPanel(final Document document)
	{
		super();
		setDocument(document);
		/* init(); */

		// TODO 【测试用，以后删除】
		createDocument();
	}

	protected void /* initialize */init()
	{
		// enableEvents(AWTEvent.KEY_EVENT_MASK
		// |AWTEvent.INPUT_METHOD_EVENT_MASK);
		enableInputMethods(true);
		setCursor(CursorManager
				.getSytemCursor(CursorManager.CursorType.TEXT_CURSOR));
		final DocumentDragGestureRecignizer drag = new DocumentDragGestureRecignizer(
				this);
		addMouseListener(drag);
		addMouseMotionListener(drag);
		setSelecctionModel(new SelectionModel());
		setMouseHandler(new DefaultMouseHandler());
		/* _document.addDocumentListener(this); */
		/*
		 * gridPanel.addMouseListener(this);
		 * gridPanel.addMouseMotionListener(this);
		 */
		/*
		 * addMouseListener(this); addMouseMotionListener(this);
		 */
		/*
		 * TableSelectMouseHandler handler = new TableSelectMouseHandler(this);
		 * addMouseListener(handler); addMouseMotionListener(handler);
		 */
		setDragEnable(true);
		setTransferHandler(new TransferHandler()
		{
			@Override
			protected Transferable createTransferable(final JComponent c)
			{
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
			public int getSourceActions(final JComponent c)
			{
				return TransferHandler.COPY_OR_MOVE;
			}

			@Override
			public boolean importData(final JComponent comp,
					final Transferable t)
			{
				return true;
			}

		});

		// 添加拖拽监听器
		// droptargethander = new DropTargetHander();
		droptargethander = new DocDropTargetHander(this);
		new DropTarget(this, droptargethander);
		// addInputMethodListener(new InputMethodHander());
		this.addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(final ComponentEvent e)
			{
				if (!isNull(caretPosition))
				{
					caret.setPosition(caretPosition);
				}

				if (!isNull(selectmodel))
				{
					highLighter.setSelections(selectmodel.getObjectSelection(),
							selectmodel.getSelectionCells());
				}

				/* caret.setComponent(currentPagePanel); */
				/* currentPagePanel.setCaret(caret); */

			}
		});

		// 添加输入事件监听器
		// addKeyListener(new Inputhander());
		addKeyListener(new DocInputHander(this));
		initKey();
	}

	private void initKey()
	{
		KeyManager.initKey(this);
	}

	/** ---------------DocumentEditor 接口实现【缺省实现:START】-------------------- */

	@Override
	public void setDocument(final Document document)
	{
		if (document != this.document)
		{
			SystemManager.setCurruentDocument(document);
			if (this.document != null)
			{
				this.document.removeDocumentListener(this);
			}
			this.document = document;
			if (this.document != null)
			{
				this.document.addDocumentListener(this);
				CellElement initposcell = findfirstchildofDocument(this.document);
				// 设置初始光标位置为版心的的第一个元素
				if (initposcell != null)
				{
					DocumentPosition pos;
					if (initposcell instanceof Inline)
					{
						pos = new DocumentPosition(initposcell, true);
					} else
					{
						pos = new DocumentPosition(initposcell, false);
					}
					this.caretPosition = pos;
				} else
				{
					this.caretPosition = null;
				}
				this.startPageSequence();
				final WisedocFrame frame = SystemManager.getMainframe();
				if (frame != null && frame.getEidtComponent() == this)
				{
					String title = WisedocFrame.DEFAULT_TITLE;
					frame.getDataStructComponent().setModel(
							this.document.getDataStructure());
					this.document.addDataStructureChangeListener(frame
							.getDataStructComponent());
					final String filepath = this.document.getSavePath();
					if (filepath != null && !filepath.isEmpty())
					{
						title = title + " - " + filepath;
					} else
					{
						title = title + " - " + "新建文档";
					}
					frame.setTitle(title);
				}
			} else
			{
				setCaretPosition(null);
			}
			destroy();
		}
	}

	/*
	 * 
	 * 获得传入文档的第一个版心区域元素
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @exception
	 */
	private CellElement findfirstchildofDocument(final Document document)
	{
		if (document != null)
		{
			PageSequence ps0 = (PageSequence) document.getChildAt(0);
			if (ps0 != null)
			{
				CellElement returncell = ps0.getMainFlow();
				while (!(returncell instanceof Inline)&&returncell.getChildCount() > 0)
				{
					returncell = returncell.getChildAt(0);
				}
				return returncell;
			}
		}
		return null;
	}

	/**
	 * 释放当前对象持有的资源
	 */
	public void destroy()
	{
		synchronized (this)
		{
			if (selectmodel != null)
			{
				selectmodel.clearSelection();
			}
			if (caret != null)
			{
				caret.destroy();
			}
			if (highLighter != null)
			{
				highLighter.destroy();
			}
		}
	}

	/** ---------------DocumentEditor 接口实现【缺省实现:END】-------------------- */

	/** ---------------DocumentListener 接口实现【缺省实现:START】-------------------- */
	public void changedUpdate(final DocumentChangeEvent e)
	{
		final List<DocumentChange> changes = e.getChanges().getChanges();
		final List<Element> newchangees = DocumentUtil
				.getChangeElements(changes);
		if (newchangees != null && !newchangees.isEmpty())
		{
			final int size = newchangees.size();
			T1: for (int i = 0; i < size; i++)
			{
				Element changeelement = newchangees.get(i);
				while (changeelement != null
						&& !(changeelement instanceof Document))
				{
					if (changeelements.contains(changeelement))
					{
						continue T1;
					}
					changeelement = changeelement.getParent();
				}
				changeelements.add(newchangees.get(i));
			}
		}

		// 放入共享队列
		changeList.add(e);
		final DocumentPosition npos = DocumentPositionUtil
				.getPositionofChanges(changes);
		if (npos != null)
		{
			final DocumentPosition old = getCaretPosition();
			if (!isNull(old))
			{
				npos.setPageIndex(old.getPageIndex());
			}
			this.caretPosition = npos;
		}
	}

	// 内部共享队列，排版变化的队列
	private static final BlockingQueue<DocumentChangeEvent> changeList = new LinkedBlockingQueue<DocumentChangeEvent>();

	{
		WiseDocThreadService.Instance
				.doDocChangeService(new DelaiedFireChange());
	}

	// 每个一段时间所执行的加入排版队列的任务
	private class DelaiedFireChange implements Runnable
	{

		@Override
		public void run()
		{

			if (changeList.isEmpty())
			{
				return;
			}

			// for (final DocumentChangeEvent dce : changeList) {
			// changeList.remove(dce);
			// }

			// 移除所有队列的元素
			Object temp = changeList.poll();

			while (temp != null)
			{
				temp = changeList.poll();
			}

			WiseDocThreadService.Instance
					.doPageLayoutService(new PageLayoutAction());
		}
	}

	// 具体的排版动作
	private class PageLayoutAction implements Runnable
	{
		@Override
		public void run()
		{
			System.out.println("start doing pagelayout....");
			final long startTime = System.currentTimeMillis();

			startPageSequence();

			final long endTime = System.currentTimeMillis();
			System.out.println("finished, spend: " + (endTime - startTime)
					+ " ms" + " in " + Thread.currentThread() + "thread");
		}
	}

	/** ---------------DocumentListener 接口实现【缺省实现:END】-------------------- */

	/**
	 * 这是父类中的方法用于重新排版，不过子类一般都会覆盖这个方法
	 */
	public void reload()
	{
		doReload(false);
	}

	private synchronized void doReload(boolean isupdataall)
	{
		if (!renderer.isRenderingDone())
		{
			// do not allow the reloading while FOV is still rendering
			JOptionPane.showMessageDialog(this,
					"Cannot perform the requested operation until "
							+ "all page are rendered. Please wait",
					"Please wait ", 1 /* INFORMATION_MESSAGE */);
			return;
		}
		if (curswingworker != null && !curswingworker.isDone())
		{
			return;
		}
		final int savedCurrentPage = currentPage;
		currentPage = 0;

		switch (displayMode)
		{
		case CONT_FACING:
			// This page intentionally left blank
			// Makes 0th/1st page on rhs
			/* gridPanel.add(new JLabel("")); */
		case CONTINUOUS:
			currentPage = 0;
			firstPage = 0;
			pageRange = renderer.getNumberOfPages();
			break;
		case SINGLE:
		default:
			currentPage = 0;
			firstPage = 0;
			pageRange = 1;
			break;
		}
		List<PageViewportPanel> oldpagepanes = new ArrayList<PageViewportPanel>(
				pagePanels);
		pagePanels.clear();
		int oldpagesize = oldpagepanes.size();
		if (pageRange < oldpagesize)
		{
			for (int i = pageRange; i < oldpagesize; i++)
			{
				remove(oldpagepanes.get(i));
			}
		}
		for (int pg = 0; pg < pageRange; pg++)
		{
			/*
			 * pagePanels[pg] = new PageViewportPanel(renderer, pg + firstPage);
			 * pagePanels[pg].setBorder(new EmptyBorder(BORDER_SPACING,
			 * BORDER_SPACING, BORDER_SPACING, BORDER_SPACING));
			 * pagePanels[pg].setEditable(DocumentPanel.this.isEditable());
			 * add(pagePanels[pg]);
			 */
			PageViewportPanel panel = null;
			if (pg < oldpagepanes.size())
			{

				panel = oldpagepanes.get(pg);
				//滚动面板是否需要重新刷新，在改变缩放比率时，或新的页面大小不等于老的页面大小时需要重新刷新
				boolean isupdataUI = isupdataall;
				if (!isupdataUI)
				{
					//获得老页面
					PageViewport oldpv = panel.getPageViewport();
					PageViewport newpv = null;
					try
					{
						//得到新页面
						newpv = renderer.getPageViewport(pg + firstPage);
					} catch (FOVException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (oldpv == null || newpv == null)
					{
						isupdataUI = true;
					} else
					{
						Rectangle2D oldr = oldpv.getViewArea();
						Rectangle2D newr = newpv.getViewArea();
						//判断新老页面大小是否相等
						if (oldr.getWidth() != newr.getWidth()
								|| oldr.getHeight() != newr.getHeight())
						{
							isupdataUI = true;
						}
					}
				}
				if (isupdataUI)
				{
					remove(panel);
					panel = createPageViewportPanel(pg + firstPage);
					add(panel, pg);

				} else
				{
					panel.setPage(pg + firstPage, renderer);
				}

			} else
			{
				panel = createPageViewportPanel(pg + firstPage);
				add(panel);
			}
			pagePanels.add(panel);
		}
		// 修改by 张强结束
		/* 【添加：START】 by 李晓光 2008-09-22 */
		setPage(savedCurrentPage);
	}

	private PageViewportPanel createPageViewportPanel(final int page)
	{
		final PageViewportPanel panel = new PageViewportPanel(renderer, page);
		panel.setBorder(new EmptyBorder(BORDER_SPACING, BORDER_SPACING,
				BORDER_SPACING, BORDER_SPACING));
		panel.setEditable(DocumentPanel.this.isEditable());
		return panel;
	}

	@Override
	protected void processInputMethodEvent(final InputMethodEvent e)
	{
		super.processInputMethodEvent(e);

		if (!e.isConsumed())
		{
			if (!isEditable())
			{
				return;
			} else
			{
				if (e.getID() == InputMethodEvent.INPUT_METHOD_TEXT_CHANGED)
				{
					replaceInputMethodText(e);
				}
			}

			e.consume();
		}
	}

	private void replaceInputMethodText(final InputMethodEvent event)
	{
		String text = "";
		System.out.println("in???");
		int commitCount = event.getCommittedCharacterCount();
		final AttributedCharacterIterator charit = event.getText();
		charit.first();
		final List<CellElement> elemt = new ArrayList<CellElement>();
		for (char c = charit.current(); commitCount > 0; c = charit.next(), commitCount--)
		{
			text = text + c;
			elemt.add(new TextInline(new Text(c), null));
		}
		final DocumentPositionRange range = selectmodel.getSelectionCell();
		if (!elemt.isEmpty())
		{
			if (range != null)
			{

				document.replaceElements(range, elemt);
			} else
			{
				final DocumentPosition caretposition = getCaretPosition();
				if (caretposition != null)
				{
					document.insertString(text, caretposition, null);
				}
			}
			System.out.println("committext:" + text);
			committext = text;
		}
	}

	/**
	 * 创建初始页的默认文档
	 */
	private void createDocument()
	{
		setDocument(new DocTemplet().getDocument());
	}

	/*
	 * @Override public void paint(final Graphics g) { super.paint(g); // 显示鼠标操作
	 * mouseHandler.paint(g); droptargethander.paint(g); if (!isNull(caret)) {
	 * caret.paintCaret(g); } }
	 */

	// 以下是多线程用于处理排版部分的试验田
	/**
	 * As getScaleToFitWindow, but ignoring the Y axis.
	 * 
	 * @return the requested scale factor
	 * @throws FOVException
	 *             in case of an error while fetching the PageViewport
	 */
	public double getScaleToFitWidth() throws FOVException
	{
		final JViewport panel = (JViewport) this.getParent();
		final Dimension extents = panel.getExtentSize();
		final double dpi = Toolkit.getDefaultToolkit().getScreenResolution(); // dpi
		final double scale = SystemUtil.DEFAULT_TARGET_RESOLUTION / dpi; //72.0/
																			// 96.0
																			// ;
		return getScaleToFit(extents.getWidth() * scale - 2
				* BORDER_SPACING_FOR_WIDTH, Double.MAX_VALUE);
	}

	/**
	 * Returns the scale factor required in order to fit either the current page
	 * within the current window or to fit two adjacent pages within the display
	 * if the displaymode is continuous.
	 * 
	 * @return the requested scale factor
	 * @throws FOVException
	 *             in case of an error while fetching the PageViewport
	 */
	public double getScaleToFitWindow() throws FOVException
	{
		final JViewport panel = (JViewport) this.getParent();
		final Dimension extents = panel.getExtentSize();
		// 获取屏幕的DPI显示
		final double dpi = Toolkit.getDefaultToolkit().getScreenResolution(); // dpi
		final double scale = SystemUtil.DEFAULT_TARGET_RESOLUTION / dpi; //72.0/
																			// 96.0
																			// ;
		return getScaleToFit(extents.getWidth() * scale - 2 * BORDER_SPACING,
				extents.getHeight() * scale - 2 * BORDER_SPACING);
	}

	/**
	 * Returns the scale factor required in order to fit either the current page
	 * or two adjacent pages within a window of the given height and width,
	 * depending on the display mode. In order to ignore either dimension, just
	 * specify it as Double.MAX_VALUE.
	 * 
	 * @param viewWidth
	 *            width of the view
	 * @param viewHeight
	 *            height of the view
	 * @return the requested scale factor
	 * @throws FOVException
	 *             in case of an error while fetching the PageViewport
	 */
	public double getScaleToFit(final double viewWidth, final double viewHeight)
			throws FOVException
	{
		final PageViewport pageViewport = renderer.getPageViewport(currentPage);
		final Rectangle2D pageSize = pageViewport.getViewArea();
		final double widthScale = viewWidth / (pageSize.getWidth() / 1000f);
		final double heightScale = viewHeight / (pageSize.getHeight() / 1000f);
		return Math.min(displayMode == CONT_FACING ? widthScale / 2
				: widthScale, heightScale);
	}

	/**
	 * Scales page image
	 * 
	 * @param scale
	 *            [0;1]
	 */
	public void setScaleFactor(final double scale)
	{
		renderer.setScaleFactor(scale);
		doReload(true);
	}
    
	public int getCurrentPage()
	{
		return currentPage;
	}

	public int getFirstPage()
	{
		return firstPage;
	}

	public int getPageRange()
	{
		return pageRange;
	}

	/**
	 * @return the currently visible page
	 */
	public int getPage()
	{
		return currentPage;
	}

	/**
	 * Selects the given page, displays it on screen and notifies listeners
	 * about the change in selection.
	 * 
	 * @param number
	 *            the page number
	 */
	public void setPage(final int number)
	{
		if (displayMode == CONTINUOUS || displayMode == CONT_FACING)
		{
			// FIXME Should scroll so page is visible
			currentPage = number;
		} else
		{ // single page mode
			currentPage = number;
			firstPage = currentPage;
		}
		showPage();
	}

	/** Starts rendering process and shows the current page. */
	public synchronized void showPage()
	{
		final ShowPageImage viewer = new ShowPageImage();

		if (SwingUtilities.isEventDispatchThread())
		{
			viewer.run();
		} else
		{
			SwingUtilities.invokeLater(viewer);
		}
	}

	/** This class is used to render the page image in a thread safe way. */
	private class ShowPageImage implements Runnable
	{

		/**
		 * The run method that does the actual rendering of the viewed page
		 */
		public synchronized void run()
		{

			/*
			 * for (int pg = firstPage; pg < firstPage + pageRange; pg++) {
			 * pagePanels.get(pg - firstPage).loadPage(); }
			 */

			/* pagePanels[pg - firstPage].setPage(pg); *//*
														 * for (int i = 0; i <
														 * pagePanels.length;
														 * i++) { try {
														 * renderer.
														 * getPageImage(i); }
														 * catch (final
														 * FOVException e) {
														 * e.printStackTrace();
														 * } }
														 */
			revalidate();
			reloadCaret();
		}
	}

	private void reloadCaret()
	{

		final Thread t = new Thread()
		{

			@Override
			public synchronized void run()
			{
				reloadObjectSelect();
				final SelectionModel model = getSelectionModel();
				if (!isNull(model))
				{
					getHighLighter().setSelections(model.getObjectSelection(),
							model.getSelectionCells());
				}
				if (getCaretPosition() == null)
				{
					return;
				}
				getCaret().setPosition(getCaretPosition());
				/* getCaret().restartCaret(); */
				/* 用于更新视窗以保证光标可见 */
				getCaret().updateViewShape();
			}
		};
		SwingUtilities.invokeLater(t);
	}

	private void reloadObjectSelect()
	{
		final List<CellElement> cells = getSelectionModel()
				.getObjectSelection();
		if (cells != null && !cells.isEmpty())
		{
			final int size = cells.size();
			Rectangle rect = null;
			for (int i = 0; i < size; i++)
			{
				final CellElement element = cells.get(i);
				if (element != null
						&& ((element instanceof AbstractGraphics) || (element instanceof BlockContainer)))
				{
					final Area area = element.getArea();
					if (area != null)
					{
						final Rectangle2D viewport = area.getViewport();
						if (viewport != null)
						{
							if (rect == null)
							{
								rect = toScreen(viewport, area);
							} else
							{
								rect.add(toScreen(viewport, area));
							}
						}
					}
				}
			}
			if (rect != null)
			{
				repaint(rect.x - 1, rect.y - 1, rect.width + 2, rect.height + 2);
			}
		}
	}

	/**
	 * Sets the display mode.
	 * 
	 * @param mode
	 *            One of SINGLE, CONTINUOUS or CONT_FACING.
	 */
	public void setDisplayMode(final int mode)
	{
		if (mode != displayMode)
		{
			displayMode = mode;
			/*
			 * gridPanel.setLayout(new GridLayout(0, displayMode == CONT_FACING
			 * ? 2 : 1));
			 */
			setLayout(new GridLayout(0, displayMode == CONT_FACING ? 2 : 1));
			reload();
		}
	}

	/**
	 * Returns the display mode.
	 * 
	 * @return mode One of SINGLE, CONTINUOUS or CONT_FACING.
	 */
	public int getDisplayMode()
	{
		return displayMode;
	}

	/**
	 * reset the param about display of the PreviewPanelAPP.
	 */
	public void reInint()
	{
		this.firstPage = 0;
		this.pageRange = 1;
		this.pagePanels = null;
		this.displayMode = CONTINUOUS;// SINGLE;
	}

	/**
	 * Allows a (yet) simple visual debug of the document.
	 */
	void debug()
	{
		renderer.debug = !renderer.debug;
		reload();
	}

	/**
	 * 抽象方法，用于重新排版。
	 */
	@Override
	public void startPageSequence()
	{
		startPs();
	}

	private void startPs()
	{
		WisedocFrame frame = SystemManager.getMainframe();
		if(frame!=null)
		{
			frame.notifyLayoutBegin();
		}
		if (isNull(handler))
		{
			LogUtil.error("没有初始化Handler");
			return;
		}
		document.setChangeCells(changeelements);
		reset();
		final Collection<PageSequence> seqs = getPageSequences();
		if (seqs == null)
		{
			return;
		}
		for (PageSequence sequence : seqs) {
			PageSequenceLayoutManager.clearBlockSource(sequence);
		}
		for (final PageSequence sequence : seqs)
		{
			sequence.reSet();
			/* renderer.clearViewportList(); */
			handler.startPageSequence(sequence);
			handler.endPageSequence(sequence);
			//【删除：START】 by 李晓光  2010-1-13
			//目前没有使用，还浪费内存。
			/*renderer.getAll().put(sequence, getPageViewports());*/
			//【删除：END】 by 李晓光  2010-1-13
		}
		changeelements.clear();

		try
		{
			handler.endDocument();

		} catch (final SAXException e)
		{
			e.printStackTrace();
		}
	}
}
