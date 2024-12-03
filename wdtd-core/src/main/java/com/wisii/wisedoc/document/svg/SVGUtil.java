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
 * @SVGUtil.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.svg;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;

import org.w3c.dom.Element;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.PercentLength;
import com.wisii.wisedoc.document.datatype.LengthBase;
import com.wisii.wisedoc.io.xsl.attribute.AttributeValueWriter;
import com.wisii.wisedoc.io.xsl.attribute.EnumPropertyWriter;
import com.wisii.wisedoc.io.xsl.attribute.FixedLengthWriter;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;

/**
 * 类功能描述：svg工具类
 * 
 * 作者：zhangqiang 创建日期：2009-2-26
 */
public class SVGUtil
{

	private static HashMap<Integer, String> attMap = new HashMap<Integer, String>();
	static
	{
		attMap.put(Constants.PR_X, FoXsltConstants.X);
		attMap.put(Constants.PR_Y, FoXsltConstants.Y);
		attMap.put(Constants.PR_X1, FoXsltConstants.X1);
		attMap.put(Constants.PR_Y1, FoXsltConstants.Y1);
		attMap.put(Constants.PR_X2, FoXsltConstants.X2);
		attMap.put(Constants.PR_Y2, FoXsltConstants.Y2);
		attMap.put(Constants.PR_CX, FoXsltConstants.CX);
		attMap.put(Constants.PR_CY, FoXsltConstants.CY);
		attMap.put(Constants.PR_R, FoXsltConstants.R);
		attMap.put(Constants.PR_RX, FoXsltConstants.RX);
		attMap.put(Constants.PR_RY, FoXsltConstants.RY);
		attMap.put(Constants.PR_FILL, FoXsltConstants.FILL);
		attMap.put(Constants.PR_WIDTH, FoXsltConstants.WIDTH);
		attMap.put(Constants.PR_HEIGHT, FoXsltConstants.HEIGHT);
		attMap.put(Constants.PR_COLOR, FoXsltConstants.STROKE);
		attMap.put(Constants.PR_STROKE_WIDTH, FoXsltConstants.STROKE_WIDTH);
		attMap.put(Constants.PR_FONT_FAMILY, FoXsltConstants.FONT_FAMILY);
		attMap.put(Constants.PR_FONT_SIZE, FoXsltConstants.FONT_SIZE);
		attMap.put(Constants.PR_FONT_WEIGHT, FoXsltConstants.FONT_WEIGHT);
		attMap.put(Constants.PR_FONT_STYLE, FoXsltConstants.FONT_STYLE);
		attMap.put(Constants.PR_TEXT_DECORATION,
				FoXsltConstants.TEXT_DECORATION);
		attMap.put(Constants.PR_ID, FoXsltConstants.ID);
		attMap
				.put(Constants.PR_SVG_LINE_TYPE,
						FoXsltConstants.STROKE_DASHARRAY);
		attMap.put(Constants.PR_SVG_STROKE_LINEJOIN,
				FoXsltConstants.STROKE_LINEJOIN);
		attMap.put(Constants.PR_APHLA, FoXsltConstants.FILL_OPACITY);
	}

	public static String getKeyString(int key)
	{
		String attName = "";
		if (attMap.containsKey(key))
		{
			attName = attMap.get(key);
		}
		return attName;
	}

