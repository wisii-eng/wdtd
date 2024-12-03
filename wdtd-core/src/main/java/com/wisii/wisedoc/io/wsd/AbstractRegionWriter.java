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
 * @AbstractRegionWriter.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.io.wsd;

import java.awt.Color;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.document.attribute.Region;
import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground.BorderInfo;

/**
 * 类功能描述：抽象的Region Writer类，实现了写 reigion的相关公共方法
 * 
 * 作者：zhangqiang 创建日期：2008-9-22
 */
abstract class AbstractRegionWriter {
	protected WSDAttributeIOFactory attiofactory = new WSDAttributeIOFactory();

	public abstract String write(Region region);

	/*
	 * 
	 * 生成Region属性代码
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * 
	 * @return {返回参数名} {返回参数说明}
	 * 
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	protected String generateRegionAttString(Region region) {
		String returns = "";
		if (region != null) {
			returns = returns
					+ generateComBPB(region.getCommonBorderPaddingBackground());
			String regionname = region.getRegionName();
			if (regionname != null) {
				returns = returns
						+ attiofactory
								.getAttributeWriter(regionname.getClass())
								.write(Constants.PR_REGION_NAME, regionname);
			}
			Object displayalign = region.getDisplayAlign();
			if (displayalign !=null&&(Integer)displayalign>0) {
				displayalign =new EnumProperty((Integer)displayalign,"");
				returns = returns
						+ attiofactory.getAttributeWriter(
								displayalign.getClass()).write(
								Constants.PR_DISPLAY_ALIGN, displayalign);
			}
			Object overflow = region.getOverflow();
			if (overflow !=null&&(Integer)overflow>0) {
				overflow = new EnumProperty((Integer)overflow,"");
				returns = returns
						+ attiofactory.getAttributeWriter(overflow.getClass())
								.write(Constants.PR_OVERFLOW, overflow);
			}
			Object ro = region.getRegionReferenceOrientation();
			if (ro != null) {
				returns = returns
						+ attiofactory.getAttributeWriter(ro.getClass()).write(
								Constants.PR_REFERENCE_ORIENTATION, ro);
			}
			Object wm = region.getRegionWritingMode();
			if (wm != null&&(Integer)wm>0) {
				returns = returns
						+ attiofactory.getAttributeWriter(wm.getClass()).write(
								Constants.PR_WRITING_MODE, wm);
			}
		}
		return returns;
	}

	/*
	 * 
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * 
	 * @return {返回参数名} {返回参数说明}
	 * 
	 * @exception {说明在某情况下,将发生什么异常}
	 */
	private String generateComBPB(CommonBorderPaddingBackground cbpb) {
		String returns = "";
		if (cbpb != null) {
			Object bgcolor = cbpb.getBackgroundColor();
			if (bgcolor != null) {
				returns = returns
						+ attiofactory.getAttributeWriter(bgcolor.getClass())
								.write(Constants.PR_BACKGROUND_COLOR, bgcolor);
			}
			Object bgimage = cbpb.getBackgroundImage();
			if (bgimage != null) {
				returns = returns
						+ attiofactory.getAttributeWriter(Constants.PR_BACKGROUND_IMAGE)
								.write(Constants.PR_BACKGROUND_IMAGE, bgimage);
			}
			Object bgpositionh = cbpb.getBackgroundPositionHorizontal();
			if (bgpositionh != null) {
				returns = returns
						+ attiofactory.getAttributeWriter(
								bgpositionh.getClass()).write(
								Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
								bgpositionh);
			}
			Object bgpositionv = cbpb.getBackgroundPositionVertical();
			if (bgpositionv != null) {
				returns = returns
						+ attiofactory.getAttributeWriter(
								bgpositionv.getClass()).write(
								Constants.PR_BACKGROUND_POSITION_VERTICAL,
								bgpositionv);
			}
			Object bgattachment = cbpb.getBackgroundAttachment();
			if (bgattachment instanceof Integer&&(Integer)bgattachment>0) {
				bgattachment =  new EnumProperty((Integer)bgattachment, ""); 
				returns = returns
						+ attiofactory.getAttributeWriter(
								bgattachment.getClass()).write(
								Constants.PR_BACKGROUND_ATTACHMENT,
								bgattachment);
			}
			Object bgrepeat = cbpb.getBackgroundRepeat();
			if (bgrepeat instanceof Integer&&(Integer)bgrepeat>0) {
				bgrepeat =  new EnumProperty((Integer)bgrepeat, ""); 
				returns = returns
						+ attiofactory
								.getAttributeWriter(bgrepeat.getClass())
								.write(Constants.PR_BACKGROUND_REPEAT, bgrepeat);
			}
			returns = returns + generateBodersString(cbpb);
			returns = returns + generatePaddingsString(cbpb);
		}
		return returns;
	}

