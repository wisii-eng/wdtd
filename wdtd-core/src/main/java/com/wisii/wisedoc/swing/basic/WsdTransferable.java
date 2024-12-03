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
 * @WsdTransferer.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing.basic;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import com.wisii.wisedoc.banding.BindNode;


/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2010-12-24
 */
public final class WsdTransferable implements Transferable
{
	public final static DataFlavor stringFlavor = DataFlavor.stringFlavor;
	public final static DataFlavor WSDFlavor = new DataFlavor(WSDData.class,
			"WiseDoc  Data");
	public final static DataFlavor BindNodeFlavor = new DataFlavor(
			BindNodeData.class, "WiseDoc bindnode");
	// 带样式信息的数据
	private WSDData data;
	private BindNodeData bindnodedata;

	public WsdTransferable(WSDData data)
	{
		this.data = data;
	}

	public WsdTransferable(BindNode node)
	{
		this.bindnodedata = new BindNodeData(node);
	}

	/* (non-Javadoc)
	* @see java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer.DataFlavor)
	*/
	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException
	{
		if (stringFlavor.equals(flavor))
		{
			if (data != null)
			{
				return data.getText();
			} else if (bindnodedata != null)
			{
				return bindnodedata.getName();
			}
		} else if (WSDFlavor.equals(flavor))
		{
			return data;
		} else if (BindNodeFlavor.equals(flavor))
		{
			return bindnodedata;
		}
		throw new UnsupportedFlavorException(flavor);
	}

	/* (non-Javadoc)
	 * @see java.awt.datatransfer.Transferable#getTransferDataFlavors()
	 */
	@Override
	public DataFlavor[] getTransferDataFlavors()
	{
		// TODO Auto-generated method stub
		return new DataFlavor[]
		{ stringFlavor, WSDFlavor, BindNodeFlavor };
	}

	/* (non-Javadoc)
	 * @see java.awt.datatransfer.Transferable#isDataFlavorSupported(java.awt.datatransfer.DataFlavor)
	 */
	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor)
	{
		if (stringFlavor.equals(flavor) || WSDFlavor.equals(flavor)
				|| BindNodeFlavor.equals(flavor))
		{
			return true;
		}
		return false;
	}
}
