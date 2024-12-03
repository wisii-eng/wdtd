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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.view.ui.parts.dialogs.psmlist.TreePanelSPMList;

/**
 * 面对页布局序列所需要的多种页布局的整体管理类
 * @author 闫舒寰
 * @version 1.0 2009/02/12
 */
public enum MultiPagelayoutModel {
	
	MultiPageLayout;
	
	private static int count;
	
	Map<String, SimplePageMasterModel> simplePageMasterList;
	
	Map<SimplePageMaster, String> systemSimplePageMaster;
	
	/**
	 * 获得给定master name所对应的simplePageMastereModel
	 * @param masterName 所给定的master name
	 * @return
	 */
	public SimplePageMasterModel getSimplePageMaster(String masterName){
		return simplePageMasterList.get(masterName);
	}
	
	/**
	 * 这里注册登记simple page master，并且更新simple page master列表
	 * @param simplePageMasterModel
	 */
	public void addSimplePageMasterModel(SimplePageMasterModel simplePageMasterModel){
		
		if (simplePageMasterList == null) {
			simplePageMasterList = new HashMap<String, SimplePageMasterModel>();
		}
		
		if (!simplePageMasterList.containsKey(simplePageMasterModel.getMasterName())) {
			simplePageMasterList.put(simplePageMasterModel.getMasterName(), simplePageMasterModel);
			TreePanelSPMList.getInstance().addSimplePageMasterList(simplePageMasterModel);
		}
	}
	
	public boolean hasSimplePageMasterName(String name){
		if (simplePageMasterList == null) {
			return false;
		}
		return simplePageMasterList.containsKey(name);
	}

	public static int getCount() {
		return ++count;
	}
	
	/**
	 * 该方法是从page sequence即章节中读取所有的simple page master
	 * @param pageSequenceList
	 */
	public void getPageSequenceMaster(List<PageSequence> pageSequenceList){
		
		if (pageSequenceList == null || pageSequenceList.size() == 0) {
			return;
		}
		
		if (simplePageMasterList == null) {
			simplePageMasterList = new HashMap<String, SimplePageMasterModel>();
		}
		
		if (simplePageMasterList.size() == 0) {
			simplePageMasterList.clear();
		}
		
		List<SimplePageMaster> tempList = new ArrayList<SimplePageMaster>();
		
		for (int i = 0; i < pageSequenceList.size(); i++) {
			Object temp = pageSequenceList.get(i).getAttribute(Constants.PR_SIMPLE_PAGE_MASTER);
			
			if (temp != null) {
				if (temp instanceof SimplePageMaster) {
					SimplePageMaster spm = (SimplePageMaster) temp;
					//过滤一下相同的对象
					if (!tempList.contains(spm)) {
						tempList.add(spm);
						addSimplePageMaster(spm);
					}
				}
			}
		}
	}
	
	public static int change;
	
	public static int getChange(){
		return ++change;
	}
	private void addSimplePageMaster(SimplePageMaster spm){
		
		if (spm == null) {
			return;
		}
		
		if (systemSimplePageMaster == null) {
			systemSimplePageMaster = new HashMap<SimplePageMaster, String>();
		}
		
		if (systemSimplePageMaster.containsKey(spm)) {
			return;
		}
		
		SimplePageMasterModel spmm = new SimplePageMasterModel.Builder().simplePageMaster(spm).build();
		String defMasterName = null;
		if (spmm.getMasterName() == null || spmm.getMasterName().equals("")) {
			defMasterName = "simple page master " + getCount();
			spmm.setMasterName(defMasterName);
		} else {
			defMasterName = spmm.getMasterName();
		}
		
		if (!systemSimplePageMaster.containsValue(defMasterName)) {
			systemSimplePageMaster.put(spm, defMasterName);
		} else {
			spm.setMastername(spm.getMasterName() + " change" + getChange());
			spmm = new SimplePageMasterModel.Builder().simplePageMaster(spm).build();
			systemSimplePageMaster.put(spm, spm.getMasterName());
		}
		
		addSimplePageMasterModel(spmm);
	}
	
	public String getSystemSimplePageMasterName(SimplePageMaster spm){
		return systemSimplePageMaster.get(spm);
	}
}
