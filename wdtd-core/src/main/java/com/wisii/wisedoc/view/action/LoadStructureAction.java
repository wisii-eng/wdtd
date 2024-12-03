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
 * @LoadStructureAction.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JFileChooser;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.banding.io.StructureReaderUtil;
import com.wisii.wisedoc.document.BarCodeText;
import com.wisii.wisedoc.document.CellElement;
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
import com.wisii.wisedoc.document.TextInline;
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
import com.wisii.wisedoc.exception.DataStructureException;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.swing.WiseDocFileFilter;
import com.wisii.wisedoc.swing.WiseDocOptionPane;
import com.wisii.wisedoc.util.DialogSupport;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.ui.parts.dialogs.BindingSetingDialog;

/**
 * 类功能描述：导入数据结构事件
 * 
 * 作者：zhangqiang 创建日期：2008-6-2
 */
public class LoadStructureAction extends BaseAction
{

	private static String[] DATASTRUTYPES =
	{ "xml", "dtd", "xsd", "txt", "csv", "dbf" };

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public LoadStructureAction()
	{
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public LoadStructureAction(String name)
	{
		super(name);
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public LoadStructureAction(String name, Icon icon)
	{
		super(name, icon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wisii.wisedoc.action.BaseAction#doAction(java.awt.event.ActionEvent)
	 */
	@Override
	public void doAction(ActionEvent e)
	{
		File file = null;
		JFileChooser chooser = DialogSupport.getDialog(new WiseDocFileFilter(
				MessageResource.getMessage(MessageConstants.DSMESSAGECONSTANTS
						+ MessageConstants.DATATYPE), DATASTRUTYPES));
		chooser.setDialogTitle(MessageResource
				.getMessage(MessageConstants.DSMESSAGECONSTANTS
						+ MessageConstants.LOADSTRUCTURE));
		String oldpath = BrowseAction.getDatafileurl();
		if (oldpath != null && !oldpath.isEmpty())
		{
			chooser.setCurrentDirectory(new File(oldpath.substring(0, oldpath
					.lastIndexOf(File.separator))));
		}
		int res = chooser.showOpenDialog(SystemManager.getMainframe());
		if (res != JFileChooser.APPROVE_OPTION)
		{
			return;
		}
		file = chooser.getSelectedFile();
		if (file == null)
			return;
		if (!file.canRead())
			return;
		DataStructureTreeModel model;
		try
		{
			String filepath = file.getPath();
			model = StructureReaderUtil.readStructure(filepath);
			model.setDatapath(filepath);
			if (model != null)
			{
				String ftype = filepath
						.substring(filepath.lastIndexOf(".") + 1).toLowerCase();
				Document doc = SystemManager.getCurruentDocument();
				// 判断文档中所有动态内容是否在新结构中存在，如果在新结构中，则需要删除相关的绑定元素，动态属性等
				if (!isAllBindingOk(doc, model))
				{
					// 弹出处理设置对话框
					BindingSetingDialog settingdialog = new BindingSetingDialog();
					DialogResult result = settingdialog.showDialog();
					if (result == DialogResult.OK)
					{
						// 得到处理方式设置
						int dealmethod = settingdialog.getDealMethodSetting();
						// 根据设置处理文档
						dealDocument(model, dealmethod, doc);
						doc.setDataStructure(model);
						// SwingUtilities.i
					}
				} else
				{
					doc.setDataStructure(model);
				}
			}
		} catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
			LogUtil.debugException("数据结构文件不存在\n", e1);
			WiseDocOptionPane.showMessageDialog(SystemManager.getMainframe(),
					MessageResource
							.getMessage(MessageConstants.DSMESSAGECONSTANTS
									+ MessageConstants.STRUCTURENOTFIND));
		} catch (DataStructureException e2)
		{
			e2.printStackTrace();
			LogUtil.debugException("读取数据结构时出错，数据结构数据的格式不正确", e2);
			WiseDocOptionPane.showMessageDialog(SystemManager.getMainframe(),
					MessageResource
							.getMessage(MessageConstants.DSMESSAGECONSTANTS
									+ MessageConstants.STRUCTUREDATAERROR));
		}

	}

	private void dealDocument(DataStructureTreeModel model, int dealmethod,
			Document doc)
	{
		dealElement((BindNode) model.getRoot(), dealmethod, (Element) doc, doc);
	}

	/*
	 * 处理元素，根据dealmethod值对文档中数据节点不存在的属性或元素进行处理
	 */
	private void dealElement(BindNode root, int dealmethod, Element element,
			Document doc)
	{
		if(element instanceof ZiMoban)
		{
			return;
		}
		Attributes att = element.getAttributes();
		if (att != null)
		{
			Map<Integer, Object> newatts = new HashMap<Integer, Object>();
			Object bgimage = att.getAttribute(Constants.PR_BACKGROUND_IMAGE);
			// 检查背景图片属性
			if (!isBackGroundImageOk(bgimage, root))
			{
				newatts.put(Constants.PR_BACKGROUND_IMAGE, null);
			}
			Group group = (Group) att.getAttribute(Constants.PR_GROUP);
			// 重复属性是否正常
			if (!isGroupOk(group, root))
			{
				newatts.put(Constants.PR_GROUP, getNewGroup(group, dealmethod,
						root));
			}
			LogicalExpression condition = (LogicalExpression) att
					.getAttribute(Constants.PR_CONDTION);
			// 显示条件属性是否正常
			if (!isLogicalExpressionOk(condition, root))
			{
				newatts.put(Constants.PR_CONDTION, getNewCondition(condition,
						dealmethod, root));
			}
			ConditionItemCollection dynamicStyle = (ConditionItemCollection) att
					.getAttribute(Constants.PR_DYNAMIC_STYLE);
			// 动态样式属性是否正常
			if (!isDynamicStyleOk(dynamicStyle, root))
			{
				newatts.put(Constants.PR_DYNAMIC_STYLE, getNewDynamicStyle(
						dynamicStyle, dealmethod, root));
			}
			ConnWith conwith = (ConnWith) att
					.getAttribute(Constants.PR_CONN_WITH);
			// 关联属性是否正常
			if (!isConnWithOK(conwith, root))
			{
				newatts.put(Constants.PR_CONN_WITH, getNewConwith(conwith,
						dealmethod, root));
			}
			SelectInfo selectinfo = (SelectInfo) att
					.getAttribute(Constants.PR_SELECT_INFO);
			// 下拉列表设置关联更新是否正常
			if (!isSelectInfoOK(selectinfo, root))
			{
				newatts.put(Constants.PR_SELECT_INFO, getNewSelectInfo(
						selectinfo, dealmethod, root));
			}
			if (!newatts.isEmpty())
			{
				if (element instanceof CellElement)
				{
					doc.setElementAttributes((CellElement) element, newatts,
							false);
				} else
				{
					element.setAttributes(newatts, false);
				}
			}
			// 如果是inline，则需要处理动态绑定
			if (element instanceof Inline)
			{
				// inline元素是否正常
				if (!isInLineBindingOK((Inline) element, root))
				{
					dealInline((Inline) element, root, doc, dealmethod);
				}
			}
			List<CellElement> children = element.getAllChildren();
			if (children != null && !children.isEmpty())
			{
				// 处理子元素
				for (CellElement child : children)
				{
					dealElement(root, dealmethod, child, doc);
				}
			}
		}
	}

	/*
	 * 根据dealmethod处理group属性，得到新的重复属性
	 */
	private Object getNewGroup(Group group, int dealmethod, BindNode root)
	{
		// 如果重复节点不存在，则返回null，则表示清除该组属性
		if (!isNodeIntheModel(group.getNode(), root))
		{
			return null;
		} else
		{
			LogicalExpression condition = group.getFliterCondition();

			if (!isLogicalExpressionOk(condition, root))
			{
				// 如果是只清除动态数据不存在的条件项，不是清除整个过滤属性，则处理，清除掉数据节点不存在的条件项
				if ((dealmethod & BindingSetingDialog.GROUPCONDITIONSELECT) == BindingSetingDialog.GROUPCONDITIONSELECT)
				{
					condition = dealCondition(condition, root);
				}
				// 清除掉整个过滤属性
				else
				{
					condition = null;
				}
			}
			List<Sort> sorts = group.getSortSet();
			if (!isSortListOK(sorts, root))
			{
				// 如果是只清除排序项，不清楚整个过滤属性，则清除数据节点不存在的过滤项
				if ((dealmethod & BindingSetingDialog.GROUPSORTSELECT) == BindingSetingDialog.GROUPSORTSELECT)
				{
					sorts = dealSorts(sorts, root);
				}
				// 否则，清除所有排序设置
				else
				{
					sorts = null;
				}
			}
			return Group.Instanceof(group.getNode(), condition, sorts, group
					.getEditmode(), group.getMax());
		}
	}

	/*
	 * 清除数据节点不存在的过滤项
	 */
	private List<Sort> dealSorts(List<Sort> sorts, BindNode root)
	{
		List<Sort> newsorts = new ArrayList<Sort>();
		for (Sort sort : sorts)
		{
			if (isNodeIntheModel(sort.getNode(), root))
			{
				newsorts.add(sort);
			}
		}
		if (newsorts.isEmpty())
		{
			newsorts = null;
		}
		return newsorts;
	}

	/*
	 * 清除数据节点不存在的条件项
	 */
	private LogicalExpression dealCondition(LogicalExpression condition,
			BindNode root)
	{
		List oldsubconditions = condition.getConditions();
		List<Integer> oldoperations = condition.getOperators();
		List newsubconditions = new ArrayList();
		List<Integer> newoperations = new ArrayList<Integer>();
		int size = oldsubconditions.size();
		int i = 0;
		for (Object cond : oldsubconditions)
		{

			if (cond instanceof Condition)
			{
				if (isConditionOk((Condition) cond, root))
				{
					if (!newsubconditions.isEmpty())
					{
						newoperations.add(oldoperations.get(i - 1));
					}
					newsubconditions.add(cond);
				}
			} else
			{
				LogicalExpression subcondition = dealCondition(
						(LogicalExpression) cond, root);
				if (subcondition != null)
				{
					if (!newsubconditions.isEmpty())
					{
						newoperations.add(oldoperations.get(i - 1));
					}
					newsubconditions.add(subcondition);
				}
			}
			i++;
		}
		// 清除单层嵌套组合条件的情况
		if (newsubconditions.size() == 1
				&& newsubconditions.get(0) instanceof LogicalExpression)
		{
			LogicalExpression suble = (LogicalExpression) newsubconditions
					.get(0);
			newsubconditions = suble.getConditions();
			newoperations = suble.getOperators();
		}
		return LogicalExpression.instance(newsubconditions, newoperations);
	}

	/*
	 * 处理显示条件属性
	 */
	private Object getNewCondition(LogicalExpression condition, int dealmethod,
			BindNode root)
	{
		// 如果设置是只清除条件项，尽可能保留条件属性的话，则指清楚数据节点不存在的条件项
		if ((dealmethod & BindingSetingDialog.CONDITIONSELECT) == BindingSetingDialog.CONDITIONSELECT)
		{
			return dealCondition(condition, root);
		}
		// 否则清除整个显示条件属性
		return null;
	}

	// 如果有关联更新设置，则直接清除关联更新属性
	private Object getNewConwith(ConnWith conwith, int dealmethod, BindNode root)
	{
		return null;
	}

	// 如果下拉列表的关联更新设置，则直接清除下拉列表的关联更新属性
	private Object getNewSelectInfo(SelectInfo selectinfo, int dealmethod,
			BindNode root)
	{
		return null;
	}

	/*
	 * 动态样式属性处理
	 */
	private Object getNewDynamicStyle(ConditionItemCollection dynamicStyles,
			int dealmethod, BindNode root)
	{
		// 如果dealmethod是只要有新结构中不存在的数据节点，则删除整个动态样式属性，则返回null，表示清除动态样式属性
		if ((dealmethod & BindingSetingDialog.DYNAMICSELECT) == BindingSetingDialog.DYNAMICSELECT)
		{
			return null;
		}
		ConditionItemCollection newdynamicStyles = new ConditionItemCollection();
		// 是否是只清除条件项，保留动态样式项
		boolean isnewdealcondition = (dealmethod & BindingSetingDialog.DYNAMICCONDITIONSELECT) == BindingSetingDialog.DYNAMICCONDITIONSELECT;
		for (ConditionItem dynamicStyle : dynamicStyles)
		{
			if (isLogicalExpressionOk(dynamicStyle.getCondition(), root))
			{
				newdynamicStyles.add(dynamicStyle);
			} else
			{
				// 是只清除条件项，保留动态样式项
				if (isnewdealcondition)
				{
					// 则清除动态数据节点不存在的条件项
					LogicalExpression newcondition = dealCondition(dynamicStyle
							.getCondition(), root);
					if (newcondition != null)
					{
						// 生成新的动态样式项
						newdynamicStyles.add(new ConditionItem(newcondition,
								dynamicStyle.getAttributes()));
					}
				}
			}
		}
		if (newdynamicStyles.isEmpty())
		{
			newdynamicStyles = null;
		}
		return newdynamicStyles;
	}

	/*
	 * 处理inline元素
	 */
	private void dealInline(Inline element, BindNode root, Document doc,
			int dealmethod)
	{
		// 如果是保留文档元素，只清除绑定属性
		if ((dealmethod & BindingSetingDialog.BINDNODESELECT) == BindingSetingDialog.BINDNODESELECT
				&& !(element instanceof TextInline)
				&& !(element instanceof ImageInline))
		{
			// 如果是统计图，则处理统计图相关的动态属性
			if (element instanceof ChartInline)
			{
				Chart chart = (Chart) element.getChildAt(0);
				if (chart == null)
				{
					return;
				}
				Map<Integer, Object> tosetattrs = new HashMap<Integer, Object>();
				Attributes attrs = chart.getAttributes();
				BarCodeText title = (BarCodeText) attrs
						.getAttribute(Constants.PR_TITLE);
				if (!isInlineContentOK(title, root))
				{
					tosetattrs.put(Constants.PR_TITLE, null);
				}
				List<BarCodeText> valuelabellist = (List<BarCodeText>) attrs
						.getAttribute(Constants.PR_VALUE_LABEL);
				if (!isBarCodeTextListOk(valuelabellist, root))
				{
					tosetattrs.put(Constants.PR_VALUE_LABEL, null);
				}
				List<BarCodeText> serieslabellist = (List<BarCodeText>) attrs
						.getAttribute(Constants.PR_SERIES_LABEL);
				if (!isBarCodeTextListOk(serieslabellist, root))
				{
					tosetattrs.put(Constants.PR_SERIES_LABEL, null);
				}
				BarCodeText domlabel = (BarCodeText) attrs
						.getAttribute(Constants.PR_DOMAINAXIS_LABEL);
				if (!isInlineContentOK(domlabel, root))
				{
					tosetattrs.put(Constants.PR_DOMAINAXIS_LABEL, null);
				}

				BarCodeText rangeaxislabel = (BarCodeText) attrs
						.getAttribute(Constants.PR_RANGEAXIS_LABEL);
				if (!isInlineContentOK(rangeaxislabel, root))
				{
					tosetattrs.put(Constants.PR_RANGEAXIS_LABEL, null);
				}
				ChartDataList seriesvalue = (ChartDataList) attrs
						.getAttribute(Constants.PR_SERIES_VALUE);
				if (!isChartDataListOk(seriesvalue, root))
				{
					tosetattrs.put(Constants.PR_SERIES_VALUE, null);
				}
				if (!tosetattrs.isEmpty())
				{
					doc.setElementAttributes(chart, tosetattrs, false);
				}
			} else
			{
				Map<Integer, Object> attrs = new HashMap<Integer, Object>();
				attrs.put(Constants.PR_INLINE_CONTENT, null);
				doc.setElementAttributes(element, attrs, false);
			}
		}
		// 动态文本元素，动态图片元素，或设置是清除文档元素时，则删除文档元素
		else
		{
			List<CellElement> elements = new ArrayList<CellElement>();
			elements.add(element);
			doc.deleteElements(elements);
		}

	}

	private boolean isAllBindingOk(Element element, DataStructureTreeModel model)
	{
		return isAllBindingOk(element, (BindNode) model.getRoot());
	}

	private boolean isAllBindingOk(Element element, BindNode root)
	{
		if(element instanceof ZiMoban)
		{
			return true;
		}
		Attributes att = element.getAttributes();
		if (att != null)
		{
			Object bgimage = att.getAttribute(Constants.PR_BACKGROUND_IMAGE);
			if (!isBackGroundImageOk(bgimage, root))
			{
				return false;
			}
			Group group = (Group) att.getAttribute(Constants.PR_GROUP);
			if (!isGroupOk(group, root))
			{
				return false;
			}
			LogicalExpression condition = (LogicalExpression) att
					.getAttribute(Constants.PR_CONDTION);
			if (!isLogicalExpressionOk(condition, root))
			{
				return false;
			}
			ConditionItemCollection dynamicStyle = (ConditionItemCollection) att
					.getAttribute(Constants.PR_DYNAMIC_STYLE);
			if (!isDynamicStyleOk(dynamicStyle, root))
			{
				return false;
			}
			if (element instanceof Inline)
			{
				return isInLineBindingOK((Inline) element, root);
			}
			int childcount = element.getChildCount();
			for (int i = 0; i < childcount; i++)
			{
				Element child = (Element) element.getChildAt(i);
				if (!isAllBindingOk(child, root))
				{
					return false;
				}
			}
		}
		return true;
	}

	private boolean isInLineBindingOK(Inline inline, BindNode root)
	{
		if (inline == null)
		{
			return true;
		}
		if (inline instanceof ChartInline)
		{
			Chart chart = (Chart) inline.getChildAt(0);
			if (chart == null)
			{
				return true;
			}
			Attributes attrs = chart.getAttributes();
			BarCodeText title = (BarCodeText) attrs
					.getAttribute(Constants.PR_TITLE);
			if (!isInlineContentOK(title, root))
			{
				return false;
			}
			List<BarCodeText> valuelabellist = (List<BarCodeText>) attrs
					.getAttribute(Constants.PR_VALUE_LABEL);
			if (!isBarCodeTextListOk(valuelabellist, root))
			{
				return false;
			}
			List<BarCodeText> serieslabellist = (List<BarCodeText>) attrs
					.getAttribute(Constants.PR_SERIES_LABEL);
			if (!isBarCodeTextListOk(serieslabellist, root))
			{
				return false;
			}
			BarCodeText domlabel = (BarCodeText) attrs
					.getAttribute(Constants.PR_DOMAINAXIS_LABEL);
			if (!isInlineContentOK(domlabel, root))
			{
				return false;
			}

			BarCodeText rangeaxislabel = (BarCodeText) attrs
					.getAttribute(Constants.PR_RANGEAXIS_LABEL);
			if (!isInlineContentOK(rangeaxislabel, root))
			{
				return false;
			}
			ChartDataList seriesvalue = (ChartDataList) attrs
					.getAttribute(Constants.PR_SERIES_VALUE);
			if (!isChartDataListOk(seriesvalue, root))
			{
				return false;
			}
			return true;
		} else if (inline instanceof ImageInline)
		{
			ExternalGraphic image = (ExternalGraphic) inline.getChildAt(0);
			if (image == null)
			{
				return true;
			}
			Object src = image.getAttribute(Constants.PR_SRC);
			if (src instanceof BindNode
					&& !isNodeIntheModel((BindNode) src, root))
			{
				return false;
			}
			return true;
		} else
		{
			Attributes attrs = inline.getAttributes();
			ConnWith conwith = (ConnWith) attrs
					.getAttribute(Constants.PR_CONN_WITH);
			if (!isConnWithOK(conwith, root))
			{
				return false;
			}
			SelectInfo selectinfo = (SelectInfo) attrs
					.getAttribute(Constants.PR_SELECT_INFO);
			if (!isSelectInfoOK(selectinfo, root))
			{
				return false;
			}
			return isInlineContentOK(inline.getContent(), root);
		}
	}

	private boolean isInlineContentOK(InlineContent inlinecontent, BindNode root)
	{
		if (inlinecontent != null && inlinecontent.isBindContent())
		{
			return isNodeIntheModel(inlinecontent.getBindNode(), root);
		}
		return true;
	}

	private boolean isBarCodeTextListOk(List<BarCodeText> barCodeTextlist,
			BindNode root)
	{
		if (barCodeTextlist == null || barCodeTextlist.isEmpty())
		{
			return true;
		}
		for (BarCodeText barCodeText : barCodeTextlist)
		{
			if (!isInlineContentOK(barCodeText, root))
			{
				return false;
			}
		}
		return true;
	}

	private boolean isBackGroundImageOk(Object bgimage, BindNode root)
	{
		if (!(bgimage instanceof BindNode))
		{
			return true;
		}
		return isNodeIntheModel((BindNode) bgimage, root);
	}

	private boolean isDynamicStyleOk(ConditionItemCollection dynamicStyles,
			BindNode root)
	{
		if (dynamicStyles == null || dynamicStyles.isEmpty())
		{
			return true;
		}
		for (ConditionItem dynamicStyle : dynamicStyles)
		{
			if (!isLogicalExpressionOk(dynamicStyle.getCondition(), root))
			{
				return false;
			}
		}
		return true;
	}

	private boolean isConnWithOK(ConnWith conwith, BindNode root)
	{
		if (conwith == null)
		{
			return true;
		}
		List<Parameter> params = conwith.getParms();
		if (params != null && !params.isEmpty())
		{
			for (Parameter param : params)
			{
				Object paramvalue = param.getValue();
				if (paramvalue instanceof BindNode
						&& !isNodeIntheModel((BindNode) paramvalue, root))
				{
					return false;
				}
			}
		}
		List<Formula> formulas = conwith.getFormulas();
		if (formulas != null && !formulas.isEmpty())
		{
			for (Formula formula : formulas)
			{
				if (!isNodeIntheModel(formula.getXpath(), root))
				{
					return false;
				}
			}
		}
		return true;
	}

	private boolean isSelectInfoOK(SelectInfo selectinfo, BindNode root)
	{
		if (selectinfo == null)
		{
			return true;
		}
		List<SelectColumnInFO> columninfoes = selectinfo.getColumninfos();
		for (SelectColumnInFO columninfo : columninfoes)
		{
			if (columninfo != null
					&& !isNodeIntheModel(columninfo.getOptionpath(), root))
			{
				return false;
			}
		}
		return true;
	}

	private boolean isNodeIntheModel(BindNode node, BindNode root)
	{
		if (node == null)
		{
			return true;
		}
		if (root == null)
		{
			return false;
		}
		if (node.xpathEqual(root))
		{
			return true;
		}
		int count = root.getChildCount();
		for (int i = 0; i < count; i++)
		{
			if (isNodeIntheModel(node, root.getChildAt(i)))
			{
				return true;
			}
		}
		return false;
	}

	private boolean isGroupOk(Group group, BindNode root)
	{
		if (group == null)
		{
			return true;
		}
		BindNode gnode = group.getNode();
		if (!isNodeIntheModel(gnode, root))
		{
			return false;
		}
		List<Sort> sorts = group.getSortSet();
		if (!isSortListOK(sorts, root))
		{
			return false;
		}
		LogicalExpression condition = group.getFliterCondition();
		if (!isLogicalExpressionOk(condition, root))
		{
			return false;
		}
		return true;
	}

	private boolean isLogicalExpressionOk(LogicalExpression condition,
			BindNode root)
	{
		if (condition == null)
		{
			return true;
		}
		List conditions = condition.getConditions();
		for (Object cond : conditions)
		{
			if (cond instanceof LogicalExpression)
			{
				if (!isLogicalExpressionOk((LogicalExpression) cond, root))
				{
					return false;
				}

			} else if (cond instanceof Condition)
			{
				Condition condi = (Condition) cond;
				if (!isConditionOk(condi, root))
				{
					return false;
				}
			}
		}
		return true;
	}

	private boolean isConditionOk(Condition condition, BindNode root)
	{
		if (condition == null)
		{
			return true;
		}
		if (!isNodeIntheModel(condition.getNode(), root))
		{
			return false;
		}
		Object parm = condition.getParam();
		if (parm instanceof BindNode
				&& !isNodeIntheModel((BindNode) parm, root))
		{
			return false;
		}
		return true;
	}

	private boolean isSortListOK(List<Sort> sorts, BindNode root)
	{
		if (sorts == null || sorts.isEmpty())
		{
			return true;
		}
		for (Sort sort : sorts)
		{
			if (!isNodeIntheModel(sort.getNode(), root))
			{
				return false;
			}
		}
		return true;
	}

	private boolean isChartDataListOk(ChartDataList seriesvalue, BindNode root)
	{
		if (seriesvalue == null || seriesvalue.isEmpty())
		{
			return true;
		}
		for (ChartData2D chd2d : seriesvalue)
		{
			if (chd2d != null)
			{
				NumberContent nc = chd2d.getNumbercontent();
				if (!isInlineContentOK(nc, root))
				{
					return false;
				}
			}
		}
		return true;
	}
}
