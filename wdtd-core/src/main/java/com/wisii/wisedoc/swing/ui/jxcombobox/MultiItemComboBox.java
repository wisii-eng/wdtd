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
 * MultiItemComboBox.java
 *
 * Created on 4 de Maro de 2005, 23:02
 */

package com.wisii.wisedoc.swing.ui.jxcombobox;

import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JPanel;

/**
 *
 * @author Ricardo Lopes
 */
/**
 * @todo move key movement support to this class instead of having almost the same code on the descendant classes
 *
 */
public abstract class MultiItemComboBox extends JComponentComboBox {
    
    protected static final int DEFAULT_COLUMN_COUNT = 5;
    protected static final int DEFAULT_ROW_COUNT = 5;

    
    protected boolean selectOnMouseOver;
    protected boolean selectOnKeyPress;
    protected int popupColumnCount;
    protected int popupRowCount;
    protected JPanel popupPanel;    

    
    /** Creates a new instance of MultiItemComboBox */
    public MultiItemComboBox() throws IncompatibleLookAndFeelException {
        super();
        
        selectOnMouseOver = false;
        selectOnKeyPress = true;
        popupColumnCount = DEFAULT_COLUMN_COUNT;
        popupRowCount = DEFAULT_ROW_COUNT;
                
        popupPanel = new JPanel();
        popupPanel.setLayout(new GridLayout(popupRowCount, popupColumnCount));
        
        setPopupComponent(popupPanel);        
    }
    
    
    public boolean isSelectOnMouseOver() {
        return selectOnMouseOver;
    }
    
    public void setSelectOnMouseOver(boolean newValue) {
        selectOnMouseOver = newValue;
    }    
    
    public boolean isSelectOnKeyPress() {
        return selectOnKeyPress;
    }
    
    public void setSelectOnKeyPress(boolean newValue) {
        selectOnKeyPress = newValue;
    }
    
    public int getPopupColumnCount() {
        return popupColumnCount;
    }
    
    public void setPopupColumnCount(int newValue) {
        popupColumnCount = newValue;
        popupPanel.setLayout(new GridLayout(popupRowCount, popupColumnCount));
        // @todo check if we have to call invalidate 
    }
    
    public int getPopupRowCount() {
        return popupRowCount;
    }
    
    public void setPopupRowCount(int newValue) {
        popupRowCount = newValue;
        popupPanel.setLayout(new GridLayout(popupRowCount, popupColumnCount));
        // @todo check if we have to call invalidate 
    }
    
    
    
    
    protected Point convertIndexToXY(int index) {
        // GridLayout orientation is left-to-right
        int x = index % popupColumnCount;
        int y = index / popupColumnCount;
        return new Point(x, y);
    }
    
    protected int convertXYToIndex(Point p) {
        // GridLayout orientation is left-to-right
        return p.x + p.y * popupColumnCount;
    }
    
}

