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
/**
 * @SidePanel.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view;

import static com.wisii.wisedoc.util.WisedocUtil.isNull;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import com.wisii.wisedoc.resource.MediaResource;

/**
 * 类功能描述：用于放置功能控件的功能区面板
 * 
 * 作者：李晓光 创建日期：2008-8-22
 */
@SuppressWarnings("serial")
public class SidePanel extends JPanel {
	private JTabbedPane tabPane = new JTabbedPane();
	/* 当前面板采用的布局 */
	private GridLayout layout = new GridLayout();
	/* tab上显示是名称【资源包中的KEY】 */
	private String title = "";
	
	private List<SidePanel> list = new ArrayList<SidePanel>();
	
	private boolean isNewTab = false;
	
	public SidePanel(){
		this("");
	}
	
	public SidePanel(String title) {
		super(true);
		setTitle(title);
		setLayout(layout);
	}

	public Component add(Component comp){
		if(isNull(comp))
			return comp;
		if(!(comp instanceof SidePanel))
			return super.add(getScrComponent(comp));
		JTabbedPane pane = null;
		if(isNewTab){
			pane = new JTabbedPane();
			pane.add(((SidePanel)comp).getTitle(), comp);
			isNewTab = false;
		}else{
			pane = tabPane; 
			pane.add(((SidePanel)comp).getTitle(), comp);
		}
		return super.add(pane);
	}
	public void removeAll(){
		list.clear();
		super.removeAll();
	}
	public void addCenter(SidePanel comp){
		tabPane.add(comp.getTitle(), comp);
		list.add(comp);
		this.add(tabPane, BorderLayout.CENTER);
	}
	public void addNorth(SidePanel comp){		
		/*if(list.isEmpty())
			this.add(comp);
		else*/{
			setLayout(new GridLayout(2, 1));
			isNewTab = true;
			this.add(comp);
		}
	}
	public void addSouth(SidePanel comp){
		setLayout(new GridLayout(2, 1));
		isNewTab = true;
		this.add(comp);
	}
	public void addWest(SidePanel comp){
		setLayout(new GridLayout(1, 2));
		isNewTab = true;
		this.add(comp);
	}
	public void addEast(SidePanel comp){
		setLayout(new GridLayout(1, 2));
		isNewTab = true;
		this.add(comp);
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 
	 * 为指定的控件添加滚动控件
	 * 
	 * @param comp
	 *            指定控件
	 * @return {@link Component} 返回添加了滚动控件的控件
	 */
	private Component getScrComponent(Component comp) {
		if (!(comp instanceof JScrollPane))
			comp = new JScrollPane(comp);
		return comp;
	}
	public boolean isRoot(){
		return !(getParent() instanceof SidePanel);/*(isNull(getParent())) && */
	}
	/*public void paint(Graphics g){
		g.drawImage(MediaResource.getImageIcon("1.jpg").getImage(), 0, 0, this);
		super.paint(g);
	}*/
	public static void main(String[] args) {
		JFrame fr = new JFrame();
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			// TODO: handle exception
		}
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setSize(800, 600);		
		SidePanel main = new SidePanel();
		SidePanel first = new SidePanel("First");
		SidePanel second = new SidePanel("Second");
		JLabel lab = new JLabel(MediaResource.getImageIcon("1.jpg"));
		JLabel lab0 = new JLabel(MediaResource.getImageIcon("1.jpg"));
		JLabel lab1 = new JLabel(MediaResource.getImageIcon("1.jpg"));
//		first.add(new JButton("First", MediaResource.getImageIcon("1.gif")));
//		second.add(new JButton("Second", MediaResource.getImageIcon("1.gif")));
		first.add(lab);
		second.add(lab0);
		main.addCenter(first);
		main.addCenter(second);
		SidePanel north = new SidePanel("North");
		north.add(lab1);
		first.addCenter(north);
		fr.setContentPane(main);
		
		fr.setVisible(true);
	}
}
