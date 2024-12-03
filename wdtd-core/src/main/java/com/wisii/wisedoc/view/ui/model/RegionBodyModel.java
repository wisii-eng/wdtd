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
import com.wisii.wisedoc.document.attribute.CommonMarginBlock;
import com.wisii.wisedoc.document.attribute.EnumLength;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.InitialManager;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.RegionBody;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;

/**
 * region-body的模型层，需要初始化29个参数
 * 
 * *
 * <table width="511" border="1">
 * <tr>
 * <td width="16">&nbsp;</td>
 * <td width="144">变量</td>
 * <td width="144">解释</td>
 * <td width="179">默认值</td>
 * </tr>
 * <tr>
 * <td>1</td>
 * <td>columnCount</td>
 * <td>分栏数</td>
 * <td>1</td>
 * </tr>
 * <tr>
 * <td>2</td>
 * <td>columnGap</td>
 * <td>栏间距</td>
 * <td>null</td>
 * </tr>
 * <tr>
 * <td>3</td>
 * <td>commonMarginBlock</td>
 * <td>body的margin属性 (10个属性)</td>
 * <td>需要建立一个CommonMarginBlock对象</td>
 * </tr>
 * <tr>
 * <td>4</td>
 * <td>layoutMaster</td>
 * <td>body的页布局
 * 这个一开始创建的时候需要设置成null，但是SimplePageMaster初始完毕以后需要把SimplePageMaster传递给region body
 * </td>
 * <td>null</td>
 * </tr>
 * <tr>
 * <td>5</td>
 * <td>commonBorderPaddingBackground</td>
 * <td>背景和边框</td>
 * <td>null（根据FO规范region-body的border和padding必须为0）</td>
 * </tr>
 * <tr>
 * <td>6</td>
 * <td>displayAlign</td>
 * <td>对齐方式</td>
 * <td>Constants.EN_TOP</td>
 * </tr>
 * <tr>
 * <td>7</td>
 * <td>overflow</td>
 * <td>overflow溢出方式</td>
 * <td>Constants.EN_AUTO</td>
 * </tr>
 * <tr>
 * <td>8</td>
 * <td>regionName</td>
 * <td>body的region名称</td>
 * <td>null</td>
 * </tr>
 * <tr>
 * <td>9</td>
 * <td>referenceOrientation</td>
 * <td>body的方向（referenceOrientation）</td>
 * <td>0</td>
 * </tr>
 * <tr>
 * <td>10</td>
 * <td>writingMode</td>
 * <td>body的内容的写作模式</td>
 * <td>Constants.EN_LR_TB</td>
 * </tr>
 * </table>
 * 
 * @author 闫舒寰
 * @version 0.1 2008/12/8
 * 
 */
public class RegionBodyModel
{

	// 分栏数
	private int columnCount;

	// 栏间距
	private Length columnGap;

	// ==============margin属性开始=====================//
	/**
	 * common region body and margin property
	 */
	// 这个是body区域的margin值，FOV中marginTop和marginBottom都没用，用space来代替的
	private Length marginTop, marginBottom, marginLeft, marginRight;

	// space属性，构成body区域margin的属性
	private SpaceProperty spaceBefore, spaceAfter;

	// start属性，构成body区域margin的属性
	private Length startIndent, endIndent;

	// space的优先级，原则上应该每个边都有一个优先级
	private EnumNumber bodyPrecedence;

	// 边的条件
	private EnumProperty bodyConditionality;

	// ==============margin属性结束=====================//

	// ==============背景属性开始========================//
	private Color bodyBackgroundColor;

	private Object bodyBackgroundImage;

	private int backgroundImageLayer;

	private EnumProperty bodyBackgroundImageRepeat;

	private Length bodyBackgroundPositionHorizontal;

	private Length bodyBackgroundPositionVertical;

	// ==============背景属性结束========================//

	/**
	 * <p>
	 * 显示对齐方式，可选值
	 * </p>
	 * <p>
	 * Constants.EN_TOP, Constants.EN_BEFORE, Constants.EN_CENTER,
	 * Constants.EN_AFTER
	 * </p>
	 */
	private int displayAlign;

	// <p>溢出方式</p> <p>可选值: Constants.EN_AUTO, Constants.EN_VISIBLE,
	// Constants.EN_HIDDEN, Constants.EN_SCROLL</p> <p>见7.21.2</p>
	private int overflow;

	// 内容方向
	private int bodyReferenceOrientation;

	// 文字方向标识
	private int writingMode;

