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
package com.wisii.wisedoc.view.ui.parts.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.CondLengthProperty;
import com.wisii.wisedoc.document.attribute.EnumProperty;
import com.wisii.wisedoc.render.java2d.Java2DGraphicsState;
import com.wisii.wisedoc.render.java2d.Java2DRenderer;
import com.wisii.wisedoc.traits.BorderProps;

public class BorderStylePreviewPanel extends JPanel
{
	Map<Integer, Object> borderProperties;

	public BorderStylePreviewPanel()
	{
		this.setLayout(new BorderLayout());
		final JLabel label = new JLabel();
		label.setBounds(28, 10, 240, 130);
		label.setFont(new Font("微软雅黑", Font.PLAIN, 36));
		label.setText("        预览区");
		this.add(label, BorderLayout.CENTER);
	}

	public void updateStyle(Map<Integer, Object> properties)
	{
		this.borderProperties = properties;
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D tg = (Graphics2D) g.create();
		/*
		 * AffineTransform at = new AffineTransform(); at.scale(2, 2);
		 * tg.setTransform(at);
		 */

		Style ss = new Style();

		if (borderProperties != null && borderProperties.size() != 0)
		{
			int stylebefore = Constants.EN_NONE;
			if (borderProperties.get(Constants.PR_BORDER_BEFORE_STYLE) instanceof EnumProperty)
			{
				stylebefore = ((EnumProperty) borderProperties
						.get(Constants.PR_BORDER_BEFORE_STYLE)).getEnum();
			}
			BorderProps borderBefore = new BorderProps(stylebefore,
					convertWidth(borderProperties
							.get(Constants.PR_BORDER_BEFORE_WIDTH)),
					(Color) borderProperties
							.get(Constants.PR_BORDER_BEFORE_COLOR),
					Constants.EN_SEPARATE);
			int styleafter = Constants.EN_NONE;
			if (borderProperties.get(Constants.PR_BORDER_AFTER_STYLE) instanceof EnumProperty)
			{
				styleafter = ((EnumProperty) borderProperties
						.get(Constants.PR_BORDER_AFTER_STYLE)).getEnum();
			}
			BorderProps borderAfter = new BorderProps(styleafter,
					convertWidth(borderProperties
							.get(Constants.PR_BORDER_AFTER_WIDTH)),
					(Color) borderProperties
							.get(Constants.PR_BORDER_AFTER_COLOR),
					Constants.EN_SEPARATE);
			int stylestart = Constants.EN_NONE;
			if (borderProperties.get(Constants.PR_BORDER_START_STYLE) instanceof EnumProperty)
			{
				stylestart = ((EnumProperty) borderProperties
						.get(Constants.PR_BORDER_START_STYLE)).getEnum();
			}
			BorderProps borderStart = new BorderProps(stylestart,
					convertWidth(borderProperties
							.get(Constants.PR_BORDER_START_WIDTH)),
					(Color) borderProperties
							.get(Constants.PR_BORDER_START_COLOR),
					Constants.EN_SEPARATE);
			int styleend = Constants.EN_NONE;
			if (borderProperties.get(Constants.PR_BORDER_END_STYLE) instanceof EnumProperty)
			{
				styleend = ((EnumProperty) borderProperties
						.get(Constants.PR_BORDER_END_STYLE)).getEnum();
			}
			BorderProps borderEnd = new BorderProps(
					styleend,
					convertWidth(borderProperties
							.get(Constants.PR_BORDER_END_WIDTH)),
					(Color) borderProperties.get(Constants.PR_BORDER_END_COLOR),
					Constants.EN_SEPARATE);

			Rectangle2D.Float borderRect = new Rectangle2D.Float(10, 10, 290,
					110);
			ss.drawBorders(tg, borderRect, borderBefore, borderAfter,
					borderStart, borderEnd);
		}

		/*
		 * BorderProps border = new BorderProps(Constants.EN_DOTTED, 10000, new
		 * Color(0), Constants.EN_SEPARATE); Rectangle2D.Float borderRect = new
		 * Rectangle2D.Float(0, 0, 200, 200); ss.drawBorders(tg, borderRect,
		 * border, border, border, border)
		 */;

		/*
		 * AffineTransform at = new AffineTransform(); at.scale(2, 2);
		 * tg.setTransform(at);
		 * 
		 * for (int i = 0; i < 300; i += 30) {
		 * 
		 * Rectangle2D.Float borderRect = new Rectangle2D.Float(i, 0, 30, 15);
		 * 
		 * Java2DRenderer.drawBorderLine(borderRect, true, false,
		 * Constants.EN_DOTTED , new Color(0), (Graphics2D) tg); }
		 * 
		 * for (int i = 0; i < 300; i += 30) {
		 * 
		 * Rectangle2D.Float borderRect = new Rectangle2D.Float(i, 80, 30, 15);
		 * Java2DRenderer.drawBorderLine(borderRect, true, false,
		 * Constants.EN_DOTTED , new Color(0), (Graphics2D) tg); }
		 * 
		 * for (int i = 0; i < 90; i += 30) {
		 * 
		 * Rectangle2D.Float borderRect = new Rectangle2D.Float(0, i, 15, 30);
		 * 
		 * Java2DRenderer.drawBorderLine(borderRect, false, true,
		 * Constants.EN_DOTTED , new Color(0), (Graphics2D) tg); }
		 * 
		 * for (int i = 0; i < 90; i += 30) {
		 * 
		 * Rectangle2D.Float borderRect = new Rectangle2D.Float(300, i, 15, 30);
		 * 
		 * Java2DRenderer.drawBorderLine(borderRect, false, true,
		 * Constants.EN_DOTTED , new Color(0), (Graphics2D) tg); }
		 */

		// System.out.println("height: " + this.HEIGHT + " width: " +
		// this.WIDTH);
	}

