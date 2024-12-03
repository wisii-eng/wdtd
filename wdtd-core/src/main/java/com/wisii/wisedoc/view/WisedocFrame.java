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
 * @WisedocFrame.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import sun.misc.BASE64Decoder;
import com.wisii.security.Function;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.listener.DocumentCaretEvent;
import com.wisii.wisedoc.document.listener.DocumentCaretListener;
import com.wisii.wisedoc.exception.FOVException;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.render.awt.AWTRenderer;
import com.wisii.wisedoc.render.awt.viewer.PageViewportPanel;
import com.wisii.wisedoc.render.awt.viewer.StatusListener;
import com.wisii.wisedoc.render.awt.viewer.Translator;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.DataStructureTree;
import com.wisii.wisedoc.swing.HorizontalRuler;
import com.wisii.wisedoc.swing.LeftUnit;
import com.wisii.wisedoc.swing.VerticalRuler;
import com.wisii.wisedoc.swing.WiseDocOptionPane;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.view.action.SaveFileAction;
import com.wisii.wisedoc.view.toolbar.ToolBarManager;
import com.wisii.wisedoc.view.ui.ribbon.RibbonPanel;

/**
 * 类功能描述：WisedocMainFrame用于创建主窗体控件
 * 
 * 作者：李晓光 创建日期：2008-8-25
 */
