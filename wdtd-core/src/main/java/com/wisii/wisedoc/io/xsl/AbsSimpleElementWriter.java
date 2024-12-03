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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.xsl.attribute.LengthPropertyWriter;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

public class AbsSimpleElementWriter implements FoElementIFWriter
{

	SelectOutputAttributeWriter writerFactory = new SelectOutputAttributeWriter();

	CellElement foElement;

	Map<Integer, Object> attributemap = new HashMap<Integer, Object>();

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @excetion 说明在某情况下,将发生什么异常}
	 */
	public AbsSimpleElementWriter(CellElement element)
	{
		foElement = element;
		setAttributemap();
	}

	public AbsSimpleElementWriter()
	{
	}

	public String write(CellElement element)
	{
		return getCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FOObject#getAttibute(java.lang.Object,
	 * java.lang.Object)
	 */
	public String getAttribute(int key, Object value)
	{
		return writerFactory.write(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FOObject#getAttributes()
	 */
	public String getAttributes()
	{
		StringBuffer code = new StringBuffer();

		Object[] keys = attributemap.keySet().toArray();
		int size = keys.length;
		if (size > 0)
		{
			for (int i = 0; i < size; i++)
			{
				int key = (Integer) keys[i];
				if (key == Constants.PR_FONT_FAMILY)
				{
					code.append(getAttribute(key, "'" + attributemap.get(key)
							+ "'"));
				} else if (key == Constants.PR_DATETIMEFORMAT
						|| key == Constants.PR_NUMBERFORMAT
						|| key == Constants.PR_POSITION_NUMBER_TYPE
						|| key == Constants.PR_CONDTION
						|| key == Constants.PR_GROUP
						|| key == Constants.PR_EDITMODE
						|| key == Constants.PR_SRC
						|| key == Constants.PR_DYNAMIC_STYLE
						|| key == Constants.PR_BLOCK_LEVEL)
				{
				} else if (key == Constants.PR_START_INDENT)
				{
					LengthPropertyWriter writer = new LengthPropertyWriter();
					// if
					// (attributemap.containsKey(Constants.PR_HANGING_INDENT))
					// {
					// code = code
					// + ElementUtil
					// .outputAttributes(
					// FoXsltConstants.START_INDENT,
					// writer.write(attributemap
					// .get(key))
					// + " + "
					// + writer
					// .write(attributemap
					// .get(Constants.PR_HANGING_INDENT)));
					// } else
					// {
					code.append(ElementUtil.outputAttributes(
							FoXsltConstants.START_INDENT, writer
									.write(attributemap.get(key))));
					// }
				} else if (key == Constants.PR_TEXT_INDENT)
				{
					LengthPropertyWriter writer = new LengthPropertyWriter();
					if (attributemap.containsKey(Constants.PR_HANGING_INDENT))
					{
						code
								.append(ElementUtil
										.outputAttributes(
												FoXsltConstants.TEXT_INDENT,
												writer.write(attributemap
														.get(key))
														+ " - "
														+ writer
																.write(attributemap
																		.get(Constants.PR_HANGING_INDENT))));
					} else
					{
						code.append(ElementUtil.outputAttributes(
								FoXsltConstants.TEXT_INDENT, writer
										.write(attributemap.get(key))));
					}
				} else if (key == Constants.PR_HANGING_INDENT)
				{
					if (!attributemap.containsKey(Constants.PR_TEXT_INDENT))
					{
						LengthPropertyWriter writer = new LengthPropertyWriter();
						code.append(ElementUtil.outputAttributes(
								FoXsltConstants.TEXT_INDENT, " - "
										+ writer.write(attributemap.get(key))));
					}
				} else if (key == Constants.PR_ID || key == Constants.PR_REF_ID
						|| key == Constants.PR_INTERNAL_DESTINATION)
				{
					code.append(getAttribute(key, "{$i"
							+ foElement.getParent().getIndex(foElement) + "}"));
				} else
				{
					if (!((IoXslUtil.isStandard()) && (key == Constants.PR_GRAPHIC_LAYER || key == Constants.PR_BACKGROUNDGRAPHIC_LAYER)))
					{
						code.append(getAttribute(key, attributemap.get(key)));
					}
				}
			}
		}
		return code.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FOObject#getChildCode(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public String getChildCode()
	{
		String output = "";
		if (foElement != null && foElement.children() != null)
		{
			Enumeration<CellElement> listobj = foElement.children();
			while (listobj.hasMoreElements())
			{
				CellElement obj = listobj.nextElement();
				output = output
						+ new SelectElementWriterFactory().getWriter(obj)
								.write(obj);
			}
		}
		return output;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FOObject#getCode()
	 */
	public String getCode()
	{
		StringBuffer code = new StringBuffer();
		code.append(getHeaderElement());
		code.append(getChildCode());
		code.append(getEndElement());
		return code.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FOObject#getEndElement()
	 */
	public String getEndElement()
	{
		StringBuffer code = new StringBuffer();
		code.append(ElementWriter.TAGENDSTART);
		code.append(getElementName());
		code.append(ElementWriter.TAGEND);
		return code.toString();
	}

	@Override
	public String getHeaderElement()
	{
		StringBuffer code = new StringBuffer();
		if (attributemap.containsKey(Constants.PR_ID))
		{
			int index = foElement.getParent().getIndex(foElement);
			code.append(getId(index, attributemap.get(Constants.PR_ID)
					.toString()));
		} else if (attributemap.containsKey(Constants.PR_REF_ID))
		{
			int index = foElement.getParent().getIndex(foElement);
			code.append(getId(index, attributemap.get(Constants.PR_REF_ID)
					.toString()));
		}
		if (attributemap.containsKey(Constants.PR_INTERNAL_DESTINATION))
		{
			int index = foElement.getParent().getIndex(foElement);
			code.append(getId(index, attributemap.get(
					Constants.PR_INTERNAL_DESTINATION).toString()));
		}
		code.append(getHeaderStart());
		code.append(getAttributes());
		code.append(getHeaderEnd());
		return code.toString();
	}

	public String getId(int index, String id)
	{
		StringBuffer code = new StringBuffer();
		code.append(ElementWriter.TAGBEGIN);
		code.append(FoXsltConstants.VARIABLE);
		code.append(ElementUtil.outputAttributes(FoXsltConstants.NAME, "i"
				+ index));
		code.append(ElementWriter.TAGEND);
		code.append(IoXslUtil.getCurrentId(id));
		code.append(ElementWriter.TAGENDSTART);
		code.append(FoXsltConstants.VARIABLE);
		code.append(ElementWriter.TAGEND);
		return code.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FOObject#getHeaderEnd()
	 */
	public String getHeaderEnd()
	{
		return ElementWriter.TAGEND;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FOObject#getHeaderStart()
	 */
	public String getHeaderStart()
	{
		String code = ElementWriter.TAGBEGIN;
		code = code + getElementName();
		return code;
	}

	public Map<Integer, Object> getAttributemap()
	{
		return attributemap;
	}

	public void setAttributemap()
	{
		Attributes attributes = foElement.getAttributes();
		if (attributes != null)
		{
			attributemap = ElementUtil.getCompleteAttributes(attributes
					.getAttributes(), foElement.getClass());
		}
		attributemap = ElementUtil.clearMap(attributemap);
	}

	@Override
	public String getElementName()
	{
		return "";
	}
}
