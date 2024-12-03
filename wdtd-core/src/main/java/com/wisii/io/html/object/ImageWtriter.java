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
 * @ImageWtriter.java
 *                    北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.io.html.object;

import com.wisii.io.html.HtmlContext;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.ExternalGraphic;
import com.wisii.wisedoc.document.ImageInline;
import com.wisii.wisedoc.io.XMLUtil;

/**
 * 类功能描述：输出图片对象的模板
 * 
 * 作者：zhangqiang
 * 创建日期：2012-5-31
 */
public class ImageWtriter implements ObjectWriter
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * 
	 * 
	 * 
	 * com.wisii.io.html.object.ObjectWriter#getString(com.wisii.wisedoc.document
	 * .CellElement, com.wisii.io.html.HtmlContext)
	 */
	@Override
	public String getString(CellElement element, HtmlContext context)
	{
		if (element instanceof ImageInline)
		{
			StringBuffer sb = new StringBuffer();
			ExternalGraphic image = (ExternalGraphic) element.getChildAt(0);
			if (image != null)
			{
				Object src = image.getAttribute(Constants.PR_SRC);
				if (src != null)
				{
					if (src instanceof BindNode)
					{
						sb.append("<xsl:variable name=\"content0\" select=\""
								+ context.getRelatePath((BindNode) src)
								+ "\"/>");
						src = "$content0";
					}
					else
					{
						src=XMLUtil.getXmlText(src.toString());
					}
					sb.append("<img src=\"wisiibase\\graphics\\"
							+ src.toString() + "\"");
					String styleclass = context.getStyleClass(image
							.getAttributes());
					if (styleclass != null)
					{
						sb.append(" class=\"" + styleclass + "\"");
					}
					sb.append("/>");
				}
			}
		}
		return "";
	}
}
