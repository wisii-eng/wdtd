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
/* $Id: TraitSetter.java,v 1.2 2007/07/03 07:46:55 hzl Exp $ */

package com.wisii.wisedoc.layoutmanager;

import com.wisii.wisedoc.area.Area;
import com.wisii.wisedoc.area.Trait;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CommonBorderPaddingBackground;
import com.wisii.wisedoc.document.attribute.CommonMarginBlock;
import com.wisii.wisedoc.document.attribute.CommonTextDecoration;
import com.wisii.wisedoc.document.datatype.LengthBase;
import com.wisii.wisedoc.document.datatype.PercentBaseContext;
import com.wisii.wisedoc.document.datatype.SimplePercentBaseContext;
import com.wisii.wisedoc.fonts.Font;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.traits.BorderProps;
import com.wisii.wisedoc.traits.MinOptMax;
/**
 * This is a helper class used for setting common traits on areas.
 */
public class TraitSetter {

	/** logger */

	/**
	 * Sets border and padding traits on areas.
	 * 
	 * @param area
	 *            area to set the traits on
	 * @param bpProps
	 *            border and padding properties
	 * @param bNotFirst
	 *            True if the area is not the first area
	 * @param bNotLast
	 *            True if the area is not the last area
	 * @param context
	 *            Property evaluation context
	 */
	public static void setBorderPaddingTraits(Area area,
			CommonBorderPaddingBackground bpProps, boolean bNotFirst,
			boolean bNotLast, PercentBaseContext context) {
		int iBP;
		iBP = bpProps.getPadding(CommonBorderPaddingBackground.START,
				bNotFirst, context);
		if (iBP > 0) {
			area.addTrait(Trait.PADDING_START, new Integer(iBP));
		}
		iBP = bpProps.getPadding(CommonBorderPaddingBackground.END, bNotLast,
				context);
		if (iBP > 0) {
			area.addTrait(Trait.PADDING_END, new Integer(iBP));
		}
		iBP = bpProps.getPadding(CommonBorderPaddingBackground.BEFORE, false,
				context);
		if (iBP > 0) {
			area.addTrait(Trait.PADDING_BEFORE, new Integer(iBP));
		}
		iBP = bpProps.getPadding(CommonBorderPaddingBackground.AFTER, false,
				context);
		if (iBP > 0) {
			area.addTrait(Trait.PADDING_AFTER, new Integer(iBP));
		}

		addBorderTrait(area, bpProps, bNotFirst,
				CommonBorderPaddingBackground.START, BorderProps.SEPARATE,
				Trait.BORDER_START);

		addBorderTrait(area, bpProps, bNotLast,
				CommonBorderPaddingBackground.END, BorderProps.SEPARATE,
				Trait.BORDER_END);

		addBorderTrait(area, bpProps, false,
				CommonBorderPaddingBackground.BEFORE, BorderProps.SEPARATE,
				Trait.BORDER_BEFORE);

		addBorderTrait(area, bpProps, false,
				CommonBorderPaddingBackground.AFTER, BorderProps.SEPARATE,
				Trait.BORDER_AFTER);
	}

	/**
	 * Sets border traits on an area.
	 * 
	 * @param area
	 *            area to set the traits on
	 * @param bpProps
	 *            border and padding properties
	 * @param mode
	 *            the border paint mode (see BorderProps)
	 */
	private static void addBorderTrait(Area area,
			CommonBorderPaddingBackground bpProps, boolean bDiscard, int iSide,
			int mode, Object oTrait) {
		int iBP = bpProps.getBorderWidth(iSide, bDiscard);
		if (iBP > 0) {
			area.addTrait(oTrait, new BorderProps(
					bpProps.getBorderStyle(iSide), iBP, bpProps
							.getBorderColor(iSide), mode));
		}
	}

