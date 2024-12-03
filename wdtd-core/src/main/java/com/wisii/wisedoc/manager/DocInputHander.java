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
package com.wisii.wisedoc.manager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.AbstractGraphics;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.DocumentPositionUtil;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.Group;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.TableBody;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.document.Text;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.ZiMoban;
import com.wisii.wisedoc.io.XMLUtil;
import com.wisii.wisedoc.view.AbstractEditComponent;
import com.wisii.wisedoc.view.CaretLocationConvert;
import com.wisii.wisedoc.view.CaretLocationConvert.PositionType;
import com.wisii.wisedoc.view.select.DocumentPositionRange;
import com.wisii.wisedoc.view.select.SelectionModel;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

public class DocInputHander implements KeyListener
{

	private int keycode;
	private boolean iscontrolpress;

	private KeyEvent e;

	private SelectionModel selectModel;
	private Document document;
	private DocumentPosition caretPosition;
	private final AbstractEditComponent documentPanel;

	public DocInputHander(final AbstractEditComponent documentPanel)
	{
		this.documentPanel = documentPanel;
		updateInfo();
	}

	private void updateInfo()
	{
		this.selectModel = this.documentPanel.getSelectionModel();
		this.document = this.documentPanel.getDocument();
		this.caretPosition = this.documentPanel.getCaretPosition();
	}

