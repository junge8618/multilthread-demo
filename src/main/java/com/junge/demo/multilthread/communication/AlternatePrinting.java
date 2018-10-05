/**
 * 
 */
package com.junge.demo.multilthread.communication;

/**
 * 交替打印：
 * 启动2个线程，线程1打印50次，线程2打印100次，再线程1打印50次，线程2打印100次这样交替打印下去，共打印100次。
 * 
 * @author liuxj
 * @date 2018年10月5日
 */
public class AlternatePrinting {

	/**
	 * 描述方法功能和使用场景
	 * @author liuxj
	 * @date 2018年10月5日
	 * @param args
	 */
	public static void main(String[] args) {
		final Printing printing = new Printing();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i=1; i<=100; i++) {
					printing.print1(i);
				}
			}
			
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i=1; i<=100; i++) {
					printing.print2(i);
				}
			}
		}).start();

	}

}

class Printing {
	private boolean isPritng1 = true;
	
	public synchronized void print1(int round) {
		while(!isPritng1) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for (int i=1; i<=50; i++) {
			System.out.println("thread of " + Thread.currentThread().getName() + " print round of " + round + " count : " + i);
		}
		
		isPritng1 = false;
		
		this.notify();
	}
	
	public synchronized void print2(int round) {
		while(isPritng1) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for (int i=1; i<=100; i++) {
			System.out.println("thread of " + Thread.currentThread().getName() + " print round of " + round + " count : " + i);
		}
		
		isPritng1 = true;
		
		this.notify();
	}
}