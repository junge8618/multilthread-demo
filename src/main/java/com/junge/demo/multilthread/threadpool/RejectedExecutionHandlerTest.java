/**
 * 
 */
package com.junge.demo.multilthread.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池队列满了后，再添加任务自定义策略：
 * 任务添加失败后，添加到二级队列，在循环遍历添加到线程池 
 * @author liuxj
 * @date 2018年10月5日
 */
public class RejectedExecutionHandlerTest {


	/**
	 * 描述方法功能和使用场景
	 * 
	 * @author liuxj
	 * @date 2018年10月5日
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<Runnable> secondQueue = new LinkedBlockingQueue<>();
		
		ExecutorService executorService = new ThreadPoolExecutor(5, 8, 1, TimeUnit.MINUTES,
				new ArrayBlockingQueue<Runnable>(30), new RejectedExecutionHandler() {
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				System.out.println("TTTTTTTTT:" + r);
				try {
					secondQueue.put(r);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		});

		for (int i = 1; i <= 100; i++) {
			try {
				executorService.submit(new Task(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		while (!secondQueue.isEmpty()) {
			TimeUnit.MILLISECONDS.sleep(100);
			Runnable task = secondQueue.poll();
			if (null != task) {
				executorService.submit(task);
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

		@Override
		public String toString() {
			return "Task [factor=" + factor + "]";
		}
		
	}
}
