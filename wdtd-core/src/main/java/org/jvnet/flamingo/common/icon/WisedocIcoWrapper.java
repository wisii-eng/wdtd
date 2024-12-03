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
package org.jvnet.flamingo.common.icon;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class WisedocIcoWrapper extends IcoWrapperIcon implements
ResizableIcon {
	/**
	 * Initial dimension.
	 */
	private Dimension initialDim;

	/**
	 * Returns the icon for the specified URL.
	 * 
	 * @param location
	 *            Icon URL.
	 * @param initialDim
	 *            Initial dimension of the icon.
	 * @return Icon instance.
	 */
	public static WisedocIcoWrapper getIcon(URL location,
			final Dimension initialDim) {
		try {
			return new WisedocIcoWrapper(location.openStream(),
					initialDim);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}
	}
	
	public BufferedImage getImage(){
		return cachedImages.get(this.getIconWidth() + ":"
				+ this.getIconHeight());
	}

	/**
	 * Returns the icon for the specified input stream.
	 * 
	 * @param inputStream
	 *            Icon input stream.
	 * @param initialDim
	 *            Initial dimension of the icon.
	 * @return Icon instance.
	 */
	public static WisedocIcoWrapper getIcon(InputStream inputStream,
			final Dimension initialDim) {
		return new WisedocIcoWrapper(inputStream, initialDim);
	}

	/**
	 * Creates a new ICO-based resizable icon.
	 * 
	 * @param inputStream
	 *            Input stream with the ICO content.
	 * @param initialDim
	 *            Initial dimension of the icon.
	 */
	private WisedocIcoWrapper(InputStream inputStream,
			Dimension initialDim) {
		super(inputStream, initialDim.width, initialDim.height);
		this.initialDim = initialDim;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.flamingo.common.ResizableIcon#revertToOriginalDimension()
	 */
	public void revertToOriginalDimension() {
		this.setPreferredSize(this.initialDim);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jvnet.flamingo.common.icon.ResizableIcon#setDimension(java.awt.Dimension
	 * )
	 */
	public void setDimension(Dimension dim) {
		this.setPreferredSize(dim);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.flamingo.common.icon.ResizableIcon#setHeight(int)
	 */
	public void setHeight(int height) {
		this.setPreferredSize(new Dimension(height, height));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.flamingo.common.icon.ResizableIcon#setWidth(int)
	 */
	public void setWidth(int width) {
		this.setPreferredSize(new Dimension(width, width));
	}
}
