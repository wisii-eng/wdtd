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
 * @AbstractElementsHandler.java 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.ChartDataList;
import com.wisii.wisedoc.document.attribute.ConditionItemCollection;
import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.document.attribute.edit.ButtonGroup;
import com.wisii.wisedoc.document.attribute.edit.ConnWith;
import com.wisii.wisedoc.document.attribute.transform.DataSource;
import com.wisii.wisedoc.document.attribute.transform.PopupBrowserInfo;
import com.wisii.wisedoc.document.attribute.transform.SelectInfo;
import com.wisii.wisedoc.io.xsl.attribute.edit.GroupUI;
/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2010-12-27
 */
public abstract class AbstractElementsHandler extends DefaultHandler
{

	private final String WSDTAG = "wisedoc";
	private boolean isroot = false;
	private String ATTRIBUTESNAME = "attributes";
	private final String LOGICALS = "logicals";
	private final String GROUPS = "groups";
	private final String DYNAMICSTYLES = "dynamicstyles";
	private final String CHARTDATALISTS = "chartdatalists";
	private final String DATASOURCES = "datasources";
	private final String SELECTINFOS = "selectinfos";
	private final String POPUPBROWSERINFOS = "popupbrowserinfos";
	private final String CONNWITHS = "connwiths";
	private final String GROUPUIS = "groupuis";
	public static final String BUTTONGROUPLISTES = "bslistes";
	protected Map<String, AbstractHandler> _handlermap;
	protected AbstractHandler _currenthandler;
	protected Map<String, com.wisii.wisedoc.document.attribute.Attributes> _attributemap = new HashMap<String, com.wisii.wisedoc.document.attribute.Attributes>();
	private List<LogicalExpression> _logexplist = new ArrayList<LogicalExpression>();
	private List<Group> _grouplist = new ArrayList<Group>();
	private List<ConditionItemCollection> _dynamicstylelist = new ArrayList<ConditionItemCollection>();
	private List<ChartDataList> _chartdatalistlist = new ArrayList<ChartDataList>();
	private List<DataSource> _datasourcelist = new ArrayList<DataSource>();
	private List<SelectInfo> _selectinfolist = new ArrayList<SelectInfo>();
	private List<PopupBrowserInfo> _popupbrowserinfolist = new ArrayList<PopupBrowserInfo>();
	private List<ConnWith> _connwithlist = new ArrayList<ConnWith>();
	protected List<GroupUI> _groupuilist = new ArrayList<GroupUI>();
	protected List<List<ButtonGroup>> _buttongrouplistlist = new ArrayList<List<ButtonGroup>>();

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * 
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	public AbstractElementsHandler()
	{
		init();
		initHanlder();
	}

	protected void init()
	{
		// 处理Attributes节点下的内容的解析的handler
		AttributesReader attshandler = new AttributesReader();
		// 处理logicals下节点的handler
		LogicalExpressionsReader logicalexpshandler = new LogicalExpressionsReader();
		// 处理groups节点下的内容的解析的handler
		GroupsReader groupshandler = new GroupsReader();
		DynamicStylesReader dynamicstylesshandler = new DynamicStylesReader();
		ChartDataListsReader chartdatalistshandler = new ChartDataListsReader();
		DataSourceListReader datasourcelistshandler = new DataSourceListReader();
		SelectInfoListReader selectinfolistshandler = new SelectInfoListReader();
		PopupBrowserInfoListReader popupbrowserinfolistshandler = new PopupBrowserInfoListReader();
		ConnWithListReader connwithlistshandler = new ConnWithListReader();
		ButtonGroupListReader buttongrouplistshandler = new ButtonGroupListReader();
		GroupUIListReader groupuilistshandler = new GroupUIListReader();
		_handlermap = new HashMap<String, AbstractHandler>();
		_handlermap.put(ATTRIBUTESNAME, attshandler);
		_handlermap.put(LOGICALS, logicalexpshandler);
		_handlermap.put(GROUPS, groupshandler);
		_handlermap.put(DYNAMICSTYLES, dynamicstylesshandler);
		_handlermap.put(CHARTDATALISTS, chartdatalistshandler);
		_handlermap.put(DATASOURCES, datasourcelistshandler);
		_handlermap.put(SELECTINFOS, selectinfolistshandler);
		_handlermap.put(POPUPBROWSERINFOS, popupbrowserinfolistshandler);
		_handlermap.put(CONNWITHS, connwithlistshandler);
		_handlermap.put(BUTTONGROUPLISTES, buttongrouplistshandler);
		_handlermap.put(GROUPUIS, groupuilistshandler);

	}

