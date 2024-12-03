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
 * Copyright (c) 2005-2008 Flamingo Kirill Grouchnikov. All Rights Reserved.
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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 * Decorator icon that adds <code>stacked</code> appearance to the decorated
 * icon. The original icon is drawn in the middle (it's made smaller). In
 * addition, a few rotated rectangular frames are drawn on margins to create an
 * appearance of multiple rotated images stacked one on another.
 * 
 * @author Kirill Grouchnikov
 */
public class StackIcon implements ResizableIcon {
	/**
	 * The delegate (decorated) icon.
	 */
	protected ResizableIcon delegate;

	/**
	 * Margin thickness (0.0 - no margin, 1.0 - only margins).
	 */
	protected double marginThickness;

	/**
	 * Margin dimension in X axis.
	 */
	protected int marginX;

	/**
	 * Margin dimension in Y axis.
	 */
	protected int marginY;

	/**
	 * Creates a new decorate icon.
	 * 
	 * @param delegate
	 *            The original icon.
	 * @param marginThickness
	 *            Margin thickness (0.0 - no margin, 1.0 - only margins).
	 */
	public StackIcon(ResizableIcon delegate, double marginThickness) {
		this.delegate = delegate;
		this.marginThickness = marginThickness;
		this.setDimension(new Dimension(delegate.getIconWidth(), delegate
				.getIconHeight()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Icon#getIconHeight()
	 */
	public int getIconHeight() {
		return (int) ((1 + 2.0 * this.marginThickness) * delegate
				.getIconHeight());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Icon#getIconWidth()
	 */
	public int getIconWidth() {
		return (int) ((1 + 2.0 * this.marginThickness) * delegate
				.getIconWidth());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Icon#paintIcon(java.awt.Component, java.awt.Graphics,
	 * int, int)
	 */
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D graphics = (Graphics2D) g.create();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		Rectangle rect = new Rectangle(x + marginX / 2, y + marginY / 2,
				this.delegate.getIconWidth() + marginX, this.delegate
						.getIconHeight()
						+ marginY);
		int rx = x + marginX + this.delegate.getIconWidth() / 2;
		int ry = y + marginY + this.delegate.getIconHeight() / 2;
		this.paintFrame(graphics, rotateRect(rect, rx - 2, ry, -8.0));
		this.paintFrame(graphics, rotateRect(rect, rx, ry + 2, 5.0));
		this.paintFrame(graphics, rect);

		graphics.dispose();
		delegate.paintIcon(c, g, (1 + x + marginX), (1 + y + marginY));
	}

	/**
	 * Rotates a point around a pivot.
	 * 
	 * @param x
	 *            X coordinate of the point to rotate.
	 * @param y
	 *            Y coordinate of the point to rotate.
	 * @param rx
	 *            X coordinate of the pivot.
	 * @param ry
	 *            Y coordinate of the pivot.
	 * @param theta
	 *            Angles (in degrees).
	 * @return Coordinates of the rotated point.
	 */
	protected Point2D rotatePoint(int x, int y, int rx, int ry, double theta) {
		double st = Math.sin(Math.PI * theta / 180.0);
		double ct = Math.cos(Math.PI * theta / 180.0);

		return new Point2D.Double(rx + (x - rx) * ct - (y - ry) * st, ry
				+ (y - ry) * ct + (x - rx) * st);
	}

	/**
	 * Rotates rectangle around a pivot.
	 * 
	 * @param rect
	 *            The original rectangle.
	 * @param rx
	 *            X coordinate of the pivot.
	 * @param ry
	 *            Y coordinate of the pivot.
	 * @param theta
	 *            Angles (in degrees).
	 * @return The rotated rectangle (as general path).
	 */
	protected GeneralPath rotateRect(Rectangle rect, int rx, int ry,
			double theta) {

		GeneralPath res = new GeneralPath();
		Point2D p1 = rotatePoint(rect.x, rect.y, rx, ry, theta);
		Point2D p2 = rotatePoint(rect.x + rect.width, rect.y, rx, ry, theta);
		Point2D p3 = rotatePoint(rect.x + rect.width, rect.y + rect.height, rx,
				ry, theta);
		Point2D p4 = rotatePoint(rect.x, rect.y + rect.height, rx, ry, theta);
		res.moveTo((float) p1.getX(), (float) p1.getY());
		res.lineTo((float) p2.getX(), (float) p2.getY());
		res.lineTo((float) p3.getX(), (float) p3.getY());
		res.lineTo((float) p4.getX(), (float) p4.getY());
		res.lineTo((float) p1.getX(), (float) p1.getY());

		return res;
	}

	/**
	 * Paints a single frame.
	 * 
	 * @param graphics
	 *            Graphic context.
	 * @param shape
	 *            Frame to paint.
	 */
	protected void paintFrame(Graphics2D graphics, Shape shape) {
		graphics.setStroke(new BasicStroke(0.8f));
		graphics.setColor(Color.white);
		graphics.fill(shape);
		graphics.setColor(Color.darkGray);
		graphics.draw(shape);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jvnet.flamingo.ribbon.ResizableIcon#setDimension(java.awt.Dimension)
	 */
	public void setDimension(Dimension newDimension) {
		this.delegate.setDimension(newDimension);
	}
}
