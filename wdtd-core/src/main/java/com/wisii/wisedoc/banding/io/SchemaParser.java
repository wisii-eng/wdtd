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

package com.wisii.wisedoc.banding.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.wisii.wisedoc.banding.AttributeBindNode;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.DefaultBindNode;
import com.wisii.wisedoc.io.xsl.util.FoXsltConstants;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 
 * 类功能描述：解析schema文件，解析的结果存储在XMLRootNodes中。 作者：zhongyajun 创建日期：2008-10-10
 * 
 * 本类的一些限定： 1.对于<xs:choice>，解析出其中的所有element元素。
 * 
 */

public class SchemaParser
{

	/* schema文件中schema元素的命名空间前缀。 */
	private String NS = "";

	// 字符串数据类型
	private String STRING = "";

	// 日期数据类型
	private String DATE = "";

	// 时间数据类型
	private String TIME = "";

	// 十进制数数据类型
	private String DECIMAL = "";

	// 整数数据类型
	private String INTEGER = "";

	// boolean型数据类型
	private String BOOLEAN = "";

	/**
	 * schema文件产生的document对象，要求产生此对象的DocumentBilderFactory factory
	 * 对象做如下操作：factory.setNamespaceAware(true); 否则无法解析。
	 */
	private Document doc;

	// 传入的字符串和相应的节点的对应mao
	private HashMap<String, BindNode> XMLRootNodes = new HashMap<String, BindNode>();

	// schema的根节点
	private Element root_document;

	// 型数据类型对应关系map
	private HashMap<String, Integer> type = new HashMap<String, Integer>();

	/**
	 * 构造函数。
	 * 
	 * @param doc
	 *            通过schema文件产生的文档对象。
	 */
	public SchemaParser(Document doc)
	{
		this.doc = doc;
	}

	/**
	 * 通过传入的字符串获取相应的节点的树形结果作为根节点。
	 * 
	 * @param rootName
	 *            传入的字符串
	 * @return 根节点的树形结构。
	 * @throws Exception
	 */
	public BindNode getResult(String rootName)
	{
		parse(rootName);
		return XMLRootNodes.get(rootName);
	}

	/**
	 * 若不提供xml的根节点的名称，则返回第一个结果。
	 * 
	 * @return 根节点的树形结构。
	 * @throws NoRootDeclException
	 */
	public BindNode getResult()
	{
		parse();
		if (XMLRootNodes != null && XMLRootNodes.size() > 0)
		{
			BindNode result_default = XMLRootNodes.values().iterator().next();
			return result_default;
		}
		return null;
	}

	/**
	 * 从根节点开始解析schema文件，没有给定名称，解析找到的第一个元素。
	 * 
	 * @throws NoRootDeclException
	 */
	private void parse()
	{
		parse("");
	}

	/**
	 * 从根节点开始解析schema文件,只解析指定名称的根节点。
	 * 
	 * @param rootElementName
	 *            要解析的根结点的名称，即对应schema文件中的 element元素的name属性的值。
	 * @throws Exception
	 */
	private void parse(String rootElementName)
	{
		root_document = doc.getDocumentElement();
		// root_document--<xsd:schema
		// xmlns:xsd="http://www.w3.org/2001/XMLSchema">
		if (root_document.getLocalName() == null)
		{
			LogUtil.warn("DoucumentBuilderFactory 工厂不支持命名空间，请将对命名空间的支持打开："
					+ "factory.setNamespaceAware(true) .");
			return;
		}
		if (root_document.getPrefix() != null)
		{
			NS = root_document.getPrefix() + ":";
		}
		setCons();
		setTypeMap();
		List<Element> rootElementList = getListChildrenByName(root_document,
				"element");
		Element rootElement = null;

		if (rootElementList == null)
		{
			LogUtil.debug("错误的shema文件，没有可以解析的元素！");
		} else
		{
			int elementNumber = rootElementList.size();
			List<String> eleName = new ArrayList<String>();
			List<String> childEleName = new ArrayList<String>();

			for (int i = 0; i < elementNumber; i++)
			{
				Element item = rootElementList.get(i);
				eleName.add(item.getAttribute("name"));
				childEleName = addChildEleName(childEleName);
			}
			int eleNumber = eleName.size();
			int childEleNumber = childEleName.size();
			if (childEleNumber == 0)
			{
				rootElement = rootElementList.get(0);
			} else if (childEleNumber > 0)
			{
				for (int m = 0; m < eleNumber; m++)
				{
					String name = eleName.get(m);
					boolean flg = true;
					for (int n = 0; n < childEleNumber; n++)
					{
						String childname = childEleName.get(n);
						if (name.equalsIgnoreCase(childname))
						{
							flg = false;
							break;
						}
					}
					if (flg)
					{
						rootElement = getElementByName(name);
					}
					break;
				}
			}
			DefaultBindNode rootNode = null;
			rootNode = (DefaultBindNode) getNode(rootElement);
			setRootElement(rootElementName, rootNode);
		}
	}

