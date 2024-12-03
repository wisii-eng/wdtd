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
 * @SaveFileAction.java 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.io.IOFactory;
import com.wisii.wisedoc.io.IOUtil;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.WiseDocFileChooser;
import com.wisii.wisedoc.swing.WiseDocFileFilter;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-11-01
 */
@SuppressWarnings("serial")
public class SaveFileAction extends BaseAction
{

	public final static String WSD = "wsd";

	public final static String WSDX = "wsdx";

	public final static String WSDM = "wsdm";

	public final static String WSDT = "wsdt";

	/**
	 * 
	 * 保存Action接口方法实现
	 * 
	 * @param ActioinEvent
	 *            事件源对数据的封装。
	 * @return void 无返回值
	 */
	public void doAction(ActionEvent e)
	{
		saveWSDFile();
	}

	public static boolean saveWSDFile()
	{
		Document doc = SystemManager.getCurruentDocument();
		if (doc != null)
		{
			String savepath = doc.getSavePath();
			if (savepath == null || savepath.trim().equals(""))
			{
				if (IOFactory.ifHaveZimoban())
				{
					final WiseDocFileChooser chooser = (WiseDocFileChooser) DialogSupport
							.getDialog(new WiseDocFileFilter("wsdm", "wsdm"));

					chooser.addActionListener(new ActionListener()
					{

						public void actionPerformed(ActionEvent evt)
						{
							if (JFileChooser.APPROVE_SELECTION.equals(evt
									.getActionCommand()))
							{
								chooser.setResult(DialogResult.OK);
								if (IOUtil.isExists(chooser.getSelectedFile(),
										"wsd"))
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
					int dialogResult = chooser.showSaveDialog(SystemManager
							.getMainframe());
					if (dialogResult == JFileChooser.APPROVE_OPTION)
					{
						savepath = chooser.getSelectedFile().getAbsolutePath();
						if (!savepath.toLowerCase().endsWith(".wsdm"))
						{
							savepath = savepath + ".wsdm";
						}
					} else
					{
						return false;
					}
				} else
				{
					final WiseDocFileChooser chooser = (WiseDocFileChooser) DialogSupport
							.getDialog(new WiseDocFileFilter(MessageResource
									.getMessage(MessageConstants.FILE
											+ MessageConstants.WSDTYPE), WSD));

					chooser.addActionListener(new ActionListener()
					{

						public void actionPerformed(ActionEvent evt)
						{
							if (JFileChooser.APPROVE_SELECTION.equals(evt
									.getActionCommand()))
							{
								chooser.setResult(DialogResult.OK);
								if (IOUtil.isExists(chooser.getSelectedFile(),
										"wsd"))
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
					int dialogResult = chooser.showSaveDialog(SystemManager
							.getMainframe());
					if (dialogResult == JFileChooser.APPROVE_OPTION)
					{
						savepath = chooser.getSelectedFile().getAbsolutePath();
						if (!savepath.toLowerCase().endsWith(".wsd"))
						{
							savepath = savepath + ".wsd";
						}
					} else
					{
						return false;
					}
				}
			}
			return IOUtil.savaFile(doc, new File(savepath));
		}
		return false;
	}
}