	public RegionBody getRegionBody()
	{
		return new RegionBody(getColumnCount(), getColumnGap(),
				getRegionBodyMargin(), null/* 页布局 */,
				getRegionBodyBackground(), getDisplayAlign(), getOverflow(),
				null/* region-name */, getBodyReferenceOrientation(),
				getWritingMode());
	}

	public int getColumnCount()
	{
		return columnCount;
	}

	public Length getColumnGap()
	{
		return columnGap;
	}

	private CommonBorderPaddingBackground getRegionBodyBackground()
	{
		Map<Integer, Object> property = new HashMap<Integer, Object>();

		property.put(Constants.PR_BACKGROUND_COLOR, getBodyBackgroundColor());
		property.put(Constants.PR_BACKGROUND_IMAGE, getBodyBackgroundImage());
		property.put(Constants.PR_BACKGROUND_REPEAT,
				getBodyBackgroundImageRepeat());
		property.put(Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
				getBodyBackgroundPositionHorizontal());
		property.put(Constants.PR_BACKGROUND_POSITION_VERTICAL,
				getBodyBackgroundPositionVertical());
		CommonBorderPaddingBackground cbpb = new CommonBorderPaddingBackground(
				property);
		return cbpb;
	}

	private CommonMarginBlock getRegionBodyMargin()
	{
		return new CommonMarginBlock(getMarginTop(), getMarginBottom(),
				getMarginLeft(), getMarginRight(), getSpaceBefore(),
				getSpaceAfter(), getStartIndent(), getEndIndent());
	}

	public Length getMarginTop()
	{
		return marginTop;
	}

	public Length getMarginBottom()
	{
		return marginBottom;
	}

	public Length getMarginLeft()
	{
		return marginLeft;
	}

	public Length getMarginRight()
	{
		return marginRight;
	}

	public SpaceProperty getSpaceBefore()
	{
		return spaceBefore;
	}

	public SpaceProperty getSpaceAfter()
	{
		return spaceAfter;
	}

	public Length getStartIndent()
	{
		return startIndent;
	}

	public Length getEndIndent()
	{
		return endIndent;
	}

	public int getBodyReferenceOrientation()
	{
		return bodyReferenceOrientation;
	}

	public int getDisplayAlign()
	{
		return displayAlign;
	}

	public int getOverflow()
	{
		return overflow;
	}

	public int getWritingMode()
	{
		return writingMode;
	}

	public static class Builder
	{

		// 分栏数
		private int columnCount;

		// 栏间距
		private Length columnGap;

		// ==============margin属性开始=====================//
		/**
		 * common region body and margin property
		 */
		// 这个是body区域的margin值，FOV中marginTop和marginBottom都没用，用space来代替的
		private Length marginTop, marginBottom, marginLeft, marginRight;

		// space属性，构成body区域margin的属性
		private SpaceProperty spaceBefore, spaceAfter;

		// start属性，构成body区域margin的属性
		private Length startIndent, endIndent;

		// space的优先级，原则上应该每个边都有一个优先级
		private EnumNumber bodyPrecedence;

		// 边的条件
		private EnumProperty bodyConditionality;

		// ==============margin属性结束=====================//

		// ==============背景属性开始========================//
		private Color bodyBackgroundColor;

		private Object bodyBackgroundImage;

		private int backgroundImageLayer;

		private EnumProperty bodyBackgroundImageRepeat;

		private Length bodyBackgroundPositionHorizontal;

		private Length bodyBackgroundPositionVertical;

		// ==============背景属性结束========================//

		/**
		 * <p>
		 * 显示对齐方式，可选值
		 * </p>
		 * <p>
		 * Constants.EN_TOP, Constants.EN_BEFORE, Constants.EN_CENTER,
		 * Constants.EN_AFTER
		 * </p>
		 */
		private int displayAlign;

		// <p>溢出方式</p> <p>可选值: Constants.EN_AUTO, Constants.EN_VISIBLE,
		// Constants.EN_HIDDEN, Constants.EN_SCROLL</p> <p>见7.21.2</p>
		private int overflow;

		// 内容方向
		private int bodyReferenceOrientation;

		// 文字方向标识
		private int writingMode;

