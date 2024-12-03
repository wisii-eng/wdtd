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
 * @DefaultDocument.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.event.EventListenerList;
import javax.swing.tree.TreeNode;
import javax.swing.undo.UndoableEditSupport;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.apps.FOUserAgent;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.listener.AttributeChange;
import com.wisii.wisedoc.document.listener.CompoundElementChange;
import com.wisii.wisedoc.document.listener.DataStructChange;
import com.wisii.wisedoc.document.listener.DataStructureChangeListener;
import com.wisii.wisedoc.document.listener.DocumentChange;
import com.wisii.wisedoc.document.listener.DocumentChangeEvent;
import com.wisii.wisedoc.document.listener.DocumentListener;
import com.wisii.wisedoc.document.listener.ElementChange;
import com.wisii.wisedoc.document.svg.SVGContainer;
import com.wisii.wisedoc.io.XMLUtil;
import com.wisii.wisedoc.io.xsl.attribute.edit.EditUIWriter;
import com.wisii.wisedoc.io.xsl.attribute.edit.GroupUI;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;
import com.wisii.wisedoc.undo.CompoundElementEdit;
import com.wisii.wisedoc.undo.DocumentUndoManager;
import com.wisii.wisedoc.view.WisedocFrame;
import com.wisii.wisedoc.view.select.DocumentPositionRange;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;

/**
 * 类功能描述：document实现类，该类中实现了所有针对文档的操作接口
 * 
 * 作者：zhangqiang 创建日期：2008-8-13
 */
public class WiseDocDocument extends UndoableEditSupport implements Document
{

	/**
	 * // * The event listener list for the document.
	 */
	protected final EventListenerList listenerList = new EventListenerList();

	// 子元素，为空表示没子元素。
	protected final List<PageSequence> _children = new ArrayList<PageSequence>();

	// 对象属性
	protected Attributes _attributes = new Attributes(
			new HashMap<Integer, Object>());

	// Undo管理器
	protected final DocumentUndoManager _undomanager = new DocumentUndoManager();

	// Keeps count of page number from over PageSequence instances
	private int _endingPageNumberOfPreviousSequence = 0;

	private int totalPagesGenerated = 0;

	// 数据结构
	private DataStructureTreeModel _datastructuremodel;

	private String _savepath;

	private boolean issaved = true;

	// 记录发生改变的元素
	List<Element> _changeelements = Collections
			.synchronizedList(new ArrayList<Element>());

	//
	// 记录是否是新打开的文档，或是新建的一个文档，即是否是用户还没有进行任何操作的文档
	private boolean isnew = true;

	/* 【添加：START】 by 李晓光 2009-4-10 */
	// 定义book-mark-tree
	private BookmarkTree bookmarkTree = null;

	private TableContents tablecontents = new TableContents();

	/* 【添加：END】 by 李晓光 2009-4-10 */
	// 缓存时间
	private final int _delaytime = 50;

	public WiseDocDocument()
	{
		final PageSequence ps = new PageSequence(null);
		_children.add(ps);
		ps.setParent(this);
		addUndoableEditListener(_undomanager);

	}

	public WiseDocDocument(final List<PageSequence> pagesequences)
	{
		this(pagesequences, null);
	}

