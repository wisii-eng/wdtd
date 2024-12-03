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

package com.wisii.wisedoc.view.ui.actions.pagelayout;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.model.SimplePageMasterModel;
import com.wisii.wisedoc.view.ui.model.SinglePagelayoutModel;
import com.wisii.wisedoc.view.ui.model.SinglePagelayoutModel.SPMLayoutType;
import com.wisii.wisedoc.view.ui.text.Messages;
import com.wisii.wisedoc.view.ui.util.ViewUiUtil;

/**
 * pagelayout的属性设置和其他的属性设置略有不同，就是每次都需要传送一个完整的SimplePageMaster
 * 对象和key值为Constants.PR_SIMPLE_PAGE_MASTER作为Map的一组值。这里负责产生SimplePageMaster 对象
 * 
 * @author 闫舒寰
 * @version 0.1 2008/10/15
 */
@SuppressWarnings("serial")
public abstract class DefaultSimplePageMasterActions extends Actions
{

	SimplePageMasterModel spmm;

	private void initialSpmm()
	{
		spmm = SinglePagelayoutModel.Instance
				.getSinglePageLayoutModel(SPMLayoutType.setFO);
	}

	private SimplePageMasterModel getSpmm()
	{
		/*
		 * if (spmm == null) { spmm =
		 * SinglePagelayoutModel.Instance.getSinglePageLayoutModel
		 * (SPMLayoutType.setFO); }
		 */
		return spmm;
	}

	/**
	 * 设置页面的大小
	 * 
	 * @param pageWidth
	 *            页面的宽度
	 * @param pageHeight
	 *            页面的高度
	 * @param measurement
	 *            宽和高的单位
	 * @return
	 */
	public SimplePageMaster setPageSize(double pageWidth, double pageHeight,
			String measurement)
	{
		initialSpmm();
		getSpmm().setPageWidth(pageWidth, measurement);
		getSpmm().setPageHeight(pageHeight, measurement);
		return getSpmm().getSimplePageMaster();
		// return new SimplePageMasterModel.Builder().pageWidth(pageWidth,
		// measurement).pageHeight(pageHeight,
		// measurement).build().getSimplePageMaster();
	}

	/**
	 * 设置页面的方向
	 * 
	 * @param referenceOrien
	 *            页面旋转的的方向
	 * @return
	 */
	public SimplePageMaster setReferenceOrientation(int referenceOrien)
	{
		initialSpmm();
		getSpmm().setPageOrientation(referenceOrien);
		return getSpmm().getSimplePageMaster();
		// return new
		// SimplePageMasterModel.Builder().pageRefOrientation(referenceOrien).build().getSimplePageMaster();
	}

	/**
	 * 设置simple-page-master文本书写方向
	 * 
	 * @param textDirection
	 *            文本方向
	 * @return
	 */
	public SimplePageMaster setPageTextDirection(int textDirection)
	{
		initialSpmm();
		SimplePageMasterModel simplepagemastermodel = getSpmm();
		simplepagemastermodel.setPageWritingMode(textDirection);

		// 同时将五个子区域的书写方向设置成继承页的书写方向
		simplepagemastermodel.getRegionBodyModel().setWritingMode(-1);
		simplepagemastermodel.getRegionBeforeModel().setWritingMode(-1);
		simplepagemastermodel.getRegionAfterModel().setWritingMode(-1);
		simplepagemastermodel.getRegionStartModel().setWritingMode(-1);
		simplepagemastermodel.getRegionEndModel().setWritingMode(-1);
		return simplepagemastermodel.getSimplePageMaster();
		// return new
		// SimplePageMasterModel.Builder().pageWritingMode(textDirection).build().getSimplePageMaster();
	}

	/**
	 * 设置region-body文本书写方向
	 * 
	 * @param textDirection
	 *            文本方向
	 * @return
	 */
	public SimplePageMaster setPageBodyTextDirection(int textDirection)
	{
		initialSpmm();
		SimplePageMasterModel simplepagemastermodel = getSpmm();
		simplepagemastermodel.getRegionBodyModel()
				.setWritingMode(textDirection);
		return simplepagemastermodel.getSimplePageMaster();
		// return new SimplePageMasterModel.Builder().regionBody(
		// new
		// RegionBodyModel.Builder().writingMode(textDirection).build().getRegionBody()).build().getSimplePageMaster();
	}

