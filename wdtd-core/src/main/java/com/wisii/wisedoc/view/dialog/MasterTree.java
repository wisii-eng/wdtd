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

package com.wisii.wisedoc.view.dialog;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.StaticContent;
import com.wisii.wisedoc.document.attribute.ConditionalPageMasterReference;
import com.wisii.wisedoc.document.attribute.EnumNumber;
import com.wisii.wisedoc.document.attribute.PageSequenceMaster;
import com.wisii.wisedoc.document.attribute.RepeatablePageMasterAlternatives;
import com.wisii.wisedoc.document.attribute.RepeatablePageMasterReference;
import com.wisii.wisedoc.document.attribute.SimplePageMaster;
import com.wisii.wisedoc.document.attribute.SinglePageMasterReference;
import com.wisii.wisedoc.document.attribute.SubSequenceSpecifier;
import com.wisii.wisedoc.document.listener.DataStructureChangeListener;
import com.wisii.wisedoc.util.WisedocUtil;
import com.wisii.wisedoc.view.dialog.MasterNode.MasterType;
import com.wisii.wisedoc.view.ui.StaticContentManeger;
import com.wisii.wisedoc.view.ui.model.CustomizeSimplePageMasterModel;
import com.wisii.wisedoc.view.ui.model.RegionAfterModel;
import com.wisii.wisedoc.view.ui.model.RegionBeforeModel;
import com.wisii.wisedoc.view.ui.model.RegionEndModel;
import com.wisii.wisedoc.view.ui.model.RegionStartModel;
import com.wisii.wisedoc.view.ui.model.SimplePageMasterModel;

@SuppressWarnings("serial")
public class MasterTree extends JTree implements DataStructureChangeListener
{

	public static int chushihuaflg = 0;

	JPopupMenu popupmenu;

	JPopupMenu popupmenuroot;

	Point currentpoint;

	JMenuItem addsingleitem;

	JMenuItem addrepeatitem;

	JMenuItem addpositionitem;

	JMenuItem afteraddsingleitem;

	JMenuItem afteraddrepeatitem;

	JMenuItem afteraddpositionitem;

	JMenuItem topmoveitem;

	JMenuItem bottommoveitem;

	Control control;

	public MasterTree()
	{
		this(null);
	}

	/**
	 * 
	 * @param structuremodel
	 *            :数据结构模型
	 * @exception
	 */
	public MasterTree(MasterTreeModel structuremodel)
	{
		setModel(structuremodel);
		setDragEnabled(true);
		setTransferHandler(new TransferHandler(""));
		initPopupMenu();
		setSize(300, 200);
		getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		getSelectionModel().addTreeSelectionListener(
				new TreeSelectionListener()
				{

					@Override
					public void valueChanged(TreeSelectionEvent e)
					{
						TreePath path = e.getPath();
						control.getresult();
						control.setCurrentnode((MasterReference) path
								.getLastPathComponent());
					}
				});
		addMouseListener(new MouseAdapter()
		{

			public void mousePressed(MouseEvent mouseevent)
			{

				if (mouseevent.getButton() == MouseEvent.BUTTON3)
				{
					MasterReference currentnode = control.getCurrentnode();
					if (currentnode != null)
					{
						Point current = mouseevent.getPoint();
						currentpoint = new Point(current.x, current.y);
						MasterType type = currentnode.getType();

						if (type == MasterType.ROOT)
						{
							popupmenuroot.show(MasterTree.this, current.x,
									current.y);
						} else if (type == MasterType.SINGLE
								|| type == MasterType.REPEAT
								|| type == MasterType.POSITION)
						{
							popupmenu.show(MasterTree.this, current.x,
									current.y);
							if (type == MasterType.POSITION
									&& currentnode.getNumber() == -1)
							{
								afteraddsingleitem.setEnabled(false);
								afteraddrepeatitem.setEnabled(false);
								afteraddpositionitem.setEnabled(false);
							} else
							{
								afteraddsingleitem.setEnabled(true);
								afteraddrepeatitem.setEnabled(true);
								afteraddpositionitem.setEnabled(true);
							}

							MasterNode parent = (MasterNode) currentnode
									.getParent();
							int index = parent.getIndex(currentnode);
							int all = parent.getChildCount();

							if (index == 0)
							{
								topmoveitem.setEnabled(false);
							} else
							{
								if (currentnode.getType() == MasterType.POSITION
										&& currentnode.getNumber() == -1)
								{
									topmoveitem.setEnabled(false);
								} else
								{
									topmoveitem.setEnabled(true);
								}
							}
							if (index == all - 1)
							{
								bottommoveitem.setEnabled(false);
							} else
							{
								MasterNode nextnode = (MasterNode) parent
										.getChildAt(index + 1);
								if (nextnode.getType() == MasterType.POSITION
										&& nextnode.getNumber() == -1)
								{
									bottommoveitem.setEnabled(false);
								} else
								{
									bottommoveitem.setEnabled(true);
								}
							}
						}

					}
				}
			}
		});
	}