	/**
	 * Add borders to an area. Note: this method also adds unconditional
	 * padding. Don't use! Layout managers that create areas with borders can
	 * use this to add the borders to the area.
	 * 
	 * @param area
	 *            the area to set the traits on.
	 * @param bordProps
	 *            border properties
	 * @param context
	 *            Property evaluation context
	 * @deprecated Call the other addBorders() method and addPadding separately.
	 */
	public static void addBorders(Area area,
			CommonBorderPaddingBackground bordProps, PercentBaseContext context) {
		BorderProps bps = getBorderProps(bordProps,
				CommonBorderPaddingBackground.BEFORE);
		if (bps != null) {
			area.addTrait(Trait.BORDER_BEFORE, bps);
		}
		bps = getBorderProps(bordProps, CommonBorderPaddingBackground.AFTER);
		if (bps != null) {
			area.addTrait(Trait.BORDER_AFTER, bps);
		}
		bps = getBorderProps(bordProps, CommonBorderPaddingBackground.START);
		if (bps != null) {
			area.addTrait(Trait.BORDER_START, bps);
		}
		bps = getBorderProps(bordProps, CommonBorderPaddingBackground.END);
		if (bps != null) {
			area.addTrait(Trait.BORDER_END, bps);
		}

		addPadding(area, bordProps, context);
	}

	/**
	 * Add borders to an area. Layout managers that create areas with borders
	 * can use this to add the borders to the area.
	 * 
	 * @param area
	 *            the area to set the traits on.
	 * @param bordProps
	 *            border properties
	 * @param discardBefore
	 *            true if the before border should be discarded
	 * @param discardAfter
	 *            true if the after border should be discarded
	 * @param discardStart
	 *            true if the start border should be discarded
	 * @param discardEnd
	 *            true if the end border should be discarded
	 * @param context
	 *            Property evaluation context
	 */
	public static void addBorders(Area area,
			CommonBorderPaddingBackground bordProps, boolean discardBefore,
			boolean discardAfter, boolean discardStart, boolean discardEnd,
			PercentBaseContext context) {
		BorderProps bps = getBorderProps(bordProps,
				CommonBorderPaddingBackground.BEFORE);
		if (bps != null && !discardBefore) {
			area.addTrait(Trait.BORDER_BEFORE, bps);
		}
		bps = getBorderProps(bordProps, CommonBorderPaddingBackground.AFTER);
		if (bps != null && !discardAfter) {
			area.addTrait(Trait.BORDER_AFTER, bps);
		}
		bps = getBorderProps(bordProps, CommonBorderPaddingBackground.START);
		if (bps != null && !discardStart) {
			area.addTrait(Trait.BORDER_START, bps);
		}
		bps = getBorderProps(bordProps, CommonBorderPaddingBackground.END);
		if (bps != null && !discardEnd) {
			area.addTrait(Trait.BORDER_END, bps);
		}
	}

	/**
	 * Add borders to an area for the collapsing border model in tables. Layout
	 * managers that create areas with borders can use this to add the borders
	 * to the area.
	 * 
	 * @param area
	 *            the area to set the traits on.
	 * @param bordProps
	 *            border properties
	 * @param outer
	 *            4 boolean values indicating if the side represents the table's
	 *            outer border. Order: before, after, start, end
	 * @param context
	 *            Property evaluation context
	 */
	public static void addCollapsingBorders(Area area,
			CommonBorderPaddingBackground bordProps, boolean[] outer,
			PercentBaseContext context) {
		BorderProps bps = getCollapsingBorderProps(bordProps,
				CommonBorderPaddingBackground.BEFORE, outer[0]);
		if (bps != null) {
			area.addTrait(Trait.BORDER_BEFORE, bps);
		}
		bps = getCollapsingBorderProps(bordProps,
				CommonBorderPaddingBackground.AFTER, outer[1]);
		if (bps != null) {
			area.addTrait(Trait.BORDER_AFTER, bps);
		}
		bps = getCollapsingBorderProps(bordProps,
				CommonBorderPaddingBackground.START, outer[2]);
		if (bps != null) {
			area.addTrait(Trait.BORDER_START, bps);
		}
		bps = getCollapsingBorderProps(bordProps,
				CommonBorderPaddingBackground.END, outer[3]);
		if (bps != null) {
			area.addTrait(Trait.BORDER_END, bps);
		}

		addPadding(area, bordProps, context);
	}

