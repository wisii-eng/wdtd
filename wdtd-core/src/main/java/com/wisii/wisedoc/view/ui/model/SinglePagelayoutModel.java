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

package com.wisii.wisedoc.view.ui.model;

import java.util.Map;

import javax.swing.JList;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmlist.TreePanelSPMList;
import com.wisii.wisedoc.view.ui.update.RibbonUpdateManager;

/**
 * 针对不同情况下的当前编辑的单一页布局 1、比如当用户通过ribbon直接设置属性时是针对当前读到的页布局来进行设置
 * 2、当用户通过页布局序列来设置单一页布局属性的时候则是针对新建的默认的页布局来设置的
 * 
 * 所有的有关页布局的属性都通过这里设定，这里可以控制页布局具体设置到什么地方，是文档里还是页布局序列中
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/12
 */
public enum SinglePagelayoutModel
{

	Instance;

	/**
	 * 这个页布局设置属性的类型
	 * 
	 * @author 闫舒寰
	 * 
	 */
	public enum SPMLayoutType
	{
		setFO, // 设置FO属性，主要是通过Ribbon进行设置页布局属性的时候读取到的页布局
		addLayout, // 添加页布局，通过页布局序列属性设置对话框进行设置的时候添加的页布局
		editLayout; // 编辑页布局，通过页布局序列属性设置对话框进行设置的时候编辑的页布局
	}

	private SimplePageMasterModel currentSpmm = new SimplePageMasterModel.Builder()
			.build();

	// 当前编辑的页布局模型
	private SimplePageMasterModel spmm /*
										 * = new
										 * SimplePageMasterModel.Builder().
										 * build()
										 */;

	// 主编辑区的页布局模型
	private SimplePageMasterModel mainSPMM;

	public SimplePageMasterModel getCurrentSinglePageLayoutModel()
	{
		return currentSpmm;
	}

	/**
	 * 读取当前设置的页布局
	 * 
	 * @param layoutType
	 */
	public void readSimplePageMasterModel(SPMLayoutType layoutType)
	{
		if (layoutType == SPMLayoutType.setFO)
		{
			// 设置FO属性，主要是通过Ribbon进行设置页布局属性的时候读取到的页布局
			if (RibbonUIModel.getCurrentPropertiesByType() != null
					&& RibbonUIModel.getCurrentPropertiesByType().get(
							ActionType.LAYOUT) != null)
			{
				SimplePageMaster value = (SimplePageMaster) RibbonUIModel
						.getCurrentPropertiesByType().get(ActionType.LAYOUT)
						.get(Constants.PR_CURRENT_SIMPLE_PAGE_MASTER);
				if (value == null)
				{
					value = (SimplePageMaster) RibbonUIModel
					.getCurrentPropertiesByType().get(ActionType.LAYOUT)
					.get(Constants.PR_SIMPLE_PAGE_MASTER);
				}
				spmm = new SimplePageMasterModel.Builder().simplePageMaster(
						value).build();
			}

			// mainSPMM = new SimplePageMasterModel.Builder

			// System.out.println(RibbonUpdateManager.Instance.getMainPSProperty());

			// 获得当前主面板的页布局模型
			Map<Integer, Object> properties = RibbonUpdateManager.Instance
					.getMainPSProperty();
			if (properties != null)
			{
				SimplePageMaster value = (SimplePageMaster) properties
						.get(Constants.PR_SIMPLE_PAGE_MASTER);
				mainSPMM = new SimplePageMasterModel.Builder()
						.simplePageMaster(value).build();
			}

		} else if (layoutType == SPMLayoutType.addLayout)
		{
			// 添加页布局，通过页布局序列属性设置对话框进行设置的时候添加的页布局，采用默认页布局属性
			spmm = new SimplePageMasterModel.Builder()
					.defaultSimplePageMaster().build();
			if (MultiPagelayoutModel.MultiPageLayout
					.hasSimplePageMasterName(spmm.getMasterName()))
			{
				spmm.setMasterName("simple page master "
						+ MultiPagelayoutModel.getCount());
				mainSPMM = spmm;
			}
		} else if (layoutType == SPMLayoutType.editLayout)
		{
			// 编辑页布局，通过页布局序列属性设置对话框进行设置的时候编辑的页布局，采用当前列表中读取到的页布局属性
			JList list = TreePanelSPMList.getInstance().getList();
			spmm = MultiPagelayoutModel.MultiPageLayout
					.getSimplePageMaster((String) list.getSelectedValue());
			mainSPMM = spmm;
		}
	}

	/**
	 * 获得当前操作的SimplePageMasterModel对象
	 * 获取这个对象时需要先用readSimplePageMasterModel()方法读取到正确的spmm对象
	 * ，在单一页布局属性设置对话框中用(SimplePageMasterDialog)
	 * 
	 * @return SimplePageMasterModel对象
	 */
	public SimplePageMasterModel getSinglePageLayoutModel()
	{
		return spmm;
	}

	/**
	 * 这个是针对外部对象要读取当前页布局时，根据类别的不同而返回不同的页布局
	 * 
	 * @param layoutType
	 *            SimplePageMasterDialog类中的SPMLayoutType类别
	 * @return SimplePageMasterModel模型
	 */
	public SimplePageMasterModel getSinglePageLayoutModel(
			SPMLayoutType layoutType)
	{
		readSimplePageMasterModel(layoutType);
		return spmm;
	}

	public void setDefaultSinglePageLayoutModel()
	{
		spmm = new SimplePageMasterModel.Builder().defaultSimplePageMaster()
				.build();
	}

	/**
	 * 取得当前主编辑区的页布局模型
	 * 
	 * @return 主编辑区的页布局模型
	 */
	public SimplePageMasterModel getMainSPMM()
	{

		if (mainSPMM == null)
		{
			// 则证明没有通过主panel取得属性,设置成默认值
			mainSPMM = new SimplePageMasterModel.Builder().build();
		}

		return mainSPMM;
	}
}
