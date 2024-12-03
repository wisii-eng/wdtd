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
 * @WsdReaderHandler.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.BindNodeUtil;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.TableContents;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.document.attribute.ParagraphStyles;
import com.wisii.wisedoc.document.attribute.edit.ButtonGroup;
import com.wisii.wisedoc.view.ui.model.paragraphstyles.ParagraphStylesModel;
/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-10-9
 */
public class WsdReaderHandler extends AbstractElementsHandler
{
	private static String DATASNAME = "datas";
	private static String PAGESEQUENCESNAME = "pagesequences";
	private static final String PARAGRAPHSTYLES = "paragraphstyles";
	private static final String TABLECONTENTS = "tablecontents";
	private TableContents tablecontents = null;
	private List<ParagraphStyles> _paragraphstylelist = new ArrayList<ParagraphStyles>();
	private Map<String, BindNode> _nodemap = new HashMap<String, BindNode>();
	private static Map<String, BindNode> nodemap = new HashMap<String, BindNode>();
	private DataStructureTreeModel datamodel;
	private com.wisii.wisedoc.document.attribute.Attributes documentatt;
	private String datapath;

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.io.wsd.reader.AbstractElementsHandler#init()
	 */
	@Override
	protected void init()
	{
		super.init();
		// 处理pagesequences节点下内容的handler
		PageSequencesHandler _pseshandler = new PageSequencesHandler();
		_handlermap.put(PAGESEQUENCESNAME, _pseshandler);
		// 处理datas节点下的内容的解析的handler
		DatasReader datashandler = new DatasReader();
		_handlermap.put(DATASNAME, datashandler);
		ParagraphStylesReader paragraphstylesshandler = new ParagraphStylesReader();
		_handlermap.put(PARAGRAPHSTYLES, paragraphstylesshandler);
		TableContentsReader tablecontentshandler = new TableContentsReader();
		_handlermap.put(TABLECONTENTS, tablecontentshandler);
	}

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.io.wsd.reader.AbstractElementsHandler#dealStartElement()
	 */
	@Override
	protected boolean dealStartElement(String uri, String localName,
			String qName, Attributes atts)
	{
		if (_currenthandler instanceof DatasReader){
			datapath = atts.getValue("datapath");
		}
		if (_currenthandler instanceof PageSequencesHandler)
		{
			documentatt = getAttributes(atts.getValue("attrefid"));
		}
		if (!(_currenthandler instanceof TableContentsReader))
		{
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.wisii.wisedoc.io.wsd.reader.AbstractElementsHandler#dealEndElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	protected boolean dealEndElement(String uri, String localName, String qName)
	{
		if (DATASNAME.equals(qName) && _currenthandler instanceof DatasReader)
		{
			_nodemap.putAll(((DatasReader) _currenthandler).getObject());
			nodemap.putAll(((DatasReader) _currenthandler).getObject());
			BindNode root = ((DatasReader) _currenthandler).getRootNode();
			if (root != null)
			{
				datamodel = new DataStructureTreeModel(
						((DatasReader) _currenthandler).getDbsetting(), root,
						false,((DatasReader) _currenthandler).getNamespaces());
				datamodel.setDatapath(datapath);
			}
		}
		else if (PARAGRAPHSTYLES.equals(qName)
				&& _currenthandler instanceof ParagraphStylesReader)
		{
			_paragraphstylelist
					.addAll(((ParagraphStylesReader) _currenthandler)
							.getObject());
			ParagraphStylesModel.Instance
					.setParagraphStylesList(new ArrayList<ParagraphStyles>(
							_paragraphstylelist));
			// 处理属性有引用的动态样式的情况
			if (!_paragraphstylelist.isEmpty())
			{
				Set<String> attkeys = _attributemap.keySet();
				for (String attkey : attkeys)
				{
					com.wisii.wisedoc.document.attribute.Attributes attributes = _attributemap
							.get(attkey);
					String parastylestring = (String) attributes
							.getAttribute(Constants.PR_BLOCK_STYLE);
					if (parastylestring != null)
					{

						Map<Integer, Object> newattmap = attributes
								.getAttributes();
						ParagraphStyles dynamicstyle = getParaStyleStyle(parastylestring);
						newattmap.put(Constants.PR_BLOCK_STYLE, dynamicstyle);
						com.wisii.wisedoc.document.attribute.Attributes nattrobutes = new com.wisii.wisedoc.document.attribute.Attributes(
								newattmap);
						_attributemap.put(attkey, nattrobutes);
					}
				}
			}
		} else if (TABLECONTENTS.equals(qName)
				&& _currenthandler instanceof TableContentsReader)
		{
			try
			{
				_currenthandler.endElement(uri, localName, qName);
				tablecontents = ((TableContentsReader) _currenthandler)
						.getObject();
			} catch (SAXException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		return true;
	}

	public Document getDocument()
	{
		Document doc = new WiseDocDocument(((PageSequencesHandler) _handlermap
				.get(PAGESEQUENCESNAME)).getObject(), tablecontents);
		doc.setDataStructureWithoutEdit(datamodel);
		if (documentatt != null)
		{
			doc.setAttributes(documentatt.getAttributes(), true);
		}
		if (!_groupuilist.isEmpty())
		{
			((WiseDocDocument) doc).getListEditUI().addAll(_groupuilist);
		}
		for (List<ButtonGroup> buttongropulist : _buttongrouplistlist)
		{
			if (buttongropulist != null)
			{
				for (ButtonGroup buttongroup : buttongropulist)
				{
					List<Integer> indexs = buttongroup.getCellindexs();
					Element cell = null;
					if (indexs != null)
					{
						int size = indexs.size();
						cell = doc;
						for (Integer index : indexs)
						{
							cell = (CellElement) cell.getChildAt(index);
							if (cell == null)
							{
								break;
							}
						}
						buttongroup.setCellment((CellElement) cell);
					}
				}
			}
		}
		return doc;
	}

	public BindNode getNode(String id)
	{
		if (id != null)
		{
			boolean isdigit = true;
			for (char c : id.toCharArray())
			{
				if (!Character.isDigit(c))
				{
					isdigit = false;
					break;
				}
			}
			//如果全部是数字，则表示时老版本的顺序号方式标识的ｉｄ
			if (isdigit)
			{
				return _nodemap.get(id);
			}
			//否则，根据xpath找到对应的节点 
			else
			{
				BindNode nodeOfPath = BindNodeUtil.getNodeOfPath(id, datamodel);
//				Map<String,BindNode> map = new HashMap<String, BindNode>();
//				
//				map.put(id, nodeOfPath);

				return nodeOfPath;
			}
		} else
		{
			return null;
		}
	}

	public static Map<String, BindNode> getNodemap() {
		return nodemap;
	}

	private ParagraphStyles getParaStyleStyle(String id)
	{
		if (id != null)
		{
			try
			{
				int index = Integer.parseInt(id);
				if (index > -1 && index < _paragraphstylelist.size())
				{
					return _paragraphstylelist.get(index);
				} else
				{
					return null;
				}
			} catch (NumberFormatException e)
			{
				return null;
			}

		} else
		{
			return null;
		}
	}

	
}