	public int convertWidth(Object width)
	{
		if (width instanceof CondLengthProperty)
		{
			CondLengthProperty value = (CondLengthProperty) width;
			return value.getLengthValue();
		}
		return 0;
	}

	/*
	 * @Override public void paint(Graphics g) { super.paint(g); Style ss = new
	 * Style(); Graphics2D tg = (Graphics2D) g.create(); BorderProps border =
	 * new BorderProps(Constants.EN_DOTTED, 10000, new Color(0),
	 * Constants.EN_SEPARATE); Rectangle2D.Float borderRect = new
	 * Rectangle2D.Float(0, 0, 200, 200); ss.drawBorders(tg, borderRect, border,
	 * border, border, border); }
	 */

	private class Style extends Java2DRenderer
	{

		protected void drawBorders(Graphics2D graphics, Float borderRect,
				BorderProps bpsBefore, BorderProps bpsAfter,
				BorderProps bpsStart, BorderProps bpsEnd)
		{

			AffineTransform at = graphics.getTransform();
			at.scale(scaleX, scaleY);
			graphics.setTransform(at);

			this.state = new Java2DGraphicsState(graphics, this.fontInfo, at);

			this.drawBorders(borderRect, bpsBefore, bpsAfter, bpsStart, bpsEnd);

		}
		protected void drawBorderLine(float x1, float y1, float x2,
				float y2, boolean horz, boolean startOrBefore, int style, Color col){
			Graphics2D g2d = state.getGraph();
			drawBorderLine(new Rectangle2D.Float(x1, y1, x2 - x1, y2 - y1), horz,
					startOrBefore, style, col, g2d);
		}
	}

	/*
	 * // 测试用 public static void main(String[] args) {
	 * 
	 * // 正常的tab的测试程序 JFrame fr = new JFrame("测试用"); final FlowLayout flowLayout
	 * = new FlowLayout(); flowLayout.setAlignment(FlowLayout.LEFT);
	 * fr.getContentPane().setLayout(flowLayout); fr.setSize(462, 600);
	 * 
	 * BorderStylePanel tui = new BorderStylePanel(); tui.setPreferredSize(new
	 * Dimension(100,100));
	 * 
	 * fr.add(tui); fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 * fr.setVisible(true); }
	 */

}
