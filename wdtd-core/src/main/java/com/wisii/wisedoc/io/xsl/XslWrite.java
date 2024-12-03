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
 * @XslWrite.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;

import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.banding.io.NameSpace;
import com.wisii.wisedoc.configure.ConfigureUtil;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentUtil;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.TableContents;
import com.wisii.wisedoc.document.TotalPageNumber;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.edit.Validation;
import com.wisii.wisedoc.document.attribute.transform.DataSource;
import com.wisii.wisedoc.document.attribute.transform.SelectInfo;
import com.wisii.wisedoc.io.Writer;
import com.wisii.wisedoc.io.xsl.attribute.edit.DataSourcesInfoWriter;
import com.wisii.wisedoc.io.xsl.attribute.edit.DataSourcesWriter;
import com.wisii.wisedoc.io.xsl.attribute.edit.EditUI;
import com.wisii.wisedoc.io.xsl.attribute.edit.EditUIWriter;
import com.wisii.wisedoc.io.xsl.attribute.edit.EnumPropertyWriter;
import com.wisii.wisedoc.io.xsl.util.DefaultValueMap;
import com.wisii.wisedoc.io.xsl.util.EditKeyAndValueUtil;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;
import com.wisii.wisedoc.util.SystemUtil;
import com.wisii.wisedoc.util.WisedocUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-8-21
 */
public class XslWrite implements Writer
{

	DataStructureTreeModel ds;
	public XslWrite()
	{

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.Writer#write(java.io.OutputStream,
	 * javax.swing.text.Document)
	 */
	public void write(OutputStream outstream, Document document)
			throws IOException
	{
		DefaultValueMap.setInitMap();
		EnumPropertyWriter.initializationValueMap();
		EditKeyAndValueUtil.initializationKeyMap();
		checkPageIndex(document);
		java.io.Writer writer = new OutputStreamWriter(outstream, "UTF-8");
		PrintWriter out = new PrintWriter(writer);
		WiseDocDocumentWriter wiseDocDocumentWriter = new WiseDocDocumentWriter(
				(WiseDocDocument) document);
		String content = wiseDocDocumentWriter.writeMore();
		ds=document.getDataStructure();
		if(ds!=null)
		{
			List<NameSpace> nses=ds.getSpaces();
			if(nses!=null)
			{
				for(NameSpace ns:nses){
				IoXslUtil.addNameSpace(ns);
				}
			}
		}
		out.print(getEncoding());
		out.print(getVersionAndDate());
		out.print(getStylesheetStart());
		out.print(getOutputElement());
		out.print(addOverall());
		out.print(content);
		out.print(addFunctionTemplate());
		out.print(addProFiles());
		out.print(addEditStart());
		out.print(addUIEdit());
		out.print(addDataSourceCode());
		out.print(addDataSourceInfoCode());
		out.flush();
		out.print(addValidationCode());
		out.print(addEditEnd(document));
		out.print(getStylesheetEnd());
		out.flush();
		out.close();
		clear();
		initialization();
		DefaultValueMap.clearCompleteAttributes();
		System.gc();
	}

	public void write(String filename, Document document) throws IOException
	{
		File file = new File(filename);
		String fileName = file.getName().replaceFirst(".xsl", "");
		IoXslUtil.setFilename(fileName);
		FileOutputStream outstream = new FileOutputStream(file);
		DefaultValueMap.setInitMap();
		EnumPropertyWriter.initializationValueMap();
		EditKeyAndValueUtil.initializationKeyMap();
		checkPageIndex(document);
		java.io.Writer writer = new OutputStreamWriter(outstream, "UTF-8");
		PrintWriter out = new PrintWriter(writer);
		WiseDocDocumentWriter wiseDocDocumentWriter = new WiseDocDocumentWriter(
				(WiseDocDocument) document);
		String content = wiseDocDocumentWriter.writeMore();
		ds=document.getDataStructure();
		if(ds!=null)
		{
			List<NameSpace> nses=ds.getSpaces();
			if(nses!=null)
			{
				for(NameSpace ns:nses){
				IoXslUtil.addNameSpace(ns);
				}
			}
		}
		out.print(getEncoding());
		out.print(getVersionAndDate());
		out.print(getStylesheetStart());
		out.print(getOutputElement());
		out.print(addOverall());
		out.print(content);
		out.print(addFunctionTemplate());
		out.print(addProFiles());
		out.print(addEditStart());
		out.print(addUIEdit());
		out.print(addDataSourceCode());
		out.print(addDataSourceInfoCode());
		
		out.print(addValidationCode());
		out.print(addEditEnd(document));
		out.print(getStylesheetEnd());
		out.flush();
		out.close();
		clear();
		initialization();
		DefaultValueMap.clearCompleteAttributes();
		System.gc();
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
		IoXslUtil.initializationNameSpace();
		IoXslUtil.setFilename("");
	}

