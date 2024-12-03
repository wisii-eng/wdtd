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
package com.wisii.wisedoc.view.ui.update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import com.wisii.wisedoc.SystemManager;
import com.wisii.wisedoc.document.BarCode;
import com.wisii.wisedoc.document.BlockContainer;
import com.wisii.wisedoc.document.Chart;
import com.wisii.wisedoc.document.CheckBoxInline;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.DateTimeInline;
import com.wisii.wisedoc.document.EditSetAble;
import com.wisii.wisedoc.document.EncryptTextInline;
import com.wisii.wisedoc.document.ExternalGraphic;
import com.wisii.wisedoc.document.NumberTextInline;
import com.wisii.wisedoc.document.PageSequence;
import com.wisii.wisedoc.document.StaticContent;
import com.wisii.wisedoc.document.Table;
import com.wisii.wisedoc.document.TableCell;
import com.wisii.wisedoc.document.TableRow;
import com.wisii.wisedoc.document.TextInline;
import com.wisii.wisedoc.document.svg.Canvas;
import com.wisii.wisedoc.document.svg.SVGContainer;
import com.wisii.wisedoc.document.svg.WordArtText;
import com.wisii.wisedoc.edit.DefaultPropertyObserver;
import com.wisii.wisedoc.view.AbstractEditComponent;
import com.wisii.wisedoc.view.ui.manager.RibbonUIManager;
import com.wisii.wisedoc.view.ui.model.RibbonUIModel;
import com.wisii.wisedoc.view.ui.observer.UIObserver;
import com.wisii.wisedoc.view.ui.ribbon.RibbonPanel;

/**
 * Ribbon界面和编辑界面绑定的控制类，确保当前编辑的界面和工具栏有正确的绑定关系，工具栏能正确的把属性设置到
 * 当前编辑界面上，同时工具栏也能正确监听当前编辑界面的属性改变
 * 
 * @author 闫舒寰
 * @version 1.0 2009/02/27
 */
public enum RibbonUpdateManager {
	
	Instance;
	
	//监听属性的监听器
	private static final ElementSelectionListenerAction elementSelectionListener = new ElementSelectionListenerAction();
	private static final DocumentCaterListenerAction documentCaterListener = new DocumentCaterListenerAction();
	private static final DocumentChangeListenerAction documentChangeListener = new DocumentChangeListenerAction();
	
	//当前主面板
	private AbstractEditComponent currentMainPanel;
	//当前选定面板
	private AbstractEditComponent currentEditPanel;
	
	/**
	 * 当前主面板中所选中的对象树，会随着弹出的新的对话框而初始化
	 */
	private List<Object> mainElementList = new ArrayList<Object>();

	/**
	 * 获得当前用户在主面板操作的page sequence的属性
	 * @return page sequence的属性
	 */
	public Map<Integer, Object> getMainPSProperty(){
		Map<Integer, Object> temp = new HashMap<Integer, Object>();
		if (getMainElementList() != null) {
			for (final Object o : getMainElementList()) {
//				System.out.println(o);
				if (o instanceof PageSequence) {
					temp = ((PageSequence) o).getAttributes().getAttributes();
				}
			}
		}
		return temp;
	}
	
	/**
	 * 获得用户当前操作的主面板的page sequence
	 * @return 当前主面板用户操作的page sequence
	 */
	public PageSequence getMainPageSequence(){
		PageSequence temp = null;
		
		if (getMainElementList() == null || getMainElementList().size() == 0) {
			//当前mainPageSequence没有被激活时先要读取一下
			initialMainElementList();
		}
		
		if (getMainElementList() != null) {
			for (final Object o : getMainElementList()) {
//				System.out.println(o);
				if (o instanceof PageSequence) {
					temp = (PageSequence) o;
				}
			}
		}
		return temp;
	}
	
	//设置属性的监听者
	private static final DefaultPropertyObserver propertyObserver = new DefaultPropertyObserver(UIObserver.getInstance());
	
