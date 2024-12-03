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
 * @PasteWithOutBindNode.java
 * 北京汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.AbstractGraphics;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.DocumentUtil;
import com.wisii.wisedoc.document.Group;
import com.wisii.wisedoc.document.ImageInline;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.Text;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.ClipboardSupport;
import com.wisii.wisedoc.swing.ImageFileFilter;
import com.wisii.wisedoc.swing.WiseDocOptionPane;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.view.select.DocumentPositionRange;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：
 *
 * 作者：zhangqiang
 * 创建日期：2010-12-28
 */
public class PasteWithOutBindNode extends BaseAction
{

	/**
	 * 
	 * 用默认的字符串和默认的图标定义一个Action对象。
	 */
	public PasteWithOutBindNode()
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
	public PasteWithOutBindNode(final String name)
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
	public PasteWithOutBindNode(final String name, final Icon icon)
	{
		super(name, icon);
	}

	/**
	 * 
	 * 粘贴菜单Action接口方法实现
	 * 
	 * @param ActioinEvent
	 *            事件源对数据的封装。
	 * @return void 无返回值
	 */
	@Override
	public void doAction(final ActionEvent e)
	{
		final Document doc = getCurrentDocument();
		if (doc == null)
		{
			return;
		}
		if (!ClipboardSupport.hasData())
		{
			return;
		}
		final DocumentPositionRange range = getSelect();
		DocumentPosition pos = getCaretPosition();
		final List<CellElement> elements = ClipboardSupport
				.readElementsFromClipboardWithoutBindNode();
		if (elements != null && !elements.isEmpty())
		{
			// 标识是否是inline级别的元素
			boolean isinlinecontent = true;
			for (CellElement element : elements)
			{

				if (!(element instanceof Inline)
						&& !DocumentUtil.isInlineGroup(element))
				{
					isinlinecontent = false;
				}
			}
			// 如果有非inline级别的元素，则需要判断是在在一个inline所在的组中
			if (!isinlinecontent)
			{
				if (range != null)
				{
					DocumentPosition startpos = range.getStartPosition();
					CellElement startleaf = startpos.getLeafElement();
					// 如果当前选中范围的起始点在一个Inline重复组中，则不能粘贴元素
					if (startleaf instanceof Inline
							&& startleaf.getParent() instanceof Group)
					{
						SystemManager.getMainframe().setStatus(
								RibbonUIText.FRAME_STATUS_UNABLE_PASTE);
						return;
					}
					DocumentPosition endpos = range.getEndPosition();
					CellElement endleaf = endpos.getLeafElement();
					// 如果当前选中范围的结束点在一个Inline重复组中，则不能粘贴元素
					if (endleaf instanceof Inline
							&& endleaf.getParent() instanceof Group)
					{
						SystemManager.getMainframe().setStatus(
								RibbonUIText.FRAME_STATUS_UNABLE_PASTE);
						return;
					}
				} else if (pos != null)
				{
					CellElement leafelement = pos.getLeafElement();
					// 如果当前光标所表示的元素在一个Group类，则表示时一个Inline
					// Group，此时不容许输入非Inline级别的元素
					if (leafelement instanceof Inline
							&& leafelement.getParent() instanceof Group)
					{
						SystemManager.getMainframe().setStatus(
								RibbonUIText.FRAME_STATUS_UNABLE_PASTE);
						return;
					}
				}
			}
			if (range != null)
			{
				doc.replaceElements(range, elements);
				getEditorPanel().getSelectionModel().clearSelection();
			} else if (pos != null)
			{
				doc.insertElements(elements, pos);
				getEditorPanel().getSelectionModel().clearSelection();
			}

		} else
		{
			String text = ClipboardSupport.readTextFromClipboard();
			if (text != null && !text.isEmpty())
			{
				if (text.contains("\n"))
				{
					if (range != null)
					{
						DocumentPosition startpos = range.getStartPosition();
						CellElement startleaf = startpos.getLeafElement();
						// 如果当前选中范围的起始点在一个Inline重复组中，则不能粘贴文本
						if (startleaf instanceof Inline
								&& startleaf.getParent() instanceof Group)
						{
							SystemManager.getMainframe().setStatus(
									RibbonUIText.FRAME_STATUS_UNABLE_INPUT);
							return;
						}
						DocumentPosition endpos = range.getEndPosition();
						CellElement endleaf = endpos.getLeafElement();
						// 如果当前选中范围的结束点在一个Inline重复组中，则不能粘贴文本
						if (endleaf instanceof Inline
								&& endleaf.getParent() instanceof Group)
						{
							SystemManager.getMainframe().setStatus(
									RibbonUIText.FRAME_STATUS_UNABLE_INPUT);
							return;
						}
					} else if (pos != null)
					{
						CellElement leafelement = pos.getLeafElement();
						// 如果当前光标所表示的元素在一个Group类，则表示时一个Inline
						// Group，此时不容许输入回车
						if (leafelement instanceof Inline
								&& leafelement.getParent() instanceof Group)
						{
							SystemManager.getMainframe().setStatus(
									RibbonUIText.FRAME_STATUS_UNABLE_INPUT);
							return;
						}
					}

				}
				if (range != null)
				{
					final List<CellElement> elemt = new ArrayList<CellElement>();
					final int size = text.length();
					Attributes att = null;
					final CellElement element = range.getStartPosition()
							.getLeafElement();
					if (element instanceof Inline)
						att = element.getAttributes();
					for (int i = 0; i < size; i++)
					{
						elemt.add(new TextInline(new Text(text.charAt(i)), att
								.getAttributes()));
					}
					doc.replaceElements(range, elemt);
					getEditorPanel().getSelectionModel().clearSelection();
				} else if (pos != null)
				{

					final CellElement element = pos.getLeafElement();
					Attributes att = null;
					if (element instanceof Inline)
						att = element.getAttributes();
					Map<Integer, Object> attmap = null;
					if (att != null)
						attmap = att.getAttributes();
					doc.insertString(text, pos, attmap);
					getEditorPanel().getSelectionModel().clearSelection();
				}
			} else
			{
				// 粘贴图片
				Image image = ClipboardSupport.readImageFromClipboard();
				if (image != null)
				{
					saveImage(image, doc);
				}
			}
		}
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	private void saveImage(Image image, Document doc)
	{
		int outputWidth = image.getWidth(null);
		if (outputWidth < 1)
		{
			LogUtil.getlogger().info(
					"output image width " + outputWidth + " is out of range");
			return;
		}
		int outputHeight = image.getHeight(null);
		if (outputHeight < 1)
		{
			LogUtil.getlogger().info(
					"output image height " + outputHeight + " is out of range");
			return;
		}
		File openFile = DialogSupport.getFileDialog(
				DialogSupport.FileDialogType.open, new ImageFileFilter());
		if (openFile != null)
		{
			String path = openFile.getPath();
			String imageroot = System.getProperty("user.dir") + File.separator
					+ "graphics" + File.separator;
			String src;
			if (path.startsWith(imageroot))
			{
				src = path.substring(imageroot.length());
			} else
			{
				src = openFile.getName();
			}
			// 图片格式
			String formate = "jpg";
			int indexdian = src.lastIndexOf('.');
			if (indexdian > -1)
			{
				String end = src.substring(indexdian + 1, src.length())
						.toLowerCase();
				boolean isendright = false;
				String[] suffixs =
				{ "jpg", "bmp", "jpeg", "gif", "tif", "png" };
				for (String suffix : suffixs)
				{
					if (suffix.equals(end))
					{
						isendright = true;
						break;
					}
				}
				// 如果不是图片文件的后缀，则默认生成jpg文件
				if (!isendright)
				{
					src = src.substring(0, indexdian + 1) + formate;
				}
			} else
			{
				src = src + "." + formate;
			}
			File savefile = new File(imageroot + src);
			if (savefile.exists())
			{
				if (!savefile.canWrite())
				{
					WiseDocOptionPane
							.showConfirmDialog(
									SystemManager.getMainframe(),
									MessageResource
											.getMessage(MessageConstants.FILE
													+ MessageConstants.FILECANOTWRITE),
									MessageResource
											.getMessage(MessageConstants.DIALOG_COMMON
													+ MessageConstants.INFORMATIONTITLE),
									WiseDocOptionPane.INFORMATION_MESSAGE);
					return;
				}
				int result = WiseDocOptionPane.showConfirmDialog(SystemManager
						.getMainframe(), MessageResource
						.getMessage(MessageConstants.FILE
								+ MessageConstants.WHETHERREPLACEFILE));
				if (result != WiseDocOptionPane.OK_OPTION)
				{
					return;
				}

			}
			BufferedImage bi = new BufferedImage(outputWidth, outputHeight,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D biContext = bi.createGraphics();
			biContext.drawImage(image, 0, 0, outputWidth, outputHeight, null);
			try
			{
				ImageIO.write(bi, formate, savefile);
			} catch (IOException e)
			{
				e.printStackTrace();
				return;
			}
			ImageInline imagein = new ImageInline(src);
			List<CellElement> inserts = new ArrayList<CellElement>();
			inserts.add(imagein);
			insertImage(doc, inserts);

		}
	}

	private void insertImage(Document doc, List<CellElement> inserts)
	{
		DocumentPositionRange range = getSelect();
		if (range != null)
		{
			doc.replaceElements(range, inserts);
		} else
		{
			DocumentPosition pos = getCaretPosition();
			if (pos == null)
			{
				List<CellElement> objselects = getObjectSelects();
				if (objselects != null && !objselects.isEmpty())
				{
					CellElement element = objselects.get(objselects.size() - 1);
					if (element instanceof AbstractGraphics)
					{
						element = (CellElement) element.getParent();
						if (element != null)
						{
							pos = new DocumentPosition(element, true);
						}
					} else if (element instanceof Inline)
					{
						pos = new DocumentPosition(element, true);
					}
				}

			}
			if (pos != null)
			{
				doc.insertElements(inserts, pos);
			}
		}
	}

	@Override
	public boolean isAvailable()
	{
		if (!super.isAvailable())
			return false;
		return ClipboardSupport.hasElements();
		/*
		 * String text = ClipboardSupport.readFromClipboard();
		 * if(text==null||text.isEmpty()) { return false; } return true;
		 */
	}
}
