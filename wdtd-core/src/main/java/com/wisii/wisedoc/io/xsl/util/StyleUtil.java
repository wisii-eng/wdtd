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
 * @StyleUtil.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl.util;
import java.util.Iterator;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.Attributes;
/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2012-5-29
 */
public class StyleUtil
{
	// 判断属性key是否为样式key,如PR_GROUP就是非样式key
	public static boolean isStyleKey(int prkey)
	{
		return prkey > 0 && prkey < Constants.PR_X_BLOCK_PROGRESSION_UNIT;
	}

	public static boolean hasStylekey(Attributes atts)
	{
		if (atts == null)
		{
			return false;
		}
		Iterator<Integer> keys = atts.getAttributeKeys();
		while (keys.hasNext())
		{
			Integer key = keys.next();
			if (isStyleKey(key))
			{
				return true;
			}
		}
		return false;
	}
}
