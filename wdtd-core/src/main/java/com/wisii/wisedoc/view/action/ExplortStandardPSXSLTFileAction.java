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

package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import sun.misc.BASE64Decoder;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.io.IOFactory;
import com.wisii.wisedoc.io.xsl.util.IoXslUtil;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.WiseDocFileChooser;
import com.wisii.wisedoc.swing.WiseDocFileFilter;
import com.wisii.wisedoc.swing.WiseDocOptionPane;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;

public class ExplortStandardPSXSLTFileAction extends BaseAction
{

	public ExplortStandardPSXSLTFileAction()
	{
		super();
	}

	/**
	 * 
	 * 用指定描述字符串和默认图标定义一个 Action 对象。
	 * 
	 * @param name
	 *            指定Action的名字。
	 */
	public ExplortStandardPSXSLTFileAction(String name)
	{
		super(name);
	}

	/**
	 * 
	 * 用指定描述字符串指定图标定义一个 Action 对象。
	 * 
	 * @param name
	 *            指定Action的名字。
	 * @param icon
	 *            指定Action的图标。
	 */
	public ExplortStandardPSXSLTFileAction(String name, Icon icon)
	{
		super(name, icon);
	}

	/**
	 * 到处xslt模版文件事件接口
	 */
	public void doAction(ActionEvent e)
	{
		if (!V())
		{
			return;
		}
		DocumentPosition dp = getCaretPosition();
		CellElement elements = dp != null ? dp.getLeafElement() : null;
		if (elements == null)
		{
			List<CellElement> current = getAllSelectObjects();
			if (current != null && !current.isEmpty())
			{
				elements = current.get(0);
			}
		}
		while (!(elements instanceof PageSequence))
		{
			elements = (CellElement) elements.getParent();
		}
		List<PageSequence> pageSequence = new ArrayList<PageSequence>();
		pageSequence.add((PageSequence) elements);
		Document currentDocument = new WiseDocDocument(pageSequence);
		Attributes docattr = SystemManager.getCurruentDocument()
				.getAttributes();
		Map<Integer, Object> docmap = docattr != null ? docattr.getAttributes()
				: null;
		currentDocument.setAttributes(docmap, true);
		exlportxsl(currentDocument);
	}

