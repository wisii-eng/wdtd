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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.avalon.framework.configuration.ConfigurationException;
import org.krysalis.barcode4j.BarcodeException;
import org.krysalis.barcode4j.BarcodeGenerator;
import org.krysalis.barcode4j.BarcodeUtil;
import org.krysalis.barcode4j.impl.code128.Code128;
import org.krysalis.barcode4j.impl.code128.EAN128;
import org.krysalis.barcode4j.impl.code39.Code39;
import org.krysalis.barcode4j.impl.int2of5.Interleaved2Of5;
import org.krysalis.barcode4j.impl.pdf417.PDF417;
import org.krysalis.barcode4j.impl.upcean.EAN13;
import org.krysalis.barcode4j.impl.upcean.EAN8;
import org.krysalis.barcode4j.impl.upcean.UPCA;
import org.krysalis.barcode4j.impl.upcean.UPCE;

import com.wisii.wisedoc.banding.io.NameSpace;
import com.wisii.wisedoc.document.BarCode;
import com.wisii.wisedoc.document.BarCodeText;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.ConditionItem;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.xsl.attribute.EnumPropertyWriter;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

public class InstreamForeignObjectWriter extends AbsElementWriter
{

	Map<Integer, Object> attributesMap;

	public InstreamForeignObjectWriter(CellElement element)
	{
		super(element);
		Attributes attributes = element.getAttributes();
		if (attributes != null)
		{
			attributesMap = ElementUtil.getCompleteAttributes(attributes
					.getAttributes(), foElement.getClass());
		}
	}

	public String write(CellElement element)
	{
		StringBuffer code = new StringBuffer();
		EnumProperty type = (EnumProperty) foElement
				.getAttribute(Constants.PR_BARCODE_TYPE);
		if (!((type != null) && (type.getEnum() == Constants.EN_PDF417) && (IoXslUtil
				.isStandard())))
		{
			code.append(beforeDeal());
			if (!(foElement instanceof com.wisii.wisedoc.document.Group))
			{
				code.append(getCode());
			}
			code.append(endDeal());
		}
		return code.toString();
	}

	public String getAttributes()
	{
		StringBuffer code = new StringBuffer();
		int key = Constants.PR_GRAPHIC_LAYER;
		if (attributesMap.containsKey(key))
		{
			code.append(getAttribute(key, attributemap.get(key)));
		}
		return code.toString();
	}

	@Override
	public String getElementName()
	{
		return FoXsltConstants.INSTREAM_FOREIGN_OBJECT;
	}

