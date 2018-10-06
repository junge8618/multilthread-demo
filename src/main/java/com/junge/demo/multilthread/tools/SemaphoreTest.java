/**
 * 
 */
package com.junge.demo.multilthread.tools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore 信号灯(许可证管理器)，维护当前范访问自身的线程个数，并提供了机制。使用Semaphore可以控制同时访问资源的线程个数
 * 当控制线程数为1时可以实现互斥锁，资源可以由非获取锁资源的线程释放。
 * 可以一次获取多个许可
 * 
 * tryAcquire是非阻塞的，获取到了许可则返回true，继续执行，没有获取到许可返回false，也继续执行
 * 
 * Semaphore经常用于限制获取某种资源的线程数量。
 *  
 * @author liuxj
 * @date 2018年10月6日
 */
public class SemaphoreTest {

	/**
	 * 获取锁资源
	 * @author liuxj
	 * @date 2018年10月6日
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		
		int count = 3;
		Semaphore se = new Semaphore(count);
		for (int i=1; i<=10; i++) {
			service.submit(new Runnable() {
				@Override
				public void run() {
					try {
						se.acquire();
						System.out.println("线程" + Thread.currentThread().getName() + "已经获取资源，当前获取资源线程个数" + (count - se.availablePermits()));
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					System.out.println("线程" + Thread.currentThread().getName() + "准备离开");
					
					se.release();
					System.out.println("线程" + Thread.currentThread().getName() + "释放资源，当前获取资源线程个数" + (count - se.availablePermits()));
					
				}
			});
		}

	}

}
