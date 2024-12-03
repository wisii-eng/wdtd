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

package com.wisii.wisedoc.view.ui.parts.regionsbg;

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
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionID;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.RegionAfter;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.RegionBefore;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.RegionEnd;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.RegionStart;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.SimplePageMasterModel;
import com.wisii.wisedoc.view.ui.text.UiText;
import com.wisii.wisedoc.view.ui.util.ViewUiUtil;

/**
 * 详细边框设置对话框
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/01
 */
public class RegionsBorderAndBackGroundDialog extends AbstractWisedocDialog
{

	RegionsBorderAndBackGroundDialog borderDialog;

	JTabbedPane tablepane = new JTabbedPane();

	ActionID actionID;

	RegionsBackGroundPanel backgroundpanel;

	// 当前页布局的属性
	SimplePageMasterModel spmm;
	SimplePageMaster old;

	public RegionsBorderAndBackGroundDialog()
	{
		super();
	}

	public RegionsBorderAndBackGroundDialog(final ActionID actionID)
	{
		this();
		this.actionID = actionID;
		borderDialog = this;
		this.setTitle(UiText.BORDER_DIALOG);
		setSize(800, 600);
		setLayout(new BorderLayout());
		final Map<Integer, Object> attrs = RibbonUIModel
				.getReadCompletePropertiesByType().get(actionID);
		old = ViewUiUtil.getCurrentSimplePageMaster(ActionType.LAYOUT);
		// 取得当前页布局的属性
		spmm = new SimplePageMasterModel.Builder().simplePageMaster(old)
				.build();

		if (actionID instanceof RegionBefore)
		{
			backgroundpanel = new RegionsBackGroundPanel(
					getRegionBeforeBgProperties(), actionID);
		} else if (actionID instanceof RegionAfter)
		{
			backgroundpanel = new RegionsBackGroundPanel(
					getRegionAfterBgProperties(), actionID);
		} else if (actionID instanceof RegionStart)
		{
			backgroundpanel = new RegionsBackGroundPanel(
					getRegionStartBgProperties(), actionID);
		} else if (actionID instanceof RegionEnd)
		{
			backgroundpanel = new RegionsBackGroundPanel(
					getRegionEndBgProperties(), actionID);
		} else
		{
			// 正常情况下
			backgroundpanel = new RegionsBackGroundPanel(attrs, actionID);
		}

		final ButtonPanel bp = new ButtonPanel();
		/*
		 * if (!(actionID instanceof RegionBefore || actionID instanceof
		 * Region)) { borderPanel = new RegionsBorderPanel(attrs, actionID);
		 * tablepane.add(UiText.BORDER, borderPanel); }
		 */
		tablepane.add(UiText.SHADING, backgroundpanel);
		add(tablepane, BorderLayout.CENTER);
		add(bp, BorderLayout.SOUTH);
		pack();
		this.setLocationRelativeTo(null);
		setVisible(true);
	}

	// 读取当前页眉的背景属性
	private Map<Integer, Object> getRegionBeforeBgProperties()
	{
		final Map<Integer, Object> temp = new HashMap<Integer, Object>();

		temp.put(Constants.PR_BACKGROUND_COLOR, spmm.getRegionBeforeModel()
				.getBodyBackgroundColor());
		temp.put(Constants.PR_BACKGROUND_IMAGE, spmm.getRegionBeforeModel()
				.getBodyBackgroundImage());
		temp.put(Constants.PR_BACKGROUND_REPEAT, spmm.getRegionBeforeModel()
				.getBodyBackgroundImageRepeat());
		temp.put(Constants.PR_BACKGROUND_POSITION_HORIZONTAL, spmm
				.getRegionBeforeModel().getBodyBackgroundPositionHorizontal());
		temp.put(Constants.PR_BACKGROUND_POSITION_VERTICAL, spmm
				.getRegionBeforeModel().getBodyBackgroundPositionVertical());

		return temp;
	}

