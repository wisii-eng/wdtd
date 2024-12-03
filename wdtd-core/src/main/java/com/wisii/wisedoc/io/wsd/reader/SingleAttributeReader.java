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
 * @AbstractAttributeReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.Map;

import com.wisii.wisedoc.io.AttributeReader;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2008-10-9
 */
public abstract class SingleAttributeReader extends AttributeReader {

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.io.AttributeReader#read(int, java.lang.String)
	 */
	@Override
	public abstract Object read(int key, String value);

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.io.AttributeReader#read(int, java.util.Map)
	 */
	@Override
	public Object read(int key, Map<String, String> values) {
		// TODO Auto-generated method stub
		return null;
	}

}
