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

package com.wisii.wisedoc.view.ui.actions.text;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import org.jvnet.flamingo.ribbon.JFlowRibbonBand;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.ExternalGraphic;
import com.wisii.wisedoc.document.ImageInline;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.swing.WiseDocOptionPane;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.WisedocFrame;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.FontPropertyModel;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;
import com.wisii.wisedoc.view.ui.parts.dialogs.TextAllPropertiesDialog;
import com.wisii.wisedoc.view.ui.parts.text.FontPropertyPanel;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 字体设置动作
 * 
 * @author 闫舒寰
 * @version 1.0 2009/01/21
 */
public class FontFamilyAction extends Actions
{

	/**
	 * TODO 1、输入没有进行验证 2、Ribbon界面上的不能进行索引 3、索引不智能 4、isAvailable没有完成，并且目前不好用
	 * 5、下拉菜单中的字体效果不能预览 6、用户自定义字体库的引用没做
	 */
	@Override
	public void doAction(final ActionEvent arg0)
	{
		if (arg0.getSource() instanceof WiseCombobox)
		{
			final WiseCombobox jb = (WiseCombobox) arg0.getSource();
			Component temp = jb.getParent();
			if (temp != null)
			{
				Object value = jb.getSelectedItem();
				ComboBoxModel model = jb.getModel();
				if (model instanceof DefaultComboBoxModel)
				{
					// 判断设置的字体在当前系统中是否存在
					if (((DefaultComboBoxModel) model).getIndexOf(value) < 0)
					{
						int result = WiseDocOptionPane.showConfirmDialog(
								SystemManager.getMainframe(),
								RibbonUIText.FONTFAMILYUNABLE,
								WisedocFrame.DEFAULT_TITLE,
								WiseDocOptionPane.YES_NO_OPTION);
						if (result == WiseDocOptionPane.NO_OPTION)
						{
							Map<Integer, Object> oldatt = RibbonUIModel
									.getReadCompletePropertiesByType().get(
											actionType);
							String oldfontfaimly = (String) InitialManager
									.getInitialValue(Constants.PR_FONT_FAMILY,
											null);
							if (oldatt != null)
							{
								String fontfaimly = (String) oldatt
										.get(Constants.PR_FONT_FAMILY);
								if (fontfaimly != null)
								{
									oldfontfaimly = fontfaimly;
								}
							}
							jb.InitValue(oldfontfaimly);
							return;
						}
					}
				}
				while (temp.getParent() != null)
				{
					if (temp instanceof TextAllPropertiesDialog
							|| temp instanceof FontPropertyPanel)
					{
						FontPropertyModel.setFOProperty(
								Constants.PR_FONT_FAMILY, value);
					} else if (temp instanceof JFlowRibbonBand)
					{
						setFOProperty(Constants.PR_FONT_FAMILY, value);
					}
					temp = temp.getParent();
				}
			}
		} else if (arg0.getSource() instanceof JList)
		{
			final JList list = (JList) arg0.getSource();
			FontPropertyModel.setFOProperty(
					Constants.PR_LENGEND_LABEL_FONTFAMILY, list
							.getSelectedValue());
		}
	}

	WiseCombobox ui;

	@Override
	protected void uiStateChange(final UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);

		if (hasPropertyKey(Constants.PR_FONT_FAMILY))
		{

			if (uiComponent instanceof WiseCombobox)
			{
				ui = (WiseCombobox) uiComponent;
				ui.InitValue(evt.getNewValue());
			}
		} else if (hasPropertyKey(Constants.PR_LENGEND_LABEL_FONTFAMILY))
		{
			if (uiComponent instanceof JList)
			{
				final JList list = (JList) uiComponent;
				list.setSelectedValue(evt.getNewValue(), Boolean.TRUE);
			}
		}
	}

	@Override
	public void setDefaultState(final ActionCommandType actionCommand)
	{
		super.setDefaultState(null);

		if (actionCommand == ActionCommandType.RIBBON_ACTION)
		{
			if (uiComponent != null)
			{
				if (uiComponent instanceof WiseCombobox)
				{
					final WiseCombobox ui = (WiseCombobox) uiComponent;
					ui.InitValue("宋体");
					ui.updateUI();
				}
			}
		}

		if (actionCommand == ActionCommandType.DIALOG_ACTION)
		{
			final Set<List<Object>> tempSet = RibbonUIManager.getUIComponents()
					.get(this.actionID).get(actionCommand);
			for (final List<Object> list : tempSet)
			{
				for (final Object object : list)
				{
					if (object instanceof WiseCombobox)
					{
						final WiseCombobox ui = (WiseCombobox) object;
						Object value = InitialManager.getInitialValue(Constants.PR_FONT_FAMILY, null);
						Map<Integer, Object> atts = RibbonUIModel
								.getCurrentPropertiesByType().get(
										this.actionType);
						if (atts != null&&atts.containsKey(Constants.PR_FONT_FAMILY))
						{
							value = atts.get(Constants.PR_FONT_FAMILY);
						}
						if (value.equals(Constants.NULLOBJECT))
						{
							ui.InitValue(null);
						} else
						{
							ui.InitValue(value);
						}
					}
				}
			}
		}

		if (actionCommand == ActionCommandType.DYNAMIC_ACTION)
		{
			final Set<List<Object>> tempSet = RibbonUIManager.getUIComponents()
					.get(this.actionID).get(actionCommand);
			for (final List<Object> list : tempSet)
			{
				for (final Object object : list)
				{
					if (object instanceof WiseCombobox)
					{
						final WiseCombobox ui = (WiseCombobox) object;
						final Object value = FontPropertyPanel
								.getInitialPropertise().get(
										Constants.PR_FONT_FAMILY);
						if (value.equals(Constants.NULLOBJECT))
						{
							ui.InitValue(null);
						} else
						{
							ui.InitValue(value);
						}
					}
				}
			}
		}
	}

	@Override
	public void setDifferentPropertyState(final UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);

		if (evt.hasPropertyKey(Constants.PR_FONT_FAMILY))
		{
			if (uiComponent != null)
			{
				if (uiComponent instanceof WiseCombobox)
				{
					final WiseCombobox ui = (WiseCombobox) uiComponent;
					ui.InitValue(null);
				}
			}
		}
	}

	@Override
	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		List<Object> elementList = RibbonUIModel.getElementList();
		if (elementList == null)
		{
			return false;
		}
		for (final Object object : elementList)
		{
			if (object instanceof ExternalGraphic
					|| object instanceof ImageInline)
			{
				return false;
			}
		}
		return true;
	}
}
