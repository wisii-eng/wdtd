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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

import com.wisii.wisedoc.io.TemplateReader;
import com.wisii.wisedoc.view.OpenModelDialog;

@SuppressWarnings("serial")
public class FilePanel extends JPanel
{

	List<FileBrowse> filelabellist = new ArrayList<FileBrowse>();

	List<File> currentlists = new ArrayList<File>();

	int onerownumber = 0;

	public FilePanel()
	{
		super();
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setPreferredSize(new Dimension(500, 600));
		paintComponent(new ArrayList<File>());
		addComponentListener(new ComponentAdapter()
		{

			@Override
			public void componentResized(ComponentEvent e)
			{
				onerownumber = new Double(FilePanel.this.getWidth() / 150)
						.intValue();
			}
		});
		this.setVisible(true);
	}

	public void paintComponent(List<File> fileList)
	{
		filelabellist.clear();
		currentlists = fileList;
		this.removeAll();
		if (!fileList.isEmpty())
		{
			for (int i = 0; i < fileList.size(); i++)
			{
				File current = fileList.get(i);
				if (!current.isDirectory())
				{
					FileBrowse cb = new FileBrowse(current, i);
					this.add(cb);
					filelabellist.add(cb);
				}
			}
		}
		validate();
		repaint();
		onerownumber = new Double(this.getWidth() / 150).intValue();
	}

	public void paintComponent(File currentfile)
	{
		if (!currentlists.isEmpty())
		{
			FileBrowse currentfb = null;
			for (int i = 0; i < filelabellist.size(); i++)
			{
				FileBrowse cb = filelabellist.get(i);
				File current = cb.getCurrentfile();
				if (currentfile.equals(current))
				{
					currentfb = cb;
				} else
				{
					cb.setBackground(Color.WHITE);
				}
			}
			if (currentfb != null)
			{
				currentfb.setBackground(Color.BLUE);
				currentfb.requestFocus();
			}
		}
		validate();
		repaint();
		onerownumber = new Double(this.getWidth() / 150).intValue();
	}

