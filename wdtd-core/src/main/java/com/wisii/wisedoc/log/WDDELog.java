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
 * @WDDELog.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.log;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.logging.impl.SimpleLog;

/**
 * 类功能描述：日志记录类
 * 
 * 作者：zhangqiang 创建日期：2008-7-15
 */
class WDDELog extends SimpleLog
{
	private OutputStream out = System.out;

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public WDDELog(String arg0)
	{
		super(arg0);
	}

	/**
	 * 
	 */
	protected void write(final StringBuffer buffer)
	{
		/* Checkstyle-GenericIllegalRegexp-Off. */
		try
		{
			out.write(buffer.toString().getBytes());
			out.write("\n".getBytes());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 设置输出流，如希望忘文件中写系统日志，则穿入文件流
	 * 
	 * @param {引入参数名}
	 *            {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public void setOut(OutputStream out)
	{
		if (out != null && this.out != out)
		{
			try
			{
				this.out.flush();
				this.out.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			this.out = out;
		}
	}

	/**
	 * 
	 * 系统退出时调用
	 * 
	 * @param {引入参数名}
	 *            {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public void finishLog()
	{
		try
		{
			out.flush();
			out.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}
