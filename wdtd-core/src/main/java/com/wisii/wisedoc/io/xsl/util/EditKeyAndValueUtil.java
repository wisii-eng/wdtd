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

package com.wisii.wisedoc.io.xsl.util;

import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;

public class EditKeyAndValueUtil
{

	private static Map<Integer, String> keyMap;

	public static void initializationKeyMap()
	{
		keyMap = new HashMap<Integer, String>();
		keyMap.put(Constants.PR_INPUT_TYPE, "type");
		keyMap.put(Constants.PR_INPUT_MULTILINE, "multiline");
		keyMap.put(Constants.PR_INPUT_WRAP, "warp");
		keyMap.put(Constants.PR_INPUT_FILTER, "filter");
		keyMap.put(Constants.PR_DATE_TYPE, "type");
		keyMap.put(Constants.PR_DATE_FORMAT, "dataFormat");
		keyMap.put(Constants.PR_RADIO_CHECK_VALUE, "selectValue");
		keyMap.put(Constants.PR_RADIO_CHECK_CHECKED, "checked");
		keyMap.put(Constants.PR_SELECT_MULTIPLE, "multiple");
		keyMap.put(Constants.PR_SELECT_LINES, "lines");
		keyMap.put(Constants.PR_SELECT_ISEDIT, "isEdit");
		keyMap.put(Constants.PR_SELECT_NEXT, "next");
		keyMap.put(Constants.PR_SELECT_SHOWLIST, "showList");
		keyMap.put(Constants.PR_ISRELOAD, "isReload");
		keyMap.put(Constants.PR_APPEARANCE, "appearance");
		keyMap.put(Constants.PR_EDIT_WIDTH, "width");
		keyMap.put(Constants.PR_EDIT_HEIGHT, "height");
		keyMap.put(Constants.PR_HINT, "hint");
		keyMap.put(Constants.PR_ONBLUR, "onBlur");
		keyMap.put(Constants.PR_ONSELECTED, "onSelected");
		keyMap.put(Constants.PR_ONKEYPRESS, "onKeyPress");
		keyMap.put(Constants.PR_ONKEYDOWN, "onKeyDown");
		keyMap.put(Constants.PR_ONKEYUP, "onKeyUp");
		keyMap.put(Constants.PR_ONCHANGE, "onChange");
		keyMap.put(Constants.PR_ONCLICK, "onClick");
		keyMap.put(Constants.PR_ONEDIT, "onEdit");
		keyMap.put(Constants.PR_ONRESULT, "onResult");
		keyMap.put(Constants.PR_BUTTON_TYPE, "type");
		keyMap.put(Constants.PR_BUTTON_INSERT_POSITION, "insert");
		keyMap.put(Constants.PR_CONN_WITH, "conn");

		keyMap.put(Constants.PR_CHECKBOX_UNSELECT_VALUE, "unselectedValue");
		keyMap.put(Constants.PR_CHECKBOX_BOXSTYLE, "boxStyle");
		keyMap.put(Constants.PR_CHECKBOX_TICKMARK, "tickMark");
		keyMap.put(Constants.PR_SELECT_TYPE, "type");
		keyMap.put(Constants.PR_SELECT_INFO, "src");
		keyMap.put(Constants.PR_POPUPBROWSER_INFO, "srcbrowser");

		keyMap.put(Constants.PR_GROUP_MAX_SELECTNUMBER, "maxSelected");
		keyMap.put(Constants.PR_GROUP_MIN_SELECTNUMBER, "minSelected");
		keyMap.put(Constants.PR_GROUP_REFERANCE, "groupReference");
		keyMap.put(Constants.PR_GROUP_NONE_SELECT_VALUE, "noneSelectValue");
		
		keyMap.put(Constants.PR_INPUT_FILTERMSG, "filterMsg");
		keyMap.put(Constants.PR_BUTTON_NODATAXPTH, "nodataxpath");


	}

	public static String getKeyName(int key)
	{
		String result = keyMap.get(key);
		return result != null ? result : "";
	}

	public static void clearMap()
	{
		keyMap = null;
	}

//	public static String getPath(int width, int height, int orientation)
//	{
//		String path = "";
//		double tanwh = height / width;
//		int qishix = 0;
//		int qishiy = 0;
//		int zhongzhix = 0;
//		int zhongzhiy = 0;
//		if (orientation == -180)
//		{
//			qishix = width;
//			qishiy = height;
//			zhongzhiy = height;
//		} else if (orientation > -180 && orientation < -90)
//		{
//			qishix = width;
//			qishiy = height;
//			orientation = orientation + 180;
//			double tan = Math.tan(Math.PI * orientation / 180);
//			if (tanwh >= tan)
//			{
//				zhongzhiy = new Double(height - width * tan).intValue();
//			} else
//			{
//				zhongzhix = new Double(width - height / tan).intValue();
//			}
//		} else if (orientation == -90)
//		{
//			qishiy = height;
//		} else if (orientation > -90 && orientation < 0)
//		{
//			qishix = 0;
//			qishiy = height;
//			orientation = Math.abs(orientation);
//			double tan = Math.tan(Math.PI * orientation / 180);
//			if (tanwh >= tan)
//			{
//				zhongzhix = width;
//				zhongzhiy = new Double(height - width * tan).intValue();
//			} else
//			{
//				zhongzhix = new Double(height / tan).intValue();
//			}
//		} else if (orientation == 0)
//		{
//			qishiy = height;
//			zhongzhix = width;
//			zhongzhiy = height;
//		} else if (orientation > 0 && orientation < 90)
//		{
//			qishix = 0;
//			qishiy = 0;
//			double tan = Math.tan(Math.PI * orientation / 180);
//			if (tanwh >= tan)
//			{
//				zhongzhix = width;
//				zhongzhiy = new Double(width * tan).intValue();
//			} else
//			{
//				zhongzhix = new Double(height / tan).intValue();
//				zhongzhiy = height;
//			}
//		} else if (orientation == 90)
//		{
//			zhongzhiy = height;
//		} else if (orientation > 90 && orientation < 180)
//		{
//			qishix = width;
//			qishiy = 0;
//			orientation = 180 - orientation;
//			double tan = Math.tan(Math.PI * orientation / 180);
//			if (tanwh >= tan)
//			{
//				zhongzhiy = new Double(width * tan).intValue();
//			} else
//			{
//				zhongzhix = new Double(width - height / tan).intValue();
//				zhongzhiy = height;
//			}
//		}
//		path = "M" + qishix + "," + qishiy + " L " + zhongzhix + " "
//				+ zhongzhiy;
//		return path;
//	}

//	public static void main(String[] args)
//	{
//		System.out.println(getPath(1000, 1000, -150));
//		System.out.println(getPath(1000, 1000, -60));
//		System.out.println(getPath(1000, 1000, 30));
//		System.out.println(getPath(1000, 1000, 120));
//	}
}
