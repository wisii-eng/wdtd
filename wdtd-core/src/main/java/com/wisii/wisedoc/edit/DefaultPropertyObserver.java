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
 * @DefaultPro.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.edit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.DocumentUtil;
import com.wisii.wisedoc.document.Flow;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableBody;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableContents;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.document.svg.Canvas;
import com.wisii.wisedoc.document.svg.SVGContainer;
import com.wisii.wisedoc.view.AbstractEditComponent;
import com.wisii.wisedoc.view.select.DocumentPositionRange;
import com.wisii.wisedoc.view.select.SelectionModel;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.observer.UIObserver;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-9-25
 */
public class DefaultPropertyObserver implements Observer
{

	private AbstractEditComponent currentEditPanel;

	public DefaultPropertyObserver(final Observable observable)
	{
		observable.addObserver(this);

	}

	public void update(final Observable o, final Object arg)
	{
		if (o instanceof UIObserver)
		{

			// Document doc = SystemManager.getCurruentDocument();
			final Document doc = getCurrentEditPanel().getDocument();

			if (doc == null)
			{
				return;
			}
			// o里面携带者具体的属性，是由事件源发送过来的
			final UIObserver po = (UIObserver) o;

			// AbstractEditComponent docpanel =
			// SystemManager.getMainframe().getEidtComponent
			// ();//DocumentPanel-->AbstractEditComponent
			final AbstractEditComponent docpanel = getCurrentEditPanel();

			final SelectionModel selectmodel = docpanel.getSelectionModel();
			final List<DocumentPositionRange> rangelist = selectmodel
					.getSelectionCells();
			DocumentPositionRange[] ranges = null;
			List<CellElement> cells = selectmodel.getObjectSelection();
			DocumentPosition pos = docpanel.getCaretPosition();
			final ActionType type = po.getPropertyType();
			if ((cells == null || cells.isEmpty()) && pos != null)
			{
				CellElement poselement = pos.getLeafElement();
				while (poselement != null && !(poselement instanceof Flow))
				{
					if (poselement instanceof TableCell
							|| poselement instanceof BlockContainer)
					{
						if (cells == null)
						{
							cells = new ArrayList<CellElement>();
						}
						if (!cells.contains(poselement))
						{
							if ((poselement instanceof TableCell)
									&&type==ActionType.TABLE_CELL)
							{
								cells.add(poselement);
								break;
							}
							if ((poselement instanceof BlockContainer)
									&&type==ActionType.BLOCK_CONTAINER)
							{
								cells.add(poselement);
								break;
							}
						}
					}
					poselement = (CellElement) poselement.getParent();
				}

			}
			if (rangelist != null && !rangelist.isEmpty())
			{
				final int size = rangelist.size();
				ranges = new DocumentPositionRange[size];
				for (int i = 0; i < size; i++)
				{
					ranges[i] = rangelist.get(i);
				}

			}
			// Observer添加了返回多个属性集合Map的getProperties()方法
			// Map<Integer, Object> attrs = new HashMap<Integer, Object>();
			// attrs.put(po.getPropertyId(), po.getProperty());
			final Map<Integer, Object> attrs = po.getProperties();

//			System.out.println("接收到的属性值： " + attrs);

			if (type == ActionType.BLOCK)
			{
				if (ranges != null)
				{
					doc.setParagraphsAttributes(ranges, attrs, false);
				} else if (pos != null)
				{
					doc.setParagraphAttributes(docpanel.getCaretPosition(),
							attrs, false);
					pos.setBlockAttributes(attrs, false);
				} else if (cells != null && !cells.isEmpty())
				{
					final List<CellElement> elements = DocumentUtil
							.getElements(cells, Block.class);
					if (elements != null && !elements.isEmpty())
					{
						doc.setElementAttributes(elements, attrs, false);
					}
				}
			} else if (type == ActionType.INLINE)
			{
				if (ranges != null)
				{
					doc.setInlineAttributes(ranges, attrs, false);
				} else if (pos != null)
				{
					pos.setInlineAttributes(attrs, false);
				} else if (cells != null && !cells.isEmpty())
				{
					final List<CellElement> elements = DocumentUtil
							.getElements(cells, Inline.class);
					if (elements != null && !elements.isEmpty())
					{
						doc.setElementAttributes(elements, attrs, false);
					}
				}
			} else if (type == ActionType.LAYOUT
					|| type == ActionType.PAGE_SEQUENCE
			/*
			 * || type == ActionType.REGION_BEFORE || type ==
			 * ActionType.REGION_AFTER || type == ActionType.REGION_START ||
			 * type == ActionType.REGION_END
			 */)
			{
				if (pos == null)
				{
					if (ranges != null && ranges[0] != null)
					{
						pos = ranges[0].getStartPosition();
					}
				}
				if (pos != null)
				{
					CellElement element = pos.getLeafElement();
					while (element != null
							&& !(element instanceof PageSequence))
					{
						element = (CellElement) element.getParent();
					}
					if (element != null && element instanceof PageSequence)
					{
						doc.setElementAttributes(element, attrs, false);
					}
				}
			}
			else if(type == ActionType.SVG_GRAPHIC)
			{
				if (cells != null && !cells.isEmpty())
				{
					//如果含有针对其父对象的SVGContainer的属性，则将该属性设置到SVGContainer对象上
					if (attrs != null
							&& attrs.containsKey(Constants.PR_SVG_CONTAINER))
					{
						Map<Integer, Object> svgcontaineratt = (Map<Integer, Object>) attrs
								.get(Constants.PR_SVG_CONTAINER);
						attrs.remove(Constants.PR_SVG_CONTAINER);
						final List<CellElement> tosetelements = DocumentUtil
								.getParentElementofType(cells,
										SVGContainer.class);
						if (tosetelements != null && !tosetelements.isEmpty())
						{
							doc.setElementAttributes(tosetelements,
									svgcontaineratt, false);
						}
					}
					//如果含有针对其父对象的Canvas的属性，则将该属性设置到Canvas对象上
					if (attrs != null
							&& attrs.containsKey(Constants.PR_SVG_CANVAS))
					{
						Map<Integer, Object> svgcontaineratt = (Map<Integer, Object>) attrs
								.get(Constants.PR_SVG_CANVAS);
						attrs.remove(Constants.PR_SVG_CANVAS);
						final List<CellElement> tosetelements = DocumentUtil
								.getParentElementofType(cells, Canvas.class);
						if (tosetelements != null && !tosetelements.isEmpty())
						{
							doc.setElementAttributes(tosetelements,
									svgcontaineratt, false);
						}
					}
					if (attrs != null && !attrs.isEmpty())
					{
						doc.setElementAttributes(cells, attrs, false);
					}
				}
			}
			else if (type == ActionType.GRAPH
					|| type == ActionType.NUMBERTEXT
					|| type == ActionType.DATETIME
					|| type == ActionType.Groupable || type == ActionType.Chart
					|| type == ActionType.WordArtText
					|| type == ActionType.ENCRYPTTEXT
					|| type == ActionType.CheckBox)
			{
				if (cells != null && !cells.isEmpty())
				{
					doc.setElementAttributes(cells, attrs, false);
				}
			} else if (type == ActionType.Edit)
			{
				if (pos != null)
				{
					CellElement poselement = pos.getLeafElement();
					doc.setElementAttributes(poselement, attrs, false);
				} else if (cells != null && !cells.isEmpty())
				{
					doc.setElementAttributes(cells, attrs, false);
				}
			} else if (type == ActionType.BLOCK_CONTAINER)
			{
				if (cells != null && !cells.isEmpty())
				{
					final List<CellElement> tosetelements = DocumentUtil
							.getParentElementofType(cells, BlockContainer.class);
					if (tosetelements != null && !tosetelements.isEmpty())
					{
						doc.setElementAttributes(tosetelements, attrs, false);
					}
				}
			} else if (type == ActionType.TABLE_AND_CAPTION
					|| type == ActionType.TABLE)
			{
				if (cells != null && !cells.isEmpty())
				{
					final List<CellElement> tosetelements = DocumentUtil
							.getParentElementofType(cells, Table.class);
					if (tosetelements != null && !tosetelements.isEmpty())
					{
						doc.setElementAttributes(tosetelements, attrs, false);
					}
				}
			} else if (type == ActionType.TABLE_BODY
					|| type == ActionType.TABLE_FOOTER
					|| type == ActionType.TABLE_HEADER)
			{
				if (cells != null && !cells.isEmpty())
				{
					final List<CellElement> tablecells = DocumentUtil
							.getElements(cells, TableCell.class);
					final List<CellElement> tosetelements = DocumentUtil
							.getParentElementofType(tablecells, TableBody.class);
					if (tosetelements != null && !tosetelements.isEmpty())
					{
						doc.setElementAttributes(tosetelements, attrs, false);
					}
				}
			} else if (type == ActionType.TABLE_ROW)
			{
				if (cells != null && !cells.isEmpty())
				{
					final List<CellElement> tablecells = DocumentUtil
							.getElements(cells, TableCell.class);
					final List<CellElement> tosetelements = DocumentUtil
							.getParentElementofType(tablecells, TableRow.class);
					if (tosetelements != null && !tosetelements.isEmpty())
					{
						doc.setElementAttributes(tosetelements, attrs, false);
					}
				}
			} else if (type == ActionType.TABLE_CELL)
			{
				if (cells != null && !cells.isEmpty())
				{
					final List<CellElement> tosetelements = DocumentUtil
							.getElements(cells, TableCell.class);
					if (tosetelements != null && !tosetelements.isEmpty())
					{
						if (attrs
								.containsKey(Constants.PR_INLINE_PROGRESSION_DIMENSION))
						{
                              dealTablecellrowspan(tosetelements,doc);
						}
						doc.setElementAttributes(tosetelements, attrs, false);
					}
				}
			} else if (type == ActionType.INDEX)
			{
				final TableContents tc = doc.getTableContents();
				doc.setElementAttributes(tc, attrs, false);
			}

			// 读取并设置属性
			// this.setProperty(po.getPropertyId(), po.getProperty());
			//			
			//			
			// //测试该观察者接收到的属性
			// System.out.println("接收到的属性： " +
			// po.getProperty().getClass().toString()
			// + " 值：" + po.getProperty().toString() + " 所属类型：" +
			// po.getPropertyType());
		}

	}

