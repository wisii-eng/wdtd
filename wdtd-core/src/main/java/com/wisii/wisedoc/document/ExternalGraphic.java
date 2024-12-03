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
 * @ExternalGraphic.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.document;

import java.util.Map;
import com.wisii.wisedoc.apps.FOUserAgent;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.image.FovImage;
import com.wisii.wisedoc.image.ImageFactory;
import com.wisii.wisedoc.log.LogUtil;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2008-8-29
 */
public class ExternalGraphic extends AbstractGraphics {
	private int intrinsicWidth = 0;

	private int intrinsicHeight = 0;

	private byte iamgeByte[];
	public static final String TYPE_FUNCTION="func-by-param";
	public static final String TYPE_INTERFACE="func-by-interface";
	// 需要设置具体的图片名
	// 类型为回调函数时显示的图片名

	public static final String FUNCTIONIMAGE = "bindnodeimage.jpg";

	// 需要设置具体的图片名
	// 类型为字节数据时显示的图片名
	public static final String BINDATAIMAGE = "";
	public ExternalGraphic()
	{
		
	}
	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public ExternalGraphic(String src) {
		setAttribute(Constants.PR_SRC, src);
	}

	/**
	 * 初始化过程的描述
	 * 
	 * @param 初始化参数说明
	 * @exception 说明在某情况下,将发生什么异常}
	 */
	public ExternalGraphic(Map<Integer, Object> attributes) {
		super(attributes);
		initImageData();
	}

	@Override
	public void setAttributes(Map<Integer, Object> atts, boolean isreplace)
	{
		super.setAttributes(atts, isreplace);
	    //只有设置有图片路径或类型时，才重新加载图片
		if (atts.containsKey(Constants.PR_SRC)
				|| atts.containsKey(Constants.PR_SRC_TYPE))
		{
			initImageData();
		}
	}

	private void initImageData() {
		String src_type = getSrc_type();
		String src = getSrc();
		if ("func-by-param".equals(src_type)) {
			src = FUNCTIONIMAGE;
		}
		String url = ImageFactory.getURL(src);

		FOUserAgent userAgent = getUserAgent();
		ImageFactory fact = userAgent.getFactory().getImageFactory();
		FovImage fovimage = fact.getImage(url, userAgent);
		if (fovimage == null) {
			LogUtil.error("Image not available: " + getSrc());
			src = "";
		} else {
			// load dimensions
			if (!fovimage.load(FovImage.DIMENSIONS)) {
				LogUtil.error("Cannot read image dimensions: " + getSrc());
			}
			this.intrinsicWidth = fovimage.getIntrinsicWidth();
			this.intrinsicHeight = fovimage.getIntrinsicHeight();
			iamgeByte = fovimage.getRessourceBytes();
		}
	}

	/**
	 * @return the "src" property.
	 */
	public String getSrc() {
		Object src = getAttribute(Constants.PR_SRC);
		if(src instanceof BindNode||"func-by-param".equals(getSrc_type()))
		{
			src = "bindnodeimage.jpg";
		}
		return src.toString();
	}

	// add by lzy
	public int getAphla() {
		return (Integer) getAttribute(Constants.PR_APHLA);
	}

	public String getSrc_type() {
		return (String) getAttribute(Constants.PR_SRC_TYPE);
	}

	public byte[] getIamgeByte() {
		return iamgeByte;
	}

	// add end

	/**
	 * @return Get the resulting URL based on the src property.
	 */
	public String getURL() {
		return ImageFactory.getURL(getSrc());
	}

	/** @see com.wisii.fov.fo.FONode#getLocalName() */
	public String getLocalName() {
		return "external-graphic";
	}

	/**
	 * @see com.wisii.fov.fo.FObj#getNameId()
	 */
	public int getNameId() {
		return Constants.FO_EXTERNAL_GRAPHIC;
	}

	/**
	 * @see com.wisii.fov.fo.flow.AbstractGraphics#getIntrinsicWidth()
	 */
	public int getIntrinsicWidth() {
		return this.intrinsicWidth;
	}

	/**
	 * @see com.wisii.fov.fo.flow.AbstractGraphics#getIntrinsicHeight()
	 */
	public int getIntrinsicHeight() {
		return this.intrinsicHeight;
	}

	public FOUserAgent getUserAgent() {
		return DocumentUtil.getUserAgent();
	}
}
