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
 * @WisedocUtil.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.JComponent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.configure.ConfigureUtil;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.StaticContent;
import com.wisii.wisedoc.document.attribute.ConditionalPageMasterReference;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.Region;
import com.wisii.wisedoc.document.attribute.RegionAfter;
import com.wisii.wisedoc.document.attribute.RegionBefore;
import com.wisii.wisedoc.document.attribute.RegionEnd;
import com.wisii.wisedoc.document.attribute.RegionStart;
import com.wisii.wisedoc.document.attribute.RepeatablePageMasterAlternatives;
import com.wisii.wisedoc.document.attribute.RepeatablePageMasterReference;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.document.attribute.SinglePageMasterReference;
import com.wisii.wisedoc.document.attribute.SubSequenceSpecifier;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.fonts.FontInfo;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.render.java2d.FontSetup;

/**
 * 类功能描述：工具类，提供通用功能处理方法，如：判断一个字符串是否由数字组成
 * 
 * 作者：李晓光 创建日期：2008-8-18
 */
public final class WisedocUtil
{

	/**
	 * 
	 * 判断指定的字符串是否Null或者""
	 * 
	 * @param t
	 *            指定被判断的字符串
	 * @return boolean 如果t==null||t=="":True 否则：False
	 */
	public final static boolean isEmpty(final String t)
	{
		return (t == null) || ("".equalsIgnoreCase(t));
	}

	/**
	 * 
	 * 判断指定的对象是否为Null
	 * 
	 * @param o
	 *            指定被判断的对象
	 * @return boolean 如果o==null：True否则：False
	 */
	public final static boolean isNull(final Object o)
	{
		return (o == null);
	}

	/**
	 * 判断一个指定字符串是否有数字组成
	 * 
	 * @param txt
	 *            指定要检查的字符串
	 * @return 由数字组成：True 否则：False
	 */
	public static final boolean isNumbers(final String txt)
	{
		if (txt == null || txt.length() == 0)
		{
			return false;
		}

		return txt.matches("\\d{1,}");
	}

	/**
	 * 
	 * 把指定的字符串解析为int
	 * 
	 * @param s
	 *            指定要解析的字符串
	 * @return int 返回整数
	 */
	public final static int getInt(final String s)
	{
		if (isEmpty(s))
		{
			LogUtil.warn("给定的字符串信息为空");
			return 0;
		}
		try
		{
			return Integer.parseInt(s.trim());
		} catch (final Exception e)
		{
			// LOG
			// LogUtil.warn(e.getMessage());
			return 0;
		}
	}

	/**
	 * 
	 * 把指定的字符串解析为Double
	 * 
	 * @param s
	 *            指定要解析的字符串
	 * @return double 返回Double浮点数
	 */
	public final static Double getDouble(final String s)
	{
		if (isEmpty(s))
		{
			LogUtil.warn("给定的字符串信息为空");
			return 0D;
		}
		try
		{
			return Double.parseDouble(s.trim());
		} catch (final Exception e)
		{
			// LOG
			// LogUtil.warn(e.getMessage());
			return 0D;
		}
	}

	/**
	 * 
	 * 使得显示控件显示在屏幕的正中央
	 * 
	 * @param Component
	 *            将要显示在屏幕中央的组件
	 * @return void 无返回值
	 */
	public static void centerOnScreen(final Component aComponent)
	{
		if (aComponent == null)
		{
			return;
		}
		final Dimension screenSize = Toolkit.getDefaultToolkit()
				.getScreenSize();
		final Dimension compSize = aComponent.getSize();

		compSize.width = Math.min(screenSize.width, compSize.width);
		compSize.height = Math.min(screenSize.height, compSize.height);

		aComponent.setSize(compSize);
		aComponent.setLocation((screenSize.width - compSize.width) / 2,
				(screenSize.height - compSize.height) / 2);
	}

	/**
	 * 
	 * {将配置文件读取到Reader流中}
	 * 
	 * @param fileName
	 *            指定菜单栏配置ws FileNotFoundException
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static Reader getXmlReader(final String fileName)
			throws FileNotFoundException
	{
		Reader reader = null;
		if (fileName == null || fileName == "")
		{
			throw new FileNotFoundException(fileName);
		}
		final InputStream in = WisedocUtil.class.getClassLoader()
				.getResourceAsStream(fileName);

		if (in != null)
		{
			try
			{
				reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			} catch (final UnsupportedEncodingException e)
			{
				// LOG
				// LogUtil.errorException(e.getMessage(), e);
			}
		}

		return reader;
	}

	/**
	 * 
	 * 根据指定的类名，返回相应的对象。
	 * 
	 * @param className
	 *            指定加载的类的名字
	 * @return Object 返回对象
	 * @exception 创建类出现异常时
	 *                ，抛出此对象。
	 */
	@SuppressWarnings("unchecked")
	public static Object newInstance(final String className)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException
	{
		Object action = null;
		if (isEmpty(className))
		{
			throw new ClassNotFoundException("");
		}

		try
		{
			final Class temp = Class.forName(className);

			if (!isNull(temp))
			{
				action = temp.newInstance();
			}

		} catch (final InstantiationException e)
		{
			throw e;
		} catch (final IllegalAccessException e)
		{
			throw e;
		}
		return action;
	}
	/**
	 * 
	 * 根据指定的Class，返回相应的对象。
	 * 
	 * @param class
	 *            指定加载的类
	 * @return Object 返回对象
	 * @exception 创建类出现异常时
	 *                ，抛出此对象。
	 */
	@SuppressWarnings("unchecked")
	public static Object newInstance(final Class classs)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException
	{
		Object object = null;
		if (classs == null)
		{
			return null;
		}

		try
		{
			object = classs.newInstance();

		} catch (final InstantiationException e)
		{
			throw e;
		} catch (final IllegalAccessException e)
		{
			throw e;
		}
		return object;
	}

	/**
	 * 
	 * 返回xml文件的根节点
	 * 
	 * @param reader
	 *            xml文件读取流
	 * @return Element xml文件的根元素
	 */
	public static Element getXmlElement(final Reader reader)
	{
		Element root = null;
		final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder = null;
		try
		{
			documentBuilder = documentBuilderFactory.newDocumentBuilder();

		} catch (final ParserConfigurationException e)
		{
			// e.printStackTrace();
			return root;
		}
		try
		{
			final Document document = documentBuilder.parse(new InputSource(
					reader));

			root = document.getDocumentElement();
		} catch (final SAXException e)
		{
			// e.printStackTrace();
		} catch (final IOException e)
		{
			// e.printStackTrace();
		}
		return root;
	}

