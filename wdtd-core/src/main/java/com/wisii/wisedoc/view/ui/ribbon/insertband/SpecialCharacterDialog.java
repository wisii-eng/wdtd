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
 * @SpecialCharacterDialog.java
 * 汇智互联版权所有，未经许可，不得使用
 */

package com.wisii.wisedoc.view.ui.ribbon.insertband;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.CellElement;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.document.DocumentPosition;
import com.wisii.wisedoc.document.Text;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.view.AbstractEditComponent;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;
import com.wisii.wisedoc.view.select.DocumentPositionRange;
import com.wisii.wisedoc.view.ui.text.Messages;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 类功能描述：
 * 
 * 作者：zhangqiang 创建日期：2009-8-11
 */
public class SpecialCharacterDialog extends AbstractWisedocDialog
{
	public SpecialCharacterDialog()
	{
		super(SystemManager.getMainframe(), UiText.SPECIALCHARACTER_TITLE,
				true);
		setSize(300, 360);
		add(new SpecialCharacterPanel());
		Rectangle bound = SystemManager.getMainframe().getBounds();
		setLocation(bound.x+ bound.width/2-150, bound.y+ bound.height/2-180);
		show();
	}

	public final class SpecialCharacterPanel extends JPanel
	{
		private String TITLEPREFIX = "wsd.view.gui.ribbon.insert.chars.";
		JTabbedPane tabpane = new JTabbedPane();

		SpecialCharacterPanel()
		{
			super(new BorderLayout());
			List<String> charslist = new SpecialCharParser()
					.getSpecialCharMaps();
			setSize(300, 340);
			if (charslist != null && !charslist.isEmpty()
					&& charslist.size() % 2 == 0)
			{
				int size = charslist.size();
				for (int i = 0; i < size; i = i + 2)
				{
					String titlekey = charslist.get(i);
					String chars = charslist.get(i + 1);
					String title = Messages.getString(TITLEPREFIX + titlekey);
					CharsTable table = new CharsTable(chars);
					tabpane.addTab(title, table);
				}
			}
			add(tabpane, BorderLayout.CENTER);
			add(new ButtonPanel(), BorderLayout.SOUTH);

		}

		Character getSelectedChar()
		{
			CharsTable table = (CharsTable) tabpane.getSelectedComponent();
			if (table != null)
			{
				int row = table.getSelectedRow();
				int column = table.getSelectedColumn();
				return (Character) table.getValueAt(row, column);
			}
			return null;
		}

		private class CharsTable extends JTable
		{
			CharsTable(String chars)
			{
				super(10, 10);
				setAutoResizeMode(AUTO_RESIZE_OFF);
				setRowSelectionAllowed(false);
				int len = chars.length();
				if (len > 100)
				{
					chars = chars.substring(0, 100);
					len = 100;
				}
				for (int i = 0; i < len; i++)
				{
					setValueAt(chars.charAt(i), i / 10, i % 10);
				}
			}

			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}

		}

		class ButtonPanel extends JPanel
		{

			/**
			 * Create the panel
			 */
			public ButtonPanel()
			{
				super(new FlowLayout(FlowLayout.TRAILING));

				final ButtonAction ba = new ButtonAction();
				final JButton yesButton = new JButton(UiText.DIALOG_OK);
				final JButton cancelButton = new JButton(UiText.DIALOG_CANCEL);
				yesButton.addActionListener(ba);
				cancelButton.addActionListener(ba);
				/* 添加ESC、ENTER健处理 by 李晓光 2009-5-14 */
				setOkButton(yesButton);
				setCancelButton(cancelButton);

				add(yesButton);
				add(cancelButton);
			}
		}

		class ButtonAction extends AbstractAction
		{

			public ButtonAction()
			{
			}

			@Override
			public void actionPerformed(ActionEvent e)
			{
				final String cmd = e.getActionCommand();

				if (cmd.equals(UiText.DIALOG_OK))
				{
					Character spechar = getSelectedChar();
					if (spechar != null)
					{
						Document doc = SystemManager.getCurruentDocument();
						AbstractEditComponent editor = SystemManager
								.getMainframe().getEidtComponent();
						final DocumentPositionRange range = editor
								.getSelectionModel().getSelectionCell();
						if (range != null)
						{
							final List<CellElement> elemt = new ArrayList<CellElement>();
							final DocumentPosition pos = range
									.getStartPosition();
							final Map<Integer, Object> attsmap = pos
									.getInlineAttriute();
							elemt
									.add(new TextInline(new Text(spechar),
											attsmap));
							doc.replaceElements(range, elemt);
						} else
						{
							DocumentPosition pos = editor.getCaretPosition();
							if (pos != null)
							{
								doc.insertString(spechar.toString(), pos, pos
										.getInlineAttriute());
								
							}
						}
						SpecialCharacterDialog.this.dispose();
					}
				} else if (cmd.equals(UiText.DIALOG_CANCEL))
				{
					SpecialCharacterDialog.this.dispose();
				} else if (cmd.equals(UiText.DIALOG_HELP))
				{
					JOptionPane.showMessageDialog(null, "别着急，快轮到做帮助菜单了",
							"帮助菜单", JOptionPane.INFORMATION_MESSAGE);
					// borderDialog.dispose();
				}
			}

		}
	}
}
