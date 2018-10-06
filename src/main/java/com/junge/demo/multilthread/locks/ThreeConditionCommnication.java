/**
 * 
 */
package com.junge.demo.multilthread.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 子线程1打印10次，子线程2打印20次，子线程3打印30次，再子线程1打印10次，子线程2打印20次，子线程3打印30次，这样交替50次。
 * 
 * @author "liuxj"
 * @date 2018年9月5日
 */
public class ThreeConditionCommnication {

	/**
	 * 描述方法功能和使用场景
	 * 
	 * @author "liuxj"
	 * @date 2018年9月5日
	 * @param args
	 */
	public static void main(String[] args) {
		final Bussiness business = new Bussiness();

		new Thread(new Runnable() {
			public void run() {
				for (int i = 1; i <= 50; i++) {
					business.sub1(i);

				}

			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				for (int i = 1; i <= 50; i++) {
					business.sub2(i);

				}

			}
		}).start();

		for (int i = 1; i <= 50; i++) {
			business.sub3(i);

		}

	}

	static class Bussiness {

		private Lock lock = new ReentrantLock();

		private Condition subCondition1 = lock.newCondition();
		private Condition subCondition2 = lock.newCondition();
		private Condition subCondition3 = lock.newCondition();

		private int runthread = 1;

		public void sub1(int i) {
			lock.lock();
			try {
				while (1 != runthread) {
					try {
						subCondition1.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for (int j = 1; j <= 10; j++) {
					System.out.println("sub1 thread " + j + " of count " + i);
				}
				runthread = 2;
				subCondition2.signal();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}

		public void sub2(int i) {
			lock.lock();
			try {
				while (2 != runthread) {
					try {
						subCondition2.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for (int j = 1; j <= 20; j++) {
					System.out.println("sub2 thread " + j + " of count " + i);
				}
				runthread = 3;
				subCondition3.signal();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}

		public void sub3(int i) {
			lock.lock();
			try {
				while (3 != runthread) {
					try {
						subCondition3.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for (int j = 1; j <= 30; j++) {
					System.out.println("sub3 thread " + j + " of count " + i);
				}
				runthread = 1;
				subCondition1.signal();
			} finally {
				lock.unlock();
			}
		}
	}

}
