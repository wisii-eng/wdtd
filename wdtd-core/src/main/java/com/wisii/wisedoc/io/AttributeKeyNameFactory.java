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
 * @AttributeKeyNameFactory.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io;

/**
 * 类功能描述：属性关键字和属性输出字符串直接对应关系映射类
 *
 * 作者：zhangqiang
 * 创建日期：2008-8-14
 */
public interface AttributeKeyNameFactory
{
	/**
	 * 
	 *获得指定属性类型的输出字符串。
	 *
	 * @param   
	 * @return   
	 * @exception  
	 */
	String getKeyName(int key);

	/**
	 * 
	 * 根据字符串返回对应属性关键字
	 *
	 * @param      name：属性名称
	 * @return     
	 * @exception  
	 */
	int getKey(String name);
}
