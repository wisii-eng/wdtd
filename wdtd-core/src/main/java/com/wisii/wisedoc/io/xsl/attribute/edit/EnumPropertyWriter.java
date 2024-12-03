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

package com.wisii.wisedoc.io.xsl.attribute.edit;

import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.io.xsl.attribute.AttributeValueWriter;

public class EnumPropertyWriter implements AttributeValueWriter
{

	public static String WDWEMSNS = "wdems:";

	@Override
	public String write(Object value)
	{
		String code = "";
		if (value != null && value instanceof EnumProperty)
		{
			EnumProperty ep = (EnumProperty) value;
			code = getValue(ep.getEnum());
		}
		return code;
	}

	private static Map<Integer, String> valueMap;

	public static void initializationValueMap()
	{
		valueMap = new HashMap<Integer, String>();
		valueMap.put(Constants.EN_INPUT, WDWEMSNS + "input");
		valueMap.put(Constants.EN_SELECT, WDWEMSNS + "select");
		valueMap.put(Constants.EN_POPUPBROWSER, WDWEMSNS + "popupbrowser");
		valueMap.put(Constants.EN_DATE, WDWEMSNS + "date");
		valueMap.put(Constants.EN_CHECKBOX, WDWEMSNS + "checkbox");
		valueMap.put(Constants.EN_GRAPHIC, WDWEMSNS + "graphic");
		valueMap.put(Constants.EN_BUTTON, WDWEMSNS + "button");
		valueMap.put(Constants.EN_GROUP, WDWEMSNS + "group");
		valueMap.put(Constants.EN_TEXT, "text");
		valueMap.put(Constants.EN_PASSWORD, "password");
		valueMap.put(Constants.EN_SORT_P, "p");
		valueMap.put(Constants.EN_SORT_N, "n");
		valueMap.put(Constants.EN_SORT_C, "c");
		valueMap.put(Constants.EN_DATE_TYPE_C, "c");
		valueMap.put(Constants.EN_DATE_TYPE_T, "t");
		valueMap.put(Constants.EN_FULL, "full");
		valueMap.put(Constants.EN_COMPACT, "compact");
		valueMap.put(Constants.EN_MINIMAL, "minimal");
		valueMap.put(Constants.EN_TRUE, "true");
		valueMap.put(Constants.EN_FALSE, "false");

		valueMap.put(Constants.EN_CHECKBOX_BOXSTYLE_CIRCLE, "circle");
		valueMap.put(Constants.EN_CHECKBOX_BOXSTYLE_SQUARE, "square");
		valueMap.put(Constants.EN_CHECKBOX_TICKMARK_TICK, "tick");
		valueMap.put(Constants.EN_CHECKBOX_TICKMARK_DOT, "dot");
		valueMap.put(Constants.EN_TABLE1, "table1");
		valueMap.put(Constants.EN_TABLE2, "table2");
		valueMap.put(Constants.EN_TREE, "tree");
		valueMap.put(Constants.EN_DATASOURCE_SQ, "sq");
		valueMap.put(Constants.EN_DATASOURCE_EQ, "eq");
		valueMap.put(Constants.EN_DATASOURCE_VERT, "vert");

		valueMap.put(Constants.EN_COMBOBOX, "combobox");
		valueMap.put(Constants.EN_LIST, "list");
		valueMap.put(Constants.EN_CHECK, "check");
		valueMap.put(Constants.EN_UNCHECK, "uncheck");

		// valueMap.put(Constants.EN_CONNECT_WITH, "conn");

	}

	public static void cleanKeyMap()
	{
		valueMap = new HashMap<Integer, String>();
	}

	public static String getValue(int value)
	{
//		System.out.println("int:" + value);
//		System.out.println("valueMap:" + (valueMap == null));
//		System.out.println("vulue:" + valueMap.get(value));
		String result = valueMap.get(value) != null ? (String) valueMap
				.get(value) : "";
		return result;
	}
}
