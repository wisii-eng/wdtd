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
 * @WSDWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

import com.wisii.wisedoc.configure.ConfigureConstants;
import com.wisii.wisedoc.configure.ConfigureUtil;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.io.IOUtil;
import com.wisii.wisedoc.io.Writer;

/**
 * 类功能描述：wsd文件生成类
 * 
 * 作者：zhangqiang 创建日期：2008-8-14
 */
public class WSDWriter implements Writer
{

	public static final String XMLDEFINE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n";

	public static final String WSDBEGINDEFINE = "<wisedoc version =\""
			+ ConfigureUtil.getProperty(ConfigureConstants.REALSE) + "\">\n";

	public static final String WSDENDDEFINE = "</wisedoc>";

	private String DOCMENTDEFINE = "<document>\n";

	private String DOCMENTENDDEFINE = "</document>\n";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.Writer#write(java.io.OutputStream,
	 * javax.swing.text.Document)
	 */
	public void write(OutputStream outstream, Document document)
			throws IOException
	{
		if (outstream != null && document != null)
		{
			DocumentWirter ew = new DocumentWirter();
			try
			{
				String outs = ew.write(document);
				if (outs != null && !outs.equals(""))
				{
					outs = XMLDEFINE + WSDBEGINDEFINE + DOCMENTDEFINE + outs
							+ DOCMENTENDDEFINE + WSDENDDEFINE;
					byte[] bts = outs.getBytes("UTF-8");
					outstream.write(IOUtil.encrypt(bts));
					outstream.flush();
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				throw new IOException("写文件错误");
			} finally
			{
				if (!(outstream instanceof ZipOutputStream))
				{
					outstream.close();
				}
			}

		}
	}

	public void write(String file, Document document) throws IOException
	{
		if (file != null && document != null)
		{
			write(new FileOutputStream(file), document);
		}
	}
}
