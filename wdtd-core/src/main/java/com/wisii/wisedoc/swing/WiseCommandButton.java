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

package com.wisii.wisedoc.swing;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.icon.ResizableIcon;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.configure.RecentOpenFile;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.io.IOFactory;
import com.wisii.wisedoc.io.Reader;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.view.action.SaveFileAction;

@SuppressWarnings("serial")
public class WiseCommandButton extends JCommandButton
{

	String file = "";

	int actionType = 0;

	public WiseCommandButton(int number, String url, ResizableIcon icon,
			int width, Font font, int action)
	{
		super(number + "  " + url, icon);
		setFile(url);
		setActionType(action);
		setRealText(url, width, font);
		addMouseListener(new MouseAdapter()
		{

			public void mouseReleased(MouseEvent event)
			{
				if (new File(file).exists())
				{
					if (actionType == 0)
					{
						int res = WiseDocOptionPane
								.showConfirmDialog(
										SystemManager.getMainframe(),
										MessageResource
												.getMessage(MessageConstants.FILE
														+ MessageConstants.FILEEXISTED));
						if (res == WiseDocOptionPane.OK_OPTION)
						{
							saveFile();
						}
					} else if (actionType == 1)
					{
						openFile();
					}

				} else
				{
					if (actionType == 0)
					{
						saveFile();
					} else if (actionType == 1)
					{
						WiseDocOptionPane.showMessageDialog(SystemManager
								.getMainframe(), "该文档不存在或已经被移动到其它位置");
						Properties recentOF = RecentOpenFile
								.getRecentopenfile();
						for (int i = 0; i < recentOF.size(); i++)
						{
							String urlName = recentOF
									.getProperty("RECENTFILEPATH" + i);
							if (getFile().equals(urlName))
							{
								recentOF.remove("RECENTFILEPATH" + i);
								break;
							}
						}
					}

				}

			}
		});
	}

	public void setRealText(String title, int width, Font font)
	{
		double panelWidth = ((Number) width).doubleValue();
		int number = getTextNumber(panelWidth, font) - 2;
		if (title.length() > number)
		{
			String text = returnPath(title, number);
			this.setText(text);
			setActionRichTooltip(new RichTooltip("完整路径", text));
		}
	}

	public int getTextNumber(double width, Font font)
	{
		TextLayout layout = null;
		String text = "";
		for (int i = 1; i < 1000; i++)
		{
			text = getString(i);
			layout = new TextLayout(text, font, new FontRenderContext(
					new AffineTransform(), false, false));
			double textWidth = layout.getBounds().getWidth();
			if (textWidth > width)
			{
				break;
			}
		}
		return layout.getCharacterCount();
	}

	// public static void main(String[] args)
	// {
	// JFrame frame = new JFrame("se dr");
	// frame.setSize(300, 600);
	// JPanel panel = new JPanel();
	// panel.setSize(300, 600);
	// String value =
	// "a bnd snj se dfslj ls dkjjk jlkljlkhadsjklfh k jkas hdfkjjk jaskjd fk kjaskd fjks  jkshfkasj k jkas djk j jask dfkjk kl asjkd kl k jk jaskd fhkas djklfjskdhf ask fk asjkf klsj   jadskl fklsjd jk adsjkask dfkj ask sdjakfkjadsl k as kf";
	// Font font = new Font("宋体", Font.PLAIN, 10);
	// TextLayout layout = new TextLayout(value, font, new FontRenderContext(
	// new AffineTransform(), false, false));
	// System.out.println("width:" + layout.getBounds().getWidth());
	// System.out.println("height:" + layout.getBounds().getHeight());
	// JTextArea area = new JTextArea(value);
	// area.setPreferredSize(new Dimension(200, 500));
	//
	// // area.setText(value);
	// panel.add(area);
	//
	// frame.add(panel);
	// frame.setVisible(true);
	// System.out.println("hang shu::" + area.getRows());
	// Composite parent=new Composite(); int style;
	// Canvas cv=new Canvas();
	// org.eclipse.swt.graphics.TextLayout lay = new
	// org.eclipse.swt.graphics.TextLayout(getDisplay());
	//
	// }

	public String getString(int length)
	{
		String text = "";
		if (length > 0)
		{
			for (int i = 0; i < length; i++)
			{
				text = text + "a";
			}
		}
		return text;
	}

