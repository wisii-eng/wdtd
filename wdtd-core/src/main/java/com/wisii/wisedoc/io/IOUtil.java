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
 * @IOUtil.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io;

import java.io.File;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.configure.RecentOpenFile;
import com.wisii.wisedoc.configure.RecentOpenModel;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.ZiMoban;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.WiseDocOptionPane;
import com.wisii.wisedoc.util.WisedocUtil;

/**
 * 类功能描述：输入输出工具类，提供数据加密等工具服务
 * 
 * 作者：zhangqiang 创建日期：2008-9-17
 */
public class IOUtil
{

	/**
	 * 定义模板zip文件中需保存文件的种类及保存的名称
	 * 
	 * @author 李晓光 2009-4-2
	 */
	public static enum FileKind
	{
		ICON(FileKind.ICON_NAME), WSD(FileKind.WSD_NAME);

		/**
		 * 获得当前类型的文件名
		 * 
		 * @return {@link String} 返回该类型文件的文件名
		 */
		public String getName()
		{
			return this.name;
		}

		/**
		 * 获得图像类型
		 * 
		 * @return {@link String} 返回图像类型
		 */
		public String getImageType()
		{
			return imageType;
		}

		private FileKind(String name)
		{
			this.name = name;
		}

		/* 定义wsd文件放置的位置、文件名称 */
		private final static String WSD_NAME = "Document/document.wsd";

		/* 定义图标类型 */
		private final static String SUFFIX = "JPG";

		/* 定义图片的放置位置、文件名称。 */
		private final static String ICON_NAME = "image/icon." + SUFFIX;

		/* 定义图片类型 */
		private String imageType = SUFFIX;

		/* 定义文件名称 */
		private String name = "";
	}

	/**
	 * 
	 * 加密数据 目前只是简单的返回原始数据，需要加密时可修改该方法
	 * 
	 * @param bs
	 *            :加密前的数据
	 * @return 加密后的数据
	 * @exception
	 */
	public static byte[] encrypt(byte[] bs)
	{
		return bs;
	}

	/**
	 * 
	 * 解密数据 目前只是简单的返回原始数据，需要解密时可修改该方法
	 * 
	 * @param bs
	 *            :解密前的数据
	 * @return 解密后的数据
	 * @exception
	 */
	public static byte[] decrypt(byte[] bs)
	{
		return bs;
	}

	/**
	 * 
	 * 对源字符串坐特殊处理，以生成符合XMl标准得字符串 替换掉里面得特殊字符（“&”，“<”等五个特殊字符）
	 * 
	 * @param src
	 *            ：要转换得字符串
	 * @return 符合XMl要求得字符串
	 * @exception
	 */
	public static String getXmlText(String src)
	{
		return XMLUtil.getXmlText(src);
	}

	/**
	 * 
	 * 从xml字符串转成内部字符串
	 * 
	 * @param src
	 *            ：要转换得字符串
	 * @return 符合XMl要求得字符串
	 * @exception
	 */
	public static String fromXmlText(String src)
	{
		return XMLUtil.fromXmlText(src);
	}

	public static boolean isHaveNormalDataStructure(Document document)
	{
		DataStructureTreeModel oldmodel = document.getDataStructure();
		if (oldmodel != null)
		{
			return oldmodel.getDbsetting() == null;
		}
		return false;
	}

	public static boolean isHaveSqlDataStructure(Document document)
	{
		DataStructureTreeModel oldmodel = document.getDataStructure();
		if (oldmodel != null)
		{
			return oldmodel.getDbsetting() != null;
		}
		return false;
	}

	public static boolean isHaveDataStructure(Document document)
	{
		DataStructureTreeModel oldmodel = document.getDataStructure();
		return oldmodel != null;
	}