	@SuppressWarnings("unchecked")
	public String getChildCode()
	{
		StringBuffer code = new StringBuffer();
		String templateName = "";
		EnumProperty type = (EnumProperty) foElement
				.getAttribute(Constants.PR_BARCODE_TYPE);
		BarcodeUtil util = BarcodeUtil.getInstance();
		try
		{
			BarcodeGenerator gen = util.createBarcodeGenerator(BarCode
					.getConfiguration(BarCode.getBarCodeType(type)));
			if (gen instanceof EAN128)
			{
				templateName = FoXsltConstants.BARCODE_EAN;
			} else if (gen instanceof Code128)
			{
				templateName = FoXsltConstants.BARCODE_128;
			} else if (gen instanceof Code39)
			{
				templateName = FoXsltConstants.BARCODE_3OF9;
			} else if (gen instanceof Interleaved2Of5)
			{
				templateName = FoXsltConstants.BARCODE_2OF5I;
			}
			else if (gen instanceof EAN13)
			{
				templateName = FoXsltConstants.BARCODE_EAN;
			} else if (gen instanceof EAN8)
			{
				templateName = FoXsltConstants.BARCODE_EAN;
			} else if (gen instanceof UPCA)
			{
				templateName = FoXsltConstants.BARCODE_EAN;
			} else if (gen instanceof UPCE)
			{
				templateName = FoXsltConstants.BARCODE_EAN;
			} 
			else if (gen instanceof PDF417)
			{
				templateName = FoXsltConstants.BARCODE_417;
				NameSpace barcodeNameSpace = new NameSpace(
						FoXsltConstants.SPACENAMEBARCODE,
						FoXsltConstants.SPACEVALUEBARCODE);
				IoXslUtil.addNameSpace(barcodeNameSpace);
			}
			if (attributesMap != null
					&& attributesMap.containsKey(Constants.PR_DYNAMIC_STYLE))
			{

				List<ConditionItem> conditions = (List<ConditionItem>) attributesMap
						.get(Constants.PR_DYNAMIC_STYLE);
				if (conditions != null && conditions.size() > 0)
				{
					for (int i = 0; i < conditions.size(); i++)
					{
						if (i == 0)
						{
							code.append(ElementUtil
									.startElement(FoXsltConstants.CHOOSE));
						}
						ConditionItem currentitem = conditions.get(i);
						LogicalExpression condition = currentitem
								.getCondition();
						Map<Integer, Object> styles = currentitem.getStyles();
						if (condition != null)
						{
							code.append(ElementWriter.TAGBEGIN
									+ FoXsltConstants.WHEN + " ");
							code.append(ElementUtil.outputAttributes(
									FoXsltConstants.TEST, ElementUtil
											.returnStringIf(condition, true)));
							code.append(ElementWriter.TAGEND);
							Map<String, Object> barcodemap = new HashMap<String, Object>();
							barcodemap = getParammap(IoXslUtil.getReplaceMap(
									attributesMap, styles));
							code.append(ElementUtil.outputCalltemplate(
									templateName, barcodemap));
							code.append(ElementUtil
									.endElement(FoXsltConstants.WHEN));
						}
					}
					code.append(ElementUtil
							.startElement(FoXsltConstants.OTHERWISE));
					Map<String, Object> barcodeMap = new HashMap<String, Object>();
					barcodeMap = getParammap(attributesMap);
					code.append(ElementUtil.outputCalltemplate(templateName,
							barcodeMap));
					code.append(ElementUtil
							.endElement(FoXsltConstants.OTHERWISE));
					code.append(ElementUtil.endElement(FoXsltConstants.CHOOSE));
				}

			} else
			{
				Map<String, Object> barcodeMap = new HashMap<String, Object>();
				barcodeMap = getParammap(attributesMap);
				code.append(ElementUtil.outputCalltemplate(templateName,
						barcodeMap));
			}

		} catch (ConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BarcodeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		IoXslUtil.addFunctionTemplate(templateName);
		NameSpace fovNameSpace = new NameSpace(FoXsltConstants.SPACENAMEFOV,
				FoXsltConstants.COMWISIIFOV);
		NameSpace svgNameSpace = new NameSpace(FoXsltConstants.SPACENAMESVG,
				FoXsltConstants.SVG_NAMESPACE);
		NameSpace mathNameSpace = new NameSpace(FoXsltConstants.XMLNSMATH,
				FoXsltConstants.MATHNAMESPACE);
		NameSpace exmathNameSpace = new NameSpace(
				FoXsltConstants.EXCLUDERESULTPREFIXES, FoXsltConstants.MATH);
		IoXslUtil.addNameSpace(fovNameSpace);
		IoXslUtil.addNameSpace(svgNameSpace);
		IoXslUtil.addNameSpace(exmathNameSpace);
		IoXslUtil.addNameSpace(mathNameSpace);

		return code.toString();
	}

	public Map<String, Object> getParammap(Map<Integer, Object> mapbarcode)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		mapbarcode.remove(Constants.PR_CONTENT_HEIGHT);
		mapbarcode.remove(Constants.PR_CONTENT_WIDTH);
		mapbarcode.remove(Constants.PR_BARCODE_TYPE);
		Object[] keys = mapbarcode.keySet().toArray();
		int size = keys.length;
		for (int i = 0; i < size; i++)
		{
			int key = (Integer) keys[i];
			String param = "";
			if (attribute.containsKey(key))
			{
				param = attribute.get(key);
			}
			if (key == Constants.PR_BARCODE_SUBSET
					|| key == Constants.PR_BARCODE_MAKEUCC
					|| key == Constants.PR_BARCODE_PRINT_TEXT
					|| key == Constants.PR_BARCODE_CODE_TYPE)
			{
				String value = new EnumPropertyWriter().write(mapbarcode
						.get(key));
				map.put(param, value);
			} else if (key == Constants.PR_BARCODE_CONTENT)
			{

				BarCodeText content = (BarCodeText) mapbarcode.get(key);
				if (content != null)
				{
					if (content.isBindContent())
					{
						map.put(attribute.get(Constants.PR_BARCODE_VALUE),
								content.getBindNode());
					} else
					{
						map.put(attribute.get(Constants.PR_BARCODE_VALUE),
								content.getText());
					}
				} else
				{
					map.put(attribute.get(Constants.PR_BARCODE_VALUE),
							getString(BarCode.CODE39_LENGTH));
				}
			} else
			{
				Object value = mapbarcode.get(key);
				map.put(param, value);
			}
		}
		if (!mapbarcode.containsKey(Constants.PR_BARCODE_CONTENT))
		{
			map.put(attribute.get(Constants.PR_BARCODE_VALUE),
					getString(BarCode.CODE39_LENGTH));
		}
		return map;
	}