	private String generateBodersString(CommonBorderPaddingBackground cbpb) {
		String returns = "";
		if (cbpb != null) {
			BorderInfo borderb = cbpb.getBorderInfo(cbpb.BEFORE);
			if (borderb != null) {
				Color color = borderb.getColor();
				if (color != null) {
					attiofactory.getAttributeWriter(color.getClass()).write(
							Constants.PR_BORDER_BEFORE_COLOR, color);
				}
				Integer style = borderb.getStyle();
				if (style != null) {
					attiofactory.getAttributeWriter(style.getClass()).write(
							Constants.PR_BORDER_BEFORE_STYLE, style);
				}
				Object width = borderb.getWidth();
				if (width != null) {
					attiofactory.getAttributeWriter(width.getClass()).write(
							Constants.PR_BORDER_BEFORE_WIDTH, width);
				}
			}
			BorderInfo bordera = cbpb.getBorderInfo(cbpb.AFTER);
			if (bordera != null) {
				Color color = bordera.getColor();
				if (color != null) {
					attiofactory.getAttributeWriter(color.getClass()).write(
							Constants.PR_BORDER_AFTER_COLOR, color);
				}
				Integer style = bordera.getStyle();
				if (style != null) {
					attiofactory.getAttributeWriter(style.getClass()).write(
							Constants.PR_BORDER_AFTER_STYLE, style);
				}
				Object width = bordera.getWidth();
				if (width != null) {
					attiofactory.getAttributeWriter(width.getClass()).write(
							Constants.PR_BORDER_AFTER_WIDTH, width);
				}
			}
			BorderInfo borders = cbpb.getBorderInfo(cbpb.START);
			if (borders != null) {
				Color color = borders.getColor();
				if (color != null) {
					attiofactory.getAttributeWriter(color.getClass()).write(
							Constants.PR_BORDER_START_COLOR, color);
				}
				Integer style = borders.getStyle();
				if (style != null) {
					attiofactory.getAttributeWriter(style.getClass()).write(
							Constants.PR_BORDER_START_STYLE, style);
				}
				Object width = borders.getWidth();
				if (width != null) {
					attiofactory.getAttributeWriter(width.getClass()).write(
							Constants.PR_BORDER_START_WIDTH, width);
				}
			}
			BorderInfo bordere = cbpb.getBorderInfo(cbpb.END);
			if (bordere != null) {
				Color color = bordere.getColor();
				if (color != null) {
					attiofactory.getAttributeWriter(color.getClass()).write(
							Constants.PR_BORDER_END_COLOR, color);
				}
				Integer style = bordere.getStyle();
				if (style != null) {
					attiofactory.getAttributeWriter(style.getClass()).write(
							Constants.PR_BORDER_END_STYLE, style);
				}
				Object width = bordere.getWidth();
				if (width != null) {
					attiofactory.getAttributeWriter(width.getClass()).write(
							Constants.PR_BORDER_END_WIDTH, width);
				}
			}
		}
		return returns;
	}

	private String generatePaddingsString(CommonBorderPaddingBackground cbpb) {
		String returns = "";
		if (cbpb != null) {
			Object padingb = cbpb.getPaddingLengthProperty(cbpb.BEFORE);
			if (padingb != null) {
				attiofactory.getAttributeWriter(padingb.getClass()).write(
						Constants.PR_PADDING_BEFORE, padingb);
			}
			Object padinga = cbpb.getPaddingLengthProperty(cbpb.AFTER);
			if (padinga != null) {
				attiofactory.getAttributeWriter(padinga.getClass()).write(
						Constants.PR_PADDING_AFTER, padinga);
			}
			Object padings = cbpb.getPaddingLengthProperty(cbpb.START);
			if (padings != null) {
				attiofactory.getAttributeWriter(padings.getClass()).write(
						Constants.PR_PADDING_START, padings);
			}
			Object padinge = cbpb.getPaddingLengthProperty(cbpb.END);
			if (padinge != null) {
				attiofactory.getAttributeWriter(padinge.getClass()).write(
						Constants.PR_PADDING_END, padinge);
			}

		}
		return returns;
	}
}
