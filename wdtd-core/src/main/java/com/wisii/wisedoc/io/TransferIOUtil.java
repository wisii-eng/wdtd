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
 * @TransferIO.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.io.wsd.reader.CopyReaderHandler;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-10-6
 */
public class TransferIOUtil {

	public static Object getTransferObject(String s,DataStructureTreeModel datamodel) {
		Object TransferObject = null;
		if (s != null && !s.isEmpty()) {
			try {
				byte[] bs = s.getBytes("utf-8");
				ByteArrayInputStream bin = new ByteArrayInputStream(bs);
				SAXParserFactory saxParserFactory = SAXParserFactory
						.newInstance();
				SAXParser saxParser = saxParserFactory.newSAXParser();
				CopyReaderHandler handler = new CopyReaderHandler(datamodel);
				saxParser.parse(bin, handler);
				return handler.getElements();
			} catch (Exception e) {
				TransferObject = s;
				e.printStackTrace();
				LogUtil.debugException("数据转换错误", e);
			}

		}
		return TransferObject;
	}

}
