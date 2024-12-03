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
 * @SelectHandler.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.mousehandler;

import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.UIManager;

import sun.swing.SwingUtilities2;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.AbstractGraphics;
import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.ZiMoban;
import com.wisii.wisedoc.view.AbstractEditComponent;
import com.wisii.wisedoc.view.select.DocumentPositionRange;
import com.wisii.wisedoc.view.select.SelectBlockAction;
import com.wisii.wisedoc.view.select.SelectWordAction;
import com.wisii.wisedoc.view.select.SelectionModel;
/**
 * 类功能描述：处理选择操作
 * 
 * 作者：zhangqiang 创建日期：2008-10-30
 */
public class SelectHandler extends AbstractMouseHandler
{
	// 单选
	public static final int SINGLE_ELEMENT_SELECTION = 1;
	// 多选
	public static final int MULTIPLE_ELEMENT_SELECTION = 4;
	// 光标位置
	protected DocumentPosition _currentpos;
	// 段落选中处理类
	protected SelectBlockAction _selblockaction = new SelectBlockAction();
	// 词选中时间处理类
	protected SelectWordAction _selwordaction = new SelectWordAction();
	// 选择操作是否可用标识
	private boolean _isvisiable = true;
	private transient boolean shouldHandleRelease;
	/**
	 * holds last MouseEvent which caused the word selection
	 */
	private transient MouseEvent selectedWordEvent = null;
	private boolean isfirstdrag = true;
	private DocumentPositionRange oldscell;

	/**
	 * 
	 * 选中操作是否可用
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public boolean isSelectVisable()
	{
		// TODO Auto-generated method stub
		return _isvisiable;
	}

	/**
	 * 设置选中模式
	 * 
	 * 
	 * @param mode
	 *            ：要设置成的选中模式
	 * @return
	 * @exception
	 */
	public void setSelectMode(int mode)
	{
		_editor.getSelectionModel().setSelectionMode(mode);

	}