	private static void addPadding(Area area,
			CommonBorderPaddingBackground bordProps, PercentBaseContext context) {
		addPadding(area, bordProps, false, false, false, false, context);
	}

	/**
	 * Add padding to an area. Layout managers that create areas with padding
	 * can use this to add the borders to the area.
	 * 
	 * @param area
	 *            the area to set the traits on.
	 * @param bordProps
	 *            border and padding properties
	 * @param discardBefore
	 *            true if the before padding should be discarded
	 * @param discardAfter
	 *            true if the after padding should be discarded
	 * @param discardStart
	 *            true if the start padding should be discarded
	 * @param discardEnd
	 *            true if the end padding should be discarded
	 * @param context
	 *            Property evaluation context
	 */
	public static void addPadding(Area area,
			CommonBorderPaddingBackground bordProps, boolean discardBefore,
			boolean discardAfter, boolean discardStart, boolean discardEnd,
			PercentBaseContext context) {
		int padding = bordProps.getPadding(
				CommonBorderPaddingBackground.BEFORE, discardBefore, context);
		if (padding != 0) {
			area.addTrait(Trait.PADDING_BEFORE, new java.lang.Integer(padding));
		}

		padding = bordProps.getPadding(CommonBorderPaddingBackground.AFTER,
				discardAfter, context);
		if (padding != 0) {
			area.addTrait(Trait.PADDING_AFTER, new java.lang.Integer(padding));
		}

		padding = bordProps.getPadding(CommonBorderPaddingBackground.START,
				discardStart, context);
		if (padding != 0) {
			area.addTrait(Trait.PADDING_START, new java.lang.Integer(padding));
		}

		padding = bordProps.getPadding(CommonBorderPaddingBackground.END,
				discardEnd, context);
		if (padding != 0) {
			area.addTrait(Trait.PADDING_END, new java.lang.Integer(padding));
		}

	}

	private static BorderProps getBorderProps(
			CommonBorderPaddingBackground bordProps, int side) {
		int width = bordProps.getBorderWidth(side, false);
		if (width != 0) {
			BorderProps bps;
			bps = new BorderProps(bordProps.getBorderStyle(side), width,
					bordProps.getBorderColor(side), BorderProps.SEPARATE);
			return bps;
		} else {
			return null;
		}
	}

	private static BorderProps getCollapsingBorderProps(
			CommonBorderPaddingBackground bordProps, int side, boolean outer) {
		int width = bordProps.getBorderWidth(side, false);
		if (width != 0) {
			BorderProps bps;
			bps = new BorderProps(bordProps.getBorderStyle(side), width,
					bordProps.getBorderColor(side),
					(outer ? BorderProps.COLLAPSE_OUTER
							: BorderProps.COLLAPSE_INNER));
			return bps;
		} else {
			return null;
		}
	}

