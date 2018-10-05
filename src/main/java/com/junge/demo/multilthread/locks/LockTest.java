/**
 * 
 */
package com.junge.demo.multilthread.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock和传统的synchronize相比，Lock更面向对象
 * 
 * @author "liuxj"
 * @date 2018年9月2日
 */
public class LockTest {

	/**
	 * 描述方法功能和使用场景
	 * 
	 * @author "liuxj"
	 * @date 2018年9月2日
	 * @param args
	 */
	public static void main(String[] args) {

		new LockTest().init();
	}

	private void init() {
		final Outputer outputer = new Outputer();
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						TimeUnit.MILLISECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					outputer.print1("zhangsan111111111111111111111111111111");
				}

			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						TimeUnit.MILLISECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					outputer.print1("lisi222222222222222222222222222222222");
				}

			}
		}).start();
	}

	static class Outputer {

		private Lock lock = new ReentrantLock();

		private static void print(String outputstr) {
			for (int i = 0; i < outputstr.length(); i++) {
				System.out.print(outputstr.charAt(i));
			}

			System.out.println();
		}

		public void print1(String outputstr) {
			try {
				lock.lock();
				print(outputstr);
			} finally {
				lock.unlock();
			}
		}

		public synchronized void print2(String outputstr) {
			print(outputstr);
		}

		public static synchronized void print3(String outputstr) {
			print(outputstr);
		}

		public void print4(String outputstr) {
			synchronized (Outputer.class) {
				print(outputstr);
			}
		}
	}

}