		/**
		 * 页布局默认的属性
		 */
		private void setDefault()
		{

			// 分栏数
			columnCount = 1;
			// 栏间距
			columnGap = null;

			// ===============region body的margion属性开始======================//
			marginTop = new FixedLength(1.04d, "cm");
			marginBottom = new FixedLength(0.79d, "cm");
			marginLeft = marginRight = new FixedLength(1.0d, "cm");
			bodyPrecedence = new EnumNumber(-1, 0);
			bodyConditionality = new EnumProperty(Constants.EN_DISCARD, "");
			spaceBefore = new SpaceProperty((LengthProperty) marginTop,
					bodyPrecedence, bodyConditionality);
			spaceAfter = new SpaceProperty((LengthProperty) marginBottom,
					bodyPrecedence, bodyConditionality);
			startIndent = endIndent = null;
			// ===============region body的margion属性结束======================//

			// ==============背景属性开始========================//
			bodyBackgroundColor = null;
			bodyBackgroundImage = null;
			backgroundImageLayer = 0;
			bodyBackgroundImageRepeat = (EnumProperty) InitialManager
					.getInitialValue(Constants.PR_BACKGROUND_REPEAT, null);
			bodyBackgroundPositionHorizontal = new EnumLength(
					Constants.EN_LEFT, null);
			bodyBackgroundPositionVertical = new EnumLength(Constants.EN_TOP,
					null);
			// ==============背景属性结束========================//

			displayAlign = Constants.EN_TOP;

			overflow = Constants.EN_HIDDEN;

			bodyReferenceOrientation = 0;

			writingMode = Constants.EN_LR_TB;
		}

		private void getCurrentProperty()
		{
			// 取得当前的页布局属性，并且复制到当前属性中
			if (RibbonUIModel.getCurrentPropertiesByType() == null
					|| RibbonUIModel.getCurrentPropertiesByType().get(
							ActionType.LAYOUT) == null)
			{
				return;
			}
			Object currnetValue = RibbonUIModel.getCurrentPropertiesByType()
					.get(ActionType.LAYOUT)
					.get(Constants.PR_SIMPLE_PAGE_MASTER);

			if (currnetValue != null)
			{
				if (currnetValue instanceof SimplePageMaster)
				{
					SimplePageMaster spm = (SimplePageMaster) currnetValue;
					RegionBody body = (RegionBody) spm
							.getRegion(Constants.FO_REGION_BODY);

					this.columnCount = body.getColumnCount();
					this.columnGap = body.getColumnGapLength();

					this.marginTop = body.getCommonMarginBlock().getMarginTop();
					this.marginBottom = body.getCommonMarginBlock()
							.getMarginBottom();
					this.marginLeft = body.getCommonMarginBlock()
							.getMarginLeft();
					this.marginRight = body.getCommonMarginBlock()
							.getMarginRight();
					this.spaceBefore = body.getCommonMarginBlock()
							.getSpaceBefore();
					this.spaceAfter = body.getCommonMarginBlock()
							.getSpaceAfter();
					this.startIndent = body.getCommonMarginBlock()
							.getStartIndent();
					this.endIndent = body.getCommonMarginBlock().getEndIndent();

					this.bodyBackgroundColor = body
							.getCommonBorderPaddingBackground()
							.getBackgroundColor();
					this.bodyBackgroundImage = body
							.getCommonBorderPaddingBackground()
							.getBackgroundImage();
					this.backgroundImageLayer = body
							.getCommonBorderPaddingBackground()
							.getBackgroundImageLayer();
					this.bodyBackgroundImageRepeat = new EnumProperty(body
							.getCommonBorderPaddingBackground()
							.getBackgroundRepeat(), "");
					this.bodyBackgroundPositionHorizontal = body
							.getCommonBorderPaddingBackground()
							.getBackgroundPositionHorizontal();
					this.bodyBackgroundPositionVertical = body
							.getCommonBorderPaddingBackground()
							.getBackgroundPositionVertical();

					this.displayAlign = body.getDisplayAlign();
					this.overflow = body.getOverflow();
					this.bodyReferenceOrientation = body
							.getRegionReferenceOrientation();
					this.writingMode = body.getRegionReferenceOrientation();
				}
			}
		}

