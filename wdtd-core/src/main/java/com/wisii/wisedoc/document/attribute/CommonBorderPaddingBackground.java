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
 * @CommonBorderPaddingBackground.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document.attribute;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import com.wisii.wisedoc.apps.FOUserAgent;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.DocumentUtil;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.datatype.Length;
import com.wisii.wisedoc.document.datatype.PercentBaseContext;
import com.wisii.wisedoc.image.FovImage;
import com.wisii.wisedoc.image.ImageFactory;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：通用的边框pading属性
 * 
 * 作者：zhangqiang 创建日期：2008-8-21
 */
public final class CommonBorderPaddingBackground extends
		AbstractCommonAttributes implements Cloneable
{

	/**
	 * The "background-attachment" property.
	 */
	private int backgroundAttachment;

	/**
	 * The "background-color" property.
	 */
	private Color backgroundColor;

	/**
	 * The "background-image" property.
	 */
	private String backgroundImage;

	/**
	 * 背景图片层
	 */
	private int backgroundImageLayer = 0;

	/**
	 * The "background-repeat" property.
	 */
	private int backgroundRepeat=((EnumProperty)InitialManager.getInitialValue(Constants.PR_BACKGROUND_REPEAT, null)).getEnum();

	/**
	 * The "background-position-horizontal" property.
	 */
	private Length backgroundPositionHorizontal;

	/**
	 * The "background-position-vertical" property.
	 */
	private Length backgroundPositionVertical;

	// 四边框属性
	private BorderInfo[] borderInfo = new BorderInfo[4];

	private FovImage fovimage;

	// 四个方向上padding的长度
	private CondLengthProperty[] padding = new CondLengthProperty[4];

	/** the "before" edge */
	public static final int BEFORE = 0;

	/** the "after" edge */
	public static final int AFTER = 1;

	/** the "start" edge */
	public static final int START = 2;

	/** the "end" edge */
	public static final int END = 3;

	/* 缺省构造方法 */
	public CommonBorderPaddingBackground()
	{
		super(null);
	}

	public CommonBorderPaddingBackground(CellElement cellelement)
	{
		super(cellelement);
		init(cellelement);
	}

	public void init(CellElement cellelement)
	{
		super.init(cellelement);
		EnumProperty prop = (EnumProperty) getAttribute(Constants.PR_BACKGROUND_ATTACHMENT);
		if (prop != null)
		{
			backgroundAttachment = prop.getEnum();
		}
		backgroundColor = (Color) getAttribute(Constants.PR_BACKGROUND_COLOR);
		Object bgimagelayer = getAttribute(Constants.PR_BACKGROUNDGRAPHIC_LAYER);
		if (bgimagelayer instanceof Integer)
		{
			backgroundImageLayer = (Integer) bgimagelayer;
		}
		Object bg = getAttribute(Constants.PR_BACKGROUND_IMAGE);
		if (bg instanceof String)
		{
			backgroundImage = (String) bg;
		} else if (bg instanceof BindNode)
		{
			backgroundImage = "bindnodeimage.jpg";
		} else
		{
			backgroundImage = null;
		}
		if (backgroundImage == null || "none".equals(backgroundImage))
		{
			backgroundImage = null;
			fovimage = null;
		} else
		{

			Object o = getAttribute(Constants.PR_BACKGROUND_REPEAT);
			if (o != null)
			{
				backgroundRepeat = ((EnumProperty) o).getEnum();
			}
			backgroundPositionHorizontal = (Length) getAttribute(Constants.PR_BACKGROUND_POSITION_HORIZONTAL);
			backgroundPositionVertical = (Length) getAttribute(Constants.PR_BACKGROUND_POSITION_VERTICAL);

			// Additional processing: preload image
			String url = ImageFactory.getURL(getBackgroundImage());
			FOUserAgent userAgent = DocumentUtil.getUserAgent();
			if (userAgent != null)
			{
				ImageFactory fact = userAgent.getFactory().getImageFactory();
				// fovimage = ImageFactory.getImage(url, userAgent);
				// TODO 应该改使用我们基础功能模块的
				fovimage = fact.getImage(url, userAgent);
				if (fovimage == null)
				{
					LogUtil.error("Background image not available: "
							+ getBackgroundImage());
				} else
				{
					// load dimensions
					if (!fovimage.load(FovImage.DIMENSIONS))
					{
						LogUtil
								.error("Cannot read background image dimensions: "
										+ getBackgroundImage());
					}
				}
			}
		}

		initBorderInfo(BEFORE, Constants.PR_BORDER_BEFORE_COLOR,
				Constants.PR_BORDER_BEFORE_STYLE,
				Constants.PR_BORDER_BEFORE_WIDTH, Constants.PR_PADDING_BEFORE);
		initBorderInfo(AFTER, Constants.PR_BORDER_AFTER_COLOR,
				Constants.PR_BORDER_AFTER_STYLE,
				Constants.PR_BORDER_AFTER_WIDTH, Constants.PR_PADDING_AFTER);
		initBorderInfo(START, Constants.PR_BORDER_START_COLOR,
				Constants.PR_BORDER_START_STYLE,
				Constants.PR_BORDER_START_WIDTH, Constants.PR_PADDING_START);
		initBorderInfo(END, Constants.PR_BORDER_END_COLOR,
				Constants.PR_BORDER_END_STYLE, Constants.PR_BORDER_END_WIDTH,
				Constants.PR_PADDING_END);
	}

	/**
	 * 
	 * 通过属性类构造该对象
	 * 
	 * @param
	 * @exception
	 */
	public CommonBorderPaddingBackground(Map<Integer, Object> attrmap)
	{
		super(null);
		if (attrmap == null)
		{
			attrmap = new HashMap<Integer, Object>();
		}
		EnumProperty prop = (EnumProperty) attrmap
				.get(Constants.PR_BACKGROUND_ATTACHMENT);
		if (prop == null)
		{
			prop = (EnumProperty) InitialManager.getInitialValue(
					Constants.PR_BACKGROUND_ATTACHMENT, null);
		}
		backgroundAttachment = prop.getEnum();
		/* 【替换】 by 李晓光 2008-09-04 */
		/*
		 * backgroundAttachment = (Integer) attr
		 * .getAttribute(Constants.PR_BACKGROUND_ATTACHMENT);
		 */
		backgroundColor = (Color) attrmap.get(Constants.PR_BACKGROUND_COLOR);
		Object bgimagelayer = attrmap.get(Constants.PR_BACKGROUNDGRAPHIC_LAYER);
		if (bgimagelayer instanceof Integer)
		{
			backgroundImageLayer = (Integer) bgimagelayer;
		}
		Object bg = attrmap.get(Constants.PR_BACKGROUND_IMAGE);
		if (bg instanceof String)
		{
			backgroundImage = (String) bg;
		} else if (bg instanceof BindNode)
		{
			backgroundImage = "bindnodeimage.jpg";
		}
		if (backgroundImage == null || "none".equals(backgroundImage))
		{
			backgroundImage = null;
			fovimage = null;
		} else
		{

			Object o = attrmap.get(Constants.PR_BACKGROUND_REPEAT);
			if (o == null)
			{
				o = InitialManager.getInitialValue(
						Constants.PR_BACKGROUND_REPEAT, null);
			}
			if (o != null)
			{
				backgroundRepeat = ((EnumProperty) o).getEnum();
			}
			backgroundPositionHorizontal = (Length) attrmap
					.get(Constants.PR_BACKGROUND_POSITION_HORIZONTAL);
			if (getBackgroundPositionHorizontal() == null)
			{
				backgroundPositionHorizontal = (Length) InitialManager
						.getInitialValue(
								Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
								null);
			}
			backgroundPositionVertical = (Length) attrmap
					.get(Constants.PR_BACKGROUND_POSITION_VERTICAL);
			if (getBackgroundPositionVertical() == null)
			{
				backgroundPositionVertical = (Length) InitialManager
						.getInitialValue(
								Constants.PR_BACKGROUND_POSITION_VERTICAL, null);
			}

			// Additional processing: preload image
			String url = ImageFactory.getURL(getBackgroundImage());
			FOUserAgent userAgent = DocumentUtil.getUserAgent();
			if (userAgent != null)
			{
				ImageFactory fact = userAgent.getFactory().getImageFactory();
				// fovimage = ImageFactory.getImage(url, userAgent);
				// TODO 应该改使用我们基础功能模块的
				fovimage = fact.getImage(url, userAgent);
				if (fovimage == null)
				{
					LogUtil.error("Background image not available: "
							+ getBackgroundImage());
				} else
				{
					// load dimensions
					if (!fovimage.load(FovImage.DIMENSIONS))
					{
						LogUtil
								.error("Cannot read background image dimensions: "
										+ getBackgroundImage());
					}
				}
			}
		}

		initBorderInfo(attrmap, BEFORE, Constants.PR_BORDER_BEFORE_COLOR,
				Constants.PR_BORDER_BEFORE_STYLE,
				Constants.PR_BORDER_BEFORE_WIDTH, Constants.PR_PADDING_BEFORE);
		initBorderInfo(attrmap, AFTER, Constants.PR_BORDER_AFTER_COLOR,
				Constants.PR_BORDER_AFTER_STYLE,
				Constants.PR_BORDER_AFTER_WIDTH, Constants.PR_PADDING_AFTER);
		initBorderInfo(attrmap, START, Constants.PR_BORDER_START_COLOR,
				Constants.PR_BORDER_START_STYLE,
				Constants.PR_BORDER_START_WIDTH, Constants.PR_PADDING_START);
		initBorderInfo(attrmap, END, Constants.PR_BORDER_END_COLOR,
				Constants.PR_BORDER_END_STYLE, Constants.PR_BORDER_END_WIDTH,
				Constants.PR_PADDING_END);

	}

	private void initBorderInfo(Map<Integer, Object> attrmap, int side,
			int colorProp, int styleProp, int widthProp, int paddingProp)
	{
		padding[side] = (CondLengthProperty) attrmap.get(paddingProp);
		if (padding[side] == null)
		{
			padding[side] = (CondLengthProperty) InitialManager
					.getInitialValue(paddingProp, null);
		}
		EnumProperty styleo = (EnumProperty) attrmap.get(styleProp);
		if (styleo == null)
		{
			styleo = (EnumProperty) InitialManager.getInitialValue(styleProp,
					null);
		}
		int style = Constants.EN_NONE;
		if (styleo != null)
		{
			style = styleo.getEnum();
		}
		if (style != Constants.EN_NONE)
		{
			CondLengthProperty width = (CondLengthProperty) attrmap
					.get(widthProp);
			if (width == null)
			{
				width = (CondLengthProperty) InitialManager.getInitialValue(
						widthProp, null);
			}
			Color color = (Color) attrmap.get(colorProp);
			if (color == null)
			{
				color = (Color) InitialManager.getInitialValue(colorProp, null);
				if (color == null)
				{
					color = Color.black;
				}
			}

			setBorderInfo(new BorderInfo(style, width, color), side);
		}
	}

	private void initBorderInfo(int side, int colorProp, int styleProp,
			int widthProp, int paddingProp)
	{
		padding[side] = (CondLengthProperty) getAttribute(paddingProp);
		EnumProperty styleo = (EnumProperty) getAttribute(styleProp);
		int style = Constants.EN_NONE;
		if (styleo != null)
		{
			style = styleo.getEnum();
		}
		if (style != Constants.EN_NONE)
		{
			CondLengthProperty width = (CondLengthProperty) getAttribute(widthProp);
			Color color = (Color) getAttribute(colorProp);
			setBorderInfo(new BorderInfo(style, width, color), side);
		} else
		{
			//如果是单元格，得是非为了排版正确而添加的一个单元格
			if ((cellelement instanceof TableCell&&!((TableCell)cellelement).isIsaddlast())
					|| cellelement instanceof BlockContainer)
			{
				setBorderInfo(
						new BorderInfo(
								Constants.EN_NOBORDER,
								new CondLengthProperty(new FixedLength(1), true),
								null), side);
			}
			else
			{
				setBorderInfo(null, side);
			}
		}
	}

	public void setBorderInfo(BorderInfo info, int side)
	{
		this.borderInfo[side] = info;
	}

	public static class BorderInfo implements Cloneable
	{

		private int mStyle; // Enum for border style

		private Color mColor; // Border color

		private CondLengthProperty mWidth;

		BorderInfo(int style, CondLengthProperty width, Color color)
		{
			mStyle = style;
			mWidth = width;
			mColor = color;
		}

		public int getStyle()
		{
			return this.mStyle;
		}

		public Color getColor()
		{
			return this.mColor;
		}

		public CondLengthProperty getWidth()
		{
			return this.mWidth;
		}

		public int getRetainedWidth()
		{
			if ((mStyle == Constants.EN_NONE)
					|| (mStyle == Constants.EN_HIDDEN))
			{
				return 0;
			} else
			{
				return mWidth.getLengthValue();
			}
		}

		/** @see java.lang.Object#toString() */
		public String toString()
		{
			StringBuffer sb = new StringBuffer("BorderInfo");
			sb.append(" {");
			sb.append(mStyle);
			sb.append(", ");
			sb.append(mColor);
			sb.append(", ");
			sb.append(mWidth);
			sb.append("}");
			return sb.toString();
		}

		public boolean equals(Object obj)
		{
			if (!(obj instanceof BorderInfo))
			{
				return false;
			}
			BorderInfo borderinfo = (BorderInfo) obj;
			return (mStyle == borderinfo.mStyle)
					&& (mColor == null ? borderinfo.mColor == null : mColor
							.equals(borderinfo.mColor))
					&& (mWidth == null ? borderinfo.mWidth == null : mWidth
							.equals(borderinfo.mWidth));
		}

		public BorderInfo clone()
		{
			int style = this.getStyle();
			CondLengthProperty width = this.getWidth().clone();
			WiseDocColor color = (WiseDocColor) this.getColor();

			Color colornew = new WiseDocColor(new Color(color.getRed(), color
					.getGreen(), color.getBlue()), color.getLayer());
			BorderInfo newborder = new BorderInfo(style, width, colornew);
			return newborder;
		}
	}

	/**
	 * 
	 * 获得指定方向上的边框信息
	 * 
	 * @param side
	 *            ：指定方向
	 * @return
	 * @exception
	 */
	public BorderInfo getBorderInfo(int side)
	{
		return this.borderInfo[side];
	}

	/**
	 * 
	 * 获得左边框的宽度，以千分之一pt为单位
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public int getBorderStartWidth(boolean bDiscard)
	{
		return getBorderWidth(START, bDiscard);
	}

	/**
	 * 
	 * 获得右边框的宽度，以千分之一pt为单位
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public int getBorderEndWidth(boolean bDiscard)
	{
		return getBorderWidth(END, bDiscard);
	}

	/**
	 * 
	 * 获得上边框的宽度，以千分之一pt为单位
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public int getBorderBeforeWidth(boolean bDiscard)
	{
		return getBorderWidth(BEFORE, bDiscard);
	}

	/**
	 * 
	 * 获得下边框的宽度，以千分之一pt为单位
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public int getBorderAfterWidth(boolean bDiscard)
	{
		return getBorderWidth(AFTER, bDiscard);
	}

	/**
	 * 
	 * 获得千分之一pt为单位的padding值
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public int getPaddingStart(boolean bDiscard, PercentBaseContext context)
	{
		return getPadding(START, bDiscard, context);
	}

	/**
	 * 
	 * 获得千分之一pt为单位的padding值
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public int getPaddingEnd(boolean bDiscard, PercentBaseContext context)
	{
		return getPadding(END, bDiscard, context);
	}

	/**
	 * 
	 * 获得千分之一pt为单位的padding值
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public int getPaddingBefore(boolean bDiscard, PercentBaseContext context)
	{
		return getPadding(BEFORE, bDiscard, context);
	}

	/**
	 * 
	 * 获得千分之一pt为单位的padding值
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public int getPaddingAfter(boolean bDiscard, PercentBaseContext context)
	{
		return getPadding(AFTER, bDiscard, context);
	}

	/**
	 * 
	 * 获得千分之一pt为单位的padding值
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public int getPadding(int side, boolean bDiscard, PercentBaseContext context)
	{
		if ((padding[side] == null) || (bDiscard && padding[side].isDiscard()))
		{
			return 0;
		} else
		{
			return padding[side].getLengthValue(context);
		}
	}

	/**
	 * 
	 * 获得指定方向上的边框宽度
	 * 
	 * @param side
	 *            ：指定方向
	 * @return 以千分之一pt为单位的长度值
	 * @exception
	 */
	public int getBorderWidth(int side, boolean bDiscard)
	{
		if ((borderInfo[side] == null)
				|| (borderInfo[side].mStyle == Constants.EN_NONE)
				|| (borderInfo[side].mStyle == Constants.EN_HIDDEN)
				|| (bDiscard && borderInfo[side].mWidth.isDiscard()))
		{
			return 0;
		} else
		{
			return borderInfo[side].mWidth.getLengthValue();
		}
	}

	/**
	 * 
	 * 获得指定方向上的边框颜色
	 */
	public Color getBorderColor(int side)
	{
		if (borderInfo[side] != null)
		{
			return borderInfo[side].mColor;
		} else
		{
			return null;
		}
	}

	/**
	 * 
	 * 获得指定方向上边框类型
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public int getBorderStyle(int side)
	{
		if (borderInfo[side] != null)
		{
			return borderInfo[side].mStyle;
		} else
		{
			return Constants.EN_NONE;
		}
	}

	/**
	 * 
	 * 获得指定方向上的padding宽度
	 * 
	 * @param 引入参数名
	 *            引入参数说明}
	 * @return 返回参数名} {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public CondLengthProperty getPaddingLengthProperty(int side)
	{
		return padding[side];
	}

	/**
	 * Return all the border and padding width in the inline progression
	 * dimension.
	 * 
	 * @param bDiscard
	 *            the discard flag.
	 * @param context
	 *            for percentage evaluation.
	 * @return all the padding and border width.
	 */
	public int getIPPaddingAndBorder(boolean bDiscard,
			PercentBaseContext context)
	{
		return getPaddingStart(bDiscard, context)
				+ getPaddingEnd(bDiscard, context)
				+ getBorderStartWidth(bDiscard) + getBorderEndWidth(bDiscard);
	}

	/**
	 * Return all the border and padding height in the block progression
	 * dimension.
	 * 
	 * @param bDiscard
	 *            the discard flag.
	 * @param context
	 *            for percentage evaluation
	 * @return all the padding and border height.
	 */
	public int getBPPaddingAndBorder(boolean bDiscard,
			PercentBaseContext context)
	{
		return getPaddingBefore(bDiscard, context)
				+ getPaddingAfter(bDiscard, context)
				+ getBorderBeforeWidth(bDiscard)
				+ getBorderAfterWidth(bDiscard);
	}

	/**
	 * @return true if there is any kind of background to be painted
	 */
	public boolean hasBackground()
	{

		return (backgroundColor != null) || (getFovImage() != null);
	}

	/** @return true if border is non-zero. */
	public boolean hasBorder()
	{
		return ((getBorderBeforeWidth(false) + getBorderAfterWidth(false)
				+ getBorderStartWidth(false) + getBorderEndWidth(false)) > 0);
	}

	/**
	 * @param context
	 *            for percentage based evaluation.
	 * @return true if padding is non-zero.
	 */
	public boolean hasPadding(PercentBaseContext context)
	{
		return ((getPaddingBefore(false, context)
				+ getPaddingAfter(false, context)
				+ getPaddingStart(false, context) + getPaddingEnd(false,
				context)) > 0);
	}

	public void setPadding(CommonBorderPaddingBackground source)
	{
		this.padding = source.padding;
	}

	public void setPadding(CondLengthProperty[] padding)
	{
		this.padding = padding;
	}

	/** @return true if there are any borders defined. */
	public boolean hasBorderInfo()
	{
		return (borderInfo[BEFORE] != null || borderInfo[AFTER] != null
				|| borderInfo[START] != null || borderInfo[END] != null);
	}

	/**
	 * @return the background image as a preloaded FovImage, null if there is no
	 *         background image.
	 */
	public FovImage getFovImage()
	{
		return this.fovimage;
	}

	/**
	 * @返回 backgroundAttachment变量的值
	 */
	public final int getBackgroundAttachment()
	{
		return backgroundAttachment;
	}

	/**
	 * @返回 backgroundColor变量的值
	 */
	public final Color getBackgroundColor()
	{
		return backgroundColor;
	}

	/**
	 * @返回 backgroundImage变量的值
	 */
	public final String getBackgroundImage()
	{
		return backgroundImage;
	}

	/**
	 * @返回 backgroundImageLayer变量的值
	 */
	public final int getBackgroundImageLayer()
	{
		return backgroundImageLayer;
	}

	/**
	 * @返回 backgroundRepeat变量的值
	 */
	public final int getBackgroundRepeat()
	{
		return backgroundRepeat;
	}

	/**
	 * @返回 backgroundPositionHorizontal变量的值
	 */
	public final Length getBackgroundPositionHorizontal()
	{
		return backgroundPositionHorizontal;
	}

	/**
	 * @返回 backgroundPositionVertical变量的值
	 */
	public final Length getBackgroundPositionVertical()
	{
		return backgroundPositionVertical;
	}

	/**
	 * @返回 borderInfo变量的值
	 */
	public final BorderInfo[] getBorderInfo()
	{
		return borderInfo;
	}

	/**
	 * @返回 fovimage变量的值
	 */
	public final FovImage getFovimage()
	{
		return fovimage;
	}

	/**
	 * @返回 padding变量的值
	 */
	public final CondLengthProperty[] getPadding()
	{
		return padding;
	}

	/**
	 * @返回 bEFORE变量的值
	 */
	public static final int getBEFORE()
	{
		return BEFORE;
	}

	/**
	 * @返回 aFTER变量的值
	 */
	public static final int getAFTER()
	{
		return AFTER;
	}

	/**
	 * @返回 sTART变量的值
	 */
	public static final int getSTART()
	{
		return START;
	}

	public CommonBorderPaddingBackground clone()
	{
		EnumProperty backgroundAttachment = new EnumProperty(
				this.backgroundAttachment, "");

		WiseDocColor backgroundColor = (WiseDocColor) this.backgroundColor;

		Color newbackgroundColor = backgroundColor != null ? new WiseDocColor(
				new Color(backgroundColor.getRed(), backgroundColor.getGreen(),
						backgroundColor.getBlue()), backgroundColor.getLayer())
				: null;

		String backgroundImage = this.backgroundImage != null ? new String(
				this.backgroundImage) : null;

		int backgroundImageLayer = this.backgroundImageLayer;

		int backgroundRepeat = this.backgroundRepeat;

		Length backgroundPositionHorizontal = this.backgroundPositionHorizontal != null ? this.backgroundPositionHorizontal
				.clone()
				: null;

		Length backgroundPositionVertical = this.backgroundPositionVertical != null ? this.backgroundPositionVertical
				.clone()
				: null;

		FovImage fovimage = this.fovimage != null ? this.fovimage.clone()
				: null;

		// 四个方向上padding的长度

		Map<Integer, Object> attrmap = new HashMap<Integer, Object>();
		attrmap.put(Constants.PR_BACKGROUND_ATTACHMENT, backgroundAttachment);
		attrmap.put(Constants.PR_BACKGROUND_COLOR, newbackgroundColor);
		attrmap.put(Constants.PR_BACKGROUNDGRAPHIC_LAYER, backgroundImageLayer);
		attrmap.put(Constants.PR_BACKGROUND_IMAGE, backgroundImage);
		attrmap.put(Constants.PR_BACKGROUND_REPEAT, backgroundRepeat);
		attrmap.put(Constants.PR_BACKGROUND_POSITION_HORIZONTAL,
				backgroundPositionHorizontal);
		attrmap.put(Constants.PR_BACKGROUND_POSITION_VERTICAL,
				backgroundPositionVertical);
		CommonBorderPaddingBackground cbpb = new CommonBorderPaddingBackground(
				attrmap);
		if (this.borderInfo != null)
		{
			int size = this.borderInfo.length;
			if (size > 0)
			{
				for (int i = 0; i < size; i++)
				{
					BorderInfo current = this.borderInfo[i];
					if (current != null)
					{
						BorderInfo currentclone = current.clone();
						cbpb.setBorderInfo(currentclone, i);
					} else
					{
						cbpb.setBorderInfo(null, i);
					}
				}
			}
		}
		CondLengthProperty[] padding = this.padding;
		if (padding != null)
		{
			CondLengthProperty[] paddingnew =
			{ padding[0].clone(), padding[1].clone(), padding[2].clone(),
					padding[3].clone(), };
			cbpb.setPadding(paddingnew);
		}
		this.fovimage = fovimage;
		return cbpb;
	}
}
