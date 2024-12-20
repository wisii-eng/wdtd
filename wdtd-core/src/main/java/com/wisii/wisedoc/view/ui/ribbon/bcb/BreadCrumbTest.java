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
package com.wisii.wisedoc.view.ui.ribbon.bcb;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import org.jvnet.flamingo.bcb.BreadcrumbItem;
import org.jvnet.flamingo.bcb.BreadcrumbPathEvent;
import org.jvnet.flamingo.bcb.BreadcrumbPathListener;
import org.jvnet.flamingo.bcb.core.BreadcrumbFileSelector;
import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.common.StringValuePair;

public class BreadCrumbTest extends JFrame {
	protected BreadcrumbFileSelector bar;

	protected ExplorerFileViewPanel<File> filePanel;

	protected JScrollPane fileListScrollPane;

	public BreadCrumbTest() {
		super("BreadCrumb test");

		this.bar = new BreadcrumbFileSelector();
		this.bar.getModel().addPathListener(new BreadcrumbPathListener() {
			@Override
			public void breadcrumbPathEvent(BreadcrumbPathEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						final List<BreadcrumbItem<File>> newPath = bar
								.getModel().getItems();
						System.out.println("New path is ");
						for (BreadcrumbItem<File> item : newPath) {
							System.out.println("\t"
									+ item.getData().getAbsolutePath());
						}

						if (newPath.size() > 0) {
							SwingWorker<List<StringValuePair<File>>, Void> worker = new SwingWorker<List<StringValuePair<File>>, Void>() {
								@Override
								protected List<StringValuePair<File>> doInBackground()
										throws Exception {
									return bar.getCallback().getLeafs(newPath);
								}

								@Override
								protected void done() {
									try {
										List<StringValuePair<File>> leafs = get();
										filePanel.setFolder(leafs);
									} catch (Exception exc) {
									}
								}
							};
							worker.execute();
						}
						return;
					}
				});
			}
		});

		this.setLayout(new BorderLayout());
		this.add(bar, BorderLayout.NORTH);

		this.filePanel = new ExplorerFileViewPanel<File>(bar,
				CommandButtonDisplayState.MEDIUM, null);
		this.filePanel.setUseNativeIcons(true);
		fileListScrollPane = new JScrollPane(this.filePanel);
		this.add(fileListScrollPane, BorderLayout.CENTER);

		JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton setPath = new JButton("Select and set path...");
		setPath.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JFileChooser folderChooser = new JFileChooser();
						folderChooser
								.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						if (folderChooser.showOpenDialog(BreadCrumbTest.this) == JFileChooser.APPROVE_OPTION) {
							File selected = folderChooser.getSelectedFile();
							bar.setPath(selected);
						}
					}
				});
			}
		});
		controls.add(setPath);
		this.add(controls, BorderLayout.SOUTH);
	}

	/**
	 * Main method for testing.
	 * 
	 * @param args
	 *            Ignored.
	 */
	public static void main(String... args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				BreadCrumbTest test = new BreadCrumbTest();
				test.setSize(550, 385);
				test.setLocationRelativeTo(null);
				test.setVisible(true);
				test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
	}
}
