/**
 * 
 */
package com.junge.demo.multilthread.tools;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 2个线程交换数据完成后才接着执行
 * 
 * @author liuxj
 * @date 2018年10月6日
 */
public class ExchangerTest {

	/**
	 * 描述方法功能和使用场景
	 * @author liuxj
	 * @date 2018年10月6日
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		
		Exchanger<String> exchanger = new Exchanger<>();
		
		service.execute(new Runnable() {
			@Override
			public void run() {
				
				try {
					String data1 = "money";
					System.out.println(Thread.currentThread().getName() + "正准备交换出" + data1);
					Thread.sleep((long)(1000 * Math.random()));
					
					String data2 = exchanger.exchange(data1);
					System.out.println(Thread.currentThread().getName() + "交换数据完成，交入数据为" + data2);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		
		service.execute(new Runnable() {
			@Override
			public void run() {
				
				try {
					String data1 = "fangbianmian";
					System.out.println(Thread.currentThread().getName() + "正准备交换出" + data1);
					Thread.sleep((long)(1000 * Math.random()));
					
					String data2 = exchanger.exchange(data1);
					System.out.println(Thread.currentThread().getName() + "交换数据完成，交入数据为" + data2);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		
		service.shutdown();

	}

}
