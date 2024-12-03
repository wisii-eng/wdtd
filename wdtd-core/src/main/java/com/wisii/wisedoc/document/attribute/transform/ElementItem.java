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

package com.wisii.wisedoc.document.attribute.transform;

import java.util.ArrayList;
import java.util.List;

public class ElementItem
{

	String name;

	List<ElementItem> children;

	List<AttributeItem> attribute;

	String text;

	public ElementItem()
	{
		name = "";
	}

	public ElementItem(String elementname)
	{
		name = elementname;
	}

	public List<ElementItem> getChildren()
	{
		return children;
	}

	public void setChildren(List<ElementItem> children)
	{
		this.children = children;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public void addElementItem(ElementItem child)
	{
		if (children == null)
		{
			children = new ArrayList<ElementItem>();
		}
		children.add(child);
	}

	public void addAttributeItem(AttributeItem child)
	{
		if (attribute == null)
		{
			attribute = new ArrayList<AttributeItem>();
		}
		attribute.add(child);
	}

	public void addElementItem(List<ElementItem> child)
	{
		if (child != null)
		{
			if (children == null)
			{
				children = new ArrayList<ElementItem>();
			}
			for (ElementItem current : child)
			{
				children.add(current);
			}
		}

	}

	public void addAttributeItem(List<AttributeItem> child)
	{
		if (child != null)
		{
			if (attribute == null)
			{
				attribute = new ArrayList<AttributeItem>();
			}
			for (AttributeItem current : child)
			{
				attribute.add(current);
			}
		}
	}

	public String toString()
	{
		String code = "";
		if (name != null && !name.equals(""))
		{
			code = code + "<";
			code = code + name;
			if (attribute != null && !attribute.isEmpty())
			{
				for (AttributeItem current : attribute)
				{
					code = code + current.toString();
				}
			}
			code = code + ">";
			if (text != null)
			{
				code = code + text;
			}
			if (children != null && !children.isEmpty())
			{
				for (ElementItem current : children)
				{
					code = code + current.toString();
				}
			}
			code = code + "</";
			code = code + name;
			code = code + ">";
		}
		return code;
	}

	public List<AttributeItem> getAttribute()
	{
		return attribute;
	}

	public void setAttribute(List<AttributeItem> attribute)
	{
		this.attribute = attribute;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<String> getElementNamesString()
	{
		List<String> list = new ArrayList<String>();
		if (children != null && !children.isEmpty())
		{
			for (ElementItem current : children)
			{
				String name = current.getName();
				if (name.equals("item"))
				{
					list.add("");
					List<ElementItem> cattr = current.getChildren();
					if (cattr != null && !cattr.isEmpty())
					{
						for (ElementItem currentai : cattr)
						{
							list.add(currentai.getText());
						}
					}
				} else
				{

					List<ElementItem> cele = current.getChildren();
					if (cele != null && !cele.isEmpty())
					{
						for (ElementItem currentei : cele)
						{
							list.add(name);
							List<ElementItem> cattr = currentei.getChildren();
							if (cattr != null && !cattr.isEmpty())
							{
								for (ElementItem currentai : cattr)
								{
									list.add(currentai.getText());
								}
							}
						}
					}
				}
			}
		}
		return list;
	}

	public List<String> getAttributesString()
	{
		List<String> list = new ArrayList<String>();
		if (children != null && !children.isEmpty())
		{
			for (ElementItem current : children)
			{
				String name = current.getName();
				if (name.equals("item"))
				{
					list.add("");
					List<AttributeItem> cattr = current.getAttribute();
					if (cattr != null && !cattr.isEmpty())
					{
						for (AttributeItem currentai : cattr)
						{
							list.add(currentai.getValue());
						}
					}
				} else
				{

					List<ElementItem> cele = current.getChildren();
					if (cele != null && !cele.isEmpty())
					{
						for (ElementItem currentei : cele)
						{

							list.add(name);
							List<AttributeItem> cattr = currentei
									.getAttribute();
							if (cattr != null && !cattr.isEmpty())
							{
								for (AttributeItem currentai : cattr)
								{
									list.add(currentai.getValue());
								}
							}
						}
					}
				}
			}
		}
		return list;
	}

	public List<String> getHeardString(int type)
	{
		List<String> list = new ArrayList<String>();
		if (children != null && !children.isEmpty())
		{
			ElementItem firstchild = children.get(0);
			String name = firstchild.getName();
			if (name.equals("item"))
			{
				if (type == 1)
				{
					List<ElementItem> cele = firstchild.getChildren();
					if (cele != null && !cele.isEmpty())
					{
						for (ElementItem current : cele)
						{
							list.add(current.getName());
						}
					}
				} else if (type == 2)
				{
					List<AttributeItem> cattr = firstchild.getAttribute();
					if (cattr != null && !cattr.isEmpty())
					{
						for (AttributeItem current : cattr)
						{
							list.add(current.getKey());
						}
					}
				}
			} else
			{
				List<ElementItem> firstchildlist = firstchild.getChildren();
				ElementItem firstgroudchild = firstchildlist.get(0);
				if (type == 1)
				{
					List<ElementItem> cele = firstgroudchild.getChildren();
					if (cele != null && !cele.isEmpty())
					{
						for (ElementItem current : cele)
						{
							list.add(current.getName());
						}
					}
				} else if (type == 2)
				{
					List<AttributeItem> cattr = firstgroudchild.getAttribute();
					if (cattr != null && !cattr.isEmpty())
					{
						for (AttributeItem current : cattr)
						{
							list.add(current.getKey());
						}
					}
				}
			}
		}
		return list;
	}

	public ElementItem clone()
	{
		ElementItem newelement = new ElementItem();
		String namenew = new String(name);
		newelement.setName(namenew);
		if (children != null)
		{
			List<ElementItem> childrennew = new ArrayList<ElementItem>();
			for (ElementItem current : children)
			{
				ElementItem currentclone = current.clone();
				childrennew.add(currentclone);
			}
			newelement.setChildren(childrennew);
		}
		if (attribute != null)
		{
			List<AttributeItem> attributenew = new ArrayList<AttributeItem>();
			for (AttributeItem current : attribute)
			{
				AttributeItem currentclone = current.clone();
				attributenew.add(currentclone);
			}
			newelement.setAttribute(attributenew);
		}

		if (text != null)
		{
			String textnew = new String(text);
			newelement.setText(textnew);
		}

		return newelement;
	}

	public int getRowSize()
	{
		int size = 0;
		if (name.equals("item"))
		{
			size = size + 1;
		} else
		{
			if (children != null)
			{
				for (ElementItem child : children)
				{
					size = size + child.getRowSize();
				}
			}
		}
		return size;
	}

	public TreeDSNode getTreeDSNode()
	{
		TreeDSNode root = null;
		if (name != null && !name.equals(""))
		{
			root = new TreeNodeDS(null, TreeDSNode.ELEMENT, name);
			
			if (children != null && !children.isEmpty())
			{
				for (ElementItem current : children)
				{
					TreeDSNode currentnode = current.getTreeDSNode();
					root.addChild(currentnode);
				}
			}
			if (attribute != null && !attribute.isEmpty())
			{
				for (AttributeItem current : attribute)
				{
					TreeDSNode currentnode = current.getTreeDSNode();
					root.addChild(currentnode);
				}
			}
			if (text != null && !text.equals(""))
			{
				TreeDSNode textnode = new TreeNodeDS(root, TreeDSNode.TEXT,
						text);
				root.addChild(textnode);
			}
		}
		return root;
	}

	public static void main(String[] args)
	{
		ElementItem item = new ElementItem();
		item.setName("element");
		ElementItem chlid = new ElementItem();
		chlid.setName("child");
		item.addElementItem(chlid);
		item.setText("text");
		AttributeItem att = new AttributeItem("key", "value");
		item.addAttributeItem(att);
	}
}