	// 读取当前页脚的背景属性
	private Map<Integer, Object> getRegionAfterBgProperties()
	{
		final Map<Integer, Object> temp = new HashMap<Integer, Object>();

		temp.put(Constants.PR_BACKGROUND_COLOR, spmm.getRegionAfterModel()
				.getBodyBackgroundColor());
		temp.put(Constants.PR_BACKGROUND_IMAGE, spmm.getRegionAfterModel()
				.getBodyBackgroundImage());
		temp.put(Constants.PR_BACKGROUND_REPEAT, spmm.getRegionAfterModel()
				.getBodyBackgroundImageRepeat());
		temp.put(Constants.PR_BACKGROUND_POSITION_HORIZONTAL, spmm
				.getRegionAfterModel().getBodyBackgroundPositionHorizontal());
		temp.put(Constants.PR_BACKGROUND_POSITION_VERTICAL, spmm
				.getRegionAfterModel().getBodyBackgroundPositionVertical());

		return temp;
	}

	// 读取当前右区域的背景属性
	private Map<Integer, Object> getRegionStartBgProperties()
	{
		final Map<Integer, Object> temp = new HashMap<Integer, Object>();

		temp.put(Constants.PR_BACKGROUND_COLOR, spmm.getRegionStartModel()
				.getBodyBackgroundColor());
		temp.put(Constants.PR_BACKGROUND_IMAGE, spmm.getRegionStartModel()
				.getBodyBackgroundImage());
		temp.put(Constants.PR_BACKGROUND_REPEAT, spmm.getRegionStartModel()
				.getBodyBackgroundImageRepeat());
		temp.put(Constants.PR_BACKGROUND_POSITION_HORIZONTAL, spmm
				.getRegionStartModel().getBodyBackgroundPositionHorizontal());
		temp.put(Constants.PR_BACKGROUND_POSITION_VERTICAL, spmm
				.getRegionStartModel().getBodyBackgroundPositionVertical());

		return temp;
	}

