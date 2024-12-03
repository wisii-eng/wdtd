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
package com.wisii.wisedoc.view.ui.parts.dialogs.psmtree;

import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * 用于排放、组织页布局序列的树形面板
 * @author 闫舒寰
 * @version 1.0 2009/02/06
 */
public class TreePanel extends JPanel {
	
    private DefaultMutableTreeNode rootNode;
    private static DefaultTreeModel treeModel;
    private static JTree tree;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    public TreePanel() {
        super(new GridLayout(1,0));
        
        rootNode = new DefaultMutableTreeNode("layout-master-set");
        treeModel = new DefaultTreeModel(rootNode);

        //创建支持拖拽的树
        tree = new DnDJTree();
        tree.setModel(treeModel);
        
        tree.addTreeSelectionListener(new TreeActions());
        
        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);

        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);
    }
    
    public void setSelection() {
    	//这个方法需要实现选择当前刚添加的节点
		TreePath parentPath = tree.getPathForRow(tree.getRowCount()-1);
		tree.setSelectionPath(parentPath);
	}
    
    public void setPageSequenceMasterSelection(){
    	TreePath parentPath = tree.getPathForRow(0);
		tree.setSelectionPath(parentPath);
    }

    /** Remove all nodes except the root node. */
    public void clear() {
        rootNode.removeAllChildren();
        treeModel.reload();
    }

    /** Remove the currently selected node. */
    public void removeCurrentNode() {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                         (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
                return;
            }
        } 

        // Either there was no selection, or the root was selected.
        toolkit.beep();
    }

    /** Add child to the currently selected node. */
    public DefaultMutableTreeNode addObject(Object child) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();

        if (parentPath == null) {
            parentNode = rootNode;
        } else {
            parentNode = (DefaultMutableTreeNode)
                         (parentPath.getLastPathComponent());
        }

        return addObject(parentNode, child, true);
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
    										Object child) {
        return addObject(parent, child, false);
    }

    //
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child, 
                                            boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
        
        if (parent == null) {
            parent = rootNode;
            tree.setRootVisible(false);
        }
	
        //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
        treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
        
        //Make sure the user can see the lovely new node.
        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }
    
    /**
     * 下面是自己添加的自定义节点。
     */
    
    /**
     * 为page-sequence-master添加node
     * @param child
     * @return
     */
    public DefaultMutableTreeNode addPageSequenceMasterNode(Object child){
    	return addObject(null, child, true);
    }
    
    /**
     * 为single-page-master-reference添加node
     * @param child
     * @return
     */
    public DefaultMutableTreeNode addSinglePageMasterReferenceNode(Object child){
    	
		DefaultMutableTreeNode parentNode = null;
	    TreePath parentPath = tree.getSelectionPath();
	
	    if (parentPath == null) {
	        return null;
	    } else {
	        parentNode = (DefaultMutableTreeNode)(parentPath.getLastPathComponent());
	    }
	    
	    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
	    childNode.setAllowsChildren(false);
	
        //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
	    try {
	    	treeModel.insertNodeInto(childNode, parentNode, parentNode.getChildCount());
		} catch (Exception e) {
			System.err.println("can not insert");
		}
        
        //Make sure the user can see the lovely new node.
        if (true) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }
    
    /**
     * 为repeatable-page-master-reference添加node
     * @param child
     * @return
     */
    public DefaultMutableTreeNode addRepeatablePageMasterReferenceNode(Object child){
    	
		DefaultMutableTreeNode parentNode = null;
	    TreePath parentPath = tree.getSelectionPath();
	
	    if (parentPath == null) {
	        return null;
	    } else {
	        parentNode = (DefaultMutableTreeNode)(parentPath.getLastPathComponent());
	    }
	    
	    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
	    childNode.setAllowsChildren(false);
	
        //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
	    try {
	    	treeModel.insertNodeInto(childNode, parentNode, parentNode.getChildCount());
		} catch (Exception e) {
			System.err.println("can not insert");
		}
        
        //Make sure the user can see the lovely new node.
        if (true) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }
    
    /**
     * 为repeatable-page-master-alternatives添加node
     * @param child
     * @return
     */
    public DefaultMutableTreeNode addRepeatablePageMasterAlternativesNode(Object child){
    	
		DefaultMutableTreeNode parentNode = null;
	    TreePath parentPath = tree.getSelectionPath();
	
	    if (parentPath == null) {
	        return null;
	    } else {
	        parentNode = (DefaultMutableTreeNode)(parentPath.getLastPathComponent());
	    }
	    
	    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
	    childNode.setAllowsChildren(true);
	
        //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
	    try {
	    	treeModel.insertNodeInto(childNode, parentNode, parentNode.getChildCount());
		} catch (Exception e) {
			System.err.println("can not insert");
		}
		
		//为repeatable-page-master-alternatives节点添加孩子
		/*DefaultMutableTreeNode conditionalPMRNode = new DefaultMutableTreeNode(new ConditionalPageMasterReferenceModel());
		conditionalPMRNode.setAllowsChildren(false);
		treeModel.insertNodeInto(conditionalPMRNode, childNode, childNode.getChildCount());*/
        
        //Make sure the user can see the lovely new node.
        if (true) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }
    
    /**
     * 为conditional-page-master-reference添加node
     * @param child
     * @return
     */
    public DefaultMutableTreeNode addConditionalPageMasterReferenceNode(Object child){
    	
		DefaultMutableTreeNode parentNode = null;
	    TreePath parentPath = tree.getSelectionPath();
	    
	    
	
	    if (parentPath == null) {
	        return null;
	    } else {
	        parentNode = (DefaultMutableTreeNode)(parentPath.getLastPathComponent());
	        parentNode.setAllowsChildren(true);
	    }
	    
	    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
	    childNode.setAllowsChildren(false);
	
//	    System.out.println(parentPath + " ch: " + child  + " parent: " + parentNode);
	    
        //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
	    try {
	    	treeModel.insertNodeInto(childNode, parentNode, parentNode.getChildCount());
		} catch (Exception e) {
			System.err.println(e);
//			System.err.println("can not insert conditional page masterReference");
		}
        
        //Make sure the user can see the lovely new node.
        if (true) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }
    
    
    /**
     * 添加节点结束
     */
    
    
    class MyTreeModelListener implements TreeModelListener {
        public void treeNodesChanged(TreeModelEvent e) {
            DefaultMutableTreeNode node;
            node = (DefaultMutableTreeNode)(e.getTreePath().getLastPathComponent());

            /*
             * If the event lists children, then the changed
             * node is the child of the node we've already
             * gotten.  Otherwise, the changed node and the
             * specified node are the same.
             */

            int index = e.getChildIndices()[0];
            node = (DefaultMutableTreeNode)(node.getChildAt(index));

            System.out.println("The user has finished editing the node.");
            System.out.println("New value: " + node.getUserObject());
        }
        public void treeNodesInserted(TreeModelEvent e) {
        }
        public void treeNodesRemoved(TreeModelEvent e) {
        }
        public void treeStructureChanged(TreeModelEvent e) {
        }
    }

	public static DefaultTreeModel getTreeModel() {
		return treeModel;
	}

	public static JTree getTree() {
		return tree;
	}
}
