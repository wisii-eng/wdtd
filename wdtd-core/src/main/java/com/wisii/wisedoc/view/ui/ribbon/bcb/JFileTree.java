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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * <p>
 * Title: OpenSwing
 * </p>
 * <p>
 * Description: JFileTree 文件树
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author <a href="mailto:sunkingxie@hotmail.com">SunKing</a>
 * @version 1.0
 */

public class JFileTree extends JTree implements Serializable
{

	public static final FileSystemView FILE_SYSTEM_VIEW = FileSystemView
			.getFileSystemView();

	DefaultTreeModel treeModel;

	private java.io.FileFilter filter = null;

	/**
	 * 只浏览目录的目录树
	 */
	public JFileTree()
	{
		this(new DirFilter());
	}

	/**
	 * 指定过滤器的文件树
	 * 
	 * @param filter
	 *            FileFilter 指定过滤器
	 */
	public JFileTree(java.io.FileFilter filter)
	{
		FileNode root = new FileNode(WiseTemplateManager.getRootfile(), filter);
		treeModel = new DefaultTreeModel(root);
		root.explore();
		treeModel.nodeStructureChanged(root);
		this.setModel(treeModel);
		this.setEditable(true);

		// XXX for test start
		this.addTreeSelectionListener(new TreeSelectionListener()
		{

			@Override
			public void valueChanged(TreeSelectionEvent e)
			{

				TreePath tp = e.getPath();

				Object[] o = tp.getPath();

				List<FileNode> ftnList = new ArrayList<FileNode>();

				for (Object oo : o)
				{
					if (oo instanceof FileNode)
					{
						FileNode ftn = (FileNode) oo;
						ftnList.add(ftn);
					}
				}

//				System.out.println("??????????"
//						+ ftnList.get(ftnList.size() - 1).getUserObject());
				//WiseTemplateManager.Instance.updata(ftnList.get(ftnList.size()
				// -1).getUserObject());
				WiseTemplateManager.Instance.updataFileTree();
			}
		});
		
		/*this.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				System.err.println(evt.getNewValue());
			}
		});*/
		
		treeModel.addTreeModelListener(new TreeModelHandler() {
			@Override
			public void treeNodesChanged(TreeModelEvent e) {
				File current = WiseTemplateManager.Instance.getCurrentSelectedFolder();
				
				String newName = (String)((FileNode)e.getChildren()[0]).getUserObject();
				
				String newFolder = current.getParentFile().getPath() + "\\" + newName;
				
//				System.out.println("new folder: " + newFolder);
				File newFile = new File(newFolder);
				current.renameTo(newFile);
				
				
				FileNode node;
	            node = (FileNode)(e.getTreePath().getLastPathComponent());

	            /*
	             * If the event lists children, then the changed
	             * node is the child of the node we've already
	             * gotten.  Otherwise, the changed node and the
	             * specified node are the same.
	             */

	            int index = e.getChildIndices()[0];
	            node = (FileNode)(node.getChildAt(index));
	            
	            node.setUserObject(WiseTemplateManager.Instance.getCurrentSelectedFolder().getParentFile());

	            WiseTemplateManager.Instance.setCurrentSelectedFolder(newFile);
	            
	            WiseTemplateManager.Instance.updateFileTreePanel();
	            
//	            System.out.println("The user has finished editing the node.");
//	            System.out.println("New value: " + node.getUserObject() + " : " + node.getUserObject().getClass());
//
//				System.err.println(current);
			}
		});

		// XXX for test end

		addTreeExpansionListener(new JFileTreeExpandsionListener());
		setCellRenderer(new JFileTreeCellRenderer());
	}

	/**
	 * 取得当前选择的节点
	 * 
	 * @return FileNode
	 */
	public FileNode getSelectFileNode()
	{
		TreePath path = getSelectionPath();
		if (path == null || path.getLastPathComponent() == null)
		{
			return null;
		}
		return (FileNode) path.getLastPathComponent();
	}

	/**
	 * 设置当前选择的节点
	 * 
	 * @param f
	 *            FileNode
	 * @throws Exception
	 */
	public void setSelectFileNode(FileNode f) throws Exception
	{
		this.setSelectFile(f.getFile());
	}

	/**
	 * 取得当前选择的文件或目录
	 * 
	 * @return File
	 */
	public File getSelectFile()
	{
		FileNode node = getSelectFileNode();
		return node == null ? null : node.getFile();
	}

	/**
	 * 设置当前选择的文件或目录
	 * 
	 * @param f
	 *            File
	 * @throws Exception
	 */
	public void setSelectFile(File f) throws Exception
	{
		FileNode node = this.expandFile(f);
		TreePath path = new TreePath(node.getPath());
		this.scrollPathToVisible(path);
		this.setSelectionPath(path);
		this.repaint();
	}

