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
 * @DataSourceWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd.attribute;

import java.util.List;

import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.attribute.transform.DataSource;
import com.wisii.wisedoc.document.attribute.transform.DataTransformTable;
import com.wisii.wisedoc.document.attribute.transform.FileSource;
import com.wisii.wisedoc.document.attribute.transform.MultiSource;
import com.wisii.wisedoc.document.attribute.transform.OutInterface;
import com.wisii.wisedoc.document.attribute.transform.PopupBrowserSource;
import com.wisii.wisedoc.document.attribute.transform.SwingDS;
import com.wisii.wisedoc.document.attribute.transform.TransformTable;
import com.wisii.wisedoc.document.attribute.transform.TreeSource;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.XMLUtil;

/**
 * 类功能描述：DataSource Wirter类
 * 
 * 作者：zhangqiang 创建日期：2009-9-2
 */
public final class DataSourceWriter extends AbstractAttributeWriter
 {
	public static final String TREESOURCE = "treesource";
	public static final String FILE = "file";
	public static final String ROOT = "root";
	public static final String COLUMNINFOS = "columninfos";
	public static final String VALUENUMBERS = "valuenumbers";
	public static final String TRANSFORMTABLE = "transformtable";
	public static final String POPUPBROWSERDATA = "popupbrowserdata";
	public static final String DATAS = "datas";
	public static final String LINESPLIT = "@l@!#,";
	public static final String DATATREAT = "datatreat";
	public static final String TEXT = "text";
	public static final String DATATRANSFORMTABLE = "datatransformtable";
	public static final String MULTISOURCE = "multisource";
	public static final String BOND = "bond";
	public static final String FILESOURCE = "filesource";
	public static final String STRUCTURE = "structure";
	public static final String OUTINTERFACE = "outinterface";
	public static final String CLASSNAME = "classname";
	public static final String COLUMNS = "columns";
	public static final String SWINGDS = "swingds";
	public static final String DATANAME = "dataname";

	@Override
	public final String write(int key, Object value) {
		if (!(value instanceof DataSource)) {
			return "";
		}
		String returns = "";
		DataSource datasource = (DataSource) value;
		if (datasource instanceof TreeSource) {
			returns = returns + getTreeSourceString((TreeSource) datasource);
		} else if (datasource instanceof OutInterface) {
			returns = returns
					+ getOutInterfaceString((OutInterface) datasource);
		} else if (datasource instanceof SwingDS) {
			returns = returns + getSwingDSString((SwingDS) datasource);
		} else if (datasource instanceof MultiSource) {
			returns = returns + getMultiSourceString((MultiSource) datasource);
		} else if (datasource instanceof DataTransformTable) {
			returns = returns
					+ getDataTransformTableString((DataTransformTable) datasource);
		} else if (datasource instanceof TransformTable) {
			returns = returns
					+ getTransformTableString((TransformTable) datasource);
		}else if (datasource instanceof PopupBrowserSource) {
			returns = returns
					+ getPopupBrowserSourceString((PopupBrowserSource) datasource);
		}
		return returns;
	}

	private String getSwingDSString(SwingDS datasource) {
		String returns = "";
		returns = returns + ElementWriter.TAGBEGIN + SWINGDS + SPACETAG;
		String classname = datasource.getCallbackclass();
		if (classname != null && !classname.isEmpty()) {
			returns = returns + CLASSNAME + EQUALTAG + QUOTATIONTAG
					+ XMLUtil.getXmlText(classname) + QUOTATIONTAG;
		}
		String dataname = datasource.getDataname();
		if (dataname != null && !dataname.isEmpty()) {
			returns = returns + SPACETAG + DATANAME + EQUALTAG + QUOTATIONTAG
					+ XMLUtil.getXmlText(dataname) + QUOTATIONTAG;
		}
		returns = returns + SPACETAG + COLUMNS + EQUALTAG + QUOTATIONTAG
				+ XMLUtil.getXmlText(datasource.getColumns()) + QUOTATIONTAG;
		returns = returns + SPACETAG + STRUCTURE + EQUALTAG + QUOTATIONTAG
				+ datasource.getType() + QUOTATIONTAG;
		returns = returns + ElementWriter.NOCHILDTAGEND;
		return returns;
	}

	private final String getTreeSourceString(TreeSource treesource) {
		String returns = "";
		String filepath = XMLUtil.getXmlText(treesource.getFile());
		String root = XMLUtil.getXmlText(treesource.getRoot());
		String columninfostring = "";
		List<String> columninfos = treesource.getColumninfo();
		for (String columninfo : columninfos) {
			if (columninfostring.equals("")) {
				columninfostring = columninfostring + columninfo;
			} else {
				columninfostring = columninfostring + TEXTSPLIT + columninfo;
			}
		}
		String valuenumberstring = "";
		List<Integer> valuenumbers = treesource.getValuenumber();
		if (valuenumbers != null) {
			for (Integer valuenumber : valuenumbers) {
				if (valuenumberstring.equals("")) {
					valuenumberstring = valuenumberstring + valuenumber;
				} else {
					valuenumberstring = "," + valuenumber;
				}
			}
		}
		returns = returns + ElementWriter.TAGBEGIN + TREESOURCE + SPACETAG
				+ FILE + EQUALTAG + QUOTATIONTAG + filepath + QUOTATIONTAG;
		returns = returns + SPACETAG + ROOT + EQUALTAG + QUOTATIONTAG + root
				+ QUOTATIONTAG;
		returns = returns + SPACETAG + COLUMNINFOS + EQUALTAG + QUOTATIONTAG
				+ XMLUtil.getXmlText(columninfostring) + QUOTATIONTAG;
		if (!valuenumberstring.isEmpty()) {
			returns = returns + SPACETAG + VALUENUMBERS + EQUALTAG
					+ QUOTATIONTAG + valuenumberstring + QUOTATIONTAG;
		}
		returns = returns + ElementWriter.NOCHILDTAGEND;
		return returns;
	}

	private String getOutInterfaceString(OutInterface outinterface) {
		String returns = "";
		String classname = outinterface.getClassname();
		int columncount = outinterface.getCloumncount();
		returns = returns + ElementWriter.TAGBEGIN + OUTINTERFACE + SPACETAG
				+ CLASSNAME + EQUALTAG + QUOTATIONTAG
				+ XMLUtil.getXmlText(classname) + QUOTATIONTAG;
		returns = returns + SPACETAG + COLUMNS + EQUALTAG + QUOTATIONTAG
				+ columncount + QUOTATIONTAG;
		returns = returns + ElementWriter.NOCHILDTAGEND;
		return returns;
	}

	private final String getTransformTableString(TransformTable datasource) {
		String returns = "";
		List<List<String>> datas = datasource.getDatas();
		String datastring = "";
		for (List<String> linedatas : datas) {
			String linestring = "";
			for (String data : linedatas) {
				data = XMLUtil.getXmlText(data);
				if (linestring.equals("")) {
					linestring = linestring + data;
				} else {
					linestring = linestring + TEXTSPLIT + data;
				}
			}
			if (datastring.equals("")) {
				datastring = datastring + linestring;
			} else {
				datastring = datastring + LINESPLIT + linestring;
			}
		}
		returns = returns + ElementWriter.TAGBEGIN + TRANSFORMTABLE + SPACETAG
				+ DATAS + EQUALTAG + QUOTATIONTAG + datastring + QUOTATIONTAG
				+ ElementWriter.NOCHILDTAGEND;
		return returns;
	}
	private final String getPopupBrowserSourceString(PopupBrowserSource datasource) {
		String returns = "";
		String file = datasource.getFile();
		Integer id = datasource.getId();
		String code = datasource.getCode();
		String name = datasource.getName();
		String data = datasource.getData();
		String para = datasource.getPara();
		String url = datasource.getUrl();
		
		String datastring = "";
		datastring = file + LINESPLIT + id +LINESPLIT+ code+LINESPLIT +name+LINESPLIT + data+LINESPLIT + para+LINESPLIT + url;
		returns = returns + ElementWriter.TAGBEGIN + POPUPBROWSERDATA + SPACETAG
				+ DATAS + EQUALTAG + QUOTATIONTAG + datastring + QUOTATIONTAG
				+ ElementWriter.NOCHILDTAGEND;
		return returns;
	}

	private final String getDataTransformTableString(
			DataTransformTable datasource) {
		String returns = "";
		List<List<String>> datas = datasource.getDatas();
		String datastring = "";
		for (List<String> linedatas : datas) {
			String linestring = "";
			for (String data : linedatas) {
				data = XMLUtil.getXmlText(data);
				if (linestring.equals("")) {
					linestring = linestring + data;
				} else {
					linestring = linestring + TEXTSPLIT + data;
				}
			}
			if (datastring.equals("")) {
				datastring = datastring + linestring;
			} else {
				datastring = datastring + LINESPLIT + linestring;
			}
		}
		int datatreat = datasource.getDatatreat();
		returns = returns + ElementWriter.TAGBEGIN + DATATRANSFORMTABLE
				+ SPACETAG + DATATREAT + EQUALTAG + QUOTATIONTAG + datatreat
				+ QUOTATIONTAG;
		String text = datasource.getText();
		if (text != null && !text.isEmpty()) {
			returns = returns + SPACETAG + TEXT + EQUALTAG + QUOTATIONTAG
					+ XMLUtil.getXmlText(text) + QUOTATIONTAG;
		}
		returns = returns + SPACETAG + DATAS + EQUALTAG + QUOTATIONTAG
				+ datastring + QUOTATIONTAG + ElementWriter.NOCHILDTAGEND;
		return returns;
	}

	private final String getMultiSourceString(MultiSource datasource) {
		String returns = "";
		returns = returns + ElementWriter.TAGBEGIN + MULTISOURCE;
		returns = returns + SPACETAG + BOND + EQUALTAG + QUOTATIONTAG
				+ datasource.getBond() + QUOTATIONTAG + ElementWriter.TAGEND;
		returns = returns + getFileSourcesString(datasource.getFilesources());
		returns = returns + ElementWriter.TAGENDSTART + MULTISOURCE
				+ ElementWriter.TAGEND;
		return returns;
	}

	private String getFileSourcesString(List<FileSource> filesources) {
		String returns = "";
		for (FileSource filesource : filesources) {
			String filepath = XMLUtil.getXmlText(filesource.getFile());
			String root = XMLUtil.getXmlText(filesource.getRoot());
			String columninfostring = "";
			List<String> columninfos = filesource.getColumninfo();
			for (String columninfo : columninfos) {
				if (columninfostring.equals("")) {
					columninfostring = columninfostring + columninfo;
				} else {
					columninfostring = columninfostring + TEXTSPLIT
							+ columninfo;
				}
			}
			String valuenumberstring = "";
			List<Integer> valuenumbers = filesource.getValuenumber();
			if (valuenumbers != null) {
				for (Integer valuenumber : valuenumbers) {
					if (valuenumberstring.equals("")) {
						valuenumberstring = valuenumberstring + valuenumber;
					} else {
						valuenumberstring = "," + valuenumber;
					}
				}
			}
			returns = returns + ElementWriter.TAGBEGIN + FILESOURCE + SPACETAG
					+ FILE + EQUALTAG + QUOTATIONTAG + filepath + QUOTATIONTAG;
			returns = returns + SPACETAG + ROOT + EQUALTAG + QUOTATIONTAG
					+ root + QUOTATIONTAG;
			returns = returns + SPACETAG + STRUCTURE + EQUALTAG + QUOTATIONTAG
					+ filesource.getStructure() + QUOTATIONTAG;
			returns = returns + SPACETAG + COLUMNINFOS + EQUALTAG
					+ QUOTATIONTAG + XMLUtil.getXmlText(columninfostring)
					+ QUOTATIONTAG;
			if (!valuenumberstring.isEmpty()) {
				returns = returns + SPACETAG + VALUENUMBERS + EQUALTAG
						+ QUOTATIONTAG + valuenumberstring + QUOTATIONTAG;
			}
			returns = returns + ElementWriter.NOCHILDTAGEND;
		}
		return returns;
	}
}
