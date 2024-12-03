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
 * @AttributeWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io;

/**
 * 类功能描述：写属性接口
 *
 * 作者：zhangqiang
 * 创建日期：2008-8-14
 */
public interface AttributeWriter
{
	public static final String TEXTSPLIT = "@!#,";
	public static final String NULL = "nullkong";
	/**
	 * 
	 * 根据属性类型，属性值生成属性字符串
	 *
	 * @param    key：属性类型，value：属性值
	 * @return   
	 * @exception   
	 */
	String write(int key,Object value);
}
