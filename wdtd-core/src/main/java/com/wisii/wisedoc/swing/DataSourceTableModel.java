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
 * @DataSourceTableModel.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.swing;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import com.wisii.wisedoc.banding.AttributeBindNode;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.banding.DefaultBindNode;
import com.wisii.wisedoc.banding.io.DataStructureTreeModel;
import com.wisii.wisedoc.banding.io.XMLReader;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.attribute.transform.FileSource;
import com.wisii.wisedoc.log.LogUtil;
import com.wisii.wisedoc.view.ui.text.RibbonUIText;

/**
 * 类功能描述：外部文件作为数据源表模型类
 * 
 * 作者：zhangqiang 创建日期：2009-8-29
 */
public class DataSourceTableModel extends AbstractTableModel
{
	private List<FileItem> fileitems = new ArrayList<FileItem>();

	public DataSourceTableModel(List<FileSource> filesources)
	{
		if (filesources != null)
		{
			this.fileitems.addAll(creatColumnItems(filesources));
		}
	}

	private List<FileItem> creatColumnItems(List<FileSource> filesources)
	{
		List<FileItem> cts = new ArrayList<FileItem>();
		for (FileSource filesource : filesources)
		{
			cts.add(new FileItem(filesource));
		}
		return cts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount()
	{
		// TODO Auto-generated method stub
		return 4;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount()
	{
		// TODO Auto-generated method stub
		return fileitems.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		FileItem fileitem = fileitems.get(rowIndex);
		if (columnIndex == 0)
		{
			return fileitem.getFile();
		} else if (columnIndex == 1)
		{
			return fileitem.getStructure();
		} else if (columnIndex == 2)
		{
			return fileitem.getRoot();
		} else
		{
			return fileitem.getColumninfos();
		}
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex)
	{
		FileItem fileitem = fileitems.get(rowIndex);
		if (columnIndex == 0)
		{
			String file = (String) value;
			if (file != null)
			{
				file = file.trim();
			}
			if (file == null || file.isEmpty())
			{
				return;
			} else
			{
				if (file.equals(fileitem.getFile()))
				{
					return;
				}
				try
				{
					DataStructureTreeModel datamodel = new XMLReader()
							.readStructure(file);
					fileitem.setFile(file);
					BindNode root = (BindNode) datamodel.getRoot();
					fileitem.setRoot(root);
					int mapcount = root.getChildCount();
					if(mapcount>0)
					{
						// 如果跟节点下第一个子节点
						BindNode mapnode = root.getChildAt(0);
						int childcount = mapnode.getChildCount();
						if (childcount > 0)
						{
							BindNode firstchild = mapnode.getChildAt(0);

							// 如果是属性节点，则设置类型为TABLE2（属性形）型，否则设置为Table1型（节点型）
							if (firstchild instanceof AttributeBindNode)
							{
								fileitem.setStructure(Constants.EN_TABLE2);
							} else
							{
								fileitem.setStructure(Constants.EN_TABLE1);
							}
						}
						else
						{
							return;
						}
					}
					else
					{
						return;
					}
					fileitem.setColumninfo(createColumnItems(root, fileitem
							.getStructure()));
				} catch (Exception e)
				{
					e.printStackTrace();
					LogUtil.debugException("导入的数据文件不可读或结构有错", e);
				}
			}
		} else if (columnIndex == 1)
		{
			int structure = (Integer) value;
			BindNode root = fileitem.getRoot();
			List<ColumnItem> items = createColumnItems(root, structure);
			if (items != null)
			{
				fileitem.setStructure(structure);
				fileitem.setColumninfo(items);
			}
		} else if (columnIndex == 2)
		{
			BindNode root = (BindNode) value;
			if (root == null)
			{
				fileitem.setRoot(root);
			} else
			{
				int mapcount = root.getChildCount();
				if(mapcount>0)
				{
					// 如果跟节点下第一个子节点
					BindNode mapnode = root.getChildAt(0);
					int childcount = mapnode.getChildCount();
					if (childcount > 0)
					{
						BindNode firstchild = mapnode.getChildAt(0);

						// 如果是属性节点，则设置类型为TABLE2（属性形）型，否则设置为Table1型（节点型）
						if (firstchild instanceof AttributeBindNode)
						{
							fileitem.setStructure(Constants.EN_TABLE2);
						} else
						{
							fileitem.setStructure(Constants.EN_TABLE1);
						}
					}
					else
					{
						return;
					}
				}
				else
				{
					return;
				}
				List<ColumnItem> items = createColumnItems(root, fileitem
						.getStructure());
				if (items != null)
				{
					fileitem.setRoot(root);
					fileitem.setColumninfo(items);
				}
			}
		} else
		{
			fileitem.setColumninfo((List<ColumnItem>) value);
		}
	}

	private BindNode findRootNodeOfNodename(BindNode root, String nodepath)
	{
		if (root != null)
		{
			while (root != null)
			{
				if (root.getXPath().equals(nodepath))
				{
					return root;
				}
				if (root.getChildCount() == 0)
				{
					return null;
				}
				root = root.getChildAt(0);
			}
		}
		return null;
	}

	private List<ColumnItem> createColumnItems(BindNode node, int structure)
	{
		if (node != null && node.getChildCount() > 0)
		{
			BindNode mapnode = node.getChildAt(0);
			if (structure == Constants.EN_TABLE1)
			{
				List<ColumnItem> cis = new ArrayList<ColumnItem>();
				int size = mapnode.getChildCount();
				for (int i = 0; i < size; i++)
				{
					BindNode childnode = mapnode.getChildAt(i);
					if ((childnode instanceof DefaultBindNode)
							&& !(childnode instanceof AttributeBindNode))
					{
						String columnname = ((DefaultBindNode) childnode)
								.getNodeName();
						cis.add(new ColumnItem(columnname, false));
					}
				}
				if (cis.isEmpty())
				{
					cis = null;
				}
				return cis;
			} else if (structure == Constants.EN_TABLE2)
			{
				List<ColumnItem> cis = new ArrayList<ColumnItem>();
				int size = mapnode.getChildCount();
				for (int i = 0; i < size; i++)
				{
					BindNode childnode = mapnode.getChildAt(i);
					if (childnode instanceof AttributeBindNode)
					{
						String columnname = ((AttributeBindNode) childnode)
								.getNodeName();
						cis.add(new ColumnItem(columnname, false));
					}
				}
				if (cis.isEmpty())
				{
					cis = null;
				}
				return cis;
			} else
			{
				return null;
			}
		}
		return null;
	}

	@Override
	public String getColumnName(int column)
	{
		if (column == 0)
		{
			return RibbonUIText.EDIT_DATASOURCE_FILESOURCE_FILE;
		} else if (column == 1)
		{
			return RibbonUIText.EDIT_DATASOURCE_FILESOURCE_FILE_STRUCTURE;
		} else if (column == 2)
		{
			return RibbonUIText.EDIT_DATASOURCE_FILESOURCE_FILE_ROOT;
		} else
		{
			return RibbonUIText.EDIT_DATASOURCE_FILESOURCE_FILE_COLUMN;
		}
	}

	/**
	 * 
	 * {方法的功能/动作描述}:
	 * 
	 * @param 引入参数
	 *            : {引入参数说明}
	 * @return List<FileSource>: {返回参数说明}
	 * @exception 说明在某情况下
	 *                ,将发生什么异常}
	 */
	public List<FileSource> getFileSources()
	{
		if (fileitems != null && !fileitems.isEmpty())
		{
			List<FileSource> fses = new ArrayList<FileSource>();
			for (FileItem fileitem : fileitems)
			{
				FileSource fs = getFileSource(fileitem);
				if (fs != null)
				{
					fses.add(fs);
				}
			}
			return fses;
		}
		return null;
	}
	public boolean isAllSetOk()
	{
		if (fileitems != null && !fileitems.isEmpty())
		{
			List<FileSource> fses = new ArrayList<FileSource>();
			for (FileItem fileitem : fileitems)
			{
				if (fileitem == null)
				{
					return false;
				}
				String file = fileitem.getFile();
				if (file == null)
				{
					return false;
				}
				BindNode root = fileitem.getRoot();
				if (root == null)
				{
					return false;
				}
				int structure = fileitem.getStructure();
				List<ColumnItem> cis = fileitem.getColumninfos();
				if (cis == null || cis.isEmpty())
				{
					return false;
				}
				List<String> columnnames = new ArrayList<String>();
				List<Integer> valuenumbers = new ArrayList<Integer>();
				int size = cis.size();
				for (int i = 0; i < size; i++)
				{
					ColumnItem columnitem = cis.get(i);
					if (columnitem != null)
					{
						columnnames.add(columnitem.getColumnname());
						if (columnitem.isKey())
						{
							valuenumbers.add(i);
						}
					}
				}
				if (columnnames.isEmpty())
				{
					return false;
				}

			}
			return true;
		} else
		{
			return true;
		}
	}

	private FileSource getFileSource(FileItem fileitem)
	{
		if (fileitem == null)
		{
			return null;
		}
		String file = fileitem.getFile();
		if (file == null)
		{
			return null;
		}
		BindNode root = fileitem.getRoot();
		if (root == null)
		{
			return null;
		}
		int structure = fileitem.getStructure();
		List<ColumnItem> cis = fileitem.getColumninfos();
		if (cis == null || cis.isEmpty())
		{
			return null;
		}
		List<String> columnnames = new ArrayList<String>();
		List<Integer> valuenumbers = new ArrayList<Integer>();
		int size = cis.size();
		for (int i = 0; i < size; i++)
		{
			ColumnItem columnitem = cis.get(i);
			if (columnitem != null)
			{
				columnnames.add(columnitem.getColumnname());
				if (columnitem.isKey())
				{
					valuenumbers.add(i);
				}
			}
		}
		if (columnnames.isEmpty())
		{
			return null;
		}
		if (valuenumbers.isEmpty())
		{
			valuenumbers = null;
		}
		return new FileSource(file, structure, root.getXPath(), columnnames,
				valuenumbers);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		if (columnIndex == 0 || columnIndex == 1)
		{
			return true;
		}
		FileItem item = fileitems.get(rowIndex);
		if (columnIndex == 2)
		{
			if (item.getRoot() != null)
			{
				return true;
			} else
			{
				return false;
			}
		} else
		{
			if (item.getColumninfos() != null)
			{
				return true;
			} else
			{
				return false;
			}
		}
	}

	private class FileItem
	{
		// 文件路径
		private String file;
		// 类型，table1，table2
		private int structure = Constants.EN_TABLE1;
		// 根节点
		private BindNode root;
		// 列顺序信息
		private List<ColumnItem> columninfos;

		FileItem(FileSource source)
		{
			this.file = source.getFile();
			this.structure = source.getStructure();
			this.root = createRootnode(source.getRoot());
			this.columninfos = createColumnInfos(source);

		}

		FileItem()
		{

		}

		private BindNode createRootnode(String nodepath)
		{
			if (nodepath == null)
			{
				return null;
			}
			DataStructureTreeModel datamodel;
			try
			{
				datamodel = new XMLReader().readStructure(
						file);
				if (datamodel == null)
				{
					return null;
				}
				BindNode root = (BindNode) datamodel.getRoot();
				return findRootNodeOfNodename(root, nodepath);
			} catch (Exception e)
			{
				LogUtil.debug("", e);
				return null;
			}

		}

		private List<ColumnItem> createColumnInfos(FileSource source)
		{
			List<String> columnnames = source.getColumninfo();
			List<Integer> valuenumbers = source.getValuenumber();
			if (columnnames != null && !columnnames.isEmpty())
			{
				List<ColumnItem> cts = new ArrayList<ColumnItem>();
				int size = columnnames.size();
				for (int i = 0; i < size; i++)
				{
					String columnname = columnnames.get(i);
					boolean iskey = valuenumbers != null
							&& valuenumbers.contains(i);
					cts.add(new ColumnItem(columnname, iskey));
				}
				return cts;
			}
			return null;
		}

		/**
		 * @返回 file变量的值
		 */
		public final String getFile()
		{
			return file;
		}

		/**
		 * @param file
		 *            设置file成员变量的值
		 * 
		 *            值约束说明
		 */
		public final void setFile(String file)
		{
			this.file = file;
		}

		/**
		 * @返回 structure变量的值
		 */
		public final int getStructure()
		{
			return structure;
		}

		/**
		 * @param structure
		 *            设置structure成员变量的值
		 * 
		 *            值约束说明
		 */
		public final void setStructure(int structure)
		{
			this.structure = structure;
		}

		/**
		 * @返回 root变量的值
		 */
		public final BindNode getRoot()
		{
			return root;
		}

		/**
		 * @param root
		 *            设置root成员变量的值
		 * 
		 *            值约束说明
		 */
		public final void setRoot(BindNode root)
		{
			this.root = root;
		}

		/**
		 * @返回 columninfo变量的值
		 */
		public final List<ColumnItem> getColumninfos()
		{
			return columninfos;
		}

		/**
		 * @param columninfo
		 *            设置columninfo成员变量的值
		 * 
		 *            值约束说明
		 */
		public final void setColumninfo(List<ColumnItem> columninfos)
		{
			this.columninfos = columninfos;
		}

	}

	public static final class ColumnItem
	{
		private String columnname;
		private boolean iskey;

		public ColumnItem(String columnname, boolean iskey)
		{
			super();
			this.columnname = columnname;
			this.iskey = iskey;
		}

		/**
		 * @返回 columnname变量的值
		 */
		public final String getColumnname()
		{
			return columnname;
		}

		/**
		 * @param columnname
		 *            设置columnname成员变量的值
		 * 
		 *            值约束说明
		 */
		public final void setColumnname(String columnname)
		{
			this.columnname = columnname;
		}

		/**
		 * @返回 iskey变量的值
		 */
		public final boolean isKey()
		{
			return iskey;
		}

		/**
		 * @param iskey
		 *            设置iskey成员变量的值
		 * 
		 *            值约束说明
		 */
		public final void setIskey(boolean iskey)
		{
			this.iskey = iskey;
		}

		@Override
		public String toString()
		{
			if (iskey)
			{
				return "[" + columnname + "]";
			} else
			{
				return columnname;
			}
		}
	}

	public void addRow(int rowindex)
	{
		if (rowindex >= fileitems.size() || rowindex < 0)
		{
			fileitems.add(new FileItem());
		} else
		{
			fileitems.add(rowindex, new FileItem());
		}
	}

	public void delRow(int rowindex)
	{
		if (rowindex >= fileitems.size() || rowindex < 0)
		{
			return;
		} else
		{
			fileitems.remove(rowindex);
		}
	}
}
