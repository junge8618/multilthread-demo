package com.junge.demo.multilthread.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.function.DoubleBinaryOperator;

public class DoubleAccumulatorTest {

	public static void main(String[] args) {
		MyAccumulator myAccumulator = new MyAccumulator();
		
		CountDownLatch latch = new CountDownLatch(10);
		
		for(int i=0;i<10;i++) {
			MyAccumulatorThread thread = new MyAccumulatorThread(myAccumulator, latch);
			thread.start();
		}
		
		try {
			latch.await();
			System.out.println(myAccumulator.getValue());
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}

class MyAccumulatorThread extends Thread {
	private MyAccumulator myAccumulator = null;
	private CountDownLatch latch;
	public MyAccumulatorThread(MyAccumulator myAccumulator,CountDownLatch latch) {
		this.myAccumulator = myAccumulator;
		this.latch = latch;
	}
	
	@Override
	public void run() {
		latch.countDown();
		myAccumulator.accumulatorValue();
	}
}

class MyAccumulator {
	private DoubleAccumulator accumulatorValue;
	
	public MyAccumulator() {
		accumulatorValue = new DoubleAccumulator(new MyDoubleBinaryOperator(), 0);
	}
	
	public void accumulatorValue() {
		accumulatorValue.accumulate(1.1);
	}
	
	public double getValue() {
		return accumulatorValue.doubleValue();
	}
}

class MyDoubleBinaryOperator implements DoubleBinaryOperator {

	@Override
	public double applyAsDouble(double left, double right) {
		//System.out.println("left:" + left + ",right:" + right);
		return left + right;
	}
	
}