	/**
	 * 设置了单元格的宽度属性后，会对该单元格以及之后的跨多行的单元格有影响。
     * 这些跨行的单元格需要查分成三段或两段
	 *
	 * @param      {引入参数名}   {引入参数说明}
	 * @return      {返回参数名}   {返回参数说明}
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	
	private void dealTablecellrowspan(List<CellElement> tosetelements,Document doc)
	{
		for (CellElement cell : tosetelements)
		{
			TableRow row = (TableRow) cell.getParent();
			if (row != null)
			{
				int index = row.getIndex(cell);
				int size = row.getChildCount();
				TableBody tablebody = (TableBody) row.getParent();
				if (tablebody == null)
				{
					return;
				}
				for (int i = index; i < size; i++)
				{
					TableCell tablecell = (TableCell) row.getChildAt(i);
					int rowspan = tablecell.getNumberRowsSpanned();
					// 跨多行时，需要特殊处理
					if (rowspan > 1)
					{
						TableRow crow = (TableRow) tablecell.getParent();
						// 如果第一行，则该单元格拆分成当前行和之后行两段
						int rowindex = tablebody.getIndex(row);
						if (crow == row)
						{
							Map<Integer, Object> atts = new HashMap<Integer, Object>();
							atts.put(Constants.PR_NUMBER_ROWS_SPANNED, null);
							doc.setElementAttributes(tablecell, atts, false);
							List<CellElement> todels = new ArrayList<CellElement>();
							todels.add(tablecell);
							Map<Integer, Object> cellatts = tablecell
									.getAttributes().getAttributes();
							int newrowspan = rowspan - 1;
							if (newrowspan > 1)
							{
								cellatts.put(Constants.PR_NUMBER_ROWS_SPANNED,
										newrowspan);
							}
							List<CellElement> toadds = new ArrayList<CellElement>();
							TableCell addtc = new TableCell(cellatts);
							toadds.add(addtc);
							for (int j = 1; j < rowspan; j++)
							{
								TableRow cellrow = (TableRow) tablebody
										.getChildAt(rowindex + j);
								int cindex = cellrow.getIndex(tablecell);
								doc.deleteElements(todels, cellrow);
								doc.insertElements(toadds, cellrow, cindex);
							}
						}
						// 如果不是第一行，则该单元格拆分成之前行，当前行和之后行三段
						else
						{
							int crowindex = tablebody.getIndex(crow);
							Map<Integer, Object> atts = new HashMap<Integer, Object>();
							int newrowspan = rowindex - crowindex;
							if (newrowspan > 1)
							{
								atts.put(Constants.PR_NUMBER_ROWS_SPANNED,
										newrowspan);
							} else
							{
								atts
										.put(Constants.PR_NUMBER_ROWS_SPANNED,
												null);
							}
							doc.setElementAttributes(tablecell, atts, false);
							List<CellElement> todels = new ArrayList<CellElement>();
							todels.add(tablecell);
							Map<Integer, Object> cellatts = tablecell
									.getAttributes().getAttributes();
							cellatts.remove(Constants.PR_NUMBER_ROWS_SPANNED);
							TableCell addtc1 = new TableCell(cellatts);
							int nextnewspan = rowspan - newrowspan - 1;
							if (nextnewspan > 1)
							{
								cellatts.put(Constants.PR_NUMBER_ROWS_SPANNED,
										nextnewspan);
							}
							TableCell addtc2 = new TableCell(cellatts);
							List<CellElement> toadd1s = new ArrayList<CellElement>();
							toadd1s.add(addtc1);
							List<CellElement> toadd2s = new ArrayList<CellElement>();
							toadd2s.add(addtc2);
							for (int j = newrowspan; j < rowspan; j++)
							{
								TableRow cellrow = (TableRow) tablebody
										.getChildAt(crowindex + j);
								int cindex = cellrow.getIndex(tablecell);
								doc.deleteElements(todels, cellrow);
								if (j == newrowspan)
								{
									doc
											.insertElements(toadd1s, cellrow,
													cindex);
								} else
								{
									doc
											.insertElements(toadd2s, cellrow,
													cindex);
								}
							}
						}
					}
				}
			}
		}
	}

	public AbstractEditComponent getCurrentEditPanel()
	{
		return currentEditPanel;
	}

	public void setCurrentEditPanel(final AbstractEditComponent currentEditPanel)
	{
		this.currentEditPanel = currentEditPanel;
	}

}
