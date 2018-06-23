/**
 * 
 */
package com.junge.demo.multilthread.deadlock;

/**
 * 死锁
 * 
 * @author liuxj
 * @date 2018年6月23日
 */
public class DeadLock {

	public static void main(String[] args) {
		Task task = new Task();
		new Thread(new Worker1(task)).start();
		new Thread(new Worker2(task)).start();
	}
}

class Worker1 implements Runnable {

	private Task task;

	public Worker1(Task task) {
		this.task = task;
	}

	public void run() {
		task.doTask1();
	}

}

class Worker2 implements Runnable {

	private Task task;

	public Worker2(Task task) {
		this.task = task;
	}

	public void run() {
		task.doTask2();
	}

}

/**
 * 任务有2个子任务，每个子任务完成都需要获取2把锁，且获取锁的顺序相反
 * 
 * @author liuxj
 * @date 2018年6月23日
 */
class Task {

	private static final byte[] lock1 = new byte[1];
	private static final byte[] lock2 = new byte[1];

	public void doTask1() {
		synchronized (lock1) {
			System.out.println("拿到锁1，开始做任务1");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("任务1完成，开始获取锁2");
			synchronized (lock2) {
				System.out.println("拿到锁2，开始做任务2");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			System.out.println("任务2完成");
		}

		System.out.println("所有任务完成");
	}

	public void doTask2() {
		synchronized (lock2) {
			System.out.println("拿到锁2，开始做任务1");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("任务1完成，开始获取锁1");
			synchronized (lock1) {
				System.out.println("拿到锁1，开始做任务2");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			System.out.println("任务2完成");
		}

		System.out.println("所有任务完成");
	}

}
