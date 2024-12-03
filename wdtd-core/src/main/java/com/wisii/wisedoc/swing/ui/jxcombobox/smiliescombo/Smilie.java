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
 * Created on 28. Februar 2005, 18:33
 */

package com.wisii.wisedoc.swing.ui.jxcombobox.smiliescombo;

import javax.swing.JComponent;

/**
 *
 * @author Thomas
 */
public class Smilie extends JComponent {
    
    protected String text;
    protected String filename;
    protected JComponent renderer;
    
    
    public Smilie(String lFilename, String lText) {
        this.text = lText;
        this.filename = lFilename;
        renderer = getNewRenderer();
    }
    
    public JComponent getCachedRenderer() {
        return renderer;
    }
    
    public JComponent getNewRenderer() {
        return new SmilieRenderer(filename, this);
    }
    
    public String toString() {
        return text;
    }
    
    public String getFilename() {
        return filename;
    }    
}

