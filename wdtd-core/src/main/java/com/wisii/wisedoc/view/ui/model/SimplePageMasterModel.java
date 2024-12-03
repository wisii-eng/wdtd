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

import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CommonMarginBlock;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.LengthProperty;
import com.wisii.wisedoc.document.attribute.Region;
import com.wisii.wisedoc.document.attribute.RegionAfter;
import com.wisii.wisedoc.document.attribute.RegionBefore;
import com.wisii.wisedoc.document.attribute.RegionBody;
import com.wisii.wisedoc.document.attribute.RegionEnd;
import com.wisii.wisedoc.document.attribute.RegionStart;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.document.attribute.SpaceProperty;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;

/**
 * 
 * singler模式的SimplePageMaster的模型类，SimplePageMaster对象只能通过new来初始化其内部元素
 * 外界并不能直接设置里面的元素，并且初始化SimplePageMaster元素又涉及到其他众多的元素的初始化，这个 类专门用来处理这些的
 * 
 * @author 闫舒寰
 * @version 0.1 2008/10/16
 */
public class SimplePageMasterModel
{

	public static int simplePageMasterModelID;

	// 若是通过系统给定的simple page master对象来创建这个model，系统的simple page master放到这里保存以便比较
	private SimplePageMaster sourceSimplePageMaster;

	// 下面是该类的逻辑部分
	private String mediaUsage;
	// simple page master的名字，默认值为空名字
	private String masterName;

	private String virtualmastername;

	// SimplePageMaster格式化对象
	private SimplePageMaster simplePageMaster;

	// 页面的宽度值
	private Length pageWidth;

	// 页面的高度值
	private Length pageHeight;

	// 页边距
	private CommonMarginBlock pageMargion;

	// 为了区分起见默认设置的是body区域的四个边的值，这里加上了page前缀以表明这个是设置page的margin的属性。
	private Length pageMarginTop, pageMarginBottom, pageMarginLeft,
			pageMarginRight;

	// 构成page的margin区域的space属性
	EnumNumber bodyPrecedence;

	EnumProperty bodyConditionality;

	private SpaceProperty pageSpaceBefore, pageSpaceAfter;

	// 构成page的margin区域的属性
	private Length pageStartIndent, pageEndIndent;

	// 页面方向(不是纸张）
	private int pageRefOrientation;

	// 文字方向标识
	private int pageWritingMode;

	// =====================五个区域对象，region-body、region-before、region-after开始================//
	// 页面的几个区域Map
	private Map<Integer, Region> regions = new HashMap<Integer, Region>();

	// RegionBody对象
	private RegionBody regionBody;

	private RegionBodyModel regionBodyModel;

	// 页眉
	private RegionBefore regionBefore;

	private RegionBeforeModel regionBeforeModel;

	// 页脚
	private RegionAfter regionAfter;

	private RegionAfterModel regionAfterModel;

	// 左区域region-start
	private RegionStart regionStart;

	private RegionStartModel regionStartModel;

	// 右区域region-end
	private RegionEnd regionEnd;

	private RegionEndModel regionEndModel;

	// =====================五个区域对象，region-body、region-before、region-after结束=================//

	/***************************** 这里负责刷新所有需要的Object ************************************/
	/**
	 * 这里可以调用到getRegions，刷新一下Regions
	 * 
	 * 这个有点类似于递归调用，通过Region刷新body和页眉或、页脚，通过body刷新margin
	 * 
	 * @return
	 */
	public SimplePageMaster getSimplePageMaster()
	{
		this.simplePageMaster = new SimplePageMaster(getPageMargin(),
				getPageHeight(), getPageWidth(), getPageOrientation(),
				getPageWritingMode(), getRegions(), mediaUsage);

		simplePageMaster.setMastername(masterName);
		simplePageMaster.setVirtualMastername(virtualmastername);
		// System.err.println(simplePageMaster.getRegions());

		return simplePageMaster;
	}

