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
import java.util.List;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.transform.Column.ColumnType;
import com.wisii.wisedoc.document.attribute.transform.DataSource;
import com.wisii.wisedoc.document.attribute.transform.FileSource;
import com.wisii.wisedoc.document.attribute.transform.MultiSource;
import com.wisii.wisedoc.document.attribute.transform.OutInterface;
import com.wisii.wisedoc.document.attribute.transform.SelectColumnInFO;
import com.wisii.wisedoc.document.attribute.transform.SelectInfo;
import com.wisii.wisedoc.document.attribute.transform.SwingDS;
import com.wisii.wisedoc.document.attribute.transform.TransformTable;
import com.wisii.wisedoc.document.attribute.transform.TreeSource;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.XMLUtil;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

public class DataSourcesInfoWriter
{

	SelectInfo sourceinfo;

	public DataSourcesInfoWriter(SelectInfo src)
	{
		sourceinfo = src;
	}

	public String getcode(String name)
	{
		StringBuffer code = new StringBuffer();
		code.append(ElementWriter.TAGBEGIN);
		code.append(EnumPropertyWriter.WDWEMSNS);
		code.append("dataSource");
		code.append(ElementUtil.outputAttributes("name", name));
		sourceinfo.getDatasourcetype();
		List<SelectColumnInFO> columninfos = sourceinfo.getColumninfos();
		DataSource source = sourceinfo.getDatasource();

		if (source instanceof TransformTable)
		{
			TransformTable transformtable = (TransformTable) source;
			code.append(ElementUtil.outputAttributes("struts", "table2"));
			String root = "root";
			code.append(ElementUtil.outputAttributes("root", root));
			code.append(ElementWriter.TAGEND);

			code.append(ElementWriter.TAGBEGIN);
			code.append(EnumPropertyWriter.WDWEMSNS);
			code.append("tableInfo");
			code.append(ElementWriter.TAGEND);
			if (columninfos != null && !columninfos.isEmpty())
			{
				for (SelectColumnInFO current : columninfos)
				{
					code.append(ElementWriter.TAGBEGIN);
					code.append(EnumPropertyWriter.WDWEMSNS);
					code.append("column");

					String columnname = current.getViewname();
					ColumnType columntype = current.getColumntype();
					String typecolumn = columntype.toString();
					code.append(ElementUtil
							.outputAttributes("name", columnname));
					code.append(ElementUtil
							.outputAttributes("type", typecolumn));
					code.append(ElementUtil.outputAttributes("attrName",
							columnname));
					code.append(ElementWriter.NOCHILDTAGEND);
				}
			}
			code.append(ElementUtil.endElement(EnumPropertyWriter.WDWEMSNS
					+ "tableInfo"));

			code.append(ElementWriter.TAGBEGIN);
			code.append(EnumPropertyWriter.WDWEMSNS);
			code.append("data");
			code.append(ElementWriter.TAGEND);

			code.append(ElementWriter.TAGBEGIN);
			code.append("root");
			code.append(ElementWriter.TAGEND);
			List<List<String>> datas = transformtable.getDatas();

			if (datas != null)
			{
				for (int j = 0; j < datas.size(); j++)
				{
					List<String> current = datas.get(j);
					code.append(ElementWriter.TAGBEGIN);
					code.append("item");

					for (int i = 0; i < current.size(); i++)
					{
						String currentitem = current.get(i) != null ? current
								.get(i) : "";
						currentitem = currentitem.toLowerCase();
						SelectColumnInFO currentinfo = columninfos.get(i);
						String infoname = currentinfo.getViewname();
						if ("@null".equals(currentitem)
								|| "@dnull".equals(currentitem))
						{
							currentitem = "";
						}
						code.append(ElementUtil.outputAttributes(infoname,
								currentitem));
					}
					code.append(ElementWriter.NOCHILDTAGEND);
					// code.append(ElementUtil.endElement("item");
				}
			}
			code.append(ElementUtil.endElement("root"));

			code.append(ElementUtil.endElement(EnumPropertyWriter.WDWEMSNS
					+ "data"));
			code.append(ElementUtil.endElement(EnumPropertyWriter.WDWEMSNS
					+ "dataSource"));
		} else if (source instanceof MultiSource)
		{
			MultiSource multisource = (MultiSource) source;
			int bq = multisource.getBond();
			String sourcebq = "";
			if (bq == Constants.EN_DATASOURCE_SQ)
			{
				sourcebq = "sq";
			} else if (bq == Constants.EN_DATASOURCE_EQ)
			{
				sourcebq = "eq";
			} else if (bq == Constants.EN_DATASOURCE_VERT)
			{
				sourcebq = "vert";
			}
			code.append(ElementUtil.outputAttributes("bond", sourcebq));
			List<FileSource> filesources = multisource.getFilesources();
			int filenumber = filesources != null ? filesources.size() : 0;
			if (filenumber == 1)
			{
				FileSource one = filesources.get(0);
				int type = one.getStructure();
				String filetype = getFileType(type);

				code.append(ElementUtil.outputAttributes("struts", filetype));
				String filename = one.getFile();
				String[] files = filename.split("\\\\");
				String realname = files[files.length - 1];
				realname = IoXslUtil.getXmlText(realname);
				// System.out.println("filename::" + filename);
				// System.out.println("files length:" + files.length);
				code.append(ElementUtil.outputAttributes("translateUrl",
						realname));
				String rootpath = one.getRoot();
				String[] roots = rootpath.split("/");
				// System.out.println("roots:" + rootpath);
				// System.out.println("roots length:" + roots.length);
				String rootname = roots[roots.length - 1];
				code.append(ElementUtil.outputAttributes("root", rootname));
				String valuenumber = "";
				List<Integer> valuenumberlist = one.getValuenumber();
				if (valuenumberlist != null && !valuenumberlist.isEmpty())
				{
					for (int i = 0; i < valuenumberlist.size(); i++)
					{
						valuenumber = valuenumber
								+ (valuenumberlist.get(i) + 1);
						if (i < valuenumberlist.size() - 1)
						{
							valuenumber = valuenumber + ",";
						}
					}
				}
				code.append(ElementUtil.outputAttributes("valueNumber",
						valuenumber));
			}
			code.append(ElementWriter.TAGEND);

			code.append(ElementWriter.TAGBEGIN);
			code.append(EnumPropertyWriter.WDWEMSNS);
			code.append("tableInfo");
			code.append(ElementWriter.TAGEND);

			if (columninfos != null && !columninfos.isEmpty()
					&& filenumber >= 1)
			{
				List<String> columninfo = new ArrayList<String>();
				FileSource one = filesources.get(0);
				if (filenumber > 1
						&& (bq == Constants.EN_DATASOURCE_SQ || bq == Constants.EN_DATASOURCE_EQ))
				{
					for (FileSource current : filesources)
					{
						List<String> currentinfo = current.getColumninfo();
						for (String currentitem : currentinfo)
						{
							columninfo.add(currentitem);
						}
					}
				} else
				{
					columninfo = one.getColumninfo();
				}
				for (int i = 0; i < columninfos.size(); i++)
				{
					SelectColumnInFO current = columninfos.get(i);
					code.append(ElementWriter.TAGBEGIN);
					code.append(EnumPropertyWriter.WDWEMSNS);
					code.append("column");
					String columnname = current.getViewname();
					ColumnType columntype = current.getColumntype();
					String typecolumn = columntype.toString();
					code.append(ElementUtil
							.outputAttributes("name", columnname));
					code.append(ElementUtil
							.outputAttributes("type", typecolumn));
					int datacolumnindex = current.getDatacolumnindex();

					String columnattrname = columninfo.get(datacolumnindex);
					code.append(ElementUtil.outputAttributes("attrName",
							columnattrname));
					code.append(ElementWriter.NOCHILDTAGEND);
				}
			}
			code.append(ElementUtil.endElement(EnumPropertyWriter.WDWEMSNS
					+ "tableInfo"));
			if (filenumber > 1)
			{
				for (int f = 0; f < filenumber; f++)
				{
					code.append(ElementWriter.TAGBEGIN);
					code.append(EnumPropertyWriter.WDWEMSNS);
					code.append("include");

					code.append(ElementUtil.outputAttributes("name", name
							+ "cn" + f));
					code.append(ElementWriter.NOCHILDTAGEND);
				}
			}
			code.append(ElementUtil.endElement(EnumPropertyWriter.WDWEMSNS
					+ "dataSource"));
			if (filenumber > 1)
			{
				for (int f = 0; f < filenumber; f++)
				{
					FileSource current = filesources.get(f);
					code.append(ElementWriter.TAGBEGIN);
					code.append(EnumPropertyWriter.WDWEMSNS);
					code.append("dataSource");
					int type = current.getStructure();
					String struts = getFileType(type);

					code.append(ElementUtil.outputAttributes("struts", struts));

					code.append(ElementUtil.outputAttributes("name", name
							+ "cn" + f));

					String valuenumber = "";
					List<Integer> valuenumberlist = current.getValuenumber();
					if (valuenumberlist != null && !valuenumberlist.isEmpty())
					{
						for (int i = 0; i < valuenumberlist.size(); i++)
						{
							valuenumber = valuenumber + (i + 1);
							if (i < valuenumberlist.size() - 1)
							{
								valuenumber = valuenumber + ",";
							}
						}
					}
					code.append(ElementUtil.outputAttributes("valueNumber",
							valuenumber));

					String filename = current.getFile();
					String[] files = filename.split("\\\\");
					String realname = files[files.length - 1];
					realname = IoXslUtil.getXmlText(realname);
					code.append(ElementUtil.outputAttributes("translateUrl",
							realname));
					String rootpath = current.getRoot();
					String[] roots = rootpath.split("\\\\");
					String rootname = roots[roots.length - 1];
					code.append(ElementUtil.outputAttributes("root", rootname));

					if (type == Constants.EN_TABLE1)
					{
						code.append(ElementWriter.NOCHILDTAGEND);
					} else
					{
						List<String> columninfo = new ArrayList<String>();
						FileSource one = filesources.get(0);
						if (filenumber > 1
								&& (bq == Constants.EN_DATASOURCE_SQ || bq == Constants.EN_DATASOURCE_EQ))
						{
							for (FileSource currentfile : filesources)
							{
								List<String> currentinfo = currentfile
										.getColumninfo();
								for (String currentitem : currentinfo)
								{
									columninfo.add(currentitem);
								}
							}
						} else
						{
							columninfo = one.getColumninfo();
						}
						code.append(ElementWriter.TAGEND);
						code.append(ElementWriter.TAGBEGIN);
						code.append(EnumPropertyWriter.WDWEMSNS);
						code.append("tableInfo");
						code.append(ElementWriter.TAGEND);
						if (columninfos != null && !columninfos.isEmpty())
						{

							for (int i = 0; i < columninfos.size(); i++)
							{
								SelectColumnInFO currentsif = columninfos
										.get(i);
								code.append(ElementWriter.TAGBEGIN);
								code.append(EnumPropertyWriter.WDWEMSNS);
								code.append("column");
								String columnname = currentsif.getViewname();
								ColumnType columntype = currentsif
										.getColumntype();
								String typecolumn = columntype.toString();
								code.append(ElementUtil.outputAttributes(
										"name", columnname));
								code.append(ElementUtil.outputAttributes(
										"type", typecolumn));
								int datacolumnindex = currentsif
										.getDatacolumnindex();
								String columnattrname = columninfo
										.get(datacolumnindex);
								code.append(ElementUtil.outputAttributes(
										"attrName", columnattrname));
								code.append(ElementWriter.NOCHILDTAGEND);
							}
						}
						code.append(ElementUtil
								.endElement(EnumPropertyWriter.WDWEMSNS
										+ "tableInfo"));
						code.append(ElementUtil
								.endElement(EnumPropertyWriter.WDWEMSNS
										+ "dataSource"));
					}

				}
			}
		} else if (source instanceof TreeSource)
		{
			TreeSource one = (TreeSource) source;
			code.append(ElementUtil.outputAttributes("struts", "tree"));
			String filename = one.getFile();
			String[] files = filename.split("\\\\");
			String realname = files[files.length - 1];
			realname = IoXslUtil.getXmlText(realname);
			code.append(ElementUtil.outputAttributes("translateUrl", realname));
			String rootpath = one.getRoot();
			String[] roots = rootpath.split("\\\\");
			String rootname = roots[roots.length - 1];
			code.append(ElementUtil.outputAttributes("root", rootname));
			String valuenumber = "";
			List<Integer> valuenumberlist = one.getValuenumber();
			if (valuenumberlist != null && !valuenumberlist.isEmpty())
			{
				for (int i = 0; i < valuenumberlist.size(); i++)
				{
					valuenumber = ""+(valuenumberlist.get(i) + 1);
					if (i != valuenumberlist.size() - 1)
					{
						valuenumber = valuenumber + ",";
					}
				}
			}
			code.append(ElementUtil
					.outputAttributes("valueNumber", valuenumber));

			code.append(ElementWriter.TAGEND);

			code.append(ElementWriter.TAGBEGIN);
			code.append(EnumPropertyWriter.WDWEMSNS);
			code.append("tableInfo");
			code.append(ElementWriter.TAGEND);
			if (columninfos != null && !columninfos.isEmpty())
			{
				List<String> columninfo = one.getColumninfo();
				for (int i = 0; i < columninfos.size(); i++)
				{
					SelectColumnInFO current = columninfos.get(i);
					code.append(ElementWriter.TAGBEGIN);
					code.append(EnumPropertyWriter.WDWEMSNS);
					code.append("column");

					String columnname = current.getViewname();
					ColumnType columntype = current.getColumntype();
					String typecolumn = columntype.toString();
					code.append(ElementUtil
							.outputAttributes("name", columnname));
					code.append(ElementUtil
							.outputAttributes("type", typecolumn));
					int datacolumnindex = current.getDatacolumnindex();
					String columnattrname = columninfo.get(datacolumnindex);
					code.append(ElementUtil.outputAttributes("attrName",
							columnattrname));
					code.append(ElementWriter.NOCHILDTAGEND);
				}
			}
			code.append(ElementUtil.endElement(EnumPropertyWriter.WDWEMSNS
					+ "tableInfo"));
			code.append(ElementUtil.endElement(EnumPropertyWriter.WDWEMSNS
					+ "dataSource"));
		} else if (source instanceof OutInterface)
		{
			OutInterface one = (OutInterface) source;
			String filename = one.getClassname();
			filename = IoXslUtil.getXmlText(filename);
			code.append(ElementUtil.outputAttributes("translateUrl", filename));
			code.append(ElementWriter.TAGEND);
			code.append(ElementWriter.TAGBEGIN);
			code.append(EnumPropertyWriter.WDWEMSNS);
			code.append("tableInfo");
			code.append(ElementWriter.TAGEND);
			if (columninfos != null && !columninfos.isEmpty())
			{
				for (SelectColumnInFO current : columninfos)
				{
					code.append(ElementWriter.TAGBEGIN);
					code.append(EnumPropertyWriter.WDWEMSNS);
					code.append("column");

					String columnname = current.getViewname();
					ColumnType columntype = current.getColumntype();
					String typecolumn = columntype.toString();
					code.append(ElementUtil
							.outputAttributes("name", columnname));
					code.append(ElementUtil
							.outputAttributes("type", typecolumn));
					code.append(ElementUtil.outputAttributes("attrName",
							columnname));
					code.append(ElementWriter.NOCHILDTAGEND);
				}
			}
			code.append(ElementUtil.endElement(EnumPropertyWriter.WDWEMSNS
					+ "tableInfo"));
			code.append(ElementUtil.endElement(EnumPropertyWriter.WDWEMSNS
					+ "dataSource"));
		}
		else if(source instanceof SwingDS)
		{

			String swname = source.hashCode() + IoXslUtil.getFilename();
			code.append(ElementUtil.outputAttributes("swingdatasource", swname));
			code.append(ElementWriter.TAGEND);
			code.append(ElementWriter.TAGBEGIN);
			code.append(EnumPropertyWriter.WDWEMSNS);
			code.append("tableInfo");
			code.append(ElementWriter.TAGEND);
			if (columninfos != null && !columninfos.isEmpty())
			{
				for (SelectColumnInFO current : columninfos)
				{
					code.append(ElementWriter.TAGBEGIN);
					code.append(EnumPropertyWriter.WDWEMSNS);
					code.append("column");

					String columnname = current.getViewname();
					ColumnType columntype = current.getColumntype();
					String typecolumn = columntype.toString();
					code.append(ElementUtil
							.outputAttributes("name", columnname));
					code.append(ElementUtil
							.outputAttributes("type", typecolumn));
					code.append(ElementUtil.outputAttributes("attrName",
							columnname));
					code.append(ElementWriter.NOCHILDTAGEND);
				}
			}
			code.append(ElementUtil.endElement(EnumPropertyWriter.WDWEMSNS
					+ "tableInfo"));
			code.append(ElementUtil.endElement(EnumPropertyWriter.WDWEMSNS
					+ "dataSource"));
			SwingDS swsource = (SwingDS) source;
			code.append(ElementWriter.TAGBEGIN);
			code.append(EnumPropertyWriter.WDWEMSNS);
			code.append("swingdatasource");
			code.append(ElementUtil.outputAttributes("name", swname));
			code.append(ElementUtil.outputAttributes("columns", XMLUtil.getXmlText(swsource.getColumns())));
			String callclass = swsource.getCallbackclass();
			if (callclass != null && !callclass.isEmpty()) {
				code.append(ElementUtil.outputAttributes("callbackclass",
						XMLUtil.getXmlText(callclass)));
			}
			String dataname = swsource.getDataname();
			if (dataname != null && !dataname.isEmpty()) {
				code.append(ElementUtil.outputAttributes("dataname", XMLUtil.getXmlText(dataname)));
			}
			int type = swsource.getType();
			String typestr = "table";
			if (type == Constants.EN_TREE) {
				typestr = "tree";
			}
			code.append(ElementUtil.outputAttributes("type", typestr));
			code.append(ElementWriter.NOCHILDTAGEND);
		}
		return code.toString();
	}

	public String getFileType(int type)
	{
		String filetype = "";
		if (type == Constants.EN_TABLE1)
		{
			filetype = "table1";
		} else if (type == Constants.EN_TABLE2)
		{
			filetype = "table2";
		} else if (type == Constants.EN_TREE)
		{
			filetype = "tree";
		}
		return filetype;
	}
}
