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
 * @yinzhang.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.imageio.ImageIO;
/**
 * 类功能描述：
 * 
 *
 * 作者：zhangqiang
 * 创建日期：2011-11-8
 */
public  class QianZhang extends AbstractGraphics
{
	public QianZhang()
	{
		
	}
	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public QianZhang(String src)
	{
		setAttribute(Constants.PR_SRC, src);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.AbstractGraphics#getIntrinsicHeight()
	 */
	@Override
	public int getIntrinsicHeight()
	{
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.AbstractGraphics#getIntrinsicWidth()
	 */
	@Override
	public int getIntrinsicWidth()
	{
		return 1;
	}

	public Image getImage()
	{
		String url = getSrc();
		if(url!=null)
		{
			try
			{
				//File file
				return ImageIO.read(new File("qianzhangs"+File.separator+url));
			} catch (MalformedURLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * @return the "src" property.
	 */
	public String getSrc() {
		Object src = getAttribute(Constants.PR_SRC);
		if(src==null)
		{
			return null;
		}
		return src.toString();
	}
}