	/**
	 * 这里刷新了body，并且判断是否有页眉和页脚，如果有则加到region中
	 * 
	 * @return the regions
	 */
	public Map<Integer, Region> getRegions()
	{

		regionBody = getRegionBody();

		regions.put(Constants.FO_REGION_BODY, regionBody);

		if (regionBefore == null)
		{
			// creatDefaultRegionBefore();
			if (regions.containsKey(Constants.FO_REGION_BEFORE))
			{
				regions.remove(Constants.FO_REGION_BEFORE);
			}
		} else
		{
			regions.put(Constants.FO_REGION_BEFORE, getRegionBefore());
		}

		if (regionAfter != null)
		{
			regions.put(Constants.FO_REGION_AFTER, getRegionAfter());
		} else
		{
			if (regions.containsKey(Constants.FO_REGION_AFTER))
			{
				regions.remove(Constants.FO_REGION_AFTER);
			}
		}

		if (regionStart != null)
		{
			regions.put(Constants.FO_REGION_START, getRegionStart());
		} else
		{
			if (regions.containsKey(Constants.FO_REGION_START))
			{
				regions.remove(Constants.FO_REGION_START);
			}
		}

		if (regionEnd != null)
		{
			regions.put(Constants.FO_REGION_END, getRegionEnd());
		} else
		{
			if (regions.containsKey(Constants.FO_REGION_END))
			{
				regions.remove(Constants.FO_REGION_END);
			}
		}

		// System.err.println(regions);

		return new HashMap<Integer, Region>(regions);
	}

	private CommonMarginBlock getPageMargin()
	{
		return new CommonMarginBlock(getPageMarginTop(), getPageMarginBottom(),
				getPageMarginLeft(), getPageMarginRight(),
				getPageSpaceBefore(), getPageSpaceAfter(),
				getPageStartIndent(), getPageEndIndent());
	}

	/***************************** 这里负责刷新所有需要的Object ************************************/

	/**
	 * 页布局默认的属性
	 */
	public void setDefault()
	{

		masterName = "default";
		virtualmastername = "default";
		pageHeight = new FixedLength(29.7d, "cm");

		pageWidth = new FixedLength(21d, "cm");

		pageRefOrientation = 0;

		pageWritingMode = Constants.EN_LR_TB;

		// ===============page的margion属性开始==========================//

		bodyPrecedence = new EnumNumber(-1, 0);
		bodyConditionality = new EnumProperty(Constants.EN_DISCARD, "");

		pageMarginTop = pageMarginBottom = pageMarginLeft = pageMarginRight = new FixedLength(
				0d, "cm");

		pageSpaceBefore = pageSpaceAfter = new SpaceProperty(
				(LengthProperty) pageMarginTop, bodyPrecedence,
				bodyConditionality);
		pageStartIndent = pageEndIndent = null;
		// ===============page的margion属性结束==========================//

		// 页眉
		regionBefore = null;
		// 页脚
		regionAfter = null;
		// 左区域
		regionStart = null;
		// 右区域
		regionEnd = null;

		regionBody = new RegionBodyModel.Builder().build().getRegionBody();

		regions.put(Constants.FO_REGION_BODY, regionBody);
		regions.put(Constants.FO_REGION_BEFORE, regionBefore);
		regions.put(Constants.FO_REGION_AFTER, regionAfter);
		regions.put(Constants.FO_REGION_START, regionStart);
		regions.put(Constants.FO_REGION_END, regionEnd);

		simplePageMaster = getSimplePageMaster();
	}

	private Length convertLength(double value, String measurement)
	{
		return new FixedLength(value, measurement);
	}

	private SpaceProperty convertSpace(double value, String measurement)
	{

		FixedLength length = new FixedLength(value, measurement);
		EnumProperty conditionality = new EnumProperty(Constants.EN_DISCARD, "");
		EnumNumber precedence = new EnumNumber(-1, 0);

		return new SpaceProperty(length, precedence, conditionality);
	}

	/**
	 * @return the pageWidth
	 */
	public Length getPageWidth()
	{
		return pageWidth;
	}

	/**
	 * @return the pageHeight
	 */
	public Length getPageHeight()
	{
		return pageHeight;
	}

	/**
	 * @return the pageOrientation
	 */
	public int getPageOrientation()
	{
		return pageRefOrientation;
	}

	/**
	 * @return the textDirection
	 */
	public int getPageWritingMode()
	{
		return pageWritingMode;
	}

