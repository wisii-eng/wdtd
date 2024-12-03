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
/**
 * @SimplePageMaster.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.datatype.Length;

/**
 * 类功能描述：页布局属性类
 * 
 * 作者：zhangqiang 创建日期：2008-8-21
 */
public class SimplePageMaster
{

	// margin属性
	private CommonMarginBlock _commonMarginBlock;

	// 页高
	private Length _pageHeight;

	// 页宽
	private Length _pageWidth;

	// 旋转属性
	private int _referenceOrientation;

	// 书写模式
	private int _writingMode = Constants.EN_LR_TB;

	// 区域定义
	private Map<Integer, Region> _regions;

	private String _mediaUsage;

	private String mastername;

	private String virtualmastername;

	/**
	 * 初始化过程的描述
	 * 
	 * <table width="783" border="1">
	 * <tr>
	 * <td width="98">&nbsp;</td>
	 * <td width="213">变量</td>
	 * <td width="222">默认值</td>
	 * <td width="222">解释</td>
	 * </tr>
	 * <tr>
	 * <td>1、</td>
	 * <td>7.11 Common Margin Properties-Block</td>
	 * <td>null</td>
	 * <td>边框设置</td>
	 * </tr>
	 * <tr>
	 * <td>2、</td>
	 * <td>7.27.13 page-height</td>
	 * <td>29.7 cm</td>
	 * <td>页高</td>
	 * </tr>
	 * <tr>
	 * <td>3、</td>
	 * <td>7.27.15 page-width</td>
	 * <td>21.0 cm</td>
	 * <td>页宽</td>
	 * </tr>
	 * <tr>
	 * <td>4、</td>
	 * <td>7.21.3 reference-orientation</td>
	 * <td>0</td>
	 * <td>索引方向</td>
	 * </tr>
	 * <tr>
	 * <td>5、</td>
	 * <td>7.29.7 writing-mode</td>
	 * <td>Constants.EN_LR_TB</td>
	 * <td>写作模式</td>
	 * </tr>
	 * <tr>
	 * <td>6、</td>
	 * <td>regions</td>
	 * <td>region-body</td>
	 * <td>所含区域</td>
	 * </tr>
	 * <tr>
	 * <td>7、</td>
	 * <td>mediaUsage</td>
	 * <td>&quot;&quot;</td>
	 * <td>所用媒体</td>
	 * </tr>
	 * </table>
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public SimplePageMaster(CommonMarginBlock commonMarginBlock,
			Length pageHeight, Length pageWidth, int referenceOrientation,
			int writingMode, Map<Integer, Region> regions, String mediaUsage)
	{
		_commonMarginBlock = commonMarginBlock;
		_pageHeight = pageHeight;
		_pageWidth = pageWidth;
		_referenceOrientation = referenceOrientation;
		if (writingMode > 0)
		{
			_writingMode = writingMode;
		}
		if (regions != null && !regions.isEmpty())
		{
			Iterator<Integer> reit = regions.keySet().iterator();
			while (reit.hasNext())
			{
				Integer key = reit.next();
				Region region = regions.get(key);
				if (region != null)
				{
					region.setLayoutMaster(this);
				}
			}
			_regions = regions;
		}

		_mediaUsage = mediaUsage;
	}

	/**
	 * 
	 * 获得指定id的Region
	 * 
	 * @param regionId
	 *            :id
	 * 
	 * @return
	 * @exception
	 */
	public Region getRegion(int regionId)
	{
		return (Region) _regions.get(regionId);
	}

	/*
	 * 返回区域属性
	 */
	public Map<Integer, Region> getRegions()
	{
		return new HashMap<Integer, Region>(_regions);
	}

	/*
	 * 返回block属性
	 */
	public CommonMarginBlock getCommonMarginBlock()
	{
		CommonMarginBlock commonMarginBlock;
		if (_commonMarginBlock == null)
		{
			Length marginTop = (Length) InitialManager.getInitialValue(
					Constants.PR_MARGIN_TOP, null);
			Length marginBottom = (Length) InitialManager.getInitialValue(
					Constants.PR_MARGIN_BOTTOM, null);
			Length marginLeft = (Length) InitialManager.getInitialValue(
					Constants.PR_MARGIN_LEFT, null);
			Length marginRight = (Length) InitialManager.getInitialValue(
					Constants.PR_MARGIN_RIGHT, null);
			SpaceProperty spaceBefore = (SpaceProperty) InitialManager
					.getInitialValue(Constants.PR_SPACE_BEFORE, null);
			SpaceProperty spaceAfter = (SpaceProperty) InitialManager
					.getInitialValue(Constants.PR_SPACE_AFTER, null);
			Length startIndent = (Length) InitialManager.getInitialValue(
					Constants.PR_START_INDENT, null);
			Length endIndent = (Length) InitialManager.getInitialValue(
					Constants.PR_END_INDENT, null);
			commonMarginBlock = new CommonMarginBlock(marginTop, marginBottom,
					marginLeft, marginRight, spaceBefore, spaceAfter,
					startIndent, endIndent);
		} else
		{
			commonMarginBlock = new CommonMarginBlock(_commonMarginBlock
					.getMarginTop(), _commonMarginBlock.getMarginBottom(),
					_commonMarginBlock.getMarginLeft(), _commonMarginBlock
							.getMarginRight(), _commonMarginBlock
							.getSpaceBefore(), _commonMarginBlock
							.getSpaceAfter(), _commonMarginBlock
							.getStartIndent(), _commonMarginBlock
							.getEndIndent());
		}
		return commonMarginBlock;
	}

