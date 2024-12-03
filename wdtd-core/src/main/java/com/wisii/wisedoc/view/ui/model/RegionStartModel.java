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

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground;
import com.wisii.wisedoc.document.attribute.EnumLength;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.document.attribute.RegionStart;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;


/**
 * 
 * 
 <table width="609" border="1">
  <tr>
    <td width="8">&nbsp;</td>
    <td width="218">变量</td>
    <td width="123">解释</td>
    <td width="232">默认值</td>
  </tr>
  <tr>
    <td>1</td>
    <td>extent</td>
    <td>左区域的高度</td>
    <td>一般为FixedLength类型的属性，比如：Length extent = new FixedLength(1d, &quot;cm&quot;);</td>
  </tr>
  <tr>
    <td>2</td>
    <td>simplePageMaster</td>
    <td>左区域的页布局</td>
    <td>null</td>
  </tr>
  <tr>
    <td>3</td>
    <td>commonBorderPaddingBackground</td>
    <td>左区域的背景和边框属性</td>
    <td>null（根据FO规范region-body的border和padding必须为0）</td>
  </tr>
  <tr>
    <td>4</td>
    <td>displayAlign</td>
    <td>左区域的基线位置 </td>
    <td>Constants.EN_TOP</td>
  </tr>
  <tr>
    <td>5</td>
    <td>overflow</td>
    <td>溢出处理</td>
    <td>Constants.EN_AUTO</td>
  </tr>
  <tr>
    <td>6</td>
    <td>regionName</td>
    <td>左区域区域的名称</td>
    <td>null</td>
  </tr>
  <tr>
    <td>7</td>
    <td>referenceOrientation</td>
    <td>左区域的referenceOrientation</td>
    <td>0</td>
  </tr>
  <tr>
    <td>8</td>
    <td>writingMode</td>
    <td>左区域的写作模式 </td>
    <td>Constants.EN_LR_TB</td>
  </tr>
</table>
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/12
 */
public class RegionStartModel {
	
	//优先级
//	private int precedence;
	
	//高度
	private Length extent;
	
	//左区域的页布局
	private SimplePageMaster layoutMaster;
	
	//左区域的基线位置
	private int displayAlign;
	
	//溢出处理
	private int overflow;
	
	//左区域区域名称
	private String regionName;
	
	//左区域的referenceOrientation
	private int referenceOrientation; 
	
	//左区域的写作模式
	private int writingMode;
	
	//左区域的背景总属性
	private CommonBorderPaddingBackground commonBorderPaddingBackground;
	
	//==============背景属性开始========================//
	private Color bodyBackgroundColor;
	private Object bodyBackgroundImage;
	private int backgroundImageLayer;
	private EnumProperty bodyBackgroundImageRepeat;
	private Length bodyBackgroundPositionHorizontal;
	private Length bodyBackgroundPositionVertical;
	//==============背景属性结束========================//
	
	//这个用来处理没有四个区域的时候，应该创建个null的region
	private RegionStartModel regionModel = this;
	
	public RegionStart getRegionStart(){
		if (regionModel == null) {
			return null;
		} else {
			return new RegionStart(getExtent(), 
					getLayoutMaster(), getCommonBorderPaddingBackground(), getDisplayAlign(), 
					getOverflow(), getRegionName(), getReferenceOrientation(), getWritingMode());
		}
	}
	
	private CommonBorderPaddingBackground getRegionStartBackground(){
		Map<Integer, Object> property = new HashMap<Integer, Object>();
		
		property.put(Constants.PR_BACKGROUND_COLOR, getBodyBackgroundColor());
		property.put(Constants.PR_BACKGROUND_IMAGE, getBodyBackgroundImage());
		property.put(Constants.PR_BACKGROUND_REPEAT, getBodyBackgroundImageRepeat());
		property.put(Constants.PR_BACKGROUND_POSITION_HORIZONTAL, getBodyBackgroundPositionHorizontal());
		property.put(Constants.PR_BACKGROUND_POSITION_VERTICAL, getBodyBackgroundPositionVertical());
		CommonBorderPaddingBackground cbpb = new CommonBorderPaddingBackground(property);
		return cbpb;
	}
	
