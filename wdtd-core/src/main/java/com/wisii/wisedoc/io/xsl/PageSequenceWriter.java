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

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.StaticContent;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.ConditionItem;
import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.util.WisedocUtil;

public class PageSequenceWriter extends AbsLayoutElementWriter
{

	PageSequence pageSequence;

	int number;

	String fileName;

	private static boolean addBlock = false;

	Map<Integer, Object> attributemap = new HashMap<Integer, Object>();

	Map<Integer, List<ConditionAndValue>> dongtaiMap = new HashMap<Integer, List<ConditionAndValue>>();

	private static List<String> regions = new ArrayList<String>();

	public PageSequenceWriter(PageSequence pagesequence, int num)
	{
		regions = new ArrayList<String>();
		pageSequence = pagesequence;
		setAttributemap();
		number = num;
		dealSimpflg();
	}

	public PageSequenceWriter(PageSequence pagesequence, String filename)
	{
		regions = new ArrayList<String>();
		pageSequence = pagesequence;
		setAttributemap();
		fileName = filename;
		dealSimpflg();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FoElementIFWriter#getAttributes()
	 */

	public String write(CellElement element)
	{
		StringBuffer output = new StringBuffer();
		String flow = getFlowTemplate();
		String layout = getLayoutTemplate();
		output.append(layout);
		output.append(flow);
		return output.toString();
	}

	public String write()
	{
		StringBuffer output = new StringBuffer();
		output.append(beforeDeal());
		output.append(getCode());
		output.append(endDeal());
		return output.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FoElementIFWriter#getAttributes()
	 */
	public String getAttributes()
	{
		StringBuffer output = new StringBuffer();

		int indexthis = pageSequence.getParent().getIndex(pageSequence);

		if (attributemap != null)
		{
			if (attributemap.containsKey(Constants.PR_SIMPLE_PAGE_MASTER))
			{
				output.append(getAttribute(Constants.PR_MASTER_REFERENCE,
						((SimplePageMaster) attributemap
								.get(Constants.PR_SIMPLE_PAGE_MASTER))
								.hashCode()));
			} else if (attributemap
					.containsKey(Constants.PR_PAGE_SEQUENCE_MASTER))
			{
				output.append(getAttribute(Constants.PR_MASTER_REFERENCE,
						((PageSequenceMaster) attributemap
								.get(Constants.PR_PAGE_SEQUENCE_MASTER))
								.hashCode()));
			}
			Object[] keys = attributemap.keySet().toArray();
			int size = keys.length;
			for (int i = 0; i < size; i++)
			{
				int key = (Integer) keys[i];
				if (key == Constants.PR_SIMPLE_PAGE_MASTER
						|| key == Constants.PR_PAGE_SEQUENCE_MASTER
						|| key == Constants.PR_CONDTION
						|| key == Constants.PR_GROUP
						|| key == Constants.PR_DYNAMIC_STYLE)
				{
				} else
				{
					if (!dongtaiMap.containsKey(key))
					{
						output.append(getAttribute(key, attributemap.get(key)));
					}
				}
			}
			Object[] keysdong = dongtaiMap.keySet().toArray();
			for (int j = 0; j < dongtaiMap.size(); j++)
			{
				int key = (Integer) keysdong[j];
				output.append(getAttribute(key, "{$" + "n" + indexthis + "a"
						+ key + "}"));
			}
		}
		output.append(getAttribute(Constants.PR_FONT_FAMILY, "'宋体'"));
		output.append(getAttribute(Constants.PR_FONT_SIZE, "12pt"));
		Map<Integer, Object> wisedocMap = ElementUtil
				.getCompleteAttributes(
						WiseDocDocumentWriter.getAttributesMap(),
						WiseDocDocument.class);
		Object[] wisekeys = wisedocMap.keySet().toArray();
		int size = wisekeys.length;
		for (int i = 0; i < size; i++)
		{
			int wisekey = (Integer) wisekeys[i];
			if (wisekey != Constants.PR_EDITMODE
					&& wisekey != Constants.PR_GROUP)
			{
				output.append(getAttribute(wisekey, wisedocMap.get(wisekey)));
			}
		}
		return output.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FoElementIFWriter#getChildCode()
	 */
	public String getChildCode()
	{
		StringBuffer output = new StringBuffer();
		if (pageSequence != null && pageSequence.children() != null)
		{
			Enumeration<CellElement> flows = pageSequence.children();
			while (flows.hasMoreElements())
			{
				CellElement cellElement = flows.nextElement();
				if (cellElement instanceof StaticContent
						&& regions.contains(cellElement
								.getAttribute(Constants.PR_FLOW_NAME)))
				{
					StaticContent sc = (StaticContent) cellElement;
					if (!WisedocUtil.isNullStaticContent(sc))
					{
						output.append(new SelectElementWriterFactory()
								.getWriter(cellElement).write(cellElement));
						WiseDocDocumentWriter.getStaticcontents().add(
								sc.getFlowName());
					}
				}
			}
			Flow flow = pageSequence.getMainFlow();
			output.append(new SelectElementWriterFactory().getWriter(flow)
					.write(flow));
		}
		return output.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FoElementIFWriter#getElementName()
	 */
	public String getElementName()
	{
		return FoXsltConstants.PAGE_SEQUENCE;
	}

	/**
	 * 
	 * 输出实际内容之前的条件和组属性的处理
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String beforeDeal()
	{
		StringBuffer output = new StringBuffer();

		LogicalExpression condition;
		if (attributemap != null
				&& attributemap.containsKey(Constants.PR_CONDTION))
		{
			condition = (LogicalExpression) attributemap
					.get(Constants.PR_CONDTION);
			output.append(ElementUtil.startElementIf(condition));
		}

		Group group = null;

		if (attributemap != null
				&& attributemap.containsKey(Constants.PR_GROUP))
		{
			group = (Group) attributemap.get(Constants.PR_GROUP);
			output.append(ElementUtil.startGroup(group));
		}
		return output.toString();
	}

	/**
	 * 
	 * 输出实际内容之前的条件和组属性的处理的尾部代码
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String endDeal()
	{
		StringBuffer output = new StringBuffer();
		if (attributemap != null
				&& attributemap.containsKey(Constants.PR_GROUP))
		{
			Group group = (Group) attributemap.get(Constants.PR_GROUP);
			output.append(ElementUtil.endGroup(group));
		}
		if (attributemap != null
				&& attributemap.containsKey(Constants.PR_CONDTION))
		{
			output.append(ElementUtil.endElement(FoXsltConstants.IF));
		}
		return output.toString();
	}

	/**
	 * 
	 * 输出组属性处理的尾部
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String endGroup()
	{
		return ElementUtil.endElement(FoXsltConstants.FOR_EACH);
	}

	/**
	 * 
	 * 输出条件属性的尾部
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String enddealIf()
	{
		return ElementUtil.endElement(FoXsltConstants.IF);
	}

	/**
	 * 
	 * 输出布局的模板
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String getLayoutTemplate()
	{
		StringBuffer output = new StringBuffer();
		output.append(ElementWriter.TAGBEGIN);
		output.append(FoXsltConstants.TEMPLATE);
		output.append(ElementUtil.outputAttributes(FoXsltConstants.NAME,
				fileName + "." + FoXsltConstants.LAYOUT));
		output.append(ElementWriter.TAGEND);
		output.append(WiseDocDocumentWriter.getStringSimpmWriter());
		output
				.append(WiseDocDocumentWriter
						.getStringPageSequenceMasterWriter());
		output.append(ElementUtil.endElement(FoXsltConstants.TEMPLATE));
		return output.toString();
	}

	public String getPageSequenceMaster(PageSequenceMaster pagesequencemaster)
	{
		StringBuffer output = new StringBuffer();

		PageSequenceMaster pageSequenceMaster = (PageSequenceMaster) pageSequence
				.getAttributes()
				.getAttribute(Constants.PR_PAGE_SEQUENCE_MASTER);
		String pageSequencemaster = new PageSequenceMasterWriter(
				pageSequenceMaster).write();
		List<SimplePageMaster> getSimpmWriter = WiseDocDocumentWriter
				.getSimpm();
		int size = getSimpmWriter.size();
		for (int i = 0; i < size; i++)
		{
			SimplePageMasterWriter spmwitem = new SimplePageMasterWriter(
					getSimpmWriter.get(i));
			output.append(spmwitem.getCode());
		}
		output.append(pageSequencemaster);

		return output.toString();
	}

	public String getPageSequenceMaster(SimplePageMaster simplepagemaster)
	{
		StringBuffer output = new StringBuffer();

		output.append(new SimplePageMasterWriter(
				(SimplePageMaster) pageSequence.getAttributes().getAttribute(
						Constants.PR_SIMPLE_PAGE_MASTER)).getCode());

		return output.toString();
	}

	/**
	 * 
	 * 输出内容的模板
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String getFlowTemplate()
	{
		StringBuffer output = new StringBuffer();
		output.append(ElementWriter.TAGBEGIN);
		output.append(FoXsltConstants.TEMPLATE);
		output.append(ElementUtil.outputAttributes(FoXsltConstants.NAME,
				fileName + "." + FoXsltConstants.FLOWNF));
		output.append(ElementWriter.TAGEND);
		// output.append(ElementUtil.ElementParam(FoXsltConstants.PATH));
		// output.append(ElementUtil.ElementParam(FoXsltConstants.POSITION));

		Document doc = SystemManager.getCurruentDocument();
		Object obj = doc.getAttribute(Constants.PR_GROUP);
		Group group = obj != null ? (Group) obj : null;
		if (group != null)
		{
			output.append(ElementUtil.startGroup(group));
		}
		output.append(beforeDeal());
		output.append(getCode());
		output.append(endDeal());
		if (group != null)
		{
			output.append(ElementUtil.endGroup(group));
		}
		output.append(ElementUtil.endElement(FoXsltConstants.TEMPLATE));
		return output.toString();
	}

	public void dealSimpflg()
	{
		if (attributemap.containsKey(Constants.PR_SIMPLE_PAGE_MASTER))
		{
			WiseDocDocumentWriter.addSimP((SimplePageMaster) attributemap
					.get(Constants.PR_SIMPLE_PAGE_MASTER));
		} else if (attributemap.containsKey(Constants.PR_PAGE_SEQUENCE_MASTER))
		{
			PageSequenceMaster pageSequenceMaster = (PageSequenceMaster) attributemap
					.get(Constants.PR_PAGE_SEQUENCE_MASTER);
			WiseDocDocumentWriter.addPsMaster(pageSequenceMaster);
		}
	}

	public static boolean isAddBlock()
	{
		return addBlock;
	}

	public static void setAddBlock(boolean addBlock)
	{
		PageSequenceWriter.addBlock = addBlock;
	}

	@SuppressWarnings("unchecked")
	public String getAttributeVariable()
	{
		StringBuffer code = new StringBuffer();
		Object[] keys = attributemap.keySet().toArray();
		int size = keys.length;
		int index = pageSequence.getParent().getIndex(pageSequence);
		if (attributemap.containsKey(Constants.PR_DYNAMIC_STYLE))
		{
			List<ConditionItem> conditions = (List<ConditionItem>) attributemap
					.get(Constants.PR_DYNAMIC_STYLE);
			for (ConditionItem current : conditions)
			{
				Map<Integer, Object> styles = current.getStyles();
				int stylessize = styles.size();
				Object[] keysstyles = styles.keySet().toArray();
				for (int i = 0; i < stylessize; i++)
				{
					int key = (Integer) keysstyles[i];
					if (dongtaiMap.containsKey(key))
					{
						List<ConditionAndValue> currentlist = (List<ConditionAndValue>) dongtaiMap
								.get(key);
						ConditionAndValue currentcandv = new ConditionAndValue(
								current.getCondition(), styles.get(key));
						currentlist.add(currentcandv);
						dongtaiMap.remove(key);
						dongtaiMap.put(key, currentlist);
					} else
					{
						List<ConditionAndValue> currentlist = new ArrayList<ConditionAndValue>();
						ConditionAndValue currentcandv = new ConditionAndValue(
								current.getCondition(), styles.get(key));
						currentlist.add(currentcandv);
						dongtaiMap.put(key, currentlist);
					}
				}
			}
			for (int i = 0; i < size; i++)
			{
				int key = (Integer) keys[i];
				if (dongtaiMap.containsKey(key))
				{
					List<ConditionAndValue> currentlist = (List<ConditionAndValue>) dongtaiMap
							.get(key);
					ConditionAndValue currentcandv = new ConditionAndValue(
							null, attributemap.get(key));
					currentlist.add(currentcandv);
					dongtaiMap.remove(key);
					dongtaiMap.put(key, currentlist);
				}
			}
			Object[] keysdongtaiMap = dongtaiMap.keySet().toArray();
			int dongtaiMapsize = dongtaiMap.size();
			for (int i = 0; i < dongtaiMapsize; i++)
			{
				int key = (Integer) keysdongtaiMap[i];
				List<ConditionAndValue> current = dongtaiMap.get(key);
				if (current != null && current.size() > 0)
				{
					for (int j = 0; j < current.size(); j++)
					{
						if (j == 0)
						{
							code.append(ElementWriter.TAGBEGIN
									+ FoXsltConstants.VARIABLE + " ");
							code.append(ElementUtil.outputAttributes(
									FoXsltConstants.NAME, "n" + index + "a"
											+ key));
							code.append(ElementWriter.TAGEND);
							code.append(ElementUtil
									.startElement(FoXsltConstants.CHOOSE));
						}
						ConditionAndValue currentitem = current.get(j);
						LogicalExpression condition = currentitem
								.getCondition();
						Object value = currentitem.getValue();

						if (condition == null)
						{
							code.append(ElementUtil
									.startElement(FoXsltConstants.OTHERWISE));
							String keyandvalue = getAttribute(key, value);

							if (keyandvalue.equals(""))
							{
								code.append(ElementUtil.ElementValueOf("'"
										+ keyandvalue + "'"));
							} else
							{
								String valueStr = "";

								String[] arr = keyandvalue.split("=");
								valueStr = arr[1].replaceAll("\"", "");
								code.append(ElementUtil.ElementValueOf("'"
										+ valueStr + "'"));
							}
							code.append(ElementUtil
									.endElement(FoXsltConstants.OTHERWISE));
						} else
						{
							code.append(ElementWriter.TAGBEGIN
									+ FoXsltConstants.WHEN + " ");
							code.append(ElementUtil.outputAttributes(
									FoXsltConstants.TEST, ElementUtil
											.returnStringIf(condition, true)));
							code.append(ElementWriter.TAGEND);
							String keyandvalue = getAttribute(key, value);
							if (keyandvalue.equals(""))
							{
								code.append(ElementUtil.ElementValueOf("'"
										+ keyandvalue + "'"));
							} else
							{
								String valueStr = "";
								String[] arr = keyandvalue.split("=");
								valueStr = arr[1].replaceAll("\"", "");
								code.append(ElementUtil.ElementValueOf("'"
										+ valueStr + "'"));
							}
							code.append(ElementUtil
									.endElement(FoXsltConstants.WHEN));
						}
						if (j == current.size() - 1)
						{
							code.append(ElementUtil
									.endElement(FoXsltConstants.CHOOSE));
							code.append(ElementUtil
									.endElement(FoXsltConstants.VARIABLE));
						}
					}
				}
			}
		}
		return code.toString();

	}

	public void setAttributemap()
	{
		Attributes attributes = pageSequence.getAttributes();
		if (attributes != null)
		{
			attributemap = ElementUtil.getCompleteAttributes(attributes
					.getAttributes(), pageSequence.getClass());
			regions = WisedocUtil.getFlowName(pageSequence);
		}
	}

	public static List<String> getRegions()
	{
		return regions;
	}

}
