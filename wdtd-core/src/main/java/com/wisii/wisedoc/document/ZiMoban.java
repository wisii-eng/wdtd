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
 * @ZiMoban.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.wisii.exception.NotAZimobanException;
import com.wisii.wisedoc.document.attribute.WiseDocColor;
import com.wisii.wisedoc.io.IOFactory;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2010-10-25
 */
public class ZiMoban extends DefaultElement
{
	// 子模板的存放路径
	public final static String PATH = System.getProperty("user.dir")
			+ File.separator + "zimobans" + File.separator;
	//文件后准名
	public final static String FILESUFFIX=".wsdx";
	public void refreshContents()
	{
		File file = new File(PATH + getAttribute(Constants.PR_ZIMOBAN_NAME));
		try
		{
			Document doc = IOFactory.getReader(IOFactory.WSDX).read(
					new FileInputStream(file));
			PageSequence ps = (PageSequence) doc.getChildAt(0);
			this.removeAllChildren();
			insert(ps.getMainFlow().getAllChildren(), 0);
		} catch (FileNotFoundException e)
		{
			LogUtil.infoException("", e);
		} catch (IOException e)
		{
			LogUtil.infoException("", e);
		}
	}
	public ZiMoban()
	{
		
	}
	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	private ZiMoban(Map<Integer, Object> attributes)
	{
		super(attributes);
	}

	/**
	 * 
	 * 传入子模板名，得到子模板对象 调用该方法前，需要调用validName方法已校验子模板名的合法性
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public static ZiMoban getInstanceof(String name)
	{
		Map<Integer, Object> atts = new HashMap<Integer, Object>();
		atts.put(Constants.PR_ZIMOBAN_NAME, name);
		ZiMoban zimoban = new ZiMoban(atts);
		File file = new File(PATH + name);
		try
		{
			Document doc = IOFactory.getReader(IOFactory.WSDX).read(
					new FileInputStream(file));
			PageSequence ps = (PageSequence) doc.getChildAt(0);
			zimoban.insert(ps.getMainFlow().getAllChildren(), 0);
			return zimoban;
		} catch (Exception e)
		{
			LogUtil.infoException("", e);
			e.printStackTrace();
			Block errorblock = new Block();
			String errormsg = "名称为{" + name + "}的子模板文件不存在或已损坏";
			List<CellElement> inlines = new ArrayList<CellElement>();
			Map<Integer, Object> inlineatts = new HashMap<Integer, Object>();
			inlineatts.put(Constants.PR_COLOR, new WiseDocColor(Color.red, 0));
			for (char c : errormsg.toCharArray())
			{

				TextInline inline = new TextInline(new Text(c), inlineatts);
				inlines.add(inline);
			}
			errorblock.insert(inlines, 0);
			zimoban.insert(errorblock, 0);
			return zimoban;
		}

	}

	public static boolean validName(String name) throws FileNotFoundException,
			NotAZimobanException
	{
		if (name == null)
		{
			throw new FileNotFoundException(name);
		}
		File file = new File(PATH + name);
		if (!file.exists())
		{
			throw new FileNotFoundException(PATH + name);
		}
		try
		{
			IOFactory.getReader(IOFactory.WSDX)
					.read(new FileInputStream(file));
		} catch (IOException e)
		{
			throw new NotAZimobanException();
		}
		return true;
	}
}
