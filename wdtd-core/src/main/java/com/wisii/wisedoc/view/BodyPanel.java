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
 * @BordyPanel.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.swing.DataStructureTree;

/**
 * 类功能描述：用于创建界面用的操作面板，主要包含：左右的两个功能区，和中间的编辑区
 * 
 * 作者：李晓光 创建日期：2008-8-21
 */
@SuppressWarnings("serial")
public class BodyPanel extends JPanel
{

	private final static JPanel DEFAULT_CENTER = new JPanel();

	/* 定义界面中三个部分的引用 */
	private Component WEST = null, EAST = null, CENTER = DEFAULT_CENTER;

	/* 定义界面中被添加了滚动面板后的引用 */
	private Component VIEW_WEST = null, VIEW_EAST = null, VIEW_CENTER = null;

	/* 定义当前Panel采用的布局 */
	private final BorderLayout layout = new BorderLayout(5, 5);

	/* tab上显示是名称【资源包中的KEY】 */
	private String title = "";

	private static float FACTOR = 0.2F;// 0.02误差

	public BodyPanel()
	{
		this("");
	}

	public BodyPanel(final String title)
	{
		super(true);
		setLayout(layout);
		setTitle(title);
	}

	/**
	 * 
	 * 向主界面中添加编辑区
	 * 
	 * @param comp
	 *            指定编辑区控件
	 * @return 无
	 */
	public void setEidtComponent(Component comp)
	{
		LogUtil.debug(comp);

		if (comp == null) {
			comp = DEFAULT_CENTER;
		}
		CENTER = comp;

		/* 【删除】 by 李晓光 2008-09-05 */
		/* comp = getScrComponent(comp); */
		comp.setPreferredSize(getBodySize());
		VIEW_CENTER = comp;
		this.add(comp, BorderLayout.CENTER);
	}

	public DocumentPanel getEidtComponent()
	{
		if(CENTER instanceof JScrollPane){
			final Component comp = ((JScrollPane) CENTER).getViewport().getView();
			return (DocumentPanel)comp;
		}
		return (DocumentPanel) CENTER;
	}

	public DataStructureTree getDataStructComponent()
	{
		return (DataStructureTree) WEST;
	}

	/**
	 * 
	 * 向主界面中添加功能区【数据结构】
	 * 
	 * @param comp
	 *            指定功能区控件
	 */
	public void setDataStructComponent(Component comp)
	{
		WEST = comp;
		comp = getScrComponent(comp);		
		comp.setPreferredSize(getSideSize());
		VIEW_WEST = comp;
		this.add(comp, BorderLayout.WEST);
		if (CENTER == null) {
			setEidtComponent(new JPanel());
		}
		updateBodySize();
	}

	/**
	 * 
	 * 向主界面中添加功能区【属性设置】
	 * 
	 * @param comp
	 *            指定功能区控件
	 */
	public void setPropertyComponent(final Component comp)
	{
		LogUtil.debug(comp);

		EAST = comp;
		/*
		 * 属性栏有自己的滚动条，暂时不需要系统提供的外部滚动条 闫舒寰于2008年9月24日修改
		 */
		// comp = getScrComponent(comp);
		/* comp = getTabComponent(comp); */
		comp.setPreferredSize(getSideSize());
		VIEW_EAST = comp;

		this.add(comp, BorderLayout.EAST);

		if (CENTER == null) {
			setEidtComponent(new JPanel());
		}
		updateBodySize();
	}

	/**
	 * 
	 * 更新编辑区的大小
	 * 
	 */
	private void updateBodySize()
	{
		if (VIEW_CENTER != null) {
			VIEW_CENTER.setPreferredSize(getBodySize());
		}
	}

	/**
	 * 重写JPanel的添加控件方法
	 * 
	 * @param comp
	 *            指定要添加的控件
	 * @param constraints
	 *            指定添加约束
	 */
	@Override
	public void add(final Component comp, final Object constraints)
	{
		super.add(getViewComponent(), BorderLayout.CENTER);
	}

	/**
	 * 
	 * 根据添加的控件的不同创建不同的样式
	 * 
	 * @return {@link Component} 返回控件对应的样式
	 */
	private Component getViewComponent()
	{
		final JSplitPane pane = new JSplitPane();
		pane.setDividerSize(5);

		if (WEST == null)
		{
			if (EAST == null)
			{
				return VIEW_CENTER;
			} else
			{
				pane.setTopComponent(VIEW_CENTER);
				pane.setBottomComponent(VIEW_EAST);
				pane.setResizeWeight(1.0);
			}
		} else
		{
			if (EAST == null)
			{
				pane.setTopComponent(VIEW_WEST);
				pane.setBottomComponent(VIEW_CENTER);
				pane.setResizeWeight(0.0);
			} else
			{
				final JSplitPane pane0 = new JSplitPane();
				pane0.setDividerSize(5);
				pane0.setTopComponent(VIEW_WEST);
				pane0.setBottomComponent(VIEW_CENTER);
				pane0.setResizeWeight(0.0);

				pane.setTopComponent(pane0);
				pane.setBottomComponent(VIEW_EAST);
				pane.setResizeWeight(1.0);
			}
		}
		return pane;
	}

