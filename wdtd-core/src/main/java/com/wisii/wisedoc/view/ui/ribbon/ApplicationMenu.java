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

package com.wisii.wisedoc.view.ui.ribbon;

import static com.wisii.wisedoc.resource.MessageResource.getMessage;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_EXIT;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_EXPORT;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_EXPORT_GROUP;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_EXPORT_WHOLE_DOC;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_EXPORT_WHOLE_DOC_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_HTML_PREVIEW;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_MODEL;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_MODEL_EDIT;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_MODEL_MANEGER;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_MODEL_OPEN;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_MODEL_RECENT;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_NEW;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_OPEN;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_OPEN_GROUP;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_PREVIEW;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_SAVE;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_SAVE_AS;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_SAVE_AS_GROUP;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_SAVE_AS_WSD;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_SAVE_AS_WSDT;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_SAVE_AS_WSDT_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_SAVE_AS_WSD_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_SAVE_GROUP;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_WISE_DOC_OPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_SAVE_AS_WSDX;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_SAVE_AS_WSDM;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_SAVE_AS_WSDX_DESCRIPTION;
import static com.wisii.wisedoc.view.ui.text.RibbonUIText.APP_SAVE_AS_WSDM_DESCRIPTION;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.common.JCommandButtonPanel;
import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.common.icon.ResizableIcon;
import org.jvnet.flamingo.ribbon.RibbonApplicationMenu;
import org.jvnet.flamingo.ribbon.RibbonApplicationMenuEntryFooter;
import org.jvnet.flamingo.ribbon.RibbonApplicationMenuEntryPrimary;
import org.jvnet.flamingo.ribbon.RibbonApplicationMenuEntrySecondary;
import org.jvnet.flamingo.ribbon.RibbonApplicationMenuEntryadd;

import com.wisii.security.Function;
import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.configure.ConfigureUtil;
import com.wisii.wisedoc.configure.RecentOpenFile;
import com.wisii.wisedoc.configure.RecentOpenModel;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.WiseCommandButton;
import com.wisii.wisedoc.swing.WiseDocOptionPane;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.action.SaveFileAction;
import com.wisii.wisedoc.view.dialog.AboutDialog;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.ui.manager.ActionFactory;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.Defalult;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.svg.transcoded.document_new;
import com.wisii.wisedoc.view.ui.svg.transcoded.document_open;
import com.wisii.wisedoc.view.ui.svg.transcoded.document_print_preview;
import com.wisii.wisedoc.view.ui.svg.transcoded.document_properties;
import com.wisii.wisedoc.view.ui.svg.transcoded.document_save;
import com.wisii.wisedoc.view.ui.svg.transcoded.document_save_as;
import com.wisii.wisedoc.view.ui.svg.transcoded.system_log_out;
import com.wisii.wisedoc.view.ui.svg.transcoded.text_html;
import com.wisii.wisedoc.view.ui.svg.transcoded.x_office_document;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 菜单
 * 
 * @author 闫舒寰
 * @version 0.1 2008/11/05
 */
public class ApplicationMenu
{