	/**
	 * 设置四个边的margin
	 * 
	 * @param top
	 *            上
	 * @param bottom
	 *            下
	 * @param left
	 *            左
	 * @param right
	 *            右
	 * @param measurement
	 *            单位
	 * @return
	 */
	public SimplePageMaster setBodyMargin(FixedLength top, FixedLength bottom,
			FixedLength left, FixedLength right)
	{
		initialSpmm();
		getSpmm().getRegionBodyModel().setMarginTop(top);
		getSpmm().getRegionBodyModel().setMarginBottom(bottom);
		getSpmm().getRegionBodyModel().setMarginLeft(left);
		getSpmm().getRegionBodyModel().setMarginRight(right);
		return getSpmm().getSimplePageMaster();
		// return new SimplePageMasterModel.Builder().regionBody(
		// new RegionBodyModel.Builder()
		// .marginTop(top).marginBottom(bottom).marginLeft(left).marginRight(right)
		// .build().getRegionBody())
		// .build().getSimplePageMaster();
	}

	public SimplePageMaster setPageMargin(FixedLength top, FixedLength bottom,
			FixedLength left, FixedLength right)
	{
		initialSpmm();
		getSpmm().setPageMarginTop(top);
		getSpmm().setPageMarginBottom(bottom);
		getSpmm().setPageMarginLeft(left);
		getSpmm().setPageMarginRight(right);
		return getSpmm().getSimplePageMaster();
		// return new SimplePageMasterModel.Builder()
		// .pageMarginTop(top).pageMarginBottom(bottom).pageMarginLeft(left).pageMarginRight(right)
		// .build().getSimplePageMaster();
	}

	public SimplePageMaster setBodyBackgroundColor(Color bodyBackgroundColor)
	{
		initialSpmm();
		getSpmm().getRegionBodyModel().setBodyBackgroundColor(
				bodyBackgroundColor);
		return getSpmm().getSimplePageMaster();
		// return new SimplePageMasterModel.Builder().regionBody(new
		// RegionBodyModel.Builder().bodyBackgroundColor(bodyBackgroundColor).build().getRegionBody()).build().getSimplePageMaster();
	}

	public SimplePageMaster setBodyBackgroundImage(Object bodyBackgroundImage)
	{
		initialSpmm();
		getSpmm().getRegionBodyModel().setBodyBackgroundImage(
				bodyBackgroundImage);
		return getSpmm().getSimplePageMaster();
		// return new SimplePageMasterModel.Builder().regionBody(new
		// RegionBodyModel.Builder().bodyBackgroundImage(bodyBackgroundImage).build().getRegionBody()).build().getSimplePageMaster();
	}

	public SimplePageMaster removeBodyBackGroundImage()
	{
		initialSpmm();
		getSpmm().getRegionBodyModel().setBodyBackgroundImage(null);
		getSpmm().getRegionBodyModel()
				.setBodyBackgroundPositionHorizontal(null);
		getSpmm().getRegionBodyModel().setBodyBackgroundPositionVertical(null);
		return getSpmm().getSimplePageMaster();
		// return new SimplePageMasterModel.Builder().regionBody(new
		// RegionBodyModel.Builder().bodyBackgroundImage(null).bodyBackgroundPositionHorizontal(null).bodyBackgroundPositionVertical(null).build().getRegionBody()).build().getSimplePageMaster();
	}

	public SimplePageMaster setBodyBackgroundImageRepeat(
			int bodyBackgroundImageRepeat)
	{
		initialSpmm();
		getSpmm().getRegionBodyModel().setBodyBackgroundImageRepeat(
				bodyBackgroundImageRepeat);
		return getSpmm().getSimplePageMaster();
		// return new SimplePageMasterModel.Builder().regionBody(new
		// RegionBodyModel.Builder().bodyBackgroundImageRepeat(bodyBackgroundImageRepeat).build().getRegionBody()).build().getSimplePageMaster();
	}

