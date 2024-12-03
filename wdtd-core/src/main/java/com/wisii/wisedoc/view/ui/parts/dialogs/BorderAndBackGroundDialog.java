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

package com.wisii.wisedoc.view.ui.parts.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.SimplePageMasterModel;
import com.wisii.wisedoc.view.ui.model.SinglePagelayoutModel;
import com.wisii.wisedoc.view.ui.model.SinglePagelayoutModel.SPMLayoutType;
import com.wisii.wisedoc.view.ui.parts.common.BackGroundPanel;
import com.wisii.wisedoc.view.ui.parts.common.BorderPanel;
import com.wisii.wisedoc.view.ui.text.UiText;
import com.wisii.wisedoc.view.ui.util.ViewUiUtil;

/**
 * 详细边框设置对话框
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/01
 */
public class BorderAndBackGroundDialog extends AbstractWisedocDialog
{

	BorderAndBackGroundDialog borderDialog;

	JTabbedPane tablepane = new JTabbedPane();

	ActionType propertyType;

	BorderPanel borderPanel;

	BackGroundPanel backgroundpanel;

	// 当前页布局的属性
	SimplePageMasterModel spmm;

	SimplePageMaster old;

	public BorderAndBackGroundDialog()
	{
		super();
	}

	public BorderAndBackGroundDialog(final ActionType propertyType)
	{
		this();
		this.propertyType = propertyType;
		borderDialog = this;
		this.setTitle(UiText.BORDER_DIALOG);
		setSize(800, 600);
		setLayout(new BorderLayout());
		final Map<Integer, Object> attrs = RibbonUIModel
				.getReadCompletePropertiesByType().get(propertyType);

		// 取得当前页布局的属性

		if (propertyType == ActionType.LAYOUT)
		{
			// 当该对话框面向页布局的时候则需要从simple-page-master中解析属性而不是直接从属性map中直接提取
			spmm = new SimplePageMasterModel.Builder().simplePageMaster(
					ViewUiUtil.getCurrentSimplePageMaster(propertyType))
					.build();
			old = ViewUiUtil.getCurrentSimplePageMaster(propertyType);
		} else
		{
			// 正常情况下
			spmm = SinglePagelayoutModel.Instance
					.getSinglePageLayoutModel(SPMLayoutType.setFO);
		}
		if (propertyType == ActionType.LAYOUT)
		{
			// 当该对话框面向页布局的时候则需要从simple-page-master中解析属性而不是直接从属性map中直接提取
			backgroundpanel = new BackGroundPanel(getRegionBodyBgProperties(),
					propertyType);
		} else
		{
			// 正常情况下
			backgroundpanel = new BackGroundPanel(attrs, propertyType);
		}

		final ButtonPanel bp = new ButtonPanel();
		if (propertyType != ActionType.LAYOUT)
		{
			borderPanel = new BorderPanel(attrs, propertyType);
			tablepane.add(UiText.BORDER, borderPanel);
		}
		tablepane.add(UiText.SHADING, backgroundpanel);
		add(tablepane, BorderLayout.CENTER);
		add(bp, BorderLayout.SOUTH);
		pack();
		this.setLocationRelativeTo(null);
		setVisible(true);
	}

	private Map<Integer, Object> getRegionBodyBgProperties()
	{
		final Map<Integer, Object> temp = new HashMap<Integer, Object>();

		temp.put(Constants.PR_BACKGROUND_COLOR, spmm.getRegionBodyModel()
				.getBodyBackgroundColor());
		temp.put(Constants.PR_BACKGROUND_IMAGE, spmm.getRegionBodyModel()
				.getBodyBackgroundImage());
		temp.put(Constants.PR_BACKGROUND_REPEAT, spmm.getRegionBodyModel()
				.getBodyBackgroundImageRepeat());
		temp.put(Constants.PR_BACKGROUND_POSITION_HORIZONTAL, spmm
				.getRegionBodyModel().getBodyBackgroundPositionHorizontal());
		temp.put(Constants.PR_BACKGROUND_POSITION_VERTICAL, spmm
				.getRegionBodyModel().getBodyBackgroundPositionVertical());

		return temp;
	}

	class ButtonPanel extends JPanel
	{