	/**
	 * @param simplePageMaster
	 *            the simplePageMaster to set
	 */
	public void setSimplePageMaster(SimplePageMaster simplePageMaster)
	{
		this.simplePageMaster = simplePageMaster;
	}

	/**
	 * @param pageWidth
	 *            the pageWidth to set
	 */
	public void setPageWidth(double value, String measurement)
	{

		this.pageWidth = convertLength(value, measurement);
	}

	public void setPageWidth(FixedLength value)
	{

		this.pageWidth = value;
	}

	/**
	 * @param pageHeight
	 *            the pageHeight to set
	 */
	public void setPageHeight(double value, String measurement)
	{
		this.pageHeight = convertLength(value, measurement);
	}

	public void setPageHeight(FixedLength value)
	{
		this.pageHeight = value;
	}

	/**
	 * @param regions
	 *            the regions to set
	 */
	public void setRegions(Map<Integer, Region> regions)
	{
		this.regions = regions;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public void setRegionBody(RegionBody regionBody)
	{
		this.regionBody = regionBody;
		this.regionBodyModel = new RegionBodyModel.Builder().regionBody(
				regionBody).build();
	}

	/**
	 * @param pageOrientation
	 *            the pageOrientation to set
	 */
	public void setPageOrientation(int pageOrientation)
	{
		this.pageRefOrientation = pageOrientation;
	}

	/**
	 * @param textDirection
	 *            the textDirection to set
	 */
	public void setPageWritingMode(int writingModel)
	{
		this.pageWritingMode = writingModel;
	}

	private RegionBody getRegionBody()
	{
		if (regionBodyModel != null)
		{
			return regionBodyModel.getRegionBody();
		} else
		{
			return regionBody;
		}
	}

	private RegionBefore getRegionBefore()
	{
		if (regionBeforeModel != null)
		{
			return regionBeforeModel.getRegionBefore();
		} else
		{
			return regionBefore;
		}
	}

	public void creatDefaultRegionBefore()
	{

		// 目前设置页眉页脚还有问题，暂时不弄
		Length extent = new FixedLength(1d, "cm");

		regionBefore = new RegionBefore(Constants.EN_FALSE, extent, null, null,
				Constants.EN_TOP, Constants.EN_HIDDEN, null, 0,
				Constants.EN_LR_TB);
	}

	public void setRegionBefore(RegionBefore regionBefore)
	{
		if (regionBefore == null)
		{
			this.regionBeforeModel = null;
		} else
		{
			this.regionBeforeModel = new RegionBeforeModel.Builder()
					.regionBefore(regionBefore).build();
		}
		this.regionBefore = regionBefore;

	}

	private RegionAfter getRegionAfter()
	{
		if (regionAfterModel != null)
		{
			return regionAfterModel.getRegionAfter();
		} else
		{
			return regionAfter;
		}
	}

	public void setRegionAfter(RegionAfter regionAfter)
	{
		if (regionAfter == null)
		{
			this.regionAfterModel = null;
		} else
		{
			this.regionAfterModel = new RegionAfterModel.Builder().regionAfter(
					regionAfter).build();
		}
		this.regionAfter = regionAfter;
	}

	public RegionAfterModel getRegionAfterModel()
	{
		return regionAfterModel;
	}

	private RegionStart getRegionStart()
	{
		if (regionStartModel != null)
		{
			return regionStartModel.getRegionStart();
		} else
		{
			return regionStart;
		}
	}

	public void setRegionStart(RegionStart regionStart)
	{
		if (regionStart == null)
		{
			this.regionStartModel = null;
		} else
		{
			this.regionStartModel = new RegionStartModel.Builder().regionStart(
					regionStart).build();
		}
		this.regionStart = regionStart;
	}

	private RegionEnd getRegionEnd()
	{
		if (regionEndModel != null)
		{
			return regionEndModel.getRegionEnd();
		} else
		{
			return regionEnd;
		}
	}

	public void setRegionEnd(RegionEnd regionEnd)
	{
		if (regionEnd == null)
		{
			this.regionEndModel = null;
		} else
		{
			this.regionEndModel = new RegionEndModel.Builder().regionEnd(
					regionEnd).build();
		}
		this.regionEnd = regionEnd;
	}

	public RegionStartModel getRegionStartModel()
	{
		return regionStartModel;
	}

	public RegionEndModel getRegionEndModel()
	{
		return regionEndModel;
	}

	public Map<Integer, Object> getDefaultSimplePageMasterProperty()
	{
		this.setDefault();
		Map<Integer, Object> defaultSPM = new HashMap<Integer, Object>();
		defaultSPM.put(Constants.PR_SIMPLE_PAGE_MASTER, this.simplePageMaster);
		return defaultSPM;
	}

	public CommonMarginBlock getPageMargion()
	{
		return pageMargion;
	}

	public void setPageMargion(CommonMarginBlock pageMargion)
	{
		this.pageMargion = pageMargion;
	}

	public Length getPageMarginTop()
	{
		return pageMarginTop;
	}

	public void setPageMarginTop(double marginTop, String measurement)
	{
		this.pageMarginTop = convertLength(marginTop, measurement);
		this.pageSpaceBefore = new SpaceProperty(
				(LengthProperty) this.pageMarginBottom, bodyPrecedence,
				bodyConditionality);
	}

	public void setPageMarginTop(FixedLength marginTop)
	{
		this.pageMarginTop = marginTop;
		this.pageSpaceBefore = new SpaceProperty(
				(LengthProperty) this.pageMarginTop, bodyPrecedence,
				bodyConditionality);
	}

	public Length getPageMarginBottom()
	{
		return pageMarginBottom;
	}

	public void setPageMarginBottom(double marginBottom, String measurement)
	{
		this.pageMarginBottom = convertLength(marginBottom, measurement);
		this.pageSpaceAfter = new SpaceProperty(
				(LengthProperty) this.pageMarginBottom, bodyPrecedence,
				bodyConditionality);
	}

	public void setPageMarginBottom(FixedLength marginBottom)
	{
		this.pageMarginBottom = marginBottom;
		this.pageSpaceAfter = new SpaceProperty(
				(LengthProperty) this.pageMarginBottom, bodyPrecedence,
				bodyConditionality);
	}

	public Length getPageMarginLeft()
	{
		return pageMarginLeft;
	}

	public void setPageMarginLeft(double marginLeft, String measurement)
	{
		this.pageMarginLeft = convertLength(marginLeft, measurement);
	}

	public void setPageMarginLeft(FixedLength marginLeft)
	{
		this.pageMarginLeft = marginLeft;
	}

	public Length getPageMarginRight()
	{
		return pageMarginRight;
	}

	public void setPageMarginRight(double marginRight, String measurement)
	{
		this.pageMarginRight = convertLength(marginRight, measurement);
	}

	public void setPageMarginRight(FixedLength marginRight)
	{
		this.pageMarginRight = marginRight;
	}

	public SpaceProperty getPageSpaceBefore()
	{
		return pageSpaceBefore;
	}

	public void setPageSpaceBefore(double value, String measurement)
	{
		this.pageSpaceBefore = convertSpace(value, measurement);
	}

	public SpaceProperty getPageSpaceAfter()
	{
		return pageSpaceAfter;
	}

	public void setPageSpaceAfter(double value, String measurement)
	{
		this.pageSpaceAfter = convertSpace(value, measurement);
	}

	public Length getPageStartIndent()
	{
		return pageStartIndent;
	}

	public void setPageStartIndent(double value, String measurement)
	{
		this.pageStartIndent = convertLength(value, measurement);
	}

	public Length getPageEndIndent()
	{
		return pageEndIndent;
	}

	public void setPageEndIndent(double value, String measurement)
	{
		this.pageEndIndent = convertLength(value, measurement);
	}
    public void setMediaUsage(String mediaUsage)
    {
    	//去除掉空的以及空格的
    	if(mediaUsage!=null)
    	{
    		mediaUsage=mediaUsage.trim();
    		if(mediaUsage.isEmpty())
    		{
    			mediaUsage=null;
    		}
    	}
    	this.mediaUsage = mediaUsage;
    }
    /**
	 * @返回  mediaUsage变量的值
	 */
	public String getMediaUsage()
	{
		return mediaUsage;
	}
	/**
	 * 8个属性
	 * 
	 * @author 闫舒寰
	 * 
	 */
	public static class Builder
	{

		// 若是通过系统给定的simple page master对象来创建这个model，系统的simple page
		// master放到这里保存以便比较
		private SimplePageMaster sourceSimplePageMaster;
		private String mediaUsage;
		private String masterName;

		private String virtualmastername;

		// 为了区分起见默认设置的是body区域的四个边的值，这里加上了page前缀以表明这个是设置page的margin的属性。
		private Length pageMarginTop, pageMarginBottom, pageMarginLeft,
				pageMarginRight;

		// 构成page的margin区域的属性
		EnumNumber bodyPrecedence;

		EnumProperty bodyConditionality;

		private SpaceProperty pageSpaceBefore, pageSpaceAfter;

		// 构成page的margin区域的属性
		private Length pageStartIndent, pageEndIndent;

		// 页面的宽度值
		private Length pageWidth;

		// 页面的高度值
		private Length pageHeight;

		// 页面方向(不是纸张）
		private int pageRefOrientation;

		// 文字方向标识
		private int pageWritingMode;

		// RegionBody对象
		// Region区域
		private Map<Integer, Region> regions;

		// 版心区域
		private RegionBody regionBody;

		// 页眉
		private RegionBefore regionBefore;

		// 页脚
		private RegionAfter regionAfter;

		// 左区域region-start
		private RegionStart regionStart;

		// 右区域region-end
		private RegionEnd regionEnd;

		/**
		 * 页布局默认的属性
		 */
		private void setDefault()
		{

			// masterName = "default";
			// virtualmastername
			// 7.27.13
			pageHeight = new FixedLength(29.7d, "cm");
			// 7.27.15
			pageWidth = new FixedLength(21d, "cm");
			// 7.21.3
			pageRefOrientation = 0;
			// 7.29.7
			pageWritingMode = Constants.EN_LR_TB;

			// ===============page的margion属性开始==========================//
			// 7.11
			bodyPrecedence = new EnumNumber(-1, 0);
			bodyConditionality = new EnumProperty(Constants.EN_DISCARD, "");

			pageMarginTop = new FixedLength(1.5d, "cm");
			pageMarginBottom = new FixedLength(1.75d, "cm");
			pageMarginLeft = pageMarginRight = new FixedLength(2.17d, "cm");

			pageSpaceBefore = new SpaceProperty((LengthProperty) pageMarginTop,
					bodyPrecedence, bodyConditionality);
			pageSpaceAfter = new SpaceProperty(
					(LengthProperty) pageMarginBottom, bodyPrecedence,
					bodyConditionality);
			pageStartIndent = pageEndIndent = null;
			// ===============page的margion属性结束==========================//

			// 用默认的regionBody
			regionBody = new RegionBodyModel.Builder().build().getRegionBody();
			// 页眉
			regionBefore = new RegionBeforeModel.Builder().build()
					.getRegionBefore();
			// 页脚
			regionAfter = new RegionAfterModel.Builder().build()
					.getRegionAfter();
			// 左区域
			regionStart = new RegionStartModel.Builder().build()
					.getRegionStart();
			// 右区域
			regionEnd = new RegionEndModel.Builder().build().getRegionEnd();
		}

		private void getCurrentProperty()
		{
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

					this.masterName = spm.getMasterName();

					this.virtualmastername = spm.getVirtualMasterName();
					this.pageHeight = spm.getPageHeight();
					this.pageWidth = spm.getPageWidth();
					this.pageRefOrientation = spm.getReferenceOrientation();
					this.pageWritingMode = spm.getWritingMode();

					this.pageMarginTop = spm.getCommonMarginBlock()
							.getMarginTop();
					this.pageMarginBottom = spm.getCommonMarginBlock()
							.getMarginBottom();
					this.pageMarginLeft = spm.getCommonMarginBlock()
							.getMarginLeft();
					this.pageMarginRight = spm.getCommonMarginBlock()
							.getMarginRight();
					this.pageSpaceBefore = spm.getCommonMarginBlock()
							.getSpaceBefore();
					this.pageSpaceAfter = spm.getCommonMarginBlock()
							.getSpaceAfter();
					this.pageStartIndent = spm.getCommonMarginBlock()
							.getStartIndent();
					this.pageEndIndent = spm.getCommonMarginBlock()
							.getEndIndent();

					this.regionBody = (RegionBody) spm
							.getRegion(Constants.FO_REGION_BODY);
					this.regionBefore = (RegionBefore) spm
							.getRegion(Constants.FO_REGION_BEFORE);
					this.regionAfter = (RegionAfter) spm
							.getRegion(Constants.FO_REGION_AFTER);
					this.regionStart = (RegionStart) spm
							.getRegion(Constants.FO_REGION_START);
					this.regionEnd = (RegionEnd) spm
							.getRegion(Constants.FO_REGION_END);
					this.mediaUsage = spm.getMediaUsage();
				}
			}
		}

