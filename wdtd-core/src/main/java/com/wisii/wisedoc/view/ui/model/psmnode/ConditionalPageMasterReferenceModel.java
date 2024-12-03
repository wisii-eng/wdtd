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

import com.wisii.wisedoc.document.attribute.ConditionalPageMasterReference;
import com.wisii.wisedoc.view.ui.model.MultiPagelayoutModel;
import com.wisii.wisedoc.view.ui.model.SimplePageMasterModel;

/**
 * 条件引用模型
 * @author 闫舒寰
 * @version 1.0 2009/02/05
 */
public class ConditionalPageMasterReferenceModel {
	
	private transient String masterReference;
	//这三个量都是用户选择的index，index是根据xsl规范中的顺序所定。
	private transient int pagePosition;//默认是any
	private transient int oddorEven;//默认是any
	private transient int blankorNotBlank;//默认是any
	
	public ConditionalPageMasterReferenceModel() {
		
	}
	
	public ConditionalPageMasterReferenceModel(ConditionalPageMasterReference cpmr) {
		this.pagePosition = cpmr.getPagePosition();
		this.oddorEven = cpmr.getOddOrEven();
		this.blankorNotBlank = cpmr.getBlankOrNotBlank();
		
		SimplePageMasterModel spmm = new SimplePageMasterModel.Builder().simplePageMaster(cpmr.getMasterReference()).build();
		MultiPagelayoutModel.MultiPageLayout.addSimplePageMasterModel(spmm);
		this.masterReference = spmm.getMasterName();
	}
	
	@Override
	public String toString() {
		return "条件引用";//conditional-page-master-reference
	}
	
	public String getAllProperties(){
		return "masterReference: " + masterReference + " pagePosition: " 
				+ pagePosition + " oddorEven: " + oddorEven + " blankorNotBlank: " + blankorNotBlank;
	}

	public String getMasterReference() {
		return masterReference;
	}

	public void setMasterReference(String masterReference) {
		this.masterReference = masterReference;
	}

	public int getPagePosition() {
		return (Integer)pagePosition;
	}
	
	public int getPagePositionIndex(){
		return (Integer) pagePosition;
	}

	public void setPagePosition(int pagePosition) {
		this.pagePosition = pagePosition;
	}


	public int getOddorEven() {
		return (Integer)oddorEven;
	}
	
	public int getOddorEvenIndex(){
		return (Integer)oddorEven;
	}


	public void setOddorEven(int oddorEven) {
		this.oddorEven = oddorEven;
	}


	public int getBlankorNotBlank() {
		return (Integer)blankorNotBlank;
	}
	
	public int getBlankorNotBlankIndex(){
		return (Integer)blankorNotBlank;
	}


	public void setBlankorNotBlank(int blankorNotBlank) {
		this.blankorNotBlank = blankorNotBlank;
	}
	
	public ConditionalPageMasterReference getConditionalPageMasterReference(){
		
		ConditionalPageMasterReference cpmr = new ConditionalPageMasterReference(MultiPagelayoutModel.MultiPageLayout.getSimplePageMaster(getMasterReference()).getSimplePageMaster(), 
				getPagePosition(), getOddorEven(), getBlankorNotBlank() );
		
		return cpmr;
	}
}
