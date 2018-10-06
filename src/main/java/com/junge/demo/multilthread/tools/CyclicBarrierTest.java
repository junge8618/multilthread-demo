/**
 * 
 */
package com.junge.demo.multilthread.tools;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 所有线程彼此等待，都达到了集合点时再全部继续执行，可以重复进入等待
 * 
 * @author liuxj
 * @date 2018年10月6日
 */
public class CyclicBarrierTest {

	/**
	 * 描述方法功能和使用场景
	 * @author liuxj
	 * @date 2018年10月6日
	 * @param args
	 */
	public static void main(String[] args) {
		
		int count = 3;
		CyclicBarrier barrier = new CyclicBarrier(count);
		for (int i=1; i<=count; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						TimeUnit.SECONDS.sleep(new Random().nextInt(5));
						System.out.println("线程" + Thread.currentThread().getName() + "即将到达集合点1，到达集合点1的有：" + barrier.getNumberWaiting());
						barrier.await();
						
						TimeUnit.SECONDS.sleep(new Random().nextInt(5));
						System.out.println("线程" + Thread.currentThread().getName() + "即将到达集合点2，到达集合点2的有：" + barrier.getNumberWaiting());
						barrier.await();
						
						TimeUnit.SECONDS.sleep(new Random().nextInt(5));
						System.out.println("线程" + Thread.currentThread().getName() + "即将到达集合点3，到达集合点3的有：" + barrier.getNumberWaiting());
						barrier.await();
						
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					}
					
				}
			}).start();
		}

	}

}
