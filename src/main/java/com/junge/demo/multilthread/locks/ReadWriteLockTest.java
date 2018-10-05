/**
 * 
 */
package com.junge.demo.multilthread.locks;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁：可以同时读，但是不能同时读、写，不能同时写写。
 * 
 * 三个线程读取数据，三个线程写数据。
 * 
 * @author liuxj
 * @date 2018年10月5日
 */
public class ReadWriteLockTest {

	/**
	 * 描述方法功能和使用场景
	 * @author liuxj
	 * @date 2018年10月5日
	 * @param args
	 */
	public static void main(String[] args) {
		ShareData data = new ShareData();
		for (int i=0; i<3; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j=0; j<10; j++) {
						data.read();
					}
					
				}
			}).start();
		}
		
		for (int i=0; i<3; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j=0; j<10; j++) {
						data.write(new Random().nextInt(10000));
					}
					
				}
			}).start();
		}

	}

}

class ShareData {
	private Object data = null;
	
	ReadWriteLock lock = new ReentrantReadWriteLock(true);
	
	public void read() {
		try {
			lock.readLock().lock();
			System.out.println(Thread.currentThread().getName() + " 准备读取数据");
			TimeUnit.MILLISECONDS.sleep((long)(Math.random() * 1000));
			System.out.println(Thread.currentThread().getName() + " 已完成读取数据: " + data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.readLock().unlock();
		}
		
	}
	
	public void write(Object data) {
		try {
			lock.writeLock().lock();
			System.out.println(Thread.currentThread().getName() + " 准备写数据");
			TimeUnit.MILLISECONDS.sleep((long)(Math.random() * 1000));
			this.data = data;
			System.out.println(Thread.currentThread().getName() + " 已完成写数据: " + data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.writeLock().unlock();
		}
		
	}
}
