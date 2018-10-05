/**
 * 
 */
package com.junge.demo.multilthread.transynchrozed;

import java.util.concurrent.TimeUnit;

/**
 * synchronize加到方法内部或者方法签名上，是对同一个对象进行互斥访问
 * 静态方法添加synchronize是对类的所有对象都进行互斥访问
 * 
 * 若锁住的不是同一个对象（或者类），同步锁不会生效
 * 
 * @author "liuxj"
 * @date 2018年9月2日
 */
public class TraditionSynchrozed {

	/**
	 * 描述方法功能和使用场景
	 * 
	 * @author "liuxj"
	 * @date 2018年9月2日
	 * @param args
	 */
	public static void main(String[] args) {

		new TraditionSynchrozed().init();
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

					outputer.print3("zhangsan111111111111111111111111111111");
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

					outputer.print4("lisi222222222222222222222222222222222");
				}

			}
		}).start();
	}

	static class Outputer {
		
		private static void print(String outputstr) {
			for (int i = 0; i < outputstr.length(); i++) {
				System.out.print(outputstr.charAt(i));
			}

			System.out.println();
		}
		
		public void print1(String outputstr) {
			synchronized (this) {
				print(outputstr);
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
