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

package com.wisii.wisedoc.io.xsl.attribute.edit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.edit.ConnWith;
import com.wisii.wisedoc.document.attribute.edit.Formula;
import com.wisii.wisedoc.document.attribute.edit.Next;
import com.wisii.wisedoc.document.attribute.edit.Parameter;
import com.wisii.wisedoc.document.attribute.edit.Parameter.DataTyle;
import com.wisii.wisedoc.document.attribute.edit.Parameter.ParamTyle;
import com.wisii.wisedoc.document.attribute.transform.DataSource;
import com.wisii.wisedoc.document.attribute.transform.PopupBrowserColumnInfo;
import com.wisii.wisedoc.document.attribute.transform.PopupBrowserInfo;
import com.wisii.wisedoc.document.attribute.transform.PopupBrowserSource;
import com.wisii.wisedoc.document.attribute.transform.SelectColumnInFO;
import com.wisii.wisedoc.document.attribute.transform.SelectInfo;
import com.wisii.wisedoc.io.xsl.attribute.AttributeValueWriter;
import com.wisii.wisedoc.io.xsl.util.EditKeyAndValueUtil;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

public class EditUIWriter
{

	EditUI currentui;

	EditAttributeWriter writer = new EditAttributeWriter();

	public EditUIWriter(EditUI ui)
	{
		currentui = ui;
	}

	public String getAttribute(int key, Object value)
	{
		String keyStr = EditKeyAndValueUtil.getKeyName(key);
		AttributeValueWriter valuewriter = writer.getAttributeWriter(value);
		String valueStr = valuewriter != null ? valuewriter.write(value) : "";
		return ElementUtil.outputAttributes(keyStr, valueStr);
	}

	public String getCode()
	{
		StringBuffer code = new StringBuffer();
		EnumProperty type = currentui.getType();
		int inttype = type.getEnum();
		if (inttype == Constants.EN_GROUP)
		{
			code.append(getGroupCode());
		} else if (inttype == Constants.EN_SELECT)
		{
			code.append(getSelectCode());
		} 
		else if (inttype == Constants.EN_POPUPBROWSER)
		{
			code.append(getPopupBrowserCode());
		}
		else if (inttype == Constants.EN_GRAPHIC)
		{
			code.append(getGraphicCode());
		}
		else if (inttype == Constants.PR_CONN_WITH)
		{
			code.append(getConnWithCode());
		} else if (inttype == EditUI.CONN_WITH)
		{
			code.append(getConnWithOptionCode());
		} else
		{
			code.append(getEditUICode());
		}
		return code.toString();
	}

	public String getConnOptionCode(ConnOption connoption)
	{
		StringBuffer code = new StringBuffer();
		code.append("<");
		code.append(EnumPropertyWriter.WDWEMSNS + "option");
		code.append(ElementUtil.outputAttributes("xpath", "/"
				+ connoption.getXpath()));
		code.append(ElementUtil.outputAttributes("value", connoption.getIndex()
				+ ""));
		code.append("/>");
		return code.toString();
	}

	public String getGroupCode()
	{
		StringBuffer code = new StringBuffer();
		code.append("<");
		String name = currentui.getName();
		code.append(new EnumPropertyWriter().write(currentui.getType()));
		code.append(ElementUtil.outputAttributes("name", name));
		Map<Integer, Object> attr = currentui.getAttr();
		Object[] keys = attr.keySet().toArray();
		String connwith = "";
		for (int i = 0; i < keys.length; i++)
		{
			Integer key = (Integer) keys[i];
			Object value = attr.get(key);
			if (value != null && value instanceof String)
			{
				value = IoXslUtil.getXmlText((String) value);
			}
			code.append(getAttribute(key, value));
		}
		GroupUI currentgroup = (GroupUI) currentui;
		if (currentgroup.getConnwith() != null)
		{

			EnumProperty type = new EnumProperty(Constants.PR_CONN_WITH, "");
			Map<Integer, Object> editmap = new HashMap<Integer, Object>();
			editmap.put(Constants.PR_CONN_WITH, currentgroup.getConnwith());
			EditUI current = new ConnWithUI(type, editmap);
			String edituiname = name + "conn";
			current.setName(edituiname);
			connwith = new EditUIWriter(current).getCode();
			code.append(ElementUtil.outputAttributes("conn", edituiname));

		}
		code.append("/>");
		code.append(connwith);
		return code.toString();
	}

