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
 * Smilie.java
 *
 * Created on 25 de Fevereiro de 2005, 17:52
 */

package com.wisii.wisedoc.swing.ui.jxcombobox.smiliescombo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 *
 * @author Lopes
 */
public class SmilieRenderer extends JComponent {    
    
    Smilie parent;
    
    public SmilieRenderer(String lFilename, Smilie lParent) {
        super();
        this.parent = lParent;
        URL url = SmilieRenderer.class.getResource(lFilename);
        
        if (url != null) {
            try {
                image = ImageIO.read(url);
                if (image != null) {
                    setPreferredSize(new Dimension(image.getWidth() + BORDER_SIZE * 2, image.getHeight() + BORDER_SIZE * 2));
                }
            } catch (IOException ioe) {
                System.err.println(ioe.getMessage());
            }
        } else {
            System.err.println("Error loading image :" + url);
        }
    }
    
    public BufferedImage getImage() {
        return image;
    }
    
    public void setOver(boolean isOver) {
        over = isOver;
        repaint();
    }
    
    public boolean isOver() {
        return over;
    }
    
    public void paintComponent(Graphics g) {
        g.drawImage(image, BORDER_SIZE, BORDER_SIZE, this); 

        if (over) {
            int width = getWidth();
            int height = getHeight();            
            g.setColor(OVER_COLOR);
            g.fillRect(0, 0, width - 1, height - 1);
            g.setColor(OVER_BORDER_COLOR);
            g.drawRect(0, 0, width - 1, height -1);
        }        
    }

    protected static final Color OVER_COLOR = new Color(182, 189, 210, 100);
    protected static final Color OVER_BORDER_COLOR = new Color(10, 30, 106);
    
    protected BufferedImage image;
    protected boolean over;    
    
    protected static final int BORDER_SIZE = 2;     

}
