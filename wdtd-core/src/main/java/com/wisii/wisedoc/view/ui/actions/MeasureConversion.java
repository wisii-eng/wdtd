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
package com.wisii.wisedoc.view.ui.actions;

import com.wisii.wisedoc.resource.MessageConstants;

/**
 * 该类是用来转化用户所选择的单位到fo识别的单位
 * 
 * @author	闫舒寰
 * @since	2008/09/17
 */
public class MeasureConversion {
	
	//这里没有处理em这种情况，并且这个类的适用范围非常有限
	
	public static String getConversion(String measurement){
		
		if(measurement.endsWith(MessageConstants.GUI_COMMON + MessageConstants.CM)){
			return "cm";
		}else if(measurement.endsWith(MessageConstants.GUI_COMMON + MessageConstants.PT)){
			return "pt";
		}else if(measurement.endsWith(MessageConstants.GUI_COMMON + MessageConstants.PX)){
			return "px";
		}else if(measurement.endsWith(MessageConstants.GUI_COMMON + MessageConstants.IN)){
			return "in";
		} else {
			return null;
		}
		
	}
	
	public static String getIndexConversion(int index){
		if(index == 1){
			return "cm";
		}else if(index == 0){
			return "pt";
		}else if(index == 2){
			return "px";
		}else if(index == 3){
			return "in";
		}else if (index == 4){
			return "em";
		}else {
			return null;
		}
	}
	
	public static int getIndexConversion(String index){
		if(index.equals("cm")){
			return 1;
		}else if(index.equals("pt")){
			return 0;
		}else if(index.equals("px")){
			return 2;
		}else if(index.equals("in")){
			return 3;
		}else if (index.equals("em")){
			return 4;
		}else {
			return 0;
		}
	}
	

	/**
	 * 测试用
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MeasureConversion mc = new MeasureConversion();
//		System.out.println(mc.getConversion(MessageConstants.GUI_COMMON + MessageConstants.PT));
	}

}
