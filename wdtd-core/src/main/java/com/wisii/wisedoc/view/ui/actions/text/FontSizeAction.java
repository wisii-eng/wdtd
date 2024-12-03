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

import static com.wisii.wisedoc.util.WisedocUtil.isEmpty;
import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

import javax.swing.text.JTextComponent;

import org.jvnet.flamingo.ribbon.JFlowRibbonBand;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.ExternalGraphic;
import com.wisii.wisedoc.document.ImageInline;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.document.datatype.LengthBase;
import com.wisii.wisedoc.document.datatype.PercentBase;
import com.wisii.wisedoc.resource.TranslationTableResource;
import com.wisii.wisedoc.swing.ui.WiseCombobox;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.FontPropertyModel;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;
import com.wisii.wisedoc.view.ui.model.UIStateChangeListener;
import com.wisii.wisedoc.view.ui.parts.dialogs.TextAllPropertiesDialog;
import com.wisii.wisedoc.view.ui.parts.text.FontPropertyPanel;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 设置字体大小的属性
 * 
 * @author 闫舒寰
 * @since 2008/09/26
 */
public class FontSizeAction extends Actions implements UIStateChangeListener {

	/**
	 * TODO 1、没有进行输入验证，文字大小大约支持0到2500pt的文字 2、字号读取还有问题，尤其是读取自定义字号时有问题
	 * 3、不能针对不同单位进行设置属性 4、对输入列表进行是否有-1的验证 5、用户输入负值的时候反应不确定
	 */
	@Override
	public void doAction(final ActionEvent arg0) {
		// arg0.getActionCommand());
		if (arg0.getSource() instanceof WiseCombobox) {

			final WiseCombobox jb = (WiseCombobox) arg0.getSource();

			Double fontSize = new Double(-1);// -1;
//			final String selectedSize = "";
			String selectedName = "";
			if (jb.getSelectedItem() instanceof String) {
				selectedName = (String) jb.getSelectedItem();
				fontSize = TranslationTableResource.searchValue(selectedName);
				{
					// 解析本来是数字形式的字符
					try {
						if (isNull(fontSize)) {
							fontSize = Double.parseDouble(selectedName);
						}
					} catch (final NumberFormatException e) {
						//当输入值不合理的时候应该有提示，不过提示方式还没有确定，而是直接返回
//						JOptionPane.showMessageDialog(RibbonPanel.getRibbon(),
//							    "不支持的字号",
//							    "输入错误",
//							    JOptionPane.ERROR_MESSAGE);
						
//						jb.get
						return;
					}
				}
			}
			
			if (fontSize <= 0) {
				return;
			}
			Component temp = jb.getParent();
			final FixedLength length = new FixedLength(fontSize, "pt");
			if (temp != null) {
				while (temp.getParent() != null) {
					if (temp instanceof TextAllPropertiesDialog
							|| temp instanceof FontPropertyPanel) {
						// 设置字号到属性中

						FontPropertyModel.setFOProperty(Constants.PR_FONT_SIZE,
								length);
						return;
					} else if (temp instanceof JFlowRibbonBand) {
						// 设置字号到属性中
						setFOProperty(Constants.PR_FONT_SIZE, length);
						return;
					}
					temp = temp.getParent();
				}
			}
		}
	}

	@Override
	public void uiStateChange(final UIStateChangeEvent evt) {
		super.uiStateChange(evt);

		if (hasPropertyKey(Constants.PR_FONT_SIZE))
		{
			if (uiComponent instanceof WiseCombobox)
			{
				final WiseCombobox ui = (WiseCombobox) uiComponent;
				Object sizevalue = evt.getNewValue();
				if (sizevalue instanceof PercentLength)
				{
					final PercentLength perlen = (PercentLength) sizevalue;
					final PercentBase perbase = perlen.getBaseLength();
					if (perbase instanceof LengthBase)
					{
						sizevalue = ((LengthBase) perbase).getBaseLength();
					}
				}
				if (sizevalue instanceof FixedLength)
				{
					final FixedLength value = (FixedLength) sizevalue;
					double doublevalue = value.getNumericValue() / 1000;
					String s = TranslationTableResource.searchKey(doublevalue);
					if (isEmpty(s))
					{
						s = doublevalue + "";
					}
					if (s.endsWith(".0"))
					{
						s = s.substring(0, s.length() - 2);
					}
					ui.InitValue(s);
				}
				if (evt.getNewValue() == Constants.NULLOBJECT)
				{
					ui.InitValue(null);
				}
			}
		}
	}

