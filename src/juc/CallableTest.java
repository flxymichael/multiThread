package juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Callable 相较于runnable：
 * 1、可以抛出异常 * 2、有返回值 * 3、启动方法不同 call(),run()
 * 线程启动方式只有一个         new Thread().start();
 * Thread()中参数只能是Runnable
 * Runnable有一种实现类是FutureTask<v>();
 * FutureTask<v>()中的参数可以是callable
 */

/**
 * 有缓存
 * 可能会被阻塞
 */

public class CallableTest {
    public static void main(String[] args) {
        CallableThread callableThread = new CallableThread();
        FutureTask<Integer> futureTask = new FutureTask<>(callableThread);//适配类
        new Thread(futureTask,"A").start();
        new Thread(futureTask,"B").start();//会打印两个call，可能是A也可能是B，因为结果会被缓存，提高效率

        try {
            Integer integer = futureTask.get();//get方法可能产生阻塞,把它放到最后，或者异步处理
            System.out.println(integer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class CallableThread implements Callable<Integer>{
    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName().toString()+"call()");
        //可能会有耗时的操作
        return 666;
    }
}
