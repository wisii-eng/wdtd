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
package com.wisii.wisedoc.swing.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 * 这个是最简单的下拉菜单样式，可以作为一个例子，为了以后的重写、
 * 
 * @author	??
 *
 */
public class JComColor extends JPanel implements ListCellRenderer {
	int rank = 0;

	public JComColor() {
		setPreferredSize(new Dimension(32, 14));
	}

	public void paint(Graphics g) {
		Color myCol;
		boolean ds = false;
		int w = getWidth();
		int h = getHeight();
		switch (rank) {
		case 0:
			myCol = Color.YELLOW;
			break;
		case 1:
			myCol = Color.ORANGE;
			break;
		case 2:
			myCol = new Color(200, 0, 200);
			break;
		case 3:
			myCol = Color.BLUE;
			break;
		case 4:
			myCol = Color.BLUE;
			break;
		case 5:
			myCol = Color.GREEN;
			break;
		case 6:
			myCol = Color.GREEN;
			break;
		case 7:
			myCol = new Color(160, 80, 0);
			break;
		case 8:
			myCol = new Color(160, 80, 0);
			break;
		case 9:
			myCol = new Color(160, 80, 0);
			break;
		case 10:
			myCol = Color.BLACK;
			break;
		default:
			myCol = Color.RED;
		}
		g.setColor(myCol);
		g.fillRect(0, 0, w, h);
		if (rank == 4) {
			g.setColor(Color.green);
			ds = true;
		}
		if (rank == 6) {
			g.setColor(new Color(160, 80, 0));
			ds = true;
		}
		if (ds)
			g.fillRect(0, (int) (h * .3), w, (int) (h * .45));
		if (rank >= 7 && rank < 10) {
			g.setColor(Color.black);
			g.fillRect((int) (w * .14), 0, (int) (w * .14), h);
		}
		if (rank >= 8 && rank < 10)
			g.fillRect((int) (w * .42), 0, (int) (w * .14), h);
		if (rank == 9)
			g.fillRect((int) (w * .70), 0, (int) (w * .14), h);
		paintBorder(g);
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		rank = ((Number) value).intValue();
		if (index < 0)
			setBorder(null);
		else
			setBorder(BorderFactory.createLineBorder(isSelected ? Color.WHITE
					: Color.LIGHT_GRAY, 2));
		return this;
	}

	public static void main(String[] argv) {
		Integer[] a = new Integer[12];
		for (int j = 0; j < a.length; j += 1)
			a[j] = new Integer(j);
		JComboBox cb = new JComboBox(a);
		cb.setRenderer(new JComColor());
		cb.setMaximumRowCount(12);
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setLayout(new FlowLayout());
		f.getContentPane().add(cb);
		f.setSize(80, 240);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}