	public class FileBrowse extends JTextField implements MouseListener,
			FocusListener, KeyListener
	{

		File currentfile;

		int currentindex;

		FileNameTextField fn;

		public FileBrowse(File file, int index)
		{
			currentfile = file;
			currentindex = index;
			this.setBorder(new EmptyBorder(2, 2, 2, 2));
			this.setFont(new Font("Tahoma", Font.PLAIN, 11));
			this.setLayout(new BorderLayout());
			this.setPreferredSize(new Dimension(150, 200));
			JPanel image = new JPanel(new FlowLayout(FlowLayout.CENTER));
			image.add(getOneFileComponent(file, index, false, false));
			this.add(image, BorderLayout.CENTER);
			JPanel filename = new JPanel(new FlowLayout(FlowLayout.CENTER));
			fn = new FileNameTextField(getFileName(file));
			filename.add(fn);
			this.add(filename, BorderLayout.SOUTH);
			addMouseListener(this);
			addFocusListener(this);
			addKeyListener(this);
			this.setOpaque(true);
		}

		public String getFileName(File file)
		{
			String name = "";
			if (file != null)
			{
				String filename = file.getPath();
				String[] nameslist = filename.split("\\\\");
				name = nameslist[nameslist.length - 1];
			}
			return name;
		}

		public Component getOneFileComponent(File file, int index,
				boolean isSelected, boolean cellHasFocus)
		{
			JLabel one = new JLabel();
			TemplateReader reader = new TemplateReader();
			Image image = null;
			try
			{
				image = reader.readIcon(file);
				if (image != null)
				{
					// BufferedImage buf = new BufferedImage(
					// image.getWidth(this) + 2,
					// image.getHeight(this) + 2,
					// BufferedImage.TYPE_4BYTE_ABGR);
					// Graphics2D g2d = buf.createGraphics();
					// g2d.drawImage(image, 1, 1, this);
					// g2d.setColor(Color.BLACK);
					// g2d.drawRect(1, 1, 100, 50);
					// g2d.dispose();
					// image = buf;
					ImageIcon icon = new ImageIcon(image.getScaledInstance(150,
							180, Image.SCALE_DEFAULT));
					one.setIcon(icon);
				} else
				{
					one.setIcon(FileSystemView.getFileSystemView()
							.getSystemIcon(file));
				}
	
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			Color back = (index % 2 == 0) ? new Color(250, 250, 250)
					: new Color(240, 240, 240);
			if (isSelected)
			{
				back = new Color(220, 220, 240);
			}
			this.setBackground(back);
			return one;
		}

		@Override
		public void focusGained(FocusEvent e)
		{
			WiseTemplateManager.Instance.updataFileList(currentfile);
		}

		@Override
		public void focusLost(FocusEvent e)
		{
		}

		@Override
		/*
		 * 0x25 左移，0x26 上移，0x27 右移，0x28 下移
		 */
		public void keyPressed(KeyEvent e)
		{
			int value = e.getKeyCode();
			if (value == 0x25)
			{
				if (currentindex > 0)
				{
					resetBorder(currentindex - 1);
				}
			} else if (value == 0x26)
			{
				if (currentindex >= onerownumber)
				{
					resetBorder(currentindex - onerownumber);
				}
			} else if (value == 0x27 || value == 0x9)
			{
				if (currentindex < filelabellist.size() - 1)
				{
					resetBorder(currentindex + 1);
				}
			} else if (value == 0x28)
			{
				if (currentindex < filelabellist.size() - onerownumber)
				{
					resetBorder(currentindex + onerownumber);
				}
			}
			// System.out.println("==" + (value == 0x9));
		}

		@Override
		public void keyReleased(KeyEvent e)
		{
		}

		@Override
		public void keyTyped(KeyEvent e)
		{
		}

		@Override
		public void mouseClicked(MouseEvent e)
		{
			// int clickTimes = e.getClickCount();
			// if (clickTimes == 2)
			// {
			// fn.setEnabled(true);
			// fn.requestFocus();
			// } else
			// {
			WiseTemplateManager.Instance.updataFileList(currentfile);
			ClearBorder(currentindex);
			OpenModelDialog.setFile(currentfile);
			this.requestFocus();
			// }
		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
		}

		public File getCurrentfile()
		{
			return currentfile;
		}

		public void setCurrentfile(File currentfile)
		{
			this.currentfile = currentfile;
		}

		public class FileNameTextField extends JTextField implements
				FocusListener, MouseListener
		{

			String oldname = "";

			public FileNameTextField(String filename)
			{
				super();
				setPreferredSize(new Dimension(150, 20));
				setText(filename);
				oldname = filename;
				setBorder(null);
				setBackground(null);
				// setEnabled(false);
				setHorizontalAlignment(JTextField.CENTER);
				addFocusListener(this);
				addMouseListener(this);
			}

			@Override
			public void focusGained(FocusEvent e)
			{
				this.setBackground(Color.WHITE);
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				String name = this.getText();
				String realname = "";
				if (!name.equals(""))
				{
					if (name.contains("."))
					{
						if (name.endsWith(".wsdt"))
						{
							realname = name;
						} else
						{
							int index = name.indexOf(".");
							realname = name.substring(0, index) + ".wsdt";
						}
					} else
					{
						realname = name + ".wsdt";
					}
				}
				if (!realname.equals(""))
				{
					String filename = currentfile.getPath();
					String[] names = filename.split("\\\\");
					String newname = "";
					for (int i = 0; i < names.length; i++)
					{
						if (i == names.length - 1)
						{
							newname = newname + realname;
						} else
						{
							newname = newname + names[i] + "\\";
						}
					}
					boolean ownflg = true;
					if (currentlists != null)
					{
						for (File current : currentlists)
						{
							if (!current.equals(currentfile)
									&& current.getName().equals(realname))
							{
								ownflg = false;
							}
						}
					}
					File newfile = new File(newname);
					boolean f = currentfile.renameTo(newfile);
					if (!f && !ownflg)
					{
						new DialogError();
						this.setText(oldname);
						this.requestFocus();
					} else
					{
						currentlists.remove(currentfile);
						currentlists.add(newfile);
						currentfile = newfile;
						WiseTemplateManager.Instance.updataFileList(newfile);
						this.setText(realname);
					}
				} else
				{
					this.setText(oldname);
					this.requestFocus();
				}
				this.setBackground(null);
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				// int clickTimes = e.getClickCount();
				// if (clickTimes == 2)
				// {
				// fn.setEnabled(true);
				// fn.requestFocus();
				// } else
				// {
				WiseTemplateManager.Instance.updataFileList(currentfile);
				ClearBorder(currentindex);
				OpenModelDialog.setFile(currentfile);
				this.setBackground(Color.WHITE);
				this.requestFocus();
				// this.requestFocus();
				// }
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
			}

			@Override
			public void mousePressed(MouseEvent e)
			{
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
			}

		}
	}

	public void ClearBorder(int index)
	{

		for (int i = 0; i < filelabellist.size(); i++)
		{
			FileBrowse current = filelabellist.get(i);
			if (i == index)
			{
				current.setBackground(Color.BLUE);
				current.fn.requestFocus();
			} else
			{
				current.setBackground(Color.WHITE);
			}
		}
		validate();
		repaint();
		OpenModelDialog.setFile(currentlists.get(index));
	}

	public void resetBorder(int index)
	{

		for (int i = 0; i < filelabellist.size(); i++)
		{
			FileBrowse current = filelabellist.get(i);
			if (i == index)
			{
				current.setBackground(Color.BLUE);
				current.requestFocus();
			} else
			{
				current.setBackground(Color.WHITE);
			}
		}
		validate();
		repaint();
		OpenModelDialog.setFile(currentlists.get(index));
	}

}
