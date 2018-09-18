/**
 * 
 */
package com.junge.demo.multilthread.transynchrozed;

/**
 * 启动4个线程，2个线程数据进行加1操作，另2个线程对数据进行减1操作
 * 
 * @author "liuxj"
 * @date 2018年9月18日
 */
public class MutilThreadShardData {

	/**
	 * 描述方法功能和使用场景
	 * 
	 * @author "liuxj"
	 * @date 2018年9月18日
	 * @param args
	 */
	public static void main(String[] args) {
		final ShareData data = new ShareData();

		for (int i = 0; i < 2; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					for (int j=0; j<100; j++) {
						data.inc();
					}

				}
			}).start();

			new Thread(new Runnable() {

				@Override
				public void run() {
					for (int j=0; j<100; j++) {
						data.dec();
					}

				}
			}).start();
		}

	}

}

class ShareData {
	private int i = 0;

	public synchronized void inc() {
		i++;
		System.out.println("thread " + Thread.currentThread().getName() + " inc 1 :" + i);
	}

	public synchronized void dec() {
		i--;
		System.out.println("thread " + Thread.currentThread().getName() + " dec 1 :" + i);
	}
}
