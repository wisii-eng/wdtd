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
 * @DocumentPositionUtil.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.util.ArrayList;
import java.util.List;
import com.wisii.wisedoc.document.listener.DocumentChange;
import com.wisii.wisedoc.document.listener.ElementChange;
/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-10-24
 */
public class DocumentPositionUtil
{
	public static CellElement findpreviousInline(CellElement element)
	{
		if(element == null)
		{
			return null;
		}
		Element parent = element.getParent();
		CellElement returns = null;
		if (parent != null)
		{
			// 先找当前元素之前的inline级别的元素，
			returns = findpreviousInlineinParent(parent, element);
			if(returns==null)
			{
				returns = findpreviousInlineinChildren(element);
			}
		}
		return returns;
	}

	public static CellElement findnextInline(CellElement element)
	{
		Element parent = element.getParent();
		CellElement returns = null;
		if (parent != null)
		{
//			先找当前元素之后的inline级别的元素，
			returns = findnextInlineinParent(parent, element); 
			if(returns==null)
			{
				returns = findnextInlineinChildren(element);
			}
		}
		return returns;
	}
	public static CellElement findFirstDocumentpositionCell(List<CellElement> elements)
	{
		if(elements != null&&!elements.isEmpty())
		{
			List<CellElement> inlines = getElements(elements);
			if(inlines != null&&!inlines.isEmpty()){
			return inlines.get(0);
			}
		}
		return null;
	}
	public static CellElement findLastDocumentpositionCell(List<CellElement> elements)
	{
		if (elements != null && !elements.isEmpty())
		{
			List<CellElement> poscells = getElements(elements);
			if (poscells != null && !poscells.isEmpty())
			{
				return poscells.get(poscells.size() - 1);
			}
		}
		return null;
	}
	/**
	 * 
	 * 获得当前文档改变后的新的doumentposition
	 *
	 * @param      
	 * @return   
	 * @exception
	 */
	public static DocumentPosition getPositionofChanges(List<DocumentChange> changes)
	{
		DocumentPosition npos = null;
		if (!changes.isEmpty())
		{
			ElementChange lastelementchange = null;
			int size = changes.size();
			for (int i = 0; i < size; i++)
			{
				DocumentChange change = changes.get(i);
				if (change != null && change instanceof ElementChange)
				{
					ElementChange elementchange = (ElementChange) change;
					// 如果有插入元素操作，则找到最后一个插入的操作，否则找到最后一个删除操作
					if (lastelementchange == null
							|| (lastelementchange != null && (lastelementchange
									.getType() != ElementChange.INSERT || elementchange
									.getType() == ElementChange.INSERT)))
					{
						lastelementchange = elementchange;
					}
				}
			}
			// 如果找到最后一个删除或插入操作，则更新光标
			if (lastelementchange != null)
			{
				CellElement nposelement = null;
				boolean isstart = false;
				if (lastelementchange.getType() == ElementChange.REMOVE)
				{
					int offset = lastelementchange.getOffset();
					Element editp = lastelementchange.getParentElement();
					if (offset == editp.getChildCount())
					{
						offset = offset - 1;
						CellElement element = null;
						if (offset >= 0)
						{
							element = (CellElement) editp.getChildAt(offset);
						} else
						{
							if (editp instanceof CellElement)
							{
								element = (CellElement) editp;
							}
						}
						if (element != null)
						{
							nposelement = findpreviousInlineinChildren(element);
							if (nposelement == null)
							{
								nposelement = findpreviousInline(element);
							}
						}
					} else
					{
						CellElement element = (CellElement) editp
								.getChildAt(offset);
						// 如果删除的位置是当前段落的起始位置，则光标移动到当前段落的起始位置
						if (offset == 0 && element instanceof Inline)
						{
							nposelement = element;
							isstart = true;
						} else
						{
							nposelement = DocumentPositionUtil
									.findpreviousInline(element);
							if (nposelement != null
									&& editp.equals(nposelement.getParent())
									&& offset == 0)
							{
								isstart = true;
							}
						}

					}
				} else
				{
					List<CellElement> elements = lastelementchange
							.getEditelements();
					// 如果需要定位到插入元素的第一个inline元素
					if (lastelementchange.shouldPositionFirst())
					{
						nposelement = findFirstDocumentpositionCell(elements);
					} else
					{
						nposelement = findLastDocumentpositionCell(elements);
					}
				}
				if (nposelement != null)
				{

					if (lastelementchange.shouldPositionFirst())
					{
						isstart = true;
					}

					npos = new DocumentPosition(nposelement, isstart);
				}

			}
		}
		return npos;
	}
	private static List<CellElement> getElements(List<CellElement> elements)
	{
		List<CellElement> returns = new ArrayList<CellElement>();
		if (elements != null && !elements.isEmpty())
		{
			int size = elements.size();
			for (int i = 0; i < size; i++)
			{
				CellElement element = elements.get(i);
				if (element != null)
				{
					if ((element instanceof Inline)||element.getChildCount()==0||element instanceof ZiMoban)
					{
						returns.add(element);
					}
//					else if(element instanceof ZiMoban)
//					{
//						continue;
//					}
					else
					{
						returns.addAll(getElements(element.getAllChildren()));
					}
				}
			}
		}
		return returns;
	}
//	/**
//	 * 
//	 * 找到该对象中子对象属于c标识的class类型的对象
//	 * 
//	 * @param element
//	 *            ：要查找的对象，c对象类型
//	 * @return
//	 * @exception
//	 */
//	private static List<CellElement> getElements(CellElement element)
//	{
//		List<CellElement> list = new ArrayList<CellElement>();
//		if (element != null && element.getChildCount() > 0)
//		{
//			Iterator<CellElement> it = element.getChildren();
//			while (it.hasNext())
//			{
//				CellElement child = it.next();
//				// 如果当前对象为该类型的对象，则将该对象加入到返回列表中
//				if ((child instanceof Inline)||child.getChildCount()==0||element instanceof ZiMoban)
//				{
//					list.add(child);
//				}
//				// 如果不是该类型的对象，则递归找到子对象中属于该类型的子对对象
//				else
//				{
//					list.addAll(getElements(child));
//				}
//			}
//		}
//		return list;
//	}
	private static CellElement findpreviousInlineinParent(Element parent,
			CellElement element)
	{
		int index = parent.getIndex(element);
		// 搜索element节点前的元素，看其是否包含inline级别的元素
		for (int i = index-1; i >= 0; i--)
		{
			Element el = (Element) parent.getChildAt(i);
			if (el != null)
			{

				CellElement elfind = findpreviousInlineinChildren(el);
				if (elfind != null)
				{
					return elfind;
				}
			}

		}
		Element nparent = parent.getParent();
		// 往上层父节点搜索
		if (nparent != null && parent instanceof CellElement&&!(parent instanceof TableCell)&&!(parent instanceof BlockContainer)&&!(parent instanceof Flow)&&!(parent instanceof ZiMoban))
		{
			return findpreviousInlineinParent(nparent, (CellElement) parent);
		}
		return null;
	}

