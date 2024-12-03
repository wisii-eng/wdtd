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

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class ConcurrentTest extends JPanel {
	
	
	public ConcurrentTest() {
		this.setLayout(new BorderLayout());
		final JTextArea jta = new JTextArea();
		this.add(jta, BorderLayout.CENTER);
		jta.addKeyListener(new KeyListener(){
		
			@Override
			public void keyTyped(final KeyEvent e) {
				singleEx.execute(new InputR(e));
//				try {
//					iEvent.put(e);
//				} catch (final InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
			}
		
			@Override
			public void keyReleased(final KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		
			@Override
			public void keyPressed(final KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
//		dealEx.schedule(new EBuffer(), 5l, TimeUnit.SECONDS);
		dealEx.scheduleAtFixedRate(new EBuffer(), 500l, 500l, TimeUnit.MILLISECONDS);
	}
	
	private BlockingQueue<StringBuilder> iBuffer = new LinkedBlockingQueue<StringBuilder>();
	
	private BlockingQueue<KeyEvent> iEvent = new LinkedBlockingQueue<KeyEvent>();
	
	private static final ExecutorService singleEx = Executors.newSingleThreadExecutor();
	
	private static final ScheduledExecutorService dealEx = Executors.newSingleThreadScheduledExecutor();
	
	
	
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
		
			@Override
			public void run() {
				final JFrame jf = new JFrame();
				
				jf.add(new ConcurrentTest());
				
				jf.setSize(200, 200);
				
				jf.setVisible(true);
				jf.setLocationRelativeTo(null);
				jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
	}
	

	private static int i;
	
	private class InputR implements Runnable {
		
		KeyEvent ke;
		
		public InputR(final KeyEvent e) {
			this.ke = e;
			++i;
		}
		
		@Override
		public void run() {
			final StringBuilder sb = new StringBuilder();
			sb.append(ke.getKeyChar());
			try {
				iBuffer.put(sb);
			} catch (final InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			System.out.print(ke.getKeyChar() + " i: " + i);
		}
	}
	
	private class EBuffer implements Runnable {
		
		@Override
		public void run() {
			
			if (iBuffer.isEmpty()) {
				return;
			}

			final StringBuilder ssb = new StringBuilder();
			for (final StringBuilder sb : iBuffer) {
				ssb.append(sb);
				iBuffer.remove(sb);
			}
			System.out.println(ssb);
			
//			if (iEvent.isEmpty()) {
//				return;
//			}
//
//			for (final KeyEvent ke : iEvent) {
//				System.out.print(ke.getKeyChar());
//				iEvent.remove(ke);
//			}
		}
	}

}
