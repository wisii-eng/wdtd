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
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.TreeNode;

import org.jvnet.flamingo.bcb.BreadcrumbItem;

@SuppressWarnings("serial")
public class WiseTreeBcb extends JPanel
{

	private List<File> fileList = new ArrayList<File>();

	FilePanel filepanel = new FilePanel();

	JScrollPane fileListScrollPane;

	// private BreadcrumbTreeAdapterSelector bar;

	// public BreadcrumbTreeAdapterSelector getBar()
	// {
	// return bar;
	// }

	/**
	 * A node in the file tree.
	 * 
	 * @author Kirill Grouchnikov
	 */
	private static class FileTreeNode implements TreeNode
	{

		/**
		 * Node file.
		 */
		private File file;

		/**
		 * Children of the node file.
		 */
		private File[] children;

		/**
		 * Parent node.
		 */
		private TreeNode parent;

		/**
		 * Creates a new file tree node.
		 * 
		 * @param file
		 *            Node file
		 * @param isFileSystemRoot
		 *            Indicates whether the file is a file system root.
		 * @param parent
		 *            Parent node.
		 */
		public FileTreeNode(File file, TreeNode parent)
		{
			this.file = file;
			this.parent = parent;
			this.children = getChildren(file);
			if (this.children == null)
			{
				this.children = new File[0];
			}
		}

		public File[] getChildren(File file)
		{
			File[] filelist = file.listFiles();
			List<File> wenjianlist = new ArrayList<File>();
			if (filelist != null && filelist.length > 0)
			{
				for (int i = 0; i < filelist.length; i++)
				{
					File current = filelist[i];
					if (!current.isDirectory())
					{
						String path = current.getPath();
						if (!path.equals(""))
						{
							String[] names = path.split("\\.");
							int length = names.length;
							if (names[length - 1].equalsIgnoreCase("wsdt"))
							{
								wenjianlist.add(current);
							}
						}
					}
				}
			}
			filelist = (File[]) wenjianlist.toArray();
			return filelist;
		}

