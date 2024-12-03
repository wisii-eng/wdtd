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

import java.util.List;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.transform.DataSource;
import com.wisii.wisedoc.document.attribute.transform.DataTransformTable;
import com.wisii.wisedoc.document.attribute.transform.SwingDS;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.XMLUtil;
import com.wisii.wisedoc.io.xsl.util.ElementUtil;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

public class DataSourcesWriter
 {

	DataSource transformtable;

	public DataSourcesWriter(DataSource src) {
		transformtable = src;
	}

	public String getcode(String name) {
		StringBuffer code = new StringBuffer();
		if (transformtable instanceof DataTransformTable) {
			DataTransformTable source = (DataTransformTable) transformtable;
			code.append(ElementWriter.TAGBEGIN);
			code.append(EnumPropertyWriter.WDWEMSNS);
			code.append("dataSource");

			code.append(outputAttributes("name", name));
			code.append(outputAttributes("struts", "table2"));

			String root = "root";
			code.append(outputAttributes("root", root));
			code.append(ElementWriter.TAGEND);

			code.append(ElementWriter.TAGBEGIN);
			code.append(EnumPropertyWriter.WDWEMSNS);
			code.append("data");
			code.append(ElementWriter.TAGEND);

			code.append(ElementWriter.TAGBEGIN);
			code.append("root");
			code.append(ElementWriter.TAGEND);

			List<List<String>> datas = source.getDatas();

			if (datas != null) {
				for (List<String> current : datas) {
					code.append(ElementWriter.TAGBEGIN);
					code.append("item");

					for (int i = 0; i < current.size(); i++) {
						String currentitem = current.get(i) != null ? current
								.get(i) : "";
						if ("@null".equals(currentitem)
								|| "@dnull".equals(currentitem)) {
							currentitem = "";
						}
						if (i == 0) {
							currentitem = IoXslUtil.getXmlText(currentitem);
							code.append(outputAttributes("key", currentitem));
						} else if (i == 1) {
							currentitem = IoXslUtil.getXmlText(currentitem);
							code.append(outputAttributes("value", currentitem));
						}
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
		} else if (transformtable instanceof SwingDS) {
			code.append(ElementWriter.TAGBEGIN);
			code.append(EnumPropertyWriter.WDWEMSNS);
			code.append("dataSource");
			code.append(ElementUtil.outputAttributes("name", "sw"+name));
			code.append(ElementUtil.outputAttributes("swingdatasource", name));
			code.append(ElementWriter.NOCHILDTAGEND);
			
			SwingDS source = (SwingDS) transformtable;
			code.append(ElementWriter.TAGBEGIN);
			code.append(EnumPropertyWriter.WDWEMSNS);
			code.append("swingdatasource");
			code.append(outputAttributes("name", name));
			code.append(ElementUtil.outputAttributes("columns", XMLUtil.getXmlText(source.getColumns())));
			String callclass = source.getCallbackclass();
			if (callclass != null && !callclass.isEmpty()) {
				code.append(outputAttributes("callbackclass", XMLUtil.getXmlText(callclass)));
			}
			String dataname = source.getDataname();
			if (dataname != null && !dataname.isEmpty()) {
				code.append(outputAttributes("dataname", XMLUtil.getXmlText(dataname)));
			}
			int type = source.getType();
			String typestr = "table";
			if (type == Constants.EN_TREE) {
				typestr = "tree";
			}
			code.append(outputAttributes("type", typestr));
			code.append(ElementWriter.NOCHILDTAGEND);
		}
		return code.toString();
	}

	public String outputAttributes(String name, String value) {
		StringBuffer output = new StringBuffer();
		if (name != null && value != null) {
			output.append(" ");
			output.append(name);
			output.append("=");
			output.append("\"");
			output.append(value);
			output.append("\"");
		}
		return output.toString();
	}
}
