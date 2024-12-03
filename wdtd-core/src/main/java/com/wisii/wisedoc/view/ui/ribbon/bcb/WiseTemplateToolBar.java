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
package com.wisii.wisedoc.view.ui.ribbon.bcb;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.ribbon.JRibbon;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;
import org.jvnet.flamingo.ribbon.RibbonTask;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.util.UiUtil;

/**
 * 针对模板选择操作面板的控制
 * @author 闫舒寰
 * @version 1.0 2009/04/02
 */
public class WiseTemplateToolBar implements ActionListener{
	
	JRibbon jRibbon;
	
    public enum Command {
    	//添加分类
    	addC,
    	//删除分类
    	delC,
    	//添加模板
    	addT,
    	//删除模板
    	delT,
    	//查找模板
    	Find;
    }
    
    private static JCommandButton addCatButton, delCatButton, addTemoplateButton, delTemoplateButton;
	
	public WiseTemplateToolBar(){
		
		jRibbon = new JRibbon();
        
        JRibbonBand jRibbonBand = new JRibbonBand("模板分类操作", MediaResource.getResizableIcon("09379.ico"));
        
        addCatButton = new JCommandButton("添加分类", MediaResource.getResizableIcon("03705.ico"));
        jRibbonBand.addCommandButton(addCatButton, RibbonElementPriority.MEDIUM);
        addCatButton.getActionModel().setActionCommand(Command.addC.name());
        addCatButton.addActionListener(this);
        
        delCatButton = new JCommandButton("删除分类", MediaResource.getResizableIcon("03705.ico"));
        jRibbonBand.addCommandButton(delCatButton, RibbonElementPriority.MEDIUM);
        delCatButton.getActionModel().setActionCommand(Command.delC.name());
        delCatButton.addActionListener(this);
        
        JRibbonBand templateFileBand = new JRibbonBand("模板操作", MediaResource.getResizableIcon("03705.ico"));
        
        /*addTemoplateButton = new JCommandButton("添加模板文件", MediaResource.getResizableIcon("03705.ico"));
        templateFileBand.addCommandButton(addTemoplateButton, RibbonElementPriority.MEDIUM);
        addTemoplateButton.getActionModel().setActionCommand(Command.addT.name());
        addTemoplateButton.addActionListener(this);*/
        
        delTemoplateButton = new JCommandButton("删除模板文件", MediaResource.getResizableIcon("03705.ico"));
        templateFileBand.addCommandButton(delTemoplateButton, RibbonElementPriority.MEDIUM);
        delTemoplateButton.getActionModel().setActionCommand(Command.delT.name());
        delTemoplateButton.addActionListener(this);
        
        /*JRibbonBand cleanBand = new JRibbonBand("查找模板", MediaResource.getResizableIcon("03705.ico"));
        JCommandButton findTemplate = new JCommandButton("查找模板", MediaResource.getResizableIcon("03705.ico"));
        cleanBand.addCommandButton(findTemplate, RibbonElementPriority.MEDIUM);
        findTemplate.getActionModel().setActionCommand(Command.Find.name());
        findTemplate.addActionListener(this);*/
        
        RibbonTask addTask = new RibbonTask("模板库管理", jRibbonBand, templateFileBand/*, cleanBand*/);
        
        jRibbon.addTask(addTask);
        
        //设置成自定义RibbonUI
        jRibbon.setUI(new TemplateRibbonUI());
	}
	
	public JComponent getToolBar(){
		return jRibbon;
	}
	
	private static int count;
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if (Command.addC.name().equals(command)) {
        	File cwd = WiseTemplateManager.Instance.getCurrentSelectedFolder();
            if(cwd != null) {
                File newDir = new File(cwd,"New Folder");
                //模仿Windows那中创建
                while(!newDir.mkdir()) {
                	newDir = new File(cwd, "New Folder " + ++count);
                }
//                WiseTemplateManager.Instance.setCurrentSelectedFolder(newDir);
                count = 0;
            }
		} else if (Command.delC.name().equals(command)) {
			File currentFile = WiseTemplateManager.Instance.getCurrentSelectedFolder();
			
			int o = JOptionPane.showConfirmDialog(WiseTemplateManager.Instance.getFileListPanel(), "确定要删掉模板分类: " + currentFile.getName() + "?");
			
			if (o == 0) {
				try {
					UiUtil.deleteFile(currentFile);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		} else if (Command.addT.name().equals(command)) {
			/*try {
				String path = WiseTemplateManager.Instance.getCurrentSelectedFolder().getPath();
				String pPath = WiseTemplateManager.Instance.getCurrentSelectedFolder().getParentFile().getPath();
				File pFile = WiseTemplateManager.Instance.getCurrentSelectedFolder().getParentFile();
				String[] com = {"rd "};
				String[] com1 = {"/s", "/q"};
				String[] cmd = {"rd", path, "/s/q"};
				String[] cmd1 = {"cmd", "/c", "dir"};
				
				Runtime.getRuntime().exec(cmd1);
			} catch (IOException e1) {
				e1.printStackTrace();
			}*/
			
		} else if (Command.delT.name().equals(command)) {
			
			File currentFile = WiseTemplateManager.Instance.getCurrentSelectedTemplate();
			int o = JOptionPane.showConfirmDialog(WiseTemplateManager.Instance.getFileListPanel(), "确定要删除模板: " + currentFile.getName() + "?");
			
			if (o == 0) {
				try {
					UiUtil.deleteFile(currentFile);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		} else if (Command.Find.name().equals(command)) {
			/*System.out.println("delete");
			File file = WiseTemplateManager.Instance.getCurrentSelectedFolder();
			System.out.println("file: " + file);
			
	        if(file != null) {
	            file.delete();
	        }*/
		}
        
//        WiseTemplateManager.Instance.updataPanels();
        WiseTemplateManager.Instance.updateFileTreePanel();
    }
    
    public static void updateUIState() {
    	
    	SwingUtilities.invokeLater(new Runnable() {
    		@Override
    		public void run() {
    			File currentFolder = WiseTemplateManager.Instance.getCurrentSelectedFolder();
    	    	File currentFile = WiseTemplateManager.Instance.getCurrentSelectedTemplate();
    	    	if (currentFolder == null) {
    	    		addCatButton.setEnabled(false);
    	    		delCatButton.setEnabled(false);
//    	    		addTemoplateButton.setEnabled(false);
    			} else {
    				addCatButton.setEnabled(true);
    	    		delCatButton.setEnabled(true);
//    	    		addTemoplateButton.setEnabled(true);
    			}
    	    	
    	    	if (currentFile == null) {
    	    		delTemoplateButton.setEnabled(false);
    			} else {
    				delTemoplateButton.setEnabled(true);
    			}
    		}
    	});
    }
}