	@Override
	public void setDefaultState(final ActionCommandType actionCommand)
	{
		super.setDefaultState(null);
		if (actionCommand == ActionCommandType.RIBBON_ACTION)
		{
			if (uiComponent instanceof WiseCombobox)
			{
				final WiseCombobox ui = (WiseCombobox) uiComponent;
				final String s = UiText.FONT_SIZE_LIST[10];
				ui.InitValue(s);
			}
		}

		if (actionCommand == ActionCommandType.DIALOG_ACTION)
		{
			for (final List<Object> list : RibbonUIManager.getUIComponents()
					.get(this.actionID).get(actionCommand))
			{
				for (final Object object : list)
				{
					if (object instanceof WiseCombobox)
					{
						final WiseCombobox ui = (WiseCombobox) object;
						Object value = UiText.FONT_SIZE_LIST[10];
						Map<Integer, Object> atts = RibbonUIModel
								.getCurrentPropertiesByType().get(
										this.actionType);
						if (atts != null&&atts.containsKey(Constants.PR_FONT_SIZE))
						{
							value = atts.get(Constants.PR_FONT_SIZE);
						}
						if (value.equals(Constants.NULLOBJECT))
						{
							final JTextComponent editor = (JTextComponent) ui
									.getEditor().getEditorComponent();
							editor.setText("");
						} else
						{
							/* ui.setSelectedItem(value); */
							FixedLength length = null;
							if (value instanceof PercentLength)
							{
								final PercentLength perlen = (PercentLength) value;
								final Length baselen = ((LengthBase) perlen
										.getBaseLength()).getBaseLength();
								if (baselen instanceof FixedLength)
								{
									length = (FixedLength) baselen;
								}
							} else if (value instanceof FixedLength)
							{
								length = (FixedLength) value;
							}
							if (length == null)
							{
								return;
							}
							double doublevalue = length.getNumericValue() / 1000;
							String s = TranslationTableResource
									.searchKey(doublevalue);
							if (isEmpty(s))
							{
								s = doublevalue + "";
							}
							if (s.endsWith(".0"))
							{
								s = s.substring(0, s.length() - 2);
							}
							ui.InitValue(s);
						}
					}
				}
			}
		}

		if (actionCommand == ActionCommandType.DYNAMIC_ACTION)
		{
			for (final List<Object> list : RibbonUIManager.getUIComponents()
					.get(this.actionID).get(actionCommand))
			{
				for (final Object object : list)
				{
					if (object instanceof WiseCombobox)
					{
						final WiseCombobox ui = (WiseCombobox) object;
						final Object value = FontPropertyPanel
								.getInitialPropertise().get(
										Constants.PR_FONT_SIZE);
						if (value.equals(Constants.NULLOBJECT))
						{
							final JTextComponent editor = (JTextComponent) ui
									.getEditor().getEditorComponent();
							editor.setText("");
						} else
						{
							/* ui.setSelectedItem(value); */
							FixedLength length = null;
							if (value instanceof PercentLength)
							{
								final PercentLength perlen = (PercentLength) value;
								final Length baselen = ((LengthBase) perlen
										.getBaseLength()).getBaseLength();
								if (baselen instanceof FixedLength)
								{
									length = (FixedLength) baselen;
								}
							} else if (value instanceof FixedLength)
							{
								length = (FixedLength) value;
							}
							if (length == null)
							{
								return;
							}
							double doublevalue = length.getNumericValue() / 1000;
							String s = TranslationTableResource
									.searchKey(doublevalue);
							if (isEmpty(s))
							{
								s = doublevalue + "";
							}
							if (s.endsWith(".0"))
							{
								s = s.substring(0, s.length() - 2);
							}
							ui.InitValue(s);
						}
					}
				}
			}
		}
	}

	@Override
	public void setDifferentPropertyState(final UIStateChangeEvent evt) {
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_FONT_SIZE)) {
			if (uiComponent instanceof WiseCombobox) {
				final WiseCombobox ui = (WiseCombobox) uiComponent;
				ui.InitValue(null);
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
