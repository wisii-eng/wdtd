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
 * @Document.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.document.listener.DataStructureChangeListener;
import com.wisii.wisedoc.document.listener.DocumentChangeEvent;
import com.wisii.wisedoc.document.listener.DocumentListener;
import com.wisii.wisedoc.undo.DocumentUndoManager;
import com.wisii.wisedoc.view.select.DocumentPositionRange;

/**
 * 类功能描述：文档接口，定义文档操作的相关接口
 * 
 * 作者：zhangqiang 创建日期：2008-4-14
 */
public interface Document extends Element, Groupable
{
	/**
	 * 
	 * 在指定位置插入文字
	 * 
	 * @param str
	 *            ：要插入的文字 pos：插入位置
	 *            attrs文字属性，为空表示不设置文本属性，即采用当前位置的文字属性作为新插入文字的文字属性
	 * @return 无
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	void insertString(String str, DocumentPosition pos,
			Map<Integer, Object> attrs);

	/**
	 * 
	 * 在指定位置插入动态数据节点
	 * 
	 * @param bindnode
	 *            ：要插入的动态数据节点 pos：插入位置
	 *            attrs文字属性，为空表示不设置文本属性，即采用当前位置的文字属性作为新插入文字的文字属性
	 * @return 无
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	void insertString(BindNode bindnode, DocumentPosition pos,
			Map<Integer, Object> attrs);

	/**
	 * 
	 * 在指定位置插入文档对象集合
	 * 
	 * @param elements
	 *            ：文档对象集合，可以是block，Inline等组成的集合。 pos：文档位置。
	 * @return 无
	 * @exception 无
	 */
	void insertElements(List<CellElement> elements, DocumentPosition pos);

	/**
	 * 
	 * 在指定元素的指定位置上插入子元素
	 * 
	 * @param elements
	 *            ：文档对象集合，可以是block，Inline等组成的集合。 parent：要插入元素的父元素 index：插入索引位置
	 * @return 无
	 * @exception 无
	 */
	void insertElements(List<CellElement> elements, CellElement parent,
			int index);

	/**
	 * 
	 * 删除位置间的元素，
	 * 
	 * @param startpos
	 *            ：起始位置，删除时包括该位置 endpos：结束位置，删除时不包括该位置
	 * @return
	 * @exception
	 */
	void deleteElements(List<CellElement> elements);
	/**
	 * 
	 * 删除位置间的元素，
	 * 
	 * @param startpos
	 *            ：起始位置，删除时包括该位置 endpos：结束位置，删除时不包括该位置
	 * @return
	 * @exception
	 */
	void deleteElements(List<CellElement> elements, CellElement parent);

	/**
	 * 
	 * 用指定元素集合elements，替换指定位置间的元素
	 * 
	 * @param startpos
	 *            ：起始位置，替换时包括该位置 endpos：结束位置，替换时不包括该位置。 注意：startpos必须在endpos之前
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	void replaceElements(DocumentPositionRange range, List<CellElement> elements);

	/**
	 * 
	 * 在指定位置插入一个新的章节
	 * 
	 * @param：pos指定位置，如果指定位置为空或是位置不存在的话则在文档的最后插入一个新的章节
	 * @return
	 * @exception
	 */
	void insertPageSequence(DocumentPosition pos);

	/**
	 * 
	 * 删除指定章节
	 * 
	 * @param：sec：要删除的章节
	 * @return
	 * @exception
	 */
	void deletePageSequence(PageSequence sec);

	/**
	 * 
	 * 获得指定位置的段落属性
	 * 
	 * @param pos
	 *            ：指定的文档位置
	 * @return 指定位置的段落属性键，值对
	 * @exception
	 */
	Map<Integer, Object> getParagraphAttributes(DocumentPosition pos);

	/**
	 * 
	 * 获得指定位置的Inline属性
	 * 
	 * @param pos
	 *            ：指定的文档位置
	 * @return 指定位置的Inline属性键，值对
	 * @exception
	 * @exception
	 */
	Map<Integer, Object> getInlineAttributes(DocumentPosition pos);

	/**
	 * 
	 * 获得指定位置的节属性
	 * 
	 * @param pos
	 *            ：指定的文档位置
	 * @return 指定位置的节属性键，值对
	 * @exception
	 * @exception
	 */
	Map<Integer, Object> getSectionAttributes(DocumentPosition pos);

	/**
	 * 
	 * 获得指定位置间的段落属性
	 * 
	 * @param pos
	 *            ：指定的文档位置
	 * @return 指定位置的段落属性键，值对
	 * @exception
	 */
	Map<Integer, Object> getParagraphAttributes(DocumentPositionRange[] ranges);

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
	Map<Integer, Object> getInlineAttributes(DocumentPositionRange[] ranges);

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
	Map<Integer, Object> getSectionAttributes(DocumentPositionRange[] ranges);

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
	Map<Integer, Object> getAttributes(DocumentPositionRange[] ranges,
			Class clazs);

	/**
	 * 
	 * 设置指定位置的段落属性
	 * 
	 * @param pos
	 *            ：指定位置 attrs段落属性 isreplace:是否替换原来的段落属性
	 * @return 无
	 * @exception 无
	 */
	void setParagraphAttributes(DocumentPosition pos,
			Map<Integer, Object> attrs, boolean isreplace);

	/**
	 * 
	 * 设置两位置之间的段落的属性
	 * 
	 * @param startpos
	 *            ：指定的起始位置 endpos：结束位置 attrs：段落属性 attrs段落属性
	 *            isreplace:是否替换原来的段落属性
	 * @return 无
	 * @exception 无
	 */
	void setParagraphsAttributes(DocumentPositionRange range,
			Map<Integer, Object> attrs, boolean isreplace);

