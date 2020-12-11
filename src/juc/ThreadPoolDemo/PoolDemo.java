package juc.ThreadPoolDemo;

import java.util.concurrent.*;

/**
 * 线程池
 *
 *
 *
 * Executors 工具类
 * 三大方法：
 * ExecutorService service2 = Executors.newSingleThreadExecutor(); 容量1
 * ExecutorService service = Executors.newFixedThreadPool(10);     固定容量
 * ExecutorService service1 = Executors.newCachedThreadPool();     可变容量
 *
 * 但一般不用Executors创建线程池（存在资源耗尽风险）用ThreadPoolExecutor
 *
 *
 *
 * ThreadPoolExecutor 创建线程池
 * 七大参数
 * int corePoolSize,     //永远开启的容量
 * int maximumPoolSize,  //最大开启的容量
 * long keepAliveTime,   //最大开启的容量在长时间未使用后，会关闭，只剩coreSize
 * TimeUnit unit,        //时间单位
 * BlockingQueue<Runnable> workQueue,  在最大容量之外，还可以排队等待的容量
 * ThreadFactory threadFactory,        有默认值，一般不变
 * RejectedExecutionHandler handler    max+queue满了后的拒绝策略
 *
 *
 * 四种拒绝策略
 * ThreadPoolExecutor.AbortPolicy() //不处理，抛出异常
 * ThreadPoolExecutor.CallerRunsPolicy() //哪来的去哪里，在该例子中会返回给main
 * ThreadPoolExecutor.DiscardOldestPolicy() //尝试和最早的线程竞争，不抛出异常
 * ThreadPoolExecutor.DiscardPolicy() //队列满了，抛弃，不抛出异常
 *
 *
 */
public class PoolDemo {
    public static void main(String[] args) {
//        ExecutorService service = Executors.newFixedThreadPool(10);
//        ExecutorService service1 = Executors.newCachedThreadPool();
//        ExecutorService service2 = Executors.newSingleThreadExecutor();

        //最大线程池如何定义？（调优）
        //CPU密集型：等于CPU核心数量决定，使CPU效率最大
        //IO密集型：大于项目中十分耗IO的线程
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                3,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        try {
            for (int i = 0; i < 100; i++) {
                //使用线程池后，用execute来创建线程
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName());
                });
            }
        } finally {
            threadPool.shutdown();
        }

    }

}

