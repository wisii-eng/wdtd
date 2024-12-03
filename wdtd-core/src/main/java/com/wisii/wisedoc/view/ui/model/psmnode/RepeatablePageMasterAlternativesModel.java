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

import com.wisii.wisedoc.document.attribute.ConditionalPageMasterReference;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.RepeatablePageMasterAlternatives;
import com.wisii.wisedoc.document.attribute.SubSequenceSpecifier;

/**
 * 特殊位置引用模型
 * repeatable-page-master-alternatives
 * @author 闫舒寰
 * @version 1.0 2009/02/06
 */
public class RepeatablePageMasterAlternativesModel implements PageSequenceMasterChildren {
	
	private transient Object maximumRepeats;
	
	private transient List<ConditionalPageMasterReferenceModel> childrenList;
	
	public RepeatablePageMasterAlternativesModel() {
//		maximumRepeats = 0;
	}
	
	public RepeatablePageMasterAlternativesModel(RepeatablePageMasterAlternatives rpma) {
		this.maximumRepeats = rpma.getMaximumRepeats();
		
		List<ConditionalPageMasterReference> cpmrList = rpma.getPageMasterRefs();
		
		if (childrenList == null) {
			childrenList = new ArrayList<ConditionalPageMasterReferenceModel>();
		}
		
		for (int i = 0; i < cpmrList.size(); i++) {
			childrenList.add(i, new ConditionalPageMasterReferenceModel(cpmrList.get(i)));
		}
	}

	public Object getMaximumRepeats() {
		return maximumRepeats;
	}

	public void setMaximumRepeats(Object maximumRepeats) {
		this.maximumRepeats = maximumRepeats;
	}

	@Override
	public String toString() {
		return "特殊位置引用";//repeatable-page-master-alternatives
	}
	
	public void addChild (ConditionalPageMasterReferenceModel child){
		if (childrenList == null) {
			childrenList = new ArrayList<ConditionalPageMasterReferenceModel>();
		}
		if (!childrenList.contains(child)) {
			childrenList.add(child);
		}
	}
	
	public void addChild(DefaultMutableTreeNode parent){
		if (childrenList == null) {
			childrenList = new ArrayList<ConditionalPageMasterReferenceModel>();
		}
		childrenList.clear();
		for (int i = 0; i < parent.getChildCount(); i++) {
			DefaultMutableTreeNode rpmaNode = (DefaultMutableTreeNode)parent.getChildAt(i);
			if (rpmaNode.getUserObject() instanceof ConditionalPageMasterReferenceModel) {
				ConditionalPageMasterReferenceModel cpmrModel = (ConditionalPageMasterReferenceModel) rpmaNode.getUserObject();
				this.addChild(cpmrModel);
			}
		}
	}
	
	public List<ConditionalPageMasterReferenceModel> getConditionalPageMasterReferenceModelList(){
		return childrenList;
	}

	@Override
	public SubSequenceSpecifier getLayout() {
		
		List<ConditionalPageMasterReference> conditionalPageMasterRefs = new ArrayList<ConditionalPageMasterReference>();
		
		for (int i = 0; i < childrenList.size(); i++) {
			conditionalPageMasterRefs.add(childrenList.get(i).getConditionalPageMasterReference());
		}
		
		EnumNumber er = null;
		
		if (maximumRepeats instanceof EnumNumber) {
			er = (EnumNumber) maximumRepeats;
		} else if (maximumRepeats instanceof Integer) {
			er = new EnumNumber(-1 ,(Integer)maximumRepeats);
		}
		
		return new RepeatablePageMasterAlternatives(er, conditionalPageMasterRefs);
	}
}
