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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.SwingWorker;

import org.apache.batik.bridge.InterruptedBridgeException;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.swing.gvt.GVTTreeRenderer;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.gvt.GVTTreeRendererListener;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.util.EventDispatcher;
import org.apache.batik.util.EventDispatcher.Dispatcher;

/**
 * A Swing Icon that draws an SVG image.
 * 
 * @author <a href="mailto:cam%40mcc%2eid%2eau">Cameron McCormack</a>
 * @authot <a href="mailto:kirillcool%40yahoo%2ecom">Kirill Grouchnikov</a>
 */
abstract class SvgBatikIcon extends UserAgentAdapter implements Icon {
	/**
	 * Contains all precomputed images.
	 */
	protected Map<String, BufferedImage> cachedImages = new HashMap<String, BufferedImage>();

	/**
	 * The width of the rendered image.
	 */
	protected int width;

	/**
	 * The height of the rendered image.
	 */
	protected int height;

	/**
	 * SVG byte array.
	 */
	protected byte[] svgBytes;

	/**
	 * The listeners.
	 */
	protected List listeners;

	/**
	 * Create a new SVG icon.
	 * 
	 * @param inputStream
	 *            The input stream to read the SVG document from.
	 * @param w
	 *            The width of the icon.
	 * @param h
	 *            The height of the icon.
	 * @throws IOException
	 *             in case any I/O operation failed.
	 */
	public SvgBatikIcon(InputStream inputStream, int w, int h)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		while (true) {
			int count = inputStream.read(b);
			if (count < 0)
				break;
			baos.write(b, 0, count);
		}
		this.svgBytes = baos.toByteArray();
		this.width = w;
		this.height = h;
		this.listeners = Collections.synchronizedList(new LinkedList());

