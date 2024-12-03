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

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;

import com.wisii.wisedoc.document.WiseDocDocument;
import com.wisii.wisedoc.document.listener.CompoundElementChange;
import com.wisii.wisedoc.document.listener.DocumentChange;
import com.wisii.wisedoc.document.listener.DocumentChangeEvent;

public enum EndEditThreadModel {

	Instance;
	
	private WiseDocDocument wiseDocument;
	
	public void endEdit(final List<DocumentChange> changes, final WiseDocDocument wiseDocDocument) {
		
		this.wiseDocument = wiseDocDocument;
		
		if (changes != null && !changes.isEmpty())
		{
//			issaved = false;
//			isnew = false;
			// 这个地方是缓冲编辑操作，使得多次在几乎是同一时间进行编辑操作可以合并成一次编辑操作，
			// 只调用一次更新和记录一次编辑操作

//			changePool.push(changes);
			/*try {
//				System.out.println("put: " + changes);
				changeQueue.put(changes);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			
//			ect.schedule(dl, _delaytime, TimeUnit.MILLISECONDS);
			//文档重排
			addEdit(changes);
			
			//发送改变消息事件
			wiseDocument.firechangedUpdate(new DocumentChangeEvent(this, new CompoundElementChange(changes)));
//			singleServiceChange.execute(new FireChange(changes));
		}
	}
	
	

	private static final ExecutorService singleServiceChange = Executors.newSingleThreadExecutor();
	
	private class FireChange implements Runnable {
		
		List<DocumentChange> changes;
		
		public FireChange(final List<DocumentChange> changes) {
			this.changes = changes;
		}
		
		@Override
		public void run() {
			wiseDocument.firechangedUpdate(new DocumentChangeEvent(this,
					new CompoundElementChange(changes)));
		}
	}
	
//	private final BlockingQueue<List<DocumentChange>> changeQueue = new LinkedBlockingQueue<List<DocumentChange>>();
	private final SynchronousQueue<List<DocumentChange>> changeQueue = new SynchronousQueue<List<DocumentChange>>();
	
    private void addEdit(final List<DocumentChange> changes)
    {
    
    }
//		if (_edittask != null && _edittask.cancel())
//		{
//			updateLevel--;
//		}
//		_edittask = new MyEditUpdateTimerTask(changes);
//		final Timer timer = new Timer();
//		// 延迟delay长的时间再通知改变时间，以使得连续的改变事件只通知最后一次
//		timer.schedule(_edittask, _delaytime);
//	}
    	//TODO 下面是多线程的程序框架代码，由于需要对某些变量做细致的同步，目前没有时间，所以目前用保守的线程模式
		/*if (runningTask == null) {
			runningTask = propertyThreadPool.submit(new MyEditUpdateTimerTask(changes));
		}
		
		if (runningTask.isDone()) {
			runningTask = propertyThreadPool.submit(new MyEditUpdateTimerTask(changes));
		} else {
			boolean b = runningTask.cancel(true);
//			System.out.println("===============cancled: ============" + b + exec.getClass());
			try {
				propertyThreadPool.awaitTermination(_delaytime, TimeUnit.MILLISECONDS);
				runningTask = propertyThreadPool.submit(new MyEditUpdateTimerTask(changes));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
    	
//    	changePool.push(changes);
    	
    	/*if (_edittask != null && _edittask.cancel())
		{
			updateLevel--;
		}
		_edittask = new MyEditUpdateTimerTask(changes);
		ect.schedule(_edittask, _delaytime, TimeUnit.MILLISECONDS);*/
		
		//XXX 单线程测试
//		postEdit(new CompoundElementEdit(changes, WiseDocDocument.this));
////    	singleServiceInput.execute(new PostEdit(changes));
//	}
    
    private static final ExecutorService singleServiceInput = Executors.newSingleThreadExecutor();
    
    private class PostEdit implements Runnable {
    	
    	final List<DocumentChange> changes;
    	
    	public PostEdit(final List<DocumentChange> changes) {
    		this.changes = changes;
    	}
    	
    	@Override
    	public void run() {
//    		postEdit(new CompoundElementEdit(changes, WiseDocDocument.this));
    	}
    }
    
    private final ScheduledThreadPoolExecutor ect = new ScheduledThreadPoolExecutor(1);
    
    private final ChangePool changePool = new ChangePool();
    private final DoLayout dl = new DoLayout(changePool);
    
	private class DoLayout implements Runnable {
	      
	    private ChangePool changePool = null;
	  
	    public DoLayout(final ChangePool changePool) {
	        this.changePool = changePool;
	    }
	      
	    public void consumerProduct() {

//			List<DocumentChange> changes = changePool.pop();
	    	
			final List<DocumentChange> changes = new LinkedList<DocumentChange>();
			
//			System.err.println(changeQueue.size());
			
			for (int i = 0; i < changeQueue.size(); i++) {
				/*try {
					changes.addAll(changeQueue.take());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				changes.addAll(changeQueue.remove());
			}
			
			/*try {
				changes = changeQueue.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	System.out.println("run: " + changes);*/

//			System.out.println(" 消费了 " + changes.size());

			if (changes.size() != 0) {
				wiseDocument.firechangedUpdate(new DocumentChangeEvent(this,
						new CompoundElementChange(changes)));
			}

		}
	  
	    public void run() {
	        consumerProduct();
	    }
	}
    
	private class ChangePool {
		  
		List<DocumentChange> changes = new LinkedList<DocumentChange>();
	  
	    public synchronized void push(final List<DocumentChange> changes) {
	        this.changes.addAll(changes);
	    }
	  
	    public synchronized List<DocumentChange> pop() {
	    	final List<DocumentChange> temp = new LinkedList<DocumentChange>(changes);
	    	changes.clear();
	        return temp;
	    }
	}
    
    
    
	//当前运行的线程
	private static Future<?> runningTask = null;
	//排版线程池
    private static final ExecutorService propertyThreadPool
        = Executors.newCachedThreadPool();
  //TODO 上面是多线程的程序框架代码，由于需要对某些变量做细致的同步，目前没有时间，所以目前用保守的线程模式
	
}
