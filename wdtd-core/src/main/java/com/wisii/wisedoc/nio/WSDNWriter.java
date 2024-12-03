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

package com.wisii.wisedoc.nio;

import static com.wisii.wisedoc.util.WisedocUtil.isEmpty;
import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.io.Writer;
import com.wisii.wisedoc.io.wsd.WSDWriter;
import com.wisii.wisedoc.nio.WSDIOUtil.WSDFileKind;

public class WSDNWriter implements Writer
{

	private Document document = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.Writer#write(java.io.OutputStream,
	 * com.wisii.wisedoc.document.Document)
	 */
	@Override
	public void write(final OutputStream outStream, final Document document)
			throws IOException
	{
		if (isNull(outStream) || isNull(document))
		{
			return;
		}
		this.document = document;
		final ZipOutputStream zip = new ZipOutputStream(outStream);
		try
		{
			writeItem(WSDFileKind.WSD, zip);
//			writeItem(WSDFileKind.PROPERTY, zip);
//			writeItem(WSDFileKind.DATA, zip);
//			writeItem(WSDFileKind.INFORMATION, zip);
//			writeItem(WSDFileKind.CONF, zip);
			// writeItem(WSDFileKind.IMAGE,zip);
			// writeItem(WSDFileKind.ICON,zip);
		} finally
		{
			zip.close();
			outStream.close();
		}
	}

	private void writeItem(WSDFileKind item, ZipOutputStream zip)
	{
		ZipEntry zipAdd = new ZipEntry(item.getName());
		try
		{
			zip.putNextEntry(zipAdd);
			write(zip, item);
			zip.closeEntry();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void write(final ZipOutputStream zip, final WSDFileKind kind)
			throws IOException
	{
		switch (kind)
		{
			case WSD:
			{
				final WSDWriter wsd = new WSDWriter();
				wsd.write(zip, document);
				break;
			}
				// case PROPERTY:
				// {
				// final PropertyWriter wsd = new PropertyWriter();
				// wsd.write(zip, document);
				// break;
				// }
				// case DATA:
				// {
				// final PropertyWriter wsd = new PropertyWriter();
				// wsd.write(zip, document);
				// break;
				// }
				// case INFORMATION:
				// {
				// final PropertyWriter wsd = new PropertyWriter();
				// wsd.write(zip, document);
				// break;
				// }
				// case CONF:
				// {
				// final PropertyWriter wsd = new PropertyWriter();
				// wsd.write(zip, document);
				// break;
				// }
				// case IMAGE:
				// {
				// final PropertyWriter wsd = new PropertyWriter();
				// wsd.write(zip, document);
				// break;
				// }
				// case ICON:
				// {
				// final PropertyWriter wsd = new PropertyWriter();
				// wsd.write(zip, document);
				// break;
				// }
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.Writer#write(java.lang.String,
	 * com.wisii.wisedoc.document.Document)
	 */
	@Override
	public void write(final String file, final Document document)
			throws IOException
	{
		if (isEmpty(file))
		{
			return;
		}
		final FileOutputStream stream = new FileOutputStream(file,
				Boolean.FALSE);
		write(stream, document);
	}

}
