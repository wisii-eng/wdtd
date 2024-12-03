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
 * @XslContext.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl.util;

import java.util.ArrayList;
import java.util.List;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.io.NameSpace;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.io.context.PathContext;
import com.wisii.wisedoc.io.xsl.XslElementObj;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2012-5-29
 */
public class XslContext
{

	private PathContext pathcontext;
	protected List<Attributes> attributelist = new ArrayList<Attributes>();
	// 存放已被使用的namespace
	private List<NameSpace> nameSpaces = new ArrayList<NameSpace>();
	// 存放已被调用功能模板名
	private List<String> functionTemplates = new ArrayList<String>();
	// 存放已被调用配置文件文件名
	private List<String> profiles = new ArrayList<String>();
	//
	// 存放需要定义的全局变量和参数
	private  List<XslElementObj> overall = new ArrayList<XslElementObj>();
	public void saveGroupContext(Group group)
	{
		if (group != null)
		{
			pathcontext = new PathContext(pathcontext, group);
		}
	}

	public void restoreGroupContext(Group group)
	{
		if (group != null)
		{
			if (!pathcontext.iscurrentGroup(group))
			{
				throw new RuntimeException(
						"调用该方法前没有先调用saveContext(CellElement cell)");
			}
			pathcontext = pathcontext.getParent();
		}
	}

	public String getRelatePath(BindNode node)
	{
		if (node == null)
		{
			return null;
		}
		if (pathcontext == null)
		{
			return node.getXPath();
		}
		return pathcontext.getRelatePath(node);
	}
   public void addAttribute(Attributes att)
   {
	   if(att!=null&&!attributelist.contains(att))
	   {
		   attributelist.add(att);
	   }
   }
	public void addNameSpace(NameSpace namespace)
	{
		if (namespace == null)
		{
			return;
		}
		if (!nameSpaces.contains(namespace))
		{
			nameSpaces.add(namespace);
		}
	}

	public void addFunctionTemplate(String functiontemplate)
	{
		if (functiontemplate==null||functiontemplate.isEmpty())
		{
			return;
		}
		if (!functionTemplates.contains(functiontemplate))
		{
			functionTemplates.add(functiontemplate);
		}
	}

	public void addProfile(String profile)
	{
		if (profile == null||profile.isEmpty())
		{
			return;
		}
		if (!profiles.contains(profile))
		{
			profiles.add(profile);
		}
	}

	/**
	 * @返回  attributelist变量的值
	 */
	public List<Attributes> getAttributelist()
	{
		return attributelist;
	}

	/**
	 * @返回  nameSpaces变量的值
	 */
	public List<NameSpace> getNameSpaces()
	{
		return nameSpaces;
	}

	/**
	 * @返回  functionTemplates变量的值
	 */
	public List<String> getFunctionTemplates()
	{
		return functionTemplates;
	}

	/**
	 * @返回  profiles变量的值
	 */
	public List<String> getProfiles()
	{
		return profiles;
	}
	/**
	 * 
	 * 往定义全局变量和参数的链表中添加元素
	 * 
	 * @param xslelement
	 *            将要往定义全局变量和参数的链表中添加的元素
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public  void addOverall(XslElementObj xslelement)
	{
		int size = overall.size();
		if (size > 0)
		{
			boolean flg = true;
			for (XslElementObj current : overall)
			{
				if (current.equals(xslelement))
				{
					flg = false;
					break;
				}
			}
			if (flg)
				overall.add(xslelement);
		} else
		{
			overall.add(xslelement);
		}
	}
	/**
	 * 
	 * 获取定义全局变量和参数的链表
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public  List<XslElementObj> getOverall()
	{
		return overall;
	}
}
