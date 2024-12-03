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

package com.wisii.io.zhumoban;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;
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
import com.wisii.wisedoc.document.attribute.transform.DataSource;
import com.wisii.wisedoc.io.IOUtil;
import com.wisii.wisedoc.io.xsl.WiseDocDocumentWriter;
import com.wisii.wisedoc.io.xsl.XslElementObj;
import com.wisii.wisedoc.io.xsl.attribute.edit.DataSourcesWriter;
import com.wisii.wisedoc.io.xsl.attribute.edit.EnumPropertyWriter;
import com.wisii.wisedoc.io.xsl.util.DefaultValueMap;
import com.wisii.wisedoc.io.xsl.util.EditKeyAndValueUtil;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

public class MainXSLWriter
{

	private static boolean haveZimoban = false;

	String functiontemplate = "";

	String datasource = "";

	String zimobannames = "";

	private static List<String> zimoban = new ArrayList<String>();

	private static boolean isMain = false;

	public MainXSLWriter()
	{

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.Writer#write(java.io.OutputStream,
	 * javax.swing.text.Document)
	 */
	public void write(OutputStream outstream, Document document, String filename)
			throws IOException
	{
		writeNoStartAndClose(outstream, document, filename);
		if (outstream instanceof ZipOutputStream)
		{
			((ZipOutputStream) outstream).closeEntry();
		} else
		{
			outstream.close();
		}
	}
	private void writeNoStartAndClose(OutputStream outstream, Document document,
			String filename) throws IOException
	{

		setMain(true);
		IoXslUtil.setFilename(filename.replaceFirst(".xsl", "").trim());
		DefaultValueMap.setInitMap();
		EnumPropertyWriter.initializationValueMap();
		EditKeyAndValueUtil.initializationKeyMap();
		checkPageIndex(document);
		StringBuffer out = new StringBuffer();
		WiseDocDocumentWriter wiseDocDocumentWriter = new WiseDocDocumentWriter(
				(WiseDocDocument) document);
		String content = wiseDocDocumentWriter.writeMore();
		out.append(getOutputElement());

		out.append(addOverall());

		if (out != null && out.length()>0)
		{
			byte[] bts = out.toString().getBytes("UTF-8");
			outstream.write(IOUtil.encrypt(bts));
			outstream.flush();
		}

		if (content != null && !content.isEmpty())
		{
			byte[] bts = content.toString().getBytes("UTF-8");
			outstream.write(IOUtil.encrypt(bts));
			outstream.flush();
		}
		setFunctiontemplate(addFuncAndPro());
		setDatasource(addDataSourceCode());
		setZimobannames(dealZimoban());
		clear();
		initialization();
		DefaultValueMap.clearCompleteAttributes();
		setMain(false);
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.Writer#write(java.io.OutputStream,
	 * javax.swing.text.Document)
	 */
	public void writeNoClose(OutputStream outstream, Document document,
			String filename) throws IOException
	{
		setMain(true);
		IoXslUtil.setFilename(filename.replaceFirst(".xsl", "").trim());
		DefaultValueMap.setInitMap();
		EnumPropertyWriter.initializationValueMap();
		EditKeyAndValueUtil.initializationKeyMap();
		checkPageIndex(document);
		StringBuffer out = new StringBuffer();
		WiseDocDocumentWriter wiseDocDocumentWriter = new WiseDocDocumentWriter(
				(WiseDocDocument) document);
		String content = wiseDocDocumentWriter.writeMore();
		out.append(getEncoding());

		out.append(getVersionAndDate());

		out.append(getStylesheetStart());

		out.append(getOutputElement());

		out.append(addOverall());

		if (out != null && out.length()>0)
		{
			byte[] bts = out.toString().getBytes("UTF-8");
			outstream.write(IOUtil.encrypt(bts));
			outstream.flush();
		}

		if (content != null && !content.isEmpty())
		{
			byte[] bts = content.toString().getBytes("UTF-8");
			outstream.write(IOUtil.encrypt(bts));
			outstream.flush();
		}
		setFunctiontemplate(addFuncAndPro());
		setDatasource(addDataSourceCode());
		setZimobannames(dealZimoban());
		clear();
		initialization();
		DefaultValueMap.clearCompleteAttributes();
		setMain(false);
	}

	public String dealZimoban()
	{
		List<String> zms = getZimoban();
		StringBuffer result = new StringBuffer();
		int size = zms.size();
		if (size > 0)
		{
			for (int i = 0; i < zms.size(); i++)
			{
				result.append(zms.get(i));
				if (i != size - 1)
				{
					result.append(",");
				}
			}
		}
		return result.toString();
	}

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
		MainXSLWriter.setHaveZimoban(false);
		MainXSLWriter.setZimoban(new ArrayList<String>());
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
		IoXslUtil.clearXpath();
		IoXslUtil.initializationOverall();
		IoXslUtil.clearFunctionTemplate();
		IoXslUtil.clearProfile();
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
		IoXslUtil.addNameSpace(new NameSpace(FoXsltConstants.SPACENAMEFO,
				FoXsltConstants.XSL_FO_NAMESPACE));
		IoXslUtil.addNameSpace( new NameSpace(FoXsltConstants.SPACENAMEXSL,
				FoXsltConstants.XSLT_NAMESPACE));