	/**
	 * 
	 * 设置多个范围内的段落属性
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void setParagraphsAttributes(DocumentPositionRange[] ranges,
			Map<Integer, Object> attrs, boolean isreplace);

	/**
	 * 
	 * 设置Inline级别元素的属性
	 * 
	 * @param startpos
	 *            ：指定的起始位置 endpos：结束位置 attrs：段落属性 attrs段落属性 isreplace:是否替换原来的属性
	 * @return 无
	 * @exception 无
	 */
	void setInlineAttributes(DocumentPositionRange range,
			Map<Integer, Object> attrs, boolean isreplace);

	/**
	 * 
	 * 设置Inline级别元素的属性
	 * 
	 * @param startpos
	 *            ：指定的起始位置 endpos：结束位置 attrs：段落属性 attrs段落属性 isreplace:是否替换原来的属性
	 * @return 无
	 * @exception 无
	 */
	void setInlineAttributes(DocumentPositionRange[] ranges,
			Map<Integer, Object> attrs, boolean isreplace);

	/**
	 * 
	 * 设置元素的属性，章节，页布局，表格等属性时用该方法
	 * 
	 * @param element
	 *            ：要设置属性的元素 attrs：要设置的属性集合 isreplace：是否替换掉原属性
	 * @return
	 * @exception
	 */
	void setElementAttributes(CellElement element, Map<Integer, Object> attrs,
			boolean isreplace);

	/**
	 * 
	 * 设置元素的属性，章节，页布局，表格等属性时用该方法，
	 * 
	 * @param elements
	 *            ：要设置属性的元素集合 attrs：要设置的属性集合 isreplace：是否替换掉原属性
	 * @return
	 * @exception
	 */
	void setElementAttributes(List<CellElement> elements,
			Map<Integer, Object> attrs, boolean isreplace);

	/**
	 * 
	 * 获得指定范围间的元素
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	List<CellElement> getElements(List<DocumentPositionRange> ranges);

	/**
	 * 
	 * 获得文档中的文本内容
	 * 
	 * @param 无
	 * 
	 * @return 整篇文档的文本内容
	 * @exception
	 */
	String getText();

	/**
	 * 
	 * 获得指定位置间的文本内容
	 * 
	 * @param start
	 *            ：起始位置 end：结束位置，文本内容是指包括起始位置，但不包括结束位置的文本内容 {引入参数说明}
	 * @return 文本内容
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	String getText(DocumentPositionRange range);

	/**
	 * 
	 * 获得指定点之前的所有文本
	 * 
	 * @param pos
	 *            ：指定位置
	 * @return
	 * @exception
	 */
	String getTextBeforePos(DocumentPosition pos);

	/**
	 * 
	 * 将文本偏移量转换成内部位置接口
	 * 
	 * @param offset
	 *            ：偏移量，是指文本数的偏移量，注意一个图片代表一个字符，一个动态绑定节点也是代表一个字符。
	 * @return 内部位置
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	DocumentPosition converoffsettoPos(int offset);

	/**
	 * 
	 * 将内部位置转换成文本偏移量
	 * 
	 * @param pos
	 *            ：位置
	 * @return 偏移量
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	int converPostooffset(DocumentPosition pos);

	/**
	 * 
	 * 设置数据结构
	 * 
	 * @param datasm
	 *            ：数据结构对象
	 * @return
	 * @exception
	 */
	void setDataStructure(DataStructureTreeModel datasm);
	/**
	 * 
	 * 以不记录到操作队列的方式设置数据结构，在undo，redo的相关内中调用
	 *
	 * @param datasm
	 *            ：数据结构对象
	 * @return     
	 * @exception  
	 */
	public void setDataStructureWithoutEdit(final DataStructureTreeModel datasm);
	/**
	 * 
	 * 获得文档的数据结构对象
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	DataStructureTreeModel getDataStructure();

	/**
	 * 
	 * 添加数据结构改变事件监听器
	 * 
	 * @param listener
	 *            ：数据结构改变事件监听器
	 * @return
	 * @exception
	 */
	void addDataStructureChangeListener(DataStructureChangeListener listener);

	/**
	 * 
	 * 添加文档事件
	 * 
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void addDocumentListener(DocumentListener listener);

	/**
	 * 
	 * 删除文档事件
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	void removeDocumentListener(DocumentListener listener);

	/**
	 * 
	 * 获得文档的撤销操作管理器
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	DocumentUndoManager getDocumentUndoManager();

	public void firechangedUpdate(DocumentChangeEvent event);

	/**
	 * 
	 * 设置文件保存路径
	 * 
	 * @param path
	 *            ：文件保存路径
	 * @return
	 * @exception
	 */
	public void setSavePath(String path);

	/**
	 * 
	 * 获得文件保存路径
	 * 
	 * @param
	 * @return 文件保存路径，为空表示没有设置文件保存路径
	 * @exception
	 */
	public String getSavePath();

	/**
	 * 
	 * 文档的最新修改是否已保存
	 * 
	 * @param
	 * @return 最新修改已保存则返回true，否则返回false。
	 * @exception
	 */
	public boolean isSaved();

	/**
	 * 判断当前元素是否在当前文档编辑过程发生了改变，整个判断的思路是如果该对象本身或其祖先对
	 * 象发生了改变，则返回真
	 * @param cell
	 * @return
	 */
	public boolean isCellChanged(CellElement cell);
	public void setChangeCells(List<Element> changeelements);
	/**
	 * 
	 * 获得文档中的目录内容
	 *
	 * @param     无
	 * @return      TableContents:  文档中的目录对象
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	public TableContents getTableContents();

}
