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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.PageNumberCitation;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

public class WiseDocDocumentWriter
{

	WiseDocDocument wiseDocDocument;

	String fileName;

	private static Map<Integer, Object> attributesMap = new HashMap<Integer, Object>();

	private static List<SimplePageMaster> simpm = new ArrayList<SimplePageMaster>();

	private static List<PageSequenceMaster> PageSequenceMaster = new ArrayList<PageSequenceMaster>();

	private static List<String> staticcontents = new ArrayList<String>();

	public WiseDocDocumentWriter(WiseDocDocument wisedocdocument)
	{
		wiseDocDocument = wisedocdocument;
		staticcontents = new ArrayList<String>();
		setAttributesMap(wisedocdocument);
	}

	public WiseDocDocumentWriter(WiseDocDocument wisedocdocument,
			String filename)
	{
		wiseDocDocument = wisedocdocument;
		staticcontents = new ArrayList<String>();
		setAttributesMap(wisedocdocument);
		fileName = filename;
	}

	public String writeMore()
	{
		StringBuffer output = new StringBuffer();
		output.append(getMainTemplateStart());
		output.append(getFoRootStart());
		String pageSequence = getPageSequence();
		String layoutMasterSet = getMoreLayoutMasterSet();

		output.append(layoutMasterSet);
		output.append(pageSequence);

		// output.append(addUIEdit());
		output.append(ElementUtil.endElement(FoXsltConstants.ROOT));
		output.append(ElementUtil.endElement(FoXsltConstants.TEMPLATE));
		getSimpm().clear();
		getPageSequenceMaster().clear();
		return output.toString();
	}

	public String writeOne()
	{
		StringBuffer output = new StringBuffer();
		output.append(getMainTemplateStart());
		output.append(getFoRootStart());
		String pageSequence = getCallPageSequence();
		String layoutMasterSet = getLayoutMasterSet();
		output.append(layoutMasterSet);
		output.append(pageSequence);

		// output.append(addUIEdit());
		output.append(ElementUtil.endElement(FoXsltConstants.ROOT));
		output.append(ElementUtil.endElement(FoXsltConstants.TEMPLATE));
		getSimpm().clear();
		getPageSequenceMaster().clear();
		return output.toString();
	}

