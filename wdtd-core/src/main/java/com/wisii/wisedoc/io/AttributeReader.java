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
 * @AttributeReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io;

import java.util.Map;

import com.wisii.wisedoc.io.wsd.reader.AbstractElementsHandler;

/**
 * 类功能描述：字符串转换成属性对象。
 * 在读wsd文件生成Document对象时会被调用
 *
 * 作者：zhangqiang
 * 创建日期：2008-8-14
 */
public abstract class AttributeReader
{
	protected AbstractElementsHandler wsdhandler;
	/**
	 * 
	 * 根据属性类型，属性字符串值生成属性对象
	 *
	 * @param   key：属性类型关键字，是AttributeConstants类中定义的静态变量
	 *           value：属性的字符串值
	 * @return  属性对象    
	 * @exception   
	 */
	public abstract  Object read(int key,String value);
	/**
	 * 
	 * 根据属性类型，属性字符串值生成属性对象
	 * 该方法是针对复合属性的读方法，如space-before.precedence， space-before.Conditionality,类属性的读取方法
	 *
	 * @param   key：属性类型关键字，是AttributeConstants类中定义的静态变量
	 *           value：属性的字符串值
	 * @return  属性对象    
	 * @exception   
	 */
	public abstract Object read(int key,Map<String,String> values);
	public void initWsdHandler(AbstractElementsHandler wsdhandler)
	{
		this.wsdhandler=wsdhandler;
	}
	
}
