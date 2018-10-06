/**
 * 
 */
package com.junge.demo.multilthread.locks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 缓存数据设计
 * 读取数据使用读锁，写数据使用写锁
 * 
 * @author liuxj
 * @date 2018年10月5日
 */
public class CacheDataTest {

	/**
	 * 描述方法功能和使用场景
	 * @author liuxj
	 * @date 2018年10月5日
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		final String key = "key";
		CountDownLatch latch = new CountDownLatch(3);
		for (int i=0; i<3; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						latch.await();
						System.out.println(Thread.currentThread().getName() + " get data from cache : " + CacheData.getData(key));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					
				}
			}).start();
		}
		
		TimeUnit.MILLISECONDS.sleep(200);
		latch.countDown();
		latch.countDown();
		latch.countDown();
		TimeUnit.MILLISECONDS.sleep(200);
		
		System.out.println("fin");

	}

}

class CacheData {
	private static Map<String, Object> datas = new HashMap<String, Object>();
	
	private static ReadWriteLock lock = new ReentrantReadWriteLock();
	
	public static Object getData(String key) {
		lock.readLock().lock();
		Object value = datas.get(key);
		try {
			
			if (null == value) {
				// 释放读锁，获取写锁
				lock.readLock().unlock();
				lock.writeLock().lock();
				try {
					value = datas.get(key);
					if (null == value) {
						value = "ddddddd" + Math.round(100); //从数据库读取数据
						System.out.println(Thread.currentThread().getName() + " get data from db : " + value);
						datas.put(key, value);
					}
					
					lock.readLock().lock();
				} finally {
					lock.writeLock().unlock();
				}
				
			}
		} finally {
			lock.readLock().unlock();
		}
		
		return value;
	}
}
