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
 * @IoXslUtil.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl.util;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.io.zhumoban.MainXSLWriter;
import com.wisii.wisedoc.banding.AttributeBindNode;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.DefaultBindNode;
import com.wisii.wisedoc.banding.io.NameSpace;
import com.wisii.wisedoc.document.BarCodeText;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.document.attribute.CondLengthProperty;
import com.wisii.wisedoc.document.attribute.Condition;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.document.attribute.edit.Validation;
import com.wisii.wisedoc.document.attribute.transform.DataSource;
import com.wisii.wisedoc.document.attribute.transform.SelectInfo;
import com.wisii.wisedoc.document.attribute.transform.SwingDS;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.IOFactory;
import com.wisii.wisedoc.io.xsl.ObjectXslWriter;
import com.wisii.wisedoc.io.xsl.XslElementObj;
import com.wisii.wisedoc.io.xsl.attribute.ColorWriter;
import com.wisii.wisedoc.io.xsl.attribute.EnumPropertyWriter;
import com.wisii.wisedoc.io.xsl.attribute.FixedLengthWriter;
import com.wisii.wisedoc.io.xsl.attribute.edit.EditUI;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.util.WiseDocConstants;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-8-28
 */
public class IoXslUtil
{

	// 添加总页码的类型
	private static boolean isStandard = false;

	// 是否编辑的总开关
	private static int xmlEditable = Constants.EN_UNEDITABLE;

	// 添加总页码的类型
	private static boolean addPageNumberType = false;

	private static boolean havaindex = false;

	// 存放已有xpath的栈
	private static List<Group> xpath = new ArrayList<Group>();

	// 存放已有xpath的栈
	private static List<NameSpace> nameSpace = new ArrayList<NameSpace>();

	// 存放需要定义的全局变量和参数
	private static List<XslElementObj> overall = new ArrayList<XslElementObj>();

	// 存放已被调用功能模板名
	private static List<String> functionTemplate = new ArrayList<String>();

	// 存放已被调用配置文件文件名
	private static List<String> profile = new ArrayList<String>();


	private static List<EditUI> edituis = new ArrayList<EditUI>();

	private static List<Validation> validation = new ArrayList<Validation>();

	private static List<DataSource> datasource = new ArrayList<DataSource>();

	private static List<SelectInfo> datasourceinfo = new ArrayList<SelectInfo>();

	private static List<String> nonullstaticcontentnames = new ArrayList<String>();

	// svg中cm和默认单位间的换算比例
	private static int coefficient = 100;

	private static String filename = "";

	/**
	 * 
	 * 往xpath的链表中添加元素
	 * 
	 * @param path
	 *            将要往xpath的链表中添加的元素
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static void addXpath(Group path)
	{
		if (path != null)
		{
			xpath.add(path);
		}
	}

	/**
	 * 
	 * 获得xpath的链表的最后一个元素
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String getTopXpath()
	{
		String topXpath = "";
		Group top = getTopGroup();
		if (top != null && top.getNode() != null)
		{
			topXpath = IoXslUtil.getXSLXpath(top.getNode());
		}
		return topXpath;
	}

	public static Group getTopGroup()
	{
		Group topXpath = null;
		int size = xpath.size();
		if (size > 0)
		{
			topXpath = xpath.get(size - 1);
		}
		return topXpath;
	}

	/**
	 * 
	 * 删除xpath的链表的最后一个元素
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static void deleteXpath()
	{
		int size = xpath.size();
		if (size > 0)
		{
			xpath.remove(size - 1);
		}
	}

	/**
	 * 
	 * 获取xpath的链表
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static List<Group> getXpath()
	{
		return xpath;
	}

	/**
	 * 
	 * 清空xpath的链表
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static void clearXpath()
	{
		xpath.clear();
	}

	/**
	 * 
	 * 往定义名称空间的链表中添加元素
	 * 
	 * @param namespace
	 *            将要往名称空间的链表中添加的元素
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static void addNameSpace(NameSpace namespace)
	{
		int size = nameSpace.size();
		if (size > 0)
		{
			for (NameSpace current : nameSpace)
			{
				if (current.equals(namespace))
				{
					return;
				}
			}
			nameSpace.add(namespace);
		} else
		{
			nameSpace.add(namespace);
		}
	}

	/**
	 * 
	 * 初始化定义名称空间的链表
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static void initializationNameSpace()
	{
		nameSpace.clear();
	}

	/**
	 * 
	 * 清空定义名称空间的链表
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static void clearNameSpace()
	{
		nameSpace.clear();
	}

	/**
	 * 
	 * 获取定义名称空间的链表
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static List<NameSpace> getNameSpace()
	{
		return nameSpace;
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
	public static void addOverall(XslElementObj xslelement)
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
	 * 清空定义全局变量和参数的链表
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static void clearOverall()
	{
		overall.clear();
	}

	/**
	 * 
	 * 清空定义全局变量和参数的链表
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static void initializationOverall()
	{
		overall.clear();
		if (isXmlEditable() == Constants.EN_EDITVARIBLE)
		{
			Map<Integer, Object> attributemap = new HashMap<Integer, Object>();
			attributemap.put(
					com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_NAME,
					"XmlEditable");
			attributemap.put(
					com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_SELECT, "'"
							+ FoXsltConstants.YES + "'");
			XslElementObj xmlEdit = new XslElementObj(attributemap,
					FoXsltConstants.VARIABLE);
			overall.add(xmlEdit);
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
	public static List<XslElementObj> getOverall()
	{
		return overall;
	}

	/**
	 * 
	 * 往fov转换表的链表中添加元素
	 * 
	 * @param fovtranslatetable
	 *            将要往fov转换表中添加的元素
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */

