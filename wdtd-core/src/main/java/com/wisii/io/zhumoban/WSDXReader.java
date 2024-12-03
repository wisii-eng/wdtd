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

package com.wisii.io.zhumoban;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.wisii.io.zhumoban.FixedFileUtil.WSDXKind;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.io.Reader;
import com.wisii.wisedoc.io.wsd.reader.WSDReader;

public class WSDXReader implements Reader
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.Reader#read(java.io.InputStream)
	 */
	@Override
	public Document read(InputStream in) throws IOException
	{
		Object obj = readSource(in, WSDXKind.WSD);
		if (obj instanceof Document)
		{
			return (Document) obj;
		}
		return null;
	}

	private Object readSource(InputStream in, WSDXKind kind)
			throws IOException
	{
		Object result = null;
		if (isNull(in))
		{
			return result;
		}
		ZipInputStream zip = new ZipInputStream(in);
		ZipEntry entry = zip.getNextEntry();
		try
		{
			for (; entry != null; entry = zip.getNextEntry())
			{
				if (entry.isDirectory())
				{
					continue;
				}
				
				if (!kind.getName().equals(entry.getName()))
				{
					continue;
				}
				result = readSource(zip, kind);
				break;
			}
		} finally
		{
			zip.close();
			in.close();
		}
		return result;
	}

	private Object readSource(ZipInputStream zip, WSDXKind kind)
			throws IOException
	{
		Object result = null;
		switch (kind)
		{
			case WSD:
				WSDReader reader = new WSDReader();
				result = reader.read(zip);
				break;
			// case ICON:
			// result = ImageIO.read(zip);
			// break;
			default:
				break;
		}

		return result;
	}
}
