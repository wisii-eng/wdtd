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
 * @DataSourceListReader.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.transform.DataSource;
import com.wisii.wisedoc.document.attribute.transform.DataTransformTable;
import com.wisii.wisedoc.document.attribute.transform.FileSource;
import com.wisii.wisedoc.document.attribute.transform.MultiSource;
import com.wisii.wisedoc.document.attribute.transform.OutInterface;
import com.wisii.wisedoc.document.attribute.transform.PopupBrowserSource;
import com.wisii.wisedoc.document.attribute.transform.SwingDS;
import com.wisii.wisedoc.document.attribute.transform.TransformTable;
import com.wisii.wisedoc.document.attribute.transform.TreeSource;
import com.wisii.wisedoc.io.XMLUtil;
import com.wisii.wisedoc.io.wsd.attribute.DataSourceWriter;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2009-9-4
 */
public class DataSourceListReader extends AbstractHandler
{
	private List<DataSource> datasourcelist = new ArrayList<DataSource>();
	private List<FileSource> filesources;
	private int bond = -1;

	void init()
	{
		filesources = null;
		bond = -1;
		datasourcelist.clear();
	}

	public void startElement(String uri, String localname, String qname,
			Attributes atts) throws SAXException
	{
		if (DataSourceWriter.DATATRANSFORMTABLE.equals(qname))
		{
			String datatreatst = atts.getValue(DataSourceWriter.DATATREAT);
			try
			{
				int datatreat = Integer.parseInt(datatreatst);
				String text = XMLUtil.fromXmlText(atts.getValue(DataSourceWriter.TEXT));
				String datastring = atts.getValue(DataSourceWriter.DATAS);
				List<List<String>> datas = getDatas(datastring);
				if (datas != null)
				{
					datasourcelist.add(new DataTransformTable(datas, datatreat,
							text,null));
				} else
				{
					datasourcelist.add(null);
					LogUtil.debug("解析datasouce时出错");
				}
			} catch (Exception e)
			{
				datasourcelist.add(null);
				LogUtil.debugException("解析datasouce时出错", e);
			}
		} 
		else if (DataSourceWriter.POPUPBROWSERDATA.equals(qname)) {
			String datastring = atts.getValue(DataSourceWriter.DATAS);
			String[] datas = null;
			if (datastring != null) {
				datas = datastring.split(DataSourceWriter.LINESPLIT);
			}
			if (datas != null) {
				PopupBrowserSource pbs = new PopupBrowserSource();
				for (int i = 0; i < datas.length; i++) {
					switch (i) {
					case 0:
						pbs.setFile(datas[i]);
						break;
					case 1:
						pbs.setId(Integer.parseInt(datas[i]));
						break;
					case 2:
						pbs.setCode(datas[i]);
						break;
					case 3:
						pbs.setName(datas[i]);
						break;
					case 4:
						pbs.setData(datas[i]);
						break;
					case 5:
						pbs.setPara(datas[i]);
						break;
					case 6:
						pbs.setUrl(datas[i]);
						break;
					default:
						break;
					}
				}
				datasourcelist.add(pbs);
			} else {
				datasourcelist.add(null);
				LogUtil.debug("解析datasouce时出错");
			}
		}

		else if (DataSourceWriter.TRANSFORMTABLE.equals(qname))
		{
			String datastring = atts.getValue(DataSourceWriter.DATAS);
			List<List<String>> datas = getDatas(datastring);
			if (datas != null)
			{
				datasourcelist.add(new TransformTable(datas));
			} else
			{
				datasourcelist.add(null);
				LogUtil.debug("解析datasouce时出错");
			}
		} else if (DataSourceWriter.TREESOURCE.equals(qname))
		{
			String file = XMLUtil.fromXmlText(atts.getValue(DataSourceWriter.FILE));
			String root = XMLUtil.fromXmlText(atts.getValue(DataSourceWriter.ROOT));
			String columninfostring = XMLUtil.fromXmlText(atts
					.getValue(DataSourceWriter.COLUMNINFOS));
			List<String> columninfos = null;
			if (columninfostring != null)
			{
				columninfos = Arrays.asList(columninfostring
						.split(DataSourceWriter.TEXTSPLIT));
			}
			String valuenumberstring = atts
					.getValue(DataSourceWriter.VALUENUMBERS);
			List<Integer> valuenumbers = null;
			if (valuenumberstring != null)
			{
				String[] vnes = valuenumberstring.split(",");
				for (String vn : vnes)
				{
					try
					{
						int key = Integer.parseInt(vn);
						valuenumbers.add(key);
					} catch (Exception e)
					{
						LogUtil.debugException("解析TREESOURCE时出错", e);
					}
				}
				if (valuenumbers.isEmpty())
				{
					valuenumbers = null;
				}
			}
			if (file == null || root == null || columninfos == null)
			{
				LogUtil.debug("解析TREESOURCE时出错");
				datasourcelist.add(null);
			} else
			{
				datasourcelist.add(new TreeSource(file, root, columninfos,
						valuenumbers));
			}
		}
		else if (DataSourceWriter.OUTINTERFACE.equals(qname))
		{
			String classname = XMLUtil.fromXmlText(atts.getValue(DataSourceWriter.CLASSNAME));
			if (classname == null)
			{
				LogUtil.debug("解析OUTINTERFACE时出错,"+DataSourceWriter.CLASSNAME+"属性不能为空");
				datasourcelist.add(null);
			}
			int cloumncount = -1;
			String columncountstring = atts
					.getValue(DataSourceWriter.COLUMNS);
			if (columncountstring != null)
			{
				try
				{
					int count = Integer.parseInt(columncountstring);
					cloumncount = count;
				} catch (Exception e)
				{
					LogUtil.debug("解析OUTINTERFACE时出错,"+DataSourceWriter.COLUMNS+"属性必须是整形数字", e);
				}
			}
			datasourcelist.add(new OutInterface(classname, cloumncount));
		} 
		else if (DataSourceWriter.SWINGDS.equals(qname))
 {
			String classname = XMLUtil.fromXmlText(atts
					.getValue(DataSourceWriter.CLASSNAME));
			String dataname = XMLUtil.fromXmlText(atts
					.getValue(DataSourceWriter.DATANAME));
			if (classname == null && dataname == null) {
				LogUtil.debug("解析SwingDS时出错," + DataSourceWriter.CLASSNAME
						+ "和" + DataSourceWriter.DATANAME + "属性必须得有一个不为空");
				datasourcelist.add(null);
			}
			String columns = atts
					.getValue(DataSourceWriter.COLUMNS);
			int type = Constants.EN_TABLE1;
			String typestring = atts.getValue(DataSourceWriter.STRUCTURE);
			if (typestring != null) {
				try {
					int t = Integer.parseInt(typestring);
					type = t;
				} catch (Exception e) {
					LogUtil.debug("解析SwingDS时出错," + DataSourceWriter.STRUCTURE
							+ "属性必须是整形数字", e);
				}
			}
			if (classname != null) {
				datasourcelist.add(new SwingDS(classname, type, columns));
			} else {
				datasourcelist.add(new SwingDS(type, dataname, columns));
			}

		} 
		else if (DataSourceWriter.MULTISOURCE.equals(qname))
		{
			String bondstring = atts.getValue(DataSourceWriter.BOND);
			if (bondstring != null)
			{
				try
				{
					bond = Integer.parseInt(bondstring);
					filesources = new ArrayList<FileSource>();
				} catch (Exception e)
				{
					LogUtil.debugException("解析multisource时出错", e);
				}
			}
		} else if (DataSourceWriter.FILESOURCE.equals(qname))
		{
			if (filesources != null)
			{
				String file = XMLUtil.fromXmlText(atts.getValue(DataSourceWriter.FILE));
				String root = XMLUtil.fromXmlText(atts.getValue(DataSourceWriter.ROOT));
				String columninfostring = XMLUtil.fromXmlText(atts
						.getValue(DataSourceWriter.COLUMNINFOS));
				List<String> columninfos = null;
				if (columninfostring != null)
				{
					columninfostring = XMLUtil.fromXmlText(columninfostring);
					columninfos = Arrays.asList(columninfostring
							.split(DataSourceWriter.TEXTSPLIT));
				}
				String valuenumberstring = atts
						.getValue(DataSourceWriter.VALUENUMBERS);
				List<Integer> valuenumbers = null;
				if (valuenumberstring != null)
				{
					String[] vnes = valuenumberstring.split(",");
					valuenumbers = new ArrayList<Integer>();
					for (String vn : vnes)
					{
						try
						{
							int key = Integer.parseInt(vn);
							valuenumbers.add(key);
						} catch (Exception e)
						{
							LogUtil.debugException("解析TREESOURCE时出错", e);
						}
					}
					if (valuenumbers.isEmpty())
					{
						valuenumbers = null;
					}
				}
				if (file != null && root != null && columninfostring != null)
				{
					String structurestring = atts
							.getValue(DataSourceWriter.STRUCTURE);
					try
					{
						int structure = Integer.parseInt(structurestring);
						filesources.add(new FileSource(file, structure, root,
								columninfos, valuenumbers));
					} catch (Exception e)
					{
						LogUtil.debugException("解析filesource时出错", e);
					}
				}
			}
		} else
		{
			throw new SAXException("chartdatalists 节点下的" + qname + "节点为非法");
		}
	}

