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

package com.wisii.wisedoc.io.xsl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.banding.io.NameSpace;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.io.xsl.attribute.edit.EnumPropertyWriter;
import com.wisii.wisedoc.io.xsl.util.DefaultValueMap;
import com.wisii.wisedoc.io.xsl.util.EditKeyAndValueUtil;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

public class XslPSWrite extends XslWrite
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wisii.wisedoc.io.Writer#write(java.io.OutputStream,
	 * javax.swing.text.Document)
	 */
	public void write(OutputStream outstream, Document document)
			throws IOException
	{
		DefaultValueMap.setInitMap();
		EnumPropertyWriter.initializationValueMap();
		EditKeyAndValueUtil.initializationKeyMap();
		checkPageIndex(document);

		java.io.Writer writer = new OutputStreamWriter(outstream, "UTF-8");
		PrintWriter out = new PrintWriter(writer);
		WiseDocDocumentWriter wiseDocDocumentWriter = new WiseDocDocumentWriter(
				(WiseDocDocument) document, "PS");
		String contentTemplates = wiseDocDocumentWriter.writeTemplates();
		String content = wiseDocDocumentWriter.writeOne();
		DataStructureTreeModel ds=document.getDataStructure();
		if(ds!=null)
		{
			List<NameSpace> nses=ds.getSpaces();
			if(nses!=null)
			{
				for(NameSpace ns:nses){
				IoXslUtil.addNameSpace(ns);
				}
			}
		}
		out.print(getEncoding());
		out.flush();
		out.print(getVersionAndDate());
		out.flush();
		out.print(getStylesheetStart());
		out.flush();
		out.print(getOutputElement());
		out.flush();
		out.print(addOverall());
		out.flush();
		out.print(content);
		out.flush();
		out.print(contentTemplates);
		out.flush();
		out.print(addFunctionTemplate());
		out.flush();
		out.print(addProFiles());
		out.flush();
		out.print(addEditStart());
		out.flush();
		out.print(addUIEdit());
		out.flush();
		out.print(addDataSourceCode());
		out.flush();
		out.print(addDataSourceInfoCode());
		out.flush();
		out.print(addValidationCode());
		out.flush();
		out.print(addEditEnd(document));
		out.flush();
		out.print(getStylesheetEnd());
		out.flush();
		out.close();
		clear();
		initialization();
		DefaultValueMap.clearCompleteAttributes();
		System.gc();
	}

	public void write(String filename, Document document) throws IOException
	{
		File file = new File(filename);
		DefaultValueMap.setInitMap();
		EnumPropertyWriter.initializationValueMap();
		EditKeyAndValueUtil.initializationKeyMap();
		checkPageIndex(document);
		String fileName = file.getName().replaceFirst(".xsl", "");
		IoXslUtil.setFilename(fileName);
		FileOutputStream outstream = new FileOutputStream(file);
		java.io.Writer writer = new OutputStreamWriter(outstream, "UTF-8");
		PrintWriter out = new PrintWriter(writer);
		WiseDocDocumentWriter wiseDocDocumentWriter = new WiseDocDocumentWriter(
				(WiseDocDocument) document, fileName);
		String contentTemplates = wiseDocDocumentWriter.writeTemplates();
		String content = wiseDocDocumentWriter.writeOne();
		DataStructureTreeModel ds=document.getDataStructure();
		if(ds!=null)
		{
			List<NameSpace> nses=ds.getSpaces();
			if(nses!=null)
			{
				for(NameSpace ns:nses){
				IoXslUtil.addNameSpace(ns);
				}
			}
		}
		out.print(getEncoding());
		out.flush();
		out.print(getVersionAndDate());
		out.flush();
		out.print(getStylesheetStart());
		out.flush();
		out.print(getOutputElement());
		out.flush();
		out.print(addOverall());
		out.flush();
		out.print(content);
		out.flush();
		out.print(contentTemplates);
		out.flush();
		out.print(addFunctionTemplate());
		out.flush();
		out.print(addProFiles());
		out.flush();
		out.print(addEditStart());
		out.flush();
		out.print(addUIEdit());
		out.flush();
		out.print(addDataSourceCode());
		out.flush();
		out.print(addDataSourceInfoCode());
		out.flush();
		out.print(addValidationCode());
		out.flush();
		out.print(addEditEnd(document));
		out.flush();
		out.print(getStylesheetEnd());
		out.flush();
		out.close();
		clear();
		initialization();
		DefaultValueMap.clearCompleteAttributes();
		System.gc();
	}
}
