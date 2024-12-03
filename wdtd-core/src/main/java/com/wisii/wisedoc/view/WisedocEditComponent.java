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
 * @WisedocEditComponent.java
 *                            汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.wisii.wisedoc.area.PageViewport;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.exception.FOVException;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.render.awt.AWTRenderer;
import com.wisii.wisedoc.render.awt.viewer.PageViewportPanel;
import com.wisii.wisedoc.render.awt.viewer.StatusListener;
import com.wisii.wisedoc.view.select.SelectionModel;

/**
 * 类功能描述：可编辑的、但仅显示一页的预览编辑用控件。
 * 
 * 作者：李晓光 创建日期：2009-2-17
 */
@SuppressWarnings("serial")
public class WisedocEditComponent extends AbstractEditComponent
{

	public WisedocEditComponent()
	{
		this(null);
	}

	public WisedocEditComponent(final Document document)
	{

		super(document);
		initional();
	}

	private void initional()
	{
		final AWTRenderer renderer = getCurrentRenderer();
		renderer.setStatusListener(new StatusListener(){
			@Override
			public void notifyCurrentPageRendered()
			{

			}

			@Override
			public void notifyPageRendered()
			{

			}

			@Override
			public void notifyRendererStopped()
			{
				/* LocationConvert改为单例模式了，故要及时更新当前的PageViewport对象 */
				LocationConvert.getInstance(getPagevViewport(0));
				/* convert.setViewport(getPagevViewport(0)); */
				pagePanel.loadPage();
				validate();
				reloadCaret();
			}
		});
		pagePanel = new PageViewportPanel(renderer, 0);
		pagePanel.setBorder(new EmptyBorder(BORDER_SPACING, BORDER_SPACING,
				BORDER_SPACING, BORDER_SPACING));
		addPropertyChangeListener(pagePanel);
		add(pagePanel);
		setCurrentPagePanel(pagePanel);
		pagePanel.setEditable(isEditable());
		addPropertyChangeListener(new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				String name = evt.getPropertyName();
				if (!(EDITBALE).equals(name))
					return;
				pagePanel.setEditable((Boolean) evt.getNewValue());
			}
		});
	}
	private void reloadCaret()
	{

		final Thread t = new Thread()
		{
			@Override
			public void run()
			{
				if (getCaretPosition() == null)
				{
					return;
				}

				getCaret().setPosition(getCaretPosition());

				final SelectionModel model = getSelectionModel();
				if (!isNull(model))
				{
					if (isNull(getHighLighter()))
					{
						return;
					}
					getHighLighter().setSelections(model.getObjectSelection(),
							model.getSelectionCells());

				}
				/* 用于更新视窗以保证光标可见 */
				getCaret().updateViewShape();
			}
		};
		SwingUtilities.invokeLater(t);
	}

	private PageViewport getPagevViewport(final int page)
	{
		final AWTRenderer renderer = getCurrentRenderer();
		try
		{
			return renderer.getPageViewport(page);
		} catch (final FOVException e)
		{
			LogUtil.debugException(e.getMessage(), e);
			return null;
		}
	}
	private PageViewportPanel pagePanel = null;
	/** The number of pixels left empty at the top bottom and sides of the page. */
	private static final int BORDER_SPACING = 10;
}
