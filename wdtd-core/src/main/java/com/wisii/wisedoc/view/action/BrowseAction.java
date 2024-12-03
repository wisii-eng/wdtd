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
 * @BrowseAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.Icon;
import com.wisii.db.model.Setting;
import com.wisii.exception.ZimobanFileNotFoundException;
import com.wisii.io.zhumoban.MainXSLWriter;
import com.wisii.io.zhumoban.FixedFileUtil.WSDXKind;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.banding.io.NameSpace;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.io.IOFactory;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;

/**
 * 类功能描述：预览事件类
 */
public class BrowseAction extends AbstractBrowseAction
 {



	/**
	 * 
	 * 用默认的字符串和默认的图标定义一个Action对象。
	 */
	public BrowseAction() {
		super();
	}

	/**
	 * 用指定描述字符串和默认图标定义一个 Action 对象。
	 * 
	 * @param name
	 *            指定Action的名字。
	 * 
	 */
	public BrowseAction(String name) {
		super(name);
	}

	/**
	 * 用指定描述字符串指定图标定义一个 Action 对象。
	 * 
	 * @param name
	 *            指定Action的名字。
	 * @param icon
	 *            指定Action的图标。
	 */
	public BrowseAction(String name, Icon icon) {
		super(name, icon);
	}

	
	protected void browse(FileItem xslfileitem,FileItem xmlfileitem)
	{
		if (xslfileitem == null || xmlfileitem == null) {
			return;
		}
		String fopPath = "../wdems" + File.separator
				+ "wdems.bat";
		String command = fopPath + " -xml "
				+ URLEncode(xmlfileitem.getFile().getAbsolutePath()) + " -xsl "
				+ URLEncode(xslfileitem.getFile().getAbsolutePath()) + " awt";
		// 采用另外一根线程调用预览程序，这样预览时还可以进行其他操作
		new BrowseThread(command, xslfileitem, xmlfileitem).start();
	}

	protected FileItem generateXSL(List<Setting> sqls, boolean ishavezimoban) {
		File xsl = null;
		try {
			xsl = File.createTempFile("xsl", ".tmp");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		Document doc = SystemManager.getCurruentDocument();
		DataStructureTreeModel model = doc.getDataStructure();
		com.wisii.db.Setting docsetting = model != null ? model
				.getDbsetting() : null;
		Setting setting = docsetting != null ? new Setting(docsetting
				.getSqlitem(), null) : null;
		if (setting != null) {
			sqls.add(setting);
		}
		if (ishavezimoban) {
			try {
				deal(doc, xsl.getName(), new FileOutputStream(xsl), sqls);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else {
			try {
				IOFactory.getWriter(IOFactory.XSL).write(
						new FileOutputStream(xsl), doc);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return new FileItem(xsl, true);
	}

	

	public static String URLEncode(String text) {
		StringBuffer StrUrl = new StringBuffer();
		for (int i = 0; i < text.length(); ++i) {
			char c = text.charAt(i);
			// 空格用替代符进行替代
			if (c == ' ') {
				StrUrl.append("%20");
			} else {
				StrUrl.append(c);
			}
		}
		return StrUrl.toString();
	}

	private class BrowseThread extends Thread {

		private String command;

		private FileItem xslfileitem;
		private FileItem xmlfileitem;

		private BrowseThread(String command, FileItem xslfileitem,
				FileItem xmlfileitem) {
			this.command = command;
			this.xslfileitem = xslfileitem;
			this.xmlfileitem = xmlfileitem;
		}

		@Override
		public void run() {
			Process p = null;
			try {
				StringTokenizer st = new StringTokenizer(command);
				String[] cmdarray = new String[st.countTokens()];
				for (int i = 0; st.hasMoreTokens(); i++)
					cmdarray[i] = st.nextToken();
				ProcessBuilder pb = new ProcessBuilder(cmdarray);
				pb.directory(new File(System.getProperty("user.dir")));
				// 该方法使得错误输出流和普通输出流采用一个流，这样可以捕捉到错误消息，从而使得在有异常时也能预览
				pb.redirectErrorStream(true);
				p = pb.start();
				BufferedReader input = new BufferedReader(
						new InputStreamReader(p.getInputStream()));
				// 必须得度这个流，否则批处理程序不执行
				String line = input.readLine();
				while (line != null) {
					line = input.readLine();
				}
				input.close();
				if (xslfileitem.isIsneeddel()) {
					xslfileitem.getFile().delete();
				}
				if (xmlfileitem.isIsneeddel()) {
					xmlfileitem.getFile().delete();
				}
			} catch (Exception e1) {
				if (p != null) {
					p.destroy();
				}
				e1.printStackTrace();
			}
		}
	}



	private void deal(Document doc, String name, OutputStream stream,
			List<Setting> sqls) throws IOException {
		String rootpath = System.getProperty("user.dir");
		String subrootpath = rootpath + File.separator + "zimobans";
		String fucrootpath = rootpath + File.separator + "xslt";
		name = name.replaceFirst(".tmp", "");
		StringBuffer otherxsl = new StringBuffer();
		// 功能模板链表
		List<String> temp = new ArrayList<String>();
		MainXSLWriter mw = new MainXSLWriter();
		// 主模版的头和内容部分
		mw.writeNoClose(stream, doc, name);
		// 子模板
		String subs = mw.getZimobannames();
		String[] subsarry = subs.toString().split(",");
		if (subsarry.length > 0) {
			for (String current : subsarry) {
				String currentzh = current;
				if (current.contains("_wisiifs_")) {
					String[] paths = current.split("_wisiifs_");
					currentzh = "";
					for (int i = 0; i < paths.length; i++) {
						currentzh += paths[i];
						if (i < paths.length - 1) {
							currentzh += File.separator;
						}
					}
				}
				if (currentzh!=null&&!currentzh.isEmpty()) {
					try {
						dealSub(subrootpath + File.separator + currentzh
								+ ".wsdx", current, otherxsl, sqls, temp);
					} catch (ZimobanFileNotFoundException e) {
						throw new ZimobanFileNotFoundException(current);
					}
				}
			}
		}
		DataStructureTreeModel ds=doc.getDataStructure();
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
		byte[] subbytes = otherxsl.toString().getBytes("UTF-8");
		stream.write(subbytes);
		String datasourceanddecimal = mw.getDatasource();
		byte[] ddbytes = datasourceanddecimal.getBytes("UTF-8");
		stream.write(ddbytes);
		String names = mw.getFunctiontemplate();
		String[] namesarry = names.split(",");
		if (namesarry.length > 0) {
			for (String current : namesarry) {
				if (!current.isEmpty() && !temp.contains(current)) {
					temp.add(current);
				}
			}
		}
		if (!temp.isEmpty()) {
			int size = temp.size();
			for (int i = 0; i < size; i++) {
				String profileName = temp.get(i);
				String filePath = fucrootpath + File.separator + profileName
						+ ".xml";
				InputStream isr = new FileInputStream(filePath);
				int c = 0;
				while ((c = isr.read()) != -1) {
					stream.write(c);
				}
				isr.close();
			}
		}
		String end = "</xsl:stylesheet>";
		byte[] endb = end.getBytes("UTF-8");
		stream.write(endb);
		stream.close();
	}

	/**
	 * 
	 * 判断指定的对象是否为Null
	 * 
	 * @param o
	 *            指定被判断的对象
	 * @return boolean 如果o==null：True否则：False
	 * @throws ZimobanFileNotFoundException
	 */
	private void dealSub(String filepath, String subname, StringBuffer xsl,
			List<Setting> sqls, List<String> temp)
			throws ZimobanFileNotFoundException {
		FileInputStream fileinsub = null;
		File zifile = new File(filepath);
		try {
			fileinsub = new FileInputStream(zifile);
			ZipInputStream zipsub = new ZipInputStream(fileinsub);
			ZipEntry entrysub = zipsub.getNextEntry();
			try {
				for (; entrysub != null; entrysub = zipsub.getNextEntry()) {
					if (entrysub.isDirectory()) {
						continue;
					}
					if (WSDXKind.XSLTEMPLATE.getName().equals(
							entrysub.getName())) {
						InputStreamReader isr = new InputStreamReader(zipsub,
								"UTF-8");
						int c = 0;
						while ((c = isr.read()) != -1) {
							xsl.append((char) c);
						}
					} else if (WSDXKind.SQL.getName()
							.equals(entrysub.getName())) {
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						int c = -1;
						byte[] reads = new byte[1024];
						while ((c = zipsub.read(reads)) != -1) {
							out.write(reads, 0, c);
						}
						ByteArrayInputStream in = new ByteArrayInputStream(out
								.toByteArray());
						sqls.add(getSetting(in, subname));
						in.close();
					} else if (WSDXKind.DATASOURCE.getName().equals(
							entrysub.getName())) {
						InputStreamReader isr = new InputStreamReader(zipsub,
								"UTF-8");
						int c = 0;
						while ((c = isr.read()) != -1) {
							xsl.append((char) c);
						}
					} else if (WSDXKind.FCTEMPLATE.getName().equals(
							entrysub.getName())) {
						StringBuffer names = new StringBuffer();
						InputStreamReader isr = new InputStreamReader(zipsub,"UTF-8");
						int c = 0;
						while ((c = isr.read()) != -1) {
							names.append((char) c);
						}
						String[] namesarry = names.toString().split(",");
						if (namesarry.length > 0) {
							for (String current : namesarry) {
								if (current!=null&&!current.isEmpty()
										&& !temp.contains(current)) {
									temp.add(current);
								}
							}
						}
					}
					else if (WSDXKind.NAMESPACE.getName().equals(
							entrysub.getName())) {
						StringBuffer names = new StringBuffer();
						BufferedReader isr = new BufferedReader(new InputStreamReader(zipsub,"UTF-8"));
						String line;
						while((line=isr.readLine())!=null)
						{
							String[] s=line.split("=");
							if(s.length==2)
							{
								IoXslUtil.addNameSpace(new NameSpace(s[0], s[1]));
							}
						}
					}
					zipsub.closeEntry();
				}
			} finally {
				zipsub.close();
				fileinsub.close();
			}
		} catch (FileNotFoundException e) {
			throw new ZimobanFileNotFoundException(subname);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



}
