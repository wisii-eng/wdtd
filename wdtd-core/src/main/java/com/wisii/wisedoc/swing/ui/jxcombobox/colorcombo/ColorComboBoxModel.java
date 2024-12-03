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
 * ColorComboBoxModel.java
 *
 * Created on 24 de Fevereiro de 2005, 22:52
 */

package com.wisii.wisedoc.swing.ui.jxcombobox.colorcombo;

import java.awt.Color;

import javax.swing.DefaultComboBoxModel;


/**
 *
 * @author Lopes
 */
public class ColorComboBoxModel extends DefaultComboBoxModel {
    
    public ColorComboBoxModel() {
        super(DEFAULT_COLORS);
    }

    protected static Color[] DEFAULT_COLORS =
        new Color[] { 
            new Color(0, 0, 0),
            new Color(153, 51, 0),
            new Color(51, 51, 0),
            new Color(0, 51, 0),
            new Color(0, 51, 102),            
            new Color(0, 0, 128),
            new Color(51, 51, 153),            
            new Color(51, 51, 51),
            
            new Color(128, 0, 0),
            new Color(255, 102, 0),
            new Color(128, 128, 0),
            new Color(0, 128, 0),
            new Color(0, 128, 128),
            new Color(0, 0, 255),
            new Color(102, 102, 153),
            new Color(128, 128, 128),
            
            new Color(255, 0, 0),  
            new Color(255, 153, 0),
            new Color(153, 204, 0),
            new Color(51, 153, 102),
            new Color(51, 204, 204),
            new Color(51, 102, 255),
            new Color(128, 0, 128),
            new Color(150, 150, 150),
            
            new Color(255, 0, 255),
            new Color(255, 204, 0),            
            new Color(255, 255, 0),
            new Color(0, 255, 0),
            new Color(0, 255, 255),
            new Color(0, 204, 255),
            new Color(153, 51, 102),
            new Color(192, 192, 192),
            
            new Color(255, 153, 204),
            new Color(255, 204, 153),            
            new Color(255, 255, 153),
            new Color(204, 255, 204),
            new Color(204, 255, 255),
            new Color(153, 204, 255),
            new Color(204, 153, 255),
            new Color(255, 255, 255)
        };            
}

