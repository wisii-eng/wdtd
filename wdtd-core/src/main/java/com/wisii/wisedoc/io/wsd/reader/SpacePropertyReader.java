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
 * @SpacePropertyReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-10-9
 */
public class SpacePropertyReader extends LengthRangePropertyReader {
	private final String CONDITIONALITY = "Conditionality";
	private final String PRECEDENCE = "precedence";
	private EnumNumberReader enumnumreader = new EnumNumberReader();

	public Object read(int key, Map<String, String> values)
	{
		if (values != null)
		{
			Object lengthpair = super.read(key, values);
			if (lengthpair != null)
			{
				EnumProperty conenum = new EnumProperty(Constants.EN_DISCARD,
						"");
				EnumNumber precedence = new EnumNumber(-1, 0);
				String con = values.get(CONDITIONALITY);
				if (con != null)
				{
					Integer conint = enumnumreader.getEnumKey(con);
					if (conint > -1)
					{
						conenum = new EnumProperty(conint, con);
					}
				}
				String pre = values.get(CONDITIONALITY);
				if (pre != null)
				{
					Object prec = enumnumreader.read(key, pre);
					if (prec != null)
					{
						precedence = (EnumNumber) prec;
					}
				}
				if (lengthpair instanceof LengthRangeProperty)
				{
					LengthRangeProperty lr = (LengthRangeProperty) lengthpair;
					return new SpaceProperty((LengthProperty) lr
							.getMinimum(null), (LengthProperty) lr
							.getOptimum(null), (LengthProperty) lr
							.getMaximum(null), precedence, conenum);
				}
			} else
			{
				LogUtil.debug("读取space属性时出错" + "key:" + key + "value" + values);
			}
		}
		return null;
	}
}
