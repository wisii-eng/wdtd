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

/**
 * 
 * 类功能描述：转换表信息相关类，包括下拉列表内置的数据源也是该类定义
 * 
 * 作者：zhangqiang 创建日期：2009-8-29
 */
public class TransformTable implements DataSource
{

	// 转换表数据
	List<List<String>> datas = new ArrayList<List<String>>();

	public TransformTable(List<List<String>> datas)
	{
		if (datas != null)
		{
			this.datas.addAll(datas);
		}
	}

	/**
	 * @返回 datas变量的值
	 */
	public final List<List<String>> getDatas()
	{
		return datas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datas == null) ? 0 : datas.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransformTable other = (TransformTable) obj;
		if (datas == null)
		{
			if (other.datas != null)
				return false;
		} else if (!datas.equals(other.datas))
			return false;
		return true;
	}

	// public String toString()
	// {
	// String code = "";
	// if (datas != null)
	// {
	// code = code + ElementUtil.startElement(DataSource.WDWENS + "data");
	// String name = getName();
	// code = code + ElementUtil.startElement(name);
	// for (ElementItem current : datas)
	// {
	// code = code + current.toString();
	// }
	// code = code + ElementUtil.endElement(name);
	// code = code + ElementUtil.endElement(DataSource.WDWENS + "data");
	// }
	// return code;
	// }
	//
	// public TransformTable clone()
	// {
	// TransformTable table = new TransformTable();
	//
	// String namenew = new String(name);
	// table.setName(namenew);
	// if (items != null)
	// {
	// List<ElementItem> itemsnew = new ArrayList<ElementItem>();
	// for (ElementItem current : items)
	// {
	// ElementItem newitem = current.clone();
	// itemsnew.add(newitem);
	// }
	// table.setItems(itemsnew);
	// }
	// return table;
	// }
	//
	// public int getAttributesize()
	// {
	// int size = 0;
	// if (items != null && !items.isEmpty())
	// {
	// ElementItem root = items.get(0);
	//
	// List<ElementItem> children = root.getChildren();
	//
	// if (children != null && !children.isEmpty())
	// {
	// ElementItem item = items.get(0);
	// String name = item.getName();
	// if (name.equals("item"))
	// {
	// List<AttributeItem> att = item.getAttribute();
	// size = att != null ? att.size() : 0;
	// } else
	// {
	// List<ElementItem> childres = item.getChildren();
	// if (childres != null && !childres.isEmpty())
	// {
	// ElementItem itemchild = childres.get(0);
	// List<AttributeItem> attchild = itemchild.getAttribute();
	// if (attchild != null)
	// {
	// size = attchild.size();
	// }
	// }
	// }
	// }
	// }
	// return size;
	// }
	//
	// public Object[][] getModel(int type)
	// {
	// Object[][] model;
	// int rows = getRowSize();
	// if (rows == 0)
	// {
	// model = getDefaultModel();
	// } else
	// {
	// ElementItem root = new ElementItem();
	// root.setChildren(items);
	// if (root != null)
	// {
	// if (type == 1)
	// {
	// int column = getElementsize() + 1;
	//
	// model = new Object[rows + 1][column];
	// model[0][0] = "列标题";
	// List<String> heard = root.getHeardString(1);
	//
	// if (!heard.isEmpty())
	// {
	// for (int i = 0; i < heard.size(); i++)
	// {
	// model[0][i + 1] = heard.get(i);
	// }
	// }
	//
	// List<String> elements = root.getElementNamesString();
	// if (!elements.isEmpty())
	// {
	// for (int i = 0; i < elements.size(); i++)
	// {
	// int columnnumber = i % column;
	// int rownumber = (i / column) + 1;
	// model[rownumber][columnnumber] = elements.get(i);
	// }
	// }
	// } else if (type == 2)
	// {
	// int column = getAttributesize() + 1;
	// model = new Object[rows + 1][column];
	// model[0][0] = "searchKey";
	// List<String> heard = root.getHeardString(2);
	// if (!heard.isEmpty())
	// {
	// for (int i = 0; i < heard.size(); i++)
	// {
	// model[0][i + 1] = heard.get(i);
	// }
	// }
	// List<String> elements = root.getAttributesString();
	// if (!elements.isEmpty())
	// {
	// for (int i = 0; i < elements.size(); i++)
	// {
	// int columnnumber = i % column;
	// int rownumber = (i / column) + 1;
	// model[rownumber][columnnumber] = elements.get(i);
	// }
	// }
	//
	// } else
	// {
	// model = getDefaultModel();
	// }
	// } else
	// {
	// model = getDefaultModel();
	// }
	// }
	// return model;
	// }
	//
	// public Object[][] getSimpleModel(int type)
	// {
	// Object[][] model;
	//
	// Object[][] dmodel =
	// { null };
	//
	// if (items != null && items.size() > 0)
	// {
	// // ElementItem root = items.get(0);
	// // List<ElementItem> childrens = root.getChildren();
	//
	// ElementItem one = items.get(0);
	// List<AttributeItem> listone = one.getAttribute();
	// int column = listone != null && listone.size() > 0 ? listone.size()
	// : 0;
	// if (column == 0)
	// {
	// return dmodel;
	// } else
	// {
	// int rows = items.size();
	// model = new Object[rows][column];
	// for (int i = 0; i < rows; i++)
	// {
	// ElementItem current = items.get(i);
	// List<AttributeItem> listattr = current.getAttribute();
	// if (listattr != null && listattr.size() > 0)
	// {
	// for (int j = 0; j < listattr.size(); j++)
	// {
	// model[i][j] = listattr.get(j).getValue();
	// }
	// } else
	// {
	// return dmodel;
	// }
	// }
	// return model;
	// }
	//
	// } else
	// {
	// return dmodel;
	// }
	// }
	//
	// public Object[][] getDefaultModel()
	// {
	// Object[][] model = new Object[1][3];
	// model[0][0] = "searchKey";
	// model[0][1] = "列名1";
	// model[0][2] = "列名2";
	// return model;
	// }
	//
	// public int getElementsize()
	// {
	// int size = 0;
	// if (items != null && !items.isEmpty())
	// {
	// ElementItem root = items.get(0);
	//
	// List<ElementItem> children = root.getChildren();
	//
	// if (children != null && !children.isEmpty())
	// {
	// ElementItem item = items.get(0);
	// String name = item.getName();
	// if (name.equals("item"))
	// {
	// List<ElementItem> child = item.getChildren();
	// size = child != null ? child.size() : 0;
	// } else
	// {
	// List<ElementItem> childres = item.getChildren();
	// if (childres != null && !childres.isEmpty())
	// {
	// ElementItem itemchild = childres.get(0);
	// List<ElementItem> elementchild = itemchild
	// .getChildren();
	// if (elementchild != null)
	// {
	// size = elementchild.size();
	// }
	// }
	// }
	// }
	// }
	// return size;
	// }
	//
	// public int getRowSize()
	// {
	// int size = 0;
	// if (items != null && !items.isEmpty())
	// {
	// for (ElementItem root : items)
	// {
	// size = size + root.getRowSize();
	// }
	// }
	// return size;
	// }
	//
	// public TreeDSNode getTreeDSNode()
	// {
	// TreeDSNode root = null;
	// if (name != null && !name.equals(""))
	// {
	// root = new TreeNodeDS(null, TreeDSNode.ELEMENT, name);
	// if (items != null && !items.isEmpty())
	// {
	// for (ElementItem current : items)
	// {
	// TreeDSNode currentnode = current.getTreeDSNode();
	// root.addChild(currentnode);
	// }
	// }
	// }
	// return root;
	// }
	//
	// public void sort()
	// {
	// int size = items.size();
	// for (int i = 0; i < size; i++)
	// {
	// ElementItem current = items.get(i);
	// String name = current.getName().trim();
	// if (!name.equals("item"))
	// {
	// if (i < size - 1)
	// {
	// for (int j = i + 1; j < size; j++)
	// {
	// ElementItem item = items.get(j);
	// String itemname = item.getName().trim();
	// if (name.equals(itemname))
	// {
	// List<ElementItem> list = item.getChildren();
	// current.addElementItem(list);
	// items.remove(item);
	// size = size - 1;
	// j = j - 1;
	// }
	// }
	// }
	// }
	// }
	// }
}