	public SimplePageMaster setBodyBackgroundPositionHorizontal(
			Length bodyBackgroundPositionHorizontal)
	{
		initialSpmm();
		getSpmm().getRegionBodyModel().setBodyBackgroundPositionHorizontal(
				bodyBackgroundPositionHorizontal);
		return getSpmm().getSimplePageMaster();
		// return new SimplePageMasterModel.Builder().regionBody(new
		// RegionBodyModel.Builder().bodyBackgroundPositionHorizontal(bodyBackgroundPositionHorizontal).build().getRegionBody()).build().getSimplePageMaster();
	}

	public SimplePageMaster setBodyBackgroundPositionVertical(
			Length bodyBackgroundPositionVertical)
	{
		initialSpmm();
		getSpmm().getRegionBodyModel().setBodyBackgroundPositionVertical(
				bodyBackgroundPositionVertical);
		return getSpmm().getSimplePageMaster();
		// return new SimplePageMasterModel.Builder().regionBody(new
		// RegionBodyModel.Builder().bodyBackgroundPositionVertical(bodyBackgroundPositionVertical).build().getRegionBody()).build().getSimplePageMaster();
	}

	/**
	 * 创建默认的页眉
	 * 
	 * @return
	 */
	/*
	 * public SimplePageMaster createDefaultHeader(){ return new
	 * SimplePageMasterModel.Builder().build().getSimplePageMaster(); }
	 */

	/**
	 * 删除页眉
	 * 
	 * @return
	 */
	/*
	 * public SimplePageMaster cancelHeader(){
	 * SimplePageMasterModel.getInstance().setHeader(null);
	 * SimplePageMasterModel
	 * .getInstance().getRegions().remove(Constants.FO_REGION_BEFORE); return
	 * SimplePageMasterModel.getInstance().getSimplePageMaster(); }
	 */

	/**
	 * 设置分栏数
	 * 
	 * @param columnCount
	 *            分栏个数
	 * @return
	 */
	public SimplePageMaster setColumnCount(int columnCount)
	{
		initialSpmm();
		getSpmm().getRegionBodyModel().setColumnCount(columnCount);
		return getSpmm().getSimplePageMaster();
		// return new SimplePageMasterModel.Builder().regionBody(new
		// RegionBodyModel.Builder().columnCount(columnCount).build().getRegionBody()).build().getSimplePageMaster();
	}

	/**
	 * 设置栏间距
	 * 
	 * @param value
	 *            栏间距的值
	 * @param measurement
	 *            单位
	 * @return
	 */
	public SimplePageMaster setColumnGap(Double value, String measurement)
	{
		initialSpmm();
		getSpmm().getRegionBodyModel().setColumnGap(value, measurement);
		return getSpmm().getSimplePageMaster();
		// return new SimplePageMasterModel.Builder().regionBody(new
		// RegionBodyModel.Builder().columnGap(value,
		// measurement).build().getRegionBody()).build().getSimplePageMaster();
	}

	public SimplePageMaster setDisplayAlign(int displayAlign)
	{
		initialSpmm();
		getSpmm().getRegionBodyModel().setDisplayAlign(displayAlign);
		return getSpmm().getSimplePageMaster();
		// return new SimplePageMasterModel.Builder().regionBody(new
		// RegionBodyModel.Builder().displayAlign(displayAlign).build().getRegionBody()).build().getSimplePageMaster();
	}

