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
 * @SqlExcuteException.java 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.db.exception;

import com.wisii.exception.WiseDocException;
import com.wisii.wisedoc.view.ui.SQLItemNode;

/**
 * 类功能描述： sql语句语法错误异常，在sql语句执行出错时弹出 作者：zhangqiang 创建日期：2010-10-28
 */
public class SqlExecuteException extends WiseDocException
{
	private String errorsql;
	private SQLItemNode node;

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public SqlExecuteException(String errorsql, SQLItemNode node,
			Throwable cause)
	{
		super(cause);
		this.errorsql = errorsql;
		this.node = node;
	}

	/**
	 * @返回 errorsql变量的值
	 */
	public String getErrorsql()
	{
		return errorsql;
	}

	/**
	 * @返回 node变量的值
	 */
	public SQLItemNode getNode()
	{
		return node;
	}

}
