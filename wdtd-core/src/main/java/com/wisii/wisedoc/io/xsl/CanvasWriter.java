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

import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.svg.Canvas;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

public class CanvasWriter
{

	Canvas canvas;

	public CanvasWriter(Canvas c)
	{
		canvas = c;
	}

	public String write()
	{
		StringBuffer output = new StringBuffer();

		output.append(ElementWriter.TAGBEGIN);
		output.append(FoXsltConstants.INSTREAM_FOREIGN_OBJECT);
		Object layer = canvas.getAttribute(Constants.PR_GRAPHIC_LAYER);
		if (layer != null)
		{
			output.append(ElementUtil.outputAttributes(
					FoXsltConstants.GRAPHIC_LAYER, layer + ""));
		}
		output.append(ElementWriter.TAGEND);
		Document doc = canvas.getSVGDocument();
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transFormer;
		try
		{
			transFormer = transFactory.newTransformer();
			// 设置输出结果
			DOMSource domSource = new DOMSource(doc.getDocumentElement());
			StringWriter writer = new StringWriter();

			// 设置输入源
			StreamResult xmlResult = new StreamResult(writer);

			// 输出xml文件
			transFormer.transform(domSource, xmlResult);
			//去除掉XML声明（<?xml version="1.0" encoding="UTF-8"?>）字符串
			output.append(writer.toString().substring(38));
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		output.append(ElementWriter.TAGENDSTART);
		output.append(FoXsltConstants.INSTREAM_FOREIGN_OBJECT);
		output.append(ElementWriter.TAGEND);
		return output.toString();
	}
}
