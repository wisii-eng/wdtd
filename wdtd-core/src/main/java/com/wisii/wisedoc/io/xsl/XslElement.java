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
 * @XslElementObj.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl;

import java.util.List;
import java.util.Map;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-8-27
 */
public interface XslElement
{
	/**
	 * 
	 * 添加子节点
	 * 
	 * @param newChild：要添加的节点
	 * @return
	 * @exception
	 */
	public void add(XslElementObj newChild);

	/**
	 * 
	 * 添加子节点
	 * 
	 * @param newChild：要添加的节点
	 * @return
	 * @exception
	 */
	public void add(List<XslElementObj> newChildren);

	/**
	 * 
	 * 获得所有子对象
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	List<XslElementObj> getChildren();

	/**
	 * 获得属性map
	 * 
	 * @param {引入参数名}
	 *            {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	Map<Integer, Object> getAttributeMap();

}
