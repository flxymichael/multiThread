package juc.BlockingQueueDemo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞：取时为空阻塞，加时为满阻塞，类似生产者消费者模型
 * 应用场景：多线程并发，线程池
 * 操作：                   添加、             删除、         查看队首元素
 * 四组API:
 * 1、抛出异常               add               remove        element
 * 2、不会抛出异常（正常返回）  offer             poll          peak
 * 3、阻塞等待               put               take
 * 4、超时等待               offer(num，time)  poll(num,time)
 */
public class BlockingQueueDemo {
    public static void main(String[] args) {
        try {
            test4();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //1、抛出异常
    public static void test1() {
        ArrayBlockingQueue<Object> queue = new ArrayBlockingQueue<>(3);

        System.out.println(queue.add("a"));
        System.out.println(queue.add("b"));
        System.out.println(queue.add("c"));


        System.out.println(queue.element());
        //抛出队列已满异常
//        System.out.println(queue.add("a"));

        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());

        //抛出队列已空异常
//        System.out.println(queue.remove());
    }

    //2、不会抛出异常（正常返回）
    public static void test2() {
        ArrayBlockingQueue<Object> queue = new ArrayBlockingQueue<>(3);

        System.out.println(queue.offer("a"));
        System.out.println(queue.offer("b"));
        System.out.println(queue.offer("c"));

        //return false 不会抛出异常
        System.out.println(queue.offer("d"));

        System.out.println(queue.peek());

        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());

        //return null 不会抛出异常
        System.out.println(queue.poll());
    }

    //3、阻塞等待
    public static void test3() throws InterruptedException {
        ArrayBlockingQueue<Object> queue = new ArrayBlockingQueue<>(3);

        queue.put("a");
        queue.put("b");
        queue.put("c");

        //等待
        //queue.put("d");

        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());

        //队列为空，等待
        System.out.println(queue.take());

    }

    /**
     * 4.超时等待
     */
    public static void test4() throws InterruptedException {
        ArrayBlockingQueue<Object> queue = new ArrayBlockingQueue<>(3);

        System.out.println(queue.offer("a"));
        System.out.println(queue.offer("b"));
        System.out.println(queue.offer("c"));

        System.out.println(queue.offer("d", 2, TimeUnit.SECONDS));

        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());

        System.out.println(queue.poll(2, TimeUnit.SECONDS));


    }

}

