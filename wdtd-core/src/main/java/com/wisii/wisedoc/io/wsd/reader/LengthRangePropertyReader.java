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
 * @LengthRangePropertyReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumLength;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-10-10
 */
public class LengthRangePropertyReader extends CompoundAttributeReader
{
	private final String MIN = "min";
	private final String MAX = "max";
	private final String OPTIMUM = "optimun";
	protected MixLengthReader mixlengthreader = new MixLengthReader();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.wsd.reader.CompoundAttributeReader#read(int,
	 * java.util.Map)
	 */
	public Object read(int key, Map<String, String> values)
	{
		if (values != null)
		{
			String smin = values.get(MIN);
			String smax = values.get(MAX);
			String soptimun = values.get(OPTIMUM);
			LengthProperty minlen = new EnumLength(Constants.EN_AUTO,
					new FixedLength(0));
			LengthProperty maxlen = new EnumLength(Constants.EN_AUTO,
					new FixedLength(0));
			LengthProperty optlen = new EnumLength(Constants.EN_AUTO,
					new FixedLength(0));
			if (smin != null)
			{
				Object lenobj = mixlengthreader.read(key, smin);
				if (lenobj instanceof LengthProperty)
				{
					minlen = (LengthProperty) lenobj;
				}
			}
			if (smax != null)
			{
				Object lenobj = mixlengthreader.read(key, smax);
				if (lenobj instanceof LengthProperty)
				{
					maxlen = (LengthProperty) lenobj;
				}
			}
			if (soptimun != null)
			{
				Object lenobj = mixlengthreader.read(key, soptimun);
				if (lenobj instanceof LengthProperty)
				{
					optlen = (LengthProperty) lenobj;
				}
			}
			return new LengthRangeProperty(minlen, optlen, maxlen);
		}
		return null;
	}

}