	/**
	 * Add background to an area. Layout managers that create areas with a
	 * background can use this to add the background to the area. Note: The
	 * area's IPD and BPD must be set before calling this method.
	 * 
	 * @param area
	 *            the area to set the traits on
	 * @param backProps
	 *            the background properties
	 * @param context
	 *            Property evaluation context
	 */
	public static void addBackground(Area area,
			CommonBorderPaddingBackground backProps, PercentBaseContext context) {
		if (!backProps.hasBackground()) {
			return;
		}
		Trait.Background back = new Trait.Background();
		back.setColor(backProps.getBackgroundColor());
		/* 【添加：START】 by 李晓光 2009-2-5 */
		area.addTrait(Trait.IMAGE_LAYER, backProps.getBackgroundImageLayer());
		/* 【添加：END】 by 李晓光 2009-2-5 */
		if (backProps.getFovImage() != null) {
			back.setURL(backProps.getBackgroundImage());
			back.setFovImage(backProps.getFovImage());
			back.setRepeat(backProps.getBackgroundRepeat());
			if (backProps.getBackgroundPositionHorizontal() != null) {
				if (back.getRepeat() == Constants.EN_NOREPEAT
						|| back.getRepeat() == Constants.EN_REPEATY) {
					if (area.getIPD() > 0) {
						int width = area.getIPD();
						width += backProps.getPaddingStart(false, context);
						width += backProps.getPaddingEnd(false, context);
						back
								.setHoriz(backProps.getBackgroundPositionHorizontal()
										.getValue(new SimplePercentBaseContext(
												context,
												LengthBase.IMAGE_BACKGROUND_POSITION_HORIZONTAL,
												(width - back.getFovImage()
														.getIntrinsicWidth()))));
					} else {
						// TODO Area IPD has to be set for this to work
						LogUtil
								.warn("Horizontal background image positioning ignored"
										+ " because the IPD was not set on the area."
										+ " (Yes, it's a bug in FOV)");
					}
				}
			}
			if (backProps.getBackgroundPositionVertical() != null) {
				if (back.getRepeat() == Constants.EN_NOREPEAT
						|| back.getRepeat() == Constants.EN_REPEATX) {
					if (area.getBPD() > 0) {
						int height = area.getBPD();
						height += backProps.getPaddingBefore(false, context);
						height += backProps.getPaddingAfter(false, context);
						back
								.setVertical(backProps.getBackgroundPositionVertical()
										.getValue(new SimplePercentBaseContext(
												context,
												LengthBase.IMAGE_BACKGROUND_POSITION_VERTICAL,
												(height - back.getFovImage()
														.getIntrinsicHeight()))));
					} else {
						// TODO Area BPD has to be set for this to work
						LogUtil
								.warn("Vertical background image positioning ignored"
										+ " because the BPD was not set on the area."
										+ " (Yes, it's a bug in FOV)");
					}
				}
			}
			/* 【删除：START】 by 李晓光 2009-2-5 导致背景图标不能正确加载*/
			/*((AbstractFovImage) (back.getFovImage())).setInputStream(null);*/ // 因为InputStream不支持序列化
			/* 【删除：END】 by 李晓光 2009-2-5 */
			// ,
			// 所以使用结束后
			// ，
			// 设置为null
		}

		area.addTrait(Trait.BACKGROUND, back);
	}

	/**
	 * Add space to a block area. Layout managers that create block areas can
	 * use this to add space outside of the border rectangle to the area.
	 * 
	 * @param area
	 *            the area to set the traits on.
	 * @param bpProps
	 *            the border, padding and background properties
	 * @param startIndent
	 *            the effective start-indent value
	 * @param endIndent
	 *            the effective end-indent value
	 * @param context
	 *            the context for evaluation of percentages
	 */
	public static void addMargins(Area area,
			CommonBorderPaddingBackground bpProps, int startIndent,
			int endIndent, PercentBaseContext context) {
		if (startIndent != 0) {
			area.addTrait(Trait.START_INDENT, new Integer(startIndent));
		}

		int spaceStart = startIndent - bpProps.getBorderStartWidth(false)
				- bpProps.getPaddingStart(false, context);
		if (spaceStart != 0) {
			area.addTrait(Trait.SPACE_START, new Integer(spaceStart));
		}

		if (endIndent != 0) {
			area.addTrait(Trait.END_INDENT, new Integer(endIndent));
		}
		int spaceEnd = endIndent - bpProps.getBorderEndWidth(false)
				- bpProps.getPaddingEnd(false, context);
		if (spaceEnd != 0) {
			area.addTrait(Trait.SPACE_END, new Integer(spaceEnd));
		}
	}

	/**
	 * Add space to a block area. Layout managers that create block areas can
	 * use this to add space outside of the border rectangle to the area.
	 * 
	 * @param area
	 *            the area to set the traits on.
	 * @param bpProps
	 *            the border, padding and background properties
	 * @param marginProps
	 *            the margin properties.
	 * @param context
	 *            the context for evaluation of percentages
	 */
	public static void addMargins(Area area,
			CommonBorderPaddingBackground bpProps,
			CommonMarginBlock marginProps, PercentBaseContext context) {
		int startIndent = marginProps.getStartIndent().getValue(context);
		int endIndent = marginProps.getEndIndent().getValue(context);
		addMargins(area, bpProps, startIndent, endIndent, context);
	}

