package com.junge.demo.multilthread.test;

public class YieldSynchronizedTest {

	final static Object lockObj = new Object();

	public static void main(String[] args) {
		SynchronizedThreadA t1 = new SynchronizedThreadA("t1");
		SynchronizedThreadA t2 = new SynchronizedThreadA("t2");
		t1.start();
		t2.start();

	}

}

class SynchronizedThreadA extends Thread {
	
	public SynchronizedThreadA(String name) {
		super(name);
	}

	public void run() {

		synchronized (YieldSynchronizedTest.lockObj) {
			for (int i = 0; i < 10; i++) {
				System.out.printf("%s [%d]:%d\n", this.getName(), this.getPriority(), i);
				// i整除4时，调用yield
				if (i % 4 == 0)
					Thread.yield();
			}
		}
	}
}