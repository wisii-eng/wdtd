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

package com.wisii.wisedoc.view.ui.actions.group;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableBody;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.action.BaseAction;
import com.wisii.wisedoc.view.dialog.SetGroupDialog;

@SuppressWarnings("serial")
public class CanSetGroupObjectTypeAction extends BaseAction
{

	List<CurrentObjectSelectType> selectType = new ArrayList<CurrentObjectSelectType>();

	public enum CurrentObjectSelectType
	{
		/**
		 * 当前图文可设置组属性
		 */
		INLINE, /**
		 * 当前段落可设置组属性
		 */
		BLOCK, /**
		 * 当前高级块容器可设置组属性
		 */
		BLOCKCONTAINER, /**
		 * 当前单元格可设置组属性
		 */
		TABLECELL, /**
		 * 当前表行可设置组属性
		 */
		TABLEROW, /**
		 * 当前表体可设置组属性
		 */
		TABLEBODY, /**
		 * 当前表可设置组属性
		 */
		TABLE, /**
		 * 当前章节可设置组属性
		 */
		PAGESEQUENCE, /**
		 * 当前文档可设置组属性
		 */
		WISEDOCDOCEMENT, /**
		 * 当前组合可设置组属性
		 */
		GROUP;
	}

	@Override
	public void doAction(ActionEvent e)
	{
		Group oldlgroup = getGroup();
		SetGroupDialog groupdialog = new SetGroupDialog(oldlgroup);
		DialogResult result = groupdialog.showDialog();
		if (DialogResult.OK.equals(result))
		{
			Group newgroup = groupdialog.getGroup();
			List<CellElement> elements = getAllSelectObjects();
			Document doc = getCurrentDocument();
			Map<Integer, Object> map = new HashMap<Integer, Object>();
			map.put(Constants.PR_GROUP, newgroup);
			for (CellElement current : elements)
			{
				if (current instanceof Inline)
				{
					doc.setElementAttributes(current, map,
							false);
				}
			}
		}
	}

	@Override
	public boolean isAvailable()
	{
		if (!super.isAvailable())
		{
			return false;
		}
		Document doc = getCurrentDocument();
		if (doc != null && doc.getDataStructure() != null)
		{
			return true;
		}
		return false;
	}

	public void getCheckObjectType()
	{
		List<CellElement> elements = getAllSelectObjects();
		if (elements != null && elements.size() > 0)
		{
			for (int i = 0; i < elements.size(); i++)
			{
				CurrentObjectSelectType type = getElementType(elements.get(i));
				if (type != null)
				{
					addSelectTypeChild(type);
				} else
				{
					selectType.clear();
					return;
				}
			}
		}
	}

	public Element getPublicParent()
	{
		Element publicparent = null;
		List<CellElement> elements = getAllSelectObjects();
		for (CellElement current : elements)
		{
			if (current instanceof Inline)
			{
				addSelectTypeChild(CurrentObjectSelectType.INLINE);
				addSelectTypeChild(CurrentObjectSelectType.BLOCK);
			}
			Element currentp = getPublicParent(publicparent, current);
			publicparent = currentp;
		}
		return publicparent;
	}

	public void getAncestors(Element element)
	{
		Element currentelement = element;
		while (!(currentelement instanceof WiseDocDocument))
		{
			CurrentObjectSelectType type = getElementType(currentelement);
			addSelectTypeChild(type);
			if (currentelement instanceof PageSequence)
			{
				addSelectTypeChild(CurrentObjectSelectType.WISEDOCDOCEMENT);
				break;
			} else
			{
				currentelement = currentelement.getParent();
			}
		}
	}

	public Element getPublicParent(Element element1, Element element2)
	{
		Element parente = null;
		if (element1 == null)
		{
			return element2;
		} else if (element2 == null)
		{
			return element1;
		} else
		{
			Element parente1 = element1;
			Element parente2 = element2;
			while (!(parente1 instanceof WiseDocDocument))
			{
				if (parente1.equals(parente2))
				{
					return parente1;
				} else
				{
					while (!(parente2 instanceof WiseDocDocument))
					{
						if (parente1.equals(parente2))
						{
							return parente1;
						} else
						{
							parente2 = parente2.getParent();
						}
					}
					parente1 = parente1.getParent();
					parente2 = element2;
				}
			}
		}
		return parente;
	}

	public CurrentObjectSelectType getElementType(Element element)
	{
		CurrentObjectSelectType type = null;
		if (element == null)
		{
		} else if (element instanceof Inline)
		{
			type = CurrentObjectSelectType.INLINE;
		} else if (element instanceof Block)
		{
			type = CurrentObjectSelectType.BLOCK;
		} else if (element instanceof BlockContainer)
		{
			type = CurrentObjectSelectType.BLOCKCONTAINER;
		} else if (element instanceof TableCell)
		{
			type = CurrentObjectSelectType.TABLECELL;
		} else if (element instanceof TableRow)
		{
			type = CurrentObjectSelectType.TABLEROW;
		} else if (element instanceof TableBody)
		{
			type = CurrentObjectSelectType.TABLEBODY;
		} else if (element instanceof Table)
		{
			type = CurrentObjectSelectType.TABLE;
		} else if (element instanceof PageSequence)
		{
			type = CurrentObjectSelectType.PAGESEQUENCE;
		} else if (element instanceof WiseDocDocument)
		{
			type = CurrentObjectSelectType.WISEDOCDOCEMENT;
		} else if (element instanceof com.wisii.wisedoc.document.Group)
		{
			type = CurrentObjectSelectType.GROUP;
		} else
		{
			return null;
		}
		return type;
	}

	public boolean getCanSetRemove()
	{
		return true;
	}

	public Group getGroup()
	{
		return null;
	}

	public void addSelectTypeChild(CurrentObjectSelectType add)
	{
		if (add != null && !(selectType.contains(add)))
		{
			selectType.add(add);
		}
	}
}
