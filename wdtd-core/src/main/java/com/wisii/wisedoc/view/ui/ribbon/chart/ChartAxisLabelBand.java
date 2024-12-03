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
 * @ChartAxisLabelBand.java
 * 汇智互联版权所有，未经许可，不得使用
 */
package com.wisii.wisedoc.view.ui.ribbon.chart;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonComponent;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.banding.BindNode;
import com.wisii.wisedoc.document.BarCodeText;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.Document;
import com.wisii.wisedoc.swing.DataStructureCellRender;
import com.wisii.wisedoc.swing.WiseDocTree;
import com.wisii.wisedoc.util.WiseDocConstants.DialogResult;
import com.wisii.wisedoc.view.ui.actions.Actions;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.ActionType;
import com.wisii.wisedoc.view.ui.manager.ActionFactory.WisedocChart;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.svg.transcoded.format_justify_left;
import com.wisii.wisedoc.view.ui.text.UiText;

/**
 * 类功能描述：用于设置坐标系标签【值坐标系标签、域坐标系标签】。
 * 
 * 
 * 作者：李晓光 创建日期：2009-5-20
 */
public class ChartAxisLabelBand {
	private final static String LABEL = UiText.AXIS_LABEL_BAND_TITLE;//MessageResource.getMessage("wisedoc.chart.axis.label");
	private final static String VALUE_LABEL = UiText.AXIS_LABEL_BAND_VALUE_LABEL;//MessageResource.getMessage("wisedoc.chart.axis.label.value");
	private final static String SERIES_LABEL = UiText.AXIS_LABEL_BAND_SERIES_LABEL;//MessageResource.getMessage("wisedoc.chart.axis.label.series");
	public JRibbonBand getBand() {
		final Actions lis = createListener();
		final JRibbonBand band = new JRibbonBand(LABEL, new format_justify_left(), lis);
		final JPanel top = new JPanel(new BorderLayout());
		final JLabel labValue = new JLabel(VALUE_LABEL);

		/*final JTextField txtValue = new JTextField(15);*/
		/*final JButton btnFontVlaue = new JButton("↓");*/
		btnFontVlaue.setMargin(new Insets(0, 2, 0, 2));
		txtValue.setLayout(new BorderLayout());
		txtValue.add(btnFontVlaue, BorderLayout.EAST);
		btnFontVlaue.addActionListener(lis);		
		btnFontVlaue.setCursor(Cursor.getDefaultCursor());
		top.add(labValue, BorderLayout.WEST);
		top.add(txtValue, BorderLayout.CENTER);

		final JPanel bottom = new JPanel(new BorderLayout());
		final JLabel labSeries = new JLabel(SERIES_LABEL);
		/*final JTextField txtSeries = new JTextField(15);*/
		/*final JButton btnFontSeries = new JButton("↓");*/
		btnFontSeries.setMargin(new Insets(0, 2, 0, 2));
		txtSeries.setLayout(new BorderLayout());
		txtSeries.add(btnFontSeries, BorderLayout.EAST);
		btnFontSeries.addActionListener(lis);		
		btnFontSeries.setCursor(Cursor.getDefaultCursor());
		bottom.add(labSeries, BorderLayout.WEST);
		bottom.add(txtSeries, BorderLayout.CENTER);

		/* 设置绑定节点，作为X、Y轴的标签 */
		createPopupMenu(txtValue);
		createPopupMenu(txtSeries);
		txtValue.addPropertyChangeListener(new PropertyChangeImp(btnFontVlaue));
		txtSeries.addPropertyChangeListener(new PropertyChangeImp(btnFontSeries));
		
		
		RibbonUIManager.getInstance().bind(WisedocChart.VALUE_TEXT_ACTION, txtValue);		
		RibbonUIManager.getInstance().bind(WisedocChart.SERIES_TEXT_ACTION, txtSeries);
		
		
		final JRibbonComponent compValue = new JRibbonComponent(top);
		final JRibbonComponent compSeries = new JRibbonComponent(bottom);

		band.addRibbonComponent(compValue);
		band.addRibbonComponent(compSeries);

		return band;
	}

