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

package com.wisii.wisedoc.view.ui.model;

import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;

public enum ParagraphPropertyModel
{
	ParagraphProperties;

	private static Map<Integer, Object> paragraphProperties = new HashMap<Integer, Object>();

	/**
	 * 供设置属性结束时调用，这个方法会清空该属性集合
	 * 
	 * @return
	 */
	public static Map<Integer, Object> getFinalProperties()
	{
		Map<Integer, Object> temp = new HashMap<Integer, Object>();
		temp.putAll(paragraphProperties);
		paragraphProperties.clear();
		return temp;
	}

	/**
	 * 清空当前设置的属性，当用户点击“取消”按钮的时候调用
	 */
	public static void clearProperty()
	{
		paragraphProperties.clear();
	}

	public static void setFOProperties(Map<Integer, Object> paragraphProperties)
	{
		ParagraphPropertyModel.paragraphProperties.putAll(paragraphProperties);
	}

	public static void setFOProperty(int propertyId, Object propertyValue)
	{

		ParagraphPropertyModel.paragraphProperties.put(propertyId,
				propertyValue);
	}

	public static Object getCurrentProperty(int propertyID)
	{

		if (!paragraphProperties.containsKey(propertyID))
		{
			Map<Integer, Object> paramap = RibbonUIModel
					.getCurrentPropertiesByType().get(ActionType.BLOCK);
			if (paramap != null)
			{
				paragraphProperties.put(propertyID, paramap.get(propertyID));
			}
		}
		return paragraphProperties.get(propertyID);
	}

	/**
	 * 添加foProperty到
	 * 
	 * @param paragraphProperties
	 */
	public static void addFOProperties(Map<Integer, Object> paragraphProperties)
	{
		ParagraphPropertyModel.paragraphProperties.putAll(paragraphProperties);
	}
}