	/**
	 * 
	 * 判断指定的File是否可写
	 * 
	 * @param file
	 *            指定被检查的文件
	 * @return boolean 可写：true 否则：false modify by csy
	 */
	public static boolean canWrite(final File file)
	{
		boolean flag = false;
		if (file == null)
		{
			flag = false;
		} else if (file.exists() && !file.canWrite())
		{
			flag = false;

		} else
		{
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * 对源字符串坐特殊处理，以生成符合XMl标准得字符串 替换掉里面得特殊字符（“&”，“<”等五个特殊字符）
	 * 
	 * @param src
	 *            ：要转换得字符串
	 * @return 符合XMl要求得字符串
	 * @exception
	 */
	public static String getXmlText(final String src)
	{
		String value = null;
		if (src != null)
		{
			value = src.replaceAll("&", "&amp;");
			value = value.replaceAll("<", "&lt;");
			value = value.replaceAll(">", "&gt;");
			value = value.replaceAll("'", "&apos;");
			value = value.replaceAll("\"", "&quot;");
		}
		return value;
	}

	/**
	 * 
	 * 从xml字符串转成内部字符串
	 * 
	 * @param src
	 *            ：要转换得字符串
	 * @return 符合XMl要求得字符串
	 * @exception
	 */
	public static String fromXmlText(final String src)
	{
		String value = null;
		if (src != null)
		{
			value = src.replaceAll("&amp;", "&");
			value = value.replaceAll("&lt;", "<");
			value = value.replaceAll("&gt;", ">");
			value = value.replaceAll("&apos;", "'");
			value = value.replaceAll("&quot;", "\"");
		}
		return value;
	}

	/**
	 * 根据毫米长度单位值获得像素
	 * 
	 * @param length
	 *            :毫米单位值
	 * @return
	 * @exception
	 */
	public static double getPointofmm(final double length)
	{
		return length * WiseDocConstants.POINTOFIN / 25.4;
	}

	/**
	 * 根据毫米长度单位值获得像素
	 * 
	 * @param length
	 *            :毫米单位值
	 * @return
	 * @exception
	 */
	public static int getMm(final int length, final String unit)
	{
		int number = length;
		if ("m".equalsIgnoreCase(unit))
		{
			number = length * 1000;
		} else if ("dm".equalsIgnoreCase(unit))
		{
			number = length * 100;
		} else if ("cm".equalsIgnoreCase(unit))
		{
			number = length * 10;
		} else if ("in".equalsIgnoreCase(unit))
		{
			final Number d = length * 25.4;
			number = d.intValue();
		} else if ("pt".equalsIgnoreCase(unit))
		{
			final Number d = length * 25.4 / 72;
			number = d.intValue();
		} else if ("mpt".equalsIgnoreCase(unit))
		{
			final Number d = length * 25.4 / 72000;
			number = d.intValue();
		}
		return number;
	}

	/**
	 * 
	 * 根据内部的mpt长度值返回指定单位的长度值
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public static FixedLength convertMPTToUnitLength(FixedLength length,
			String unit)
	{
		double mpt = length.getValue();
		double dvalue = 0.000d;
		mpt = mpt / 1000;
		if (unit.equals("in"))
		{
			dvalue = mpt / 72;
		} else if (unit.equals("cm"))
		{
			dvalue = mpt / 28.3464567;
		} else if (unit.equals("mm"))
		{
			dvalue = mpt / 2.83464567;
		} else if (unit.equals("pt"))
		{
		} else if (unit.equals("mpt"))
		{
			mpt = mpt * 1000;
		} else if (unit.equals("pc"))
		{
			dvalue = mpt / 12;
			/*
			 * } else if (unit.equals("em")) { dvalue = dvalue fontsize;
			 */
		} else if (unit.equals("px"))
		{
			// TODO: get resolution from user agent?
			dvalue = mpt;
		} else
		{
			unit = "pt";
		}
		return new FixedLength(dvalue, unit);

	}

	public static double convertMPTToMM(FixedLength length, String unit)
	{
		double mpt = length.getValue();
		double dvalue = 0.000d;
		mpt = mpt / 1000;
		dvalue = mpt / 2.83464567;
		NumberFormat nf = new DecimalFormat();
		nf.setMaximumFractionDigits(3);
		String v = nf.format(dvalue);
		return new Double(v).doubleValue();
	}

