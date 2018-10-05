/**
 * 
 */
package com.junge.demo.multilthread.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;

/**
 * 线程池队列满了后，再添加任务策略： 
 * 1.AbortPolicy 新的任务直接终止，抛异常RejectedExecutionException
 * 2.CallerRunsPolicy 添加任务时直接调用线程的run方法 
 * 3.DiscardOldestPolicy 丢弃队列中最老的任务，添加新的任务到队尾 
 * 4.DiscardPolicy 直接丢弃，不做任何事
 * 
 * @author liuxj
 * @date 2018年10月5日
 */
public class ThreadPoolExecutorTest {

	/**
	 * 描述方法功能和使用场景
	 * 
	 * @author liuxj
	 * @date 2018年10月5日
	 * @param args
	 */
	public static void main(String[] args) {
		// ExecutorService executorService = new ThreadPoolExecutor(5, 8, 1,
		// TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(30), new
		// AbortPolicy());
		// ExecutorService executorService = new ThreadPoolExecutor(5, 8, 1,
		// TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(30), new
		// CallerRunsPolicy());
		// ExecutorService executorService = new ThreadPoolExecutor(5, 8, 1,
		// TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(30), new
		// DiscardOldestPolicy());
		ExecutorService executorService = new ThreadPoolExecutor(5, 8, 1, TimeUnit.MINUTES,
				new ArrayBlockingQueue<Runnable>(30), new DiscardPolicy());

		for (int i = 1; i <= 100; i++) {
			try {
				executorService.submit(new Task(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		executorService.shutdown();

	}

	static class Task implements Runnable {

		private int factor = 0;

		public Task(int factor) {
			this.factor = factor;
		}

		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(1);
				System.out.println(Thread.currentThread().getName() + " of cal result " + factor);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
