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
 * @InputSeperatorAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.datetime;

import java.awt.event.ActionEvent;

import javax.swing.JTextField;

import com.wisii.wisedoc.document.attribute.WisedocDateTimeFormat;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-12-2
 */
public class InputSeperatorAction extends AbstractDateTimeInfoSetAction
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.view.ui.actions.datetime.AbstractDateTimeInfoSetAction
	 * #UpDateUI()
	 */
	@Override
	protected void UpDateUI()
	{
		if (uiComponent instanceof JTextField)
		{
			JTextField tf = (JTextField) uiComponent;
			String text = oldformat.getInputseperate();
			if (text != null)
			{
				tf.setText(text);
			} else
			{
				tf.setText("");
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.view.ui.actions.datetime.AbstractDateTimeInfoSetAction
	 * #createformat(java.awt.event.ActionEvent)
	 */
	@Override
	protected WisedocDateTimeFormat createformat(ActionEvent e)
	{
		if (oldformat == null)
		{
			oldformat = createDefaultFormat();
		}
		if (e.getSource() instanceof JTextField)
		{
			String text = ((JTextField) e.getSource()).getText();
			return new WisedocDateTimeFormat(oldformat.getDatein(), oldformat
					.getTimein(), oldformat.getDateout(), oldformat
					.getTimeout(), text, oldformat.getOutseperate());
		}
		return null;
	}
	@Override
	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		if (oldformat != null)
		{
			return oldformat.getDatein() != null&&oldformat.getTimein()!=null;
		} else
		{
			return false;
		}
	}
}
