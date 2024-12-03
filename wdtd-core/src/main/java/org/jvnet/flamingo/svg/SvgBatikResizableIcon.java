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
package org.jvnet.flamingo.svg;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import javax.swing.event.EventListenerList;

import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.util.EventDispatcher.Dispatcher;
import org.jvnet.flamingo.common.AsynchronousLoadListener;
import org.jvnet.flamingo.common.AsynchronousLoading;
import org.jvnet.flamingo.common.icon.ResizableIcon;

/**
 * SVG-based implementation of {@link ResizableIcon} based on Apache Batik
 * library.
 * 
 * @author Kirill Grouchnikov.
 */
public class SvgBatikResizableIcon extends SvgBatikIcon implements
		ResizableIcon, AsynchronousLoading {
	/**
	 * Initial dimension.
	 */
	private Dimension initialDim;

	/**
	 * The listeners.
	 */
	protected EventListenerList listenerList;

	/**
	 * Constructs an input stream with uncompressed contents from the specified
	 * input stream with compressed contents.
	 * 
	 * @param zippedStream
	 *            Input stream with compressed contents.
	 * @return Input stream with uncompressed contents.
	 * @throws IOException
	 *             in case any I/O operation failed.
	 */
	protected static InputStream constructFromZipStream(InputStream zippedStream)
			throws IOException {
		GZIPInputStream gis = new GZIPInputStream(zippedStream);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		byte[] buf = new byte[2048];
		int len;
		while ((len = gis.read(buf)) != -1) {
			baos.write(buf, 0, len);
		}

		return new ByteArrayInputStream(baos.toByteArray());
	}

	/**
	 * Returns the icon for the specified URL. The URL is assumed to point to
	 * uncompressed SVG contents.
	 * 
	 * @param location
	 *            Icon URL.
	 * @param initialDim
	 *            Initial dimension of the icon.
	 * @return Icon instance.
	 */
	public static SvgBatikResizableIcon getSvgIcon(URL location,
			final Dimension initialDim) {
		try {
			return new SvgBatikResizableIcon(location.openStream(), initialDim);
		} catch (IOException ioe) {
			return null;
		}
	}

	/**
	 * Returns the icon for the specified input stream. The stream is assumed to
	 * contain uncompressed SVG contents.
	 * 
	 * @param inputStream
	 *            Icon stream.
	 * @param initialDim
	 *            Initial dimension of the icon.
	 * @return Icon instance.
	 */
	public static SvgBatikResizableIcon getSvgIcon(InputStream inputStream,
			final Dimension initialDim) {
		if (inputStream == null)
			return null;
		try {
			return new SvgBatikResizableIcon(inputStream, initialDim);
		} catch (IOException ioe) {
			return null;
		}
	}

	/**
	 * Returns the icon for the specified URL. The URL is assumed to point to
	 * compressed SVG contents.
	 * 
	 * @param location
	 *            Icon URL.
	 * @param initialDim
	 *            Initial dimension of the icon.
	 * @return Icon instance.
	 */
	public static SvgBatikResizableIcon getSvgzIcon(URL location,
			final Dimension initialDim) {
		try {
			return new SvgBatikResizableIcon(constructFromZipStream(location
					.openStream()), initialDim);
		} catch (IOException ioe) {
			return null;
		}
	}

	/**
	 * Returns the icon for the specified input stream. The stream is assumed to
	 * contain compressed SVG contents.
	 * 
	 * @param inputStream
	 *            Icon stream.
	 * @param initialDim
	 *            Initial dimension of the icon.
	 * @return Icon instance.
	 */
	public static SvgBatikResizableIcon getSvgzIcon(InputStream inputStream,
			final Dimension initialDim) {
		try {
			return new SvgBatikResizableIcon(
					constructFromZipStream(inputStream), initialDim);
		} catch (IOException ioe) {
			return null;
		}
	}

	/**
	 * Creates a new resizable icon based on SVG content.
	 * 
	 * @param inputStream
	 *            Input stream with uncompressed SVG content.
	 * @param initialDim
	 *            Initial dimension.
	 * @throws IOException
	 *             in case any I/O operation failed.
	 */
	@SuppressWarnings("unchecked")
	private SvgBatikResizableIcon(InputStream inputStream,
			final Dimension initialDim) throws IOException {
		super(inputStream, initialDim.width, initialDim.height);
		this.listenerList = new EventListenerList();

		this.initialDim = initialDim;
		this.addGVTTreeRendererListener(new GVTTreeRendererAdapter() {
			@Override
			public void gvtRenderingCompleted(GVTTreeRendererEvent e) {
				fireAsyncCompleted(Boolean.valueOf(true));
			}

			@Override
			public void gvtRenderingFailed(GVTTreeRendererEvent arg0) {
				fireAsyncCompleted(Boolean.valueOf(false));
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jvnet.flamingo.common.icon.ResizableIcon#revertToOriginalDimension()
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jvnet.flamingo.common.AsynchronousLoading#addAsynchronousLoadListener
	 * (org.jvnet.flamingo.common.AsynchronousLoadListener)
	 */
	@SuppressWarnings("unchecked")
	public void addAsynchronousLoadListener(AsynchronousLoadListener l) {
		listenerList.add(AsynchronousLoadListener.class, l);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jvnet.flamingo.common.AsynchronousLoading#removeAsynchronousLoadListener
	 * (org.jvnet.flamingo.common.AsynchronousLoadListener)
	 */
	public void removeAsynchronousLoadListener(AsynchronousLoadListener l) {
		listenerList.remove(AsynchronousLoadListener.class, l);
	}

	@Override
	protected boolean renderGVTTree(int renderWidth, int renderHeight) {
		boolean cached = super.renderGVTTree(renderWidth, renderHeight);
		if (cached) {
			this.fireAsyncCompleted(Boolean.valueOf(true));
		}
		return cached;
	}

	/**
	 * Fires the asynchronous load event.
	 * 
	 * @param event
	 *            Event object.
	 */
	protected void fireAsyncCompleted(Boolean event) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == AsynchronousLoadListener.class) {
				((AsynchronousLoadListener) listeners[i + 1]).completed(event);
			}
		}
	}

	/**
	 * Dispatcher for GVT tree rendering completion.
	 */
	static Dispatcher asyncCompletedDispatcher = new Dispatcher() {
		public void dispatch(Object listener, Object event) {
			((AsynchronousLoadListener) listener).completed((Boolean) event);
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jvnet.flamingo.common.AsynchronousLoading#isLoading()
	 */
	@Override
	public synchronized boolean isLoading() {
		BufferedImage image = this.cachedImages.get(this.getIconWidth() + ":"
				+ this.getIconHeight());
		return (image == null);
	}
}
