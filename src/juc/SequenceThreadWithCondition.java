package juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 循环打印ABC，考察线程间通信，采用lock和condition
 */
public class SequenceThreadWithCondition {
    public static void main(String[] args) {
        Resource resource = new Resource();
        new Thread(() -> {
            for (int i = 0; i < 10; i ++) {
                resource.printA();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i ++) {
                resource.printB();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i ++) {
                resource.printC();
            }
        }, "C").start();
    }
}

//资源类
class Resource {
    private int signal = 1;//A1,B2,C3
    ReentrantLock reentrantLock = new ReentrantLock();
    //condition可以一个监视器监视一个线程
    Condition conditionA = reentrantLock.newCondition();
    Condition conditionB = reentrantLock.newCondition();
    Condition conditionC = reentrantLock.newCondition();

    public void printA() {
        reentrantLock.lock();
        try {
            while (signal != 1) {
                conditionA.await();
            }
            System.out.println(Thread.currentThread().getName()+"--->"+"AAAAAAAAAAAAAAAAA");
            signal = 2;
            conditionB.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void printB() {
        reentrantLock.lock();
        try {
            while (signal != 2) {
                conditionB.await();
            }
            System.out.println(Thread.currentThread().getName()+"--->"+"BBBBBBBBBBBBBBBBB");
            signal = 3;
            conditionC.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void printC() {
        reentrantLock.lock();
        try {
            while (signal != 3) {
                conditionC.await();
            }
            System.out.println(Thread.currentThread().getName()+"--->"+"CCCCCCCCCCCCCCCCC");
            signal = 1;
            conditionA.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }
}