	public String getSelectCode()
	{
		EnumProperty typeui = currentui.getType();
		String name = currentui.getName();
		StringBuffer code = new StringBuffer();
		code.append("<");
		code.append(new EnumPropertyWriter().write(typeui));
		code.append(ElementUtil.outputAttributes("name", name));
		Map<Integer, Object> attr = currentui.getAttr();
		Object[] keys = attr.keySet().toArray();
		String connwith = "";
		for (int i = 0; i < keys.length; i++)
		{
			Integer key = (Integer) keys[i];
			if (key == Constants.PR_SELECT_INFO)
			{
				SelectInfo select = (SelectInfo) attr.get(key);
				int type = select.getDatasourcetype();
				if (type == SelectInfo.SCHEMA)
				{
					code.append(ElementUtil.outputAttributes("src", "schema"));
				} else
				{
					String dataname = IoXslUtil.addDatasourceInfo(select);
					code.append(ElementUtil.outputAttributes("src", dataname));
					String issearchable = select.isSearchable() ? "true" : "";
					code.append(ElementUtil.outputAttributes("isSearchable",
							issearchable));
					int sorttype = select.getSorttype();
					String stype = "";
					if (sorttype == Constants.EN_SORT_P)
					{
						stype = "p";
					} else if (sorttype == Constants.EN_SORT_N)
					{
						stype = "n";
					}
					code.append(ElementUtil.outputAttributes("sort", stype));
					List<SelectColumnInFO> columninfos = select
							.getColumninfos();
					code.append(ElementUtil.outputAttributes("sortColumn",
							getSortColumn(columninfos)));
					code.append(ElementUtil.outputAttributes("view",
							getViewColumn(columninfos)));
					code.append(ElementUtil.outputAttributes("searchQueue",
							getSearchColumn(columninfos)));
					EnumProperty typeennum = new EnumProperty(EditUI.CONN_WITH,
							"");
					Map<Integer, Object> editmap = new HashMap<Integer, Object>();
					editmap.put(ConnWithUI.CONN_WITH,
							getConnOption(columninfos));
					ConnOptionUI connwithui = new ConnOptionUI(typeennum,
							editmap);
					String edituiname = "conn" + name;
					connwithui.setName(edituiname);
					connwith = new EditUIWriter(connwithui).getCode();
					code.append(ElementUtil
							.outputAttributes("conn", edituiname));
				}
			}
			else if (key == Constants.PR_CONN_WITH)
			{
				EnumProperty type = new EnumProperty(0, "");
				Map<Integer, Object> editmap = new HashMap<Integer, Object>();
				editmap.put(key, attr.get(key));
				EditUI current = new ConnWithUI(type, editmap);
				String edituiname = name + "conn";
				current.setName(edituiname);
				connwith = new EditUIWriter(current).getCode();
				code.append(ElementUtil.outputAttributes("conn", edituiname));
			} else if (key == Constants.PR_SELECT_NEXT)
			{
				Object nextobj = attr.get(key);
				if (nextobj != null && nextobj instanceof Next)
				{
					Next next = (Next) nextobj;
					code.append(ElementUtil.outputAttributes("next", next
							.getName()
							+ "("
							+ next.getColumn()
							+ ","
							+ next.getNextcolumn() + ")"));
				}
			} else
			{
				Object value = attr.get(key);
				if (value != null && value instanceof String
						&& key != Constants.PR_DATE_FORMAT)
				{
					value = IoXslUtil.getXmlText((String) value);
				}
				code.append(getAttribute(key, value));
			}
		}
		code.append("/>");
		code.append(connwith);
		return code.toString();

	}
	public String getPopupBrowserCode()
	{
		EnumProperty typeui = currentui.getType();
		String name = currentui.getName();
		StringBuffer code = new StringBuffer();
		code.append("<");
		code.append(new EnumPropertyWriter().write(typeui));
		code.append(ElementUtil.outputAttributes("name", name));
		Map<Integer, Object> attr = currentui.getAttr();
		Object[] keys = attr.keySet().toArray();
		String connwith = "";
		for (int i = 0; i < keys.length; i++)
		{
			Integer key = (Integer) keys[i];
			if (key == Constants.PR_POPUPBROWSER_INFO)
			{
				PopupBrowserInfo select = (PopupBrowserInfo) attr.get(key);
				int type = 2;
				if (type == SelectInfo.SCHEMA)
				{
					code.append(ElementUtil.outputAttributes("src", "schema"));
				} else
				{
//					String dataname = IoXslUtil.addDatasourceInfo(select);
					DataSource datasource = select.getDatasource();
					PopupBrowserSource source = (PopupBrowserSource) datasource;
					code.append(ElementUtil.outputAttributes("src", source.getUrl()+"?"+source.getPara()));
//					String issearchable = select.isSearchable() ? "true" : "";
//					code.append(ElementUtil.outputAttributes("isSearchable",
//							issearchable));
//					int sorttype = select.getSorttype();
//					String stype = "";
//					if (sorttype == Constants.EN_SORT_P)
//					{
//						stype = "p";
//					} else if (sorttype == Constants.EN_SORT_N)
//					{
//						stype = "n";
//					}
//					code.append(ElementUtil.outputAttributes("sort", stype));
					List<PopupBrowserColumnInfo> columninfos = select
							.getColumninfos();
//					code.append(ElementUtil.outputAttributes("sortColumn",
//							getSortColumn(columninfos)));
//					code.append(ElementUtil.outputAttributes("view",
//							getViewColumn(columninfos)));
//					code.append(ElementUtil.outputAttributes("searchQueue",
//							getSearchColumn(columninfos)));
					EnumProperty typeennum = new EnumProperty(EditUI.CONN_WITH,
							"");
					Map<Integer, Object> editmap = new HashMap<Integer, Object>();
					editmap.put(ConnWithUI.CONN_WITH,
							getConnOption2(columninfos));
					ConnOptionUI connwithui = new ConnOptionUI(typeennum,
							editmap);
					String edituiname = "conn" + name;
					connwithui.setName(edituiname);
					connwith = new EditUIWriter(connwithui).getCode();
					code.append(ElementUtil
							.outputAttributes("conn", edituiname));
				}
			}
			else if (key == Constants.PR_CONN_WITH)
			{
				EnumProperty type = new EnumProperty(0, "");
				Map<Integer, Object> editmap = new HashMap<Integer, Object>();
				editmap.put(key, attr.get(key));
				EditUI current = new ConnWithUI(type, editmap);
				String edituiname = name + "conn";
				current.setName(edituiname);
				connwith = new EditUIWriter(current).getCode();
				code.append(ElementUtil.outputAttributes("conn", edituiname));
			} else if (key == Constants.PR_SELECT_NEXT)
			{
				Object nextobj = attr.get(key);
				if (nextobj != null && nextobj instanceof Next)
				{
					Next next = (Next) nextobj;
					code.append(ElementUtil.outputAttributes("next", next
							.getName()
							+ "("
							+ next.getColumn()
							+ ","
							+ next.getNextcolumn() + ")"));
				}
			} else
			{
				Object value = attr.get(key);
				if (value != null && value instanceof String
						&& key != Constants.PR_DATE_FORMAT)
				{
					value = IoXslUtil.getXmlText((String) value);
				}
				code.append(getAttribute(key, value));
			}
		}
		code.append("/>");
		code.append(connwith);
		return code.toString();
		
	}

