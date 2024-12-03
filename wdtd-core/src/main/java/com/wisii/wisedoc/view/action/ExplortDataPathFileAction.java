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
 * 
 */
package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.BarCodeText;
import com.wisii.wisedoc.document.Chart;
import com.wisii.wisedoc.document.ChartInline;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.Element;
import com.wisii.wisedoc.document.ExternalGraphic;
import com.wisii.wisedoc.document.ImageInline;
import com.wisii.wisedoc.document.Inline;
import com.wisii.wisedoc.document.InlineContent;
import com.wisii.wisedoc.document.NumberContent;
import com.wisii.wisedoc.document.ZiMoban;
import com.wisii.wisedoc.document.attribute.Attributes;
import com.wisii.wisedoc.document.attribute.ChartData2D;
import com.wisii.wisedoc.document.attribute.ChartDataList;
import com.wisii.wisedoc.document.attribute.Condition;
import com.wisii.wisedoc.document.attribute.ConditionItem;
import com.wisii.wisedoc.document.attribute.ConditionItemCollection;
import com.wisii.wisedoc.document.attribute.Group;
import com.wisii.wisedoc.document.attribute.LogicalExpression;
import com.wisii.wisedoc.document.attribute.Sort;
import com.wisii.wisedoc.document.attribute.edit.ConnWith;
import com.wisii.wisedoc.document.attribute.edit.Formula;
import com.wisii.wisedoc.document.attribute.edit.Parameter;
import com.wisii.wisedoc.document.attribute.transform.SelectColumnInFO;
import com.wisii.wisedoc.document.attribute.transform.SelectInfo;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.WiseDocFileChooser;
import com.wisii.wisedoc.swing.WiseDocFileFilter;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * @author wisii
 * 
 */
public class ExplortDataPathFileAction extends BaseAction {

	public ExplortDataPathFileAction() {
		super();
	}

	/**
	 * 
	 * 用指定描述字符串和默认图标定义一个 Actionion的名字。
	 */
	public ExplortDataPathFileAction(String name) {
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
	public ExplortDataPathFileAction(String name, Icon icon) {
		super(name, icon);
	}

	/**
	 * 到处xslt模版文件事件接口
	 */
	public void doAction(ActionEvent e) {
		Document doc = getCurrentDocument();
		if (doc == null) {
			JOptionPane.showInternalMessageDialog(null,
					MessageResource.getMessage("nodocument"),
					MessageResource.getMessage("prompt"),
					JOptionPane.CLOSED_OPTION);
			return;
		}

		exlportdatapath(doc);
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
	public void exlportdatapath(Document lay) {
		List<String> paths = getAllpath(lay);
		if (paths == null || paths.isEmpty()) {
			JOptionPane.showMessageDialog(null, RibbonUIText.APP_EXPORT_XPATH_NULL);
			return;
		}
		final WiseDocFileChooser chooser = (WiseDocFileChooser) DialogSupport
				.getDialog(new WiseDocFileFilter("xml", "xml"));
		Document doc = getCurrentDocument();
		String path = doc.getSavePath();
		if (path != null && !path.isEmpty()) {
			chooser.setSelectedFile(new File(path.substring(0,
					path.lastIndexOf('.'))
					+ ".xml"));
		}
		chooser.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				if (JFileChooser.APPROVE_SELECTION.equals(evt
						.getActionCommand())) {
					chooser.setResult(DialogResult.OK);
					if (isExists(chooser.getSelectedFile())) {
						int result = JOptionPane.showConfirmDialog(
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
						.getActionCommand())) {
					chooser.setResult(DialogResult.Cancel);
				}
				chooser.distroy();
			}
		});
		DialogResult result = chooser.showDialog(JFileChooser.SAVE_DIALOG);
		if (result == DialogResult.OK) {
			String filename = "";
			filename = chooser.getSelectedFile().getAbsolutePath();
			if (!filename.endsWith(".xml")) {
				filename += ".xml";
			}
			write(filename, paths);
		}
	}

