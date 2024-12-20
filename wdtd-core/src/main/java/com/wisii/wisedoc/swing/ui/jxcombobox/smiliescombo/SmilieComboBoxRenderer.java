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
 * SmilieComboBoxRenderer.java
 *
 * Created on 25 de Fevereiro de 2005, 18:24
 */

package com.wisii.wisedoc.swing.ui.jxcombobox.smiliescombo;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 *
 * @author Lopes
 */
public class SmilieComboBoxRenderer extends DefaultListCellRenderer {
    

    public Component getListCellRendererComponent(JList list, Object value, int index,
                                       boolean isSelected, boolean cellHasFocus) {
        
        Smilie smilie = (Smilie) value;
        return smilie.getCachedRenderer();
    }
    
}