	private Map<Integer, String> attribute = new HashMap<Integer, String>();
	{
		attribute.put(Constants.PR_BARCODE_VALUE, FoXsltConstants.VALUE);
		attribute.put(Constants.PR_BARCODE_HEIGHT,
				FoXsltConstants.BARCODE_HEIGHT);
		attribute.put(Constants.PR_BARCODE_MODULE,
				FoXsltConstants.BARCODE_MODULE);
		attribute.put(Constants.PR_BARCODE_FONT_HEIGHT,
				FoXsltConstants.BARCODE_FONT_HEIGHT);
		attribute.put(Constants.PR_BARCODE_FONT_FAMILY,
				FoXsltConstants.BARCODE_FONT_FAMILY);
		attribute.put(Constants.PR_BARCODE_QUIET_HORIZONTAL,
				FoXsltConstants.BARCODE_QUIET_HORIZONTAL);
		attribute.put(Constants.PR_BARCODE_QUIET_VERTICAL,
				FoXsltConstants.BARCODE_QUIET_VERTICAL);
		attribute.put(Constants.PR_BARCODE_ORIENTATION,
				FoXsltConstants.BARCODE_ORIENTATION);
		attribute.put(Constants.PR_BARCODE_ADDCHECKSUM,
				FoXsltConstants.BARCODE_ADDCHECKSUM);
		attribute.put(Constants.PR_BARCODE_WIDE_TO_NARROW,
				FoXsltConstants.BARCODE_WIDE_TO_NARROW);
		attribute.put(Constants.PR_BARCODE_TEXT_CHAR_SPACE,
				FoXsltConstants.BARCODE_TEXT_CHAR_SPACE);
		attribute.put(Constants.PR_BARCODE_STRING,
				FoXsltConstants.BARCODE_STRING);
		attribute.put(Constants.PR_BARCODE_PRINT_TEXT,
				FoXsltConstants.BARCODE_PRINT_TEXT);
		attribute.put(Constants.PR_BARCODE_TEXT_BLOCK,
				FoXsltConstants.BARCODE_TEXT_BLOCK);
		attribute.put(Constants.PR_BARCODE_CODE_TYPE,
				FoXsltConstants.BARCODE_CODE_TYPE);
		attribute.put(Constants.PR_BARCODE_SUBSET,
				FoXsltConstants.BARCODE_SUBSET);
		attribute.put(Constants.PR_BARCODE_MAKEUCC,
				FoXsltConstants.BARCODE_MAKEUCC);
		attribute.put(Constants.PR_BARCODE_EC_LEVEL,
				FoXsltConstants.BARCODE_EC_LEVAEL);
		attribute.put(Constants.PR_BARCODE_COLUMNS,
				FoXsltConstants.BARCODE_COLUMNS);
		attribute.put(Constants.PR_BARCODE_MIN_COLUMNS,
				FoXsltConstants.BARCODE_MIN_COLUMNS);
		attribute.put(Constants.PR_BARCODE_MAX_COLUMNS,
				FoXsltConstants.BARCODE_MAX_COLUMNS);
		attribute.put(Constants.PR_BARCODE_MIN_ROWS,
				FoXsltConstants.BARCODE_MIN_ROWS);
		attribute.put(Constants.PR_BARCODE_MAX_ROWS,
				FoXsltConstants.BARCODE_MAX_ROWS);
	}

	public String getString(int length)
	{
		String text = "";
		if (length > 0)
		{
			for (int i = 0; i < length; i++)
			{
				if (i < 10)
				{
					text = text + i;
				} else
				{
					text = text + "1";
				}
			}
		}

		return text;
	}
}