	private static CellElement findpreviousInlineinChildren(Element el)
	{
		int size = el.getChildCount();
		if (size == 0||el instanceof Inline||el instanceof ZiMoban) 
		{
			return (CellElement) el;
		}
//		else if(el instanceof ZiMoban)
//		{
//			return null;
//		}
		else
		{
			for (int i = size; i >= 0; i--)
			{
				Element elc = (Element) el.getChildAt(i);
				if (elc != null)
				{
					return findpreviousInlineinChildren(elc);
				}
			}
		}
		return null;
	}

	private static CellElement findnextInlineinParent(Element parent,
			CellElement element)
	{
		int index = parent.getIndex(element);
		int size = parent.getChildCount();
		// 搜索element节点前的元素，看其是否包含inline级别的元素
		for (int i = index + 1; i < size; i++)
		{
			Element el = (Element) parent.getChildAt(i);
			if (el != null)
			{

				CellElement elfind = findnextInlineinChildren(el);
				if (elfind != null)
				{
					return elfind;
				}
			}

		}
		Element nparent = parent.getParent();
		// 往上层父节点搜索
		if (nparent != null && parent instanceof CellElement&&!(parent instanceof TableCell)&&!(parent instanceof BlockContainer)&&!(parent instanceof Flow)&&!(parent instanceof Flow))
		{
			return findnextInlineinParent(nparent, (CellElement) parent);
		}
		return null;
	}

	private static CellElement findnextInlineinChildren(Element el)
	{
		int size = el.getChildCount();
		if (size == 0||el instanceof Inline||el instanceof ZiMoban)
		{
			return (CellElement) el;
		}
//		else if(el instanceof ZiMoban)
//		{
//			return null;
//		}
		else
		{
			for (int i = 0; i < size; i++)
			{
				Element elc = (Element) el.getChildAt(i);
				if (elc != null)
				{
					return findnextInlineinChildren(elc);
				}
			}
		}
		return null;
	}
}
