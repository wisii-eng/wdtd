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
package com.wisii.wisedoc.view.ui.actions.barcode;

import java.awt.event.ActionEvent;

import javax.swing.JTextField;

import com.wisii.wisedoc.document.BarCodeText;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionCommandType;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.UIStateChangeEvent;

/**
 * 条形码内容输入动作
 * @author 闫舒寰
 * @version 1.0 2009/02/02
 */
public class BarcodeInputContentAction extends Actions
{

	@Override
	public void doAction(ActionEvent e)
	{
		if (e.getSource() instanceof JTextField)
		{
			JTextField value = (JTextField) e.getSource();

			int barcodeType = ((EnumProperty) RibbonUIModel
					.getCurrentPropertiesByType().get(this.actionType).get(
							Constants.PR_BARCODE_TYPE)).getEnum();
			BarCodeText oldcontent = (BarCodeText) RibbonUIModel
					.getCurrentPropertiesByType().get(this.actionType).get(
							Constants.PR_BARCODE_CONTENT);
			String valuetext = value.getText();
			if(valuetext != null)
			{
				valuetext = valuetext.trim(); 
			}
			// 如果输入值不合法，则还原为输入前的值
			if (!VerifyContent(barcodeType, valuetext))
			{
				value.requestFocus();
				if (oldcontent != null)
				{
					value.setText(oldcontent.toString());
				}
				return;
			}
			setFOProperty(Constants.PR_BARCODE_CONTENT, new BarCodeText(
					valuetext));
		}
	}

	/**
	 * 
	 * 根据校验类型判断条码的输入值是否合法
	 *
	 * @param      
	 * @return      boolean:true，则输入值合法，false：输入值不合法
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	private final boolean VerifyContent(int barcodeType, String value)
	{
		if (value == null || value.isEmpty())
		{
			return false;
		}
		if (barcodeType == Constants.EN_UPCE
				|| barcodeType == Constants.EN_EAN8)
		{
			if (value.length() == 7 && isAllNumber(value))
			{
				return true;
			} else if (value.length() == 8)
			{
				return checksum(value);
			} else
			{
				return false;
			}

		} 
		else if (barcodeType == Constants.EN_UPCA
				|| barcodeType == Constants.EN_EAN13)
		{
			if (value.length() == 11 && isAllNumber(value))
			{
				return true;
			} else if (value.length() == 12)
			{
				return checksum(value);
			} else
			{
				return false;
			}
		} else
		{
			return true;
		}
	}

	private boolean checksum(String value)
	{
		if (value != null)
		{
			char[] chars = value.toCharArray();
			int sum = 0;
			int checksum = 0;
			for (int i = 0; i < chars.length; i++)
			{
				// 如果不是数字，则直接返回假
				if (chars[i] < '0' && chars[i] > '9')
				{
					return false;
				}
				int number = chars[i] - '0';
				// 偶数为
				if (i == chars.length - 1)
				{
					checksum = number;
				} else if (i % 2 == 0)
				{
					sum = sum + number * 3;
				} else
				{
					sum = sum + number;
				}
			}
			int mod = sum % 10;
			if (mod == 0)
			{
				return checksum == 0;
			} else
			{
				return checksum + mod == 10;
			}

		}
		return false;
	}

	private final boolean isAllNumber(String value)
	{
		if (value != null)
		{
			char[] chars = value.toCharArray();
			for (int i = 0; i < chars.length; i++)
			{
				// 如果不是数字，则直接返回假
				if (chars[i] < '0' && chars[i] > '9')
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	protected void uiStateChange(UIStateChangeEvent evt)
	{
		super.uiStateChange(evt);

		if (hasPropertyKey(Constants.PR_BARCODE_CONTENT))
		{
			Object obj = evt.getNewValue();
			if (uiComponent instanceof JTextField)
			{
				JTextField ui = (JTextField) uiComponent;
				if (obj instanceof BarCodeText)
				{
					BarCodeText value = (BarCodeText) obj;
					ui.setText(value.toString());
				}
			}
		}
	}

	@Override
	protected void setDifferentPropertyState(UIStateChangeEvent evt)
	{
		super.setDifferentPropertyState(evt);
		if (evt.hasPropertyKey(Constants.PR_BARCODE_CONTENT))
		{
			if (uiComponent instanceof JTextField)
			{
				JTextField ui = (JTextField) uiComponent;
				ui.setText("");
			}
		}
	}

	@Override
	public void setDefaultState(ActionCommandType actionCommand)
	{
		super.setDefaultState(null);
		if (uiComponent instanceof JTextField)
		{
			JTextField ui = (JTextField) uiComponent;
			ui.setText("请输入条形码内容");
		}
	}

	@Override
	public boolean isAvailable()
	{
		return super.isAvailable();
	}

}