		this.renderGVTTree(this.width, this.height);
	}

	/**
	 * A transcoder that generates a BufferedImage.
	 */
	public static class BufferedImageTranscoder extends ImageTranscoder {

		/**
		 * The BufferedImage generated from the SVG document.
		 */
		protected BufferedImage bufferedImage;

		/**
		 * Creates a new ARGB image with the specified dimension.
		 * 
		 * @param width
		 *            the image width in pixels
		 * @param height
		 *            the image height in pixels
		 */
		@Override
		public BufferedImage createImage(int width, int height) {
			return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		}

		/**
		 * Writes the specified image to the specified output.
		 * 
		 * @param img
		 *            the image to write
		 * @param output
		 *            the output where to store the image
		 * @param TranscoderException
		 *            if an error occured while storing the image
		 */
		@Override
		public void writeImage(BufferedImage img, TranscoderOutput output)
				throws TranscoderException {
			bufferedImage = img;
		}

		/**
		 * Returns the {@link BufferedImage} generated from the SVG document.
		 * 
		 * @return {@link BufferedImage} generated from the SVG document.
		 */
		public BufferedImage getBufferedImage() {
			return bufferedImage;
		}

		/**
		 * Set the dimensions to be used for the image.
		 * 
		 * @param w
		 *            Width.
		 * @param h
		 *            Height.
		 */
		public void setDimensions(int w, int h) {
			hints.put(KEY_WIDTH, new Float(w));
			hints.put(KEY_HEIGHT, new Float(h));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Icon#getIconWidth()
	 */
	public int getIconWidth() {
		return width;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Icon#getIconHeight()
	 */
	public int getIconHeight() {
		return height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Icon#paintIcon(java.awt.Component, java.awt.Graphics,
	 * int, int)
	 */
	public void paintIcon(Component c, Graphics g, int x, int y) {
		BufferedImage image = this.cachedImages.get(this.getIconWidth() + ":"
				+ this.getIconHeight());
		if (image != null) {
			int dx = (this.width - image.getWidth()) / 2;
			int dy = (this.height - image.getHeight()) / 2;
			g.drawImage(image, x + dx, y + dy, null);
		}
	}

	// UserAgent /////////////////////////////////////////////////////////////

	/**
	 * Returns the default size of this user agent.
	 */
	@Override
	public Dimension2D getViewportSize() {
		return new Dimension(width, height);
	}

	/**
	 * Sets the preferred size for <code>this</code> icon. The rendering is
	 * scheduled automatically.
	 * 
	 * @param dim
	 *            Preferred size.
	 */
	public synchronized void setPreferredSize(Dimension dim) {
		if ((this.width == dim.width) && (this.height == dim.height))
			return;
		this.width = dim.width;
		this.height = dim.height;
		this.renderGVTTree(this.width, this.height);
	}

	/**
	 * Returns the SVG bytes of the loaded SVG image.
	 * 
	 * @return SVG bytes of the loaded SVG image.
	 */
	public byte[] getSvgBytes() {
		return this.svgBytes;
	}

	/**
	 * Fires event.
	 * 
	 * @param dispatcher
	 *            Event dispatcher.
	 * @param event
	 *            Event data.
	 */
	public void fireEvent(Dispatcher dispatcher, Object event) {
		EventDispatcher.fireEvent(dispatcher, listeners, event, true);
	}

	/**
	 * Renders the GVT tree.
	 * 
	 * @param renderWidth
	 *            Requested rendering width.
	 * @param renderHeight
	 *            Requested rendering height.
	 * @return If <code>true</code>, the image is already computed and cached.
	 */
	protected boolean renderGVTTree(final int renderWidth,
			final int renderHeight) {
		String key = renderWidth + ":" + renderHeight;
		if (this.cachedImages.containsKey(key)) {
			return true;
		}

		SwingWorker<BufferedImage, Void> worker = new SwingWorker<BufferedImage, Void>() {
			@Override
			protected BufferedImage doInBackground() throws Exception {
				GVTTreeRendererEvent ev = new GVTTreeRendererEvent(this, null);
				try {
					ev = new GVTTreeRendererEvent(this, null);
					fireEvent(startedDispatcher, ev);

					BufferedImageTranscoder t = new BufferedImageTranscoder();
					if (renderWidth != 0 && renderHeight != 0) {
						t.setDimensions(renderWidth, renderHeight);
					}

					InputStream is = new ByteArrayInputStream(svgBytes);
					TranscoderInput ti = new TranscoderInput(is);
					t.transcode(ti, null);

					BufferedImage result = t.getBufferedImage();
					return result;
				} catch (InterruptedBridgeException e) {
					// this sometimes happens with SVG Fonts since the glyphs
					// are not built till the rendering stage
					fireEvent(cancelledDispatcher, ev);
				} catch (ThreadDeath td) {
					fireEvent(failedDispatcher, ev);
					throw td;
				} catch (Throwable t) {
					fireEvent(failedDispatcher, ev);
				}
				return null;
			}

			@Override
			protected void done() {
				GVTTreeRendererEvent ev = new GVTTreeRendererEvent(this, null);
				try {
					BufferedImage bufferedImage = get();
					if (bufferedImage != null) {
						// width = bufferedImage.getWidth();
						// height = bufferedImage.getHeight();

						synchronized (SvgBatikIcon.this) {
							cachedImages.put(renderWidth + ":" + renderHeight,
									bufferedImage);
						}

						ev = new GVTTreeRendererEvent(this, bufferedImage);
						fireEvent(completedDispatcher, ev);
					}
				} catch (Throwable t) {
					fireEvent(failedDispatcher, ev);
				}
			}
		};
		worker.execute();

		// executorService.execute(new Renderer(this.svgBytes, this.width,
		// this.height));
		return false;
	}

	/**
	 * Adds a {@link GVTTreeRendererListener} to this {@link GVTTreeRenderer}.
	 * 
	 * @param l
	 *            Listener to add.
	 */
	@SuppressWarnings("unchecked")
	public void addGVTTreeRendererListener(GVTTreeRendererListener l) {
		// this.listenerList.add(GVTTreeRendererListener.class, l);
		listeners.add(l);
	}

	/**
	 * Removes a {@link GVTTreeRendererListener}ner from this
	 * {@link GVTTreeRenderer}.
	 * 
	 * @param l
	 *            Listener to remove.
	 */
	public void removeGVTTreeRendererListener(GVTTreeRendererListener l) {
		listeners.remove(l);
	}

	/**
	 * Dispatcher for GVT tree rendering completion.
	 */
	static Dispatcher completedDispatcher = new Dispatcher() {
		public void dispatch(Object listener, Object event) {
			((GVTTreeRendererListener) listener)
					.gvtRenderingCompleted((GVTTreeRendererEvent) event);
		}
	};

	/**
	 * Dispatcher for GVT tree rendering start.
	 */
	static Dispatcher startedDispatcher = new Dispatcher() {
		public void dispatch(Object listener, Object event) {
			((GVTTreeRendererListener) listener)
					.gvtRenderingStarted((GVTTreeRendererEvent) event);
		}
	};

	/**
	 * Dispatcher for GVT tree rendering fail.
	 */
	static Dispatcher failedDispatcher = new Dispatcher() {
		public void dispatch(Object listener, Object event) {
			((GVTTreeRendererListener) listener)
					.gvtRenderingFailed((GVTTreeRendererEvent) event);
		}
	};

	/**
	 * Dispatcher for GVT tree rendering cancel.
	 */
	static Dispatcher cancelledDispatcher = new Dispatcher() {
		public void dispatch(Object listener, Object event) {
			((GVTTreeRendererListener) listener)
					.gvtRenderingCancelled((GVTTreeRendererEvent) event);
		}
	};
}
