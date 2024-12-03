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

package com.wisii.wisedoc.io.xsl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.document.attribute.transform.DataSource;
import com.wisii.wisedoc.document.attribute.transform.SelectInfo;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.ElementWriterFactory;
import com.wisii.wisedoc.io.IOUtil;
import com.wisii.wisedoc.io.xsl.attribute.edit.DataSourcesInfoWriter;
import com.wisii.wisedoc.io.xsl.attribute.edit.DataSourcesWriter;
import com.wisii.wisedoc.io.xsl.attribute.edit.EnumPropertyWriter;
import com.wisii.wisedoc.io.xsl.util.DefaultValueMap;
import com.wisii.wisedoc.io.xsl.util.EditKeyAndValueUtil;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

public class ObjectXslWriter
{

	ElementWriterFactory elementwriterfactory = new SelectElementWriterFactory();

	String functiontemplate = "";

	String datasource = "";

	private static boolean isSub = false;

	@SuppressWarnings("unchecked")
	public void write(OutputStream outstream, Document document, String filename)
			throws IOException
	{
		setSub(true);
		IoXslUtil.setFilename(filename.replaceFirst(".xsl", "").trim());
		DefaultValueMap.setInitMap();
		EnumPropertyWriter.initializationValueMap();
		EditKeyAndValueUtil.initializationKeyMap();
		if (document != null)
		{
			StringBuffer content = new StringBuffer();
			content.append(ElementWriter.TAGBEGIN);
			content.append(FoXsltConstants.TEMPLATE);
			content.append(ElementUtil.outputAttributes(FoXsltConstants.NAME,
					filename));
			content.append(ElementWriter.TAGEND);
			Enumeration<Object> list = document.children();
			Attributes Attributes = document.getAttributes();
			Map<Integer, Object> map = Attributes == null ? null : Attributes
					.getAttributes();
			Group group = map.containsKey(Constants.PR_GROUP) ? (Group) map
					.get(Constants.PR_GROUP) : null;
			if (group != null)
			{
				content.append(ElementUtil.startGroup(group));
			}
			while (list.hasMoreElements())
			{
				PageSequence pagesequence = (PageSequence) list.nextElement();
				Attributes Attributesps = pagesequence.getAttributes();
				Map<Integer, Object> mapps = Attributes == null ? null
						: Attributesps.getAttributes();
				Group groupps = mapps.containsKey(Constants.PR_GROUP) ? (Group) mapps
						.get(Constants.PR_GROUP)
						: null;
				if (groupps != null)
				{
					content.append(ElementUtil.startGroup(groupps));
				}
				Flow flow = pagesequence.getMainFlow();
				List<CellElement> elements = flow.getAllChildren();
				for (CellElement current : elements)
				{
					content.append(elementwriterfactory.getWriter(current)
							.write(current));
				}
				if (groupps != null)
				{
					content.append(ElementUtil.endGroup(groupps));
				}
			}
			if (group != null)
			{
				content.append(ElementUtil.endGroup(group));
			}
			content.append(ElementUtil.endElement(FoXsltConstants.TEMPLATE));
			if (content != null && !content.equals(""))
			{
				byte[] bts = content.toString().getBytes("UTF-8");
				outstream.write(IOUtil.encrypt(bts));
				outstream.flush();
			}
			setFunctiontemplate(addFuncAndPro());
			setDatasource(addDataSourceCode());
			clear();
			initialization();
			DefaultValueMap.clearCompleteAttributes();
			if (outstream instanceof ZipOutputStream)
			{
				((ZipOutputStream) outstream).closeEntry();
			} else
			{
				outstream.close();
			}
		}
		setSub(false);
	}

	/**
	 * 
	 * 初始化，为生成xsl模板代码做准备工作
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public void clear()
	{
		IoXslUtil.initializationOverall();
		EnumPropertyWriter.cleanKeyMap();
		EditKeyAndValueUtil.clearMap();
		IoXslUtil.clearEdituis();
		IoXslUtil.clearValidation();
		IoXslUtil.clearDatasource();
		IoXslUtil.clearDatasourceInfo();
		IoXslUtil.clearNameSpace();
		IoXslUtil.clearXpath();
		IoXslUtil.clearOverall();
		IoXslUtil.clearFunctionTemplate();
		IoXslUtil.clearProfile();
		IoXslUtil.clearStaticcontent();
	}

	/**
	 * 
	 * 初始化，为生成xsl模板代码做准备工作
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public void initialization()
	{
		IoXslUtil.setXmlEditable(Constants.EN_UNEDITABLE);
		IoXslUtil.setFilename("");
		IoXslUtil.initializationNameSpace();
	}

	public String addDataSourceCode()
	{
		StringBuffer output = new StringBuffer();
		List<DataSource> listdatasource = IoXslUtil.getDatasource();

		if (listdatasource != null && !listdatasource.isEmpty())
		{
			for (int i = 0; i < listdatasource.size(); i++)
			{
				DataSource current = listdatasource.get(i);
				output.append(new DataSourcesWriter(current).getcode(current
						.hashCode()
						+ IoXslUtil.getFilename()));
			}
		}
		return output.toString();
	}

	public String addDataSourceInfoCode()
	{
		StringBuffer output = new StringBuffer();
		List<SelectInfo> listdatasource = IoXslUtil.getDatasourceInfo();

		if (listdatasource != null && !listdatasource.isEmpty())
		{
			for (int i = 0; i < listdatasource.size(); i++)
			{
				SelectInfo current = listdatasource.get(i);
				output.append(new DataSourcesInfoWriter(current)
						.getcode("DATASOURCEINFO" + i));
			}
		}
		return output.toString();
	}

	/**
	 * 
	 * 添加功能模板
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @throws IOException
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String addFuncAndPro() throws IOException
	{
		StringBuffer output = new StringBuffer();
		List<String> functionTemplate = IoXslUtil.getFunctionTemplate();
		List<String> profile = IoXslUtil.getProfile();
		if (!profile.isEmpty())
		{
			for (String current : profile)
			{
				functionTemplate.add(current);
			}
		}
		if (!functionTemplate.isEmpty())
		{
			int size = functionTemplate.size();
			for (int i = 0; i < size; i++)
			{
				String templateName = functionTemplate.get(i);
				output.append(templateName);
				if (i < size - 1)
				{
					output.append(",");
				}
			}
		}
		return output.toString();
	}


	public String getFunctiontemplate()
	{
		return functiontemplate;
	}

	public void setFunctiontemplate(String functiontemplate)
	{
		this.functiontemplate = functiontemplate;
	}

	public String getDatasource()
	{
		return datasource;
	}

	public void setDatasource(String datasource)
	{
		this.datasource = datasource;
	}

	public static boolean isSub()
	{
		return isSub;
	}

	public static void setSub(boolean isSub)
	{
		ObjectXslWriter.isSub = isSub;
	}
}
