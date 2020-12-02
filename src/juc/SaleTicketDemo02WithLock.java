package juc;
//基本的卖票例子

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程开发，公司中的开发，降低耦合性
 * 1、线程就是一个单独的资源类，没有任何附属的操作
 * 资源：属性，方法
 */
public class SaleTicketDemo02WithLock {
    public static void main(String[] args) {

        //并发，多线程操作同一个资源类，把资源类丢入线程
        Ticket02 ticket = new Ticket02();
        new Thread(() -> { for (int i = 0; i < 60; i++) ticket.sale(); }, "a").start();
        new Thread(() -> { for (int i = 0; i < 60; i++) ticket.sale(); }, "b").start();
        new Thread(() -> { for (int i = 0; i < 60; i++) ticket.sale(); }, "c").start();

    }
}

//资源类 oop
class Ticket02 {
    //属性，方法
    private int nums = 50;

    //Lock三部曲
    //1、new ReentrantLock()
    //2、lock.lock()
    //3、lock.unlock()
    Lock lock = new ReentrantLock();

    //卖票的方式
    public synchronized void sale() {
        lock.lock();
        //lock.tryLock();尝试获得锁
        try {
            //业务代码
            if (nums > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出了" + nums-- + "票，剩余：" + nums);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}