		private void getSimplePageMasterProperty(
				SimplePageMaster simplePageMaster)
		{
			if (simplePageMaster != null)
			{
				SimplePageMaster spm = simplePageMaster;

				this.masterName = spm.getMasterName();

				this.virtualmastername = spm.getVirtualMasterName();
				this.pageHeight = spm.getPageHeight();
				this.pageWidth = spm.getPageWidth();
				this.pageRefOrientation = spm.getReferenceOrientation();
				this.pageWritingMode = spm.getWritingMode();

				this.pageMarginTop = spm.getCommonMarginBlock().getMarginTop();
				this.pageMarginBottom = spm.getCommonMarginBlock()
						.getMarginBottom();
				this.pageMarginLeft = spm.getCommonMarginBlock()
						.getMarginLeft();
				this.pageMarginRight = spm.getCommonMarginBlock()
						.getMarginRight();
				this.pageSpaceBefore = spm.getCommonMarginBlock()
						.getSpaceBefore();
				this.pageSpaceAfter = spm.getCommonMarginBlock()
						.getSpaceAfter();
				this.pageStartIndent = spm.getCommonMarginBlock()
						.getStartIndent();
				this.pageEndIndent = spm.getCommonMarginBlock().getEndIndent();

				this.regionBody = (RegionBody) spm
						.getRegion(Constants.FO_REGION_BODY);
				this.regionBefore = (RegionBefore) spm
						.getRegion(Constants.FO_REGION_BEFORE);
				this.regionAfter = (RegionAfter) spm
						.getRegion(Constants.FO_REGION_AFTER);
				this.regionStart = (RegionStart) spm
						.getRegion(Constants.FO_REGION_START);
				this.regionEnd = (RegionEnd) spm
						.getRegion(Constants.FO_REGION_END);
				this.mediaUsage = spm.getMediaUsage();
			}
		}