	// 以前仅仅是clone布局，现在将clone流的操作一并在此完成
	public static CustomizeSimplePageMasterModel getClone(SimplePageMaster old,
			Map<String, StaticContent> scmap)
	{
		SimplePageMasterModel newspm;
		if (old == null)
		{
			newspm = new SimplePageMasterModel.Builder().build();
			String oldref = newspm.getMasterName();
			String spmname = "SPM" + chushihuaflg;
			spmname = isAdd(spmname);
			newspm.setMasterName(spmname);
			newspm.setVirtualMasterName(spmname);
			String before = spmname + "before";
			newspm.getRegionBeforeModel().setRegionName(before);
			WisedocUtil.addStaticContentclone(oldref + "before", before, scmap);

			String after = spmname + "after";
			newspm.getRegionAfterModel().setRegionName(after);
			WisedocUtil.addStaticContentclone(oldref + "after", after, scmap);

			String start = spmname + "start";
			newspm.getRegionStartModel().setRegionName(start);
			WisedocUtil.addStaticContentclone(oldref + "start", start, scmap);

			String end = spmname + "end";
			newspm.getRegionEndModel().setRegionName(end);
			WisedocUtil.addStaticContentclone(oldref + "end", end, scmap);
			MasterNameManager.addMasterName(spmname);
		} else
		{
			newspm = new SimplePageMasterModel.Builder().simplePageMaster(old)
					.build();
			setName(newspm, scmap);
		}
		chushihuaflg = chushihuaflg + 1;
		return new CustomizeSimplePageMasterModel(newspm);
	}

	private static void setName(SimplePageMasterModel spmm,
			Map<String, StaticContent> scmap)
	{
		String name = spmm.getSimplePageMaster().getMasterName();
		String spmname = name + "c" + chushihuaflg;
		spmname = isAdd(spmname);
		spmm.setMasterName(spmname);
		spmm.setVirtualMasterName(spmname);
		MasterNameManager.addMasterName(spmname);
		RegionBeforeModel before = spmm.getRegionBeforeModel();
		if (before != null)
		{
			String beforename = spmname + "before";
			before.setRegionName(beforename);
			WisedocUtil.addStaticContentclone(name + "before", beforename,
					scmap);
		}
		RegionAfterModel after = spmm.getRegionAfterModel();
		if (after != null)
		{
			String aftername = spmname + "after";
			after.setRegionName(aftername);
			WisedocUtil.addStaticContentclone(name + "after", aftername, scmap);
		}
		RegionStartModel start = spmm.getRegionStartModel();
		if (start != null)
		{
			String startname = spmname + "start";
			start.setRegionName(startname);
			WisedocUtil.addStaticContentclone(name + "start", startname, scmap);
		}
		RegionEndModel end = spmm.getRegionEndModel();
		if (end != null)
		{
			String endname = spmname + "end";
			end.setRegionName(endname);
			WisedocUtil.addStaticContentclone(name + "end", endname, scmap);
		}
	}

	public static String isAdd(String name)
	{
		String resultname = "";
		if (!MasterNameManager.isCanChage(name))
		{
			resultname = name + "c";
			if (!MasterNameManager.isCanChage(resultname))
			{
				return isAdd(resultname);
			} else
			{
				return resultname;
			}
		}
		return name;
	}

	public static CustomizeSimplePageMasterModel getModel(SimplePageMaster old)
	{
		SimplePageMasterModel newspm;
		if (old == null)
		{
			newspm = new SimplePageMasterModel.Builder().build();
		} else
		{
			newspm = new SimplePageMasterModel.Builder().simplePageMaster(old)
					.build();
		}
		return new CustomizeSimplePageMasterModel(newspm);
	}

	public TreeCellRenderer getCellRenderer()
	{
		return new MasterCellRender();
	}

