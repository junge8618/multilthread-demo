/**
 * 
 */
package com.junge.demo.multilthread.threadlocal;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.plaf.synth.SynthSpinnerUI;

/**
 * 描述类功能
 * 
 * @author "liuxj"
 * @date 2018年9月17日
 */
public class ThreadLocalTest {

	/**
	 * 描述方法功能和使用场景
	 * 
	 * @author "liuxj"
	 * @date 2018年9月17日
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					int data1 = new Random().nextInt();
					System.out.println(Thread.currentThread().getName() + " put data : " + data1);

					ContextDataHoldor.getThreadInstance().setAge(data1);
					ContextDataHoldor.getThreadInstance().setName("name" + data1);
					new A().getData();
					new B().getData();
				}
			}).start();
		}
		
		Runtime runtime = Runtime.getRuntime();
		runtime.addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("####################");
				try {
					TimeUnit.SECONDS.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}));

	}

	static class A {
		public void getData() {
			System.out.println("a thread of " + Thread.currentThread().getName() + " get data : "
					+ ContextDataHoldor.getThreadInstance());
		}
	}

	static class B {
		public void getData() {
			System.out.println("b thread of " + Thread.currentThread().getName() + " get data : "
					+ ContextDataHoldor.getThreadInstance());
		}
	}

}

class ContextDataHoldor {

	private ContextDataHoldor() {

	}

	private static ThreadLocal<ContextDataHoldor> threadInstance = new ThreadLocal<ContextDataHoldor>();

	public static ContextDataHoldor getThreadInstance() {
		ContextDataHoldor holdor = threadInstance.get();
		if (null == holdor) {
			holdor = new ContextDataHoldor();
			threadInstance.set(holdor);
		}

		return holdor;
	}

	private String name;
	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "ContextDataHoldor [name=" + name + ", age=" + age + "]";
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("finalize of " + this);
	}

}
