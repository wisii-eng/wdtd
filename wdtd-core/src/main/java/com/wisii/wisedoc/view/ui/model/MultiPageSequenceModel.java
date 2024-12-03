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

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.view.ui.parts.dialogs.CustomLayoutManager;

/**
 * 多种页布局序列的内部模型，目前（1.0）只支持一种页布局序列的设置，所以这个应该还没有被用到，然而一开始设计的时候是支持同时多种页布局序列一起设置的
 * @author 闫舒寰
 * @version 1.0 2009/02/12
 */
public enum MultiPageSequenceModel {
	
	MultiPageSeqModel;
	
	//文档中page sequence的集合，从头到尾的排列
	private List<PageSequence> pageSequenceList;
	//page sequence的属性集合
	private List<Map<Integer, Object>> pageSequenceProperties;
	
	private PageSequence currentSelectingSequence;
	
	public List<PageSequence> initialPageSequenceList(){
		
		//初始化page sequence列表
		if (pageSequenceList == null) {
			pageSequenceList = new ArrayList<PageSequence>();
		}
		
		if (pageSequenceList.size() != 0) {
			pageSequenceList.clear();
		}
		
		Document doc = SystemManager.getCurruentDocument();
		
		for (int i = 0; i < doc.getChildCount(); i++) {
			Object temp = doc.getChildAt(i);
			
			if (temp instanceof PageSequence) {
				PageSequence ps = (PageSequence) temp;
				
				pageSequenceList.add(ps);
				
//				System.out.println(ps);
				
//				doc.setElementAttributes(ps, null, false);
//				System.out.println(ps.getAttribute(Constants.PR_SIMPLE_PAGE_MASTER));
				
//				System.out.println("page sequence " + i);
			}
		}
		
		//初始化所有page sequence的属性
		if (pageSequenceProperties == null) {
			pageSequenceProperties = new ArrayList<Map<Integer,Object>>();
		}
		
		if (pageSequenceProperties.size() != 0) {
			pageSequenceProperties.clear();
		}
		
		for (int i = 0; i < pageSequenceList.size(); i++) {
			pageSequenceProperties.add(i, pageSequenceList.get(i).getAttributes().getAttributes());
			//由于element的getAttributes只能提供更改过的属性map，即里面没有默认值，所以需要挨个调用所用的属性id来初始化这个map
			pageSequenceProperties.get(i).put(Constants.PR_INITIAL_PAGE_NUMBER, pageSequenceList.get(i).getAttribute(Constants.PR_INITIAL_PAGE_NUMBER));
			pageSequenceProperties.get(i).put(Constants.PR_FORCE_PAGE_COUNT, pageSequenceList.get(i).getAttribute(Constants.PR_FORCE_PAGE_COUNT));
		}
		
		MultiPagelayoutModel.MultiPageLayout.getPageSequenceMaster(pageSequenceList);
		
		return pageSequenceList;
	}
	
	public List<PageSequence> getPageSequenceList() {
		
		return pageSequenceList;
	}
	
	public void setCurrentSelectingSequence(int index){
//		System.out.println(index);
		currentSelectingSequence = pageSequenceList.get(index);
		
		CustomLayoutManager.getInstance().checkPSPPanel();
	}

	public PageSequence getCurrentSelectingSequence() {
		/*for (Iterator iterator = pageSequenceList.iterator(); iterator.hasNext();) {
			PageSequence temp = (PageSequence) iterator.next();
			System.out.println(temp.getInitialPageNumber());
		}
		System.out.println("=========over===============");*/
		return currentSelectingSequence;
	}
	
	public void setPageSequenceProperty(Map<Integer, Object> property) {
		int index = pageSequenceList.indexOf(currentSelectingSequence);
		pageSequenceProperties.get(index).putAll(property);
	}
	
	public Map<Integer, Object> getCurrentSequenceProperty(){
		
		if (currentSelectingSequence == null) {
			return null;
		}
		
//		System.out.println(currentSelectingSequence + " : " + currentSelectingSequence.getAttribute(Constants.PR_INITIAL_PAGE_NUMBER));
		
		int index = pageSequenceList.indexOf(currentSelectingSequence);
		
		if (index == -1) {
			return null;
		}
		
//		System.err.println("index: " + index + pageSequenceProperties.get(index));
		return pageSequenceProperties.get(index);
	}
	
	public void setFOProperty(){
		
		
		
		Document doc = SystemManager.getCurruentDocument();
		
		Map<String, SimplePageMaster> spmTemp = new HashMap<String, SimplePageMaster>();
		
		for (int i = 0; i < pageSequenceList.size(); i++) {
//				pageSequenceProperties.get(i).put(Constants.PR_SIMPLE_PAGE_MASTER, value)
			
			Object temp = pageSequenceProperties.get(i).get(Constants.PR_SIMPLE_PAGE_MASTER);
			
			if (temp instanceof String) {
				String value = (String) temp;
				if (!spmTemp.containsKey(value)) {
					spmTemp.put(value, MultiPagelayoutModel.MultiPageLayout.getSimplePageMaster(value).getSimplePageMaster());
				}
				pageSequenceProperties.get(i).put(Constants.PR_SIMPLE_PAGE_MASTER, spmTemp.get(value));
//					pageSequenceProperties.get(i).put(Constants.PR_SIMPLE_PAGE_MASTER, spmTemp.get(value));
			}
			
			doc.setElementAttributes(pageSequenceList.get(i), pageSequenceProperties.get(i), false);
		}
		
		
		
//		doc.setElementAttributes(ps, null, false);
	}

}