@SuppressWarnings("serial")
public class WisedocFrame extends JFrame implements StatusListener
{
	/** 纵向打印 */
	public OrientationRequested orientationRequested = OrientationRequested.PORTRAIT;
	/*** 英寸 */
	public static final int INCH = 72000;
	/*** 点 */
	public static final int PT = 25400;
	/** 页面纵向的偏移量。正数时，向上偏移；负数时，向下偏移 */
	public float excursionX = 0.0f;
	/** 页面横向的偏移量。正数时，向左偏移；负数时，向右偏移 */
	public float excursionY = 0.0f;
	/** 页面横向的缩放比例 */
	public float scaleX = 100.0f;
	/** 页面纵向的的缩放比例 */
	public float scaleY = 100.0f;
	/** 是否在打印纸的height上使用缩放 */
	public boolean isSelectedHeightCheckBox = false;
	/** 打印纸的height增加的值 */
	public float heightAddABS = 0.0f;
	/** The min percent of the page. */
	private static final double MIN_PERCENT = 20.0;
	/** The min percent of the page. */
	private static final double MAX_PERCENT = 500.0;
	/** Formats the text in the scale combobox. */
	private static final DecimalFormat percentFormat = new DecimalFormat(
			"###0.0#");
	/** The Translator for localization */
	protected Translator translator;
	/** The main display area */
	protected DocumentPanel previewPanel;
	/** The AWT renderer */
	protected AWTRenderer renderer;
	/** The JCombobox to rescale the rendered page view */
	private JComboBox scale = new JComboBox();
	/** The JLabel for the process status bar */
	private JLabel processStatus;
	/** The JLabel information status bar */
	private JLabel infoStatus;
	/* 主窗体默认的大小 */
	private final static Dimension DEFAULT_DIMENSION = new Dimension(800, 600);
	/* 主窗体默认标题 */
	public  static String DEFAULT_TITLE = "Wisedoc Designer";
	/* 主窗体默认的内容面板 */
	private final JPanel mainPanel = new JPanel(new BorderLayout());
	/* 用于放置编辑区、功能区用面板 */
	private final BodyPanel editPanel = new BodyPanel();
	/* 定义窗体公交栏 */
	private HorizontalRuler rulerH = null;
	private VerticalRuler rulerV = null;
	/*
	 * 定义标尺控件 final Bar ruler = new Bar();
	 */
	static
	{
		try
		{
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (final Exception e)
		{
			LogUtil.errorException(e.getMessage(), e);
		}
	}

	public WisedocFrame()
	{
		this(DEFAULT_TITLE);
	}

	@SuppressWarnings("static-access")
	public WisedocFrame( String title)
	{
		if(!V())
		{
			DEFAULT_TITLE = DEFAULT_TITLE+"-未经授权版本";
		}
		title = DEFAULT_TITLE;
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setTitle(title);
		setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.CENTER);
		setSize(DEFAULT_DIMENSION);
		mainPanel.add(editPanel, BorderLayout.CENTER);

		// RepaintManager.setCurrentManager(new TracingRepaintManager());
		// 应用新的Ribbon界面，暂时注销掉ToolBar
		setJToolbar(new RibbonPanel(asdasasa).getRibbon());
		/* 要在setJToolbar后面设置，否则工具栏上该图标不能显示。 */
		setIconImage(MediaResource.getImage("wisii.gif"));
		initEditorArea();
		initstatusBar();
		previewPanel.startPageSequence();
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				Document doc = SystemManager.getCurruentDocument();
				// 判断当前文档是否保存，没有保存则提示保存
				if (doc != null && !doc.isSaved())
				{
					int res = WiseDocOptionPane.showConfirmDialog(
							WisedocFrame.this, MessageResource
									.getMessage("wsd.file.documentnotsave"));
					if (res == WiseDocOptionPane.CANCEL_OPTION)
					{

						return;
					} else if (res == WiseDocOptionPane.OK_OPTION)
					{
						if (!SaveFileAction.saveWSDFile())
						{
							return;
						}
					}
				}
				System.exit(0);
			}

		});
	}

	/* -----------------------------移植代码：开始------------------------------- */
	/** @see com.wisii.wisedoc.render.awt.viewer.StatusListener#notifyRendererStopped() */
	public void notifyRendererStopped()
	{
		reload();
	}

	/**
	 * Sets message to be shown in the status bar in a thread safe way.
	 * 
	 * @param message
	 *            the message
	 */
	public void setStatus(final String message)
	{
		SwingUtilities.invokeLater(new ShowStatus(message));
	}

	/**
	 * Updates the message to be shown in the info bar in a thread safe way.
	 */
	public void notifyPageRendered()
	{
		//张强去掉，原来是在重新排版后刷新当前页数状态，现在直接在改为调用notifuPageNumberChanged方法
//		SwingUtilities.invokeLater(new ShowInfo());
	}
	/**
	 * add by zq
	 * 更新光标所在页的页号，总页数状态信息
	 * 光标所在页的页号发生改变时调用
	 *
	 * @param     page：当前页号
	 * @return    
	 * @exception   
	 */
	public void notifyPageIndexChanged(final int page,final int pagesequenceindex)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				String message = translator.getString("Status.Page") 
						+ page  + translator.getString("Status.of") 
						+ (renderer.getNumberOfPages());
				if (pagesequenceindex > -1)
				{
					message = message + " "
							+ translator.getString("Status.PageSequence")
							+ pagesequenceindex;
				}
				infoStatus.setText(message);
			}
		});

	}
	public void notifyCurrentPageRendered()
	{
	}
	/**
	 * 
	 *zhangqiang添加
	 *显示排版开始状态信息
	 *
	 * @param     
	 * @return    
	 * @exception  
	 */
	public void notifyLayoutBegin()
	{
		setStatus(translator.getString("Status.processStatus"));	
	}

	/** 闫舒寰添加， Ribbon界面需要preview panel **/
	public DocumentPanel getPreviewPanel()
	{
		return previewPanel;
	}

	/** Scales page image */
	public void setScale(double scaleFactor)
	{
		if (scaleFactor < MIN_PERCENT)
		{ // guard Width (0) and height (0) of
			// BufferedImage
			scaleFactor = MIN_PERCENT;
		}
		if (scaleFactor > MAX_PERCENT)
		{ // guard out of memory
			scaleFactor = MAX_PERCENT;
		}

		final String item = ((String) scale.getSelectedItem()).trim();
		try
		{
			if ((item.indexOf('%') == -1)
					&& !item.equals(translator.getString("Menu.Fit.Window"))
					&& !item.equals(translator.getString("Menu.Fit.Width")))
			{
				scale.setSelectedItem(percentFormat.format(scaleFactor) + "%");
			} else if (item.lastIndexOf('%') != (item.length() - 1)
					&& !item.equals(translator.getString("Menu.Fit.Window"))
					&& !item.equals(translator.getString("Menu.Fit.Width")))
			{
				scale.setSelectedItem("100%");
			}
		} catch (final Exception e)
		{
			scale.setSelectedItem("100%");
		}
		if (this.renderer.isRenderingDone())
		{
			previewPanel.setScaleFactor(scaleFactor / 100d);
			if (scaleFactor == MAX_PERCENT || scaleFactor == MIN_PERCENT)
			{
				scale.setSelectedItem(String.valueOf(scaleFactor) + '%');
			}
		}
	}

	/* -----------------------------移植代码：结束------------------------------- */
	/**
	 * 为窗体设置工具栏
	 */
	public void setJToolbar(JComponent comp)
	{ /* 原来是private */
		if (isNull(comp))
		{
			comp = ToolBarManager.createToolBarPanel();
		}

		mainPanel.add(comp, BorderLayout.NORTH);
	}

	/**
	 * 
	 * 向主窗体编辑区设置控件
	 * 
	 * @param comp
	 *            指定要设置的控件
	 * @return 无
	 */
	public void setEidtComponent(final Component comp)
	{
		editPanel.setEidtComponent(comp);
	}

	/**
	 * 获得主窗体编辑区控件
	 * 
	 * @return {@link AbstractEditComponent} 返回编辑区控件
	 */
	public AbstractEditComponent getEidtComponent()
	{
		return editPanel.getEidtComponent();
	}

	/**
	 * 获得数据结构展示控件
	 * 
	 * @return {@link DataStructureTree} 返回数据结构展示控件
	 */
	public DataStructureTree getDataStructComponent()
	{
		return editPanel.getDataStructComponent();
	}

	/**
	 * 
	 * 向功能区设置数据结构处理展现、处理控件
	 * 
	 * @param comp
	 *            指定要添加的控件
	 * @return 无
	 */
	public void setDataStructComponent(final Component comp)
	{
		editPanel.setDataStructComponent(comp);
	}

	/**
	 * 
	 * 向功能区设置属性编辑控件
	 * 
	 * @param comp
	 *            指定要添加的控件
	 * @return 无
	 */
	public void setPropertyComponent(final Component comp)
	{
		editPanel.setPropertyComponent(comp);
	}
	/* -----------------------------移植代码：开始------------------------------- */
	// 设置是否显示锯齿效果
	private void initEditorArea()
	{
		previewPanel = new DocumentPanel();
		final JScrollPane pane = new JScrollPane(previewPanel);
		WisedocUtil.disableScroPaneKeyborde(pane);
		final JScrollBar scrBar = pane.getVerticalScrollBar();
		scrBar.setUnitIncrement(100);
		scrBar.setBlockIncrement(100);
		renderer = previewPanel.getCurrentRenderer();
		renderer.setStatusListener(this);
		initRuler(pane);
		setEidtComponent(pane);
	}

	/* 初始化标尺 */
	private void initRuler(final JScrollPane pane)
	{
		rulerH = new HorizontalRuler();
		rulerV = new VerticalRuler();
		pane.setColumnHeaderView(rulerH);
		pane.setRowHeaderView(rulerV);
		final LeftUnit button = new LeftUnit();
		pane.setCorner(JScrollPane.UPPER_LEFT_CORNER, button);

		previewPanel.addMouseMotionListener(new MouseAdapter()
		{
			@Override
			public void mouseMoved(final MouseEvent e)
			{
				rulerH.mouseRepaint(e);
				rulerV.mouseRepaint(e);
			}
		});
		final UpdateRulerListenerImp imp = new UpdateRulerListenerImp();
		previewPanel.addPropertyChangeListener(imp);
		previewPanel.addComponentListener(imp);
		final JScrollBar barV = pane.getVerticalScrollBar();
		barV.addAdjustmentListener(imp);
	}

	/* 创建标尺的监视器，用于及时刷新表示上的刻度。 */
	private class UpdateRulerListenerImp extends ComponentAdapter implements
			PropertyChangeListener, DocumentCaretListener, AdjustmentListener
	{
		@Override
		public void propertyChange(final PropertyChangeEvent evt)
		{
			if (!DocumentPanel.RULER.equalsIgnoreCase(evt.getPropertyName()))
			{
				return;
			}
			// 传过来的PageViewportPanel不会为空
			final PageViewportPanel panel = (PageViewportPanel) evt
					.getNewValue();
			rulerH.viewRepaint(panel);
			rulerV.viewRepaint(panel);
		}

		@Override
		public void documentCaretUpdate(final DocumentCaretEvent event)
		{
			DocumentPosition pos = event.getNewpos();
			final PageViewportPanel panel = previewPanel
					.getPageViewportPanel(pos);
			pos = event.getOldpos();
			final PageViewportPanel p0 = previewPanel.getPageViewportPanel(pos);
			if (isNull(panel) || panel == p0)
			{
				return;
			}
			rulerH.viewRepaint(panel);
			rulerV.viewRepaint(panel);
		}

		@Override
		public void componentResized(final ComponentEvent e)
		{
			final PageViewportPanel panel = previewPanel.getCurrentPagePanel();
			rulerH.viewRepaint(panel);
			rulerV.viewRepaint(panel);
		}

		@Override
		public void adjustmentValueChanged(final AdjustmentEvent e)
		{
			final Thread t = new Thread()
			{
				@Override
				public void run()
				{
					final WisedocHighLighter high = previewPanel
							.getHighLighter();
					high.reload();
				}
			};
			SwingUtilities.invokeLater(t);
		}
	}

	/* 初始化状态栏 */
	private void initstatusBar()
	{
		translator = new Translator();
		final double ScaleFactor = 100.0;
		scale = new JComboBox();
		scale.addItem(translator.getString("Menu.Fit.Window"));
		scale.addItem(translator.getString("Menu.Fit.Width"));
		scale.addItem("25%");
		scale.addItem("50%");
		scale.addItem("75%");
		scale.addItem("100%");
		scale.addItem("150%");
		scale.addItem("200%");
		scale.setMaximumSize(new Dimension(80, 24));
		scale.setPreferredSize(new Dimension(80, 24));
		scale.setEditable(true);

		scale.setSelectedItem(ScaleFactor + "%");

		renderer.setScaleFactor(ScaleFactor / 100d); // 设置relod的显示比例

		scale.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent e)
			{
				scaleActionPerformed(e);
			}
		});
		// 设置是否显示锯齿效果
		showHideAntialias();
		// Status bar
		final JPanel statusBar = new JPanel();
		processStatus = new JLabel();
		processStatus.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEtchedBorder(), BorderFactory
						.createEmptyBorder(0, 3, 0, 0)));
		infoStatus = new JLabel();
		infoStatus.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createEtchedBorder(), BorderFactory.createEmptyBorder(0, 3, 0,
				0)));

		statusBar.setLayout(new GridBagLayout());

		processStatus.setPreferredSize(new Dimension(200, 21));
		processStatus.setMinimumSize(new Dimension(200, 21));

		infoStatus.setPreferredSize(new Dimension(100, 21));
		infoStatus.setMinimumSize(new Dimension(100, 21));

		statusBar.add(processStatus, new GridBagConstraints(0, 0, 1, 0, 2.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 3), 0, 0));
		statusBar.add(infoStatus, new GridBagConstraints(1, 0, 1, 0, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));
		statusBar.add(scale, new GridBagConstraints(2, 0, 1, 0, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));
		getContentPane().add(statusBar, BorderLayout.SOUTH);
	}

	private void showHideAntialias()
	{
		renderer.setAntialias(true);
	}

	private void reload()
	{
		previewPanel.reload();
		setStatus(translator.getString("Status.Show"));
	}
   
	private void scaleActionPerformed(final ActionEvent e)
	{
		try
		{
			final int index = scale.getSelectedIndex();
			if (index == 0)
			{
				setScale(previewPanel.getScaleToFitWindow() * 100);
			} else if (index == 1)
			{
				setScale(previewPanel.getScaleToFitWidth() * 100);
			} else
			{
				final String item = ((String) scale.getSelectedItem()).trim();
				int indexofPercent = item.indexOf('%');
				if (indexofPercent == -1)
				{
					indexofPercent = item.length();
				}
				setScale(Double.parseDouble(item.substring(0, indexofPercent)));
			}
		} catch (final FOVException fovEx)
		{
			LogUtil.debugException("出错", fovEx);
		} catch (final NumberFormatException numEx)
		{ // The percent is not
			// available
			setScale(100.0);
			scale.setSelectedItem(percentFormat.format(100.0) + "%");
		}
	}

	/** This class is used to show status in a thread safe way. */
	private class ShowStatus implements Runnable
	{

		/** The message to display */
		private final String message;

		/**
		 * Constructs ShowStatus thread
		 * 
		 * @param message
		 *            message to display
		 */
		public ShowStatus(final String message)
		{
			this.message = message;
		}

		public void run()
		{
			processStatus.setText(message.toString());
		}
	}
