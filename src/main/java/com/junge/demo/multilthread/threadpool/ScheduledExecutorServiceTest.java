/**
 * 
 */
package com.junge.demo.multilthread.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.FastDateFormat;

/**
 * Executors可以创建调度任务线程池ScheduledExecutorService
 * schedule : 只调度一次，运行Runnable，不返回结果；运行Callable，可以获取结果
 * scheduleAtFixedRate ： 调度多次，指定延迟调第一次，后面指定间隔调用（不包含任务执行时长）。
 * scheduleWithFixedDelay： 调度多次，指定延迟调第一次，后面指定间隔调用（包含任务执行时长）。
 * 
 * 描述类功能
 * 
 * @author liuxj
 * @date 2018年10月5日
 */
public class ScheduledExecutorServiceTest {
	
	private static FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

	/**
	 * 描述方法功能和使用场景
	 * 
	 * @author liuxj
	 * @date 2018年10月5日
	 * @param args
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
		
		System.out.println("cur time : " + format.format(System.currentTimeMillis()));

		ScheduledFuture<Integer> result = service.schedule(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				System.out.println(Thread.currentThread().getName() + " bombing111..." + format.format(System.currentTimeMillis()));
				return Integer.valueOf(1);
			}
		}, 4, TimeUnit.SECONDS);
		
		System.out.println("schedule result : " + result.get());
		
		service.schedule(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " bombing222..." + format.format(System.currentTimeMillis()));
				
			}
		}, 6, TimeUnit.SECONDS);
		
		service.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + " bombing333..." + format.format(System.currentTimeMillis()));
				
			}
		}, 8, 2, TimeUnit.SECONDS);
		
		service.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + " bombing555..." + format.format(System.currentTimeMillis()));
				
			}
		}, 9, 2, TimeUnit.SECONDS);

		//service.shutdown();
	}

}
