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

import com.wisii.wisedoc.io.xsl.attribute.AttributeValueWriter;

public interface EditAttributeFactory
{

	/**
	 * 
	 * 根据属性关键字获得属性对象的writer
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public AttributeValueWriter getAttributeWriter(int key);

	/**
	 * 
	 * 根据数据类型获得对应的writer
	 * 
	 * @param clazs
	 *            ：属性类型
	 * @return
	 * @exception
	 */
	public AttributeValueWriter getAttributeWriter(Object value);
}