		public Builder()
		{
			setDefault();
			getCurrentProperty();
		}

		/**
		 * 创建默认属性的页布局
		 */
		public Builder defaultSimplePageMaster()
		{
			setDefault();
			return this;
		}

		public Builder simplePageMaster(SimplePageMaster simplePageMaster)
		{
			setDefault();
			getSimplePageMasterProperty(simplePageMaster);
			this.sourceSimplePageMaster = simplePageMaster;
			return this;
		}

		public Builder masterName(String masterName)
		{
			this.masterName = masterName;
			return this;
		}

		public Builder VirtualmasterName(String virtualmastername)
		{
			this.virtualmastername = virtualmastername;
			return this;
		}

		public Builder pageWidth(double pageWidth, String measurement)
		{
			this.pageWidth = convertLength(pageWidth, measurement);
			return this;
		}

		public Builder pageWidth(Length pageWidth)
		{
			this.pageWidth = pageWidth;
			return this;
		}

		public Builder pageHeight(double pageHeight, String measurement)
		{
			this.pageHeight = convertLength(pageHeight, measurement);
			return this;
		}

		public Builder pageHeight(Length pageHeight)
		{
			this.pageHeight = pageHeight;
			return this;
		}

		public Builder pageRefOrientation(int pageRefOrientation)
		{
			this.pageRefOrientation = pageRefOrientation;
			return this;
		}