	public String getConnWithCode()
	{
		ConnWithUI connwithui = (ConnWithUI) currentui;
		String name = connwithui.getName();
		ConnWith connwith = connwithui.getConnwith();
		StringBuffer code = new StringBuffer();
		code.append("<");
		code.append(EnumPropertyWriter.WDWEMSNS + "connwith");
		code.append(ElementUtil.outputAttributes("name", name));
		Map<Integer, Object> attr = currentui.getAttr();
		Object[] keys = attr.keySet().toArray();
		for (int i = 0; i < keys.length; i++)
		{
			Integer key = (Integer) keys[i];
			Object value = attr.get(key);
			if (value != null && value instanceof String
					&& key != Constants.PR_DATE_FORMAT)
			{
				value = IoXslUtil.getXmlText((String) value);
			}
			code.append(getAttribute(key, value));
		}
		code.append(">");

		StringBuffer parmscode = new StringBuffer();
		List<Parameter> parms = connwith.getParms();
		if (parms != null && !parms.isEmpty())
		{
			for (Parameter current : parms)
			{
				parmscode.append(getParameterCode(current, name));
			}
		}
		List<Formula> formulas = connwith.getFormulas();
		if (formulas != null && !formulas.isEmpty())
		{
			for (Formula current : formulas)
			{
				code.append(getFormulasCode(current, parmscode.toString()));
			}
		}
		code.append("</");
		code.append(EnumPropertyWriter.WDWEMSNS + "connwith");
		code.append(">");
		return code.toString();

	}