	/**
	 * Returns the effective space length of a resolved space specifier based on
	 * the adjustment value.
	 * 
	 * @param adjust
	 *            the adjustment value
	 * @param space
	 *            the space specifier
	 * @return the effective space length
	 */
	public static int getEffectiveSpace(double adjust, MinOptMax space) {
		if (space == null) {
			return 0;
		}
		int sp = space.opt;
		if (adjust > 0) {
			sp = sp + (int) (adjust * (space.max - space.opt));
		} else {
			sp = sp + (int) (adjust * (space.opt - space.min));
		}
		return sp;
	}

	/**
	 * Adds traits for space-before and space-after to an area.
	 * 
	 * @param area
	 *            the target area
	 * @param adjust
	 *            the adjustment value
	 * @param spaceBefore
	 *            the space-before space specifier
	 * @param spaceAfter
	 *            the space-after space specifier
	 */
	public static void addSpaceBeforeAfter(Area area, double adjust,
			MinOptMax spaceBefore, MinOptMax spaceAfter) {
		int space;
		space = getEffectiveSpace(adjust, spaceBefore);
		if (space != 0) {
			area.addTrait(Trait.SPACE_BEFORE, new Integer(space));
		}
		space = getEffectiveSpace(adjust, spaceAfter);
		if (space != 0) {
			area.addTrait(Trait.SPACE_AFTER, new Integer(space));
		}
	}

	/**
	 * Sets the traits for breaks on an area.
	 * 
	 * @param area
	 *            the area to set the traits on.
	 * @param breakBefore
	 *            the value for break-before
	 * @param breakAfter
	 *            the value for break-after
	 */
	public static void addBreaks(Area area, int breakBefore, int breakAfter) {
		/*
		 * Currently disabled as these traits are never used by the renderers
		 * area.addTrait(Trait.BREAK_AFTER, new Integer(breakAfter));
		 * area.addTrait(Trait.BREAK_BEFORE, new Integer(breakBefore));
		 */
	}

	/**
	 * Adds font traits to an area
	 * 
	 * @param area
	 *            the target are
	 * @param font
	 *            the font to use
	 */
	public static void addFontTraits(Area area, Font font) {
		area.addTrait(Trait.FONT, font.getFontTriplet());
		area.addTrait(Trait.FONT_SIZE, new Integer(font.getFontSize()));
	}

	/**
	 * Adds the text-decoration traits to the area.
	 * 
	 * @param area
	 *            the area to set the traits on
	 * @param deco
	 *            the text decorations
	 */
	public static void addTextDecoration(Area area, CommonTextDecoration deco) {
		// TODO Finish text-decoration
		if (deco != null) {
			if (deco.hasUnderline()) {
				area.addTrait(Trait.UNDERLINE, Boolean.TRUE);
				area.addTrait(Trait.UNDERLINE_COLOR, deco.getUnderlineColor());
			}
			if (deco.hasOverline()) {
				area.addTrait(Trait.OVERLINE, Boolean.TRUE);
				area.addTrait(Trait.OVERLINE_COLOR, deco.getOverlineColor());
			}
			if (deco.hasLineThrough()) {
				area.addTrait(Trait.LINETHROUGH, Boolean.TRUE);
				area.addTrait(Trait.LINETHROUGH_COLOR, deco
						.getLineThroughColor());
			}
			if (deco.isBlinking()) {
				area.addTrait(Trait.BLINK, Boolean.TRUE);
			}
		}
	}

	/**
	 * Sets the producer's ID as a trait on the area. This can be used to track
	 * back the generating FO node.
	 * 
	 * @param area
	 *            the area to set the traits on
	 * @param id
	 *            the ID to set
	 */
	public static void setProducerID(Area area, String id) {
		if (id != null && id.length() > 0) {
			area.addTrait(Trait.PROD_ID, id);
		}
	}
}