	/**
	 * 设置页眉高度
	 * 
	 * @param extent
	 *            页眉高度
	 * @return
	 */
	public SimplePageMaster setRegionBeforeExtent(Length extent)
	{
		initialSpmm();
		getSpmm().getRegionBeforeModel().setExtent(extent);
		getSpmm().getRegionBodyModel().setMarginTop(extent);
		SinglePagelayoutModel.Instance.getMainSPMM().getRegionBodyModel()
				.setMarginTop(extent);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置页眉优先级
	 * 
	 * @param value
	 * @return
	 */
	public SimplePageMaster setRegionBeforePrecedence(int value)
	{
		initialSpmm();
		getSpmm().getRegionBeforeModel().setPrecedence(value);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置页眉中文字的写作方向
	 * 
	 * @param value
	 * @return
	 */
	public SimplePageMaster setRegionBeforeWritingMode(int value)
	{
		initialSpmm();
		getSpmm().getRegionBeforeModel().setWritingMode(value);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置页眉中文字的对齐方式
	 * 
	 * @param value
	 * @return
	 */
	public SimplePageMaster setRegionBeforeDisplayAlign(int value)
	{
		initialSpmm();
		getSpmm().getRegionBeforeModel().setDisplayAlign(value);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置页眉中文字的方向
	 * 
	 * @param value
	 * @return
	 */
	public SimplePageMaster setRegionBeforeReferenceOrientation(int value)
	{
		initialSpmm();
		getSpmm().getRegionBeforeModel().setReferenceOrientation(value);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置页眉中文字的溢出处理
	 * 
	 * @param value
	 * @return
	 */
	public SimplePageMaster setRegionBeforeOverflow(int value)
	{
		initialSpmm();
		getSpmm().getRegionBeforeModel().setOverflow(value);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置页眉背景颜色
	 * 
	 * @param value
	 * @return
	 */
	public SimplePageMaster setRegionBeforeColor(Color value)
	{
		initialSpmm();
		getSpmm().getRegionBeforeModel().setBodyBackgroundColor(value);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置页眉背景图片
	 * 
	 * @param bodyBackgroundImage
	 * @return
	 */
	public SimplePageMaster setRegionBeforeBackgroundImage(
			Object bodyBackgroundImage)
	{
		initialSpmm();
		getSpmm().getRegionBeforeModel().setBodyBackgroundImage(
				bodyBackgroundImage);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 移除页眉背景图片
	 * 
	 * @return
	 */
	public SimplePageMaster removeRegionBeforeBackGroundImage()
	{
		initialSpmm();
		getSpmm().getRegionBeforeModel().setBodyBackgroundImage(null);
		getSpmm().getRegionBeforeModel().setBodyBackgroundPositionHorizontal(
				null);
		getSpmm().getRegionBeforeModel()
				.setBodyBackgroundPositionVertical(null);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置页脚高度
	 * 
	 * @param extent
	 *            页脚高度
	 * @return
	 */
	public SimplePageMaster setRegionAfterExtent(Length extent)
	{
		initialSpmm();
		getSpmm().getRegionAfterModel().setExtent(extent);
		getSpmm().getRegionBodyModel().setMarginBottom(extent);
		SinglePagelayoutModel.Instance.getMainSPMM().getRegionBodyModel()
				.setMarginBottom(extent);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置页脚优先级
	 * 
	 * @param value
	 * @return
	 */
	public SimplePageMaster setRegionAfterPrecedence(int value)
	{
		initialSpmm();
		getSpmm().getRegionAfterModel().setPrecedence(value);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置页脚中文字的写作方向
	 * 
	 * @param value
	 * @return
	 */
	public SimplePageMaster setRegionAfterWritingMode(int value)
	{
		initialSpmm();
		getSpmm().getRegionAfterModel().setWritingMode(value);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置页脚中文字的对齐方式
	 * 
	 * @param value
	 * @return
	 */
	public SimplePageMaster setRegionAfterDisplayAlign(int value)
	{
		initialSpmm();
		getSpmm().getRegionAfterModel().setDisplayAlign(value);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置页脚中文字的方向
	 * 
	 * @param value
	 * @return
	 */
	public SimplePageMaster setRegionAfterReferenceOrientation(int value)
	{
		initialSpmm();
		getSpmm().getRegionAfterModel().setReferenceOrientation(value);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置页脚中文字的溢出处理
	 * 
	 * @param value
	 * @return
	 */
	public SimplePageMaster setRegionAfterOverflow(int value)
	{
		initialSpmm();
		getSpmm().getRegionAfterModel().setOverflow(value);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置页脚背景颜色
	 * 
	 * @param value
	 * @return
	 */
	public SimplePageMaster setRegionAfterColor(Color value)
	{
		initialSpmm();
		getSpmm().getRegionAfterModel().setBodyBackgroundColor(value);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置页脚背景图片
	 * 
	 * @param bodyBackgroundImage
	 * @return
	 */
	public SimplePageMaster setRegionAfterBackgroundImage(
			Object bodyBackgroundImage)
	{
		initialSpmm();
		getSpmm().getRegionAfterModel().setBodyBackgroundImage(
				bodyBackgroundImage);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 移除页脚背景图片
	 * 
	 * @return
	 */
	public SimplePageMaster removeRegionAfterBackGroundImage()
	{
		initialSpmm();
		getSpmm().getRegionAfterModel().setBodyBackgroundImage(null);
		getSpmm().getRegionAfterModel().setBodyBackgroundPositionHorizontal(
				null);
		getSpmm().getRegionAfterModel().setBodyBackgroundPositionVertical(null);
		return getSpmm().getSimplePageMaster();
	}

	/************ 左区域动作开始 ***********************************/

	/**
	 * 设置左区域高度
	 * 
	 * @param extent
	 *            左区域高度
	 * @return
	 */
	public SimplePageMaster setRegionStartExtent(Length extent)
	{
		initialSpmm();
		getSpmm().getRegionStartModel().setExtent(extent);
		getSpmm().getRegionBodyModel().setMarginLeft(extent);
		SinglePagelayoutModel.Instance.getMainSPMM().getRegionBodyModel()
				.setMarginLeft(extent);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置左区域优先级
	 * 
	 * @param value
	 * @return
	 */
	public SimplePageMaster setRegionStartPrecedence(int value)
	{
		initialSpmm();
		getSpmm().getRegionBeforeModel().setPrecedence(value);
		getSpmm().getRegionAfterModel().setPrecedence(value);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置左区域中文字的写作方向
	 * 
	 * @param value
	 * @return
	 */
	public SimplePageMaster setRegionStartWritingMode(int value)
	{
		initialSpmm();
		getSpmm().getRegionStartModel().setWritingMode(value);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置左区域中文字的对齐方式
	 * 
	 * @param value
	 * @return
	 */
	public SimplePageMaster setRegionStartDisplayAlign(int value)
	{
		initialSpmm();
		getSpmm().getRegionStartModel().setDisplayAlign(value);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置左区域中文字的方向
	 * 
	 * @param value
	 * @return
	 */
	public SimplePageMaster setRegionStartReferenceOrientation(int value)
	{
		initialSpmm();
		getSpmm().getRegionStartModel().setReferenceOrientation(value);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置左区域中文字的溢出处理
	 * 
	 * @param value
	 * @return
	 */
	public SimplePageMaster setRegionStartOverflow(int value)
	{
		initialSpmm();
		getSpmm().getRegionStartModel().setOverflow(value);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置左区域背景颜色
	 * 
	 * @param value
	 * @return
	 */
	public SimplePageMaster setRegionStartColor(Color value)
	{
		initialSpmm();
		getSpmm().getRegionStartModel().setBodyBackgroundColor(value);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置左区域背景图片
	 * 
	 * @param bodyBackgroundImage
	 * @return
	 */
	public SimplePageMaster setRegionStartBackgroundImage(
			Object bodyBackgroundImage)
	{
		initialSpmm();
		getSpmm().getRegionStartModel().setBodyBackgroundImage(
				bodyBackgroundImage);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 移除左区域背景图片
	 * 
	 * @return
	 */
	public SimplePageMaster removeRegionStartBackGroundImage()
	{
		initialSpmm();
		getSpmm().getRegionStartModel().setBodyBackgroundImage(null);
		getSpmm().getRegionStartModel().setBodyBackgroundPositionHorizontal(
				null);
		getSpmm().getRegionStartModel().setBodyBackgroundPositionVertical(null);
		return getSpmm().getSimplePageMaster();
	}

	/************ 右区域动作开始 ***********************************/

	/**
	 * 设置右区域高度
	 * 
	 * @param extent
	 *            右区域高度
	 * @return
	 */
	public SimplePageMaster setRegionEndExtent(Length extent)
	{
		initialSpmm();
		getSpmm().getRegionEndModel().setExtent(extent);
		getSpmm().getRegionBodyModel().setMarginRight(extent);
		SinglePagelayoutModel.Instance.getMainSPMM().getRegionBodyModel()
				.setMarginRight(extent);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置右区域优先级
	 * 
	 * @param value
	 * @return
	 */
	public SimplePageMaster setRegionEndPrecedence(int value)
	{
		initialSpmm();
		getSpmm().getRegionBeforeModel().setPrecedence(value);
		getSpmm().getRegionAfterModel().setPrecedence(value);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置右区域中文字的写作方向
	 * 
	 * @param value
	 * @return
	 */
	public SimplePageMaster setRegionEndWritingMode(int value)
	{
		initialSpmm();
		getSpmm().getRegionEndModel().setWritingMode(value);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置右区域中文字的对齐方式
	 * 
	 * @param value
	 * @return
	 */
	public SimplePageMaster setRegionEndDisplayAlign(int value)
	{
		initialSpmm();
		getSpmm().getRegionEndModel().setDisplayAlign(value);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置右区域中文字的方向
	 * 
	 * @param value
	 * @return
	 */
	public SimplePageMaster setRegionEndReferenceOrientation(int value)
	{
		initialSpmm();
		getSpmm().getRegionEndModel().setReferenceOrientation(value);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置右区域中文字的溢出处理
	 * 
	 * @param value
	 * @return
	 */
	public SimplePageMaster setRegionEndOverflow(int value)
	{
		initialSpmm();
		getSpmm().getRegionEndModel().setOverflow(value);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置右区域背景颜色
	 * 
	 * @param value
	 * @return
	 */
	public SimplePageMaster setRegionEndColor(Color value)
	{
		initialSpmm();
		getSpmm().getRegionEndModel().setBodyBackgroundColor(value);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 设置右区域背景图片
	 * 
	 * @param bodyBackgroundImage
	 * @return
	 */
	public SimplePageMaster setRegionEndBackgroundImage(
			Object bodyBackgroundImage)
	{
		initialSpmm();
		getSpmm().getRegionEndModel().setBodyBackgroundImage(
				bodyBackgroundImage);
		return getSpmm().getSimplePageMaster();
	}

	/**
	 * 移除右区域背景图片
	 * 
	 * @return
	 */
	public SimplePageMaster removeRegionEndBackGroundImage()
	{
		initialSpmm();
		getSpmm().getRegionEndModel().setBodyBackgroundImage(null);
		getSpmm().getRegionEndModel().setBodyBackgroundPositionHorizontal(null);
		getSpmm().getRegionEndModel().setBodyBackgroundPositionVertical(null);
		return getSpmm().getSimplePageMaster();
	}

	public abstract void doAction(ActionEvent e);

	@Override
	public boolean isAvailable()
	{

		// 在多种页布局下单一页布局属性按钮要设置成不可用
		if (RibbonUIModel.getCurrentPropertiesByType().get(this.actionType) == null)
		{
			return false;
		}

		// if (RibbonUIModel.getCurrentPropertiesByType().get(this.actionType)
		// .get(Constants.PR_CURRENT_SIMPLE_PAGE_MASTER) != null)

		if (ViewUiUtil.getCurrentSimplePageMaster(this.actionType) != null)
		{
			return true;
		} else
		{
			return false;
		}
	}

	protected void setFOProperties(Object result, SimplePageMaster current)
	{
		Map<Integer, Object> properties = new HashMap<Integer, Object>();
		if (result instanceof SimplePageMaster)
		{
			properties.put(Constants.PR_SIMPLE_PAGE_MASTER, result);
		} else if (result instanceof PageSequenceMaster)
		{
			properties.put(Constants.PR_PAGE_SEQUENCE_MASTER, result);
			properties.put(Constants.PR_CURRENT_SIMPLE_PAGE_MASTER, current);
		}
		setFOProperties(properties);
	}

	public String getString(int value)
	{
		String result = Messages.getString("wsd.view.gui.text.282");

		switch (value)
		{
			case 90:
				result = Messages.getString("wsd.view.gui.text.283");
				break;
			case 180:
				result = Messages.getString("wsd.view.gui.text.284");
				break;
			case 270:
				result = Messages.getString("wsd.view.gui.text.285");
				break;
			case -90:
				result = Messages.getString("wsd.view.gui.text.286");
				break;
			case -180:
				result = Messages.getString("wsd.view.gui.text.287");
				break;
			case -270:
				result = Messages.getString("wsd.view.gui.text.288");
				break;
			default:
				break;
		}
		return result;
	}

	public int getValue(int index)
	{
		int result = 0;

		switch (index)
		{
			case 1:
				result = 90;
				break;
			case 2:
				result = 180;
				break;
			case 3:
				result = 270;
				break;
			case 4:
				result = -90;
				break;
			case 5:
				result = -180;
				break;
			case 6:
				result = -270;
				break;
			default:
				break;
		}
		return result;
	}
}