	private JPopupMenu createPopupMenu(final WisedocTextField field) {
		final JPopupMenu menu = new JPopupMenu();
		final WiseDocTree tree = new WiseDocTree();
		tree.setCellRenderer(new DataStructureCellRender());
		final JScrollPane scrPanel = new JScrollPane(tree);
		menu.setPreferredSize(new Dimension(120, 150));

		menu.add(scrPanel);

		field.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				final int btn = e.getButton();
				if (btn == MouseEvent.BUTTON2) {
					return;
				}
				if(btn == MouseEvent.BUTTON1 && e.getClickCount() != 2) {
					return;
				}
				final Document doc = SystemManager.getCurruentDocument();
				if (doc != null && doc.getDataStructure() != null) {
					tree.setModel(doc.getDataStructure());
					final int height = field.getHeight();
					menu.show(field, 0, height);
				}
			}
		});		
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(final TreeSelectionEvent e) {
				final TreePath path = tree.getSelectionPath();
				if (path == null) {
					return;
				}
				final BindNode node = (BindNode)path.getLastPathComponent();
				field.setValue(node);
				menu.setVisible(false);
			}
		});
		return menu;
	}

	/*
	 * 根据光标所在的位置不同【值标签、域标签文本框】，点击左下角小按钮，弹出字体相关属性设置对话框
	 * 
	 */
	@SuppressWarnings("serial")
	private Actions createListener() {
		final Actions lis = new Actions(){
			@Override
			public void doAction(final ActionEvent e) {
				final Map<Integer, Object> map = RibbonUIModel.getCurrentPropertiesByType().get(ActionType.Chart);
				FontStyleDialog dialog = null/*new FontStyleDialog(createValueKeyList(), map)*/;
				final Object source = e.getSource();
				if(source == btnFontVlaue) {
					dialog = new FontStyleDialog(createValueKeyList(), map);
				} else {
					dialog = new FontStyleDialog(createSeriesKeyList(), map);
				}
				if(dialog == null) {
					return;
				}
				final DialogResult result = dialog.showDialog();
				if(result == DialogResult.Cancel) {
					return;
				}
				this.actionType = ActionType.Chart;
				
				final Map<Integer, Object> values = dialog.getAttributes();
				if(source != btnFontSeries && source != btnFontVlaue){
					final Map<Integer, Object> r = values;
					final Set<Integer> set = r.keySet();
					
					Object temp = null;
					for (final Integer key : set) {
						temp = r.get(key);
						if(key == Constants.PR_DOMAINAXIS_LABEL_FONTFAMILY){
							values.put(Constants.PR_RANGEAXIS_LABEL_FONTFAMILY, temp);
						}else if(key == Constants.PR_DOMAINAXIS_LABEL_FONTSTYLE){
							values.put(Constants.PR_RANGEAXIS_LABEL_FONTSTYLE, temp);
						}else if(key == Constants.PR_DOMAINAXIS_LABEL_COLOR){
							values.put(Constants.PR_RANGEAXIS_LABEL_COLOR, temp);
						}else if(key == Constants.PR_FONT_WEIGHT) {
							values.put(Constants.PR_FONT_WEIGHT, temp);
						}
					}
				}
				setFOProperties(dialog.getAttributes());
			}
		};
		return lis;
	}
	private List<Integer> createValueKeyList(){
		/* Key的顺序是按界面顺序给定的，不能打乱了顺序。 */
		final List<Integer> list = new ArrayList<Integer>();
		/*list.add(Constants.PR_VALUE_LABEL_FONTFAMILY);		
		list.add(Constants.PR_VALUE_LABEL_FONTSIZE);		
		list.add(Constants.PR_VALUE_LABEL_FONTSTYLE);
		list.add(Constants.PR_VALUE_LABEL_COLOR);*/
		
		list.add(Constants.PR_RANGEAXIS_LABEL_FONTFAMILY);		
		list.add(Constants.PR_RANGEAXIS_LABEL_FONTSIZE);		
		list.add(Constants.PR_RANGEAXIS_LABEL_FONTSTYLE);
		list.add(Constants.PR_RANGEAXIS_LABEL_COLOR);
		
		return list;
	}
	private List<Integer> createSeriesKeyList(){
		/* Key的顺序是按界面顺序给定的，不能打乱了顺序。 */
		final List<Integer> list = new ArrayList<Integer>();
		/*list.add(Constants.PR_SERIES_LABEL_FONTFAMILY);		
		list.add(Constants.PR_SERIES_LABEL_FONTSIZE);		
		list.add(Constants.PR_SERIES_LABEL_FONTSTYLE);
		list.add(Constants.PR_SERIES_LABEL_COLOR);*/
		
		list.add(Constants.PR_DOMAINAXIS_LABEL_FONTFAMILY);		
		list.add(Constants.PR_DOMAINAXIS_LABEL_FONTSIZE);		
		list.add(Constants.PR_DOMAINAXIS_LABEL_FONTSTYLE);
		list.add(Constants.PR_DOMAINAXIS_LABEL_COLOR);
		return list;
	}
	final private WisedocTextField txtValue = new WisedocTextField(20);
	final private WisedocTextField txtSeries = new WisedocTextField(20);
	final JButton btnFontSeries = new JButton("A");//MediaResource.getImageIcon("OneTouchBottomWin.gif")
	final JButton btnFontVlaue = new JButton("A");
	
	public static class WisedocTextField extends JTextField{
		private BarCodeText value = null;
		private boolean bindNode = Boolean.FALSE;
		public WisedocTextField(final int cols){
			super(cols);			
		}		
		public BarCodeText getValue() {
			return value;
		}
		public void setValue(final BindNode value) {
			this.value = new BarCodeText(value);
			bindNode = Boolean.TRUE;
			fireActionPerformed();
			bindNode = Boolean.FALSE;
		}
		public boolean isBindNode(){
			return bindNode;
		}
	}
	private class PropertyChangeImp implements PropertyChangeListener{
		private JButton btn = null;
		PropertyChangeImp(final JButton btn){
			this.btn = btn;
		}
		@Override
		public void propertyChange(final PropertyChangeEvent evt) {
			if(evt.getPropertyName() != "enabled") {
				return;
			}
			final Boolean b = (Boolean)evt.getNewValue();
			btn.setEnabled(b);
		}
	}
}
