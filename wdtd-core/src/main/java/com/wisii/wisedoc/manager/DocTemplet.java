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
package com.wisii.wisedoc.manager;

import com.wisii.wisedoc.document.Block;
import com.wisii.wisedoc.document.Bookmark;
import com.wisii.wisedoc.document.BookmarkTitle;
import com.wisii.wisedoc.document.BookmarkTree;
import com.wisii.wisedoc.document.Constants;
import com.wisii.wisedoc.document.WiseDocDocument;

public class DocTemplet {
	
	public WiseDocDocument getDocument() {
		final WiseDocDocument doc = new WiseDocDocument();
//		TreeNode node = doc.getChildAt(0);
//		if (!(node instanceof PageSequence))
//		{
//			System.out.println("is Null");
//			return;
//		}
		
//		PageSequence sequence = (PageSequence) node;
//		StaticContent beforeflow = new StaticContent("xsl-region-before");
//		Block beforblock = new Block();
//		TextInline textbefore = new TextInline(new Text('B'), null);
//		beforblock.add(textbefore);
//		insertString(beforblock,
//				"测试页眉，页眉测试，测试页眉，页眉测试，测试页眉，页眉测试，测试页眉，页眉测试测试页眉，页眉测试", textbefore);
//		beforeflow.add(beforblock);
//		sequence.add(beforeflow);
//		StaticContent afterflow = new StaticContent("xsl-region-after");
//		Block afterblock = new Block();
//		TextInline textafter = new TextInline(new Text('A'), null);
//		afterblock.add(textafter);
//		insertString(afterblock,
//				"测试页脚，页脚测试，测试页脚，页脚测试，测试页脚，页脚测试，测试页脚，页脚测试，测试页脚页脚测试，测试页脚，页脚测试",
//				textafter);
//		afterflow.add(afterblock);
//		sequence.add(afterflow);
//
//		StaticContent startflow = new StaticContent("xsl-region-start");
//		Block startblock = new Block();
//		TextInline textstart = new TextInline(new Text('S'), null);
//		startblock.add(textstart);
//		insertString(
//				startblock,
//				"测试左区域，左区域测试，测试左区域，左区域测试，测试左区域，左区域测试，测试左区域，左区域测试，测试左区域，左区域测试，测试左区域，左区域测试",
//				textstart);
//		startflow.add(startblock);
//		sequence.add(startflow);
//		StaticContent endflow = new StaticContent("xsl-region-end");
//		Block endblock = new Block();
//		TextInline textend = new TextInline(new Text('E'), null);
//		endblock.add(textend);
//		insertString(
//				endblock,
//				"测试右区域，页脚右区域，测试右区域，页脚右区域，测试右区域，页脚右区域，测试右区域，页脚右区域，测试右区域，页脚右区域，测试右区域",
//				textend);
//		endflow.add(endblock);
//		sequence.add(endflow);
//
//		Flow flow = (Flow) sequence.getChildAt(0);
//		// -------------------Table------------------
//		String ID = new String("OP");
//		Table table = new Table();
//		TableBody body = new TableBody();
//		TableRow row = new TableRow();
//		TableCell cell = new TableCell();
//		Block block = new Block();
//		TableCell cell0 = new TableCell();
//		Block block0 = new Block();
//
//		table.add(body);
//
//		row.add(cell0);
//
//		cell0.add(block0);
//
//		row.add(cell);
//		cell.add(block);
//		body.add(row);
//		// -------------------Table------------------
//
//		// // Singler模式的消息发布者，类似于全局变量（类）
//		// // 添加观察者到消息发布者上(注册）
//		// PropertyObserver pos = new PropertyObserver(SinglerObserver
//		// .getInstance());
//		// // 设置需要更改的元素，这里需要设置一组需要改变的元素
//		// ArrayList<DefaultElement> selectedElements = new
//		// ArrayList<DefaultElement>();
//		// selectedElements.add(block);
//		// // 绑定这一组元素到观察者上，有属性改变时这一组元素会自动被设置上属性
//		// pos.setELementPorperty(selectedElements);
//
//		/*
//		 * block.setAttribute(Constants.PR_BORDER_BEFORE_COLOR, Color.RED);
//		 * block.setAttribute(Constants.PR_BORDER_BEFORE_STYLE, "solid");
//		 * block.setAttribute(Constants.PR_BORDER_BEFORE_WIDTH, "1");
//		 */
//		// System.out.println(block.getCommonFont().fontSize.toString());
//		Block blockTop = new Block();
//		blockTop.setAttribute(Constants.PR_ID, FIRST);
//		TextInline textTop = new TextInline(new Text('F'), null);
//		/* TextInline top = new TextInline(new Text('李'), null); */
//		blockTop.add(textTop);
//		/* blockTop.add(top); */
//		String s = "his is a book are";// you ready test size font String red
//		// solid width height top";
//		String ss = "受到法律实践上篮得分结束了十六大激素疗法";
//		insertString(blockTop, ss + "受到法律实践上篮得分结束了十六大激素疗法" + "", textTop);
		/* insertString(blockTop, "李晓光是否独立审计多少了附件是李晓光", top); */
//		flow.add(blockTop);
//
//		flow.add(table);
//
//		Block blockBottom = new Block();
//		blockBottom.setAttribute(Constants.PR_ID, SECOND);
//		TextInline textBottom = new TextInline(new Text('S'), null);
		/*blockBottom.add(textBottom);*/
//		// doc.insertString(,
//		// new DocumentPosition(textBottom), null);
//		insertString(blockBottom,
//				"受到法律实践上篮得分结束了十六大激素疗法受到法律实践上篮得分结束了十六大激素疗法受到法律实践上"
//						+ "受到法律实践上篮得分结束了十六大激素疗法受到法律实践上篮得分结束了十六大激素疗法受到法律实践上"
//						+ "受到法律实践上篮得分结束了十六大激素疗法受到法律实践上篮得分结束了十六大激素疗法受到法律实践上"
//						+ "受到法律实践上篮得分结束了十六大激素疗法受到法律实践上篮得分结束了十六大激素疗法受到法律实践上"
//						+ "受到法律实践上篮得分结束了十六大激素疗法受到法律实践上篮得分结束了十六大激素疗法受到法律实践上"
//						+ "篮得分结束了十六大激素疗法受到法律实践上篮得分结束了十六大激素疗法"
//						+ "受到法律实践上篮得分结束了十六大激素疗法" + "受到法律实践上篮得分结束了十六大激素疗法"
//						+ "受到法律实践上篮得分结束了十六大激素疗法" + "受到法律实践上篮得分结束了十六大激素疗法"
//						+ "受到法律实践上篮得分结束了十六大激素疗法", textBottom);
//		flow.add(blockBottom);
//		doc.setBookmarkTree(createMarkTree(blockTop, blockBottom));
//
//		// body.add(row0);
//		/*
//		 * Table table0 = new Table(false, false, 1, 2, null); //
//		 * flow.add(table0);
//		 */
//
//		Table table0 = new Table();
//		TableBody body0 = new TableBody();
//		TableRow row0 = new TableRow();
//		TableCell cell01 = new TableCell();
//		TableCell cell11 = new TableCell();
//		Block block2 = new Block();
//		Block block1 = new Block();
//		cell01.add(block1);
//		cell11.add(block2);
//		row0.add(cell01);
//
//		row0.add(cell11);
//		body.add(row0);
//
//		// body0.add(row0);
//		// table0.add(body0);
//		// flow.add(table0);
//		// flow.add(blockBottom);
//
//		TextInline text0 = new TextInline(new Text('0'), null);
//		TextInline text1 = new TextInline(new Text('1'), null);
//		TextInline text2 = new TextInline(new Text('2'), null);
//		TextInline text3 = new TextInline(new Text('3'), null);
//		/*
//		 * TextInline text2 = new TextInline(new Text('速'),null); TextInline
//		 * text3 = new TextInline(new Text('度'),null); TextInline text4 = new
//		 * TextInline(new Text('上'),null);
//		 */
//
//		/*
//		 * block.add(text0); block0.add(text1); block1.add(text2);
//		 * block2.add(text3);
//		 */
//		/*
//		 * block.add(text1); block.add(text2); block.add(text3);
//		 * block.add(text4);
//		 */
//
//		/* 下划线没有效果 */
//		/*
//		 * block.setAttribute(Constants.PR_TEXT_DECORATION, 1);
//		 * block.setAttribute(Constants.PR_FONT_SIZE, new FixedLength(12d,
//		 * "pt"));
//		 */
//		// System.out.println(block.getAttribute(Constants.PR_TEXT_DECORATION));
//		String text = "对不起，系统目前没有找到您订票单上的门票。您可以更改订单上的场次或票价选择，稍后再试。对不起，系统目前没有找到您订票单上的门票。您可以更改订单上的场次或票价选择，稍后再试。对不起，系统目前没有找到您订票单上的门票。您可以更改订单上的场次或票价选择，稍后再试。|";
//		String str = "你好系统目前没有找到您订票单上的门票系统目前没有找到您订票单上的门票系统目前没有找到您订票单上的门票";// "This is a test are you ready I'm reading book, what are you doing! this a new block test book page sequence "
//
//		// str += text;
//		// str += str;
//		// str += text;
//		// str += str;
//		// str += text;
//		// str += str;
//		// str += text;
//		// str += str;
//		// str += text;
//
//		// String text = "This"; String str = "is";
//		insertString(block, new String(str), text0);
//		insertString(block0, new String(text), text1);
//		insertString(block1, new String(text), text2);
//		insertString(block2, new String(text), text3);
		return doc;
	}
	