	public String getConnWithOptionCode()
	{
		ConnOptionUI connoptionui = (ConnOptionUI) currentui;
		String name = connoptionui.getName();
		StringBuffer code = new StringBuffer();
		code.append("<");
		code.append(EnumPropertyWriter.WDWEMSNS + "connwith");
		code.append(ElementUtil.outputAttributes("name", name));
		code.append(">");

		List<ConnOption> connoption = connoptionui.getConnoption();
		if (connoption != null && !connoption.isEmpty())
		{
			for (ConnOption current : connoption)
			{
				code.append(getConnOptionCode(current));
			}
		}
		code.append("</");
		code.append(EnumPropertyWriter.WDWEMSNS + "connwith");
		code.append(">");
		return code.toString();

	}

	public String getEditUICode()
	{
		String name = currentui.getName();
		StringBuffer code = new StringBuffer();
		code.append("<");
		code.append(new EnumPropertyWriter().write(currentui.getType()));
		code.append(ElementUtil.outputAttributes("name", name));
		String connwith = "";

		Map<Integer, Object> attr = currentui.getAttr();
		Object[] keys = attr.keySet().toArray();
		for (int i = 0; i < keys.length; i++)
		{
			Integer key = (Integer) keys[i];
			if (key == Constants.PR_CONN_WITH)
			{
				if (attr.get(key) != null)
				{
					EnumProperty type = new EnumProperty(
							Constants.PR_CONN_WITH, "");
					Map<Integer, Object> editmap = new HashMap<Integer, Object>();
					editmap.put(key, attr.get(key));
					EditUI current = new ConnWithUI(type, editmap);
					String edituiname = name + "conn";
					current.setName(edituiname);
					connwith = new EditUIWriter(current).getCode();
					code.append(ElementUtil
							.outputAttributes("conn", edituiname));
				}
			}
			else if(key == Constants.PR_DATA_SOURCE)
			{
				DataSource ds = (DataSource) attr.get(key);
				IoXslUtil.addDataTransformTable(ds);
				String dictionary = "sw" + ds.hashCode()
						+ IoXslUtil.getFilename();
				code.append(ElementUtil.outputAttributes("dictionary",
						dictionary));
			}
			else if (key == Constants.PR_GROUP_REFERANCE)
			{
				GroupUI getgroup = (GroupUI) attr.get(key);
				code.append(ElementUtil.outputAttributes("groupReference",
						IoXslUtil.getXmlText(getgroup.getName())));
			} else
			{
				Object value = attr.get(key);
				if (value != null && value instanceof String
						&& key != Constants.PR_DATE_FORMAT)
				{
					value = IoXslUtil.getXmlText((String) value);
				}
				code.append(getAttribute(key, value));
			}
		}
		code.append("/>");
		code.append(connwith);
		return code.toString();
	}
	
