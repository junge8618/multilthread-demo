/**
 * 
 */
package com.junge.demo.multilthread.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * AtomicIntegerFieldUpdater是对对象的Integer成员进行原子操作
 * 
 * @author "liuxj"
 * @date 2018年9月18日
 */
public class AtomicIntegerFieldUpdaterTest {

	/**
	 * 描述方法功能和使用场景
	 * 
	 * @author "liuxj"
	 * @date 2018年9月18日
	 * @param args
	 */
	public static void main(String[] args) {
		final ShareData shareData = new ShareData();
		final AtomicIntegerFieldUpdater<ShareData> shareDataUpdater = AtomicIntegerFieldUpdater
				.newUpdater(ShareData.class, "age");

		for (int i = 0; i < 2; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int k = 0; k < 100; k++) {
						int j = shareDataUpdater.incrementAndGet(shareData);
						System.out.println("thread " + Thread.currentThread().getName() + " inc 1 :" + j);
					}

				}
			}).start();

			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int k = 0; k < 100; k++) {
						int j = shareDataUpdater.decrementAndGet(shareData);
						System.out.println("thread " + Thread.currentThread().getName() + " dec 1 :" + j);
					}

				}
			}).start();
		}

	}

}

class ShareData {
	public volatile int age = 0;

	@Override
	public String toString() {
		return "ShareData [age=" + age + "]";
	}

}