	private BookmarkTree createMarkTree(final Block...block){
		final BookmarkTree tree = new BookmarkTree();
		
		final Bookmark mark0 = new Bookmark();
		System.out.println("block[0].getId() = " + block[0].getId());
		mark0.setAttribute(Constants.PR_INTERNAL_DESTINATION, block[0].getId());
		final BookmarkTitle title0 = new BookmarkTitle();
		/*TextInline text0 = new TextInline(new Text('0'), null);
		insertString(title0, new String("First bookmark title"), text0);*/
		title0.setAttribute(Constants.PR_BOOKMARK_TITLE, new String("First bookmark title"));
		mark0.add(title0);
		
		final Bookmark mark1 = new Bookmark();
		System.out.println("block[1].getId() = " + block[1].getId());
		mark1.setAttribute(Constants.PR_INTERNAL_DESTINATION, block[1].getId());
		final BookmarkTitle title1 = new BookmarkTitle();
		/*TextInline text1 = new TextInline(new Text('1'), null);
		insertString(title1, new String("Second bookmark title"), text1);*/
		title1.setAttribute(Constants.PR_BOOKMARK_TITLE, new String("Second bookmark title"));
		mark1.add(title1);
		mark0.add(mark1);
		
		tree.add(mark0);
		return tree;
	}
	private final static String FIRST = "FIRST";
	private final static String SECOND = "SECOND";
}
