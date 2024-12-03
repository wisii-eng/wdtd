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
 * @XslElementIFWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-8-27
 */
public interface XslElementIFWriter
{
	/**
	 * 
	 * 返回该对象对应的xsl模板代码
	 * 
	 */
	String getCode();

	/**
	 * 
	 * 返回元素头部的模板代码
	 * 
	 * 
	 */
	String getHeaderElement();

	/**
	 * 
	 * 返回元素属性部分的模板代码
	 * 
	 */
	String getAttributes();

	/**
	 * 
	 * 返回元素名
	 * 
	 */
	String getElementName();

	 /**
		 * 
		 * 返回元素某一个属性的模板代码
		 * 
		 * @param key
		 *            属性类型
		 * @param value
		 *            属性值
		 * @return 返回元素某一个属性的模板代码
		 * 
		 */
	public String getAttibute(int key, Object value);

	/**
	 * 
	 * 返回元素结束部分的模板代码
	 * 
	 * 
	 */
	String getEndElement();

	/**
	 * 
	 * 返回元素头部属性之前部分的模板代码
	 * 
	 */
	String getHeaderStart();

	/**
	 * 
	 * 返回元素头部属性之后的模板代码
	 * 
	 * 
	 */
	String getHeaderEnd();

	/**
	 * 
	 * 返回元素的子元素的模板代码
	 * 
	 * @param {引入参数名}
	 *            {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	String getChildCode();
}
