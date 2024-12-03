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
 * @TimeOutputSetAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.actions.datetime;

import java.awt.event.ActionEvent;

import com.wisii.wisedoc.document.attribute.DateInfo;
import com.wisii.wisedoc.document.attribute.TimeInfo;
import com.wisii.wisedoc.document.attribute.WisedocDateTimeFormat;
import com.wisii.wisedoc.swing.ui.WiseCombobox;

/**
 * 类功能描述：时间输出格式设置事件
 * 
 * 作者：zhangqiang 创建日期：2008-12-2
 */
public class TimeOutputSetAction extends AbstractDateTimeInfoSetAction
{

	public TimeOutputSetAction()
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.view.ui.actions.Actions#doAction(java.awt.event.ActionEvent
	 * )
	 */
	protected WisedocDateTimeFormat createformat(ActionEvent e)
	{
		if (e.getSource() instanceof WiseCombobox)
		{
			WiseCombobox box = (WiseCombobox) e.getSource();
			int index = box.getSelectedIndex();
			if (index == 0)
			{
				DateInfo outdate = oldformat.getDateout();
				if (outdate != null)
				{
					return new WisedocDateTimeFormat(oldformat.getDatein(),
							oldformat.getTimein(), outdate, (TimeInfo) null,
							oldformat.getInputseperate(), (String) null,
							oldformat.isIndatefirst(), oldformat
									.isOutdatefirst());
				} else
				{
					box.InitValue(oldformat.getTimeout());
					return oldformat;
				}
			} else
			{
				return new WisedocDateTimeFormat(oldformat.getDatein(),
						oldformat.getTimein(), oldformat.getDateout(),
						(TimeInfo) box.getSelectedItem(), oldformat
								.getInputseperate(),
						oldformat.getOutseperate(), oldformat.isIndatefirst(),
						oldformat.isOutdatefirst());
			}

		}
		return null;

	}

	protected void UpDateUI()
	{
		if (uiComponent instanceof WiseCombobox)
		{
			WiseCombobox box = (WiseCombobox) uiComponent;
			if (oldformat == null)
			{
				oldformat = createDefaultFormat();
			}
			TimeInfo outtimeinfo = oldformat.getTimeout();
			if (outtimeinfo == null)
			{
				box.initIndex(0);
			} else
			{
				box.InitValue(outtimeinfo);
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
		if (oldformat != null)
		{
			return oldformat.getTimein() != null;
		} else
		{
			return false;
		}
	}
}
