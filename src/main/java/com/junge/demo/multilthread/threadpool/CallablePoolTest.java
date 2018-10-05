/**
 * 
 */
package com.junge.demo.multilthread.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述类功能
 * @author liuxj
 * @date 2018年10月5日
 */
public class CallablePoolTest {

	/**
	 * 描述方法功能和使用场景
	 * @author liuxj
	 * @date 2018年10月5日
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(3);
		for (int i=1; i<=100; i++) {
			final int task = i;
			service.submit(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					for (int j=1; j<=10; j++) {
						System.out.println(Thread.currentThread().getName() + " loop " + j + " of task " + task);
//						System.out.println(j/(j-5));
					}
					return 1;
				}
			});
		}

	}

}
