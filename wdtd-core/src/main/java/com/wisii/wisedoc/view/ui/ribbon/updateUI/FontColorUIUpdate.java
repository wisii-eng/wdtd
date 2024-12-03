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
package com.wisii.wisedoc.view.ui.ribbon.updateUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import org.jvnet.flamingo.common.AbstractCommandButton;

public enum FontColorUIUpdate {

	HeighLighter, FontColor;

	public Color color;
	private AbstractCommandButton button;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		updateButton();
	}

	public AbstractCommandButton getButton() {
		return button;
	}

	public void setButton(AbstractCommandButton button) {
		this.button = button;
	}

	private void updateButton() {
		// button.setIcon(defaultIcon);

		// Image
		/*Icon icon = button.getIcon();
		System.out.println(icon.getClass());
		System.out.println("icon height: " + icon.getIconHeight() + " width: "
				+ icon.getIconWidth());*/

		
		/*renderer = new Renderer();
        
        URL url = ColorComboBoxRenderer.class.getResource(MediaResource.getImageURL("00340.ico").toString());
        if (url != null) {
            try {
                image = ImageIO.read(url);
                if (image != null) {
                    return;
                }
            } catch (IOException ioe) { 
                System.err.println(ioe.getMessage());
            }
        }    
//        System.err.println("Error loading image :" + url);
        image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
		button.setIcon(ImageWrapperResizableIcon.getIcon(image, new Dimension(16,16)));*/
		
		/*button.setDisabledIcon(MediaResource.getResizableIcon("00417.ico"));
		System.out.println(button.getIcon().getIconWidth());*/
//		Icon icon = button.getIcon();
//		System.out.println("icon height: " + icon.getIconHeight() + " width: "	+ icon.getIconWidth());
//		button.setIcon(MediaResource.getResizableIcon("09379.ico"));
	}
	
	protected Renderer renderer;
	protected Image image;

	class Renderer extends Component {

		public void setColor(Color color) {
			this.color = color;
		}

		public Color getColor() {
			return color;
		}

		public void paint(Graphics g) {
			g.setColor((Color) color);
			g.fillRect(1, 19, 23, 5);
			g.setColor(Color.darkGray);
			g.draw3DRect(1, 19, 23, 5, true);
			g.drawImage(image, 4, 1, this);
		}

		private Color color = FontColorUIUpdate.this.color;
	}

}