	/**
	 * 
	 * 获取xsl根模板的头部代码
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String getMainTemplateStart()
	{
		StringBuffer output = new StringBuffer();
		output.append(ElementWriter.TAGBEGIN);
		output.append(FoXsltConstants.TEMPLATE);
		output.append(ElementUtil.outputAttributes(FoXsltConstants.MATCH, "/"));
		output.append(ElementWriter.TAGEND);
		return output.toString();
	}

	/**
	 * 
	 * 获取fo根元素的头部代码
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String getFoRootStart()
	{
		StringBuffer output = new StringBuffer();
		output.append(ElementWriter.TAGBEGIN);
		output.append(FoXsltConstants.ROOT);
		output.append(ElementWriter.TAGEND);
		return output.toString();
	}

	/**
	 * 
	 * 获取布局信息的调用语句
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	@SuppressWarnings("unchecked")
	public String getLayoutMasterSet()
	{
		StringBuffer output = new StringBuffer();
		output.append(ElementUtil
				.startElement(FoXsltConstants.LAYOUT_MASTER_SET));
		// HashMap<String, Object> param = new HashMap<String, Object>();
		Enumeration<Object> list = wiseDocDocument.children();
		if (list.hasMoreElements())
		{
			output.append(ElementUtil.outputCalltemplate(fileName + "."
					+ FoXsltConstants.LAYOUT, null));
		}
		output
				.append(ElementUtil
						.endElement(FoXsltConstants.LAYOUT_MASTER_SET));
		return output.toString();
	}

	public String getMoreLayoutMasterSet()
	{
		StringBuffer output = new StringBuffer();
		output.append(ElementUtil
				.startElement(FoXsltConstants.LAYOUT_MASTER_SET));
		String pasm = getStringPageSequenceMasterWriter();
		String simp = getStringSimpmWriter();
		output.append(simp);
		output.append(pasm);
		output
				.append(ElementUtil
						.endElement(FoXsltConstants.LAYOUT_MASTER_SET));
		return output.toString();
	}

	public static String getStringSimpmWriter()
	{
		StringBuffer output = new StringBuffer();
		List<SimplePageMaster> simpmwriter = getSimpm();
		int sizeSimp = simpmwriter.size();
		if (sizeSimp > 0)
		{
			for (int i = 0; i < sizeSimp; i++)
			{
				SimplePageMasterWriter simpWriter = new SimplePageMasterWriter(
						simpmwriter.get(i));
				output.append(simpWriter.getCode());
			}
		}
		return output.toString();
	}

	public static String getStringPageSequenceMasterWriter()
	{
		StringBuffer output = new StringBuffer();
		List<PageSequenceMaster> pagesequencemasterwriter = getPageSequenceMaster();
		int sizePasMaster = pagesequencemasterwriter.size();
		if (sizePasMaster > 0)
		{
			for (int i = 0; i < sizePasMaster; i++)
			{
				PageSequenceMasterWriter pageSequenceMasterWriter = new PageSequenceMasterWriter(
						pagesequencemasterwriter.get(i));
				output.append(pageSequenceMasterWriter.getCode());
			}
		}
		return output.toString();
	}

	/**
	 * 
	 * 获取内容信息的调用语句
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String getCallPageSequence()
	{
		StringBuffer output = new StringBuffer();
		output.append(ElementUtil.outputCalltemplate(fileName + "."
				+ FoXsltConstants.FLOWNF, null));
		return output.toString();
	}

	@SuppressWarnings("unchecked")
	public String getPageSequence()
	{
		StringBuffer output = new StringBuffer();
		int i = 0;
		Enumeration<Object> list = wiseDocDocument.children();
		Group group = getAttributesMap().containsKey(Constants.PR_GROUP) ? (Group) getAttributesMap()
				.get(Constants.PR_GROUP)
				: null;
		if (group != null)
		{
			output.append(ElementUtil.startGroup(group));
		}
		while (list.hasMoreElements())
		{
			i++;
			PageSequence pagesequence = (PageSequence) list.nextElement();
			output.append(new PageSequenceWriter(pagesequence, i).write());

		}
		if (group != null)
		{
			output.append(ElementUtil.endGroup(group));
		}
		return output.toString();
	}

	@SuppressWarnings("unchecked")
	public void setpgStatic(CellElement cellment)
	{
		if (cellment instanceof PageNumberCitation)
		{
			new PageNumberCitationWriter(cellment).write(cellment);
		} else
		{
			Enumeration<Object> list = cellment.children();
			if (list != null)
			{
				while (list.hasMoreElements())
				{
					CellElement current = (CellElement) list.nextElement();
					setpgStatic(current);
				}
			}
		}
	}

	/**
	 * 
	 * 输出各page-sequence对应的模板
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	@SuppressWarnings("unchecked")
	public String writeTemplates()
	{
		StringBuffer output = new StringBuffer();
		Enumeration<Object> list = wiseDocDocument.children();
		// int number = wiseDocDocument.getChildCount();
		while (list.hasMoreElements())
		{
			PageSequence pagesequence = (PageSequence) list.nextElement();
			output.append(new PageSequenceWriter(pagesequence, fileName)
					.write(pagesequence));
		}

		return output.toString();
	}

	public static List<SimplePageMaster> getSimpm()
	{
		return simpm;
	}

	public static List<PageSequenceMaster> getPageSequenceMaster()
	{
		return PageSequenceMaster;
	}

	public static void addSimP(SimplePageMaster simp)
	{
		List<SimplePageMaster> simpm = getSimpm();
		int size = simpm.size();
		if (size > 0)
		{
			if (!simpm.contains(simp))
			{
				simpm.add(simp);
			}
		} else if (size == 0)
		{
			simpm.add(simp);
		}
	}

	public static void addPsMaster(PageSequenceMaster psqm)
	{
		List<PageSequenceMaster> pagesequencemaster = getPageSequenceMaster();
		int size = pagesequencemaster.size();
		if (size > 0)
		{
			if (!pagesequencemaster.contains(psqm))
			{
				pagesequencemaster.add(psqm);
			}
		} else if (size == 0)
		{
			pagesequencemaster.add(psqm);
		}
	}

	public static Map<Integer, Object> getAttributesMap()
	{
		return WiseDocDocumentWriter.attributesMap;
	}

	public static void setAttributesMap(Document document)
	{
		Attributes Attributes = document.getAttributes();
		Map<Integer, Object> map = Attributes == null ? null : Attributes
				.getAttributes();
		if (map != null)
		{
			WiseDocDocumentWriter.attributesMap = ElementUtil
					.getCompleteAttributes(map, WiseDocDocument.class);
		}

		boolean standard = IoXslUtil.isStandard();
		if (standard)
		{
			IoXslUtil.setXmlEditable(Constants.EN_UNEDITABLE);
		} else
		{
			EnumProperty xmlEdit = (EnumProperty) attributesMap
					.get(Constants.PR_EDITMODE);
			if (xmlEdit != null)
			{
				IoXslUtil.setXmlEditable(xmlEdit.getEnum());
			} else
			{
				IoXslUtil.setXmlEditable(Constants.EN_UNEDITABLE);
			}
		}
	}

	public static List<String> getStaticcontents()
	{
		return staticcontents;
	}
	// public String addUIEdit()
	// {
	// String output = "";
	// if (IoXslUtil.isXmlEditable() == Constants.EN_EDITABLE)
	// {
	// output = output + addUIEditCode();
	// } else if (IoXslUtil.isXmlEditable() == Constants.EN_EDITVARIBLE)
	// {
	// output = output + ElementUtil.startElement(FoXsltConstants.CHOOSE);
	// String test =
	// "contains($XmlEditable,'yes') and contains('yes',$XmlEditable)";
	// output = output
	// + ElementUtil.startElementWI(FoXsltConstants.WHEN, test);
	// output = output + addUIEditCode();
	// output = output + ElementUtil.endElement(FoXsltConstants.WHEN);
	// output = output
	// + ElementUtil.startElement(FoXsltConstants.OTHERWISE);
	// output = output + ElementUtil.endElement(FoXsltConstants.OTHERWISE);
	// output = output + ElementUtil.endElement(FoXsltConstants.CHOOSE);
	// }
	// return output;
	// }
	//
	// public String addUIEditCode()
	// {
	// String output = "";
	// List<EditUI> listuis = IoXslUtil.getEdituis();
	// output = output + "<wdems:wdems>";
	// for (EditUI current : listuis)
	// {
	// output = output + current.toString();
	// }
	// output = output + "</wdems:wdems>";
	// return output;
	// }
}