	public String getGraphicCode()
	{
		String name = currentui.getName();
		StringBuffer code = new StringBuffer();
		code.append("<");
		code.append(new EnumPropertyWriter().write(currentui.getType()));
		code.append(ElementUtil.outputAttributes("name", name));
		String connwith = "";

		Map<Integer, Object> attr = currentui.getAttr();
		Object[] keys = attr.keySet().toArray();
		for (int i = 0; i < keys.length; i++)
		{
			Integer key = (Integer) keys[i];
			if (key == Constants.PR_CONN_WITH)
			{
				if (attr.get(key) != null)
				{
					EnumProperty type = new EnumProperty(
							Constants.PR_CONN_WITH, "");
					Map<Integer, Object> editmap = new HashMap<Integer, Object>();
					editmap.put(key, attr.get(key));
					EditUI current = new ConnWithUI(type, editmap);
					String edituiname = name + "conn";
					current.setName(edituiname);
					connwith = new EditUIWriter(current).getCode();
					code.append(ElementUtil
							.outputAttributes("conn", edituiname));
				}
			}
			else if(key == Constants.PR_DATA_SOURCE)
			{
				DataSource ds = (DataSource) attr.get(key);
				IoXslUtil.addDataTransformTable(ds);
				String dictionary = "sw" + ds.hashCode()
						+ IoXslUtil.getFilename();
				code.append(ElementUtil.outputAttributes("dictionary",
						dictionary));
			}
			else if (key == Constants.PR_GROUP_REFERANCE)
			{
				GroupUI getgroup = (GroupUI) attr.get(key);
				code.append(ElementUtil.outputAttributes("groupReference",
						IoXslUtil.getXmlText(getgroup.getName())));
			} else
			{
				Object value = attr.get(key);
				if (value != null && value instanceof String
						&& key != Constants.PR_DATE_FORMAT)
				{
					value = IoXslUtil.getXmlText((String) value);
				}
				code.append(getAttribute(key, value));
			}
		}
		code.append("/>");
		code.append(connwith);
		return code.toString();
	}

	public List<ConnOption> getConnOption(List<SelectColumnInFO> columninfos)
	{
		List<ConnOption> connoption = new ArrayList<ConnOption>();
		if (columninfos != null && !columninfos.isEmpty())
		{
			for (int i = 0; i < columninfos.size(); i++)
			{
				SelectColumnInFO current = columninfos.get(i);
				BindNode node = current.getOptionpath();
				if (node != null)
				{
					ConnOption opt = new ConnOption(
							IoXslUtil.getXSLXpath(node), i + 1);
					connoption.add(opt);
				}
			}
		}
		return connoption;
	}

	public List<ConnOption> getConnOption2(List<PopupBrowserColumnInfo> columninfos)
	{
		List<ConnOption> connoption = new ArrayList<ConnOption>();
		if (columninfos != null && !columninfos.isEmpty())
		{
			for (int i = 0; i < columninfos.size(); i++)
			{
				PopupBrowserColumnInfo current = columninfos.get(i);
				BindNode node = current.getOptionpath();
				if (node != null)
				{
					ConnOption opt = new ConnOption(
							IoXslUtil.getXSLXpath(node), i + 1);
					connoption.add(opt);
				}
			}
		}
		return connoption;
	}
	
	public String getSortColumn(List<SelectColumnInFO> columninfos)
	{
		StringBuffer result = new StringBuffer();
		List<Integer> list = new ArrayList<Integer>();
		if (columninfos != null && !columninfos.isEmpty())
		{
			for (int i = 0; i < columninfos.size(); i++)
			{
				SelectColumnInFO current = columninfos.get(i);
				int index = current.getSortorder();
				if (index > -1)
				{
					if (list.isEmpty())
					{
						list.add(i);
					} else
					{
						boolean flg = true;
						for (int j = 0; j < list.size(); j++)
						{
							SelectColumnInFO currentcolumn = columninfos
									.get(list.get(j));
							int columnindex = currentcolumn.getSortorder();
							if (index < columnindex)
							{
								list.add(j, i);
								flg = false;
								break;
							}
						}
						if (flg)
						{
							list.add(i);
						}
					}
				}
			}
			int size = list.size();
			if (size > 0)
			{
				for (int index = 0; index < size; index++)
				{
					result.append((list.get(index) + 1));
					if (index < size - 1)
					{
						result.append(",");
					}
				}
			}
		}
		return result.toString();
	}

	public String getViewColumn(List<SelectColumnInFO> columninfos)
	{
		StringBuffer result = new StringBuffer();
		List<Integer> list = new ArrayList<Integer>();
		if (columninfos != null && !columninfos.isEmpty())
		{
			for (int i = 0; i < columninfos.size(); i++)
			{
				SelectColumnInFO current = columninfos.get(i);

				if (current.isIsviewable())
				{
					list.add(i + 1);
				}
			}
		}
		int size = list.size();
		for (int index = 0; index < size; index++)
		{
			result.append(list.get(index));
			if (index < size - 1)
			{
				result.append(",");
			}
		}
		return result.toString();
	}