		public Builder pageWritingMode(int pageWritingMode)
		{
			this.pageWritingMode = pageWritingMode;
			return this;
		}

		// ===========设置margin属性开始==========================//
		public Builder pageMarginTop(FixedLength pageMarginTop)
		{
			this.pageMarginTop = pageMarginTop;
			this.pageSpaceBefore = new SpaceProperty(
					(LengthProperty) this.pageMarginTop, bodyPrecedence,
					bodyConditionality);
			return this;
		}

		public Builder pageMarginBottom(FixedLength pageMarginBottom)
		{
			this.pageMarginBottom = pageMarginBottom;
			this.pageSpaceAfter = new SpaceProperty(
					(LengthProperty) this.pageMarginBottom, bodyPrecedence,
					bodyConditionality);
			return this;
		}

		public Builder pageMarginLeft(FixedLength pageMarginLeft)
		{
			this.pageMarginLeft = pageMarginLeft;
			return this;
		}

		public Builder pageMarginRight(FixedLength pageMarginRight)
		{
			this.pageMarginRight = pageMarginRight;
			return this;
		}

		/*
		 * public Builder pageMarginTop(Double pageMarginTop, String
		 * measurement){ this.pageMarginTop = convertLength(pageMarginTop,
		 * measurement); this.pageSpaceBefore = new
		 * SpaceProperty((LengthProperty) this.pageMarginTop, bodyPrecedence,
		 * bodyConditionality); return this; }
		 * 
		 * public Builder pageMarginBottom(Double pageMarginBottom, String
		 * measurement){ this.pageMarginBottom = convertLength(pageMarginBottom,
		 * measurement); this.pageSpaceAfter = new
		 * SpaceProperty((LengthProperty) this.pageMarginBottom, bodyPrecedence,
		 * bodyConditionality); return this; }
		 * 
		 * public Builder pageMarginLeft(Double pageMarginLeft, String
		 * measurement){ this.pageMarginLeft = convertLength(pageMarginLeft,
		 * measurement); return this; }
		 * 
		 * public Builder pageMarginRight(Double pageMarginRight, String
		 * measurement){ this.pageMarginRight = convertLength(pageMarginRight,
		 * measurement); return this; }
		 */
		// ==============设置margin属性结束=================//

