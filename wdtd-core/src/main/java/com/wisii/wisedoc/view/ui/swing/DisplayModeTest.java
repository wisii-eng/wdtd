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

/*
 * Copyright (c) 1995 - 2008 Sun Microsystems, Inc.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Sun Microsystems nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
  test @(#)DisplayModeTest.java	1.4 01/07/17
  @bug 4189326
  @summary Tests changing display mode
  @author martak@eng: area=FullScreen
  @ignore This test enters full-screen mode, if available, and should not
  be run as an applet or as part of the test harness.
*/

/**
 * This test generates a table of all available display modes, enters
 * full-screen mode, if available, and allows you to change the display mode.
 * The application should look fine under each enumerated display mode.
 * On UNIX, only a single display mode should be available, and on Microsoft
 * Windows, display modes should depend on direct draw availability and the
 * type of graphics card.
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

class DisplayModeModel extends DefaultTableModel {
    private DisplayMode[] modes;

    public DisplayModeModel(final DisplayMode[] modes) {
        this.modes = modes;
    }

    public DisplayMode getDisplayMode(final int r) {
        return modes[r];
    }

    @Override
	public String getColumnName(final int c) {
        return DisplayModeTest.COLUMN_NAMES[c];
    }

    @Override
	public int getColumnCount() {
        return DisplayModeTest.COLUMN_WIDTHS.length;
    }

    @Override
	public boolean isCellEditable(final int r, final int c) {
        return false;
    }

    @Override
	public int getRowCount() {
        if (modes == null) {
            return 0;
        }
        return modes.length;
    }

    @Override
	public Object getValueAt(final int rowIndex, final int colIndex) {
        final DisplayMode dm = modes[rowIndex];
        switch (colIndex) {
            case DisplayModeTest.INDEX_WIDTH :
                return Integer.toString(dm.getWidth());
            case DisplayModeTest.INDEX_HEIGHT :
                return Integer.toString(dm.getHeight());
            case DisplayModeTest.INDEX_BITDEPTH : {
                final int bitDepth = dm.getBitDepth();
                String ret;
                if (bitDepth == DisplayMode.BIT_DEPTH_MULTI) {
                    ret = "Multi";
                } else {
                    ret = Integer.toString(bitDepth);
                }
                return ret;
            }
            case DisplayModeTest.INDEX_REFRESHRATE : {
                final int refreshRate = dm.getRefreshRate();
                String ret;
                if (refreshRate == DisplayMode.REFRESH_RATE_UNKNOWN) {
                    ret = "Unknown";
                } else {
                    ret = Integer.toString(refreshRate);
                }
                return ret;
            }
        }
        throw new ArrayIndexOutOfBoundsException("Invalid column value");
    }

}

public class DisplayModeTest extends JFrame implements ActionListener,
    ListSelectionListener {

    private boolean waiting = false;
    private GraphicsDevice device;
    private DisplayMode originalDM;
    private JButton exit = new JButton("Exit");
    private JButton changeDM = new JButton("Set Display");
    private JLabel currentDM = new JLabel();
    private JTable dmList = new JTable();
    private JScrollPane dmPane = new JScrollPane(dmList);
    private boolean isFullScreen = false;

    public static final int INDEX_WIDTH = 0;
    public static final int INDEX_HEIGHT = 1;
    public static final int INDEX_BITDEPTH = 2;
    public static final int INDEX_REFRESHRATE = 3;

    public static final int[] COLUMN_WIDTHS = new int[] {
        100, 100, 100, 100
    };
    public static final String[] COLUMN_NAMES = new String[] {
        "Width", "Height", "Bit Depth", "Refresh Rate"
    };

    public DisplayModeTest(final GraphicsDevice device) {
        super(device.getDefaultConfiguration());
        this.device = device;
        setTitle("Display Mode Test");
        originalDM = device.getDisplayMode();
        setDMLabel(originalDM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Make sure a DM is always selected in the list
        exit.addActionListener(this);
        changeDM.addActionListener(this);
        changeDM.setEnabled(device.isDisplayChangeSupported());
    }

    public void actionPerformed(final ActionEvent ev) {
        final Object source = ev.getSource();
        if (source == exit) {
            device.setDisplayMode(originalDM);
            System.exit(0);
        } else { // if (source == changeDM)
            final int index = dmList.getSelectionModel().getAnchorSelectionIndex();
            if (index >= 0) {
                final DisplayModeModel model = (DisplayModeModel)dmList.getModel();
                final DisplayMode dm = model.getDisplayMode(index);
                device.setDisplayMode(dm);
                setDMLabel(dm);
                setSize(new Dimension(dm.getWidth(), dm.getHeight()));
                validate();
            }
        }
    }

    public void valueChanged(final ListSelectionEvent ev) {
        changeDM.setEnabled(device.isDisplayChangeSupported());
    }

    private void initComponents(final Container c) {
        setContentPane(c);
        c.setLayout(new BorderLayout());
        // Current DM
        final JPanel currentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        c.add(currentPanel, BorderLayout.NORTH);
        final JLabel current = new JLabel("Current Display Mode : ");
        currentPanel.add(current);
        currentPanel.add(currentDM);
        // Display Modes
        final JPanel modesPanel = new JPanel(new GridLayout(1, 2));
        c.add(modesPanel, BorderLayout.CENTER);
        // List of display modes
        for (int i = 0; i < COLUMN_WIDTHS.length; i++) {
            final TableColumn col = new TableColumn(i, COLUMN_WIDTHS[i]);
            col.setIdentifier(COLUMN_NAMES[i]);
            col.setHeaderValue(COLUMN_NAMES[i]);
            dmList.addColumn(col);
        }
        dmList.getSelectionModel().setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION);
        dmList.getSelectionModel().addListSelectionListener(this);
        modesPanel.add(dmPane);
        // Controls
        final JPanel controlsPanelA = new JPanel(new BorderLayout());
        modesPanel.add(controlsPanelA);
        final JPanel controlsPanelB = new JPanel(new GridLayout(2, 1));
        controlsPanelA.add(controlsPanelB, BorderLayout.NORTH);
        // Exit
        final JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        controlsPanelB.add(exitPanel);
        exitPanel.add(exit);
        // Change DM
        final JPanel changeDMPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        controlsPanelB.add(changeDMPanel);
        changeDMPanel.add(changeDM);
        controlsPanelA.add(new JPanel(), BorderLayout.CENTER);
    }

    @Override
	public void setVisible(final boolean isVis) {
        super.setVisible(isVis);
        if (isVis) {
            dmList.setModel(new DisplayModeModel(device.getDisplayModes()));
        }
    }

    public void setDMLabel(final DisplayMode newMode) {
        final int bitDepth = newMode.getBitDepth();
        final int refreshRate = newMode.getRefreshRate();
        String bd, rr;
        if (bitDepth == DisplayMode.BIT_DEPTH_MULTI) {
            bd = "Multi";
        } else {
            bd = Integer.toString(bitDepth);
        }
        if (refreshRate == DisplayMode.REFRESH_RATE_UNKNOWN) {
            rr = "Unknown";
        } else {
            rr = Integer.toString(refreshRate);
        }
        currentDM.setText(
            COLUMN_NAMES[INDEX_WIDTH] + ": " + newMode.getWidth() + " "
            + COLUMN_NAMES[INDEX_HEIGHT] + ": " + newMode.getHeight() + " "
            + COLUMN_NAMES[INDEX_BITDEPTH] + ": " + bd + " "
            + COLUMN_NAMES[INDEX_REFRESHRATE] + ": " + rr
            );
    }

    public void begin() {
        isFullScreen = device.isFullScreenSupported();
        setUndecorated(isFullScreen);
        setResizable(!isFullScreen);
        if (isFullScreen) {
            // Full-screen mode
            device.setFullScreenWindow(this);
            validate();
        } else {
            // Windowed mode
            pack();
            setVisible(true);
        }
    }

    public static void main(final String[] args) {
        final GraphicsEnvironment env = GraphicsEnvironment.
            getLocalGraphicsEnvironment();
        final GraphicsDevice[] devices = env.getScreenDevices();
        // REMIND : Multi-monitor full-screen mode not yet supported
        for (int i = 0; i < 1 /* devices.length */; i++) {
            final DisplayModeTest test = new DisplayModeTest(devices[i]);
            test.initComponents(test.getContentPane());
            test.begin();
        }
    }
}
