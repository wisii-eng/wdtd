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
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFileChooser;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import sun.misc.BASE64Decoder;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.swing.WiseDocFileFilter;
import com.wisii.wisedoc.swing.WiseDocOptionPane;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.view.WisedocFrame;
public class LoadRegistrationDocumentAction extends BaseAction
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.action.BaseAction#doAction(java.awt.event.ActionEvent)
	 */
	@Override
	public void doAction(ActionEvent e)
	{

		File newfile = new File(System.getProperty("user.dir")
				+ "/license/license.lic");
		if (sqewreA(newfile, false))
		{
			int resout = WiseDocOptionPane.showConfirmDialog(SystemManager
					.getMainframe(), "原许可还在有效期内，是否替换？","重载许可",WiseDocOptionPane.OK_CANCEL_OPTION);
			if (resout != WiseDocOptionPane.OK_OPTION)
			{
				return;
			}
		}
		JFileChooser chooser = DialogSupport.getDialog(new WiseDocFileFilter(
				".lic", "lic"));
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		chooser.setAcceptAllFileFilterUsed(false);

		int dialogResult = chooser.showOpenDialog(SystemManager.getMainframe());
		if (dialogResult == JFileChooser.APPROVE_OPTION)
		{
			File file = chooser.getSelectedFile();
			if(file.equals(newfile))
			{
				return;
			}
			try
			{
				if (sqewreA(file, true))
				{
					write(new FileOutputStream(newfile), file);
					WisedocFrame.DEFAULT_TITLE = "Wisedoc Designer2.1";
					String oldtitle = SystemManager.getMainframe().getTitle();
					SystemManager.getMainframe().setTitle(
							oldtitle.replaceAll("-未经授权版本", ""));
					WiseDocOptionPane.showMessageDialog(SystemManager
							.getMainframe(), "导入许可文件成功");
				} else
				{
					WiseDocOptionPane.showMessageDialog(SystemManager
							.getMainframe(), "许可文件不正确，导入许可文件不成功");
				}
			} catch (IOException e1)
			{
			}
		}
	}

	public void write(OutputStream outstream, File file) throws IOException
	{
		ZipOutputStream zipout = new ZipOutputStream(outstream);
		ZipInputStream zipin = new ZipInputStream(new FileInputStream(file));
		ZipEntry entry = zipin.getNextEntry();
		try
		{
			for (; entry != null; entry = zipin.getNextEntry())
			{
				zipout.putNextEntry(entry);
				int b = zipin.read();
				while (b != -1)
				{
					zipout.write(b);
					b = zipin.read();
				}
				zipout.closeEntry();
			}
		} finally
		{
			zipin.close();
			zipout.close();
			outstream.close();
		}
	}

	/**
	 * 
	 * 判断许可文件是否存在
	 * 
	 * @param
	 * @return true：表示许可文件存在，false：表示许可文件不存在
	 * @exception
	 */
	private static boolean usosj(File c)
	{
		if (c != null && c.exists() && c.canRead())
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
	private static boolean posdusuds(File oij, boolean ggi)
	{
		FileInputStream qwwewe;
		try
		{

			long ct = System.currentTimeMillis();
			long lt = oij.lastModified();
			if (ct < lt)
			{
				if (ggi)
				{
					WiseDocOptionPane.showMessageDialog(null, "请不要修改系统时间");
				}
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
								if (ggi)
								{
									WiseDocOptionPane.showMessageDialog(null,
											"请不要修改系统时间");
								}
								oij.setLastModified(childrenfs[i]
										.lastModified());
								return false;
							}
						}
					}
				}
				oij.setLastModified(ct);
			}
			qwwewe = new FileInputStream(oij);
			ZipInputStream jyyd = new ZipInputStream(qwwewe);
			byte[] licensedatas = null;
			byte[] signdatas = null;
			byte[] keydatas = null;
			for (ZipEntry hjkj = jyyd.getNextEntry(); hjkj != null; hjkj = jyyd
					.getNextEntry())
			{
				if (hjkj.getName().equals("sign"))
				{
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					int i = jyyd.read();
					while (i != -1)
					{
						out.write(i);
						i = jyyd.read();
					}
					licensedatas = out.toByteArray();

				} else if (hjkj.getName().equals("license"))
				{
					BASE64Decoder fdsdf = new BASE64Decoder();
					signdatas = fdsdf.decodeBuffer(jyyd);
				} else if (hjkj.getName().equals("key"))
				{
					BASE64Decoder rtrty = new BASE64Decoder();
					keydatas = rtrty.decodeBuffer(jyyd);
				} else
				{
					return false;
				}
				jyyd.closeEntry();
			}
			jyyd.close();
			if (!utpoosj(licensedatas, signdatas, keydatas))
			{
				return false;
			}
			return ytrsrs(ewuuewyw(licensedatas), ggi);
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	private static boolean utpoosj(byte[] dsaw, byte[] dsaxs, byte[] dswe)
	{
		if (dsaw == null || dsaxs == null || dswe == null)
		{
			return false;
		}
		X509EncodedKeySpec dsgrt = new X509EncodedKeySpec(dswe);
		try
		{
			KeyFactory weew = KeyFactory.getInstance("RSA");
			PublicKey qass = weew.generatePublic(dsgrt);
			Signature uyt = Signature.getInstance("MD5withRSA");
			uyt.initVerify(qass);
			uyt.update(dsaw);
			return uyt.verify(dsaxs);

		} catch (Exception e)
		{
			return false;
		}

	}

	private static byte[] ewuuewyw(byte[] asdw)
	{
		try
		{
			Cipher wewa = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
			byte[] wewb = Arrays.copyOfRange(asdw, 0, 16);
			byte[] wwew = Arrays.copyOfRange(asdw, 16, asdw.length);
			/* ------------------------------------------------------------- */
			SecretKeySpec qqwq = new SecretKeySpec(wewb, "AES");
			wewa.init(Cipher.DECRYPT_MODE, qqwq);
			return wewa.doFinal(wwew);
		} catch (Exception e)
		{
			return null;
		}
	}

	private static boolean ytrsrs(byte[] datas, boolean iuii)
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
									|| !sda.equals(EardQareCn.asdasdw()))
							{
								return false;
							}
							String wewe = x.attributeValue("asdsdsm");
							if (wewe == null
									|| !wewe.equals(EardQareCn.asdwe()))
							{
								return false;
							}
							String zsdsd = x.attributeValue("ggdspp");
							if (zsdsd == null
									|| !zsdsd.equals(EardQareCn.tytyuyu()))
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
								if (iuii)
								{
									WiseDocOptionPane.showMessageDialog(null,
											"软件许可已到期，请申请新的许可");
								}
								return false;
							}
							String dsd = x.attributeValue("asdsdszxs");
							long dsdas = Long.parseLong(dsd);
							if (System.currentTimeMillis() < dsdas)
							{
								if (iuii)
								{
									WiseDocOptionPane.showMessageDialog(null,
											"请不要修改系统时间");
								}
								return false;
							}

						} catch (Exception e)
						{
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
			return false;
		}
	}

	private static String asasqsds(String data1)
	{
		try
		{
			MessageDigest dfdsdf = MessageDigest.getInstance("MD5");
			return new String(dfdsdf.digest(data1.getBytes()));
		} catch (NoSuchAlgorithmException e)
		{
			return null;
		}
	}

	private static boolean sqewreA(File c, boolean iii)
	{
		if (!usosj(c))
		{
			return false;
		}
		return posdusuds(c, iii);
	}

	static final class EardQareCn
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