	public void initPopupMenu()
	{
		popupmenuroot = new JPopupMenu();
		popupmenuroot.setSize(200, 100);

		addsingleitem = new JMenuItem("添加单次使用");
		AddSingleitemAction addsingleitemAction = new AddSingleitemAction();
		addsingleitem.addActionListener(addsingleitemAction);

		addrepeatitem = new JMenuItem("添加多次使用");
		AddRepeatitemAction addrepeatitemAction = new AddRepeatitemAction();
		addrepeatitem.addActionListener(addrepeatitemAction);

		addpositionitem = new JMenuItem("添加特殊位置使用");
		AddPositionitemAction addpositionitemAction = new AddPositionitemAction();
		addpositionitem.addActionListener(addpositionitemAction);

		popupmenuroot.add(addsingleitem);
		popupmenuroot.add(addrepeatitem);
		popupmenuroot.add(addpositionitem);

		popupmenu = new JPopupMenu();
		popupmenu.setSize(200, 100);

		afteraddsingleitem = new JMenuItem("添加单次使用");
		AfterAddSingleitemAction afteraddsingleitemAction = new AfterAddSingleitemAction();
		afteraddsingleitem.addActionListener(afteraddsingleitemAction);

		afteraddrepeatitem = new JMenuItem("添加多次使用");
		AfterAddRepeatitemAction afteraddrepeatitemAction = new AfterAddRepeatitemAction();
		afteraddrepeatitem.addActionListener(afteraddrepeatitemAction);

		afteraddpositionitem = new JMenuItem("添加特殊位置使用");
		AfterAddPositionitemAction afteraddpositionitemAction = new AfterAddPositionitemAction();
		afteraddpositionitem.addActionListener(afteraddpositionitemAction);

		JMenuItem deletecurrentitem = new JMenuItem("删除当前使用");
		RemoveitemAction removeitemAction = new RemoveitemAction();
		deletecurrentitem.addActionListener(removeitemAction);

		topmoveitem = new JMenuItem("上移");
		TopMoveitemAction topmoveitemAction = new TopMoveitemAction();
		topmoveitem.addActionListener(topmoveitemAction);

		bottommoveitem = new JMenuItem("下移");
		BottomMoveitemAction bottomoveitemAction = new BottomMoveitemAction();
		bottommoveitem.addActionListener(bottomoveitemAction);

		popupmenu.add(afteraddsingleitem);
		popupmenu.add(afteraddrepeatitem);
		popupmenu.add(afteraddpositionitem);
		popupmenu.addSeparator();
		popupmenu.add(deletecurrentitem);
		popupmenu.addSeparator();
		popupmenu.add(topmoveitem);
		popupmenu.add(bottommoveitem);
	}