	/**
	 * 
	 * 另存为Action的实现
	 * 
	 * @param page
	 *            指定要保存的对象。
	 * @return void 无返回值。
	 * 
	 */
	public void exlportxsl(Document lay)
	{
		final WiseDocFileChooser chooser = (WiseDocFileChooser) DialogSupport
				.getDialog(new WiseDocFileFilter("xsl", "xsl"));
		chooser.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent evt)
			{
				if (JFileChooser.APPROVE_SELECTION.equals(evt
						.getActionCommand()))
				{
					chooser.setResult(DialogResult.OK);
					if (isExists(chooser.getSelectedFile()))
					{
						int result = JOptionPane
								.showConfirmDialog(
										chooser,
										MessageResource
												.getMessage(MessageConstants.FILE
														+ MessageConstants.WHETHERREPLACEFILE),
										MessageResource
												.getMessage(MessageConstants.FILE
														+ MessageConstants.FILEEXISTED),
										JOptionPane.YES_NO_OPTION);
						if (result != JOptionPane.YES_OPTION)
							return;
					}
				} else if (JFileChooser.CANCEL_SELECTION.equals(evt
						.getActionCommand()))
				{
					chooser.setResult(DialogResult.Cancel);
				}
				chooser.distroy();
			}
		});
		DialogResult result = chooser.showDialog(JFileChooser.SAVE_DIALOG);
		if (result == DialogResult.OK)
		{
			IoXslUtil.setStandard(true);
			String filename = "";
			filename = chooser.getSelectedFile().getAbsolutePath();
			if (!filename.endsWith(".xsl"))
			{
				filename += ".xsl";
			}
			try
			{
				IOFactory.getWriter(IOFactory.XSLPS).write(filename, lay);
			} catch (FileNotFoundException e)
			{
				LogUtil.debugException("出错", e);
			} catch (IOException e)
			{
				LogUtil.debugException("出错", e);
			}
		}
	}

	private boolean isExists(File file)
	{
		boolean result = false;
		if (file == null)
			result = false;
		else if (file.isDirectory())
			result = false;
		else if (file.exists())
			result = true;
		else
		{
			String fileName = file.getAbsolutePath() + ".xsl";
			if (new File(fileName).exists())
				result = true;
			else
				result = false;
		}
		return result;
	}

	public boolean isAvailable()
	{

		if (!super.isAvailable())
		{
			return false;
		}
		CellElement elements = getCaretPosition() == null ? null
				: getCaretPosition().getLeafElement();
		if (elements != null)
		{
			while (!(elements instanceof PageSequence))
			{
				elements = (CellElement) elements.getParent();
			}
		}
		if (elements != null && elements instanceof PageSequence)
		{

			return true;
		}

		if (isSelected())
		{
			return true;
		}
		return false;
	}

	/**
	 * 
	 *判断许可文件是否存在
	 * 
	 * @param
	 * @return true：表示许可文件存在，false：表示许可文件不存在
	 * @exception
	 */
	private static boolean IE()
	{
		File c = new File("license/license.lic");
		if (c != null && c.exists())
		{
			return true;
		}
		return false;
	}

	/*
	 * 
	 * 开始校验前的初始化方法
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @exception
	 */
	private static boolean VL()
	{
		FileInputStream fileinstream;
		try
		{

			File file = new File("license/license.lic");
			long ct = System.currentTimeMillis();
			long lt = file.lastModified();
			if (ct < lt)
			{
				WiseDocOptionPane.showMessageDialog(null, "请不要修改系统时间");
				return false;
			} else
			{
				File[] diskfs = File.listRoots();
				if (diskfs != null && diskfs.length > 1)
				{
					File[] childrenfs = diskfs[0].listFiles();
					if (childrenfs != null)
					{
						for (int i = 0; i < childrenfs.length; i++)
						{
							if (ct < childrenfs[i].lastModified())
							{
								WiseDocOptionPane.showMessageDialog(null,
										"请不要修改系统时间");
								file.setLastModified(childrenfs[i]
										.lastModified());
								return false;
							}
						}
					}
				}
				file.setLastModified(ct);
			}
			fileinstream = new FileInputStream(file);
			ZipInputStream wwd = new ZipInputStream(fileinstream);
			byte[] licensedatas = null;
			byte[] signdatas = null;
			byte[] keydatas = null;
			for (ZipEntry hjkj = wwd.getNextEntry(); hjkj != null; hjkj = wwd
					.getNextEntry())
			{
				if (hjkj.getName().equals("sign"))
				{
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					int i = wwd.read();
					while (i != -1)
					{
						out.write(i);
						i = wwd.read();
					}
					licensedatas = out.toByteArray();

				} else if (hjkj.getName().equals("license"))
				{
					BASE64Decoder fdsdf = new BASE64Decoder();
					signdatas = fdsdf.decodeBuffer(wwd);
				} else if (hjkj.getName().equals("key"))
				{
					BASE64Decoder rtrty = new BASE64Decoder();
					keydatas = rtrty.decodeBuffer(wwd);
				} else
				{
					return false;
				}
				wwd.closeEntry();
			}
			wwd.close();
			if (!CS(licensedatas, signdatas, keydatas))
			{
				return false;
			}
			return po(D(licensedatas));
		} catch (Exception e)
		{
			LogUtil.debugException("出错", e);
			return false;
		}
	}

	private static boolean CS(byte[] wds, byte[] sdsds, byte[] sdadasda)
	{
		if (wds == null || sdsds == null || sdadasda == null)
		{
			return false;
		}
		X509EncodedKeySpec sdsdw = new X509EncodedKeySpec(sdadasda);
		try
		{
			KeyFactory a = KeyFactory.getInstance("RSA");
			PublicKey s = a.generatePublic(sdsdw);
			Signature d = Signature.getInstance("MD5withRSA");
			d.initVerify(s);
			d.update(wds);
			return d.verify(sdsds);

		} catch (Exception e)
		{
			LogUtil.debugException("出错", e);
			return false;
		}

	}

	private static byte[] D(byte[] wew)
	{
		try
		{
			Cipher q = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
			byte[] sasas = Arrays.copyOfRange(wew, 0, 16);
			byte[] wwew = Arrays.copyOfRange(wew, 16, wew.length);
			/* ------------------------------------------------------------- */
			SecretKeySpec qqwq = new SecretKeySpec(sasas, "AES");
			q.init(Cipher.DECRYPT_MODE, qqwq);
			return q.doFinal(wwew);
		} catch (Exception e)
		{
			LogUtil.debugException("出错", e);
			return null;
		}
	}

	private static boolean po(byte[] datas)
	{
		try
		{
			org.dom4j.Document a = DocumentHelper.parseText(new String(datas,
					"utf-8"));
			Element w = a.getRootElement();
			int q = w.nodeCount();
			if (q < 3)
			{
				return false;
			}
			String ssdsdid = w.attributeValue("pcifd");
			if (!"ESAQ".equals(ssdsdid))
			{
				return false;
			}
			for (int i = 0; i < q; i++)
			{
				Node r = w.node(i);
				if (r instanceof Element)
				{
					Element x = (Element) r;
					String nodename = x.getName();
					if ("ueerrel".equals(nodename))
					{
						// donothing
						// username = element.getAttribute("name");
						// if (username == null)
						// {
						// return false;
						// }
					} else if ("hsdsdsw".equals(nodename))
					{
						String uynsg = x.attributeValue("uoopdsopso");
						if (uynsg == null || !uynsg.equals("iuipi"))
						{
							String sda = x.attributeValue("wdewsds");
							if (sda == null
									|| !sda.equals(HardWareInfo.asdasdw()))
							{
								return false;
							}
							String wewe = x.attributeValue("asdsdsm");
							if (wewe == null
									|| !wewe.equals(HardWareInfo.asdwe()))
							{
								return false;
							}
							String zsdsd = x.attributeValue("ggdspp");
							if (zsdsd == null
									|| !zsdsd.equals(HardWareInfo.tytyuyu()))
							{
								return false;
							}
						}
					} else if ("pasdssds".equals(nodename))
					{
						String yuyu = x.attributeValue("iopmsy");
						try
						{
							long xsdswa = Long.parseLong(yuyu);
							if (xsdswa > 0l
									&& System.currentTimeMillis() > xsdswa)
							{
								WiseDocOptionPane.showMessageDialog(null,
										"软件许可已到期，请申请新的许可");
								return false;
							}
							String dsd = x.attributeValue("asdsdszxs");
							long dsdas = Long.parseLong(dsd);
							if (System.currentTimeMillis() < dsdas)
							{
								WiseDocOptionPane.showMessageDialog(null,
										"请不要修改系统时间");
								return false;
							}

						} catch (Exception e)
						{
							LogUtil.debugException("出错", e);
							return false;
						}
					} else if ("weiwntsdg".equals(nodename))
					{

					} else
					{
						return false;
					}
				} else
				{
					return false;
				}
			}
			return true;
		} catch (Exception e)
		{
			LogUtil.debugException("出错", e);
			return false;
		}
	}

	private static final boolean AD(String data1, String data2)
	{
		if (data1 == null || data2 == null)
		{
			return false;
		}
		if (data2.equals(E(data1)))
		{
			return true;
		} else
		{
			return false;
		}
	}

	private static String E(String data1)
	{
		try
		{
			MessageDigest dfdsdf = MessageDigest.getInstance("MD5");
			return new String(dfdsdf.digest(data1.getBytes()));
		} catch (NoSuchAlgorithmException e)
		{
			LogUtil.debugException("出错", e);
			return null;
		}
	}

	private static boolean V()
	{
		if (!IE())
		{
			WiseDocOptionPane.showMessageDialog(null, "需要授权才能进行该操作，请向北京汇智互联(www.wisii.com)申请许可");
			return false;
		}
		return VL();
	}

	static final class HardWareInfo
	{

		static String asdasdw()
		{
			return "testdiskid";
		}

		static String asdwe()
		{
			String sas = System.getProperty("os.name");
			try
			{
				if (sas.startsWith("Windows"))
				{
					return asdsadsa(iuop());

				}

				else if (sas.startsWith("Linux"))

				{
					return fdew(hfssdfd());
				} else
				{
					throw new IOException("unknown   operating   system:   "
							+ sas);
				}
			} catch (Exception ex)
			{
				LogUtil.debugException("出错", ex);
				return "cannot get";
			}
		}

		static String tytyuyu()
		{
			return "testprocessorid";
		}

		/* Linux stuff */

		private final static String fdew(String sdsds) throws ParseException
		{
			StringTokenizer opoi = new StringTokenizer(sdsds, "\n");
			while (opoi.hasMoreTokens())
			{
				String line = opoi.nextToken().trim();
				int weqw = line.indexOf("HWaddr");
				if (weqw < 0)
					continue;
				String qqwq = line.substring(weqw + 6).trim();
				if (qqwq != null && !"".equals(qqwq))
				{
					return qqwq;
				}
			}
			ParseException ex = new ParseException(
					"cannot   read   MAC   address   for   " + "   from   ["
							+ sdsds + "]", 0);
			LogUtil.debugException("出错", ex);
			throw ex;
		}

		private final static String hfssdfd() throws IOException
		{
			Process p = Runtime.getRuntime().exec("/sbin/ifconfig");
			InputStream stdoutStream = new BufferedInputStream(p
					.getInputStream());
			StringBuffer buffer = new StringBuffer();
			for (;;)
			{
				int c = stdoutStream.read();
				if (c == -1)
					break;
				buffer.append((char) c);
			}
			String outputText = buffer.toString();
			stdoutStream.close();
			return outputText;
		}

		/* Windows stuff */

		private final static String asdsadsa(String sdsdsas)
				throws ParseException
		{
			StringTokenizer tokenizer = new StringTokenizer(sdsdsas, "\n");
			while (tokenizer.hasMoreTokens())
			{
				String line = tokenizer.nextToken().trim();
				if (line.startsWith("Physical Address"))
				{
					int sdswusa = line.indexOf(":");
					if (sdswusa <= 0)
						continue;
					String ugmkl = line.substring(sdswusa + 1).trim();
					if (ugmkl != null && !"".equals(ugmkl))
					{
						return ugmkl;
					}
				}
			}
			ParseException ex = new ParseException(
					"cannot   read   MAC   address   from   [" + sdsdsas + "]",
					0);
			LogUtil.debugException("出错", ex);
			throw ex;
		}

		private final static String iuop() throws IOException
		{
			Process p = Runtime.getRuntime().exec("ipconfig   /all");
			InputStream stdoutStream = new BufferedInputStream(p
					.getInputStream());
			StringBuffer buffer = new StringBuffer();
			for (;;)
			{
				int c = stdoutStream.read();
				if (c == -1)
					break;
				buffer.append((char) c);
			}
			String outputText = buffer.toString();
			stdoutStream.close();
			return outputText;
		}
	}
}
