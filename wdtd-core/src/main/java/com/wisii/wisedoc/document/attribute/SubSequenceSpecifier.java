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
 * SubSequenceSpecifier.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

/**
 * 
 * 类功能描述：布局引用声明类
 * 
 * 作者：zhangqiang 创建日期：2008-8-26
 */
public interface SubSequenceSpecifier
{

	/**
	 * 
	 * 根据当前页的相关信息返回页布局
	 * 
	 * @param 引入参数名} {引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	SimplePageMaster getNextPageMaster(boolean isOddPage, boolean isFirstPage,
			boolean isLastPage, boolean isBlankPage);

	/**
	 * 
	 * 重新初始化相关信息，则重新排版时调用
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void reset();

	/**
	 * 
	 * 返回前一个布局声明，返回成功则返回true
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	boolean goToPrevious();

	/**
	 * 
	 * 是否能够处理最后一页的页布局
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	boolean hasPagePositionLast();

	public SubSequenceSpecifier clone();
}
