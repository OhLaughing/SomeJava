package com.gustavo.thread;

import java.util.concurrent.TimeUnit;

public class ThreadStatusDemo_JoinWithTime {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Joinning());
        Thread t2 = new Thread(new Joinning1(t1));
        t1.start();
        t2.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println(t2.getState());
    }

    static class Joinning1 implements Runnable {

        Thread thread;

        public Joinning1(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                //时间设置的值保证外面调用getState()方式时，该线程没有结束即可
                thread.join(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Joinning implements Runnable {
        @Override
        public void run() {
            System.out.println("in thread.");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("out thread..");
        }
    }
}
