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
package com.wisii.wisedoc.view.ui.ribbon.edit;
import static com.wisii.wisedoc.resource.MessageResource.getMessage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.undo.UndoManager;

import com.wisii.wisedoc.resource.MediaResource;
import com.wisii.wisedoc.resource.MessageConstants;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.dialog.AbstractWisedocDialog;

public class FormulaEditorDialog extends AbstractWisedocDialog {

	private JPanel contentPane;
	private JTextPane textPane;
	private FormulaExpression oldValue;
	private FormulaExpression newValue;
	private JList varList;
	private UndoManager undo = new UndoManager();
	final FormulaContentDocment doc;
	private JButton okbutton = new JButton(getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.OK));

	private JButton cancelbutton = new JButton(getMessage(MessageConstants.DIALOG_COMMON + MessageConstants.CANCEL));
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormulaEditorDialog frame = new FormulaEditorDialog(null,null,null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FormulaEditorDialog(AbstractWisedocDialog parent, FormulaExpression formula,FormulaVar[] para) {
		setSize(800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel buttonpanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		buttonpanel.add(okbutton);
		buttonpanel.add(cancelbutton);
		contentPane.add(buttonpanel, BorderLayout.SOUTH);
		
		JPanel barPanel = new JPanel();
		barPanel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 1, true), "\u8FD0\u7B97\u7B26", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		FlowLayout flowLayout = (FlowLayout) barPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(barPanel, BorderLayout.NORTH);
		
		JButton plus = new JButton(" + ");
		plus.setFont(new Font("宋体", Font.PLAIN, 22));
		plus.setBorder(BorderFactory.createEmptyBorder());
		plus.setSize(35, 35);
		plus.addMouseListener(new OperatorListener());
		barPanel.add(plus);
		
		JButton minus = new JButton(" - ");
		minus.setFont(new Font("宋体", Font.PLAIN, 22));
		minus.setBorder(BorderFactory.createEmptyBorder());
		minus.addMouseListener(new OperatorListener());
		minus.setSize(35, 35);
		barPanel.add(minus);
		
		JButton multiply = new JButton(" × ");
		multiply.setFont(new Font("宋体", Font.PLAIN, 22));
		multiply.setBorder(BorderFactory.createEmptyBorder());
		multiply.addMouseListener(new OperatorListener());
		multiply.setSize(35, 35);
		barPanel.add(multiply);
		
		JButton divide = new JButton(" ÷ ");
		divide.setFont(new Font("宋体", Font.PLAIN, 22));
		divide.setBorder(BorderFactory.createEmptyBorder());
		divide.addMouseListener(new OperatorListener());;
		divide.setSize(35, 35);
		barPanel.add(divide);
		
		JButton sqrt2 = new JButton("2√");
		sqrt2.setFont(new Font("宋体", Font.PLAIN, 0));
		divide.setBorder(BorderFactory.createEmptyBorder());
		sqrt2.setIcon(new ImageIcon(MediaResource.getImage("sqrt2.gif")));
		sqrt2.addMouseListener(new OperatorListener());
		sqrt2.setSize(35, 35);
		barPanel.add(sqrt2);
		
		JButton sqrt3 = new JButton("3√");
		sqrt3.setFont(new Font("宋体", Font.PLAIN, 0));
		divide.setBorder(BorderFactory.createEmptyBorder());
		sqrt3.setIcon(new ImageIcon(MediaResource.getImage("sqrt3.gif")));
		sqrt3.addMouseListener(new OperatorListener());
		sqrt3.setSize(35, 35);
		barPanel.add(sqrt3);
		
		JButton ln = new JButton(" ㏑ ");
		ln.setFont(new Font("宋体", Font.ITALIC, 22));
		ln.setBorder(BorderFactory.createEmptyBorder());
		ln.addMouseListener(new OperatorListener());
		ln.setSize(35, 35);
		barPanel.add(ln);
		
		JButton log = new JButton(" ㏒ ");
		log.setFont(new Font("宋体", Font.ITALIC, 22));
		log.setBorder(BorderFactory.createEmptyBorder());
		log.addMouseListener(new OperatorListener());
		log.setSize(35, 35);
		barPanel.add(log);
		
		JButton sin = new JButton(" sin ");
		sin.setFont(new Font("宋体", Font.ITALIC, 22));
		sin.setBorder(BorderFactory.createEmptyBorder());
		sin.addMouseListener(new OperatorListener());
		sin.setSize(35, 35);
		barPanel.add(sin);
		
		JButton cos = new JButton(" cos ");
		cos.setFont(new Font("宋体", Font.ITALIC, 22));
		cos.setBorder(BorderFactory.createEmptyBorder());
		cos.addMouseListener(new OperatorListener());
		cos.setSize(35, 35);
		barPanel.add(cos);
		
		JButton leftBracket = new JButton(" ( ");
		leftBracket.setFont(new Font("宋体", Font.ITALIC, 22));
		leftBracket.setBorder(BorderFactory.createEmptyBorder());
		leftBracket.addMouseListener(new OperatorListener());
		leftBracket.setSize(35, 35);
		barPanel.add(leftBracket);
		
		JButton rightBracket = new JButton(" ) ");
		rightBracket.setFont(new Font("宋体", Font.ITALIC, 22));
		rightBracket.setBorder(BorderFactory.createEmptyBorder());
		rightBracket.addMouseListener(new OperatorListener());
		rightBracket.setSize(35, 35);
		barPanel.add(rightBracket);
		
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		varList = new JList(para);
		varList.setPrototypeCellValue("wwwwwwwwwwwwww");//初始化splitPane左侧宽度
		varList.addMouseListener(new VarListSelectListener());
		scrollPane.setViewportView(varList);

		oldValue = formula;
		List contents = null;
		if (oldValue != null)
		{
			contents = oldValue.getContents();
		}
		doc = new FormulaContentDocment(contents);
		textPane = new JTextPane();
		textPane.setDocument(doc);
		textPane.getDocument().addUndoableEditListener(new UndoableEditListener() {
			
			@Override
			public void undoableEditHappened(UndoableEditEvent e) {
				undo.addEdit(e.getEdit());
			}
		});
		KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_MASK);
		ActionMap am = textPane.getActionMap();
		Action act = new AbstractAction() {
			public void actionPerformed(final ActionEvent e) {
				if(undo.canUndo()){
					undo.undo();
				}
			}
		};
		am.put("undo", act);
		textPane.getInputMap().put(ks, "undo");
		
		KeyStroke ks2 = KeyStroke.getKeyStroke(KeyEvent.VK_Y,InputEvent.CTRL_MASK);
		ActionMap am2 = textPane.getActionMap();
		Action act2 = new AbstractAction() {
			public void actionPerformed(final ActionEvent e) {
				if(undo.canRedo()){
					undo.redo();
				}
			}
		};
		am2.put("redo", act2);
		textPane.getInputMap().put(ks2, "redo");
		
		KeyStroke ks3 = KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_MASK);
		ActionMap am3 = textPane.getActionMap();
		Action act3 = new AbstractAction() {
			public void actionPerformed(final ActionEvent e) {
				int caretPosition = textPane.getCaretPosition();
				doc.insert(paste(),caretPosition);
			}
		};
		am3.put("paste", act3);
		textPane.getInputMap().put(ks3, "paste");
		
		textPane.addInputMethodListener(new InputMethodListener()
		{
			@Override
			public void inputMethodTextChanged(InputMethodEvent event)
			{
				if(event.getCommittedCharacterCount()<1){//不加上该代码，打包之后在谷歌输入法中输入中文时会把附近的动态节点清掉
				event.consume();
				}
			}
			@Override
			public void caretPositionChanged(InputMethodEvent event)
			{
				event.consume();
			}
		});
		textPane.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
