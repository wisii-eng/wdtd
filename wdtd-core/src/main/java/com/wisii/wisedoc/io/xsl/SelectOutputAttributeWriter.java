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
 * @XSLAttributeNameAndValueWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.xsl;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground;
import com.wisii.wisedoc.document.attribute.CommonMarginBlock;
import com.wisii.wisedoc.document.attribute.CondLengthProperty;
import com.wisii.wisedoc.document.attribute.EnumLength;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.KeepProperty;
import com.wisii.wisedoc.document.attribute.LengthRangeProperty;
import com.wisii.wisedoc.document.attribute.NumberProperty;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.document.attribute.TableColLength;
import com.wisii.wisedoc.io.AttributeIOFactory;
import com.wisii.wisedoc.io.AttributeKeyNameFactory;
import com.wisii.wisedoc.io.AttributeReader;
import com.wisii.wisedoc.io.AttributeWriter;
import com.wisii.wisedoc.io.xsl.attribute.BindNodeWriter;
import com.wisii.wisedoc.io.xsl.attribute.ColorWriter;
import com.wisii.wisedoc.io.xsl.attribute.CommonBorderPaddingBackgroundWriter;
import com.wisii.wisedoc.io.xsl.attribute.CommonMarginBlockWriter;
import com.wisii.wisedoc.io.xsl.attribute.CondLengthPropertyWriter;
import com.wisii.wisedoc.io.xsl.attribute.DealPathWriter;
import com.wisii.wisedoc.io.xsl.attribute.EnumLengthWriter;
import com.wisii.wisedoc.io.xsl.attribute.EnumNumberWriter;
import com.wisii.wisedoc.io.xsl.attribute.EnumPropertyWriter;
import com.wisii.wisedoc.io.xsl.attribute.FixedLengthWriter;
import com.wisii.wisedoc.io.xsl.attribute.FovEditModeWriter;
import com.wisii.wisedoc.io.xsl.attribute.FovXpathWriter;
import com.wisii.wisedoc.io.xsl.attribute.ImageSrcWriter;
import com.wisii.wisedoc.io.xsl.attribute.IntegerWriter;
import com.wisii.wisedoc.io.xsl.attribute.KeepWriter;
import com.wisii.wisedoc.io.xsl.attribute.LengthRangePropertyWriter;
import com.wisii.wisedoc.io.xsl.attribute.NumberPropertyWriter;
import com.wisii.wisedoc.io.xsl.attribute.PercentLengthWriter;
import com.wisii.wisedoc.io.xsl.attribute.SameOutputWriter;
import com.wisii.wisedoc.io.xsl.attribute.SpaceWriter;
import com.wisii.wisedoc.io.xsl.attribute.TableColLengthWriter;
import com.wisii.wisedoc.io.xsl.attribute.TextDecorationWriter;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008-9-19
 */
public class SelectOutputAttributeWriter implements AttributeIOFactory
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeWriter#write(int, java.lang.Object)
	 */
	public String write(int key, Object value)
	{
		String output = "";
		AttributeWriter attrVawriter = getAttributeWriter(key);
		if (attrVawriter == null)
		{
			attrVawriter = getAttributeWriter(value);
		}
		if (attrVawriter != null)
		{
			output = attrVawriter.write(key, value);
		}
		// System.out.println("attrVawriter:"+attrVawriter.getClass());
		// System.out.println("output:"+output);
		return output;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeIOFactory#getAttributeKeyNameFactory()
	 */
	public AttributeKeyNameFactory getAttributeKeyNameFactory()
	{
		return new XSLAttributeNameWriter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.AttributeIOFactory#getAttributeReader(int)
	 */
	public AttributeReader getAttributeReader(int key)
	{
		return null;
	}

	/**
	 * 
	 * 将属性名和该属性将调用的Writer作为键值对放入map中
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	// 属性名key和该属性调用的Writer之间对应关系
	private Map<Integer, AttributeWriter> attributeValue = new HashMap<Integer, AttributeWriter>();
	{
		attributeValue.put(Constants.PR_SRC, new ImageSrcWriter());
		attributeValue.put(Constants.PR_TEXT_DECORATION,
				new TextDecorationWriter());
		attributeValue.put(
				com.wisii.wisedoc.io.xsl.util.Constants.PR_XSL_SELECT,
				new DealPathWriter());
		attributeValue.put(Constants.PR_XPATH, new FovXpathWriter());
		attributeValue.put(Constants.PR_EDITMODE, new FovEditModeWriter());
		attributeValue.put(Constants.PR_COLUMN_COUNT, new SameOutputWriter());
	}

	public AttributeWriter getAttributeWriter(int key)
	{
		if (attributeValue.containsKey(key))
		{
			AttributeWriter write = attributeValue.get(key);
			return write;
		} else
		{
			LogUtil.debug("传入的参数\"" + key + "\"在属性处理map中获取不到相应的处理方法");
			return null;
		}
	}

	public AttributeWriter getAttributeWriter(Object value)
	{
		AttributeWriter attrVawriter = null;
		if (attrVawriter == null)
		{
			if (value instanceof Color)
			{
				attrVawriter = new ColorWriter();
			} else if (value instanceof EnumLength)
			{
				attrVawriter = new EnumLengthWriter();
			} else if (value instanceof EnumNumber)
			{
				attrVawriter = new EnumNumberWriter();
			} else if (value instanceof SpaceProperty)
			{
				attrVawriter = new SpaceWriter();
			} else if (value instanceof PercentLength)
			{
				attrVawriter = new PercentLengthWriter();
			} else if (value instanceof FixedLength)
			{
				attrVawriter = new FixedLengthWriter();
			} else if (value instanceof TableColLength)
			{
				attrVawriter = new TableColLengthWriter();
			}

			else if (value instanceof LengthRangeProperty)
			{
				attrVawriter = new LengthRangePropertyWriter();
			}

			// else if (value instanceof LengthPairProperty)
			// {
			// attrVawriter = new LengthPairPropertyWriter();
			// }
			else if (value instanceof KeepProperty)
			{
				attrVawriter = new KeepWriter();
			} else if (value instanceof EnumProperty)
			{
				attrVawriter = new EnumPropertyWriter();
			} else if (value instanceof CommonMarginBlock)
			{
				attrVawriter = new CommonMarginBlockWriter();
			} else if (value instanceof CommonBorderPaddingBackground)
			{
				attrVawriter = new CommonBorderPaddingBackgroundWriter();
			} else if (value instanceof CondLengthProperty)
			{
				attrVawriter = new CondLengthPropertyWriter();
			} else if (value instanceof Integer)
			{
				attrVawriter = new IntegerWriter();
			} else if (value instanceof NumberProperty)
			{
				attrVawriter = new NumberPropertyWriter();
			} else if (value instanceof BindNode)
			{
				attrVawriter = new BindNodeWriter();
			} else
			{
				attrVawriter = new SameOutputWriter();
			}
		}
		return attrVawriter;
	}

	@SuppressWarnings("unchecked")
	public AttributeWriter getAttributeWriter(Class clazs)
	{
		return null;
	}
}
