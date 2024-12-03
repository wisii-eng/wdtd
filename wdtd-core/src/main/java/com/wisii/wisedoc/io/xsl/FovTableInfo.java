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
 * @FovTableInfo.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl;

import com.wisii.wisedoc.banding.io.NameSpace;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-8-28
 */
public class FovTableInfo
{

	String startName;

	String countStr;

	public FovTableInfo(String startname, String count)
	{
		startName = startname;
		countStr = count;
	}

	public FovTableInfo(String startname)
	{
		startName = startname;
	}

	/**
	 * 
	 * 得到元素fov_tableinfo的代码
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String writeStart()
	{
		String output = ElementWriter.TAGBEGIN;
		output = output + FoXsltConstants.FOV_TABLEINFO;
		output = output + getStartNameAttribute();
		if ("".equals(countStr))
		{
			output = output + ElementWriter.TAGEND;
		} else
		{
			output = output + getCountAttribute();
			output = output + ElementWriter.NOCHILDTAGEND;
		}
		NameSpace fov = new NameSpace(FoXsltConstants.SPACENAMEFOV,
				FoXsltConstants.COMWISIIFOV);
		IoXslUtil.addNameSpace(fov);
		return output;
	}

	public String writeEnd()
	{
		if ("".equals(countStr))
		{
			return ElementUtil.endElement(FoXsltConstants.FOV_TABLEINFO);
		} else
		{
			return "";
		}
	}

	/**
	 * 
	 * 得到属性fov_rowcount的赋值语句
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String getCountAttribute()
	{
		if (!"".equalsIgnoreCase(countStr))
		{
			return ElementUtil.outputAttributes(FoXsltConstants.FOV_ROWCOUNT,
					countStr);
		}
		return "";
	}

	/**
	 * 
	 * 得到属性fov_startname的赋值语句
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String getStartNameAttribute()
	{
		if (!"".equalsIgnoreCase(startName))
		{
			return ElementUtil.outputAttributes(FoXsltConstants.FOV_STARTNAME,
					startName);
		}
		return "";
	}

	/**
	 * @返回 countStr变量的值
	 */
	public String getCountStr()
	{
		return countStr;
	}

	/**
	 * @param countStr
	 *            设置countStr成员变量的值 值约束说明
	 */
	public void setCountStr(String countStr)
	{
		this.countStr = countStr;
	}

	/**
	 * @返回 startName变量的值
	 */
	public String getStartName()
	{
		return startName;
	}

	/**
	 * @param startName
	 *            设置startName成员变量的值 值约束说明
	 */
	public void setStartName(String startName)
	{
		this.startName = startName;
	}

}