	public static void initSVGAttributes(org.w3c.dom.Element svgelement,
			Attributes attrs)
	{
		if (svgelement != null && attrs != null)
		{
			Iterator<Integer> keyit = attrs.getAttributeKeys();
			while (keyit.hasNext())
			{
				int key = keyit.next();
				Object value = attrs.getAttribute(key);
				if (value == null)
				{
					continue;
				} else
				{
					if ((key == Constants.PR_WIDTH || key == Constants.PR_HEIGHT)
							&& svgelement.getNodeName().equals("ellipse"))
					{
						if (value instanceof FixedLength)
						{
							value = new FixedLength(((FixedLength) value)
									.getValue() / 2);
						}
						if (key == Constants.PR_WIDTH)
						{
							key = Constants.PR_RX;
						}
						if (key == Constants.PR_HEIGHT)
						{
							key = Constants.PR_RY;
						}
					}
					if ((key == Constants.PR_X || key == Constants.PR_Y)
							&& svgelement.getNodeName().equals("ellipse"))
					{
						if (key == Constants.PR_X)
						{
							FixedLength width = (FixedLength) attrs
									.getAttribute(Constants.PR_WIDTH);
							value = new FixedLength(((FixedLength) value)
									.getValue()
									+ width.getValue() / 2);
							key = Constants.PR_CX;
						} else
						{
							FixedLength height = (FixedLength) attrs
									.getAttribute(Constants.PR_HEIGHT);
							value = new FixedLength(((FixedLength) value)
									.getValue()
									+ height.getValue() / 2);
							key = Constants.PR_CY;
						}
					}
					String keyStr = getKeyString(key);
					if (keyStr == null || keyStr.equals(""))
					{
						continue;
					} else
					{
						String valueStr = "";
						if (value instanceof String)
						{
							valueStr = value.toString();
						} else if (value instanceof Color)
						{
							Color color = (Color) value;
							valueStr = "rgb(" + color.getRed() + ","
									+ color.getGreen() + "," + color.getBlue()
									+ ")";
						} else if (value instanceof Integer)
						{
							if (key == Constants.PR_SVG_LINE_TYPE)
							{
								FixedLength linewidth = (FixedLength) attrs
										.getAttribute(Constants.PR_STROKE_WIDTH);
								int index = (Integer) attrs.getAttribute(key);
								valueStr = getBorderStyle(index, linewidth);
							}
							// 如果是线头线尾箭头属性，则不在这处理
							else if (key == Constants.PR_SVG_ARROW_START_TYPE
									|| key == Constants.PR_SVG_ARROW_END_TYPE)
							{
								continue;
							} else
							{
								valueStr = value + "";
							}

						} else
						{
							// 如果是位置属性，则需要偏移线性宽度的大小
							if ((key >= Constants.PR_X)
									&& (key <= Constants.PR_CY)
									&& (value instanceof FixedLength))
							{
								FixedLength linewidth = (FixedLength) attrs
										.getAttribute(Constants.PR_STROKE_WIDTH);
								// 得加上线的粗细的一半做为偏移量
								if (linewidth != null)
								{
									value = new FixedLength(
											((FixedLength) value).getValue()
													+ Math.round(linewidth
															.getValue() / 2.0f));
								}
							}

							AttributeValueWriter write = getWriter(value);
							if (write != null)
							{
								valueStr = write.write(value);
							}
							if (key == Constants.PR_APHLA)
							{
								valueStr = "" + value;
							}
						}
						if (!valueStr.equals(""))
						{
							svgelement.setAttribute(keyStr, valueStr);
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * 根据线头类型和线头颜色初始化线头对应Marker元素的属性
	 * 
	 * @param svgmarker
	 *            :线头元素 type：线头类型。 color：线头颜色 isstart是否是歧视点线头
	 * @return
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static void initSVGMarkerAttributes(org.w3c.dom.Document doc,
			org.w3c.dom.Element svgmarker, int type, Color color,
			boolean isstart)
	{
		if (svgmarker != null)
		{
			svgmarker.setAttribute("viewBox", "0 0 10 10");
			if (isstart)
			{
				svgmarker.setAttribute("refX", "2");
			} else
			{
				svgmarker.setAttribute("refX", "8");
			}
			svgmarker.setAttribute("refY", "5");
			svgmarker.setAttribute("markerUnits", "strokeWidth");
			svgmarker.setAttribute("orient", "auto");
			if (color == null)
			{
				color = Color.black;
			}
			String colorstring = "rgb(" + color.getRed() + ","
					+ color.getGreen() + "," + color.getBlue() + ")";
			svgmarker.setAttribute("fill", colorstring);
			svgmarker.setAttribute("stroke-width", "1");
			svgmarker.setAttribute("markerWidth", "5");
			svgmarker.setAttribute("markerHeight", "5");
			org.w3c.dom.Element path = doc.createElementNS(Canvas.NAMESPACEURI,
					"path");
			initSVGPathAttributes(path, type, isstart);
			svgmarker.appendChild(path);
		}
	}

	private static void initSVGPathAttributes(Element path, int type,
			boolean isstart)
	{
		if (path != null)
		{
			String pathvalue = null;
			switch (type)
			{
			// 三角
			case 1:
				if (isstart)
				{
					pathvalue = "M 0 5 L 10 0 L 10 10 z";
				} else
				{
					pathvalue = "M 0 0 L 10 5 L 0 10 z";
				}
				break;
			// 正方形
			case 2:
				pathvalue = "M 0 0 L 10 0 L 10 10 L 0 10 z";
				break;
			// 圆
			case 3:
				pathvalue = "M 5 0 L 10 5 L 5 10 L 0 5 z";
				break;
			// 菱形
			case 4:
				pathvalue = "M5,0 a5,5 0 0,0 0,10 a5,5 0 0,0 0,-10";
				break;
			// 内凹平面三角头
			case 5:
				if (isstart)
				{
					pathvalue = "M 0 5 L 10 0 L 5 5 L 10 10 z";
				} else
				{
					pathvalue = "M 0 0 L 10 5 L 0 10 L 5 5 z";
				}
				break;
			// 内凹弧面三角头
			case 6:
				if (isstart)
				{
					pathvalue = "M 10,10 L 0,5 L 10,0  a3,5 0 0,0 -3,5 a3,5 0 0,0 3,5";
				} else
				{
					pathvalue = "M 0,0 L 10,5 L 0,10  a3,5 0 0,0 3,-5 a3,5 0 0,0 -3,-5";
				}
				break;
			default:
				break;
			}
			if (pathvalue != null)
			{
				path.setAttribute("d", pathvalue);
			}
		}

	}

	public static AttributeValueWriter getWriter(Object value)
	{
		AttributeValueWriter writer = null;
		if (value instanceof FixedLength)
		{
			writer = new FixedLengthWriter();
		} else if (value instanceof EnumProperty)
		{
			writer = new EnumPropertyWriter();
		}
		return writer;
	};

	public static boolean inLeftTopSide(FixedLength jidian,
			FixedLength bijiaodian)
	{
		boolean flg = false;
		if (jidian != null && bijiaodian != null)
		{
			double xjValue = jidian.getNumericValue();
			double xValue = bijiaodian.getNumericValue();
			if (xjValue >= xValue)
			{
				flg = true;
			}
		}
		return flg;
	}

	public static boolean inRightBottomSide(FixedLength jidian,
			FixedLength bijiaodian)
	{
		boolean flg = false;
		if (bijiaodian != null)
		{
			double xjValue = jidian != null ? jidian.getNumericValue() : 0d;
			double xValue = bijiaodian.getNumericValue();
			if (xjValue <= xValue)
			{
				flg = true;
			}
		}
		return flg;
	}

	public static FixedLength getSumLength(FixedLength old, FixedLength add)
	{
		FixedLength sum = null;
		if (old != null && add != null)
		{
			double oldValue = old.getNumericValue();
			double addValue = add.getNumericValue();
			double sumValue = oldValue + addValue;
			String oldunits = old.getUnits();
			double dvalue = sumValue;
			if (oldunits.equals("in"))
			{
				dvalue = sumValue / 72000;
			} else if (oldunits.equals("cm"))
			{
				dvalue = sumValue / 72000 * 2.54;
			} else if (oldunits.equals("mm"))
			{
				dvalue = sumValue / 72000 * 25.4;
			} else if (oldunits.equals("pt"))
			{
				dvalue = sumValue / 1000;
			}
			int precision = old.getPrecision();
			sum = new FixedLength(dvalue, oldunits, precision);
		} else if (add == null)
		{
			sum = old;
		}
		return sum;
	}

	public static FixedLength getSumLength(LengthProperty old,
			LengthProperty add)
	{
		FixedLength sum = null;
		if (old != null && add != null)
		{
			double oldValue = 0;
			String oldunits = "";
			int precision = 0;
			if (old instanceof FixedLength)
			{
				FixedLength length = (FixedLength) old;
				oldValue = length.getNumericValue();
				oldunits = length.getUnits();
				precision = length.getPrecision();
			} else if (old instanceof PercentLength)
			{
				PercentLength property = (PercentLength) old;
				double factor = property.value();
				FixedLength length = (FixedLength) ((LengthBase) property
						.getBaseLength()).getBaseLength();
				oldValue = factor * length.getNumericValue();
				oldunits = length.getUnits();
				precision = length.getPrecision();
			}
			double addValue = add.getNumericValue();
			if (add instanceof FixedLength)
			{
				FixedLength length = (FixedLength) add;
				oldValue = length.getNumericValue();
				oldunits = length.getUnits();
				precision = length.getPrecision();
			} else if (add instanceof PercentLength)
			{
				PercentLength property = (PercentLength) add;
				double factor = property.value();
				FixedLength length = (FixedLength) ((LengthBase) property
						.getBaseLength()).getBaseLength();
				oldValue = factor * length.getNumericValue();
				oldunits = length.getUnits();
				precision = length.getPrecision();
			}
			double sumValue = oldValue + addValue;
			double dvalue = sumValue;
			if (oldunits.equals("in"))
			{
				dvalue = sumValue / 72000;
			} else if (oldunits.equals("cm"))
			{
				dvalue = sumValue / 72000 * 2.54;
			} else if (oldunits.equals("mm"))
			{
				dvalue = sumValue / 72000 * 25.4;
			} else if (oldunits.equals("pt"))
			{
				dvalue = sumValue / 1000;
			}
			sum = new FixedLength(dvalue, oldunits, precision);
		} else if (add == null)
		{
			if (old instanceof FixedLength)
			{
				FixedLength length = (FixedLength) old;
				sum = length;
			} else if (old instanceof PercentLength)
			{
				PercentLength property = (PercentLength) old;
				double factor = property.value();
				FixedLength length = (FixedLength) ((LengthBase) property
						.getBaseLength()).getBaseLength();
				sum = new FixedLength(factor * length.getNumericValue(), length
						.getUnits(), length.getPrecision());
			}
		}
		return sum;
	}

	public static FixedLength getSumHalfLength(FixedLength old, FixedLength add)
	{
		FixedLength sum = null;
		if (old != null && add != null)
		{
			double oldValue = old.getNumericValue();
			double addValue = add.getNumericValue();
			double sumValue = oldValue + addValue / 2;
			String oldunits = old.getUnits();
			double dvalue = sumValue;
			if (oldunits.equals("in"))
			{
				dvalue = sumValue / 72000;
			} else if (oldunits.equals("cm"))
			{
				dvalue = sumValue / 72000 * 2.54;
			} else if (oldunits.equals("mm"))
			{
				dvalue = sumValue / 72000 * 25.4;
			} else if (oldunits.equals("pt"))
			{
				dvalue = sumValue / 1000;
			}
			int precision = old.getPrecision();
			sum = new FixedLength(dvalue, oldunits, precision);
		} else if (add == null)
		{
			sum = old;
		}
		return sum;
	}

	public static FixedLength getHalfOfSumLength(FixedLength old,
			FixedLength add)
	{
		FixedLength sum = null;
		if (old != null && add != null)
		{
			double oldValue = old.getNumericValue();
			double addValue = add.getNumericValue();
			double sumValue = (oldValue + addValue) / 2;
			String oldunits = old.getUnits();
			double dvalue = sumValue;
			if (oldunits.equals("in"))
			{
				dvalue = sumValue / 72000;
			} else if (oldunits.equals("cm"))
			{
				dvalue = sumValue / 72000 * 2.54;
			} else if (oldunits.equals("mm"))
			{
				dvalue = sumValue / 72000 * 25.4;
			} else if (oldunits.equals("pt"))
			{
				dvalue = sumValue / 1000;
			}
			int precision = old.getPrecision();
			sum = new FixedLength(dvalue, oldunits, precision);
		} else if (add == null)
		{
			sum = old;
		}
		return sum;
	}

	public static FixedLength getSubLength(FixedLength old, FixedLength add)
	{
		FixedLength sub = null;
		if (old != null && add != null)
		{
			double oldValue = old.getNumericValue();
			double addValue = add.getNumericValue();
			double sumValue = oldValue - addValue;
			String oldunits = old.getUnits();
			double dvalue = sumValue;
			if (oldunits.equals("in"))
			{
				dvalue = sumValue / 72000;
			} else if (oldunits.equals("cm"))
			{
				dvalue = sumValue / 72000 * 2.54;
			} else if (oldunits.equals("mm"))
			{
				dvalue = sumValue / 72000 * 25.4;
			} else if (oldunits.equals("pt"))
			{
				dvalue = sumValue / 1000;
			}
			int precision = old.getPrecision();
			sub = new FixedLength(dvalue, oldunits, precision);
		} else if (add == null)
		{
			sub = old;
		}
		return sub;
	}

	public static FixedLength getSubLength(LengthProperty old,
			LengthProperty add)
	{
		FixedLength sub = null;
		if (old != null && add != null)
		{
			double oldValue = 0;
			String oldunits = "";
			int precision = 0;
			if (old instanceof FixedLength)
			{
				FixedLength length = (FixedLength) old;
				oldValue = length.getNumericValue();
				oldunits = length.getUnits();
				precision = length.getPrecision();
			} else if (old instanceof PercentLength)
			{
				PercentLength property = (PercentLength) old;
				double factor = property.value();
				FixedLength length = (FixedLength) ((LengthBase) property
						.getBaseLength()).getBaseLength();
				oldValue = factor * length.getNumericValue();
				oldunits = length.getUnits();
				precision = length.getPrecision();
			}
			double addValue = add.getNumericValue();
			if (add instanceof FixedLength)
			{
				FixedLength length = (FixedLength) add;
				oldValue = length.getNumericValue();
				oldunits = length.getUnits();
				precision = length.getPrecision();
			} else if (add instanceof PercentLength)
			{
				PercentLength property = (PercentLength) add;
				double factor = property.value();
				FixedLength length = (FixedLength) ((LengthBase) property
						.getBaseLength()).getBaseLength();
				oldValue = factor * length.getNumericValue();
				oldunits = length.getUnits();
				precision = length.getPrecision();
			}
			double sumValue = oldValue - addValue;
			double dvalue = sumValue;
			if (oldunits.equals("in"))
			{
				dvalue = sumValue / 72000;
			} else if (oldunits.equals("cm"))
			{
				dvalue = sumValue / 72000 * 2.54;
			} else if (oldunits.equals("mm"))
			{
				dvalue = sumValue / 72000 * 25.4;
			} else if (oldunits.equals("pt"))
			{
				dvalue = sumValue / 1000;
			}
			sub = new FixedLength(dvalue, oldunits, precision);
		} else if (add == null)
		{
			if (old instanceof FixedLength)
			{
				FixedLength length = (FixedLength) old;
				sub = length;
			} else if (old instanceof PercentLength)
			{
				PercentLength property = (PercentLength) old;
				double factor = property.value();
				FixedLength length = (FixedLength) ((LengthBase) property
						.getBaseLength()).getBaseLength();
				sub = new FixedLength(factor * length.getNumericValue(), length
						.getUnits(), length.getPrecision());
			}
		}
		return sub;
	}

	public static FixedLength getSubHalfLength(FixedLength old, FixedLength add)
	{
		FixedLength sub = null;
		if (old != null && add != null)
		{
			double oldValue = old.getNumericValue();
			double addValue = add.getNumericValue();
			double sumValue = oldValue - addValue / 2;
			String oldunits = old.getUnits();
			double dvalue = sumValue;
			if (oldunits.equals("in"))
			{
				dvalue = sumValue / 72000;
			} else if (oldunits.equals("cm"))
			{
				dvalue = sumValue / 72000 * 2.54;
			} else if (oldunits.equals("mm"))
			{
				dvalue = sumValue / 72000 * 25.4;
			} else if (oldunits.equals("pt"))
			{
				dvalue = sumValue / 1000;
			}
			int precision = old.getPrecision();
			sub = new FixedLength(dvalue, oldunits, precision);
		} else if (add == null)
		{
			sub = old;
		}
		return sub;
	}

	public static FixedLength getSubAbsLength(FixedLength old, FixedLength add)
	{
		FixedLength sub = null;
		if (old != null && add != null)
		{
			double oldValue = old.getNumericValue();
			double addValue = add.getNumericValue();
			double sumValue = Math.abs(oldValue - addValue);
			String oldunits = old.getUnits();
			double dvalue = sumValue;
			if (oldunits.equals("in"))
			{
				dvalue = sumValue / 72000;
			} else if (oldunits.equals("cm"))
			{
				dvalue = sumValue / 72000 * 2.54;
			} else if (oldunits.equals("mm"))
			{
				dvalue = sumValue / 72000 * 25.4;
			} else if (oldunits.equals("pt"))
			{
				dvalue = sumValue / 1000;
			}
			int precision = old.getPrecision();
			sub = new FixedLength(dvalue, oldunits, precision);
		} else if (add == null)
		{
			sub = old;
		}
		return sub;
	}

	public static FixedLength getNumbersOfLength(FixedLength old, double num)
	{
		FixedLength sub = null;
		if (old != null)
		{
			double oldValue = old.getNumericValue();
			double sumValue = oldValue * num;
			String oldunits = old.getUnits();
			double dvalue = sumValue;
			if (oldunits.equals("in"))
			{
				dvalue = sumValue / 72000;
			} else if (oldunits.equals("cm"))
			{
				dvalue = sumValue / 72000 * 2.54;
			} else if (oldunits.equals("mm"))
			{
				dvalue = sumValue / 72000 * 25.4;
			} else if (oldunits.equals("pt"))
			{
				dvalue = sumValue / 1000;
			}
			int precision = old.getPrecision();
			sub = new FixedLength(dvalue, oldunits, precision);
		}
		return sub;
	}

	public static FixedLength createFixedLength(String fixedlength)
	{
		if (fixedlength.equalsIgnoreCase(""))
		{
			return null;
		} else
		{
			String[] number = fixedlength.split(",");
			FixedLength fl = null;
			if (number.length == 1)
			{
				fl = new FixedLength(new Integer(number[0]).intValue());
			} else
			{
				fl = new FixedLength(new Double(number[0]).doubleValue(),
						number[1]);
			}
			return fl;
		}
	}

	/**
	 * 
	 * 获取线型
	 * 
	 * @param index
	 *            线型的索引
	 * @return 线型名称
	 * @exception 当线型为实线时
	 *                ，在属性map中不要添加线型属性
	 */
	public static String getBorderStyle(int index, FixedLength linewidth)
	{
		String result = "";
		String lengthx8 = getFixedLengthString(getNumbersOfLength(linewidth, 8d));
		String lengthx4 = getFixedLengthString(getNumbersOfLength(linewidth, 4d));
		String lengthx2 = getFixedLengthString(getNumbersOfLength(linewidth, 2d));
		switch (index)
		{
		case 1:
		{
			result = lengthx2 + "," + lengthx2;
			break;
		}
		case 2:
		{
			result = lengthx8 + "," + lengthx4 + "," + lengthx2 + ","
					+ lengthx4;
			break;
		}
		case 3:
		{
			result = lengthx8 + "," + lengthx4 + "," + lengthx2 + ","
					+ lengthx2 + "," + lengthx2 + "," + lengthx4;
			break;
		}
		}
		return result;
	}

	public static String getFixedLengthString(FixedLength length)
	{
		return length != null ? length.getLengthValueString()
				+ length.getUnits() : "";
	}
}
