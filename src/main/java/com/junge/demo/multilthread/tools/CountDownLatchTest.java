/**
 * 
 */
package com.junge.demo.multilthread.tools;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 可以实现：
 * 1.一个条件触发多个任务(田径赛跑，哨声一响，运动员开始跑)
 * 2.多个任务触发一个条件(运动员跑到终点，裁判开始统计成绩)
 * 
 * @author liuxj
 * @date 2018年10月6日
 */
public class CountDownLatchTest {

	/**
	 * 描述方法功能和使用场景
	 * @author liuxj
	 * @date 2018年10月6日
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		CountDownLatch latch1 = new CountDownLatch(1);
		CountDownLatch latch2 = new CountDownLatch(3);
		for (int i=0; i<3; i++) {
			service.execute(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println(Thread.currentThread().getName() + "已经准备接收命令");
						latch1.await();
						System.out.println(Thread.currentThread().getName() + "接收命令成功");
						
						Thread.sleep((long)(1000 * Math.random()));
						System.out.println(Thread.currentThread().getName() + "已经到达终点，发送命令");
						latch2.countDown();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			});
		}
		
		try {
			
			Thread.sleep((long)(1000 * Math.random()));
			System.out.println(Thread.currentThread() + "发送命令");
			latch1.countDown();
			
			latch2.await();
			System.out.println(Thread.currentThread() + "接收终点命令完成");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		service.shutdown();

	}

}
