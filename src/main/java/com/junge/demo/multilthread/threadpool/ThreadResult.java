/**
 * 
 */
package com.junge.demo.multilthread.threadpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 描述类功能
 * @author liuxj
 * @date 2018年10月5日
 */
public class ThreadResult {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService service = Executors.newFixedThreadPool(30);
		for (int i=1; i<=100; i++) {
			Future<Integer> future = service.submit(new ResultRunnable(i), i);
			System.out.println(future.get());
		}
		
		service.shutdown();
	}

}

class ResultRunnable implements Runnable {
	private Integer result;
	
	public ResultRunnable(Integer result) {
		this.result = result;
	}
	
	@Override
	public void run() {
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("返回结果:" + result);
		
	}
}
