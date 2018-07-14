/**
 * 
 */
package com.junge.demo.multilthread.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.DoubleAdder;

/**
 * @author liuxj
 *
 */
public class DoubleAdderTest {

	public static void main(String[] args) {
		DoubleAdder doubleAdder = new DoubleAdder();
		
		int count = 10000;

		CountDownLatch latch = new CountDownLatch(count);

		for (int i = 0; i < count; i++) {
			MyDoubleAdderThread thread = new MyDoubleAdderThread(doubleAdder, latch);
			thread.start();
		}

		try {
			latch.await();
			System.out.println(doubleAdder.doubleValue());

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}

class MyDoubleAdderThread extends Thread {
	private DoubleAdder doubleAdder = null;
	private CountDownLatch latch;

	public MyDoubleAdderThread(DoubleAdder doubleAdder, CountDownLatch latch) {
		this.doubleAdder = doubleAdder;
		this.latch = latch;
	}

	@Override
	public void run() {
		latch.countDown();
		doubleAdder.add(1.1);
	}
}

class MyDoubleAdder {
	private DoubleAdder doubleAdder;

	public MyDoubleAdder() {
		doubleAdder = new DoubleAdder();
	}

	public void add() {
		doubleAdder.add(1.1);
	}

	public double getValue() {
		return doubleAdder.doubleValue();
	}
}