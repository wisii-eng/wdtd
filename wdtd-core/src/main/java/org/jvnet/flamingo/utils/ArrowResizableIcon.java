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
package org.jvnet.flamingo.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.jvnet.flamingo.common.icon.ResizableIcon;

/**
 * Helper implementation of {@link ResizableIcon} that draws an arrow.
 * 
 * @author Kirill Grouchnikov
 */
public class ArrowResizableIcon implements ResizableIcon {
	/**
	 * Initial dimension.
	 */
	private Dimension initialDim;

	/**
	 * The current icon width.
	 */
	protected int width;

	/**
	 * The current icon height.
	 */
	protected int height;

	/**
	 * Arrow direction. One of {@link SwingConstants#SOUTH},
	 * {@link SwingConstants#NORTH}, {@link SwingConstants#EAST} or
	 * {@link SwingConstants#WEST}.
	 */
	protected int direction;

	/**
	 * Creates a new arrow resizable icon.
	 * 
	 * @param initialDim
	 *            Initial icon dimension.
	 * @param direction
	 *            Arrow direction. Must be one of {@link SwingConstants#SOUTH},
	 *            {@link SwingConstants#NORTH}, {@link SwingConstants#EAST} or
	 *            {@link SwingConstants#WEST}.
	 */
	public ArrowResizableIcon(Dimension initialDim, int direction) {
		this.initialDim = initialDim;
		this.width = initialDim.width;
		this.height = initialDim.height;
		this.direction = direction;
	}

	/**
	 * Creates a new arrow resizable icon.
	 * 
	 * @param initialDim
	 *            Initial icon dimension.
	 * @param direction
	 *            Arrow direction. Must be one of {@link SwingConstants#SOUTH},
	 *            {@link SwingConstants#NORTH}, {@link SwingConstants#EAST} or
	 *            {@link SwingConstants#WEST}.
	 */
	public ArrowResizableIcon(int initialDim, int direction) {
		this(new Dimension(initialDim, initialDim), direction);
	}

	public void revertToOriginalDimension() {
		this.width = initialDim.width;
		this.height = initialDim.height;
	}

	public void setDimension(Dimension newDimension) {
		this.width = newDimension.width;
		this.height = newDimension.height;
	}

	public int getIconHeight() {
		return this.height;
	}

	public int getIconWidth() {
		return this.width;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D graphics = (Graphics2D) g.create();

		Color arrowColor = c.isEnabled() ? Color.black : Color.gray;
		graphics.setColor(arrowColor);
		float strokeWidth = this.width / 8.0f;
		if (strokeWidth < 1.0f)
			strokeWidth = 1.0f;
		Stroke stroke = new BasicStroke(strokeWidth, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER);

		graphics.setStroke(stroke);
		graphics.translate(x, y);
		GeneralPath gp = new GeneralPath();
		switch (direction) {
		case SwingUtilities.SOUTH:
			gp.moveTo(0, 2);
			gp.lineTo((float) 0.5 * (width - 1), height - 2);
			gp.lineTo(width - 1, 2);
			break;
		case SwingUtilities.NORTH:
			gp.moveTo(0, height - 2);
			gp.lineTo((float) 0.5 * (width - 1), 2);
			gp.lineTo(width - 1, height - 2);
			break;
		case SwingUtilities.EAST:
			gp.moveTo(2, 0);
			gp.lineTo(width - 2, (float) 0.5 * (height - 1));
			gp.lineTo(2, height - 1);
			break;
		case SwingUtilities.WEST:
			gp.moveTo(width - 2, 0);
			gp.lineTo(2, (float) 0.5 * (height - 1));
			gp.lineTo(width - 2, height - 1);
			break;
		}
		if (this.width < 9) {
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_OFF);
			graphics.draw(gp);
		}
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.draw(gp);
		graphics.dispose();
	}
}
