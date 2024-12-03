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
package com.wisii.wisedoc.view.ui.model.psmnode;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.SubSequenceSpecifier;

/**
 * 页布局序列模型
 * page-sequence-master model
 * @author 闫舒寰
 * @version 1.0 2009/02/09
 */
public class PageSequenceMasterModel {
	
	private transient String masterName;
	
	private transient List<PageSequenceMasterChildren> childrenList;
	
	public PageSequenceMasterModel(PageSequenceMaster psm) {
		
		String name = psm.getMasterName();
		
		if (name != null) {
			this.masterName = name;
		} else {
			this.masterName = "页布局序列";
		}
	}
	
	public PageSequenceMasterModel(String masterName) {
		this.masterName = masterName;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	@Override
	public String toString() {
		return masterName;
	}

	public void addChild (PageSequenceMasterChildren child){
		if (childrenList == null) {
			childrenList = new ArrayList<PageSequenceMasterChildren>();
		}
		childrenList.add(child);
	}
	
	public void addChild(DefaultMutableTreeNode parent){
		if (childrenList == null) {
			childrenList = new ArrayList<PageSequenceMasterChildren>();
		}
		childrenList.clear();
		for (int i = 0; i < parent.getChildCount(); i++) {
			DefaultMutableTreeNode psm = (DefaultMutableTreeNode)parent.getChildAt(i);
			if (psm.getUserObject() instanceof PageSequenceMasterChildren) {
				PageSequenceMasterChildren psmc = (PageSequenceMasterChildren) psm.getUserObject();
				this.addChild(psmc);
				
				if (psm.getUserObject() instanceof RepeatablePageMasterAlternativesModel) {
					RepeatablePageMasterAlternativesModel rpmaModel = (RepeatablePageMasterAlternativesModel) psm.getUserObject();
					rpmaModel.addChild(psm);
				}
			}
		}
	}
	
	public PageSequenceMaster getPageSequenceMaster(){
		
		PageSequenceMaster psm = new PageSequenceMaster(getLayoutList());
		psm.setMasterName(getMasterName());
		
		return psm;
	}
	
	private List<SubSequenceSpecifier> getLayoutList(){
		
		List<SubSequenceSpecifier> sssList = new ArrayList<SubSequenceSpecifier>();
		
		if (childrenList != null || childrenList.size() != 0) {
			for (int i = 0; i < childrenList.size(); i++) {
				sssList.add(childrenList.get(i).getLayout());
			}
			return sssList;
		}
		return null;
	}
}