	/**
	 * 
	 * 获取从传入的Element中生成的元素节点
	 * 
	 * @param rootElement
	 *            传入的Element
	 * @return rootNode 生成的元素节点
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private BindNode getNode(Element rootElement)
	{
		DefaultBindNode rootNode = new DefaultBindNode(null,
				getDataType(rootElement.getAttribute("type")), -1, rootElement
						.getAttribute("name"));
		List<Element> childList = getListChildren(rootElement);
		if (childList != null && !childList.isEmpty())
		{
			for (int i = 0; i < childList.size(); i++)
			{
				Element child = childList.get(i);
				String eleName = child.getLocalName();
				if ("complexType".equalsIgnoreCase(eleName))
				{
					rootNode = (DefaultBindNode) getNodeFComplexType(child,
							rootNode);
				}
			}
		}
		return rootNode;
	}

	/**
	 * 
	 * 将child生成的子元素节点添加到currNode中，
	 * 
	 * @param child
	 *            Element元素
	 * @param currNode
	 *            元素节点
	 * @return currNode 添加了子节点的元素节点
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private BindNode getNodeFComplexType(Element child, BindNode currNode)
	{
		List<Element> childList = getListChildren(child);
		if (childList != null && !childList.isEmpty())
		{
			for (int i = 0; i < childList.size(); i++)
			{
				Element childComplexType = childList.get(i);
				String eleComplexTypeChildName = childComplexType
						.getLocalName();
				if ("simpleContent".equalsIgnoreCase(eleComplexTypeChildName))
				{
					currNode = getNodeFSimpleContent(childComplexType, currNode);
				} else if ("sequence".equalsIgnoreCase(eleComplexTypeChildName)
						|| "all".equalsIgnoreCase(eleComplexTypeChildName)
						|| "choice".equalsIgnoreCase(eleComplexTypeChildName))
				{
					NodeList children = childComplexType.getChildNodes();
					if (children != null)
					{
						for (int j = 0; j < children.getLength(); j++)
						{
							Node node = children.item(j);
							if (node instanceof Element)
							{
								Element current = (Element) node;
								String elementName = current.getLocalName();
								if ("sequence".equals(elementName))
								{
									NodeList childrensequence = current
											.getChildNodes();
									if (childrensequence != null)
									{
										for (int k = 0; k < childrensequence
												.getLength(); k++)
										{
											Node nodek = childrensequence
													.item(k);
											if (nodek instanceof Element)
											{
												Element childchildrensequence = (Element) nodek;
												String eleChildrenSequenceChildName = childchildrensequence
														.getLocalName();
												if ("element"
														.equalsIgnoreCase(eleChildrenSequenceChildName))
												{
													DefaultBindNode elechoiceNode = (DefaultBindNode) getNodeFElement(childchildrensequence);
													// elechoiceNode.setParent(currNode);
													currNode
															.addChild(elechoiceNode);
												}
											}
										}
									}
								} else if ("element"
										.equalsIgnoreCase(elementName))
								{
									DefaultBindNode elechoiceNode = (DefaultBindNode) getNodeFElement(current);
									// elechoiceNode.setParent(currNode);

									currNode.addChild(elechoiceNode);
								} else if ("group".equals(elementName))
								{
									List<BindNode> childGroup = new ArrayList<BindNode>();
									childGroup = getNodeFGroup(current,
											childGroup);
									int size = childGroup.size();
									if (size > 0)
									{
										for (int l = 0; l < size; l++)
										{
											DefaultBindNode elechoiceNode = (DefaultBindNode) childGroup
													.get(l);
											// elechoiceNode.setParent(currNode);
											currNode.addChild(elechoiceNode);
										}
									}
								}
							}
						}
					}
				} else if ("attribute"
						.equalsIgnoreCase(eleComplexTypeChildName))
				{
					AttributeBindNode attrNode = (AttributeBindNode) getNodeFAttribute(childComplexType);
					// attrNode.setParent(currNode);
					currNode.addChild(attrNode);
				} else if ("attributeGroup"
						.equalsIgnoreCase(eleComplexTypeChildName))
				{
					String nameref = childComplexType.getAttribute("ref");
					List<BindNode> childGroup = new ArrayList<BindNode>();
					childGroup = getNodeFAttributeGroup(currNode, nameref);
					int sizeGroup = childGroup.size();
					if (sizeGroup > 0)
					{
						for (int p = 0; p < sizeGroup; p++)
						{
							AttributeBindNode attriNode = (AttributeBindNode) childGroup
									.get(p);
							// attriNode.setParent(currNode);
							currNode.addChild(attriNode);
						}
					}
				}
			}
		}
		return currNode;
	}

	/**
	 * 
	 * 将从simpleContent获取到的元素节点添加到currNode，并设置currNode的类型
	 * 
	 * @param simplecontent
	 *            simplecontent元素
	 * @return currNode 添加了子元素节点的节点
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private BindNode getNodeFSimpleContent(Element simplecontent,
			BindNode currNode)
	{
		NodeList childSimpleContentList = simplecontent.getChildNodes();
		if (childSimpleContentList != null)
		{
			for (int i = 0; i < childSimpleContentList.getLength(); i++)
			{
				Node node = childSimpleContentList.item(i);
				if (node instanceof Element)
				{
					Element childSimpleContent = (Element) node;
					String eleSimpleContentChildName = childSimpleContent
							.getLocalName();
					if ("extension".equalsIgnoreCase(eleSimpleContentChildName))
					{
						String typestr = childSimpleContent
								.getAttribute("base");
						currNode.setDataType(getDataType(typestr));
						List<BindNode> childNodeList = addChildExtension(childSimpleContent);
						if (childNodeList != null)
						{
							int size = childNodeList.size();
							if (size > 0)
							{
								for (int j = 0; j < size; j++)
								{
									BindNode childNode = childNodeList.get(j);
									// childNode.setParent(currNode);
									currNode.addChild(childNode);
								}
							}
						}
					}
				}
			}
		}
		return currNode;
	}

	/**
	 * 
	 * 获得extension元素的元素节点列表
	 * 
	 * @param extension
	 *            extension元素
	 * @return child 子元素列表
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private List<BindNode> addChildExtension(Element extension)
	{
		List<BindNode> child = new ArrayList<BindNode>();
		NodeList children = extension.getChildNodes();
		if (children != null)
		{
			for (int i = 0; i < children.getLength(); i++)
			{
				Node node = children.item(i);
				if (node instanceof Element)
				{
					Element current = (Element) node;
					String eleName = current.getLocalName();
					if ("attribute".equals(eleName))
					{
						child.add(getNodeFAttribute(current));
					} else if ("group".equals(eleName))
					{
						child = getNodeFGroup(current, child);
					}
				}
			}
		}
		return child;
	}

	/**
	 * 
	 * 获得group中的元素节点列表
	 * 
	 * @param current
	 *            group元素
	 * @return childlist 子元素节点列表
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private List<BindNode> getNodeFGroup(Element current,
			List<BindNode> childlist)
	{
		String name = current.getAttribute("ref");
		if (!name.equalsIgnoreCase(""))
		{
			name = name.replaceFirst(NS, "");
		}
		Element elementGroup = getGroup(name);
		NodeList childrenelementGroup = elementGroup.getChildNodes();
		if (childrenelementGroup != null)
		{
			for (int i = 0; i < childrenelementGroup.getLength(); i++)
			{
				Node node = childrenelementGroup.item(i);
				if (node instanceof Element)
				{
					Element child = (Element) node;
					if (child.getLocalName().equalsIgnoreCase("choice")
							|| child.getLocalName()
									.equalsIgnoreCase("sequence")
							|| child.getLocalName().equalsIgnoreCase("all"))
					{
						NodeList childrenelementchoice = child.getChildNodes();
						if (childrenelementchoice != null)
						{
							for (int j = 0; j < childrenelementchoice
									.getLength(); j++)
							{
								Node nodej = childrenelementchoice.item(j);
								if (nodej instanceof Element)
								{
									Element currentj = (Element) nodej;
									if (currentj.getLocalName()
											.equalsIgnoreCase("element"))
									{
										DefaultBindNode elechoiceNode = (DefaultBindNode) getNodeFElement(currentj);
										childlist.add(elechoiceNode);
									}
								}
							}
						}
					}
				}
			}
		}
		return childlist;
	}

	/**
	 * 
	 * 获得element元素中的元素节点
	 * 
	 * @param element
	 *            element元素
	 * @return attNode 返回的元素节点
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private BindNode getNodeFElement(Element element)
	{
		DefaultBindNode attNode = new DefaultBindNode(null, getDataType(""),
				-1, element.getAttribute("name"));
		String strName = element.getAttribute("name");
		String strRef = element.getAttribute("ref");
		if (!strName.equalsIgnoreCase(""))
		{
			String strType = element.getAttribute("type");
			if (getTypeFlg(strType))
			{
				attNode.setDataType(getDataType(strType));
			} else if ("".equalsIgnoreCase(strType))
			{
				NodeList children = element.getChildNodes();
				if (children != null)
				{
					for (int i = 0; i < children.getLength(); i++)
					{
						Node node = children.item(i);
						if (node instanceof Element)
						{
							Element current = (Element) node;
							String eleName = current.getLocalName();
							if ("complexType".equals(eleName))
							{
								attNode = (DefaultBindNode) getNodeFComplexType(
										current, attNode);
							}
						}
					}
				}
			} else
			{
				attNode = (DefaultBindNode) getNodeFComplexType(strType);
			}
		} else if (!strRef.equalsIgnoreCase(""))
		{
			attNode = (DefaultBindNode) getNode(getElementByName(strRef));
		}
		return attNode;
	}

	/**
	 * 
	 * 获得complexType元素中元素节点
	 * 
	 * @param name
	 *            complexType元素的那么属性名
	 * @return bindNote 返回的元素节点
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private BindNode getNodeFComplexType(String name)
	{
		Element element = getComplexTypeByName(name);
		DefaultBindNode bindNote = new DefaultBindNode(null, getDataType(""),
				-1, name);
		bindNote = (DefaultBindNode) getNodeFComplexType(element, bindNote);
		return bindNote;
	}

	/**
	 * 
	 * 根据传入的元素名取出schema的子元素中的element元素
	 * 
	 * @param name
	 *            元素名
	 * @return element 返回的element
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private Element getElementByName(String name)
	{
		Element element = null;
		NodeList children = root_document.getChildNodes();
		if (children != null)
		{
			for (int i = 0; i < children.getLength(); i++)
			{
				Node node = children.item(i);
				if (node instanceof Element)
				{
					Element current = (Element) node;
					String eleName = current.getLocalName();
					if ("element".equals(eleName)
							&& current.getAttribute("name").equalsIgnoreCase(
									name))
					{
						element = current;
					}
				}
			}
		}
		return element;
	}

	/**
	 * 
	 * 返回attributeGroup元素中包含的数据元素节点
	 * 
	 * @param attribute
	 *            attribute元素
	 * @return attNode 返回的元素节点
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private List<BindNode> getNodeFAttributeGroup(BindNode bindnode, String name)
	{
		List<BindNode> list = new ArrayList<BindNode>();
		Element arrributeGroup = getArrributeGroup(name);
		NodeList children = arrributeGroup.getChildNodes();
		if (children != null)
		{
			for (int i = 0; i < children.getLength(); i++)
			{
				Node node = children.item(i);
				if (node instanceof Element)
				{
					Element current = (Element) node;
					if (current.getLocalName().equalsIgnoreCase("attribute"))
					{
						AttributeBindNode attNode = (AttributeBindNode) getNodeFAttribute(current);
						list.add(attNode);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * 返回attribute元素中包含的数据元素节点
	 * 
	 * @param attribute
	 *            attribute元素
	 * @return attNode 返回的元素节点
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private BindNode getNodeFAttribute(Element attribute)
	{
		String type = attribute.getAttribute("type");
		if (type.equalsIgnoreCase(""))
		{
			type = getType(attribute);
		}
		int length = getLength(getLength(attribute));
		AttributeBindNode attNode = new AttributeBindNode(null,
				getDataType(type), length, attribute.getAttribute("name"));
		return attNode;
	}

	/**
	 * 
	 * 根据传入的name获取schema子元素中的对应的group元素
	 * 
	 * @param name
	 *            group元素的name
	 * @return element 相应的group元素
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private Element getArrributeGroup(String name)
	{
		Element element = null;
		NodeList children = root_document.getChildNodes();
		if (children != null)
		{
			for (int i = 0; i < children.getLength(); i++)
			{
				Node node = children.item(i);
				if (node instanceof Element)
				{
					Element current = (Element) node;
					if (current.getLocalName().equalsIgnoreCase(
							"attributeGroup")
							&& current.getAttribute("name").equalsIgnoreCase(
									name))
					{
						element = current;
						break;
					}
				}
			}
		}
		return element;
	}

	/**
	 * 
	 * 根据传入的name获取schema子元素中的对应的group元素
	 * 
	 * @param name
	 *            group元素的name
	 * @return element 相应的group元素
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private Element getGroup(String name)
	{
		Element element = null;
		NodeList children = root_document.getChildNodes();
		if (children != null)
		{
			for (int i = 0; i < children.getLength(); i++)
			{
				Node node = children.item(i);
				if (node instanceof Element)
				{
					Element current = (Element) node;
					if (current.getLocalName().equalsIgnoreCase("group")
							&& current.getAttribute("name").equalsIgnoreCase(
									name))
					{
						element = current;
						break;
					}
				}
			}
		}
		return element;
	}

	/**
	 * 
	 * 从attribute元素中获取到其子元素节点
	 * 
	 * @param attribute
	 *            attribute元素
	 * @return rootChildElementList 子元素节点列表
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private List<Element> getListChildren(Element attributeelement)
	{

		if (attributeelement != null)
		{
			List<Element> rootChildElementList = new ArrayList<Element>();
			NodeList children = attributeelement.getChildNodes();
			if (children != null)
			{
				for (int i = 0; i < children.getLength(); i++)
				{
					Node node = children.item(i);
					if (node instanceof Element)
					{
						Element current = (Element) node;
						rootChildElementList.add(current);
					}
				}
			}
			return rootChildElementList;
		}
		return null;
	}

	/**
	 * 
	 * 获得attribute元素中包含的类型信息
	 * 
	 * @param element
	 *            attribute元素
	 * @return type 数据类型字符串
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private String getType(Element element)
	{
		String type = "";
		NodeList children = element.getChildNodes();
		if (children != null)
		{
			for (int i = 0; i < children.getLength(); i++)
			{
				Node node = children.item(i);
				if (node instanceof Element)
				{
					Element current = (Element) node;
					String eleName = current.getLocalName();
					if ("simpleType".equals(eleName))
					{
						NodeList childrenSimpleType = current.getChildNodes();
						if (childrenSimpleType != null)
						{
							for (int j = 0; j < childrenSimpleType.getLength(); j++)
							{
								Node nodej = childrenSimpleType.item(j);
								if (nodej instanceof Element)
								{
									Element currentj = (Element) nodej;
									String eleNamej = currentj.getLocalName();
									if ("restriction".equals(eleNamej))
									{
										type = getRestrictionType(currentj);
									}
								}
							}
						}
					}
				}
			}
		}
		return type;
	}

	private String getLength(Element element)
	{
		String length = "";
		NodeList children = element.getChildNodes();
		if (children != null)
		{
			for (int i = 0; i < children.getLength(); i++)
			{
				Node node = children.item(i);
				if (node instanceof Element)
				{
					Element current = (Element) node;
					String eleName = current.getLocalName();
					if ("simpleType".equals(eleName))
					{
						NodeList childrenSimpleType = current.getChildNodes();
						if (childrenSimpleType != null)
						{
							for (int j = 0; j < childrenSimpleType.getLength(); j++)
							{
								Node nodej = childrenSimpleType.item(j);
								if (nodej instanceof Element)
								{
									Element currentj = (Element) nodej;
									String eleNamej = currentj.getLocalName();
									if ("restriction".equals(eleNamej))
									{
										length = getRestrictionLength(currentj);
									}
								}
							}
						}
					}
				}
			}
		}
		return length;
	}

	/**
	 * 
	 * 获得restriction元素中包含的类型信息
	 * 
	 * @param elementrestriction
	 *            restriction元素
	 * @return type 数据类型字符串
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private String getRestrictionType(Element elementrestriction)
	{
		String type = "";
		String elementrestrictionType = elementrestriction.getAttribute("base");
		if (getTypeFlg(elementrestrictionType))
		{
			type = elementrestrictionType;
		} else
		{
			Element simpleType = getSimpleTypeByName(elementrestrictionType);
			type = getTypeFSimpleType(simpleType);
		}
		return type;
	}

	private String getRestrictionLength(Element elementrestriction)
	{
		String length = "";
		NodeList children = elementrestriction.getChildNodes();
		if (children != null)
		{
			for (int i = 0; i < children.getLength(); i++)
			{
				Node node = children.item(i);
				if (node instanceof Element)
				{
					Element current = (Element) node;
					String eleName = current.getLocalName();
					if ("length ".equals(eleName))
					{
						length = current.getAttribute("value");
					}
				}
			}
		}
		return length;
	}

	/**
	 * 
	 * 取出SimpleType中定义的数据类型
	 * 
	 * @param simpletype
	 *            需要从其中取数据类型的simpletype元素
	 * @return str 数据类型
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private String getTypeFSimpleType(Element simpletype)
	{
		String str = "";
		NodeList children = simpletype.getChildNodes();
		Node node = children.item(0);
		if (node instanceof Element)
		{
			Element current = (Element) node;
			String eleName = current.getLocalName();
			if ("restriction".equals(eleName))
			{
				str = current.getAttribute("base");
			} else
			{
				str = getTypeFSimpleType(current);
			}
		}
		return str;
	}

	/**
	 * 
	 * 获取schema文件的根节点下的名为name的simpleType元素
	 * 
	 * @param name
	 *            simpleType的name
	 * @return element simpleType元素
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private Element getSimpleTypeByName(String name)
	{
		Element element = null;
		NodeList children = root_document.getChildNodes();
		if (children != null)
		{
			for (int i = 0; i < children.getLength(); i++)
			{
				Node node = children.item(i);
				if (node instanceof Element)
				{
					Element current = (Element) node;
					String eleName = current.getLocalName();
					if ("simpleType".equals(eleName)
							&& current.getAttribute("name").equalsIgnoreCase(
									name))
					{
						element = current;
					}
				}
			}
		}
		return element;
	}

	/**
	 * 
	 * 获取schema文件的根节点下的名为name的complexType元素
	 * 
	 * @param name
	 *            complexType的name
	 * @return element complexType元素
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private Element getComplexTypeByName(String name)
	{
		Element element = null;
		NodeList children = root_document.getChildNodes();
		if (children != null)
		{
			for (int i = 0; i < children.getLength(); i++)
			{
				Node node = children.item(i);
				if (node instanceof Element)
				{
					Element current = (Element) node;
					String eleName = current.getLocalName();
					if ("complexType".equals(eleName)
							&& current.getAttribute("name").equalsIgnoreCase(
									name))
					{
						element = current;
					}
				}
			}
		}
		return element;
	}

	/**
	 * 
	 * 根据传入的字符类型判断是否是schema中的基本类型
	 * 
	 * @param typestr
	 *            字符类型
	 * @return type 整数类型
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private boolean getTypeFlg(String typestr)
	{
		boolean type = false;
		if (typestr.startsWith(NS))
		{
			type = true;
		}
		return type;
	}

	/**
	 * 
	 * 获取元素的所有指定元素名的元素
	 * 
	 * @param element
	 *            要从中获取子元素的元素
	 * @param name
	 *            要从中获取子元素的元素名
	 * @return rootChildElementList 指定元素名的子元素
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private List<Element> getListChildrenByName(Element element, String name)
	{
		List<Element> rootChildElementList = new ArrayList<Element>();
		NodeList children = element.getChildNodes();
		if (children != null)
		{
			for (int i = 0; i < children.getLength(); i++)
			{
				Node node = children.item(i);
				if (node instanceof Element)
				{
					Element current = (Element) node;
					String eleName = current.getLocalName();
					if (name.equals(eleName))
					{
						rootChildElementList.add(current);
					}
				}
			}
		}
		return rootChildElementList;
	}

	/**
	 * 
	 * 设置字符类型常量
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private void setCons()
	{
		STRING = NS + FoXsltConstants.STRINGLOWER;
		DATE = NS + FoXsltConstants.DATETYPELOWER;
		TIME = NS + FoXsltConstants.TIMELOWER;
		DECIMAL = NS + FoXsltConstants.DECIMALLOWER;
		INTEGER = NS + FoXsltConstants.INTEGERLOWER;
		BOOLEAN = NS + FoXsltConstants.BOOLEANLOWER;
	}

	/**
	 * 
	 * 传入字符类型获取相应的整数类型
	 * 
	 * @param typestr
	 *            传入的字符类型
	 * @return type 返回的整数类型
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private int getDataType(String typestr)
	{
		int type = BindNode.STRING;
		HashMap<String, Integer> map = getTypeMap();
		if (map.containsKey(typestr))
		{
			type = map.get(typestr);
		}
		return type;
	}

	/**
	 * 
	 * 设置根节点map
	 * 
	 * @param rootElementName
	 *            节点名
	 * @param rootNode
	 *            完整的树结构节点
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private void setRootElement(String rootElementName, DefaultBindNode rootNode)
	{
		String nodeName = rootNode.getXPath();
		if ("".equalsIgnoreCase(rootElementName))
		{
			XMLRootNodes.put("", rootNode);
		} else if (rootElementName.equalsIgnoreCase(nodeName))
		{
			XMLRootNodes.put(rootElementName, rootNode);
		} else
		{
			List<BindNode> childList = rootNode.getChildren();
			if (childList != null && !childList.isEmpty())
			{
				for (int i = 0; i < childList.size(); i++)
				{
					DefaultBindNode node = (DefaultBindNode) childList.get(i);
					setRootElement(rootElementName, node);
				}
			}
		}
	}

	/**
	 * 
	 * 设置字符类型和整数类型的对应关系的map
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private void setTypeMap()
	{
		type.put(STRING, BindNode.STRING);
		type.put(DECIMAL, BindNode.DECIMAL);
		type.put(INTEGER, BindNode.INTEGER);
		type.put(TIME, BindNode.TIME);
		type.put(DATE, BindNode.DATE);
		type.put(BOOLEAN, BindNode.BOOLEAN);
	}

	/**
	 * 
	 * 获得字符类型和整数类型的对应关系的map
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private HashMap<String, Integer> getTypeMap()
	{
		return this.type;
	}

	private List<String> addChildEleName(List<String> list)
	{
		return list;
	}

	public int getLength(String length)
	{
		int chang = BindNode.UNLIMT;
		if (length != null && length.length() >= 0)
		{
			try
			{
				chang = Integer.parseInt(length);
			} catch (Exception e)
			{
			}
		}
		return chang;
	}
}
