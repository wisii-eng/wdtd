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
 * @HtmlBrowseAction.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.swing.Icon;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import com.wisii.db.model.Setting;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.io.IOFactory;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2012-6-5
 */
public class HtmlBrowseAction extends AbstractBrowseAction
{

	/**
	 * 
	 * 用默认的字符串和默认的图标定义一个Action对象。
	 */
	public HtmlBrowseAction()
	{
		super();
	}

	/**
	 * 用指定描述字符串和默认图标定义一个 Action 对象。
	 * 
	 * @param name
	 *            指定Action的名字。
	 * 
	 */
	public HtmlBrowseAction(String name)
	{
		super(name);
	}

	/**
	 * 用指定描述字符串指定图标定义一个 Action 对象。
	 * 
	 * @param name
	 *            指定Action的名字。
	 * @param icon
	 *            指定Action的图标。
	 */
	public HtmlBrowseAction(String name, Icon icon)
	{
		super(name, icon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.view.action.AbstractBrowseAction#browse(com.wisii.wisedoc
	 * .view.action.AbstractBrowseAction.FileItem,
	 * com.wisii.wisedoc.view.action.AbstractBrowseAction.FileItem)
	 */
	@Override
	protected void browse(FileItem xslfileitem, FileItem xmlfileitem)
	{
		if (xslfileitem == null || xmlfileitem == null)
		{
			return;
		}
		try
		{
			File html = File.createTempFile("wisebrowse", ".html");
			Result result = new StreamResult(html);
			Transformer trans = TransformerFactory.newInstance()
					.newTransformer(new StreamSource(xslfileitem.getFile()));
			trans.transform(new StreamSource(xmlfileitem.getFile()), result);
			html.deleteOnExit();
			Desktop.getDesktop().open(html);
			if (xslfileitem.isIsneeddel())
			{
				xslfileitem.getFile().delete();
			}
			if (xmlfileitem.isIsneeddel())
			{
				xmlfileitem.getFile().delete();
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.view.action.AbstractBrowseAction#generateXSL(java.util
	 * .List, boolean)
	 */
	@Override
	protected FileItem generateXSL(List<Setting> sqls, boolean ishavezimoban)
	{
		File xsl = null;
		try
		{
			xsl = File.createTempFile("xsl", ".tmp");
		} catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
		Document doc = SystemManager.getCurruentDocument();
		DataStructureTreeModel model = doc.getDataStructure();
		com.wisii.db.Setting docsetting = model != null ? model.getDbsetting()
				: null;
		Setting setting = docsetting != null ? new Setting(docsetting
				.getSqlitem(), null) : null;
		if (setting != null)
		{
			sqls.add(setting);
		}
//		if (ishavezimoban)
//		{
//
//		} else
//		{
			try
			{
				IOFactory.getWriter(IOFactory.HTML).write(
						new FileOutputStream(xsl), doc);
			} catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
//		}
		return new FileItem(xsl, true);
	}

}
