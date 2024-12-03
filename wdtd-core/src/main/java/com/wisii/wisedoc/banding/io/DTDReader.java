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
 * @DTDReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.banding.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import com.wisii.wisedoc.banding.DefaultBindNode;
import com.wisii.wisedoc.banding.io.dtd.DTD;
import com.wisii.wisedoc.banding.io.dtd.DTDParser;
import com.wisii.wisedoc.exception.NorootElementException;

/**
 * 类功能描述：
 * 
 * 作者：zhongyajun 创建日期：2008.10.23
 */
public class DTDReader implements StructureReader
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.io.StructureReader#readStructure(java.io.InputStream)
	 */
	public DataStructureTreeModel readStructure(String file)
	{
		InputStreamReader streamReader = null;
		try
		{
			streamReader = new InputStreamReader(new FileInputStream(file));
		} catch (Exception e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(streamReader);
		DTDParser dtdparser = null;
		dtdparser = new DTDParser(reader);
		DTD dtd = null;
		try
		{
			dtd = dtdparser.parse();
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DefaultBindNode rootNode = null;
		try
		{
			rootNode = (DefaultBindNode) dtd.getRootElement("");
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (rootNode == null)
		{
			try
			{
				throw new NorootElementException("您选择的DTD文件没有指定根节点！");
			} catch (NorootElementException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new DataStructureTreeModel(rootNode, false,null);
	}

	public HashMap<String, DefaultBindNode> getAllElement()
	{
		return DTD.elementMap;
	}
}