	public WiseDocDocument(final List<PageSequence> pagesequences,
			final TableContents tablecontents)
	{
		if (pagesequences != null && !pagesequences.isEmpty())
		{
			final int size = pagesequences.size();
			for (int i = 0; i < size; i++)
			{
				final PageSequence ps = pagesequences.get(i);
				if (ps != null)
				{
					ps.setParent(this);
					_children.add(ps);
				}
			}

		} else
		{
			final PageSequence ps = new PageSequence(null);
			_children.add(ps);
		}
		if (tablecontents != null)
		{
			this.tablecontents = tablecontents;
		}
		addUndoableEditListener(_undomanager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Document#insertString(java.lang.String,
	 * com.wisii.wisedoc.document.DocumentPosition, java.util.Map)
	 */
	public void insertString(final String str, final DocumentPosition pos,
			final Map<Integer, Object> attrs)
	{
		if (str != null && str.length() > 0 && pos != null)
		{
			beginUpdate();
			// 记录文档的
			final List<DocumentChange> changes = new ArrayList<DocumentChange>();
			final char[] chars = str.toCharArray();
			final int size = chars.length;
			final CellElement element = pos.getLeafElement();
			CellElement parentElement;
			List<CellElement> childil = null;
			final Map<Integer, Object> attmap = pos.getBlockAttriute();
			int offset = 0;
			// 判断pos中记录的是什么类型的元素。
			// 如果是单元格
			if ((element instanceof TableCell)
					|| (element instanceof BlockContainer)
					|| (element instanceof Flow))
			{
				final Block block = new Block(attmap);
				element.add(block);
				parentElement = block;
				// 处理编辑事件。
				final List<CellElement> cells = new ArrayList<CellElement>();
				cells.add(block);
				changes.add(new ElementChange(0, element, cells,
						ElementChange.INSERT));
			}
			// 如果是段落
			else if (element instanceof Block)
			{
				parentElement = element;
			}
			else if(element instanceof ZiMoban)
			{
				final Block block = new Block(attmap);
				CellElement zimobanparent = (CellElement) element.getParent();
				int zimobanindex=0;
				if (!pos.isStartPos())
				{
					zimobanindex = zimobanparent.getIndex(element) + 1;
				} else
				{
					zimobanindex = zimobanparent.getIndex(element);
				}
				zimobanparent.insert(block, zimobanindex);
				parentElement = block;
				// 处理编辑事件。
				final List<CellElement> cells = new ArrayList<CellElement>();
				cells.add(block);
				changes.add(new ElementChange(0, zimobanparent, cells,
						ElementChange.INSERT));
			}
			// 如果是Inline
			else
			{
				parentElement = (CellElement) element.getParent();
				if (!pos.isStartPos())
				{
					offset = parentElement.getIndex(element) + 1;
				} else
				{
					offset = parentElement.getIndex(element);
				}
				// 获得光标所在位置之后的对象

				childil = parentElement.getChildren(offset);
				if (childil != null && !childil.isEmpty())
				{
					// 如果含有换行符。则拆分当前段落
					if (str.contains("\n"))
					{
						changes.add(new ElementChange(offset, parentElement,
								childil, ElementChange.REMOVE, true));
						parentElement.removeChildren(childil);
					} else
					{
						childil = null;
					}
				} else
				{
					if (attmap != null)
					{
						attmap.remove(Constants.PR_BLOCK_LEVEL);
					}
				}

			}
			final List<CellElement> inlines = new ArrayList<CellElement>();

			for (int i = 0; i < size; i++)
			{

				final char c = chars[i];
				// 如果是换行符，则新生成一个block。
				if (c == '\n')
				{
					// 将换行符以前的内容添加到当前段落中
					if (!inlines.isEmpty())
					{
						parentElement.insert(inlines, offset);
						changes.add(new ElementChange(offset, parentElement,
								inlines, ElementChange.INSERT));
						inlines.clear();
					}
					// 如果不是最后一个字符，或没有光标所在位置段落没有被拆分，则新起一个段落
					// if(i != size-1||(childil != null&&!childil.isEmpty())){
					// 新起一个段落对象
					final Block np = new Block(attmap);
					if (attrs != null && !attrs.isEmpty())
					{
						np.setInlineatt(attrs);
					}
					final CellElement pe = (CellElement) parentElement
							.getParent();
					final int index = pe.getIndex(parentElement);
					// 将新生成的段落对象添加到当前段落的父对象上去
					pe.insert(np, index + 1);
					final List<CellElement> cs = new ArrayList<CellElement>();
					cs.add(np);
					changes.add(new ElementChange(index + 1, pe, cs,
							ElementChange.INSERT));
					parentElement = np;
					offset = 0;
					// }

				}
				// 如果是控制字符，则不容许输入
				else if (Character.isISOControl(c))
				{
					continue;
				}

				// 如果不是换行符，则按字符生成Inline对象
				else
				{
					// 如果是非XML所支持的字符，则用空格代替
					if (!XMLUtil.isXMLCharacter(c))
					{
						final TextInline tmpinline = new TextInline(new Text(
								'_'), attrs);
						inlines.add(tmpinline);
					} else
					{
						final TextInline tmpinline = new TextInline(
								new Text(c), attrs);
						inlines.add(tmpinline);
					}
				}
			}
			if (!inlines.isEmpty())
			{
				//
				parentElement.insert(inlines, offset);
				changes.add(new ElementChange(offset, parentElement, inlines,
						ElementChange.INSERT));
				// 更新offset
				offset = offset + inlines.size();
			}
			// 如果光标所在点被拆分成了两个段落
			if (childil != null && !childil.isEmpty())
			{
				parentElement.insert(childil, offset);
				changes.add(new ElementChange(offset, parentElement, childil,
						ElementChange.INSERT, true));
			}
			endedit(changes);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.Document#insertString(com.wisii.wisedoc.banding
	 * .BindNode, com.wisii.wisedoc.document.DocumentPosition, java.util.Map)
	 */
	public void insertString(final BindNode bindnode,
			final DocumentPosition pos, final Map<Integer, Object> attrs)
	{
		if (bindnode != null && pos != null)
		{
			beginUpdate();
			// 记录文档的改变操作
			final List<DocumentChange> changes = new ArrayList<DocumentChange>();
			final CellElement element = pos.getLeafElement();
			CellElement parentElement;
			int offset = 0;
			// 判断pos中记录的是什么类型的元素。
			// 如果是单元格
			if ((element instanceof TableCell)
					|| (element instanceof BlockContainer)
					|| (element instanceof Flow))
			{
				final Map<Integer, Object> attmap = pos.getBlockAttriute();
				final Block block = new Block(attmap);
				element.add(block);
				parentElement = block;
				// 处理编辑事件。
				final List<CellElement> cells = new ArrayList<CellElement>();
				cells.add(block);
				changes.add(new ElementChange(0, element, cells,
						ElementChange.INSERT));
			}
			else if(element instanceof ZiMoban)
			{
				final Map<Integer, Object> attmap = pos.getBlockAttriute();
				final Block block = new Block(attmap);
				CellElement zimobanparent = (CellElement) element.getParent();
				int zimobanindex=0;
				if (!pos.isStartPos())
				{
					zimobanindex = zimobanparent.getIndex(element) + 1;
				} else
				{
					zimobanindex = zimobanparent.getIndex(element);
				}
				zimobanparent.insert(block, zimobanindex);
				parentElement = block;
				// 处理编辑事件。
				final List<CellElement> cells = new ArrayList<CellElement>();
				cells.add(block);
				changes.add(new ElementChange(0, zimobanparent, cells,
						ElementChange.INSERT));
			}
			// 如果是段落
			else if (element instanceof Block)
			{
				parentElement = element;
			}
			// 如果是Inline
			else
			{
				parentElement = (CellElement) element.getParent();
				if (!pos.isStartPos())
				{
					offset = parentElement.getIndex(element) + 1;
				} else
				{
					offset = parentElement.getIndex(element);
				}
			}
			// 生成包含动态数据节点的Inline对象
			final CellElement newChild = new TextInline(new Text(bindnode),
					attrs);
			parentElement.insert(newChild, offset);
			final List<CellElement> cs = new ArrayList<CellElement>();
			cs.add(newChild);
			changes.add(new ElementChange(offset, parentElement, cs,
					ElementChange.INSERT));
			endedit(changes);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.Document#insertSection(com.wisii.wisedoc.document
	 * .DocumentPosition)
	 */
	public void insertPageSequence(final DocumentPosition pos)
	{
		if (pos != null)
		{
			beginUpdate();
			// 记录文档的改变操作
			final List<DocumentChange> changes = new ArrayList<DocumentChange>();

			final CellElement leaf = pos.getLeafElement();
			CellElement block = leaf;
			CellElement parent = leaf;

			while ((parent != null) && !(parent instanceof Flow))
			{
				block = parent;
				parent = (CellElement) parent.getParent();
			}
			// 如果光标是在页眉页脚区域，则不可以设置
			if (!(parent instanceof Flow) || (parent instanceof StaticContent))
			{
				return;
			}
			final Flow flow = (Flow) parent;
			final PageSequence currentSect = (PageSequence) flow.getParent();

			final int index = flow.getIndex(block);
			final List<CellElement> elements = flow.getChildren(index);
			// 如果是表格或是由多个段落组成的段落组（这类对象可以设置重复属性），则将这类对象整个放到下一章节中去
			if (block instanceof Table || block instanceof BlockContainer)
			{

				flow.removeChildren(elements);
				changes.add(new ElementChange(index, flow, elements,
						ElementChange.REMOVE));
			}
			// 如果是段落，则从插入点的位置将段落分成两个段落，插入点后的内容拆分到新段中，并作为下一章节的起始段落
			else
			{
				int offset = -1;
				if (!pos.isStartPos())
				{
					offset = block.getoffsetofPos(pos) + 1;
				} else
				{
					offset = block.getoffsetofPos(pos);
				}
				elements.remove(block);
				flow.removeChildren(elements);
				changes.add(new ElementChange(index, flow, elements,
						ElementChange.REMOVE));
				// }
				final List<CellElement> childe = block.getChildren(offset);
				if (childe != null && !childe.isEmpty())
				{
					block.removeChildren(childe);
					changes.add(new ElementChange(offset, block, childe,
							ElementChange.REMOVE));
					final Block nb = new Block(block.getAttributes()
							.getAttributes());
					nb.insert(childe, 0);
					elements.add(0, nb);
				}

			}
			// 生成新章节
			final Attributes oldatt = currentSect.getAttributes();
			Map<Integer, Object> newattmap = null;
			if (oldatt != null)
			{
				final Map<Integer, Object> oldattmap = oldatt.getAttributes();
				if (oldattmap != null && !oldattmap.isEmpty())
				{
					newattmap = new HashMap<Integer, Object>();
					final Iterator<Integer> keyit = oldattmap.keySet()
							.iterator();
					while (keyit.hasNext())
					{
						final Integer key = keyit.next();
						Object value = oldattmap.get(key);
						if (value instanceof PageSequenceMaster)
						{
							final PageSequenceMaster oldpsmaster = (PageSequenceMaster) value;
							value = new PageSequenceMaster(oldpsmaster
									.getSubsequenceSpecifiers());
						}
						newattmap.put(key, value);
					}
				}
			}
			final PageSequence ps = new PageSequence(newattmap);
			int flowcount= currentSect.getChildCount();
			//如果当前章节中有页眉页脚等静态流，则把这些内容也插入在新的章节中去
			if(flowcount>1)
			{
				List<CellElement> stes = new ArrayList<CellElement>();
				for (int i = 0; i < flowcount; i++)
				{
					CellElement cflow = currentSect.getChildAt(i);
					if (cflow instanceof StaticContent)
					{
						stes.add((StaticContent) cflow.clone());
					}
				}
				if (!stes.isEmpty())
				{
					ps.insert(stes, 1);
				}
			}
			final Flow mainf = ps.getMainFlow();
			// 向流中插入插入点以后的文档内容
			mainf.insert(elements, 0);
			final List<CellElement> childrens = new ArrayList<CellElement>();
			childrens.add(ps);
			final int insertindex = getIndex(currentSect) + 1;
			insert(childrens, insertindex);
			changes.add(new ElementChange(insertindex, this, childrens,
					ElementChange.INSERT));
			endedit(changes);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Document#insertElements(java.util.List,
	 * com.wisii.wisedoc.document.DocumentPosition)
	 */
	public void insertElements(final List<CellElement> elements,
			final DocumentPosition pos)
	{
		if (elements != null && !elements.isEmpty() && pos != null)
		{
			beginUpdate();
			// 记录文档的改变操作
			final List<DocumentChange> changes = insertelements(elements, pos);
			endedit(changes);
		}

	}

	/**
	 * 注意给定删除的元素里面不应该有空元素，需要过滤一下 也不能有相同的元素在element里，这样删除第一个再处理第二个时会有问题
	 */
	public void deleteElements(final List<CellElement> elements)
	{

		if (elements != null && !elements.isEmpty())
		{
			beginUpdate();
			// 记录文档的改变操作
			final List<DocumentChange> changes = deleteelements(elements);
			endedit(changes);
		}

	}

	public void deleteElements(final List<CellElement> elements,
			final CellElement parent)
	{
		if (elements != null && !elements.isEmpty() && parent != null)
		{
			// 先按照其在父对象上的顺序进行排序
			Collections.sort(elements, new Comparator<CellElement>()
			{

				public int compare(final CellElement o1, final CellElement o2)
				{
					final int index1 = parent.getIndex(o1);
					final int index2 = parent.getIndex(o2);
					if (index1 == index2)
					{
						return 0;
					} else if (index1 > index2)
					{
						return 1;
					} else
					{
						return -1;
					}
				}
			});
			final int index = parent.getIndex(elements.get(0));
			if (index > -1)
			{
				final int size = elements.size();
				// 是否是连续的元素
				boolean issequence = true;
				for (int i = 1; i < size; i++)
				{
					final CellElement cell = elements.get(i);
					final int indexc = parent.getIndex(cell);
					if (index + i != indexc)
					{
						issequence = false;
						break;
					}
				}
				if (issequence)
				{
					beginUpdate();
					final List<DocumentChange> changes = new ArrayList<DocumentChange>();
					changes.add(new ElementChange(index, parent, elements,
							ElementChange.REMOVE));
					parent.removeChildren(elements);
					if (parent.getChildCount() == 0)
					{
						final DocumentChange change = deletenullparent(parent);
						if (change != null)
						{
							changes.add(change);
						}
					}
					endedit(changes);
				}
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.Document#replaceElements(com.wisii.wisedoc
	 * .document.DocumentPosition, com.wisii.wisedoc.document.DocumentPosition,
	 * java.util.List)
	 */
	public void replaceElements(final DocumentPositionRange range,
			final List<CellElement> elements)
	{
		// 获得两个位置点的元素
		final List<CellElement> todelelements = DocumentUtil.getElements(range);
		beginUpdate();
		final List<DocumentChange> changes = new ArrayList<DocumentChange>();
		// 插入元素
		if (elements != null && !elements.isEmpty())
		{
			changes.addAll(insertelements(elements, range.getStartPosition()));
		}
		if (todelelements != null && !todelelements.isEmpty())
		{
			// 删除元素
			changes.addAll(deleteelements(todelelements));
		}

		endedit(changes);

	}

	/*
	 * 不触发编辑事件以及undoredo操作的插入元素方法
	 */
	private List<DocumentChange> insertelements(
			final List<CellElement> elements, final DocumentPosition pos)
	{
		if (elements != null && !elements.isEmpty() && pos != null)
		{
			final List<DocumentChange> changes = new ArrayList<DocumentChange>();
			boolean allisinline = true;
			final int size = elements.size();
			// 判断要添加的内容是否全部是inline级别的元素
			for (int i = 0; i < size; i++)
			{
				final CellElement elementsi = elements.get(i);
				if (!(elementsi instanceof Inline)
						&& !((elementsi instanceof BlockContainer || elementsi instanceof SVGContainer) && new EnumProperty(
								Constants.EN_ABSOLUTE, "").equals(elementsi
								.getAttribute(Constants.PR_ABSOLUTE_POSITION)))&&!DocumentUtil.isInlineGroup(elementsi))
				{
					allisinline = false;
					break;
				}
			}
			final CellElement element = pos.getLeafElement();
			CellElement parentElement;
			CellElement gparent = null;
			int index = -1;
			List<CellElement> breakcells = null;
			final Map<Integer, Object> attmap = pos.getBlockAttriute();
			int offset = 0;
			// 判断pos中记录的是什么类型的元素。
			// 如果是单元格
			if ((element instanceof TableCell)
					|| (element instanceof BlockContainer)
					|| (element instanceof Flow))
			{
				if (elements.get(0) instanceof Inline)
				{
					final Block block = new Block(attmap);
					element.add(block);
					parentElement = block;
					// 处理编辑事件。
					final List<CellElement> cells = new ArrayList<CellElement>();
					cells.add(block);
					changes.add(new ElementChange(0, element, cells,
							ElementChange.INSERT));
				} else
				{
					parentElement = element;
				}
			}
			else if(element instanceof ZiMoban)
			{
				final Block block = new Block(attmap);
				CellElement zimobanparent = (CellElement) element.getParent();
				int zimobanindex=0;
				if (!pos.isStartPos())
				{
					zimobanindex = zimobanparent.getIndex(element) + 1;
				} else
				{
					zimobanindex = zimobanparent.getIndex(element);
				}
				zimobanparent.insert(block, zimobanindex);
				parentElement = block;
				// 处理编辑事件。
				final List<CellElement> cells = new ArrayList<CellElement>();
				cells.add(block);
				changes.add(new ElementChange(0, zimobanparent, cells,
						ElementChange.INSERT));
			}
			// 如果是段落
			else if (element instanceof Block)
			{
				parentElement = element;
			}
			// 如果是Inline
			else
			{
				parentElement = (CellElement) element.getParent();
			    if (!pos.isStartPos())
				{
					offset = parentElement.getIndex(element) + 1;
				} else
				{
					offset = parentElement.getIndex(element);
				}
				breakcells = parentElement.getChildren(offset);
				// 如果中间有非inline级别的元素，则拆分当前段落
				if (breakcells != null && !breakcells.isEmpty() && !allisinline)
				{
					if (parentElement.getChildCount() > breakcells.size())
					{
						parentElement.removeChildren(breakcells);
						changes.add(new ElementChange(offset, parentElement,
								breakcells, ElementChange.REMOVE));
					} else
					{
						gparent = (CellElement) parentElement.getParent();
						index = gparent.getIndex(parentElement);
						gparent.remove(parentElement);
						final List<CellElement> removeslements = new ArrayList<CellElement>();
						removeslements.add(parentElement);
						changes.add(new ElementChange(index, gparent,
								removeslements, ElementChange.REMOVE));
					}
				} else
				{
					breakcells = null;
				}
			}
			final CellElement oldparent = parentElement;
			if (gparent == null)
			{
				gparent = (CellElement) parentElement.getParent();
				// 记录原插入点所在的段落
				index = gparent.getIndex(parentElement) + 1;
			}
			final List<CellElement> inlines = new ArrayList<CellElement>();
			final List<CellElement> blocks = new ArrayList<CellElement>();
			for (int i = 0; i < size; i++)
			{

				final CellElement tempe = elements.get(i);
				final EnumProperty abspos = (EnumProperty) tempe
						.getAttribute(Constants.PR_ABSOLUTE_POSITION);
				// 如果是Inline级别的对象，则将连续的几个Inline收集在一起
				if (tempe instanceof Inline||DocumentUtil.isInlineGroup(tempe))
				{
					inlines.add(tempe);
				} else if ((tempe instanceof BlockContainer || tempe instanceof SVGContainer)
						&& abspos != null
						&& abspos.getEnum() == Constants.EN_ABSOLUTE)
				{
					CellElement flow = element;
					int insertindex = 0;
					while (flow != null && !(flow instanceof Flow))
					{
						insertindex = flow.getParent().getIndex(flow);
						flow = (CellElement) flow.getParent();
					}
					if (flow != null)
					{

						final List<CellElement> insertelements = new ArrayList<CellElement>();
						if (insertindex < flow.getChildCount())
						{
							insertindex++;
						}
						insertelements.add(tempe);
						flow.insert(insertelements, insertindex);
						changes.add(new ElementChange(insertindex, flow,
								insertelements, ElementChange.INSERT));

					}

				} else
				{
					// 将收集在一起的连续的Inline对象放到blocks集合中去
					if (!inlines.isEmpty())
					{
						parentElement.insert(inlines, offset);
						// 如果不是原插入点所在的段落
						if (!parentElement.equals(oldparent))
						{
							blocks.add(parentElement);
						} else
						{
							changes.add(new ElementChange(offset,
									parentElement, inlines,
									ElementChange.INSERT));
						}
						inlines.clear();
						parentElement = new Block(attmap);
						offset = 0;
					}
					blocks.add(tempe);
				}

			}
			if (!inlines.isEmpty()
					|| (breakcells != null && !breakcells.isEmpty()))
			{
				// 如果不是原插入点所在的段落
				if (!parentElement.equals(oldparent))
				{
					blocks.add(parentElement);
				} else
				{
					// 如果插入的有段落级别元素，则将原段落拆分成两个段落，在拆分后的两段落之间插blocks
					if (!blocks.isEmpty())
					{
						parentElement = new Block(attmap);
						offset = 0;
						blocks.add(parentElement);
					}
				}
			}
			// 如果得到的段落级别的对象为非空
			if (!blocks.isEmpty())
			{
				if ((gparent instanceof PageSequence)
						|| (parentElement instanceof TableCell)
						|| (parentElement instanceof BlockContainer))
				{
					gparent = oldparent;
					index = 0;
				}
				gparent.insert(blocks, index);
				changes.add(new ElementChange(index, gparent, blocks,
						ElementChange.INSERT));
			}
			// 如果原段落被拆分了
			if (breakcells != null && !breakcells.isEmpty())
			{
				parentElement.insert(breakcells, offset);
				changes.add(new ElementChange(offset, parentElement,
						breakcells, ElementChange.INSERT, true));
				offset = 0;
			}
			// 如果得到的inlines元素不为空，则插入对应的元素
			if (!inlines.isEmpty())
			{
				parentElement.insert(inlines, offset);
				changes.add(new ElementChange(offset, parentElement, inlines,
						ElementChange.INSERT));
			}
			return changes;
		}
		return null;
	}
	/*
	 * 不触发编辑事件以及undoredo操作的删除元素方法
	 */
	private List<DocumentChange> deleteelements(final List<CellElement> elements)
	{
		if (elements != null && !elements.isEmpty())
		{
			final List<DocumentChange> changes = new ArrayList<DocumentChange>();
			final int size = elements.size();
			CellElement element0 = elements.get(0);
			CellElement parentelement = (CellElement) element0.getParent();
			if (parentelement instanceof Inline)
			{
				element0 = parentelement;
				parentelement = (CellElement) element0.getParent();
			}
			final List<CellElement> todeletes = new ArrayList<CellElement>();
			todeletes.add(element0);
			int offset = parentelement.getIndex(element0);
			for (int i = 1; i < size; i++)
			{
				CellElement element = elements.get(i);
				//

				CellElement elementp = (CellElement) element.getParent();
				// 如果删除的元素的父元素是Inline级别的，则删除该Inline元素
				if ((elementp instanceof Inline)
						|| (elementp instanceof SVGContainer))
				{
					element = elementp;
					elementp = (CellElement) elementp.getParent();
				}
				// 如果要删除的元素的父元素和上一个元素的父元素相同，则将该元素
				// 添加到删除列表中
				if (elementp.equals(parentelement))
				{
					todeletes.add(element);
				}
				// 如果不同，则删除点之前的相同的父元素的集合
				else
				{
					// 如果可以删除，则删除之
					if (((DefaultElement) parentelement).iscanRemove(todeletes))
					{
						parentelement.removeChildren(todeletes);
						changes.add(new ElementChange(offset, parentelement,
								todeletes, ElementChange.REMOVE));
					}
					todeletes.clear();
					// 如果当前父对象已没有子对象，则应该从该父对象的的父对象中删除该对象
					if (parentelement.getChildCount() == 0)
					{
						// 如果父对象和当前对象的父对象相同，则加入到删除列表中进行删除
						if (parentelement.getParent().equals(
								element.getParent()))
						{
							todeletes.add(parentelement);
						} else
						{
							final DocumentChange change = deletenullparent(parentelement);
							if (change != null)
							{
								changes.add(change);
							}
						}
					}
					todeletes.add(element);
					parentelement = elementp;
					offset = elementp.getIndex(element);
				}
			}
			if (!todeletes.isEmpty())
			{
				parentelement.removeChildren(todeletes);
				changes.add(new ElementChange(offset, parentelement, todeletes,
						ElementChange.REMOVE));
				if (parentelement.getChildCount() == 0)
				{
					final DocumentChange change = deletenullparent(parentelement);
					if (change != null)
					{
						changes.add(change);
					}
				}
			}
			return changes;

		}
		return null;
	}

	private DocumentChange deletenullparent(final CellElement parentelement)
	{
		if (parentelement != null && !(parentelement instanceof Document)
				&& parentelement.getChildCount() == 0)
		{
			Element oldparent = parentelement;
			Element parent = oldparent.getParent();
			// 循环到子元素不为零的父对象
			while (parent != null && !(oldparent instanceof Group)
					&& !(oldparent instanceof SVGContainer)
					&& !(oldparent instanceof Table)
					&& !(oldparent instanceof TableCell)
					&& !(parent instanceof Document)
					&& parent.getChildCount() == 1)
			{
				oldparent = parent;
				parent = parent.getParent();
			}
			if (oldparent instanceof Group
					|| oldparent instanceof SVGContainer
					|| (oldparent instanceof TableFObj && !(oldparent instanceof TableCell)))
			{
				final List<CellElement> todels = new ArrayList<CellElement>();
				final int offset = parent.getIndex(oldparent);
				todels.add((CellElement) oldparent);
				parent.removeChildren(todels);
				return new ElementChange(offset, parent, todels,
						ElementChange.REMOVE);
			}
		}
		return null;
	}
	private void endedit(final List<DocumentChange> changes)
	{
		endedit(changes,true);
	}
	/*
	 * 
	 * 编辑操作结束方法
	 * 
	 * @param {引入参数名} {引入参数说明} @return {返回参数名} {返回参数说明} @exception
	 * {说明在某情况下,将发生什么异常}
	 */
	private void endedit(final List<DocumentChange> changes,boolean needfirechanged)
	{
		if (changes != null && !changes.isEmpty())
		{
			issaved = false;
			isnew = false;
			// 这个地方是缓冲编辑操作，使得多次在几乎是同一时间进行编辑操作可以合并成一次编辑操作，
			// 只调用一次更新和记录一次编辑操作

			// 积累变化并且记录在undo中
			// addEdit(changes);
			addEditV2(changes);

			// 专门用于积累undo事件的
			// changeList.add(changes);

			// 发送改变消息事件
			if(needfirechanged){
			firechangedUpdate(new DocumentChangeEvent(this,
					new CompoundElementChange(changes)));
			}
		}
	}

	// 用于存放改变的内容
	// public static BlockingQueue<List<DocumentChange>> changeList = new
	// LinkedBlockingQueue<List<DocumentChange>>();

	// {
	// WiseDocThreadService.Instance.doUndoService(new DelaiedFireChange());
	// }

	// private class DelaiedFireChange implements Runnable {
	//
	// @Override
	// public void run() {
	// if (changeList.isEmpty()) {
	// return;
	// }
	//
	// final List<DocumentChange> temp = new DocumentChangeList();
	// final Collection<List<DocumentChange>> tempC = new
	// ArrayList<List<DocumentChange>>();
	// changeList.drainTo(tempC);
	// for (final List<DocumentChange> list : tempC)
	// {
	// for (final DocumentChange dc : list) {
	// temp.add(dc);
	// }
	// }
	//
	// //处理undo事件
	// postEdit(new CompoundElementEdit(temp, WiseDocDocument.this));
	// endUpdate();
	// }
	// }

	// 把timer放到这里防止每次都创建，比每次都创建减少1M的内存
	// private static final Timer timer = new Timer();
	//
	// //老的用于积累undo改变的方法
	// private void addEdit(final List<DocumentChange> changes)
	// {
	// if(_edittask != null && _edittask.cancel())
	// {
	// updateLevel--;
	// }
	//
	// // final Timer timer = new Timer();
	// _edittask = new MyEditUpdateTimerTask(changes);
	//
	// // 延迟delay长的时间再通知改变时间，以使得连续的改变事件只通知最后一次
	// timer.schedule(_edittask, _delaytime);
	// }

	private FutureTask<?> editTask;

	private final ScheduledExecutorService undoService = Executors
			.newScheduledThreadPool(1);

	//
	private void addEditV2(final List<DocumentChange> changes)
	{
		if (editTask != null && editTask.cancel(true))
		{
			updateLevel--;
		}

		editTask = new FutureTask<Object>(new EditUpdateTask(changes));

		// 延迟delay长的时间再通知改变时间，以使得连续的改变事件只通知最后一次
		undoService.schedule(editTask, _delaytime, TimeUnit.MILLISECONDS);
	}

	// TODO 上面是多线程的程序框架代码，由于需要对某些变量做细致的同步，目前没有时间，所以目前用保守的线程模式

	/*
	 * 触发文档改变事件
	 */
	public synchronized void firechangedUpdate(final DocumentChangeEvent event)
	{
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == DocumentListener.class)
			{
				((DocumentListener) listeners[i + 1]).changedUpdate(event);
			}
		}
	}

	/*
	 * 触发数据结构改变事件
	 */
	protected void firedatastructureUpdate()
	{
		// Guaranteed to return a non-null array
		final Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == DataStructureChangeListener.class)
			{
				((DataStructureChangeListener) listeners[i + 1])
						.DataStructureChanged();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.Document#setElementAttributes(com.wisii.wisedoc
	 * .document.CellElement, java.util.Map, boolean)
	 */
	public void setElementAttributes(final CellElement element,
			final Map<Integer, Object> attrs, final boolean isreplace)
	{
		if (element != null)
		{
			beginUpdate();
			endedit(setelementAttributes(element, attrs, isreplace));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.Document#setElementAttributes(java.util.List,
	 * java.util.Map, boolean)
	 */
	public void setElementAttributes(final List<CellElement> elements,
			final Map<Integer, Object> attrs, final boolean isreplace)
	{
		if (elements != null && !elements.isEmpty())
		{
			beginUpdate();
			final int size = elements.size();
			final List<DocumentChange> changes = new ArrayList<DocumentChange>();
			for (int i = 0; i < size; i++)
			{
				changes.addAll(setelementAttributes(elements.get(i), attrs,
						isreplace));
			}
			endedit(changes);
		}

	}

	/*
	 * 
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明} @return {返回参数名} {返回参数说明} @exception
	 * {说明在某情况下,将发生什么异常}
	 */
	private List<DocumentChange> setelementAttributes(
			final CellElement element, final Map<Integer, Object> attrs,
			final boolean isreplace)
	{
		if (element != null)
		{
			final List<DocumentChange> changes = new ArrayList<DocumentChange>();
			final List<Element> elements = new ArrayList<Element>();
			elements.add(element);
			changes.add(new AttributeChange(elements, attrs, isreplace));
			element.setAttributes(attrs, isreplace);
			return changes;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.Document#setInlineAttributes(com.wisii.wisedoc
	 * .document.DocumentPosition, com.wisii.wisedoc.document.DocumentPosition,
	 * java.util.Map, boolean)
	 */
	public void setInlineAttributes(final DocumentPositionRange range,
			final Map<Integer, Object> attrs, final boolean isreplace)
	{
		if (range != null)
		{
			setElementAttributes(getElements(range, Inline.class), attrs,
					isreplace);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.Document#setInlineAttributes(com.wisii.wisedoc
	 * .document.DocumentPosition, com.wisii.wisedoc.document.DocumentPosition,
	 * java.util.Map, boolean)
	 */
	public void setInlineAttributes(final DocumentPositionRange[] ranges,
			final Map<Integer, Object> attrs, final boolean isreplace)
	{
		if (ranges != null)
		{
			final List<CellElement> elements = getElements(Arrays
					.asList(ranges), Inline.class);
			setElementAttributes(elements, attrs, isreplace);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.Document#setParagraphAttributes(com.wisii.
	 * wisedoc.document.DocumentPosition, java.util.Map, boolean)
	 */
	public void setParagraphAttributes(final DocumentPosition pos,
			final Map<Integer, Object> attrs, final boolean isreplace)
	{
		if (pos != null)
		{
			CellElement element = pos.getLeafElement();
			while (element != null && !(element instanceof Flow)
					&& !(element instanceof Block))
			{
				element = (CellElement) element.getParent();
			}
			if (element != null && element instanceof Block)
			{
				beginUpdate();
				endedit(setelementAttributes(element, attrs, isreplace));
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.Document#setParagraphsAttributes(com.wisii
	 * .wisedoc.document.DocumentPosition,
	 * com.wisii.wisedoc.document.DocumentPosition, java.util.Map, boolean)
	 */
	public void setParagraphsAttributes(final DocumentPositionRange range,
			final Map<Integer, Object> attrs, final boolean isreplace)
	{
		if (range != null)
		{
			final List<CellElement> setelements = getParentElements(
					new DocumentPositionRange[]
					{ range }, Block.class);
			if (!setelements.isEmpty())
			{
				setElementAttributes(setelements, attrs, isreplace);
			}
		}

	}

	/**
	 * 设置段落属性
	 */
	public void setParagraphsAttributes(final DocumentPositionRange[] ranges,
			final Map<Integer, Object> attrs, final boolean isreplace)
	{
		if (ranges != null)
		{
			final List<CellElement> setelements = getParentElements(ranges,
					Block.class);
			if (!setelements.isEmpty())
			{
				setElementAttributes(setelements, attrs, isreplace);
			}
		}
	}

	public List<CellElement> getElements(
			final List<DocumentPositionRange> ranges)
	{
		return DocumentUtil.getElements(ranges);
	}

	protected List<CellElement> getElements(
			final List<DocumentPositionRange> ranges, final Class c)
	{
		List<CellElement> returns = null;
		if (ranges != null)
		{
			returns = new ArrayList<CellElement>();
			final int size = ranges.size();
			for (int i = 0; i < size; i++)
			{
				final List<CellElement> elements = getElements(ranges.get(i), c);
				if (elements != null)
				{
					returns.addAll(elements);
				}
			}
		}
		return returns;
	}

	protected List<CellElement> getElements(final DocumentPositionRange range,
			final Class c)
	{
		final List<CellElement> old = DocumentUtil.getElements(range);
		return DocumentUtil.getElements(old, c);
	}

	/*
	 * 
	 * 获得指定多个范围的叶子节点元素
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @exception
	 */
	protected List<CellElement> getLeafElements(
			final List<DocumentPositionRange> ranges)
	{
		List<CellElement> returns = null;
		if (ranges != null)
		{
			returns = new ArrayList<CellElement>();
			final int size = ranges.size();
			for (int i = 0; i < size; i++)
			{
				final List<CellElement> elements = getLeafElements(ranges
						.get(i));
				if (elements != null)
				{
					returns.addAll(elements);
				}
			}
		}
		return returns;
	}

	/*
	 * 
	 * 获得指定单个个范围的叶子节点元素
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @exception
	 */
	protected List<CellElement> getLeafElements(
			final DocumentPositionRange range)
	{
		final List<CellElement> old = DocumentUtil.getElements(range);
		return DocumentUtil.getLeafElements(old);
	}

	/**
	 * 
	 * 获得指定位置间的段落属性
	 * 
	 * @param pos
	 *            ：指定的文档位置
	 * @return 指定位置的段落属性键，值对
	 * @exception
	 */
	public Map<Integer, Object> getParagraphAttributes(
			final DocumentPositionRange[] ranges)
	{
		return DocumentUtil.getElementAttributes(getParentElements(ranges,
				Block.class));
	}

	protected List<CellElement> getParentElements(
			final DocumentPositionRange[] ranges, final Class claze)
	{
		final List<CellElement> inlines = getLeafElements(Arrays.asList(ranges));
		return DocumentUtil.getParentElementofType(inlines, claze);
	}

	/**
	 * 
	 * 获得指定位置间的指定类型的属性
	 * 
	 * @param pos
	 *            ：指定的文档位置
	 * @return 指定位置的节属性键，值对
	 * @exception
	 * @exception
	 */
	public Map<Integer, Object> getAttributes(
			final DocumentPositionRange[] ranges, final Class clazs)
	{
		final List<CellElement> elements = getElements(Arrays.asList(ranges),
				clazs);
		return DocumentUtil.getElementAttributes(elements);
	}

	/**
	 * 
	 * 获得指定位置间的Inline属性
	 * 
	 * @param pos
	 *            ：指定的文档位置
	 * @return 指定位置的Inline属性键，值对
	 * @exception
	 * @exception
	 */
	public Map<Integer, Object> getInlineAttributes(
			final DocumentPositionRange[] ranges)
	{
		return getAttributes(ranges, Inline.class);
	}

	/**
	 * 
	 * 获得指定位置间的节属性
	 * 
	 * @param pos
	 *            ：指定的文档位置
	 * @return 指定位置的节属性键，值对
	 * @exception
	 * @exception
	 */
	public Map<Integer, Object> getSectionAttributes(
			final DocumentPositionRange[] ranges)
	{
		return getAttributes(ranges, PageSequence.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#getAttribute(int)
	 */
	public Object getAttribute(final int key)
	{
		final Attributes atts = getAttributes();
		if (atts != null)
		{
			return atts.getAttribute(key);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#getAttributes()
	 */
	public Attributes getAttributes()
	{
		return _attributes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.Element#getoffsetofPos(com.wisii.wisedoc.document
	 * .DocumentPosition)
	 */
	public int getoffsetofPos(final DocumentPosition pos)
	{
		int offset = -1;
		final CellElement element = pos.getLeafElement();
		Element parent = element.getParent();
		Element oldparent = element;
		while (!(parent instanceof Document))
		{
			if (parent instanceof PageSequence)
			{
				offset = _children.indexOf(oldparent);
				break;
			}
			oldparent = parent;
			parent = parent.getParent();
		}
		return offset;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#removeAttributes(int)
	 */
	public void removeAttributes(final int key)
	{
		if (getAttribute(key) != null)
		{
			final Map<Integer, Object> map = getAttributes().getAttributes();
			map.put(key, null);
			setAttributes(map, false);
		}

	}
	/*
     *withedit是否在undo，redo中记录操作过程，测试该操作能撤销重做
     */
	public void setAttribute(final int key, final Object value,boolean withedit)
	{
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(key, value);
		setAttributes(map, false,withedit);
	}
	/*
     *withedit是否在undo，redo中记录操作过程，测试该操作能撤销重做
     */
	public void setAttributes(final Map<Integer, Object> atts,
			final boolean isreplace,boolean withedit)
	{
		if (atts != null)
		{
			final Map<Integer, Object> oldattribute = _attributes
					.getAttributes();
			Map<Integer, Object> map = null;
			if (isreplace)
			{
				map = atts;
			} else
			{
				map = oldattribute;
			}
			final Iterator<Integer> it = atts.keySet().iterator();
			while (it.hasNext())
			{
				final int i = it.next();
				final Object newvalue = atts.get(i);
				// 去除掉里面为空的属性
				if (newvalue == null)
				{
					map.remove(i);
				} else
				{
					if (!isreplace)
					{
						map.put(i, newvalue);
					}
				}
			}
			issaved = false;
			if (withedit)
			{
				beginUpdate();
				List<Element> elements = new ArrayList<Element>();
				elements.add(this);
				List<DocumentChange> changes = new ArrayList<DocumentChange>();
				changes.add(new AttributeChange(elements, atts, isreplace));
				endedit(changes, false);
			}
			_attributes = new Attributes(map);
		}

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#setAttribute(int,
	 * java.lang.Object)
	 */
	public void setAttribute(final int key, final Object value)
	{
		setAttribute(key,value, false);
	}

	public void setAttributes(final Map<Integer, Object> atts,
			final boolean isreplace)
	{
		setAttributes(atts, isreplace,false);
	}

	@Override
	public Element clone()
	{
		return null;
	}

	public void addDocumentListener(final DocumentListener listener)
	{
		listenerList.add(DocumentListener.class, listener);

	}

	public void removeDocumentListener(final DocumentListener listener)
	{
		listenerList.remove(DocumentListener.class, listener);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Document#converoffsettoPos(int)
	 */
	public DocumentPosition converoffsettoPos(final int offset)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * 将内部位置转换成文本偏移量
	 * 
	 * @param offset
	 *            ：偏移量，是指文本数的偏移量，注意一个图片代表一个字符，一个动态绑定节点也是代表一个字符。
	 * @return 内部位置
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public int converPostooffset(final DocumentPosition pos)
	{
		return getTextBeforePos(pos).length();
	}

	/**
	 * 
	 */
	public String getTextBeforePos(final DocumentPosition pos)
	{
		if (pos != null)
		{
			CellElement elementstar = (CellElement) this.getChildAt(0);
			while (elementstar.getChildCount() > 0)
			{
				elementstar = elementstar.getChildAt(0);
			}
			final DocumentPosition startpos = new DocumentPosition(elementstar,
					true);
			final DocumentPositionRange range = DocumentPositionRange
					.creatSelectCell(startpos, pos);
			return getText(range);

		}
		return "";
	}

	public void deletePageSequence(final PageSequence sec)
	{
		final int index = _children.indexOf(sec);
		if (index != -1)
		{
			beginUpdate();
			final List<DocumentChange> changes = new ArrayList<DocumentChange>();
			final List<CellElement> children = new ArrayList<CellElement>();
			children.add(sec);
			changes.add(new ElementChange(index, this, children,
					ElementChange.REMOVE));
			_children.remove(sec);
			endedit(changes);
		}

	}

	public DocumentUndoManager getDocumentUndoManager()
	{
		return _undomanager;
	}

	public Map<Integer, Object> getInlineAttributes(final DocumentPosition pos)
	{
		if (pos != null)
		{
			final CellElement element = pos.getLeafElement();
			if (element instanceof Inline)
			{
				return element.getAttributes().getAttributes();
			}
		}
		return null;
	}

	public Map<Integer, Object> getParagraphAttributes(
			final DocumentPosition pos)
	{
		if (pos != null)
		{
			Element element = pos.getLeafElement();
			// 找到元素所在的段落，注意有可能光标是在一个空单元格上，此时将没有段落属性
			while (element != null && !(element instanceof Block))
			{
				element = element.getParent();
			}
			if (element instanceof Block)
			{
				return element.getAttributes().getAttributes();
			}
		}
		return null;
	}

	public Map<Integer, Object> getSectionAttributes(final DocumentPosition pos)
	{
		if (pos != null)
		{
			CellElement element = pos.getLeafElement();
			// 找到元素所在章节
			while (element != null && !(element instanceof PageSequence))
			{
				element = (CellElement) element.getParent();
			}
			if (element != null)
			{
				return element.getAttributes().getAttributes();
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Document#getText()
	 */
	public String getText()
	{
		final List<CellElement> inlines =DocumentUtil.getElementsofDocument(this, Inline.class);
		StringBuilder text = null;

		if (inlines != null)
		{
			final int size = inlines.size();
			text = new StringBuilder(size);
			for (int i = 0; i < size; i++)
			{
				text.append(((Inline)inlines.get(i)).getText());
			}
		}
		return text.toString();

		// 老版本代码
		// final List<Inline> inlines = getinlines(this);
		// String text = "";
		// if (inlines != null)
		// {
		// final int size = inlines.size();
		//
		// for (int i = 0; i < size; i++)
		// {
		// final Inline il = inlines.get(i);
		// text = text + il.getText();
		// }
		// }
		// return text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.Document#getText(com.wisii.wisedoc.document
	 * .DocumentPosition, com.wisii.wisedoc.document.DocumentPosition)
	 */
	public String getText(final DocumentPositionRange range)
	{

		final List<CellElement> inlines =DocumentUtil.getElementsofDocument(this, Inline.class);
		StringBuilder text = null;

		if (inlines != null)
		{
			final int size = inlines.size();
			text = new StringBuilder(size);
			for (int i = 0; i < size; i++)
			{
				text.append(((Inline)inlines.get(i)).getText());
			}
		}
		return text.toString();

		// 老版本代码
		// final List<CellElement> inlines = getElements(range,
		// TextInline.class);
		// String text = "";
		// if (inlines != null)
		// {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#children()
	 */
	public Enumeration children()
	{
		final Vector<CellElement> children = new Vector<CellElement>();
		children.addAll(_children);
		return children.elements();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getAllowsChildren()
	 */
	public boolean getAllowsChildren()
	{
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getChildAt(int)
	 */
	public TreeNode getChildAt(final int childIndex)
	{
		return _children.get(childIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getChildCount()
	 */
	public int getChildCount()
	{
		return _children.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getIndex(javax.swing.tree.TreeNode)
	 */
	public int getIndex(final TreeNode node)
	{
		return _children.indexOf(node);
	}

	public Element getParent()
	{
		return null;
	}

	public boolean isLeaf()
	{
		return false;
	}

	/**
	 * 
	 * 返回书签对象
	 * 
	 * @param
	 * 
	 * @return
	 * @exception
	 */
	public BookmarkTree getBookmarkTree()
	{
		/* return (BookmarkTree) getAttribute(Constants.FO_BOOKMARK_TREE); */
		return this.bookmarkTree;
	}

	/**
	 * 为当前文档设置Bookmark-Tree
	 * 
	 * @param bookmarkTree
	 *            指定Bookmark-Tree对象
	 */
	public void setBookmarkTree(final BookmarkTree bookmarkTree)
	{
		this.bookmarkTree = bookmarkTree;
		bookmarkTree.setParent(this);
	}

	/**
	 * 
	 * {方法的功能/动作描述}
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public Declarations getDeclarations()
	{
		return (Declarations) getAttribute(Constants.FO_DECLARATIONS);
	}

	/**
	 * Gets the last page number generated by the previous page-sequence
	 * 
	 * @return the last page number, 0 if no page sequences yet generated
	 */
	public int getEndingPageNumberOfPreviousSequence()
	{
		return _endingPageNumberOfPreviousSequence;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#insert(java.util.List, int)
	 */
	public void insert(final List<CellElement> children, final int childIndex)
	{
		if (children != null && !children.isEmpty()
				&& childIndex < _children.size() + 1)
		{
			final List<PageSequence> psl = new ArrayList<PageSequence>();
			final int size = children.size();
			// 判断要添加的子对象如果是PageSquence对象，则可添加之，否则直接返回
			for (int i = 0; i < size; i++)
			{
				final CellElement child = children.get(i);
				if (!(child instanceof PageSequence))
				{
					return;
				}
				((PageSequence) child).setParent(this);
				psl.add((PageSequence) child);
			}
			if (childIndex < _children.size())
			{
				_children.addAll(childIndex, psl);
			} else
			{
				_children.addAll(psl);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Element#removeChildren(java.util.List)
	 */
	public void removeChildren(final List<CellElement> children)
	{
		if (children != null && !children.isEmpty())
		{

			final List<PageSequence> psl = new ArrayList<PageSequence>();
			final int size = children.size();
			// 判断要删除的子对象如果是PageSquence对象，则可删除之，否则直接返回
			for (int i = 0; i < size; i++)
			{
				final CellElement child = children.get(i);
				if (!(child instanceof PageSequence))
				{
					return;
				}
				psl.add((PageSequence) child);
			}
			_children.removeAll(psl);
		}

	}

	public int getNameId()
	{
		return Constants.FO_ROOT;
	}

	public FOUserAgent getUserAgent()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Document#addDataStructureChangeListener()
	 */
	public void addDataStructureChangeListener(
			final DataStructureChangeListener listener)
	{
		listenerList.add(DataStructureChangeListener.class, listener);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.document.Document#getDataStructure()
	 */
	public DataStructureTreeModel getDataStructure()
	{
		return _datastructuremodel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.Document#setDataStructure(com.wisii.wisedoc
	 * .banding.io.DataStructureTreeModel)
	 */
	public void setDataStructure(final DataStructureTreeModel datasm)
	{
		if ((datasm == null && _datastructuremodel != null)
				|| (datasm != null && !datasm.equals(_datastructuremodel)))
		{
			beginUpdate();
			final List<DocumentChange> changes = new ArrayList<DocumentChange>();
			changes.add(new DataStructChange(_datastructuremodel, datasm, this));
			_datastructuremodel = datasm;
			firedatastructureUpdate();
			addEditV2(changes);
			RibbonUIManager.updateUIAvailable();
			issaved = false;
		}
	}
	public void setDataStructureWithoutEdit(final DataStructureTreeModel datasm)
	{
		if ((datasm == null && _datastructuremodel != null)
				|| (datasm != null && !datasm.equals(_datastructuremodel)))
		{
			_datastructuremodel = datasm;
			firedatastructureUpdate();
			RibbonUIManager.updateUIAvailable();
		}
	}
	/**
	 * 
	 * 设置文件保存路径
	 * 
	 * @param path
	 *            ：文件保存路径
	 * @return
	 * @exception
	 */
	public void setSavePath(final String path)
	{
		_savepath = path;
		issaved = true;
		if (_savepath != null)
		{
			final WisedocFrame mainframe = SystemManager.getMainframe();
			final String title = WisedocFrame.DEFAULT_TITLE + " - " + _savepath;
			if (mainframe != null)
			{
				mainframe.setTitle(title);
			}
		}
	}

	/**
	 * 
	 * 获得文件保存路径
	 * 
	 * @param
	 * @return 文件保存路径，为空表示没有设置文件保存路径
	 * @exception
	 */
	public String getSavePath()
	{
		return _savepath;
	}

	public boolean isSaved()
	{
		return issaved;
	}

	// XXX 延时的编辑
	private class EditUpdateTask implements Callable<Object>
	{

		public EditUpdateTask(final List<DocumentChange> changes)
		{
			postEdit(new CompoundElementEdit(changes, WiseDocDocument.this));
		}

		@Override
		public Object call() throws Exception
		{
			endUpdate();
			return null;
		}
	}

	private class MyEditUpdateTimerTask extends TimerTask
	{

		MyEditUpdateTimerTask(final List<DocumentChange> changes)
		{
			postEdit(new CompoundElementEdit(changes, WiseDocDocument.this));
		}

		@Override
		public void run()
		{
			endUpdate();
		}
	}

	@Override
	public synchronized void endUpdate()
	{
		updateLevel = 1;
		super.endUpdate();
	}

	/*
	 * 重新更新发生改变的对象
	 */
	public void setChangeCells(final List<Element> changes)
	{
		_changeelements.clear();
		_changeelements.addAll(changes);
	}

	public void insertElements(final List<CellElement> elements,
			final CellElement parent, final int index)
	{
		if (elements != null && !elements.isEmpty() && parent != null)
		{
			final int size = parent.getChildCount();
			if (index >= 0 && index <= size)
			{
				beginUpdate();
				// 记录文档的改变操作
				final List<DocumentChange> changes = new ArrayList<DocumentChange>();
				final ElementChange change = new ElementChange(index, parent,
						elements, ElementChange.INSERT);
				changes.add(change);
				parent.insert(elements, index);
				endedit(changes);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.document.Document#isCellChanged(com.wisii.wisedoc.document
	 * .CellElement)
	 */
	public boolean isCellChanged(final CellElement cell)
	{

		if (isnew || (cell != null && cell.getChildCount() == 0))
		{
			return true;
		}
		if (cell instanceof Block && ((Block) cell).ischange())
		{
			return true;
		}
		if (_changeelements.contains(cell))
		{
			return true;
		}
		Element cellp = cell.getParent();
		while (cellp != null && !(cellp instanceof Document))
		{
			if ((cellp instanceof TableFObj || cellp instanceof BlockContainer || cellp instanceof PageSequence)
					&& _changeelements.contains(cellp))
			{
				return true;
			}
			if (cellp instanceof PageSequence
					&& cellp.getAttribute(Constants.PR_PAGE_SEQUENCE_MASTER) != null)
			{
				return true;
			}
			cellp = cellp.getParent();
		}
		return false;
	}

	public boolean isGroupAble()
	{
		return true;
	}

	@Override
	public TableContents getTableContents()
	{
		// TODO Auto-generated method stub
		return tablecontents;
	}

	/**
	 * Notify additional pages generated to increase the totalPagesGenerated
	 * counter
	 * 
	 * @param lastPageNumber
	 *            the last page number generated by the sequence
	 * @param additionalPages
	 *            the total pages generated by the sequence (for statistics)
	 * @throws IllegalArgumentException
	 *             for negative additional page counts
	 */
	public void notifyPageSequenceFinished(final int lastPageNumber,
			final int additionalPages)
	{

		if (additionalPages >= 0)
		{
			totalPagesGenerated += additionalPages;
			_endingPageNumberOfPreviousSequence = lastPageNumber;
		} else
		{
			throw new IllegalArgumentException("副页必须大于等于零.");
		}
	}

	/**
	 * Returns the total number of pages generated by FOV (May not equal
	 * endingPageNumberOfPreviousSequence due to initial-page-number property on
	 * fo:page-sequences.)
	 * 
	 * @return the last page number, 0 if no page sequences yet generated
	 */
	public int getTotalPagesGenerated()
	{
		return totalPagesGenerated;
	}

	public void reset()
	{
		totalPagesGenerated = 0;
		_endingPageNumberOfPreviousSequence = 0;
	}

	public void addGroupUI(GroupUI group)
	{
		List<GroupUI> groupuilist = getListEditUI();
		boolean flg = true;
		for (GroupUI current : groupuilist)
		{
			if (current.equals(group) && group.getName() != null
					&& group.getName().equals(current.getName()))
			{
				flg = false;
				break;
			}
		}
		if (flg)
		{
			groupuilist.add(group);
		}
	}

	public void removeGroupUI(GroupUI group)
	{
		List<GroupUI> groupuilist = getListEditUI();
		if (groupuilist != null)
		{
			groupuilist.remove(group);
		}
	}

	public int getGroupUIPosition(GroupUI group)
	{
		List<GroupUI> groupuilist = getListEditUI();
		int flg = -1;
		if (groupuilist.size() > 0)
		{
			for (int i = 0; i < groupuilist.size(); i++)
			{
				GroupUI current = groupuilist.get(i);
				if (current.equals(group) && group.getName() != null
						&& group.getName().equals(current.getName()))
				{
					return i;
				}
			}
		}
		return flg;
	}

	public GroupUI getGroupUI(String name)
	{
		List<GroupUI> groupuilist = getListEditUI();
		if (groupuilist.size() > 0)
		{
			for (int i = 0; i < groupuilist.size(); i++)
			{
				GroupUI current = groupuilist.get(i);
				String currentname = current.getName();
				if (currentname.equals(name))
				{
					return current;
				}
			}
		}
		return null;
	}

	public List<String> getGroupNames()
	{
		List<String> names = new ArrayList<String>();
		List<GroupUI> groupuilist = getListEditUI();
		for (GroupUI current : groupuilist)
		{
			String name = current.getName();
			names.add(name);
		}
		return names;
	}

	public List<GroupUI> getListEditUI()
	{
		return groupui;
	}

	public String getListEditUIString()
	{
		String result = "";
		if (IoXslUtil.isXmlEditable() == Constants.EN_EDITABLE
				|| IoXslUtil.isXmlEditable() == Constants.EN_EDITVARIBLE)
		{
			List<GroupUI> groupui = getListEditUI();
			if (!groupui.isEmpty())
			{
				for (GroupUI current : groupui)
				{
					result = result + new EditUIWriter(current).getCode();
				}
			}
		}
		return result;
	}

	private List<GroupUI> groupui = new ArrayList<GroupUI>();
	public List<CellElement> getAllChildren()
	{
		return new ArrayList<CellElement>(_children);
	}
}
