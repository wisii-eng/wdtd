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
package com.wisii.wisedoc.view.ui.parts.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.wisii.wisedoc.area.inline.TextArea;
import com.wisii.wisedoc.fonts.Font;
import com.wisii.wisedoc.fonts.FontMetrics;
import com.wisii.wisedoc.fonts.FontTriplet;
import com.wisii.wisedoc.render.java2d.FontMetricsMapper;

public class FontStylePreviewPanel extends JPanel{
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D gd = (Graphics2D) g;
		
		Map<Integer, Object> prop = new HashMap<Integer, Object>();
		
		
		TextArea text = new TextArea();
		text.addWord("hih", 2);
		
		Rectangle2D.Float wordRect = new Rectangle2D.Float(10, 10, 100, 100);
		text.setShowRec(wordRect);
		
		/*text.setAreaKind(BlockKind.NORMAL);
		
		text.setBaselineOffset(11156);
		text.setBind(false);
		text.setBPD(12703);
		text.setIPD(474000);
		text.addTrait(new Trait(Constants.PR_FONT_FAMILY, "宋体"));
		char[] cc = new char[]{'c', 'b', 'a'};
		FOText foText = new FOText(new Block(), cc);
		
		PreviewDialogAPP pda = PreviewDialogAPP.
		text.setSource(foText);*/
//		text.
		
//		Block block = DocumentPanel.bk;

		
		
		
//		System.out.println(text.getText());
		
		String key = "宋体"; 
		FontTriplet triplet = new FontTriplet(key, "normal", 800); 
		FontMetrics met = new FontMetricsMapper(key, 800, gd); 
		int fontSize = 36;
		
		
		
		Font font = new Font(key, triplet, met, fontSize);
		
//		new AWTRenderer().renderText(text);
//		Java2DRenderer.
//		Java2DRenderer.renderText(Java2DRenderer.tt, gd, Java2DRenderer.ft);
		
		String s = "sss";
//		int[] letterAdjust = word.getLetterAdjustArray();
//		GlyphVector gv = gd.getFont().createGlyphVector(gd.getFontRenderContext(), s);
//		gd.drawGlyphVector(gv, 10, 10);
	}
	
	
    public static void createAndShowGUI() {
    	// 正常的tab的测试程序
		JFrame fr = new JFrame("测试用");
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		fr.getContentPane().setLayout(flowLayout);
		fr.setSize(462, 600);

		FontStylePreviewPanel tui = new FontStylePreviewPanel();
		tui.setPreferredSize(new Dimension(100,100));
		tui.setBackground(Color.yellow);
		
		fr.add(tui);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setVisible(true);
    }
    
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
	

}
