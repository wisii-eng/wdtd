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
 * @IOFactory.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io;

import java.util.List;

import org.dom4j.io.HTMLWriter;

import com.wisii.io.html.HtmlWriter;
import com.wisii.io.zhumoban.WSDMReader;
import com.wisii.io.zhumoban.WSDMWriter;
import com.wisii.io.zhumoban.WSDXReader;
import com.wisii.io.zhumoban.WSDXWriter;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.ZiMoban;
import com.wisii.wisedoc.io.wsd.WSDAttributeIOFactory;
import com.wisii.wisedoc.io.wsd.WSDElementWriterFactory;
import com.wisii.wisedoc.io.wsd.WSDWriter;
import com.wisii.wisedoc.io.wsd.reader.WSDReader;
import com.wisii.wisedoc.io.xsl.SelectElementWriterFactory;
import com.wisii.wisedoc.io.xsl.SelectOutputAttributeWriter;
import com.wisii.wisedoc.io.xsl.XslPSWrite;
import com.wisii.wisedoc.io.xsl.XslWrite;

/**
 * 类功能描述：输出功能工厂类
 * 
 * 作者：zhangqiang 创建日期：2008-8-14
 */
public class IOFactory
{

	/**
	 * wsd文件类型
	 */
	public final static int WSD = 0;

	public final static int XSL = 1;

	public final static int XSLPS = 4;

	public final static int WSDT = 2;

	public final static int WSDX = 3;

	public final static int WSDM = 5;
	public final static int HTML = 6;

	public static ElementWriterFactory WSDELEMENTWRITERFACTORY = null;

	public static ElementWriterFactory SELECTELEMENTWRITERFACTORY = null;

	/**
	 * 
	 * 获得指定输出类型的writer
	 * 
	 * @param type
	 *            ：类型
	 * @return
	 * @exception
	 */
	public static Writer getWriter(int type)
	{
		Writer reader = null;
		if (type == WSD)
		{
			reader = (Writer) new WSDWriter();
		} else if (type == XSL)
		{
			// boolean xmleditable = true;
			reader = new XslWrite();
		} else if (type == XSLPS)
		{
			// boolean xmleditable = true;
			reader = new XslPSWrite();
		} else if (type == WSDT)
		{
			reader = new TemplateWriter();
		} else if (type == WSDX)
		{
			reader = new WSDXWriter();
		} else if (type == WSDM)
		{
			reader = new WSDMWriter();
		} else if (type == HTML)
		{
			reader = new HtmlWriter();
		}
		return reader;

	}

	/**
	 * 
	 * 获得指定类型的reader
	 * 
	 * @param type
	 *            ：类型
	 * @return
	 * @exception
	 */
	public static Reader getReader(int type)
	{
		Reader reader = null;
		if (type == WSD)
		{
			reader = new WSDReader();
		} else if (type == WSDT)
		{
			reader = new TemplateReader();
		} else if (type == WSDX)
		{
			reader = new WSDXReader();
		} else if (type == WSDM)
		{
			reader = new WSDMReader();
		}
		return reader;
	}

	/**
	 * 
	 * 根据指定类型获得属性工厂类
	 * 
	 * @param type
	 *            ：类型
	 * @return
	 * @exception
	 */
	public static AttributeIOFactory getAttributeIOFactory(int type)
	{
		AttributeIOFactory factory = null;
		if (type == WSD)
		{
			factory = new WSDAttributeIOFactory();
		} else if (type == XSL)
		{
			factory = new SelectOutputAttributeWriter();
		}
		return factory;
	}

	/**
	 * 
	 * 根据指定类型获得属性工厂类
	 * 
	 * @param type
	 *            ：类型
	 * @return
	 * @exception
	 */
	public static ElementWriterFactory getElementWriterFactory(int type)
	{

		if (type == WSD)
		{
			if (WSDELEMENTWRITERFACTORY == null)
			{
				WSDELEMENTWRITERFACTORY = new WSDElementWriterFactory();
			}
			return WSDELEMENTWRITERFACTORY;
		} else if (type == XSL)
		{
			if (SELECTELEMENTWRITERFACTORY == null)
			{
				SELECTELEMENTWRITERFACTORY = new SelectElementWriterFactory();
			}
			return SELECTELEMENTWRITERFACTORY;
		}
		return null;
	}

	public static boolean isIfHaveSql()
	{
		DataStructureTreeModel model = SystemManager.getCurruentDocument()
				.getDataStructure();
		return model == null ? false : model.getDbsetting() != null;
	}

	public static boolean ifHaveZimoban()
	{
		// ZiMoban
		Document document = SystemManager.getCurruentDocument();
		if (document != null)
		{
			List<CellElement> listele = document.getAllChildren();
			if (listele != null)
			{
				for (CellElement current : listele)
				{
					boolean flg = ifHaveZimoban(current);
					if (flg)
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean ifHaveZimoban(CellElement element)
	{
		if (element != null)
		{
			if (element instanceof ZiMoban)
			{
				return true;
			}
			List<CellElement> listele = element.getAllChildren();
			if (listele != null)
			{
				for (CellElement current : listele)
				{
					boolean flg = ifHaveZimoban(current);
					if (flg)
					{
						return true;
					}
				}
			}
		}
		return false;
	}
}
