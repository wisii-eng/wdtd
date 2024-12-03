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
package com.wisii.wisedoc.view.ui.swing;

import java.awt.BorderLayout;
import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JPanel;

public class TestXMLPanel {
	
	
	public JPanel createPanel() {
		final JPanel jp = new JPanel(new BorderLayout());
		
		jp.add(new JButton("test"), BorderLayout.CENTER);
		
		return jp;
	}
	
	
	public static void main(final String[] args) {
		
		final JPanel jp = new TestXMLPanel().createPanel();
		
		try {
			final XMLEncoder encoder = new XMLEncoder(new FileOutputStream("C:\\Users\\Karl\\Desktop\\xPanel.xml"));
			encoder.writeObject(jp);
			encoder.close();
		} catch (final FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