	/**
	 * 
	 * 设置选中操作的可用性
	 * 
	 * @param visable
	 *            ：
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public void setSelectVisable(boolean visable)
	{
		_isvisiable = visable;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e)
	{
		if (!e.isConsumed())
		{
			int nclicks = e.getClickCount();
			if (SwingUtilities.isLeftMouseButton(e))
			{
				// mouse 1 behavior
				if (nclicks == 1)
				{
					selectedWordEvent = null;
				}
				// 如果双击在调用词选中处理功能
				else if (nclicks == 2
						&& SwingUtilities2.canEventAccessSystemClipboard(e))
				{
					selectWord(e);
					selectedWordEvent = null;
				}
				// 如果三连击，则选中整个段落
				else if (nclicks == 3
						&& SwingUtilities2.canEventAccessSystemClipboard(e))
				{
					_selblockaction.actionPerformed(new ActionEvent(_editor,
							ActionEvent.ACTION_PERFORMED, null, e.getWhen(), e
									.getModifiers()));
				}
			} else if (SwingUtilities.isMiddleMouseButton(e))
			{
				// mouse 2 behavior
				if (nclicks == 1 && _editor.isEditable() && _editor.isEnabled()
						&& SwingUtilities2.canEventAccessSystemClipboard(e))
				{
					// paste system selection, if it exists
					AbstractEditComponent c = (AbstractEditComponent) e.getSource();
					if (c != null)
					{
						try
						{
							Toolkit tk = c.getToolkit();
							Clipboard buffer = tk.getSystemSelection();
							if (buffer != null)
							{
								// platform supports system selections, update
								// it.
								adjustposandfouse(e);
								TransferHandler th = c.getTransferHandler();
								if (th != null)
								{
									Transferable trans = null;

									try
									{
										trans = buffer.getContents(null);
									} catch (IllegalStateException ise)
									{
										// clipboard was unavailable
										UIManager.getLookAndFeel()
												.provideErrorFeedback(c);
									}

									if (trans != null)
									{
										th.importData(c, trans);
									}
								}
								adjustFocus(true);
							}
						} catch (HeadlessException he)
						{
							// do nothing... there is no system clipboard
						}
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e)
	{
		if (SwingUtilities.isLeftMouseButton(e))
		{
			if (e.isConsumed())
			{
				shouldHandleRelease = true;
			} else
			{
				// isfirstdrag = true;
				shouldHandleRelease = false;
				adjustposandfouse(e);

				if (e.getClickCount() == 2
						&& SwingUtilities2.canEventAccessSystemClipboard(e))
				{
					selectWord(e);
				}
			}
		}
	}

	protected void adjustposandfouse(MouseEvent e)
	{
		DocumentPosition pos = _editor.pointtodocpos(e.getPoint());
		if (pos != null)
		{
			SelectionModel smodel = _editor.getSelectionModel();
			if ((e.getModifiers() & ActionEvent.SHIFT_MASK) != 0
					&& _currentpos != null)
			{
				DocumentPositionRange scell = DocumentPositionRange
						.creatSelectCell(_currentpos, pos);
				if (scell != null)
				{
					if (isfirstdrag)
					{
						smodel.addSelectionCell(scell);

						isfirstdrag = false;

					} else
					{
						smodel.replaceSelectionCell(oldscell, scell);
					}
					oldscell = scell;
				}
			} else
			{
				isfirstdrag = true;
			}
			// 如果没有按住了shift圈选键，则移动光标
			if (_currentpos == null
					|| (e.getModifiers() & ActionEvent.SHIFT_MASK) == 0)
			{
				_currentpos = pos;
			}
			// 如果没有按住ctrl键，则设置光标，且清空选中状态
			if ((e.getModifiers() & (ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK)) == 0)
			{
				DocumentPositionRange scell = smodel.getSelectionCell();
				if (scell == null
						|| pos.compareTo(scell.getStartPosition()) < 0
						|| pos.compareTo(scell.getEndPosition()) > 0
						|| shouldHandleRelease)
				{
					smodel.clearSelection();
				} else
				{
					shouldHandleRelease = true;
				}
				CellElement poselement = pos.getLeafElement();
				CellElement element = poselement.getChildAt(0);
				if (pos.isOn() && (poselement instanceof Inline)
						&& !(poselement instanceof TextInline))
				{
					if (element instanceof AbstractGraphics)
					{
						smodel.addObjectSelection(element);
					} else
					{
						smodel.addObjectSelection(poselement);
					}
					_editor.setCaretPosition(null);
				} else
				{
					Element poselemparent = poselement.getParent();
					//是否在子模板区域内
					boolean isinzimoban = false;
					Element leaf = poselemparent;
					while (leaf != null && !(leaf instanceof PageSequence))
					{
						if (leaf instanceof ZiMoban)
						{
							poselement = (CellElement) leaf;
							poselemparent = leaf.getParent();
							isinzimoban = true;
							break;
						}
						leaf = leaf.getParent();
					}
					if (isinzimoban||(!(poselement instanceof Inline)
							&& !(poselement instanceof Block) && !(poselement instanceof TableCell)&& !pos.isOn()))
					{
						//	如果是最后一个元素，则添加一个空block					
						boolean islastone = (poselemparent.getChildCount() == (poselemparent
								.getIndex(poselement) + 1));
						if (islastone)
						{
							if (poselemparent instanceof CellElement)
							{
								Document doc = SystemManager
										.getCurruentDocument();
								int index = poselemparent.getIndex(poselement);
								List<CellElement> elements = new ArrayList<CellElement>();
								Block block = null;
								Map<Integer, Object> blockatt = pos
										.getBlockAttriute();
								if (blockatt == null)
								{
									block = new Block();
								} else
								{
									block = new Block(blockatt);
								}
								elements.add(block);
								doc.insertElements(elements,
										(CellElement) poselemparent, index + 1);
							}
						}
					} else
					{
						_editor.setCaretPosition(pos);
					}

				}
			}
			adjustFocus(false);
		}

	}

	/**
	 * 
	 * 选中单词事件
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	private void selectWord(MouseEvent e)
	{
		if (selectedWordEvent != null && selectedWordEvent.getX() == e.getX()
				&& selectedWordEvent.getY() == e.getY())
		{
			// we already done selection for this
			return;
		}
		_selwordaction.actionPerformed(new ActionEvent(_editor,
				ActionEvent.ACTION_PERFORMED, null, e.getWhen(), e
						.getModifiers()));
		selectedWordEvent = e;
	}

	/*
	 * 
	 * 更新焦点状态
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @exception
	 */
	private void adjustFocus(boolean inWindow)
	{
		if ((_editor != null) && _editor.isEnabled()
				&& _editor.isRequestFocusEnabled())
		{
			if (inWindow)
			{
				_editor.requestFocusInWindow();
			} else
			{
				_editor.requestFocus();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e)
	{
		if (shouldHandleRelease && SwingUtilities.isLeftMouseButton(e))
		{
			adjustposandfouse(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent
	 * )
	 */
	public void mouseDragged(MouseEvent e)
	{
		if ((!e.isConsumed()) && SwingUtilities.isLeftMouseButton(e))
		{
			moveCaret(e);
		}
	}

	private void moveCaret(MouseEvent e)
	{
		DocumentPosition pos = _editor.pointtodocpos(e.getPoint());
		if (pos != null && !pos.equals(_currentpos))
		{
			SelectionModel smodel = _editor.getSelectionModel();
			DocumentPositionRange scell = DocumentPositionRange
					.creatSelectCell(_currentpos, pos);
			if (isfirstdrag)
			{
				if (scell != null)
				{
					smodel.addSelectionCell(scell);
					isfirstdrag = false;
				}
			} else
			{
				DocumentPositionRange oldscell = smodel.getSelectionCell();
				smodel.replaceSelectionCell(oldscell, scell);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void paint(Graphics g)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * 设置段落选中处理类
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public void setSelectBlockAction(SelectBlockAction selblockaction)
	{
		_selblockaction = selblockaction;
	}

	/**
	 * 
	 * 设置单词选中处理类
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public void setSelectWordAction(SelectWordAction selwordaction)
	{
		_selwordaction = selwordaction;
	}

	public boolean isInControl(MouseEvent event)
	{
		return true;
	}
	public void init()
	{
		// _currentpos = null;
	}
	/* 【添加：START】 by 李晓光  2009-3-4  */
	public SelectionModel getSelectionModel(){
		if(_editor == null)
			return null;
		return _editor.getSelectionModel();
	}
	/* 【添加：END】 by 李晓光  2009-3-4  */
}