		public Builder regionBody(RegionBody regionBody)
		{
			this.regionBody = regionBody;
			return this;
		}

		public Builder regionBefore(RegionBefore regionBefore)
		{
			this.regionBefore = regionBefore;
			return this;
		}

		public Builder regionAfter(RegionAfter regionAfter)
		{
			this.regionAfter = regionAfter;
			return this;
		}

		public Builder regionStart(RegionStart regionStart)
		{
			this.regionStart = regionStart;
			return this;
		}

		public Builder regionEnd(RegionEnd regionEnd)
		{
			this.regionEnd = regionEnd;
			return this;
		}

		public SimplePageMasterModel build()
		{
			return new SimplePageMasterModel(this);
		}

		private Length convertLength(double value, String measurement)
		{
			return new FixedLength(value, measurement);
		}

		private SpaceProperty convertSpace(double value, String measurement)
		{

			FixedLength length = new FixedLength(value, measurement);
			EnumProperty conditionality = new EnumProperty(
					Constants.EN_DISCARD, "");
			EnumNumber precedence = new EnumNumber(-1, 0);

			return new SpaceProperty(length, precedence, conditionality);
		}

	}

	private SimplePageMasterModel(Builder builder)
	{

		simplePageMasterModelID += 1;

		this.setSourceSimplePageMaster(builder.sourceSimplePageMaster);
		this.mediaUsage = builder.mediaUsage;
		this.masterName = builder.masterName;

		this.virtualmastername = builder.virtualmastername;
		this.pageWidth = builder.pageWidth;
		this.pageHeight = builder.pageHeight;

		this.pageRefOrientation = builder.pageRefOrientation;
		this.pageWritingMode = builder.pageWritingMode;

		this.bodyPrecedence = builder.bodyPrecedence;
		this.bodyConditionality = builder.bodyConditionality;

		this.pageMarginTop = builder.pageMarginTop;
		this.pageMarginBottom = builder.pageMarginBottom;
		this.pageMarginLeft = builder.pageMarginLeft;
		this.pageMarginRight = builder.pageMarginRight;
		this.pageSpaceBefore = builder.pageSpaceBefore;
		this.pageSpaceAfter = builder.pageSpaceAfter;
		this.pageStartIndent = builder.pageStartIndent;
		this.pageEndIndent = builder.pageEndIndent;

		this.setRegionBody(builder.regionBody);
		this.setRegionBefore(builder.regionBefore);
		this.setRegionAfter(builder.regionAfter);
		this.setRegionStart(builder.regionStart);
		this.setRegionEnd(builder.regionEnd);
	}

	@Override
	public String toString()
	{
		/*
		 * if (masterName.equals("")) { return "default master"; }
		 */
		// return masterName;
		return virtualmastername;
	}

	public String getMasterName()
	{
		return masterName;
	}

	public String getVirtualMasterName()
	{
		return virtualmastername;
	}

	public void setMasterName(String masterName)
	{
		this.masterName = masterName;
	}

	public void setVirtualMasterName(String virtualmastername)
	{
		this.virtualmastername = virtualmastername;
	}

	public RegionBodyModel getRegionBodyModel()
	{
		return regionBodyModel;
	}

	public RegionBeforeModel getRegionBeforeModel()
	{
		return regionBeforeModel;
	}

	public SimplePageMaster getSourceSimplePageMaster()
	{
		return sourceSimplePageMaster;
	}

	private void setSourceSimplePageMaster(
			SimplePageMaster sourceSimplePageMaster)
	{
		this.sourceSimplePageMaster = sourceSimplePageMaster;
	}

	// @Override
	// public Object clone() throws CloneNotSupportedException
	// {
	//
	// SimplePageMasterModel clone = new SimplePageMasterModel.Builder()
	// .simplePageMaster(this.getSimplePageMaster()).build();
	// setName(clone, null, 0);
	// // 该克隆方法效率不高，以后有时间，拷贝每个属性
	// return clone;
	// }

	// public static void setName(SimplePageMasterModel spmm)
	// {
	// String name = spmm.getSimplePageMaster().getMasterName();
	// if (!(name == null || "default".equals(name) || "".equals(name)))
	// {
	// String spmname = name + "c";
	// spmname = MasterTree.isAdd(spmname);
	// spmm.setMasterName(spmname);
	// // String spmname = spmm.getSimplePageMaster().hashCode() + "";
	// spmm.setMasterName(spmname);
	//
	// RegionBeforeModel before = spmm.getRegionBeforeModel();
	// if (before != null)
	// {
	// String beforename = spmname + "before";
	// before.setRegionName(beforename);
	// // WisedocUtil.addStaticContentclone(beforename, sclist);
	// }
	// RegionAfterModel after = spmm.getRegionAfterModel();
	// if (after != null)
	// {
	// String aftername = spmname + "after";
	// after.setRegionName(aftername);
	// // WisedocUtil.addStaticContentclone(aftername, sclist);
	// }
	// RegionStartModel start = spmm.getRegionStartModel();
	// if (start != null)
	// {
	// String startname = spmname + "start";
	// start.setRegionName(startname);
	// // WisedocUtil.addStaticContentclone(startname, sclist);
	// }
	// RegionEndModel end = spmm.getRegionEndModel();
	// if (end != null)
	// {
	// String endname = spmname + "end";
	// end.setRegionName(endname);
	// // WisedocUtil.addStaticContentclone(endname, sclist);
	// }
	// }
	// }

	// public static void setName(SimplePageMasterModel spmm, String name)
	// {
	// spmm.setMasterName(name);
	// RegionBeforeModel before = spmm.getRegionBeforeModel();
	// if (before != null)
	// {
	// before.setRegionName(name + "before");
	// }
	// RegionAfterModel after = spmm.getRegionAfterModel();
	// if (after != null)
	// {
	// after.setRegionName(name + "after");
	// }
	// RegionStartModel start = spmm.getRegionStartModel();
	// if (start != null)
	// {
	// start.setRegionName(name + "start");
	// }
	// RegionEndModel end = spmm.getRegionEndModel();
	// if (end != null)
	// {
	// end.setRegionName(name + "end");
	// }
	// }
}
