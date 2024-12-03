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
 * ColorComboBoxRenderer.java
 *
 * Created on 24 de Fevereiro de 2005, 22:23
 */

package com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;


/**
 *
 * @author  Ricardo Lopes
 *
 */
public class ColorComboBoxRenderer extends DefaultListCellRenderer {
    
    protected static final String RENDERER_IMAGE_FILE = "/com/wisii/wisedoc/swing/ui/jxcombobox/colorcombo/resources/color_fill.png";
    protected Renderer renderer;
    protected Image image;
    private ColorComboBox combox = null;
    public ColorComboBoxRenderer() {
    	this.combox = combox;
        renderer = new Renderer();
        
        URL url = ColorComboBoxRenderer.class.getResource(RENDERER_IMAGE_FILE);
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
        System.err.println("Error loading image :" + url);
        image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
    }
    
    public Component getListCellRendererComponent(JList list, Object value, int index,
                                       boolean isSelected, boolean cellHasFocus) {
                                           
        renderer.setColor((Color) value);
        return renderer;
    }
    
    
    class Renderer extends Component {
        
        public void setColor(Color color) {
        	repaint();
            this.color = color;
        }
        
        public Color getColor() {
            return color;
        }
        
        public void paint(Graphics g) {
            g.setColor((Color) color);
            g.fillRect(1, 1, 23, 5);//【修改】by 李晓光 2009-1-6 【纵坐标19->1】
            g.setColor(Color.darkGray);
            g.draw3DRect(1, 1, 23, 5, true);//【修改】by 李晓光 2009-1-6 【纵坐标19->1】
            g.drawImage(image, 4, 1, this);
        }
        
        private Color color;
    }
}