/*   张强去掉
	*//** This class is used to show info in a thread safe way. *//*
	private class ShowInfo implements Runnable
	{

		public void run()
		{
			final String message = translator.getString("Status.Page") + " "
					+ (previewPanel.getPage() + 1) + " "
					+ translator.getString("Status.of") + " "
					+ (renderer.getNumberOfPages());
			infoStatus.setText(message);
		}
	}
*/
	/* -----------------------------移植代码：结束------------------------------- */
	private static String FP = "license/license.lic";
	private static List<Function> asdasasa;

	/**
	 * 
	 *判断许可文件是否存在
	 * 
	 * @param
	 * @return true：表示许可文件存在，false：表示许可文件不存在
	 * @exception
	 */
	private static boolean IE()
	{
		File c = new File(FP);
		if (c != null && c.exists())
		{
			return true;
		}
		return false;
	}

	/*
	 * 
	 * 开始校验前的初始化方法
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @exception
	 */
	private static boolean VL()
	{
		FileInputStream fileinstream;
		try
		{

			File file = new File(FP);
			long ct = System.currentTimeMillis();
			long lt = file.lastModified();
			if (ct < lt)
			{
				WiseDocOptionPane.showMessageDialog(null, "请不要修改系统时间");
				return false;
			} else
			{
				File[] diskfs = File.listRoots();
				if (diskfs != null && diskfs.length > 1)
				{
					File[] childrenfs = diskfs[0].listFiles();
					if (childrenfs != null)
					{
						for (int i = 0; i < childrenfs.length; i++)
						{
							if (ct < childrenfs[i].lastModified())
							{
								WiseDocOptionPane.showMessageDialog(null,
										"请不要修改系统时间");
								file.setLastModified(childrenfs[i]
										.lastModified());
								return false;
							}
						}
					}
				}
				file.setLastModified(ct);
			}
			fileinstream = new FileInputStream(file);
			ZipInputStream wwd = new ZipInputStream(fileinstream);
			byte[] licensedatas = null;
			byte[] signdatas = null;
			byte[] keydatas = null;
			for (ZipEntry hjkj = wwd.getNextEntry(); hjkj != null; hjkj = wwd
					.getNextEntry())
			{
				if (hjkj.getName().equals("sign"))
				{
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					int i = wwd.read();
					while (i != -1)
					{
						out.write(i);
						i = wwd.read();
					}
					licensedatas = out.toByteArray();

				} else if (hjkj.getName().equals("license"))
				{
					BASE64Decoder fdsdf = new BASE64Decoder();
					signdatas = fdsdf.decodeBuffer(wwd);
				} else if (hjkj.getName().equals("key"))
				{
					BASE64Decoder rtrty = new BASE64Decoder();
					keydatas = rtrty.decodeBuffer(wwd);
				} else
				{
					return false;
				}
				wwd.closeEntry();
			}
			wwd.close();
			if (!CS(licensedatas, signdatas, keydatas))
			{
				return false;
			}
			return po(D(licensedatas));
		} catch (Exception e)
		{
			LogUtil.debugException("出错", e);
			return false;
		}
	}

	private static boolean CS(byte[] wds, byte[] sdsds, byte[] sdadasda)
	{
		if (wds == null || sdsds == null || sdadasda == null)
		{
			return false;
		}
		X509EncodedKeySpec sdsdw = new X509EncodedKeySpec(sdadasda);
		try
		{
			KeyFactory a = KeyFactory.getInstance("RSA");
			PublicKey s = a.generatePublic(sdsdw);
			Signature d = Signature.getInstance("MD5withRSA");
			d.initVerify(s);
			d.update(wds);
			return d.verify(sdsds);

		} catch (Exception e)
		{
			LogUtil.debugException("出错", e);
			return false;
		}

	}

	private static byte[] D(byte[] wew)
	{
		try
		{
			Cipher q = Cipher
					.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
			byte[] sasas =  Arrays.copyOfRange(wew, 0, 16);
			byte[] wwew = Arrays.copyOfRange(wew, 16, wew.length);
			/* ------------------------------------------------------------- */
			SecretKeySpec qqwq = new SecretKeySpec(sasas, "AES");
			q.init(Cipher.DECRYPT_MODE, qqwq);
			return q.doFinal(wwew);
		} catch (Exception e)
		{
			LogUtil.debugException("出错", e);
			return null;
		}
	}

	private static boolean po(byte[] datas)
	{
		try
		{
			org.dom4j.Document a = DocumentHelper.parseText(new String(datas, "utf-8"));
			Element w = a.getRootElement();
			int q = w.nodeCount();
			if (q < 3)
			{
				return false;
			}
			String ssdsdid = w.attributeValue("pcifd");
			if (!"ESAQ".equals(ssdsdid))
			{
				return false;
			}
			for (int i = 0; i < q; i++)
			{
				Node r = w.node(i);
				if (r instanceof Element)
				{
					Element x = (Element) r;
					String nodename = x.getName();
					if ("ueerrel".equals(nodename))
					{
						// donothing
						// username = element.getAttribute("name");
						// if (username == null)
						// {
						// return false;
						// }
					} else if ("hsdsdsw".equals(nodename))
					{
						String uynsg = x.attributeValue("uoopdsopso");
						if (uynsg == null || !uynsg.equals("iuipi"))
						{
							String sda = x.attributeValue("wdewsds");
							if (sda == null
									|| !sda.equals(HardWareInfo.asdasdw()))
							{
								return false;
							}
							String wewe = x.attributeValue("asdsdsm");
							if (wewe == null
									|| !wewe.equals(HardWareInfo.asdwe()))
							{
								return false;
							}
							String zsdsd = x.attributeValue("ggdspp");
							if (zsdsd == null
									|| !zsdsd.equals(HardWareInfo.tytyuyu()))
							{
								return false;
							}
						}
					} else if ("pasdssds".equals(nodename))
					{
						String yuyu = x.attributeValue("iopmsy");
						try
						{
							long xsdswa = Long.parseLong(yuyu);
							if (xsdswa > 0l
									&& System.currentTimeMillis() > xsdswa)
							{
								WiseDocOptionPane.showMessageDialog(null,
										"软件许可已到期，请申请新的许可");
								return false;
							}
							String dsd = x.attributeValue("asdsdszxs");
							long dsdas = Long.parseLong(dsd);
							if(System.currentTimeMillis() < dsdas)
							{
								WiseDocOptionPane.showMessageDialog(null,
										"请不要修改系统时间");
								return false;
							}

						} catch (Exception e)
						{
							LogUtil.debugException("出错", e);
							return false;
						}
					} else if ("weiwntsdg".equals(nodename))
					{
						ADsdsd(x);
					} else
					{
						return false;
					}
				} else
				{
					return false;
				}
			}
			return true;
		} catch (Exception e)
		{
			LogUtil.debugException("出错", e);
			return false;
		}
	}

	private static void ADsdsd(Element x)
	{
		if (x != null)
		{
			List<Element> cls = x.elements();
			if (cls != null && !cls.isEmpty())
			{
				asdasasa = new ArrayList<Function>();
				for (Element cl : cls)
				{
					if (cl.getName().equals("uipsnsdbsb"))
					{
						List<Attribute> as = cl.attributes();

						if (as != null && !as.isEmpty())
						{
							String i = null;
							Map<String, String> ss = new HashMap<String, String>();
							for (Attribute a : as)
							{
								if (a.getName().equals("ioippds"))
								{
									i = a.getValue();
								} else
								{
									ss.put(a.getName(), a.getValue());
								}
							}
							if (i != null)
							{
								asdasasa.add(new Function(i, ss));
							}
						}
					}
				}
			}
		}
	}

	private static final boolean AD(String data1, String data2)
	{
		if (data1 == null || data2 == null)
		{
			return false;
		}
		if (data2.equals(E(data1)))
		{
			return true;
		} else
		{
			return false;
		}
	}

	private static String E(String data1)
	{
		try
		{
			MessageDigest dfdsdf = MessageDigest.getInstance("MD5");
			return new String(dfdsdf.digest(data1.getBytes()));
		} catch (NoSuchAlgorithmException e)
		{
			LogUtil.debugException("出错", e);
			return null;
		}
	}

	private static boolean V()
	{
		if (!IE())
		{
//			WiseDocOptionPane.showMessageDialog(null, "许可文件不存在");
			return false;
		}
		return VL();
	}

	static final class HardWareInfo
	{

		static String asdasdw()
		{
			return "testdiskid";
		}

		static String asdwe()
		{
			String sas = System.getProperty("os.name");
			try
			{
				if (sas.startsWith("Windows"))
				{
					return asdsadsa(iuop());

				}

				else if (sas.startsWith("Linux"))

				{
					return fdew(hfssdfd());
				} else
				{
					throw new IOException("unknown   operating   system:   "
							+ sas);
				}
			} catch (Exception ex)
			{
				LogUtil.debugException("出错", ex);
				return "cannot get";
			}
		}

		static String tytyuyu()
		{
			return "testprocessorid";
		}

		/* Linux stuff */

		private final static String fdew(String sdsds) throws ParseException
		{
			StringTokenizer opoi = new StringTokenizer(sdsds, "\n");
			while (opoi.hasMoreTokens())
			{
				String line = opoi.nextToken().trim();
				int weqw = line.indexOf("HWaddr");
				if (weqw < 0)
					continue;
				String qqwq = line.substring(weqw + 6).trim();
				if (qqwq != null
						&& !"".equals(qqwq))
				{
					return qqwq;
				}
			}
			ParseException ex = new ParseException(
					"cannot   read   MAC   address   for   "
							+ "   from   [" + sdsds + "]", 0);
			LogUtil.debugException("出错", ex);
			throw ex;
		}

		private final static String hfssdfd() throws IOException
		{
			Process p = Runtime.getRuntime().exec("/sbin/ifconfig");
			InputStream stdoutStream = new BufferedInputStream(p
					.getInputStream());
			StringBuffer buffer = new StringBuffer();
			for (;;)
			{
				int c = stdoutStream.read();
				if (c == -1)
					break;
				buffer.append((char) c);
			}
			String outputText = buffer.toString();
			stdoutStream.close();
			return outputText;
		}

		/* Windows stuff */

		private final static String asdsadsa(String sdsdsas)
				throws ParseException
		{
			StringTokenizer tokenizer = new StringTokenizer(sdsdsas, "\n");
			while (tokenizer.hasMoreTokens())
			{
				String line = tokenizer.nextToken().trim();
				if (line.startsWith("Physical Address"))
				{
					int sdswusa = line.indexOf(":");
					if (sdswusa <= 0)
						continue;
					String ugmkl = line.substring(
							sdswusa + 1).trim();
					if (ugmkl != null
							&& !"".equals(ugmkl))
					{
						return ugmkl;
					}
				}
			}
			ParseException ex = new ParseException(
					"cannot   read   MAC   address   from   [" + sdsdsas
							+ "]", 0);
			throw ex;
		}

		private final static String iuop() throws IOException
		{
			Process p = Runtime.getRuntime().exec("ipconfig   /all");
			InputStream stdoutStream = new BufferedInputStream(p
					.getInputStream());
			StringBuffer buffer = new StringBuffer();
			for (;;)
			{
				int c = stdoutStream.read();
				if (c == -1)
					break;
				buffer.append((char) c);
			}
			String outputText = buffer.toString();
			stdoutStream.close();
			return outputText;
		}
	}
}