	public RibbonApplicationMenu getApplicationMenu(List<Function> funclist)
	{

		// 新建文档菜单
		RibbonApplicationMenuEntryPrimary amEntryNew = new RibbonApplicationMenuEntryPrimary(
				new document_new(), APP_NEW, ActionFactory
						.getAction(Defalult.NEW_DOCUMENT_ACTION),
				CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
		amEntryNew
				.setRolloverCallback(new RibbonApplicationMenuEntryPrimary.PrimaryRolloverCallback()
				{

					@Override
					public void menuEntryActivated(final JPanel targetPanel)
					{
						targetPanel.removeAll();
						javax.swing.SwingUtilities.invokeLater(new Runnable()
						{

							public void run()
							{
								targetPanel.updateUI();
							}
						});
					}
				});
		amEntryNew
				.setRolloverCallback(new RibbonApplicationMenuEntryPrimary.PrimaryRolloverCallback()
				{

					@Override
					public void menuEntryActivated(JPanel targetPanel)
					{
						targetPanel.removeAll();

						JCommandButtonPanel modelPanel = new JCommandButtonPanel(
								CommandButtonDisplayState.MEDIUM);
						String groupName = APP_MODEL;
						modelPanel.addButtonGroup(groupName);
						ResizableIcon image = new text_html();
						JCommandButton openmodel = new JCommandButton(
								APP_MODEL_OPEN, image);
						openmodel.setHorizontalAlignment(SwingUtilities.LEFT);
						modelPanel.addButtonToLastGroup(openmodel);
						RibbonUIManager.getInstance().bind(
								Defalult.MODEL_OPEN_ACTION, openmodel);
						modelPanel.setMaxButtonColumns(1);

						JCommandButton editmodel = new JCommandButton(
								APP_MODEL_EDIT, image);
						editmodel.setHorizontalAlignment(SwingUtilities.LEFT);
						modelPanel.addButtonToLastGroup(editmodel);
						RibbonUIManager.getInstance().bind(
								Defalult.MODEL_EDIT_ACTION, editmodel);
						modelPanel.setMaxButtonColumns(1);

						targetPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
						modelPanel.setPreferredSize(new Dimension(targetPanel
								.getWidth(), 80));
						targetPanel.add(modelPanel);

						JCommandButtonPanel modelHistoryPanel = new JCommandButtonPanel(
								CommandButtonDisplayState.MEDIUM);
						String modelgroupName = APP_MODEL_RECENT;
						modelHistoryPanel.addButtonGroup(modelgroupName);
						int width = targetPanel.getWidth();
						int imageWidth = image.getIconWidth();
						Font font = modelHistoryPanel.getFont();
						Properties recentOF = RecentOpenModel
								.getRecentopenfile();
						for (int i = 0; i < recentOF.size(); i++)
						{
							int number = i + 1;
							String urlName = recentOF
									.getProperty("RECENTFILEPATH" + i);
							if (urlName != null)
							{
								WiseCommandButton historyButton = new WiseCommandButton(
										number, urlName, image, width
												- imageWidth, font, 1);
								historyButton
										.setHorizontalAlignment(SwingUtilities.LEFT);
								modelHistoryPanel
										.addButtonToLastGroup(historyButton);
							}
						}
						modelHistoryPanel.setMaxButtonColumns(1);
						modelHistoryPanel.setPreferredSize(new Dimension(
								targetPanel.getWidth(), 200));
						targetPanel.add(modelHistoryPanel);
					}
				});

		amEntryNew.setEnabled(true);

		// 模板管理菜单
		RibbonApplicationMenuEntryPrimary amEntryModel = new RibbonApplicationMenuEntryPrimary(
				new document_new(), APP_MODEL_MANEGER, ActionFactory
						.getAction(Defalult.MODEL_MANEGEMENT_ACTION),
				CommandButtonKind.ACTION_ONLY);
		amEntryModel
				.setRolloverCallback(new RibbonApplicationMenuEntryPrimary.PrimaryRolloverCallback()
				{

					@Override
					public void menuEntryActivated(final JPanel targetPanel)
					{
						targetPanel.removeAll();
						javax.swing.SwingUtilities.invokeLater(new Runnable()
						{

							public void run()
							{
								targetPanel.updateUI();
							}
						});
					}
				});
		amEntryModel.setEnabled(true);
		// 打开文档菜单
		RibbonApplicationMenuEntryPrimary amEntryOpen = new RibbonApplicationMenuEntryPrimary(
				new document_open(), APP_OPEN, ActionFactory
						.getAction(Defalult.OPEN_FILE_ACTION),
				CommandButtonKind.ACTION_ONLY);
		amEntryOpen
				.setRolloverCallback(new RibbonApplicationMenuEntryPrimary.PrimaryRolloverCallback()
				{

					@Override
					public void menuEntryActivated(JPanel targetPanel)
					{
						targetPanel.removeAll();
						JCommandButtonPanel openHistoryPanel = new JCommandButtonPanel(
								CommandButtonDisplayState.MEDIUM);
						String groupName = APP_OPEN_GROUP;
						openHistoryPanel.addButtonGroup(groupName);
						int width = targetPanel.getWidth();
						ResizableIcon image = new text_html();
						int imageWidth = image.getIconWidth();
						Font font = openHistoryPanel.getFont();
						Properties recentOF = RecentOpenFile
								.getRecentopenfile();
						for (int i = 0; i < recentOF.size(); i++)
						{
							int number = i + 1;
							String urlName = recentOF
									.getProperty("RECENTFILEPATH" + i);
							if (urlName != null)
							{

								WiseCommandButton historyButton = new WiseCommandButton(
										number, urlName, image, width
												- imageWidth, font, 1);
								historyButton
										.setHorizontalAlignment(SwingUtilities.LEFT);
								openHistoryPanel
										.addButtonToLastGroup(historyButton);
							}
						}
						openHistoryPanel.setMaxButtonColumns(1);
						targetPanel.setLayout(new BorderLayout());
						targetPanel.add(openHistoryPanel, BorderLayout.CENTER);
					}
				});

		// 保存文档菜单
		RibbonApplicationMenuEntryPrimary amEntrySave = new RibbonApplicationMenuEntryPrimary(
				new document_save(), APP_SAVE, new SaveFileAction(),
				CommandButtonKind.ACTION_ONLY);
		amEntrySave
				.setRolloverCallback(new RibbonApplicationMenuEntryPrimary.PrimaryRolloverCallback()
				{

					public void menuEntryActivated(JPanel targetPanel)
					{
						targetPanel.removeAll();
						JCommandButtonPanel savePanel = new JCommandButtonPanel(
								CommandButtonDisplayState.MEDIUM);
						String groupName = APP_SAVE_GROUP;
						savePanel.addButtonGroup(groupName);
						int width = targetPanel.getWidth();
						ResizableIcon image = new text_html();
						int imageWidth = image.getIconWidth();
						Font font = savePanel.getFont();
						Properties recentOF = RecentOpenFile
								.getRecentopenfile();
						for (int i = 0; i < recentOF.size(); i++)
						{
							int number = i + 1;
							String urlName = recentOF
									.getProperty("RECENTFILEPATH" + i);
							if (urlName != null)
							{
								WiseCommandButton historyButton = new WiseCommandButton(
										number, urlName, image, width
												- imageWidth, font, 0);
								historyButton
										.setHorizontalAlignment(SwingUtilities.LEFT);
								savePanel.addButtonToLastGroup(historyButton);
							}
						}
						savePanel.setMaxButtonColumns(1);
						targetPanel.setLayout(new BorderLayout());
						targetPanel.add(savePanel, BorderLayout.CENTER);
					}
				});
		amEntrySave.setEnabled(true);

		// 另存为菜单
		RibbonApplicationMenuEntryPrimary amEntrySaveAs = new RibbonApplicationMenuEntryPrimary(
				new document_save_as(), APP_SAVE_AS, /* new SaveAsWSDFileAction() */
				null, CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);

		// 另存为菜单的二级子菜单，保存为WSD文档
		RibbonApplicationMenuEntrySecondary amEntrySaveAsWSD = new RibbonApplicationMenuEntrySecondary(
				new x_office_document(), APP_SAVE_AS_WSD, ActionFactory
						.getAction(Defalult.SAVEAS_WSD_ACTION),
				CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(Defalult.SAVEAS_WSD_ACTION,
				amEntrySaveAsWSD);
		amEntrySaveAsWSD.setDescriptionText(APP_SAVE_AS_WSD_DESCRIPTION);

		// 另存为菜单的二级子菜单，保存为WSDT文档
		RibbonApplicationMenuEntrySecondary amEntrySaveAsWSDT = new RibbonApplicationMenuEntrySecondary(
				new x_office_document(), APP_SAVE_AS_WSDT, ActionFactory
						.getAction(Defalult.SAVEAS_WSDT_ACTION),
				CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(Defalult.SAVEAS_WSDT_ACTION,
				amEntrySaveAsWSDT);
		amEntrySaveAsWSDT.setDescriptionText(APP_SAVE_AS_WSDT_DESCRIPTION);

		// 另存为菜单的二级子菜单，保存为WSDX文档
		RibbonApplicationMenuEntrySecondary amEntrySaveAsWSDX = new RibbonApplicationMenuEntrySecondary(
				new x_office_document(), APP_SAVE_AS_WSDX, ActionFactory
						.getAction(Defalult.SAVEAS_WSDX_ACTION),
				CommandButtonKind.ACTION_ONLY);
		RibbonUIManager.getInstance().bind(Defalult.SAVEAS_WSDX_ACTION,
				amEntrySaveAsWSDX);
		amEntrySaveAsWSDX.setDescriptionText(APP_SAVE_AS_WSDX_DESCRIPTION);

		// 另存为菜单的二级子菜单，保存为WSDX文档
		RibbonApplicationMenuEntrySecondary amEntrySaveAsWSDM = new RibbonApplicationMenuEntrySecondary(
				new x_office_document(), APP_SAVE_AS_WSDM, ActionFactory
						.getAction(Defalult.SAVEAS_WSDM_ACTION),
				CommandButtonKind.ACTION_ONLY);
		amEntrySaveAsWSDM.setDescriptionText(APP_SAVE_AS_WSDM_DESCRIPTION);

		// 另存为其他格式的文档
		// RibbonApplicationMenuEntrySecondary amEntrySaveAsOtherFormats = new
		// RibbonApplicationMenuEntrySecondary(
		// new document_save_as(), APP_SAVE_AS_OTHER_FORMAT, null,
		// CommandButtonKind.ACTION_ONLY);
		// amEntrySaveAsOtherFormats
		// .setDescriptionText(APP_SAVE_AS_OTHER_FORMAT_DESCRIPTION);
		// 设置另存为小组
		amEntrySaveAs.addSecondaryMenuGroup(APP_SAVE_AS_GROUP,
				amEntrySaveAsWSD, amEntrySaveAsWSDT, amEntrySaveAsWSDX,
				amEntrySaveAsWSDM
				/** , amEntrySaveAsOtherFormats
								 */);

		// 导出菜单
		RibbonApplicationMenuEntryPrimary amEntryExport = new RibbonApplicationMenuEntryPrimary(
				new document_save_as(), APP_EXPORT, /* new SaveAsWSDFileAction() */
				null, CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);

		// 整个文档导出为XSLT文档
		RibbonApplicationMenuEntrySecondary amEntryWiseDocXSLT = new RibbonApplicationMenuEntrySecondary(
				new text_html(), APP_EXPORT_WHOLE_DOC, ActionFactory
						.getAction(Defalult.EXPLORT_XSLTFILE_ACTION),
				CommandButtonKind.ACTION_ONLY);
		amEntryWiseDocXSLT.setDescriptionText(APP_EXPORT_WHOLE_DOC_DESCRIPTION);
		amEntryExport.setEnabled(true);

//		// 当前章节导出为XSLT文档
//		RibbonApplicationMenuEntryadd amEntryPageSequenceXSLT = new RibbonApplicationMenuEntryadd(
//				new document_save_as(), APP_EXPORT_PAGE_SEQUENCE, ActionFactory
//						.getAction(Defalult.EXPLORT_PSXSLTFILE_ACTION),
//				CommandButtonKind.ACTION_ONLY);
//		amEntryPageSequenceXSLT
//				.setDescriptionText(APP_EXPORT_PAGE_SEQUENCE_DESCRIPTION);
//		amEntryPageSequenceXSLT.setEnabled(true);

		// // 当前内容导出为XSLT文档
		// RibbonApplicationMenuEntryadd amEntryContentXSLT = new
		// RibbonApplicationMenuEntryadd(
		// new document_save_as(), APP_EXPORT_CONTENT, ActionFactory
		// .getAction(Defalult.EXPLORT_CONTENTXSLTFILE_ACTION),
		// CommandButtonKind.ACTION_ONLY);
		// amEntryContentXSLT.setDescriptionText(APP_EXPORT_CONTENT_DESCRIPTION);
		// amEntryContentXSLT.setEnabled(true);

		// 整个文档导出为XSLT文档
//		RibbonApplicationMenuEntrySecondary amEntryWiseDocStandardXSLT = new RibbonApplicationMenuEntrySecondary(
//				new text_html(), APP_EXPORT_WHOLE_STANDARD_DOC, ActionFactory
//						.getAction(Defalult.EXPLORT_STANDARD_XSLTFILE_ACTION),
//				CommandButtonKind.ACTION_ONLY);
//		amEntryWiseDocStandardXSLT
//				.setDescriptionText(APP_EXPORT_WHOLE_DOC_STANDARD_DESCRIPTION);
//		amEntryWiseDocStandardXSLT.setEnabled(true);

		// 当前章节导出为XSLT文档
//		RibbonApplicationMenuEntryadd amEntryPageSequenceStandardXSLT = new RibbonApplicationMenuEntryadd(
//				new document_save_as(),
//				APP_EXPORT_STANDARD_PAGE_SEQUENCE,
//				ActionFactory
//						.getAction(Defalult.EXPLORT_PS_STANDARD_XSLTFILE_ACTION),
//				CommandButtonKind.ACTION_ONLY);
//		amEntryPageSequenceStandardXSLT
//				.setDescriptionText(APP_EXPORT_PAGE_SEQUENCE_STANDARD_DESCRIPTION);
//
//		amEntryPageSequenceStandardXSLT.setEnabled(true);
		// 导出html格式XSLT文档
//		RibbonApplicationMenuEntryadd amEntryHtmlXSLT = new RibbonApplicationMenuEntryadd(
//				new document_save_as(),
//				APP_EXPORT_HTML,
//				ActionFactory
//						.getAction(Defalult.EXPLORT_HTML_XSLTFILE_ACTION),
//				CommandButtonKind.ACTION_ONLY);
//		amEntryHtmlXSLT
//				.setDescriptionText(APP_EXPORT_HTML_DESCRIPTION);
//
//		amEntryHtmlXSLT.setEnabled(true);
		// 导出html格式XSLT文档
				RibbonApplicationMenuEntryadd amEntryXMLPath = new RibbonApplicationMenuEntryadd(
						new document_save_as(),
						RibbonUIText.APP_EXPORT_XPATH,
						ActionFactory
								.getAction(Defalult.EXPLORT_XPTAH_FILE_ACTION),
						CommandButtonKind.ACTION_ONLY);
				amEntryXMLPath
						.setDescriptionText(RibbonUIText.APP_EXPORT_XPATH_DESCRIPTION);

				amEntryXMLPath.setEnabled(true);
		// 二级菜单上的文字
		// 当前内容导出为标准XSLT文档
		// RibbonApplicationMenuEntryadd amEntryContentStandardXSLT = new
		// RibbonApplicationMenuEntryadd(
		// new document_save_as(),
		// APP_EXPORT_STANDARD_CONTENT,
		// ActionFactory
		// .getAction(Defalult.EXPLORT_CONTENT_STANDARD_XSLTFILE_ACTION),
		// CommandButtonKind.ACTION_ONLY);
		// amEntryContentStandardXSLT
		// .setDescriptionText(APP_EXPORT_STANDARD_CONTENT_DESCRIPTION);
		// amEntryContentStandardXSLT.setEnabled(true);
		// 二级菜单上的文字
		amEntryExport.addSecondaryMenuGroup(APP_EXPORT_GROUP,
				amEntryWiseDocXSLT
//				, amEntryPageSequenceXSLT,
				// amEntryContentXSLT,
//				amEntryWiseDocStandardXSLT, amEntryPageSequenceStandardXSLT,amEntryHtmlXSLT
				,amEntryXMLPath
		// , amEntryContentStandardXSLT
				);
		// 导出文档菜单
		RibbonApplicationMenuEntryPrimary amEntryBroswer = new RibbonApplicationMenuEntryPrimary(
				new document_print_preview(), APP_PREVIEW, ActionFactory
						.getAction(Defalult.BROWSE_ACTION),
				CommandButtonKind.ACTION_ONLY);

		amEntryBroswer
				.setRolloverCallback(new RibbonApplicationMenuEntryPrimary.PrimaryRolloverCallback()
				{

					public void menuEntryActivated(final JPanel targetPanel)
					{
						targetPanel.removeAll();
						javax.swing.SwingUtilities.invokeLater(new Runnable()
						{

							public void run()
							{
								targetPanel.updateUI();
							}
						});
					}
				});
		amEntryBroswer.setEnabled(true);
		
		RibbonApplicationMenuEntryPrimary amEntryHtmlBroswer = new RibbonApplicationMenuEntryPrimary(
				new text_html(), APP_HTML_PREVIEW, ActionFactory
						.getAction(Defalult.HTMLBROWSE_ACTION),
				CommandButtonKind.ACTION_ONLY);

		amEntryHtmlBroswer
				.setRolloverCallback(new RibbonApplicationMenuEntryPrimary.PrimaryRolloverCallback()
				{

					public void menuEntryActivated(final JPanel targetPanel)
					{
						targetPanel.removeAll();
						javax.swing.SwingUtilities.invokeLater(new Runnable()
						{

							public void run()
							{
								targetPanel.updateUI();
							}
						});
					}
				});
		amEntryHtmlBroswer.setEnabled(true);

		RibbonApplicationMenuEntryPrimary amEntryGeneratedRegistrationInformation = new RibbonApplicationMenuEntryPrimary(
				new document_open(),
				RibbonUIText.GENERATE_REGISTRATION_INFORMATION, ActionFactory
						.getAction(Defalult.GENERATE_REGISTRATION_INFORMATION),
				CommandButtonKind.ACTION_ONLY);
		amEntryGeneratedRegistrationInformation.setEnabled(true);

		RibbonApplicationMenuEntryPrimary aboutInformation = new RibbonApplicationMenuEntryPrimary(
				MediaResource.getResizableIcon("About.gif"),
				RibbonUIText.SIMPLE_ABOUT, new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e)
					{
						new AboutDialog();
					}
				}, CommandButtonKind.ACTION_ONLY);
		aboutInformation.setEnabled(true);

		// RibbonApplicationMenuEntryPrimary amEntryLoadRegistrationDocument =
		// new RibbonApplicationMenuEntryPrimary(
		// new document_open(), RibbonUIText.LOAD_REGISTRATION_DOCUMENT,
		// ActionFactory.getAction(Defalult.LOAD_REGISTRATION_DOCUMENT),
		// CommandButtonKind.ACTION_ONLY);
		// amEntryLoadRegistrationDocument.setEnabled(true);

		// 打印菜单
		// RibbonApplicationMenuEntryPrimary amEntryPrint = new
		// RibbonApplicationMenuEntryPrimary(
		// new document_print(), APP_PRINT, new ActionListener()
		// {
		//
		// @Override
		// public void actionPerformed(ActionEvent e)
		// {
		// // System.out.println("Invoked printing document");
		// }
		// }, CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
		//
		// RibbonApplicationMenuEntrySecondary amEntryPrintSelect = new
		// RibbonApplicationMenuEntrySecondary(
		// new printer(), APP_PRINT, null, CommandButtonKind.ACTION_ONLY);
		// amEntryPrintSelect.setDescriptionText(APP_PRINT_DESCRIPTION);
		//
		// RibbonApplicationMenuEntrySecondary amEntryPrintDefault = new
		// RibbonApplicationMenuEntrySecondary(
		// new document_print(), APP_PRINT_QUICK, null,
		// CommandButtonKind.ACTION_ONLY);
		// amEntryPrintDefault.setDescriptionText(APP_PRINT_QUICK_DESCRIPTION);
		//
		// amEntryPrint.addSecondaryMenuGroup(APP_PRINT_GROUP,
		// amEntryPrintSelect,
		// amEntryPrintDefault);

		// 页面设置
		// RibbonApplicationMenuEntrySecondary amEntryPrintMemo = new
		// RibbonApplicationMenuEntrySecondary(
		// new text_x_generic(), APP_PAGE_SET, null,
		// CommandButtonKind.ACTION_ONLY);
		//
		// amEntryPrint
		// .addSecondaryMenuGroup(APP_PAGE_SET_GROUP, amEntryPrintMemo);

		// 发送菜单
		/*
		 * RibbonApplicationMenuEntryPrimary amEntrySend = new
		 * RibbonApplicationMenuEntryPrimary( new mail_forward(), "发送", new
		 * ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * System.out.println("Invoked sending document"); } },
		 * CommandButtonKind.POPUP_ONLY);
		 * 
		 * RibbonApplicationMenuEntrySecondary amEntrySendMail = new
		 * RibbonApplicationMenuEntrySecondary( new mail_message_new(),
		 * "E-mail", null, CommandButtonKind.ACTION_ONLY);
		 * amEntrySendMail.setDescriptionText
		 * ("Send a copy of the document in an e-mail message as an attachment"
		 * ); RibbonApplicationMenuEntrySecondary amEntrySendHtml = new
		 * RibbonApplicationMenuEntrySecondary( new text_html(),
		 * "E-mail as HTML Attachment", null, CommandButtonKind.ACTION_ONLY);
		 * amEntrySendHtml.setDescriptionText(
		 * "Send a copy of the document in a message as an HTML attachment");
		 * RibbonApplicationMenuEntrySecondary amEntrySendDoc = new
		 * RibbonApplicationMenuEntrySecondary( new x_office_document(),
		 * "E-mail as Word Attachment", null, CommandButtonKind.ACTION_ONLY);
		 * amEntrySendDoc.setDescriptionText(
		 * "Send a copy of the document in a message as a Word attachment");
		 * RibbonApplicationMenuEntrySecondary amEntrySendWireless = new
		 * RibbonApplicationMenuEntrySecondary( new network_wireless(),
		 * "Wireless", null, CommandButtonKind.POPUP_ONLY);
		 * 
		 * amEntrySendWireless.setPopupCallback(new PopupPanelCallback() {
		 * 
		 * @Override public JPopupPanel getPopupPanel(JCommandButton
		 * commandButton) { JCommandPopupMenu wirelessChoices = new
		 * JCommandPopupMenu(); wirelessChoices.addMenuButton(new
		 * JCommandMenuButton( "Via WiFi", new EmptyResizableIcon(16)));
		 * wirelessChoices.addMenuButton(new JCommandMenuButton(
		 * "Via BlueTooth", new EmptyResizableIcon(16))); return
		 * wirelessChoices; } });
		 * 
		 * amEntrySendWireless.setDescriptionText(
		 * "Locate a wireless device and send a copy of the document to it");
		 * 
		 * amEntrySend.addSecondaryMenuGroup(
		 * "Send a copy of the document to other people", amEntrySendMail,
		 * amEntrySendHtml, amEntrySendDoc, amEntrySendWireless);
		 */

		// 离开菜单
		RibbonApplicationMenuEntryPrimary amEntryExit = new RibbonApplicationMenuEntryPrimary(
				new system_log_out(), APP_EXIT, new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e)
					{
						Document doc = SystemManager.getCurruentDocument();
						// 判断当前文档是否保存，没有保存则提示保存
						if (doc != null && !doc.isSaved())
						{
							int res = WiseDocOptionPane
									.showConfirmDialog(
											SystemManager.getMainframe(),
											MessageResource
													.getMessage("wsd.file.documentnotsave"));
							if (res == WiseDocOptionPane.CANCEL_OPTION)
							{

								return;
							} else if (res == WiseDocOptionPane.OK_OPTION)
							{
								if (!SaveFileAction.saveWSDFile())
								{
									return;
								}
							}
						}
						System.exit(0);
					}
				}, CommandButtonKind.ACTION_ONLY);
		amEntryExit
				.setRolloverCallback(new RibbonApplicationMenuEntryPrimary.PrimaryRolloverCallback()
				{

					@Override
					public void menuEntryActivated(JPanel targetPanel)
					{
						targetPanel.removeAll();
					}
				});

