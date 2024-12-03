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
package com.wisii.wisedoc.view.ui.ribbon;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class TestMain {
	
	public TestMain() {
		
		long start = System.currentTimeMillis();
		System.out.println("run..." + start);
		
		
		/*try {
			Thread.currentThread().sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		long end = System.currentTimeMillis();
		System.out.println("finish..." + end);
		
		System.out.println("total: " + (end - start));
	}
	
	private final ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1);
	
	
	private class TimeThread implements ThreadFactory {

		@Override
		public Thread newThread(Runnable r) {
			System.out.println("hihi");
			return null;
		}
		
	}
	
	private class TimeTest implements Runnable {

		@Override
		public void run() {
			System.out.println("run...");
		}
		
	}
	

	public void beepForAnHour() {
		final Runnable beeper = new Runnable() {
			public void run() {
				System.out.println("beep");
			}
		};
		
		
	
		final ScheduledThreadPoolExecutor ect = new ScheduledThreadPoolExecutor(1);
		
		List<Runnable> tasks = new LinkedList<Runnable>();
		
		for (int i = 0; i < 100; i++) {
			
			tasks.add(new TimeTest());
		}
		
		System.out.println(tasks.size());
		
		for (Runnable runnable : tasks) {
			ect.schedule(runnable, 2000, TimeUnit.MILLISECONDS);
		}
		
		
		
		System.out.println(ect.getCompletedTaskCount());
		
		
		
		
		try {
			ect.awaitTermination(3000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		scheduler.i
		
//		final ScheduledFuture<?> beeperHandle = scheduler.scheduleWithFixedDelay(
//				beeper, 100, 100, TimeUnit.MILLISECONDS);
		
		/*scheduler.schedule(new Runnable() {
			public void run() {
				beeperHandle.cancel(true);
			}
		}, Long.MAX_VALUE, TimeUnit.MILLISECONDS);*/
	}
	
	
	  
	private static class ChangeDoc implements Runnable {
	  
	    private String producerName = null;
	  
	    private ChangePool storeHouse = null;
	  
	    public ChangeDoc(String producerName, ChangePool storeHouse) {
	        this.producerName = producerName;
	        this.storeHouse = storeHouse;
	    }
	  
	    public void setProducerName(String producerName) {
	        this.producerName = producerName;
	    }
	  
	    public String getProducerName() {
	        return producerName;
	    }
	      
	    public void produceProduct() {
	        int i = 0;
	        while (true) {
	            i++;
	            Product pro = new Product(i);
	            storeHouse.push(pro);
	            System.out.println(getProducerName() + " 生产了 " + pro);
	            try {
	                Thread.sleep(1000);
	            } catch (InterruptedException e) {
	                return;
	            }
	        }
	    }
	  
	    public void run() {
	        produceProduct();
	    }
	}
	
	
	public static class DoLayout implements Runnable {
	      
	    private String consumerName = null;
	      
	    private ChangePool storeHouse = null;
	  
	    public DoLayout(String consumerName, ChangePool storeHouse) {
	        this.consumerName = consumerName;
	        this.storeHouse = storeHouse;
	    }
	      
	    public void setConsumerName(String consumerName) {
	        this.consumerName = consumerName;
	    }
	      
	    public String getConsumerName() {
	        return consumerName;
	    }
	      
	    public void consumerProduct() {
	        while (true) {
	            System.out.println(getConsumerName() + " 消费了 " + storeHouse.pop());
	            try {
	                Thread.sleep(2000);
	            } catch (InterruptedException e) {
	                return;
	            }
	        }
	    }
	  
	    public void run() {
	        consumerProduct();
	    }
	}
	
	public static class Product {
		  
	    private int productId = 0;
	      
	    public Product(int productId) {
	        this.productId = productId;
	    }
	  
	    public int getProductId() {
	        return productId;
	    }
	  
	    @Override
		public String toString() {
	        return Integer.toString(productId);
	    }
	}
	
	
	public static class ChangePool {
		  
	    private int base = 0;
	  
	    private int top = 0;
	  
	    private Product[] products = new Product[10];
	  
	    public synchronized void push(Product product) {
	        while (top == products.length) {
	            notify();
	            try {
	                System.out.println("仓库已满，正等待消费...");
	                wait();
	            } catch (InterruptedException e) {
	                System.out.println("stop push product because other reasons");
//	              <strong>必须用while，因为当线程在wait的时候被打断，那么程序会跳出if而去执行下一条语句</strong>
	            }
	        }
	        products[top] = product;
	        top++;
	    }
	  
	    public synchronized Product pop() {
	        Product pro = null;
	        while (top == base) {
	            notify();
	            try {
	                System.out.println("仓库已空，正等待生产...");
	                wait();
	            } catch (InterruptedException e) {
	                System.out.println("stop push product because other reasons");
//	              <strong>必须用while，因为当线程在wait的时候被打断，那么程序会跳出if而去执行下一条语句</strong>
	            }
	        }
	        top--;
	        pro = products[top];
	        products[top] = null;
	        return pro;
	    }
	}
	
	public static void main(String[] args) {
		
		ChangePool storeHouse = new TestMain.ChangePool();
        ChangeDoc producer = new TestMain.ChangeDoc("生产者", storeHouse);
        DoLayout comsumer = new TestMain.DoLayout("消费者", storeHouse);
        Thread t1 = new Thread(producer);
        Thread t2 = new Thread(comsumer);
        t1.start();
        t2.start();
		
		
		
//		new TestMain().beepForAnHour();
	}
	
	

}