	public String getSearchColumn(List<SelectColumnInFO> columninfos)
	{
		StringBuffer result = new StringBuffer();
		List<Integer> list = new ArrayList<Integer>();
		if (columninfos != null && !columninfos.isEmpty())
		{
			for (int i = 0; i < columninfos.size(); i++)
			{
				SelectColumnInFO current = columninfos.get(i);
				int index = current.getSearchorder();
				if (index > -1)
				{
					if (list.isEmpty())
					{
						list.add(i+1);
					} else
					{
						boolean flg = true;
						for (int j = 0; j < list.size(); j++)
						{
							SelectColumnInFO currentcolumn = columninfos
									.get(list.get(j-1));
							int columnindex = currentcolumn.getSearchorder();
							if (index < columnindex)
							{
								list.add(j, i+1);
								flg = false;
								break;
							}
						}
						if (flg)
						{
							list.add(i+1);
						}
					}
				}
			}
			int size = list.size();
			if (size > 0)
			{
				for (int index = 0; index < size; index++)
				{
					result.append((list.get(index) + 1));
					if (index < size - 1)
					{
						result.append(",");
					}
				}
			}
		}
		return result.toString();
	}

	public String getParameterCode(Parameter pra, String name)
	{
		StringBuffer code = new StringBuffer();
		code.append("<");
		code.append(EnumPropertyWriter.WDWEMSNS + "para");
		code.append(ElementUtil.outputAttributes("name", pra.getName()));
		ParamTyle type = pra.getType();
		if (type == ParamTyle.UI)
		{
			DataTyle datatype = pra.getDatatype();
			if (datatype == DataTyle.NUMBER)
			{
				code.append(ElementUtil.outputAttributes("type", "number"));
			} else if (datatype == DataTyle.STRING)
			{
				code.append(ElementUtil.outputAttributes("type", "string"));
			}
			code.append(ElementUtil.outputAttributes("match", name.substring(0, name.length()-4)));
		} else if (type == ParamTyle.CONSTANCE)
		{
			DataTyle datatype = pra.getDatatype();
			if (datatype == DataTyle.NUMBER)
			{
				code.append(ElementUtil.outputAttributes("type", "number"));
			} else if (datatype == DataTyle.STRING)
			{
				code.append(ElementUtil.outputAttributes("type", "string"));
			}
			
			Object pravalue = pra.getValue();
			if (pravalue != null && pravalue instanceof String)
			{
				code.append(ElementUtil.outputAttributes("constance", IoXslUtil
						.getXmlText((String) pravalue)
						+ ""));
			} else
			{
				code.append(ElementUtil.outputAttributes("constance", pravalue
						+ ""));
			}

		} else if (type == ParamTyle.XPATH)
		{
			DataTyle datatype = pra.getDatatype();
			if (datatype == DataTyle.NUMBER)
			{
				code.append(ElementUtil.outputAttributes("type", "number"));
			} else if (datatype == DataTyle.STRING)
			{
				code.append(ElementUtil.outputAttributes("type", "string"));
			}
			BindNode xpath = (BindNode) pra.getValue();
			String path = xpath != null ? "/" + IoXslUtil.getXSLXpath(xpath)
					: "";
			code.append(ElementUtil.outputAttributes("xpath", path));
		}
		code.append("/>");
		return code.toString();
	}

	public String getFormulasCode(Formula formula, String param)
	{
		StringBuffer code = new StringBuffer();
		code.append("<");
		code.append(EnumPropertyWriter.WDWEMSNS + "formula");
		BindNode xpath = formula.getXpath();
		if (xpath != null)
		{
			code.append(ElementUtil.outputAttributes("xpath", "/"
					+ IoXslUtil.getXSLXpath(xpath)));
		}
		code.append(">");

		code.append("<");
		code.append(EnumPropertyWriter.WDWEMSNS + "function");

		String expression = formula.getExpression();
		code.append(ElementUtil.outputAttributes("expression", IoXslUtil
				.getXmlText(expression)));
		code.append("/>");
		code.append(param);
		code.append("</");
		code.append(EnumPropertyWriter.WDWEMSNS + "formula");
		code.append(">");
		return code.toString();
	}
}
