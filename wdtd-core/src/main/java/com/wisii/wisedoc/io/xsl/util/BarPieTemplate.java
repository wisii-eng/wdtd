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

package com.wisii.wisedoc.io.xsl.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.metadata.IIOMetadataNode;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import com.wisii.wisedoc.document.ChartReaderUtil;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.barpieattribute.ChartData2D;
import com.wisii.wisedoc.document.barpieattribute.ChartDataList;

//import com.wisii.wisedoc.document.svg.DocumentParser;

public class BarPieTemplate
{


	// public static void main(String[] args)
	// {
	// String proty =
	// "a137=0 a342=1 a136=0 a341=6 a138=0 a135=0 a395=48 a394=215 a393=90 a397=48 a396=48 a332=208 a385=149 a333=统计图 a384=23 a390=0 a373=2.0 a375=10.5 a374=0.5 a369=1 a368=213 a371=48 a106=226.771 a379=20 a354=0 a242=368.503 a343=-256"
	// ;
	// String xzhou = "";
	// String yzhou = "";
	// String strvaluelabel = "种类1@@种类2@@种类3@@种类4@@种类5@@种类6";
	// String strserteslabel = "1";
	// String sertesvalue =
	// "0@0@2.545;0@1@3.456;0@2@2.654;0@3@3.154;0@4@3.5469;0@5@4.1284585";
	// System.out.println(new BarPieTemplate().getSvgCode(proty, xzhou,
	// yzhou, strvaluelabel, strserteslabel, sertesvalue));
	// }

	public static DocumentFragment getSvgCode(String proty, String xzhou,
			String yzhou, String strvaluelabel, String strserteslabel,
			String sertesvalue)
	{

		Map<Integer, Object> map = new HashMap<Integer, Object>();
		NamedNodeMap properties = (getElement(proty, xzhou, yzhou,
				strvaluelabel, strserteslabel, sertesvalue)).getAttributes();
		Double height = 0d;
		Double width = 0d;
		for (int i = 0; i < properties.getLength(); i++)
		{
			Node node = properties.item(i);
			int key = new Integer(node.getNodeName().replaceAll("a", ""))
					.intValue();

			String value = node.getNodeValue();

			Object obj = getObject(key, value);
			if (obj != null)
			{
				map.put(key, obj);
				if (key == Constants.PR_WIDTH)
				{
					width = (Double) obj;
				} else if (key == Constants.PR_HEIGHT)
				{
					height = (Double) obj;
				}
			}
		}
		
		Element element = ChartReaderUtil.getSVGDocument(map);
		element.setAttribute("xmlns", "http://www.w3.org/2000/svg");
		element.setAttribute("width", width + "pt");
		element.setAttribute("height", height + "pt");
		Document doc = element.getOwnerDocument();
		DocumentFragment frag = doc.createDocumentFragment();
		frag.appendChild(doc.importNode(element, true));
		return frag;
	}

	
	private static Element getElement(String proty, String xzhou, String yzhou,
			String strvaluelabel, String strserteslabel, String sertesvalue)
	{
		IIOMetadataNode element = new IIOMetadataNode();

		String[] lists = proty.split(" ");
		for (int i = 0; i < lists.length; i++)
		{
			String node = lists[i];
			if (node.contains("="))
			{
				String[] item = node.split("=");
				element.setAttribute(item[0], item[1]);
			}
		}
		if (!xzhou.isEmpty())
		{
			element.setAttribute("a" + Constants.PR_DOMAINAXIS_LABEL, xzhou);
		}
		if (!yzhou.isEmpty())
		{
			element.setAttribute("a" + Constants.PR_RANGEAXIS_LABEL, yzhou);
		}
		if (!strvaluelabel.isEmpty())
		{
			element.setAttribute("a" + Constants.PR_VALUE_LABEL, strvaluelabel);
		}
		if (!"".equals(strserteslabel))
		{
			element.setAttribute("a" + Constants.PR_SERIES_LABEL,
					strserteslabel);
		}
		if (!sertesvalue.isEmpty())
		{
			element.setAttribute("a" + Constants.PR_SERIES_VALUE, sertesvalue);
		}
		return element;
	}

