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
 * @BarCode.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfiguration;
import org.krysalis.barcode4j.BarcodeException;
import org.krysalis.barcode4j.BarcodeGenerator;
import org.krysalis.barcode4j.BarcodeUtil;
import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.impl.code128.Code128;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code128.EAN128;
import org.krysalis.barcode4j.impl.code128.EAN128Bean;
import org.krysalis.barcode4j.impl.code39.Code39;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.impl.int2of5.Interleaved2Of5;
import org.krysalis.barcode4j.impl.int2of5.Interleaved2Of5Bean;
import org.krysalis.barcode4j.impl.pdf417.PDF417;
import org.krysalis.barcode4j.impl.pdf417.PDF417Bean;
import org.krysalis.barcode4j.impl.upcean.EAN13;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.impl.upcean.EAN8;
import org.krysalis.barcode4j.impl.upcean.EAN8Bean;
import org.krysalis.barcode4j.impl.upcean.UPCA;
import org.krysalis.barcode4j.impl.upcean.UPCABean;
import org.krysalis.barcode4j.impl.upcean.UPCE;
import org.krysalis.barcode4j.impl.upcean.UPCEANBean;
import org.krysalis.barcode4j.output.BarcodeCanvasSetupException;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.output.svg.SVGCanvasProvider;
import org.krysalis.barcode4j.tools.MimeTypes;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.DefaultBindNode;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.util.WisedocUtil;

/**
 * 类功能描述：条码对象
 * 
 * 作者：zhangqiang 创建日期：2008-10-24
 */
public class BarCode extends AbstractGraphics
{

	private int intrinsicWidth = 0;

	private int intrinsicHeight = 0;

	private BufferedImage image;

	public final static int CODE39_LENGTH = 6;

	// private final int EAN8_LENGTH = 7;
	//
	// private final int UPCA_LENGTH = 12;
	//
	// private final int UPCE_LENGTH = 6;

	public BarCode()
	{
		initImageData();

	}

