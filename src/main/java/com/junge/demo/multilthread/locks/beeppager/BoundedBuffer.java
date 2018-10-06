/**
 * 
 */
package com.junge.demo.multilthread.locks.beeppager;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 有界缓冲区
 * 
 * @author liuxj
 * @date 2018年10月6日
 */
public class BoundedBuffer {
	
	// 缓冲区容量
	private int capacity;
	
	// 放数据指针
	private int putptr;
	
	// 获取数据指针
	private int takeptr;
	
	// 实际放的数据个数
	private int count;
	
	private Object[] items;

	final private Lock lock = new ReentrantLock();
	final private Condition notEmpty = lock.newCondition();
	final private Condition notFull = lock.newCondition(); 
	
	public BoundedBuffer(int capacity) {
		this.capacity = capacity;
		this.items = new Object[this.capacity];
	}

	public BoundedBuffer() {
		this(10);
	}
	
	public int put(Object x) throws InterruptedException {
		lock.lock();
		//System.out.println(this);
		try {
			while (count == this.capacity) {
				notFull.await();
			}
			
			int tmp = putptr;
			
			items[putptr++] = x;
			count++;
			if (putptr == this.capacity) {
				putptr = 0;
			}
			
			notEmpty.signal();
			
			return tmp;
		} finally {
			lock.unlock();
		}
	}
	
	public Object take() throws InterruptedException {
		lock.lock();
		
		try {
			
			while (count == 0) {
				notEmpty.await();
			}
			
			Object x = items[takeptr++];
			if (takeptr == this.capacity) {
				takeptr = 0;
			}
			
			count--;
			
			notFull.signal();
			
			return x;
		} finally {
			lock.unlock();
		}
	}
	

	@Override
	public String toString() {
		return "BoundedBuffer [capacity=" + capacity + ", putptr=" + putptr + ", takeptr=" + takeptr + ", count="
				+ count + ", items=" + Arrays.toString(items) + "]";
	}


	/**
	 * 描述方法功能和使用场景
	 * @author liuxj
	 * @date 2018年10月6日
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) {
		BoundedBuffer buffer = new BoundedBuffer(10000);
		
		// 启动5个线程，每秒放一个；启动1个线程，每秒读一个
		for (int i=1; i<=500; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						try {
							System.out.println(Thread.currentThread().getName() + " thread put data, index=" + buffer.put(new Object()));
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
				}
			}).start();
		}
		
		for (int i=1; i<=200; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						try {
							System.out.println(Thread.currentThread().getName() + " thread take data, data=" + buffer.take());
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
				}
			}).start();
		}

	}

}
