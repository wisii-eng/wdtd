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

package com.wisii.io.zhumoban;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import com.wisii.db.Setting;
import com.wisii.db.model.SQLItem;
import com.wisii.io.zhumoban.FixedFileUtil.WSDMKind;
import com.wisii.io.zhumoban.FixedFileUtil.WSDXKind;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.banding.io.NameSpace;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.io.ElementWriter;
import com.wisii.wisedoc.io.IOFactory;
import com.wisii.wisedoc.io.IOUtil;
import com.wisii.wisedoc.io.Writer;
import com.wisii.wisedoc.io.XMLUtil;
import com.wisii.wisedoc.io.wsd.WSDWriter;
import com.wisii.wisedoc.view.action.ExplortXSLTFileAction;

public class WSDMWriter implements Writer
{

	@Override
	public void write(OutputStream outStream, Document document)
			throws IOException
	{

	}

	@Override
	public void write(String filename, Document document) throws IOException
	{
		File file = new File(filename);
		FileOutputStream outstream = new FileOutputStream(file);

		final ZipOutputStream zip = new ZipOutputStream(outstream);

		ZipEntry wsdentry = new ZipEntry(WSDMKind.WSD.getName());
		zip.putNextEntry(wsdentry);
		final WSDWriter wsd = new WSDWriter();
		wsd.write(zip, document);

		ZipEntry xsl = new ZipEntry(WSDMKind.XSLTEMPLATE.getName());
		zip.putNextEntry(xsl);
		final MainXSLWriter xslwriter = new MainXSLWriter();
		String fn = file.getName();
		if (fn.contains("."))
		{
			int dindex = fn.indexOf(".");
			fn = fn.substring(0, dindex);
		}
		xslwriter.writeNoClose(zip, document, fn);
		String datasource = xslwriter.getDatasource();
		if (datasource!=null&&!datasource.isEmpty())
		{
			ZipEntry datasourceentry = new ZipEntry(WSDMKind.DATASOURCE
					.getName());
			zip.putNextEntry(datasourceentry);
			write(zip, datasource);
		}
		String functiontemplate = xslwriter.getFunctiontemplate();
		if (functiontemplate!=null&&!functiontemplate.isEmpty())
		{
			ZipEntry functiontemplateentry = new ZipEntry(WSDMKind.FCTEMPLATE
					.getName());
			zip.putNextEntry(functiontemplateentry);
			write(zip, functiontemplate);
		}
		Setting seting = getSql();
		ZipEntry sqlentry = new ZipEntry(WSDXKind.SQL.getName());
		zip.putNextEntry(sqlentry);
		String sql = generateDBSetting(seting, document);
		write(zip, sql);
		String zimoban = xslwriter.getZimobannames();
		if (zimoban!=null&&!zimoban.isEmpty())
		{
			ZipEntry zimobanentry = new ZipEntry(WSDMKind.SUB.getName());
			zip.putNextEntry(zimobanentry);
			write(zip, zimoban);
		}
		DataStructureTreeModel ds=document.getDataStructure();
		if(ds!=null)
		{
			List<NameSpace> nses=ds.getSpaces();
			if(nses!=null&&!nses.isEmpty())
			{
				StringBuffer sb=new StringBuffer();
				for(NameSpace ns:nses){
					sb.append(ns.getAttribute());
					sb.append('=');
					sb.append(ns.getAttributeValue());
					sb.append('\n');
				}
				ZipEntry namespaces = new ZipEntry(WSDXKind.NAMESPACE.getName());
				zip.putNextEntry(namespaces);
				write(zip, sb.toString());
			}
		}
		ZipEntry exsl = new ZipEntry("exsl/xsl.xsl");
		zip.putNextEntry(exsl);
		ByteOutputStream bos = new ByteOutputStream();
		IOFactory.getWriter(IOFactory.XSL).write(bos, document);
		byte[] buff = bos.toByteArray();
		String xslstr=new String(buff,"utf-8");
		write(zip, xslstr);
		zip.close();
		outstream.close();
		System.gc();
	}

	public Setting getSql()
	{
		DataStructureTreeModel model = SystemManager.getCurruentDocument()
				.getDataStructure();
		if (model != null)
		{
			return model.getDbsetting();
		}
		return null;
	}

	public void write(OutputStream outstream, String content)
			throws IOException
	{
		byte[] bts = content.toString().getBytes("UTF-8");
		outstream.write(IOUtil.encrypt(bts));
		outstream.flush();
		((ZipOutputStream) outstream).closeEntry();
	}

	public void write(OutputStream outstream, Setting set) throws IOException
	{
		ObjectOutputStream oos = new ObjectOutputStream(outstream);
		oos.writeObject(set.getSqlitem());
		outstream.flush();
		((ZipOutputStream) outstream).closeEntry();
		// oos.close();
	}

	private String generateDBSetting(Setting dbsetting, Document document)
	{
		String returns = "";
		Object dbtype = document.getAttribute(Constants.PR_DBTYPE);
		String dsname = dbtype != null ? dbtype.toString() : "";
		returns = returns + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		returns = returns + "<DS dsname=\"" + dsname + "\">";
		if (dbsetting != null)
		{
			List<SQLItem> sqlitems = dbsetting.getSqlitem();
			if (sqlitems != null && !sqlitems.isEmpty())
			{
				for (SQLItem sqlitem : sqlitems)
				{
					returns = returns + generateSQLItem(sqlitem);
				}
			}
		}
		returns = returns + "</DS>";
		return returns;
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	private String generateSQLItem(SQLItem sqlitem)
	{
		String returns = "";
		if (sqlitem != null)
		{
			returns = returns + ElementWriter.TAGBEGIN + "i" + " ";
			returns = returns + "sql=\"" + XMLUtil.getXmlText(sqlitem.getSql())
					+ "\"";
			returns = returns + ElementWriter.TAGEND + ElementWriter.LINEBREAK;
			List<SQLItem> sqlitems = sqlitem.getChildren();
			if (sqlitems != null && !sqlitems.isEmpty())
			{
				for (SQLItem childsqlitem : sqlitems)
				{
					returns = returns + generateSQLItem(childsqlitem);
				}
			}
			returns = returns + ElementWriter.TAGENDSTART + "i"
					+ ElementWriter.TAGEND + ElementWriter.LINEBREAK;
		}
		return returns;
	}
}