	private static Object getObject(int key, String value)
	{
		Object obj = null;
		if (key == Constants.PR_CHART_VALUE_FONTFAMILY
				|| key == Constants.PR_LENGEND_LABEL_FONTFAMILY
				|| key == Constants.PR_RANGEAXIS_LABEL_FONTFAMILY
				|| key == Constants.PR_DOMAINAXIS_LABEL_FONTFAMILY
				|| key == Constants.PR_RANGEAXIS_LABEL
				|| key == Constants.PR_DOMAINAXIS_LABEL
				|| key == Constants.PR_SERIES_LABEL_FONTFAMILY
				|| key == Constants.PR_VALUE_LABEL_FONTFAMILY
				|| key == Constants.PR_TITLE_FONTFAMILY
				|| key == Constants.PR_TITLE
				|| key == Constants.PR_BACKGROUND_IMAGE)
		{
			obj = value;
		} else if (key == Constants.PR_WIDTH || key == Constants.PR_HEIGHT
				|| key == Constants.PR_BORDER_WIDTH
				|| key == Constants.PR_BORDER_BEFORE_WIDTH
				|| key == Constants.PR_BORDER_AFTER_WIDTH
				|| key == Constants.PR_BORDER_START_WIDTH
				|| key == Constants.PR_BORDER_END_WIDTH
				|| key == Constants.PR_CHART_VALUE_OFFSET
				|| key == Constants.PR_3DDEPNESS
				|| key == Constants.PR_RANGEAXIS_LABEL_FONTSIZE
				|| key == Constants.PR_CHART_VALUE_FONTSIZE
				|| key == Constants.PR_LENGEND_LABEL_FONTSIZE
				|| key == Constants.PR_DOMAINAXIS_LABEL_FONTSIZE
				|| key == Constants.PR_SERIES_LABEL_FONTSIZE
				|| key == Constants.PR_VALUE_LABEL_FONTSIZE
				|| key == Constants.PR_TITLE_FONTSIZE
				|| key == Constants.PR_RANGEAXIS_MAXUNIT
				|| key == Constants.PR_RANGEAXIS_MINUNIT
				|| key == Constants.PR_RANGEAXIS_UNITINCREMENT
				|| key == Constants.PR_MARGIN || key == Constants.PR_MARGIN_TOP
				|| key == Constants.PR_MARGIN_BOTTOM
				|| key == Constants.PR_MARGIN_LEFT
				|| key == Constants.PR_MARGIN_RIGHT)
		{
			obj = createDouble(value);
		} else if (key == Constants.PR_BORDER_COLOR
				|| key == Constants.PR_BORDER_BEFORE_COLOR
				|| key == Constants.PR_BORDER_AFTER_COLOR
				|| key == Constants.PR_BORDER_START_COLOR
				|| key == Constants.PR_BORDER_END_COLOR
				|| key == Constants.PR_LENGEND_LABEL_COLOR
				|| key == Constants.PR_RANGEAXIS_LABEL_COLOR
				|| key == Constants.PR_DOMAINAXIS_LABEL_COLOR
				|| key == Constants.PR_SERIES_LABEL_COLOR
				|| key == Constants.PR_VALUE_LABEL_COLOR
				|| key == Constants.PR_CHART_VALUE_COLOR
				|| key == Constants.PR_TITLE_COLOR
				|| key == Constants.PR_BACKGROUND_COLOR)
		{
			obj = createColor(value);
		} else if (key == Constants.PR_BACKGROUNDIMAGE_ALAPH
				|| key == Constants.PR_FOREGROUND_ALAPH)
		{
			obj = createFloat(value);
		} else if (key == Constants.PR_PIECHART_STARTANGLE
				|| key == Constants.PR_SERIES_LABEL_ORIENTATION
				|| key == Constants.PR_SERIES_COUNT
				|| key == Constants.PR_VALUE_COUNT
				|| key == Constants.PR_GRAPHIC_LAYER
				|| key == Constants.PR_BORDER_STYLE
				|| key == Constants.PR_BORDER_BEFORE_STYLE
				|| key == Constants.PR_BORDER_AFTER_STYLE
				|| key == Constants.PR_BORDER_START_STYLE
				|| key == Constants.PR_BORDER_END_STYLE
				|| key == Constants.PR_PIECHARTLENGENDLABEL_VISABLE
				|| key == Constants.PR_PERCENTVALUE_VISABLE
				|| key == Constants.PR_ZEROVALUE_VISABLE
				|| key == Constants.PR_NULLVALUE_VISABLE
				|| key == Constants.PR_PIECHART_DIRECTION
				|| key == Constants.PR_BORDER_BEFORE_STYLE
				|| key == Constants.PR_CHART_VALUE_FONTSTYLE
				|| key == Constants.PR_VALUE_LABLEVISABLE
				|| key == Constants.PR_LENGEND_LABLE_ALIGNMENT
				|| key == Constants.PR_LENGEND_LABEL_FONTSTYLE
				|| key == Constants.PR_3DENABLE
				|| key == Constants.PR_ZERORANGELINE_VISABLE
				|| key == Constants.PR_RANGELINE_VISABLE
				|| key == Constants.PR_DOMIANLINE_VISABLE
				|| key == Constants.PR_CHART_ORIENTATION
				|| key == Constants.PR_LENGEND_LABEL_LOCATION
				|| key == Constants.PR_RANGEAXIS_LABEL_ALIGNMENT
				|| key == Constants.PR_RANGEAXIS_LABEL_FONTSTYLE
				|| key == Constants.PR_DOMAINAXIS_LABEL_ALIGNMENT
				|| key == Constants.PR_DOMAINAXIS_LABEL_FONTSTYLE
				|| key == Constants.PR_SERIES_LABEL_FONTSTYLE
				|| key == Constants.PR_VALUE_LABEL_FONTSTYLE
				|| key == Constants.PR_TITLE_ALIGNMENT
				|| key == Constants.PR_TITLE_FONTSTYLE
				|| key == Constants.PR_CHART_TYPE
				|| key == Constants.PR_RANGEAXIS_PRECISION)
		{
			obj = createInteger(value);
		} else if (key == Constants.PR_VALUE_COLOR)
		{
			List<Color> colorlist = new ArrayList<Color>();
			String[] colors = value.split(",");
			for (String current : colors)
			{
				Color color = new Color(new Integer(current).intValue());
				colorlist.add(color);
			}
			obj = colorlist;
		} else if (key == Constants.PR_VALUE_LABEL)
		{
			obj = new ArrayList<String>();
			List<String> valuelist = new ArrayList<String>();
			String[] values = value.split("@@");
			for (String current : values)
			{
				valuelist.add(current);
			}
			obj = valuelist;
		} else if (key == Constants.PR_SERIES_LABEL)
		{
			obj = new ArrayList<String>();
			obj = new ArrayList<String>();
			List<String> setreslist = new ArrayList<String>();
			String[] values = value.split("@@");
			for (String current : values)
			{
				setreslist.add(current);
			}
			obj = setreslist;
		} else if (key == Constants.PR_SERIES_VALUE)
		{
			if (!value.isEmpty())
			{
				obj = new ChartDataList();
				ChartDataList lists = new ChartDataList();
				String[] items = value.split(";");
				for (String item : items)
				{
					String[] depart = item.split("@");
					if (depart.length == 3)
					{
						ChartData2D current = new ChartData2D(new Integer(
								depart[0]).intValue(), new Integer(depart[1])
								.intValue(), new Double(depart[2])
								.doubleValue());
						lists.add(current);
					}
				}
				return lists;
			}
		}
		return obj;
	}

	private static Color createColor(String color)
	{
		if (color.isEmpty())
		{
			return null;
		} else
		{
			return new Color(new Integer(color).intValue());
		}
	}

	private static Integer createInteger(String strbunber)
	{
		if (strbunber.isEmpty())
		{
			return null;
		} else
		{
			return new Integer(strbunber).intValue();
		}
	}

	private static Double createDouble(String strbunber)
	{
		if (strbunber.isEmpty())
		{
			return null;
		} else
		{
			return new Double(strbunber).doubleValue();
		}
	}

	private static Float createFloat(String strbunber)
	{
		if (strbunber.isEmpty())
		{
			return null;
		} else
		{
			return new Float(strbunber).floatValue();
		}
	}

}
