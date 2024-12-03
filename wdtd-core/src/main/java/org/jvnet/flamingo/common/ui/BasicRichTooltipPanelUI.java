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
package org.jvnet.flamingo.common.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.text.AttributedString;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;

import org.jvnet.flamingo.common.RichTooltip;
import org.jvnet.flamingo.utils.FlamingoUtilities;

/**
 * Basic UI for rich tooltip panel {@link JRichTooltipPanel}.
 * 
 * @author Kirill Grouchnikov
 */
public class BasicRichTooltipPanelUI extends RichTooltipPanelUI {
	/**
	 * The associated tooltip panel.
	 */
	protected JRichTooltipPanel richTooltipPanel;

	protected JLabel titleLabel;

	protected java.util.List<JLabel> descriptionLabels;

	protected JLabel mainImageLabel;

	protected JSeparator footerSeparator;

	protected JLabel footerImageLabel;

	protected java.util.List<JLabel> footerLabels;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent c) {
		return new BasicRichTooltipPanelUI();
	}

	public BasicRichTooltipPanelUI() {
		this.descriptionLabels = new ArrayList<JLabel>();
		this.footerLabels = new ArrayList<JLabel>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#installUI(javax.swing.JComponent)
	 */
	@Override
	public void installUI(JComponent c) {
		this.richTooltipPanel = (JRichTooltipPanel) c;
		super.installUI(this.richTooltipPanel);
		installDefaults();
		installComponents();
		installListeners();

		this.richTooltipPanel.setLayout(createLayoutManager());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#uninstallUI(javax.swing.JComponent)
	 */
	@Override
	public void uninstallUI(JComponent c) {
		uninstallListeners();
		uninstallComponents();
		uninstallDefaults();
		super.uninstallUI(this.richTooltipPanel);
	}

	/**
	 * Installs default settings for the associated rich tooltip panel.
	 */
	protected void installDefaults() {
		Border b = this.richTooltipPanel.getBorder();
		if (b == null || b instanceof UIResource) {
			Border toSet = UIManager.getBorder("RichTooltipPanel.border");
			if (toSet == null)
				toSet = new BorderUIResource.CompoundBorderUIResource(
						new LineBorder(FlamingoUtilities.getBorderColor()),
						new EmptyBorder(2, 4, 3, 4));
			this.richTooltipPanel.setBorder(toSet);
		}
		LookAndFeel.installProperty(this.richTooltipPanel, "opaque",
				Boolean.TRUE);
	}

	/**
	 * Installs listeners on the associated rich tooltip panel.
	 */
	protected void installListeners() {
	}

	/**
	 * Installs components on the associated rich tooltip panel.
	 */
	protected void installComponents() {
	}

	/**
	 * Uninstalls default settings from the associated rich tooltip panel.
	 */
	protected void uninstallDefaults() {
		LookAndFeel.uninstallBorder(this.richTooltipPanel);
	}

	/**
	 * Uninstalls listeners from the associated rich tooltip panel.
	 */
	protected void uninstallListeners() {
	}

	/**
	 * Uninstalls subcomponents from the associated rich tooltip panel.
	 */
	protected void uninstallComponents() {
		this.removeExistingComponents();
	}

	@Override
	public void update(Graphics g, JComponent c) {
		this.paintBackground(g);
		this.paint(g, c);
	}

	protected void paintBackground(Graphics g) {
		Color main = FlamingoUtilities.getColor(Color.gray,
				"Label.disabledForeground").brighter();
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setPaint(new GradientPaint(0, 0, FlamingoUtilities.getLighterColor(
				main, 0.9), 0, this.richTooltipPanel.getHeight(),
				FlamingoUtilities.getLighterColor(main, 0.4)));
		g2d.fillRect(0, 0, this.richTooltipPanel.getWidth(),
				this.richTooltipPanel.getHeight());
		g2d.setFont(FlamingoUtilities.getFont(this.richTooltipPanel,
				"Ribbon.font", "Button.font", "Panel.font"));
		g2d.dispose();
	}

	@Override
	public void paint(Graphics g, JComponent c) {
	}

	protected LayoutManager createLayoutManager() {
		return new RichTooltipPanelLayout();
	}

	protected class RichTooltipPanelLayout implements LayoutManager {
		@Override
		public void addLayoutComponent(String name, Component comp) {
		}

		@Override
		public void removeLayoutComponent(Component comp) {
		}

		@Override
		public Dimension minimumLayoutSize(Container parent) {
			return this.preferredLayoutSize(parent);
		}

		@Override
		public Dimension preferredLayoutSize(Container parent) {
			Insets ins = parent.getInsets();
			int gap = getLayoutGap();
			Font font = FlamingoUtilities.getFont(parent, "Ribbon.font",
					"Button.font", "Panel.font");

			// the main text gets 200 pixels. The width is defined
			// by this and the presence of the main text.
			// The height is defined based on the width and the
			// text broken into multiline paragraphs

			int descTextWidth = getDescriptionTextWidth();
			int width = ins.left + 2 * gap + descTextWidth + ins.right;
			RichTooltip tooltipInfo = richTooltipPanel.getTooltipInfo();
			FontRenderContext frc = new FontRenderContext(
					new AffineTransform(), true, false);
			if (tooltipInfo.getMainImage() != null) {
				width += tooltipInfo.getMainImage().getWidth(null);
			}

			int fontHeight = parent.getFontMetrics(font).getHeight();

			int height = ins.top;

			// The title label
			height += fontHeight + gap;

			// The description text
			int descriptionTextHeight = 0;
			for (String descText : tooltipInfo.getDescriptionSections()) {
				AttributedString attributedDescription = new AttributedString(
						descText);
				attributedDescription.addAttribute(TextAttribute.FONT, font);
				LineBreakMeasurer lineBreakMeasurer = new LineBreakMeasurer(
						attributedDescription.getIterator(), frc);
				while (true) {
					TextLayout tl = lineBreakMeasurer.nextLayout(descTextWidth);
					if (tl == null)
						break;
					descriptionTextHeight += fontHeight;
				}
				// add an empty line after the paragraph
				descriptionTextHeight += fontHeight;
			}
			// remove the empty line after the last paragraph
			descriptionTextHeight -= fontHeight;

			if (tooltipInfo.getMainImage() != null) {
				height += Math.max(descriptionTextHeight, new JLabel(
						new ImageIcon(tooltipInfo.getMainImage()))
						.getPreferredSize().height);
			} else {
				height += descriptionTextHeight;
			}

			if ((tooltipInfo.getFooterImage() != null)
					|| (tooltipInfo.getFooterSections().size() > 0)) {
				height += gap;
				// The footer separator
				height += new JSeparator(JSeparator.HORIZONTAL)
						.getPreferredSize().height;

				height += gap;

				int footerTextHeight = 0;
				int availableWidth = descTextWidth;
				if (tooltipInfo.getFooterImage() != null) {
					availableWidth -= tooltipInfo.getFooterImage().getWidth(
							null);
				}
				if (tooltipInfo.getMainImage() != null) {
					availableWidth += tooltipInfo.getMainImage().getWidth(null);
				}
				for (String footerText : tooltipInfo.getFooterSections()) {
					AttributedString attributedDescription = new AttributedString(
							footerText);
					attributedDescription
							.addAttribute(TextAttribute.FONT, font);
					LineBreakMeasurer lineBreakMeasurer = new LineBreakMeasurer(
							attributedDescription.getIterator(), frc);
					while (true) {
						TextLayout tl = lineBreakMeasurer
								.nextLayout(availableWidth);
						if (tl == null)
							break;
						footerTextHeight += fontHeight;
					}
					// add an empty line after the paragraph
					footerTextHeight += fontHeight;
				}
				// remove the empty line after the last paragraph
				footerTextHeight -= fontHeight;

				if (tooltipInfo.getFooterImage() != null) {
					height += Math.max(footerTextHeight, new JLabel(
							new ImageIcon(tooltipInfo.getFooterImage()))
							.getPreferredSize().height);
				} else {
					height += footerTextHeight;
				}
			}

			height += ins.bottom;
			return new Dimension(width, height);
		}

		@Override
		public void layoutContainer(Container parent) {
			removeExistingComponents();

			Font font = FlamingoUtilities.getFont(parent, "Ribbon.font",
					"Button.font", "Panel.font");
			Insets ins = richTooltipPanel.getInsets();
			int y = ins.top;
			RichTooltip tooltipInfo = richTooltipPanel.getTooltipInfo();
			FontRenderContext frc = new FontRenderContext(
					new AffineTransform(), true, false);
			int gap = getLayoutGap();

			int fontHeight = parent.getFontMetrics(font).getHeight();

			// The title label
			titleLabel = new JLabel(tooltipInfo.getTitle());
			titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD));
			richTooltipPanel.add(titleLabel);

			titleLabel.setBounds(ins.left, y,
					titleLabel.getPreferredSize().width, fontHeight);
			y += titleLabel.getHeight() + gap;

			// The main image
			int x = ins.left;
			if (tooltipInfo.getMainImage() != null) {
				mainImageLabel = new JLabel(new ImageIcon(tooltipInfo
						.getMainImage()));
				richTooltipPanel.add(mainImageLabel);
				mainImageLabel.setBounds(x, y, mainImageLabel
						.getPreferredSize().width, mainImageLabel
						.getPreferredSize().height);
				x += mainImageLabel.getWidth();
			}
			x += 2 * gap;

			// The description text
			int descLabelWidth = parent.getWidth() - x - ins.right;
			for (String descText : tooltipInfo.getDescriptionSections()) {
				AttributedString attributedDescription = new AttributedString(
						descText);
				attributedDescription.addAttribute(TextAttribute.FONT, font);
				LineBreakMeasurer lineBreakMeasurer = new LineBreakMeasurer(
						attributedDescription.getIterator(), frc);
				int currOffset = 0;
				while (true) {
					TextLayout tl = lineBreakMeasurer
							.nextLayout(descLabelWidth);
					if (tl == null)
						break;
					int charCount = tl.getCharacterCount();
					String line = descText.substring(currOffset, currOffset
							+ charCount);

					JLabel descLabel = new JLabel(line);
					descriptionLabels.add(descLabel);
					richTooltipPanel.add(descLabel);
					descLabel.setBounds(x, y,
							descLabel.getPreferredSize().width, fontHeight);
					y += descLabel.getHeight();

					currOffset += charCount;
				}
				// add an empty line after the paragraph
				y += titleLabel.getHeight();
			}
			// remove the empty line after the last paragraph
			y -= titleLabel.getHeight();

			if (mainImageLabel != null) {
				y = Math.max(y, mainImageLabel.getY()
						+ mainImageLabel.getHeight());
			}

			if ((tooltipInfo.getFooterImage() != null)
					|| (tooltipInfo.getFooterSections().size() > 0)) {
				y += gap;
				// The footer separator
				footerSeparator = new JSeparator(JSeparator.HORIZONTAL);
				richTooltipPanel.add(footerSeparator);
				footerSeparator.setBounds(ins.left, y, parent.getWidth()
						- ins.left - ins.right, footerSeparator
						.getPreferredSize().height);

				y += footerSeparator.getHeight() + gap;

				// The footer image
				x = ins.left;
				if (tooltipInfo.getFooterImage() != null) {
					footerImageLabel = new JLabel(new ImageIcon(tooltipInfo
							.getFooterImage()));
					richTooltipPanel.add(footerImageLabel);
					footerImageLabel.setBounds(x, y, footerImageLabel
							.getPreferredSize().width, footerImageLabel
							.getPreferredSize().height);
					x += footerImageLabel.getWidth() + 2 * gap;
				}

				// The footer text
				int footerLabelWidth = parent.getWidth() - x - ins.right;
				for (String footerText : tooltipInfo.getFooterSections()) {
					AttributedString attributedDescription = new AttributedString(
							footerText);
					attributedDescription
							.addAttribute(TextAttribute.FONT, font);
					LineBreakMeasurer lineBreakMeasurer = new LineBreakMeasurer(
							attributedDescription.getIterator(), frc);
					int currOffset = 0;
					while (true) {
						TextLayout tl = lineBreakMeasurer
								.nextLayout(footerLabelWidth);
						if (tl == null)
							break;
						int charCount = tl.getCharacterCount();
						String line = footerText.substring(currOffset,
								currOffset + charCount);

						JLabel footerLabel = new JLabel(line);
						footerLabels.add(footerLabel);
						richTooltipPanel.add(footerLabel);
						footerLabel.setBounds(x, y, footerLabel
								.getPreferredSize().width, fontHeight);
						y += footerLabel.getHeight();

						currOffset += charCount;
					}
					// add an empty line after the paragraph
					y += titleLabel.getHeight();
				}
				// remove the empty line after the last paragraph
				y -= titleLabel.getHeight();
			}
		}
	}

	protected int getDescriptionTextWidth() {
		return 200;
	}

	protected int getLayoutGap() {
		return 4;
	}

	protected void removeExistingComponents() {
		if (this.titleLabel != null) {
			this.richTooltipPanel.remove(this.titleLabel);
		}
		if (this.mainImageLabel != null) {
			this.richTooltipPanel.remove(this.mainImageLabel);
		}
		for (JLabel label : this.descriptionLabels)
			this.richTooltipPanel.remove(label);
		if (this.footerSeparator != null) {
			this.richTooltipPanel.remove(this.footerSeparator);
		}
		if (this.footerImageLabel != null) {
			this.richTooltipPanel.remove(this.footerImageLabel);
		}
		for (JLabel label : this.footerLabels)
			this.richTooltipPanel.remove(label);
	}
}