		/**
		 * Creates a new file tree node.
		 * 
		 * @param children
		 *            Children files.
		 */
		public FileTreeNode(File[] children)
		{
			this.file = null;
			this.parent = null;
			this.children = children;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.tree.TreeNode#children()
		 */
		public Enumeration<?> children()
		{
			final int elementCount = this.children.length;
			return new Enumeration<File>()
			{

				int count = 0;

				/*
				 * (non-Javadoc)
				 * 
				 * @see java.util.Enumeration#hasMoreElements()
				 */
				public boolean hasMoreElements()
				{
					return this.count < elementCount;
				}

				/*
				 * (non-Javadoc)
				 * 
				 * @see java.util.Enumeration#nextElement()
				 */
				public File nextElement()
				{
					if (this.count < elementCount)
					{
						return FileTreeNode.this.children[this.count++];
					}
					throw new NoSuchElementException("Vector Enumeration");
				}
			};

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.tree.TreeNode#getAllowsChildren()
		 */
		public boolean getAllowsChildren()
		{
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.tree.TreeNode#getChildAt(int)
		 */
		public TreeNode getChildAt(int childIndex)
		{
			return new FileTreeNode(this.children[childIndex], this);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.tree.TreeNode#getChildCount()
		 */
		public int getChildCount()
		{
			return this.children.length;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.tree.TreeNode#getIndex(javax.swing.tree.TreeNode)
		 */
		public int getIndex(TreeNode node)
		{
			FileTreeNode ftn = (FileTreeNode) node;
			for (int i = 0; i < this.children.length; i++)
			{
				if (ftn.file.equals(this.children[i]))
				{
					return i;
				}
			}
			return -1;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.tree.TreeNode#getParent()
		 */
		public TreeNode getParent()
		{
			return this.parent;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.tree.TreeNode#isLeaf()
		 */
		public boolean isLeaf()
		{
			boolean isNotFolder = (this.file != null) && (this.file.isFile());
			return (this.getChildCount() == 0) && isNotFolder;
		}
	}

	// public class FileListRenderer extends JLabel implements ListCellRenderer
	// {
	//
	// public FileListRenderer()
	// {
	// this.setBorder(new EmptyBorder(1, 1, 1, 1));
	// this.setFont(new Font("Tahoma", Font.PLAIN, 11));
	// this.setOpaque(true);
	// }
	//
	// public Component getListCellRendererComponent(JList list, Object value,
	// int index, boolean isSelected, boolean cellHasFocus)
	// {
	// File file = (File) value;
	// TemplateReader reader = new TemplateReader();
	// Image image = null;
	// try
	// {
	// image = reader.readIcon(file);
	// // image.
	// } catch (IOException e)
	// {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// if (image != null)
	// {
	// ImageIcon icon = new ImageIcon(image);
	// // icon.set
	// this.setIcon(icon);
	// } else
	// {
	// this.setIcon(FileSystemView.getFileSystemView().getSystemIcon(
	// file));
	// }
	//
	// this.setText(FileSystemView.getFileSystemView()
	// .getSystemDisplayName(file));
	// Color back = (index % 2 == 0) ? new Color(250, 250, 250)
	// : new Color(240, 240, 240);
	// if (isSelected)
	// {
	// back = new Color(220, 220, 240);
	// }
	// this.setBackground(back);
	// return this;
	// }
	// }

	public static class FileListModel extends AbstractListModel
	{

		private ArrayList<File> files = new ArrayList<File>();

		public void add(File file)
		{
			files.add(file);
		}

		public void sort()
		{
			Collections.sort(files, new Comparator<File>()
			{

				public int compare(File o1, File o2)
				{
					if (o1.isDirectory() && (!o2.isDirectory()))
					{
						return -1;
					}
					if (o2.isDirectory() && (!o1.isDirectory()))
					{
						return 1;
					}
					return o1.getName().toLowerCase().compareTo(
							o2.getName().toLowerCase());
				}
			});
		}

		public Object getElementAt(int index)
		{
			return files.get(index);
		}

		public int getSize()
		{
			return files.size();
		}
	}

	public class MemoryListRenderer extends JLabel implements ListCellRenderer
	{

		public MemoryListRenderer()
		{
			this.setBorder(new EmptyBorder(1, 1, 1, 1));
			this.setFont(new Font("Tahoma", Font.PLAIN, 11));
			this.setOpaque(true);
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus)
		{
			BreadcrumbItem<File>[] path = (BreadcrumbItem<File>[]) value;
			if (path.length > 0)
			{
				// this.setIcon(bar.getFileSystemView().getSystemIcon(
				// new File(path[0].getValue()[1])));
				this.setText(path[path.length - 1].getData().getName());
			} else
			{
				this.setIcon(null);
				this.setText("Root");
			}
			Color back = (index % 2 == 0) ? new Color(250, 250, 250)
					: new Color(240, 240, 240);
			this.setBackground(back);
			return this;
		}
	}

	public WiseTreeBcb()
	{
		File[] roots = WiseTemplateManager.getRootfile().listFiles();
		FileTreeNode rootTreeNode = new FileTreeNode(roots);

		// this.bar = new BreadcrumbTreeAdapterSelector(new DefaultTreeModel(
		// rootTreeNode), new BreadcrumbTreeAdapterSelector.TreeAdapter()
		// {
		//
		// @Override
		// public String toString(Object node)
		// {
		// FileTreeNode n = (FileTreeNode) node;
		// if (n.file == null)
		// {
		// return "Computer";
		// }
		// String result = FileSystemView.getFileSystemView()
		// .getSystemDisplayName(n.file);
		// if (result.length() == 0)
		// {
		// result = n.file.getAbsolutePath();
		// }
		// return result;
		// }
		//
		// @Override
		// public Icon getIcon(Object node)
		// {
		// FileTreeNode n = (FileTreeNode) node;
		// if (n.file == null)
		// {
		// return null;
		// }
		// Icon result = FileSystemView.getFileSystemView().getSystemIcon(
		// n.file);
		// return result;
		// }
		// }, false);
		// this.bar.getModel().addPathListener(new BreadcrumbPathListener()
		// {
		//
		// @Override
		// public void breadcrumbPathEvent(BreadcrumbPathEvent event)
		// {
		// SwingUtilities.invokeLater(new Runnable()
		// {
		//
		// public void run()
		// {
		// final List<BreadcrumbItem<Object>> newPath = bar
		// .getModel().getItems();
		// /*
		// * System.out.println("New path is "); for
		// * (BreadcrumbItem<Object> item : newPath) {
		// * FileTreeNode node = (FileTreeNode) item.getData(); if
		// * (node.file == null) { continue; }
		// * System.out.println("\t" + node.file.getName()); }
		// */
		//
		// if (newPath.size() > 0)
		// {
		// SwingWorker<List<StringValuePair<Object>>, Void> worker = new
		// SwingWorker<List<StringValuePair<Object>>, Void>()
		// {
		//
		// @Override
		// protected List<StringValuePair<Object>> doInBackground()
		// throws Exception
		// {
		// return bar.getCallback().getLeafs(newPath);
		// }
		//
		// @Override
		// protected void done()
		// {
		// try
		// {
		// FileListModel model = new FileListModel();
		// List<StringValuePair<Object>> leafs = get();
		// for (StringValuePair<Object> leaf : leafs)
		// {
		// FileTreeNode node = (FileTreeNode) leaf
		// .getValue();
		// model.add(node.file);
		// }
		// model.sort();
		// fileList.setModel(model);
		// } catch (Exception exc)
		// {
		// }
		// // System.out.println("number:"+fileList.size());
		// }
		// };
		// worker.execute();
		// }
		// return;
		//
		// }
		// });
		// }
		// });

		this.setLayout(new BorderLayout());
		// this.add(bar, BorderLayout.NORTH);
		// bar.setVisible(false);

		// // XXX for test start
		// fileList.addListSelectionListener(new ListSelectionListener()
		// {
		//
		// @Override
		// public void valueChanged(ListSelectionEvent e)
		// {
		//
		// if (e.getValueIsAdjusting())
		// {
		// if (e.getSource() instanceof JList)
		// {
		// JList list = (JList) e.getSource();
		// System.out.println(list.getSelectedValue());
		// WiseTemplateManager.Instance.updataFileList(list
		// .getSelectedValue());
		// OpenModelDialog.setFile((File) list.getSelectedValue());
		// System.out.println("huo de lei xing ::"
		// + list.getSelectedValue().getClass());
		// System.out.println("cun ru:"
		// + ((File) list.getSelectedValue()).getPath());
		// }
		// }
		// }
		// });

		// XXXfor test end

		fileListScrollPane = new JScrollPane();
		fileListScrollPane.setViewportView(filepanel);
		// fileListScrollPane.setBorder(new TitledBorder("File list"));

		// fileListScrollPane.setBorder(new TitledBorder(
		// getFileName(WiseTemplateManager.getCurrentSelectedFolder())
		// + "包含的模板文件"));
		this.add(fileListScrollPane, BorderLayout.CENTER);
	}

	// public void updateBar()
	// {
	//
	// SwingUtilities.invokeLater(new Runnable()
	// {
	//
	// public void run()
	// {
	// final List<BreadcrumbItem<Object>> newPath = bar.getModel()
	// .getItems();
	//
	// /*
	// * System.out.println("New path is "); for
	// * (BreadcrumbItem<Object> item : newPath) { FileTreeNode node =
	// * (FileTreeNode) item.getData(); if (node.file == null) {
	// * continue; } System.out.println("\t" + node.file.getName()); }
	// */
	//
	// if (newPath.size() > 0)
	// {
	// SwingWorker<List<StringValuePair<Object>>, Void> worker = new
	// SwingWorker<List<StringValuePair<Object>>, Void>()
	// {
	//
	// @Override
	// protected List<StringValuePair<Object>> doInBackground()
	// throws Exception
	// {
	// return bar.getCallback().getLeafs(newPath);
	// }
	//
	// @Override
	// protected void done()
	// {
	// try
	// {
	// FileListModel model = new FileListModel();
	// List<StringValuePair<Object>> leafs = get();
	// for (StringValuePair<Object> leaf : leafs)
	// {
	// FileTreeNode node = (FileTreeNode) leaf
	// .getValue();
	// model.add(node.file);
	// }
	// model.sort();
	// fileList.setModel(model);
	// } catch (Exception exc)
	// {
	// }
	// }
	// };
	// worker.execute();
	// }
	// return;
	//
	// }
	// });
	// }

	File cufl;

	int oldnum = 0;

	@SuppressWarnings("static-access")
	public void updateFilelist()
	{
		fileListScrollPane.setBorder(new TitledBorder(
				getFileName(WiseTemplateManager.getCurrentSelectedFolder())));
		File currentfile = WiseTemplateManager.Instance
				.getCurrentSelectedFolder();
		if (currentfile != cufl && currentfile != null)
		{
			updateFilelist(currentfile);
		} else if (currentfile == cufl)
		{
			File[] filec = getChildren(currentfile);
			int newnum = filec != null ? filec.length : 0;
			if (newnum != oldnum)
			{
				updateFilelist(currentfile);
			}
		}
	}

	public void updateFilelist(File currentfile)
	{
		File[] files = getChildren(currentfile);
		fileList.clear();
		for (int i = 0; i < files.length; i++)
		{
			File current = files[i];
			this.fileList.add(current);
		}
		filepanel.paintComponent(fileList);
		cufl = currentfile;
		oldnum = cufl != null ? getChildren(cufl).length : 0;
	}

	public File[] getChildren(File file)
	{
		File[] filelist = file.listFiles();
		List<File> wenjianlist = new ArrayList<File>();
		if (filelist != null && filelist.length > 0)
		{
			for (int i = 0; i < filelist.length; i++)
			{
				File current = filelist[i];
				if (!current.isDirectory())
				{
					String path = current.getPath();
					if (!path.equals(""))
					{
						String[] names = path.split("\\.");
						int length = names.length;
						if (names[length - 1].equalsIgnoreCase("wsdt"))
						{
							wenjianlist.add(current);
						}
					}
				}
			}
		}
		// Object[] fs= wenjianlist.toArray();

		filelist = (File[]) wenjianlist.toArray(new File[]
		{});
		return filelist;
	}

	public String getFileName(File file)
	{
		String name = "";
		if (file != null)
		{
			String filename = file.getPath();
			String[] nameslist = filename.split("\\\\");
			name = nameslist[nameslist.length - 1];
			name = "分类" + name + "中包含的模板文件";
		}
		return name;
	}
	// /**
	// * Main method for testing.
	// *
	// * @param args
	// * Ignored.
	// */
	// public static void main(String... args)
	// {
	// SwingUtilities.invokeLater(new Runnable()
	// {
	//
	// public void run()
	// {
	// WiseTreeBcb test = new WiseTreeBcb();
	// test.setSize(700, 400);
	// test.setLocation(300, 100);
	// test.setVisible(true);
	//
	// JFrame fr = new JFrame("测试用");
	// fr.setSize(800, 600);
	//
	// fr.add(test);
	// fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// fr.setVisible(true);
	//
	// }
	// });
	// }
}
