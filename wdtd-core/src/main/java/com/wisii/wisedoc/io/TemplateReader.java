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
 * @TemplateReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.io;

import static com.wisii.wisedoc.util.WisedocUtil.isEmpty;
import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;

import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.io.IOUtil.FileKind;
import com.wisii.wisedoc.io.wsd.reader.WSDReader;

/**
 * 类功能描述：用于把本地的模板文件【zip】读出为Document
 * 
 * 作者：李晓光 创建日期：2009-4-1
 */
public class TemplateReader implements Reader {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.Reader#read(java.io.InputStream)
	 */
	@Override
	public Document read(InputStream in) throws IOException {
		Object obj = readSource(in, FileKind.WSD);
		if (obj instanceof Document) {
			return (Document) obj;
		}
		return null;
	}

	/**
	 * 从指定的模板文件中读取图标。
	 * 
	 * @param in
	 *            指定模板文件的输入流。
	 * @return {@link BufferedImage} 返回模板文件中的图标，并封装在BufferedImage中。
	 * @throws IOException
	 *             若果zip文件有问题，抛出该异常。
	 */
	public BufferedImage readIcon(InputStream in) throws IOException {
		Object obj = readSource(in, FileKind.ICON);
		if (obj instanceof BufferedImage) {
			return (BufferedImage) obj;
		}
		return null;
	}

	/**
	 * 根据指定的模板名称，读取模板中图标
	 * 
	 * @param name
	 *            指定模板名称
	 * @return {@link BufferedImage} 返回模板文件中的图标，并封装在BufferedImage中。
	 * @throws IOException
	 *             若果zip文件有问题，抛出该异常。
	 */
	public BufferedImage readIcon(String name) throws IOException {
		if (isEmpty(name)) {
			return null;
		}
		File file = new File(name);
		return readIcon(file);
	}

	/**
	 * 根据指定的模板名称，读取模板中图标
	 * 
	 * @param file
	 *            指定模板文件
	 * @return {@link BufferedImage} 返回模板文件中的图标，并封装在BufferedImage中。
	 * @throws IOException
	 *             若果zip文件有问题，抛出该异常。
	 */
	public BufferedImage readIcon(File file) throws IOException {
		if (isNull(file) || !file.exists() || file.isDirectory()) {
			return null;
		}

		FileInputStream stream = new FileInputStream(file);
		return readIcon(stream);
	}

	private Object readSource(InputStream in, FileKind kind) throws IOException {
		Object result = null;
		if (isNull(in)) {
			return result;
		}
		ZipInputStream zip = new ZipInputStream(in);
		ZipEntry entry = zip.getNextEntry();
		try {
			for (; entry != null; entry = zip.getNextEntry()) {
				if (entry.isDirectory()) {
					continue;
				}
				if (!kind.getName().equals(entry.getName())) {
					continue;
				}
				result = readSource(zip, kind);
				break;
			}
		} finally {
			zip.close();
			in.close();
		}
		return result;
	}

	private Object readSource(ZipInputStream zip, FileKind kind)
			throws IOException {
		Object result = null;
		switch (kind) {
		case WSD:
			WSDReader reader = new WSDReader();
			result = reader.read(zip);
			break;
		case ICON:
			result = ImageIO.read(zip);
			break;
		default:
			break;
		}

		return result;
	}
}