		private void getRegionBodyProperty(RegionBody body)
		{

			this.columnCount = body.getColumnCount();
			this.columnGap = body.getColumnGapLength();

			this.marginTop = body.getCommonMarginBlock().getMarginTop();
			this.marginBottom = body.getCommonMarginBlock().getMarginBottom();
			this.marginLeft = body.getCommonMarginBlock().getMarginLeft();
			this.marginRight = body.getCommonMarginBlock().getMarginRight();
			this.spaceBefore = body.getCommonMarginBlock().getSpaceBefore();
			this.spaceAfter = body.getCommonMarginBlock().getSpaceAfter();
			this.startIndent = body.getCommonMarginBlock().getStartIndent();
			this.endIndent = body.getCommonMarginBlock().getEndIndent();

			this.bodyBackgroundColor = body.getCommonBorderPaddingBackground()
					.getBackgroundColor();
			this.bodyBackgroundImage = body.getCommonBorderPaddingBackground()
					.getBackgroundImage();
			this.backgroundImageLayer = body.getCommonBorderPaddingBackground()
					.getBackgroundImageLayer();
			this.bodyBackgroundImageRepeat = new EnumProperty(body
					.getCommonBorderPaddingBackground().getBackgroundRepeat(),
					"");
			this.bodyBackgroundPositionHorizontal = body
					.getCommonBorderPaddingBackground()
					.getBackgroundPositionHorizontal();
			this.bodyBackgroundPositionVertical = body
					.getCommonBorderPaddingBackground()
					.getBackgroundPositionVertical();

			this.displayAlign = body.getDisplayAlign();
			this.overflow = body.getOverflow();
			this.bodyReferenceOrientation = body
					.getRegionReferenceOrientation();
			this.writingMode = body.getRegionWritingMode();
		}

		/**
		 * 获得当前的region body属性
		 */
		public Builder()
		{
			setDefault();
			getCurrentProperty();
		}

		/**
		 * 获得默认的region body属性
		 * 
		 * @param type
		 */
		public Builder defaultRegionBody()
		{
			setDefault();
			return this;
		}

		/**
		 * 获得给定regionBody的属性
		 * 
		 * @param regionBody
		 * @return
		 */
		public Builder regionBody(RegionBody regionBody)
		{
			setDefault();
			getRegionBodyProperty(regionBody);
			return this;
		}

		/**
		 * 设置分栏数
		 * 
		 * @param columnCount
		 * @return
		 */
		public Builder columnCount(int columnCount)
		{
			this.columnCount = columnCount;
			return this;
		}

		/**
		 * 设置栏间距
		 * 
		 * @param columnGap
		 *            栏间距数值
		 * @param measurement
		 *            栏间距单位
		 * @return
		 */
		public Builder columnGap(Double columnGap, String measurement)
		{
			this.columnGap = convertLength(columnGap, measurement);
			return this;
		}

		// ===========设置margin属性开始==========================//
		public Builder marginTop(Length marginTop)
		{
			this.marginTop = marginTop;
			this.spaceBefore = new SpaceProperty(
					(LengthProperty) this.marginTop, bodyPrecedence,
					bodyConditionality);
			return this;
		}

		public Builder marginBottom(Length marginBottom)
		{
			this.marginBottom = marginBottom;
			this.spaceAfter = new SpaceProperty(
					(LengthProperty) this.marginBottom, bodyPrecedence,
					bodyConditionality);
			return this;
		}

		public Builder marginLeft(Length marginLeft)
		{
			this.marginLeft = marginLeft;
			return this;
		}

		public Builder marginRight(Length marginRight)
		{
			this.marginRight = marginRight;
			return this;
		}

		/*
		 * public Builder spaceBefore(Double spaceBefore, String measurement){
		 * this.spaceBefore = convertSpace(spaceBefore, measurement); return
		 * this; }
		 * 
		 * public Builder spaceAfter(Double spaceAfter, String measurement){
		 * this.spaceAfter = convertSpace(spaceAfter, measurement); return this;
		 * }
		 * 
		 * public Builder startIndent(Double startIndent, String measurement){
		 * this.startIndent = convertLength(startIndent, measurement); return
		 * this; }
		 * 
		 * public Builder endIndent(Double endIndent, String measurement){
		 * this.endIndent = convertLength(endIndent, measurement); return this;
		 * }
		 */
		// ==============设置margin属性结束=================//

		/**
		 * 文本对齐方式
		 */
		public Builder displayAlign(int displayAlign)
		{
			this.displayAlign = displayAlign;
			return this;
		}

		/**
		 * 溢出方式
		 * 
		 * @param overflow
		 * @return
		 */
		public Builder overflow(int overflow)
		{
			this.overflow = overflow;
			return this;
		}

		/**
		 * 文字方向
		 * 
		 * @param bodyReferenceOrientation
		 * @return
		 */
		public Builder bodyReferenceOrientation(int bodyReferenceOrientation)
		{
			this.bodyReferenceOrientation = bodyReferenceOrientation;
			return this;
		}