	/**
	 * 
	 * 根据内部的mpt长度值返回系统配置单位的长度值
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public static double convertMPTToLength(double mpt)
	{

		// mpt = mpt;
		double dvalue = mpt;
		String unit = ConfigureUtil.getUnit();
		if (unit.equals("in"))
		{
			dvalue = mpt / 72;
		} else if (unit.equals("cm"))
		{
			dvalue = mpt / 28.3464567;
		} else if (unit.equals("mm"))
		{
			dvalue = mpt / 2.83464567;
		} else if (unit.equals("pt"))
		{

		} else if (unit.equals("mpt"))
		{
			dvalue = mpt * 1000;
		} else if (unit.equals("pc"))
		{
			dvalue = mpt / 12;
			/*
			 * } else if (unit.equals("em")) { dvalue = dvalue fontsize;
			 */
		} else if (unit.equals("px"))
		{
			// TODO: get resolution from user agent?
			dvalue = mpt;
		} else
		{
			unit = "pt";
		}
		return dvalue;

	}

	/**
	 * 把指定的矩形数据信息，转换为当前单位下的矩形信息。 系统中统一采用mpt单位，在界面中可能用cm等，这里的
	 * 操作就是把mpt数据转换为cm单位下的数据。
	 * 
	 * @param x
	 *            指定矩形的横坐标。
	 * @param y
	 *            指定矩形的纵坐标。
	 * @param w
	 *            指定矩形的宽度。
	 * @param h
	 *            指定矩形的高度。
	 * @return {@link Rectangle2D} 返回计算后的矩形
	 */
	public static Rectangle2D convertMPT2Length(final double x, final double y,
			final double w, final double h)
	{
		return convertMPT2Length(new Rectangle2D.Double(x, y, w, h));
	}

	/**
	 * 把指定的矩形数据信息，转换为当前单位下的矩形信息。 系统中统一采用mpt单位，在界面中可能用cm等，这里的
	 * 操作就是把mpt数据转换为cm单位下的数据。
	 * 
	 * @param r
	 *            指定要进行转换的矩形。
	 * @return {@link Rectangle2D} 返回计算后的矩形
	 */
	public static Rectangle2D convertMPT2Length(final Rectangle2D r)
	{
		if (isNull(r))
		{
			return r;
		}
		final double x = convertMPTToLength(r.getX()), y = convertMPTToLength(r
				.getY()), w = convertMPTToLength(r.getWidth()), h = convertMPTToLength(r
				.getHeight());

		r.setRect(x, y, w, h);
		return r;
	}

	public static FontInfo getFontInfo()
	{
		return FONTINFO;
	}

	private static FontInfo FONTINFO = new FontInfo();
	static
	{
		final BufferedImage fontImage = new BufferedImage(100, 100,
				BufferedImage.TYPE_INT_RGB);
		final Graphics2D g = fontImage.createGraphics();
		// The next line is important to get accurate font metrics!
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
				RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		FontSetup.setup(FONTINFO, g);
	}

	/**
	 * 
	 * 去除Windows中文本保存时文件中多出的几个编码标识字节
	 * 
	 * @param in
	 *            ：转换前的流
	 * @return InputStream：去掉多余的编码表示字节后的流
	 * @exception
	 */
	public static InputStream coverInputStream(final InputStream in)
	{
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final byte[] utf8 = new byte[3];
		utf8[0] = -17;
		utf8[1] = -69;
		utf8[2] = -65;
		final byte[] bs = new byte[3];
		try
		{
			if (in.read(bs) != -1)
			{
				// Unicode、Unicode big endian和UTF-8编码的txt文件的开头会多出几个字节，
				// 分别是FF、FE（Unicode）,FE、FF（Unicode big endian）,EF、BB、BF（UTF-8
				if (bs[0] != utf8[0] || bs[1] != utf8[1] || bs[2] != utf8[2])
				{
					out.write(bs);
					// in.reset();
				}
			}
			int r = in.read();
			while (r != -1)
			{
				out.write(r);
				r = in.read();
			}
			out.flush();
			in.close();
		} catch (final IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

	public static ConditionalPageMasterReference getConditionalPageMasterReference(
			Object pam, int flg)
	{
		ConditionalPageMasterReference check = null;
		if (pam instanceof PageSequenceMaster)
		{
			PageSequenceMaster psm = (PageSequenceMaster) pam;
			List<SubSequenceSpecifier> subSequenceSpecifiers = psm
					.getSubsequenceSpecifiers();
			if (subSequenceSpecifiers != null
					&& !subSequenceSpecifiers.isEmpty()
					&& subSequenceSpecifiers.size() == 1
					&& subSequenceSpecifiers.get(0) instanceof RepeatablePageMasterAlternatives)
			{
				RepeatablePageMasterAlternatives rpm = (RepeatablePageMasterAlternatives) subSequenceSpecifiers
						.get(0);
				List<ConditionalPageMasterReference> conditionlist = rpm
						.getPageMasterRefs();
				if (conditionlist != null && !conditionlist.isEmpty())
				{
					for (ConditionalPageMasterReference current : conditionlist)
					{
						if (flg == 1)
						{
							int currentflg = current.getPagePosition();
							if (currentflg == Constants.EN_FIRST)
							{
								return current;
							}
						} else if (flg == 2)
						{
							int currentflg = current.getPagePosition();
							if (currentflg == Constants.EN_LAST)
							{
								return current;
							}
						} else if (flg == 3)
						{
							int currentflg = current.getOddOrEven();
							if (currentflg == Constants.EN_ODD)
							{
								return current;
							}
						} else if (flg == 4)
						{
							int currentflg = current.getOddOrEven();
							if (currentflg == Constants.EN_EVEN)
							{
								return current;
							}
						} else if (flg == 5)
						{
							int currentflg = current.getPagePosition();
							if (currentflg == Constants.EN_REST)
							{
								return current;
							}
						}
					}
				}
			}
		}
		return check;
	}

	public static boolean getIsCheck(Object pam, int flg)
	{
		boolean check = false;
		if (pam instanceof PageSequenceMaster)
		{
			PageSequenceMaster psm = (PageSequenceMaster) pam;
			List<SubSequenceSpecifier> subSequenceSpecifiers = psm
					.getSubsequenceSpecifiers();
			if (subSequenceSpecifiers != null
					&& !subSequenceSpecifiers.isEmpty()
					&& subSequenceSpecifiers.size() == 1
					&& subSequenceSpecifiers.get(0) instanceof RepeatablePageMasterAlternatives)
			{
				RepeatablePageMasterAlternatives rpm = (RepeatablePageMasterAlternatives) subSequenceSpecifiers
						.get(0);
				List<ConditionalPageMasterReference> conditionlist = rpm
						.getPageMasterRefs();
				if (conditionlist != null && !conditionlist.isEmpty())
				{
					Map<Integer, Boolean> checkmap = new HashMap<Integer, Boolean>();
					for (ConditionalPageMasterReference current : conditionlist)
					{
						int currentflgp = current.getPagePosition();
						int currentflgoe = current.getOddOrEven();
						int realflg = currentflgp == Constants.EN_ANY ? currentflgoe
								: currentflgp;
						if (realflg == Constants.EN_FIRST)
						{
							checkmap.put(0, true);
						} else if (realflg == Constants.EN_REST)
						{
							checkmap.put(4, true);
						} else if (realflg == Constants.EN_LAST)
						{
							checkmap.put(1, true);
						} else if (realflg == Constants.EN_ODD)
						{
							checkmap.put(2, true);
						} else if (realflg == Constants.EN_EVEN)
						{
							checkmap.put(3, true);
						} else if (realflg == Constants.EN_ONLY)
						{
							checkmap.put(5, true);
						}
					}
					int size = checkmap.size();
					if (size == 2)
					{
						if (flg == 1)
						{
							return checkmap.containsKey(0)
									&& checkmap.containsKey(4);
						} else if (flg == 2)
						{
							return checkmap.containsKey(1)
									&& checkmap.containsKey(4);
						} else if (flg == 3)
						{
							return checkmap.containsKey(2)
									&& checkmap.containsKey(3);
						}
					} else if (size > 2)
					{
						if (flg == 1)
						{
							return checkmap.containsKey(0);
						} else if (flg == 2)
						{
							return checkmap.containsKey(1);
						} else if (flg == 3)
						{
							return checkmap.containsKey(2)
									&& checkmap.containsKey(3);
						}
					}
				}
			}
		}
		return check;
	}

	public static Object getPageSequenceMaster(Object pam, boolean ischeck,
			int flg)
	{
		Object result = null;
		int any = Constants.EN_ANY;
		if (pam instanceof SimplePageMaster)
		{
			if (ischeck)
			{
				int pagePosition = any;
				int oddOrEven = any;
				int blankOrNotBlank = any;
				if (flg == 1)
				{
					ConditionalPageMasterReference first = new ConditionalPageMasterReference(
							(SimplePageMaster) pam, Constants.EN_FIRST,
							oddOrEven, blankOrNotBlank);
					SimplePageMaster restspm = ((SimplePageMaster) pam).clone();
					ConditionalPageMasterReference rest = new ConditionalPageMasterReference(
							restspm, Constants.EN_REST, oddOrEven,
							blankOrNotBlank);
					List<ConditionalPageMasterReference> conditionlist = new ArrayList<ConditionalPageMasterReference>();
					conditionlist.add(first);
					conditionlist.add(rest);
					RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
							new EnumNumber(
									RepeatablePageMasterAlternatives.INFINITE,
									0), conditionlist);
					List<SubSequenceSpecifier> subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
					subSequenceSpecifiers.add(rpm);
					PageSequenceMaster psm = new PageSequenceMaster(
							subSequenceSpecifiers);
					result = psm;
				} else if (flg == 2)
				{
					ConditionalPageMasterReference rest = new ConditionalPageMasterReference(
							(SimplePageMaster) pam, Constants.EN_REST,
							oddOrEven, blankOrNotBlank);
					SimplePageMaster restspm = ((SimplePageMaster) pam).clone();
					ConditionalPageMasterReference last = new ConditionalPageMasterReference(
							restspm, Constants.EN_LAST, oddOrEven,
							blankOrNotBlank);
					List<ConditionalPageMasterReference> conditionlist = new ArrayList<ConditionalPageMasterReference>();
					conditionlist.add(last);
					conditionlist.add(rest);
					RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
							new EnumNumber(
									RepeatablePageMasterAlternatives.INFINITE,
									0), conditionlist);
					List<SubSequenceSpecifier> subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
					subSequenceSpecifiers.add(rpm);
					PageSequenceMaster psm = new PageSequenceMaster(
							subSequenceSpecifiers);
					result = psm;
				} else if (flg == 3)
				{
					SimplePageMaster restspm = ((SimplePageMaster) pam).clone();
					ConditionalPageMasterReference odd = new ConditionalPageMasterReference(
							(SimplePageMaster) pam, pagePosition,
							Constants.EN_ODD, blankOrNotBlank);
					ConditionalPageMasterReference even = new ConditionalPageMasterReference(
							restspm, pagePosition, Constants.EN_EVEN,
							blankOrNotBlank);
					List<ConditionalPageMasterReference> conditionlist = new ArrayList<ConditionalPageMasterReference>();
					conditionlist.add(odd);
					conditionlist.add(even);
					RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
							new EnumNumber(
									RepeatablePageMasterAlternatives.INFINITE,
									0), conditionlist);
					List<SubSequenceSpecifier> subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
					subSequenceSpecifiers.add(rpm);
					PageSequenceMaster psm = new PageSequenceMaster(
							subSequenceSpecifiers);
					result = psm;
				}
			} else
			{
				result = pam;
			}
		} else if (pam instanceof PageSequenceMaster)
		{
			boolean firstrest = getIsCheck(pam, 1);
			boolean restlast = getIsCheck(pam, 2);
			boolean oddeven = getIsCheck(pam, 3);
			if (firstrest || restlast || oddeven)
			{
				if (flg == 1)
				{
					if (!ischeck)
					{
						if (restlast)
						{
							if (oddeven)
							{
								ConditionalPageMasterReference first = getConditionalPageMasterReference(
										pam, 1);
								ConditionalPageMasterReference last = getConditionalPageMasterReference(
										pam, 2);
								ConditionalPageMasterReference even = getConditionalPageMasterReference(
										pam, 4);
								SimplePageMaster sipfirst = first
										.getMasterReference();
								ConditionalPageMasterReference newodd = new ConditionalPageMasterReference(
										sipfirst, any, Constants.EN_ODD, any);

								List<ConditionalPageMasterReference> conditionlist = new ArrayList<ConditionalPageMasterReference>();
								conditionlist.add(last);
								conditionlist.add(newodd);
								conditionlist.add(even);
								List<SubSequenceSpecifier> subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
								RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
										new EnumNumber(-1, 0), conditionlist);
								subSequenceSpecifiers.add(rpm);
								PageSequenceMaster psm = new PageSequenceMaster(
										subSequenceSpecifiers);
								result = psm;
							} else
							{
								ConditionalPageMasterReference first = getConditionalPageMasterReference(
										pam, 1);
								ConditionalPageMasterReference last = getConditionalPageMasterReference(
										pam, 2);

								SimplePageMaster sipfirst = first
										.getMasterReference();

								ConditionalPageMasterReference rest = new ConditionalPageMasterReference(
										sipfirst, Constants.EN_REST, any, any);

								List<ConditionalPageMasterReference> conditionlist = new ArrayList<ConditionalPageMasterReference>();
								conditionlist.add(last);
								conditionlist.add(rest);
								List<SubSequenceSpecifier> subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
								RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
										new EnumNumber(-1, 0), conditionlist);
								subSequenceSpecifiers.add(rpm);
								PageSequenceMaster psm = new PageSequenceMaster(
										subSequenceSpecifiers);
								result = psm;
							}
						} else
						{
							if (oddeven)
							{
								ConditionalPageMasterReference first = getConditionalPageMasterReference(
										pam, 1);
								ConditionalPageMasterReference even = getConditionalPageMasterReference(
										pam, 4);
								SimplePageMaster sipfirst = first
										.getMasterReference();
								ConditionalPageMasterReference newodd = new ConditionalPageMasterReference(
										sipfirst, any, Constants.EN_ODD, any);

								List<ConditionalPageMasterReference> conditionlist = new ArrayList<ConditionalPageMasterReference>();
								conditionlist.add(newodd);
								conditionlist.add(even);
								List<SubSequenceSpecifier> subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
								RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
										new EnumNumber(-1, 0), conditionlist);
								subSequenceSpecifiers.add(rpm);
								PageSequenceMaster psm = new PageSequenceMaster(
										subSequenceSpecifiers);
								result = psm;
							} else
							{
								ConditionalPageMasterReference first = getConditionalPageMasterReference(
										pam, 1);
								SimplePageMaster sipfirst = first
										.getMasterReference();
								result = sipfirst;
							}
						}
					} else
					{
						if (restlast)
						{
							if (oddeven)
							{
								ConditionalPageMasterReference last = getConditionalPageMasterReference(
										pam, 2);
								ConditionalPageMasterReference odd = getConditionalPageMasterReference(
										pam, 3);
								ConditionalPageMasterReference even = getConditionalPageMasterReference(
										pam, 4);
								SimplePageMaster sipfirst = odd
										.getMasterReference().clone();
								ConditionalPageMasterReference first = new ConditionalPageMasterReference(
										sipfirst, Constants.EN_FIRST, any, any);

								List<ConditionalPageMasterReference> conditionlist = new ArrayList<ConditionalPageMasterReference>();
								conditionlist.add(first);
								conditionlist.add(last);
								conditionlist.add(odd);
								conditionlist.add(even);
								List<SubSequenceSpecifier> subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
								RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
										new EnumNumber(-1, 0), conditionlist);
								subSequenceSpecifiers.add(rpm);
								PageSequenceMaster psm = new PageSequenceMaster(
										subSequenceSpecifiers);
								result = psm;
							} else
							{
								ConditionalPageMasterReference last = getConditionalPageMasterReference(
										pam, 2);
								ConditionalPageMasterReference rest = getConditionalPageMasterReference(
										pam, 5);
								SimplePageMaster sipfirst = rest
										.getMasterReference().clone();
								ConditionalPageMasterReference first = new ConditionalPageMasterReference(
										sipfirst, Constants.EN_FIRST, any, any);
								List<ConditionalPageMasterReference> conditionlist = new ArrayList<ConditionalPageMasterReference>();
								conditionlist.add(first);
								conditionlist.add(last);
								conditionlist.add(rest);
								List<SubSequenceSpecifier> subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
								RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
										new EnumNumber(-1, 0), conditionlist);
								subSequenceSpecifiers.add(rpm);
								PageSequenceMaster psm = new PageSequenceMaster(
										subSequenceSpecifiers);
								result = psm;
							}
						} else
						{
							ConditionalPageMasterReference odd = getConditionalPageMasterReference(
									pam, 3);
							ConditionalPageMasterReference even = getConditionalPageMasterReference(
									pam, 4);
							SimplePageMaster sipfirst = odd
									.getMasterReference().clone();
							ConditionalPageMasterReference first = new ConditionalPageMasterReference(
									sipfirst, Constants.EN_FIRST, any, any);

							List<ConditionalPageMasterReference> conditionlist = new ArrayList<ConditionalPageMasterReference>();
							conditionlist.add(first);
							conditionlist.add(odd);
							conditionlist.add(even);
							List<SubSequenceSpecifier> subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
							RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
									new EnumNumber(-1, 0), conditionlist);
							subSequenceSpecifiers.add(rpm);
							PageSequenceMaster psm = new PageSequenceMaster(
									subSequenceSpecifiers);
							result = psm;
						}
					}
				} else if (flg == 2)
				{
					if (!ischeck)
					{
						if (firstrest)
						{
							if (oddeven)
							{
								ConditionalPageMasterReference first = getConditionalPageMasterReference(
										pam, 1);
								ConditionalPageMasterReference odd = getConditionalPageMasterReference(
										pam, 3);
								ConditionalPageMasterReference even = getConditionalPageMasterReference(
										pam, 4);

								List<ConditionalPageMasterReference> conditionlist = new ArrayList<ConditionalPageMasterReference>();
								conditionlist.add(first);
								conditionlist.add(odd);
								conditionlist.add(even);
								List<SubSequenceSpecifier> subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
								RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
										new EnumNumber(-1, 0), conditionlist);
								subSequenceSpecifiers.add(rpm);
								PageSequenceMaster psm = new PageSequenceMaster(
										subSequenceSpecifiers);
								result = psm;
							} else
							{
								ConditionalPageMasterReference first = getConditionalPageMasterReference(
										pam, 1);
								ConditionalPageMasterReference last = getConditionalPageMasterReference(
										pam, 2);

								SimplePageMaster siplast = last
										.getMasterReference();

								ConditionalPageMasterReference rest = new ConditionalPageMasterReference(
										siplast, Constants.EN_REST, any, any);

								List<ConditionalPageMasterReference> conditionlist = new ArrayList<ConditionalPageMasterReference>();
								conditionlist.add(first);
								conditionlist.add(rest);
								List<SubSequenceSpecifier> subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
								RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
										new EnumNumber(-1, 0), conditionlist);
								subSequenceSpecifiers.add(rpm);
								PageSequenceMaster psm = new PageSequenceMaster(
										subSequenceSpecifiers);
								result = psm;
							}
						} else
						{
							if (oddeven)
							{
								ConditionalPageMasterReference odd = getConditionalPageMasterReference(
										pam, 3);
								ConditionalPageMasterReference even = getConditionalPageMasterReference(
										pam, 4);

								List<ConditionalPageMasterReference> conditionlist = new ArrayList<ConditionalPageMasterReference>();
								conditionlist.add(odd);
								conditionlist.add(even);
								List<SubSequenceSpecifier> subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
								RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
										new EnumNumber(-1, 0), conditionlist);
								subSequenceSpecifiers.add(rpm);
								PageSequenceMaster psm = new PageSequenceMaster(
										subSequenceSpecifiers);
								result = psm;
							} else
							{
								ConditionalPageMasterReference rest = getConditionalPageMasterReference(
										pam, 5);
								SimplePageMaster sipfirst = rest
										.getMasterReference();
								result = sipfirst;
							}
						}
					} else
					{
						if (firstrest)
						{
							if (oddeven)
							{
								ConditionalPageMasterReference first = getConditionalPageMasterReference(
										pam, 1);
								ConditionalPageMasterReference odd = getConditionalPageMasterReference(
										pam, 3);
								ConditionalPageMasterReference even = getConditionalPageMasterReference(
										pam, 4);
								SimplePageMaster sipfirst = odd
										.getMasterReference().clone();
								ConditionalPageMasterReference last = new ConditionalPageMasterReference(
										sipfirst, Constants.EN_LAST, any, any);

								List<ConditionalPageMasterReference> conditionlist = new ArrayList<ConditionalPageMasterReference>();
								conditionlist.add(first);
								conditionlist.add(last);
								conditionlist.add(odd);
								conditionlist.add(even);
								List<SubSequenceSpecifier> subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
								RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
										new EnumNumber(-1, 0), conditionlist);
								subSequenceSpecifiers.add(rpm);
								PageSequenceMaster psm = new PageSequenceMaster(
										subSequenceSpecifiers);
								result = psm;
							} else
							{
								ConditionalPageMasterReference first = getConditionalPageMasterReference(
										pam, 1);
								ConditionalPageMasterReference rest = getConditionalPageMasterReference(
										pam, 5);
								SimplePageMaster sipfirst = rest
										.getMasterReference().clone();

								ConditionalPageMasterReference last = new ConditionalPageMasterReference(
										sipfirst, Constants.EN_LAST, any, any);

								List<ConditionalPageMasterReference> conditionlist = new ArrayList<ConditionalPageMasterReference>();
								conditionlist.add(first);
								conditionlist.add(last);
								conditionlist.add(rest);
								List<SubSequenceSpecifier> subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
								RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
										new EnumNumber(-1, 0), conditionlist);
								subSequenceSpecifiers.add(rpm);
								PageSequenceMaster psm = new PageSequenceMaster(
										subSequenceSpecifiers);
								result = psm;
							}
						} else
						{

							ConditionalPageMasterReference odd = getConditionalPageMasterReference(
									pam, 3);
							ConditionalPageMasterReference even = getConditionalPageMasterReference(
									pam, 4);

							SimplePageMaster sipfirst = odd
									.getMasterReference().clone();
							ConditionalPageMasterReference last = new ConditionalPageMasterReference(
									sipfirst, Constants.EN_LAST, any, any);

							List<ConditionalPageMasterReference> conditionlist = new ArrayList<ConditionalPageMasterReference>();
							conditionlist.add(last);
							conditionlist.add(odd);
							conditionlist.add(even);
							List<SubSequenceSpecifier> subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
							RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
									new EnumNumber(-1, 0), conditionlist);
							subSequenceSpecifiers.add(rpm);
							PageSequenceMaster psm = new PageSequenceMaster(
									subSequenceSpecifiers);
							result = psm;
						}
					}

				} else if (flg == 3)
				{
					if (!ischeck)
					{
						if (firstrest)
						{
							if (restlast)
							{
								ConditionalPageMasterReference first = getConditionalPageMasterReference(
										pam, 1);
								ConditionalPageMasterReference last = getConditionalPageMasterReference(
										pam, 2);
								ConditionalPageMasterReference even = getConditionalPageMasterReference(
										pam, 4);
								SimplePageMaster siprest = even
										.getMasterReference();
								ConditionalPageMasterReference rest = new ConditionalPageMasterReference(
										siprest, Constants.EN_REST, any, any);

								List<ConditionalPageMasterReference> conditionlist = new ArrayList<ConditionalPageMasterReference>();
								conditionlist.add(first);
								conditionlist.add(last);
								conditionlist.add(rest);
								List<SubSequenceSpecifier> subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
								RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
										new EnumNumber(-1, 0), conditionlist);
								subSequenceSpecifiers.add(rpm);
								PageSequenceMaster psm = new PageSequenceMaster(
										subSequenceSpecifiers);
								result = psm;
							} else
							{
								ConditionalPageMasterReference first = getConditionalPageMasterReference(
										pam, 1);
								ConditionalPageMasterReference even = getConditionalPageMasterReference(
										pam, 4);
								SimplePageMaster siprest = even
										.getMasterReference();
								ConditionalPageMasterReference rest = new ConditionalPageMasterReference(
										siprest, Constants.EN_REST, any, any);

								List<ConditionalPageMasterReference> conditionlist = new ArrayList<ConditionalPageMasterReference>();
								conditionlist.add(first);
								conditionlist.add(rest);
								List<SubSequenceSpecifier> subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
								RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
										new EnumNumber(-1, 0), conditionlist);
								subSequenceSpecifiers.add(rpm);
								PageSequenceMaster psm = new PageSequenceMaster(
										subSequenceSpecifiers);
								result = psm;
							}
						} else
						{
							if (restlast)
							{
								ConditionalPageMasterReference last = getConditionalPageMasterReference(
										pam, 2);

								ConditionalPageMasterReference even = getConditionalPageMasterReference(
										pam, 4);

								SimplePageMaster siprest = even
										.getMasterReference();

								ConditionalPageMasterReference rest = new ConditionalPageMasterReference(
										siprest, Constants.EN_REST, any, any);

								List<ConditionalPageMasterReference> conditionlist = new ArrayList<ConditionalPageMasterReference>();
								conditionlist.add(rest);
								conditionlist.add(last);
								List<SubSequenceSpecifier> subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
								RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
										new EnumNumber(-1, 0), conditionlist);
								subSequenceSpecifiers.add(rpm);
								PageSequenceMaster psm = new PageSequenceMaster(
										subSequenceSpecifiers);
								result = psm;
							} else
							{
								ConditionalPageMasterReference odd = getConditionalPageMasterReference(
										pam, 3);
								SimplePageMaster sipfirst = odd
										.getMasterReference();
								result = sipfirst;
							}
						}
					} else
					{
						if (firstrest)
						{
							if (restlast)
							{
								ConditionalPageMasterReference last = getConditionalPageMasterReference(
										pam, 2);
								ConditionalPageMasterReference first = getConditionalPageMasterReference(
										pam, 1);
								ConditionalPageMasterReference rest = getConditionalPageMasterReference(
										pam, 5);
								SimplePageMaster sipodd = rest
										.getMasterReference().clone();
								SimplePageMaster sipeven = rest
										.getMasterReference().clone();
								ConditionalPageMasterReference odd = new ConditionalPageMasterReference(
										sipodd, any, Constants.EN_ODD, any);
								ConditionalPageMasterReference even = new ConditionalPageMasterReference(
										sipeven, any, Constants.EN_EVEN, any);

								List<ConditionalPageMasterReference> conditionlist = new ArrayList<ConditionalPageMasterReference>();
								conditionlist.add(first);
								conditionlist.add(last);
								conditionlist.add(odd);
								conditionlist.add(even);
								List<SubSequenceSpecifier> subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
								RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
										new EnumNumber(-1, 0), conditionlist);
								subSequenceSpecifiers.add(rpm);
								PageSequenceMaster psm = new PageSequenceMaster(
										subSequenceSpecifiers);
								result = psm;
							} else
							{
								ConditionalPageMasterReference first = getConditionalPageMasterReference(
										pam, 1);
								ConditionalPageMasterReference rest = getConditionalPageMasterReference(
										pam, 5);
								SimplePageMaster sipodd = first
										.getMasterReference().clone();
								SimplePageMaster sipeven = rest
										.getMasterReference().clone();
								ConditionalPageMasterReference odd = new ConditionalPageMasterReference(
										sipodd, any, Constants.EN_ODD, any);
								ConditionalPageMasterReference even = new ConditionalPageMasterReference(
										sipeven, any, Constants.EN_EVEN, any);

								List<ConditionalPageMasterReference> conditionlist = new ArrayList<ConditionalPageMasterReference>();
								conditionlist.add(first);
								conditionlist.add(odd);
								conditionlist.add(even);
								List<SubSequenceSpecifier> subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
								RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
										new EnumNumber(-1, 0), conditionlist);
								subSequenceSpecifiers.add(rpm);
								PageSequenceMaster psm = new PageSequenceMaster(
										subSequenceSpecifiers);
								result = psm;
							}
						} else
						{
							ConditionalPageMasterReference last = getConditionalPageMasterReference(
									pam, 2);
							ConditionalPageMasterReference rest = getConditionalPageMasterReference(
									pam, 5);
							SimplePageMaster sipodd = rest.getMasterReference()
									.clone();
							SimplePageMaster sipeven = last
									.getMasterReference().clone();
							ConditionalPageMasterReference odd = new ConditionalPageMasterReference(
									sipodd, any, Constants.EN_ODD, any);
							ConditionalPageMasterReference even = new ConditionalPageMasterReference(
									sipeven, any, Constants.EN_EVEN, any);
							List<ConditionalPageMasterReference> conditionlist = new ArrayList<ConditionalPageMasterReference>();
							conditionlist.add(last);
							conditionlist.add(odd);
							conditionlist.add(even);
							List<SubSequenceSpecifier> subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
							RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
									new EnumNumber(-1, 0), conditionlist);
							subSequenceSpecifiers.add(rpm);
							PageSequenceMaster psm = new PageSequenceMaster(
									subSequenceSpecifiers);
							result = psm;
						}
					}
				}
			} else
			{
				PageSequenceMaster psm = (PageSequenceMaster) pam;
				List<SubSequenceSpecifier> subSequenceSpecifiers = psm
						.getSubsequenceSpecifiers();
				if (subSequenceSpecifiers != null
						&& !subSequenceSpecifiers.isEmpty())
				{
					int size = subSequenceSpecifiers.size();

					if (flg == 1)
					{
						SimplePageMaster first = null;
						SimplePageMaster rest = null;

						SubSequenceSpecifier one = subSequenceSpecifiers.get(0);
						if (one instanceof RepeatablePageMasterReference)

						{
							first = ((RepeatablePageMasterReference) one)
									.getMasterReference();
						} else if (one instanceof SinglePageMasterReference)
						{
							first = ((SinglePageMasterReference) one)
									.getMasterReference();
						} else if (one instanceof RepeatablePageMasterAlternatives)
						{
							RepeatablePageMasterAlternatives rpma = (RepeatablePageMasterAlternatives) one;
							List<ConditionalPageMasterReference> listcpmr = rpma
									.getPageMasterRefs();
							int listcpmrsize = listcpmr.size();
							if (listcpmrsize == 1)
							{
								first = listcpmr.get(0).getMasterReference();
							} else
							{
								SimplePageMaster[] spmarry = getSimplePageMasterTwo(
										listcpmr, 1);
								first = spmarry[0];
								rest = spmarry[1];
							}
						}

						if (size > 1 && rest == null)
						{
							SubSequenceSpecifier two = subSequenceSpecifiers
									.get(size - 1);
							if (two instanceof RepeatablePageMasterReference)
							{
								rest = ((RepeatablePageMasterReference) two)
										.getMasterReference();
							} else if (two instanceof SinglePageMasterReference)
							{
								rest = ((SinglePageMasterReference) two)
										.getMasterReference();
							} else if (two instanceof RepeatablePageMasterAlternatives)
							{
								RepeatablePageMasterAlternatives rpma = (RepeatablePageMasterAlternatives) two;
								List<ConditionalPageMasterReference> listcpmr = rpma
										.getPageMasterRefs();
								int listcpmrsize = listcpmr.size();
								if (listcpmrsize == 1)
								{
									rest = listcpmr.get(0).getMasterReference();
								} else
								{
									rest = getSimplePageMaster(listcpmr);
								}
							}
						} else
						{
							rest = first.clone();
						}
						ConditionalPageMasterReference firstcpr = new ConditionalPageMasterReference(
								first, Constants.EN_FIRST, any, any);
						ConditionalPageMasterReference restcpr = new ConditionalPageMasterReference(
								rest, Constants.EN_REST, any, any);

						List<ConditionalPageMasterReference> conditionlist = new ArrayList<ConditionalPageMasterReference>();
						conditionlist.add(firstcpr);
						conditionlist.add(restcpr);

						List<SubSequenceSpecifier> subSequenceSpecifiersnew = new ArrayList<SubSequenceSpecifier>();
						RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
								new EnumNumber(-1, 0), conditionlist);
						subSequenceSpecifiersnew.add(rpm);
						PageSequenceMaster psmnew = new PageSequenceMaster(
								subSequenceSpecifiersnew);
						result = psmnew;
					} else if (flg == 2)
					{

						SimplePageMaster last = null;
						SimplePageMaster rest = null;
						SubSequenceSpecifier one = subSequenceSpecifiers.get(0);
						if (one instanceof RepeatablePageMasterReference)
						{
							rest = ((RepeatablePageMasterReference) one)
									.getMasterReference();
						} else if (one instanceof SinglePageMasterReference)
						{
							rest = ((SinglePageMasterReference) one)
									.getMasterReference();
						} else if (one instanceof RepeatablePageMasterAlternatives)
						{
							RepeatablePageMasterAlternatives rpma = (RepeatablePageMasterAlternatives) one;
							List<ConditionalPageMasterReference> listcpmr = rpma
									.getPageMasterRefs();
							int listcpmrsize = listcpmr.size();
							if (listcpmrsize == 1)
							{
								rest = listcpmr.get(0).getMasterReference();
							} else
							{
								SimplePageMaster[] spmarry = getSimplePageMasterTwo(
										listcpmr, 1);
								last = spmarry[0];
								rest = spmarry[1];
							}

						}

						if (size > 1 && last == null)
						{
							SubSequenceSpecifier two = subSequenceSpecifiers
									.get(size - 1);
							if (two instanceof RepeatablePageMasterReference)
							{
								last = ((RepeatablePageMasterReference) two)
										.getMasterReference();
							} else if (two instanceof SinglePageMasterReference)
							{
								last = ((SinglePageMasterReference) two)
										.getMasterReference();
							} else if (two instanceof RepeatablePageMasterAlternatives)
							{
								RepeatablePageMasterAlternatives rpma = (RepeatablePageMasterAlternatives) two;
								List<ConditionalPageMasterReference> listcpmr = rpma
										.getPageMasterRefs();
								int listcpmrsize = listcpmr.size();
								if (listcpmrsize == 1)
								{
									last = listcpmr.get(0).getMasterReference();
								} else
								{
									last = getSimplePageMaster(listcpmr);
								}
							}
						} else
						{
							last = rest.clone();
						}
						ConditionalPageMasterReference laststcpr = new ConditionalPageMasterReference(
								last, Constants.EN_LAST, any, any);
						ConditionalPageMasterReference restcpr = new ConditionalPageMasterReference(
								rest, Constants.EN_REST, any, any);

						List<ConditionalPageMasterReference> conditionlist = new ArrayList<ConditionalPageMasterReference>();
						conditionlist.add(laststcpr);
						conditionlist.add(restcpr);
						List<SubSequenceSpecifier> subSequenceSpecifiersnew = new ArrayList<SubSequenceSpecifier>();
						RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
								new EnumNumber(-1, 0), conditionlist);
						subSequenceSpecifiersnew.add(rpm);
						PageSequenceMaster psmnew = new PageSequenceMaster(
								subSequenceSpecifiersnew);
						result = psmnew;
					} else if (flg == 3)
					{
						SimplePageMaster odd = null;
						SimplePageMaster even = null;

						SubSequenceSpecifier one = subSequenceSpecifiers.get(0);
						if (one instanceof RepeatablePageMasterReference)

						{
							odd = ((RepeatablePageMasterReference) one)
									.getMasterReference();
						} else if (one instanceof SinglePageMasterReference)
						{
							odd = ((SinglePageMasterReference) one)
									.getMasterReference();
						} else if (one instanceof RepeatablePageMasterAlternatives)
						{
							RepeatablePageMasterAlternatives rpma = (RepeatablePageMasterAlternatives) one;
							List<ConditionalPageMasterReference> listcpmr = rpma
									.getPageMasterRefs();
							int listcpmrsize = listcpmr.size();
							if (listcpmrsize == 1)
							{
								odd = listcpmr.get(0).getMasterReference();
							} else
							{
								SimplePageMaster[] spmarry = getSimplePageMasterTwo(
										listcpmr, 1);
								odd = spmarry[0];
								even = spmarry[1];
							}
						}

						if (size > 1 && even == null)
						{
							SubSequenceSpecifier two = subSequenceSpecifiers
									.get(size - 1);
							if (two instanceof RepeatablePageMasterReference)
							{
								even = ((RepeatablePageMasterReference) two)
										.getMasterReference();
							} else if (two instanceof SinglePageMasterReference)
							{
								even = ((SinglePageMasterReference) two)
										.getMasterReference();
							} else if (two instanceof RepeatablePageMasterAlternatives)
							{
								RepeatablePageMasterAlternatives rpma = (RepeatablePageMasterAlternatives) two;
								List<ConditionalPageMasterReference> listcpmr = rpma
										.getPageMasterRefs();
								int listcpmrsize = listcpmr.size();
								if (listcpmrsize == 1)
								{
									even = listcpmr.get(0).getMasterReference();
								} else
								{
									even = getSimplePageMaster(listcpmr);
								}
							}
						} else
						{
							even = odd.clone();
						}
						ConditionalPageMasterReference oddcpr = new ConditionalPageMasterReference(
								odd, any, Constants.EN_ODD, any);
						ConditionalPageMasterReference evencpr = new ConditionalPageMasterReference(
								even, any, Constants.EN_EVEN, any);

						List<ConditionalPageMasterReference> conditionlist = new ArrayList<ConditionalPageMasterReference>();
						conditionlist.add(oddcpr);
						conditionlist.add(evencpr);

						List<SubSequenceSpecifier> subSequenceSpecifiersnew = new ArrayList<SubSequenceSpecifier>();
						RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
								new EnumNumber(-1, 0), conditionlist);
						subSequenceSpecifiersnew.add(rpm);
						PageSequenceMaster psmnew = new PageSequenceMaster(
								subSequenceSpecifiersnew);
						result = psmnew;
					}
				}
			}
		}
		return result;
	}

	public static SimplePageMaster getSimplePageMaster(
			List<ConditionalPageMasterReference> listcpmr)
	{
		SimplePageMaster[] all = getSimplePageMasterArry(listcpmr);
		for (int i = all.length - 1; i > 0; i--)
		{
			if (all[i] != null)
			{
				return all[i];
			}
		}
		return null;
	}

	public static SimplePageMaster[] getSimplePageMasterTwo(
			List<ConditionalPageMasterReference> listcpmr, int flg)
	{
		SimplePageMaster[] all = getSimplePageMasterArry(listcpmr);
		if (flg == 1)
		{
			SimplePageMaster first = null;
			SimplePageMaster rest = null;
			for (int i = 0; i < all.length; i++)
			{
				if (all[i] != null)
				{
					first = all[i];
					break;
				}
			}
			for (int i = all.length - 1; i > 0; i--)
			{
				if (all[i] != null)
				{
					rest = all[i];
					break;
				}
			}
			SimplePageMaster[] firstrest =
			{ first, rest };
			return firstrest;
		} else if (flg == 2)
		{
			SimplePageMaster last = null;
			SimplePageMaster rest = null;
			for (int i = 0; i < all.length; i++)
			{
				if (all[i] != null)
				{
					rest = all[i];
					break;
				}
			}
			for (int i = all.length - 1; i > 0; i--)
			{
				if (all[i] != null)
				{
					rest = all[i];
					break;
				}
			}
			SimplePageMaster[] lastrest =
			{ last, rest };
			return lastrest;
		} else if (flg == 3)
		{

			SimplePageMaster odd = null;
			SimplePageMaster even = null;
			for (int i = 0; i < all.length; i++)
			{
				if (all[i] != null)
				{
					odd = all[i];
					break;
				}
			}
			for (int i = all.length - 1; i > 0; i--)
			{
				if (all[i] != null)
				{
					even = all[i];
					break;
				}
			}
			SimplePageMaster[] oddeven =
			{ odd, even };
			return oddeven;
		}
		return null;
	}

	public static SimplePageMaster[] getSimplePageMasterArry(
			List<ConditionalPageMasterReference> listcpmr)
	{
		SimplePageMaster first = null;
		SimplePageMaster last = null;
		SimplePageMaster rest = null;
		SimplePageMaster only = null;
		SimplePageMaster odd = null;
		SimplePageMaster even = null;
		SimplePageMaster blank = null;
		SimplePageMaster noblank = null;
		int any = Constants.EN_ANY;
		for (ConditionalPageMasterReference current : listcpmr)
		{
			int pageposition = current.getPagePosition();
			int oddeven = current.getOddOrEven();
			int blankorno = current.getBlankOrNotBlank();
			SimplePageMaster currentspm = current.getMasterReference();
			if (pageposition == any)
			{
				if (oddeven == any)
				{
					if (blankorno == Constants.EN_BLANK)
					{
						blank = currentspm;
					} else if (blankorno == Constants.EN_NOT_BLANK)
					{
						noblank = currentspm;
					}
				} else
				{
					if (pageposition == Constants.EN_ODD)
					{
						odd = currentspm;
					} else if (pageposition == Constants.EN_EVEN)
					{
						even = currentspm;
					}
				}
			} else
			{
				if (pageposition == Constants.EN_FIRST)
				{
					first = currentspm;
				} else if (pageposition == Constants.EN_LAST)
				{
					last = currentspm;
				} else if (pageposition == Constants.EN_REST)
				{
					rest = currentspm;
				} else if (pageposition == Constants.EN_ONLY)
				{
					only = currentspm;
				}
			}
		}
		SimplePageMaster[] result =
		{ first, only, odd, blank, rest, noblank, even, last };
		return result;
	}

	public final static void disableScroPaneKeyborde(JComponent scropane)
	{
		final ActionMap map = scropane.getActionMap();
		final PNullAction nullAction = new PNullAction();
		map.put("scrollUp", nullAction);
		map.put("scrollDown", nullAction);
		map.put("scrollLeft", nullAction);
		map.put("scrollRight", nullAction);
		map.put("unitScrollRight", nullAction);
		map.put("unitScrollLeft", nullAction);
		map.put("unitScrollUp", nullAction);
		map.put("unitScrollDown", nullAction);
		map.put("scrollEnd", nullAction);
		map.put("scrollHome", nullAction);
	}

	@SuppressWarnings("serial")
	private static class PNullAction extends AbstractAction
	{

		public void actionPerformed(final ActionEvent e)
		{
		}
	}

	public static PageSequence getCurrentPageSequence()
	{
		CellElement elements = SystemManager.getMainframe().getEidtComponent()
				.getCaretPosition().getLeafElement();
		// List<CellElement> pagesequence = DocumentUtil.getElements(elements,
		// PageSequence.class);
		if (elements != null)
		{
			while (!(elements instanceof PageSequence))
			{
				com.wisii.wisedoc.document.Element parent = elements
						.getParent();
				if (parent != null)
				{
					elements = (CellElement) elements.getParent();
				} else
				{
					return null;
				}
			}
		}
		PageSequence current = null;
		if (elements instanceof PageSequence)
		{
			current = (PageSequence) elements;
		}
		return current;
	}

	public static void removeStaticContent(String scname, PageSequence currentps)
	{

		if (currentps != null)
		{
			Enumeration<CellElement> flows = currentps.children();
			while (flows.hasMoreElements())
			{
				CellElement cellElement = flows.nextElement();
				if (cellElement instanceof StaticContent)
				{
					Object name = cellElement
							.getAttribute(Constants.PR_FLOW_NAME);
					if (name != null)
					{
						if (name.equals(scname))
						{
							currentps.remove(cellElement);
							break;
						}
					}
				}
			}
		}
	}

	public static void addStaticContent(String scname, StaticContent newsc,
			PageSequence currentps)
	{
		if (currentps != null)
		{
			Enumeration<CellElement> flows = currentps.children();
			boolean flg = true;
			while (flows.hasMoreElements())
			{
				CellElement cellElement = flows.nextElement();
				if (cellElement instanceof StaticContent)
				{
					Object name = cellElement
							.getAttribute(Constants.PR_FLOW_NAME);
					if (name != null)
					{
						if (name.equals(scname))
						{
							currentps.remove(cellElement);
							currentps.add(newsc);
							flg = false;
							break;
						}
					}
				}
			}
			if (flg)
			{
				currentps.add(newsc);
			}
		}
	}

	// public static StaticContent getStaticContent(String scname,
	// PageSequence currentps)
	// {
	// if (currentps != null)
	// {
	// Enumeration<CellElement> flows = currentps.children();
	// while (flows.hasMoreElements())
	// {
	// CellElement cellElement = flows.nextElement();
	// if (cellElement instanceof StaticContent)
	// {
	// Object name = cellElement
	// .getAttribute(Constants.PR_FLOW_NAME);
	// if (name != null)
	// {
	// if (name.equals(scname))
	// {
	// return (StaticContent) cellElement;
	// }
	// }
	// }
	// }
	// }
	// return null;
	// }

	// public static void clearPageSequence(PageSequence pagesequence)
	// {
	// Enumeration<CellElement> flows = pagesequence.children();
	// while (flows.hasMoreElements())
	// {
	// List<String> flownames = WisedocUtil.getFlowName(pagesequence);
	// CellElement cellElement = flows.nextElement();
	// if (cellElement instanceof StaticContent
	// && !flownames.contains(cellElement
	// .getAttribute(Constants.PR_FLOW_NAME)))
	// {
	// pagesequence.remove(cellElement);
	// }
	// }
	// }

	public static List<String> getFlowName(PageSequence pagesequence)
	{
		List<String> flownames = new ArrayList<String>();
		if (pagesequence != null)
		{
			Object simplepagemaster = pagesequence
					.getAttribute(Constants.PR_SIMPLE_PAGE_MASTER);
			if (simplepagemaster != null)
			{
				SimplePageMaster currentspm = (SimplePageMaster) simplepagemaster;
				getSimPageMaster(flownames, currentspm);
			} else
			{
				Object pagesequencemaster = pagesequence
						.getAttribute(Constants.PR_PAGE_SEQUENCE_MASTER);
				if (pagesequencemaster != null)
				{
					PageSequenceMaster currentpsm = (PageSequenceMaster) pagesequencemaster;
					List<SubSequenceSpecifier> subSequenceSpecifiers = currentpsm
							.getSubsequenceSpecifiers();
					if (subSequenceSpecifiers != null
							&& !subSequenceSpecifiers.isEmpty())
					{
						for (SubSequenceSpecifier currentsss : subSequenceSpecifiers)
						{
							if (currentsss instanceof SinglePageMasterReference)
							{
								getSimPageMaster(
										flownames,
										((SinglePageMasterReference) currentsss)
												.getMasterReference());
							} else if (currentsss instanceof RepeatablePageMasterReference)
							{
								getSimPageMaster(
										flownames,
										((RepeatablePageMasterReference) currentsss)
												.getMasterReference());
							} else if (currentsss instanceof RepeatablePageMasterAlternatives)
							{
								RepeatablePageMasterAlternatives rpma = (RepeatablePageMasterAlternatives) currentsss;
								List<ConditionalPageMasterReference> conditionalPMR = rpma
										.getPageMasterRefs();
								if (conditionalPMR != null
										&& !conditionalPMR.isEmpty())
								{
									for (ConditionalPageMasterReference currentcd : conditionalPMR)
									{
										getSimPageMaster(flownames, currentcd
												.getMasterReference());
									}
								}
							}
						}
					}
				}
			}
		}
		return flownames;
	}

	public static StaticContent getDefaultStaticContent(String scname)
	{
		StaticContent staticcontent = new StaticContent(scname);
		// Block block = new Block();
		// final TextInline text = new TextInline(new Text(' '), null);
		// block.add(text);
		// staticcontent.add(block);
		return staticcontent;
	}

	public static void getSimPageMaster(List<String> list, SimplePageMaster spm)
	{
		Map<Integer, Region> regions = spm.getRegions();
		Object[] keys = regions.keySet().toArray();
		for (int i = 0; i < keys.length; i++)
		{
			int key = (Integer) keys[i];
			if (key != Constants.FO_REGION_BODY)
			{
				Region currentregion = regions.get(key);
				Length extent = null;
				boolean visible = currentregion.getOverflow() == Constants.EN_VISIBLE;
				if (currentregion instanceof RegionBefore)
				{
					RegionBefore region = (RegionBefore) currentregion;
					extent = region.getExtent();
				} else if (currentregion instanceof RegionAfter)
				{
					RegionAfter region = (RegionAfter) currentregion;
					extent = region.getExtent();
				} else if (currentregion instanceof RegionStart)
				{
					RegionStart region = (RegionStart) currentregion;
					extent = region.getExtent();
				} else if (currentregion instanceof RegionEnd)
				{
					RegionEnd region = (RegionEnd) currentregion;
					extent = region.getExtent();
				}
				int lengthvalue = extent != null ? extent.getValue() : 0;
				String regionname = currentregion.getRegionName();
				if (regionname != null && !"".equals(regionname)
						&& (lengthvalue > 0 || (lengthvalue == 0 && visible)))
				{
					list.add(regionname);
				}
			}
		}
	}

	// // 操作流
	// public static void cloneStaticContent(String mastername,
	// List<StaticContent> sclist)
	// {
	// if (mastername == null || "".equals(mastername))
	// {
	// } else
	// {
	// addStaticContentclone(mastername + "before", sclist);
	// addStaticContentclone(mastername + "after", sclist);
	// addStaticContentclone(mastername + "start", sclist);
	// addStaticContentclone(mastername + "end", sclist);
	// }
	// }

	public static void removeStaticContents(String mastername,
			Map<String, StaticContent> scmap)
	{
		if (!(mastername == null || "".equals(mastername)))
		{
			if (scmap.containsKey(mastername + "before"))
			{
				scmap.remove(mastername + "before");
			}
			if (scmap.containsKey(mastername + "after"))
			{
				scmap.remove(mastername + "after");
			}
			if (scmap.containsKey(mastername + "start"))
			{
				scmap.remove(mastername + "start");
			}
			if (scmap.containsKey(mastername + "end"))
			{
				scmap.remove(mastername + "end");
			}
		}
	}

	public static void addStaticContentclone(String oldname, String newname,
			Map<String, StaticContent> scmap)
	{
		StaticContent old = scmap.get(oldname);
		if (old != null)
		{
			StaticContent newsc = (StaticContent) old.clone();
			newsc.setAttribute(Constants.PR_FLOW_NAME, newname);
			scmap.put(newname, newsc);
		} else
		{
			StaticContent newsc = WisedocUtil.getDefaultStaticContent(newname);
			scmap.put(newname, newsc);
		}
	}

	public static boolean isNullStaticContent(StaticContent currentstaticcontent)
	{

		String scname = currentstaticcontent.getFlowName();

		List<CellElement> blocks = currentstaticcontent.getAllChildren();
		if (blocks == null)
		{
			return true;
		} else
		{
			for (CellElement currentblock : blocks)
			{
				boolean currentflg = isNullBlock(currentblock);
				if (!currentflg)
				{
					IoXslUtil.addStaticcontent(scname);
					return false;
				}
			}
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	public static boolean isNullBlock(CellElement block)
	{
		if (block instanceof Block)
		{
			Enumeration childrens = block.children();
			if (childrens == null)
			{
				return true;
			}
			while (childrens.hasMoreElements())
			{
				return false;
			}
		}
		return false;
	}
}