	public String returnPath(String value, int except)
	{
		String[] paths = value.split("\\\\");
		int allLength = paths.length;
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		if (allLength > 1)
		{
			int halfof = allLength % 2 == 0 ? allLength / 2
					: (allLength + 1) / 2;
			for (int i = 0; i < halfof; i++)
			{
				if (i == 0)
				{
					map.put(0, paths[0]);
					map.put(allLength - 1, paths[allLength - 1]);
					int currentLengthL = paths[0].length();
					int currentLengthR = paths[allLength - 1].length();
					except = except - currentLengthL - currentLengthR - 1;
					if (except < 0)
					{
						break;
					}
				} else
				{
					int currentLengthR = paths[allLength - 1 - i].length();
					except = except - currentLengthR - 1;
					if (except > 0)
					{
						map.put(i, paths[i]);
						int currentLengthL = paths[i].length();
						except = except - currentLengthL - 1;
						if (except > 0)
						{
							map
									.put(allLength - 1 - i, paths[allLength - 1
											- i]);
						} else
						{
							map.put(allLength - 1 - i, "...");
							break;
						}
					} else
					{
						map.put(i, "...");
						break;
					}
				}
			}
		}
		return retuenString(map, allLength);
	}

	public String retuenString(HashMap<Integer, String> map, int allLength)
	{
		String result = "";
		for (int i = 0; i < allLength; i++)
		{
			if (map.containsKey(i))
			{
				result = result + map.get(i) + "\\";
			}
		}
		return result.substring(0, result.length() - 1);
	}

	public void openFile()
	{
		Document currentdoc = SystemManager.getCurruentDocument();
		if (currentdoc != null && !currentdoc.isSaved())
		{
			int res = WiseDocOptionPane.showConfirmDialog(SystemManager
					.getMainframe(), MessageResource
					.getMessage(MessageConstants.FILE
							+ MessageConstants.DOCUMENTNOTSAVED));
			if (res == JOptionPane.CANCEL_OPTION)
			{
				return;
			} else if (res == JOptionPane.OK_OPTION)
			{
				if (!SaveFileAction.saveWSDFile())
				{
					return;
				}
			}
		}
		File file = new File(getFile());
		if (file.exists())
		{
			try
			{
				FileInputStream filein = new FileInputStream(file);
				Reader reader = IOFactory.getReader(IOFactory.WSD);
				if (file.getPath().endsWith(".wsdt"))
				{
					reader = IOFactory.getReader(IOFactory.WSDT);
				} else if (file.getPath().endsWith(".wsdx"))
				{
					reader = IOFactory.getReader(IOFactory.WSDX);
				} else if (file.getPath().endsWith(".wsdm"))
				{
					reader = IOFactory.getReader(IOFactory.WSDM);
				}
				try
				{
					Document doc = reader.read(filein);
					SystemManager.getMainframe().getEidtComponent()
							.setDocument(doc);
					if (RecentOpenFile.isLoadROF())
					{
						String path = file.getPath();
						if (path.endsWith(".wsd") || path.endsWith(".wsdx")
								|| path.endsWith(".wsdm"))
						{
							doc.setSavePath(file.getAbsolutePath());
							RecentOpenFile.addOpenFile(path);
						}
					}
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}

	public boolean saveFile()
	{
		Document doc = SystemManager.getCurruentDocument();
		if (doc != null)
		{
			File filename = new File(file);
			if (!WisedocUtil.canWrite(filename))
			{
				WiseDocOptionPane.showMessageDialog(SystemManager
						.getMainframe(), MessageResource
						.getMessage(MessageConstants.FILE
								+ MessageConstants.FILECANOTWRITE),
						MessageResource
								.getMessage(MessageConstants.DIALOG_COMMON
										+ MessageConstants.INFORMATIONTITLE),
						WiseDocOptionPane.INFORMATION_MESSAGE);
				doc.setSavePath(null);
				return true;
			}
			try
			{
				if (file.endsWith(".wsd"))
				{
					IOFactory.getWriter(IOFactory.WSD).write(file, doc);
				} else if (file.endsWith(".wsdx"))
				{
					IOFactory.getWriter(IOFactory.WSDX).write(file, doc);
				} else if (file.endsWith(".wsdm"))
				{
					IOFactory.getWriter(IOFactory.WSDM).write(file, doc);
				}
				doc.setSavePath(file);
				if (RecentOpenFile.isLoadROF()
						&& (file.endsWith(".wsd") || file.endsWith(".wsdx") || file
								.endsWith(".wsdm")))
				{
					RecentOpenFile.addOpenFile(file);
				}
				return true;
			} catch (Exception e1)
			{
				WiseDocOptionPane.showMessageDialog(SystemManager
						.getMainframe(), MessageResource
						.getMessage(MessageConstants.FILE
								+ MessageConstants.FILESAVEFAILED),
						MessageResource
								.getMessage(MessageConstants.DIALOG_COMMON
										+ MessageConstants.INFORMATIONTITLE),
						WiseDocOptionPane.INFORMATION_MESSAGE);
				e1.printStackTrace();
				LogUtil.debugException("文件保存不成功", e1);
				return false;
			}

		}

		return true;
	}

	public String getFile()
	{
		return file;
	}

	public void setFile(String file)
	{
		this.file = file;
	}

	public int getActionType()
	{
		return actionType;
	}

	public void setActionType(int actionType)
	{
		this.actionType = actionType;
	}
}
