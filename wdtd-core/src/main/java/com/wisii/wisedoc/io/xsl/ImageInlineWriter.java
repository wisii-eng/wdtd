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

import com.wisii.wisedoc.banding.DefaultBindNode;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.ExternalGraphic;
import com.wisii.wisedoc.document.ImageInline;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

public class ImageInlineWriter extends InlineWriter
{

	Inline inline;

	int number;

	public ImageInlineWriter(CellElement element, int n)
	{
		super(element, n);
		inline = (Inline) element;
		number = n;
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.xsl.AbsElementWriter#write(com.wisii.wisedoc.document
	 * .CellElement)
	 */
	public String write(CellElement element)
	{
		StringBuffer output = new StringBuffer();
		output.append(beforeDeal());
		ImageInline inlne = (ImageInline) foElement;
		Object src = null;
		Enumeration<CellElement> child = inlne.children();
		if (child != null && child.hasMoreElements())
		{
			ExternalGraphic externalg = (ExternalGraphic) child.nextElement();
			src = externalg.getAttribute(Constants.PR_SRC);
		}
		if (src == null || src instanceof String)
		{
			output.append(textNoBinding());
		} else if (src instanceof DefaultBindNode)
		{
			DefaultBindNode bindingsrc = (DefaultBindNode) src;
			output.append(getCode(bindingsrc));
		}
		output.append(endDeal());
		return output.toString();
	}

	/**
	 * 
	 * 获得元素的代码部分，内容为非绑定节点。
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String textNoBinding()
	{
		StringBuffer output = new StringBuffer();
		output.append(getHeaderElement());
		output.append(getChildCode());
		output.append(ElementUtil.endElement(FoXsltConstants.INLINE));
		return output.toString();
	}

	public String getHeaderElement()
	{
		StringBuffer code = new StringBuffer();
		ImageInline inlne = (ImageInline) foElement;
		Object src = null;
		Enumeration<CellElement> child = inlne.children();
		if (child != null && child.hasMoreElements())
		{
			ExternalGraphic externalg = (ExternalGraphic) child.nextElement();
			src = externalg.getAttribute(Constants.PR_SRC);
		}
		code.append(getHeaderStart());
		code.append(getAttributes());
		if (flg && src instanceof DefaultBindNode)
		{
			code.append(getFovAttributes());
		}
		code.append(getHeaderEnd());
		return code.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.xsl.FOObject#getCode()
	 */
	public String getCode(DefaultBindNode bindingnode)
	{
		StringBuffer output = new StringBuffer();
		String yuanshiPath = IoXslUtil.getXSLXpath(bindingnode);
		// String relativePath = IoXslUtil.compareCurrentPath(yuanshiPath);
		if (IoXslUtil.isXmlEditable() == Constants.EN_UNEDITABLE)
		{
			setFlg(false);

			output.append(getCodebasic());
		} else if (IoXslUtil.isXmlEditable() == Constants.EN_EDITABLE)
		{
			setFlg(true);
			output.append(getBindVarable(yuanshiPath));
			output.append(getCodebasic());

		} else if (IoXslUtil.isXmlEditable() == Constants.EN_EDITVARIBLE)
		{
			boolean oldflg = isFlg();
			setFlg(true);
			String setVarible = getBindVarable(yuanshiPath);
			setFlg(oldflg);
			if (isOutFov())
			{
				output.append(ElementUtil.startElement(FoXsltConstants.CHOOSE));
				String test = "contains($XmlEditable,'yes') and contains('yes',$XmlEditable)";
				output.append(ElementUtil.startElementWI(FoXsltConstants.WHEN,
						test));
				setFlg(true);
				output.append(setVarible);
				output.append(getCodebasic());
				output.append(ElementUtil.endElement(FoXsltConstants.WHEN));
				output.append(ElementUtil
						.startElement(FoXsltConstants.OTHERWISE));
				setFlg(false);
				output.append(getCodebasic());
				output
						.append(ElementUtil
								.endElement(FoXsltConstants.OTHERWISE));
				output.append(ElementUtil.endElement(FoXsltConstants.CHOOSE));
			} else
			{
				setFlg(false);
				output.append(getCodebasic());
			}
		}
		return output.toString();
	}

	/**
	 * 
	 * 获得元素的代码部分，不包括对元素代码部分用到的变量的定义。
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public String getCodebasic()
	{
		StringBuffer code = new StringBuffer();
		code.append(getHeaderElement());
		code.append(getChildCode());
		code.append(getEndElement());
		return code.toString();
	}
}
