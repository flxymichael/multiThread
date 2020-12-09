package juc.supportClass;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量
 * 六车，三个停车位
 * 多个共享资源互斥的使用
 * 熔断、限流
 */
public class SemaphoreTest {
    public static void main(String[] args) {

        //线程数量：停车位
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                //acquire()获得，如果已经满了，等待到被释放为止
                //release()释放，会将当前的信号量释放+1，然后唤醒等待的线程
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName().toString() + "得到车位");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(Thread.currentThread().getName().toString() + "离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //所有关闭、释放的操作都应该放到finally中
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }


    }
}