		/**
		 * Create the panel
		 */
		public ButtonPanel()
		{
			super(new FlowLayout(FlowLayout.TRAILING));

			final ButtonAction ba = new ButtonAction(propertyType);
			final JButton yesButton = new JButton(UiText.DIALOG_OK);
			final JButton cancelButton = new JButton(UiText.DIALOG_CANCEL);
			yesButton.addActionListener(ba);
			cancelButton.addActionListener(ba);

			/* 添加ESC、ENTER健处理 by 李晓光 2009-5-14 */
			setOkButton(yesButton);
			setCancelButton(cancelButton);

			add(yesButton);
			add(cancelButton);
		}
	}

	class ButtonAction extends Actions
	{

		public ButtonAction(final ActionType actionType)
		{
			this.actionType = actionType;
		}

		@Override
		public void doAction(final ActionEvent e)
		{
			final String cmd = e.getActionCommand();

			if (cmd.equals(UiText.DIALOG_OK))
			{

				if (actionType == ActionType.LAYOUT)
				{
					// 页布局的背景相关属性需要通过simple-page-master来设置，所以需要特殊对待
					final Map<Integer, Object> bgattris = backgroundpanel
							.getProperties();
					if (bgattris != null)
					{
						if (bgattris.get(Constants.PR_BACKGROUND_COLOR) != null)
						{
							spmm
									.getRegionBodyModel()
									.setBodyBackgroundColor(
											(Color) bgattris
													.get(Constants.PR_BACKGROUND_COLOR));
						}

						if (bgattris.get(Constants.PR_BACKGROUND_IMAGE) != null)
						{
							spmm
									.getRegionBodyModel()
									.setBodyBackgroundImage(
											bgattris
													.get(Constants.PR_BACKGROUND_IMAGE));
						}

						if (bgattris.get(Constants.PR_BACKGROUND_REPEAT) != null)
						{
							spmm
									.getRegionBodyModel()
									.setBodyBackgroundImageRepeat(
											(EnumProperty) bgattris
													.get(Constants.PR_BACKGROUND_REPEAT));
						}

						if (bgattris
								.get(Constants.PR_BACKGROUND_POSITION_HORIZONTAL) != null)
						{
							spmm
									.getRegionBodyModel()
									.setBodyBackgroundPositionHorizontal(
											(Length) bgattris
													.get(Constants.PR_BACKGROUND_POSITION_HORIZONTAL));
						}

						if (bgattris
								.get(Constants.PR_BACKGROUND_POSITION_VERTICAL) != null)
						{
							spmm
									.getRegionBodyModel()
									.setBodyBackgroundPositionVertical(
											(Length) bgattris
													.get(Constants.PR_BACKGROUND_POSITION_VERTICAL));
						}
					}
					SimplePageMaster newspm = spmm.getSimplePageMaster();

					Object result = ViewUiUtil.getMaster(newspm, old,
							actionType);

					if (result instanceof SimplePageMaster)
					{
						setFOProperty(Constants.PR_SIMPLE_PAGE_MASTER, result);
					} else if (result instanceof PageSequenceMaster)
					{
						setFOProperty(Constants.PR_PAGE_SEQUENCE_MASTER, result);
						setFOProperty(Constants.PR_CURRENT_SIMPLE_PAGE_MASTER,
								newspm);
					}

				} else
				{
					Map<Integer, Object> borderattris = null;
					if (borderPanel != null)
					{
						borderattris = borderPanel.getProperties();
					}
					final Map<Integer, Object> bgattris = backgroundpanel
							.getProperties();
					if (borderattris != null || bgattris != null)
					{
						final Map<Integer, Object> attris = new HashMap<Integer, Object>();
						if (borderattris != null)
						{
							attris.putAll(borderattris);
						}
						if (bgattris != null)
						{
							attris.putAll(bgattris);
						}
						setFOProperties(attris);
					}
				}

				borderDialog.dispose();
			} else if (cmd.equals(UiText.DIALOG_CANCEL))
			{
				borderDialog.dispose();
			} else if (cmd.equals(UiText.DIALOG_HELP))
			{
				JOptionPane.showMessageDialog(null, "别着急，快轮到做帮助菜单了", "帮助菜单",
						JOptionPane.INFORMATION_MESSAGE);
				// borderDialog.dispose();
			}
		}

	}

}
