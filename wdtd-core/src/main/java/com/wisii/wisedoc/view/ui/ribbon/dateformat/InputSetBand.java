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
 * @InputSetBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.dateformat;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentUtil;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.document.attribute.WisedocDateTimeFormat;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.action.BaseAction;
import com.wisii.wisedoc.view.ui.ribbon.WiseBand;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：日期时间输入设置面板
 *
 * 作者：zhangqiang
 * 创建日期：2008-11-28
 */
public class InputSetBand implements WiseBand
{
    private DateTimeFormatSetPanel inputsetpanel;
	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.view.ui.ribbon.WiseBand#getBand()
	 */
	public JRibbonBand getBand()
	{
		JRibbonBand inputSetBand = new JRibbonBand(RibbonUIText.DATE_TIME_INPUT_BAND, MediaResource.getResizableIcon("09379.ico"),new DetailSet());
		inputsetpanel = new DateTimeFormatSetPanel(false);
//		inputSetBand.addPanel(inputsetpanel);
		//以Ribbon组件对象包装当前组件，然后以流式排版排列
		JRibbonComponent inputsetpanelWrapper = new JRibbonComponent(inputsetpanel);
		inputSetBand.addRibbonComponent(inputsetpanelWrapper);
		return inputSetBand;
	}
	private class DetailSet extends BaseAction
	{
		public void doAction(ActionEvent e)
		{
			WisedocDateTimeFormat oldformat = null;
			List<CellElement> elements = getObjectSelects();
			Document doc = getCurrentDocument();
			if (elements!=null&&!elements.isEmpty())
			{
				// 获得当前选中对象的属性
				Map<Integer, Object> attrs = DocumentUtil
						.getElementAttributes(elements);
				if (attrs != null)
				{
					// 获得当前条件属性
					Object format = attrs.get(Constants.PR_DATETIMEFORMAT);
					// 如果条件属性不为不相等表示对象，则赋值
					if (!Constants.NULLOBJECT.equals(format))
					{
						oldformat = (WisedocDateTimeFormat) format;
					}
				}
				if(oldformat==null)
				{
					oldformat = (WisedocDateTimeFormat) InitialManager.getInitialValue(Constants.PR_DATETIMEFORMAT, null);
				}
			}
			else
			{
				return;
			}
			DateTimeSetDialog dtsetdia = new DateTimeSetDialog(oldformat,true);
			DialogResult result = dtsetdia.showDialog();
			if (DialogResult.OK.equals(result))
			{
				WisedocDateTimeFormat newdatetimeformat = dtsetdia
						.getDatetimeformat();

				if (doc == null || newdatetimeformat == null)
				{
					return;
				}
				inputsetpanel.addformateinfo(newdatetimeformat);
				Map<Integer, Object> newatts = new HashMap<Integer, Object>();
				newatts.put(Constants.PR_DATETIMEFORMAT, newdatetimeformat);

				if (elements != null)
				{
					doc.setElementAttributes(elements, newatts, false);
				}

			}

		}}
}
