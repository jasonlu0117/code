package com.jl.template;

/**
 * 面试题
 */
public class  DailyCodesTemplate8 {

    private static final Object lock = new Object();
    private static boolean isOne = true;
    private static int oneCount = 1;
    private static int twoCount = 1;

    /**
     * 两个线程交替打印1-100，即
     * 线程1-1
     * 线程2-1
     * 线程1-2
     * 线程2-2
     * 线程1-3
     * 线程2-3
     */
    public static void main(String[] args) {
        new Thread(new Runnable(){
            public void run() {
                for (; oneCount <= 100; ) {
                    synchronized (lock) {
                        if (isOne) {
                            System.out.println("线程1-" + oneCount);
                            isOne = false;
                            oneCount++;
                            lock.notifyAll();
                        } else {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }).start();

        new Thread(new Runnable(){
            public void run() {
                for (; twoCount <= 100; ) {
                    synchronized (lock) {
                        if (!isOne) {
                            System.out.println("线程2-" + twoCount);
                            isOne = true;
                            twoCount++;
                            lock.notifyAll();
                        } else {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }).start();
    }


    public static void threadPrint1() {
        new Thread(new Runnable() {
            public void run() {
                for (;oneCount <= 100;) {
                    while (!isOne) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("thread1: " + oneCount);
                    oneCount++;
                    isOne = false;
                    lock.notifyAll();
                }
            }
        });

        new Thread(new Runnable() {
            public void run() {
                for (;twoCount <= 100;) {
                    while (isOne) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("thread2: " + twoCount);
                    twoCount++;
                    isOne = true;
                    lock.notifyAll();
                }
            }
        });
    }

}