		IoXslUtil.addNameSpace(new NameSpace(FoXsltConstants.SPACENAMESVG,
				FoXsltConstants.SVG_NAMESPACE));
		// nameSpaces.add(new NameSpace(FoXsltConstants.SPACENAMEMY,
		// FoXsltConstants.XSLT_NAMESPACE));
		IoXslUtil.addNameSpace(new NameSpace(FoXsltConstants.SPACENAMEFOV,
				FoXsltConstants.COMWISIIFOV));

		IoXslUtil.addNameSpace(new NameSpace(FoXsltConstants.SPACENAMEBARCODE,
				FoXsltConstants.SPACEVALUEBARCODE));
		IoXslUtil.addNameSpace(new NameSpace(FoXsltConstants.SPACENAME_DATE,
				FoXsltConstants.DATE_NAMESPACE));

		IoXslUtil.addNameSpace(new NameSpace(FoXsltConstants.SPACENAMESAXON,
				FoXsltConstants.SAXON_NAMESPACE));
		IoXslUtil.addNameSpace(new NameSpace(FoXsltConstants.EXCLUDERESULTPREFIXES,
				FoXsltConstants.MATH));
		IoXslUtil.addNameSpace(new NameSpace("xmlns:regular",
				"com.wisii.wisedoc.io.xsl.util.Regular"));

		IoXslUtil.addNameSpace(new NameSpace("xmlns:piebar",
				"com.wisii.wisedoc.io.xsl.util.BarPieTemplate"));
		IoXslUtil.addNameSpace(new NameSpace(FoXsltConstants.SPACENAMEWDWE,
				FoXsltConstants.WDWE));
		IoXslUtil.addNameSpace(new NameSpace(FoXsltConstants.XMLNSMATH,
				FoXsltConstants.MATHNAMESPACE));
		IoXslUtil.addNameSpace(new NameSpace(FoXsltConstants.SPACENAMEWISII,
				FoXsltConstants.WISII));
		IoXslUtil.addNameSpace(new NameSpace("xmlns:textlines",
				"com.wisii.wisedoc.io.xsl.util.TextLines"));
		IoXslUtil.addNameSpace(new NameSpace("xmlns:wisiiextend",
				"com.wisii.wisedoc.io.xsl.util.WisiiXslUtil"));
		List<NameSpace> nameSpaces=IoXslUtil.getNameSpace();
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
		IoXslUtil.clearNameSpace();
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
		String result = "<!--Designer版本号：" + version + "    生成模板时间："
				+ dataTimeFormat + "  请勿修改本文件，违者后果自负！-->";
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
		if (MainXSLWriter.isHaveZimoban())
		{
			return "";
		} else
		{
			return ElementUtil.endElement(FoXsltConstants.STYLESHEET);
		}
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
		Map<Integer, Object> attributemapnum = new HashMap<Integer, Object>();
		attributemapnum.put(
				com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_NAME, "num");
		attributemapnum.put(
				com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_SELECT,
				"document('')/xsl:stylesheet");
		XslElementObj xslelementnum = new XslElementObj(attributemapnum,
				FoXsltConstants.VARIABLE);
		IoXslUtil.addOverall(xslelementnum);
		int size = xslelementobj.size();
		for (int i = 0; i < size; i++)
		{
			XslElementObj namespaceitem = xslelementobj.get(i);
			output.append(namespaceitem.getCode());
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
						+IoXslUtil.getFilename() ));
			}
		}
		return output.toString();
	}

	public static boolean isHaveZimoban()
	{
		return haveZimoban;
	}

	public static void setHaveZimoban(boolean haveZimoban)
	{
		MainXSLWriter.haveZimoban = haveZimoban;
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

	public static List<String> getZimoban()
	{
		return zimoban;
	}

	public static void setZimoban(List<String> zimoban)
	{
		MainXSLWriter.zimoban = zimoban;
	}

	public static void addZimoban(String zimoban)
	{
		List<String> old = getZimoban();
		if (!old.contains(zimoban))
		{
			old.add(zimoban);
		}
	}

	public String getZimobannames()
	{
		return zimobannames;
	}

	public void setZimobannames(String zimobannames)
	{
		this.zimobannames = zimobannames;
	}

	public static boolean isMain()
	{
		return isMain;
	}

	public static void setMain(boolean isSub)
	{
		MainXSLWriter.isMain = isSub;
	}
}
