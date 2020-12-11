package juc.BlockingQueueDemo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * SynchronousQueue
 * 容量为1，put一个之后，必须take后才能接着put
 */
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        BlockingQueue<Object> queue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "生产了" + 1);
                queue.put(1);
                System.out.println(Thread.currentThread().getName() + "生产了" + 2);
                queue.put(2);
                System.out.println(Thread.currentThread().getName() + "生产了" + 3);
                queue.put(3);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "生产者").start();

        new Thread(() -> {
            Integer product = null;
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName().toString() + "消费了" + queue.take());
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName().toString() + "消费了" + queue.take());
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName().toString() + "消费了" + queue.take());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "消费者").start();
    }
}