	// 读取当前左区域的背景属性
	private Map<Integer, Object> getRegionEndBgProperties()
	{
		final Map<Integer, Object> temp = new HashMap<Integer, Object>();

		temp.put(Constants.PR_BACKGROUND_COLOR, spmm.getRegionEndModel()
				.getBodyBackgroundColor());
		temp.put(Constants.PR_BACKGROUND_IMAGE, spmm.getRegionEndModel()
				.getBodyBackgroundImage());
		temp.put(Constants.PR_BACKGROUND_REPEAT, spmm.getRegionEndModel()
				.getBodyBackgroundImageRepeat());
		temp.put(Constants.PR_BACKGROUND_POSITION_HORIZONTAL, spmm
				.getRegionEndModel().getBodyBackgroundPositionHorizontal());
		temp.put(Constants.PR_BACKGROUND_POSITION_VERTICAL, spmm
				.getRegionEndModel().getBodyBackgroundPositionVertical());

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

			final ButtonAction ba = new ButtonAction(actionID);
			final JButton yesButton = new JButton(UiText.DIALOG_OK);
			final JButton cancelButton = new JButton(UiText.DIALOG_CANCEL);
			yesButton.addActionListener(ba);
			cancelButton.addActionListener(ba);
			add(yesButton);
			add(cancelButton);

			/* 添加ESC、ENTER健处理 */
			setOkButton(yesButton);
			setCancelButton(cancelButton);
		}
	}

	class ButtonAction extends Actions
	{

		ActionID actID;

		public ButtonAction(final ActionID actionID)
		{
			this.actID = actionID;
			this.actionType = ActionType.LAYOUT;
		}

		@Override
		public void doAction(final ActionEvent e)
		{
			final String cmd = e.getActionCommand();

			if (cmd.equals(UiText.DIALOG_OK))
			{

				if (actID instanceof RegionBefore)
				{
					setRegionBeforeProperties();
				} else if (actID instanceof RegionAfter)
				{
					setRegionAfterProperties();
				} else if (actID instanceof RegionStart)
				{
					setRegionStartProperties();
				} else if (actID instanceof RegionEnd)
				{
					setRegionEndProperties();
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

		// 设置页眉的背景属性
		private void setRegionBeforeProperties()
		{
			// 页眉部分的属性
			final Map<Integer, Object> bgattris = backgroundpanel
					.getProperties();

			if (bgattris == null)
			{
				return;
			}
			if (bgattris.get(Constants.PR_BACKGROUND_COLOR) != null)
			{
				spmm.getRegionBeforeModel().setBodyBackgroundColor(
						(Color) bgattris.get(Constants.PR_BACKGROUND_COLOR));
			}
			if (bgattris.get(Constants.PR_BACKGROUND_IMAGE) != null)
			{
				spmm.getRegionBeforeModel().setBodyBackgroundImage(
						bgattris.get(Constants.PR_BACKGROUND_IMAGE));
			}
			if (bgattris.get(Constants.PR_BACKGROUND_REPEAT) != null)
			{
				spmm.getRegionBeforeModel().setBodyBackgroundImageRepeat(
						(EnumProperty) bgattris
								.get(Constants.PR_BACKGROUND_REPEAT));
			}
			if (bgattris.get(Constants.PR_BACKGROUND_POSITION_HORIZONTAL) != null)
			{
				spmm
						.getRegionBeforeModel()
						.setBodyBackgroundPositionHorizontal(
								(Length) bgattris
										.get(Constants.PR_BACKGROUND_POSITION_HORIZONTAL));
			}
			if (bgattris.get(Constants.PR_BACKGROUND_POSITION_VERTICAL) != null)
			{
				spmm
						.getRegionBeforeModel()
						.setBodyBackgroundPositionVertical(
								(Length) bgattris
										.get(Constants.PR_BACKGROUND_POSITION_VERTICAL));
			}
			setLayout(spmm.getSimplePageMaster());
		}

		// 设置页脚的背景属性
		private void setRegionAfterProperties()
		{
			// 页眉部分的属性
			final Map<Integer, Object> bgattris = backgroundpanel
					.getProperties();

			if (bgattris == null)
			{
				return;
			}
			if (bgattris.get(Constants.PR_BACKGROUND_COLOR) != null)
			{
				spmm.getRegionAfterModel().setBodyBackgroundColor(
						(Color) bgattris.get(Constants.PR_BACKGROUND_COLOR));
			}
			if (bgattris.get(Constants.PR_BACKGROUND_IMAGE) != null)
			{
				spmm.getRegionAfterModel().setBodyBackgroundImage(
						bgattris.get(Constants.PR_BACKGROUND_IMAGE));
			}
			if (bgattris.get(Constants.PR_BACKGROUND_REPEAT) != null)
			{
				spmm.getRegionAfterModel().setBodyBackgroundImageRepeat(
						(EnumProperty) bgattris
								.get(Constants.PR_BACKGROUND_REPEAT));
			}
			if (bgattris.get(Constants.PR_BACKGROUND_POSITION_HORIZONTAL) != null)
			{
				spmm
						.getRegionAfterModel()
						.setBodyBackgroundPositionHorizontal(
								(Length) bgattris
										.get(Constants.PR_BACKGROUND_POSITION_HORIZONTAL));
			}
			if (bgattris.get(Constants.PR_BACKGROUND_POSITION_VERTICAL) != null)
			{
				spmm
						.getRegionAfterModel()
						.setBodyBackgroundPositionVertical(
								(Length) bgattris
										.get(Constants.PR_BACKGROUND_POSITION_VERTICAL));
			}
			setLayout(spmm.getSimplePageMaster());
		}

		// 设置左区域的背景属性
		private void setRegionStartProperties()
		{
			// 页眉部分的属性
			final Map<Integer, Object> bgattris = backgroundpanel
					.getProperties();

			if (bgattris == null)
			{
				return;
			}
			if (bgattris.get(Constants.PR_BACKGROUND_COLOR) != null)
			{
				spmm.getRegionStartModel().setBodyBackgroundColor(
						(Color) bgattris.get(Constants.PR_BACKGROUND_COLOR));
			}
			if (bgattris.get(Constants.PR_BACKGROUND_IMAGE) != null)
			{
				spmm.getRegionStartModel().setBodyBackgroundImage(
						bgattris.get(Constants.PR_BACKGROUND_IMAGE));
			}
			if (bgattris.get(Constants.PR_BACKGROUND_REPEAT) != null)
			{
				spmm.getRegionStartModel().setBodyBackgroundImageRepeat(
						(EnumProperty) bgattris
								.get(Constants.PR_BACKGROUND_REPEAT));
			}
			if (bgattris.get(Constants.PR_BACKGROUND_POSITION_HORIZONTAL) != null)
			{
				spmm
						.getRegionStartModel()
						.setBodyBackgroundPositionHorizontal(
								(Length) bgattris
										.get(Constants.PR_BACKGROUND_POSITION_HORIZONTAL));
			}
			if (bgattris.get(Constants.PR_BACKGROUND_POSITION_VERTICAL) != null)
			{
				spmm
						.getRegionStartModel()
						.setBodyBackgroundPositionVertical(
								(Length) bgattris
										.get(Constants.PR_BACKGROUND_POSITION_VERTICAL));
			}
			setLayout(spmm.getSimplePageMaster());
		}

		// 设置右区域的背景属性
		private void setRegionEndProperties()
		{
			// 页眉部分的属性
			final Map<Integer, Object> bgattris = backgroundpanel
					.getProperties();

			if (bgattris == null)
			{
				return;
			}
			if (bgattris.get(Constants.PR_BACKGROUND_COLOR) != null)
			{
				spmm.getRegionEndModel().setBodyBackgroundColor(
						(Color) bgattris.get(Constants.PR_BACKGROUND_COLOR));
			}
			if (bgattris.get(Constants.PR_BACKGROUND_IMAGE) != null)
			{
				spmm.getRegionEndModel().setBodyBackgroundImage(
						bgattris.get(Constants.PR_BACKGROUND_IMAGE));
			}
			if (bgattris.get(Constants.PR_BACKGROUND_REPEAT) != null)
			{
				spmm.getRegionEndModel().setBodyBackgroundImageRepeat(
						(EnumProperty) bgattris
								.get(Constants.PR_BACKGROUND_REPEAT));
			}
			if (bgattris.get(Constants.PR_BACKGROUND_POSITION_HORIZONTAL) != null)
			{
				spmm
						.getRegionEndModel()
						.setBodyBackgroundPositionHorizontal(
								(Length) bgattris
										.get(Constants.PR_BACKGROUND_POSITION_HORIZONTAL));
			}
			if (bgattris.get(Constants.PR_BACKGROUND_POSITION_VERTICAL) != null)
			{
				spmm
						.getRegionEndModel()
						.setBodyBackgroundPositionVertical(
								(Length) bgattris
										.get(Constants.PR_BACKGROUND_POSITION_VERTICAL));
			}

			setLayout(spmm.getSimplePageMaster());
		}

		private void setLayout(SimplePageMaster newspm)
		{
			Object result = ViewUiUtil.getMaster(newspm, old, actionType);
			if (result instanceof SimplePageMaster)
			{
				setFOProperty(Constants.PR_SIMPLE_PAGE_MASTER, result);
			} else if (result instanceof PageSequenceMaster)
			{
				setFOProperty(Constants.PR_PAGE_SEQUENCE_MASTER, result);
				setFOProperty(Constants.PR_CURRENT_SIMPLE_PAGE_MASTER, newspm);
			}
		}
	}

}