	class AddSingleitemAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			MasterNode newnode = new MasterReference(MasterType.SINGLE, "单次使用");
			SimplePageMaster spm = getClone(null,
					StaticContentManeger.getScmap()).getSimplePageMaster();
			newnode.setValue(spm);
			control.getCurrentnode().addChild(newnode, 0);
			MasterTree.this.updateUI();
		}
	}

	class AfterAddSingleitemAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{

			MasterNode newnode = new MasterReference(MasterType.SINGLE, "单次使用");
			SimplePageMaster spm = getClone(null,
					StaticContentManeger.getScmap()).getSimplePageMaster();
			newnode.setValue(spm);
			MasterNode parent = (MasterNode) control.getCurrentnode()
					.getParent();
			int index = parent.getIndex(control.getCurrentnode());
			parent.addChild(newnode, index + 1);
			MasterTree.this.updateUI();
		}
	}

	class AddRepeatitemAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			MasterNode newnode = new MasterReference(MasterType.REPEAT, "多次使用");
			SimplePageMaster spm = getClone(null,
					StaticContentManeger.getScmap()).getSimplePageMaster();
			newnode.setValue(spm);
			control.getCurrentnode().addChild(newnode, 0);
			MasterTree.this.updateUI();
		}
	}

	class AfterAddRepeatitemAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			MasterNode newnode = new MasterReference(MasterType.REPEAT, "多次使用");
			SimplePageMaster spm = getClone(null,
					StaticContentManeger.getScmap()).getSimplePageMaster();
			newnode.setValue(spm);
			MasterNode parent = (MasterNode) control.getCurrentnode()
					.getParent();
			int index = parent.getIndex(control.getCurrentnode());
			parent.addChild(newnode, index + 1);
			MasterTree.this.updateUI();
		}
	}

	class AddPositionitemAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			MasterNode newnode = new MasterReference(MasterType.POSITION,
					"特殊位置使用");
			MasterNode newchildnode = new MasterReference(
					MasterType.CONDITION_REST, "剩余页使用");
			SimplePageMaster spm = getClone(null,
					StaticContentManeger.getScmap()).getSimplePageMaster();
			newchildnode.setValue(spm);
			newnode.addChild(newchildnode);
			control.getCurrentnode().addChild(newnode, 0);
			MasterTree.this.updateUI();
		}
	}

	class AfterAddPositionitemAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			MasterReference currentnode = control.getCurrentnode();
			MasterNode newnode = new MasterReference(MasterType.POSITION,
					"特殊位置使用");
			MasterNode newchildnode = new MasterReference(
					MasterType.CONDITION_REST, "剩余页使用");
			SimplePageMaster spm = getClone(null,
					StaticContentManeger.getScmap()).getSimplePageMaster();
			newchildnode.setValue(spm);
			newnode.addChild(newchildnode);
			MasterNode parent = (MasterNode) currentnode.getParent();
			int index = parent.getIndex(currentnode);
			parent.addChild(newnode, index + 1);
			MasterTree.this.updateUI();
		}
	}

	class RemoveitemAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			MasterReference currentnode = control.getCurrentnode();
			MasterNode parent = (MasterNode) currentnode.getParent();
			MasterType type = currentnode.getType();
			if (type == MasterType.POSITION)
			{
				List<MasterNode> children = currentnode.getChildren();
				if (children != null)
				{
					for (MasterNode current : children)
					{
						String name = current.getValue().getMasterName();
						WisedocUtil.removeStaticContents(name,
								StaticContentManeger.getScmap());
						MasterNameManager.removeMasterName(name);
					}
				}
			} else
			{
				String name = currentnode.getValue().getMasterName();
				WisedocUtil.removeStaticContents(name, StaticContentManeger
						.getScmap());
				MasterNameManager.removeMasterName(name);
			}

			parent.removeChild(currentnode);
			List<MasterNode> childrens = parent.getChildren();
			if (childrens != null && !childrens.isEmpty())
			{
				control.setCurrentnode((MasterReference) parent
						.getChildAt(0));
			} else
			{
				control.setCurrentnode((MasterReference)parent);
			}
			MasterTree.this.updateUI();
		}
	}

	class TopMoveitemAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			MasterNode parent = (MasterNode) control.getCurrentnode()
					.getParent();
			int index = parent.getIndex(control.getCurrentnode());
			MasterNode beforenode = (MasterNode) parent.getChildAt(index - 1);
			parent.removeChild(beforenode);
			parent.addChild(beforenode, index);
			MasterTree.this.updateUI();
		}
	}

	class BottomMoveitemAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			MasterReference currentnode = control.getCurrentnode();
			MasterNode parent = (MasterNode) currentnode.getParent();
			int index = parent.getIndex(currentnode);
			MasterNode afternode = (MasterNode) parent.getChildAt(index + 1);
			parent.removeChild(afternode);
			parent.addChild(afternode, index);
			MasterTree.this.updateUI();
		}
	}

	@Override
	public void DataStructureChanged()
	{

	}

	public Control getControl()
	{
		return control;
	}

	public void setControl(Control control)
	{
		this.control = control;
	}

	public boolean setMaster()
	{
		MasterTreeModel model = (MasterTreeModel) this.getModel();
		MasterNode root = (MasterNode) model.getRoot();
		List<MasterNode> childrens = root.getChildren();
		if (childrens != null && !childrens.isEmpty())
		{
			boolean flg = true;
			for (MasterNode current : childrens)
			{
				if (current.getValue() == null)
				{
					if (current.getType() == MasterType.POSITION)
					{
						List<MasterNode> grchildrens = current.getChildren();
						if (grchildrens != null && !grchildrens.isEmpty())
						{
							for (MasterNode currentgr : grchildrens)
							{
								if (currentgr.getValue() == null)
								{
									return false;
								}
							}
						} else
						{
							return false;
						}
					} else
					{
						return false;
					}
				}
			}
			return flg;
		}
		return false;
	}

	public Object getMaster()
	{
		MasterTreeModel model = (MasterTreeModel) this.getModel();
		MasterNode root = (MasterNode) model.getRoot();
		List<MasterNode> childrens = root.getChildren();
		if (childrens != null && !childrens.isEmpty())
		{
			List<SubSequenceSpecifier> subSequenceSpecifiers = new ArrayList<SubSequenceSpecifier>();
			for (MasterNode current : childrens)
			{
				MasterType type = current.getType();
				SimplePageMaster currentvalue=current.getValue();
				switch (type)
				{
					case SINGLE:
					{
						SinglePageMasterReference currentsingle = new SinglePageMasterReference(
								currentvalue);
						subSequenceSpecifiers.add(currentsingle);
						StaticContentManeger.addRealstaticcontent(currentvalue);
						break;
					}
					case REPEAT:
					{
						RepeatablePageMasterReference currentrepeat = new RepeatablePageMasterReference(
								new EnumNumber(0, current.getNumber()), currentvalue);
						subSequenceSpecifiers.add(currentrepeat);
						StaticContentManeger.addRealstaticcontent(currentvalue);
						break;
					}
					case POSITION:
					{
						List<ConditionalPageMasterReference> listcpmr = new ArrayList<ConditionalPageMasterReference>();
						Map<MasterType, ConditionalPageMasterReference> resultmap = new HashMap<MasterType, ConditionalPageMasterReference>();
						List<MasterNode> grchildrens = current.getChildren();
						if (grchildrens != null && !grchildrens.isEmpty())
						{
							for (MasterNode currentgr : grchildrens)
							{
								SimplePageMaster currentspm = currentgr
										.getValue();
								StaticContentManeger.addRealstaticcontent(currentspm);
								ConditionalPageMasterReference currentcpm = null;
								int any = Constants.EN_ANY;
								MasterType grtype = currentgr.getType();
								switch (grtype)
								{
									case CONDITION_FIRST:
									{
										currentcpm = new ConditionalPageMasterReference(
												currentspm, Constants.EN_FIRST,
												any, any);
										break;
									}
									case CONDITION_LAST:
									{
										currentcpm = new ConditionalPageMasterReference(
												currentspm, Constants.EN_LAST,
												any, any);
										break;
									}
									case CONDITION_REST:
									{
										currentcpm = new ConditionalPageMasterReference(
												currentspm, Constants.EN_ANY,
												any, any);
										break;
									}
									case CONDITION_ODD:
									{
										currentcpm = new ConditionalPageMasterReference(
												currentspm, any,
												Constants.EN_ODD, any);
										break;
									}
									case CONDITION_EVEN:
									{
										currentcpm = new ConditionalPageMasterReference(
												currentspm, any,
												Constants.EN_EVEN, any);
										break;
									}
									case CONDITION_BLANK:
									{
										currentcpm = new ConditionalPageMasterReference(
												currentspm, any, any,
												Constants.EN_BLANK);
										break;
									}
								}
								resultmap.put(grtype, currentcpm);
							}
						}
						if (resultmap.containsKey(MasterType.CONDITION_FIRST))
						{
							listcpmr.add(resultmap
									.get(MasterType.CONDITION_FIRST));
						}
						if (resultmap.containsKey(MasterType.CONDITION_LAST))
						{
							listcpmr.add(resultmap
									.get(MasterType.CONDITION_LAST));
						}
						if (resultmap.containsKey(MasterType.CONDITION_ODD))
						{
							listcpmr.add(resultmap
									.get(MasterType.CONDITION_ODD));
						}
						if (resultmap.containsKey(MasterType.CONDITION_EVEN))
						{
							listcpmr.add(resultmap
									.get(MasterType.CONDITION_EVEN));
						}
						if (resultmap.containsKey(MasterType.CONDITION_BLANK))
						{
							listcpmr.add(resultmap
									.get(MasterType.CONDITION_BLANK));
						}
						if (resultmap.containsKey(MasterType.CONDITION_REST))
						{
							listcpmr.add(resultmap
									.get(MasterType.CONDITION_REST));
						}
						EnumNumber currentnumber = current.getNumber() > 0 ? new EnumNumber(
								0, current.getNumber())
								: new EnumNumber(
										RepeatablePageMasterAlternatives.INFINITE,
										-1);
						RepeatablePageMasterAlternatives rpm = new RepeatablePageMasterAlternatives(
								currentnumber, listcpmr);
						subSequenceSpecifiers.add(rpm);
					}
				}
			}
			PageSequenceMaster psm = new PageSequenceMaster(
					subSequenceSpecifiers);
			return psm;
		}
		return null;
	}
}