		RibbonApplicationMenu applicationMenu = new RibbonApplicationMenu();
		applicationMenu.addMenuEntry(amEntryNew);
		applicationMenu.addMenuEntry(amEntryModel);
		applicationMenu.addMenuEntry(amEntryOpen);
		applicationMenu.addMenuEntry(amEntrySave);
		applicationMenu.addMenuEntry(amEntrySaveAs);
		boolean isexportfunenable = isExportEnable(funclist);
		if (isexportfunenable)
		{
			applicationMenu.addMenuEntry(amEntryExport);
		}
		applicationMenu.addMenuEntry(amEntryBroswer);
		applicationMenu.addMenuEntry(amEntryHtmlBroswer);
		applicationMenu.addMenuEntry(amEntryGeneratedRegistrationInformation);
		// applicationMenu.addMenuEntry(amEntryLoadRegistrationDocument);
		// applicationMenu.addMenuEntry(amEntryPrint);
		// applicationMenu.addMenuEntry(amEntrySend);
		applicationMenu.addMenuEntry(aboutInformation);

		applicationMenu.addMenuEntry(amEntryExit);

		// 最下面的两个按钮
		RibbonApplicationMenuEntryFooter amFooterProps = new RibbonApplicationMenuEntryFooter(
				new document_properties(), APP_WISE_DOC_OPTION,
				new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e)
					{
						ConfDialog di = new ConfDialog();
						di.setLocationRelativeTo(null);
						di.setVisible(true);
					}
				});
		RibbonApplicationMenuEntryFooter amFooterExit = new RibbonApplicationMenuEntryFooter(
				new system_log_out(), APP_EXIT, new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e)
					{
						System.exit(0);
					}
				});
		amFooterExit.setEnabled(false);
		applicationMenu.addFooterEntry(amFooterProps);
		applicationMenu.addFooterEntry(amFooterExit);

		// 添加具体的菜单
		// RibbonPanel.getRibbon().setApplicationMenu(applicationMenu);

		RichTooltip appMenuRichTooltip = new RichTooltip();
		appMenuRichTooltip.setTitle(RibbonUIText.DESIGNER_BUTTON);
		appMenuRichTooltip
				.addDescriptionSection(RibbonUIText.DESIGNER_DISCRIBLE);

		try
		{
			appMenuRichTooltip
					.setMainImage(ImageIO
							.read(ApplicationMenu.class
									.getResource("/com/wisii/wisedoc/view/ui/svg/appmenubutton-tooltip-mainnew.png")));
			// appMenuRichTooltip
			// .setFooterImage(ImageIO
			// .read(ApplicationMenu.class
			// .getResource("/com/wisii/wisedoc/view/ui/svg/help-browser.png")));
		} catch (IOException ioe)
		{
			// TODO do something
		}

		// appMenuRichTooltip.addFooterSection("Press F1 for more help");

		// 添加菜单说明
		RibbonPanel.getRibbon().setApplicationMenuRichTooltip(
				appMenuRichTooltip);

		return applicationMenu;
	}

	private boolean isExportEnable(List<Function> funclist)
	{
		if (funclist == null || funclist.isEmpty())
		{
			return false;
		} else
		{
			for (Function func : funclist)
			{
				if (func.getId().equals("pioui"))
				{
					return true;
				}
			}
			return false;
		}
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
	
	private class ConfDialog extends AbstractWisedocDialog{
		private ConfDialogPanel confPanel;
		private JButton okbutton = new JButton(getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.OK));

		private JButton cancelbutton = new JButton(getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.CANCEL));

		/**
		 * Create the frame.
		 */
		public ConfDialog() {
			JPanel mainpanel = new JPanel(new BorderLayout());
			confPanel = new ConfDialogPanel();
			mainpanel.add(confPanel, BorderLayout.CENTER);
			JPanel buttonpanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
			buttonpanel.add(okbutton);
			buttonpanel.add(cancelbutton);
			mainpanel.add(buttonpanel, BorderLayout.SOUTH);
			getContentPane().add(mainpanel);
			setModal(true);
			setSize(300, 200);
			initAction();
		}

		private void initAction() {
			okbutton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					result = DialogResult.OK;
					dispose();

				}
			});
			cancelbutton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}

		private class ConfDialogPanel extends JPanel {
			public ConfDialogPanel() {
				this.setSize(318, 213);
				this.setLayout(null);
				final JCheckBox autoCheck = new JCheckBox("自动登录");
				String auto = ConfigureUtil.getProperty("autologin");
				autoCheck.setSelected(Boolean.parseBoolean(auto));
				autoCheck.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ConfigureUtil.setAutoLogin(autoCheck.isSelected());
					}
				});
				autoCheck.setBounds(10, 60, 84, 23);
				this.add(autoCheck);

				
			}

		}
	}
}