	private void write(String filename, List<String> paths) {
		StringBuffer sb = new StringBuffer();
		for (String path : paths) {
			sb.append(path);
			sb.append(',');
		}
		try {
			FileOutputStream out = new FileOutputStream(filename);
			out.write(sb.substring(0, sb.length() - 1).getBytes("UTF-8"));
			out.close();
		} catch (Exception e) {

			LogUtil.debugException("出错", e);
		}
	}

	private List<String> getAllpath(Document lay) {
		List<String> paths = new ArrayList<String>();
		getAllpath(lay, paths);
		return paths;
	}

	private void getAllpath(Element element, List<String> paths) {
		if (element instanceof ZiMoban) {
			return;
		}
		Attributes att = element.getAttributes();
		if (att != null) {
			Object bgimage = att.getAttribute(Constants.PR_BACKGROUND_IMAGE);
			if (bgimage != null && bgimage instanceof BindNode) {
				addBindeNodeTolist((BindNode) bgimage, paths);
			}
			Group group = (Group) att.getAttribute(Constants.PR_GROUP);
			if (group != null) {
				addGroup(group, paths);
			}

			LogicalExpression condition = (LogicalExpression) att
					.getAttribute(Constants.PR_CONDTION);
			if (condition != null) {
				addCondition(condition, paths);
			}
			ConditionItemCollection dynamicStyle = (ConditionItemCollection) att
					.getAttribute(Constants.PR_DYNAMIC_STYLE);
			if (dynamicStyle != null) {
				addDynamicStyle(dynamicStyle, paths);
			}
			if (element instanceof Inline) {
				addInLineBindingOK((Inline) element, paths);
				return;
			}
			int childcount = element.getChildCount();
			for (int i = 0; i < childcount; i++) {
				Element child = (Element) element.getChildAt(i);
				getAllpath(child, paths);
			}
		}
	}

	private void addInLineBindingOK(Inline inline, List<String> paths) {

		if (inline instanceof ChartInline) {
			Chart chart = (Chart) inline.getChildAt(0);
			if (chart == null) {
				return;
			}
			Attributes attrs = chart.getAttributes();
			BarCodeText title = (BarCodeText) attrs
					.getAttribute(Constants.PR_TITLE);
			if (title != null && title.isBindContent()) {
				addBindeNodeTolist(title.getBindNode(), paths);
			}
			List<BarCodeText> valuelabellist = (List<BarCodeText>) attrs
					.getAttribute(Constants.PR_VALUE_LABEL);
			if (valuelabellist != null && !valuelabellist.isEmpty()) {
				for (BarCodeText valuelabel : valuelabellist) {
					if (valuelabel.isBindContent()) {
						addBindeNodeTolist(valuelabel.getBindNode(), paths);
					}
				}
			}
			List<BarCodeText> serieslabellist = (List<BarCodeText>) attrs
					.getAttribute(Constants.PR_SERIES_LABEL);
			if (serieslabellist != null && !serieslabellist.isEmpty()) {
				for (BarCodeText serieslabel : serieslabellist) {
					if (serieslabel.isBindContent()) {
						addBindeNodeTolist(serieslabel.getBindNode(), paths);
					}
				}
			}
			BarCodeText domlabel = (BarCodeText) attrs
					.getAttribute(Constants.PR_DOMAINAXIS_LABEL);
			if (domlabel != null && domlabel.isBindContent()) {
				addBindeNodeTolist(domlabel.getBindNode(), paths);
			}

			BarCodeText rangeaxislabel = (BarCodeText) attrs
					.getAttribute(Constants.PR_RANGEAXIS_LABEL);
			if (rangeaxislabel != null && rangeaxislabel.isBindContent()) {
				addBindeNodeTolist(rangeaxislabel.getBindNode(), paths);
			}
			ChartDataList seriesvalue = (ChartDataList) attrs
					.getAttribute(Constants.PR_SERIES_VALUE);
			if (seriesvalue != null && !seriesvalue.isEmpty()) {
				for (ChartData2D chd2d : seriesvalue) {
					if (chd2d != null) {
						NumberContent nc = chd2d.getNumbercontent();
						if (nc.isBindContent()) {
							addBindeNodeTolist(nc.getBindNode(), paths);
						}
					}
				}

			}
		} else if (inline instanceof ImageInline) {
			ExternalGraphic image = (ExternalGraphic) inline.getChildAt(0);
			if (image == null) {
				return;
			}
			Object src = image.getAttribute(Constants.PR_SRC);
			if (src instanceof BindNode) {
				addBindeNodeTolist((BindNode) src, paths);
			}
		} else {
			Attributes attrs = inline.getAttributes();
			ConnWith conwith = (ConnWith) attrs
					.getAttribute(Constants.PR_CONN_WITH);
			if (conwith != null) {
				addConnWith(conwith, paths);
			}
			SelectInfo selectinfo = (SelectInfo) attrs
					.getAttribute(Constants.PR_SELECT_INFO);
			if (selectinfo != null) {
				addSelectinfo(selectinfo, paths);
			}
			InlineContent content = inline.getContent();
			if (content.isBindContent()) {
				addBindeNodeTolist(content.getBindNode(), paths);
			}
		}

	}

