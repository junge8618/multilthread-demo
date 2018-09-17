/**
 * 
 */
package com.junge.demo.multilthread.threadlocal;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述类功能
 * @author "liuxj"
 * @date 2018年9月17日
 */
public class ThreadScopeShareData {
	
	private static Map<Thread, Integer> localHolder = new ConcurrentHashMap<>();

	/**
	 * 描述方法功能和使用场景
	 * @author "liuxj"
	 * @date 2018年9月17日
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i=0; i<2; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					int data1 = new Random().nextInt();
					System.out.println(Thread.currentThread().getName() + " put data : " + data1);
					
					localHolder.put(Thread.currentThread(), data1);
					new A().getData();
					new B().getData();
				}
			}).start();
		}

	}
	
	static class A {
		public void getData() {
			System.out.println("a thread of " + Thread.currentThread().getName() + " get data : " + localHolder.get(Thread.currentThread()));
		}
	}
	
	static class B {
		public void getData() {
			System.out.println("b thread of " + Thread.currentThread().getName() + " get data : " + localHolder.get(Thread.currentThread()));
		}
	}

}
