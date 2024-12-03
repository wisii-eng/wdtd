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
 * @DocumentUtil.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wisii.wisedoc.apps.FOUserAgent;
import com.wisii.wisedoc.apps.FovFactory;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.listener.AttributeChange;
import com.wisii.wisedoc.document.listener.DataStructChange;
import com.wisii.wisedoc.document.listener.DocumentChange;
import com.wisii.wisedoc.document.listener.ElementChange;
import com.wisii.wisedoc.document.svg.Canvas;
import com.wisii.wisedoc.document.svg.WordArtText;
import com.wisii.wisedoc.view.select.DocumentPositionRange;

/**
 * 类功能描述：文档一些相关工具方法
 * 
 * 作者：zhangqiang 创建日期：2008-9-25
 */
public class DocumentUtil
{
	// 用户的排版设置类
	private static FOUserAgent USERAGERNT;

	/**
	 * 
	 * 获得指定元素的公共属性值
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public static Map<Integer, Object> getElementAttributes(
			final List<CellElement> elements)
	{
		if (elements != null && !elements.isEmpty())
		{
			final Map<Integer, Object> map = new HashMap<Integer, Object>();
			final int size = elements.size();
			Attributes old = null;
			Attributes current;
			for (int i = 0; i < size; i++)
			{
				final CellElement element = elements.get(i);
				if (element != null)
				{
					current = element.getAttributes();
					if (current != null && !current.equals(old))
					{

						final Set<Integer> oldkeyset = map.keySet();
						for (int oldkey : oldkeyset)
						{
							final Object tmpvalue = current
									.getAttribute(oldkey);
							if (tmpvalue == null)
							{
								map.put((Integer) oldkey, Constants.NULLOBJECT);
							}
						}
						final Iterator<Integer> keyit = current
								.getAttributeKeys();
						while (keyit.hasNext())
						{
							final int key = keyit.next();
							final Object valued = map.get(key);
							// 如果属性key已经不相等，则直接continue
							if (Constants.NULLOBJECT.equals(valued))
							{
								continue;
							} else
							{
								final Object tmpvalue = current
										.getAttribute(key);
								if (valued == null)
								{
									if (tmpvalue != null)
									{
										if (i == 0)
										{
											map.put((Integer) key, tmpvalue);
										} else
										{
											map.put(key, Constants.NULLOBJECT);
										}
									}
								} else if (!valued.equals(tmpvalue))
								{
									map
											.put((Integer) key,
													Constants.NULLOBJECT);
								}
							}
						}
					}
					old = current;
				}
			}
			return map;
		}

		return null;
	}

	public static List<CellElement> getElementsofDocument(final Document doc,
			final Class c)
	{
		if (doc == null || c == null)
		{
			return null;
		}
		int count = doc.getChildCount();
		List<CellElement> returns = new ArrayList<CellElement>();
		for (int i = 0; i < count; i++)
		{
			returns.addAll(getElements((CellElement) doc.getChildAt(i), c));
		}
		return returns;
	}

	/**
	 * 
	 * 获得传入的对象中类型为c，包括传入的对象及其子对象中属于该类型的所有对象，返回的对象 则将会包括所有类型为c的子对象
	 * 
	 * @param elements
	 *            :传入的对象，c：类型
	 * @return 返回传入对象，包括传入的对象的子对象的类型为c的所有元素
	 * @exception
	 */
	public static List<CellElement> getElements(
			final List<CellElement> elements, final Class c)
	{
		if (elements == null || elements.isEmpty() || c == null)
		{
			return null;
		}
		final List<CellElement> returns = new ArrayList<CellElement>();

		for (CellElement element : elements)
		{
			if (element != null)
			{
				if (returns.contains(element))
				{
					continue;
				} else if (c.isInstance(element))
				{
					returns.add(element);
				} else if (c.equals(Inline.class)
						&& element instanceof WordArtText
						&& c.isInstance(element.getParent()))
				{
					returns.add((CellElement) element.getParent());
				} else
				{
					List<CellElement> subresult = getElements(element, c);
					if (subresult != null && !subresult.isEmpty())
					{
						for (CellElement elem : subresult)
						{
							if (!returns.contains(elem))
							{
								returns.add(elem);
							}
						}
					}
				}
			}
		}
		return returns;
	}

