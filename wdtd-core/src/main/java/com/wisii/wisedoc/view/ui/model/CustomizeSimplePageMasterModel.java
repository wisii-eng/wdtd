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

import com.wisii.wisedoc.document.attribute.CommonMarginBlock;
import com.wisii.wisedoc.document.attribute.FixedLength;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.document.datatype.Length;

public class CustomizeSimplePageMasterModel
{

	SimplePageMasterModel spm;

	// static int chushihuaflg = 0;

	public CustomizeSimplePageMasterModel(SimplePageMasterModel simplepmm)
	{
		spm = simplepmm;
		// String name = spm.getMasterName();
		// if (name == null || "default".equals(name) || "".equals(name)
		// )
		// {
		// // || "region".equals(name)
		// String spmname = "SPM" + chushihuaflg;
		// spm.setMasterName(spmname);
		// // spm.setRegionBefore(new RegionBeforeModel.Builder().regionName(
		// // spmname + "before").build().getRegionBefore());
		// // spm.setRegionAfter(new RegionAfterModel.Builder().regionName(
		// // spmname + "after").build().getRegionAfter());
		// // spm.setRegionStart(new RegionStartModel.Builder().regionName(
		// // spmname + "start").build().getRegionStart());
		// // spm.setRegionEnd(new RegionEndModel.Builder().regionName(
		// // spmname + "end").build().getRegionEnd());
		// simplepmm.getRegionBeforeModel().setRegionName(spmname + "before");
		// simplepmm.getRegionAfterModel().setRegionName(spmname + "after");
		// simplepmm.getRegionStartModel().setRegionName(spmname + "start");
		// simplepmm.getRegionEndModel().setRegionName(spmname + "end");
		// chushihuaflg = chushihuaflg + 1;
		// }
	}

	/**
	 * @return the pageWidth
	 */
	public Length getPageWidth()
	{
		return spm.getPageWidth();
	}

	/**
	 * @param pageWidth
	 *            the pageWidth to set
	 */

	public void setPageWidth(FixedLength value)
	{
		spm.setPageWidth(value);
	}

	/**
	 * @return the pageHeight
	 */
	public Length getPageHeight()
	{
		return spm.getPageHeight();
	}

	/**
	 * @param pageHeight
	 *            the pageHeight to set
	 */

	public void setPageHeight(FixedLength value)
	{
		spm.setPageHeight(value);
	}

	/**
	 * @return the pageOrientation
	 */
	public int getPageOrientation()
	{
		return spm.getPageOrientation();
	}

	/**
	 * @param pageOrientation
	 *            the pageOrientation to set
	 */
	public void setPageOrientation(int pageOrientation)
	{
		spm.setPageOrientation(pageOrientation);
	}

	/**
	 * @return the textDirection
	 */
	public int getPageWritingMode()
	{
		return spm.getPageWritingMode();
	}

	/**
	 * @param textDirection
	 *            the textDirection to set
	 */
	public void setPageWritingMode(int writingModel)
	{
		spm.setPageWritingMode(writingModel);
	}

	/**
	 * @return the PageMargion
	 */
	public CommonMarginBlock getPageMargion()
	{
		if (spm.getPageMargion() == null)
		{
			return new CommonMarginBlock(spm.getPageMarginTop(), spm
					.getPageMarginBottom(), spm.getPageMarginLeft(), spm
					.getPageMarginRight(), null, null, null, null);
		}
		return spm.getPageMargion();
	}

	/**
	 * @param PageMargion
	 *            the PageMargion to set
	 */
	public void setPageMargion(CommonMarginBlock pageMargion)
	{
		spm.setPageMargion(pageMargion);
	}

	public void setPageMargionTop(FixedLength length)
	{
		spm.setPageMarginTop(length);
	}

	public void setPageMargionBottom(FixedLength length)
	{
		spm.setPageMarginBottom(length);
	}

	public void setPageMargionLeft(FixedLength length)
	{
		spm.setPageMarginLeft(length);
	}

	public void setPageMargionRight(FixedLength length)
	{
		spm.setPageMarginRight(length);
	}

	public CustomizeRegionBodyModel getRegionBodyModel()
	{
		RegionBodyModel bodymodel = spm.getRegionBodyModel();
		return bodymodel != null ? new CustomizeRegionBodyModel(bodymodel)
				: null;
	}

	public void setRegionBody(CustomizeRegionBodyModel region)
	{
		spm.setRegionBody(region.getRegionbodymodel().getRegionBody());
	}

	public CustomizeRegionBeforeModel getRegionBeforeModel()
	{
		RegionBeforeModel beforemodel = spm.getRegionBeforeModel();
		return beforemodel != null ? new CustomizeRegionBeforeModel(beforemodel)
				: null;
	}

	public void setRegionBefore(CustomizeRegionBeforeModel region)
	{
		spm.setRegionBefore(region.getRegionbeforemodel().getRegionBefore());
	}

	public CustomizeRegionAfterModel getRegionAfterModel()
	{
		RegionAfterModel aftermodel = spm.getRegionAfterModel();
		return aftermodel != null ? new CustomizeRegionAfterModel(aftermodel)
				: null;
	}

	public void setRegionAfter(CustomizeRegionAfterModel region)
	{
		spm.setRegionAfter(region.getRegionaftermodel().getRegionAfter());
	}

	public CustomizeRegionStartModel getRegionStartModel()
	{
		RegionStartModel startmodel = spm.getRegionStartModel();
		return startmodel != null ? new CustomizeRegionStartModel(startmodel)
				: null;
	}

	public void setRegionStart(CustomizeRegionStartModel region)
	{
		spm.setRegionStart(region.getRegionstartmodel().getRegionStart());
	}

	public CustomizeRegionEndModel getRegionEndModel()
	{
		RegionEndModel endmodel = spm.getRegionEndModel();
		return endmodel != null ? new CustomizeRegionEndModel(endmodel) : null;
	}

	public void setRegionEnd(CustomizeRegionEndModel region)
	{
		spm.setRegionEnd(region.getRegionendmodel().getRegionEnd());
	}

	public void setMediaUsage(String mediaUsage)
	{
		spm.setMediaUsage(mediaUsage);
	}

	/**
	 * @返回 mediaUsage变量的值
	 */
	public String getMediaUsage()
	{
		return spm.getMediaUsage();
	}

	public SimplePageMasterModel getSpm()
	{
		return spm;
	}

	public void setSpm(SimplePageMasterModel spm)
	{
		this.spm = spm;
	}

	public SimplePageMaster getSimplePageMaster()
	{
		return spm.getSimplePageMaster();
	}
}