	public void keyPressed(final KeyEvent e)
	{
		keycode = e.getKeyCode();
		if (keycode == KeyEvent.VK_HOME || keycode == KeyEvent.VK_END
				|| keycode == KeyEvent.VK_UP || keycode == KeyEvent.VK_DOWN
				|| keycode == KeyEvent.VK_PAGE_DOWN
				|| keycode == KeyEvent.VK_PAGE_UP
				|| keycode == KeyEvent.VK_LEFT || keycode == KeyEvent.VK_RIGHT)
		{
			DocumentPosition caret = documentPanel.getCaretPosition();
			switch (keycode)
			{
			case KeyEvent.VK_HOME:
				caret = CaretLocationConvert.getPosition(caret, PositionType.HOME);
				break;
			case KeyEvent.VK_END:
				caret = CaretLocationConvert.getPosition(caret, PositionType.END);
				break;
			case KeyEvent.VK_UP:
				caret = CaretLocationConvert.getPosition(caret, PositionType.UP);
				break;
			case KeyEvent.VK_DOWN:
				caret = CaretLocationConvert.getPosition(caret, PositionType.DOWN);
				break;
			case KeyEvent.VK_PAGE_UP:
				caret = CaretLocationConvert.getPosition(caret, PositionType.PAGE_UP);
				break;
			case KeyEvent.VK_PAGE_DOWN:
				caret = CaretLocationConvert.getPosition(caret, PositionType.PAGE_DOWN);
				break;
			case KeyEvent.VK_LEFT:
				caret = CaretLocationConvert.getPosition(caret, PositionType.LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				caret = CaretLocationConvert.getPosition(caret, PositionType.RIGHT);
				break;
			}
			if (caret == null || caret == caretPosition)
			{
				return;
			}
			DocumentPosition oldpos = documentPanel.getCaretPosition();
			if (e.isShiftDown())
			{
				updateSelect(documentPanel.getSelectionModel(), oldpos, caret);
			}
			else
			{
			
				documentPanel.getSelectionModel().clearSelection();
			}
			caretPosition = caret;
			documentPanel.setCaretPosition(caretPosition);
		}
	}

	public void keyReleased(final KeyEvent e)
	{

	}

	public void keyTyped(final KeyEvent e)
	{
		this.e = e;
		updateInfo();

		// XXX 输入单独启动一个线程
		// WiseDocThreadService.Instance.doInputService(new KeyType());

		keyTypedAction();
	}

	private class KeyType implements Runnable
	{
		@Override
		public void run()
		{
			keyTypedAction();
		}
	}

	private void keyTypedAction()
	{
		iscontrolpress = !e.isActionKey() && !e.isAltDown()
				&& !e.isAltGraphDown() && !e.isConsumed() && !e.isControlDown()
				&& !e.isMetaDown();
		if (iscontrolpress)
		{
			final char c = e.getKeyChar();
			final DocumentPositionRange range = selectModel.getSelectionCell();
			final List<CellElement> elements = selectModel.getObjectSelection();
			DocumentPosition pos = caretPosition;
			//换行符不能插入到由inline组成的重复组对象中，因此进行控制
			if (c == '\n')
			{
				if (range != null)
				{
					DocumentPosition startpos = range.getStartPosition();
					CellElement startleaf = startpos.getLeafElement();
					//如果当前选中范围的起始点在一个Inline重复组中，则不能输入回车
					if (startleaf instanceof Inline
							&& startleaf.getParent() instanceof Group)
					{
						SystemManager.getMainframe().setStatus(RibbonUIText.FRAME_STATUS_UNABLE_INPUT);
						return;
					}
					DocumentPosition endpos = range.getEndPosition();
					CellElement endleaf = endpos.getLeafElement();
					//如果当前选中范围的结束点在一个Inline重复组中，则不能输入回车
					if (endleaf instanceof Inline
							&& endleaf.getParent() instanceof Group)
					{
						SystemManager.getMainframe().setStatus(RibbonUIText.FRAME_STATUS_UNABLE_INPUT);
						return;
					}
				}
				if (pos == null)
				{
					if (elements != null && !elements.isEmpty())
					{
						CellElement element = elements.get(elements.size() - 1);
						if (element instanceof AbstractGraphics)
						{
							element = (CellElement) element.getParent();
							if (element != null)
							{
								pos = new DocumentPosition(element, true);
							}
						} else if (element instanceof Inline)
						{
							pos = new DocumentPosition(element, true);
						}
					}
				}
				if(pos!=null)
				{
					CellElement leafelement =  pos.getLeafElement();
					//如果当前光标所表示的元素在一个Group类，则表示时一个Inline Group，此时不容许输入回车
					if(leafelement instanceof Inline&&leafelement.getParent() instanceof Group)
					{
						SystemManager.getMainframe().setStatus(RibbonUIText.FRAME_STATUS_UNABLE_INPUT);
						return;
					}
				}
			}
			// List<CellElement> selectobjects =
			// _selectmodel.getObjectSelection();
			if (range != null)
			{
				if (keycode == KeyEvent.VK_BACK_SPACE
						|| keycode == KeyEvent.VK_DELETE)
				{
					if (range != null)
					{
						document.deleteElements(document
								.getElements(selectModel.getSelectionCells()));
					}
				} else
				{
					// 限制非XML支持字符的输入
					if (!XMLUtil.isXMLCharacter(c))
					{
						return;
					}
					final List<CellElement> elemt = new ArrayList<CellElement>();
					final Map<Integer, Object> attsmap = pos
							.getInlineAttriute();
					elemt.add(new TextInline(new Text(c), attsmap));
					document.replaceElements(range, elemt);
				}
				selectModel.clearSelection();
			} else
			{
				// 如果有对象处于选中状态。则删除选中对象
				if ((keycode == KeyEvent.VK_BACK_SPACE || keycode == KeyEvent.VK_DELETE)
						&& !elements.isEmpty())
				{
					List<CellElement> todelete = new ArrayList<CellElement>();
					// 校验，其中如果含有表格行或单元格，则不能通过删除键删，得通过表格的删除按钮删除
					for (CellElement element : elements)
					{
						if (element instanceof TableCell)
						{
							deleteTableCell((TableCell) element);
						} else if (element instanceof TableRow)
						{
							deleteTableRow((TableRow) element);
						} else
						{
							todelete.add(element);
						}
					}
					if (!todelete.isEmpty())
					{
						document.deleteElements(todelete);
					}
					selectModel.clearObjectSelection();
					keycode = 0;
					return;
				}
				if (pos != null)
				{
					if (keycode == KeyEvent.VK_BACK_SPACE
							|| keycode == KeyEvent.VK_DELETE)
					{
						CellElement leaf = pos.getLeafElement();
						// 如果当前光标为一个段落，即空段落对象，则直接删除之
						if (leaf instanceof Block||leaf instanceof ZiMoban)
						{
							final List<CellElement> deleteblocks = new ArrayList<CellElement>();
							deleteblocks.add(leaf);
							document.deleteElements(deleteblocks);
							keycode = 0;
							return;
						}
						final CellElement parent = (CellElement) leaf
								.getParent();
						// 如果不是inline或段落对象则不可删除
						if (parent == null
								|| (!(leaf instanceof Inline) && !(leaf instanceof Block)))
						{
							keycode = 0;
							return;
						}
						final int childransize = parent.getChildCount();
						final int offset = parent.getIndex(leaf);
						// 如果光标在段落的起始位置，则将当前光标所在的段落合并到上一个段落中去
						if (offset == 0 && leaf instanceof Inline
								&& pos.isStartPos()
								&& keycode == KeyEvent.VK_BACK_SPACE)
						{
							final Element grandfather = parent.getParent();
							if (grandfather instanceof CellElement)
							{
								int indexparent = grandfather.getIndex(parent);
								// 如果当前父元素不是祖父元素的第一个元素
								if (indexparent > 0)
								{
									indexparent--;
									// 找到父节点的兄弟节点
									final CellElement parentb = (CellElement) grandfather
											.getChildAt(indexparent);
									if (parentb instanceof Block)
									{
										final List<CellElement> toinsertelements = parent
												.getChildren(0);
										final List<CellElement> todellements = new ArrayList<CellElement>();
										todellements.add(parent);
										document.deleteElements(todellements);
										document.insertElements(
												toinsertelements, parentb,
												parentb.getChildCount());
										final DocumentPosition npos = new DocumentPosition(
												toinsertelements.get(0), true);
										documentPanel.setCaretPosition(npos);
									}
								}
							}
						}
						// 如果是在段落的结束位置，则将后一段落的内容合并到当前段落中
						else if (offset == childransize - 1
								&& leaf instanceof Inline && !pos.isStartPos()
								&& keycode == KeyEvent.VK_DELETE)
						{
							final Element grandfather = parent.getParent();
							if (grandfather instanceof CellElement)
							{
								int indexparent = grandfather.getIndex(parent);
								// 如果当前父元素不是祖父元素的第一个元素
								if (indexparent < grandfather.getChildCount() - 1)
								{
									indexparent++;
									// 找到父节点的兄弟节点
									final CellElement parentb = (CellElement) grandfather
											.getChildAt(indexparent);
									if (parentb instanceof Block)
									{
										final List<CellElement> toinsertelements = parentb
												.getChildren(0);
										final List<CellElement> todellements = new ArrayList<CellElement>();
										todellements.add(parentb);
										document.insertElements(
												toinsertelements, parent,
												parent.getChildCount());
										document.deleteElements(todellements);
										final DocumentPosition npos = new DocumentPosition(
												toinsertelements.get(0), true);
										documentPanel.setCaretPosition(npos);
									}
								}
							}
						} else
						{
							final List<CellElement> elemt = new ArrayList<CellElement>();
							CellElement nleaf = null;
							if (!pos.isStartPos())
							{
								// 如果是delete键，则删除当前光标所在的后一个元素
								if (keycode == KeyEvent.VK_DELETE)
								{
									nleaf = DocumentPositionUtil
											.findnextInline(leaf);

								}
							} else
							{
								// 如果是回退键键，则删除当前光标所在的前一个元素
								if (keycode == KeyEvent.VK_BACK_SPACE)
								{
									nleaf = DocumentPositionUtil
											.findpreviousInline(leaf);
								}
							}
							if (nleaf == leaf)
							{
								leaf = null;
							} else
							{
								if (nleaf != null)
								{
									leaf = nleaf;
								}
							}

							if (leaf != null)
							{
								elemt.add(leaf);
								document.deleteElements(elemt);
							}
						}
					} else
					{
						// 限制非XML支持字符的输入
						if (XMLUtil.isXMLCharacter(c) && !e.isActionKey())
						{
							document.insertString("" + c, pos, pos
									.getInlineAttriute());
						}
					}
				}
			}
		}
		keycode = 0;
	}
	/**
	 * {方法的功能/动作描述}
	 *
	 * @param      {引入参数名}   {引入参数说明}
	 * @return      {返回参数名}   {返回参数说明}
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	
	private void deleteTableRow(TableRow tablerow)
	{
		List<CellElement> cells = tablerow.getAllChildren();
		TableBody tablebody = (TableBody) tablerow.getParent();
		int size = cells.size();
		for (int i = 0; i < size; i++)
		{
			TableCell cell = (TableCell) cells.get(i);
			int rowspan = cell.getNumberRowsSpanned();
			// 如果删除的行的的单元格跨行
			if (rowspan > 1)
			{
				Map<Integer, Object> attmap = new HashMap<Integer, Object>();
				attmap.put(Constants.PR_NUMBER_ROWS_SPANNED, rowspan - 1);
				document.setElementAttributes(cell, attmap, false);
			}
		}
		List<CellElement> rowtodelete = new ArrayList<CellElement>();
		rowtodelete.add(tablerow);
		document.deleteElements(rowtodelete, tablebody);
		selectModel.clearObjectSelection();
		
	}

	/**
	 * {方法的功能/动作描述}
	 *
	 * @param      {引入参数名}   {引入参数说明}
	 * @return      {返回参数名}   {返回参数说明}
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	
	private void deleteTableCell(TableCell tablecell)
	{
		TableRow tablerow = (TableRow) tablecell.getParent();
		if (tablerow != null)
		{
			int tablecellindex = tablerow.getIndex(tablecell);
			int tablecellrowspan = tablecell.getNumberRowsSpanned();
			int size = tablerow.getChildCount();
			// 遍历其后的单元格，如果其后面的单元格的行合并数和该单元格的行合并数不相等，则不可删除
			for (int i = tablecellindex + 1; i < size; i++)
			{
				TableCell temp = (TableCell) tablerow.getChildAt(i);
				if (temp.getNumberRowsSpanned() != tablecellrowspan)
				{
					return;
				}
				else
				{
					if(tablerow!=temp.getParent())
					{
						return ;
					}
				}
			}
			//12|
			//12-这种情况下1或2（1和2分别跨2行，但2的第二行中有个单元格）这样的单元格也不能删除
			if(tablecellrowspan>1)
			{
				TableBody body = (TableBody) tablerow.getParent();
				if (body == null)
				{
					return;
				}
				int rowindex = body.getIndex(tablerow);
				int rowsize = body.getChildCount();
				for (int i = rowindex + 1; i < rowsize; i++)
				{
					TableRow nextrow = (TableRow) body.getChildAt(i);
					if (nextrow.getChildCount() - 1 != nextrow
							.getIndex(tablerow.getChildAt(tablerow.getChildCount()-1)))
					{
						return;
					}
				}
			}

		} else
		{
			return;
		}
		List<CellElement> todeletes = new ArrayList<CellElement>();
		todeletes.add(tablecell);
		int rowspan = tablecell.getNumberRowsSpanned();
		TableRow row = (TableRow) tablecell.getParent();
		int childindex = row.getIndex(tablecell);
		if (rowspan > 1)
		{
			TableBody tbody = (TableBody) row.getParent();
			int index = row.getParent().getIndex(row);
//			如果单元格跨多行，则需要在所有行中删除该单元格
			for (int i = 0; i < rowspan; i++)
			{
				document.deleteElements(todeletes, tbody.getChildAt(index + i));
			}

		} else
		{
			document.deleteElements(todeletes, row);
		}
		if(childindex>0)
		{
			TableCell formatecell = (TableCell) row.getChildAt(childindex - 1);
			int crowspan = formatecell.getNumberRowsSpanned();
			if (crowspan > 1)
			{
				TableBody body = (TableBody) row.getParent();
				List<TableRow> formaterows = new ArrayList<TableRow>();
				TableRow formaterow = (TableRow) formatecell.getParent();
				int rowindex = body.getIndex(formaterow);
				for (int i = 0; i < crowspan; i++)
				{
					formaterows.add((TableRow) body.getChildAt(rowindex + i));
				}
				formateTableRows(formaterows, document);
			}
		}
	}
	private void formateTableRows(List<TableRow> rows,Document doc)
	{
		if (rows != null && !rows.isEmpty()&&rows.size()>1)
		{
			int size = rows.size();
			// 收集只有一个单元格的行
			for (int i = 0; i < size;)
			{
				TableRow tr = rows.get(i);
				//找到当前行中公共的跨行的单元格
				List<CellElement> tablecells = tr.getAllChildren();
				int toincrease = 1;
				int tablecellsize = tablecells.size(); 
				int minspanrow = size-i;
				for(int j=0;j<tablecellsize;j++)
				{
					TableCell tablecell = (TableCell) tablecells.get(j);
					int rowspan =  getrowspan(tr,tablecell); 
					if(rowspan<minspanrow)
					{
						minspanrow = rowspan;
					}
					if(minspanrow<=1)
					{
						break;
					}
				}
				//往后遍历该单元格之后的表格行，确保该单元格后在其他行中没有其之后的单元格，
				//如如下情况下删除最后一个单元格
				//|||
				//|||-
				if(minspanrow>1)
				{
					for (int k = minspanrow - 1; k > 0; k--)
					{
						TableRow nexttr = rows.get(i + k);
						int index = nexttr.getIndex(tablecells.get(tablecells
								.size() - 1));
						if (index < 0)
						{
							break;
						} else
						{
							if (index != nexttr.getChildCount() - 1)
							{
								minspanrow--;
							} else
							{
								break;
							}
						}
					}
				}
				if(minspanrow>1)
				{
					toincrease = minspanrow;
					for(int j=0;j<tablecellsize;j++)
					{
						TableCell tablecell = (TableCell) tablecells.get(j);
						int newrowspan = tablecell.getNumberRowsSpanned()-minspanrow+1; 
						Map<Integer,Object> tcattrs = new HashMap<Integer, Object>();
						tcattrs.put(Constants.PR_NUMBER_ROWS_SPANNED, newrowspan);
						// 设置单元新的rowspan属性
						doc.setElementAttributes(tablecell, tcattrs, false);
					}
					List<CellElement> toMergerow = new ArrayList<CellElement>();
					for(int k=i+1;k<i+minspanrow;k++)
					{
						toMergerow.add(rows.get(k));
					}
					if (!toMergerow.isEmpty())
					{
						doc.deleteElements(toMergerow,(CellElement) rows.get(0).getParent());
					}
				}
				i+=toincrease;
			}
			
		}
	}

	/**
	 * {方法的功能/动作描述}
	 *
	 * @param      {引入参数名}   {引入参数说明}
	 * @return      {返回参数名}   {返回参数说明}
	 * @exception   {说明在某情况下,将发生什么异常}
	 */
	
	private int getrowspan(TableRow tr, TableCell tablecell)
	{
		int rowspan = tablecell.getNumberRowsSpanned();
		if (rowspan > 1)
		{
			TableBody tablebody = (TableBody) tr.getParent();
			for (CellElement tablerow : tablebody.getAllChildren())
			{
				if (tablerow == tr)
				{
					break;
				}
				if (tablerow.getIndex(tablecell) > -1)
				{
					rowspan--;
				}
			}
			return rowspan;
		}
		return 1;
	}
	private void updateSelect(SelectionModel selectionModel,
			DocumentPosition pos, DocumentPosition npos)
	{
		if (selectionModel != null && pos != null && npos != null)
		{
			DocumentPositionRange currentrange = selectionModel
					.getSelectionCell();
			if (currentrange != null)
			{
				DocumentPosition startpos = currentrange.getStartPosition();
				DocumentPosition endpos = currentrange.getEndPosition();
				DocumentPositionRange ncell = null;
				// 如果光标在当前选中区域的两端
				if (startpos.equals(pos) || endpos.equals(pos))
				{
					if (startpos.equals(pos))
					{
						ncell = DocumentPositionRange.creatSelectCell(npos,
								endpos);

					} else
					{
						ncell = DocumentPositionRange.creatSelectCell(startpos,
								npos);

					}
					selectionModel.replaceSelectionCell(currentrange, ncell);
				}
				// 如果光标在当前选中区域之间。do nothing
				else if (startpos.compareTo(pos) > 0
						&& endpos.compareTo(pos) < 0)
				{

				}
				// 否则
				else
				{
					selectionModel.addSelectionCell(DocumentPositionRange
							.creatSelectCell(pos, npos));
				}
			} else
			{
				selectionModel.addSelectionCell(DocumentPositionRange
						.creatSelectCell(pos, npos));
			}
		}

	}
}