	/**
	 * 
	 * 根据主窗体的大小，获得功能区的大小
	 * 
	 * @return {@link Dimension} 返回功能区的大小
	 */
	private Dimension getSideSize()
	{
		final Component comp = getParent(this);
		int width = 0, height = 0;
		if (!isNull(comp))
		{
			width = (int) (comp.getWidth() * FACTOR);
			height = comp.getHeight();
		}
		return new Dimension(width, height);
	}

	/**
	 * 
	 * 根据主窗体的大小，获得编辑区的大小
	 * 
	 * @return {@link Dimension} 返回编辑区的大小
	 */
	private Dimension getBodySize()
	{
		final Component comp = getParent(this);
		double factor = 1;// (1 - FACTOR - 0.02);// 0.2 - 0.78;
		if (WEST != null && EAST != null) {
			factor = (1 - 2 * (FACTOR + 0.02));// 0.2 - 0.56;
		} else if (WEST == null && EAST == null) {
			factor = 1 - 0.02;
		} else {
			factor = 1 - FACTOR - 0.02;// 0.2 - 0.78;
		}
		int width = 0, height = 0;

		if (!isNull(comp))
		{
			width = (int) (comp.getWidth() * factor);
			height = comp.getHeight();
		}

		return new Dimension(width, height);
	}

	/**
	 * 
	 * 获得指定控件的最外层主窗体【要求是JFrame对象】
	 * 
	 * @param comp
	 *            指定控件
	 * @return {@link JFrame} 返回主窗体的引用
	 */
	private JFrame getParent(final Component comp)
	{
		if (comp instanceof JFrame) {
			return (JFrame) comp;
		}
		if (comp == null) {
			return null;
		}
		return getParent(comp.getParent());
	}

	/**
	 * 
	 * 为指定的控件添加滚动控件
	 * 
	 * @param comp
	 *            指定控件
	 * @return {@link Component} 返回添加了滚动控件的控件
	 */
	private Component getScrComponent(Component comp)
	{
		if (!(comp instanceof JScrollPane)) {
			comp = new JScrollPane(comp);
		}
		return comp;
	}

	/**
	 * 
	 * 未指定的控件包装JTablebedPane
	 * 
	 * @param comp
	 *            指定要包装的控件
	 * @return {@link Component} 返回包装好的控件
	 */
	private Component getTabComponent(Component comp)
	{
		if (!(comp instanceof JTabbedPane))
		{
			final JTabbedPane t = new JTabbedPane();
			t.add("功能区", comp);
			comp = t;
		}
		return comp;
	}

	/**
	 * 
	 * 如果当前窗体在某一Tab中显示，Tab中的标题，从这里可以获得
	 * 
	 * @return {@link String} 返回当前窗体的标题
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * 
	 * 如果把该控件添加的Tab中，可以通过该方法设置Tab中显示的标题
	 * 
	 * @param title
	 *            指定要显示的标题
	 */
	public void setTitle(final String title)
	{
		this.title = title;
	}

	/**
	 * 测试用
	 */
	public static void main(final String[] args)
	{
		try
		{
			// com.sun.java.swing.plaf.windows.WindowsLookAndFeel laf = new
			// com.sun.java.swing.plaf.windows.WindowsLookAndFeel();
			// UIManager.setLookAndFeel(
			// "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

		} catch (final Exception e)
		{
			e.printStackTrace();
		}
		final JFrame fr = new JFrame("测试用");
		fr.setSize(800, 600);
		final JLabel west = new JLabel(MediaResource.getImageIcon("1.jpg"));
		final JLabel east = new JLabel(MediaResource.getImageIcon("1.gif"));
		// JTextArea east = new JTextArea(10, 30);
		final JLabel center = new JLabel(MediaResource.getImageIcon("1.jpg"));
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final BodyPanel body = new BodyPanel();
		fr.setContentPane(body);
		body.setDataStructComponent(west);
		body.setPropertyComponent(east);
		body.setEidtComponent(center);

		fr.setVisible(true);
	}

}
