/**
 * 
 */
package com.junge.demo.multilthread.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executors可以获取
 * 缓存线程池：按需分配线程
 * 单线程池：只分配一个线程
 * 固定线程池：指定大小线程池
 * 
 * @author liuxj
 * @date 2018年10月5日
 */
public class ExecutorsTest {

	/**
	 * 描述方法功能和使用场景
	 * @author liuxj
	 * @date 2018年10月5日
	 * @param args
	 */
	public static void main(String[] args) {
		//ExecutorService executorService = Executors.newCachedThreadPool();
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		//ExecutorService executorService = Executors.newFixedThreadPool(2);
		
		for (int i=0; i<100; i++) {
			executorService.submit(new Task(i));
		}
		
		executorService.shutdown();

	}

}

class Task implements Runnable {
	
	private int factor = 0;
	
	public Task(int factor) {
		this.factor = factor;
	}
	
	@Override
	public void run() {
		try {
			int result = 10 / (this.factor - 5);
			System.out.println(Thread.currentThread().getName() + " of cal result " + result);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
}