	private void initHanlder()
	{
		for (AbstractHandler handler : _handlermap.values())
		{
			handler.ininHandler(this);
		}
	}

	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException
	{
		super.startElement(uri, localName, qName, atts);
		if (!isroot)
		{
			if (!WSDTAG.equals(qName))
			{
				throw new SAXException("根节点必须为wisedoc");
			}
			isroot = true;
			return;
		}
		AbstractHandler handler = _handlermap.get(qName);
		if (handler != null)
		{
			_currenthandler = handler;
			if (!dealStartElement(uri, localName, qName, atts))
			{
				return;
			}
		}
		if (_currenthandler != null)
		{
			_currenthandler.startElement(uri, localName, qName, atts);
		}
	}

	protected boolean dealStartElement(String uri, String localName,
			String qName, Attributes atts)
	{
		return true;
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		super.endElement(uri, localName, qName);
		if(WSDTAG.equals(qName))
		{
			return;
		}
		if (_currenthandler != null)
		{
			if (ATTRIBUTESNAME.equals(qName)
					&& _currenthandler instanceof AttributesReader)
			{
				_attributemap.putAll(((AttributesReader) _currenthandler)
						.getObject());
			} else if (LOGICALS.equals(qName)
					&& _currenthandler instanceof LogicalExpressionsReader)
			{
				_logexplist.addAll(((LogicalExpressionsReader) _currenthandler)
						.getObject());
			} else if (GROUPS.equals(qName)
					&& _currenthandler instanceof GroupsReader)
			{
				_grouplist.addAll(((GroupsReader) _currenthandler).getObject());
			} else if (CHARTDATALISTS.equals(qName)
					&& _currenthandler instanceof ChartDataListsReader)
			{
				_chartdatalistlist
						.addAll(((ChartDataListsReader) _currenthandler)
								.getObject());
			} else if (CHARTDATALISTS.equals(qName)
					&& _currenthandler instanceof ChartDataListsReader)
			{
				_chartdatalistlist
						.addAll(((ChartDataListsReader) _currenthandler)
								.getObject());
			}

			else if (DATASOURCES.equals(qName)
					&& _currenthandler instanceof DataSourceListReader)
			{
				_datasourcelist.addAll(((DataSourceListReader) _currenthandler)
						.getObject());
			} else if (SELECTINFOS.equals(qName)
					&& _currenthandler instanceof SelectInfoListReader)
			{
				_selectinfolist.addAll(((SelectInfoListReader) _currenthandler)
						.getObject());
			} 
			else if (POPUPBROWSERINFOS.equals(qName)
					&& _currenthandler instanceof PopupBrowserInfoListReader)
			{
				_popupbrowserinfolist.addAll(((PopupBrowserInfoListReader) _currenthandler)
						.getObject());
			}
			else if (CONNWITHS.equals(qName)
					&& _currenthandler instanceof ConnWithListReader)
			{
				_connwithlist.addAll(((ConnWithListReader) _currenthandler)
						.getObject());
			} else if (GROUPUIS.equals(qName)
					&& _currenthandler instanceof GroupUIListReader)
			{
				_groupuilist.addAll(((GroupUIListReader) _currenthandler)
						.getObject());
			} else if (BUTTONGROUPLISTES.equals(qName)
					&& _currenthandler instanceof ButtonGroupListReader)
			{
				_buttongrouplistlist
						.addAll(((ButtonGroupListReader) _currenthandler)
								.getObject());
			} else if (DYNAMICSTYLES.equals(qName)
					&& _currenthandler instanceof DynamicStylesReader)
			{
				_dynamicstylelist
						.addAll(((DynamicStylesReader) _currenthandler)
								.getObject());
				// 处理属性有引用的动态样式的情况
				if (!_dynamicstylelist.isEmpty())
				{
					Set<String> attkeys = _attributemap.keySet();
					for (String attkey : attkeys)
					{
						com.wisii.wisedoc.document.attribute.Attributes attributes = _attributemap
								.get(attkey);
						String dynamicstring = (String) attributes
								.getAttribute(Constants.PR_DYNAMIC_STYLE);
						if (dynamicstring != null)
						{

							Map<Integer, Object> newattmap = attributes
									.getAttributes();
							ConditionItemCollection dynamicstyle = getDynamicStyle(dynamicstring);
							newattmap.put(Constants.PR_DYNAMIC_STYLE,
									dynamicstyle);
							com.wisii.wisedoc.document.attribute.Attributes nattrobutes = new com.wisii.wisedoc.document.attribute.Attributes(
									newattmap);
							_attributemap.put(attkey, nattrobutes);
						}
					}
				}
			} else
			{
				if (!dealEndElement(uri, localName, qName))
				{
					return;
				}
			}
			_currenthandler.endElement(uri, localName, qName);
		}
	}

