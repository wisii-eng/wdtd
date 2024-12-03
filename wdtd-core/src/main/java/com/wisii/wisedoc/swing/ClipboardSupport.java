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
 * @ClipboardSupport.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import com.wisii.wisedoc.io.wsd.ElementsWriter;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.swing.basic.WSDData;
import com.wisii.wisedoc.swing.basic.WsdTransferable;
import java.util.List;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.DocumentUtil;

/**
 * 类功能描述：剪切板的录入数据和获取数据功能
 * 
 * 作者：李晓光 创建日期：2007-6-22
 */
public class ClipboardSupport {

	/* 获取系统剪切板 */
	private static Clipboard wiseDocClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

	/**
	 * 
	 * 把指定的字符串，放到剪切板中
	 * 
	 * @param data		指定数据
	 * @return void		无返回值
	 */
	public synchronized static void writeToClipboard(
			final List<CellElement> elements)
	{
		final Clipboard clip = wiseDocClipboard;
		if (elements == null)
		{
			clip.setContents(new StringSelection(""), null);
		} else
		{
			String plaintext =new ElementsWriter().write(elements);
			String text = DocumentUtil.getText(elements);
			WsdTransferable wsdtran = new WsdTransferable(new WSDData(text,
					plaintext));
			clip.setContents(wsdtran, null);
		}

	}

	/**
	 * 
	 * 从剪切板中读取数据
	 *
	 * @return      String		返回剪切板中数据
	 * @exception   UnsupportedFlavorException 读取剪切板中数据时，指定的DataFlavor不支持。
	 * @exception   IOException 指定格式对象无法创建时。
	 */
	public synchronized static String readTextFromClipboard() {
		String result = null;
		final Clipboard clip = wiseDocClipboard;
		if(!clip.isDataFlavorAvailable(DataFlavor.stringFlavor))
			return result;
		final Transferable pasteData = clip.getContents(null);
		if(pasteData == null)
			return result;
		
		if (!pasteData.isDataFlavorSupported(DataFlavor.stringFlavor))
			return result;
		try {
			result = (String) pasteData.getTransferData(DataFlavor.stringFlavor);
		} catch (final UnsupportedFlavorException e) {
			LogUtil.errorException(e.getMessage(), e);
		} catch (final IOException e) {
			LogUtil.errorException(e.getMessage(), e);
		}
		return result;
	}
	/**
	 * 
	 * 从剪切板中读取数据
	 *
	 * @return      String		返回剪切板中数据
	 * @exception   UnsupportedFlavorException 读取剪切板中数据时，指定的DataFlavor不支持。
	 * @exception   IOException 指定格式对象无法创建时。
	 */
	public synchronized static List<CellElement> readElementsFromClipboard()
	{
		final Clipboard clip = wiseDocClipboard;
		if (!clip.isDataFlavorAvailable(WsdTransferable.WSDFlavor))
			return null;
		final Transferable pasteData = clip.getContents(null);
		if (pasteData == null)
			return null;
		try
		{
			WSDData data = (WSDData) pasteData
					.getTransferData(WsdTransferable.WSDFlavor);
			if (data != null)
			{
				return data.getElements();
			}
		} catch (final Exception e)
		{
			e.printStackTrace();
			// LogUtil.errorException(e.getMessage(), e);
		}
		return null;
	}
	/**
	 * 
	 * 从剪切板中读取数据
	 *
	 * @return      String		返回剪切板中数据
	 * @exception   UnsupportedFlavorException 读取剪切板中数据时，指定的DataFlavor不支持。
	 * @exception   IOException 指定格式对象无法创建时。
	 */
	public synchronized static List<CellElement> readElementsFromClipboardWithoutBindNode()
	{
		final Clipboard clip = wiseDocClipboard;
		if (!clip.isDataFlavorAvailable(WsdTransferable.WSDFlavor))
			return null;
		final Transferable pasteData = clip.getContents(null);
		if (pasteData == null)
			return null;

		if (!pasteData.isDataFlavorSupported(WsdTransferable.WSDFlavor))
			return null;
		try
		{
			WSDData data = (WSDData) pasteData
					.getTransferData(WsdTransferable.WSDFlavor);
			if (data != null)
			{
				return data.getElementsWithoutbindData();
			}
		} catch (final Exception e)
		{
			e.printStackTrace();
			// LogUtil.errorException(e.getMessage(), e);
		}
		return null;
	}
	/**
	 * 
	 * 从剪切板中读取图片
	 *
	 * @return      String		返回剪切板中数据
	 * @exception   UnsupportedFlavorException 读取剪切板中数据时，指定的DataFlavor不支持。
	 * @exception   IOException 指定格式对象无法创建时。
	 */
	public synchronized static Image readImageFromClipboard() {
		Image result = null;
		final Clipboard clip = wiseDocClipboard;
		if(!clip.isDataFlavorAvailable(DataFlavor.imageFlavor))
			return result;
		final Transferable pasteData = clip.getContents(null);
		if(pasteData == null)
			return result;
		
		if (!pasteData.isDataFlavorSupported(DataFlavor.imageFlavor))
			return result;
		try {
			result = (Image) pasteData.getTransferData(DataFlavor.imageFlavor);
		} catch (final UnsupportedFlavorException e) {
			LogUtil.errorException(e.getMessage(), e);
		} catch (final IOException e) {
			LogUtil.errorException(e.getMessage(), e);
		}
		return result;
	}
	/**
	 * 判断剪切板中是否包含数据
	 * @return	{@link Boolean} 如果剪切板中有数据：True，否则：
	 */
	public synchronized final static boolean hasData()
	{
		final Transferable pasteData = wiseDocClipboard.getContents(null);
		return pasteData!=null;
	}
	/**
	 * 判断剪切板中是否包含数据
	 * @return	{@link Boolean} 如果剪切板中有数据：True，否则：
	 */
	public synchronized final static boolean hasElements()
	{
		final Transferable pasteData = wiseDocClipboard.getContents(null);
		if (pasteData == null)
		{
			return false;
		}
		try
		{
			return (pasteData.isDataFlavorSupported(DataFlavor.imageFlavor) && pasteData
					.getTransferData(DataFlavor.imageFlavor) != null)
					|| (pasteData
							.isDataFlavorSupported(WsdTransferable.WSDFlavor) && pasteData
							.getTransferData(WsdTransferable.WSDFlavor) != null);
		} catch (Exception e)
		{
			return false;
		}
	}
}