	/**
	 * 
	 * 往功能模板名的链表中添加元素
	 * 
	 * @param tempname
	 *            将要往功能模板名的链表中添加的元素
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static void addFunctionTemplate(String tempname)
	{
		if (!functionTemplate.contains(tempname)
				&& !tempname.isEmpty())
		{
			functionTemplate.add(tempname);
		}
	}

	/**
	 * 
	 * 清空功能模板名的链表
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static void clearFunctionTemplate()
	{
		functionTemplate.clear();
	}

	/**
	 * 
	 * 获取功能模板名的链表
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static List<String> getFunctionTemplate()
	{
		return functionTemplate;
	}

	/**
	 * 
	 * 往配置文件文件名的链表中添加元素
	 * 
	 * @param filename
	 *            将要往配置文件文件名的链表中添加的元素
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static void addProfile(String filename)
	{
		if (!profile.contains(filename))
		{
			profile.add(filename);
		}
	}

	/**
	 * 
	 * 清空配置文件文件名的链表
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static void clearProfile()
	{
		profile.clear();
	}

	/**
	 * 
	 * 获取配置文件文件名的链表
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static List<String> getProfile()
	{
		return profile;
	}


	/**
	 * 返回元素值处理后的结果
	 * 
	 * @param value
	 *            元素的值
	 * @return output 最终输出结果
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String dealToString(Object value)
	{
		String output = "";
		if (value instanceof Integer)
		{
			output = value + "";
		} else if (value instanceof String)
		{
			output = "'" + value.toString() + "'";
		} else if (value instanceof StringBuffer)
		{
			output = value.toString();
		} else if (value instanceof BarCodeText)
		{
			BarCodeText text = (BarCodeText) value;
			if (text.isBindContent())
			{
				output = compareCurrentPath(IoXslUtil.getXSLXpath(text
						.getBindNode()));
			} else
			{
				output = "'" + value + "'";
			}
		} else if (value instanceof FixedLength)
		{
			FixedLength fixedLength = (FixedLength) value;
			output = "'" + new FixedLengthWriter().write(fixedLength) + "'";
		} else if (value instanceof EnumProperty)
		{
			EnumProperty enumProperty = (EnumProperty) value;
			output = "'" + new EnumPropertyWriter().write(enumProperty) + "'";
		} else if (value instanceof Boolean)
		{
			boolean valueB = (Boolean) value;
			if (valueB)
			{
				output = "'" + FoXsltConstants.TRUE + "'";
			} else
			{
				output = "'" + FoXsltConstants.FALSE + "'";
			}

		} else if (value instanceof Color)
		{
			Color color = (Color) value;
			output = "'" + new ColorWriter().getColorValue(color) + "'";
		} else if (value instanceof DefaultBindNode)
		{
			DefaultBindNode text = (DefaultBindNode) value;
			output = compareCurrentPath(IoXslUtil.getXSLXpath(text));
		} else
		{
			output = value.toString();
		}
		return output;
	}

	/**
	 * 
	 * 求xpath路径间的相对路径,两路径相同时用"."标注。
	 * 
	 * @param parentpath
	 *            ：父xpath path：要求相对路径的xpath
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String comparePath(String parentpath, String path)
	{

		if (parentpath == null || path == null)
		{
			return null;
		}
		if (parentpath.trim().equals(""))
		{
			return path;
		}
		parentpath = parentpath.trim();
		path = path.trim();

		if (path.startsWith(parentpath) || parentpath.startsWith(path))
		{
			return cpPath(parentpath, path);
		} else
		{
			// System.out.println("parentpath::" + parentpath);
			// System.out.println("path::" + path);
//			Map<String, Group> ppathmap = new HashMap<String, Group>();
//			List<Group> pxpath = getXpath();
//			int ppsize = pxpath.size();
//			if (ppsize > 1)
//			{
//				for (int i = 0; i < ppsize; i++)
//				{
//					Group current = pxpath.get(i);
//					BindNode currentnode = current.getNode();
//					if (currentnode != null)
//					{
//						String cp = IoXslUtil.getXSLXpath(currentnode);
//						if (cp != null && !"".equals(cp))
//						{
//							ppathmap.put(cp, current);
//							// System.out.println("AAAA::" + cp);
//						}
//					}
//				}
//			}
			// 当前重复属性的节点
			String[] pnodes = parentpath.split("/");

			String[] nodes = path.split("/");
			int psize = pnodes.length;
			int size = nodes.length;
			int fsize = psize >= size ? psize : size;
			// 记录第一个路径不同时的节点位置
			int postion = -1;
			for (int i = 0; i < fsize; i++)
			{
				String current = nodes[i];
				String pcurrent = pnodes[i];
				if (current.equals(pcurrent))
				{
					postion = i;
				} else
				{
					break;
				}
			}
			int chai = psize - postion - 1;
			String finalpath = "";
			for (int j = 0; j < chai; j++)
			{
				finalpath = finalpath + "..";
				if (j < chai - 1)
				{
					finalpath = finalpath + "/";
				}
			}
			/*String xiangtong = "";
			for (int i = 0; i <= postion; i++)
			{
				if (i > 0)
				{
					xiangtong = xiangtong + "/";
				}
				xiangtong = xiangtong + pnodes[i];
			}*/
			// System.out.println("XXX::" + xiangtong);
			for (int i = postion + 1; i < size; i++)
			{
				String current = nodes[i];
//				xiangtong = xiangtong + "/" + current;
				// System.out.println("SSSSSSSSSSS::" + i);
				// System.out.println("IIIII::" + xiangtong);
				finalpath = finalpath + "/" + current;
//				if (ppathmap.containsKey(xiangtong))
//				{
//					// System.out.println("CCCCCCCCCCCC:::");
//					finalpath = finalpath + "[";
//					Group cp = ppathmap.get(xiangtong);
//					LogicalExpression cl = cp.getFliterCondition();
//					if (cl != null)
//					{
//						finalpath = finalpath + "(";
//						finalpath = finalpath
//								+ returnStringIfPosition(cl, true, IoXslUtil
//										.getXSLXpath(cp.getNode()).trim());
//						finalpath = finalpath + ") and (";
//					}
//					int index = pxpath.indexOf(cp);
//					finalpath = finalpath + "position()=$n" + index;
//					if (cl != null)
//					{
//						finalpath = finalpath + ")";
//					}
//					finalpath = finalpath + "]";
//				}
			}
			return finalpath;
		}
	}

	public static String cpPath(String parentpath, String path)
	{
		String s = "";
		String[] pnodes = parentpath.split("/");
		String[] nodes = path.split("/");
		int psize = pnodes.length;
		int size = nodes.length;
		// 记录第一个路径不同时的节点位置
		int postion = -1;
		// 找到路径相同的位置
		for (int i = 0, j = 0; i < psize && j < size
				&& pnodes[i].equals(nodes[j]); postion = i, i++, j++)
			;
		if (postion == psize - 1)
		{
			if (postion == size - 1)
			{
				s = ".";
			} else
			{
				for (int i = postion + 1; i < size; i++)
				{
					if (i == postion + 1)
					{
						s = s + nodes[i];
					} else
					{
						s = s + "/" + nodes[i];
					}
				}
			}
		}
		// 即postion < psize-1;
		else
		{
			for (int i = postion + 1; i < psize; i++)
			{
				if (i != psize - 1)
				{
					s = "../" + s;
				} else
				{
					s = s + "..";
				}
			}
			for (int i = postion + 1; i < size; i++)
			{
				s = s + "/" + nodes[i];
			}
		}
		return s;
	}

	/**
	 * 
	 * 求xpath路径间的相对路径，两路径相同时用"../最后一节点名"标注。
	 * 
	 * @param parentpath
	 *            ：父xpath path：要求相对路径的xpath
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String compareLocalNamePath(String parentpath, String path)
	{
		if (parentpath == null || path == null)
		{
			return null;
		}
		if (parentpath.trim().equals(""))
		{
			return path;
		}
		String s = "";
		String[] pnodes = parentpath.split("/");
		String[] nodes = path.split("/");
		int psize = pnodes.length;
		int size = nodes.length;
		// 记录第一个路径不同时的节点位置
		int postion = -1;
		// 找到路径相同的位置
		for (int i = 0, j = 0; i < psize && j < size
				&& pnodes[i].equals(nodes[j]); postion = i, i++, j++)
			;
		if (postion == psize - 1)
		{
			if (postion == size - 1)
			{
				s = "../" + nodes[size - 1];
			} else
			{
				for (int i = postion + 1; i < size; i++)
				{
					if (i == postion + 1)
					{
						s = s + nodes[i];
					} else
					{
						s = s + "/" + nodes[i];
					}
				}
			}

		}
		// 即postion < psize-1;
		else
		{
			for (int i = postion + 1; i < psize; i++)
			{
				if (i != psize - 1)
				{
					s = "/.." + s;
				} else
				{
					s = ".." + s;
				}
			}
			for (int i = postion + 1; i < size; i++)
			{
				s = s + "/" + nodes[i];
			}
		}
		return s;
	}

	/**
	 * 
	 * 求传入xpath路径和当前xpath的相对路径，,两路径相同时用"."标注。
	 * 
	 * @param path
	 *            ：要求相对路径的xpath
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String compareCurrentPath(String path)
	{
		String relativePath = "";
		String parentPath = getTopXpath();
		relativePath = comparePath(parentPath, path);
		return relativePath;
	}

	/**
	 * 
	 * 求传入xpath路径和当前xpath的相对路径，两路径相同时用"../最后一节点名"标注。
	 * 
	 * @param path
	 *            ：要求相对路径的xpath
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String compareCurrentLocalNamePath(String path)
	{
		String relativePath = "";
		String parentPath = getTopXpath();
		relativePath = compareLocalNamePath(parentPath, path);
		return relativePath;
	}

	public static String getAbsoluteXPath(String path)
	{
		String xPath = "";
		List<Group> pathList = getXpath();
		if (pathList == null || pathList.isEmpty())
		{
			xPath = path;
			if (!xPath.startsWith("/"))
			{
				xPath = "'/" + xPath + "'";
			}
		} else
		{
			String currentpath = "";
			List<String> pathlistrelative = new ArrayList<String>();
			for (Group current : pathList)
			{
				String pathitem = "";
				if (current != null && current.getNode() != null)
				{
					pathitem = IoXslUtil.getXSLXpath(current.getNode());
				}
				String relativepath = getRelative(currentpath, pathitem);
				currentpath = pathitem;
				pathlistrelative.add(relativepath);
			}
			pathlistrelative.add(getRelative(currentpath, path));
			int size = pathlistrelative.size();
			if (size > 0)
			{
				xPath = xPath + "concat(";
				for (int i = 0; i < size; i++)
				{
					xPath = xPath + "'";
					String current = pathlistrelative.get(i);
					if (current.equals(""))
					{
						xPath = xPath + "'";
					} else
					{
						if (i == 0 && !current.startsWith("/"))
						{
							current = "/" + current;
						}

						xPath = xPath + current;
						if (i < size - 1)
						{
							xPath = xPath + "[',";
							xPath = xPath + "$n" + i + ",']";
						}
						xPath = xPath + "'";
					}

					if (i < size - 1)
					{
						xPath = xPath + ",";
					}
				}
				xPath = xPath + ")";
			}
		}
		return xPath;
	}

	// public static String getButtonAbsoluteXPath(String path, int listsize)
	// {
	// String xPath = "";
	// List<Group> paths = getXpath();
	// // System.out.println("old size:" + paths.size());
	// // System.out.println("new size:" + listsize);
	// // System.out.println("group:" + path);
	// if (paths == null || paths.isEmpty())
	// {
	// xPath = path;
	// if (!xPath.startsWith("/"))
	// {
	// xPath = "'/" + xPath + "'";
	// }
	// } else
	// {
	// List<Group> pathList = new ArrayList<Group>();
	//
	// for (int i = 0; i < listsize; i++)
	// {
	// pathList.add(paths.get(i));
	// }
	// // System.out.println("pathList size:" + pathList.size());
	// String currentpath = "";
	// List<String> pathlistrelative = new ArrayList<String>();
	// for (Group current : pathList)
	// {
	// String pathitem = "";
	// if (current != null && current.getNode() != null)
	// {
	// pathitem = current.getNode().getXPath();
	// }
	// String relativepath = getRelative(currentpath, pathitem);
	// currentpath = pathitem;
	// pathlistrelative.add(relativepath);
	// }
	// pathlistrelative.add(getRelative(currentpath, path));
	// int size = pathlistrelative.size();
	// if (size > 0)
	// {
	// xPath = xPath + "concat(";
	// for (int i = 0; i < size; i++)
	// {
	// xPath = xPath + "'";
	// String current = pathlistrelative.get(i);
	// if (current.equals(""))
	// {
	// xPath = xPath + "'";
	// } else
	// {
	// if (i == 0 && !current.startsWith("/"))
	// {
	// current = "/" + current;
	// }
	//
	// xPath = xPath + current;
	// if (i < size - 1)
	// {
	// xPath = xPath + "[',";
	// xPath = xPath + "$n" + i + ",']";
	// }
	// xPath = xPath + "'";
	// }
	//
	// if (i < size - 1)
	// {
	// xPath = xPath + ",";
	// }
	// }
	// xPath = xPath + ")";
	// }
	// }
	// return xPath;
	// }
	public static String getButtonAbsoluteXPath(Element cellment)
	{
		String xPath = "";

		Object obj = cellment.getAttribute(Constants.PR_GROUP);
		if (obj != null)
		{
			Group group = (Group) obj;

			List<Group> paths = new ArrayList<Group>();
			paths.add(group);
			while (!(cellment instanceof WiseDocDocument))
			{
				cellment = cellment.getParent();
				Object currentobj = cellment.getAttribute(Constants.PR_GROUP);
				if (currentobj != null)
				{
					paths.add((Group) currentobj);
				}
			}

			if (paths == null || paths.isEmpty())
			{
				String path = IoXslUtil.getXSLXpath(group.getNode());
				xPath = path;
				if (!xPath.startsWith("/"))
				{
					xPath = "'/" + xPath + "'";
				}
			} else
			{

				List<String> pathlistrelative = new ArrayList<String>();
				String currentpath = "";
				for (int i = paths.size() - 1; i > -1; i--)
				{
					Group current = paths.get(i);
					String pathitem = IoXslUtil.getXSLXpath(current.getNode());
					String relativepath = getRelative(currentpath, pathitem);
					currentpath = pathitem;
					pathlistrelative.add(relativepath);
				}
				pathlistrelative.add(getRelative(currentpath, currentpath));
				int size = pathlistrelative.size();
				if (size > 0)
				{
					for (int i = 0; i < size; i++)
					{
						String current = pathlistrelative.get(i);
						if (!current.equals(""))
						{
							if (i > 0)
							{
								xPath = xPath + ",";
							}
							xPath = xPath + "'";
							if (!current.startsWith("/"))
							{
								current = "/" + current;
							}
							xPath = xPath + current;
							xPath = xPath + "[',";
							xPath = xPath + "$n" + i + ",']'";
							// if (i == size - 1)
							// {
							// xPath = xPath + ",'";
							// }
						}

						// xPath = xPath + "'";
						// String current = pathlistrelative.get(i);
						// if (current.equals(""))
						// {
						// xPath = xPath + "'";
						// } else
						// {
						// if (i == 0 && !current.startsWith("/"))
						// {
						// current = "/" + current;
						// }
						//
						// xPath = xPath + current;
						// if (i < size - 1)
						// {
						// xPath = xPath + "[',";
						// xPath = xPath + "$n" + i + ",']";
						// }
						// xPath = xPath + "'";
						// }
						//
						// if (i < size - 1)
						// {
						// xPath = xPath + ",";
						// }
					}
				}
			}
		}
		return xPath;
	}

	public static String getAbsoluteXPathTest(String path, List<String> pathList)
	{
		StringBuffer xPath = new StringBuffer();
		if (pathList == null || pathList.isEmpty())
		{
			xPath.append(path);
		} else
		{
			String currentpath = "";
			List<String> pathlistrelative = new ArrayList<String>();
			for (String pathitem : pathList)
			{
				String relativepath = getRelative(currentpath, pathitem);
				currentpath = pathitem;
				pathlistrelative.add(relativepath);
			}
			pathlistrelative.add(getRelative(currentpath, path));
			int size = pathlistrelative.size();
			if (size > 0)
			{
				xPath.append("concat(");
				for (int i = 0; i < size; i++)
				{
					xPath.append("'");
					String currant = pathlistrelative.get(i);
					if (currant.equals(""))
					{
						xPath.append("/.'");
					} else
					{
						xPath.append(currant);
						if (i < size - 1)
						{
							xPath.append("[',");
							xPath.append("$n" + i + ",']");
						}
						xPath.append("'");
					}

					if (i < size - 1)
					{
						xPath.append(",");
					}
				}
				xPath.append(")");
			}
		}
		return xPath.toString();
	}

	// public static void main(String[] args)
	// {
	// String path = "/a/b/c/d/e/f/g/h/j/k/l";
	// List<String> pathList = new ArrayList<String>();
	// pathList.add("/a");
	// pathList.add("/a/b");
	// pathList.add("/a/b/c/d");
	// pathList.add("/a/b/c/d");
	// pathList.add("/a/b/c/d/e");
	// pathList.add("/a/b/c/d/e/f/g/h");
	// pathList.add("/a/b/c/d/e/f/g/h/j/k");
	// System.out.println(getAbsoluteXPathTest(path, pathList));
	// }

	public static String getRelative(String cr, String cp)
	{
		String result = "";
		String current = new String(cr);
		String compare = new String(cp);
		result = compare.replaceFirst(current, "");
		return result;
	}

	/**
	 * 
	 * 传入xpath路径处理成绝对路径。
	 * 
	 * @param path
	 *            ：路径的path
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String dealXPath(String path)
	{
		String xPath = "";
		List<Group> pathList = getXpath();
		List<Integer> equalPosition = new ArrayList<Integer>();
		int size = pathList.size();
		if (size == 0)
		{
			xPath = path;
		} else
		{
			for (int i = 0; i < size; i++)
			{
				Group group = pathList.get(i);
				String pathitem = "";
				if (group != null && group.getNode() != null)
				{
					pathitem = IoXslUtil.getXSLXpath(group.getNode());
				}
				if (path.startsWith(pathitem))
				{
					String[] pnodes = pathitem.split("/");
					int cp = pnodes.length - 1;
					boolean flg = true;
					int num = equalPosition.size();
					if (num > 0)
					{
						for (int j = 0; j < num; j++)
						{
							int current = equalPosition.get(j);
							if (current == cp)
							{
								flg = false;
							}
						}
					}
					if (flg)
					{
						equalPosition.add(cp);
					}
				}
			}
			int number = equalPosition.size();
			if (number == 0)
			{
				xPath = path;
			} else
			{
				String[] pathNodes = path.split("/");
				for (int i = 0; i < number; i++)
				{
					int position = equalPosition.get(i);
					// pathNodes[position] = pathNodes[position]
					// + "[<xsl:value-of select=\"position()\"/>]";
					pathNodes[position] = pathNodes[position] + "[position()]";
				}
				int length = pathNodes.length;
				if (length > 0)
				{
					for (int n = 0; n < length; n++)
					{
						if (n != length - 1)
						{
							xPath = xPath + pathNodes[n] + "/";
						} else
						{
							xPath = xPath + pathNodes[n];
						}
					}
				}
			}
		}
		if (!xPath.startsWith("/"))
		{
			xPath = "/" + xPath;
		}
		xPath = "'" + xPath + "'";
		return xPath;
	}

	public static double getValueOfCm(double value, String units)
	{
		double valueOfCm = 0d;
		if ("".equalsIgnoreCase(units))
		{
			LogUtil.debug("传入的参数\"" + value + "\"的单位为空");
		}
		String unit = units.toLowerCase();
		if (unit.equals("cm"))
		{
			valueOfCm = value;
		} else if (unit.equals("in"))
		{
			valueOfCm = value * WiseDocConstants.CMOFIN;
		} else if (unit.equals("pt"))
		{
			valueOfCm = value * WiseDocConstants.CMOFIN
					/ WiseDocConstants.POINTOFIN;
		} else if (unit.equals("m"))
		{
			valueOfCm = value * 100;
		} else if (unit.equals("dm"))
		{
			valueOfCm = value * 10;
		}
		return valueOfCm;
	}

	public static String getValueOfCm(String oldvalue, String units)
	{
		String valueOfCm = oldvalue;
		if ("".equalsIgnoreCase(units))
		{
			LogUtil.debug("传入的参数\"" + oldvalue + "\"的单位为空");
		}
		String unit = units.toLowerCase();
		double value = new Double(oldvalue).doubleValue();
		if (unit.equals("in"))
		{
			valueOfCm = value * WiseDocConstants.CMOFIN + "";
		} else if (unit.equals("pt"))
		{
			valueOfCm = value * WiseDocConstants.CMOFIN
					/ WiseDocConstants.POINTOFIN + "";
		} else if (unit.equals("m"))
		{
			valueOfCm = value * 100 + "";
		} else if (unit.equals("dm"))
		{
			valueOfCm = value * 10 + "";
		}
		return valueOfCm;
	}

	public static String getValueOfCm(String oldvalue, String units,
			int coefficient)
	{
		String valueOfCm = "";
		if ("".equalsIgnoreCase(units))
		{
			LogUtil.debug("传入的参数\"" + oldvalue + "\"的单位为空");
		}
		String unit = units.toLowerCase();
		double value = (new Double(oldvalue).doubleValue()) * coefficient;
		if (unit.equals("cm"))
		{
			valueOfCm = value + "";
		} else if (unit.equals("in"))
		{
			valueOfCm = value * WiseDocConstants.CMOFIN + "";
		} else if (unit.equals("pt"))
		{
			valueOfCm = value * WiseDocConstants.CMOFIN
					/ WiseDocConstants.POINTOFIN + "";
		} else if (unit.equals("m"))
		{
			valueOfCm = value * 100 + "";
		} else if (unit.equals("dm"))
		{
			valueOfCm = value * 10 + "";
		}
		return valueOfCm;
	}

	/**
	 * 根据毫米长度单位值获得像素
	 * 
	 * @param length
	 *            :毫米单位值
	 * @return
	 * @exception
	 */
	public static double getPointofmm(double length)
	{
		return length * WiseDocConstants.POINTOFIN / 25.4;
	}

	/**
	 * @返回 coefficient变量的值
	 */
	public static int getCoefficient()
	{
		return coefficient;
	}

	/**
	 * @param coefficient
	 *            设置coefficient成员变量的值 值约束说明
	 */
	public static void setCoefficient(int coefficient)
	{
		IoXslUtil.coefficient = coefficient;
	}

	/**
	 * 
	 * 获取是否编辑的总开关
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static int isXmlEditable()
	{
		return xmlEditable;
	}

	/**
	 * 
	 * 设置是否编辑的总开关
	 * 
	 * @param xmlEditable
	 *            是否编辑的总开关
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static void setXmlEditable(int xmleditable)
	{
		xmlEditable = xmleditable;
		if (xmleditable == Constants.EN_EDITVARIBLE)
		{
			Map<Integer, Object> attributemap = new HashMap<Integer, Object>();
			attributemap.put(
					com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_NAME,
					"XmlEditable");
			attributemap.put(
					com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_SELECT, "'"
							+ FoXsltConstants.YES + "'");
			XslElementObj xmlEdit = new XslElementObj(attributemap,
					FoXsltConstants.VARIABLE);
			overall.add(xmlEdit);
		}
	}

	/**
	 * 
	 * 获取总页码的类型
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static boolean getAddPageNumberType()
	{
		return addPageNumberType;
	}

	/**
	 * 
	 * 设置总页码的类型
	 * 
	 * @param addPageNumberType
	 *            总页码的类型
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static void setAddPageNumberType(boolean addPageNumberType)
	{
		IoXslUtil.addPageNumberType = addPageNumberType;
	}

	public static String getPersent(double factor)
	{
		BigDecimal cu=new BigDecimal(factor);
		BigDecimal resultcu=cu.multiply(new BigDecimal(100),new MathContext(6));
		return resultcu.toPlainString() + "%";
	}

	public static String getDeleteLastZero(double yuan)
	{
		String value = "";
		String old = yuan + "";
		value = getDeleteLastZero(old);
		return value;
	}

	public static String getDeleteLastZero(String old)
	{
		String value = "";
		if (old.contains("."))
		{
			String[] arr = old.split("\\.");
			String[] delete = getRealNumber(arr[0], arr[1]);
			String zhengShu = delete[0];
			String xiaoShu = delete[1];
			int length = xiaoShu.length();
			if (length < 3)
			{
				value = removePoint(zhengShu + "." + xiaoShu);
			} else
			{
				String flg = xiaoShu.substring(2, 3);
				int intFlg = new Integer(flg).intValue();
				if (intFlg > 4)
				{
					String before = xiaoShu.substring(0, 2);
					value = getAfterAdd(zhengShu + "." + before);

				} else
				{
					value = removePoint(zhengShu + "."
							+ xiaoShu.substring(0, 2));
				}
			}
		} else
		{
			value = old;
		}
		return value;
	}

	/**
	 * 
	 * 去掉数字后多余的零
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static String removePoint(String point)
	{
		String value = point;
		if (value.contains("."))
		{
			int index = value.indexOf(".");
			String yuanshi = value.length() == index + 1 ? "" : value
					.substring(index + 1);
			if (yuanshi.length() > 0)
			{
				int number = 0;
				for (int i = 0; i < yuanshi.length(); i++)
				{
					String current = yuanshi.substring(i, i + 1);
					if (current.equalsIgnoreCase("0"))
					{

						number++;
					} else
					{
						number = 0;
					}
				}
				if (number > 0)
				{
					yuanshi = yuanshi.substring(0, yuanshi.length() - number);
				}
			}
			if (yuanshi.length() > 0)
			{
				value = value.substring(0, index + 1) + yuanshi;
			} else
			{
				value = value.substring(0, index);
			}
		}

		return value;
	}

	public static String[] getRealNumber(String zhengshu, String xiaoshu)
	{
		String realZheng = zhengshu;
		String realXiao = getReal(xiaoshu);
		if (realXiao.contains("A"))
		{
			if (!realXiao.equalsIgnoreCase("A"))
			{
				String replce = realXiao.substring(0, realXiao.indexOf("A"));
				String value = replce.equalsIgnoreCase("") ? getAfterAdd(realZheng)
						: getAfterAdd(realZheng + "." + replce);
				if (value.contains("."))
				{
					String[] real = value.split("\\.");
					realZheng = real[0];
					realXiao = real[1];
				} else
				{
					realZheng = value;
					realXiao = "";
				}
			} else
			{
				realXiao = "";
				realZheng = ((new Integer(realZheng).intValue()) + 1) + "";
			}
		}
		String[] result =
		{ realZheng, realXiao };
		return result;
	}

	public static String getAfterAdd(String text)
	{
		String realzheng = "";
		String realxiao = "";
		if (text.contains("."))
		{
			String[] arr = text.split("\\.");
			String realZheng = arr[0];
			String realXiao = arr[1];
			realzheng = realZheng;
			realxiao = realXiao;
			String lastStr = realXiao.substring(realXiao.length() - 1, realXiao
					.length());
			int last = new Integer(lastStr).intValue();
			if (last < 9)
			{
				if (realXiao.length() == 1)
				{
					realxiao = (last + 1) + "";
					return realzheng + "." + realxiao;
				} else
				{
					realxiao = realXiao.substring(0, realXiao.length() - 1)
							+ (last + 1);
					return realzheng + "." + realxiao;
				}

			} else
			{
				if (realXiao.length() == 1)
				{
					realzheng = (new Integer(realZheng).intValue() + 1) + "";
					realxiao = "";
					return realzheng;
				} else
				{
					return getAfterAdd(realZheng + "."
							+ realXiao.substring(0, realXiao.length() - 1));
				}
			}
		} else
		{
			realzheng = (new Integer(text).intValue() + 1) + "";
			return realzheng;
		}

	}

	public static String getReal(String yuanshi)
	{
		String value = yuanshi;
		if (!yuanshi.equalsIgnoreCase(""))
		{
			if (value.contains("99999"))
			{
				int number = 0;
				for (int i = 0; i < yuanshi.length(); i++)
				{
					String current = yuanshi.substring(i, i + 1);
					if (current.equalsIgnoreCase("9"))
					{
						number++;
					} else
					{
						if (number > 5)
						{
							value = value.replaceFirst(getNumberOfDefineAdd(
									number, "9"), "A");
							return value;
						}
						number = 0;
					}
				}
				if (number > 5)
				{
					value = value.replaceFirst(
							getNumberOfDefineAdd(number, "9"), "A");
				}
			}
		}
		return value;
	}

	public static String getNumberOfDefineAdd(int number, String add)
	{
		String value = "";
		if (number > 0)
		{
			value = value + add;
			value = value + getNumberOfDefineAdd(number - 1, add);
		}
		return value;
	}

	public static String getId(String id)
	{
		String result = id;
		List<Group> group = getXpath();
		int size = group == null ? 0 : group.size();
		if (size > 0)
		{
			for (int i = 0; i < size; i++)
			{
				if (result.contains("concat"))
				{
					result = "concat(" + result + ",position(),'e')";
				} else
				{
					result = "concat('" + result + "',position(),'e')";
				}
			}
		}
		return result;
	}

	public static String getCurrentId(String id)
	{
		StringBuffer result = new StringBuffer();
		List<Group> group = getXpath();
		if (!group.isEmpty())
		{

			for (int i = 0; i < group.size(); i++)
			{
				// Group current = group.get(i);
				if (i == 0)
				{
					result.append(ElementUtil.startElementVF(
							FoXsltConstants.VALUE_OF, "concat('" + id + "',"
									+ "$n" + i + ",'d')"));
					result.append(ElementUtil
							.endElement(FoXsltConstants.VALUE_OF));
					// result = "concat('" + id + "'," + "$n" + i + ",'d')";

				} else
				{
					result.append(ElementUtil
							.startElementVF(FoXsltConstants.VALUE_OF,
									"concat($n" + i + ",'d')"));
					result.append(ElementUtil
							.endElement(FoXsltConstants.VALUE_OF));
					// result = "concat($n,'d')";
					// result = "concat($n,position(),'d')";
				}

			}
		} else
		{
			// result = "'" + id + "'";
			result.append(ElementUtil.startElementVF(FoXsltConstants.VALUE_OF,
					"'" + id + "'"));
			result.append(ElementUtil.endElement(FoXsltConstants.VALUE_OF));
		}
		return result.toString();
	}

	// public static void main(String[] args)
	// {
	// addXpath(Group.Instanceof(new DefaultBindNode(null, 1, 1, "a",
	// new HashMap<Integer, Object>()), null, null, null, null));
	// addXpath(Group.Instanceof(new DefaultBindNode(null, 1, 1, "a",
	// new HashMap<Integer, Object>()), null, null, null, null));
	// System.out.println(getCurrentId("e"));
	// }

	public static String getLastId(String id)
	{
		String result = id;
		List<Group> group = getXpath();
		int size = group == null ? 0 : group.size();
		if (size > 0)
		{
			for (int i = 0; i < size; i++)
			{
				if (i < size - 1)
				{
					if (result.contains("concat"))
					{
						result = "concat(" + result + ",position(),'e')";
					} else
					{
						result = "concat('" + result + "',position(),'e')";
					}
				} else
				{
					result = "concat('" + result + "',last(),'e')";
				}
			}
		}
		return result;
	}

	public static String getTotalPagenumberContent(String id)
	{
		StringBuffer result = new StringBuffer();
		result.append(id);
		List<Group> group = getXpath();
		int size = group == null ? 0 : group.size();
		if (size > 0)
		{
			for (int i = 0; i < size; i++)
			{
				Group current = group.get(i);
				if (current != null)
				{
					result.append(ElementUtil.startGroup(current));
					result.append(ElementUtil.ElementValueOf("concat("
							+ "last(),'e')"));
				}
			}
			for (int i = size; i > 0; i--)
			{
				Group current = group.get(i - 1);
				if (current != null)
				{
					result.append(ElementUtil.endCurrentGroup(current));
				}
			}
		}
		return result.toString();
	}

	public static String getLastId(String id, List<Group> group)
	{
		String result = id;
		int size = group == null ? 0 : group.size();
		if (size > 0)
		{
			for (int i = 0; i < size; i++)
			{
				if (i < size - 1)
				{
					if (result.contains("concat"))
					{
						result = "concat(" + result + ",position(),'e')";
					} else
					{
						result = "concat('" + result + "',position(),'e')";
					}
				} else
				{
					result = "concat('" + result + "',last(),'e')";
				}
			}
		}
		return result;
	}

	public static String getVarName(String id)
	{
		String result = id;
		result = result.replaceAll("position", "p");
		result = result.replaceAll("last" + "\\(" + "\\)", "l");
		result = result.replaceAll("concat", "");
		result = result.replaceAll(",", "");
		result = result.replaceAll("\\(", "");
		result = result.replaceAll("\\)", "");
		result = result.replaceAll("'", "");
		return "v" + result;
	}

	public static String getXmlText(String src)
	{
		String value = null;
		if (src != null)
		{
			value = src.replaceAll("&", "&amp;");
			value = value.replaceAll("<", "&lt;");
			value = value.replaceAll(">", "&gt;");
			value = value.replaceAll("'", "&apos;");
			value = value.replaceAll("\"", "&quot;");
		}
		return value;
	}

	public static Map<Integer, Object> getReplaceMap(Map<Integer, Object> old,
			Map<Integer, Object> replace)
	{
		Map<Integer, Object> newmap = new HashMap<Integer, Object>(old);
		if (replace != null)
		{
			Object[] keys = replace.keySet().toArray();
			int length = keys.length;
			for (int i = 0; i < length; i++)
			{
				int key = (Integer) keys[i];
				newmap.remove(key);
				newmap.put(key, replace.get(key));
			}
		}
		return newmap;
	}

	public static boolean isHavaindex()
	{
		return havaindex;
	}

	public static void setHavaindex(boolean havaindex)
	{
		IoXslUtil.havaindex = havaindex;
	}

	public static boolean isStandard()
	{
		return isStandard;
	}

	public static void setStandard(boolean isstandard)
	{
		isStandard = isstandard;
	}

	public static List<EditUI> getEdituis()
	{
		return edituis;
	}

	public static void setEdituis(List<EditUI> edituis)
	{
		IoXslUtil.edituis = edituis;
	}

	public static void clearEdituis()
	{
		IoXslUtil.edituis.clear();
	}

	public static String addEditUI(EditUI add)
	{
		String name = "";
		List<EditUI> listuis = getEdituis();
		if (listuis.size() == 0)
		{
			listuis.add(add);
			name = getEditUIName(add, 1);
			add.setName(name);
		} else
		{
			boolean flg = false;
			for (int i = 0; i < listuis.size(); i++)
			{
				EditUI current = listuis.get(i);
				if (add.equals(current))
				{
					name = current.getName();
					flg = true;
					return name;
				}
			}
			if (!flg)
			{
				listuis.add(add);
				name = getEditUIName(add, listuis.size());
				add.setName(name);
			}
		}
		return name;
	}

	public static String getEditUIName(EditUI ui, int number)
	{
		String name = "###" + number;
		Map<Integer, Object> map = ui.getAttr();
		EnumProperty type = ui.getType();
		if (type.getEnum() == Constants.EN_SELECT)
		{
			Object uiname = map.get(Constants.PR_SELECT_NAME);
			if (uiname != null)
			{
				name = uiname.toString();
			}
		}
//		else if (type.getEnum() == Constants.EN_POPUPBROWSER)
//		{
//			Object uiname = map.get(Constants.PR_SELECT_NAME);
//			if (uiname != null)
//			{
//				name = uiname.toString();
//			}
//		}
		return name;
	}

	public static List<Validation> getValidation()
	{
		return validation;
	}

	public static void setValidation(List<Validation> validation)
	{
		IoXslUtil.validation = validation;
	}

	public static void clearValidation()
	{
		IoXslUtil.validation.clear();
	}

	public static String addValidation(Validation add)
	{
		String name = "";
		List<Validation> listuis = getValidation();
		if (listuis.size() == 0)
		{
			listuis.add(add);
			name = "VD" + 1;
		} else
		{
			boolean flg = false;
			for (int i = 0; i < listuis.size(); i++)
			{
				Validation current = listuis.get(i);
				if (add.equals(current))
				{
					flg = true;
					return name;
				}
			}
			if (!flg)
			{
				listuis.add(add);
				name = "VD" + listuis.size();
			}
		}
		return name;
	}

	@SuppressWarnings("unchecked")
	public static String getValidationCode(Validation validation, int index)
	{
		StringBuffer code = new StringBuffer();
		String ref = com.wisii.wisedoc.io.xsl.attribute.edit.EnumPropertyWriter.WDWEMSNS;
		String name = "VD" + (index + 1);
		String type = validation.getValidate();
		String msg = validation.getMsg();
		code.append(ElementWriter.TAGBEGIN);
		code.append(ref + "validation");
		code.append(ElementUtil.outputAttributes("name", name));
		code.append(ElementUtil.outputAttributes("validate", type));
		code.append(ElementUtil.outputAttributes("msg", msg));
		code.append(ElementWriter.TAGEND);

		List parms = validation.getParms();
		if (parms != null && !parms.isEmpty())
		{
			for (Object current : parms)
			{
				if (current instanceof String)
				{
					code.append(ElementUtil.startElement(ref + "para"));
					code.append(current.toString());
					code.append(ElementUtil.endElement(ref + "para"));
				} else if (current instanceof BindNode)
				{
					BindNode node = (BindNode) current;
					code.append(ElementWriter.TAGBEGIN);
					code.append(ref + "para");
					code.append(ElementUtil.outputAttributes("xpath",
							getAbsoluteXPath(IoXslUtil.getXSLXpath(node))));
					code.append(ElementWriter.NOCHILDTAGEND);
				}
			}
		}
		code.append(ElementUtil.endElement(ref + "validation"));
		return code.toString();
	}

	public static List<DataSource> getDatasource()
	{
		return datasource;
	}

	public static void clearDatasource()
	{
		datasource.clear();
	}

	public static void addDataTransformTable(DataSource source)
	{
		List<DataSource> listuis = getDatasource();
		for (DataSource current : listuis)
		{
			if (current.equals(source))
			{
				return;
			}
		}
		listuis.add(source);
	}

	public static List<SelectInfo> getDatasourceInfo()
	{
		return datasourceinfo;
	}

	public static void clearDatasourceInfo()
	{
		datasourceinfo.clear();
	}

	public static String addDatasourceInfo(SelectInfo source)
	{
		String name = "";
		List<SelectInfo> listuis = getDatasourceInfo();
		if (listuis.size() == 0)
		{
			listuis.add(source);
			DataSource ds=source.getDatasource();
			name = "DATASOURCEINFO" + 0;
		} else
		{
			boolean flg = false;
			for (int i = 0; i < listuis.size(); i++)
			{
				SelectInfo current = listuis.get(i);
				if (source.equals(current))
				{
					flg = true;
					return "DATASOURCEINFO" + i;
				}
			}
			if (!flg)
			{
				name = "DATASOURCEINFO" + listuis.size();
				DataSource ds=source.getDatasource();
				if(ds instanceof SwingDS)
				{
					addDataTransformTable(ds);
				}
				listuis.add(source);
			}
		}
		return name;
	}

	public static List<String> getStaticcontentnames()
	{
		return nonullstaticcontentnames;
	}

	public static void addStaticcontent(String name)
	{
		if (name != null)
		{
			List<String> namelist = getStaticcontentnames();
			for (String current : namelist)
			{
				if (current.equals(name))
				{
					return;
				}
			}
			namelist.add(name);
		}
	}

	public static void clearStaticcontent()
	{
		List<String> namelist = getStaticcontentnames();
		namelist.clear();
	}

	public static boolean isStaticcontentContainer(String name)
	{
		if (name != null)
		{
			List<String> namelist = getStaticcontentnames();
			for (String current : namelist)
			{
				if (current.equals(name))
				{
					return true;
				}
			}
		}
		return false;
	}

	public static String getFilename()
	{
		return filename;
	}

	public static void setFilename(String filename)
	{
		IoXslUtil.filename = filename;
	}

	@SuppressWarnings("unchecked")
	public static String returnStringIfPosition(LogicalExpression condition,
			boolean isfirest, String topXpath)
	{
		if (condition == null)
		{
			return "";
		}
		List listCondition = condition.getConditions();
		List<Integer> listType = condition.getOperators();

		StringBuffer output = new StringBuffer();
		if (listCondition != null)
		{
			int number = listCondition.size();
			for (int i = 0; i < number; i++)
			{
				Object current = listCondition.get(i);

				if (current instanceof LogicalExpression)
				{
					LogicalExpression cond = (LogicalExpression) current;
					if (!isfirest)
					{
						output.append("(");
					}
					if (number == 1)
					{
						output.append(returnStringIfPosition(cond, true,
								topXpath));
					} else
					{
						output.append(returnStringIfPosition(cond, false,
								topXpath));
					}
					if (!isfirest)
					{
						output.append(")");
					}
				} else if (current instanceof Condition)
				{
					Condition cond = (Condition) current;
					if (!(isfirest && number == 1))
					{
						output.append("(");
					}
					output.append(returnStringIfPosition(cond, topXpath));
					if (!(isfirest && number == 1))
					{
						output.append(")");
					}
				}

				if (listType != null && listType.size() > 0 && i < number - 1)
				{
					output.append(ElementUtil.returnType(listType.get(i)));
				}
			}
		}
		return output.toString();
	}

	public static String returnStringIfPosition(Condition condition,
			String topXpath)
	{
		BindNode node = condition.getNode();
		int type = condition.getType();
		Object param = condition.getParam();
		String nodeStr = IoXslUtil.comparePath(topXpath, IoXslUtil
				.getXSLXpath(node));
		String typeStr = ElementUtil.returnYunsuanfu(type);
		String paramStr = "";
		if (param instanceof BindNode)
		{
			BindNode value = (BindNode) param;
			paramStr = IoXslUtil.comparePath(topXpath, IoXslUtil
					.getXSLXpath(value));
		} else if (param instanceof String)
		{
			paramStr = IoXslUtil.getXmlText(param.toString());
			if (paramStr.equalsIgnoreCase("@null"))
			{
				paramStr = "";
			} else if (paramStr.equalsIgnoreCase("@dnull"))
			{
				paramStr = "";
				nodeStr = "normalize-space(" + nodeStr + ")";
			}
		}
		StringBuffer output = new StringBuffer();
		if (type == Condition.STRINGNOTEQUAL)
		{
			output.append("not(contains(" + nodeStr + ",");
			if (param instanceof String)
			{
				output.append("'");
			}
			output.append(paramStr);
			if (param instanceof String)
			{
				output.append("'");
			}
			output.append(") and contains(");
			if (param instanceof String)
			{
				output.append("'");
			}
			output.append(paramStr);
			if (param instanceof String)
			{
				output.append("'");
			}
			output.append("," + nodeStr + "))");
		} else if (type == Condition.STRINGEQUAL)
		{
			output.append("contains(" + nodeStr + ",");
			if (param instanceof String)
			{
				output.append("'");
			}
			output.append(paramStr);
			if (param instanceof String)
			{
				output.append("'");
			}
			output.append(") and contains(");
			if (param instanceof String)
			{
				output.append("'");
			}
			output.append(paramStr);
			if (param instanceof String)
			{
				output.append("'");
			}
			output.append("," + nodeStr + ")");
		} else if (type == Condition.FIRST)
		{
			output.append("position()=1");
		} else if (type == Condition.NOTFIRST)
		{
			output.append("position()&gt;1");
		} else if (type == Condition.LAST)
		{
			output.append("position()=last()");
		} else if (type == Condition.NOTLAST)
		{
			output.append("position()&lt;last()");
		} else if (type == Condition.ODD)
		{
			output.append("(position() mod 2)=1");
		} else if (type == Condition.EVEN)
		{
			output.append("(position() mod 2)=0");
		} else if (type == Condition.POSITIONLESS)
		{
			output.append("position()&lt;" + param);
		} else if (type == Condition.POSITIONGREATER)
		{
			output.append("position()&gt;" + param);
		} else if (type == Condition.POSITION)
		{
			output.append("position()=" + param);
		}
		else if(type>=Condition.POSITIONMOD3&&type<Condition.POSITIONMOD9)
		{
			output.append("(position() mod "+(type+3-Condition.POSITIONMOD3)+")="+param);
		}
		else if (type == Condition.LENGTHLESS)
		{
			output.append("string-length(" + nodeStr + ")&lt;" + paramStr);
		} else if (type == Condition.LENGTHGREATER)
		{
			output.append("string-length(" + nodeStr + ")&gt;" + paramStr);
		} else if (type == Condition.EQUAL)
		{
			output.append("number(" + nodeStr + ")=number(" + paramStr + ")");
		} else if (type == Condition.REGULAREQUAL)
		{
			paramStr = param.toString();
			NameSpace textlinesNameSpace = new NameSpace("xmlns:regular",
					"com.wisii.wisedoc.io.xsl.util.Regular");
			IoXslUtil.addNameSpace(textlinesNameSpace);
			if (param instanceof BindNode)
			{
				output.append("regular:getResult(" + paramStr + "," + nodeStr
						+ ")");
			} else if (param instanceof String)
			{
				output.append("regular:getResult('" + paramStr + "'," + nodeStr
						+ ")");
			}
		}
		else if (type == Condition.COUNT)
		{
			output.append("count(" + nodeStr + ")=" + param);
		} else if (type == Condition.COUNTLESS)
		{
			output.append("count(" + nodeStr + ")&lt;" + param);
		} else if (type == Condition.COUNTGREATER)
		{
			output.append("count(" + nodeStr + ")&gt;" + param);
		} else if (type == Condition.COUNTODD)
		{
			output.append("(count(" + nodeStr + ") mod 2)=1");
		} else if (type == Condition.COUNTEVEN)
		{
			output.append("(count(" + nodeStr + ") mod 2)=0");
		}
		else if(type>=Condition.COUNTMOD3&&type<Condition.COUNTMOD9)
		{
			output.append("(count(" + nodeStr + ") mod "+(type+3-Condition.COUNTMOD3)+")="+param);
		}
		else
		{
			output.append(nodeStr + typeStr + paramStr);
		}
		return output.toString();
	}

	public static String getXSLXpath(BindNode node)
	{
		if (node != null)
		{
			String nodeName = node.getNodeName();
			String attstr = (node instanceof AttributeBindNode) ? "@" : "";
			if (node.getParent() != null)
			{
				return IoXslUtil
						.getXSLXpath((DefaultBindNode) node.getParent())
						+ "/" + attstr + nodeName;
			} else
			{
				if (IOFactory.isIfHaveSql())
				{
					String zjnodename = new String(nodeName).trim();
					if (zjnodename.startsWith("/"))
					{
						zjnodename = zjnodename.replaceFirst("/root", "");
					} else
					{
						zjnodename = zjnodename.replaceFirst("root", "");
					}
					String rootpath = "";
					if (ObjectXslWriter.isSub())
					{
						rootpath = "root/subs/" + IoXslUtil.getFilename();
					} else if (MainXSLWriter.isMain())
					{
						rootpath = "root/main";
					} else
					{
						String filename = IoXslUtil.getFilename();
						if (filename == null || "".equals(filename.trim()))
						{
							rootpath = "root";
						} else
						{
							rootpath = "root/" + filename;
						}
					}
					if (zjnodename.startsWith("/"))
					{
						rootpath = rootpath + zjnodename;
					} else if (!"".equals(zjnodename))
					{
						rootpath = rootpath + "/" + attstr + zjnodename;
					}
					return rootpath;
				}
				return nodeName;
			}
		}
		return "";
	}

	/**
	 * @return the "block-progression-dimension" property.
	 */
	public static LengthRangeProperty getLengthRangeProperty(
			CondLengthProperty one, CondLengthProperty two,
			LengthRangeProperty length)
	{
		int padingheight = 0;
		if (one != null)
		{
			padingheight = one.getLengthValue();
		}
		if (two != null)
		{
			padingheight = padingheight + two.getLengthValue();
		}
		// 由于FO的pading属性石往外扩的，所以高度需要减去高度方向上的pading
		if (padingheight > 0)
		{
			LengthProperty optheight = length.getOptimum(null);
			// 如果是固定长度，则减去相应的pading
			if (optheight instanceof FixedLength)
			{
				return new LengthRangeProperty(new FixedLength(optheight
						.getValue()
						- padingheight));
			}
		}
		return length;
	}
}