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
 * @XMLReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.banding.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.wisii.wisedoc.banding.AttributeBindNode;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.DefaultBindNode;
import com.wisii.wisedoc.exception.DataStructureException;

/**
 * 读取XML文件，生成文档结构树
 * 
 * 作者：zhangqiang 创建日期：2007-6-11
 */
public class XMLReader implements StructureReader {
	private Stack _nodePath;
	private BindNode _root;
	private List<NameSpace> spaces;

	/**
	 * 
	 * 根据指定文件流返回对应的数据结构
	 * 
	 * @param in
	 *            ：文件输入流
	 * @return DataStructureTreeModel：文档结构
	 * @exception Exception
	 *                ：文件语法错误
	 */
	public DataStructureTreeModel readStructure(String file)
			throws DataStructureException {
		_nodePath = new Stack();
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser;
		try {
			saxParserFactory.setNamespaceAware(true);
			saxParser = saxParserFactory.newSAXParser();
			saxParser.parse(new File(file), new DefaultHandler() {

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {
					super.startElement(uri, localName, qName, attributes);
					if (_root == null) {
						_root = createTreeNode(null, qName, false);
						_nodePath.push(_root);
						for (int i = 0; i < attributes.getLength(); i++)
							addAttribute(attributes.getQName(i));

					} else {
						BindNode node = addNode(qName, false);
						_nodePath.push(node);
						for (int i = 0; i < attributes.getLength(); i++)
							addAttribute(attributes.getQName(i));

					}
				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {
					super.endElement(uri, localName, qName);
					_nodePath.pop();
				}
				@Override
				public void startPrefixMapping(String prefix, String uri)
						throws SAXException {
					NameSpace ns=new NameSpace("xmlns:"+prefix, uri);
					if(spaces==null)
					{
						spaces=new ArrayList<NameSpace>();
						spaces.add(ns);
					}
					else
					{
						if(!spaces.contains(ns))
						{
							spaces.add(ns);
						}
					}
				}

			});
		} catch (Exception e) {
			throw new DataStructureException(e);
		}
		return new DataStructureTreeModel(_root, false,spaces);
	}

	/*
	 * 
	 * 根据节点名创建节点
	 * 
	 * @param nodeName：节点名 @return MutableTreeNode：返回文档节点 @exception
	 */

	private BindNode createTreeNode(BindNode parent, String nodeName,
			boolean isatt) {
		// if(nodeName.startsWith("@"))
		BindNode node;
		if (isatt) {
			node = new AttributeBindNode(parent, BindNode.STRING, BindNode.UNLIMT, nodeName);
		} else {
			node = new DefaultBindNode(parent, BindNode.STRING, BindNode.UNLIMT, nodeName);
		}
		return node;
	}

	/*
	 * 
	 * 添加名为name的节点到文档数据结构节点中去，注意同一层中的 同名节点不再添加
	 * 
	 * @param name：节点名 @return MutableTreeNode：返回文档节点 @exception
	 */
	private BindNode addNode(String name, boolean isatt) {
		BindNode parent = (BindNode) _nodePath.peek();
		for (int j = 0; j < parent.getChildCount(); j++) {
			DefaultBindNode child = (DefaultBindNode) parent.getChildAt(j);
			if (child.getNodeName().equals(name))
				return child;
		}

		BindNode node = createTreeNode(parent, name, isatt);
		parent.addChild(node);
		return node;
	}

	/*
	 * 
	 * 根据节点属性名创建数据结构节点
	 * 
	 * @param nodeName：节点名 @return MutableTreeNode：返回文档节点 @exception
	 */
	private void addAttribute(String name) {
		if(name.startsWith("xmlns:"))
		{
			return;
		}
		addNode(name, true);
	}

}
