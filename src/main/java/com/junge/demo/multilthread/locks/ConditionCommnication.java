/**
 * 
 */
package com.junge.demo.multilthread.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 子线程打印10次，主线程打印100次，再子线程打印10次，主线程打印100次，这样交替50次。
 * 
 * @author "liuxj"
 * @date 2018年9月5日
 */
public class ConditionCommnication {

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
					business.sub(i);

				}

			}
		}).start();

		for (int i = 1; i <= 50; i++) {
			business.main(i);

		}

	}

}

class Bussiness {
	
	private Lock lock = new ReentrantLock();
	
	private Condition subCondition = lock.newCondition();
	private Condition mainCondition = lock.newCondition();
	
	private boolean isSub = true;

	public void sub(int i) {
		lock.lock();
		try {
			while (!isSub) {
				try {
					subCondition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for (int j = 1; j <= 10; j++) {
				System.out.println("sub thread " + j + " of count " + i);
			}
			isSub = false;
			mainCondition.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void main(int i) {
		lock.lock();
		try {
			while (isSub) {
				try {
					mainCondition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for (int j = 1; j <= 100; j++) {
				System.out.println("main thread " + j + " of count " + i);
			}
			isSub = true;
			subCondition.signal();
		} finally {
			lock.unlock();
		}
	}
}