	private void addSelectinfo(SelectInfo selectinfo, List<String> paths) {
		List<SelectColumnInFO> columninfoes = selectinfo.getColumninfos();
		for (SelectColumnInFO columninfo : columninfoes) {
			if (columninfo != null) {
				addBindeNodeTolist(columninfo.getOptionpath(), paths);
			}
		}
	}

	private void addConnWith(ConnWith conwith, List<String> paths) {

		List<Parameter> params = conwith.getParms();
		if (params != null && !params.isEmpty()) {
			for (Parameter param : params) {
				Object paramvalue = param.getValue();
				if (paramvalue instanceof BindNode) {
					addBindeNodeTolist((BindNode) paramvalue, paths);
				}
			}
		}
		List<Formula> formulas = conwith.getFormulas();
		if (formulas != null && !formulas.isEmpty()) {
			for (Formula formula : formulas) {
				addBindeNodeTolist(formula.getXpath(), paths);
			}
		}

	}

	private void addDynamicStyle(ConditionItemCollection dynamicStyles,
			List<String> paths) {
		for (ConditionItem dynamicStyle : dynamicStyles) {
			addCondition(dynamicStyle.getCondition(), paths);
		}

	}

	private void addCondition(LogicalExpression condition, List<String> paths) {
		List conditions = condition.getConditions();
		for (Object cond : conditions) {
			if (cond instanceof LogicalExpression) {
				addCondition((LogicalExpression) cond, paths);

			} else if (cond instanceof Condition) {
				Condition condi = (Condition) cond;
				addBindeNodeTolist(condi.getNode(), paths);
				Object parm = condi.getParam();
				if (parm instanceof BindNode) {
					addBindeNodeTolist((BindNode) parm, paths);
				}
			}
		}
	}

	private void addGroup(Group group, List<String> paths) {
		BindNode gnode = group.getNode();
		addBindeNodeTolist((BindNode) gnode, paths);
		List<Sort> sorts = group.getSortSet();
		if (sorts != null && !sorts.isEmpty()) {
			for (Sort sort : sorts) {
				addBindeNodeTolist(sort.getNode(), paths);
			}
		}
		LogicalExpression condition = group.getFliterCondition();
		if (condition != null) {
			addCondition(condition, paths);
		}
	}

	private void addBindeNodeTolist(BindNode node, List<String> list) {
		String xpath = node.getXPath();
		if (!list.contains(xpath)) {
			list.add(xpath);
		}
	}

	private boolean isExists(File file) {
		boolean result = false;
		if (file == null)
			result = false;
		else if (file.isDirectory())
			result = false;
		else if (file.exists())
			result = true;
		else {
			String fileName = file.getAbsolutePath() + ".xml";
			if (new File(fileName).exists())
				result = true;
			else
				result = false;
		}
		return result;
	}

}
