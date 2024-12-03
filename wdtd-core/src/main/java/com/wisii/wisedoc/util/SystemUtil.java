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
package com.wisii.wisedoc.util;

import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import com.wisii.wisedoc.configure.ConfigureUtil;


//import com.wisii.init.ReadConfig;

/**
 * <p>
 * Title: Util
 * </p>
 * 
 * <p>
 * Description: definition constant
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not hongjia
 * @version 1.0
 */
public class SystemUtil
{

	// charset
	public static final String CHARSET = "GBK";

	/** configuration file's relative path */
	public static final String CONFRELATIVEPATH = "conf"+File.separator;

	/** graphics file's relative path */
	public static final String GRAPHICSRELATIVEPATH = "graphics"+File.separator;

	/** scheme component: http:// */
	public static final String HTTPSCHEME = "http://";

	/** scheme component: file: */
	public static final String FILESCHEME = "file:";

	/** Defines the default target resolution (72dpi) for editor */
	public static final float DEFAULT_TARGET_RESOLUTION = 72.0f; // dpi

	/** Defines the default source resolution (25.4dpi) for editor */
	public static final float DEFAULT_SOURCE_RESOLUTION = 25.4f; // dpi


	/** *英寸 */
	public static final int INCH = 72000;

	/** *点 */
	public static final int PT = 25400;


	// 获得baseurl
	public static String getBaseURL()
	{
		return getURL("base.baseurl");
	}

	public static String getURL(String ss)
	{
		String path = ConfigureUtil.getProperty(ss);
		if (path == null)
		{
			path = "";
		}
      
		if (path == null || path.trim().equals("")
				|| path.trim().startsWith("./"))
		{
			path = System.getProperty("user.dir"); 
//			String relatePath = path;
//			if (relatePath != null && path.trim().startsWith("./"))
//			{
//				relatePath = relatePath.trim().substring(2);
//			}
//
//			URL url = SystemUtil.class.getClassLoader().getResource(
//					"wddeconfig.properties");
//			path = url.getPath();
//	
//			if (path.indexOf("WEB-INF") != -1)
//			{
//				path = path.substring(0, url.getPath().length() - 37)
//						+ relatePath;
//			}
//			else if (path.indexOf(".jar!") != -1)
//			{
//				// lib/wdems.jar!/wddeconfig.properties
//				path = path.substring(0, url.getPath().length() - 43)
//						+ relatePath;
//			}
//			else
//			{
//				path = path.substring(0, url.getPath().length() - 25)
//						+ relatePath;
//			}
//			
		}
		if (path != null && !path.trim().endsWith("/")
				&& !path.trim().endsWith("\\"))
		{
			path = path + "/";
		}

		path = path.replaceAll("%20", " ");

		return path;
	}

	// --------------------------------------------------add by zkl.
	// ---------------------add by liuxiao 20080102
	// start-----------------------//
	/**
	 * 对字符串进行URLDecoder.encode(strEncoding)编码
	 * 
	 * @param String
	 *            src 要进行编码的字符串
	 * 
	 * @return String 进行编码后的字符串
	 */

	public static String getURLEncode(String src)
	{
		String requestValue = "";
		try
		{

			requestValue = URLEncoder.encode(src, SystemUtil.CHARSET);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return requestValue;
	}

	/**
	 * 对字符串进行URLDecoder.decode(strEncoding)解码
	 * 
	 * @param String
	 *            src 要进行解码的字符串
	 * 
	 * @return String 进行解码后的字符串
	 */

	public static String getURLDecoderdecode(String src)
	{
		String requestValue = "";
		try
		{

			requestValue = URLDecoder.decode(src, SystemUtil.CHARSET);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return requestValue;
	}

	// ---------------------add by liuxiao 20080102 end-----------------------//
	public static void main(String[] args) throws Exception
	{

		System.out.print(getBaseURL());

	}

	/**
	 * 该方法用于将输入流复制成两个输入流。输出一个输入流数组，长度为2
	 * 
	 * @author liuxiao
	 * @param cInputStream
	 * @return InputStream[]
	 * 
	 */
	public static InputStream[] getCopyInputStream(InputStream cInputStream)
	{
		try
		{
			byte[] bytes = new byte[1];
			InputStream[] vArrayInputStream = new InputStream[2];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
	
			while (cInputStream.read(bytes) != -1)
			{
				baos.write(bytes);
			}
			baos.flush();
			baos.close();
			vArrayInputStream[0] = new ByteArrayInputStream(baos.toByteArray());
			vArrayInputStream[1] = new ByteArrayInputStream(baos.toByteArray());
			return vArrayInputStream;
		}
		catch (NullPointerException e)
		{

			return null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

}