	public Length getExtent() {
		return extent;
	}
	public void setExtent(Length extent) {
		this.extent = extent;
	}
	public SimplePageMaster getLayoutMaster() {
		return layoutMaster;
	}
	public void setLayoutMaster(SimplePageMaster layoutMaster) {
		this.layoutMaster = layoutMaster;
	}
	public int getDisplayAlign() {
		return displayAlign;
	}
	public void setDisplayAlign(int displayAlign) {
		this.displayAlign = displayAlign;
	}
	public int getOverflow() {
		return overflow;
	}
	public void setOverflow(int overflow) {
		this.overflow = overflow;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public int getReferenceOrientation() {
		return referenceOrientation;
	}
	public void setReferenceOrientation(int referenceOrientation) {
		this.referenceOrientation = referenceOrientation;
	}
	public int getWritingMode() {
		return writingMode;
	}
	public void setWritingMode(int writingMode) {
		this.writingMode = writingMode;
	}
	public CommonBorderPaddingBackground getCommonBorderPaddingBackground() {
		return getRegionStartBackground();
	}
	public void setCommonBorderPaddingBackground(
			CommonBorderPaddingBackground commonBorderPaddingBackground) {
		this.commonBorderPaddingBackground = commonBorderPaddingBackground;
	}
	public Color getBodyBackgroundColor() {
		return bodyBackgroundColor;
	}
	public void setBodyBackgroundColor(Color bodyBackgroundColor) {
		this.bodyBackgroundColor = bodyBackgroundColor;
	}
	public Object getBodyBackgroundImage() {
		return bodyBackgroundImage;
	}
	public void setBodyBackgroundImage(Object bodyBackgroundImage) {
		this.bodyBackgroundImage = bodyBackgroundImage;
	}
	public int getBodyBackgroundImageLayer() {
		return backgroundImageLayer;
	}
	public void setBodyBackgroundImageLayer(int bodyBackgroundImageLayer) {
		this.backgroundImageLayer = bodyBackgroundImageLayer;
	}
	public EnumProperty getBodyBackgroundImageRepeat() {
		return bodyBackgroundImageRepeat;
	}
	public void setBodyBackgroundImageRepeat(EnumProperty bodyBackgroundImageRepeat) {
		this.bodyBackgroundImageRepeat = bodyBackgroundImageRepeat;
	}
	public Length getBodyBackgroundPositionHorizontal() {
		return bodyBackgroundPositionHorizontal;
	}
	public void setBodyBackgroundPositionHorizontal(
			Length bodyBackgroundPositionHorizontal) {
		this.bodyBackgroundPositionHorizontal = bodyBackgroundPositionHorizontal;
	}
	public Length getBodyBackgroundPositionVertical() {
		return bodyBackgroundPositionVertical;
	}
	public void setBodyBackgroundPositionVertical(
			Length bodyBackgroundPositionVertical) {
		this.bodyBackgroundPositionVertical = bodyBackgroundPositionVertical;
	}
	
	public static class Builder {
		
		//高度
		private Length extent;
		
		//左区域的页布局
		private SimplePageMaster layoutMaster;
		
		//左区域的基线位置
		private int displayAlign;
		
		//溢出处理
		private int overFlow;
		
		//左区域区域名称
		private String regionName;
		
		//左区域的referenceOrientation
		private int referenceOrientation; 
		
		//左区域的写作模式
		private int writingMode;
		
		//左区域的背景总属性
		private CommonBorderPaddingBackground commonBorderPaddingBackground;
		
		//==============背景属性开始========================//
		private Color bodyBackgroundColor;
		private Object bodyBackgroundImage;
		private int backgroundImageLayer;
		private EnumProperty bodyBackgroundImageRepeat;
		private Length bodyBackgroundPositionHorizontal;
		private Length bodyBackgroundPositionVertical;
		//==============背景属性结束========================//
		
		//这个用来处理没有四个区域的时候，应该创建个null的region
		private Builder builder = this;
		
		private void setDefault(){
			extent = new FixedLength(10.0d, "mm");
			layoutMaster = null;
			commonBorderPaddingBackground = null;
			displayAlign = Constants.EN_TOP;
			overFlow = Constants.EN_HIDDEN;
			regionName = null;
			referenceOrientation = 0;
			writingMode = Constants.EN_LR_TB;
			
			//==============背景属性开始========================//
			bodyBackgroundColor = null;
			bodyBackgroundImage = null;
			backgroundImageLayer=0;
			bodyBackgroundImageRepeat = (EnumProperty) InitialManager.getInitialValue(Constants.PR_BACKGROUND_REPEAT, null);
			bodyBackgroundPositionHorizontal = new EnumLength(Constants.EN_LEFT, null);
			bodyBackgroundPositionVertical = new EnumLength(Constants.EN_TOP, null);
			//==============背景属性结束========================//
		}
		
		private void getCurrentProperty(){
			//取得当前的页布局属性，并且复制到当前属性中
			if (RibbonUIModel.getCurrentPropertiesByType() == null || RibbonUIModel.getCurrentPropertiesByType().get(ActionType.LAYOUT) == null) {
				return;
			}
			Object currnetValue =  RibbonUIModel.getCurrentPropertiesByType().get(ActionType.LAYOUT).get(Constants.PR_SIMPLE_PAGE_MASTER);
			
			if (currnetValue != null) {
				if (currnetValue instanceof SimplePageMaster) {
					SimplePageMaster spm = (SimplePageMaster) currnetValue;
					RegionStart regionStart = (RegionStart) spm.getRegion(Constants.FO_REGION_START);
					
					if (regionStart != null) {
						this.extent = regionStart.getExtent();
						this.displayAlign = regionStart.getDisplayAlign();
						this.overFlow = regionStart.getOverflow();
						this.referenceOrientation = regionStart.getRegionReferenceOrientation();
						this.regionName = regionStart.getRegionName();
						this.writingMode = regionStart.getRegionWritingMode();
						this.commonBorderPaddingBackground = regionStart.getCommonBorderPaddingBackground();
						CommonBorderPaddingBackground cbpbg = regionStart.getCommonBorderPaddingBackground();
						this.bodyBackgroundColor = cbpbg.getBackgroundColor();
						this.bodyBackgroundImage = cbpbg.getBackgroundImage();
						 	this.backgroundImageLayer = cbpbg.getBackgroundImageLayer();
						this.bodyBackgroundImageRepeat = new EnumProperty(cbpbg.getBackgroundRepeat(),"");
						this.bodyBackgroundPositionHorizontal = cbpbg.getBackgroundPositionHorizontal();
						this.bodyBackgroundPositionVertical = cbpbg.getBackgroundPositionVertical();
					}
				}
			}
		}
		
		private void getRegionStartProperty(RegionStart regionStart){
			this.extent = regionStart.getExtent();
			this.displayAlign = regionStart.getDisplayAlign();
			this.overFlow = regionStart.getOverflow();
			this.referenceOrientation = regionStart.getRegionReferenceOrientation();
			this.regionName = regionStart.getRegionName();
			this.writingMode = regionStart.getRegionWritingMode();
			this.commonBorderPaddingBackground = regionStart.getCommonBorderPaddingBackground();
			CommonBorderPaddingBackground cbpbg = regionStart.getCommonBorderPaddingBackground();
			this.bodyBackgroundColor = cbpbg.getBackgroundColor();
			this.bodyBackgroundImage = cbpbg.getBackgroundImage();
			 this.backgroundImageLayer = cbpbg.getBackgroundImageLayer();
			this.bodyBackgroundImageRepeat = new EnumProperty(cbpbg.getBackgroundRepeat(),"");
			this.bodyBackgroundPositionHorizontal = cbpbg.getBackgroundPositionHorizontal();
			this.bodyBackgroundPositionVertical =cbpbg.getBackgroundPositionVertical();
		}
		
		/**
		 * 获得当前的region body属性
		 */
		public Builder(){
			setDefault();
			getCurrentProperty();
		}
		
		/**
		 * 获得默认的region body属性
		 * @param type
		 */
		public Builder defaultRegionStart(){
			setDefault();
			return this;
		}
		
		/**
		 * 获得给定regionBody的属性
		 * @param regionBody
		 * @return
		 */
		public Builder regionStart(RegionStart regionStart){
			if (regionStart != null) {
				setDefault();
				getRegionStartProperty(regionStart);
				return this;
			} else {
				this.builder = null;
				return this;
			}
			
		}
		
		public Builder extent(Length extent) {
			this.extent = extent;
			return this;
		}
		
		public Builder displayAlign(int displayAlign){
			this.displayAlign = displayAlign;
			return this;
		}
		
		public Builder overFlow(int overFlow){
			this.overFlow = overFlow;
			return this;
		}
		
		public Builder regionName(String regionName){
			this.regionName = regionName;
			return this;
		}
		
		public Builder referenceOrientation(int referenceOrientation){
			this.referenceOrientation = referenceOrientation;
			return this;
		}
		
		public Builder writingMode(int writingMode){
			this.writingMode = writingMode;
			return this;
		}
		
		public Builder commonBorderPanddingBackground(CommonBorderPaddingBackground commonBorderPaddingBackground){
			this.commonBorderPaddingBackground = commonBorderPaddingBackground;
			return this;
		}
		
		public Builder bodyBackgroundColor(Color bodyBackgroundColor){
			this.bodyBackgroundColor = bodyBackgroundColor;
			return this;
		}
		
		public Builder bodyBackgroundImage(Object bodyBackgroundImage){
			this.bodyBackgroundImage = bodyBackgroundImage;
			return this;
		}
		public Builder bodyBackgroundImageLayer(int bodyBackgroundImageLayer){
			this.backgroundImageLayer = bodyBackgroundImageLayer;
			return this;
		}
		
		public Builder bodyBackgroundImageRepeat(EnumProperty bodyBackgroundImageRepeat){
			this.bodyBackgroundImageRepeat = bodyBackgroundImageRepeat;
			return this;
		}
		
		public Builder bodyBackgroundPositionHorizontal(Length bodyBackgroundPositionHorizontal){
			this.bodyBackgroundPositionHorizontal = bodyBackgroundPositionHorizontal;
			return this;
		}
		
		public Builder bodyBackgroundPositionVertical(Length bodyBackgroundPositionVertical){
			this.bodyBackgroundPositionVertical = bodyBackgroundPositionVertical;
			return this;
		}
		
		/**
		 * 创建RegionBodyModel的模式
		 * @return
		 */
		public RegionStartModel build(){
			if (this.builder == null) {
				return new RegionStartModel(null);
			} else {
				return new RegionStartModel(this);
			}
		}
		
		private Length convertLength(double value, String measurement){
			return new FixedLength(value, measurement);
		}
		
	}
	
	private RegionStartModel(Builder builder) {
		if (builder == null) {
			this.regionModel = null;
		} else {
			this.extent = builder.extent;
			this.displayAlign = builder.displayAlign;
			this.overflow = builder.overFlow;
			this.regionName = builder.regionName;
			this.referenceOrientation = builder.referenceOrientation;
			this.writingMode = builder.writingMode;
			
			this.commonBorderPaddingBackground = builder.commonBorderPaddingBackground;
			
			this.bodyBackgroundColor = builder.bodyBackgroundColor;
			this.bodyBackgroundImage = builder.bodyBackgroundImage;
			 	this.backgroundImageLayer = builder.backgroundImageLayer;
			this.bodyBackgroundImageRepeat = builder.bodyBackgroundImageRepeat;
			this.bodyBackgroundPositionHorizontal = builder.bodyBackgroundPositionHorizontal;
			this.bodyBackgroundPositionVertical = builder.bodyBackgroundPositionVertical;
		}
	}
}