	/**
	 * 
	 * 获得传入的对象的叶子节点对象，如果传入元素不是叶子节点的对象，则深度遍历找到所有叶子节点对象
	 * 
	 * @param elements
	 *            :传入的对象
	 * @return 返回传入对象的所有叶子节点对象
	 * @exception
	 */
	public static List<CellElement> getLeafElements(
			final List<CellElement> elements)
	{
		if (elements == null || elements.isEmpty())
		{
			return null;
		}
		final List<CellElement> returns = new ArrayList<CellElement>();
		final int size = elements.size();
		for (int i = 0; i < size; i++)
		{
			final CellElement element = elements.get(i);
			if (element != null)
			{
				if (returns.contains(element))
				{
					continue;
				} else if (element.getChildCount() == 0)
				{
					returns.add(element);
				} else
				{
					returns.addAll(getLeafElements(element.getChildren(0)));
				}
			}
		}
		return returns;
	}

	/**
	 * 
	 * 找到该对象中子对象属于c标识的class类型的对象
	 * 
	 * @param element
	 *            ：要查找的对象，c对象类型
	 * @return
	 * @exception
	 */
	public static List<CellElement> getElements(final CellElement element,
			final Class c)
	{
		final List<CellElement> list = new ArrayList<CellElement>();
		if (element instanceof ZiMoban)
		{
			return list;
		}
		if (element != null && element.getChildCount() > 0 && c != null)
		{
			final Iterator<CellElement> it = element.getChildren();
			while (it.hasNext())
			{
				final CellElement child = it.next();
				// 如果当前对象为该类型的对象，则将该对象加入到返回列表中
				if (list.contains(child))
				{
					continue;
				} else if (c.isInstance(child))
				{
					/*
					 * if(child instanceof BasicLink){//如果要支持编辑目录，把这部分放开
					 * list.addAll(getElements(child, c)); }else
					 */
					list.add(child);
				} else
				{
					List<CellElement> subresult = getElements(child, c);
					if (subresult != null && !subresult.isEmpty())
					{
						for (CellElement elem : subresult)
						{
							if (!list.contains(elem))
							{
								list.add(elem);
							}
						}
					}
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * 获得elements中祖先对象类型为指定类型的所有祖先对象
	 * 
	 * @param elements
	 *            ：要查找父对象的元素 c:父对象类型
	 * @return 所有类型为c的祖先对象
	 * @exception
	 */
	public static List<CellElement> getParentElementofType(
			final List<CellElement> elements, final Class c)
	{
		List<CellElement> parentelements = null;
		if (elements != null && !elements.isEmpty() && c != null)
		{
			parentelements = new ArrayList<CellElement>();
			final int size = elements.size();
			for (int i = 0; i < size; i++)
			{
				CellElement element = elements.get(i);
				while (element != null && !(element instanceof Flow)
						&& !c.isInstance(element))
				{
					element = (CellElement) element.getParent();
				}
				if (c.isInstance(element))
				{
					if (!parentelements.contains(parentelements))
					{
						parentelements.add(element);
					}
				}
			}
		}
		return parentelements;
	}

	/**
	 * 
	 * 获得用户的排版配置对象
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public static FOUserAgent getUserAgent()
	{
		if (USERAGERNT == null)
		{
			USERAGERNT = new FOUserAgent(FovFactory.newInstance());
		}
		return USERAGERNT;
	}

	public static String getText(final List<CellElement> elements)
	{

		final List<CellElement> inlines = getElements(elements,
				TextInline.class);
		StringBuilder text = null;

		if (inlines != null)
		{
			final int size = inlines.size();
			text = new StringBuilder(size);
			for (int i = 0; i < size; i++)
			{
				text.append(((Inline) inlines.get(i)).getText());
			}
		}
		return text.toString();

		// 老版本代码
		// final List<CellElement> inlines = getElements(elements,
		// TextInline.class);
		// String text = null;
		// if (inlines != null)
		// {
		// text = "";
		// final int size = inlines.size();
		//
		// for (int i = 0; i < size; i++)
		// {
		// final Inline il = (Inline) inlines.get(i);
		// text = text + il.getText();
		// }
		// }
		// return text;
	}

	public static List<Element> getChangeElements(
			final List<DocumentChange> changes)
	{
		final List<Element> changeelements = new ArrayList<Element>();
		if (changes != null && !changes.isEmpty())
		{
			for (final DocumentChange change : changes)
			{
				if (change instanceof ElementChange)
				{
					final ElementChange ec = (ElementChange) change;
					if (ec.getType() == ElementChange.INSERT)
					{
						final List<CellElement> elements = ec.getEditelements();
						for (final Element element : elements)
						{
							if (element != null && !(element instanceof Inline)
									&& !(element instanceof Document)
									&& !changeelements.contains(element))
							{
								changeelements.add(element);
							}
						}
					}
					Element celle = ec.getParentElement();
					if (celle instanceof Canvas)
					{
						celle = celle.getParent();
					}
					if (celle instanceof Inline)
					{
						celle = celle.getParent();
					}
					if (celle instanceof Group)
					{
						Element parente = celle;
						while (parente != null && !(parente instanceof Flow))
						{
							if (parente instanceof Block)
							{
								celle = parente;
								break;
							}
							parente = parente.getParent();
						}
					}
					if (celle != null && !(celle instanceof Flow)
							&& !changeelements.contains(celle))
					{
						changeelements.add(celle);
					}
				} else if (change instanceof DataStructChange)
				{
					// do nothing;
				} else
				{
					final AttributeChange ec = (AttributeChange) change;
					final List<Element> celles = ec.getEditelements();
					if (celles != null && !celles.isEmpty())
					{
						for (Element celle : celles)
						{
							final Element cellep = celle.getParent();

							// 如果是行内对象，则取其父段落对象作为改变的对象
							if (celle instanceof Inline)
							{
								celle = cellep;
								if (celle instanceof Group)
								{
									Element parente = celle;
									while (parente != null
											&& !(parente instanceof Flow))
									{
										if (parente instanceof Block)
										{
											celle = parente;
											break;
										}
										parente = parente.getParent();
									}
								}
							}
							//如果单元格的宽度发生了变化，需要重排整个表
							else if (celle instanceof TableCell
									&& ec
											.getNewvalue()
											.containsKey(
													Constants.PR_INLINE_PROGRESSION_DIMENSION))
							{
								if (cellep != null)
								{
									celle = cellep.getParent();
								}
							} else if (cellep instanceof Canvas)
							{
								celle = cellep.getParent().getParent();
								if (celle instanceof Group)
								{
									Element parente = celle;
									while (parente != null
											&& !(parente instanceof Flow))
									{
										if (parente instanceof Block)
										{
											celle = parente;
											break;
										}
										parente = parente.getParent();
									}
								}
							} else if (cellep instanceof Inline)
							{
								celle = cellep.getParent();
								if (celle instanceof Group)
								{
									Element parente = celle;
									while (parente != null
											&& !(parente instanceof Flow))
									{
										if (parente instanceof Block)
										{
											celle = parente;
											break;
										}
										parente = parente.getParent();
									}
								}
							}
							if (celle != null
									&& !changeelements.contains(celle))
							{
								changeelements.add(celle);
							}
						}
					}
				}

			}
		}
		return changeelements;
	}

	public static List<CellElement> getElements(
			final List<DocumentPositionRange> ranges)
	{
		final List<CellElement> returnl = new ArrayList<CellElement>();
		if (ranges != null)
		{
			for (final DocumentPositionRange range : ranges)
			{
				final List<CellElement> elements = DocumentUtil
						.getElements(range);
				if (elements != null)
				{
					returnl.addAll(elements);
				}
			}
		}
		return returnl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * 
	 * com.wisii.wisedoc.document.Document#getElements(com.wisii.wisedoc.document
	 * .DocumentPosition, com.wisii.wisedoc.document.DocumentPosition)
	 */
	public static List<CellElement> getElements(
			final DocumentPositionRange range)
	{
		final List<CellElement> listcs = new ArrayList<CellElement>();
		if (range != null)
		{
			final DocumentPosition startpos = range.getStartPosition();
			final DocumentPosition endpos = range.getEndPosition();
			final CellElement startc = startpos.getLeafElement();
			final CellElement endc = endpos.getLeafElement();
			CellElement parentstart = startc;
			CellElement parentend;
			// 两者的共同祖先变量
			CellElement comancestor = null;
			// 找到2个光标点第一个相同的祖先元素
			a: while (parentstart != null
					&& !(parentstart instanceof PageSequence))
			{
				parentend = endc;
				while (parentend != null
						&& !(parentend instanceof PageSequence))
				{
					if (parentend.equals(parentstart))
					{
						comancestor = parentstart;
						break a;
					}
					parentend = (CellElement) parentend.getParent();
				}
				parentstart = (CellElement) parentstart.getParent();
			}
			if (comancestor != null)
			{
				if (comancestor == startc)
				{
					comancestor = (CellElement) startc.getParent();
				}
				if (comancestor != null)
				{
					final int startoffset = comancestor
							.getoffsetofPos(startpos);
					final int endoffset = comancestor.getoffsetofPos(endpos);
					// 如果其实元素和结束元素是同一个父对象的子元素
					if ((comancestor == startc.getParent())
							&& (comancestor == endc.getParent()))
					{

						int startindex = startoffset;
						int endindex = endoffset;
						if (!startpos.isStartPos())
						{
							startindex++;
						}
						if (!endpos.isStartPos())
						{
							endindex++;
						}
						return comancestor.getChildren(startindex, endindex);
					} else
					{
						final CellElement starte = comancestor
								.getChildAt(startoffset);
						final CellElement ende = comancestor
								.getChildAt(endoffset);
						// // 如果起始光标点在一个Inline上，则将光标所在段落的光标后的Inline加入到返回列表中去
						// // 注意：光标在一个空的tablecell或block的情况没处理
						CellElement parent = startc;
						if (startc.getParent() != comancestor)
						{
							parent = (CellElement) startc.getParent();
							int index = parent.getIndex(startc);
							if (!startpos.isStartPos())
							{
								index++;
							}
							listcs.addAll(parent.getChildren(index));
						} else
						{
							listcs.addAll(getStartelement(parent, comancestor));
						}
						// 添加光标之间的对象。
						listcs.addAll(comancestor.getChildren(startoffset + 1,
								endoffset));
						// 如果结束光标点在一个Inline上，则将光标所在段落的光标前后的Inline加入到返回列表中去
						// 注意：光标在一个空的tablecell或block的情况没处理
						parent = endc;
						if (endc.getParent() != comancestor)
						{
							parent = (CellElement) endc.getParent();
							listcs.addAll(getEndelement(parent, comancestor));
							int index = parent.getIndex(endc);
							if (!endpos.isStartPos())
							{
								index++;
							}
							listcs.addAll(parent.getChildren(0, index));
						} else
						{
							listcs.addAll(getEndelement(parent, comancestor));
						}

					}
				}
			}

		}
		return listcs;
	}

	/*
	 * startc和comancestor之间的元素
	 */
	private static List<CellElement> getStartelement(CellElement startc,
			final CellElement comancestor)
	{
		final List<CellElement> list = new ArrayList<CellElement>();
		if (comancestor != null && startc != null)
		{
			CellElement parent = (CellElement) startc.getParent();
			while (parent != null && !parent.equals(comancestor)
					&& !(parent instanceof PageSequence))
			{
				final int index = parent.getIndex(startc);
				list.addAll(parent.getChildren(index + 1));
				startc = parent;
				parent = (CellElement) parent.getParent();
			}
		}
		return list;
	}

	/*
	 * comancestor和endc之间的元素
	 */
	private static List<CellElement> getEndelement(CellElement endc,
			final CellElement comancestor)
	{
		final List<CellElement> list = new ArrayList<CellElement>();
		if (comancestor != null && endc != null)
		{
			CellElement parent = (CellElement) endc.getParent();
			while (parent != null && !parent.equals(comancestor)
					&& !(parent instanceof PageSequence))
			{
				final int index = parent.getIndex(endc);
				list.addAll(0, parent.getChildren(0, index));
				endc = parent;
				parent = (CellElement) parent.getParent();
			}
		}
		return list;
	}

	/**
	 * 
	 * 返回元素最近的类型为Group的祖先对象
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public static Group getFirstGroupofCell(final CellElement celle)
	{
		if (celle != null)
		{
			Element parent = celle;
			while (parent != null && !(parent instanceof Flow))
			{
				if (parent instanceof Group)
				{
					return (Group) parent;
				}
				parent = parent.getParent();
			}
		}
		return null;

	}

	public static List<CellElement> getElementsofGroup(Group group)
	{
		final List<CellElement> list = new ArrayList<CellElement>();
		if (group != null)
		{
			int childcount = group.getChildCount();
			for (int i = 0; i < childcount; i++)
			{
				CellElement child = group.getChildAt(i);
				if (child instanceof Group)
				{
					list.addAll(getElementsofGroup((Group) child));
				} else
				{
					list.add(child);
				}
			}
		}
		return list;
	}

	public static boolean isInlineGroup(CellElement group)
	{
		if (!(group instanceof Group))
		{
			return false;
		}
		List<CellElement> gelements = getElementsofGroup((Group) group);
		if (gelements == null || gelements.isEmpty())
		{
			return false;
		}
		for (CellElement gelement : gelements)
		{
			if (!(gelement instanceof Inline))
			{
				return false;
			}
		}
		return true;
	}
}