		/**
		 * 写作模式
		 * 
		 * @param writingMode
		 * @return
		 */
		public Builder writingMode(int writingMode)
		{
			this.writingMode = writingMode;
			return this;
		}

		public Builder bodyBackgroundColor(Color bodyBackgroundColor)
		{
			this.bodyBackgroundColor = bodyBackgroundColor;
			return this;
		}

		public Builder bodyBackgroundImage(Object bodyBackgroundImage)
		{
			this.bodyBackgroundImage = bodyBackgroundImage;
			return this;
		}

		public Builder bodyBackgroundImageLayer(int bodyBackgroundImageLayer)
		{
			this.backgroundImageLayer = bodyBackgroundImageLayer;
			return this;
		}

		public Builder bodyBackgroundImageRepeat(int bodyBackgroundImageRepeat)
		{
			this.bodyBackgroundImageRepeat = new EnumProperty(
					bodyBackgroundImageRepeat, "");
			return this;
		}

		public Builder bodyBackgroundPositionHorizontal(
				Length bodyBackgroundPositionHorizontal)
		{
			this.bodyBackgroundPositionHorizontal = bodyBackgroundPositionHorizontal;
			return this;
		}

		public Builder bodyBackgroundPositionVertical(
				Length bodyBackgroundPositionVertical)
		{
			this.bodyBackgroundPositionVertical = bodyBackgroundPositionVertical;
			return this;
		}

		/**
		 * 创建RegionBodyModel的模式
		 * 
		 * @return
		 */
		public RegionBodyModel build()
		{
			return new RegionBodyModel(this);
		}

		private Length convertLength(double value, String measurement)
		{
			return new FixedLength(value, measurement);
		}

	}

	private RegionBodyModel(Builder builder)
	{
		// 分栏数
		columnCount = builder.columnCount;
		// 栏间距
		columnGap = builder.columnGap;

		// ===============region body的margion属性开始======================//
		marginTop = builder.marginTop;
		marginBottom = builder.marginBottom;
		marginLeft = builder.marginLeft;
		marginRight = builder.marginRight;

		bodyPrecedence = new EnumNumber(-1, 0);
		bodyConditionality = new EnumProperty(Constants.EN_DISCARD, "");

		spaceBefore = new SpaceProperty((LengthProperty) marginTop,
				bodyPrecedence, bodyConditionality);
		spaceAfter = new SpaceProperty((LengthProperty) marginBottom,
				bodyPrecedence, bodyConditionality);
		startIndent = endIndent = null;
		// ===============region body的margion属性结束======================//

		displayAlign = builder.displayAlign;

		overflow = builder.overflow;

		bodyReferenceOrientation = builder.bodyReferenceOrientation;

		writingMode = builder.writingMode;

		// ==============背景属性开始========================//
		bodyBackgroundColor = builder.bodyBackgroundColor;
		bodyBackgroundImage = builder.bodyBackgroundImage;
		backgroundImageLayer = builder.backgroundImageLayer;
		bodyBackgroundImageRepeat = builder.bodyBackgroundImageRepeat;
		bodyBackgroundPositionHorizontal = builder.bodyBackgroundPositionHorizontal;
		bodyBackgroundPositionVertical = builder.bodyBackgroundPositionVertical;
		// ==============背景属性结束========================//
	}

	private Length convertLength(double value, String measurement)
	{
		return new FixedLength(value, measurement);
	}

	public Color getBodyBackgroundColor()
	{
		return bodyBackgroundColor;
	}

	public void setBodyBackgroundColor(Color bodyBackgroundColor)
	{
		this.bodyBackgroundColor = bodyBackgroundColor;
	}

	public Object getBodyBackgroundImage()
	{
		return bodyBackgroundImage;
	}

	public void setBodyBackgroundImage(Object bodyBackgroundImage)
	{
		this.bodyBackgroundImage = bodyBackgroundImage;
	}

	public int getBodyBackgroundImageLayer()
	{
		return backgroundImageLayer;
	}

	public void setBodyBackgroundImageLayer(int bodyBackgroundImageLayer)
	{
		this.backgroundImageLayer = bodyBackgroundImageLayer;
	}

	public EnumProperty getBodyBackgroundImageRepeat()
	{
		return bodyBackgroundImageRepeat;
	}

	public void setBodyBackgroundImageRepeat(int bodyBackgroundImageRepeat)
	{
		this.bodyBackgroundImageRepeat = new EnumProperty(
				bodyBackgroundImageRepeat, "");
	}

