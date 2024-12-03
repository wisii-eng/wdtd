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
 * @WisedocHardWareInfoGetDialog.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.security;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent.EventType;

import com.wisii.wisedoc.resource.MessageResource;
import com.wisii.wisedoc.view.action.LoadRegistrationDocumentAction;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;

/**
 * 类功能描述：用于创建客户端注册信息文件，加载厂家提供注册文件。
 * 
 * 作者：李晓光 
 * 创建日期：2009-11-12
 */
public class WisedocHardWareInfoGetDialog extends AbstractWisedocDialog {
	private static final long serialVersionUID = 8711127699632160786L;
	
	/* dos命令，用资源管理器打开文件 Explorer [/n][/e][[,/root],[path]][[,/select],[path filename]]   */
	private final static String CMD = "cmd /c explorer /select,";
	
	private JTextField txtPath = new JTextField(25);
	private final static String title = MessageResource.getMessage("wisedoc.hardware.dialg.title");//注册
	private final static String select = MessageResource.getMessage("wisedoc.hardware.dialg.select");//选择
	private final static String load = MessageResource.getMessage("wisedoc.hardware.dialg.load");//加载
	private final static String quit = MessageResource.getMessage("wisedoc.hardware.dialg.quit");//退出
	private final static String lab1 = MessageResource.getMessage("wisedoc.hardware.dialg.lab1");//1.生成注册码文件：
	private final static String lab2 = MessageResource.getMessage("wisedoc.hardware.dialg.lab2");//<html>2.发送注册码文件至北京汇智互联软件技术有限公司.<br>联系方式：</html>
	private final static String lab3 = MessageResource.getMessage("wisedoc.hardware.dialg.lab3");//3.加载许可文件：
	private final static String companyName = MessageResource.getMessage("wisedoc.hardware.dialg.company");//北京汇智互联软件技术有限公司.
	private final static String homepage = MessageResource.getMessage("wisedoc.hardware.dialg.homepage");//主页: 
	private final static String homepageAddress = MessageResource.getMessage("wisedoc.hardware.dialg.homepage.address");//http://www.wisii.com
	private final static String mail = MessageResource.getMessage("wisedoc.hardware.dialg.mail");//邮箱:
	private final static String mailAddress = MessageResource.getMessage("wisedoc.hardware.dialg.mail.address");//office@wisii.com
	private final static String mailSubject = MessageResource.getMessage("wisedoc.hardware.dialg.mail.subject");//注册码文件
	private final static String telephone = MessageResource.getMessage("wisedoc.hardware.dialg.telephone");//电话:86-010-62612185
	private final static String fax = MessageResource.getMessage("wisedoc.hardware.dialg.fax");//传真:86-010-62612185-8001
	private final static String copyright = MessageResource.getMessage("wisedoc.hardware.dialg.copyright");//Copyright：Beijing Wise Internet Software Co., Ltd.
	private final static String notopen = MessageResource.getMessage("wisedoc.hardware.dialg.notopen");//无法打开：
	private final static String createfail = MessageResource.getMessage("wisedoc.hardware.dialg.createfail");//创建失败！
	private JButton btnCreate = new JButton(select);
	private JButton btnLoad = new JButton(load);
	private JButton btnClose = new JButton(quit);
	private JEditorPane area = new JEditorPane();
	