	public void setDocumentEditorListener(final AbstractEditComponent documentEditorPanel){
		
		if (getCurrentEditPanel() != null) {
			getCurrentEditPanel().getSelectionModel().removeElementSelectionListener(elementSelectionListener);
			getCurrentEditPanel().removeDocumentCaretListener(documentCaterListener);
			getCurrentEditPanel().getDocument().removeDocumentListener(documentChangeListener);
		}
		
		//监听指定文档编辑面板
		setCurrentEditPanel(documentEditorPanel);
		
		//不在SystemManager中设置当前编辑属性会有其他的问题，但不应该在这里设置，以后会删除
		SystemManager.setCurruentDocument(documentEditorPanel.getDocument());
		
		//设置当前文档编辑面板的属性
		propertyObserver.setCurrentEditPanel(documentEditorPanel);
		
		getCurrentEditPanel().getSelectionModel().addElementSelectionListener(elementSelectionListener);
		getCurrentEditPanel().addDocumentCaretListener(documentCaterListener);
		getCurrentEditPanel().getDocument().addDocumentListener(documentChangeListener);
		
		RibbonUIManager.updateUIAvailable();
	}
	
	
	//负责更新界面的内容、属性和可用状态
	public void updateUIAvaliableAndProperty(final List<?> elements,boolean isEditSetAble){
//		System.out.println(elements);
		//更新动态内容界面
		updateRibbonUI(elements,isEditSetAble);
		//更新界面属性
		RibbonUIModel.RibbonUIModelInstance.updateUIState(elements);
		//更新界面可用状态
		RibbonUIManager.updateUIAvailable();
		//清空当前list
		elements.clear();
	}
	
	/**
	 * 更新RibbonUI， 并且切换到相应的Ribbon面板，并且关闭不相关的面板
	 * @param element	当前选定的元素的所有父亲节点
	 * @version 1.5 2009/02/20
	 */
	private void updateRibbonUI(final List<?> eList,boolean isEditSetAble) {
		
		if (eList == null || eList.size() == 0) {
			return;
		}
		
		final List<Class<?>> cList = new ArrayList<Class<?>>();
		for (final Object element : eList)
		{
			cList.add(element.getClass());
		}
		setEnabled(EditSetAble.class,isEditSetAble);
		
		ecList = cList;
		//以下具体执行开启或者关闭不相关的面板
		if (cList.contains(TableCell.class) || cList.contains(TableRow.class) || cList.contains(Table.class)) {
			setEnabled(TableCell.class, true);
		} else {
			setEnabled(TableCell.class, false);
		}
		
		if (cList.contains(BarCode.class)) {
			setEnabled(BarCode.class, true);
		} else {
			setEnabled(BarCode.class, false);
		}
		
		if (cList.contains(ExternalGraphic.class)) {
			setEnabled(ExternalGraphic.class, true);
		} else {
			setEnabled(ExternalGraphic.class, false);
		}
		
		if (cList.contains(BlockContainer.class)) {
			setEnabled(BlockContainer.class, true);
		} else {
			setEnabled(BlockContainer.class, false);
		}
		
		if (cList.contains(DateTimeInline.class)) {
			setEnabled(DateTimeInline.class, true);
		} else {
			setEnabled(DateTimeInline.class, false);
		}
		
		if (cList.contains(NumberTextInline.class)) {
			setEnabled(NumberTextInline.class, true);
		} else {
			setEnabled(NumberTextInline.class, false);
		}
		if (cList.contains(CheckBoxInline.class)) {
			setEnabled(CheckBoxInline.class, true);
		} else {
			setEnabled(CheckBoxInline.class, false);
		}
		if (cList.contains(StaticContent.class)) {
			setEnabled(StaticContent.class, true);
		} else {
			setEnabled(StaticContent.class, false);
		}
		
		if (cList.contains(SVGContainer.class) || cList.contains(Canvas.class)) {
			setEnabled(SVGContainer.class, true);
		} else {
			setEnabled(SVGContainer.class, false);
		}
		if (cList.contains(WordArtText.class)) {
			setEnabled(WordArtText.class, true);
		} else {
			setEnabled(WordArtText.class, false);
		}
		if (cList.contains(EncryptTextInline.class)) {
			setEnabled(EncryptTextInline.class, true);
		} else {
			setEnabled(EncryptTextInline.class, false);
		}
		/* 【添加：START】  用于及时更新，在工具栏可见。 by 李晓光 	2009-5-18 */
		if (cList.contains(Chart.class)) {
			setEnabled(Chart.class, true);
		} else {
			setEnabled(Chart.class, false);
		}
		
		/* 【添加：END】  by 李晓光 	2009-5-18 */
	}
	