	public void setBodyBackgroundImageRepeat(
			EnumProperty bodyBackgroundImageRepeat)
	{
		this.bodyBackgroundImageRepeat = bodyBackgroundImageRepeat;
	}

	public Length getBodyBackgroundPositionHorizontal()
	{
		return bodyBackgroundPositionHorizontal;
	}

	public void setBodyBackgroundPositionHorizontal(
			Length bodyBackgroundPositionHorizontal)
	{
		this.bodyBackgroundPositionHorizontal = bodyBackgroundPositionHorizontal;
	}

	public Length getBodyBackgroundPositionVertical()
	{
		return bodyBackgroundPositionVertical;
	}

	public void setBodyBackgroundPositionVertical(
			Length bodyBackgroundPositionVertical)
	{
		this.bodyBackgroundPositionVertical = bodyBackgroundPositionVertical;
	}

	public EnumNumber getBodyPrecedence()
	{
		return bodyPrecedence;
	}

	public void setBodyPrecedence(EnumNumber bodyPrecedence)
	{
		this.bodyPrecedence = bodyPrecedence;
	}

	public EnumProperty getBodyConditionality()
	{
		return bodyConditionality;
	}

	public void setBodyConditionality(EnumProperty bodyConditionality)
	{
		this.bodyConditionality = bodyConditionality;
	}

	public void setColumnCount(int columnCount)
	{
		this.columnCount = columnCount;
	}

	public void setColumnGap(Double columnGap, String measurement)
	{
		this.columnGap = convertLength(columnGap, measurement);
	}

	public void setColumnGap(Length columnGap)
	{
		this.columnGap = columnGap;
	}

	/******************* margin 系列属性开始 *******************************/
	public void setMarginTop(Double value, String measurement)
	{
		this.marginTop = convertLength(value, measurement);
		this.spaceBefore = new SpaceProperty((LengthProperty) this.marginTop,
				bodyPrecedence, bodyConditionality);
	}

	public void setMarginTop(Length value)
	{
		this.marginTop = value;
		// 这里要注意上下边是space属性
		this.spaceBefore = new SpaceProperty((LengthProperty) this.marginTop,
				bodyPrecedence, bodyConditionality);
	}

	public void setMarginBottom(Double value, String measurement)
	{
		this.marginBottom = convertLength(value, measurement);
		this.spaceAfter = new SpaceProperty((LengthProperty) this.marginBottom,
				bodyPrecedence, bodyConditionality);
	}

	public void setMarginBottom(Length value)
	{
		this.marginBottom = value;
		// 这里要注意上下边是space属性
		this.spaceAfter = new SpaceProperty((LengthProperty) this.marginBottom,
				bodyPrecedence, bodyConditionality);
	}

	public void setMarginLeft(Double value, String measurement)
	{
		this.marginLeft = convertLength(value, measurement);
	}

	public void setMarginLeft(Length value)
	{
		this.marginLeft = value;
	}

	public void setMarginRight(Double value, String measurement)
	{
		this.marginRight = convertLength(value, measurement);
	}

	public void setMarginRight(Length value)
	{
		this.marginRight = value;
	}

	public void setSpaceBefore(Double value, String measurement)
	{
		// this.spaceBefore = spaceBefore;
		this.marginTop = convertLength(value, measurement);
		this.spaceBefore = new SpaceProperty((LengthProperty) this.marginTop,
				bodyPrecedence, bodyConditionality);
	}

	public void setSpaceAfter(Double value, String measurement)
	{
		// this.spaceAfter = spaceAfter;
		this.marginBottom = convertLength(value, measurement);
		this.spaceAfter = new SpaceProperty((LengthProperty) this.marginBottom,
				bodyPrecedence, bodyConditionality);
	}

	public void setStartIndent(Length startIndent)
	{
		this.startIndent = startIndent;
	}

	public void setEndIndent(Length endIndent)
	{
		this.endIndent = endIndent;
	}

	/******************* margin 系列属性结束 *******************************/

	public void setDisplayAlign(int displayAlign)
	{
		this.displayAlign = displayAlign;
	}

	public void setOverflow(int overflow)
	{
		this.overflow = overflow;
	}

	public void setBodyReferenceOrientation(int bodyReferenceOrientation)
	{
		this.bodyReferenceOrientation = bodyReferenceOrientation;
	}

	public void setWritingMode(int writingMode)
	{
		this.writingMode = writingMode;
	}

}
