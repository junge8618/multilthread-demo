/**
 * 
 */
package com.junge.demo.multilthread.transynchrozed;

/**
 * 子线程打印10次，主线程打印100次，再子线程打印10次，主线程打印100次，这样交替50次。
 * 
 * @author "liuxj"
 * @date 2018年9月5日
 */
public class ThreadCommnication {

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
	
	private boolean isSub = true;

	public synchronized void sub(int i) {
		while (!isSub) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for (int j = 1; j <= 10; j++) {
			System.out.println("sub thread " + j + " of count " + i);
		}
		
		isSub = false;
		this.notify();
	}

	public synchronized void main(int i) {
		while (isSub) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for (int j = 1; j <= 100; j++) {
			System.out.println("main thread " + j + " of count " + i);
		}
		
		isSub = true;
		this.notify();
	}
}
