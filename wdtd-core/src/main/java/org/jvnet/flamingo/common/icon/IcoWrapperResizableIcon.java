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
/*
 * Copyright (c) 2005-2009 Flamingo Kirill Grouchnikov. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  o Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *     
 *  o Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution. 
 *     
 *  o Neither the name of Flamingo Kirill Grouchnikov nor the names of 
 *    its contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission. 
 *     
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
package org.jvnet.flamingo.common.icon;

import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Implementation of {@link ResizableIcon} interface that wraps ICO files.
 * 
 * @author Kirill Grouchnikov
 */
public class IcoWrapperResizableIcon extends IcoWrapperIcon implements
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
	public static IcoWrapperResizableIcon getIcon(URL location,
			final Dimension initialDim) {
		try {
			return new IcoWrapperResizableIcon(location.openStream(),
					initialDim);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}
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
	public static IcoWrapperResizableIcon getIcon(InputStream inputStream,
			final Dimension initialDim) {
		return new IcoWrapperResizableIcon(inputStream, initialDim);
	}

	/**
	 * Creates a new ICO-based resizable icon.
	 * 
	 * @param inputStream
	 *            Input stream with the ICO content.
	 * @param initialDim
	 *            Initial dimension of the icon.
	 */
	private IcoWrapperResizableIcon(InputStream inputStream,
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
