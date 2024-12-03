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

package com.wisii.wisedoc.view.ui.ribbon;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import org.jvnet.flamingo.ribbon.JRibbon;
import org.jvnet.flamingo.ribbon.RibbonApplicationMenu;
import org.jvnet.flamingo.ribbon.RibbonContextualTaskGroup;
import org.jvnet.flamingo.ribbon.RibbonTask;

import com.wisii.security.Function;
import com.wisii.wisedoc.view.ui.ribbon.basicband.BasicTask;
import com.wisii.wisedoc.view.ui.ribbon.button.ButtonTask;
import com.wisii.wisedoc.view.ui.ribbon.condition.ConditionTask;
import com.wisii.wisedoc.view.ui.ribbon.datatreat.DataTreatTask;
import com.wisii.wisedoc.view.ui.ribbon.dynamicstyle.DynamicStyleTask;
import com.wisii.wisedoc.view.ui.ribbon.group.GroupPropertyTask;
import com.wisii.wisedoc.view.ui.ribbon.insertband.InsertTask;
import com.wisii.wisedoc.view.ui.ribbon.pagesequence.PageSequenceTask;
import com.wisii.wisedoc.view.ui.ribbon.pagetask.PageTask;
import com.wisii.wisedoc.view.ui.ribbon.qianzhang.QianZhangTask;
import com.wisii.wisedoc.view.ui.ribbon.wisedoc.WiseDocTask;

/**
 * 所有菜单的创建应该在这里类中进行，以保证所有菜单只创建一次
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/20
 */
public enum RibbonPanelManager
{

	Instance;

	// 一直显示的面板
	private List<WiseTask> wiseShowTask;

	// 固定显示的面板
	List<RibbonTask> ribbonTask = new ArrayList<RibbonTask>();

	// 根据内容而改变的面板
	List<RibbonContextualTaskGroup> wiseTaskGroup;

	// Ribbon栏最上面的按钮
	List<Component> taskbarComponent;

	// 菜单弹出按钮
	public RibbonApplicationMenu ram;

	private static List<Class<? extends WiseTask>> taskClass = new ArrayList<Class<? extends WiseTask>>();

	static
	{
		taskClass.add(BasicTask.class);
		taskClass.add(InsertTask.class);
		taskClass.add(PageTask.class);
		taskClass.add(GroupPropertyTask.class);
		taskClass.add(ConditionTask.class);
		taskClass.add(DynamicStyleTask.class);
		taskClass.add(PageSequenceTask.class);
		taskClass.add(WiseDocTask.class);
		taskClass.add(DataTreatTask.class);
		taskClass.add(ButtonTask.class);
		taskClass.add(QianZhangTask.class);
	}

	public void initialTask(List<Function> funclist)
	{

		if (wiseShowTask == null)
		{
			wiseShowTask = new ArrayList<WiseTask>();
			for (Class<? extends WiseTask> tc : taskClass)
			{
				try
				{
					wiseShowTask.add(tc.newInstance());
				} catch (InstantiationException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		for (WiseTask wt : wiseShowTask)
		{
			// System.out.println(wt.getClass());
			ribbonTask.add(wt.getTask());
			// RibbonPanel.getRibbon().addTask(wt.getTask());
		}

		for (RibbonTask task : ribbonTask)
		{
			RibbonPanel.getRibbon().addTask(task);
		}

		ram = new ApplicationMenu().getApplicationMenu(funclist);
		RibbonPanel.getRibbon().setApplicationMenu(ram);

		// RibbonPanel.getRibbon().getTask(0).getBand(0).setVisible(false);
		// System.out.println(RibbonPanel.getRibbon().getTask(0).getBand(0).
		// getClass());
		// RibbonPanel.getRibbon().getTask(3).

		// RibbonPanel.getRibbon().addTask(task)

		// return wiseShowTask;
	}

	// 一直显示的面板
	private List<RibbonTask> regionShowTask;

	public JComponent getRegionRibbonPanel()
	{
		if (regionShowTask == null)
		{
			regionShowTask = new ArrayList<RibbonTask>();
		}
		regionShowTask.add(ribbonTask.get(0));
		regionShowTask.add(ribbonTask.get(1));
		regionShowTask.add(ribbonTask.get(3));
		regionShowTask.add(ribbonTask.get(4));

		/*
		 * RibbonPanel.getRibbon().removeAll(); for (WiseTask task :
		 * regionShowTask) { RibbonPanel.getRibbon().addTask(task.getTask()); }
		 */
		/*
		 * List<JComponent> uiList = new ArrayList<JComponent>(); for (WiseTask
		 * task : wiseShowTask) { System.out.println(task.getClass()); if (task
		 * instanceof AbstractRibbonBand) { AbstractRibbonBand ui =
		 * (AbstractRibbonBand) task; uiList.add(ui);
		 * 
		 * } }
		 */

		// RibbonPanel.getRibbon().remove
		JRibbon regionRibbon = new JRibbon();

		for (RibbonTask task : regionShowTask)
		{
			regionRibbon.addTask(task);
		}

		return regionRibbon;

		// RibbonPanel.getRibbon().getTask(0).getBand(0).setVisible(false);

		// uiList.get(2).setVisible(false);

	}

	public void mainRibbonSetting()
	{
		// RibbonPanel.getRibbon().removeAll();
		// for (WiseTask task : wiseShowTask) {
		// RibbonPanel.getRibbon().addTask(task.getTask());
		// }

		for (RibbonTask task : ribbonTask)
		{
			RibbonPanel.getRibbon().addTask(task);
		}

		/*
		 * for (WiseTask task : wiseShowTask) { if (task instanceof JComponent)
		 * { JComponent ui = (JComponent) task; ui.setVisible(true); } }
		 */
	}

	public List<WiseTask> getWiseShowTask()
	{
		return wiseShowTask;
	}

}
