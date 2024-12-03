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
 * @HtmlWriter.java
 *                  北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.io.html;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import com.wisii.io.html.att.AttributesWriter;
import com.wisii.io.html.object.DefaultObjectWriter;
import com.wisii.wisedoc.banding.io.NameSpace;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.CommonMarginBlock;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.io.AbstractWriter;
import com.wisii.wisedoc.io.xsl.XslElementObj;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

/**
 * 类功能描述：
 * html模板输出主类
 * 根据Document对象，得到对应的html模板
 * 作者：zhangqiang
 * 创建日期：2012-5-25
 */
public class HtmlWriter extends AbstractWriter
{
	private HtmlContext xslcontext;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.Writer#write(java.io.OutputStream,
	 * com.wisii.wisedoc.document.Document)
	 */
	@Override
	public void write(OutputStream outstream, Document document)
			throws IOException
	{
		if (outstream == null || document == null)
		{
			return;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb
				.append("<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\" ");
		xslcontext = new HtmlContext();
		String contextstr = getContentString(document);
		List<NameSpace> nameSpaces = xslcontext.getNameSpaces();
		NameSpace namewisii = new NameSpace(FoXsltConstants.SPACENAMEWISII,
				FoXsltConstants.WISII);
		nameSpaces.add(namewisii);
		int size = nameSpaces.size();
		for (int i = 0; i < size; i++)
		{
			NameSpace namespaceitem = nameSpaces.get(i);
			sb.append(ElementUtil
					.outputAttributes(namespaceitem.getAttribute(), namespaceitem.getAttributeValue()));
		}
		sb
				.append("><xsl:output method=\"html\" encoding=\"UTF-8\"  version=\"4.0\"/>");
		sb.append(addOverall());
		sb.append("<xsl:template match=\"/\">");
		sb.append("<html><HEAD><TITLE>汇智互联单证预览</TITLE>");
		sb.append(getStyleString());
		sb.append("</HEAD>");
		sb.append("<body style=\"text-align:center\">");
		sb.append(contextstr);
		sb.append("</body></html>");
		sb.append("</xsl:template>");
		sb.append(getProfileString(xslcontext.getProfiles()));
		sb.append(addFunctionTemplate());
		sb.append("</xsl:stylesheet>");
		outstream.write(sb.toString().getBytes("UTF-8"));
	}
	private String getProfileString(List<String> profiles)
	{
		if (profiles == null || profiles.isEmpty())
		{
			return "";
		}
		String rootpath = System.getProperty("user.dir") + File.separator
				+ "xslt" + File.separator;
		StringBuffer sb = new StringBuffer();
		for (String profile : profiles)
		{
			String filepath = rootpath + profile + ".xml";
			InputStream in;
			try
			{
				in=new FileInputStream(filepath);
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] data=new byte[512];
				int read=-1;
				while((read=in.read(data))!=-1)
				{
					out.write(data, 0, read);
				}
				sb.append(new String(out.toByteArray(),"UTF-8"));
				in.close();
				out.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return sb.toString();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	private String getStyleString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<STYLE TYPE=\"text/css\">\n");
		String bodyattstr = AttributesWriter.getString(InitialManager.getInitalAttributes(), xslcontext);
		if(bodyattstr != null && !bodyattstr.isEmpty())
		{
			StringBuffer attsb = new StringBuffer();
			attsb.append("BODY ");
			attsb.append(" {");
			attsb.append(bodyattstr);
			attsb.append("}\n");
			xslcontext.addStyle(attsb.toString());
			xslcontext.addStyle("p {margin-top:0pt; margin-bottom:0pt}\n");
		}
		
		List<Attributes> attslist = xslcontext.getAttributelist();
		int size = attslist.size();
		for (int i = 0; i < size; i++)
		{
			Attributes att = attslist.get(i);
			String attstr = AttributesWriter.getString(att, xslcontext);
			if (attstr != null && !attstr.isEmpty())
			{
				StringBuffer attsb = new StringBuffer();
				attsb.append(".wisestle");
				attsb.append(i);
				attsb.append(" {");
				attsb.append(attstr);
				attsb.append("}\n");
				xslcontext.addStyle(attsb.toString());
			}
		}
		
		List<String> stylelist = xslcontext.getStylelist();
		if (stylelist != null)
		{
			for (String style : stylelist)
			{
				sb.append(style);
			}
		}
		sb.append("</STYLE>");
		return sb.toString();
	}

	private String getContentString(Document document)
	{
		StringBuffer sb = new StringBuffer();
		int childcount = document.getChildCount();
		PageSequenceWriter pswriter = new PageSequenceWriter();
		Attributes att = document.getAttributes();
		sb.append(DefaultObjectWriter.getDynicStringBegin(att, xslcontext));
		for (int i = 0; i < childcount; i++)
		{
			PageSequence child = (PageSequence) document.getChildAt(i);
			SimplePageMaster master = child.getNextSimplePageMaster(0, false,
					false, false);
			Attributes psatt = child.getAttributes();
			sb.append(DefaultObjectWriter.getDynicStringBegin(psatt, xslcontext));
			sb.append("<div style=\"");
			sb.append(getPageMasterString(master));
			if (i != childcount - 1)
			{
				sb.append(";page-break-after:always");
			}
			sb.append("\">");
			sb.append("<div style=\"margin:0px;padding:0px\">");
			sb.append(pswriter.getString(child, xslcontext));
			sb.append("</div></div>");
			sb.append("<xsl:if test=\"last()&gt;1 and position()&lt;last()\"><div style=\"page-break-before:always\"/></xsl:if>");
			sb.append(DefaultObjectWriter.getDynicStringEnd(psatt, xslcontext));
		}
		sb.append("<xsl:if test=\"last()&gt;1 and position()&lt;last()\"><div style=\"page-break-before:always\"/></xsl:if>");
		sb.append(DefaultObjectWriter.getDynicStringEnd(att, xslcontext));
		return sb.toString();
	}

	private String getPageMasterString(SimplePageMaster master)
	{
		int pagewidth = master.getPageWidth().getValue();
		CommonMarginBlock marging = master.getCommonMarginBlock();
		int bottom = marging.getMarginBottom().getValue();
		int top = marging.getMarginTop().getValue();
		int left = marging.getMarginLeft().getValue();
		int right = marging.getMarginRight().getValue();
		int contentwidth = pagewidth - left - right;
		return "margin: " + Math.round(top / 1000f) + "pt "
				+ Math.round(right / 1000f) + "pt "
				+ Math.round(bottom / 1000f) + "pt " + Math.round(left / 1000f)
				+ "pt; width: " + Math.round(contentwidth / 1000f) + "pt";
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
		List<XslElementObj> xslelementobj = xslcontext.getOverall();
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
	public String addFunctionTemplate()
	{
		StringBuffer output = new StringBuffer();
		List<String> functionTemplate = xslcontext.getFunctionTemplates();
		int size = functionTemplate.size();
		for (int i = 0; i < size; i++)
		{
			String templateName = functionTemplate.get(i);
			InputStream in;
			try
			{
				String filepath = System.getProperty("user.dir") + File.separator+"xslt"+ File.separator+templateName+".xml";
				in=new FileInputStream(filepath);
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] data=new byte[512];
				int read=-1;
				while((read=in.read(data))!=-1)
				{
					out.write(data, 0, read);
				}
				output.append(new String(out.toByteArray(),"UTF-8"));
				in.close();
				out.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// System.out.println("m ::"+output.toString());
		return output.toString();
	}
}
