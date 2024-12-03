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

import com.wisii.wisedoc.document.attribute.SinglePageMasterReference;
import com.wisii.wisedoc.document.attribute.SubSequenceSpecifier;
import com.wisii.wisedoc.view.ui.model.MultiPagelayoutModel;
import com.wisii.wisedoc.view.ui.model.SimplePageMasterModel;

/**
 * 单次页布局引用模型
 * single-page-master-reference
 * @author 闫舒寰
 * @version 1.0 2009/02/09
 */
public class SinglePageMasterReferenceModel implements PageSequenceMasterChildren {
	
	private transient String masterReference;
	
	public SinglePageMasterReferenceModel() {
	}
	
	public SinglePageMasterReferenceModel(SinglePageMasterReference spmr) {
		SimplePageMasterModel spmm = new SimplePageMasterModel.Builder().simplePageMaster(spmr.getMasterReference()).build();
		//到simple page master列表所对应的多个simple page master登记一下
		MultiPagelayoutModel.MultiPageLayout.addSimplePageMasterModel(spmm);
		this.masterReference = spmm.getMasterName();
	}

	public String getMasterReference() {
		return masterReference;
	}

	public void setMasterReference(String masterReference) {
		this.masterReference = masterReference;
	}
	
	@Override
	public String toString() {
		return "单次引用";
	}

	@Override
	public SubSequenceSpecifier getLayout() {
		
		if (MultiPagelayoutModel.MultiPageLayout.getSimplePageMaster(masterReference) != null) {
			return new SinglePageMasterReference(MultiPagelayoutModel.MultiPageLayout.getSimplePageMaster(masterReference).getSimplePageMaster());
		}
		return null;
	}
}