	private List<List<String>> getDatas(String datastring)
	{
		if (datastring == null || datastring.isEmpty())
		{
			return null;
		} else
		{
			List<List<String>> datalistes = new ArrayList<List<String>>();
			String[] linedatas = datastring.split(DataSourceWriter.LINESPLIT);
			for (String linedata : linedatas)
			{
				if (linedata != null && !linedata.isEmpty())
				{
					String[] datas = linedata.split(DataSourceWriter.TEXTSPLIT);
					for(int i=0;i<datas.length;i++)
					{
						datas[i] = XMLUtil.fromXmlText(datas[i]);  
					}
					List<String> datalist = Arrays.asList(datas);
					datalistes.add(datalist);
				}
			}
			if (datalistes.isEmpty())
			{
				datalistes = null;
			}
			return datalistes;
		}
	}

	public void endElement(String uri, String localname, String qname)
			throws SAXException
	{
		if (DataSourceWriter.MULTISOURCE.equals(qname))
		{
			if (filesources != null)
			{
				datasourcelist.add(new MultiSource(bond, filesources));
				bond = -1;
				filesources = null;
			} else
			{
				datasourcelist.add(null);
				LogUtil.debug("解析MULTISOURCE时出错");
			}
		}
	}

	public List<DataSource> getObject()
	{
		return datasourcelist;
	}

}