	private String body = "";
	public WisedocHardWareInfoGetDialog(){
		super();
		
		initDialog();
	}
	@SuppressWarnings("serial")
	private void initDialog(){
		final JRootPane root = getRootPane();
		ActionMap am = root.getActionMap();
		am.put("ESCAPE", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		setTitle(title);
				
		setSize(350, 290);
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		JPanel temp = new JPanel(new FlowLayout(FlowLayout.LEADING));
		temp.add(new JLabel(lab1));
		temp.add(btnCreate);
		p.add(temp);
		
		area.setEditable(Boolean.FALSE);
		area.setContentType("text/html");
		area.setText(getHtml());
		temp = new JPanel(new BorderLayout(5, 5));
		temp.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		temp.add(new JLabel(lab2), BorderLayout.NORTH);
		temp.add(area, BorderLayout.CENTER);
		p.add(temp);
		temp = new JPanel(new FlowLayout(FlowLayout.LEADING));
		temp.add(new JLabel(lab3));
		temp.add(btnLoad);
		p.add(temp);
		temp = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		temp.add(btnClose);
		p.add(temp);
		setContentPane(p);
		
		pack();
		setResizable(Boolean.FALSE);
		
		initActions();
	}
	private String getHtml(){
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<body>");
		sb.append(companyName);
		sb.append("<br>");
		sb.append(copyright);
		sb.append("<br>");
		sb.append(homepage);
		sb.append("<a href=");
		sb.append(homepageAddress);
		sb.append(">");
		sb.append(homepageAddress);
		sb.append("</a>");
		sb.append("<br>");
		sb.append(mail);
		sb.append("<a href=mailto:");
		sb.append(mailAddress);
		sb.append(">");
		sb.append(mailAddress);
		sb.append("</a>");
		sb.append("<br>");
		sb.append(telephone);
		sb.append("<br>");
		sb.append(fax);
		sb.append("<br>");
		sb.append("</body>");
		sb.append("</html>");
		
		return sb.toString();
	}

	private void initActions(){
		btnCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
				int result = chooser.showSaveDialog(WisedocHardWareInfoGetDialog.this);
				
				if (result != JFileChooser.APPROVE_OPTION)
					return;
				
				File file = chooser.getSelectedFile();
				String path = file.getAbsolutePath();
				boolean issavesucessfull = HardwareInfoSave.save(path,
						HardWareInfoGetter.getDiskID(), HardWareInfoGetter
								.getMac(), HardWareInfoGetter.getProcessorid());
				
				if (issavesucessfull) {
					/*JOptionPane.showMessageDialog(WisedocHardWareInfoGetDialog.this, "产生成功！" + "请将生成的注册信息文件发送至北京汇智互联软件技术有限公司，获取注册文件！");*/
					/*close();*/
					jumpToPaht(file);
				} else {
					JOptionPane.showMessageDialog(WisedocHardWareInfoGetDialog.this, createfail);
					updatePath(path);
				}
			}
		});
		btnLoad.addActionListener(new LoadRegistrationDocumentAction());
		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		area.addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				HyperlinkEvent.EventType type = e.getEventType();
				if(type != EventType.ACTIVATED)
					return;
				
				try {
					String cmd = "cmd /c start ";
				    String url = e.getDescription();
				    if(url.startsWith("mailto")){				    
				    	url = getMailUrl(url);
				    }else{
				    }
				    Runtime.getRuntime().exec(cmd + url);
				    //仅支持1.6以上平台
				    /*try {
						Desktop.getDesktop().mail(new URI(url));
					} catch (URISyntaxException e1) {
					}*/
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(WisedocHardWareInfoGetDialog.this, notopen + e.getDescription());
				}
			}
		});
	}
	private String getMailUrl(String mail){
		StringBuilder sb = new StringBuilder(mail);
		sb.append("?");
		/*sb.append("cc=");
		sb.append("lxg.job@gamil.com");
		sb.append("&subject=‘mailto'");*/
		sb.append("subject=");
		sb.append(mailSubject);
		sb.append('&');
		sb.append("body=");
		sb.append(body);
				
		return sb.toString();
	}
	private void close(){
		dispose();
	}
	private void updatePath(String path){
		txtPath.setText(path);
	}	
	private void jumpToPaht(File file) {
		Runtime run = Runtime.getRuntime();
		try {
			/* 采用start方式打开文件夹 */
			/*String[] commands = {"cmd", "/c", "start", "\"Wisedoc2.01\"", path};*/
			String cmd = CMD + file.getAbsolutePath();
			run.exec(cmd);
		} catch (IOException e) {
			
		}
	}
	public static void main(String[] args) {
		WisedocHardWareInfoGetDialog dialog = new WisedocHardWareInfoGetDialog();
		dialog.showDialog();
	}
}