	/*
	 * 返回页高属性
	 */
	public Length getPageWidth()
	{
		if (_pageWidth == null)
		{
			return (Length) InitialManager.getInitialValue(
					Constants.PR_PAGE_WIDTH, null);
		}
		return _pageWidth;
	}

	/*
	 * 返回页宽属性
	 */
	public Length getPageHeight()
	{
		if (_pageHeight == null)
		{
			return (Length) InitialManager.getInitialValue(
					Constants.PR_PAGE_HEIGHT, null);
		}
		return _pageHeight;
	}

	/*
	 * 返回书写模式属性
	 */
	public int getWritingMode()
	{
		return _writingMode;
	}

	/*
	 * 返回旋转模式属性
	 */
	public int getReferenceOrientation()
	{
		return _referenceOrientation;
	}

	public String getMediaUsage()
	{
		return _mediaUsage;
	}

	/** @return "master-name" property. */
	public String getMasterName()
	{
		return mastername;
	}

	/**
	 * @param mastername
	 *            设置mastername成员变量的值
	 * 
	 *            值约束说明
	 */
	public void setMastername(String mastername)
	{
		this.mastername = mastername;
	}

	/** @return "master-name" property. */
	public String getVirtualMasterName()
	{
		if (virtualmastername == null || "".equals(virtualmastername))
		{
			virtualmastername = mastername;
		}
		return virtualmastername;
	}

	/**
	 * @param mastername
	 *            设置mastername成员变量的值
	 * 
	 *            值约束说明
	 */
	public void setVirtualMastername(String virtualmastername)
	{
		this.virtualmastername = virtualmastername;
	}

	/** @return the extension attachments of this FObj. */
	public List getExtensionAttachments()
	{
		return Collections.EMPTY_LIST;
	}

	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof SimplePageMaster))
		{
			return false;
		}
		if (this == obj)
		{
			return true;
		}
		SimplePageMaster spm = (SimplePageMaster) obj;
		return (this._regions.equals(spm._regions))
				&& (this._commonMarginBlock == null ? spm._commonMarginBlock == null
						: _commonMarginBlock.equals(spm._commonMarginBlock))
				&& (this._pageHeight == spm._pageHeight)
				&& (this._pageWidth == spm._pageWidth)
				&& (this._referenceOrientation == spm._referenceOrientation)
				&& (this._writingMode == spm._writingMode);
	}

	public SimplePageMaster clone()
	{
		CommonMarginBlock commonMarginBlock = this._commonMarginBlock.clone();
		Length pageHeight = this._pageHeight.clone();

		Length pageWidth = this._pageWidth.clone();

		int referenceOrientation = this._referenceOrientation;
		int writingMode = this._writingMode;
		Map<Integer, Region> regions = null;
		if (this._regions != null)
		{
			regions = new HashMap<Integer, Region>();
			Object[] keys = this._regions.keySet().toArray();
			for (int i = 0; i < keys.length; i++)
			{
				int key = (Integer) keys[i];
				Region old = this._regions.get(key);
				Region newregion = old.clone();
				regions.put(key, newregion);
			}
		}
		String mediaUsage = new String(this._mediaUsage);
		SimplePageMaster newspm = new SimplePageMaster(commonMarginBlock,
				pageHeight, pageWidth, referenceOrientation, writingMode,
				regions, mediaUsage);
		if (this._regions != null)
		{
			regions = new HashMap<Integer, Region>();
			Object[] keys = regions.keySet().toArray();
			for (int i = 0; i < keys.length; i++)
			{
				int key = (Integer) keys[i];
				Region rg = this._regions.get(key);
				rg.setLayoutMaster(newspm);
			}
		}
		return newspm;
	}

}
