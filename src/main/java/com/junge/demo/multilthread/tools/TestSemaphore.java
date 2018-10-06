package com.junge.demo.multilthread.tools;

import java.util.concurrent.Semaphore;

/**
 * Created by feizi on 2018/6/5.
 */
public class TestSemaphore {
    public static void main(String[] args) {
        //工人数
        int N = 8;

        //机器数目
        Semaphore semaphore = new Semaphore(5);

        for (int i = 0; i < N; i++){
            new Worker(i, semaphore).start();
        }
    }

    static class Worker extends Thread{
        private int num;
        private Semaphore semaphore;

        public Worker(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                //通过信号量获取许可
                semaphore.acquire();

                System.out.println("工人 " + this.num + " 占用一个机器在生产...");
                Thread.sleep(2000);
                System.out.println("工人 "  + this.num + " 释放出机器...");

                //释放许可
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}