	public BarCode(final Map<Integer, Object> attributes)
	{
		super(attributes);
		initImageData();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.AbstractGraphics#getIntrinsicHeight()
	 */
	public int getIntrinsicHeight()
	{
		return intrinsicHeight;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.AbstractGraphics#getIntrinsicWidth()
	 */
	public int getIntrinsicWidth()
	{
		return intrinsicWidth;
	}

	public void setAttributes(Map<Integer, Object> atts, boolean isreplace)
	{
		super.setAttributes(atts, isreplace);
		initImageData();
	}

	private void initImageData()
	{
		BarcodeGenerator gen = setGen();
		Attributes attributes = getAttributes();
		Map<Integer, Object> atts = new HashMap<Integer, Object>();
		if (attributes != null)
		{
			atts = attributes.getAttributes();
		}
		EnumProperty type = atts.containsKey(Constants.PR_BARCODE_TYPE) ? (EnumProperty) atts.get(Constants.PR_BARCODE_TYPE)
				: null;
		BarCodeText content = atts == null ? null : (BarCodeText) atts
				.get(Constants.PR_BARCODE_CONTENT);
		int orientation = 0;
		if (atts.containsKey(Constants.PR_BARCODE_ORIENTATION)
				&& atts.get(Constants.PR_BARCODE_ORIENTATION) != null)
		{
			orientation = (Integer) atts
					.get(Constants.PR_BARCODE_ORIENTATION);
		}
		String format = "image/png";
		// int dpi = Toolkit.getDefaultToolkit().getScreenResolution();
		int dpi = 72;
		String text = "";
		if (content == null || (content != null && content.isBindContent()))
		{
			int length = DefaultBindNode.LENGTH;
			if (content != null)
			{
				length = content.getBindNode().getLength();
				if (length == BindNode.UNLIMT)
				{
					length = DefaultBindNode.LENGTH;
				}
			}
			if (length == DefaultBindNode.LENGTH)
			{
				int number = type == null ? Constants.EN_CODE128
						: (Integer) type.getEnum();
				switch (number)
				{
				case Constants.EN_CODE128:
				{
					text = "123456789";
					break;
				}
				case Constants.EN_CODE39:
				{
					text = "123456789";
					break;
				}
				case Constants.EN_EAN128:
				{
					text = "12345678912";
					break;
				}
				case Constants.EN_2OF5:
				{
					text = "123456789";
					break;
				}
				case Constants.EN_EAN13:
				{
					text = "012345678901";
					break;
				}
				case Constants.EN_EAN8:
				{
					text = "1234567";
					break;
				}
				case Constants.EN_UPCA:
				{
					text = "01234567890";
					break;
				}
				case Constants.EN_UPCE:
				{
					text = "0123456";
					break;
				}
				default:
				{
					text = "123456789";
					break;
				}
				}
			} else
			{
				text = getString(length);
			}
		} else
		{
			text = content.getText();
			if (text.equals(""))
			{
				text = getString(2);
			}
		}
		FileOutputStream out = null;
		try
		{
			if (MimeTypes.MIME_SVG.equals(format))
			{
				SVGCanvasProvider svg = null;
				try
				{
					svg = new SVGCanvasProvider(true, orientation);
				} catch (BarcodeCanvasSetupException e1)
				{
					e1.printStackTrace();
				}
				gen.generateBarcode(svg, text);
				TransformerFactory factory = TransformerFactory.newInstance();
				Transformer trans = null;
				try
				{
					trans = factory.newTransformer();
				} catch (TransformerConfigurationException e)
				{
					e.printStackTrace();
				}
				Source src = new javax.xml.transform.dom.DOMSource(svg
						.getDOMFragment());

				Result res = new javax.xml.transform.stream.StreamResult(out);
				trans.transform(src, res);

			} else
			{
				BitmapCanvasProvider bitmap;
				bitmap = new BitmapCanvasProvider(out, format, dpi,
						BufferedImage.TYPE_BYTE_GRAY, true, orientation);
				gen.generateBarcode(bitmap, text);
				bitmap.finish();
				BufferedImage imageBit = bitmap.getBufferedImage();
				setImage(imageBit);
				setIntrinsicHeight(imageBit.getHeight() * 1000);
				setIntrinsicWidth(imageBit.getWidth() * 1000);

				if (out != null)
				{
					out.close();
				}
			}

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		catch (TransformerException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static Configuration getConfiguration(String str)
	{
		DefaultConfiguration cfg = new DefaultConfiguration("cfg");
		DefaultConfiguration child = new DefaultConfiguration(str);
		cfg.addChild(child);
		return cfg;
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

	private BarcodeGenerator setGen()
	{
		BarcodeGenerator gen = null;
		EnumProperty type = (EnumProperty) getAttribute(Constants.PR_BARCODE_TYPE);
		BarcodeUtil util = BarcodeUtil.getInstance();
		Attributes _attributes = getAttributes();
		Map<Integer, Object> attMap = new HashMap<Integer, Object>();
		if (_attributes != null)
		{
			attMap = _attributes.getAttributes();
		}
		try
		{
			String barcodeType = getBarCodeType(type);
			if ("".equalsIgnoreCase(barcodeType)
					|| "code39".equalsIgnoreCase(barcodeType))
			{
				Code39 code39 = (Code39) util
						.createBarcodeGenerator(getConfiguration(barcodeType));
				Code39Bean code39bean = (Code39Bean) code39.getBean();
				code39bean = (Code39Bean) setPublic(code39bean, attMap);
				if (attMap.containsKey(Constants.PR_BARCODE_ADDCHECKSUM))
				{
					Object checkSum = attMap
							.get(Constants.PR_BARCODE_ADDCHECKSUM);
					if (checkSum instanceof Boolean)
					{
						Boolean bl = (Boolean) checkSum;
						code39bean.setDisplayChecksum(bl);
					}
				}
				if (attMap.containsKey(Constants.PR_BARCODE_WIDE_TO_NARROW))
				{
					double wideFactor = (Double) attMap
							.get(Constants.PR_BARCODE_WIDE_TO_NARROW);
					code39bean.setWideFactor(wideFactor);

				}
				if (attMap.containsKey(Constants.PR_BARCODE_TEXT_CHAR_SPACE))
				{
					FixedLength intercharGapWidth = (FixedLength) attMap
							.get(Constants.PR_BARCODE_TEXT_CHAR_SPACE);
					if (intercharGapWidth != null)
					{
						code39bean
								.setIntercharGapWidth(getMmDouble(intercharGapWidth));
					}
				}
				gen = code39;
//				code39bean.setMsgPosition(placement)
			} else if ("code128".equalsIgnoreCase(barcodeType))
			{
				Code128 code128 = (Code128) util
						.createBarcodeGenerator(getConfiguration(barcodeType));
				Code128Bean code128bean = (Code128Bean) code128.getBean();
				code128bean = (Code128Bean) setPublic(code128bean, attMap);
//				if (attMap.containsKey(Constants.PR_BARCODE_MAKEUCC))
//				{
//					Object makeUCC = attMap.get(Constants.PR_BARCODE_MAKEUCC);
//					code128bean.
//							.setMakeUCC(((EnumProperty) makeUCC).getEnum() == Constants.EN_TRUE);
//				}
				gen = code128;
			} else if ("ean128".equalsIgnoreCase(barcodeType))
			{
				EAN128 ean128 = (EAN128) util
						.createBarcodeGenerator(getConfiguration(barcodeType));
				EAN128Bean ean128bean = (EAN128Bean) ean128.getBean();
				ean128bean = (EAN128Bean) setPublic(ean128bean, attMap);
				gen = ean128;
			} else if ("ean13".equalsIgnoreCase(barcodeType))
			{
				EAN13 ean13 = (EAN13) util
						.createBarcodeGenerator(getConfiguration(barcodeType));
				EAN13Bean ean13bean = (EAN13Bean) ean13.getBean();
				ean13bean = (EAN13Bean) setPublic(ean13bean, attMap);

				gen = ean13;
			} else if ("ean8".equalsIgnoreCase(barcodeType))
			{
				EAN8 ean8 = (EAN8) util
						.createBarcodeGenerator(getConfiguration(barcodeType));
				EAN8Bean ean8bean = (EAN8Bean) ean8.getBean();
				ean8bean = (EAN8Bean) setPublic(ean8bean, attMap);
				gen = ean8;
			} else if ("upca".equalsIgnoreCase(barcodeType))
			{
				UPCA upca = (UPCA) util
						.createBarcodeGenerator(getConfiguration(barcodeType));
				UPCABean upcabean = (UPCABean) upca.getBean();
				upcabean = (UPCABean) setPublic(upcabean, attMap);
				gen = upca;
			} else if ("upce".equalsIgnoreCase(barcodeType))
			{
				UPCE upce = (UPCE) util
						.createBarcodeGenerator(getConfiguration(barcodeType));
				UPCEANBean upcebean = (UPCEANBean) upce.getBean();
				upcebean = (UPCEANBean) setPublic(upcebean, attMap);
				gen = upce;
			} else if ("2of5".equalsIgnoreCase(barcodeType))
			{
				Interleaved2Of5 code2of5 = (Interleaved2Of5) util
						.createBarcodeGenerator(getConfiguration(barcodeType));
				Interleaved2Of5Bean code2of5bean = (Interleaved2Of5Bean) code2of5
						.getBean();
				code2of5bean = (Interleaved2Of5Bean) setPublic(code2of5bean,
						attMap);
				if (attMap.containsKey(Constants.PR_BARCODE_ADDCHECKSUM))
				{
					Object checkSum = attMap
							.get(Constants.PR_BARCODE_ADDCHECKSUM);
					if (checkSum instanceof Boolean)
					{
						Boolean bl = (Boolean) checkSum;
						code2of5bean.setDisplayChecksum(bl);
					}
				}
				if (attMap.containsKey(Constants.PR_BARCODE_WIDE_TO_NARROW))
				{
					double wideFactor = (Double) attMap
							.get(Constants.PR_BARCODE_WIDE_TO_NARROW);
					code2of5bean.setWideFactor(wideFactor);
				}
				gen = code2of5;
			} else if ("pdf417".equalsIgnoreCase(barcodeType))
			{
				PDF417 pdf47 = (PDF417) util
						.createBarcodeGenerator(getConfiguration(barcodeType));
				PDF417Bean pdf47bean = (PDF417Bean) pdf47.getBean();
				pdf47bean = (PDF417Bean) setPublic(pdf47bean, attMap);
				if (attMap.containsKey(Constants.PR_BARCODE_QUIET_VERTICAL))
				{
					FixedLength quietZoneVertical = (FixedLength) attMap
							.get(Constants.PR_BARCODE_QUIET_VERTICAL);
					if (quietZoneVertical != null)
					{
						pdf47bean
								.setVerticalQuietZone(getMmDouble(quietZoneVertical));
					}
				}
				gen = pdf47;
			}

		} catch (ConfigurationException e)
		{
			e.printStackTrace();
		} catch (BarcodeException e)
		{
			e.printStackTrace();
		}

		return gen;
	}

	public BufferedImage getImage()
	{
		return image;
	}

	private void setImage(BufferedImage image)
	{
		this.image = image;
	}

	private void setIntrinsicWidth(int intrinsicWidth)
	{
		this.intrinsicWidth = intrinsicWidth;
	}

	private void setIntrinsicHeight(int intrinsicHeight)
	{
		this.intrinsicHeight = intrinsicHeight;
	}

	public static String getBarCodeType(EnumProperty type)
	{
		String text = "";
		int number = (Integer) type.getEnum();
		switch (number)
		{
		case Constants.EN_CODE128:
		{
			text = "code128";
			break;
		}
		case Constants.EN_CODE39:
		{
			text = "code39";
			break;
		}
		case Constants.EN_EAN128:
		{
			text = "ean128";
			break;
		}
		case Constants.EN_2OF5:
		{
			text = "2of5";
			break;
		}
		case Constants.EN_UPCE:
		{
			text = "upce";
			break;
		}
		case Constants.EN_UPCA:
		{
			text = "upca";
			break;
		}
		case Constants.EN_EAN8:
		{
			text = "ean8";
			break;
		}
		case Constants.EN_EAN13:
		{
			text = "ean13";
			break;
		}
		case Constants.EN_PDF417:
		{
			text = "pdf417";
		}
		}
		return text;
	}

	private AbstractBarcodeBean setPublic(AbstractBarcodeBean bean,
			Map<Integer, Object> attMap)
	{
		if (attMap.containsKey(Constants.PR_BARCODE_HEIGHT))
		{
			FixedLength height = (FixedLength) attMap
					.get(Constants.PR_BARCODE_HEIGHT);
			if (height != null)
			{
				bean.setBarHeight(getMmDouble(height));
			}
		}
		if (attMap.containsKey(Constants.PR_BARCODE_MODULE))
		{
			FixedLength moduleWidth = (FixedLength) attMap
					.get(Constants.PR_BARCODE_MODULE);
			if (moduleWidth != null)
			{
				bean.setModuleWidth(getMmDouble(moduleWidth));
			}
		}
		if (attMap.containsKey(Constants.PR_BARCODE_FONT_HEIGHT))
		{
			FixedLength fontSize = (FixedLength) attMap
					.get(Constants.PR_BARCODE_FONT_HEIGHT);
			if (fontSize != null)
			{
				bean.setFontSize(getMmDouble(fontSize));
			}
		}
		if (attMap.containsKey(Constants.PR_BARCODE_FONT_FAMILY))
		{

			bean.setFontName(attMap.get(Constants.PR_BARCODE_FONT_FAMILY)
					.toString());
		}
		if (attMap.containsKey(Constants.PR_BARCODE_QUIET_HORIZONTAL))
		{
			FixedLength quietZone = (FixedLength) attMap
					.get(Constants.PR_BARCODE_QUIET_HORIZONTAL);
			if (quietZone != null)
			{
				bean.setQuietZone(getMmDouble(quietZone));
			}
		}
		if (attMap.containsKey(Constants.PR_BARCODE_QUIET_VERTICAL))
		{
			FixedLength quietZoneVertical = (FixedLength) attMap
					.get(Constants.PR_BARCODE_QUIET_VERTICAL);
			if (quietZoneVertical != null)
			{
				bean.setVerticalQuietZone(getMmDouble(quietZoneVertical));
			}
		}
		if (attMap.containsKey(Constants.PR_BARCODE_PRINT_TEXT))
		{

			EnumProperty printTextenum = (EnumProperty) attMap
					.get(Constants.PR_BARCODE_PRINT_TEXT);
			boolean printText = printTextenum != null
					&& printTextenum.getEnum() == Constants.EN_TRUE;
			if (!printText)
			{
				bean.setFontSize(0);
			}
		}
		if (attMap.containsKey(Constants.PR_BARCODE_STRING))
		{
			Object content = attMap == null ? null : attMap
					.get(Constants.PR_BARCODE_STRING);
			if (content instanceof BarCodeText && ((BarCodeText)content).isBindContent())
			{
				bean.setPattern(content.toString() + "+d");
			} else if (content != null)
			{
				bean.setPattern(content.toString());
			}
		}/* else
		{
			BarCodeText content = attMap == null ? null : (BarCodeText) attMap
					.get(Constants.PR_BARCODE_CONTENT);
			if (content != null && content.isBindContent())
			{
				bean.setPattern(content.toString());
			}
		}*/
		return bean;
	}

	public Double getMmDouble(FixedLength length)
	{
		return WisedocUtil.convertMPTToMM(length, "mm");
	}

	public Element clone()
	{
		BarCode barcode = (BarCode) super.clone();
		barcode.initImageData();
		return barcode;
	}
}