	protected boolean dealEndElement(String uri, String localName, String qName)
	{
		return true;
	}

	public void characters(char[] arg0, int arg1, int arg2) throws SAXException
	{
		// TODO Auto-generated method stub
		super.characters(arg0, arg1, arg2);
		if (_currenthandler != null)
		{
			_currenthandler.characters(arg0, arg1, arg2);
		}
	}

	public com.wisii.wisedoc.document.attribute.Attributes getAttributes(
			String id)
	{
		if (id != null)
		{
			return _attributemap.get(id);
		} else
		{
			return null;
		}
	}

	public abstract BindNode getNode(String id);

	public LogicalExpression getLogicalExp(String id)
	{
		if (id != null)
		{
			try
			{
				int index = Integer.parseInt(id);
				if (index > -1 && index < _logexplist.size())
				{
					return _logexplist.get(index);
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

	public Group getGroup(String id)
	{
		if (id != null)
		{
			try
			{
				int index = Integer.parseInt(id);
				if (index > -1 && index < _grouplist.size())
				{
					return _grouplist.get(index);
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

	public ChartDataList getChartDataList(String id)
	{
		if (id != null)
		{
			try
			{
				int index = Integer.parseInt(id);
				if (index > -1 && index < _chartdatalistlist.size())
				{
					return _chartdatalistlist.get(index);
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

	public ConditionItemCollection getDynamicStyle(String id)
	{
		if (id != null)
		{
			try
			{
				int index = Integer.parseInt(id);
				if (index > -1 && index < _dynamicstylelist.size())
				{
					return _dynamicstylelist.get(index);
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

	public DataSource getDataSource(String id)
	{
		if (id != null)
		{
			try
			{
				int index = Integer.parseInt(id);
				if (index > -1 && index < _datasourcelist.size())
				{
					return _datasourcelist.get(index);
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

	public SelectInfo getSelectInFo(String id)
	{
		if (id != null)
		{
			try
			{
				int index = Integer.parseInt(id);
				if (index > -1 && index < _selectinfolist.size())
				{
					return _selectinfolist.get(index);
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
	public PopupBrowserInfo getPopupBrowserInFo(String id)
	{
		if (id != null)
		{
			try
			{
				int index = Integer.parseInt(id);
				if (index > -1 && index < _popupbrowserinfolist.size())
				{
					return _popupbrowserinfolist.get(index);
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

	public ConnWith getConnWith(String id)
	{
		if (id != null)
		{
			try
			{
				int index = Integer.parseInt(id);
				if (index > -1 && index < _connwithlist.size())
				{
					return _connwithlist.get(index);
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

	public GroupUI getGroupUI(String id)
	{
		if (id != null)
		{
			try
			{
				int index = Integer.parseInt(id);
				if (index > -1 && index < _groupuilist.size())
				{
					return _groupuilist.get(index);
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

	public List<ButtonGroup> getButtonGroupList(String id)
	{
		if (id != null)
		{
			try
			{
				int index = Integer.parseInt(id);
				if (index > -1 && index < _buttongrouplistlist.size())
				{
					return _buttongrouplistlist.get(index);
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