	public static boolean savaFile(Document doc, File file)
	{
		if (!WisedocUtil.canWrite(file))
		{
			WiseDocOptionPane.showMessageDialog(SystemManager.getMainframe(),
					MessageResource.getMessage(MessageConstants.FILE
							+ MessageConstants.FILECANOTWRITE), MessageResource
							.getMessage(MessageConstants.DIALOG_COMMON
									+ MessageConstants.INFORMATIONTITLE),
					WiseDocOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		String savepath = file.getAbsolutePath();
		try
		{
			if (savepath.endsWith(".wsd"))
			{
				if (IOFactory.ifHaveZimoban())
				{
					WiseDocOptionPane.showMessageDialog(SystemManager
							.getMainframe(), MessageResource
							.getMessage(MessageConstants.CANT_HAVE_ZIMOBAN),
							"", WiseDocOptionPane.INFORMATION_MESSAGE);
					return false;
				} else
				{
					IOFactory.getWriter(IOFactory.WSD).write(savepath, doc);
				}
			} else if (savepath.endsWith(".wsdt"))
			{
				if (IOFactory.ifHaveZimoban())
				{
					WiseDocOptionPane.showMessageDialog(SystemManager
							.getMainframe(), MessageResource
							.getMessage(MessageConstants.CANT_HAVE_ZIMOBAN),
							"", WiseDocOptionPane.INFORMATION_MESSAGE);
					return false;
				} else
				{
					IOFactory.getWriter(IOFactory.WSDT).write(savepath, doc);
				}
			} else if (savepath.endsWith(".wsdx"))
			{
				if (savepath.startsWith(ZiMoban.PATH)) {
					if (IOFactory.ifHaveZimoban()) {
						WiseDocOptionPane
								.showMessageDialog(
										SystemManager.getMainframe(),
										MessageResource
												.getMessage(MessageConstants.CANT_HAVE_ZIMOBAN),
										"",
										WiseDocOptionPane.INFORMATION_MESSAGE);
						return false;
					} else {
						IOFactory.getWriter(IOFactory.WSDX)
								.write(savepath, doc);
					}
				} else {
					WiseDocOptionPane
							.showMessageDialog(
									SystemManager.getMainframe(),
									MessageResource
											.getMessage(MessageConstants.FILE_ONLY_IN_ZIMOBANS),
									"", WiseDocOptionPane.INFORMATION_MESSAGE);
					return false;
				}
			} else if (savepath.endsWith(".wsdm"))
			{

					IOFactory.getWriter(IOFactory.WSDM).write(savepath, doc);
			}
			if (savepath.endsWith(".wsd") || savepath.endsWith(".wsdx")
					|| savepath.endsWith(".wsdm") || savepath.endsWith(".wsdt"))
			{
				doc.setSavePath(savepath);
				if (savepath.endsWith(".wsdt") && RecentOpenModel.isLoadROF())
				{
					RecentOpenModel.addOpenFile(savepath);
				}
				if (RecentOpenFile.isLoadROF())
				{
					RecentOpenFile.addOpenFile(savepath);
				}
			}
		} catch (Exception e1)
		{
			WiseDocOptionPane.showMessageDialog(SystemManager.getMainframe(),
					MessageResource.getMessage(MessageConstants.FILE
							+ MessageConstants.FILESAVEFAILED), MessageResource
							.getMessage(MessageConstants.DIALOG_COMMON
									+ MessageConstants.INFORMATIONTITLE),
					WiseDocOptionPane.INFORMATION_MESSAGE);
			LogUtil.debugException("文件保存不成功", e1);
			return false;
		}
		return true;
	}

	public static boolean isExists(File file, String houzhui)
	{
		boolean result = false;
		if (file == null)
		{
			result = false;
		} else if (file.isDirectory())
		{
			result = false;
		} else if (file.exists())
		{
			result = true;
		} else
		{
			String fileName = file.getAbsolutePath() + "." + houzhui;
			if (new File(fileName).exists())
			{
				result = true;
			} else
			{
				result = false;
			}
		}
		return result;
	}
}
