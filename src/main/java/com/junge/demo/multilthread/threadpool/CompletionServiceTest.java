/**
 * 
 */
package com.junge.demo.multilthread.threadpool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMResult;

/**
 * CompletionService用于批量提交任务，异步获取任务结果
 * 
 * @author liuxj
 * @date 2018年10月5日
 */
public class CompletionServiceTest {

	/**
	 * 描述方法功能和使用场景
	 * 
	 * @author liuxj
	 * @date 2018年10月5日
	 * @param args
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException, Exception {
		Executor e = Executors.newFixedThreadPool(300);
		CompletionService<Result> ecs = new ExecutorCompletionService<Result>(e);

		Collection<Callable<Result>> solvers = new ArrayList<Callable<Result>>();
		for (int i = 1; i <= 10000; i++) {
			solvers.add(new Callable<Result>() {
				@Override
				public Result call() throws Exception {
					TimeUnit.SECONDS.sleep(new Random().nextInt(10));
					System.out.println("dddddddddddddddd");
					return new DOMResult();
				}
			});
		}

		for (Callable<Result> s : solvers)
			ecs.submit(s);
		int n = solvers.size();
		for (int i = 0; i < n; ++i) {
			Result r = ecs.take().get();
			if (r != null)
				use(r);
		}

	}
	
	private static void use(Result r) {
		System.out.println(r);
	}

}