	//为表格、块容器之类的界面更新是否切换提供参考
	private List<Class<?>> ecList = new ArrayList<Class<?>>();
	
	/**
	 * 设置Ribbon上动态面板是否可见
	 * @param c
	 * @param b
	 */
	private void setEnabled(final Class<?> c, final boolean b){
		if (c == TableCell.class || c == TableRow.class || c == Table.class) {
			
			//只有当当前状态和要更改状态不一样的时候才更新界面
			if (b != RibbonPanel.getRibbon().isVisible(RibbonPanel.getTableTaskGroup())) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						RibbonPanel.getRibbon().setVisible(RibbonPanel.getTableTaskGroup(), b);
						//如果表格面板激活则切换到表格面板上
						if (b) {
							//如果表格内不含有文字则切换到表格菜单
							if (!ecList.contains(TextInline.class)) {
								RibbonPanel.getRibbon().setSelectedTask(RibbonPanel.getTableTaskGroup().getTask(1));
							}
						}
					}
				});
			}
		}
		
		if (c == BarCode.class) {
			if (b != RibbonPanel.getRibbon().isVisible(RibbonPanel.getBarcodeTaskGroup())) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						RibbonPanel.getRibbon().setVisible(RibbonPanel.getBarcodeTaskGroup(), b);
						if (b) {
							RibbonPanel.getRibbon().setSelectedTask(RibbonPanel.getBarcodeTaskGroup().getTask(0));
						}
					}
				});
			}
		}
		/* 【添加：START】  by 李晓光 	2009-5-18 */
		if(c == Chart.class){
			if (b != RibbonPanel.getRibbon().isVisible(RibbonPanel.getChartGroup())) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						RibbonPanel.getRibbon().setVisible(RibbonPanel.getChartGroup(), b);
						if (b) {
							RibbonPanel.getRibbon().setSelectedTask(RibbonPanel.getChartGroup().getTask(0));
						}
					}
				});
			}
		}
		/* 【添加：END】  by 李晓光 	2009-5-18 */
		if (c == ExternalGraphic.class) {
			if (b != RibbonPanel.getRibbon().isVisible(RibbonPanel.getImageTaskGroup())) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						RibbonPanel.getRibbon().setVisible(RibbonPanel.getImageTaskGroup(), b);
						if (b) {
							RibbonPanel.getRibbon().setSelectedTask(RibbonPanel.getImageTaskGroup().getTask(0));
						}
					}
				});
			}
		}
		
		if (c == BlockContainer.class) {
			if (b != RibbonPanel.getRibbon().isVisible(RibbonPanel.getBlockContainer())) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						RibbonPanel.getRibbon().setVisible(RibbonPanel.getBlockContainer(), b);
						if (b) {
							//如果表格内不含有文字则切换到表格菜单
							if (!ecList.contains(TextInline.class)) {
								RibbonPanel.getRibbon().setSelectedTask(RibbonPanel.getBlockContainer().getTask(0));
							}
						}
					}
				});
			}
		}
		
		if (c == DateTimeInline.class) {
			if (b != RibbonPanel.getRibbon().isVisible(RibbonPanel.getDateFormat())) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						RibbonPanel.getRibbon().setVisible(RibbonPanel.getDateFormat(), b);
					}
				});
			}
		}
		
		if (c == NumberTextInline.class) {
			if (b != RibbonPanel.getRibbon().isVisible(RibbonPanel.getNumberFormat())) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						RibbonPanel.getRibbon().setVisible(RibbonPanel.getNumberFormat(), b);
					}
				});
			}
		}
		if (c == CheckBoxInline.class) {
			if (b != RibbonPanel.getRibbon().isVisible(RibbonPanel.getCheckBox())) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						RibbonPanel.getRibbon().setVisible(RibbonPanel.getCheckBox(), b);
					}
				});
			}
		}
		if (c == SVGContainer.class) {
			if (b != RibbonPanel.getRibbon().isVisible(RibbonPanel.getSvgGraph())) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						RibbonPanel.getRibbon().setVisible(RibbonPanel.getSvgGraph(), b);
						if (b) {
							RibbonPanel.getRibbon().setSelectedTask(RibbonPanel.getSvgGraph().getTask(0));
						}
					}
				});
			}
		}
		if (c == WordArtText.class) {
			if (b != RibbonPanel.getRibbon().isVisible(RibbonPanel.getWordArtText())) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						RibbonPanel.getRibbon().setVisible(RibbonPanel.getWordArtText(), b);
					}
				});
			}
		}
		if (c == EncryptTextInline.class) {
			if (b != RibbonPanel.getRibbon().isVisible(RibbonPanel.getEncryptText())) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						RibbonPanel.getRibbon().setVisible(RibbonPanel.getEncryptText(), b);
					}
				});
			}
		}
		//add by zq
		if (c == EditSetAble.class&&RibbonPanel.isEditenable()) {
			if (b != RibbonPanel.getRibbon().isVisible(RibbonPanel.getEditGroup())) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						RibbonPanel.getRibbon().setVisible(RibbonPanel.getEditGroup(), b);
					}
				});
			}
		}
		// zq add end
		if (c == StaticContent.class) {
			
			if (b) {
				if (currentEditPanel.findRegion() != null) {
					final int id = currentEditPanel.findRegion().getNameId();
					
					if (id == Constants.FO_REGION_BEFORE) {
						if (b != RibbonPanel.getRibbon().isVisible(RibbonPanel.getRegionBefore())) {
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									RibbonPanel.getRibbon().setVisible(RibbonPanel.getRegionBefore(), b);
									if (b) {
										//如果不含有文字则切换
										if (!ecList.contains(TextInline.class)) {
											RibbonPanel.getRibbon().setSelectedTask(RibbonPanel.getRegionBefore().getTask(0));
										}
									}
								}
							});
						}
					} else {
						if (false != RibbonPanel.getRibbon().isVisible(RibbonPanel.getRegionBefore())) {
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									RibbonPanel.getRibbon().setVisible(RibbonPanel.getRegionBefore(), false);
								}
							});
						}
					}
					
					if (id == Constants.FO_REGION_AFTER) {
						if (b != RibbonPanel.getRibbon().isVisible(RibbonPanel.getRegionAfter())) {
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									RibbonPanel.getRibbon().setVisible(RibbonPanel.getRegionAfter(), b);
									if (b) {
										//如果不含有文字则切换
										if (!ecList.contains(TextInline.class)) {
											RibbonPanel.getRibbon().setSelectedTask(RibbonPanel.getRegionAfter().getTask(0));
										}
									}
								}
							});
						}
					} else {
						if (false != RibbonPanel.getRibbon().isVisible(RibbonPanel.getRegionAfter())) {
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									RibbonPanel.getRibbon().setVisible(RibbonPanel.getRegionAfter(), false);
								}
							});
						}
					}
					
					if (id == Constants.FO_REGION_START) {
						if (b != RibbonPanel.getRibbon().isVisible(RibbonPanel.getRegionStart())) {
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									RibbonPanel.getRibbon().setVisible(RibbonPanel.getRegionStart(), b);
									if (b) {
										//如果不含有文字则切换
										if (!ecList.contains(TextInline.class)) {
											RibbonPanel.getRibbon().setSelectedTask(RibbonPanel.getRegionStart().getTask(0));
										}
									}
								}
							});
						}
					} else {
						if (false != RibbonPanel.getRibbon().isVisible(RibbonPanel.getRegionStart())) {
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									RibbonPanel.getRibbon().setVisible(RibbonPanel.getRegionStart(), false);
								}
							});
						}
					}
					
					if (id == Constants.FO_REGION_END) {
						if (b != RibbonPanel.getRibbon().isVisible(RibbonPanel.getRegionEnd())) {
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									RibbonPanel.getRibbon().setVisible(RibbonPanel.getRegionEnd(), b);
									if (b) {
										//如果不含有文字则切换
										if (!ecList.contains(TextInline.class)) {
											RibbonPanel.getRibbon().setSelectedTask(RibbonPanel.getRegionEnd().getTask(0));
										}
									}
								}
							});
						}
					} else {
						if (false != RibbonPanel.getRibbon().isVisible(RibbonPanel.getRegionEnd())) {
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									RibbonPanel.getRibbon().setVisible(RibbonPanel.getRegionEnd(), false);
								}
							});
						}
					}
				}
			} else {
				if (false != RibbonPanel.getRibbon().isVisible(RibbonPanel.getRegionBefore())) {
					RibbonPanel.getRibbon().setVisible(RibbonPanel.getRegionBefore(), false);
				}
				
				if (false != RibbonPanel.getRibbon().isVisible(RibbonPanel.getRegionAfter())) {
					RibbonPanel.getRibbon().setVisible(RibbonPanel.getRegionAfter(), false);
				}
				
				if (false != RibbonPanel.getRibbon().isVisible(RibbonPanel.getRegionStart())) {
					RibbonPanel.getRibbon().setVisible(RibbonPanel.getRegionStart(), false);
				}
				
				if (false != RibbonPanel.getRibbon().isVisible(RibbonPanel.getRegionEnd())) {
					RibbonPanel.getRibbon().setVisible(RibbonPanel.getRegionEnd(), false);
				}
				
				
				/*SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						
						RibbonPanel.getRibbon().setVisible(RibbonPanel.getRegionAfter(), false);
						RibbonPanel.getRibbon().setVisible(RibbonPanel.getRegionStart(), false);
						RibbonPanel.getRibbon().setVisible(RibbonPanel.getRegionEnd(), false);
					}
				});*/
			}
		}
		
		//转移焦点，所有更新ribbon界面必须要在这个之上，以保证每次Ribbon取得焦点以后会把焦点还给主面板
		if (b) {
			SystemManager.getMainframe().getEidtComponent().requestFocus();
		}
	}


	public AbstractEditComponent getCurrentMainPanel() {
		return currentMainPanel;
	}


	public void setCurrentMainPanel(final AbstractEditComponent currentMainPanel) {
		this.currentMainPanel = currentMainPanel;
		
		setDocumentEditorListener(currentMainPanel);
	}
	
	public void addMainEditorListener(){
		setDocumentEditorListener(this.currentMainPanel);
	}


	public AbstractEditComponent getCurrentEditPanel() {
		return currentEditPanel;
	}


	public void setCurrentEditPanel(final AbstractEditComponent currentEditPanel) {
		this.currentEditPanel = currentEditPanel;
	}


	public List<Object> getMainElementList() {
		return mainElementList;
	}

	
	private void setMainElementList(final List<Object> mainElementList) {
		this.mainElementList = mainElementList;
	}
	
	/**
	 * 这个方法用来保存当前用户针对主面板操作时所操作的章节的引用
	 * 并且读取当前章节的四区域的流的信息和四区域的页布局的信息，（单一页布局simple page master或者页布局序列page sequence master）
	 */
	public void initialMainElementList() {
		this.setMainElementList(RibbonUIModel.getElementList());
		RegionDocumentManager.Instance.initialRegionFlows();
	}
}
