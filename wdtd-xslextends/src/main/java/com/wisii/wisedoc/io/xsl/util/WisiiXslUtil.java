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
 * 
 */
package com.wisii.wisedoc.io.xsl.util;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

/**
 * @author wisii
 *
 */
public class WisiiXslUtil {
	/**
	 * 根据宽度，获得可以在此宽度下可显示的字号
	 * 
	 * @param content
	 *            :内容
	 * @param fontname
	 *            ：字体名称
	 * @param fontstyle
	 *            :粗体，斜体
	 * @param width
	 *            :可排版宽度,mpt为单位
	 * @return
	 */
	public String getSize(String content, String fontname, String fontstyle,
			String width, String defaultsize) {
		Integer sizempt = Integer.parseInt(defaultsize);
		if (content == null || content.isEmpty()) {
			return (sizempt/1000f) + "pt";
		}
		if(fontname == null
				|| fontname.isEmpty())
		{
			fontname="宋体";
		}
		Integer style = Font.PLAIN;
		try {
			style = Integer.parseInt(fontstyle);
		} catch (Exception e) {
		}
		try {
			Integer widthint = Integer.parseInt(width);
			int sizept = Math.round(sizempt / 1000f);
			Font font = new Font(fontname, style, sizept);
			if (font.canDisplayUpTo(content)!=-1)
			{
				font = new java.awt.Font("Dialog", style, sizept);
			}
			AffineTransform a = new AffineTransform();
			FontRenderContext context = new FontRenderContext(a, true, true);
			double newwidth = font.createGlyphVector(context,content).getLogicalBounds().getWidth();
			if (widthint < newwidth * 1000) {
				int newsizempt = (int) Math.round((sizempt * (widthint - 2000))
						/ (newwidth * 1000));
				
				return (newsizempt/1000f) + "pt";
			} else {
				return (sizempt/1000f) + "pt";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return (sizempt/1000f) + "pt";
		}
	}
}