//				int c = textPane.getCaretPosition();
//				AttributeSet attribute = doc.getCharacterElement(c).getAttributes();
//				Object node = attribute.getAttribute(FormulaContentDocment.VARATT);
//				if(node instanceof FormulaVar){
//					FormulaVar var = (FormulaVar)node;
//					String varName = var.getVarName();
//					int len = varName.length()+3;
//					int n = 0;
//					int m = 0;
//					for(int i = c;i>0;i--){
//						AttributeSet attribute2 = doc.getCharacterElement(i).getAttributes();
//						Object nnode = attribute2.getAttribute(FormulaContentDocment.VARATT);
//						m = i;
//						if(!node.equals(nnode)){
//							break;
//						}
//					}
//					for(int i = c;i<len+c;i++){
//						AttributeSet attribute2 = doc.getCharacterElement(i).getAttributes();
//						Object nnode = attribute2.getAttribute(FormulaContentDocment.VARATT);
//						n = i;
//						if(!node.equals(nnode)){
//							break;
//						}
//					}
//					textPane.select(m+1, n);
//				}
			}
		});
		
		textPane.addCaretListener(new MyCaretListener());
		initAction();
	
	
		textPane.setFont(new Font("Serif", Font.ITALIC, 28));
		splitPane.setRightComponent(textPane);
		
	}
	private void initAction()
	{
		okbutton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String text = textPane.getText();
				newValue = new FormulaExpression(dealValue(text));
				result = DialogResult.OK;
				FormulaEditorDialog.this.dispose();
			}
		});
		cancelbutton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				result = DialogResult.Cancel;
				FormulaEditorDialog.this.dispose();
			}
		});
	}
	
	private List paste()
	   {
	      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	      DataFlavor flavor = DataFlavor.stringFlavor;
	      if (clipboard.isDataFlavorAvailable(flavor))
	      {
	         try
	         {
	            String text = (String) clipboard.getData(flavor);
	            List dealValue = dealValue(text);
	            return dealValue;
	         }
	         catch (UnsupportedFlavorException e)
	         {
	            JOptionPane.showMessageDialog(this, e);
	         }
	         catch (IOException e)
	         {
	            JOptionPane.showMessageDialog(this, e);
	         }
	      }
		return null;
	   }
	
	private List dealValue(String value) {
		String regEx = "([#][{][a-zA-Z_$][a-zA-Z0-9_$]*[}])";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(value);
		String[] ss = value.split(regEx);
		List content = new ArrayList();
		int i = 0;
		while (mat.find()) {
//			System.out.print(ss[i++]);
			content.add(ss[i++]);
//			System.out.print(mat.group(1));
			content.add(new FormulaVar(mat.group(1)));
		}
		try {
			content.add(ss[i]);
		} catch (Exception e) {
		}
//		System.out.println(ss[i]);
		return content;
		
	}
	private class MyCaretListener implements CaretListener{

		@Override
		public void caretUpdate(CaretEvent e)
		{
			int mark = e.getMark();
			int offs=e.getDot();
			if(offs ==0 || offs != mark){//不加上该代码，删除或拖拽选取变量时会出现堆栈溢出。
				return;
			}
			AttributeSet attribute = doc.getCharacterElement(offs).getAttributes();
			int newoffs = offs;
			int newoffsend = offs;
			if (attribute != null)
			{
				Object node = attribute.getAttribute(FormulaContentDocment.VARATT);
				if (node != null)
				{
					for (int i = offs - 1; i >= 0; i--)
					{
						AttributeSet nattribute = doc.getCharacterElement(i)
								.getAttributes();
						if (nattribute == null)
						{
							break;
						}
						Object nnode = nattribute.getAttribute(FormulaContentDocment.VARATT);
						if (!node.equals(nnode))
						{
							break;
						}
						newoffs = i;
					}
					for (int i = offs; i <doc.getLength() ; i++)
					{
						AttributeSet nattribute = doc.getCharacterElement(i)
								.getAttributes();
						if (nattribute == null)
						{
							break;
						}
						Object nnode = nattribute.getAttribute(FormulaContentDocment.VARATT);
						if (!node.equals(nnode))
						{
							break;
						}
						newoffsend = i+1;
					}
					if(newoffsend-offs>offs-newoffs)
					{
						offs=newoffs;
					}
					else{
						offs=newoffsend;
					}
					textPane.setCaretPosition(offs);
				}
			}
			
		}
		
	}
	private class VarListSelectListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent mouseevent)
		{
			mouseevent.consume();
			if (SwingUtilities.isLeftMouseButton(mouseevent)
					&& mouseevent.getClickCount() == 2)
			{
				FormulaVar varValue = (FormulaVar) varList.getSelectedValue();
				if(varValue != null){
					int start = textPane.getSelectionStart();
					int end = textPane.getSelectionEnd();
					((FormulaContentDocment) textPane.getDocument())
							.insert(varValue, start, end - start);
					textPane.setCaretPosition(end
							+ varValue.toString().length());
					textPane.requestFocus();
				}
			}
		}
	}
	private class OperatorListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent mouseevent) {
			mouseevent.consume();

			Object source = mouseevent.getSource();
			JButton btn = (JButton) source;
			String text = btn.getText().trim();
			if ("+".equals(text)) {
				dealOper("+");
			} else if ("-".equals(text)) {
				dealOper("-");
			} else if ("×".equals(text)) {
				dealOper("*");
			} else if ("÷".equals(text)) {
				dealOper("/");
			} else if ("2√".equals(text)) {
				dealOper("sqrt()");
			} else if ("3√".equals(text)) {
				dealOper("pow(,1/3)");
			} else if ("㏑".equals(text)) {
				dealOper("exp()");
			} else if ("㏒".equals(text)) {
				dealOper("log()");
			} else if ("sin".equals(text)) {
				dealOper("sin()");
			} else if ("cos".equals(text)) {
				dealOper("cos()");
			}else if ("(".equals(text)) {
				dealOper("(");
			}else if (")".equals(text)) {
				dealOper(")");
			}
		}

		private void dealOper(String oper) {
			int start = textPane.getSelectionStart();
			int end = textPane.getSelectionEnd();
			((FormulaContentDocment) textPane.getDocument()).insert(oper, start, end - start);
			textPane.setCaretPosition(end + oper.length());
			textPane.requestFocus();
		}
	}
	public FormulaExpression getNewValue() {
		return newValue;
	}

	public void setNewValue(FormulaExpression newValue) {
		this.newValue = newValue;
	}
	

}