	/**
	 * 展开指定的文件或目录
	 * 
	 * @param f
	 *            File
	 * @return FileNode
	 * @throws Exception
	 */
	public FileNode expandFile(File f) throws Exception
	{
		if (!f.exists())
		{
			throw new java.io.FileNotFoundException(f.getAbsolutePath());
		}
		Vector vTemp = new Vector();
		File fTemp = f;
		while (fTemp != null)
		{
			vTemp.add(fTemp);
			fTemp = FILE_SYSTEM_VIEW.getParentDirectory(fTemp);
		}

		FileNode nParent = (FileNode) treeModel.getRoot();
		for (int i = vTemp.size() - 1; i >= 0; i--)
		{
			fTemp = (File) vTemp.get(i);
			nParent.explore();
			for (int j = 0; j < nParent.getChildCount(); j++)
			{
				FileNode nChild = (FileNode) nParent.getChildAt(j);
				if (nChild.getFile().equals(fTemp))
				{
					nParent = nChild;
				}
			}
		}
		return nParent;
	}

	/**
	 * 
	 * <p>
	 * Title: OpenSwing
	 * </p>
	 * 
	 * <p>
	 * Description: 文件树修饰器
	 * </p>
	 * 
	 * <p>
	 * Copyright: Copyright (c) 2004
	 * </p>
	 * 
	 * <p>
	 * Company:
	 * </p>
	 * 
	 * @author <a href="mailto:sunkingxie@hotmail.com">SunKing</a>
	 * @version 1.0
	 */
	class JFileTreeCellRenderer extends DefaultTreeCellRenderer
	{

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus)
		{
			super.getTreeCellRendererComponent(tree, value, sel, expanded,
					leaf, row, hasFocus);
			try
			{
				FileNode node = (FileNode) value;
				closedIcon = FILE_SYSTEM_VIEW.getSystemIcon(((FileNode) value)
						.getFile());
				openIcon = closedIcon;
				setIcon(closedIcon);
			} catch (Exception ex)
			{
			}
			return this;
		}
	}

	/**
	 * 
	 * <p>
	 * Title: OpenSwing
	 * </p>
	 * 
	 * <p>
	 * Description: 文件树展开事件监听器
	 * </p>
	 * 
	 * <p>
	 * Copyright: Copyright (c) 2004
	 * </p>
	 * 
	 * <p>
	 * Company:
	 * </p>
	 * 
	 * @author <a href="mailto:sunkingxie@hotmail.com">SunKing</a>
	 * @version 1.0
	 */
	class JFileTreeExpandsionListener implements TreeExpansionListener
	{

		public JFileTreeExpandsionListener()
		{
		}

		public void treeExpanded(TreeExpansionEvent event)
		{
			TreePath path = event.getPath();
			if (path == null || path.getLastPathComponent() == null)
			{
				return;
			}
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			FileNode node = (FileNode) path.getLastPathComponent();
			node.explore();
			JTree tree = (JTree) event.getSource();
			DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
			treeModel.nodeStructureChanged(node);
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}

		public void treeCollapsed(TreeExpansionEvent event)
		{
		}
	}

	/**
	 * 
	 * <p>
	 * Title: OpenSwing
	 * </p>
	 * 
	 * <p>
	 * Description:文件节点
	 * </p>
	 * 
	 * <p>
	 * Copyright: Copyright (c) 2004
	 * </p>
	 * 
	 * <p>
	 * Company:
	 * </p>
	 * 
	 * @author <a href="mailto:sunkingxie@hotmail.com">SunKing</a>
	 * @version 1.0
	 */
	public static class FileNode extends DefaultMutableTreeNode
	{

		private boolean explored = false;

		private java.io.FileFilter filter = null;

		public FileNode(File file, java.io.FileFilter filter)
		{
			if (filter == null)
			{
				this.filter = new DirFilter();
			} else
			{
				this.filter = filter;
			}
			setUserObject(file);
		}

		@Override
		public boolean getAllowsChildren()
		{
			return isDirectory();
		}

		public boolean isDirectory()
		{
			return !isLeaf();
		}

		@Override
		public boolean isLeaf()
		{
			return getFile().isFile();
		}

		public File getFile()
		{
			/*Object o = getUserObject();
			
			if (o instanceof File) {
				File ff = (File) o;
				return ff;
			}*/
			
			return (File) getUserObject();
		}

		public boolean isExplored()
		{
			return explored;
		}

		public void setExplored(boolean b)
		{
			explored = b;
		}

		@Override
		public String toString()
		{
			if (getFile() instanceof File)
			{
				return FILE_SYSTEM_VIEW.getSystemDisplayName(getFile());
			} else
			{
				return getFile().toString();
			}
		}

		/**
		 * 展开节点
		 */
		public void explore()
		{
			if (!explored)
			{
				explored = true;
				File file = getFile();
				// 如果这里使用 file.listFiles(filter) 有BUG
				File[] children = file.listFiles();
				if (children == null || children.length == 0)
				{
					return;
				}
				// 过滤后排序,选加入排序后的目录, 再加入排序后的文件
				ArrayList listDir = new ArrayList();
				ArrayList listFile = new ArrayList();
				for (int i = 0; i < children.length; ++i)
				{
					File f = children[i];
					if (filter.accept(f))
					{
						if (f.isDirectory())
						{
							listDir.add(f);
						} else
						{
							listFile.add(f);
						}
					}
				}
				Collections.sort(listDir);
				Collections.sort(listFile);
				for (int i = 0; i < listDir.size(); i++)
				{
					add(new FileNode((File) listDir.get(i), filter));
				}
				for (int i = 0; i < listFile.size(); i++)
				{
					add(new FileNode((File) listFile.get(i), filter));
				}
			}
		}
	}

	/**
	 * 
	 * <p>
	 * Title: OpenSwing
	 * </p>
	 * 
	 * <p>
	 * Description:目录过滤器
	 * </p>
	 * 
	 * <p>
	 * Copyright: Copyright (c) 2004
	 * </p>
	 * 
	 * <p>
	 * Company:
	 * </p>
	 * 
	 * @author <a href="mailto:sunkingxie@hotmail.com">SunKing</a>
	 * @version 1.0
	 */
	public static class DirFilter implements java.io.FileFilter
	{

		public boolean accept(File pathname)
		{
			return pathname.isDirectory();
		}
	}

	/**
	 * 
	 * <p>
	 * Title: OpenSwing
	 * </p>
	 * 
	 * <p>
	 * Description: 所有文件过滤器
	 * </p>
	 * 
	 * <p>
	 * Copyright: Copyright (c) 2004
	 * </p>
	 * 
	 * <p>
	 * Company:
	 * </p>
	 * 
	 * @author <a href="mailto:sunkingxie@hotmail.com">SunKing</a>
	 * @version 1.0
	 */
	public static class AllFileFilter implements java.io.FileFilter
	{

		public boolean accept(File pathname)
		{
			return true;
		}
	}

	/**
	 * 
	 * <p>
	 * Title: OpenSwing
	 * </p>
	 * 
	 * <p>
	 * Description:扩展名过滤器
	 * </p>
	 * 
	 * <p>
	 * Copyright: Copyright (c) 2004
	 * </p>
	 * 
	 * <p>
	 * Company:
	 * </p>
	 * 
	 * @author <a href="mailto:sunkingxie@hotmail.com">SunKing</a>
	 * @version 1.0
	 */
	public static class ExtensionFilter implements java.io.FileFilter
	{

		String extensions[];

		public ExtensionFilter(String extensions[])
		{
			this.extensions = new String[extensions.length];
			for (int i = 0; i < extensions.length; i++)
			{
				this.extensions[i] = extensions[i].toLowerCase();
			}
		}

		public boolean accept(File pathname)
		{
			if (pathname.isDirectory())
			{
				return true;
			}
			String name = pathname.getName().toLowerCase();
			for (int i = 0; i < extensions.length; i++)
			{
				if (name.endsWith(this.extensions[i]))
				{
					return true;
				}
			}
			return false;
		}
	}

	public static void main(String[] args)
	{
		// JFrame frame = OpenSwingUtil.createDemoFrame("JFileTree Demo");
		JFrame frame = new JFrame("测试");
		frame.setSize(600, 400);
		JFileTree tree = new JFileTree();
		try
		{
			tree.setSelectFile(new File("C:/"));
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		Container pContent = frame.getContentPane();
		pContent.setLayout(new BorderLayout());
		JPanel pNorth = new JPanel(new GridLayout(1, 3));
		JPanel pCenter = new JPanel(new GridLayout(1, 3));
		pNorth.add(new JLabel("Directory only"));
		pNorth.add(new JLabel("All file"));
		pNorth.add(new JLabel("Directory and *.doc,*.txt file"));
		pCenter.add(new JScrollPane(tree));
		pCenter.add(new JScrollPane(new JFileTree(new AllFileFilter())));
		pCenter.add(new JScrollPane(new JFileTree(new ExtensionFilter(
				new String[]
				{ "doc", "txt" }))));
		pContent.add(pNorth, BorderLayout.NORTH);
		pContent.add(pCenter, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
