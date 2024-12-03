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

import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.RepeatablePageMasterReference;
import com.wisii.wisedoc.document.attribute.SubSequenceSpecifier;
import com.wisii.wisedoc.view.ui.model.MultiPagelayoutModel;
import com.wisii.wisedoc.view.ui.model.SimplePageMasterModel;

/**
 * 多次引用模型
 * repeatable-page-master-reference model
 * @author 闫舒寰
 * @version 1.0 2009/02/09
 */
public class RepeatablePageMasterReferenceModel implements PageSequenceMasterChildren {
	
	private transient String masterReference;
	
	private transient Object maximumRepeats;//应该接收EnumNumber类型变量
	
	public RepeatablePageMasterReferenceModel(RepeatablePageMasterReference prmr) {
		
		SimplePageMasterModel spmm = new SimplePageMasterModel.Builder().simplePageMaster(prmr.getMasterReference()).build();
		
		MultiPagelayoutModel.MultiPageLayout.addSimplePageMasterModel(spmm);
		
		this.masterReference = spmm.getMasterName();
		this.maximumRepeats = prmr.getMaximumRepeats();
	}
	
	public RepeatablePageMasterReferenceModel() {
	}
	
	@Override
	public String toString() {
		return "多次引用";//repeatable-page-master-reference
	}

	public String getMasterReference() {
		return masterReference;
	}

	public void setMasterReference(String masterReference) {
		this.masterReference = masterReference;
	}

	public Object getMaximumRepeats() {
		return maximumRepeats;
	}

	public void setMaximumRepeats(Object maximumRepeats) {
		this.maximumRepeats = maximumRepeats;
	}

	@Override
	public SubSequenceSpecifier getLayout() {
		
		EnumNumber en = null;
		if (maximumRepeats instanceof Integer) {
			Integer repeats = (Integer) maximumRepeats;
			en = new EnumNumber(-1, repeats);
		}
		
		if (maximumRepeats instanceof EnumNumber) {
			EnumNumber value = (EnumNumber) maximumRepeats;
			en = value;
		}
		return new RepeatablePageMasterReference(en, MultiPagelayoutModel.MultiPageLayout.getSimplePageMaster(masterReference).getSimplePageMaster());
	}
}
