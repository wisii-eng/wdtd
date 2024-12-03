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

package com.wisii.wisedoc.document.svg;

import java.awt.Color;
import java.util.Map;

import org.w3c.dom.Element;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;

public class WordArtText extends Canvas
{

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public WordArtText()
	{
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public WordArtText(final Map<Integer, Object> attributes)
	{
		super(attributes);
	}

	protected void buildSVGElement(org.w3c.dom.Document doc)
	{
		org.w3c.dom.Element svg = doc.getDocumentElement();
		svg.setAttribute("version", "1.1");
		svg.setAttribute("overflow", "visible");
		svg
				.setAttribute(
						"width",
						getFixedLengthvalue((FixedLength) getAttribute(Constants.PR_WIDTH)));
		svg
				.setAttribute(
						"height",
						getFixedLengthvalue((FixedLength) getAttribute(Constants.PR_HEIGHT)));
		int width = getIntrinsicWidth();
		int height = getIntrinsicHeight();
		svg.setAttribute("viewBox", "0 0 " + width + " " + height);
		Element defs = doc.createElementNS(getNamespaceURI(), "defs");
		Element path = doc.createElementNS(getNamespaceURI(), "path");
		path.setAttribute("id", "mypath");
		int angle = 0;
		Integer anglep = (Integer) getAttribute(Constants.PR_WORDARTTEXT_ROTATION);
		if (anglep != null)
		{
			angle = anglep;
		}
		path.setAttribute("d", getPathValue(width, height, angle));
		defs.appendChild(path);
		svg.appendChild(defs);
		Element text = doc.createElementNS(getNamespaceURI(), "text");
		text.setAttribute("font-family",
				(String) getAttribute(Constants.PR_FONT_FAMILY));
		FixedLength fontsize = (FixedLength) getAttribute(Constants.PR_FONT_SIZE);
		if (fontsize != null)
		{
			text.setAttribute("font-size", "" + fontsize.getValue());
		}
		Color fontcolor = (Color) getAttribute(Constants.PR_COLOR);
		if (fontcolor != null)
		{
			// text.setAttribute("fill","green");
			text.setAttribute("stroke", "rgb(" + fontcolor.getRed() + ","
					+ fontcolor.getGreen() + "," + fontcolor.getBlue() + ")");
			text.setAttribute("fill", "rgb(" + fontcolor.getRed() + ","
					+ fontcolor.getGreen() + "," + fontcolor.getBlue() + ")");
		}
		Integer fontweight = (Integer) getParent().getAttribute(
				Constants.PR_FONT_WEIGHT);
		if (fontweight != null && fontweight == Constants.EN_700)
		{
			// text.setAttribute("fill","green");
			text.setAttribute("font-weight", "bold");
		}
		Integer fontstyle = (Integer) getParent().getAttribute(
				Constants.PR_FONT_STYLE);
		if (fontstyle != null && fontstyle == Constants.EN_ITALIC)
		{
			text.setAttribute("font-style", "italic");
		}
		EnumProperty alignment = (EnumProperty) getAttribute(Constants.PR_TEXT_ALIGN);
		if (alignment != null)
		{
			int alignmentint = alignment.getEnum();
			if (alignmentint == Constants.EN_MIDDLE)
			{
				text.setAttribute("text-anchor", "middle");
			} else if (alignmentint == Constants.EN_END)
			{
				text.setAttribute("text-anchor", "end");
			}
		}
		Element textpath = doc.createElementNS(getNamespaceURI(), "textPath");
		textpath.setAttributeNS("http://www.w3.org/1999/xlink", "href",
				"#mypath");
		textpath.setAttribute("xlink:href", "#mypath");
		FixedLength letterspace = (FixedLength) getAttribute(Constants.PR_WORDARTTEXT_LETTERSPACE);
		if (letterspace != null)
		{
			textpath
					.setAttribute("letter-spacing", "" + letterspace.getValue());
		}
		Integer startpos = (Integer) getAttribute(Constants.PR_WORDARTTEXT_STARTPOSITION);
		if (startpos != null)
		{
			textpath.setAttribute("startOffset", startpos + "%");
		}
		textpath.setTextContent(getAttribute(Constants.PR_WORDARTTEXT_CONTENT)
				.toString());
		text.appendChild(textpath);
		svg.appendChild(text);
		EnumProperty pathvisble = (EnumProperty) getAttribute(Constants.PR_WORDARTTEXT_PATHVISABLE);
		if (pathvisble != null)
		{
			int pvint = pathvisble.getEnum();
			if (pvint == Constants.EN_TRUE)
			{
				Element use = doc.createElementNS(getNamespaceURI(), "use");
				use.setAttributeNS("http://www.w3.org/1999/xlink", "href",
						"#mypath");
				use.setAttribute("xlink:href", "#mypath");
				use.setAttribute("stroke", "rgb(" + fontcolor.getRed() + ","
						+ fontcolor.getGreen() + "," + fontcolor.getBlue()
						+ ")");
				use.setAttribute("fill", "none");
				svg.appendChild(use);
			}
		}
	}

	private String getFixedLengthvalue(FixedLength length)
	{
		String text = length.getLengthValueString();
		text = text + length.getUnits();
		return text;
	}

	/*
	 * 
	 * 根据艺术字类型获得对应的path值
	 * 
	 * @param {引入参数}: {引入参数说明}
	 * 
	 * @return String: {返回参数说明}
	 * 
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	private String getPathValue(int width, int height, int orientation)
	{
		EnumProperty type = (EnumProperty) getAttribute(Constants.PR_WORDARTTEXT_TYPE);
		int typeint = Constants.EN_WORDARTTEXT_TYPE_LINE;
		if (type != null)
		{
			typeint = type.getEnum();
		}
		String value = null;
		switch (typeint)
		{
			case Constants.EN_WORDARTTEXT_TYPE_BEZIERCURVES2:
			{
				float[] scales =
				{ 4f / 88, 0.5f, 24f / 88, 2.0f / 30, 0.5f, 0.5f, 84f / 88,
						0.5f };
				// value = "M40,150 Q240,20 440,150 T840,150";
				value = "M" + Math.round(width * scales[0]) + ","
						+ Math.round(height * scales[1]) + " Q"
						+ Math.round(width * scales[2]) + ","
						+ Math.round(height * scales[3]) + " "
						+ Math.round(width * scales[4]) + ","
						+ Math.round(height * scales[5]) + " T"
						+ Math.round(width * scales[6]) + ","
						+ Math.round(height * scales[7]);

				break;
			}
			case Constants.EN_WORDARTTEXT_TYPE_BEZIERCURVES3:
			{
				float[] scales =
				{ 4f / 88,
						2.0f / 3,// M
						14f / 88, 1.0f / 3, 24f / 88, 0f, 34f / 88,
						1.0f / 3,// C
						0.5f, 2.0f / 3, 54f / 88, 1f, 64f / 88,
						2.0f / 3,// c
						74f / 88, 1.0f / 3, 84f / 88, 1.0f / 3, 84f / 88,
						1.0f / 3 // c
				};
				// value =
				// "M 40 200 C 140 100 240   0 340 100 C 440 200 540 300 640 200 C 740 100 840 100 840 100"
				value = "M " + Math.round(width * scales[0]) + " "
						+ Math.round(height * scales[1]) + " C"
						+ Math.round(width * scales[2]) + " "
						+ Math.round(height * scales[3]) + " "
						+ Math.round(width * scales[4]) + " "
						+ Math.round(height * scales[5]) + " "
						+ Math.round(width * scales[6]) + " "
						+ Math.round(height * scales[7]) + " C"
						+ Math.round(width * scales[8]) + " "
						+ Math.round(height * scales[9]) + " "
						+ Math.round(width * scales[10]) + " "
						+ Math.round(height * scales[11]) + " "
						+ Math.round(width * scales[12]) + " "
						+ Math.round(height * scales[13]) + " C"
						+ Math.round(width * scales[14]) + " "
						+ Math.round(height * scales[15]) + " "
						+ Math.round(width * scales[16]) + " "
						+ Math.round(height * scales[17]) + " "
						+ Math.round(width * scales[18]) + " "
						+ Math.round(height * scales[19]);
				break;
			}
			case Constants.EN_WORDARTTEXT_TYPE_ELLIPSE:
			{
				float[] scales =
				{ 4f / 32, 0.5f,// M
						12f / 32, 0.3f, 24f / 32, 0f,// a
						12f / 32, 0.3f, -24f / 32, 0f,// a
				};
				// value = "M40,150 a120,90 0 0,1 240,0 a120,90 0 0,1 -240,0";
				value = "M" + Math.round(width * scales[0]) + " "
						+ Math.round(height * scales[1]) + "a"
						+ Math.round(width * scales[2]) + " "
						+ Math.round(height * scales[3]) + " 0 0,1 "
						+ Math.round(width * scales[4]) + " "
						+ Math.round(height * scales[5]) + "a"
						+ Math.round(width * scales[6]) + " "
						+ Math.round(height * scales[7]) + " 0 0,1 "
						+ Math.round(width * scales[8]) + " "
						+ Math.round(height * scales[9]);
				break;
			}
			case Constants.EN_WORDARTTEXT_TYPE_ELLIPSEDOWN:
			{
				float[] scales =
				{ 4f / 58, 2.0f / 30,// M
						25f / 58, 1.9f / 3, 50f / 58, 0f,// a
				};
				// value = "M40,20 a250,190 0 0,0 500,0";
				value = "M" + Math.round(width * scales[0]) + " "
						+ Math.round(height * scales[1]) + "a"
						+ Math.round(width * scales[2]) + " "
						+ Math.round(height * scales[3]) + " 0 0,0 "
						+ Math.round(width * scales[4]) + " "
						+ Math.round(height * scales[5]);
				break;
			}
			case Constants.EN_WORDARTTEXT_TYPE_ELLIPSEINNER:
			{
				float[] scales =
				{ 4f / 32, 0.5f,// M
						12f / 32, 0.3f, 24f / 32, 0f,// a
						12f / 32, 0.3f, -24f / 32, 0f,// a
				};
				// value = "M40,150 a120,90 0 0,0 240,0 a120,90 0 0,0 -240,0";
				value = "M" + Math.round(width * scales[0]) + " "
						+ Math.round(height * scales[1]) + "a"
						+ Math.round(width * scales[2]) + " "
						+ Math.round(height * scales[3]) + " 0 0,0 "
						+ Math.round(width * scales[4]) + " "
						+ Math.round(height * scales[5]) + "a"
						+ Math.round(width * scales[6]) + " "
						+ Math.round(height * scales[7]) + " 0 0,0 "
						+ Math.round(width * scales[8]) + " "
						+ Math.round(height * scales[9]);
				break;
			}
			case Constants.EN_WORDARTTEXT_TYPE_ELLIPSEUP:
			{
				float[] scales =
				{ 4f / 58, 28f / 30,// M
						25f / 58, 1.9f / 3, 50f / 58, 0f,// a
				};
				// value = "M40,280 a250,190 0 0,1 500,0";
				value = "M" + Math.round(width * scales[0]) + " "
						+ Math.round(height * scales[1]) + "a"
						+ Math.round(width * scales[2]) + " "
						+ Math.round(height * scales[3]) + " 0 0,1 "
						+ Math.round(width * scales[4]) + " "
						+ Math.round(height * scales[5]);
				break;
			}
			case Constants.EN_WORDARTTEXT_TYPE_ZHEXIAN2:
			{
				float[] scales =
				{ 4f / 62, 1f,// M
						0.5f, 1.0f / 3,// L
						58f / 62, 1f,// L
				};
				// value = "M40,300 L 310 100 L580 300";
				value = "M" + Math.round(width * scales[0]) + " "
						+ Math.round(height * scales[1]) + " L"
						+ Math.round(width * scales[2]) + " "
						+ Math.round(height * scales[3]) + " L"
						+ Math.round(width * scales[4]) + " "
						+ Math.round(height * scales[5]);
				break;
			}
			case Constants.EN_WORDARTTEXT_TYPE_ZHEXIAN3:
			{
				float[] scales =
				{ 4f / 70, 2f / 3,// M
						2f / 7, 8f / 30,// L
						4f / 7, 2f / 3,// L
						66f / 70, 8f / 30,// L
				};
				// value = "M40,200 L 220 80 L400 200 L660 80"
				value = "M" + Math.round(width * scales[0]) + " "
						+ Math.round(height * scales[1]) + " L"
						+ Math.round(width * scales[2]) + " "
						+ Math.round(height * scales[3]) + " L"
						+ Math.round(width * scales[4]) + " "
						+ Math.round(height * scales[5]) + " L"
						+ Math.round(width * scales[6]) + " "
						+ Math.round(height * scales[7]);
				break;
			}
			default:
			{
				// float[] scales =
				// { 0.02f, 0.5f,// M
				// 1f, 0.5f,// L
				// };
				// // value = "M20,150 L 1000 150";
				// value = "M" + Math.round(width * scales[0]) + " "
				// + Math.round(height * scales[1]) + " L"
				// + Math.round(width * scales[2]) + " "
				// + Math.round(height * scales[3]);
				// String path = "";
				double tanwh = height / width;
				int qishix = 0;
				int qishiy = 0;
				int zhongzhix = 0;
				int zhongzhiy = 0;
				if (orientation == -180)
				{
					qishix = width;
					qishiy = height;
					zhongzhiy = height;
				} else if (orientation > -180 && orientation < -90)
				{
					qishix = width;
					qishiy = height;
					orientation = orientation + 180;
					double tan = Math.tan(Math.PI * orientation / 180);
					if (tanwh >= tan)
					{
						zhongzhiy = new Double(height - width * tan).intValue();
					} else
					{
						zhongzhix = new Double(width - height / tan).intValue();
					}
				} else if (orientation == -90)
				{
					qishiy = height;
				} else if (orientation > -90 && orientation < 0)
				{
					qishix = 0;
					qishiy = height;
					orientation = Math.abs(orientation);
					double tan = Math.tan(Math.PI * orientation / 180);
					if (tanwh >= tan)
					{
						zhongzhix = width;
						zhongzhiy = new Double(height - width * tan).intValue();
					} else
					{
						zhongzhix = new Double(height / tan).intValue();
					}
				} else if (orientation == 0)
				{
					qishiy = height;
					zhongzhix = width;
					zhongzhiy = height;
				} else if (orientation > 0 && orientation < 90)
				{
					qishix = 0;
					qishiy = 0;
					double tan = Math.tan(Math.PI * orientation / 180);
					if (tanwh >= tan)
					{
						zhongzhix = width;
						zhongzhiy = new Double(width * tan).intValue();
					} else
					{
						zhongzhix = new Double(height / tan).intValue();
						zhongzhiy = height;
					}
				} else if (orientation == 90)
				{
					zhongzhiy = height;
				} else if (orientation > 90 && orientation < 180)
				{
					qishix = width;
					qishiy = 0;
					orientation = 180 - orientation;
					double tan = Math.tan(Math.PI * orientation / 180);
					if (tanwh >= tan)
					{
						zhongzhiy = new Double(width * tan).intValue();
					} else
					{
						zhongzhix = new Double(width - height / tan).intValue();
						zhongzhiy = height;
					}
				} else if (orientation == 180)
				{
					qishix = width;
					qishiy = height;
					zhongzhiy = height;
				}
				value = "M" + qishix + "," + qishiy + " L " + zhongzhix + " "
						+ zhongzhiy;
				break;
			}
		}
		return value;
	}
}