	@SuppressWarnings("unchecked")
	public void checkPageIndex(Document document)
	{
		IoXslUtil.setAddPageNumberType(false);
		IoXslUtil.setHavaindex(false);
		if (document != null)
		{
			Enumeration<Object> list = document.children();
			while (list.hasMoreElements())
			{
				PageSequence pagesequence = (PageSequence) list.nextElement();
				List<CellElement> pagenumberlist = DocumentUtil.getElements(
						pagesequence, TotalPageNumber.class);
				if (!IoXslUtil.getAddPageNumberType()
						&& !pagenumberlist.isEmpty())
				{
					for (int i = 0; i < pagenumberlist.size(); i++)
					{
						TotalPageNumber current = (TotalPageNumber) pagenumberlist
								.get(i);
						IoXslUtil.setHavaindex(true);
						if (current.getAttribute(Constants.PR_ENDOFALL) != null)
						{
							EnumProperty showtotal = (EnumProperty) current
									.getAttribute(Constants.PR_ENDOFALL);
							int showTotal = showtotal == null ? 0 : showtotal
									.getEnum();
							if (showTotal == Constants.EN_TRUE)
							{
								IoXslUtil.setAddPageNumberType(true);
							}
						}
					}
				}
				List<CellElement> indexlist = DocumentUtil.getElements(
						pagesequence, TableContents.class);
				if (!IoXslUtil.isHavaindex() && !indexlist.isEmpty())
				{
					IoXslUtil.setHavaindex(true);
				}
				if (IoXslUtil.getAddPageNumberType() && IoXslUtil.isHavaindex())
				{
					break;
				}
			}
		}
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
	 * 获取xsl根元素的头部代码
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String getStylesheetStart()
	{
		StringBuffer output = new StringBuffer();
		output.append("<");
		output.append(FoXsltConstants.STYLESHEET);
		List<NameSpace> nameSpaces = IoXslUtil.getNameSpace();
		
		NameSpace nameSpaceFo = new NameSpace(FoXsltConstants.SPACENAMEFO,
				FoXsltConstants.XSL_FO_NAMESPACE);
		NameSpace nameSpaceXsl = new NameSpace(FoXsltConstants.SPACENAMEXSL,
				FoXsltConstants.XSLT_NAMESPACE);
		NameSpace namewisii = new NameSpace(FoXsltConstants.SPACENAMEWISII,
				FoXsltConstants.WISII);
		nameSpaces.add(nameSpaceFo);
		nameSpaces.add(nameSpaceXsl);
		nameSpaces.add(namewisii);
		int size = nameSpaces.size();
		for (int i = 0; i < size; i++)
		{
			NameSpace namespaceitem = nameSpaces.get(i);
			output.append(ElementUtil
					.outputAttributes(namespaceitem.getAttribute(), namespaceitem.getAttributeValue()));
		}
		output.append(ElementUtil.outputAttributes(FoXsltConstants.VERSION,
				FoXsltConstants.VERSION_NUM));
		output.append(">");
		return output.toString();
	}

	/**
	 * 
	 * 获取xsl根元素的头部代码
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String getEncoding()
	{
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	}

	public String getVersionAndDate()
	{
		String version = ConfigureUtil.getProperty("realse");
		String dataTimeFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒")
				.format(Calendar.getInstance().getTime());
		String result = "<!--此模板为汇智互联(www.wisii.com)设计器生成，版本号为：" + version + "    生成模板时间："
				+ dataTimeFormat +   "  -->";
		if(ds != null){
			result = "<!--此模板为汇智互联(www.wisii.com)设计器生成，版本号为：" + version + "    生成模板时间："
					+ dataTimeFormat + ";datapath=" + ds.getDatapath() + "  -->";
		}
		return result;
	}

	/**
	 * 
	 * 获取xsl根元素的头部代码
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String getOutputElement()
	{
		StringBuffer output = new StringBuffer();
		output.append("<");
		output.append(FoXsltConstants.OUTPUT);
		output.append(ElementUtil.outputAttributes(FoXsltConstants.METHOD,
				FoXsltConstants.XML));
		output.append(ElementUtil.outputAttributes(FoXsltConstants.ENCODING,
				FoXsltConstants.UTF_8));
		output.append(ElementUtil.outputAttributes(FoXsltConstants.INDENT,
				FoXsltConstants.NO));
		output.append(ElementUtil.outputAttributes(FoXsltConstants.VERSION,
				FoXsltConstants.VERSION_NUM));
		output.append("/>");
		return output.toString();
	}

	/**
	 * 
	 * 获取xsl根元素的尾部代码
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String getStylesheetEnd()
	{
		return ElementUtil.endElement(FoXsltConstants.STYLESHEET);
	}


	/**
	 * 
	 * 添加全局变量或参数的定义
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String addOverall()
	{
		StringBuffer output = new StringBuffer();
		List<XslElementObj> xslelementobj = IoXslUtil.getOverall();
		int size = xslelementobj.size();
		for (int i = 0; i < size; i++)
		{
			XslElementObj namespaceitem = xslelementobj.get(i);
			output.append(namespaceitem.getCode());
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
	public String addFunctionTemplate() throws IOException
	{
		StringBuffer output = new StringBuffer();
		List<String> functionTemplate = IoXslUtil.getFunctionTemplate();
		int size = functionTemplate.size();
		StringBuffer filePath;
		for (int i = 0; i < size; i++)
		{
			String templateName = functionTemplate.get(i);
			// System.out.println("mo ban ::"+templateName);
			filePath = new StringBuffer();
			filePath.append(System.getProperty("user.dir") + "/xslt");
			filePath.append("/");
			filePath.append(templateName);
			filePath.append(".xml");
			InputStreamReader isr = new InputStreamReader(new FileInputStream(
					filePath.toString()), "UTF-8");
			// System.out.println("filePath ::"+filePath.toString());
			int c = 0;
			while ((c = isr.read()) != -1)
			{
				output.append((char) c);
			}
			isr.close();
		}
		// System.out.println("m ::"+output.toString());
		return output.toString();
	}

	/**
	 * 
	 * 添加配置文件
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @throws IOException
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String addProFiles() throws IOException
	{
		StringBuffer output = new StringBuffer();
		List<String> profile = IoXslUtil.getProfile();
		int size = profile.size();
		StringBuffer filePath;
		for (int i = 0; i < size; i++)
		{
			String profileName = profile.get(i);
			filePath = new StringBuffer();
			filePath.append(System.getProperty("user.dir") + "/xslt");
			filePath.append("/");
			filePath.append(profileName);
			filePath.append(".xml");
			InputStreamReader isr = new InputStreamReader(new FileInputStream(
					filePath.toString()), "UTF-8");
			int c = 0;
			while ((c = isr.read()) != -1)
			{
				output.append((char) c);
			}
			isr.close();
		}
		return output.toString();
	}


	public String addUIEdit()
	{
		String output = "";
		if (IoXslUtil.isXmlEditable() == Constants.EN_EDITABLE
				|| IoXslUtil.isXmlEditable() == Constants.EN_EDITVARIBLE)
		{
			output = output + addUIEditCode();
		}
		return output;
	}

	public String addEditStart()
	{
		if (IoXslUtil.isXmlEditable() == Constants.EN_EDITABLE
				|| IoXslUtil.isXmlEditable() == Constants.EN_EDITVARIBLE)
		{
			return "<wdems:wdems xmlns:wdems=\"http://www.wisii.com/wdems\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">";
		}
		return "";
	}

	public String addEditEnd(Document document)
	{
		String result = "";
		if (IoXslUtil.isXmlEditable() == Constants.EN_EDITABLE
				|| IoXslUtil.isXmlEditable() == Constants.EN_EDITVARIBLE)
		{
			result = result
					+ ((WiseDocDocument) document).getListEditUIString();
			result = result + "</wdems:wdems>";
		}
		return result;
	}

	public String addUIEditCode()
	{
		StringBuffer output = new StringBuffer();
		List<EditUI> listuis = IoXslUtil.getEdituis();
		for (EditUI current : listuis)
		{
			output.append(new EditUIWriter(current).getCode());
		}
		return output.toString();
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

	public String addValidationCode()
	{
		StringBuffer output = new StringBuffer();
		List<Validation> listuis = IoXslUtil.getValidation();
		for (int i = 0; i < listuis.size(); i++)
		{
			Validation current = listuis.get(i);
			output.append(IoXslUtil.getValidationCode(current, i));
		}
		return output.toString();
	}
	
}
