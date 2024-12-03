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

import java.util.Collection;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.swing.SwingUtilities;

/**
 * 调整有关线程池的地方
 * 
 * @author 闫舒寰
 * @version 1.0 2009/04/27
 */
public enum WiseDocThreadService
{

	Instance;

	public static final long DELAYTIME;

	private final int processorCount = Runtime.getRuntime()
			.availableProcessors();

	static
	{
		// 根据cpu数采用不同的延迟数
		if (Runtime.getRuntime().availableProcessors() >= 4)
		{
			DELAYTIME = 200l;
		} else
		{
			DELAYTIME = 300l;
		}
	}
	/**
	 * 专门用于处理undo的线程池
	 */
	// private final ScheduledExecutorService undoService =
	// Executors.newSingleThreadScheduledExecutor();
	/**
	 * 专门用于积累文档变化的线程池
	 */
	private final ScheduledExecutorService docChangeService = Executors
			.newSingleThreadScheduledExecutor();

	/**
	 * undo在线程池中积累的方式
	 * 
	 * @param undoTask
	 *            文档变化线程积累任务
	 */
	// public void doUndoService(final Runnable undoTask) {
	// undoService.scheduleAtFixedRate(undoTask, DELAYTIME, DELAYTIME,
	// TimeUnit.MILLISECONDS);
	// }
	/**
	 * 文档改变在文档线程池中的积累方式
	 * 
	 * @param docChangeTask
	 *            文档变化线程积累任务
	 */
	public void doDocChangeService(final Runnable docChangeTask)
	{
		docChangeService.scheduleAtFixedRate(docChangeTask, DELAYTIME,
				DELAYTIME, TimeUnit.MILLISECONDS);
	}

	// 排版线程池
	// private final ExecutorService pageLayoutService =
	// Executors.newSingleThreadExecutor();

	/**
	 * 检测当前是否有正在排版的线程
	 */
	public boolean isDoingLayout()
	{
		return !pageLayoutPool.getQueue().isEmpty();
	}

	/**
	 * 做文档排版任务，新任务放到排版队列中
	 * 
	 * @param layoutTask
	 */
	public void doPageLayoutService(final Runnable layoutTask)
	{

		final FutureTask<?> fult = new FutureTask<Object>(layoutTask, null);

		// 目前低于双核的cpu由于数据吞吐量不够，不开启多线程排版
		if (processorCount < 1)
		{
			SwingUtilities.invokeLater(layoutTask);
		} else
		{
			// pageLayoutService.execute(layoutTask);

			// 在把排版任务放到排版队列之前先获得当前排版任务，（或者当前队列的任务），取消，然后再把新任务放到排版队列中
			final Runnable last = pageLayoutPool.getQueue().poll();
			if (last != null)
			{
				if (last instanceof FutureTask<?>)
				{
					final FutureTask<?> fuLast = (FutureTask<?>) last;
					if (!fuLast.isDone())
					{
						fuLast.cancel(true);
					}
				}
			}
			pageLayoutPool.execute(fult);
		}
	}

	/**
	 * 同时只能有一个线程运行的自定义的线程池
	 */
	private static final ThreadPoolExecutor pageLayoutPool = new ThreadPoolExecutor(
			1, 1, 0L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>());

	// ExecutorService cs = new
	// FinalizableDelegatedExecutorService(pageLayoutPool);

	static class FinalizableDelegatedExecutorService extends
			DelegatedExecutorService
	{
		FinalizableDelegatedExecutorService(final ExecutorService executor)
		{
			super(executor);
		}

		@Override
		protected void finalize()
		{
			super.shutdown();
		}
	}

	/**
	 * A wrapper class that exposes only the ExecutorService methods of an
	 * ExecutorService implementation.
	 */
	static class DelegatedExecutorService extends AbstractExecutorService
	{
		private final ExecutorService e;

		DelegatedExecutorService(final ExecutorService executor)
		{
			e = executor;
		}

		public void execute(final Runnable command)
		{
			e.execute(command);
		}

		public void shutdown()
		{
			e.shutdown();
		}

		public List<Runnable> shutdownNow()
		{
			return e.shutdownNow();
		}

		public boolean isShutdown()
		{
			return e.isShutdown();
		}

		public boolean isTerminated()
		{
			return e.isTerminated();
		}

		public boolean awaitTermination(final long timeout, final TimeUnit unit)
				throws InterruptedException
		{
			return e.awaitTermination(timeout, unit);
		}

		@Override
		public Future<?> submit(final Runnable task)
		{
			return e.submit(task);
		}

		@Override
		public <T> Future<T> submit(final Callable<T> task)
		{
			return e.submit(task);
		}

		@Override
		public <T> Future<T> submit(final Runnable task, final T result)
		{
			return e.submit(task, result);
		}

		@Override
		public <T> List<Future<T>> invokeAll(
				final Collection<? extends Callable<T>> tasks)
				throws InterruptedException
		{
			return e.invokeAll(tasks);
		}

		@Override
		public <T> List<Future<T>> invokeAll(
				final Collection<? extends Callable<T>> tasks,
				final long timeout, final TimeUnit unit)
				throws InterruptedException
		{
			return e.invokeAll(tasks, timeout, unit);
		}

		@Override
		public <T> T invokeAny(final Collection<? extends Callable<T>> tasks)
				throws InterruptedException, ExecutionException
		{
			return e.invokeAny(tasks);
		}

		@Override
		public <T> T invokeAny(final Collection<? extends Callable<T>> tasks,
				final long timeout, final TimeUnit unit)
				throws InterruptedException, ExecutionException,
				TimeoutException
		{
			return e.invokeAny(tasks, timeout, unit);
		}
	}

	/**
	 * 处理输入事件时的线程池，目前（2009年4月28日）暂时不用
	 */
	// private final ExecutorService inputService =
	// Executors.newSingleThreadExecutor();
	//
	// public void doInputService(final Runnable inputTask) {
	// inputService.execute(inputTask);
	// }
	/**
	 * 设置属性的线程池
	 */
	private final ExecutorService setPropertyService = Executors
			.newSingleThreadExecutor();

	public void doSetPropertyService(final Callable<Object> call)
	{
		setPropertyService.execute(new FutureTask<Object>(call));
	}

}
